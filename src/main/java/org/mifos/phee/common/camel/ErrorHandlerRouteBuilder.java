/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.phee.common.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONObject;

import static org.mifos.phee.common.mojaloop.type.ErrorCode.SERVER_TIMED_OUT;

public abstract class ErrorHandlerRouteBuilder extends RouteBuilder {

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
        onException(Exception.class)
                .routeId("errorHandlerRoute")
                .handled(true)
                .log(LoggingLevel.ERROR, "@@ unhandled exception. Body: ${body}, stacktrace: ${exception.stacktrace}")
                .stop();

    }
}
