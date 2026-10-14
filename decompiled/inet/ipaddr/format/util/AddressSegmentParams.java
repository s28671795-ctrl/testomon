/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.standard.AddressDivisionGrouping;

public interface AddressSegmentParams {
    public AddressDivisionGrouping.StringOptions.Wildcards getWildcards();

    public boolean preferWildcards();

    public int getLeadingZeros(int var1);

    public String getSegmentStrPrefix();

    public int getRadix();

    public boolean isUppercase();

    public boolean isSplitDigits();

    public Character getSplitDigitSeparator();

    public boolean isReverseSplitDigits();
}

