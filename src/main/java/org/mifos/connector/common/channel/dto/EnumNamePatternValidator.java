package org.mifos.connector.common.channel.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {

    private Class<? extends Enum> enumType;

    @Override
    public void initialize(EnumNamePattern annotation) {
        enumType = annotation.enumType();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null && Stream.of(enumType.getEnumConstants())
                .filter(e -> e.equals(value))
                .findFirst()
                .orElse(null) != null;
    }
}
