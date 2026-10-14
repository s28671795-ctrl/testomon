/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import liquibase.change.DatabaseChangeNote;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface DatabaseChange {
    public String name();

    public String description();

    public int priority();

    public String[] appliesTo() default {};

    public DatabaseChangeNote[] databaseNotes() default {};

    public String since() default "";
}

