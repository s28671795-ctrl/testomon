/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Arrays;

public final class VectorInline$ {
    public static final VectorInline$ MODULE$ = new VectorInline$();

    public final int BITS() {
        return 5;
    }

    public final int WIDTH() {
        return 32;
    }

    public final int MASK() {
        return 31;
    }

    public final int BITS2() {
        return 10;
    }

    public final int WIDTH2() {
        return 1024;
    }

    public final int BITS3() {
        return 15;
    }

    public final int WIDTH3() {
        return 32768;
    }

    public final int BITS4() {
        return 20;
    }

    public final int WIDTH4() {
        return 0x100000;
    }

    public final int BITS5() {
        return 25;
    }

    public final int WIDTH5() {
        return 0x2000000;
    }

    public final int BITS6() {
        return 30;
    }

    public final int WIDTH6() {
        return 0x40000000;
    }

    public final int LASTWIDTH() {
        return 64;
    }

    public final int Log2ConcatFaster() {
        return 5;
    }

    public int vectorSliceDim(int count, int idx) {
        int c = count / 2;
        return c + 1 - Math.abs(idx - c);
    }

    public <T> T[] copyOrUse(T[] a, int start2, int end) {
        if (start2 == 0 && end == a.length) {
            return a;
        }
        return Arrays.copyOfRange(a, start2, end);
    }

    public final <T> T[] copyTail(T[] a) {
        return Arrays.copyOfRange(a, 1, a.length);
    }

    public final <T> T[] copyInit(T[] a) {
        return Arrays.copyOfRange(a, 0, a.length - 1);
    }

    public final <T> T[] copyIfDifferentSize(T[] a, int len) {
        if (a.length == len) {
            return a;
        }
        return Arrays.copyOf(a, len);
    }

    public final Object[] wrap1(Object x) {
        Object[] a = new Object[]{x};
        return a;
    }

    public final Object[][] wrap2(Object[] x) {
        Object[][] a = new Object[][]{x};
        return a;
    }

    public final Object[][][] wrap3(Object[][] x) {
        Object[][][] a = new Object[][][]{x};
        return a;
    }

    public final Object[][][][] wrap4(Object[][][] x) {
        Object[][][][] a = new Object[][][][]{x};
        return a;
    }

    public final Object[][][][][] wrap5(Object[][][][] x) {
        Object[][][][][] a = new Object[][][][][]{x};
        return a;
    }

    public final Object[] copyUpdate(Object[] a1, int idx1, Object elem) {
        Object[] a1c = (Object[])a1.clone();
        a1c[idx1] = elem;
        return a1c;
    }

    public final Object[][] copyUpdate(Object[][] a2, int idx2, int idx1, Object elem) {
        Object[][] a2c = (Object[][])a2.clone();
        Object[] copyUpdate_a1c = (Object[])a2c[idx2].clone();
        copyUpdate_a1c[idx1] = elem;
        a2c[idx2] = copyUpdate_a1c;
        return a2c;
    }

    public final Object[][][] copyUpdate(Object[][][] a3, int idx3, int idx2, int idx1, Object elem) {
        Object[][][] a3c = (Object[][][])a3.clone();
        Object[][] copyUpdate_a2c = (Object[][])a3c[idx3].clone();
        Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[idx2].clone();
        copyUpdate_copyUpdate_a1c[idx1] = elem;
        copyUpdate_a2c[idx2] = copyUpdate_copyUpdate_a1c;
        a3c[idx3] = copyUpdate_a2c;
        return a3c;
    }

    public final Object[][][][] copyUpdate(Object[][][][] a4, int idx4, int idx3, int idx2, int idx1, Object elem) {
        Object[][][][] a4c = (Object[][][][])a4.clone();
        Object[][][] copyUpdate_a3c = (Object[][][])a4c[idx4].clone();
        Object[][] copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_a3c[idx3].clone();
        Object[] copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_a2c[idx2].clone();
        copyUpdate_copyUpdate_copyUpdate_a1c[idx1] = elem;
        copyUpdate_copyUpdate_a2c[idx2] = copyUpdate_copyUpdate_copyUpdate_a1c;
        copyUpdate_a3c[idx3] = copyUpdate_copyUpdate_a2c;
        a4c[idx4] = copyUpdate_a3c;
        return a4c;
    }

    public final Object[][][][][] copyUpdate(Object[][][][][] a5, int idx5, int idx4, int idx3, int idx2, int idx1, Object elem) {
        Object[][][][][] a5c = (Object[][][][][])a5.clone();
        Object[][][][] copyUpdate_a4c = (Object[][][][])a5c[idx5].clone();
        Object[][][] copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_a4c[idx4].clone();
        Object[][] copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_a3c[idx3].clone();
        Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_a2c[idx2].clone();
        copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[idx1] = elem;
        copyUpdate_copyUpdate_copyUpdate_a2c[idx2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
        copyUpdate_copyUpdate_a3c[idx3] = copyUpdate_copyUpdate_copyUpdate_a2c;
        copyUpdate_a4c[idx4] = copyUpdate_copyUpdate_a3c;
        a5c[idx5] = copyUpdate_a4c;
        return a5c;
    }

    public final Object[][][][][][] copyUpdate(Object[][][][][][] a6, int idx6, int idx5, int idx4, int idx3, int idx2, int idx1, Object elem) {
        Object[][][][][][] a6c = (Object[][][][][][])a6.clone();
        Object[][][][][] copyUpdate_a5c = (Object[][][][][])a6c[idx6].clone();
        Object[][][][] copyUpdate_copyUpdate_a4c = (Object[][][][])copyUpdate_a5c[idx5].clone();
        Object[][][] copyUpdate_copyUpdate_copyUpdate_a3c = (Object[][][])copyUpdate_copyUpdate_a4c[idx4].clone();
        Object[][] copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c = (Object[][])copyUpdate_copyUpdate_copyUpdate_a3c[idx3].clone();
        Object[] copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[idx2].clone();
        copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c[idx1] = elem;
        copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c[idx2] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_copyUpdate_a1c;
        copyUpdate_copyUpdate_copyUpdate_a3c[idx3] = copyUpdate_copyUpdate_copyUpdate_copyUpdate_a2c;
        copyUpdate_copyUpdate_a4c[idx4] = copyUpdate_copyUpdate_copyUpdate_a3c;
        copyUpdate_a5c[idx5] = copyUpdate_copyUpdate_a4c;
        a6c[idx6] = copyUpdate_a5c;
        return a6c;
    }

    public final <T> T[] concatArrays(T[] a, T[] b) {
        T[] dest = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, dest, a.length, b.length);
        return dest;
    }

    private VectorInline$() {
    }
}

