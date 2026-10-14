/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.CachePolicy;
import akka.io.dns.UnknownRecord;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class UnknownRecord$
implements Serializable {
    public static final UnknownRecord$ MODULE$ = new UnknownRecord$();

    @InternalApi
    public UnknownRecord parseBody(String name, CachePolicy.Ttl ttl, short recType, short recClass, short length, ByteIterator it) {
        return new UnknownRecord(name, ttl, recType, recClass, it.toByteString());
    }

    public UnknownRecord apply(String name, CachePolicy.Ttl ttl, short recType, short recClass, ByteString data) {
        return new UnknownRecord(name, ttl, recType, recClass, data);
    }

    public Option<Tuple5<String, CachePolicy.Ttl, Object, Object, ByteString>> unapply(UnknownRecord x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<String, CachePolicy.Ttl, Object, Object, ByteString>>(new Tuple5<String, CachePolicy.Ttl, Short, Short, ByteString>(x$0.name(), x$0.ttl(), BoxesRunTime.boxToShort(x$0.recType()), BoxesRunTime.boxToShort(x$0.recClass()), x$0.data()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UnknownRecord$.class);
    }

    private UnknownRecord$() {
    }
}

