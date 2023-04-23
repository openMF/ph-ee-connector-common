package org.mifos.connector.common.gsma.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessTokenDTO {

    private String access_token;
    private int expires_in;

}
