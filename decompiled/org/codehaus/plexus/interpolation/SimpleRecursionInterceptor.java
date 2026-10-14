/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.codehaus.plexus.interpolation.RecursionInterceptor;

public class SimpleRecursionInterceptor
implements RecursionInterceptor {
    private Stack expressions = new Stack();

    @Override
    public void expressionResolutionFinished(String expression) {
        this.expressions.pop();
    }

    @Override
    public void expressionResolutionStarted(String expression) {
        this.expressions.push(expression);
    }

    @Override
    public boolean hasRecursiveExpression(String expression) {
        return this.expressions.contains(expression);
    }

    @Override
    public List getExpressionCycle(String expression) {
        int idx = this.expressions.indexOf(expression);
        if (idx < 0) {
            return Collections.EMPTY_LIST;
        }
        return this.expressions.subList(idx, this.expressions.size());
    }

    @Override
    public void clear() {
        this.expressions.clear();
    }
}

