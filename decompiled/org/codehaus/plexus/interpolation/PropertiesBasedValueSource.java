/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.Properties;
import org.codehaus.plexus.interpolation.AbstractValueSource;

public class PropertiesBasedValueSource
extends AbstractValueSource {
    private final Properties properties;

    public PropertiesBasedValueSource(Properties properties) {
        super(false);
        this.properties = properties;
    }

    @Override
    public Object getValue(String expression) {
        return this.properties == null ? null : this.properties.getProperty(expression);
    }
}

