/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import org.bouncycastle.crypto.EncapsulatedSecretExtractor;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPrivateKeyParameters;

public class KyberKEMExtractor
implements EncapsulatedSecretExtractor {
    private KyberEngine engine;
    private KyberPrivateKeyParameters key;

    public KyberKEMExtractor(KyberPrivateKeyParameters kyberPrivateKeyParameters) {
        this.key = kyberPrivateKeyParameters;
        this.initCipher(kyberPrivateKeyParameters);
    }

    private void initCipher(AsymmetricKeyParameter asymmetricKeyParameter) {
        KyberPrivateKeyParameters kyberPrivateKeyParameters = (KyberPrivateKeyParameters)asymmetricKeyParameter;
        this.engine = kyberPrivateKeyParameters.getParameters().getEngine();
    }

    @Override
    public byte[] extractSecret(byte[] byArray) {
        byte[] byArray2 = this.engine.kemDecrypt(byArray, this.key.getEncoded());
        return byArray2;
    }

    @Override
    public int getEncapsulationLength() {
        return this.engine.getCryptoCipherTextBytes();
    }
}

