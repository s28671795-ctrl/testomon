/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.ntru;

import java.security.SecureRandom;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.ntru.NTRUOWCPA;
import org.bouncycastle.pqc.crypto.ntru.NTRUPublicKeyParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUSampling;
import org.bouncycastle.pqc.crypto.ntru.PolynomialPair;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;
import org.bouncycastle.pqc.math.ntru.Polynomial;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUParameterSet;
import org.bouncycastle.util.Arrays;

public class NTRUKEMGenerator
implements EncapsulatedSecretGenerator {
    private final SecureRandom random;

    public NTRUKEMGenerator(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    @Override
    public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter asymmetricKeyParameter) {
        NTRUParameterSet nTRUParameterSet = ((NTRUPublicKeyParameters)asymmetricKeyParameter).getParameters().parameterSet;
        NTRUSampling nTRUSampling = new NTRUSampling(nTRUParameterSet);
        NTRUOWCPA nTRUOWCPA = new NTRUOWCPA(nTRUParameterSet);
        byte[] byArray = new byte[nTRUParameterSet.owcpaMsgBytes()];
        byte[] byArray2 = new byte[nTRUParameterSet.sampleRmBytes()];
        this.random.nextBytes(byArray2);
        PolynomialPair polynomialPair = nTRUSampling.sampleRm(byArray2);
        Polynomial polynomial = polynomialPair.r();
        Polynomial polynomial2 = polynomialPair.m();
        byte[] byArray3 = polynomial.s3ToBytes(nTRUParameterSet.owcpaMsgBytes());
        System.arraycopy(byArray3, 0, byArray, 0, byArray3.length);
        byte[] byArray4 = polynomial2.s3ToBytes(byArray.length - nTRUParameterSet.packTrinaryBytes());
        System.arraycopy(byArray4, 0, byArray, nTRUParameterSet.packTrinaryBytes(), byArray4.length);
        SHA3Digest sHA3Digest = new SHA3Digest(256);
        sHA3Digest.update(byArray, 0, byArray.length);
        byte[] byArray5 = new byte[sHA3Digest.getDigestSize()];
        sHA3Digest.doFinal(byArray5, 0);
        polynomial.z3ToZq();
        byte[] byArray6 = nTRUOWCPA.encrypt(polynomial, polynomial2, ((NTRUPublicKeyParameters)asymmetricKeyParameter).publicKey);
        byte[] byArray7 = Arrays.copyOfRange(byArray5, 0, nTRUParameterSet.sharedKeyBytes());
        Arrays.clear(byArray5);
        return new SecretWithEncapsulationImpl(byArray7, byArray6);
    }
}

