/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.IPAddress;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv6.IPv6Address;

public interface IPAddressConverter
extends IPv6Address.IPv6AddressConverter,
IPv4Address.IPv4AddressConverter {
    public boolean isIPv4Convertible(IPAddress var1);

    public boolean isIPv6Convertible(IPAddress var1);

    public static class DefaultAddressConverter
    implements IPAddressConverter {
        @Override
        public IPv4Address toIPv4(IPAddress address) {
            if (this.isIPv4Convertible(address)) {
                return address.isIPv4() ? address.toIPv4() : address.toIPv6().getEmbeddedIPv4Address();
            }
            return null;
        }

        @Override
        public IPv6Address toIPv6(IPAddress address) {
            return address.isIPv6() ? address.toIPv6() : address.toIPv4().getIPv4MappedAddress();
        }

        @Override
        public boolean isIPv4Convertible(IPAddress address) {
            return address.isIPv4() || address.toIPv6().isIPv4Mapped();
        }

        @Override
        public boolean isIPv6Convertible(IPAddress address) {
            return true;
        }
    }
}

