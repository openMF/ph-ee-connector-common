package org.mifos.connector.common.gsma.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomData {
    public String key;
    public Object value;
}
