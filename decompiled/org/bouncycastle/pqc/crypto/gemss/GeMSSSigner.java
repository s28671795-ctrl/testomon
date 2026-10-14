/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.gemss.GeMSSEngine;
import org.bouncycastle.pqc.crypto.gemss.GeMSSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSPublicKeyParameters;

public class GeMSSSigner
implements MessageSigner {
    private GeMSSPrivateKeyParameters privKey;
    private GeMSSPublicKeyParameters pubKey;
    private SecureRandom random;

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        if (bl) {
            if (cipherParameters instanceof ParametersWithRandom) {
                this.privKey = (GeMSSPrivateKeyParameters)((ParametersWithRandom)cipherParameters).getParameters();
                this.random = ((ParametersWithRandom)cipherParameters).getRandom();
            } else {
                this.privKey = (GeMSSPrivateKeyParameters)cipherParameters;
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
        } else {
            this.pubKey = (GeMSSPublicKeyParameters)cipherParameters;
        }
    }

    @Override
    public byte[] generateSignature(byte[] byArray) {
        GeMSSEngine geMSSEngine = this.privKey.getParameters().getEngine();
        int n = geMSSEngine.HFEnv + (geMSSEngine.NB_ITE - 1) * (geMSSEngine.HFEnv - geMSSEngine.HFEm) + 7 >>> 3;
        byte[] byArray2 = new byte[byArray.length + n];
        System.arraycopy(byArray, 0, byArray2, n, byArray.length);
        geMSSEngine.signHFE_FeistelPatarin(this.random, byArray2, byArray, 0, byArray.length, this.privKey.sk);
        return byArray2;
    }

    @Override
    public boolean verifySignature(byte[] byArray, byte[] byArray2) {
        GeMSSEngine geMSSEngine = this.pubKey.getParameters().getEngine();
        int n = geMSSEngine.crypto_sign_open(this.pubKey.getPK(), byArray, byArray2);
        return n != 0;
    }
}

