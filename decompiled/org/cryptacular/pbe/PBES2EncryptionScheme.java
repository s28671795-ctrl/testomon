/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.pbe;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.EncryptionScheme;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.engines.RC532Engine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.cryptacular.pbe.AbstractEncryptionScheme;
import org.cryptacular.pbe.PBES2Algorithm;

public class PBES2EncryptionScheme
extends AbstractEncryptionScheme {
    private int keyLength;

    public PBES2EncryptionScheme(PBES2Parameters params, char[] password) {
        PBKDF2Params kdfParams = PBKDF2Params.getInstance(params.getKeyDerivationFunc().getParameters());
        byte[] salt = kdfParams.getSalt();
        int iterations = kdfParams.getIterationCount().intValue();
        if (kdfParams.getKeyLength() != null) {
            this.keyLength = kdfParams.getKeyLength().intValue() * 8;
        }
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();
        generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password), salt, iterations);
        this.initCipher(generator, params.getEncryptionScheme());
    }

    private void initCipher(PKCS5S2ParametersGenerator generator, EncryptionScheme scheme2) {
        PBES2Algorithm alg = PBES2Algorithm.fromOid(scheme2.getAlgorithm().getId());
        if (this.keyLength == 0) {
            this.keyLength = alg.getKeySize();
        }
        byte[] iv = null;
        CipherParameters cipherParameters = generator.generateDerivedParameters(this.keyLength);
        switch (alg) {
            case RC2: {
                this.setCipher(alg.getCipherSpec().newInstance());
                ASN1Sequence rc2Params = ASN1Sequence.getInstance(scheme2.getParameters());
                if (rc2Params.size() <= 1) break;
                cipherParameters = new RC2Parameters(((KeyParameter)cipherParameters).getKey(), ASN1Integer.getInstance(rc2Params.getObjectAt(0)).getValue().intValue());
                iv = ASN1OctetString.getInstance(rc2Params.getObjectAt(0)).getOctets();
                break;
            }
            case RC5: {
                ASN1Sequence rc5Params = ASN1Sequence.getInstance(scheme2.getParameters());
                int rounds = ASN1Integer.getInstance(rc5Params.getObjectAt(1)).getValue().intValue();
                int blockSize = ASN1Integer.getInstance(rc5Params.getObjectAt(2)).getValue().intValue();
                if (blockSize == 32) {
                    this.setCipher(new PaddedBufferedBlockCipher(new CBCBlockCipher(new RC532Engine()), new PKCS7Padding()));
                }
                cipherParameters = new RC5Parameters(((KeyParameter)cipherParameters).getKey(), rounds);
                if (rc5Params.size() <= 3) break;
                iv = ASN1OctetString.getInstance(rc5Params.getObjectAt(3)).getOctets();
                break;
            }
            default: {
                this.setCipher(alg.getCipherSpec().newInstance());
                iv = ASN1OctetString.getInstance(scheme2.getParameters()).getOctets();
            }
        }
        if (iv != null) {
            cipherParameters = new ParametersWithIV(cipherParameters, iv);
        }
        this.setCipherParameters(cipherParameters);
    }
}

