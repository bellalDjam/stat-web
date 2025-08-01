package com.minagri.stats.core.validation.control;

import com.minagri.stats.core.validation.entity.IntSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntSizeValidator implements ConstraintValidator<IntSize, Integer> {
    private IntSize annotation;

    @Override
    public void initialize(IntSize annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintContext) {
        if (integer == null) {
            return true;
        }

        int size = annotation.value();
        boolean isValid = integer.toString().length() == size;

        if (!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext
                    .buildConstraintViolationWithTemplate("size should be " + size)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
