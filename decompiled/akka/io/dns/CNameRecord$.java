/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.CNameRecord;
import akka.io.dns.CachePolicy;
import akka.io.dns.internal.DomainName$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class CNameRecord$
implements Serializable {
    public static final CNameRecord$ MODULE$ = new CNameRecord$();

    @InternalApi
    public CNameRecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it, ByteString msg) {
        return new CNameRecord(name, ttl, DomainName$.MODULE$.parse(it, msg));
    }

    public CNameRecord apply(String name, CachePolicy.Ttl ttl, String canonicalName) {
        return new CNameRecord(name, ttl, canonicalName);
    }

    public Option<Tuple3<String, CachePolicy.Ttl, String>> unapply(CNameRecord x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<String, CachePolicy.Ttl, String>>(new Tuple3<String, CachePolicy.Ttl, String>(x$0.name(), x$0.ttl(), x$0.canonicalName()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CNameRecord$.class);
    }

    private CNameRecord$() {
    }
}

