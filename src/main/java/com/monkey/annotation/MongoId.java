package com.monkey.annotation;


import com.monkey.utils.ObjectIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * renfei
 *
 *
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ObjectIdValidator.class})
public @interface MongoId {
    boolean canNull() default false;//可以为null，默认不可以

    boolean canEmpty() default false;//可以为""，默认不可以

    String message() default "mongodb String must be 24 char length!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}