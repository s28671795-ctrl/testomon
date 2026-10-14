/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface ExtensibleObjectAttribute {
    public String description();

    public boolean required() default false;
}

