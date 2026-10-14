/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUtil {
    public static String getLocalHostName() throws UnknownHostException {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException uhe) {
            int colon2;
            String host2 = uhe.getMessage();
            if (host2 != null && (colon2 = host2.indexOf(58)) > 0) {
                return host2.substring(0, colon2);
            }
            throw uhe;
        }
    }
}

