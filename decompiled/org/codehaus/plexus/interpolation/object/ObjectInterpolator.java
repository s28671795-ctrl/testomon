/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.object;

import java.util.List;
import org.codehaus.plexus.interpolation.BasicInterpolator;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.RecursionInterceptor;

public interface ObjectInterpolator {
    public void interpolate(Object var1, BasicInterpolator var2) throws InterpolationException;

    public void interpolate(Object var1, BasicInterpolator var2, RecursionInterceptor var3) throws InterpolationException;

    public boolean hasWarnings();

    public List getWarnings();
}

