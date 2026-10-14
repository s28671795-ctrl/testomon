/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.concurrent.atomic.AtomicReference;
import liquibase.Scope;
import liquibase.configuration.ConfigurationValueConverter;
import liquibase.util.ObjectUtil;

public class ConfigurationValueUtils {
    public static <T> T convertDataType(String key, T value, ConfigurationValueConverter<T> converter) {
        AtomicReference reference = new AtomicReference();
        try {
            Scope.child(ObjectUtil.ARGUMENT_KEY, (Object)key, () -> reference.set(converter.convert(value)));
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return (T)reference.get();
    }
}

