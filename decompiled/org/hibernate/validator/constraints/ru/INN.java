/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.constraints.ru;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented
@Constraint(validatedBy={})
@Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(value=RetentionPolicy.RUNTIME)
@Repeatable(value=List.class)
@ReportAsSingleViolation
public @interface INN {
    public String message() default "{org.hibernate.validator.constraints.ru.INN.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public Type type() default Type.ANY;

    public static enum Type {
        INDIVIDUAL,
        JURIDICAL,
        ANY;

    }

    @Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(value=RetentionPolicy.RUNTIME)
    @Documented
    public static @interface List {
        public INN[] value();
    }
}

