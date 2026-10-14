/*
 * Decompiled with CFR 0.152.
 */
package jakarta.inject;

import jakarta.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Documented
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Named {
    public String value() default "";
}

