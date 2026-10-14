/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.List;
import org.codehaus.plexus.interpolation.ValueSource;

public abstract class AbstractDelegatingValueSource
implements ValueSource {
    private final ValueSource delegate;

    protected AbstractDelegatingValueSource(ValueSource delegate) {
        if (delegate == null) {
            throw new NullPointerException("Delegate ValueSource cannot be null.");
        }
        this.delegate = delegate;
    }

    protected ValueSource getDelegate() {
        return this.delegate;
    }

    @Override
    public Object getValue(String expression) {
        return this.getDelegate().getValue(expression);
    }

    @Override
    public void clearFeedback() {
        this.delegate.clearFeedback();
    }

    @Override
    public List getFeedback() {
        return this.delegate.getFeedback();
    }
}

