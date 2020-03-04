package org.mifos.common.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public abstract class ErrorHandlerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        onException(Exception.class)
                .routeId("errorHandlerRoute")
                .handled(true)
                .log(LoggingLevel.ERROR, "@@ unhandled exception. Body: ${body}, stacktrace: ${exception.stacktrace}")
                .stop();

    }
}
