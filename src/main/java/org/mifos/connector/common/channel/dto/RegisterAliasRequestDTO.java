/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.channel.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.mifos.connector.common.mojaloop.type.IdentifierType;

public class RegisterAliasRequestDTO {

    @NotEmpty
    private String accountId;
    @NotNull
    private IdentifierType idType;
    @NotEmpty
    private String idValue;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public IdentifierType getIdType() {
        return idType;
    }

    public void setIdType(IdentifierType idType) {
        this.idType = idType;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }
}
