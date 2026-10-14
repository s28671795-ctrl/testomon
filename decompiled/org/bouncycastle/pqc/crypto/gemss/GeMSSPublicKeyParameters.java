/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import org.bouncycastle.pqc.crypto.gemss.GeMSSKeyParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSParameters;
import org.bouncycastle.util.Arrays;

public class GeMSSPublicKeyParameters
extends GeMSSKeyParameters {
    private final byte[] pk;

    public GeMSSPublicKeyParameters(GeMSSParameters geMSSParameters, byte[] byArray) {
        super(false, geMSSParameters);
        this.pk = new byte[byArray.length];
        System.arraycopy(byArray, 0, this.pk, 0, this.pk.length);
    }

    public byte[] getPK() {
        return this.pk;
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.pk);
    }
}

