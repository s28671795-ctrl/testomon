/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import liquibase.change.DatabaseChangeProperty;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface DatabaseChangeProperties {
    public DatabaseChangeProperty[] value();
}

