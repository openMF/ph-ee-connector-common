package org.mifos.connector.common.channel.dto;

import java.util.List;
import lombok.*;

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
