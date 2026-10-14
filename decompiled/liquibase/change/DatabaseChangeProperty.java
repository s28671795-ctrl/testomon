/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import liquibase.ChecksumVersion;
import liquibase.change.DatabaseChangeProperties;
import liquibase.serializer.LiquibaseSerializable;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Repeatable(value=DatabaseChangeProperties.class)
public @interface DatabaseChangeProperty {
    public String description() default "";

    public String exampleValue() default "";

    public String since() default "";

    public boolean isChangeProperty() default true;

    public String[] requiredForDatabase() default {"COMPUTE"};

    public String[] supportsDatabase() default {"COMPUTE"};

    public String mustEqualExisting() default "";

    public LiquibaseSerializable.SerializationType serializationType() default LiquibaseSerializable.SerializationType.NAMED_FIELD;

    public ChecksumVersion[] version() default {};

    public String[] alternatePropertyNames() default {};
}

