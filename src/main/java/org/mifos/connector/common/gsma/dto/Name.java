package org.mifos.connector.common.gsma.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Name {

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String nativeName;
}
