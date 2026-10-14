/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.AAAARecord$;
import akka.io.dns.CachePolicy;
import akka.io.dns.RecordClass$;
import akka.io.dns.RecordType$;
import akka.io.dns.ResourceRecord;
import akka.util.ByteIterator;
import java.io.Serializable;
import java.net.Inet6Address;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rg\u0001\u0002\u000f\u001e\u0005\u0012B\u0001b\u000f\u0001\u0003\u0016\u0004%\t\u0005\u0010\u0005\n\u000b\u0002\u0011\t\u0012)A\u0005{\u0019C\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0005\u0013\u0005\n!\u0002\u0011\t\u0012)A\u0005\u0013FC\u0001B\u0015\u0001\u0003\u0016\u0004%\ta\u0015\u0005\t9\u0002\u0011\t\u0012)A\u0005)\")Q\f\u0001C\u0001=\"91\rAA\u0001\n\u0003!\u0007b\u00025\u0001#\u0003%\t!\u001b\u0005\bi\u0002\t\n\u0011\"\u0001v\u0011\u001d9\b!%A\u0005\u0002aDqA\u001f\u0001\u0002\u0002\u0013\u00053\u0010C\u0005\u0002\u0004\u0001\t\t\u0011\"\u0001\u0002\u0006!I\u0011Q\u0002\u0001\u0002\u0002\u0013\u0005\u0011q\u0002\u0005\n\u00037\u0001\u0011\u0011!C!\u0003;A\u0011\"a\u000b\u0001\u0003\u0003%\t!!\f\t\u0013\u0005]\u0002!!A\u0005B\u0005e\u0002\"CA\u001f\u0001\u0005\u0005I\u0011IA \u0011%\t\t\u0005AA\u0001\n\u0003\n\u0019\u0005C\u0005\u0002F\u0001\t\t\u0011\"\u0011\u0002H\u001dA\u00111J\u000f\t\u0002}\tiEB\u0004\u001d;!\u0005q$a\u0014\t\ru3B\u0011AA0\u0011\u001d\t\tG\u0006C\u0001\u0003GB\u0011\"a&\u0017\u0003\u0003%\t)!'\t\u0013\u0005\u0005f#!A\u0005\u0002\u0006\r\u0006\"CA[-\u0005\u0005I\u0011BA\\\u0005)\t\u0015)Q!SK\u000e|'\u000f\u001a\u0006\u0003=}\t1\u0001\u001a8t\u0015\t\u0001\u0013%\u0001\u0002j_*\t!%\u0001\u0003bW.\f7\u0001A\n\u0005\u0001\u0015Js\u0006\u0005\u0002'O5\tQ$\u0003\u0002);\tq!+Z:pkJ\u001cWMU3d_J$\u0007C\u0001\u0016.\u001b\u0005Y#\"\u0001\u0017\u0002\u000bM\u001c\u0017\r\\1\n\u00059Z#a\u0002)s_\u0012,8\r\u001e\t\u0003aar!!\r\u001c\u000f\u0005I*T\"A\u001a\u000b\u0005Q\u001a\u0013A\u0002\u001fs_>$h(C\u0001-\u0013\t94&A\u0004qC\u000e\\\u0017mZ3\n\u0005eR$\u0001D*fe&\fG.\u001b>bE2,'BA\u001c,\u0003\u0011q\u0017-\\3\u0016\u0003u\u0002\"A\u0010\"\u000f\u0005}\u0002\u0005C\u0001\u001a,\u0013\t\t5&\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u0007\u0012\u0013aa\u0015;sS:<'BA!,\u0003\u0015q\u0017-\\3!\u0013\tYt%A\u0002ui2,\u0012!\u0013\t\u0003\u00156s!AJ&\n\u00051k\u0012aC\"bG\",\u0007k\u001c7jGfL!AT(\u0003\u0007Q#HN\u0003\u0002M;\u0005!A\u000f\u001e7!\u0013\t9u%\u0001\u0002jaV\tA\u000b\u0005\u0002V56\taK\u0003\u0002X1\u0006\u0019a.\u001a;\u000b\u0003e\u000bAA[1wC&\u00111L\u0016\u0002\r\u0013:,GON!eIJ,7o]\u0001\u0004SB\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003`A\u0006\u0014\u0007C\u0001\u0014\u0001\u0011\u0015Yt\u00011\u0001>\u0011\u00159u\u00011\u0001J\u0011\u0015\u0011v\u00011\u0001U\u0003\u0011\u0019w\u000e]=\u0015\t}+gm\u001a\u0005\bw!\u0001\n\u00111\u0001>\u0011\u001d9\u0005\u0002%AA\u0002%CqA\u0015\u0005\u0011\u0002\u0003\u0007A+\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003)T#!P6,\u00031\u0004\"!\u001c:\u000e\u00039T!a\u001c9\u0002\u0013Ut7\r[3dW\u0016$'BA9,\u0003)\tgN\\8uCRLwN\\\u0005\u0003g:\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012A\u001e\u0016\u0003\u0013.\fabY8qs\u0012\"WMZ1vYR$3'F\u0001zU\t!6.A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002yB\u0019Q0!\u0001\u000e\u0003yT!a -\u0002\t1\fgnZ\u0005\u0003\u0007z\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a\u0002\u0011\u0007)\nI!C\u0002\u0002\f-\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!\u0005\u0002\u0018A\u0019!&a\u0005\n\u0007\u0005U1FA\u0002B]fD\u0011\"!\u0007\u000f\u0003\u0003\u0005\r!a\u0002\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\ty\u0002\u0005\u0004\u0002\"\u0005\u001d\u0012\u0011C\u0007\u0003\u0003GQ1!!\n,\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003S\t\u0019C\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0018\u0003k\u00012AKA\u0019\u0013\r\t\u0019d\u000b\u0002\b\u0005>|G.Z1o\u0011%\tI\u0002EA\u0001\u0002\u0004\t\t\"\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,Gc\u0001?\u0002<!I\u0011\u0011D\t\u0002\u0002\u0003\u0007\u0011qA\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011qA\u0001\ti>\u001cFO]5oOR\tA0\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003_\tI\u0005C\u0005\u0002\u001aQ\t\t\u00111\u0001\u0002\u0012\u0005Q\u0011)Q!B%\u0016\u001cwN\u001d3\u0011\u0005\u001922#\u0002\f\u0002R\u0005]\u0003c\u0001\u0016\u0002T%\u0019\u0011QK\u0016\u0003\r\u0005s\u0017PU3g!\u0011\tI&!\u0018\u000e\u0005\u0005m#B\u0001\u0011Y\u0013\rI\u00141\f\u000b\u0003\u0003\u001b\n\u0011\u0002]1sg\u0016\u0014u\u000eZ=\u0015\u0013}\u000b)'a\u001a\u0002j\u0005\u0005\u0005\"B\u001e\u0019\u0001\u0004i\u0004\"B$\u0019\u0001\u0004I\u0005bBA61\u0001\u0007\u0011QN\u0001\u0007Y\u0016tw\r\u001e5\u0011\u0007)\ny'C\u0002\u0002r-\u0012Qa\u00155peRDC!!\u001b\u0002vA!\u0011qOA?\u001b\t\tIHC\u0002\u0002|\u0005\nA!\u001e;jY&!\u0011qPA=\u0005\u0019)h.^:fI\"9\u00111\u0011\rA\u0002\u0005\u0015\u0015AA5u!\u0011\t9(a\"\n\t\u0005%\u0015\u0011\u0010\u0002\r\u0005f$X-\u0013;fe\u0006$xN\u001d\u0015\u00041\u00055\u0005\u0003BAH\u0003'k!!!%\u000b\u0005E\f\u0013\u0002BAK\u0003#\u00131\"\u00138uKJt\u0017\r\\!qS\u0006)\u0011\r\u001d9msR9q,a'\u0002\u001e\u0006}\u0005\"B\u001e\u001a\u0001\u0004i\u0004\"B$\u001a\u0001\u0004I\u0005\"\u0002*\u001a\u0001\u0004!\u0016aB;oCB\u0004H.\u001f\u000b\u0005\u0003K\u000b\t\fE\u0003+\u0003O\u000bY+C\u0002\u0002*.\u0012aa\u00149uS>t\u0007C\u0002\u0016\u0002.vJE+C\u0002\u00020.\u0012a\u0001V;qY\u0016\u001c\u0004\u0002CAZ5\u0005\u0005\t\u0019A0\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002:B\u0019Q0a/\n\u0007\u0005ufP\u0001\u0004PE*,7\r\u001e\u0015\u0004-\u00055\u0005fA\u000b\u0002\u000e\u0002")
public final class AAAARecord
extends ResourceRecord
implements Product,
Serializable {
    private final Inet6Address ip;

    public static Option<Tuple3<String, CachePolicy.Ttl, Inet6Address>> unapply(AAAARecord x$0) {
        return AAAARecord$.MODULE$.unapply(x$0);
    }

    public static AAAARecord apply(String name, CachePolicy.Ttl ttl, Inet6Address ip2) {
        return AAAARecord$.MODULE$.apply(name, ttl, ip2);
    }

    @InternalApi
    public static AAAARecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it) {
        return AAAARecord$.MODULE$.parseBody(name, ttl, length, it);
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

    public Inet6Address ip() {
        return this.ip;
    }

    public AAAARecord copy(String name, CachePolicy.Ttl ttl, Inet6Address ip2) {
        return new AAAARecord(name, ttl, ip2);
    }

    public String copy$default$1() {
        return this.name();
    }

    public CachePolicy.Ttl copy$default$2() {
        return this.ttl();
    }

    public Inet6Address copy$default$3() {
        return this.ip();
    }

    @Override
    public String productPrefix() {
        return "AAAARecord";
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
        return x$1 instanceof AAAARecord;
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
        if (!(object instanceof AAAARecord)) return false;
        boolean bl = true;
        if (!bl) return false;
        AAAARecord aAAARecord = (AAAARecord)x$1;
        String string = this.name();
        String string2 = aAAARecord.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        CachePolicy.Ttl ttl = this.ttl();
        CachePolicy.Ttl ttl2 = aAAARecord.ttl();
        if (ttl == null) {
            if (ttl2 != null) {
                return false;
            }
        } else if (!((Object)ttl).equals(ttl2)) return false;
        Inet6Address inet6Address = this.ip();
        Inet6Address inet6Address2 = aAAARecord.ip();
        if (inet6Address == null) {
            if (inet6Address2 == null) return true;
            return false;
        } else {
            if (!((Object)inet6Address).equals(inet6Address2)) return false;
            return true;
        }
    }

    public AAAARecord(String name, CachePolicy.Ttl ttl, Inet6Address ip2) {
        this.ip = ip2;
        super(name, ttl, RecordType$.MODULE$.AAAA().code(), RecordClass$.MODULE$.IN().code());
        Product.$init$(this);
    }
}

