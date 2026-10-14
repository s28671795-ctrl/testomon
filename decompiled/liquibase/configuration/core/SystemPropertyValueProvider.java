/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration.core;

import java.util.Map;
import liquibase.configuration.AbstractMapConfigurationValueProvider;

public class SystemPropertyValueProvider
extends AbstractMapConfigurationValueProvider {
    @Override
    public int getPrecedence() {
        return 200;
    }

    @Override
    protected String getSourceDescription() {
        return "System property";
    }

    @Override
    protected Map<?, ?> getMap() {
        return System.getProperties();
    }
}

