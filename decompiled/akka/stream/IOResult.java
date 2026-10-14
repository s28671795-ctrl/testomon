/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.Done;
import akka.stream.IOResult$;
import java.io.Serializable;
import scala.MatchError;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005ef\u0001\u0002\u0011\"\u0005\u001aB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005}!A!\t\u0001BK\u0002\u0013\u00051\t\u0003\u0005Y\u0001\tE\t\u0015!\u0003E\u0011\u0015Q\u0006\u0001\"\u0001\\\u0011\u0015\u0001\u0007\u0001\"\u0001b\u0011\u0015!\u0007\u0001\"\u0001f\u0011\u0015A\u0007\u0001\"\u0001>\u0011\u0015I\u0007\u0001\"\u0001k\u0011\u0015y\u0007\u0001\"\u0001q\u0011\u001d)\b!!A\u0005\u0002YDq!\u001f\u0001\u0012\u0002\u0013\u0005!\u0010C\u0005\u0002\f\u0001\t\n\u0011\"\u0001\u0002\u000e!I\u0011\u0011\u0003\u0001\u0002\u0002\u0013\u0005\u00131\u0003\u0005\n\u0003K\u0001\u0011\u0011!C\u0001\u0003OA\u0011\"a\f\u0001\u0003\u0003%\t!!\r\t\u0013\u0005u\u0002!!A\u0005B\u0005}\u0002\"CA'\u0001\u0005\u0005I\u0011AA(\u0011%\t\u0019\u0006AA\u0001\n\u0003\n)\u0006C\u0005\u0002Z\u0001\t\t\u0011\"\u0011\u0002\\!I\u0011Q\f\u0001\u0002\u0002\u0013\u0005\u0013q\f\u0005\n\u0003C\u0002\u0011\u0011!C!\u0003G:q!a\u001a\"\u0011\u0003\tIG\u0002\u0004!C!\u0005\u00111\u000e\u0005\u00075b!\t!a\u001e\t\u000f\u0005e\u0004\u0004\"\u0001\u0002|!9\u0011q\u0010\r\u0005\u0002\u0005\u0005\u0005bBAC1\u0011\u0005\u0011q\u0011\u0005\n\u0003sB\u0012\u0011!CA\u0003+C\u0011\"a'\u0019\u0003\u0003%\t)!(\t\u0013\u0005=\u0006$!A\u0005\n\u0005E&\u0001C%P%\u0016\u001cX\u000f\u001c;\u000b\u0005\t\u001a\u0013AB:ue\u0016\fWNC\u0001%\u0003\u0011\t7n[1\u0004\u0001M!\u0001aJ\u00171!\tA3&D\u0001*\u0015\u0005Q\u0013!B:dC2\f\u0017B\u0001\u0017*\u0005\u0019\te.\u001f*fMB\u0011\u0001FL\u0005\u0003_%\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00022s9\u0011!g\u000e\b\u0003gYj\u0011\u0001\u000e\u0006\u0003k\u0015\na\u0001\u0010:p_Rt\u0014\"\u0001\u0016\n\u0005aJ\u0013a\u00029bG.\fw-Z\u0005\u0003um\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001O\u0015\u0002\u000b\r|WO\u001c;\u0016\u0003y\u0002\"\u0001K \n\u0005\u0001K#\u0001\u0002'p]\u001e\faaY8v]R\u0004\u0013AB:uCR,8/F\u0001E!\r)\u0005JS\u0007\u0002\r*\u0011q)K\u0001\u0005kRLG.\u0003\u0002J\r\n\u0019AK]=\u0011\u0005-cU\"A\u0012\n\u00055\u001b#\u0001\u0002#p]\u0016DcaA(S'V3\u0006C\u0001\u0015Q\u0013\t\t\u0016F\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001U\u0003\u0015\u001aH/\u0019;vg\u0002J7\u000fI1mo\u0006L8\u000fI:fi\u0002\"x\u000eI*vG\u000e,7o\u001d\u0015E_:,\u0017&A\u0003tS:\u001cW-I\u0001X\u0003\u0015\u0011dF\u000e\u00181\u0003\u001d\u0019H/\u0019;vg\u0002Bc\u0001B(S'V3\u0016A\u0002\u001fj]&$h\bF\u0002]=~\u0003\"!\u0018\u0001\u000e\u0003\u0005BQ\u0001P\u0003A\u0002yBQAQ\u0003A\u0002\u0011\u000b\u0011b^5uQ\u000e{WO\u001c;\u0015\u0005q\u0013\u0007\"B2\u0007\u0001\u0004q\u0014!\u0002<bYV,\u0017AC<ji\"\u001cF/\u0019;vgR\u0011AL\u001a\u0005\u0006G\u001e\u0001\r\u0001\u0012\u0015\u0007\u000f=\u00136+\u0016,\u0002\u0011\u001d,GoQ8v]R\fQb^1t'V\u001c7-Z:tMVdW#A6\u0011\u0005!b\u0017BA7*\u0005\u001d\u0011un\u001c7fC:Dc!C(S'V3\u0016\u0001C4fi\u0016\u0013(o\u001c:\u0016\u0003E\u0004\"!\r:\n\u0005M\\$!\u0003+ie><\u0018M\u00197fQ\u0019QqJU*V-\u0006!1m\u001c9z)\rav\u000f\u001f\u0005\by-\u0001\n\u00111\u0001?\u0011\u001d\u00115\u0002%AA\u0002\u0011\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0001|U\tqDpK\u0001~!\rq\u0018qA\u0007\u0002\u007f*!\u0011\u0011AA\u0002\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0006%\n!\"\u00198o_R\fG/[8o\u0013\r\tIa \u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003\u001fQ#\u0001\u0012?\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\t)\u0002\u0005\u0003\u0002\u0018\u0005\u0005RBAA\r\u0015\u0011\tY\"!\b\u0002\t1\fgn\u001a\u0006\u0003\u0003?\tAA[1wC&!\u00111EA\r\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011\u0011\u0006\t\u0004Q\u0005-\u0012bAA\u0017S\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u00111GA\u001d!\rA\u0013QG\u0005\u0004\u0003oI#aA!os\"I\u00111\b\t\u0002\u0002\u0003\u0007\u0011\u0011F\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\u0005\u0003CBA\"\u0003\u0013\n\u0019$\u0004\u0002\u0002F)\u0019\u0011qI\u0015\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002L\u0005\u0015#\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2a[A)\u0011%\tYDEA\u0001\u0002\u0004\t\u0019$\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u000b\u0003/B\u0011\"a\u000f\u0014\u0003\u0003\u0005\r!!\u000b\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!\u000b\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0006\u0002\r\u0015\fX/\u00197t)\rY\u0017Q\r\u0005\n\u0003w1\u0012\u0011!a\u0001\u0003g\t\u0001\"S(SKN,H\u000e\u001e\t\u0003;b\u0019B\u0001G\u0014\u0002nA!\u0011qNA;\u001b\t\t\tH\u0003\u0003\u0002t\u0005u\u0011AA5p\u0013\rQ\u0014\u0011\u000f\u000b\u0003\u0003S\nQ!\u00199qYf$2\u0001XA?\u0011\u0015a$\u00041\u0001?\u0003A\u0019'/Z1uKN+8mY3tg\u001a,H\u000eF\u0002]\u0003\u0007CQ\u0001P\u000eA\u0002y\nAb\u0019:fCR,g)Y5mK\u0012$R\u0001XAE\u0003\u0017CQ\u0001\u0010\u000fA\u0002yBa!!$\u001d\u0001\u0004\t\u0018AA3yQ\u001darJUAI+Z\u000b#!a%\u0002EU\u001cX\rI%P\u001fB,'/\u0019;j_:LenY8na2,G/Z#yG\u0016\u0004H/[8o)\u0015a\u0016qSAM\u0011\u0015aT\u00041\u0001?\u0011\u0015\u0011U\u00041\u0001E\u0003\u001d)h.\u00199qYf$B!a(\u0002,B)\u0001&!)\u0002&&\u0019\u00111U\u0015\u0003\r=\u0003H/[8o!\u0015A\u0013q\u0015 E\u0013\r\tI+\u000b\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u00055f$!AA\u0002q\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\u0019\f\u0005\u0003\u0002\u0018\u0005U\u0016\u0002BA\\\u00033\u0011aa\u00142kK\u000e$\b")
public final class IOResult
implements Product,
Serializable {
    private final long count;
    private final Try<Done> status;

    public static Option<Tuple2<Object, Try<Done>>> unapply(IOResult x$0) {
        return IOResult$.MODULE$.unapply(x$0);
    }

    public static IOResult apply(long count, Try<Done> status) {
        return IOResult$.MODULE$.apply(count, status);
    }

    public static IOResult createFailed(long count, Throwable ex) {
        return IOResult$.MODULE$.createFailed(count, ex);
    }

    public static IOResult createSuccessful(long count) {
        return IOResult$.MODULE$.createSuccessful(count);
    }

    public static IOResult apply(long count) {
        return IOResult$.MODULE$.apply(count);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long count() {
        return this.count;
    }

    public Try<Done> status() {
        return this.status;
    }

    public IOResult withCount(long value) {
        return this.copy(value, this.copy$default$2());
    }

    public IOResult withStatus(Try<Done> value) {
        Try<Done> x$1 = value;
        long x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public long getCount() {
        return this.count();
    }

    public boolean wasSuccessful() {
        return this.status().isSuccess();
    }

    public Throwable getError() {
        Try<Done> try_ = this.status();
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable t2 = failure.exception();
            return t2;
        }
        if (try_ instanceof Success) {
            throw new UnsupportedOperationException("IO operation was successful.");
        }
        throw new MatchError(try_);
    }

    public IOResult copy(long count, Try<Done> status) {
        return new IOResult(count, status);
    }

    public long copy$default$1() {
        return this.count();
    }

    public Try<Done> copy$default$2() {
        return this.status();
    }

    @Override
    public String productPrefix() {
        return "IOResult";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToLong(this.count());
            }
            case 1: {
                return this.status();
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
        return x$1 instanceof IOResult;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "count";
            }
            case 1: {
                return "status";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.longHash(this.count()));
        n = Statics.mix(n, Statics.anyHash(this.status()));
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof IOResult)) return false;
        boolean bl = true;
        if (!bl) return false;
        IOResult iOResult = (IOResult)x$1;
        if (this.count() != iOResult.count()) return false;
        Try<Done> try_ = this.status();
        Try<Done> try_2 = iOResult.status();
        if (try_ != null) {
            if (!try_.equals(try_2)) return false;
            return true;
        }
        if (try_2 == null) return true;
        return false;
    }

    public IOResult(long count, Try<Done> status) {
        this.count = count;
        this.status = status;
        Product.$init$(this);
    }
}

