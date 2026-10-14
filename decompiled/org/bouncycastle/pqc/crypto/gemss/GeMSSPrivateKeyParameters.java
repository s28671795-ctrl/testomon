/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import org.bouncycastle.pqc.crypto.gemss.GeMSSKeyParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSParameters;
import org.bouncycastle.util.Arrays;

public class GeMSSPrivateKeyParameters
extends GeMSSKeyParameters {
    final byte[] sk;

    public GeMSSPrivateKeyParameters(GeMSSParameters geMSSParameters, byte[] byArray) {
        super(false, geMSSParameters);
        this.sk = new byte[byArray.length];
        System.arraycopy(byArray, 0, this.sk, 0, this.sk.length);
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.sk);
    }
}

