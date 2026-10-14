/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.RecursionInterceptor;

public class InterpolationCycleException
extends InterpolationException {
    private static final long serialVersionUID = 1L;

    public InterpolationCycleException(RecursionInterceptor recursionInterceptor, String realExpr, String wholeExpr) {
        super("Detected the following recursive expression cycle in '" + realExpr + "': " + recursionInterceptor.getExpressionCycle(realExpr), wholeExpr);
    }
}

