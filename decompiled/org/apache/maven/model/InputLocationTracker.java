/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import org.apache.maven.model.InputLocation;

public interface InputLocationTracker {
    public InputLocation getLocation(Object var1);

    public void setLocation(Object var1, InputLocation var2);
}

