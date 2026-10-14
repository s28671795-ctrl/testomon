/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.internal.DnsClient;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.util.matching.Regex;

@InternalApi
public final class AsyncDnsResolver$ {
    public static final AsyncDnsResolver$ MODULE$ = new AsyncDnsResolver$();
    private static final Regex ipv4Address = StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$"));
    private static final Regex ipv6Address = StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$"));
    private static final Future<DnsClient.Answer> akka$io$dns$internal$AsyncDnsResolver$$Empty = Future$.MODULE$.successful(new DnsClient.Answer(-1, (Seq)Seq$.MODULE$.empty(), (Seq)Seq$.MODULE$.empty()));

    private Regex ipv4Address() {
        return ipv4Address;
    }

    private Regex ipv6Address() {
        return ipv6Address;
    }

    public boolean isIpv4Address(String name) {
        return this.ipv4Address().findAllMatchIn(name).nonEmpty();
    }

    public boolean isIpv6Address(String name) {
        return this.ipv6Address().findAllMatchIn(name).nonEmpty();
    }

    public boolean akka$io$dns$internal$AsyncDnsResolver$$isInetAddress(String name) {
        return this.isIpv4Address(name) || this.isIpv6Address(name);
    }

    public Future<DnsClient.Answer> akka$io$dns$internal$AsyncDnsResolver$$Empty() {
        return akka$io$dns$internal$AsyncDnsResolver$$Empty;
    }

    private AsyncDnsResolver$() {
    }
}

