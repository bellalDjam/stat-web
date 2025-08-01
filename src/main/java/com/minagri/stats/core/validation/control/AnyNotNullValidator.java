package com.minagri.stats.core.validation.control;

import com.minagri.stats.core.validation.entity.AnyNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class AnyNotNullValidator implements ConstraintValidator<AnyNotNull, Object> {
    private AnyNotNull annotation;

    @Override
    public void initialize(AnyNotNull annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintContext) {
        if (o == null) {
            return false;
        }

        List<String> fieldNames;
        if (annotation.value().length != 0) {
            fieldNames = Arrays.asList(annotation.value());
        } else {
            fieldNames = Arrays.stream(o.getClass().getDeclaredFields()).map(Field::getName).toList();
        }

        boolean isValid = fieldNames.stream().anyMatch(fieldName -> {
            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(o) != null;
            } catch (Exception e) {
                return false;
            }
        });

        if (!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext
                    .buildConstraintViolationWithTemplate("at least one field should not be null: " + fieldNames)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
