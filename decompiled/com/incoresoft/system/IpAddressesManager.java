/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpAddressesManager {
    public List<String> getIPv4Addresses() {
        try {
            ArrayList<String> ips = new ArrayList<String>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!(address instanceof Inet4Address)) continue;
                    ips.add(address.getHostAddress());
                }
            }
            return ips;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

