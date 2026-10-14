/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Ip$;
import akka.io.dns.DnsProtocol$Srv$;

public final class DnsProtocol$ {
    public static final DnsProtocol$ MODULE$ = new DnsProtocol$();

    public DnsProtocol.RequestType ipRequestType(boolean ipv4, boolean ipv6) {
        return new DnsProtocol.Ip(ipv4, ipv6);
    }

    public DnsProtocol.RequestType ipRequestType() {
        return new DnsProtocol.Ip(true, true);
    }

    public DnsProtocol.RequestType srvRequestType() {
        return DnsProtocol$Srv$.MODULE$;
    }

    public DnsProtocol.Resolve resolve(String name) {
        return new DnsProtocol.Resolve(name, new DnsProtocol.Ip(DnsProtocol$Ip$.MODULE$.apply$default$1(), DnsProtocol$Ip$.MODULE$.apply$default$2()));
    }

    public DnsProtocol.Resolve resolve(String name, DnsProtocol.RequestType requestType) {
        return new DnsProtocol.Resolve(name, requestType);
    }

    private DnsProtocol$() {
    }
}

