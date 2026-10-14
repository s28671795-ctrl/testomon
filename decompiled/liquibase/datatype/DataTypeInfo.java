/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface DataTypeInfo {
    public String name();

    public int minParameters();

    public int maxParameters();

    public String[] aliases() default {};

    public String description() default "##default";

    public int priority();
}

