/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.HostName;
import inet.ipaddr.HostNameException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.MACAddressString;
import inet.ipaddr.format.validate.IPAddressProvider;
import inet.ipaddr.format.validate.MACAddressProvider;
import inet.ipaddr.format.validate.ParsedHost;

public interface HostIdentifierStringValidator {
    public static final int MAX_PREFIX = 128;
    public static final int MAX_PREFIX_CHARS = Integer.toString(128).length();
    public static final String SMTP_IPV6_IDENTIFIER = "IPv6:";
    public static final char IPvFUTURE = 'v';

    public ParsedHost validateHost(HostName var1) throws HostNameException;

    public IPAddressProvider validateAddress(IPAddressString var1) throws AddressStringException;

    public MACAddressProvider validateAddress(MACAddressString var1) throws AddressStringException;

    public int validatePrefix(CharSequence var1, IPAddress.IPVersion var2) throws AddressStringException;
}

