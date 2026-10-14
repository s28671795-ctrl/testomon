/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.Serializable;
import java.util.LinkedList;
import org.apache.activemq.util.BitArray;

public class BitArrayBin
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final LinkedList<BitArray> list;
    private int maxNumberOfArrays;
    private int firstIndex = -1;
    private long lastInOrderBit = -1L;
    private long longFirstIndex = -1L;

    public BitArrayBin(int windowSize) {
        this.maxNumberOfArrays = (windowSize + 1) / 64 + 1;
        this.maxNumberOfArrays = Math.max(this.maxNumberOfArrays, 1);
        this.list = new LinkedList();
        for (int i = 0; i < this.maxNumberOfArrays; ++i) {
            this.list.add(null);
        }
    }

    public boolean setBit(long index, boolean value) {
        int offset;
        boolean answer = false;
        BitArray ba = this.getBitArray(index);
        if (ba != null && (offset = this.getOffset(index)) >= 0) {
            answer = ba.set(offset, value);
        }
        return answer;
    }

    public boolean isInOrder(long index) {
        boolean result = false;
        result = this.lastInOrderBit == -1L ? true : this.lastInOrderBit + 1L == index;
        this.lastInOrderBit = index;
        return result;
    }

    public boolean getBit(long index) {
        boolean answer = index >= this.longFirstIndex;
        BitArray ba = this.getBitArray(index);
        if (ba != null) {
            int offset = this.getOffset(index);
            if (offset >= 0) {
                answer = ba.get(offset);
                return answer;
            }
        } else {
            answer = true;
        }
        return answer;
    }

    private BitArray getBitArray(long index) {
        int bin = this.getBin(index);
        BitArray answer = null;
        if (bin >= 0) {
            if (bin >= this.maxNumberOfArrays) {
                for (int overShoot = bin - this.maxNumberOfArrays + 1; overShoot > 0; --overShoot) {
                    this.list.removeFirst();
                    this.longFirstIndex += 64L;
                    this.list.add(new BitArray());
                }
                bin = this.maxNumberOfArrays - 1;
            }
            if ((answer = this.list.get(bin)) == null) {
                answer = new BitArray();
                this.list.set(bin, answer);
            }
        }
        return answer;
    }

    private int getBin(long index) {
        int answer = 0;
        if (this.longFirstIndex < 0L) {
            this.longFirstIndex = index - index % 64L;
        } else if (this.longFirstIndex >= 0L) {
            answer = (int)((index - this.longFirstIndex) / 64L);
        }
        return answer;
    }

    private int getOffset(long index) {
        int answer = 0;
        if (this.longFirstIndex >= 0L) {
            answer = (int)(index - this.longFirstIndex - (long)(64 * this.getBin(index)));
        }
        return answer;
    }

    public long getLastSetIndex() {
        long result = -1L;
        if (this.longFirstIndex >= 0L) {
            result = this.longFirstIndex;
            BitArray last = null;
            for (int lastBitArrayIndex = this.maxNumberOfArrays - 1; lastBitArrayIndex >= 0; --lastBitArrayIndex) {
                last = this.list.get(lastBitArrayIndex);
                if (last == null) continue;
                result += (long)(last.length() - 1);
                result += (long)lastBitArrayIndex * 64L;
                break;
            }
        }
        return result;
    }
}

