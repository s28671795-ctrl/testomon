/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.CachePolicy;
import akka.io.dns.ResourceRecord;
import akka.io.dns.UnknownRecord$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=h\u0001\u0002\u0012$\u0005*B\u0001\"\u0011\u0001\u0003\u0016\u0004%\tE\u0011\u0005\n\u0017\u0002\u0011\t\u0012)A\u0005\u00072C\u0001\"\u0014\u0001\u0003\u0016\u0004%\tE\u0014\u0005\n-\u0002\u0011\t\u0012)A\u0005\u001f^C\u0001\u0002\u0017\u0001\u0003\u0016\u0004%\t%\u0017\u0005\n;\u0002\u0011\t\u0012)A\u00055zC\u0001b\u0018\u0001\u0003\u0016\u0004%\t%\u0017\u0005\nA\u0002\u0011\t\u0012)A\u00055\u0006D\u0001B\u0019\u0001\u0003\u0016\u0004%\ta\u0019\u0005\tU\u0002\u0011\t\u0012)A\u0005I\")1\u000e\u0001C\u0001Y\"91\u000fAA\u0001\n\u0003!\bb\u0002>\u0001#\u0003%\ta\u001f\u0005\n\u0003\u001b\u0001\u0011\u0013!C\u0001\u0003\u001fA\u0011\"a\u0005\u0001#\u0003%\t!!\u0006\t\u0013\u0005e\u0001!%A\u0005\u0002\u0005U\u0001\"CA\u000e\u0001E\u0005I\u0011AA\u000f\u0011%\t\t\u0003AA\u0001\n\u0003\n\u0019\u0003C\u0005\u00024\u0001\t\t\u0011\"\u0001\u00026!I\u0011Q\b\u0001\u0002\u0002\u0013\u0005\u0011q\b\u0005\n\u0003\u0017\u0002\u0011\u0011!C!\u0003\u001bB\u0011\"a\u0017\u0001\u0003\u0003%\t!!\u0018\t\u0013\u0005\u001d\u0004!!A\u0005B\u0005%\u0004\"CA7\u0001\u0005\u0005I\u0011IA8\u0011%\t\t\bAA\u0001\n\u0003\n\u0019\bC\u0005\u0002v\u0001\t\t\u0011\"\u0011\u0002x\u001dA\u00111P\u0012\t\u0002\r\niHB\u0004#G!\u00051%a \t\r-dB\u0011AAH\u0011\u001d\t\t\n\bC\u0001\u0003'C\u0011\"a0\u001d\u0003\u0003%\t)!1\t\u0013\u00055G$!A\u0005\u0002\u0006=\u0007\"CAq9\u0005\u0005I\u0011BAr\u00055)fn\u001b8po:\u0014VmY8sI*\u0011A%J\u0001\u0004I:\u001c(B\u0001\u0014(\u0003\tIwNC\u0001)\u0003\u0011\t7n[1\u0004\u0001M!\u0001aK\u00186!\taS&D\u0001$\u0013\tq3E\u0001\bSKN|WO]2f%\u0016\u001cwN\u001d3\u0011\u0005A\u001aT\"A\u0019\u000b\u0003I\nQa]2bY\u0006L!\u0001N\u0019\u0003\u000fA\u0013x\u000eZ;diB\u0011aG\u0010\b\u0003oqr!\u0001O\u001e\u000e\u0003eR!AO\u0015\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0014BA\u001f2\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0010!\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005u\n\u0014\u0001\u00028b[\u0016,\u0012a\u0011\t\u0003\t\"s!!\u0012$\u0011\u0005a\n\u0014BA$2\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011J\u0013\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u001d\u000b\u0014!\u00028b[\u0016\u0004\u0013BA!.\u0003\r!H\u000f\\\u000b\u0002\u001fB\u0011\u0001k\u0015\b\u0003YEK!AU\u0012\u0002\u0017\r\u000b7\r[3Q_2L7-_\u0005\u0003)V\u00131\u0001\u0016;m\u0015\t\u00116%\u0001\u0003ui2\u0004\u0013BA'.\u0003\u001d\u0011Xm\u0019+za\u0016,\u0012A\u0017\t\u0003amK!\u0001X\u0019\u0003\u000bMCwN\u001d;\u0002\u0011I,7\rV=qK\u0002J!\u0001W\u0017\u0002\u0011I,7m\u00117bgN\f\u0011B]3d\u00072\f7o\u001d\u0011\n\u0005}k\u0013\u0001\u00023bi\u0006,\u0012\u0001\u001a\t\u0003K\"l\u0011A\u001a\u0006\u0003O\u001e\nA!\u001e;jY&\u0011\u0011N\u001a\u0002\u000b\u0005f$Xm\u0015;sS:<\u0017!\u00023bi\u0006\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0004n]>\u0004\u0018O\u001d\t\u0003Y\u0001AQ!Q\u0006A\u0002\rCQ!T\u0006A\u0002=CQ\u0001W\u0006A\u0002iCQaX\u0006A\u0002iCQAY\u0006A\u0002\u0011\fAaY8qsR1Q.\u001e<xqfDq!\u0011\u0007\u0011\u0002\u0003\u00071\tC\u0004N\u0019A\u0005\t\u0019A(\t\u000fac\u0001\u0013!a\u00015\"9q\f\u0004I\u0001\u0002\u0004Q\u0006b\u00022\r!\u0003\u0005\r\u0001Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005a(FA\"~W\u0005q\bcA@\u0002\n5\u0011\u0011\u0011\u0001\u0006\u0005\u0003\u0007\t)!A\u0005v]\u000eDWmY6fI*\u0019\u0011qA\u0019\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\f\u0005\u0005!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\tU\tyU0\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005]!F\u0001.~\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ\nabY8qs\u0012\"WMZ1vYR$S'\u0006\u0002\u0002 )\u0012A-`\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u0015\u0002\u0003BA\u0014\u0003ci!!!\u000b\u000b\t\u0005-\u0012QF\u0001\u0005Y\u0006twM\u0003\u0002\u00020\u0005!!.\u0019<b\u0013\rI\u0015\u0011F\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003o\u00012\u0001MA\u001d\u0013\r\tY$\r\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u0003\n9\u0005E\u00021\u0003\u0007J1!!\u00122\u0005\r\te.\u001f\u0005\n\u0003\u0013\"\u0012\u0011!a\u0001\u0003o\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA(!\u0019\t\t&a\u0016\u0002B5\u0011\u00111\u000b\u0006\u0004\u0003+\n\u0014AC2pY2,7\r^5p]&!\u0011\u0011LA*\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005}\u0013Q\r\t\u0004a\u0005\u0005\u0014bAA2c\t9!i\\8mK\u0006t\u0007\"CA%-\u0005\u0005\t\u0019AA!\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005\u0015\u00121\u000e\u0005\n\u0003\u0013:\u0012\u0011!a\u0001\u0003o\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003o\t\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003K\ta!Z9vC2\u001cH\u0003BA0\u0003sB\u0011\"!\u0013\u001b\u0003\u0003\u0005\r!!\u0011\u0002\u001bUs7N\\8x]J+7m\u001c:e!\taCdE\u0003\u001d\u0003\u0003\u000b9\tE\u00021\u0003\u0007K1!!\"2\u0005\u0019\te.\u001f*fMB!\u0011\u0011RAG\u001b\t\tYIC\u0002'\u0003[I1aPAF)\t\ti(A\u0005qCJ\u001cXMQ8esRiQ.!&\u0002\u0018\u0006e\u00151TAO\u0003SCQ!\u0011\u0010A\u0002\rCQ!\u0014\u0010A\u0002=CQ\u0001\u0017\u0010A\u0002iCQa\u0018\u0010A\u0002iCa!a(\u001f\u0001\u0004Q\u0016A\u00027f]\u001e$\b\u000e\u000b\u0003\u0002\u001e\u0006\r\u0006cA3\u0002&&\u0019\u0011q\u00154\u0003\rUtWo]3e\u0011\u001d\tYK\ba\u0001\u0003[\u000b!!\u001b;\u0011\u0007\u0015\fy+C\u0002\u00022\u001a\u0014ABQ=uK&#XM]1u_JD3AHA[!\u0011\t9,a/\u000e\u0005\u0005e&bAA\u0004O%!\u0011QXA]\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u00175\f\u0019-!2\u0002H\u0006%\u00171\u001a\u0005\u0006\u0003~\u0001\ra\u0011\u0005\u0006\u001b~\u0001\ra\u0014\u0005\u00061~\u0001\rA\u0017\u0005\u0006?~\u0001\rA\u0017\u0005\u0006E~\u0001\r\u0001Z\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t.!8\u0011\u000bA\n\u0019.a6\n\u0007\u0005U\u0017G\u0001\u0004PaRLwN\u001c\t\ta\u0005e7i\u0014.[I&\u0019\u00111\\\u0019\u0003\rQ+\b\u000f\\36\u0011!\ty\u000eIA\u0001\u0002\u0004i\u0017a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u001d\t\u0005\u0003O\t9/\u0003\u0003\u0002j\u0006%\"AB(cU\u0016\u001cG\u000fK\u0002\u001d\u0003kC3aGA[\u0001")
public final class UnknownRecord
extends ResourceRecord
implements Product,
Serializable {
    private final ByteString data;

    public static Option<Tuple5<String, CachePolicy.Ttl, Object, Object, ByteString>> unapply(UnknownRecord x$0) {
        return UnknownRecord$.MODULE$.unapply(x$0);
    }

    public static UnknownRecord apply(String name, CachePolicy.Ttl ttl, short recType, short recClass, ByteString data) {
        return UnknownRecord$.MODULE$.apply(name, ttl, recType, recClass, data);
    }

    @InternalApi
    public static UnknownRecord parseBody(String name, CachePolicy.Ttl ttl, short recType, short recClass, short length, ByteIterator it) {
        return UnknownRecord$.MODULE$.parseBody(name, ttl, recType, recClass, length, it);
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

    @Override
    public short recType() {
        return super.recType();
    }

    @Override
    public short recClass() {
        return super.recClass();
    }

    public ByteString data() {
        return this.data;
    }

    public UnknownRecord copy(String name, CachePolicy.Ttl ttl, short recType, short recClass, ByteString data) {
        return new UnknownRecord(name, ttl, recType, recClass, data);
    }

    public String copy$default$1() {
        return this.name();
    }

    public CachePolicy.Ttl copy$default$2() {
        return this.ttl();
    }

    public short copy$default$3() {
        return this.recType();
    }

    public short copy$default$4() {
        return this.recClass();
    }

    public ByteString copy$default$5() {
        return this.data();
    }

    @Override
    public String productPrefix() {
        return "UnknownRecord";
    }

    @Override
    public int productArity() {
        return 5;
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
                return BoxesRunTime.boxToShort(this.recType());
            }
            case 3: {
                return BoxesRunTime.boxToShort(this.recClass());
            }
            case 4: {
                return this.data();
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
        return x$1 instanceof UnknownRecord;
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
                return "recType";
            }
            case 3: {
                return "recClass";
            }
            case 4: {
                return "data";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.name()));
        n = Statics.mix(n, Statics.anyHash(this.ttl()));
        n = Statics.mix(n, this.recType());
        n = Statics.mix(n, this.recClass());
        n = Statics.mix(n, Statics.anyHash(this.data()));
        return Statics.finalizeHash(n, 5);
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
        if (!(object instanceof UnknownRecord)) return false;
        boolean bl = true;
        if (!bl) return false;
        UnknownRecord unknownRecord = (UnknownRecord)x$1;
        if (this.recType() != unknownRecord.recType()) return false;
        if (this.recClass() != unknownRecord.recClass()) return false;
        String string = this.name();
        String string2 = unknownRecord.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        CachePolicy.Ttl ttl = this.ttl();
        CachePolicy.Ttl ttl2 = unknownRecord.ttl();
        if (ttl == null) {
            if (ttl2 != null) {
                return false;
            }
        } else if (!((Object)ttl).equals(ttl2)) return false;
        ByteString byteString = this.data();
        ByteString byteString2 = unknownRecord.data();
        if (byteString == null) {
            if (byteString2 == null) return true;
            return false;
        } else {
            if (!((Object)byteString).equals(byteString2)) return false;
            return true;
        }
    }

    public UnknownRecord(String name, CachePolicy.Ttl ttl, short recType, short recClass, ByteString data) {
        this.data = data;
        super(name, ttl, recType, recClass);
        Product.$init$(this);
    }
}

