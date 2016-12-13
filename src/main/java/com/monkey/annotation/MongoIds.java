package com.monkey.annotation;


import com.monkey.utils.ObjectIdsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * renfei
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ObjectIdsValidator.class})
public @interface MongoIds {
    boolean canNull() default false;

    boolean canEmpty() default false;

    String message() default "mongodb String must be 24 char length!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
