/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.List;

public interface ValueSource {
    public Object getValue(String var1);

    public List getFeedback();

    public void clearFeedback();
}

