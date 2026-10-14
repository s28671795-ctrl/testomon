/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.List;

public interface RecursionInterceptor {
    public void expressionResolutionStarted(String var1);

    public void expressionResolutionFinished(String var1);

    public boolean hasRecursiveExpression(String var1);

    public List getExpressionCycle(String var1);

    public void clear();
}

