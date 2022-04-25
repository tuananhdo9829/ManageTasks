package com.tuananhdo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidate implements ConstraintValidator<ValidateEnum, String> {

    private List<String> valueList;

    @Override
    public void initialize(ValidateEnum constraintAnnotation) {
        valueList = new ArrayList<>();
        for (String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return valueList.contains(value.toUpperCase());
    }
}
