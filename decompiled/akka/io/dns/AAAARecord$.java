/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.AAAARecord;
import akka.io.dns.CachePolicy;
import akka.util.ByteIterator;
import java.io.Serializable;
import java.net.Inet6Address;
import java.net.InetAddress;
import scala.Array$;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.reflect.ClassTag$;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class AAAARecord$
implements Serializable {
    public static final AAAARecord$ MODULE$ = new AAAARecord$();

    @InternalApi
    public AAAARecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it) {
        byte[] address = (byte[])Array$.MODULE$.ofDim(16, ClassTag$.MODULE$.Byte());
        it.getBytes(address);
        return new AAAARecord(name, ttl, (Inet6Address)InetAddress.getByAddress(address));
    }

    public AAAARecord apply(String name, CachePolicy.Ttl ttl, Inet6Address ip2) {
        return new AAAARecord(name, ttl, ip2);
    }

    public Option<Tuple3<String, CachePolicy.Ttl, Inet6Address>> unapply(AAAARecord x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<String, CachePolicy.Ttl, Inet6Address>>(new Tuple3<String, CachePolicy.Ttl, Inet6Address>(x$0.name(), x$0.ttl(), x$0.ip()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AAAARecord$.class);
    }

    private AAAARecord$() {
    }
}

