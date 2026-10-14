/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import java.security.SecureRandom;
import java.util.Arrays;
import org.bouncycastle.pqc.crypto.gemss.GeMSSUtils;
import org.bouncycastle.pqc.crypto.gemss.PointerUnion;
import org.bouncycastle.util.Pack;

class Pointer {
    protected long[] array;
    protected int cp;

    public Pointer() {
        this.cp = 0;
    }

    public Pointer(int n) {
        this.array = new long[n];
        this.cp = 0;
    }

    public Pointer(Pointer pointer) {
        this.array = pointer.array;
        this.cp = pointer.cp;
    }

    public Pointer(Pointer pointer, int n) {
        this.array = pointer.array;
        this.cp = pointer.cp + n;
    }

    public long get(int n) {
        return this.array[this.cp + n];
    }

    public long get() {
        return this.array[this.cp];
    }

    public void set(int n, long l) {
        this.array[this.cp + n] = l;
    }

    public void set(long l) {
        this.array[this.cp] = l;
    }

    public void setXor(int n, long l) {
        int n2 = this.cp + n;
        this.array[n2] = this.array[n2] ^ l;
    }

    public void setXor(long l) {
        int n = this.cp;
        this.array[n] = this.array[n] ^ l;
    }

    public void setXorRange(Pointer pointer, int n) {
        int n2 = this.cp;
        int n3 = pointer.cp;
        for (int i = 0; i < n; ++i) {
            int n4 = n2++;
            this.array[n4] = this.array[n4] ^ pointer.array[n3++];
        }
    }

    public void setXorRange(Pointer pointer, int n, int n2) {
        int n3 = this.cp;
        n += pointer.cp;
        for (int i = 0; i < n2; ++i) {
            int n4 = n3++;
            this.array[n4] = this.array[n4] ^ pointer.array[n++];
        }
    }

    public void setXorRange(int n, Pointer pointer, int n2, int n3) {
        n += this.cp;
        n2 += pointer.cp;
        for (int i = 0; i < n3; ++i) {
            int n4 = n++;
            this.array[n4] = this.array[n4] ^ pointer.array[n2++];
        }
    }

    public void setXorRange_SelfMove(Pointer pointer, int n) {
        int n2 = pointer.cp;
        for (int i = 0; i < n; ++i) {
            int n3 = this.cp++;
            this.array[n3] = this.array[n3] ^ pointer.array[n2++];
        }
    }

    public void setXorMatrix_NoMove(Pointer pointer, int n, int n2) {
        int n3 = this.cp;
        for (int i = 0; i < n2; ++i) {
            int n4 = n3;
            for (int j = 0; j < n; ++j) {
                int n5 = n4++;
                this.array[n5] = this.array[n5] ^ pointer.array[pointer.cp++];
            }
        }
    }

    public void setXorMatrix(Pointer pointer, int n, int n2) {
        int n3 = this.cp;
        for (int i = 0; i < n2; ++i) {
            int n4 = n3;
            for (int j = 0; j < n; ++j) {
                int n5 = n4++;
                this.array[n5] = this.array[n5] ^ pointer.array[pointer.cp++];
            }
        }
        this.cp += n;
    }

    public void setXorRangeXor(int n, Pointer pointer, int n2, Pointer pointer2, int n3, int n4) {
        n += this.cp;
        n2 += pointer.cp;
        n3 += pointer2.cp;
        for (int i = 0; i < n4; ++i) {
            int n5 = n++;
            this.array[n5] = this.array[n5] ^ (pointer.array[n2++] ^ pointer2.array[n3++]);
        }
    }

    public void setXorRange(int n, PointerUnion pointerUnion, int n2, int n3) {
        n += this.cp;
        n2 += pointerUnion.cp;
        if (pointerUnion.remainder == 0) {
            for (int i = 0; i < n3; ++i) {
                int n4 = n++;
                this.array[n4] = this.array[n4] ^ pointerUnion.array[n2++];
            }
        } else {
            int n5 = pointerUnion.remainder << 3;
            int n6 = 8 - pointerUnion.remainder << 3;
            for (int i = 0; i < n3; ++i) {
                int n7 = n++;
                this.array[n7] = this.array[n7] ^ (pointerUnion.array[n2] >>> n5 | pointerUnion.array[++n2] << n6);
            }
        }
    }

    public void setXorRangeAndMask(Pointer pointer, int n, long l) {
        int n2 = this.cp;
        int n3 = pointer.cp;
        for (int i = 0; i < n; ++i) {
            int n4 = n2++;
            this.array[n4] = this.array[n4] ^ pointer.array[n3++] & l;
        }
    }

    public void setXorRangeAndMaskMove(Pointer pointer, int n, long l) {
        int n2 = this.cp;
        for (int i = 0; i < n; ++i) {
            int n3 = n2++;
            this.array[n3] = this.array[n3] ^ pointer.array[pointer.cp++] & l;
        }
    }

    public void setRangeRotate(int n, Pointer pointer, int n2, int n3, int n4) {
        int n5 = 64 - n4;
        n += this.cp;
        n2 += pointer.cp;
        for (int i = 0; i < n3; ++i) {
            this.array[n++] = pointer.array[n2] >>> n5 ^ pointer.array[++n2] << n4;
        }
    }

    public void move(int n) {
        this.cp += n;
    }

    public void moveIncremental() {
        ++this.cp;
    }

    public long[] getArray() {
        return this.array;
    }

    public int getIndex() {
        return this.cp;
    }

    public void setAnd(int n, long l) {
        int n2 = this.cp + n;
        this.array[n2] = this.array[n2] & l;
    }

    public void setAnd(long l) {
        int n = this.cp;
        this.array[n] = this.array[n] & l;
    }

    public void setClear(int n) {
        this.array[this.cp + n] = 0L;
    }

    public void changeIndex(Pointer pointer) {
        this.array = pointer.array;
        this.cp = pointer.cp;
    }

    public void changeIndex(int n) {
        this.cp = n;
    }

    public void changeIndex(Pointer pointer, int n) {
        this.array = pointer.array;
        this.cp = pointer.cp + n;
    }

    public void setRangeClear(int n, int n2) {
        Arrays.fill(this.array, n += this.cp, n + n2, 0L);
    }

    public int getLength() {
        return this.array.length - this.cp;
    }

    public void copyFrom(Pointer pointer, int n) {
        System.arraycopy(pointer.array, pointer.cp, this.array, this.cp, n);
    }

    public void copyFrom(int n, Pointer pointer, int n2, int n3) {
        System.arraycopy(pointer.array, pointer.cp + n2, this.array, this.cp + n, n3);
    }

    public void set1_gf2n(int n, int n2) {
        int n3 = this.cp + n;
        this.array[n3++] = 1L;
        for (int i = 1; i < n2; ++i) {
            this.array[n3++] = 0L;
        }
    }

    public byte[] toBytes(int n) {
        byte[] byArray = new byte[n];
        for (int i = 0; i < byArray.length; ++i) {
            byArray[i] = (byte)(this.array[this.cp + (i >>> 3)] >>> ((i & 7) << 3));
        }
        return byArray;
    }

    public void indexReset() {
        this.cp = 0;
    }

    public void fillRandom(int n, SecureRandom secureRandom, int n2) {
        byte[] byArray = new byte[n2];
        secureRandom.nextBytes(byArray);
        this.fill(n, byArray, 0, byArray.length);
    }

    public void fill(int n, byte[] byArray, int n2, int n3) {
        int n4;
        int n5 = 0;
        for (n4 = this.cp + n; n4 < this.array.length && n5 + 8 <= n3; ++n4) {
            this.array[n4] = Pack.littleEndianToLong(byArray, n2);
            n2 += 8;
            n5 += 8;
        }
        if (n5 < n3 && n4 < this.array.length) {
            int n6 = 0;
            this.array[n4] = 0L;
            while (n6 < 8 && n5 < n3) {
                int n7 = n4;
                this.array[n7] = this.array[n7] | ((long)byArray[n2] & 0xFFL) << (n6 << 3);
                ++n6;
                ++n2;
                ++n5;
            }
        }
    }

    public void setRangeFromXor(int n, Pointer pointer, int n2, Pointer pointer2, int n3, int n4) {
        n += this.cp;
        n2 += pointer.cp;
        n3 += pointer2.cp;
        for (int i = 0; i < n4; ++i) {
            this.array[n++] = pointer.array[n2++] ^ pointer2.array[n3++];
        }
    }

    public void setRangeFromXor(Pointer pointer, Pointer pointer2, int n) {
        int n2 = this.cp;
        int n3 = pointer.cp;
        int n4 = pointer2.cp;
        for (int i = 0; i < n; ++i) {
            this.array[n2++] = pointer.array[n3++] ^ pointer2.array[n4++];
        }
    }

    public void setRangeFromXorAndMask_xor(Pointer pointer, Pointer pointer2, long l, int n) {
        int n2 = this.cp;
        int n3 = pointer.cp;
        int n4 = pointer2.cp;
        for (int i = 0; i < n; ++i) {
            this.array[n2] = (pointer.array[n3] ^ pointer2.array[n4]) & l;
            int n5 = n3++;
            pointer.array[n5] = pointer.array[n5] ^ this.array[n2];
            int n6 = n4++;
            pointer2.array[n6] = pointer2.array[n6] ^ this.array[n2++];
        }
    }

    public int is0_gf2n(int n, int n2) {
        long l = this.get(n);
        for (int i = 1; i < n2; ++i) {
            l |= this.get(n + i);
        }
        return (int)GeMSSUtils.NORBITS_UINT(l);
    }

    public long getDotProduct(int n, Pointer pointer, int n2, int n3) {
        n += this.cp;
        n2 += pointer.cp;
        long l = this.array[n++] & pointer.array[n2++];
        for (int i = 1; i < n3; ++i) {
            l ^= this.array[n++] & pointer.array[n2++];
        }
        return l;
    }

    public int getD_for_not0_or_plus(int n, int n2) {
        int n3 = 0;
        long l = 0L;
        int n4 = this.cp;
        for (int i = n2; i > 0; --i) {
            long l2 = this.array[n4++];
            for (int j = 1; j < n; ++j) {
                l2 |= this.array[n4++];
            }
            n3 = (int)((long)n3 + (l |= GeMSSUtils.ORBITS_UINT(l2)));
        }
        return n3;
    }

    public int setRange_xi(long l, int n, int n2) {
        int n3 = 0;
        while (n3 < n2) {
            this.array[this.cp + n] = -(l >>> n3 & 1L);
            ++n3;
            ++n;
        }
        return n;
    }

    public int searchDegree(int n, int n2, int n3) {
        while (this.is0_gf2n(n * n3, n3) != 0 && n >= n2) {
            --n;
        }
        return n;
    }

    public void setRangePointerUnion(PointerUnion pointerUnion, int n) {
        if (pointerUnion.remainder == 0) {
            System.arraycopy(pointerUnion.array, pointerUnion.cp, this.array, this.cp, n);
        } else {
            int n2 = 8 - pointerUnion.remainder << 3;
            int n3 = pointerUnion.remainder << 3;
            int n4 = this.cp;
            int n5 = pointerUnion.cp;
            for (int i = 0; i < n; ++i) {
                this.array[n4++] = pointerUnion.array[n5] >>> n3 ^ pointerUnion.array[++n5] << n2;
            }
        }
    }

    public void setRangePointerUnion(PointerUnion pointerUnion, int n, int n2) {
        int n3 = n2 & 0x3F;
        int n4 = 64 - n3;
        int n5 = this.cp;
        int n6 = pointerUnion.cp;
        if (pointerUnion.remainder == 0) {
            for (int i = 0; i < n; ++i) {
                this.array[n5++] = pointerUnion.array[n6] >>> n3 ^ pointerUnion.array[++n6] << n4;
            }
        } else {
            int n7 = pointerUnion.remainder << 3;
            int n8 = 8 - pointerUnion.remainder << 3;
            for (int i = 0; i < n; ++i) {
                this.array[n5++] = (pointerUnion.array[n6] >>> n7 | pointerUnion.array[++n6] << n8) >>> n3 ^ (pointerUnion.array[n6] >>> n7 | pointerUnion.array[n6 + 1] << n8) << n4;
            }
        }
    }

    public void setRangePointerUnion_Check(PointerUnion pointerUnion, int n, int n2) {
        int n3 = n2 & 0x3F;
        int n4 = 64 - n3;
        int n5 = this.cp;
        int n6 = pointerUnion.cp;
        if (pointerUnion.remainder == 0) {
            int n7;
            for (n7 = 0; n7 < n && n6 < pointerUnion.array.length - 1; ++n7) {
                this.array[n5++] = pointerUnion.array[n6] >>> n3 ^ pointerUnion.array[++n6] << n4;
            }
            if (n7 < n) {
                this.array[n5] = pointerUnion.array[n6] >>> n3;
            }
        } else {
            int n8;
            int n9 = pointerUnion.remainder << 3;
            int n10 = 8 - pointerUnion.remainder << 3;
            for (n8 = 0; n8 < n && n6 < pointerUnion.array.length - 2; ++n8) {
                this.array[n5++] = (pointerUnion.array[n6] >>> n9 | pointerUnion.array[++n6] << n10) >>> n3 ^ (pointerUnion.array[n6] >>> n9 | pointerUnion.array[n6 + 1] << n10) << n4;
            }
            if (n8 < n) {
                this.array[n5] = (pointerUnion.array[n6] >>> n9 | pointerUnion.array[++n6] << n10) >>> n3 ^ pointerUnion.array[n6] >>> n9 << n4;
            }
        }
    }

    public int isEqual_nocst_gf2(Pointer pointer, int n) {
        int n2 = pointer.cp;
        int n3 = this.cp;
        for (int i = 0; i < n; ++i) {
            if (this.array[n3++] == pointer.array[n2++]) continue;
            return 0;
        }
        return 1;
    }

    public void swap(Pointer pointer) {
        long[] lArray = pointer.array;
        int n = pointer.cp;
        pointer.array = this.array;
        pointer.cp = this.cp;
        this.array = lArray;
        this.cp = n;
    }
}

