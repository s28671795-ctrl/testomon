/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.annotation;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Documented
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ServletSecurity {
    public HttpConstraint value() default @HttpConstraint;

    public HttpMethodConstraint[] httpMethodConstraints() default {};

    public static enum TransportGuarantee {
        NONE,
        CONFIDENTIAL;

    }

    public static enum EmptyRoleSemantic {
        PERMIT,
        DENY;

    }
}

