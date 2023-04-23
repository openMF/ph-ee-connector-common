package org.mifos.connector.common.gsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthErrorDTO {

    private  String errorCode;
    private String errorMessage;
}
