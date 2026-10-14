/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.Map;
import org.codehaus.plexus.interpolation.AbstractValueSource;

public class MapBasedValueSource
extends AbstractValueSource {
    private final Map values;

    public MapBasedValueSource(Map values2) {
        super(false);
        this.values = values2;
    }

    @Override
    public Object getValue(String expression) {
        return this.values == null ? null : this.values.get(expression);
    }
}

