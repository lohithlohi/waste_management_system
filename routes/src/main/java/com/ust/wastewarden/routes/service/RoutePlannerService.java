package com.ust.wastewarden.routes.service;

import com.ust.wastewarden.routes.model.RouteRequest;
import com.ust.wastewarden.routes.model.RouteResponse;
import io.netty.handler.ssl.SslProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import org.springframework.http.HttpStatus;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Service
@Slf4j
public class RoutePlannerService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${geoapify.api.key}")
    private String apiKey;

    public RouteResponse getOptimizedRoute(RouteRequest request) throws Exception {
        // Create a TrustManager that does not validate certificate chains
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        SslContext sslContext = SslContextBuilder.forClient().trustManager(trustManager).build();

        // Create an HttpClient with the custom SSL context
        HttpClient httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        WebClient webClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        String url = "https://api.geoapify.com/v1/routeplanner?apiKey=" + apiKey;
        log.info("Geoapify API URL: {}", url);
        log.info("Request Body: {}", request);

        return webClientBuilder.build()
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(status -> status.value() >= 400 && status.value() < 500,
                        response -> {
                            log.error("Client error: {}", response.statusCode());
                            return response.createException();
                        })
                .onStatus(status -> status.value() >= 500,
                        response -> {
                            log.error("Server error: {}", response.statusCode());
                            return response.createException();
                        })
                .bodyToMono(RouteResponse.class)
                .block();
    }
}


//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RoutePlannerService {
//
//    @Autowired
//    private WebClient.Builder webClientBuilder;
//
//    @Value("${geoapify.api.key}")
//    private String apiKey;
//
//    public RouteResponse getOptimizedRoute(RouteRequest request) throws SSLException {
//        // Disabling SSL verification
//        SslContextBuilder sslContextBuilder = SslContextBuilder.forClient()
//                .trustManager(new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//                }).sslProvider(SslProvider.JDK);
//
//        HttpClient httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContextBuilder));
//
//        WebClient webClient = webClientBuilder
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//
//        String url = "https://api.geoapify.com/v1/routeplanner?apiKey=" + apiKey;
//        log.info("Geoapify API URL: {}", url);
//
//        return webClient.post()
//                .uri(url)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(BodyInserters.fromValue(request))
//                .retrieve()
//                .bodyToMono(RouteResponse.class)
//                .block();
//    }
//}



//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RoutePlannerService {
//
//    @Autowired
//    private WebClient.Builder webClientBuilder;
//
//    @Value("${geoapify.api.key}")
//    public String apiKey;        // = "64994d6242224963b530456ac5455578" /"4982373890ce442b86e8f78c73936e2d";
//
//    public RouteResponse getOptimizedRoute(RouteRequest request) {
//        String url = "https://api.geoapify.com/v1/routeplanner?apiKey=" + apiKey;
//        log.info("Geoapify API URL: {}", url);
//
//        return webClientBuilder.build()
//                .post()
//                .uri(url)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(BodyInserters.fromValue(request))
//                .retrieve()
//                .bodyToMono(RouteResponse.class)
//                .block();
//    }
//}

