/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.mojaloop.type;

public enum IdentifierType {

    MSISDN, EMAIL, PERSONAL_ID, BUSINESS, DEVICE, ACCOUNT_ID, IBAN, ALIAS;

    public static final String[] REGEX_VALUES = { "a" };
}
