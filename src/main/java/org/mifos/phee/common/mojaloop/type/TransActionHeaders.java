/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.phee.common.mojaloop.type;

public enum TransActionHeaders {

    FSPIOP_SOURCE("fspiop-source"),
    FSPIOP_DESTINATION("fspiop-destination"),
    PARTIES_CONTENT_TYPE("application/vnd.interoperability.parties+json;version=1.0"),
    PARTIES_ACCEPT_TYPE("application/vnd.interoperability.parties+json;version=1.0"),
    QUOTES_CONTENT_TYPE("application/vnd.interoperability.quotes+json;version=1.0"),
    QUOTES_ACCEPT_TYPE("application/vnd.interoperability.quotes+json;version=1.0"),
    TRANSFERS_CONTENT_TYPE("application/vnd.interoperability.transfers+json;version=1.0"),
    TRANSFERS_ACCEPT_TYPE("application/vnd.interoperability.transfers+json;version=1.0");

    private String headerValue;

    TransActionHeaders(String headerValue) {
        this.headerValue = headerValue;
    }

    public String headerValue() {
        return this.headerValue;
    }
}
