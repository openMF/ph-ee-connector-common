package org.mifos.connector.common.gsma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GsmaTransfer {
    @JsonProperty("requestingOrganisationTransactionReference")
    private String requestingOrganisationTransactionReference;
    @JsonProperty("subType")
    private String subType;
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("descriptionText")
    private String descriptionText;
    @JsonProperty("requestDate")
    private String requestDate;
    @JsonProperty("customData")
    private List<CustomData> customData;
    @JsonProperty("payer")
    private List<Party> payer;
    @JsonProperty("payee")
    private List<Party> payee;
}
