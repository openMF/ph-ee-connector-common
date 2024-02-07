package org.mifos.connector.common.channel.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Errors {
    public String errorCategory;
    public String errorCode;
    public String errorDescription;
    public List<ErrorParameter> errorParameters;
}
