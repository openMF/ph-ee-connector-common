package org.mifos.connector.common.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorParameter {

    private String key;

    private String value;

}
