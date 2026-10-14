/*
 * Decompiled with CFR 0.152.
 */
package org.h2.compress;

import java.nio.ByteBuffer;
import org.h2.compress.Compressor;

public final class CompressLZF
implements Compressor {
    private static final int HASH_SIZE = 16384;
    private static final int MAX_LITERAL = 32;
    private static final int MAX_OFF = 8192;
    private static final int MAX_REF = 264;
    private int[] cachedHashTable;

    @Override
    public void setOptions(String string) {
    }

    private static int first(byte[] byArray, int n) {
        return byArray[n] << 8 | byArray[n + 1] & 0xFF;
    }

    private static int first(ByteBuffer byteBuffer, int n) {
        return byteBuffer.get(n) << 8 | byteBuffer.get(n + 1) & 0xFF;
    }

    private static int next(int n, byte[] byArray, int n2) {
        return n << 8 | byArray[n2 + 2] & 0xFF;
    }

    private static int next(int n, ByteBuffer byteBuffer, int n2) {
        return n << 8 | byteBuffer.get(n2 + 2) & 0xFF;
    }

    private static int hash(int n) {
        return n * 2777 >> 9 & 0x3FFF;
    }

    @Override
    public int compress(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        int n4 = n;
        n2 += n;
        if (this.cachedHashTable == null) {
            this.cachedHashTable = new int[16384];
        }
        int[] nArray = this.cachedHashTable;
        int n5 = 0;
        ++n3;
        int n6 = CompressLZF.first(byArray, n);
        while (n < n2 - 4) {
            byte by = byArray[n + 2];
            n6 = (n6 << 8) + (by & 0xFF);
            int n7 = CompressLZF.hash(n6);
            int n8 = nArray[n7];
            nArray[n7] = n;
            if (n8 < n && n8 > n4 && (n7 = n - n8 - 1) < 8192 && byArray[n8 + 2] == by && byArray[n8 + 1] == (byte)(n6 >> 8) && byArray[n8] == (byte)(n6 >> 16)) {
                int n9;
                int n10 = n2 - n - 2;
                if (n10 > 264) {
                    n10 = 264;
                }
                if (n5 == 0) {
                    --n3;
                } else {
                    byArray2[n3 - n5 - 1] = (byte)(n5 - 1);
                    n5 = 0;
                }
                for (n9 = 3; n9 < n10 && byArray[n8 + n9] == byArray[n + n9]; ++n9) {
                }
                if ((n9 -= 2) < 7) {
                    byArray2[n3++] = (byte)((n7 >> 8) + (n9 << 5));
                } else {
                    byArray2[n3++] = (byte)((n7 >> 8) + 224);
                    byArray2[n3++] = (byte)(n9 - 7);
                }
                byArray2[n3++] = (byte)n7;
                ++n3;
                n6 = CompressLZF.first(byArray, n += n9);
                n6 = CompressLZF.next(n6, byArray, n);
                nArray[CompressLZF.hash((int)n6)] = n++;
                n6 = CompressLZF.next(n6, byArray, n);
                nArray[CompressLZF.hash((int)n6)] = n++;
                continue;
            }
            byArray2[n3++] = byArray[n++];
            if (++n5 != 32) continue;
            byArray2[n3 - n5 - 1] = (byte)(n5 - 1);
            n5 = 0;
            ++n3;
        }
        while (n < n2) {
            byArray2[n3++] = byArray[n++];
            if (++n5 != 32) continue;
            byArray2[n3 - n5 - 1] = (byte)(n5 - 1);
            n5 = 0;
            ++n3;
        }
        byArray2[n3 - n5 - 1] = (byte)(n5 - 1);
        if (n5 == 0) {
            --n3;
        }
        return n3;
    }

    public int compress(ByteBuffer byteBuffer, int n, byte[] byArray, int n2) {
        int n3 = n;
        int n4 = byteBuffer.capacity();
        if (this.cachedHashTable == null) {
            this.cachedHashTable = new int[16384];
        }
        int[] nArray = this.cachedHashTable;
        int n5 = 0;
        ++n2;
        int n6 = CompressLZF.first(byteBuffer, n);
        while (n < n4 - 4) {
            byte by = byteBuffer.get(n + 2);
            n6 = (n6 << 8) + (by & 0xFF);
            int n7 = CompressLZF.hash(n6);
            int n8 = nArray[n7];
            nArray[n7] = n;
            if (n8 < n && n8 > n3 && (n7 = n - n8 - 1) < 8192 && byteBuffer.get(n8 + 2) == by && byteBuffer.get(n8 + 1) == (byte)(n6 >> 8) && byteBuffer.get(n8) == (byte)(n6 >> 16)) {
                int n9;
                int n10 = n4 - n - 2;
                if (n10 > 264) {
                    n10 = 264;
                }
                if (n5 == 0) {
                    --n2;
                } else {
                    byArray[n2 - n5 - 1] = (byte)(n5 - 1);
                    n5 = 0;
                }
                for (n9 = 3; n9 < n10 && byteBuffer.get(n8 + n9) == byteBuffer.get(n + n9); ++n9) {
                }
                if ((n9 -= 2) < 7) {
                    byArray[n2++] = (byte)((n7 >> 8) + (n9 << 5));
                } else {
                    byArray[n2++] = (byte)((n7 >> 8) + 224);
                    byArray[n2++] = (byte)(n9 - 7);
                }
                byArray[n2++] = (byte)n7;
                ++n2;
                n6 = CompressLZF.first(byteBuffer, n += n9);
                n6 = CompressLZF.next(n6, byteBuffer, n);
                nArray[CompressLZF.hash((int)n6)] = n++;
                n6 = CompressLZF.next(n6, byteBuffer, n);
                nArray[CompressLZF.hash((int)n6)] = n++;
                continue;
            }
            byArray[n2++] = byteBuffer.get(n++);
            if (++n5 != 32) continue;
            byArray[n2 - n5 - 1] = (byte)(n5 - 1);
            n5 = 0;
            ++n2;
        }
        while (n < n4) {
            byArray[n2++] = byteBuffer.get(n++);
            if (++n5 != 32) continue;
            byArray[n2 - n5 - 1] = (byte)(n5 - 1);
            n5 = 0;
            ++n2;
        }
        byArray[n2 - n5 - 1] = (byte)(n5 - 1);
        if (n5 == 0) {
            --n2;
        }
        return n2;
    }

    @Override
    public void expand(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        if (n < 0 || n3 < 0 || n4 < 0) {
            throw new IllegalArgumentException();
        }
        do {
            int n5;
            if ((n5 = byArray[n++] & 0xFF) < 32) {
                System.arraycopy(byArray, n, byArray2, n3, ++n5);
                n3 += n5;
                n += n5;
                continue;
            }
            int n6 = n5 >> 5;
            if (n6 == 7) {
                n6 += byArray[n++] & 0xFF;
            }
            n5 = -((n5 & 0x1F) << 8) - 1;
            n5 -= byArray[n++] & 0xFF;
            n5 += n3;
            if (n3 + (n6 += 2) >= byArray2.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            for (int i = 0; i < n6; ++i) {
                byArray2[n3++] = byArray2[n5++];
            }
        } while (n3 < n4);
    }

    public static void expand(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        do {
            int n;
            int n2;
            if ((n2 = byteBuffer.get() & 0xFF) < 32) {
                ++n2;
                for (n = 0; n < n2; ++n) {
                    byteBuffer2.put(byteBuffer.get());
                }
            } else {
                n = n2 >> 5;
                if (n == 7) {
                    n += byteBuffer.get() & 0xFF;
                }
                n += 2;
                n2 = -((n2 & 0x1F) << 8) - 1;
                n2 -= byteBuffer.get() & 0xFF;
                n2 += byteBuffer2.position();
                for (int i = 0; i < n; ++i) {
                    byteBuffer2.put(byteBuffer2.get(n2++));
                }
            }
        } while (byteBuffer2.position() < byteBuffer2.capacity());
    }

    @Override
    public int getAlgorithm() {
        return 1;
    }
}

