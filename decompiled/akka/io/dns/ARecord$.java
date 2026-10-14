/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.ARecord;
import akka.io.dns.CachePolicy;
import akka.util.ByteIterator;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import scala.Array$;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.reflect.ClassTag$;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class ARecord$
implements Serializable {
    public static final ARecord$ MODULE$ = new ARecord$();

    public ARecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it) {
        byte[] address = (byte[])Array$.MODULE$.ofDim(4, ClassTag$.MODULE$.Byte());
        it.getBytes(address);
        return new ARecord(name, ttl, (Inet4Address)InetAddress.getByAddress(address));
    }

    public ARecord apply(String name, CachePolicy.Ttl ttl, InetAddress ip2) {
        return new ARecord(name, ttl, ip2);
    }

    public Option<Tuple3<String, CachePolicy.Ttl, InetAddress>> unapply(ARecord x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<String, CachePolicy.Ttl, InetAddress>>(new Tuple3<String, CachePolicy.Ttl, InetAddress>(x$0.name(), x$0.ttl(), x$0.ip()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ARecord$.class);
    }

    private ARecord$() {
    }
}

