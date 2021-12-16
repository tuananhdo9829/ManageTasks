package com.tuananhdo.validator;

import javax.validation.Payload;

public @interface ValidateEnum {

    String[] acceptedValues();

    String message() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
