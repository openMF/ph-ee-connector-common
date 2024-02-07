package org.mifos.connector.common.webclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class WebClientAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebClientAdapter.class);
    private final WebClient webClient;

    public WebClientAdapter() {
        this.webClient = WebClient.builder().build();
    }

    // GET
    public <T> CompletableFuture<T> getRequest(String url, HttpHeaders headers, Class<T> responseType) {
        log.info("Executing GET request to URL: {}", url);
        return webClient.get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.info("GET request to {} succeeded", url))
                .doOnError(error -> log.error("GET request to {} failed: {}", url, error.getMessage()))
                .doOnNext(body -> log.debug("GET Response: {}", body))
                .toFuture();
    }

    public <T> CompletableFuture<T> getRequest(String url, Class<T> responseType) {
        return getRequest(url, new HttpHeaders(), responseType);
    }

    // POST
    public <T, R> CompletableFuture<R> postRequest(String url, T body, HttpHeaders headers, MediaType mediaType, Class<R> responseType) {
        log.info("Executing POST request to URL: {}", url);
        return webClient.post()
                .uri(url)
                .contentType(mediaType)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(body), body.getClass())
                .retrieve()
                .bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.info("POST request to {} succeeded", url))
                .doOnError(error -> log.error("POST request to {} failed: {}", url, error.getMessage()))
                .doOnNext(responseBody -> log.debug("POST Response: {}", responseBody))
                .toFuture();
    }

    public <T, R> CompletableFuture<R> postRequest(String url, T body, Class<R> responseType) {
        return postRequest(url, body, new HttpHeaders(), MediaType.APPLICATION_JSON, responseType);
    }


    // PUT
    public <T, R> CompletableFuture<R> putRequest(String url, T body, HttpHeaders headers, MediaType mediaType, Class<R> responseType) {
        log.info("Executing PUT request to URL: {}", url);
        return webClient.put()
                .uri(url)
                .contentType(mediaType)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.info("PUT request to {} succeeded", url))
                .doOnError(error -> log.error("PUT request to {} failed: {}", url, error.getMessage()))
                .toFuture();
    }

    public <T, R> CompletableFuture<R> putRequest(String url, T body, Class<R> responseType) {
        return putRequest(url, body, new HttpHeaders(), MediaType.APPLICATION_JSON, responseType);
    }

    // DELETE
    public CompletableFuture<String> deleteRequest(String url, HttpHeaders headers) {
        log.info("Executing DELETE request to URL: {}", url);
        return webClient.delete()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(responseBody -> log.info("DELETE request to {} succeeded", url))
                .doOnError(error -> log.error("DELETE request to {} failed: {}", url, error.getMessage()))
                .doOnNext(responseBody -> log.debug("DELETE Response Body: {}", responseBody))
                .toFuture();
    }

    public CompletableFuture<String> deleteRequest(String url) {
        return deleteRequest(url, new HttpHeaders());
    }
}
