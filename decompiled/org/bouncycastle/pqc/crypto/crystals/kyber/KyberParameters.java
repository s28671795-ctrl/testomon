/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import org.bouncycastle.pqc.crypto.KEMParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

public class KyberParameters
implements KEMParameters {
    public static final KyberParameters kyber512 = new KyberParameters("kyber512", 2, 256, false);
    public static final KyberParameters kyber768 = new KyberParameters("kyber768", 3, 256, false);
    public static final KyberParameters kyber1024 = new KyberParameters("kyber1024", 4, 256, false);
    private final String name;
    private final int k;
    private final int sessionKeySize;
    private final boolean usingAes;

    private KyberParameters(String string, int n, int n2, boolean bl) {
        this.name = string;
        this.k = n;
        this.sessionKeySize = n2;
        this.usingAes = bl;
    }

    public String getName() {
        return this.name;
    }

    KyberEngine getEngine() {
        return new KyberEngine(this.k, this.usingAes);
    }

    public int getSessionKeySize() {
        return this.sessionKeySize;
    }
}

