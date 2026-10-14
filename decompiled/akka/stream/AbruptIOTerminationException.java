/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.AbruptIOTerminationException$;
import akka.stream.IOResult;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005]d\u0001\u0002\r\u001a\u0005zA\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0005\u0002\u0011\t\u0012)A\u0005}!A1\t\u0001BK\u0002\u0013\u0005A\t\u0003\u0005I\u0001\tE\t\u0015!\u0003F\u0011\u0015I\u0005\u0001\"\u0001K\u0011\u001dq\u0005!!A\u0005\u0002=CqA\u0015\u0001\u0012\u0002\u0013\u00051\u000bC\u0004_\u0001E\u0005I\u0011A0\t\u000f\u0005\u0004\u0011\u0011!C!E\"91\u000eAA\u0001\n\u0003a\u0007b\u00029\u0001\u0003\u0003%\t!\u001d\u0005\bo\u0002\t\t\u0011\"\u0011y\u0011!y\b!!A\u0005\u0002\u0005\u0005\u0001\"CA\u0006\u0001\u0005\u0005I\u0011IA\u0007\u0011%\t\t\u0002AA\u0001\n\u0003\n\u0019\u0002C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0011\u0002\u0018\u001dI\u0011qF\r\u0002\u0002#\u0005\u0011\u0011\u0007\u0004\t1e\t\t\u0011#\u0001\u00024!1\u0011J\u0005C\u0001\u0003\u0017B\u0011\"!\u0014\u0013\u0003\u0003%)%a\u0014\t\u0013\u0005E##!A\u0005\u0002\u0006M\u0003\"CA-%\u0005\u0005I\u0011QA.\u0011%\tiGEA\u0001\n\u0013\tyG\u0001\u000fBEJ,\b\u000f^%P)\u0016\u0014X.\u001b8bi&|g.\u0012=dKB$\u0018n\u001c8\u000b\u0005iY\u0012AB:ue\u0016\fWNC\u0001\u001d\u0003\u0011\t7n[1\u0004\u0001M)\u0001aH\u00176sA\u0011\u0001E\u000b\b\u0003C\u001dr!AI\u0013\u000e\u0003\rR!\u0001J\u000f\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0013!B:dC2\f\u0017B\u0001\u0015*\u0003\u001d\u0001\u0018mY6bO\u0016T\u0011AJ\u0005\u0003W1\u0012\u0001CU;oi&lW-\u0012=dKB$\u0018n\u001c8\u000b\u0005!J\u0003C\u0001\u00184\u001b\u0005y#B\u0001\u00192\u0003\u001d\u0019wN\u001c;s_2T!AM\u0015\u0002\tU$\u0018\u000e\\\u0005\u0003i=\u0012ABT8Ti\u0006\u001c7\u000e\u0016:bG\u0016\u0004\"AN\u001c\u000e\u0003%J!\u0001O\u0015\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001EO\u0005\u0003w1\u0012AbU3sS\u0006d\u0017N_1cY\u0016\f\u0001\"[8SKN,H\u000e^\u000b\u0002}A\u0011q\bQ\u0007\u00023%\u0011\u0011)\u0007\u0002\t\u0013>\u0013Vm];mi\u0006I\u0011n\u001c*fgVdG\u000fI\u0001\u0006G\u0006,8/Z\u000b\u0002\u000bB\u0011\u0001ER\u0005\u0003\u000f2\u0012\u0011\u0002\u00165s_^\f'\r\\3\u0002\r\r\fWo]3!\u0003\u0019a\u0014N\\5u}Q\u00191\nT'\u0011\u0005}\u0002\u0001\"\u0002\u001f\u0006\u0001\u0004q\u0004\"B\"\u0006\u0001\u0004)\u0015\u0001B2paf$2a\u0013)R\u0011\u001dad\u0001%AA\u0002yBqa\u0011\u0004\u0011\u0002\u0003\u0007Q)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003QS#AP+,\u0003Y\u0003\"a\u0016/\u000e\u0003aS!!\u0017.\u0002\u0013Ut7\r[3dW\u0016$'BA.*\u0003)\tgN\\8uCRLwN\\\u0005\u0003;b\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012\u0001\u0019\u0016\u0003\u000bV\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A2\u0011\u0005\u0011LW\"A3\u000b\u0005\u0019<\u0017\u0001\u00027b]\u001eT\u0011\u0001[\u0001\u0005U\u00064\u0018-\u0003\u0002kK\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012!\u001c\t\u0003m9L!a\\\u0015\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005I,\bC\u0001\u001ct\u0013\t!\u0018FA\u0002B]fDqA^\u0006\u0002\u0002\u0003\u0007Q.A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002sB\u0019!0 :\u000e\u0003mT!\u0001`\u0015\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002\u007fw\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t\u0019!!\u0003\u0011\u0007Y\n)!C\u0002\u0002\b%\u0012qAQ8pY\u0016\fg\u000eC\u0004w\u001b\u0005\u0005\t\u0019\u0001:\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004G\u0006=\u0001b\u0002<\u000f\u0003\u0003\u0005\r!\\\u0001\tQ\u0006\u001c\bnQ8eKR\tQ.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u0007\tI\u0002C\u0004w!\u0005\u0005\t\u0019\u0001:)\u0017\u0001\ti\"a\t\u0002&\u0005%\u00121\u0006\t\u0004m\u0005}\u0011bAA\u0011S\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012\u0011qE\u0001#kN,\u0007%S(Pa\u0016\u0014\u0018\r^5p]&s7m\\7qY\u0016$X-\u0012=dKB$\u0018n\u001c8\u0002\u000bMLgnY3\"\u0005\u00055\u0012!\u0002\u001a/m9\u0002\u0014\u0001H!ceV\u0004H/S(UKJl\u0017N\\1uS>tW\t_2faRLwN\u001c\t\u0003\u007fI\u0019RAEA\u001b\u0003\u0003\u0002r!a\u000e\u0002>y*5*\u0004\u0002\u0002:)\u0019\u00111H\u0015\u0002\u000fI,h\u000e^5nK&!\u0011qHA\u001d\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\t\u0005\u0003\u0007\nI%\u0004\u0002\u0002F)\u0019\u0011qI4\u0002\u0005%|\u0017bA\u001e\u0002FQ\u0011\u0011\u0011G\u0001\ti>\u001cFO]5oOR\t1-A\u0003baBd\u0017\u0010F\u0003L\u0003+\n9\u0006C\u0003=+\u0001\u0007a\bC\u0003D+\u0001\u0007Q)A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005u\u0013\u0011\u000e\t\u0006m\u0005}\u00131M\u0005\u0004\u0003CJ#AB(qi&|g\u000eE\u00037\u0003KrT)C\u0002\u0002h%\u0012a\u0001V;qY\u0016\u0014\u0004\u0002CA6-\u0005\u0005\t\u0019A&\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002rA\u0019A-a\u001d\n\u0007\u0005UTM\u0001\u0004PE*,7\r\u001e")
public final class AbruptIOTerminationException
extends RuntimeException
implements NoStackTrace,
Product {
    private final IOResult ioResult;
    private final Throwable cause;

    public static Option<Tuple2<IOResult, Throwable>> unapply(AbruptIOTerminationException x$0) {
        return AbruptIOTerminationException$.MODULE$.unapply(x$0);
    }

    public static AbruptIOTerminationException apply(IOResult ioResult, Throwable cause) {
        return AbruptIOTerminationException$.MODULE$.apply(ioResult, cause);
    }

    public static Function1<Tuple2<IOResult, Throwable>, AbruptIOTerminationException> tupled() {
        return AbruptIOTerminationException$.MODULE$.tupled();
    }

    public static Function1<IOResult, Function1<Throwable, AbruptIOTerminationException>> curried() {
        return AbruptIOTerminationException$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public IOResult ioResult() {
        return this.ioResult;
    }

    public Throwable cause() {
        return this.cause;
    }

    public AbruptIOTerminationException copy(IOResult ioResult, Throwable cause) {
        return new AbruptIOTerminationException(ioResult, cause);
    }

    public IOResult copy$default$1() {
        return this.ioResult();
    }

    public Throwable copy$default$2() {
        return this.cause();
    }

    @Override
    public String productPrefix() {
        return "AbruptIOTerminationException";
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
                return this.ioResult();
            }
            case 1: {
                return this.cause();
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
        return x$1 instanceof AbruptIOTerminationException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "ioResult";
            }
            case 1: {
                return "cause";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof AbruptIOTerminationException)) return false;
        boolean bl = true;
        if (!bl) return false;
        AbruptIOTerminationException abruptIOTerminationException = (AbruptIOTerminationException)x$1;
        IOResult iOResult = this.ioResult();
        IOResult iOResult2 = abruptIOTerminationException.ioResult();
        if (iOResult == null) {
            if (iOResult2 != null) {
                return false;
            }
        } else if (!((Object)iOResult).equals(iOResult2)) return false;
        Throwable throwable = this.cause();
        Throwable throwable2 = abruptIOTerminationException.cause();
        if (throwable == null) {
            if (throwable2 == null) return true;
            return false;
        } else {
            if (!throwable.equals(throwable2)) return false;
            return true;
        }
    }

    public AbruptIOTerminationException(IOResult ioResult, Throwable cause) {
        this.ioResult = ioResult;
        this.cause = cause;
        super("Stream terminated without completing IO operation.", cause);
        NoStackTrace.$init$(this);
        Product.$init$(this);
    }
}

