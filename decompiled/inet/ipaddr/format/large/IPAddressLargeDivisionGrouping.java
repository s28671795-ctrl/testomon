/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.large;

import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.InconsistentPrefixException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import java.math.BigInteger;

public class IPAddressLargeDivisionGrouping
extends AddressDivisionGroupingBase
implements IPAddressDivisionSeries {
    private static final long serialVersionUID = 1L;
    private IPAddressNetwork<?, ?, ?, ?, ?> network;

    public IPAddressLargeDivisionGrouping(IPAddressLargeDivision[] divisions, IPAddressNetwork<?, ?, ?, ?, ?> network) {
        super(divisions);
        this.network = network;
        int totalPrefixBits = 0;
        for (int i = 0; i < divisions.length; ++i) {
            IPAddressLargeDivision division = divisions[i];
            Integer divPrefix = division.getDivisionPrefixLength();
            if (divPrefix != null) {
                this.cachedPrefixLength = IPAddressLargeDivisionGrouping.cacheBits(totalPrefixBits + divPrefix);
                ++i;
                while (i < divisions.length) {
                    division = divisions[i];
                    divPrefix = division.getDivisionPrefixLength();
                    if (divPrefix == null || divPrefix != 0) {
                        throw new InconsistentPrefixException((AddressItem)divisions[i - 1], (AddressItem)division, divPrefix);
                    }
                    ++i;
                }
                return;
            }
            totalPrefixBits += division.getBitCount();
        }
        this.cachedPrefixLength = NO_PREFIX_LENGTH;
    }

    @Override
    public IPAddressNetwork<?, ?, ?, ?, ?> getNetwork() {
        return this.network;
    }

    @Override
    public IPAddressLargeDivision getDivision(int index) {
        return (IPAddressLargeDivision)super.getDivision(index);
    }

    @Override
    public boolean containsPrefixBlock(int prefixLength) {
        return IPAddressLargeDivisionGrouping.containsPrefixBlock(this, prefixLength);
    }

    @Override
    public boolean containsSinglePrefixBlock(int prefixLength) throws PrefixLenException {
        return IPAddressLargeDivisionGrouping.containsSinglePrefixBlock(this, prefixLength);
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        return IPAddressLargeDivisionGrouping.getPrefixLengthForSingleBlock(this);
    }

    @Override
    public Integer getPrefixLength() {
        return this.getNetworkPrefixLength();
    }

    @Override
    public Integer getNetworkPrefixLength() {
        Integer ret = this.cachedPrefixLength;
        if (ret == null) {
            Integer result = IPAddressLargeDivisionGrouping.calculatePrefix(this);
            if (result != null) {
                this.cachedPrefixLength = result;
                return this.cachedPrefixLength;
            }
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
            return null;
        }
        if (ret.intValue() == NO_PREFIX_LENGTH.intValue()) {
            return null;
        }
        return ret;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        byte[] bytes = new byte[this.getBitCount() + 7 >> 3];
        int byteCount = bytes.length;
        int divCount = this.getDivisionCount();
        int byteIndex = byteCount - 1;
        int bitIndex = 8;
        block0: for (int k = divCount - 1; k >= 0; --k) {
            IPAddressLargeDivision div = this.getDivision(k);
            BigInteger divValue = low ? div.getValue() : div.getUpperValue();
            for (int divBits = div.getBitCount(); divBits > 0; divBits -= bitIndex) {
                BigInteger bits = divValue.shiftLeft(8 - bitIndex);
                int n = byteIndex--;
                bytes[n] = (byte)(bytes[n] | bits.byteValue());
                divValue = divValue.shiftRight(bitIndex);
                if (divBits < bitIndex) {
                    bitIndex -= divBits;
                    continue block0;
                }
                bitIndex = 8;
            }
        }
        return bytes;
    }

    @Override
    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        return other instanceof IPAddressLargeDivisionGrouping && super.isSameGrouping(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IPAddressLargeDivisionGrouping) {
            IPAddressLargeDivisionGrouping other = (IPAddressLargeDivisionGrouping)o;
            return other.isSameGrouping(this);
        }
        return false;
    }
}

