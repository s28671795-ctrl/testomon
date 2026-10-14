/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.SparseTernaryPolynomial;
import org.bouncycastle.util.Arrays;

public class ProductFormPolynomial
implements Polynomial {
    private SparseTernaryPolynomial f1;
    private SparseTernaryPolynomial f2;
    private SparseTernaryPolynomial f3;

    public ProductFormPolynomial(SparseTernaryPolynomial sparseTernaryPolynomial, SparseTernaryPolynomial sparseTernaryPolynomial2, SparseTernaryPolynomial sparseTernaryPolynomial3) {
        this.f1 = sparseTernaryPolynomial;
        this.f2 = sparseTernaryPolynomial2;
        this.f3 = sparseTernaryPolynomial3;
    }

    public static ProductFormPolynomial generateRandom(int n, int n2, int n3, int n4, int n5, SecureRandom secureRandom) {
        SparseTernaryPolynomial sparseTernaryPolynomial = SparseTernaryPolynomial.generateRandom(n, n2, n2, secureRandom);
        SparseTernaryPolynomial sparseTernaryPolynomial2 = SparseTernaryPolynomial.generateRandom(n, n3, n3, secureRandom);
        SparseTernaryPolynomial sparseTernaryPolynomial3 = SparseTernaryPolynomial.generateRandom(n, n4, n5, secureRandom);
        return new ProductFormPolynomial(sparseTernaryPolynomial, sparseTernaryPolynomial2, sparseTernaryPolynomial3);
    }

    public static ProductFormPolynomial fromBinary(byte[] byArray, int n, int n2, int n3, int n4, int n5) throws IOException {
        return ProductFormPolynomial.fromBinary(new ByteArrayInputStream(byArray), n, n2, n3, n4, n5);
    }

    public static ProductFormPolynomial fromBinary(InputStream inputStream2, int n, int n2, int n3, int n4, int n5) throws IOException {
        SparseTernaryPolynomial sparseTernaryPolynomial = SparseTernaryPolynomial.fromBinary(inputStream2, n, n2, n2);
        SparseTernaryPolynomial sparseTernaryPolynomial2 = SparseTernaryPolynomial.fromBinary(inputStream2, n, n3, n3);
        SparseTernaryPolynomial sparseTernaryPolynomial3 = SparseTernaryPolynomial.fromBinary(inputStream2, n, n4, n5);
        return new ProductFormPolynomial(sparseTernaryPolynomial, sparseTernaryPolynomial2, sparseTernaryPolynomial3);
    }

    public byte[] toBinary() {
        byte[] byArray = this.f1.toBinary();
        byte[] byArray2 = this.f2.toBinary();
        byte[] byArray3 = this.f3.toBinary();
        byte[] byArray4 = Arrays.copyOf(byArray, byArray.length + byArray2.length + byArray3.length);
        System.arraycopy(byArray2, 0, byArray4, byArray.length, byArray2.length);
        System.arraycopy(byArray3, 0, byArray4, byArray.length + byArray2.length, byArray3.length);
        return byArray4;
    }

    @Override
    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial) {
        IntegerPolynomial integerPolynomial2 = this.f1.mult(integerPolynomial);
        integerPolynomial2 = this.f2.mult(integerPolynomial2);
        integerPolynomial2.add(this.f3.mult(integerPolynomial));
        return integerPolynomial2;
    }

    @Override
    public BigIntPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        BigIntPolynomial bigIntPolynomial2 = this.f1.mult(bigIntPolynomial);
        bigIntPolynomial2 = this.f2.mult(bigIntPolynomial2);
        bigIntPolynomial2.add(this.f3.mult(bigIntPolynomial));
        return bigIntPolynomial2;
    }

    @Override
    public IntegerPolynomial toIntegerPolynomial() {
        IntegerPolynomial integerPolynomial = this.f1.mult(this.f2.toIntegerPolynomial());
        integerPolynomial.add(this.f3.toIntegerPolynomial());
        return integerPolynomial;
    }

    @Override
    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial, int n) {
        IntegerPolynomial integerPolynomial2 = this.mult(integerPolynomial);
        integerPolynomial2.mod(n);
        return integerPolynomial2;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (this.f1 == null ? 0 : this.f1.hashCode());
        n = 31 * n + (this.f2 == null ? 0 : this.f2.hashCode());
        n = 31 * n + (this.f3 == null ? 0 : this.f3.hashCode());
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        ProductFormPolynomial productFormPolynomial = (ProductFormPolynomial)object;
        if (this.f1 == null ? productFormPolynomial.f1 != null : !this.f1.equals(productFormPolynomial.f1)) {
            return false;
        }
        if (this.f2 == null ? productFormPolynomial.f2 != null : !this.f2.equals(productFormPolynomial.f2)) {
            return false;
        }
        return !(this.f3 == null ? productFormPolynomial.f3 != null : !this.f3.equals(productFormPolynomial.f3));
    }
}

