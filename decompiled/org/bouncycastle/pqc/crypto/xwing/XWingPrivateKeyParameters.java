/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.xwing;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xwing.XWingKeyParameters;
import org.bouncycastle.util.Arrays;

public class XWingPrivateKeyParameters
extends XWingKeyParameters {
    private final KyberPrivateKeyParameters kybPriv;
    private final X25519PrivateKeyParameters xdhPriv;

    XWingPrivateKeyParameters(AsymmetricKeyParameter asymmetricKeyParameter, AsymmetricKeyParameter asymmetricKeyParameter2) {
        super(true);
        this.kybPriv = (KyberPrivateKeyParameters)asymmetricKeyParameter;
        this.xdhPriv = (X25519PrivateKeyParameters)asymmetricKeyParameter2;
    }

    public XWingPrivateKeyParameters(byte[] byArray) {
        super(false);
        this.kybPriv = new KyberPrivateKeyParameters(KyberParameters.kyber768, Arrays.copyOfRange(byArray, 0, byArray.length - 32));
        this.xdhPriv = new X25519PrivateKeyParameters(byArray, byArray.length - 32);
    }

    KyberPrivateKeyParameters getKyberPrivateKey() {
        return this.kybPriv;
    }

    X25519PrivateKeyParameters getXDHPrivateKey() {
        return this.xdhPriv;
    }

    public byte[] getEncoded() {
        return Arrays.concatenate(this.kybPriv.getEncoded(), this.xdhPriv.getEncoded());
    }
}

