/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.io.dns.ARecord$;
import akka.io.dns.CachePolicy;
import akka.io.dns.RecordClass$;
import akka.io.dns.RecordType$;
import akka.io.dns.ResourceRecord;
import akka.util.ByteIterator;
import java.io.Serializable;
import java.net.InetAddress;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005g\u0001\u0002\u000f\u001e\u0005\u0012B\u0001b\u000f\u0001\u0003\u0016\u0004%\t\u0005\u0010\u0005\n\u000b\u0002\u0011\t\u0012)A\u0005{\u0019C\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0005\u0013\u0005\n!\u0002\u0011\t\u0012)A\u0005\u0013FC\u0001B\u0015\u0001\u0003\u0016\u0004%\ta\u0015\u0005\t9\u0002\u0011\t\u0012)A\u0005)\")Q\f\u0001C\u0001=\"91\rAA\u0001\n\u0003!\u0007b\u00025\u0001#\u0003%\t!\u001b\u0005\bi\u0002\t\n\u0011\"\u0001v\u0011\u001d9\b!%A\u0005\u0002aDqA\u001f\u0001\u0002\u0002\u0013\u00053\u0010C\u0005\u0002\u0004\u0001\t\t\u0011\"\u0001\u0002\u0006!I\u0011Q\u0002\u0001\u0002\u0002\u0013\u0005\u0011q\u0002\u0005\n\u00037\u0001\u0011\u0011!C!\u0003;A\u0011\"a\u000b\u0001\u0003\u0003%\t!!\f\t\u0013\u0005]\u0002!!A\u0005B\u0005e\u0002\"CA\u001f\u0001\u0005\u0005I\u0011IA \u0011%\t\t\u0005AA\u0001\n\u0003\n\u0019\u0005C\u0005\u0002F\u0001\t\t\u0011\"\u0011\u0002H\u001dA\u00111J\u000f\t\u0002}\tiEB\u0004\u001d;!\u0005q$a\u0014\t\ru3B\u0011AA0\u0011\u001d\t\tG\u0006C\u0001\u0003GB\u0011\"a#\u0017\u0003\u0003%\t)!$\t\u0013\u0005Ue#!A\u0005\u0002\u0006]\u0005\"CAU-\u0005\u0005I\u0011BAV\u0005\u001d\t%+Z2pe\u0012T!AH\u0010\u0002\u0007\u0011t7O\u0003\u0002!C\u0005\u0011\u0011n\u001c\u0006\u0002E\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001A\u0013*_A\u0011aeJ\u0007\u0002;%\u0011\u0001&\b\u0002\u000f%\u0016\u001cx.\u001e:dKJ+7m\u001c:e!\tQS&D\u0001,\u0015\u0005a\u0013!B:dC2\f\u0017B\u0001\u0018,\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\r\u001d\u000f\u0005E2dB\u0001\u001a6\u001b\u0005\u0019$B\u0001\u001b$\u0003\u0019a$o\\8u}%\tA&\u0003\u00028W\u00059\u0001/Y2lC\u001e,\u0017BA\u001d;\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t94&\u0001\u0003oC6,W#A\u001f\u0011\u0005y\u0012eBA A!\t\u00114&\u0003\u0002BW\u00051\u0001K]3eK\u001aL!a\u0011#\u0003\rM#(/\u001b8h\u0015\t\t5&A\u0003oC6,\u0007%\u0003\u0002<O\u0005\u0019A\u000f\u001e7\u0016\u0003%\u0003\"AS'\u000f\u0005\u0019Z\u0015B\u0001'\u001e\u0003-\u0019\u0015m\u00195f!>d\u0017nY=\n\u00059{%a\u0001+uY*\u0011A*H\u0001\u0005iRd\u0007%\u0003\u0002HO\u0005\u0011\u0011\u000e]\u000b\u0002)B\u0011QKW\u0007\u0002-*\u0011q\u000bW\u0001\u0004]\u0016$(\"A-\u0002\t)\fg/Y\u0005\u00037Z\u00131\"\u00138fi\u0006#GM]3tg\u0006\u0019\u0011\u000e\u001d\u0011\u0002\rqJg.\u001b;?)\u0011y\u0006-\u00192\u0011\u0005\u0019\u0002\u0001\"B\u001e\b\u0001\u0004i\u0004\"B$\b\u0001\u0004I\u0005\"\u0002*\b\u0001\u0004!\u0016\u0001B2paf$BaX3gO\"91\b\u0003I\u0001\u0002\u0004i\u0004bB$\t!\u0003\u0005\r!\u0013\u0005\b%\"\u0001\n\u00111\u0001U\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012A\u001b\u0016\u0003{-\\\u0013\u0001\u001c\t\u0003[Jl\u0011A\u001c\u0006\u0003_B\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005E\\\u0013AC1o]>$\u0018\r^5p]&\u00111O\u001c\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0002m*\u0012\u0011j[\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\u0005I(F\u0001+l\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tA\u0010E\u0002~\u0003\u0003i\u0011A \u0006\u0003\u007fb\u000bA\u0001\\1oO&\u00111I`\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000f\u00012AKA\u0005\u0013\r\tYa\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003#\t9\u0002E\u0002+\u0003'I1!!\u0006,\u0005\r\te.\u001f\u0005\n\u00033q\u0011\u0011!a\u0001\u0003\u000f\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0010!\u0019\t\t#a\n\u0002\u00125\u0011\u00111\u0005\u0006\u0004\u0003KY\u0013AC2pY2,7\r^5p]&!\u0011\u0011FA\u0012\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005=\u0012Q\u0007\t\u0004U\u0005E\u0012bAA\u001aW\t9!i\\8mK\u0006t\u0007\"CA\r!\u0005\u0005\t\u0019AA\t\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007q\fY\u0004C\u0005\u0002\u001aE\t\t\u00111\u0001\u0002\b\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\b\u0005AAo\\*ue&tw\rF\u0001}\u0003\u0019)\u0017/^1mgR!\u0011qFA%\u0011%\tI\u0002FA\u0001\u0002\u0004\t\t\"A\u0004B%\u0016\u001cwN\u001d3\u0011\u0005\u001922#\u0002\f\u0002R\u0005]\u0003c\u0001\u0016\u0002T%\u0019\u0011QK\u0016\u0003\r\u0005s\u0017PU3g!\u0011\tI&!\u0018\u000e\u0005\u0005m#B\u0001\u0011Y\u0013\rI\u00141\f\u000b\u0003\u0003\u001b\n\u0011\u0002]1sg\u0016\u0014u\u000eZ=\u0015\u0013}\u000b)'a\u001a\u0002j\u0005\u0005\u0005\"B\u001e\u0019\u0001\u0004i\u0004\"B$\u0019\u0001\u0004I\u0005bBA61\u0001\u0007\u0011QN\u0001\u0007Y\u0016tw\r\u001e5\u0011\u0007)\ny'C\u0002\u0002r-\u0012Qa\u00155peRDC!!\u001b\u0002vA!\u0011qOA?\u001b\t\tIHC\u0002\u0002|\u0005\nA!\u001e;jY&!\u0011qPA=\u0005\u0019)h.^:fI\"9\u00111\u0011\rA\u0002\u0005\u0015\u0015AA5u!\u0011\t9(a\"\n\t\u0005%\u0015\u0011\u0010\u0002\r\u0005f$X-\u0013;fe\u0006$xN]\u0001\u0006CB\u0004H.\u001f\u000b\b?\u0006=\u0015\u0011SAJ\u0011\u0015Y\u0014\u00041\u0001>\u0011\u00159\u0015\u00041\u0001J\u0011\u0015\u0011\u0016\u00041\u0001U\u0003\u001d)h.\u00199qYf$B!!'\u0002&B)!&a'\u0002 &\u0019\u0011QT\u0016\u0003\r=\u0003H/[8o!\u0019Q\u0013\u0011U\u001fJ)&\u0019\u00111U\u0016\u0003\rQ+\b\u000f\\34\u0011!\t9KGA\u0001\u0002\u0004y\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u0016\t\u0004{\u0006=\u0016bAAY}\n1qJ\u00196fGRD3AFA[!\u0011\t9,a/\u000e\u0005\u0005e&BA9\"\u0013\u0011\ti,!/\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0004+\u0005U\u0006")
public final class ARecord
extends ResourceRecord
implements Product,
Serializable {
    private final InetAddress ip;

    public static Option<Tuple3<String, CachePolicy.Ttl, InetAddress>> unapply(ARecord x$0) {
        return ARecord$.MODULE$.unapply(x$0);
    }

    public static ARecord apply(String name, CachePolicy.Ttl ttl, InetAddress ip2) {
        return ARecord$.MODULE$.apply(name, ttl, ip2);
    }

    public static ARecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it) {
        return ARecord$.MODULE$.parseBody(name, ttl, length, it);
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

    public InetAddress ip() {
        return this.ip;
    }

    public ARecord copy(String name, CachePolicy.Ttl ttl, InetAddress ip2) {
        return new ARecord(name, ttl, ip2);
    }

    public String copy$default$1() {
        return this.name();
    }

    public CachePolicy.Ttl copy$default$2() {
        return this.ttl();
    }

    public InetAddress copy$default$3() {
        return this.ip();
    }

    @Override
    public String productPrefix() {
        return "ARecord";
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
                return this.ip();
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
        return x$1 instanceof ARecord;
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
                return "ip";
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
        if (!(object instanceof ARecord)) return false;
        boolean bl = true;
        if (!bl) return false;
        ARecord aRecord = (ARecord)x$1;
        String string = this.name();
        String string2 = aRecord.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        CachePolicy.Ttl ttl = this.ttl();
        CachePolicy.Ttl ttl2 = aRecord.ttl();
        if (ttl == null) {
            if (ttl2 != null) {
                return false;
            }
        } else if (!((Object)ttl).equals(ttl2)) return false;
        InetAddress inetAddress = this.ip();
        InetAddress inetAddress2 = aRecord.ip();
        if (inetAddress == null) {
            if (inetAddress2 == null) return true;
            return false;
        } else {
            if (!((Object)inetAddress).equals(inetAddress2)) return false;
            return true;
        }
    }

    public ARecord(String name, CachePolicy.Ttl ttl, InetAddress ip2) {
        this.ip = ip2;
        super(name, ttl, RecordType$.MODULE$.A().code(), RecordClass$.MODULE$.IN().code());
        Product.$init$(this);
    }
}

