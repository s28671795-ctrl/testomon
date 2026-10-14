/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.pqc.jcajce.provider.Util
 */
package org.bouncycastle.pqc.jcajce.provider;

import java.security.InvalidKeyException;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;

/*
 * Exception performing whole class analysis ignored.
 */
public class Util {
    public static byte[] makeKeyBytes(KTSParameterSpec kTSParameterSpec, byte[] byArray) throws InvalidKeyException {
        AlgorithmIdentifier algorithmIdentifier = kTSParameterSpec.getKdfAlgorithm();
        byte[] byArray2 = kTSParameterSpec.getOtherInfo();
        byte[] byArray3 = new byte[(kTSParameterSpec.getKeySize() + 7) / 8];
        if (X9ObjectIdentifiers.id_kdf_kdf2.equals(algorithmIdentifier.getAlgorithm())) {
            AlgorithmIdentifier algorithmIdentifier2 = AlgorithmIdentifier.getInstance(algorithmIdentifier.getParameters());
            KDF2BytesGenerator kDF2BytesGenerator = new KDF2BytesGenerator(Util.getDigest((ASN1ObjectIdentifier)algorithmIdentifier2.getAlgorithm()));
            kDF2BytesGenerator.init(new KDFParameters(byArray, byArray2));
            kDF2BytesGenerator.generateBytes(byArray3, 0, byArray3.length);
        } else if (X9ObjectIdentifiers.id_kdf_kdf3.equals(algorithmIdentifier.getAlgorithm())) {
            AlgorithmIdentifier algorithmIdentifier3 = AlgorithmIdentifier.getInstance(algorithmIdentifier.getParameters());
            ConcatenationKDFGenerator concatenationKDFGenerator = new ConcatenationKDFGenerator(Util.getDigest((ASN1ObjectIdentifier)algorithmIdentifier3.getAlgorithm()));
            concatenationKDFGenerator.init(new KDFParameters(byArray, byArray2));
            concatenationKDFGenerator.generateBytes(byArray3, 0, byArray3.length);
        } else if (NISTObjectIdentifiers.id_shake256.equals(algorithmIdentifier.getAlgorithm())) {
            SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
            sHAKEDigest.update(byArray, 0, byArray.length);
            sHAKEDigest.update(byArray2, 0, byArray2.length);
            sHAKEDigest.doFinal(byArray3, 0, byArray3.length);
        } else {
            throw new InvalidKeyException("Unrecognized KDF: " + String.valueOf(algorithmIdentifier.getAlgorithm()));
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
        throw new IllegalArgumentException("unrecognized digest OID: " + String.valueOf(aSN1ObjectIdentifier));
    }
}

