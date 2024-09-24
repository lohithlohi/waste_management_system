package com.ust.wastewarden.routes.service;

import com.ust.wastewarden.routes.model.RouteRequest;
import com.ust.wastewarden.routes.model.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoutePlannerService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${geoapify.api.key}")
    public String apiKey;        // = "64994d6242224963b530456ac5455578" /"4982373890ce442b86e8f78c73936e2d";

    public RouteResponse getOptimizedRoute(RouteRequest request) {
        String url = "https://api.geoapify.com/v1/routeplanner?apiKey=" + apiKey;
        log.info("Geoapify API URL: {}", url);

        return webClientBuilder.build()
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(RouteResponse.class)
                .block();
    }
}
