/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

abstract class Rem_GF2n {
    protected long mask;
    protected int ki;
    protected int ki64;

    Rem_GF2n() {
    }

    public abstract void rem_gf2n(long[] var1, int var2, long[] var3);

    public abstract void rem_gf2n_xor(long[] var1, int var2, long[] var3);

    public static class REM192_SPECIALIZED_TRINOMIAL_GF2X
    extends Rem_GF2n {
        private final int k3;
        private final int k364;
        private final int ki_k3;

        REM192_SPECIALIZED_TRINOMIAL_GF2X(int n, int n2, int n3, int n4, long l) {
            this.k3 = n;
            this.ki = n2;
            this.ki64 = n3;
            this.k364 = n4;
            this.mask = l;
            this.ki_k3 = n2 - n;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[2] >>> this.ki ^ lArray2[3] << this.ki64;
            long l2 = lArray2[3] >>> this.ki ^ lArray2[4] << this.ki64;
            long l3 = lArray2[4] >>> this.ki ^ lArray2[5] << this.ki64;
            lArray[n + 1] = lArray2[1] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3;
            lArray[n + 2] = (lArray2[2] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3) & this.mask;
            lArray[n] = lArray2[0] ^ (l ^= l3 >>> this.ki_k3) ^ l << this.k3;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[2] >>> this.ki ^ lArray2[3] << this.ki64;
            long l2 = lArray2[3] >>> this.ki ^ lArray2[4] << this.ki64;
            long l3 = lArray2[4] >>> this.ki ^ lArray2[5] << this.ki64;
            int n2 = n + 1;
            lArray[n2] = lArray[n2] ^ (lArray2[1] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3);
            int n3 = n + 2;
            lArray[n3] = lArray[n3] ^ (lArray2[2] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3) & this.mask;
            int n4 = n;
            lArray[n4] = lArray[n4] ^ (lArray2[0] ^ (l ^= l3 >>> this.ki_k3) ^ l << this.k3);
        }
    }

    public static class REM288_SPECIALIZED_TRINOMIAL_GF2X
    extends Rem_GF2n {
        private final int k3;
        private final int k364;
        private final int k364ki;
        private final int k3_ki;

        public REM288_SPECIALIZED_TRINOMIAL_GF2X(int n, int n2, int n3, int n4, long l) {
            this.k3 = n;
            this.ki = n2;
            this.ki64 = n3;
            this.k364 = n4;
            this.mask = l;
            this.k364ki = n4 + n2;
            this.k3_ki = n - n2;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64;
            long l2 = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            lArray[n + 2] = lArray2[2] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3;
            long l3 = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            lArray[n + 3] = lArray2[3] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3;
            long l4 = lArray2[8] >>> this.ki;
            l2 = lArray2[4] >>> this.ki ^ lArray2[5] << this.ki64 ^ l3 >>> this.k364ki ^ l4 << this.k3_ki;
            lArray[n + 4] = (lArray2[4] ^ l4 ^ l3 >>> this.k364 ^ l4 << this.k3) & this.mask;
            lArray[n] = lArray2[0] ^ l2 ^ l2 << this.k3;
            lArray[n + 1] = lArray2[1] ^ l ^ l << this.k3 ^ l2 >>> this.k364;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64;
            long l2 = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            int n2 = n + 2;
            lArray[n2] = lArray[n2] ^ (lArray2[2] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3);
            long l3 = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            int n3 = n + 3;
            lArray[n3] = lArray[n3] ^ (lArray2[3] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3);
            l2 = lArray2[8] >>> this.ki;
            int n4 = n + 4;
            lArray[n4] = lArray[n4] ^ (lArray2[4] ^ l2 ^ l3 >>> this.k364 ^ l2 << this.k3) & this.mask;
            l3 = lArray2[4] >>> this.ki ^ lArray2[5] << this.ki64 ^ l3 >>> this.k364ki ^ l2 << this.k3_ki;
            int n5 = n;
            lArray[n5] = lArray[n5] ^ (lArray2[0] ^ l3 ^ l3 << this.k3);
            int n6 = n + 1;
            lArray[n6] = lArray[n6] ^ (lArray2[1] ^ l ^ l << this.k3 ^ l3 >>> this.k364);
        }
    }

    public static class REM384_SPECIALIZED358_TRINOMIAL_GF2X
    extends Rem_GF2n {
        private final int k3;
        private final int k364;
        private final int k364ki;
        private final int k3_ki;

        public REM384_SPECIALIZED358_TRINOMIAL_GF2X(int n, int n2, int n3, int n4, long l) {
            this.k3 = n;
            this.ki = n2;
            this.ki64 = n3;
            this.k364 = n4;
            this.mask = l;
            this.k364ki = n4 + n2;
            this.k3_ki = n - n2;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            long l2 = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            lArray[n + 2] = lArray2[2] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3;
            long l3 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            lArray[n + 3] = lArray2[3] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3;
            l2 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            lArray[n + 4] = lArray2[4] ^ l2 ^ l3 >>> this.k364 ^ l2 << this.k3;
            l3 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l4 = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64 ^ l2 >>> this.k364ki ^ l3 << this.k3_ki;
            lArray[n + 5] = (lArray2[5] ^ l3 ^ l2 >>> this.k364) & this.mask;
            lArray[n] = lArray2[0] ^ l4 ^ l4 << this.k3;
            lArray[n + 1] = lArray2[1] ^ l ^ l4 >>> this.k364 ^ l << this.k3;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            long l2 = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            int n2 = n + 2;
            lArray[n2] = lArray[n2] ^ (lArray2[2] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3);
            long l3 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            int n3 = n + 3;
            lArray[n3] = lArray[n3] ^ (lArray2[3] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3);
            l2 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            int n4 = n + 4;
            lArray[n4] = lArray[n4] ^ (lArray2[4] ^ l2 ^ l3 >>> this.k364 ^ l2 << this.k3);
            l3 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            int n5 = n + 5;
            lArray[n5] = lArray[n5] ^ (lArray2[5] ^ l3 ^ l2 >>> this.k364) & this.mask;
            l2 = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64 ^ l2 >>> this.k364ki ^ l3 << this.k3_ki;
            int n6 = n;
            lArray[n6] = lArray[n6] ^ (lArray2[0] ^ l2 ^ l2 << this.k3);
            int n7 = n + 1;
            lArray[n7] = lArray[n7] ^ (lArray2[1] ^ l ^ l2 >>> this.k364 ^ l << this.k3);
        }
    }

    public static class REM384_SPECIALIZED_TRINOMIAL_GF2X
    extends Rem_GF2n {
        private final int k3;
        private final int k364;
        private final int k364ki;
        private final int k3_ki;

        public REM384_SPECIALIZED_TRINOMIAL_GF2X(int n, int n2, int n3, int n4, long l) {
            this.k3 = n;
            this.ki = n2;
            this.ki64 = n3;
            this.k364 = n4;
            this.mask = l;
            this.k364ki = n4 + n2;
            this.k3_ki = n - n2;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            long l2 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            long l3 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            long l4 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l5 = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64 ^ l2 >>> this.k364ki ^ l3 << this.k3_ki;
            long l6 = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64 ^ l3 >>> this.k364ki ^ l4 << this.k3_ki;
            lArray[n] = lArray2[0] ^ l5;
            lArray[n + 1] = lArray2[1] ^ l6 ^ l5 << this.k3;
            lArray[n + 2] = lArray2[2] ^ l ^ l5 >>> this.k364 ^ l6 << this.k3;
            lArray[n + 3] = lArray2[3] ^ l2 ^ l6 >>> this.k364 ^ l << this.k3;
            lArray[n + 4] = lArray2[4] ^ l3 ^ l >>> this.k364 ^ l2 << this.k3;
            lArray[n + 5] = (lArray2[5] ^ l4 ^ l2 >>> this.k364) & this.mask;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            long l2 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            long l3 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            long l4 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l5 = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64 ^ l2 >>> this.k364ki ^ l3 << this.k3_ki;
            long l6 = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64 ^ l3 >>> this.k364ki ^ l4 << this.k3_ki;
            int n2 = n;
            lArray[n2] = lArray[n2] ^ (lArray2[0] ^ l5);
            int n3 = n + 1;
            lArray[n3] = lArray[n3] ^ (lArray2[1] ^ l6 ^ l5 << this.k3);
            int n4 = n + 2;
            lArray[n4] = lArray[n4] ^ (lArray2[2] ^ l ^ l5 >>> this.k364 ^ l6 << this.k3);
            int n5 = n + 3;
            lArray[n5] = lArray[n5] ^ (lArray2[3] ^ l2 ^ l6 >>> this.k364 ^ l << this.k3);
            int n6 = n + 4;
            lArray[n6] = lArray[n6] ^ (lArray2[4] ^ l3 ^ l >>> this.k364 ^ l2 << this.k3);
            int n7 = n + 5;
            lArray[n7] = lArray[n7] ^ (lArray2[5] ^ l4 ^ l2 >>> this.k364) & this.mask;
        }
    }

    public static class REM384_TRINOMIAL_GF2X
    extends Rem_GF2n {
        private final int k3;
        private final int k364;
        private final int ki_k3;

        public REM384_TRINOMIAL_GF2X(int n, int n2, int n3, int n4, long l) {
            this.k3 = n;
            this.ki = n2;
            this.ki64 = n3;
            this.k364 = n4;
            this.mask = l;
            this.ki_k3 = n2 - n;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64;
            long l2 = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            long l3 = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            long l4 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            long l5 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            long l6 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l7 = l ^ l6 >>> this.ki_k3;
            lArray[n] = lArray2[0] ^ l7 ^ l7 << this.k3;
            lArray[n + 1] = lArray2[1] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3;
            lArray[n + 2] = lArray2[2] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3;
            lArray[n + 3] = lArray2[3] ^ l4 ^ l3 >>> this.k364 ^ l4 << this.k3;
            lArray[n + 4] = lArray2[4] ^ l5 ^ l4 >>> this.k364 ^ l5 << this.k3;
            lArray[n + 5] = (lArray2[5] ^ l6 ^ l5 >>> this.k364 ^ l6 << this.k3) & this.mask;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[5] >>> this.ki ^ lArray2[6] << this.ki64;
            long l2 = lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            long l3 = lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            long l4 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            long l5 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            long l6 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l7 = l ^ l6 >>> this.ki_k3;
            int n2 = n;
            lArray[n2] = lArray[n2] ^ (lArray2[0] ^ l7 ^ l7 << this.k3);
            int n3 = n + 1;
            lArray[n3] = lArray[n3] ^ (lArray2[1] ^ l2 ^ l >>> this.k364 ^ l2 << this.k3);
            int n4 = n + 2;
            lArray[n4] = lArray[n4] ^ (lArray2[2] ^ l3 ^ l2 >>> this.k364 ^ l3 << this.k3);
            int n5 = n + 3;
            lArray[n5] = lArray[n5] ^ (lArray2[3] ^ l4 ^ l3 >>> this.k364 ^ l4 << this.k3);
            int n6 = n + 4;
            lArray[n6] = lArray[n6] ^ (lArray2[4] ^ l5 ^ l4 >>> this.k364 ^ l5 << this.k3);
            int n7 = n + 5;
            lArray[n7] = lArray[n7] ^ (lArray2[5] ^ l6 ^ l5 >>> this.k364 ^ l6 << this.k3) & this.mask;
        }
    }

    public static class REM402_SPECIALIZED_TRINOMIAL_GF2X
    extends Rem_GF2n {
        private final int k3;
        private final int k364;

        public REM402_SPECIALIZED_TRINOMIAL_GF2X(int n, int n2, int n3, int n4, long l) {
            this.k3 = n;
            this.ki = n2;
            this.ki64 = n3;
            this.k364 = n4;
            this.mask = l;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            long l2 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l3 = lArray2[11] >>> this.ki ^ lArray2[12] << this.ki64;
            long l4 = lArray2[12] >>> this.ki;
            long l5 = l >>> 39 ^ l2 << 25 ^ lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            long l6 = l2 >>> 39 ^ l3 << 25 ^ lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            long l7 = l3 >>> 39 ^ l4 << 25 ^ lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            lArray[n] = lArray2[0] ^ l5;
            lArray[n + 1] = lArray2[1] ^ l6;
            lArray[n + 2] = lArray2[2] ^ l7 ^ l5 << this.k3;
            lArray[n + 3] = lArray2[3] ^ l ^ l5 >>> this.k364 ^ l6 << this.k3;
            lArray[n + 4] = lArray2[4] ^ l2 ^ l6 >>> this.k364 ^ l7 << this.k3;
            lArray[n + 5] = lArray2[5] ^ l3 ^ l7 >>> this.k364 ^ l << this.k3;
            lArray[n + 6] = (lArray2[6] ^ l4 ^ l >>> this.k364) & this.mask;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            long l2 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l3 = lArray2[11] >>> this.ki ^ lArray2[12] << this.ki64;
            long l4 = lArray2[12] >>> this.ki;
            long l5 = l >>> 39 ^ l2 << 25 ^ lArray2[6] >>> this.ki ^ lArray2[7] << this.ki64;
            long l6 = l2 >>> 39 ^ l3 << 25 ^ lArray2[7] >>> this.ki ^ lArray2[8] << this.ki64;
            long l7 = l3 >>> 39 ^ l4 << 25 ^ lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            int n2 = n;
            lArray[n2] = lArray[n2] ^ (lArray2[0] ^ l5);
            int n3 = n + 1;
            lArray[n3] = lArray[n3] ^ (lArray2[1] ^ l6);
            int n4 = n + 2;
            lArray[n4] = lArray[n4] ^ (lArray2[2] ^ l7 ^ l5 << this.k3);
            int n5 = n + 3;
            lArray[n5] = lArray[n5] ^ (lArray2[3] ^ l ^ l5 >>> this.k364 ^ l6 << this.k3);
            int n6 = n + 4;
            lArray[n6] = lArray[n6] ^ (lArray2[4] ^ l2 ^ l6 >>> this.k364 ^ l7 << this.k3);
            int n7 = n + 5;
            lArray[n7] = lArray[n7] ^ (lArray2[5] ^ l3 ^ l7 >>> this.k364 ^ l << this.k3);
            int n8 = n + 6;
            lArray[n8] = lArray[n8] ^ (lArray2[6] ^ l4 ^ l >>> this.k364) & this.mask;
        }
    }

    public static class REM544_PENTANOMIAL_GF2X
    extends Rem_GF2n {
        private final int k1;
        private final int k2;
        private final int k3;
        private final int k164;
        private final int k264;
        private final int k364;
        private final int ki_k3;
        private final int ki_k2;
        private final int ki_k1;

        public REM544_PENTANOMIAL_GF2X(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
            this.k1 = n;
            this.k2 = n2;
            this.k3 = n3;
            this.ki = n4;
            this.ki64 = n5;
            this.k164 = n6;
            this.k264 = n7;
            this.k364 = n8;
            this.mask = l;
            this.ki_k3 = n4 - n3;
            this.ki_k2 = n4 - n2;
            this.ki_k1 = n4 - n;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[16] >>> this.ki;
            long l2 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            long l3 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            lArray[n + 1] = lArray2[1] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3;
            lArray[n] = lArray2[0] ^ (l2 ^= l >>> this.ki_k3 ^ l >>> this.ki_k2 ^ l >>> this.ki_k1) ^ l2 << this.k1 ^ l2 << this.k2 ^ l2 << this.k3;
            l2 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            lArray[n + 2] = lArray2[2] ^ l2 ^ l3 >>> this.k164 ^ l2 << this.k1 ^ l3 >>> this.k264 ^ l2 << this.k2 ^ l3 >>> this.k364 ^ l2 << this.k3;
            l3 = lArray2[11] >>> this.ki ^ lArray2[12] << this.ki64;
            lArray[n + 3] = lArray2[3] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3;
            l2 = lArray2[12] >>> this.ki ^ lArray2[13] << this.ki64;
            lArray[n + 4] = lArray2[4] ^ l2 ^ l3 >>> this.k164 ^ l2 << this.k1 ^ l3 >>> this.k264 ^ l2 << this.k2 ^ l3 >>> this.k364 ^ l2 << this.k3;
            l3 = lArray2[13] >>> this.ki ^ lArray2[14] << this.ki64;
            lArray[n + 5] = lArray2[5] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3;
            l2 = lArray2[14] >>> this.ki ^ lArray2[15] << this.ki64;
            lArray[n + 6] = lArray2[6] ^ l2 ^ l3 >>> this.k164 ^ l2 << this.k1 ^ l3 >>> this.k264 ^ l2 << this.k2 ^ l3 >>> this.k364 ^ l2 << this.k3;
            l3 = lArray2[15] >>> this.ki ^ lArray2[16] << this.ki64;
            lArray[n + 7] = lArray2[7] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3;
            lArray[n + 8] = (lArray2[8] ^ l ^ l3 >>> this.k164 ^ l << this.k1 ^ l3 >>> this.k264 ^ l << this.k2 ^ l3 >>> this.k364 ^ l << this.k3) & this.mask;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[16] >>> this.ki;
            long l2 = lArray2[8] >>> this.ki ^ lArray2[9] << this.ki64;
            long l3 = lArray2[9] >>> this.ki ^ lArray2[10] << this.ki64;
            int n2 = n + 1;
            lArray[n2] = lArray[n2] ^ (lArray2[1] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3);
            int n3 = n;
            lArray[n3] = lArray[n3] ^ (lArray2[0] ^ (l2 ^= l >>> this.ki_k3 ^ l >>> this.ki_k2 ^ l >>> this.ki_k1) ^ l2 << this.k1 ^ l2 << this.k2 ^ l2 << this.k3);
            l2 = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            int n4 = n + 2;
            lArray[n4] = lArray[n4] ^ (lArray2[2] ^ l2 ^ l3 >>> this.k164 ^ l2 << this.k1 ^ l3 >>> this.k264 ^ l2 << this.k2 ^ l3 >>> this.k364 ^ l2 << this.k3);
            l3 = lArray2[11] >>> this.ki ^ lArray2[12] << this.ki64;
            int n5 = n + 3;
            lArray[n5] = lArray[n5] ^ (lArray2[3] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3);
            l2 = lArray2[12] >>> this.ki ^ lArray2[13] << this.ki64;
            int n6 = n + 4;
            lArray[n6] = lArray[n6] ^ (lArray2[4] ^ l2 ^ l3 >>> this.k164 ^ l2 << this.k1 ^ l3 >>> this.k264 ^ l2 << this.k2 ^ l3 >>> this.k364 ^ l2 << this.k3);
            l3 = lArray2[13] >>> this.ki ^ lArray2[14] << this.ki64;
            int n7 = n + 5;
            lArray[n7] = lArray[n7] ^ (lArray2[5] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3);
            l2 = lArray2[14] >>> this.ki ^ lArray2[15] << this.ki64;
            int n8 = n + 6;
            lArray[n8] = lArray[n8] ^ (lArray2[6] ^ l2 ^ l3 >>> this.k164 ^ l2 << this.k1 ^ l3 >>> this.k264 ^ l2 << this.k2 ^ l3 >>> this.k364 ^ l2 << this.k3);
            l3 = lArray2[15] >>> this.ki ^ lArray2[16] << this.ki64;
            int n9 = n + 7;
            lArray[n9] = lArray[n9] ^ (lArray2[7] ^ l3 ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2 ^ l2 >>> this.k364 ^ l3 << this.k3);
            int n10 = n + 8;
            lArray[n10] = lArray[n10] ^ (lArray2[8] ^ l ^ l3 >>> this.k164 ^ l << this.k1 ^ l3 >>> this.k264 ^ l << this.k2 ^ l3 >>> this.k364 ^ l << this.k3) & this.mask;
        }
    }

    public static class REM544_PENTANOMIAL_K3_IS_128_GF2X
    extends Rem_GF2n {
        private final int k1;
        private final int k2;
        private final int k164;
        private final int k264;

        public REM544_PENTANOMIAL_K3_IS_128_GF2X(int n, int n2, int n3, int n4, int n5, int n6, long l) {
            this.k1 = n;
            this.k2 = n2;
            this.ki = n3;
            this.ki64 = n4;
            this.k164 = n5;
            this.k264 = n6;
            this.mask = l;
        }

        @Override
        public void rem_gf2n(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l2 = lArray2[11] >>> this.ki ^ lArray2[12] << this.ki64;
            long l3 = lArray2[12] >>> this.ki ^ lArray2[13] << this.ki64;
            lArray[n + 4] = lArray2[4] ^ l3 ^ l ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2;
            long l4 = lArray2[13] >>> this.ki ^ lArray2[14] << this.ki64;
            lArray[n + 5] = lArray2[5] ^ l4 ^ l2 ^ l3 >>> this.k164 ^ l4 << this.k1 ^ l3 >>> this.k264 ^ l4 << this.k2;
            long l5 = lArray2[14] >>> this.ki ^ lArray2[15] << this.ki64;
            lArray[n + 6] = lArray2[6] ^ l5 ^ l3 ^ l4 >>> this.k164 ^ l5 << this.k1 ^ l4 >>> this.k264 ^ l5 << this.k2;
            l3 = lArray2[15] >>> this.ki ^ lArray2[16] << this.ki64;
            lArray[n + 7] = lArray2[7] ^ l3 ^ l4 ^ l5 >>> this.k164 ^ l3 << this.k1 ^ l5 >>> this.k264 ^ l3 << this.k2;
            l4 = lArray2[16] >>> this.ki;
            lArray[n + 8] = (lArray2[8] ^ l4 ^ l5 ^ l3 >>> this.k164 ^ l4 << this.k1 ^ l3 >>> this.k264 ^ l4 << this.k2) & this.mask;
            l5 = (lArray2[8] ^ l5) >>> this.ki ^ (lArray2[9] ^ l3) << this.ki64 ^ lArray2[16] >>> this.k264;
            l3 = (lArray2[9] ^ l3) >>> this.ki ^ (lArray2[10] ^ l4) << this.ki64;
            lArray[n] = lArray2[0] ^ l5 ^ l5 << this.k1 ^ l5 << this.k2;
            lArray[n + 1] = lArray2[1] ^ l3 ^ l5 >>> this.k164 ^ l3 << this.k1 ^ l5 >>> this.k264 ^ l3 << this.k2;
            lArray[n + 2] = lArray2[2] ^ l ^ l5 ^ l3 >>> this.k164 ^ l << this.k1 ^ l3 >>> this.k264 ^ l << this.k2;
            lArray[n + 3] = lArray2[3] ^ l2 ^ l3 ^ l >>> this.k164 ^ l2 << this.k1 ^ l >>> this.k264 ^ l2 << this.k2;
        }

        @Override
        public void rem_gf2n_xor(long[] lArray, int n, long[] lArray2) {
            long l = lArray2[10] >>> this.ki ^ lArray2[11] << this.ki64;
            long l2 = lArray2[11] >>> this.ki ^ lArray2[12] << this.ki64;
            long l3 = lArray2[12] >>> this.ki ^ lArray2[13] << this.ki64;
            int n2 = n + 4;
            lArray[n2] = lArray[n2] ^ (lArray2[4] ^ l3 ^ l ^ l2 >>> this.k164 ^ l3 << this.k1 ^ l2 >>> this.k264 ^ l3 << this.k2);
            long l4 = lArray2[13] >>> this.ki ^ lArray2[14] << this.ki64;
            int n3 = n + 5;
            lArray[n3] = lArray[n3] ^ (lArray2[5] ^ l4 ^ l2 ^ l3 >>> this.k164 ^ l4 << this.k1 ^ l3 >>> this.k264 ^ l4 << this.k2);
            long l5 = lArray2[14] >>> this.ki ^ lArray2[15] << this.ki64;
            int n4 = n + 6;
            lArray[n4] = lArray[n4] ^ (lArray2[6] ^ l5 ^ l3 ^ l4 >>> this.k164 ^ l5 << this.k1 ^ l4 >>> this.k264 ^ l5 << this.k2);
            l3 = lArray2[15] >>> this.ki ^ lArray2[16] << this.ki64;
            int n5 = n + 7;
            lArray[n5] = lArray[n5] ^ (lArray2[7] ^ l3 ^ l4 ^ l5 >>> this.k164 ^ l3 << this.k1 ^ l5 >>> this.k264 ^ l3 << this.k2);
            l4 = lArray2[16] >>> this.ki;
            int n6 = n + 8;
            lArray[n6] = lArray[n6] ^ (lArray2[8] ^ l4 ^ l5 ^ l3 >>> this.k164 ^ l4 << this.k1 ^ l3 >>> this.k264 ^ l4 << this.k2) & this.mask;
            l5 = (lArray2[8] ^ l5) >>> this.ki ^ (lArray2[9] ^ l3) << this.ki64 ^ lArray2[16] >>> this.k264;
            l3 = (lArray2[9] ^ l3) >>> this.ki ^ (lArray2[10] ^ l4) << this.ki64;
            int n7 = n;
            lArray[n7] = lArray[n7] ^ (lArray2[0] ^ l5 ^ l5 << this.k1 ^ l5 << this.k2);
            int n8 = n + 1;
            lArray[n8] = lArray[n8] ^ (lArray2[1] ^ l3 ^ l5 >>> this.k164 ^ l3 << this.k1 ^ l5 >>> this.k264 ^ l3 << this.k2);
            int n9 = n + 2;
            lArray[n9] = lArray[n9] ^ (lArray2[2] ^ l ^ l5 ^ l3 >>> this.k164 ^ l << this.k1 ^ l3 >>> this.k264 ^ l << this.k2);
            int n10 = n + 3;
            lArray[n10] = lArray[n10] ^ (lArray2[3] ^ l2 ^ l3 ^ l >>> this.k164 ^ l2 << this.k1 ^ l >>> this.k264 ^ l2 << this.k2);
        }
    }
}

