/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.mojaloop.dto;

import java.time.LocalDateTime;
import org.mifos.connector.common.mojaloop.type.TransferState;
import org.mifos.connector.common.util.ContextUtil;

public class TransferSwitchResponseDTO {

    private String fulfilment;
    private String completedTimestamp;
    private TransferState transferState; // mandatory
    private ExtensionList extensionList;

    public TransferSwitchResponseDTO() {}

    public TransferSwitchResponseDTO(String fulfilment, LocalDateTime completedTimestamp, TransferState transferState,
            ExtensionList extensionList) {
        this.fulfilment = fulfilment;
        this.completedTimestamp = ContextUtil.formatDate(completedTimestamp);
        this.transferState = transferState;
        this.extensionList = extensionList;
    }

    public TransferSwitchResponseDTO(TransferState transferState) {
        this(null, null, transferState, null);
    }

    public String getFulfilment() {
        return fulfilment;
    }

    public void setFulfilment(String fulfilment) {
        this.fulfilment = fulfilment;
    }

    public String getCompletedTimestamp() {
        return completedTimestamp;
    }

    public void setCompletedTimestamp(String completedTimestamp) {
        this.completedTimestamp = completedTimestamp;
    }

    public TransferState getTransferState() {
        return transferState;
    }

    public void setTransferState(TransferState transferState) {
        this.transferState = transferState;
    }

    public ExtensionList getExtensionList() {
        return extensionList;
    }

    public void setExtensionList(ExtensionList extensionList) {
        this.extensionList = extensionList;
    }
}
