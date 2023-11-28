package org.mifos.connector.common.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mifos.connector.common.gsma.dto.ErrorParameter;

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
