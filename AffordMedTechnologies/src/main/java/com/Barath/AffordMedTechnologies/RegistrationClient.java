package com.Barath.AffordMedTechnologies;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RegistrationClient {

    public static void main(String[] args) {
        String url = "http://20.244.56.144/evaluation-service/register";

        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("ramkrishna@abc.edu");
        request.setName("Ram Krishna");
        request.setMobileNo("9999999999");
        request.setGithubUsername("github");
        request.setRollNo("aa1bb");
        request.setCollegeName("ABC University");
        request.setAccessCode("xgAsNC");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegistrationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RegistrationResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                RegistrationResponse.class
        );

        RegistrationResponse responseBody = response.getBody();
        System.out.println("Client ID: " + responseBody.getClientID());
        System.out.println("Client Secret: " + responseBody.getClientSecret());
    }
}

