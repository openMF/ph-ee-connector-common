package org.mifos.connector.common.channel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mifos.connector.common.gsma.dto.ErrorParameter;

import java.util.List;

@Getter
@Setter
@ToString
public class Errors {
    public String errorCategory;
    public String errorCode;
    public String errorDescription;
    public List<org.mifos.connector.common.gsma.dto.ErrorParameter> errorParameters = null;
}
