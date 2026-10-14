/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.jcajce.provider.util;

import java.security.InvalidKeyException;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.ARIAEngine;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.RFC3394WrapEngine;
import org.bouncycastle.crypto.engines.RFC5649WrapEngine;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.bouncycastle.util.Arrays;

public class WrapUtil {
    public static Wrapper getKeyWrapper(KTSParameterSpec kTSParameterSpec, byte[] byArray) throws InvalidKeyException {
        Wrapper wrapper = WrapUtil.getWrapper(kTSParameterSpec.getKeyAlgorithmName());
        AlgorithmIdentifier algorithmIdentifier = kTSParameterSpec.getKdfAlgorithm();
        if (algorithmIdentifier == null) {
            wrapper.init(true, new KeyParameter(Arrays.copyOfRange(byArray, 0, (kTSParameterSpec.getKeySize() + 7) / 8)));
        } else {
            wrapper.init(true, new KeyParameter(WrapUtil.makeKeyBytes(kTSParameterSpec, byArray)));
        }
        return wrapper;
    }

    public static Wrapper getKeyUnwrapper(KTSParameterSpec kTSParameterSpec, byte[] byArray) throws InvalidKeyException {
        Wrapper wrapper = WrapUtil.getWrapper(kTSParameterSpec.getKeyAlgorithmName());
        AlgorithmIdentifier algorithmIdentifier = kTSParameterSpec.getKdfAlgorithm();
        if (algorithmIdentifier == null) {
            wrapper.init(false, new KeyParameter(byArray, 0, (kTSParameterSpec.getKeySize() + 7) / 8));
        } else {
            wrapper.init(false, new KeyParameter(WrapUtil.makeKeyBytes(kTSParameterSpec, byArray)));
        }
        return wrapper;
    }

    public static Wrapper getWrapper(String string) {
        Wrapper wrapper;
        if (string.equalsIgnoreCase("AESWRAP") || string.equalsIgnoreCase("AES")) {
            wrapper = new RFC3394WrapEngine(new AESEngine());
        } else if (string.equalsIgnoreCase("ARIA")) {
            wrapper = new RFC3394WrapEngine(new ARIAEngine());
        } else if (string.equalsIgnoreCase("Camellia")) {
            wrapper = new RFC3394WrapEngine(new CamelliaEngine());
        } else if (string.equalsIgnoreCase("SEED")) {
            wrapper = new RFC3394WrapEngine(new SEEDEngine());
        } else if (string.equalsIgnoreCase("AES-KWP")) {
            wrapper = new RFC5649WrapEngine(new AESEngine());
        } else if (string.equalsIgnoreCase("Camellia-KWP")) {
            wrapper = new RFC5649WrapEngine(new CamelliaEngine());
        } else if (string.equalsIgnoreCase("ARIA-KWP")) {
            wrapper = new RFC5649WrapEngine(new ARIAEngine());
        } else {
            throw new UnsupportedOperationException("unknown key algorithm: " + string);
        }
        return wrapper;
    }

    private static byte[] makeKeyBytes(KTSParameterSpec kTSParameterSpec, byte[] byArray) throws InvalidKeyException {
        AlgorithmIdentifier algorithmIdentifier = kTSParameterSpec.getKdfAlgorithm();
        byte[] byArray2 = kTSParameterSpec.getOtherInfo();
        byte[] byArray3 = new byte[(kTSParameterSpec.getKeySize() + 7) / 8];
        if (X9ObjectIdentifiers.id_kdf_kdf2.equals(algorithmIdentifier.getAlgorithm())) {
            AlgorithmIdentifier algorithmIdentifier2 = AlgorithmIdentifier.getInstance(algorithmIdentifier.getParameters());
            KDF2BytesGenerator kDF2BytesGenerator = new KDF2BytesGenerator(WrapUtil.getDigest(algorithmIdentifier2.getAlgorithm()));
            kDF2BytesGenerator.init(new KDFParameters(byArray, byArray2));
            kDF2BytesGenerator.generateBytes(byArray3, 0, byArray3.length);
        } else if (X9ObjectIdentifiers.id_kdf_kdf3.equals(algorithmIdentifier.getAlgorithm())) {
            AlgorithmIdentifier algorithmIdentifier3 = AlgorithmIdentifier.getInstance(algorithmIdentifier.getParameters());
            ConcatenationKDFGenerator concatenationKDFGenerator = new ConcatenationKDFGenerator(WrapUtil.getDigest(algorithmIdentifier3.getAlgorithm()));
            concatenationKDFGenerator.init(new KDFParameters(byArray, byArray2));
            concatenationKDFGenerator.generateBytes(byArray3, 0, byArray3.length);
        } else if (NISTObjectIdentifiers.id_shake256.equals(algorithmIdentifier.getAlgorithm())) {
            SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
            sHAKEDigest.update(byArray, 0, byArray.length);
            sHAKEDigest.update(byArray2, 0, byArray2.length);
            sHAKEDigest.doFinal(byArray3, 0, byArray3.length);
        } else {
            throw new InvalidKeyException("Unrecognized KDF: " + algorithmIdentifier.getAlgorithm());
        }
        return byArray3;
    }

    static Digest getDigest(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (aSN1ObjectIdentifier.equals(NISTObjectIdentifiers.id_sha256)) {
            return new SHA256Digest();
        }
        if (aSN1ObjectIdentifier.equals(NISTObjectIdentifiers.id_sha512)) {
            return new SHA512Digest();
        }
        if (aSN1ObjectIdentifier.equals(NISTObjectIdentifiers.id_shake128)) {
            return new SHAKEDigest(128);
        }
        if (aSN1ObjectIdentifier.equals(NISTObjectIdentifiers.id_shake256)) {
            return new SHAKEDigest(256);
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + aSN1ObjectIdentifier);
    }
}

