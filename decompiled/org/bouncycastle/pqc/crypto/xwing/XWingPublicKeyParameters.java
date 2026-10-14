/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.xwing;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPublicKeyParameters;
import org.bouncycastle.pqc.crypto.xwing.XWingKeyParameters;
import org.bouncycastle.util.Arrays;

public class XWingPublicKeyParameters
extends XWingKeyParameters {
    private final KyberPublicKeyParameters kybPub;
    private final X25519PublicKeyParameters xdhPub;

    XWingPublicKeyParameters(AsymmetricKeyParameter asymmetricKeyParameter, AsymmetricKeyParameter asymmetricKeyParameter2) {
        super(false);
        this.kybPub = (KyberPublicKeyParameters)asymmetricKeyParameter;
        this.xdhPub = (X25519PublicKeyParameters)asymmetricKeyParameter2;
    }

    public XWingPublicKeyParameters(byte[] byArray) {
        super(false);
        this.kybPub = new KyberPublicKeyParameters(KyberParameters.kyber768, Arrays.copyOfRange(byArray, 0, byArray.length - 32));
        this.xdhPub = new X25519PublicKeyParameters(byArray, byArray.length - 32);
    }

    KyberPublicKeyParameters getKyberPublicKey() {
        return this.kybPub;
    }

    X25519PublicKeyParameters getXDHPublicKey() {
        return this.xdhPub;
    }

    public byte[] getEncoded() {
        return Arrays.concatenate(this.kybPub.getEncoded(), this.xdhPub.getEncoded());
    }
}

