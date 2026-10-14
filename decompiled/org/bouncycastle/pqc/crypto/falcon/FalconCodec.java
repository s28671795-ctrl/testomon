/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

class FalconCodec {
    final byte[] max_fg_bits = new byte[]{0, 8, 8, 8, 8, 8, 7, 7, 6, 6, 5};
    final byte[] max_FG_bits = new byte[]{0, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    final byte[] max_sig_bits = new byte[]{0, 10, 11, 11, 12, 12, 12, 12, 12, 12, 12};

    FalconCodec() {
    }

    int modq_encode(byte[] byArray, int n, int n2, short[] sArray, int n3, int n4) {
        int n5;
        int n6 = 1 << n4;
        for (n5 = 0; n5 < n6; ++n5) {
            if ((sArray[n3 + n5] & 0xFFFF) < 12289) continue;
            return 0;
        }
        int n7 = n6 * 14 + 7 >> 3;
        if (byArray == null) {
            return n7;
        }
        if (n7 > n2) {
            return 0;
        }
        int n8 = n;
        int n9 = 0;
        int n10 = 0;
        for (n5 = 0; n5 < n6; ++n5) {
            n9 = n9 << 14 | sArray[n3 + n5] & 0xFFFF;
            n10 += 14;
            while (n10 >= 8) {
                byArray[n8++] = (byte)(n9 >> (n10 -= 8));
            }
        }
        if (n10 > 0) {
            byArray[n8] = (byte)(n9 << 8 - n10);
        }
        return n7;
    }

    int modq_decode(short[] sArray, int n, int n2, byte[] byArray, int n3, int n4) {
        int n5 = 1 << n2;
        int n6 = n5 * 14 + 7 >> 3;
        if (n6 > n4) {
            return 0;
        }
        int n7 = n3;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        while (n10 < n5) {
            n8 = n8 << 8 | byArray[n7++] & 0xFF;
            if ((n9 += 8) < 14) continue;
            int n11 = n8 >>> (n9 -= 14) & 0x3FFF;
            if (n11 >= 12289) {
                return 0;
            }
            sArray[n + n10] = (short)n11;
            ++n10;
        }
        if ((n8 & (1 << n9) - 1) != 0) {
            return 0;
        }
        return n6;
    }

    int trim_i16_encode(byte[] byArray, int n, int n2, short[] sArray, int n3, int n4, int n5) {
        int n6;
        int n7 = 1 << n4;
        int n8 = (1 << n5 - 1) - 1;
        int n9 = -n8;
        for (n6 = 0; n6 < n7; ++n6) {
            if (sArray[n3 + n6] >= n9 && sArray[n3 + n6] <= n8) continue;
            return 0;
        }
        int n10 = n7 * n5 + 7 >> 3;
        if (byArray == null) {
            return n10;
        }
        if (n10 > n2) {
            return 0;
        }
        int n11 = n;
        int n12 = 0;
        int n13 = 0;
        int n14 = (1 << n5) - 1;
        for (n6 = 0; n6 < n7; ++n6) {
            n12 = n12 << n5 | sArray[n3 + n6] & 0xFFF & n14;
            n13 += n5;
            while (n13 >= 8) {
                byArray[n11++] = (byte)(n12 >> (n13 -= 8));
            }
        }
        if (n13 > 0) {
            byArray[n11++] = (byte)(n12 << 8 - n13);
        }
        return n10;
    }

    int trim_i16_decode(short[] sArray, int n, int n2, int n3, byte[] byArray, int n4, int n5) {
        int n6 = 1 << n2;
        int n7 = n6 * n3 + 7 >> 3;
        if (n7 > n5) {
            return 0;
        }
        int n8 = n4;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = (1 << n3) - 1;
        int n13 = 1 << n3 - 1;
        while (n9 < n6) {
            n10 = n10 << 8 | byArray[n8++] & 0xFF;
            n11 += 8;
            while (n11 >= n3 && n9 < n6) {
                int n14 = n10 >>> (n11 -= n3) & n12;
                if ((n14 |= -(n14 & n13)) == -n13) {
                    return 0;
                }
                n14 |= -(n14 & n13);
                sArray[n + n9] = (short)n14;
                ++n9;
            }
        }
        if ((n10 & (1 << n11) - 1) != 0) {
            return 0;
        }
        return n7;
    }

    int trim_i8_encode(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4, int n5) {
        int n6;
        int n7 = 1 << n4;
        int n8 = (1 << n5 - 1) - 1;
        int n9 = -n8;
        for (n6 = 0; n6 < n7; ++n6) {
            if (byArray2[n3 + n6] >= n9 && byArray2[n3 + n6] <= n8) continue;
            return 0;
        }
        int n10 = n7 * n5 + 7 >> 3;
        if (byArray == null) {
            return n10;
        }
        if (n10 > n2) {
            return 0;
        }
        int n11 = n;
        int n12 = 0;
        int n13 = 0;
        int n14 = (1 << n5) - 1;
        for (n6 = 0; n6 < n7; ++n6) {
            n12 = n12 << n5 | byArray2[n3 + n6] & 0xFFFF & n14;
            n13 += n5;
            while (n13 >= 8) {
                byArray[n11++] = (byte)(n12 >>> (n13 -= 8));
            }
        }
        if (n13 > 0) {
            byArray[n11++] = (byte)(n12 << 8 - n13);
        }
        return n10;
    }

    int trim_i8_decode(byte[] byArray, int n, int n2, int n3, byte[] byArray2, int n4, int n5) {
        int n6 = 1 << n2;
        int n7 = n6 * n3 + 7 >> 3;
        if (n7 > n5) {
            return 0;
        }
        int n8 = n4;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = (1 << n3) - 1;
        int n13 = 1 << n3 - 1;
        while (n9 < n6) {
            n10 = n10 << 8 | byArray2[n8++] & 0xFF;
            n11 += 8;
            while (n11 >= n3 && n9 < n6) {
                int n14 = n10 >>> (n11 -= n3) & n12;
                if ((n14 |= -(n14 & n13)) == -n13) {
                    return 0;
                }
                byArray[n + n9] = (byte)n14;
                ++n9;
            }
        }
        if ((n10 & (1 << n11) - 1) != 0) {
            return 0;
        }
        return n7;
    }

    int comp_encode(byte[] byArray, int n, int n2, short[] sArray, int n3, int n4) {
        int n5;
        int n6 = 1 << n4;
        int n7 = n;
        for (n5 = 0; n5 < n6; ++n5) {
            if (sArray[n3 + n5] >= -2047 && sArray[n3 + n5] <= 2047) continue;
            return 0;
        }
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        for (n5 = 0; n5 < n6; ++n5) {
            n8 <<= 1;
            int n11 = sArray[n3 + n5];
            if (n11 < 0) {
                n11 = -n11;
                n8 |= 1;
            }
            int n12 = n11;
            n8 <<= 7;
            n8 |= n12 & 0x7F;
            n9 += 8;
            n8 <<= (n12 >>>= 7) + 1;
            n8 |= 1;
            n9 += n12 + 1;
            while (n9 >= 8) {
                n9 -= 8;
                if (byArray != null) {
                    if (n10 >= n2) {
                        return 0;
                    }
                    byArray[n7 + n10] = (byte)(n8 >>> n9);
                }
                ++n10;
            }
        }
        if (n9 > 0) {
            if (byArray != null) {
                if (n10 >= n2) {
                    return 0;
                }
                byArray[n7 + n10] = (byte)(n8 << 8 - n9);
            }
            ++n10;
        }
        return n10;
    }

    int comp_decode(short[] sArray, int n, int n2, byte[] byArray, int n3, int n4) {
        int n5 = 1 << n2;
        int n6 = n3;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (int i = 0; i < n5; ++i) {
            int n10;
            int n11;
            block7: {
                if (n9 >= n4) {
                    return 0;
                }
                n7 = n7 << 8 | byArray[n6 + n9] & 0xFF;
                ++n9;
                int n12 = n7 >>> n8;
                n11 = n12 & 0x80;
                n10 = n12 & 0x7F;
                do {
                    if (n8 == 0) {
                        if (n9 >= n4) {
                            return 0;
                        }
                        n7 = n7 << 8 | byArray[n6 + n9] & 0xFF;
                        ++n9;
                        n8 = 8;
                    }
                    if ((n7 >>> --n8 & 1) != 0) break block7;
                } while ((n10 += 128) <= 2047);
                return 0;
            }
            if (n11 != 0 && n10 == 0) {
                return 0;
            }
            sArray[n + i] = (short)(n11 != 0 ? -n10 : n10);
        }
        if ((n7 & (1 << n8) - 1) != 0) {
            return 0;
        }
        return n9;
    }
}

