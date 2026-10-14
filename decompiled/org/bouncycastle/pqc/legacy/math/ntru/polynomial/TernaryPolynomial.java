/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;

public interface TernaryPolynomial
extends Polynomial {
    @Override
    public IntegerPolynomial mult(IntegerPolynomial var1);

    public int[] getOnes();

    public int[] getNegOnes();

    public int size();

    public void clear();
}

