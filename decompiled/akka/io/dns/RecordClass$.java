/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.io.dns.RecordClass;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class RecordClass$
implements Serializable {
    public static final RecordClass$ MODULE$ = new RecordClass$();
    private static final RecordClass IN = new RecordClass(1, "IN");
    private static final RecordClass CS = new RecordClass(2, "CS");
    private static final RecordClass CH = new RecordClass(3, "CH");
    private static final RecordClass HS = new RecordClass(4, "HS");
    private static final RecordClass WILDCARD = new RecordClass(255, "WILDCARD");

    public RecordClass IN() {
        return IN;
    }

    public RecordClass CS() {
        return CS;
    }

    public RecordClass CH() {
        return CH;
    }

    public RecordClass HS() {
        return HS;
    }

    public RecordClass WILDCARD() {
        return WILDCARD;
    }

    public RecordClass apply(short code, String name) {
        return new RecordClass(code, name);
    }

    public Option<Tuple2<Object, String>> unapply(RecordClass x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, String>>(new Tuple2<Short, String>(BoxesRunTime.boxToShort(x$0.code()), x$0.name()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RecordClass$.class);
    }

    private RecordClass$() {
    }
}

