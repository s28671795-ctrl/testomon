/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressComparator;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.HostIdentifierException;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.NetworkMismatchException;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Address
implements AddressSegmentSeries {
    private static final long serialVersionUID = 4L;
    public static final String HEX_PREFIX = "0x";
    public static final String OCTAL_PREFIX = "0";
    public static final char RANGE_SEPARATOR = '-';
    public static final String RANGE_SEPARATOR_STR = String.valueOf('-');
    public static final char ALTERNATIVE_RANGE_SEPARATOR = '\u00bb';
    public static final String ALTERNATIVE_RANGE_SEPARATOR_STR = String.valueOf('\u00bb');
    public static final char SEGMENT_WILDCARD = '*';
    public static final String SEGMENT_WILDCARD_STR = String.valueOf('*');
    public static final String ALTERNATIVE_SEGMENT_WILDCARD_STR = "\u00bf";
    public static final char SEGMENT_SQL_WILDCARD = '%';
    public static final String SEGMENT_SQL_WILDCARD_STR = String.valueOf('%');
    public static final char SEGMENT_SQL_SINGLE_WILDCARD = '_';
    public static final String SEGMENT_SQL_SINGLE_WILDCARD_STR = String.valueOf('_');
    public static final AddressComparator DEFAULT_ADDRESS_COMPARATOR = new AddressComparator.CountComparator(true);
    public static final AddressComparator ADDRESS_LOW_VALUE_COMPARATOR = new AddressComparator.ValueComparator(true, false);
    public static final AddressComparator ADDRESS_HIGH_VALUE_COMPARATOR = new AddressComparator.ValueComparator(true, true);
    private static MACAddressNetwork macNetwork;
    private static IPv6AddressNetwork ipv6Network;
    private static IPv4AddressNetwork ipv4Network;
    final AddressSection addressSection;
    protected HostIdentifierString fromString;

    protected Address(AddressSection section) {
        this.addressSection = section;
        if (!this.getNetwork().isCompatible(this.addressSection.getNetwork())) {
            throw new NetworkMismatchException(this.addressSection);
        }
    }

    protected Address(Function<Address, AddressSection> supplier) {
        this.addressSection = supplier.apply(this);
        if (!this.getNetwork().isCompatible(this.addressSection.getNetwork())) {
            throw new NetworkMismatchException(this.addressSection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static IPv6AddressNetwork defaultIpv6Network() {
        if (ipv6Network != null) return ipv6Network;
        Class<Address> clazz = Address.class;
        synchronized (Address.class) {
            if (ipv6Network != null) return ipv6Network;
            ipv6Network = new IPv6AddressNetwork();
            // ** MonitorExit[var0] (shouldn't be in output)
            return ipv6Network;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static IPv4AddressNetwork defaultIpv4Network() {
        if (ipv4Network != null) return ipv4Network;
        Class<Address> clazz = Address.class;
        synchronized (Address.class) {
            if (ipv4Network != null) return ipv4Network;
            ipv4Network = new IPv4AddressNetwork();
            // ** MonitorExit[var0] (shouldn't be in output)
            return ipv4Network;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MACAddressNetwork defaultMACNetwork() {
        if (macNetwork != null) return macNetwork;
        Class<Address> clazz = Address.class;
        synchronized (Address.class) {
            if (macNetwork != null) return macNetwork;
            macNetwork = new MACAddressNetwork();
            // ** MonitorExit[var0] (shouldn't be in output)
            return macNetwork;
        }
    }

    protected static String getMessage(String key) {
        return HostIdentifierException.getMessage(key);
    }

    @Override
    public int getSegmentCount() {
        return this.getSection().getSegmentCount();
    }

    @Override
    public int getDivisionCount() {
        return this.getSection().getDivisionCount();
    }

    @Override
    public int getBitCount() {
        return this.getSection().getBitCount();
    }

    @Override
    public int getByteCount() {
        return this.getSection().getByteCount();
    }

    @Override
    public AddressSection getSection() {
        return this.addressSection;
    }

    @Override
    public void getSegments(AddressSegment[] segs) {
        this.getSection().getSegments(segs);
    }

    @Override
    public void getSegments(int start2, int end, AddressSegment[] segs, int index) {
        this.getSection().getSegments(start2, end, segs, index);
    }

    public abstract Iterable<? extends Address> getIterable();

    public abstract Iterator<? extends Address> iterator();

    @Override
    public abstract AddressComponentSpliterator<? extends Address> spliterator();

    public abstract Stream<? extends Address> stream();

    public abstract Iterator<? extends Address> prefixIterator();

    public abstract AddressComponentSpliterator<? extends Address> prefixSpliterator();

    public abstract Stream<? extends Address> prefixStream();

    public abstract Iterator<? extends Address> prefixBlockIterator();

    public abstract AddressComponentSpliterator<? extends Address> prefixBlockSpliterator();

    public abstract Stream<? extends Address> prefixBlockStream();

    @Override
    public abstract Address increment(long var1) throws AddressValueException;

    @Override
    public abstract Address incrementBoundary(long var1) throws AddressValueException;

    @Override
    public abstract Address getLower();

    @Override
    public abstract Address getUpper();

    public boolean isIPAddress() {
        return false;
    }

    public boolean isMACAddress() {
        return false;
    }

    public IPAddress toIPAddress() {
        return null;
    }

    public MACAddress toMACAddress() {
        return null;
    }

    @Override
    public boolean isMultiple() {
        return this.getSection().isMultiple();
    }

    @Override
    public boolean isPrefixed() {
        return this.getSection().isPrefixed();
    }

    @Override
    public Integer getPrefixLength() {
        return this.getSection().getPrefixLength();
    }

    @Override
    public int getMinPrefixLengthForBlock() {
        return this.getSection().getMinPrefixLengthForBlock();
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        return this.getSection().getPrefixLengthForSingleBlock();
    }

    public abstract boolean isMulticast();

    @Override
    public BigInteger getCount() {
        return this.getSection().getCount();
    }

    @Override
    public BigInteger getPrefixCount(int prefixLength) {
        return this.getSection().getPrefixCount(prefixLength);
    }

    @Override
    public BigInteger getPrefixCount() {
        return this.getSection().getPrefixCount();
    }

    @Override
    public BigInteger getBlockCount(int segmentCount) {
        return this.getSection().getBlockCount(segmentCount);
    }

    @Override
    public byte[] getBytes() {
        return this.getSection().getBytes();
    }

    @Override
    public byte[] getBytes(byte[] bytes) {
        return this.getSection().getBytes(bytes);
    }

    @Override
    public byte[] getBytes(byte[] bytes, int index) {
        return this.getSection().getBytes(bytes, index);
    }

    @Override
    public byte[] getUpperBytes() {
        return this.getSection().getUpperBytes();
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes) {
        return this.getSection().getUpperBytes(bytes);
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes, int index) {
        return this.getSection().getUpperBytes(bytes, index);
    }

    @Override
    public BigInteger getValue() {
        return this.getSection().getValue();
    }

    @Override
    public BigInteger getUpperValue() {
        return this.getSection().getUpperValue();
    }

    @Override
    public boolean isZero() {
        return this.getSection().isZero();
    }

    @Override
    public boolean includesZero() {
        return this.getSection().includesZero();
    }

    @Override
    public boolean isMax() {
        return this.getSection().isMax();
    }

    @Override
    public boolean includesMax() {
        return this.getSection().includesMax();
    }

    @Override
    public boolean isFullRange() {
        return this.getSection().isFullRange();
    }

    public abstract boolean isLocal();

    public int hashCode() {
        return this.getSection().hashCode();
    }

    protected abstract boolean isFromSameString(HostIdentifierString var1);

    public boolean isSameAddress(Address other) {
        return other == this || this.getSection().equals(other.getSection());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Address) {
            Address other = (Address)o;
            if (this.isFromSameString(other.fromString)) {
                return true;
            }
            return this.isSameAddress(other);
        }
        return false;
    }

    public boolean prefixEquals(Address other) {
        if (other == this) {
            return true;
        }
        return this.getSection().prefixEquals(other.getSection());
    }

    public boolean contains(Address other) {
        if (other == this) {
            return true;
        }
        return this.getSection().contains(other.getSection());
    }

    @Override
    public boolean isSequential() {
        return this.getSection().isSequential();
    }

    public HostIdentifierString toAddressString() {
        return this.fromString;
    }

    @Override
    public String toHexString(boolean with0xPrefix) throws IncompatibleAddressException {
        return this.getSection().toHexString(with0xPrefix);
    }

    @Override
    public String toNormalizedString() {
        return this.getSection().toNormalizedString();
    }

    @Override
    public String toCanonicalString() {
        return this.getSection().toCanonicalString();
    }

    @Override
    public String toCompressedString() {
        return this.getSection().toCompressedString();
    }

    public String toString() {
        return this.toCanonicalString();
    }

    @Override
    public String[] getDivisionStrings() {
        return this.getSection().getDivisionStrings();
    }

    @Override
    public String[] getSegmentStrings() {
        return this.getSection().getSegmentStrings();
    }

    @Override
    public abstract Address reverseSegments();

    @Override
    public abstract Address reverseBits(boolean var1);

    @Override
    public abstract Address reverseBytes();

    @Override
    public abstract Address reverseBytesPerSegment();

    @Override
    public boolean isPrefixBlock() {
        return this.getSection().isPrefixBlock();
    }

    @Override
    public boolean containsPrefixBlock(int prefixLength) {
        return this.getSection().containsPrefixBlock(prefixLength);
    }

    @Override
    public boolean isSinglePrefixBlock() {
        return this.getSection().isSinglePrefixBlock();
    }

    @Override
    public boolean containsSinglePrefixBlock(int prefixLength) {
        return this.getSection().containsSinglePrefixBlock(prefixLength);
    }

    @Override
    public abstract Address toPrefixBlock();

    @Override
    @Deprecated
    public abstract Address removePrefixLength();

    @Override
    public abstract Address withoutPrefixLength();

    @Override
    @Deprecated
    public abstract Address removePrefixLength(boolean var1);

    @Override
    public abstract Address adjustPrefixBySegment(boolean var1);

    @Override
    public abstract Address adjustPrefixBySegment(boolean var1, boolean var2);

    @Override
    public abstract Address adjustPrefixLength(int var1);

    @Override
    public abstract Address adjustPrefixLength(int var1, boolean var2);

    @Override
    public abstract Address setPrefixLength(int var1);

    @Override
    public abstract Address setPrefixLength(int var1, boolean var2);

    @Override
    @Deprecated
    public abstract Address applyPrefixLength(int var1);

    public static boolean matchUnordered(Address[] addrs1, Address[] addrs2) {
        boolean result;
        boolean sameLen;
        int len1 = addrs1 == null ? 0 : addrs1.length;
        int len2 = addrs2 == null ? 0 : addrs2.length;
        boolean bl = sameLen = len1 == len2;
        if (len1 == 0 || len2 == 0) {
            result = sameLen;
        } else if (len1 == 1 && sameLen) {
            result = addrs1[0].equals(addrs2[0]);
        } else if (len1 == 2 && sameLen) {
            if (addrs1[0].equals(addrs2[0])) {
                result = addrs1[1].equals(addrs2[1]);
            } else {
                result = addrs1[0].equals(addrs2[1]);
                if (result) {
                    result = addrs1[1].equals(addrs2[0]);
                }
            }
        } else {
            result = Objects.equals(Address.asSet(addrs1), Address.asSet(addrs2));
        }
        return result;
    }

    private static HashSet<Address> asSet(Address[] addrs) {
        int addrLen = addrs.length;
        if (addrLen > 0) {
            HashSet<Address> result = new HashSet<Address>();
            for (int i = 0; i < addrs.length; ++i) {
                Address addr = addrs[i];
                result.add(addr);
            }
            return result;
        }
        return null;
    }

    public static boolean matchOrdered(Address[] addrs1, Address[] addrs2) {
        int len2;
        int len1 = addrs1 == null ? 0 : addrs1.length;
        int n = len2 = addrs2 == null ? 0 : addrs2.length;
        if (len1 != len2) {
            return false;
        }
        for (int i = 0; i < addrs1.length; ++i) {
            if (addrs1[i].equals(addrs2[i])) continue;
            return false;
        }
        return true;
    }

    @FunctionalInterface
    public static interface SegmentValueProvider {
        public int getValue(int var1);
    }

    public static interface AddressValueProvider {
        public int getSegmentCount();

        public SegmentValueProvider getValues();

        default public SegmentValueProvider getUpperValues() {
            return this.getValues();
        }
    }
}

