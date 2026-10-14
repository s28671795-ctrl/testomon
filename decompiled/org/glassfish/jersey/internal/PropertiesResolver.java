/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.util.PropertiesHelper;

public interface PropertiesResolver {
    public <T> T resolveProperty(String var1, Class<T> var2);

    public <T> T resolveProperty(String var1, T var2);

    public static PropertiesResolver create(final Configuration configuration, final PropertiesDelegate delegate) {
        return new PropertiesResolver(){

            @Override
            public <T> T resolveProperty(String name, Class<T> type) {
                return this.resolveProperty(name, null, type);
            }

            @Override
            public <T> T resolveProperty(String name, T defaultValue) {
                return (T)this.resolveProperty(name, defaultValue, defaultValue.getClass());
            }

            private <T> T resolveProperty(String name, Object defaultValue, Class<T> type) {
                Object result = configuration.getProperty(name);
                if (result != null) {
                    defaultValue = result;
                }
                if ((result = delegate.getProperty(name)) == null) {
                    result = defaultValue;
                }
                return result == null ? null : (T)PropertiesHelper.convertValue(result, type);
            }
        };
    }
}

