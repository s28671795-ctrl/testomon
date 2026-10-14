/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import liquibase.Beta;
import liquibase.command.CommandStep;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Beta
public @interface CommandOverride {
    public Class<? extends CommandStep> override();
}

