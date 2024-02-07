package org.mifos.connector.common.webclient;

import java.util.concurrent.CompletableFuture;
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
        return webClient.get().uri(url).headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve().bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.debug("GET request to {} succeeded, response Body {}", url, responseBody))
                .doOnError(error -> log.error("GET request to {} failed: {}", url, error.getMessage())).toFuture();
    }

    public <T> CompletableFuture<T> getRequest(String url, Class<T> responseType) {
        return getRequest(url, new HttpHeaders(), responseType);
    }

    // POST
    public <T, R> CompletableFuture<R> postRequest(String url, T body, HttpHeaders headers, MediaType mediaType, Class<R> responseType) {
        log.info("Executing POST request to URL: {}", url);
        return webClient.post().uri(url).contentType(mediaType).headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(body), body.getClass()).retrieve().bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.debug("POST request to {} succeeded with response {}", url, responseBody))
                .doOnError(error -> log.error("POST request to {} failed: {}", url, error.getMessage())).toFuture();
    }

    public <T, R> CompletableFuture<R> postRequest(String url, T body, Class<R> responseType) {
        return postRequest(url, body, new HttpHeaders(), MediaType.APPLICATION_JSON, responseType);
    }

    public <R> CompletableFuture<R> postRequest(String url, HttpHeaders headers, MediaType mediaType, Class<R> responseType) {
        log.info("Executing POST request to URL: {} without a body", url);
        return webClient.post().uri(url).contentType(mediaType).headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve()
                .bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.debug("POST request to {} succeeded with response {}", url, responseBody))
                .doOnError(error -> log.error("POST request to {} failed: {}", url, error.getMessage())).toFuture();
    }

    public <R> CompletableFuture<R> postRequest(String url, Class<R> responseType) {
        return postRequest(url, new HttpHeaders(), MediaType.APPLICATION_JSON, responseType);
    }

    public <R> CompletableFuture<R> postRequestWithoutBodyAndHeaders(String url, Class<R> responseType) {
        log.info("Executing POST request to URL: {} without a body and headers", url);
        return webClient.post().uri(url).retrieve().bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.debug("POST request to {} succeeded, response {}", url, responseBody))
                .doOnError(error -> log.error("POST request to {} failed: {}", url, error.getMessage())).toFuture();
    }

    // PUT
    public <T, R> CompletableFuture<R> putRequest(String url, T body, HttpHeaders headers, MediaType mediaType, Class<R> responseType) {
        log.info("Executing PUT request to URL: {}", url);
        return webClient.put().uri(url).contentType(mediaType).headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(body)).retrieve().bodyToMono(responseType)
                .doOnSuccess(responseBody -> log.debug("PUT request to {} succeeded, response {}", url, body))
                .doOnError(error -> log.error("PUT request to {} failed: {}", url, error.getMessage())).toFuture();
    }

    public <T, R> CompletableFuture<R> putRequest(String url, T body, Class<R> responseType) {
        return putRequest(url, body, new HttpHeaders(), MediaType.APPLICATION_JSON, responseType);
    }

    // DELETE
    public CompletableFuture<String> deleteRequest(String url, HttpHeaders headers) {
        log.info("Executing DELETE request to URL: {}", url);
        return webClient.delete().uri(url).headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve().bodyToMono(String.class)
                .doOnSuccess(responseBody -> log.debug("DELETE request to {} succeeded, response body {}", url, responseBody))
                .doOnError(error -> log.error("DELETE request to {} failed: {}", url, error.getMessage())).toFuture();
    }

    public CompletableFuture<String> deleteRequest(String url) {
        return deleteRequest(url, new HttpHeaders());
    }

    public <T, R> CompletableFuture<R> sendCallBackPost(String url, T body, HttpHeaders headers, MediaType mediaType,
            Class<R> responseType) {
        String headerName = "X-Correlation-ID";
        log.info("Client Correlation Id: {}", headers.getFirst(headerName));
        return postRequest(url, body, headers, mediaType, responseType);
    }

    public <T, R> CompletableFuture<R> sendCallBackPut(String url, T body, HttpHeaders headers, MediaType mediaType,
            Class<R> responseType) {
        String headerName = "X-Correlation-ID";
        log.info("Client Correlation Id: {}", headers.getFirst(headerName));
        return putRequest(url, body, headers, mediaType, responseType);
    }
}
