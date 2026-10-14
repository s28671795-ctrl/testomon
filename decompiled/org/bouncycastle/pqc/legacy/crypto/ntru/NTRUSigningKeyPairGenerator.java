/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningKeyGenerationParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningPrivateKeyParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.ntru.euclid.BigIntEuclidean;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigDecimalPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.DenseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.ProductFormPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Resultant;

public class NTRUSigningKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private NTRUSigningKeyGenerationParameters params;

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.params = (NTRUSigningKeyGenerationParameters)keyGenerationParameters;
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        Object object;
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = null;
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        ArrayList<Future<NTRUSigningPrivateKeyParameters.Basis>> arrayList = new ArrayList<Future<NTRUSigningPrivateKeyParameters.Basis>>();
        for (int i = this.params.B; i >= 0; --i) {
            arrayList.add(executorService2.submit(new BasisGenerationTask()));
        }
        executorService2.shutdown();
        ArrayList<NTRUSigningPrivateKeyParameters.Basis> arrayList2 = new ArrayList<NTRUSigningPrivateKeyParameters.Basis>();
        for (int i = this.params.B; i >= 0; --i) {
            object = (Future)arrayList.get(i);
            try {
                arrayList2.add((NTRUSigningPrivateKeyParameters.Basis)object.get());
                if (i != this.params.B) continue;
                nTRUSigningPublicKeyParameters = new NTRUSigningPublicKeyParameters(((NTRUSigningPrivateKeyParameters.Basis)object.get()).h, this.params.getSigningParameters());
                continue;
            }
            catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
        NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters = new NTRUSigningPrivateKeyParameters(arrayList2, nTRUSigningPublicKeyParameters);
        object = new AsymmetricCipherKeyPair(nTRUSigningPublicKeyParameters, nTRUSigningPrivateKeyParameters);
        return object;
    }

    public AsymmetricCipherKeyPair generateKeyPairSingleThread() {
        ArrayList<NTRUSigningPrivateKeyParameters.Basis> arrayList = new ArrayList<NTRUSigningPrivateKeyParameters.Basis>();
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = null;
        for (int i = this.params.B; i >= 0; --i) {
            NTRUSigningPrivateKeyParameters.Basis basis = this.generateBoundedBasis();
            arrayList.add(basis);
            if (i != 0) continue;
            nTRUSigningPublicKeyParameters = new NTRUSigningPublicKeyParameters(basis.h, this.params.getSigningParameters());
        }
        NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters = new NTRUSigningPrivateKeyParameters(arrayList, nTRUSigningPublicKeyParameters);
        return new AsymmetricCipherKeyPair(nTRUSigningPublicKeyParameters, nTRUSigningPrivateKeyParameters);
    }

    private void minimizeFG(IntegerPolynomial integerPolynomial, IntegerPolynomial integerPolynomial2, IntegerPolynomial integerPolynomial3, IntegerPolynomial integerPolynomial4, int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 += 2 * n * (integerPolynomial.coeffs[i] * integerPolynomial.coeffs[i] + integerPolynomial2.coeffs[i] * integerPolynomial2.coeffs[i]);
        }
        n2 -= 4;
        IntegerPolynomial integerPolynomial5 = (IntegerPolynomial)integerPolynomial.clone();
        IntegerPolynomial integerPolynomial6 = (IntegerPolynomial)integerPolynomial2.clone();
        int n3 = 0;
        int n4 = n;
        for (int i = 0; n3 < n4 && i < n; ++i) {
            int n5;
            int n6 = 0;
            for (int j = 0; j < n; ++j) {
                n5 = integerPolynomial3.coeffs[j] * integerPolynomial.coeffs[j];
                int n7 = integerPolynomial4.coeffs[j] * integerPolynomial2.coeffs[j];
                int n8 = 4 * n * (n5 + n7);
                n6 += n8;
            }
            n5 = 4 * (integerPolynomial3.sumCoeffs() + integerPolynomial4.sumCoeffs());
            if ((n6 -= n5) > n2) {
                integerPolynomial3.sub(integerPolynomial5);
                integerPolynomial4.sub(integerPolynomial6);
                ++n3;
                i = 0;
            } else if (n6 < -n2) {
                integerPolynomial3.add(integerPolynomial5);
                integerPolynomial4.add(integerPolynomial6);
                ++n3;
                i = 0;
            }
            integerPolynomial5.rotate1();
            integerPolynomial6.rotate1();
        }
    }

    private FGBasis generateBasis() {
        BigIntPolynomial bigIntPolynomial;
        Object object;
        Object object2;
        Object object3;
        Object object4;
        Object object5;
        Object object6;
        BigIntEuclidean bigIntEuclidean;
        Resultant resultant;
        IntegerPolynomial integerPolynomial;
        DenseTernaryPolynomial denseTernaryPolynomial;
        DenseTernaryPolynomial denseTernaryPolynomial2;
        IntegerPolynomial integerPolynomial2;
        IntegerPolynomial integerPolynomial3;
        int n = this.params.N;
        int n2 = this.params.q;
        int n3 = this.params.d;
        int n4 = this.params.d1;
        int n5 = this.params.d2;
        int n6 = this.params.d3;
        int n7 = this.params.basisType;
        int n8 = 2 * n + 1;
        boolean bl = this.params.primeCheck;
        do {
            denseTernaryPolynomial2 = this.params.polyType == 0 ? DenseTernaryPolynomial.generateRandom(n, n3 + 1, n3, CryptoServicesRegistrar.getSecureRandom()) : ProductFormPolynomial.generateRandom(n, n4, n5, n6 + 1, n6, CryptoServicesRegistrar.getSecureRandom());
            integerPolynomial3 = denseTernaryPolynomial2.toIntegerPolynomial();
        } while (bl && integerPolynomial3.resultant((int)n8).res.equals(BigInteger.ZERO) || (integerPolynomial2 = integerPolynomial3.invertFq(n2)) == null);
        Resultant resultant2 = integerPolynomial3.resultant();
        while (true) {
            denseTernaryPolynomial = this.params.polyType == 0 ? DenseTernaryPolynomial.generateRandom(n, n3 + 1, n3, CryptoServicesRegistrar.getSecureRandom()) : ProductFormPolynomial.generateRandom(n, n4, n5, n6 + 1, n6, CryptoServicesRegistrar.getSecureRandom());
            integerPolynomial = denseTernaryPolynomial.toIntegerPolynomial();
            if (bl && integerPolynomial.resultant((int)n8).res.equals(BigInteger.ZERO) || integerPolynomial.invertFq(n2) == null) continue;
            resultant = integerPolynomial.resultant();
            bigIntEuclidean = BigIntEuclidean.calculate(resultant2.res, resultant.res);
            if (bigIntEuclidean.gcd.equals(BigInteger.ONE)) break;
        }
        BigIntPolynomial bigIntPolynomial2 = (BigIntPolynomial)resultant2.rho.clone();
        bigIntPolynomial2.mult(bigIntEuclidean.x.multiply(BigInteger.valueOf(n2)));
        BigIntPolynomial bigIntPolynomial3 = (BigIntPolynomial)resultant.rho.clone();
        bigIntPolynomial3.mult(bigIntEuclidean.y.multiply(BigInteger.valueOf(-n2)));
        if (this.params.keyGenAlg == 0) {
            object6 = new int[n];
            object5 = new int[n];
            object6[0] = integerPolynomial3.coeffs[0];
            object5[0] = integerPolynomial.coeffs[0];
            for (int i = 1; i < n; ++i) {
                object6[i] = integerPolynomial3.coeffs[n - i];
                object5[i] = integerPolynomial.coeffs[n - i];
            }
            object4 = new IntegerPolynomial((int[])object6);
            object3 = new IntegerPolynomial((int[])object5);
            object2 = denseTernaryPolynomial2.mult((IntegerPolynomial)object4);
            ((IntegerPolynomial)object2).add(denseTernaryPolynomial.mult((IntegerPolynomial)object3));
            object = ((IntegerPolynomial)object2).resultant();
            bigIntPolynomial = ((IntegerPolynomial)object4).mult(bigIntPolynomial3);
            bigIntPolynomial.add(((IntegerPolynomial)object3).mult(bigIntPolynomial2));
            bigIntPolynomial = bigIntPolynomial.mult(((Resultant)object).rho);
            bigIntPolynomial.div(((Resultant)object).res);
        } else {
            int n9 = 0;
            for (int i = 1; i < n; i *= 10) {
                ++n9;
            }
            object5 = resultant2.rho.div(new BigDecimal(resultant2.res), bigIntPolynomial3.getMaxCoeffLength() + 1 + n9);
            object4 = resultant.rho.div(new BigDecimal(resultant.res), bigIntPolynomial2.getMaxCoeffLength() + 1 + n9);
            object3 = ((BigDecimalPolynomial)object5).mult(bigIntPolynomial3);
            ((BigDecimalPolynomial)object3).add(((BigDecimalPolynomial)object4).mult(bigIntPolynomial2));
            ((BigDecimalPolynomial)object3).halve();
            bigIntPolynomial = ((BigDecimalPolynomial)object3).round();
        }
        object6 = (BigIntPolynomial)bigIntPolynomial3.clone();
        ((BigIntPolynomial)object6).sub(denseTernaryPolynomial2.mult(bigIntPolynomial));
        object5 = (BigIntPolynomial)bigIntPolynomial2.clone();
        ((BigIntPolynomial)object5).sub(denseTernaryPolynomial.mult(bigIntPolynomial));
        object4 = new IntegerPolynomial((BigIntPolynomial)object6);
        object3 = new IntegerPolynomial((BigIntPolynomial)object5);
        this.minimizeFG(integerPolynomial3, integerPolynomial, (IntegerPolynomial)object4, (IntegerPolynomial)object3, n);
        if (n7 == 0) {
            object2 = object4;
            object = denseTernaryPolynomial.mult(integerPolynomial2, n2);
        } else {
            object2 = denseTernaryPolynomial;
            object = ((IntegerPolynomial)object4).mult(integerPolynomial2, n2);
        }
        ((IntegerPolynomial)object).modPositive(n2);
        return new FGBasis(denseTernaryPolynomial2, (Polynomial)object2, (IntegerPolynomial)object, (IntegerPolynomial)object4, (IntegerPolynomial)object3, this.params);
    }

    public NTRUSigningPrivateKeyParameters.Basis generateBoundedBasis() {
        FGBasis fGBasis;
        while (!(fGBasis = this.generateBasis()).isNormOk()) {
        }
        return fGBasis;
    }

    private class BasisGenerationTask
    implements Callable<NTRUSigningPrivateKeyParameters.Basis> {
        private BasisGenerationTask() {
        }

        @Override
        public NTRUSigningPrivateKeyParameters.Basis call() throws Exception {
            return NTRUSigningKeyPairGenerator.this.generateBoundedBasis();
        }
    }

    public static class FGBasis
    extends NTRUSigningPrivateKeyParameters.Basis {
        public IntegerPolynomial F;
        public IntegerPolynomial G;

        FGBasis(Polynomial polynomial, Polynomial polynomial2, IntegerPolynomial integerPolynomial, IntegerPolynomial integerPolynomial2, IntegerPolynomial integerPolynomial3, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) {
            super(polynomial, polynomial2, integerPolynomial, nTRUSigningKeyGenerationParameters);
            this.F = integerPolynomial2;
            this.G = integerPolynomial3;
        }

        boolean isNormOk() {
            double d = this.params.keyNormBoundSq;
            int n = this.params.q;
            return (double)this.F.centeredNormSq(n) < d && (double)this.G.centeredNormSq(n) < d;
        }
    }
}

