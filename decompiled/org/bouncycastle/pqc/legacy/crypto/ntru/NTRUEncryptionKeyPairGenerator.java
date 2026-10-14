/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionKeyGenerationParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionPrivateKeyParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.DenseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.ProductFormPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.util.Util;

public class NTRUEncryptionKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private NTRUEncryptionKeyGenerationParameters params;

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.params = (NTRUEncryptionKeyGenerationParameters)keyGenerationParameters;
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        IntegerPolynomial integerPolynomial;
        IntegerPolynomial integerPolynomial2;
        Polynomial polynomial;
        int n = this.params.N;
        int n2 = this.params.q;
        int n3 = this.params.df;
        int n4 = this.params.df1;
        int n5 = this.params.df2;
        int n6 = this.params.df3;
        int n7 = this.params.dg;
        boolean bl = this.params.fastFp;
        boolean bl2 = this.params.sparse;
        IntegerPolynomial integerPolynomial3 = null;
        while (true) {
            if (bl) {
                polynomial = this.params.polyType == 0 ? Util.generateRandomTernary(n, n3, n3, bl2, this.params.getRandom()) : ProductFormPolynomial.generateRandom(n, n4, n5, n6, n6, this.params.getRandom());
                integerPolynomial2 = polynomial.toIntegerPolynomial();
                integerPolynomial2.mult(3);
                integerPolynomial2.coeffs[0] = integerPolynomial2.coeffs[0] + 1;
            } else {
                polynomial = this.params.polyType == 0 ? Util.generateRandomTernary(n, n3, n3 - 1, bl2, this.params.getRandom()) : ProductFormPolynomial.generateRandom(n, n4, n5, n6, n6 - 1, this.params.getRandom());
                integerPolynomial2 = polynomial.toIntegerPolynomial();
                integerPolynomial3 = integerPolynomial2.invertF3();
                if (integerPolynomial3 == null) continue;
            }
            if ((integerPolynomial = integerPolynomial2.invertFq(n2)) != null) break;
        }
        if (bl) {
            integerPolynomial3 = new IntegerPolynomial(n);
            integerPolynomial3.coeffs[0] = 1;
        }
        while ((integerPolynomial2 = DenseTernaryPolynomial.generateRandom(n, n7, n7 - 1, this.params.getRandom())).invertFq(n2) == null) {
        }
        IntegerPolynomial integerPolynomial4 = ((DenseTernaryPolynomial)integerPolynomial2).mult(integerPolynomial, n2);
        integerPolynomial4.mult3(n2);
        integerPolynomial4.ensurePositive(n2);
        integerPolynomial2.clear();
        integerPolynomial.clear();
        NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters = new NTRUEncryptionPrivateKeyParameters(integerPolynomial4, polynomial, integerPolynomial3, this.params.getEncryptionParameters());
        NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters = new NTRUEncryptionPublicKeyParameters(integerPolynomial4, this.params.getEncryptionParameters());
        return new AsymmetricCipherKeyPair(nTRUEncryptionPublicKeyParameters, nTRUEncryptionPrivateKeyParameters);
    }
}

