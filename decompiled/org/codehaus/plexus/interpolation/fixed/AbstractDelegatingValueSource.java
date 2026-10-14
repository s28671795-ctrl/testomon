/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.InterpolationState;

public abstract class AbstractDelegatingValueSource
implements FixedValueSource {
    private final FixedValueSource delegate;

    protected AbstractDelegatingValueSource(FixedValueSource delegate) {
        if (delegate == null) {
            throw new NullPointerException("Delegate ValueSource cannot be null.");
        }
        this.delegate = delegate;
    }

    protected FixedValueSource getDelegate() {
        return this.delegate;
    }

    @Override
    public Object getValue(String expression, InterpolationState interpolationState) {
        return this.getDelegate().getValue(expression, interpolationState);
    }
}

