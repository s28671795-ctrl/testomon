/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.CachePolicy;
import akka.io.dns.RecordClass$;
import akka.io.dns.RecordType$;
import akka.io.dns.ResourceRecord;
import akka.io.dns.SRVRecord$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Uh\u0001B\u0013'\u00056B\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t%\u0012\u0005\n\u001d\u0002\u0011\t\u0012)A\u0005\r>C\u0001\u0002\u0015\u0001\u0003\u0016\u0004%\t%\u0015\u0005\n3\u0002\u0011\t\u0012)A\u0005%jC\u0001b\u0017\u0001\u0003\u0016\u0004%\t\u0001\u0018\u0005\tA\u0002\u0011\t\u0012)A\u0005;\"A\u0011\r\u0001BK\u0002\u0013\u0005A\f\u0003\u0005c\u0001\tE\t\u0015!\u0003^\u0011!\u0019\u0007A!f\u0001\n\u0003a\u0006\u0002\u00033\u0001\u0005#\u0005\u000b\u0011B/\t\u0011\u0015\u0004!Q3A\u0005\u0002\u0015C\u0001B\u001a\u0001\u0003\u0012\u0003\u0006IA\u0012\u0005\u0006O\u0002!\t\u0001\u001b\u0005\ba\u0002\t\t\u0011\"\u0001r\u0011\u001dA\b!%A\u0005\u0002eD\u0011\"!\u0003\u0001#\u0003%\t!a\u0003\t\u0013\u0005=\u0001!%A\u0005\u0002\u0005E\u0001\"CA\u000b\u0001E\u0005I\u0011AA\t\u0011%\t9\u0002AI\u0001\n\u0003\t\t\u0002\u0003\u0005\u0002\u001a\u0001\t\n\u0011\"\u0001z\u0011%\tY\u0002AA\u0001\n\u0003\ni\u0002\u0003\u0005\u0002.\u0001\t\t\u0011\"\u0001]\u0011%\ty\u0003AA\u0001\n\u0003\t\t\u0004C\u0005\u0002>\u0001\t\t\u0011\"\u0011\u0002@!I\u0011Q\n\u0001\u0002\u0002\u0013\u0005\u0011q\n\u0005\n\u00033\u0002\u0011\u0011!C!\u00037B\u0011\"a\u0018\u0001\u0003\u0003%\t%!\u0019\t\u0013\u0005\r\u0004!!A\u0005B\u0005\u0015\u0004\"CA4\u0001\u0005\u0005I\u0011IA5\u000f!\tiG\nE\u0001M\u0005=daB\u0013'\u0011\u00031\u0013\u0011\u000f\u0005\u0007O~!\t!!!\t\u000f\u0005\ru\u0004\"\u0001\u0002\u0006\"I\u00111Y\u0010\u0002\u0002\u0013\u0005\u0015Q\u0019\u0005\n\u0003'|\u0012\u0011!CA\u0003+D\u0011\"a: \u0003\u0003%I!!;\u0003\u0013M\u0013fKU3d_J$'BA\u0014)\u0003\r!gn\u001d\u0006\u0003S)\n!![8\u000b\u0003-\nA!Y6lC\u000e\u00011\u0003\u0002\u0001/ea\u0002\"a\f\u0019\u000e\u0003\u0019J!!\r\u0014\u0003\u001dI+7o\\;sG\u0016\u0014VmY8sIB\u00111GN\u0007\u0002i)\tQ'A\u0003tG\u0006d\u0017-\u0003\u00028i\t9\u0001K]8ek\u000e$\bCA\u001dB\u001d\tQtH\u0004\u0002<}5\tAH\u0003\u0002>Y\u00051AH]8pizJ\u0011!N\u0005\u0003\u0001R\nq\u0001]1dW\u0006<W-\u0003\u0002C\u0007\na1+\u001a:jC2L'0\u00192mK*\u0011\u0001\tN\u0001\u0005]\u0006lW-F\u0001G!\t95J\u0004\u0002I\u0013B\u00111\bN\u0005\u0003\u0015R\na\u0001\u0015:fI\u00164\u0017B\u0001'N\u0005\u0019\u0019FO]5oO*\u0011!\nN\u0001\u0006]\u0006lW\rI\u0005\u0003\tB\n1\u0001\u001e;m+\u0005\u0011\u0006CA*W\u001d\tyC+\u0003\u0002VM\u0005Y1)Y2iKB{G.[2z\u0013\t9\u0006LA\u0002Ui2T!!\u0016\u0014\u0002\tQ$H\u000eI\u0005\u0003!B\n\u0001\u0002\u001d:j_JLG/_\u000b\u0002;B\u00111GX\u0005\u0003?R\u00121!\u00138u\u0003%\u0001(/[8sSRL\b%\u0001\u0004xK&<\u0007\u000e^\u0001\bo\u0016Lw\r\u001b;!\u0003\u0011\u0001xN\u001d;\u0002\u000bA|'\u000f\u001e\u0011\u0002\rQ\f'oZ3u\u0003\u001d!\u0018M]4fi\u0002\na\u0001P5oSRtDcB5kW2lgn\u001c\t\u0003_\u0001AQ\u0001R\u0007A\u0002\u0019CQ\u0001U\u0007A\u0002ICQaW\u0007A\u0002uCQ!Y\u0007A\u0002uCQaY\u0007A\u0002uCQ!Z\u0007A\u0002\u0019\u000bAaY8qsR9\u0011N]:ukZ<\bb\u0002#\u000f!\u0003\u0005\rA\u0012\u0005\b!:\u0001\n\u00111\u0001S\u0011\u001dYf\u0002%AA\u0002uCq!\u0019\b\u0011\u0002\u0003\u0007Q\fC\u0004d\u001dA\u0005\t\u0019A/\t\u000f\u0015t\u0001\u0013!a\u0001\r\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001>+\u0005\u0019[8&\u0001?\u0011\u0007u\f)!D\u0001\u007f\u0015\ry\u0018\u0011A\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u00015\u0003)\tgN\\8uCRLwN\\\u0005\u0004\u0003\u000fq(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u0007U\t\u001160\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005M!FA/|\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ\nabY8qs\u0012\"WMZ1vYR$S'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001c\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ty\u0002\u0005\u0003\u0002\"\u0005-RBAA\u0012\u0015\u0011\t)#a\n\u0002\t1\fgn\u001a\u0006\u0003\u0003S\tAA[1wC&\u0019A*a\t\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u00111GA\u001d!\r\u0019\u0014QG\u0005\u0004\u0003o!$aA!os\"A\u00111H\f\u0002\u0002\u0003\u0007Q,A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u0003\u0002b!a\u0011\u0002J\u0005MRBAA#\u0015\r\t9\u0005N\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA&\u0003\u000b\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011\u0011KA,!\r\u0019\u00141K\u0005\u0004\u0003+\"$a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003wI\u0012\u0011!a\u0001\u0003g\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qDA/\u0011!\tYDGA\u0001\u0002\u0004i\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003u\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003?\ta!Z9vC2\u001cH\u0003BA)\u0003WB\u0011\"a\u000f\u001e\u0003\u0003\u0005\r!a\r\u0002\u0013M\u0013fKU3d_J$\u0007CA\u0018 '\u0015y\u00121OA=!\r\u0019\u0014QO\u0005\u0004\u0003o\"$AB!osJ+g\r\u0005\u0003\u0002|\u0005}TBAA?\u0015\rI\u0013qE\u0005\u0004\u0005\u0006uDCAA8\u0003%\u0001\u0018M]:f\u0005>$\u0017\u0010F\u0006j\u0003\u000f\u000bI)a#\u0002$\u00065\u0006\"\u0002#\"\u0001\u00041\u0005\"\u0002)\"\u0001\u0004\u0011\u0006bBAGC\u0001\u0007\u0011qR\u0001\u0007Y\u0016tw\r\u001e5\u0011\u0007M\n\t*C\u0002\u0002\u0014R\u0012Qa\u00155peRDC!a#\u0002\u0018B!\u0011\u0011TAP\u001b\t\tYJC\u0002\u0002\u001e*\nA!\u001e;jY&!\u0011\u0011UAN\u0005\u0019)h.^:fI\"9\u0011QU\u0011A\u0002\u0005\u001d\u0016AA5u!\u0011\tI*!+\n\t\u0005-\u00161\u0014\u0002\r\u0005f$X-\u0013;fe\u0006$xN\u001d\u0005\b\u0003_\u000b\u0003\u0019AAY\u0003\ri7o\u001a\t\u0005\u00033\u000b\u0019,\u0003\u0003\u00026\u0006m%A\u0003\"zi\u0016\u001cFO]5oO\"\u001a\u0011%!/\u0011\t\u0005m\u0016qX\u0007\u0003\u0003{S1!a\u0001+\u0013\u0011\t\t-!0\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0006CB\u0004H.\u001f\u000b\u000eS\u0006\u001d\u0017\u0011ZAf\u0003\u001b\fy-!5\t\u000b\u0011\u0013\u0003\u0019\u0001$\t\u000bA\u0013\u0003\u0019\u0001*\t\u000bm\u0013\u0003\u0019A/\t\u000b\u0005\u0014\u0003\u0019A/\t\u000b\r\u0014\u0003\u0019A/\t\u000b\u0015\u0014\u0003\u0019\u0001$\u0002\u000fUt\u0017\r\u001d9msR!\u0011q[Ar!\u0015\u0019\u0014\u0011\\Ao\u0013\r\tY\u000e\u000e\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0013M\nyN\u0012*^;v3\u0015bAAqi\t1A+\u001e9mKZB\u0001\"!:$\u0003\u0003\u0005\r![\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAv!\u0011\t\t#!<\n\t\u0005=\u00181\u0005\u0002\u0007\u001f\nTWm\u0019;)\u0007}\tI\fK\u0002\u001f\u0003s\u0003")
public final class SRVRecord
extends ResourceRecord
implements Product,
Serializable {
    private final int priority;
    private final int weight;
    private final int port;
    private final String target;

    public static Option<Tuple6<String, CachePolicy.Ttl, Object, Object, Object, String>> unapply(SRVRecord x$0) {
        return SRVRecord$.MODULE$.unapply(x$0);
    }

    public static SRVRecord apply(String name, CachePolicy.Ttl ttl, int priority, int weight, int port, String target) {
        return SRVRecord$.MODULE$.apply(name, ttl, priority, weight, port, target);
    }

    @InternalApi
    public static SRVRecord parseBody(String name, CachePolicy.Ttl ttl, short length, ByteIterator it, ByteString msg) {
        return SRVRecord$.MODULE$.parseBody(name, ttl, length, it, msg);
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

    public int priority() {
        return this.priority;
    }

    public int weight() {
        return this.weight;
    }

    public int port() {
        return this.port;
    }

    public String target() {
        return this.target;
    }

    public SRVRecord copy(String name, CachePolicy.Ttl ttl, int priority, int weight, int port, String target) {
        return new SRVRecord(name, ttl, priority, weight, port, target);
    }

    public String copy$default$1() {
        return this.name();
    }

    public CachePolicy.Ttl copy$default$2() {
        return this.ttl();
    }

    public int copy$default$3() {
        return this.priority();
    }

    public int copy$default$4() {
        return this.weight();
    }

    public int copy$default$5() {
        return this.port();
    }

    public String copy$default$6() {
        return this.target();
    }

    @Override
    public String productPrefix() {
        return "SRVRecord";
    }

    @Override
    public int productArity() {
        return 6;
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
                return BoxesRunTime.boxToInteger(this.priority());
            }
            case 3: {
                return BoxesRunTime.boxToInteger(this.weight());
            }
            case 4: {
                return BoxesRunTime.boxToInteger(this.port());
            }
            case 5: {
                return this.target();
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
        return x$1 instanceof SRVRecord;
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
                return "priority";
            }
            case 3: {
                return "weight";
            }
            case 4: {
                return "port";
            }
            case 5: {
                return "target";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.name()));
        n = Statics.mix(n, Statics.anyHash(this.ttl()));
        n = Statics.mix(n, this.priority());
        n = Statics.mix(n, this.weight());
        n = Statics.mix(n, this.port());
        n = Statics.mix(n, Statics.anyHash(this.target()));
        return Statics.finalizeHash(n, 6);
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
        if (!(object instanceof SRVRecord)) return false;
        boolean bl = true;
        if (!bl) return false;
        SRVRecord sRVRecord = (SRVRecord)x$1;
        if (this.priority() != sRVRecord.priority()) return false;
        if (this.weight() != sRVRecord.weight()) return false;
        if (this.port() != sRVRecord.port()) return false;
        String string = this.name();
        String string2 = sRVRecord.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        CachePolicy.Ttl ttl = this.ttl();
        CachePolicy.Ttl ttl2 = sRVRecord.ttl();
        if (ttl == null) {
            if (ttl2 != null) {
                return false;
            }
        } else if (!((Object)ttl).equals(ttl2)) return false;
        String string3 = this.target();
        String string4 = sRVRecord.target();
        if (string3 == null) {
            if (string4 == null) return true;
            return false;
        } else {
            if (!string3.equals(string4)) return false;
            return true;
        }
    }

    public SRVRecord(String name, CachePolicy.Ttl ttl, int priority, int weight, int port, String target) {
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.target = target;
        super(name, ttl, RecordType$.MODULE$.SRV().code(), RecordClass$.MODULE$.IN().code());
        Product.$init$(this);
    }
}

