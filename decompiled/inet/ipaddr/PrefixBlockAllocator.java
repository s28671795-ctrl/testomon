/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.format.AddressItem;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PrefixBlockAllocator<E extends IPAddress> {
    private static final IPAddress[] emptyBlocks = new IPAddress[0];
    private IPAddress.IPVersion version;
    private ArrayDeque<E>[] blocks;
    int reservedCount;
    int totalBlockCount;

    public int getBlockCount() {
        return this.totalBlockCount;
    }

    public IPAddress.IPVersion getVersion() {
        return this.version;
    }

    public BigInteger getTotalCount() {
        if (this.getBlockCount() == 0) {
            return BigInteger.ZERO;
        }
        BigInteger result = BigInteger.ZERO;
        if (this.blocks == null) {
            return result;
        }
        IPAddress.IPVersion version = this.version;
        for (int i = this.blocks.length - 1; i >= 0; --i) {
            int blockCount;
            ArrayDeque<E> rowBlocks = this.blocks[i];
            if (rowBlocks == null || (blockCount = rowBlocks.size()) == 0) continue;
            BigInteger size = AddressItem.getBlockSize(IPAddress.getBitCount(version) - i);
            size = size.multiply(BigInteger.valueOf(blockCount));
            result = result.add(size);
        }
        return result;
    }

    public void setReserved(int reservedCount) {
        this.reservedCount = reservedCount;
    }

    public int getReserved() {
        return this.reservedCount;
    }

    void insertBlocks(E[] newBlocks) {
        for (int i = 0; i < newBlocks.length; ++i) {
            E newBlock = newBlocks[i];
            int prefLen = ((Address)newBlock).getPrefixLength();
            ArrayDeque<E> existing = this.blocks[prefLen];
            if (existing == null) {
                existing = new ArrayDeque();
                this.blocks[prefLen] = existing;
            }
            existing.addLast(newBlock);
            ++this.totalBlockCount;
        }
    }

    public void addAvailable(E ... newBlocks) {
        if (newBlocks.length == 0) {
            return;
        }
        IPAddress.IPVersion version = this.version;
        for (int i = 0; i < newBlocks.length; ++i) {
            E block = newBlocks[i];
            if (version == null) {
                this.version = version = ((IPAddress)block).getIPVersion();
                continue;
            }
            if (version.equals((Object)((IPAddress)block).getIPVersion())) continue;
            throw new IncompatibleAddressException((AddressItem)block, "ipaddress.error.typeMismatch");
        }
        if (this.blocks == null) {
            int size = IPAddress.getBitCount(version) + 1;
            this.blocks = new ArrayDeque[size];
        } else if (this.totalBlockCount > 0) {
            ArrayList<E> newList = new ArrayList<E>(newBlocks.length + this.totalBlockCount);
            for (int i = 0; i < this.blocks.length; ++i) {
                if (this.blocks[i] == null) continue;
                newList.addAll(this.blocks[i]);
                this.blocks[i].clear();
            }
            newList.addAll(Arrays.asList(newBlocks));
            newBlocks = newList.toArray(new IPAddress[newList.size()]);
        }
        newBlocks = ((IPAddress)newBlocks[0]).mergeToPrefixBlocks((IPAddress[])newBlocks);
        this.insertBlocks((IPAddress[])newBlocks);
    }

    public E[] getAvailable() {
        if (this.totalBlockCount == 0) {
            return emptyBlocks;
        }
        ArrayList<E> newList = new ArrayList<E>(this.totalBlockCount);
        for (int i = 0; i < this.blocks.length; ++i) {
            if (this.blocks[i] == null) continue;
            newList.addAll(this.blocks[i]);
        }
        return newList.toArray(new IPAddress[newList.size()]);
    }

    public E allocateBitLength(int bitLength) {
        int i;
        if (this.totalBlockCount == 0) {
            return null;
        }
        int newPrefixBitCount = IPAddress.getBitCount(this.version) - bitLength;
        Address block = null;
        for (i = newPrefixBitCount; i >= 0; --i) {
            ArrayDeque<E> blockRow = this.blocks[i];
            if (blockRow == null || blockRow.size() <= 0) continue;
            block = (IPAddress)blockRow.removeFirst();
            --this.totalBlockCount;
            break;
        }
        if (block == null || !block.isMultiple() || i == newPrefixBitCount) {
            return (E)block;
        }
        IPAddress adjustedBlock = ((IPAddress)block).setPrefixLength(newPrefixBitCount, false);
        Iterator<? extends IPAddress> blockIterator = adjustedBlock.prefixBlockIterator();
        IPAddress result = blockIterator.next();
        IPAddressSeqRange range = blockIterator.next().getLower().spanWithRange(((IPAddress)block).getUpper());
        this.insertBlocks(range.spanWithPrefixBlocks());
        return (E)result;
    }

    public E allocateSize(long sizeRequired) {
        int bitsRequired;
        if (this.reservedCount < 0) {
            long adjustment = -this.reservedCount;
            if (adjustment >= sizeRequired) {
                return null;
            }
            bitsRequired = AddressItem.getBitsForCount(sizeRequired -= adjustment);
        } else if (Long.MAX_VALUE - (long)this.reservedCount < sizeRequired) {
            long extra = sizeRequired - (Long.MAX_VALUE - (long)this.reservedCount) - 1L;
            bitsRequired = extra == 0L ? 63 : AddressItem.getBitsForCount(extra) + 63;
        } else {
            Integer bRequired = AddressItem.getBitsForCount(sizeRequired += (long)this.reservedCount);
            if (bRequired == null) {
                return null;
            }
            bitsRequired = bRequired;
        }
        return this.allocateBitLength(bitsRequired);
    }

    public AllocatedBlock<E>[] allocateSizes(long ... blockSizes) {
        ArrayList<Long> sizes = new ArrayList<Long>(blockSizes.length);
        for (int i = 0; i < blockSizes.length; ++i) {
            sizes.add(blockSizes[i]);
        }
        sizes.sort((one, two) -> {
            long diff = two - one;
            if (diff < 0L) {
                return -1;
            }
            if (diff > 0L) {
                return 1;
            }
            return 0;
        });
        ArrayList<AllocatedBlock<E>> result = new ArrayList<AllocatedBlock<E>>();
        for (int i = 0; i < sizes.size(); ++i) {
            long blockSize = (Long)sizes.get(i);
            if (this.reservedCount < 0 && (long)(-this.reservedCount) >= blockSize) continue;
            E allocated = this.allocateSize(blockSize);
            if (allocated == null) {
                return null;
            }
            result.add(new AllocatedBlock<E>(allocated, BigInteger.valueOf(blockSize), this.reservedCount));
        }
        return result.toArray(new AllocatedBlock[result.size()]);
    }

    public AllocatedBlock<E>[] allocateBitLengths(int ... bitLengths) {
        ArrayList<Integer> lengths = new ArrayList<Integer>(bitLengths.length);
        for (int i = 0; i < bitLengths.length; ++i) {
            lengths.add(bitLengths[i]);
        }
        lengths.sort((one, two) -> {
            long diff = two - one;
            if (diff < 0L) {
                return -1;
            }
            if (diff > 0L) {
                return 1;
            }
            return 0;
        });
        ArrayList<AllocatedBlock<E>> result = new ArrayList<AllocatedBlock<E>>();
        for (int i = 0; i < lengths.size(); ++i) {
            int bitLength = (Integer)lengths.get(i);
            E allocated = this.allocateBitLength(bitLength);
            if (allocated == null) {
                return null;
            }
            BigInteger blockSize = AddressItem.getBlockSize(bitLength);
            result.add(new AllocatedBlock<E>(allocated, blockSize, 0));
        }
        return result.toArray(new AllocatedBlock[result.size()]);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        IPAddress.IPVersion version = this.version;
        boolean hasBlocks = false;
        builder.append("available blocks:\n");
        if (this.blocks != null) {
            for (int i = this.blocks.length - 1; i >= 0; --i) {
                ArrayDeque<E> row = this.blocks[i];
                if (row == null || row.size() == 0) continue;
                int blockCount = row.size();
                BigInteger size = AddressItem.getBlockSize(IPAddress.getBitCount(version) - i);
                builder.append(blockCount);
                if (blockCount == 1) {
                    builder.append(" block");
                } else {
                    builder.append(" blocks");
                }
                builder.append(" with prefix length ").append(i).append(" size ").append(size).append("\n");
                hasBlocks = true;
            }
        }
        if (!hasBlocks) {
            builder.append("none\n");
        }
        return builder.toString();
    }

    public static class AllocatedBlock<E extends IPAddress> {
        public final BigInteger blockSize;
        public final E block;
        public final int reservedCount;

        AllocatedBlock(E block, BigInteger blockSize, int reservedCount) {
            this.block = block;
            this.blockSize = blockSize;
            this.reservedCount = reservedCount;
        }

        public BigInteger getCount() {
            return ((Address)this.block).getCount();
        }

        public String toString() {
            if (this.reservedCount > 0) {
                return this.block + " for " + this.blockSize + " hosts and " + this.reservedCount + " reserved addresses";
            }
            return this.block + " for " + this.blockSize + " hosts";
        }
    }
}

