/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.camel;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.mifos.connector.common.camel.AuthRouteBuilder.AUTH_ERROR;
import static org.mifos.connector.common.camel.AuthRouteBuilder.HAS_AUTHORITY;
import static org.mifos.connector.common.camel.AuthRouteBuilder.UNKNOWN_ERROR;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONObject;

public abstract class ErrorHandlerRouteBuilder extends RouteBuilder {

    private AuthProcessor authProcessor;
    private AuthProperties properties;

    public ErrorHandlerRouteBuilder() {}

    public ErrorHandlerRouteBuilder(AuthProcessor authProcessor, AuthProperties properties) {
        this.authProcessor = authProcessor;
        this.properties = properties;
    }

    public static JSONObject createError(String errorCode, String errorDescription) {
        JSONObject errorObject = new JSONObject();
        JSONObject error = new JSONObject();
        error.put("errorCode", errorCode);
        error.put("errorDescription", errorDescription);
        errorObject.put("errorInformation", error);
        return errorObject;
    }

    @Override
    public void configure() {
        onException(Exception.class).routeId("errorHandlerRoute")
                .log(LoggingLevel.ERROR, "@@ unhandled exception. Body: ${body}, stacktrace: ${exception.stacktrace}").process(e -> {
                    boolean isHttpOrServletHeaderExist = e.getIn().getHeaders().entrySet().stream().anyMatch(h -> {
                        String headerKey = h.getKey();
                        return headerKey != null && (headerKey.startsWith("CamelHttp") || headerKey.startsWith("CamelServlet"));
                    });
                    if (isHttpOrServletHeaderExist) {
                        e.getIn().setBody(null);
                        e.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                    }
                }).handled(true).stop();

        if (authProcessor != null && properties != null) {
            properties.getSettings().forEach(s -> configureEndpointWithAuthority(s.getEndpoint(), s.getAuthority()));
        }
    }

    private void configureEndpointWithAuthority(String endpoint, String requiredAuthority) {
        interceptFrom(endpoint).process(e -> {
            e.setProperty(HAS_AUTHORITY, requiredAuthority);
        }).process(authProcessor).choice().when(simple("${exchangeProperty." + AUTH_ERROR + "} == true")).process(e -> {
            e.getIn().setHeader(HTTP_RESPONSE_CODE, 401);
        }).stop().endChoice().when(simple("${exchangeProperty." + UNKNOWN_ERROR + "} == true")).process(e -> {
            e.getIn().setHeader(HTTP_RESPONSE_CODE, 400);
        }).stop().endChoice().end();
    }
}
