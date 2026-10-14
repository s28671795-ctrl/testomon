/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import liquibase.database.jvm.JdbcConnection;

public interface ConfigurationValueObfuscator<DataType> {
    public static final ConfigurationValueObfuscator<String> STANDARD = value -> value == null ? null : "*****";
    public static final ConfigurationValueObfuscator<String> URL_OBFUSCATOR = value -> value == null ? null : JdbcConnection.sanitizeUrl(value);
    public static final ConfigurationValueObfuscator<String> NONE = value -> value;

    public DataType obfuscate(DataType var1);
}

