/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.codehaus.plexus.interpolation.reflection.ReflectionValueExtractor;

public class ObjectBasedValueSource
extends AbstractValueSource {
    private final Object root;

    public ObjectBasedValueSource(Object root) {
        super(true);
        this.root = root;
    }

    @Override
    public Object getValue(String expression) {
        if (expression == null || expression.trim().length() < 1) {
            return null;
        }
        try {
            return ReflectionValueExtractor.evaluate(expression, this.root, false);
        }
        catch (Exception e) {
            this.addFeedback("Failed to extract '" + expression + "' from: " + this.root, e);
            return null;
        }
    }
}

