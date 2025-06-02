package com.Question2.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final RestTemplate restTemplate;

    public StockService() {
        this.restTemplate = new RestTemplate();
    }

    public StockResponse getAveragePrice(String ticker, int minutes) {
        // Call external stock exchange API (replace with real URL)
        String url = "http://external-stock-api/stocks/" + ticker + "/history";

        ResponseEntity<PricePoint[]> response = restTemplate.getForEntity(url, PricePoint[].class);
        PricePoint[] allPricePoints = response.getBody();

        if (allPricePoints == null || allPricePoints.length == 0) {
            return new StockResponse(); // return empty or handle error as needed
        }

        Instant cutoff = Instant.now().minus(Duration.ofMinutes(minutes));

        // Filter price points for last 'minutes' only
        List<PricePoint> filtered = Arrays.stream(allPricePoints)
                .filter(pp -> pp.getLastUpdatedAt().isAfter(cutoff))
                .collect(Collectors.toList());

        double avg = filtered.stream()
                .mapToDouble(PricePoint::getPrice)
                .average()
                .orElse(0.0);

        StockResponse stockResponse = new StockResponse();
        stockResponse.setAverageStockPrice(avg);
        stockResponse.setPriceHistory(filtered);

        return stockResponse;
    }
}

