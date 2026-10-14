/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import java.security.SecureRandom;
import org.bouncycastle.pqc.crypto.gemss.Pointer;

class PointerUnion
extends Pointer {
    protected int remainder;

    public PointerUnion(byte[] byArray) {
        super((byArray.length >> 3) + ((byArray.length & 7) != 0 ? 1 : 0));
        int n = 0;
        for (int i = 0; n < byArray.length && i < this.array.length; ++i) {
            for (int j = 0; j < 8 && n < byArray.length; ++j, ++n) {
                int n2 = i;
                this.array[n2] = this.array[n2] | ((long)byArray[n] & 0xFFL) << (j << 3);
            }
        }
        this.remainder = 0;
    }

    public PointerUnion(int n) {
        super((n >>> 3) + ((n & 7) != 0 ? 1 : 0));
        this.remainder = 0;
    }

    public PointerUnion(PointerUnion pointerUnion) {
        super(pointerUnion);
        this.remainder = pointerUnion.remainder;
    }

    public PointerUnion(Pointer pointer) {
        super(pointer);
        this.remainder = 0;
    }

    public void moveNextBytes(int n) {
        this.remainder += n;
        this.cp += this.remainder >>> 3;
        this.remainder &= 7;
    }

    public void moveNextByte() {
        ++this.remainder;
        this.cp += this.remainder >>> 3;
        this.remainder &= 7;
    }

    @Override
    public long get() {
        if (this.remainder == 0) {
            return this.array[this.cp];
        }
        return this.array[this.cp] >>> (this.remainder << 3) | this.array[this.cp + 1] << (8 - this.remainder << 3);
    }

    public long getWithCheck() {
        if (this.cp >= this.array.length) {
            return 0L;
        }
        if (this.remainder == 0) {
            return this.array[this.cp];
        }
        if (this.cp == this.array.length - 1) {
            return this.array[this.cp] >>> (this.remainder << 3);
        }
        return this.array[this.cp] >>> (this.remainder << 3) | this.array[this.cp + 1] << (8 - this.remainder << 3);
    }

    public long getWithCheck(int n) {
        if ((n += this.cp) >= this.array.length) {
            return 0L;
        }
        if (this.remainder == 0) {
            return this.array[n];
        }
        if (n == this.array.length - 1) {
            return this.array[n] >>> (this.remainder << 3);
        }
        return this.array[n] >>> (this.remainder << 3) | this.array[n + 1] << (8 - this.remainder << 3);
    }

    @Override
    public long get(int n) {
        if (this.remainder == 0) {
            return this.array[this.cp + n];
        }
        return this.array[this.cp + n] >>> (this.remainder << 3) | this.array[this.cp + n + 1] << (8 - this.remainder << 3);
    }

    public byte getByte() {
        return (byte)(this.array[this.cp] >>> (this.remainder << 3));
    }

    public byte getByte(int n) {
        int n2 = this.cp + (n + this.remainder >>> 3);
        int n3 = this.remainder + n & 7;
        return (byte)(this.array[n2] >>> (n3 << 3));
    }

    @Override
    public void setRangeClear(int n, int n2) {
        if (this.remainder == 0) {
            super.setRangeClear(n, n2);
        } else {
            int n3 = this.cp + n;
            this.array[n3] = this.array[n3] & -1L >>> (8 - this.remainder << 3);
            super.setRangeClear(n + 1, n2);
            int n4 = this.cp + n2 + 1;
            this.array[n4] = this.array[n4] & -1L << (this.remainder << 3);
        }
    }

    @Override
    public void setAnd(int n, long l) {
        if (this.remainder == 0) {
            super.setAnd(n, l);
        } else {
            int n2 = this.remainder << 3;
            int n3 = 8 - this.remainder << 3;
            int n4 = this.cp + n;
            this.array[n4] = this.array[n4] & (l << n2 | -1L >>> n3);
            int n5 = this.cp + n + 1;
            this.array[n5] = this.array[n5] & (l >>> n3 | -1L << n2);
        }
    }

    @Override
    public void indexReset() {
        this.cp = 0;
        this.remainder = 0;
    }

    public void setByteIndex(int n) {
        this.remainder = n & 7;
        this.cp = n >>> 3;
    }

    @Override
    public byte[] toBytes(int n) {
        byte[] byArray = new byte[n];
        for (int i = this.remainder; i < byArray.length + this.remainder; ++i) {
            byArray[i - this.remainder] = (byte)(this.array[this.cp + (i >>> 3)] >>> ((i & 7) << 3));
        }
        return byArray;
    }

    public int toBytesMove(byte[] byArray, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            byArray[n++] = (byte)(this.array[this.cp] >>> (this.remainder++ << 3));
            if (this.remainder != 8) continue;
            this.remainder = 0;
            ++this.cp;
        }
        return n;
    }

    @Override
    public void setXor(int n, long l) {
        if (this.remainder == 0) {
            super.setXor(n, l);
        } else {
            int n2 = this.cp + n;
            this.array[n2] = this.array[n2] ^ l << (this.remainder << 3);
            int n3 = this.cp + n + 1;
            this.array[n3] = this.array[n3] ^ l >>> (8 - this.remainder << 3);
        }
    }

    @Override
    public void setXor(long l) {
        if (this.remainder == 0) {
            super.setXor(l);
        } else {
            int n = this.cp;
            this.array[n] = this.array[n] ^ l << (this.remainder << 3);
            int n2 = this.cp + 1;
            this.array[n2] = this.array[n2] ^ l >>> (8 - this.remainder << 3);
        }
    }

    @Override
    public void setXorRangeAndMask(Pointer pointer, int n, long l) {
        if (this.remainder == 0) {
            super.setXorRangeAndMask(pointer, n, l);
            return;
        }
        int n2 = this.cp;
        int n3 = pointer.cp;
        int n4 = this.remainder << 3;
        int n5 = 8 - this.remainder << 3;
        for (int i = 0; i < n; ++i) {
            long l2 = pointer.array[n3++] & l;
            int n6 = n2++;
            this.array[n6] = this.array[n6] ^ l2 << n4;
            int n7 = n2;
            this.array[n7] = this.array[n7] ^ l2 >>> n5;
        }
    }

    public void setXorByte(int n) {
        int n2 = this.cp;
        this.array[n2] = this.array[n2] ^ ((long)n & 0xFFL) << (this.remainder << 3);
    }

    public void setAndByte(int n, long l) {
        int n2 = n + this.remainder + (this.cp << 3);
        int n3 = n2 >>> 3;
        int n4 = n3;
        this.array[n4] = this.array[n4] & ((l & 0xFFL) << ((n2 &= 7) << 3) | 255L << (n2 << 3) ^ 0xFFFFFFFFFFFFFFFFL);
    }

    public void setAndThenXorByte(int n, long l, long l2) {
        int n2 = n + this.remainder + (this.cp << 3);
        int n3 = n2 >>> 3;
        int n4 = n3;
        this.array[n4] = this.array[n4] & ((l & 0xFFL) << ((n2 &= 7) << 3) | 255L << (n2 << 3) ^ 0xFFFFFFFFFFFFFFFFL);
        int n5 = n3;
        this.array[n5] = this.array[n5] ^ (l2 & 0xFFL) << (n2 << 3);
    }

    @Override
    public void set(int n, long l) {
        if (this.remainder == 0) {
            super.setXor(n, l);
        } else {
            int n2 = this.remainder << 3;
            int n3 = 8 - this.remainder << 3;
            this.array[this.cp + n] = l << n2 | this.array[this.cp + n] & -1L >>> n3;
            this.array[this.cp + n + 1] = l >>> n3 | this.array[this.cp + n + 1] & -1L << n2;
        }
    }

    public void setByte(int n) {
        this.array[this.cp] = ((long)n & 0xFFL) << (this.remainder << 3) | this.array[this.cp] & -1L >>> (8 - this.remainder << 3);
    }

    @Override
    public void fill(int n, byte[] byArray, int n2, int n3) {
        if (this.remainder != 0) {
            int n4 = this.cp + n;
            int n5 = this.remainder;
            int n6 = n4;
            this.array[n6] = this.array[n6] & (-1L << (n5 << 3) ^ 0xFFFFFFFFFFFFFFFFL);
            for (int i = 0; n5 < 8 && i < n3; ++i, ++n5) {
                int n7 = n4;
                this.array[n7] = this.array[n7] | ((long)byArray[n2] & 0xFFL) << (n5 << 3);
                ++n2;
            }
            ++n;
            n3 -= 8 - this.remainder;
        }
        super.fill(n, byArray, n2, n3);
    }

    public void fillBytes(int n, byte[] byArray, int n2, int n3) {
        int n4 = n + this.remainder;
        int n5 = this.cp + (n4 >>> 3);
        if ((n4 &= 7) != 0) {
            int n6;
            int n7 = n5;
            this.array[n7] = this.array[n7] & (-1L << (n4 << 3) ^ 0xFFFFFFFFFFFFFFFFL);
            for (n6 = 0; n4 < 8 && n6 < n3; ++n6, ++n4) {
                int n8 = n5;
                this.array[n8] = this.array[n8] | ((long)byArray[n2] & 0xFFL) << (n4 << 3);
                ++n2;
            }
            ++n5;
            n3 -= n6;
        }
        super.fill(n5 - this.cp, byArray, n2, n3);
    }

    public void fillRandomBytes(int n, SecureRandom secureRandom, int n2) {
        byte[] byArray = new byte[n2];
        secureRandom.nextBytes(byArray);
        this.fillBytes(n, byArray, 0, byArray.length);
    }

    public void changeIndex(PointerUnion pointerUnion) {
        this.array = pointerUnion.array;
        this.cp = pointerUnion.cp;
        this.remainder = pointerUnion.remainder;
    }
}

