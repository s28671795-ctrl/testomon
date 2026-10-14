/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.xwing;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.agreement.X25519Agreement;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKEMGenerator;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;
import org.bouncycastle.pqc.crypto.xwing.XWingPublicKeyParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class XWingKEMGenerator
implements EncapsulatedSecretGenerator {
    private final SecureRandom sr;

    public XWingKEMGenerator(SecureRandom secureRandom) {
        this.sr = secureRandom;
    }

    @Override
    public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter asymmetricKeyParameter) {
        XWingPublicKeyParameters xWingPublicKeyParameters = (XWingPublicKeyParameters)asymmetricKeyParameter;
        KyberKEMGenerator kyberKEMGenerator = new KyberKEMGenerator(this.sr);
        SecretWithEncapsulation secretWithEncapsulation = kyberKEMGenerator.generateEncapsulated(xWingPublicKeyParameters.getKyberPublicKey());
        X25519Agreement x25519Agreement = new X25519Agreement();
        byte[] byArray = secretWithEncapsulation.getSecret();
        byte[] byArray2 = new byte[byArray.length + x25519Agreement.getAgreementSize()];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        Arrays.clear(byArray);
        X25519KeyPairGenerator x25519KeyPairGenerator = new X25519KeyPairGenerator();
        x25519KeyPairGenerator.init(new X25519KeyGenerationParameters(this.sr));
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = x25519KeyPairGenerator.generateKeyPair();
        x25519Agreement.init(asymmetricCipherKeyPair.getPrivate());
        x25519Agreement.calculateAgreement(xWingPublicKeyParameters.getXDHPublicKey(), byArray2, byArray.length);
        X25519PublicKeyParameters x25519PublicKeyParameters = (X25519PublicKeyParameters)asymmetricCipherKeyPair.getPublic();
        SHA3Digest sHA3Digest = new SHA3Digest(256);
        sHA3Digest.update(Strings.toByteArray("\\.//^\\"), 0, 6);
        sHA3Digest.update(byArray2, 0, byArray2.length);
        sHA3Digest.update(x25519PublicKeyParameters.getEncoded(), 0, 32);
        sHA3Digest.update(xWingPublicKeyParameters.getXDHPublicKey().getEncoded(), 0, 32);
        byte[] byArray3 = new byte[32];
        sHA3Digest.doFinal(byArray3, 0);
        return new SecretWithEncapsulationImpl(byArray3, Arrays.concatenate(secretWithEncapsulation.getEncapsulation(), x25519PublicKeyParameters.getEncoded()));
    }
}

