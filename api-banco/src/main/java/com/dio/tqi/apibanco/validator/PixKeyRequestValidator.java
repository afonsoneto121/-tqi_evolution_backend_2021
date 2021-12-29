package com.dio.tqi.apibanco.validator;

import com.dio.tqi.apibanco.dto.request.PixKeyDTORequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PixKeyRequestValidator implements ConstraintValidator<PixKeyValid, PixKeyDTORequest> {
    @Override
    public boolean isValid(PixKeyDTORequest value, ConstraintValidatorContext context) {
        if((value.getKey() == null && value.getKeys() == null) ||
                (value.getKey() != null && value.getKeys() != null)
            ) {
            return false;
        }

        return true;
    }
}
