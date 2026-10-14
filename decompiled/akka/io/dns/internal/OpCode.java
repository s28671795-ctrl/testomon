/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.internal.OpCode$;
import scala.Enumeration;
import scala.Enumeration$ValueOrdering$;
import scala.Enumeration$ValueSet$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u:aAC\u0006\t\u0002-\u0019bAB\u000b\f\u0011\u0003Ya\u0003C\u0003\u001e\u0003\u0011\u0005q\u0004C\u0004!\u0003\t\u0007I\u0011A\u0011\t\r\u0019\n\u0001\u0015!\u0003#\u0011\u001d9\u0013A1A\u0005\u0002\u0005Ba\u0001K\u0001!\u0002\u0013\u0011\u0003bB\u0015\u0002\u0005\u0004%\t!\t\u0005\u0007U\u0005\u0001\u000b\u0011\u0002\u0012\t\u000f-\n\u0011\u0011!C\u0005Y\u00051q\n]\"pI\u0016T!\u0001D\u0007\u0002\u0011%tG/\u001a:oC2T!AD\b\u0002\u0007\u0011t7O\u0003\u0002\u0011#\u0005\u0011\u0011n\u001c\u0006\u0002%\u0005!\u0011m[6b!\t!\u0012!D\u0001\f\u0005\u0019y\u0005oQ8eKN\u0011\u0011a\u0006\t\u00031mi\u0011!\u0007\u0006\u00025\u0005)1oY1mC&\u0011A$\u0007\u0002\f\u000b:,X.\u001a:bi&|g.\u0001\u0004=S:LGOP\u0002\u0001)\u0005\u0019\u0012!B)V\u000bJKV#\u0001\u0012\u0011\u0005\r\"S\"A\u0001\n\u0005\u0015Z\"!\u0002,bYV,\u0017AB)V\u000bJK\u0006%\u0001\u0004J#V+%+W\u0001\b\u0013F+VIU-!\u0003\u0019\u0019F+\u0011+V'\u000691\u000bV!U+N\u0003\u0013\u0001D<sSR,'+\u001a9mC\u000e,G#A\u0017\u0011\u00059\u001aT\"A\u0018\u000b\u0005A\n\u0014\u0001\u00027b]\u001eT\u0011AM\u0001\u0005U\u00064\u0018-\u0003\u00025_\t1qJ\u00196fGRD#!\u0001\u001c\u0011\u0005]RT\"\u0001\u001d\u000b\u0005e\n\u0012AC1o]>$\u0018\r^5p]&\u00111\b\u000f\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001m\u0001")
@InternalApi
public final class OpCode {
    public static Enumeration.Value STATUS() {
        return OpCode$.MODULE$.STATUS();
    }

    public static Enumeration.Value IQUERY() {
        return OpCode$.MODULE$.IQUERY();
    }

    public static Enumeration.Value QUERY() {
        return OpCode$.MODULE$.QUERY();
    }

    public static Enumeration$ValueSet$ ValueSet() {
        return OpCode$.MODULE$.ValueSet();
    }

    public static Enumeration$ValueOrdering$ ValueOrdering() {
        return OpCode$.MODULE$.ValueOrdering();
    }

    public static Enumeration.Value withName(String s2) {
        return OpCode$.MODULE$.withName(s2);
    }

    public static Enumeration.Value apply(int x) {
        return OpCode$.MODULE$.apply(x);
    }

    public static int maxId() {
        return OpCode$.MODULE$.maxId();
    }

    public static Enumeration.ValueSet values() {
        return OpCode$.MODULE$.values();
    }

    public static String toString() {
        return OpCode$.MODULE$.toString();
    }
}

