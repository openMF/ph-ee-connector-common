/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */

/**
@Author Sidhant Gupta
*/
package org.mifos.connector.common.gsma.dto;

/*
{
    "status": "available",
    "subStatus": null,
    "lei": "AAAA0012345678901299"
}
 */
//Refactor to use interoperability APIs instead.

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStatus {

    String status;
    String subStatus;
    String lei;
}