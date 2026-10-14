/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

public interface Explain {
    public double rows();

    public double cost();

    public String plan();
}

