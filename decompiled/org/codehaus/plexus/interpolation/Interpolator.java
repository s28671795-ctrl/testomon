/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.List;
import org.codehaus.plexus.interpolation.BasicInterpolator;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.ValueSource;

public interface Interpolator
extends BasicInterpolator {
    public void addValueSource(ValueSource var1);

    public void removeValuesSource(ValueSource var1);

    public void addPostProcessor(InterpolationPostProcessor var1);

    public void removePostProcessor(InterpolationPostProcessor var1);

    public String interpolate(String var1, String var2) throws InterpolationException;

    public String interpolate(String var1, String var2, RecursionInterceptor var3) throws InterpolationException;

    public List getFeedback();

    public void clearFeedback();

    public boolean isCacheAnswers();

    public void setCacheAnswers(boolean var1);

    public void clearAnswers();
}

