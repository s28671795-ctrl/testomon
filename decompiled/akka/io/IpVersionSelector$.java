/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.annotation.InternalApi;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import scala.Function0;
import scala.Option;

@InternalApi
public final class IpVersionSelector$ {
    public static final IpVersionSelector$ MODULE$ = new IpVersionSelector$();

    @InternalApi
    public Option<InetAddress> getInetAddress(Option<Inet4Address> ipv4, Option<Inet6Address> ipv6) {
        String string = System.getProperty("java.net.preferIPv6Addresses");
        switch (string == null ? 0 : string.hashCode()) {
            case 3569038: {
                if (!"true".equals(string)) break;
                return ipv6.orElse((Function0<Option> & Serializable)() -> ipv4);
            }
        }
        return ipv4.orElse((Function0<Option> & Serializable)() -> ipv6);
    }

    private IpVersionSelector$() {
    }
}

