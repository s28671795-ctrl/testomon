/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;

public class KyberKeyParameters
extends AsymmetricKeyParameter {
    private KyberParameters params;

    public KyberKeyParameters(boolean bl, KyberParameters kyberParameters) {
        super(bl);
        this.params = kyberParameters;
    }

    public KyberParameters getParameters() {
        return this.params;
    }
}

