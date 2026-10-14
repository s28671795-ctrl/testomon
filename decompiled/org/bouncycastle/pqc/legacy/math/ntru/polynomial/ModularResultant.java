/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.math.BigInteger;
import org.bouncycastle.pqc.legacy.math.ntru.euclid.BigIntEuclidean;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Resultant;

public class ModularResultant
extends Resultant {
    BigInteger modulus;

    ModularResultant(BigIntPolynomial bigIntPolynomial, BigInteger bigInteger, BigInteger bigInteger2) {
        super(bigIntPolynomial, bigInteger);
        this.modulus = bigInteger2;
    }

    static ModularResultant combineRho(ModularResultant modularResultant, ModularResultant modularResultant2) {
        BigInteger bigInteger = modularResultant.modulus;
        BigInteger bigInteger2 = modularResultant2.modulus;
        BigInteger bigInteger3 = bigInteger.multiply(bigInteger2);
        BigIntEuclidean bigIntEuclidean = BigIntEuclidean.calculate(bigInteger2, bigInteger);
        BigIntPolynomial bigIntPolynomial = (BigIntPolynomial)modularResultant.rho.clone();
        bigIntPolynomial.mult(bigIntEuclidean.x.multiply(bigInteger2));
        BigIntPolynomial bigIntPolynomial2 = (BigIntPolynomial)modularResultant2.rho.clone();
        bigIntPolynomial2.mult(bigIntEuclidean.y.multiply(bigInteger));
        bigIntPolynomial.add(bigIntPolynomial2);
        bigIntPolynomial.mod(bigInteger3);
        return new ModularResultant(bigIntPolynomial, null, bigInteger3);
    }
}

