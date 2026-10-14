/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.Map;
import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.InterpolationState;

public class MapBasedValueSource
implements FixedValueSource {
    private final Map values;

    public MapBasedValueSource(Map values2) {
        this.values = values2;
    }

    @Override
    public Object getValue(String expression, InterpolationState interpolationState) {
        return this.values == null ? null : this.values.get(expression);
    }
}

