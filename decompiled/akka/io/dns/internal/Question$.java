/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.RecordClass;
import akka.io.dns.RecordType;
import akka.io.dns.internal.DomainName$;
import akka.io.dns.internal.Question;
import akka.io.dns.internal.RecordClassSerializer$;
import akka.io.dns.internal.RecordTypeSerializer$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class Question$
implements Serializable {
    public static final Question$ MODULE$ = new Question$();

    public Question parse(ByteIterator it, ByteString msg) {
        String name = DomainName$.MODULE$.parse(it, msg);
        RecordType qType = RecordTypeSerializer$.MODULE$.parse(it);
        RecordClass qClass = RecordClassSerializer$.MODULE$.parse(it);
        return new Question(name, qType, qClass);
    }

    public Question apply(String name, RecordType qType, RecordClass qClass) {
        return new Question(name, qType, qClass);
    }

    public Option<Tuple3<String, RecordType, RecordClass>> unapply(Question x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<String, RecordType, RecordClass>>(new Tuple3<String, RecordType, RecordClass>(x$0.name(), x$0.qType(), x$0.qClass()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Question$.class);
    }

    private Question$() {
    }
}

