/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.format.standard.AddressDivisionGrouping;

public class ParsedAddressGrouping {
    private static final Integer[] cache = new Integer[Short.MAX_VALUE];

    public static int getNetworkSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        if (bytesPerSegment > 1) {
            if (bytesPerSegment == 2) {
                return networkPrefixLength - 1 >> 4;
            }
            return (networkPrefixLength - 1) / bitsPerSegment;
        }
        return networkPrefixLength - 1 >> 3;
    }

    public static int getHostSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        if (bytesPerSegment > 1) {
            if (bytesPerSegment == 2) {
                return networkPrefixLength >> 4;
            }
            return networkPrefixLength / bitsPerSegment;
        }
        return networkPrefixLength >> 3;
    }

    public static Integer getSegmentPrefixLength(int bitsPerSegment, Integer prefixLength, int segmentIndex) {
        if (prefixLength != null) {
            return ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
        }
        return null;
    }

    public static Integer getPrefixedSegmentPrefixLength(int bitsPerSegment, int prefixLength, int segmentIndex) {
        int decrement = bitsPerSegment == 8 ? segmentIndex << 3 : (bitsPerSegment == 16 ? segmentIndex << 4 : segmentIndex * bitsPerSegment);
        return ParsedAddressGrouping.getDivisionPrefixLength(bitsPerSegment, prefixLength - decrement);
    }

    public static Integer getDivisionPrefixLength(int divisionBits, int divisionPrefixedBits) {
        if (divisionPrefixedBits <= 0) {
            return ParsedAddressGrouping.cache(0);
        }
        if (divisionPrefixedBits <= divisionBits) {
            return ParsedAddressGrouping.cache(divisionPrefixedBits);
        }
        return null;
    }

    public static Integer getNetworkPrefixLength(int bitsPerSegment, int segmentPrefixLength, int segmentIndex) {
        int increment = bitsPerSegment == 8 ? segmentIndex << 3 : (bitsPerSegment == 16 ? segmentIndex << 4 : segmentIndex * bitsPerSegment);
        return ParsedAddressGrouping.cache(increment + segmentPrefixLength);
    }

    public static boolean isPrefixSubnet(AddressDivisionGrouping.DivisionValueProvider lowerValueProvider, AddressDivisionGrouping.DivisionValueProvider lowerExtendedValueProvider, AddressDivisionGrouping.DivisionValueProvider upperValueProvider, AddressDivisionGrouping.DivisionValueProvider upperExtendedValueProvider, AddressDivisionGrouping.DivisionLengthProvider bitLengthProvider, int divisionCount, Integer networkPrefixLength, AddressNetwork.PrefixConfiguration prefixConfiguration, boolean fullRangeOnly) {
        if (networkPrefixLength == null || prefixConfiguration.prefixedSubnetsAreExplicit()) {
            return false;
        }
        if (networkPrefixLength < 0) {
            networkPrefixLength = 0;
        }
        int totalBitLength = 0;
        block0: for (int i = 0; i < divisionCount; ++i) {
            int extendedPrefixLength;
            boolean hasExtendedPrefixLength;
            int extendedDivBitLength;
            boolean isExtended;
            boolean hasPrefLen;
            int divBitLength = bitLengthProvider.getLength(i);
            Integer divisionPrefLength = ParsedAddressGrouping.getDivisionPrefixLength(divBitLength, networkPrefixLength - totalBitLength);
            if (divBitLength == 0) continue;
            if (divisionPrefLength == null) {
                totalBitLength += divBitLength;
                continue;
            }
            int divisionPrefixLength = divisionPrefLength;
            boolean bl = hasPrefLen = divisionPrefixLength != divBitLength;
            if (hasPrefLen) {
                isExtended = divBitLength > 64;
                if (isExtended) {
                    extendedDivBitLength = divBitLength - 64;
                    divBitLength = 64;
                    hasExtendedPrefixLength = divisionPrefixLength < extendedDivBitLength;
                    if (hasExtendedPrefixLength) {
                        extendedPrefixLength = divisionPrefixLength;
                        divisionPrefixLength = 0;
                    } else {
                        isExtended = false;
                        extendedPrefixLength = extendedDivBitLength;
                        divisionPrefixLength -= extendedDivBitLength;
                    }
                } else {
                    extendedDivBitLength = 0;
                    extendedPrefixLength = 0;
                    hasExtendedPrefixLength = false;
                }
            } else {
                extendedDivBitLength = 0;
                extendedPrefixLength = 0;
                isExtended = false;
                hasExtendedPrefixLength = false;
            }
            while (true) {
                long lower;
                int upperZeros;
                int upperOnes;
                long hostMask;
                long maxVal;
                if (isExtended) {
                    long extendedLower = lowerExtendedValueProvider.getValue(i);
                    if (extendedPrefixLength == 0) {
                        if (extendedLower != 0L) {
                            return false;
                        }
                        long extendedUpper = upperExtendedValueProvider.getValue(i);
                        if (fullRangeOnly) {
                            maxVal = -1L >>> 64 - extendedDivBitLength;
                            if (extendedUpper != maxVal) {
                                return false;
                            }
                        } else {
                            int upperOnes2 = Long.numberOfTrailingZeros(extendedUpper ^ 0xFFFFFFFFFFFFFFFFL);
                            if (upperOnes2 > 0) {
                                if (upperOnes2 < 64 && extendedUpper >>> upperOnes2 != 0L) {
                                    return false;
                                }
                                fullRangeOnly = true;
                            } else if (extendedUpper != 0L) {
                                return false;
                            }
                        }
                    } else if (hasExtendedPrefixLength) {
                        int divHostBits = extendedDivBitLength - extendedPrefixLength;
                        if (fullRangeOnly) {
                            hostMask = -1L << divHostBits ^ 0xFFFFFFFFFFFFFFFFL;
                            if ((hostMask & extendedLower) != 0L) {
                                return false;
                            }
                            long extendedUpper = upperExtendedValueProvider.getValue(i);
                            if ((hostMask & extendedUpper) != hostMask) {
                                return false;
                            }
                        } else {
                            int lowerZeros = Long.numberOfTrailingZeros(extendedLower);
                            if (lowerZeros < divHostBits) {
                                return false;
                            }
                            long extendedUpper = upperExtendedValueProvider.getValue(i);
                            upperOnes = Long.numberOfTrailingZeros(extendedUpper ^ 0xFFFFFFFFFFFFFFFFL);
                            if (upperOnes < divHostBits) {
                                upperZeros = Long.numberOfTrailingZeros(extendedUpper >>> upperOnes);
                                if (upperOnes + upperZeros < divHostBits) {
                                    return false;
                                }
                                fullRangeOnly = upperOnes > 0;
                            } else {
                                fullRangeOnly = true;
                            }
                        }
                    }
                }
                if (divisionPrefixLength == 0) {
                    lower = lowerValueProvider.getValue(i);
                    if (lower != 0L) {
                        return false;
                    }
                    long upper = upperValueProvider.getValue(i);
                    if (fullRangeOnly) {
                        maxVal = -1L >>> 64 - divBitLength;
                        if (upper != maxVal) {
                            return false;
                        }
                    } else {
                        int upperOnes3 = Long.numberOfTrailingZeros(upper ^ 0xFFFFFFFFFFFFFFFFL);
                        if (upperOnes3 > 0) {
                            if (upperOnes3 < 64 && upper >>> upperOnes3 != 0L) {
                                return false;
                            }
                            fullRangeOnly = true;
                        } else if (upper != 0L) {
                            return false;
                        }
                    }
                } else if (hasPrefLen) {
                    lower = lowerValueProvider.getValue(i);
                    int divHostBits = divBitLength - divisionPrefixLength;
                    if (fullRangeOnly) {
                        hostMask = -1L << divHostBits ^ 0xFFFFFFFFFFFFFFFFL;
                        if ((hostMask & lower) != 0L) {
                            return false;
                        }
                        long upper = upperValueProvider.getValue(i);
                        if ((hostMask & upper) != hostMask) {
                            return false;
                        }
                    } else {
                        int lowerZeros = Long.numberOfTrailingZeros(lower);
                        if (lowerZeros < divHostBits) {
                            return false;
                        }
                        long upper = upperValueProvider.getValue(i);
                        upperOnes = Long.numberOfTrailingZeros(upper ^ 0xFFFFFFFFFFFFFFFFL);
                        if (upperOnes < divHostBits) {
                            upperZeros = Long.numberOfTrailingZeros(upper >>> upperOnes);
                            if (upperOnes + upperZeros < divHostBits) {
                                return false;
                            }
                            fullRangeOnly = upperOnes > 0;
                        } else {
                            fullRangeOnly = true;
                        }
                    }
                }
                if (++i == divisionCount) break block0;
                divBitLength = bitLengthProvider.getLength(i);
                isExtended = divBitLength > 64;
                hasExtendedPrefixLength = isExtended;
                if (isExtended) {
                    extendedDivBitLength = divBitLength - 64;
                    divBitLength = 64;
                } else {
                    extendedDivBitLength = 0;
                }
                divisionPrefixLength = 0;
                extendedPrefixLength = 0;
            }
        }
        return true;
    }

    public static boolean isPrefixSubnet(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int segmentCount, int bytesPerSegment, int bitsPerSegment, int segmentMaxValue, Integer networkPrefixLength, AddressNetwork.PrefixConfiguration prefixConfiguration, boolean fullRangeOnly) {
        if (networkPrefixLength == null || prefixConfiguration.prefixedSubnetsAreExplicit()) {
            return false;
        }
        if (networkPrefixLength < 0) {
            networkPrefixLength = 0;
        } else {
            int totalBitCount;
            int n = bitsPerSegment == 8 ? segmentCount << 3 : (totalBitCount = bitsPerSegment == 16 ? segmentCount << 4 : segmentCount * bitsPerSegment);
            if (networkPrefixLength >= totalBitCount) {
                return false;
            }
        }
        if (prefixConfiguration.allPrefixedAddressesAreSubnets()) {
            return true;
        }
        int prefixedSegment = ParsedAddressGrouping.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
        int i = prefixedSegment;
        if (i < segmentCount) {
            int segmentPrefixLength = ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
            do {
                int lower = lowerValueProvider.getValue(i);
                if (segmentPrefixLength == 0) {
                    if (lower != 0) {
                        return false;
                    }
                    int upper = upperValueProvider.getValue(i);
                    if (fullRangeOnly) {
                        if (upper != segmentMaxValue) {
                            return false;
                        }
                    } else {
                        int upperOnes = Integer.numberOfTrailingZeros(~upper);
                        if (upperOnes > 0) {
                            if (upper >>> upperOnes != 0) {
                                return false;
                            }
                            fullRangeOnly = true;
                        } else if (upper != 0) {
                            return false;
                        }
                    }
                } else if (segmentPrefixLength < bitsPerSegment) {
                    int upper;
                    int segHostBits = bitsPerSegment - segmentPrefixLength;
                    if (fullRangeOnly) {
                        int hostMask = ~(-1 << segHostBits);
                        if ((hostMask & lower) != 0) {
                            return false;
                        }
                        upper = upperValueProvider.getValue(i);
                        if ((hostMask & upper) != hostMask) {
                            return false;
                        }
                    } else {
                        int lowerZeros = Integer.numberOfTrailingZeros(lower);
                        if (lowerZeros < segHostBits) {
                            return false;
                        }
                        upper = upperValueProvider.getValue(i);
                        int upperOnes = Integer.numberOfTrailingZeros(~upper);
                        if (upperOnes < segHostBits) {
                            int upperZeros = Integer.numberOfTrailingZeros((upper | -1 << bitsPerSegment) >>> upperOnes);
                            if (upperOnes + upperZeros < segHostBits) {
                                return false;
                            }
                            fullRangeOnly = upperOnes > 0;
                        } else {
                            fullRangeOnly = true;
                        }
                    }
                }
                segmentPrefixLength = 0;
            } while (++i < segmentCount);
        }
        return true;
    }

    public static Integer cache(int i) {
        if (i >= 0 && i < cache.length) {
            Integer result = cache[i];
            if (result == null) {
                result = ParsedAddressGrouping.cache[i] = Integer.valueOf(i);
            }
            return result;
        }
        return i;
    }

    static {
        for (int i = 0; i <= 128; ++i) {
            ParsedAddressGrouping.cache[i] = i;
        }
    }
}

