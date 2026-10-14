/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.CachePolicy;
import akka.io.dns.SRVRecord;
import akka.io.dns.internal.DomainName$;
import akka.io.dns.internal.package$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple6;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class SRVRecord$
implements Serializable {
    public static final SRVRecord$ MODULE$ = new SRVRecord$();

    @InternalApi
    public SRVRecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it, ByteString msg) {
        int priority = it.getShort(package$.MODULE$.networkByteOrder()) & 0xFFFF;
        int weight = it.getShort(package$.MODULE$.networkByteOrder()) & 0xFFFF;
        int port = it.getShort(package$.MODULE$.networkByteOrder()) & 0xFFFF;
        return new SRVRecord(name, ttl, priority, weight, port, DomainName$.MODULE$.parse(it, msg));
    }

    public SRVRecord apply(String name, CachePolicy.Ttl ttl, int priority, int weight, int port, String target) {
        return new SRVRecord(name, ttl, priority, weight, port, target);
    }

    public Option<Tuple6<String, CachePolicy.Ttl, Object, Object, Object, String>> unapply(SRVRecord x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple6<String, CachePolicy.Ttl, Object, Object, Object, String>>(new Tuple6<String, CachePolicy.Ttl, Integer, Integer, Integer, String>(x$0.name(), x$0.ttl(), BoxesRunTime.boxToInteger(x$0.priority()), BoxesRunTime.boxToInteger(x$0.weight()), BoxesRunTime.boxToInteger(x$0.port()), x$0.target()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SRVRecord$.class);
    }

    private SRVRecord$() {
    }
}

