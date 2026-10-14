/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.RecursionInterceptor;

public interface BasicInterpolator {
    public String interpolate(String var1) throws InterpolationException;

    public String interpolate(String var1, RecursionInterceptor var2) throws InterpolationException;
}

