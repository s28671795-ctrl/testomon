/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Hashtable;
import javax.naming.directory.InitialDirContext;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.LinearSeqOps;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.util.Try;
import scala.util.Try$;
import scala.util.matching.Regex;

public final class DnsSettings$ {
    public static final DnsSettings$ MODULE$ = new DnsSettings$();
    private static final Regex inetSocketAddress = StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(.*?)(?::(\\d+))?"));

    private final int DnsFallbackPort() {
        return 53;
    }

    private Regex inetSocketAddress() {
        return inetSocketAddress;
    }

    @InternalApi
    public InetSocketAddress parseNameserverAddress(String str) {
        Option<List<String>> option;
        String string = str;
        if (string != null && !(option = this.inetSocketAddress().unapplySeq(string)).isEmpty() && option.get() != null && option.get().lengthCompare(2) == 0) {
            String host2 = (String)((LinearSeqOps)option.get()).apply(0);
            String port = (String)((LinearSeqOps)option.get()).apply(1);
            return new InetSocketAddress(host2, BoxesRunTime.unboxToInt(Option$.MODULE$.apply(port).fold(() -> 53, (Function1<String, Object> & Serializable)x$4 -> BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x$4))))));
        }
        throw new IllegalArgumentException(new StringBuilder(28).append("Unparseable address string: ").append(string).toString());
    }

    public Try<List<InetSocketAddress>> getDefaultNameServers(ExtendedActorSystem system) {
        return DnsSettings$.getNameserversUsingJNDI$1().orElse((Function0<Try> & Serializable)() -> DnsSettings$.getNameserversUsingReflection$1(system));
    }

    private static final Try asInetSocketAddress$1(String server) {
        return Try$.MODULE$.apply((Function0<InetSocketAddress> & Serializable)() -> {
            int n;
            URI uri = new URI(server);
            String host2 = uri.getHost();
            int n2 = uri.getPort();
            switch (n2) {
                case -1: {
                    n = 53;
                    break;
                }
                default: {
                    n = n2;
                    break;
                }
            }
            int port = n;
            return new InetSocketAddress(host2, port);
        });
    }

    private static final Try getNameserversUsingJNDI$1() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        env.put("java.naming.provider.url", "dns://");
        return Try$.MODULE$.apply((Function0<List> & Serializable)() -> {
            InitialDirContext ctx = new InitialDirContext(env);
            String dnsUrls = (String)ctx.getEnvironment().get("java.naming.provider.url");
            if (dnsUrls != null && !dnsUrls.isEmpty()) {
                String[] servers = dnsUrls.split(" ");
                return Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.flatMap$extension(Predef$.MODULE$.refArrayOps(servers), (Function1<String, Option> & Serializable)server -> DnsSettings$.asInetSocketAddress$1(server).toOption(), ClassTag$.MODULE$.apply(InetSocketAddress.class))).toList();
            }
            return Nil$.MODULE$;
        });
    }

    private static final Try getNameserversUsingReflection$1(ExtendedActorSystem system$1) {
        return system$1.dynamicAccess().getClassFor("sun.net.dns.ResolverConfiguration", ClassTag$.MODULE$.Any()).flatMap((Function1<Class, Try> & Serializable)c -> Try$.MODULE$.apply((Function0<List> & Serializable)() -> {
            Object instance;
            Method open = c.getMethod("open", new Class[0]);
            Method nameservers = c.getMethod("nameservers", new Class[0]);
            java.util.List ns = (java.util.List)nameservers.invoke(instance = open.invoke(null, new Object[0]), new Object[0]);
            if (ns.isEmpty()) {
                throw new IllegalStateException("Empty nameservers list discovered using reflection. Consider configuring default nameservers manually!");
            }
            List res = package$JavaConverters$.MODULE$.ListHasAsScala(ns).asScala().toList();
            return res.flatMap((Function1<String, Option> & Serializable)s2 -> DnsSettings$.asInetSocketAddress$1(s2).toOption());
        }));
    }

    private DnsSettings$() {
    }
}

