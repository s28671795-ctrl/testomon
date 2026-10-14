/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.io.dns.RecordType;
import akka.util.OptionVal$;
import java.io.Serializable;
import scala.Array$;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class RecordType$
implements Serializable {
    public static final RecordType$ MODULE$ = new RecordType$();
    private static final RecordType[] lookupTable = (RecordType[])Array$.MODULE$.ofDim(256, ClassTag$.MODULE$.apply(RecordType.class));
    private static final RecordType A = MODULE$.register(new RecordType(1, "A"));
    private static final RecordType NS = MODULE$.register(new RecordType(2, "NS"));
    private static final RecordType MD = MODULE$.register(new RecordType(3, "MD"));
    private static final RecordType MF = MODULE$.register(new RecordType(4, "MF"));
    private static final RecordType CNAME = MODULE$.register(new RecordType(5, "CNAME"));
    private static final RecordType SOA = MODULE$.register(new RecordType(6, "SOA"));
    private static final RecordType MB = MODULE$.register(new RecordType(7, "MB"));
    private static final RecordType MG = MODULE$.register(new RecordType(8, "MG"));
    private static final RecordType MR = MODULE$.register(new RecordType(9, "MR"));
    private static final RecordType NULL = MODULE$.register(new RecordType(10, "NULL"));
    private static final RecordType WKS = MODULE$.register(new RecordType(11, "WKS"));
    private static final RecordType PTR = MODULE$.register(new RecordType(12, "PTR"));
    private static final RecordType HINFO = MODULE$.register(new RecordType(13, "HINFO"));
    private static final RecordType MINFO = MODULE$.register(new RecordType(14, "MINFO"));
    private static final RecordType MX = MODULE$.register(new RecordType(15, "MX"));
    private static final RecordType TXT = MODULE$.register(new RecordType(16, "TXT"));
    private static final RecordType AAAA = MODULE$.register(new RecordType(28, "AAAA"));
    private static final RecordType SRV = MODULE$.register(new RecordType(33, "SRV"));
    private static final RecordType AXFR = MODULE$.register(new RecordType(252, "AXFR"));
    private static final RecordType MAILB = MODULE$.register(new RecordType(253, "MAILB"));
    private static final RecordType MAILA = MODULE$.register(new RecordType(254, "MAILA"));
    private static final RecordType WILDCARD = MODULE$.register(new RecordType(255, "WILDCARD"));

    private final RecordType[] lookupTable() {
        return lookupTable;
    }

    public RecordType lookup(int code) {
        return this.lookupTable()[code];
    }

    public RecordType apply(short id) {
        if (id < 1 || id > 255) {
            OptionVal$.MODULE$.None();
            return null;
        }
        return OptionVal$.MODULE$.apply(this.lookup(id));
    }

    private RecordType register(RecordType t2) {
        this.lookupTable()[t2.code()] = t2;
        return t2;
    }

    public final RecordType A() {
        return A;
    }

    public final RecordType NS() {
        return NS;
    }

    public final RecordType MD() {
        return MD;
    }

    public final RecordType MF() {
        return MF;
    }

    public final RecordType CNAME() {
        return CNAME;
    }

    public final RecordType SOA() {
        return SOA;
    }

    public final RecordType MB() {
        return MB;
    }

    public final RecordType MG() {
        return MG;
    }

    public final RecordType MR() {
        return MR;
    }

    public final RecordType NULL() {
        return NULL;
    }

    public final RecordType WKS() {
        return WKS;
    }

    public final RecordType PTR() {
        return PTR;
    }

    public final RecordType HINFO() {
        return HINFO;
    }

    public final RecordType MINFO() {
        return MINFO;
    }

    public final RecordType MX() {
        return MX;
    }

    public final RecordType TXT() {
        return TXT;
    }

    public final RecordType AAAA() {
        return AAAA;
    }

    public final RecordType SRV() {
        return SRV;
    }

    public final RecordType AXFR() {
        return AXFR;
    }

    public final RecordType MAILB() {
        return MAILB;
    }

    public final RecordType MAILA() {
        return MAILA;
    }

    public final RecordType WILDCARD() {
        return WILDCARD;
    }

    public RecordType apply(short code, String name) {
        return new RecordType(code, name);
    }

    public Option<Tuple2<Object, String>> unapply(RecordType x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, String>>(new Tuple2<Short, String>(BoxesRunTime.boxToShort(x$0.code()), x$0.name()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RecordType$.class);
    }

    private RecordType$() {
    }
}

