/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import org.codehaus.plexus.interpolation.QueryEnabledValueSource;
import org.codehaus.plexus.interpolation.ValueSource;

public abstract class AbstractFunctionValueSourceWrapper
implements ValueSource {
    private final ValueSource valueSource;

    protected AbstractFunctionValueSourceWrapper(ValueSource valueSource) {
        this.valueSource = valueSource;
    }

    @Override
    public Object getValue(String expression) {
        Object value = this.valueSource.getValue(expression);
        String expr = expression;
        if (this.valueSource instanceof QueryEnabledValueSource) {
            expr = ((QueryEnabledValueSource)this.valueSource).getLastExpression();
        }
        return this.executeFunction(expr, value);
    }

    protected ValueSource getValueSource() {
        return this.valueSource;
    }

    protected abstract Object executeFunction(String var1, Object var2);
}

