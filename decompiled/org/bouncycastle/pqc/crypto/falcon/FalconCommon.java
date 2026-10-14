/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.SHAKE256;

class FalconCommon {
    static final int[] l2bound = new int[]{0, 101498, 208714, 428865, 892039, 1852696, 3842630, 7959734, 16468416, 34034726, 70265242};

    FalconCommon() {
    }

    void hash_to_point_vartime(SHAKE256 sHAKE256, short[] sArray, int n, int n2) {
        int n3 = 1 << n2;
        while (n3 > 0) {
            byte[] byArray = new byte[2];
            sHAKE256.inner_shake256_extract(byArray, 0, 2);
            int n4 = (byArray[0] & 0xFF) << 8 | byArray[1] & 0xFF;
            if (n4 >= 61445) continue;
            while (n4 >= 12289) {
                n4 -= 12289;
            }
            sArray[n++] = (short)n4;
            --n3;
        }
    }

    void hash_to_point_ct(SHAKE256 sHAKE256, short[] sArray, int n, int n2, short[] sArray2, int n3) {
        int n4;
        int n5;
        int n6;
        short[] sArray3 = new short[]{0, 65, 67, 71, 77, 86, 100, 122, 154, 205, 287};
        short[] sArray4 = new short[63];
        int n7 = 1 << n2;
        int n8 = n7 << 1;
        int n9 = sArray3[n2];
        int n10 = n7 + n9;
        int n11 = n3;
        for (n6 = 0; n6 < n10; ++n6) {
            byte[] byArray = new byte[2];
            sHAKE256.inner_shake256_extract(byArray, 0, byArray.length);
            n5 = (byArray[0] & 0xFF) << 8 | byArray[1] & 0xFF;
            n4 = n5 - (0x6002 & (n5 - 24578 >>> 31) - 1);
            n4 -= 0x6002 & (n4 - 24578 >>> 31) - 1;
            n4 -= 0x3001 & (n4 - 12289 >>> 31) - 1;
            n4 |= (n5 - 61445 >>> 31) - 1;
            if (n6 < n7) {
                sArray[n + n6] = (short)n4;
                continue;
            }
            if (n6 < n8) {
                sArray2[n11 + n6 - n7] = (short)n4;
                continue;
            }
            sArray4[n6 - n8] = (short)n4;
        }
        for (int i = 1; i <= n9; i <<= 1) {
            int n12 = 0;
            for (n6 = 0; n6 < n10; ++n6) {
                short s2;
                int n13;
                short s3;
                int n14;
                if (n6 < n7) {
                    n14 = 1;
                    n5 = n + n6;
                    s3 = sArray[n5];
                } else if (n6 < n8) {
                    n14 = 2;
                    n5 = n11 + n6 - n7;
                    s3 = sArray2[n5];
                } else {
                    n14 = 3;
                    n5 = n6 - n8;
                    s3 = sArray4[n5];
                }
                int n15 = n6 - n12;
                int n16 = (s3 >>> 15) - 1;
                n12 -= n16;
                if (n6 < i) continue;
                if (n6 - i < n7) {
                    n13 = 1;
                    n4 = n + n6 - i;
                    s2 = sArray[n4];
                } else if (n6 - i < n8) {
                    n13 = 2;
                    n4 = n11 + (n6 - i) - n7;
                    s2 = sArray2[n4];
                } else {
                    n13 = 3;
                    n4 = n6 - i - n8;
                    s2 = sArray4[n4];
                }
                n16 &= -((n15 & i) + 511 >> 9);
                if (n14 == 1) {
                    sArray[n5] = (short)(s3 ^ n16 & (s3 ^ s2));
                } else if (n14 == 2) {
                    sArray2[n5] = (short)(s3 ^ n16 & (s3 ^ s2));
                } else {
                    sArray4[n5] = (short)(s3 ^ n16 & (s3 ^ s2));
                }
                if (n13 == 1) {
                    sArray[n4] = (short)(s2 ^ n16 & (s3 ^ s2));
                    continue;
                }
                if (n13 == 2) {
                    sArray2[n4] = (short)(s2 ^ n16 & (s3 ^ s2));
                    continue;
                }
                sArray4[n4] = (short)(s2 ^ n16 & (s3 ^ s2));
            }
        }
    }

    int is_short(short[] sArray, int n, short[] sArray2, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = 0;
        int n6 = 0;
        for (int i = 0; i < n4; ++i) {
            short s2 = sArray[n + i];
            n6 |= (n5 += s2 * s2);
            s2 = sArray2[n2 + i];
            n6 |= (n5 += s2 * s2);
        }
        return ((long)(n5 |= -(n6 >>> 31)) & 0xFFFFFFFFL) <= (long)l2bound[n3] ? 1 : 0;
    }

    int is_short_half(int n, short[] sArray, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = -(n >>> 31);
        for (int i = 0; i < n4; ++i) {
            short s2 = sArray[n2 + i];
            n5 |= (n += s2 * s2);
        }
        return ((long)(n |= -(n5 >>> 31)) & 0xFFFFFFFFL) <= (long)l2bound[n3] ? 1 : 0;
    }
}

