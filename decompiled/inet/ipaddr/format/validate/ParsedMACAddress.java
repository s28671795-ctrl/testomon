/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.AddressSegment;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.MACAddressString;
import inet.ipaddr.format.validate.AddressParseData;
import inet.ipaddr.format.validate.MACAddressParseData;
import inet.ipaddr.format.validate.MACAddressProvider;
import inet.ipaddr.format.validate.ParsedAddressCreator;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;

class ParsedMACAddress
extends MACAddressParseData
implements MACAddressProvider {
    private static final long serialVersionUID = 4L;
    private final MACAddressString originator;
    private MACAddress address;

    ParsedMACAddress(MACAddressString from, CharSequence addressString) {
        super(addressString);
        this.originator = from;
    }

    private MACAddressNetwork.MACAddressCreator getMACAddressCreator() {
        return this.originator.getValidationOptions().getNetwork().getAddressCreator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MACAddress getAddress() {
        if (this.address == null) {
            ParsedMACAddress parsedMACAddress = this;
            synchronized (parsedMACAddress) {
                if (this.address == null) {
                    this.address = this.createAddress();
                    this.releaseSegmentData();
                }
            }
        }
        return this.address;
    }

    MACAddress createAddress() {
        MACAddressNetwork.MACAddressCreator creator = this.getMACAddressCreator();
        return (MACAddress)creator.createAddressInternal(this.createSection(), (HostIdentifierString)this.originator);
    }

    private MACAddressSection createSection() {
        int initialSegmentCount;
        int finalSegmentCount;
        CharSequence addressString = this.str;
        AddressParseData addressParseData = this.getAddressParseData();
        int actualInitialSegmentCount = addressParseData.getSegmentCount();
        MACAddressNetwork.MACAddressCreator creator = this.getMACAddressCreator();
        MACAddressParseData.MACFormat format = this.getFormat();
        if (format == null) {
            finalSegmentCount = this.isExtended() ? 8 : 6;
            initialSegmentCount = finalSegmentCount;
        } else if (format == MACAddressParseData.MACFormat.DOTTED) {
            int n = initialSegmentCount = this.isExtended() ? 4 : 3;
            finalSegmentCount = actualInitialSegmentCount <= 3 && !this.isExtended() ? 6 : 8;
        } else {
            finalSegmentCount = addressParseData.isSingleSegment() || this.isDoubleSegment() ? (this.isExtended() ? 8 : 6) : (actualInitialSegmentCount <= 6 && !this.isExtended() ? 6 : 8);
            initialSegmentCount = finalSegmentCount;
        }
        int missingCount = initialSegmentCount - actualInitialSegmentCount;
        boolean expandedSegments = missingCount <= 0;
        AddressSegment[] segments = creator.createSegmentArray(finalSegmentCount);
        int normalizedSegmentIndex = 0;
        for (int i = 0; i < actualInitialSegmentCount; ++i) {
            int count;
            long lower = addressParseData.getValue(i, 2);
            long upper = addressParseData.getValue(i, 10);
            if (format == MACAddressParseData.MACFormat.DOTTED) {
                int lowerHalfLower = (int)lower >>> 8;
                int lowerHalfUpper = (int)upper >>> 8;
                int adjustedLower2 = (int)lower & 0xFF;
                int adjustedUpper2 = (int)upper & 0xFF;
                if (lowerHalfLower != lowerHalfUpper && adjustedUpper2 - adjustedLower2 != 255) {
                    throw new IncompatibleAddressException(addressString, "ipaddress.error.invalid.joined.ranges");
                }
                segments[normalizedSegmentIndex++] = this.createSegment(addressString, lowerHalfLower, lowerHalfUpper, false, addressParseData, i, creator);
                segments[normalizedSegmentIndex] = this.createSegment(addressString, adjustedLower2, adjustedUpper2, false, addressParseData, i, creator);
            } else {
                if (addressParseData.isSingleSegment() || this.isDoubleSegment()) {
                    boolean useStringIndicators = true;
                    count = i == actualInitialSegmentCount - 1 ? missingCount : 2;
                    missingCount -= count;
                    boolean isRange = lower != upper;
                    boolean previousAdjustedWasRange = false;
                    while (count >= 0) {
                        int newUpper;
                        int newLower;
                        if (isRange) {
                            int segmentMask = 255;
                            int shift = count << 3;
                            newLower = (int)(lower >>> shift) & segmentMask;
                            newUpper = (int)(upper >>> shift) & segmentMask;
                            if (previousAdjustedWasRange && newUpper - newLower != 255) {
                                throw new IncompatibleAddressException(addressString, "ipaddress.error.invalid.joined.ranges");
                            }
                            boolean bl = previousAdjustedWasRange = newLower != newUpper;
                            if (count == 0 && (long)newLower == lower) {
                                if ((long)newUpper != upper) {
                                    addressParseData.unsetFlag(i, 524288);
                                }
                            } else {
                                useStringIndicators = false;
                            }
                        } else {
                            newLower = newUpper = (int)(lower >> (count << 3)) & 0xFF;
                            if (count != 0 || (long)newLower != lower) {
                                useStringIndicators = false;
                            }
                        }
                        segments[normalizedSegmentIndex] = this.createSegment(addressString, newLower, newUpper, useStringIndicators, addressParseData, i, creator);
                        ++normalizedSegmentIndex;
                        --count;
                    }
                    continue;
                }
                segments[normalizedSegmentIndex] = this.createSegment(addressString, (int)lower, (int)upper, true, addressParseData, i, creator);
            }
            if (!expandedSegments && addressParseData.isWildcard(i)) {
                boolean expandSegments = true;
                for (int j = i + 1; j < actualInitialSegmentCount; ++j) {
                    if (!addressParseData.isWildcard(j)) continue;
                    expandSegments = false;
                    break;
                }
                if (expandSegments) {
                    expandedSegments = true;
                    count = missingCount;
                    while (count-- > 0) {
                        if (format == MACAddressParseData.MACFormat.DOTTED) {
                            MACAddressSegment seg = this.createSegment(addressString, 0, 255, false, addressParseData, i, creator);
                            segments[++normalizedSegmentIndex] = seg;
                            segments[++normalizedSegmentIndex] = seg;
                            continue;
                        }
                        segments[++normalizedSegmentIndex] = this.createSegment(addressString, 0, 255, false, addressParseData, i, creator);
                    }
                }
            }
            ++normalizedSegmentIndex;
        }
        MACAddressNetwork.MACAddressCreator addressCreator = creator;
        MACAddressSection result = (MACAddressSection)addressCreator.createSectionInternal(segments);
        return result;
    }

    private <S extends MACAddressSegment> S createSegment(CharSequence addressString, int val, int upperVal, boolean useFlags, AddressParseData parseData, int parsedSegIndex, ParsedAddressCreator<?, ?, ?, S> creator) {
        if (val != upperVal) {
            return this.createRangeSegment(addressString, val, upperVal, useFlags, parseData, parsedSegIndex, creator);
        }
        MACAddressSegment result = !useFlags ? (MACAddressSegment)creator.createSegment(val, val, null) : (MACAddressSegment)creator.createSegmentInternal(val, null, addressString, val, parseData.getFlag(parsedSegIndex, 262144), parseData.getIndex(parsedSegIndex, 6), parseData.getIndex(parsedSegIndex, 7));
        return (S)result;
    }

    private <S extends MACAddressSegment> S createRangeSegment(CharSequence addressString, int lower, int upper, boolean useFlags, AddressParseData parseData, int parsedSegIndex, ParsedAddressCreator<?, ?, ?, S> creator) {
        MACAddressSegment result = !useFlags ? (MACAddressSegment)creator.createSegment(lower, upper, null) : (MACAddressSegment)creator.createRangeSegmentInternal(lower, upper, null, addressString, lower, upper, parseData.getFlag(parsedSegIndex, 262144), parseData.getFlag(parsedSegIndex, 524288), parseData.getIndex(parsedSegIndex, 6), parseData.getIndex(parsedSegIndex, 7), parseData.getIndex(parsedSegIndex, 15));
        return (S)result;
    }
}

