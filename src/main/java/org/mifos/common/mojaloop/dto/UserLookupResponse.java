package org.mifos.common.mojaloop.dto;

import org.apache.camel.Exchange;

import java.util.Map;
import java.util.function.Function;

public class UserLookupResponse {
    private String phone;
    private String source;
    private String destination;

    public UserLookupResponse(String phone, String source, String destination) {
        this.phone = phone;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "UserLookupResponse{" +
                "phone='" + phone + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public static Function<Exchange, UserLookupResponse> parser() {
        return exchange -> {
            Map<String, Object> headers = exchange.getIn().getHeaders();

            return new UserLookupResponse(
                    (String) headers.get("phone"),
                    (String) headers.get("fspiop-source"),
                    (String) headers.get("fspiop-destination")
            );
        };
    }
}
