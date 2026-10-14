/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationDefinition;

@Deprecated
public class DefaultLoggerConfiguration
implements AutoloadedConfigurations {
    @Deprecated
    public static ConfigurationDefinition<String> LOG_LEVEL;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase.defaultlogger");
        LOG_LEVEL = builder.define("level", String.class).setDescription("Logging level").setDefaultValue("INFO").setInternal(true).build();
    }
}

