/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.xwing;

import org.bouncycastle.crypto.EncapsulatedSecretExtractor;
import org.bouncycastle.crypto.agreement.X25519Agreement;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKEMExtractor;
import org.bouncycastle.pqc.crypto.xwing.XWingPrivateKeyParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class XWingKEMExtractor
implements EncapsulatedSecretExtractor {
    private final XWingPrivateKeyParameters key;
    private final KyberKEMExtractor kemExtractor;

    public XWingKEMExtractor(XWingPrivateKeyParameters xWingPrivateKeyParameters) {
        this.key = xWingPrivateKeyParameters;
        this.kemExtractor = new KyberKEMExtractor(this.key.getKyberPrivateKey());
    }

    @Override
    public byte[] extractSecret(byte[] byArray) {
        byte[] byArray2 = this.kemExtractor.extractSecret(Arrays.copyOfRange(byArray, 0, byArray.length - 32));
        X25519Agreement x25519Agreement = new X25519Agreement();
        byte[] byArray3 = new byte[byArray2.length + x25519Agreement.getAgreementSize()];
        System.arraycopy(byArray2, 0, byArray3, 0, byArray2.length);
        Arrays.clear(byArray2);
        x25519Agreement.init(this.key.getXDHPrivateKey());
        X25519PublicKeyParameters x25519PublicKeyParameters = new X25519PublicKeyParameters(Arrays.copyOfRange(byArray, byArray.length - 32, byArray.length));
        x25519Agreement.calculateAgreement(x25519PublicKeyParameters, byArray3, byArray2.length);
        SHA3Digest sHA3Digest = new SHA3Digest(256);
        sHA3Digest.update(Strings.toByteArray("\\.//^\\"), 0, 6);
        sHA3Digest.update(byArray3, 0, byArray3.length);
        sHA3Digest.update(x25519PublicKeyParameters.getEncoded(), 0, 32);
        sHA3Digest.update(this.key.getXDHPrivateKey().generatePublicKey().getEncoded(), 0, 32);
        byte[] byArray4 = new byte[32];
        sHA3Digest.doFinal(byArray4, 0);
        return byArray4;
    }

    @Override
    public int getEncapsulationLength() {
        return this.kemExtractor.getEncapsulationLength() + 32;
    }
}

