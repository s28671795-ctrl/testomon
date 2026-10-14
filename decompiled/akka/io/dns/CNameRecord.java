/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.CNameRecord$;
import akka.io.dns.CachePolicy;
import akka.io.dns.RecordClass$;
import akka.io.dns.RecordType$;
import akka.io.dns.ResourceRecord;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mf\u0001\u0002\u000f\u001e\u0005\u0012B\u0001b\u000f\u0001\u0003\u0016\u0004%\t\u0005\u0010\u0005\n\u000b\u0002\u0011\t\u0012)A\u0005{\u0019C\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0005\u0013\u0005\n!\u0002\u0011\t\u0012)A\u0005\u0013FC\u0001B\u0015\u0001\u0003\u0016\u0004%\t\u0001\u0010\u0005\t'\u0002\u0011\t\u0012)A\u0005{!)A\u000b\u0001C\u0001+\"9!\fAA\u0001\n\u0003Y\u0006bB0\u0001#\u0003%\t\u0001\u0019\u0005\bW\u0002\t\n\u0011\"\u0001m\u0011\u001dq\u0007!%A\u0005\u0002\u0001Dqa\u001c\u0001\u0002\u0002\u0013\u0005\u0003\u000fC\u0004y\u0001\u0005\u0005I\u0011A=\t\u000fu\u0004\u0011\u0011!C\u0001}\"I\u0011\u0011\u0002\u0001\u0002\u0002\u0013\u0005\u00131\u0002\u0005\n\u00033\u0001\u0011\u0011!C\u0001\u00037A\u0011\"!\n\u0001\u0003\u0003%\t%a\n\t\u0013\u0005-\u0002!!A\u0005B\u00055\u0002\"CA\u0018\u0001\u0005\u0005I\u0011IA\u0019\u0011%\t\u0019\u0004AA\u0001\n\u0003\n)d\u0002\u0005\u0002:uA\t!HA\u001e\r\u001daR\u0004#\u0001\u001e\u0003{Aa\u0001\u0016\f\u0005\u0002\u00055\u0003bBA(-\u0011\u0005\u0011\u0011\u000b\u0005\n\u0003\u001f3\u0012\u0011!CA\u0003#C\u0011\"!'\u0017\u0003\u0003%\t)a'\t\u0013\u00055f#!A\u0005\n\u0005=&aC\"OC6,'+Z2pe\u0012T!AH\u0010\u0002\u0007\u0011t7O\u0003\u0002!C\u0005\u0011\u0011n\u001c\u0006\u0002E\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001A\u0013*_A\u0011aeJ\u0007\u0002;%\u0011\u0001&\b\u0002\u000f%\u0016\u001cx.\u001e:dKJ+7m\u001c:e!\tQS&D\u0001,\u0015\u0005a\u0013!B:dC2\f\u0017B\u0001\u0018,\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\r\u001d\u000f\u0005E2dB\u0001\u001a6\u001b\u0005\u0019$B\u0001\u001b$\u0003\u0019a$o\\8u}%\tA&\u0003\u00028W\u00059\u0001/Y2lC\u001e,\u0017BA\u001d;\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t94&\u0001\u0003oC6,W#A\u001f\u0011\u0005y\u0012eBA A!\t\u00114&\u0003\u0002BW\u00051\u0001K]3eK\u001aL!a\u0011#\u0003\rM#(/\u001b8h\u0015\t\t5&A\u0003oC6,\u0007%\u0003\u0002<O\u0005\u0019A\u000f\u001e7\u0016\u0003%\u0003\"AS'\u000f\u0005\u0019Z\u0015B\u0001'\u001e\u0003-\u0019\u0015m\u00195f!>d\u0017nY=\n\u00059{%a\u0001+uY*\u0011A*H\u0001\u0005iRd\u0007%\u0003\u0002HO\u0005i1-\u00198p]&\u001c\u0017\r\u001c(b[\u0016\fabY1o_:L7-\u00197OC6,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0005-^C\u0016\f\u0005\u0002'\u0001!)1h\u0002a\u0001{!)qi\u0002a\u0001\u0013\")!k\u0002a\u0001{\u0005!1m\u001c9z)\u00111F,\u00180\t\u000fmB\u0001\u0013!a\u0001{!9q\t\u0003I\u0001\u0002\u0004I\u0005b\u0002*\t!\u0003\u0005\r!P\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\t'FA\u001fcW\u0005\u0019\u0007C\u00013j\u001b\u0005)'B\u00014h\u0003%)hn\u00195fG.,GM\u0003\u0002iW\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005),'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#A7+\u0005%\u0013\u0017AD2paf$C-\u001a4bk2$HeM\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003E\u0004\"A]<\u000e\u0003MT!\u0001^;\u0002\t1\fgn\u001a\u0006\u0002m\u0006!!.\u0019<b\u0013\t\u00195/\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001{!\tQ30\u0003\u0002}W\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019q0!\u0002\u0011\u0007)\n\t!C\u0002\u0002\u0004-\u00121!\u00118z\u0011!\t9ADA\u0001\u0002\u0004Q\u0018a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u000eA)\u0011qBA\u000b\u007f6\u0011\u0011\u0011\u0003\u0006\u0004\u0003'Y\u0013AC2pY2,7\r^5p]&!\u0011qCA\t\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005u\u00111\u0005\t\u0004U\u0005}\u0011bAA\u0011W\t9!i\\8mK\u0006t\u0007\u0002CA\u0004!\u0005\u0005\t\u0019A@\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004c\u0006%\u0002\u0002CA\u0004#\u0005\u0005\t\u0019\u0001>\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012A_\u0001\ti>\u001cFO]5oOR\t\u0011/\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003;\t9\u0004\u0003\u0005\u0002\bQ\t\t\u00111\u0001\u0000\u0003-\u0019e*Y7f%\u0016\u001cwN\u001d3\u0011\u0005\u001922#\u0002\f\u0002@\u0005\u0015\u0003c\u0001\u0016\u0002B%\u0019\u00111I\u0016\u0003\r\u0005s\u0017PU3g!\u0011\t9%a\u0013\u000e\u0005\u0005%#B\u0001\u0011v\u0013\rI\u0014\u0011\n\u000b\u0003\u0003w\t\u0011\u0002]1sg\u0016\u0014u\u000eZ=\u0015\u0017Y\u000b\u0019&!\u0016\u0002X\u0005=\u0014\u0011\u0010\u0005\u0006wa\u0001\r!\u0010\u0005\u0006\u000fb\u0001\r!\u0013\u0005\b\u00033B\u0002\u0019AA.\u0003\u0019aWM\\4uQB\u0019!&!\u0018\n\u0007\u0005}3FA\u0003TQ>\u0014H\u000f\u000b\u0003\u0002X\u0005\r\u0004\u0003BA3\u0003Wj!!a\u001a\u000b\u0007\u0005%\u0014%\u0001\u0003vi&d\u0017\u0002BA7\u0003O\u0012a!\u001e8vg\u0016$\u0007bBA91\u0001\u0007\u00111O\u0001\u0003SR\u0004B!!\u001a\u0002v%!\u0011qOA4\u00051\u0011\u0015\u0010^3Ji\u0016\u0014\u0018\r^8s\u0011\u001d\tY\b\u0007a\u0001\u0003{\n1!\\:h!\u0011\t)'a \n\t\u0005\u0005\u0015q\r\u0002\u000b\u0005f$Xm\u0015;sS:<\u0007f\u0001\r\u0002\u0006B!\u0011qQAF\u001b\t\tII\u0003\u0002iC%!\u0011QRAE\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000fY\u000b\u0019*!&\u0002\u0018\")1(\u0007a\u0001{!)q)\u0007a\u0001\u0013\")!+\u0007a\u0001{\u00059QO\\1qa2LH\u0003BAO\u0003S\u0003RAKAP\u0003GK1!!),\u0005\u0019y\u0005\u000f^5p]B1!&!*>\u0013vJ1!a*,\u0005\u0019!V\u000f\u001d7fg!A\u00111\u0016\u000e\u0002\u0002\u0003\u0007a+A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!-\u0011\u0007I\f\u0019,C\u0002\u00026N\u0014aa\u00142kK\u000e$\bf\u0001\f\u0002\u0006\"\u001aQ#!\"")
public final class CNameRecord
extends ResourceRecord
implements Product,
Serializable {
    private final String canonicalName;

    public static Option<Tuple3<String, CachePolicy.Ttl, String>> unapply(CNameRecord x$0) {
        return CNameRecord$.MODULE$.unapply(x$0);
    }

    public static CNameRecord apply(String name, CachePolicy.Ttl ttl, String canonicalName) {
        return CNameRecord$.MODULE$.apply(name, ttl, canonicalName);
    }

    @InternalApi
    public static CNameRecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it, ByteString msg) {
        return CNameRecord$.MODULE$.parseBody(name, ttl, length, it, msg);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public String name() {
        return super.name();
    }

    @Override
    public CachePolicy.Ttl ttl() {
        return super.ttl();
    }

    public String canonicalName() {
        return this.canonicalName;
    }

    public CNameRecord copy(String name, CachePolicy.Ttl ttl, String canonicalName) {
        return new CNameRecord(name, ttl, canonicalName);
    }

    public String copy$default$1() {
        return this.name();
    }

    public CachePolicy.Ttl copy$default$2() {
        return this.ttl();
    }

    public String copy$default$3() {
        return this.canonicalName();
    }

    @Override
    public String productPrefix() {
        return "CNameRecord";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.name();
            }
            case 1: {
                return this.ttl();
            }
            case 2: {
                return this.canonicalName();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof CNameRecord;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "name";
            }
            case 1: {
                return "ttl";
            }
            case 2: {
                return "canonicalName";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof CNameRecord)) return false;
        boolean bl = true;
        if (!bl) return false;
        CNameRecord cNameRecord = (CNameRecord)x$1;
        String string = this.name();
        String string2 = cNameRecord.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        CachePolicy.Ttl ttl = this.ttl();
        CachePolicy.Ttl ttl2 = cNameRecord.ttl();
        if (ttl == null) {
            if (ttl2 != null) {
                return false;
            }
        } else if (!((Object)ttl).equals(ttl2)) return false;
        String string3 = this.canonicalName();
        String string4 = cNameRecord.canonicalName();
        if (string3 == null) {
            if (string4 == null) return true;
            return false;
        } else {
            if (!string3.equals(string4)) return false;
            return true;
        }
    }

    public CNameRecord(String name, CachePolicy.Ttl ttl, String canonicalName) {
        this.canonicalName = canonicalName;
        super(name, ttl, RecordType$.MODULE$.CNAME().code(), RecordClass$.MODULE$.IN().code());
        Product.$init$(this);
    }
}

