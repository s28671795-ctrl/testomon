/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.hostnameverifier;

import java.net.IDN;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import nl.altindag.ssl.exception.GenericHostnameVerifierException;
import nl.altindag.ssl.hostnameverifier.HostnameCommon;

final class Hostnames {
    private Hostnames() {
    }

    static String toCanonicalHost(String host2) {
        String result = host2;
        if (host2.contains(":")) {
            if (host2.startsWith("[") && host2.endsWith("]")) {
                result = host2.substring(1, host2.length() - 1);
            }
            try {
                InetAddress inetAddress = InetAddress.getByName(result);
                return inetAddress.getHostAddress();
            }
            catch (UnknownHostException e) {
                throw new GenericHostnameVerifierException(e);
            }
        }
        try {
            result = IDN.toASCII(host2).toLowerCase(Locale.US);
            if (result.isEmpty()) {
                return null;
            }
            if (HostnameCommon.containsInvalidHostnameAsciiCodes(result)) {
                return null;
            }
            if (HostnameCommon.containsInvalidLabelLengths(result)) {
                return null;
            }
            return result;
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}

