/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.Collections;
import java.util.List;
import org.codehaus.plexus.interpolation.ValueSource;

public class SingleResponseValueSource
implements ValueSource {
    private final String expression;
    private final Object response;

    public SingleResponseValueSource(String expression, Object response) {
        this.expression = expression;
        this.response = response;
    }

    @Override
    public void clearFeedback() {
    }

    @Override
    public List getFeedback() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getValue(String expression) {
        if (this.expression.equals(expression)) {
            return this.response;
        }
        return null;
    }
}

