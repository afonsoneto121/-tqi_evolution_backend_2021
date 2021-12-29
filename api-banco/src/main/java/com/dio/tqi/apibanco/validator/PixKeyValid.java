package com.dio.tqi.apibanco.validator;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Document
@Constraint(validatedBy = PixKeyRequestValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PixKeyValid {
    String message() default "Invalid Pik Key";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
