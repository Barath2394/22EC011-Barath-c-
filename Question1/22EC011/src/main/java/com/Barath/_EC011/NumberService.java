package com.Barath._EC011;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NumberService {

    @Value("${window.size}")
    private int windowSize;

    @Value("${thirdparty.timeout}")
    private int timeoutMs;

    @Value("${thirdparty.baseurl}")
    private String thirdPartyBaseUrl;

    // Store per numberId state (could be extended to multiple categories)
    private final Map<String, LinkedHashSet<Integer>> storedNumbersMap = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate;

    public NumberService() {
        // RestTemplate with timeout settings
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeoutMs);
        factory.setReadTimeout(timeoutMs);
        this.restTemplate = new RestTemplate(factory);
    }

    public NumbersResponse fetchAndCalculate(String numberId) {
        // Validate numberId
        if (!Set.of("p", "f", "e", "r").contains(numberId)) {
            throw new IllegalArgumentException("Invalid numberId");
        }

        LinkedHashSet<Integer> storedNumbers = storedNumbersMap.computeIfAbsent(numberId, k -> new LinkedHashSet<>());

        // Copy previous state for response
        List<Integer> windowPrevState = new ArrayList<>(storedNumbers);

        List<Integer> thirdPartyNumbers;
        try {
            // Call 3rd party API with timeout, get numbers list
            String url = thirdPartyBaseUrl + "/" + numberId;
            // Assuming the API returns JSON array of integers
            ResponseEntity<List<Integer>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Integer>>() {}
            );
            thirdPartyNumbers = response.getBody();
            if (thirdPartyNumbers == null) {
                thirdPartyNumbers = Collections.emptyList();
            }
        } catch (Exception e) {
            // Timeout or error: return response with unchanged stored numbers
            double avgUnchanged = calculateAverage(storedNumbers);
            return buildResponse(windowPrevState, windowPrevState, Collections.emptyList(), avgUnchanged);
        }

        // Update stored numbers uniquely and maintain insertion order
        for (Integer num : thirdPartyNumbers) {
            if (!storedNumbers.contains(num)) {
                // Add new number
                storedNumbers.add(num);
                // Maintain window size: remove oldest if exceeded
                if (storedNumbers.size() > windowSize) {
                    Iterator<Integer> it = storedNumbers.iterator();
                    it.next();
                    it.remove();
                }
            }
        }

        List<Integer> windowCurrState = new ArrayList<>(storedNumbers);

        double avg = calculateAverage(storedNumbers);

        return buildResponse(windowPrevState, windowCurrState, thirdPartyNumbers, avg);
    }

    private double calculateAverage(Collection<Integer> numbers) {
        if (numbers.isEmpty()) return 0.0;
        return numbers.stream().mapToInt(i -> i).average().orElse(0.0);
    }

    private NumbersResponse buildResponse(List<Integer> prev, List<Integer> curr, List<Integer> numbers, double avg) {
        NumbersResponse response = new NumbersResponse();
        response.setWindowPrevState(prev);
        response.setWindowCurrState(curr);
        response.setNumbers(numbers);
        response.setAvg(String.format("%.2f", avg));
        return response;
    }
}

