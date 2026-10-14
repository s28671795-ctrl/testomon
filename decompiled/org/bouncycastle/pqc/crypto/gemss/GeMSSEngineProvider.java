/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import org.bouncycastle.pqc.crypto.gemss.GeMSSEngine;

public interface GeMSSEngineProvider {
    public GeMSSEngine get();

    public int getN();
}

