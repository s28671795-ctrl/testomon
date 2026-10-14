/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.Properties;
import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.InterpolationState;

public class PropertiesBasedValueSource
implements FixedValueSource {
    private final Properties properties;

    public PropertiesBasedValueSource(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Object getValue(String expression, InterpolationState interpolationState) {
        return this.properties == null ? null : this.properties.getProperty(expression);
    }
}

