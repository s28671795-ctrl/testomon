/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.InvalidMessageException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015c\u0001B\u000b\u0017\u0005nA\u0001B\r\u0001\u0003\u0016\u0004%\ta\r\u0005\ty\u0001\u0011\t\u0012)A\u0005i!1Q\b\u0001C\u00011yBqA\u0011\u0001\u0002\u0002\u0013\u00051\tC\u0004F\u0001E\u0005I\u0011\u0001$\t\u000fE\u0003\u0011\u0011!C!%\"9!\fAA\u0001\n\u0003Y\u0006bB0\u0001\u0003\u0003%\t\u0001\u0019\u0005\bM\u0002\t\t\u0011\"\u0011h\u0011\u001dq\u0007!!A\u0005\u0002=Dq\u0001\u001e\u0001\u0002\u0002\u0013\u0005S\u000fC\u0004x\u0001\u0005\u0005I\u0011\t=\t\u000fe\u0004\u0011\u0011!C!u\u001eI\u0011Q\u0001\f\u0002\u0002#\u0005\u0011q\u0001\u0004\t+Y\t\t\u0011#\u0001\u0002\n!1Qh\u0004C\u0001\u0003CA\u0011\"a\t\u0010\u0003\u0003%)%!\n\t\u0013\u0005\u001dr\"!A\u0005\u0002\u0006%\u0002\"CA\u0017\u001f\u0005\u0005I\u0011QA\u0018\u0011%\tYdDA\u0001\n\u0013\tiDA\fJ]Z\fG.\u001b3NKN\u001c\u0018mZ3Fq\u000e,\u0007\u000f^5p]*\u0011q\u0003G\u0001\u0006C\u000e$xN\u001d\u0006\u00023\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001\u0001\u000f!MA\u0011QDH\u0007\u00021%\u0011q\u0004\u0007\u0002\u000e\u0003.\\\u0017-\u0012=dKB$\u0018n\u001c8\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0003\r\nQa]2bY\u0006L!!\n\u0012\u0003\u000fA\u0013x\u000eZ;diB\u0011qe\f\b\u0003Q5r!!\u000b\u0017\u000e\u0003)R!a\u000b\u000e\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0019\u0013B\u0001\u0018#\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001M\u0019\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u00059\u0012\u0013aB7fgN\fw-Z\u000b\u0002iA\u0011Q'\u000f\b\u0003m]\u0002\"!\u000b\u0012\n\u0005a\u0012\u0013A\u0002)sK\u0012,g-\u0003\u0002;w\t11\u000b\u001e:j]\u001eT!\u0001\u000f\u0012\u0002\u00115,7o]1hK\u0002\na\u0001P5oSRtDCA B!\t\u0001\u0005!D\u0001\u0017\u0011\u0015\u00114\u00011\u00015\u0003\u0011\u0019w\u000e]=\u0015\u0005}\"\u0005b\u0002\u001a\u0005!\u0003\u0005\r\u0001N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u00059%F\u0001\u001bIW\u0005I\u0005C\u0001&P\u001b\u0005Y%B\u0001'N\u0003%)hn\u00195fG.,GM\u0003\u0002OE\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005A[%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u0015\t\u0003)fk\u0011!\u0016\u0006\u0003-^\u000bA\u0001\\1oO*\t\u0001,\u0001\u0003kCZ\f\u0017B\u0001\u001eV\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005a\u0006CA\u0011^\u0013\tq&EA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002bIB\u0011\u0011EY\u0005\u0003G\n\u00121!\u00118z\u0011\u001d)\u0007\"!AA\u0002q\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u00015\u0011\u0007%d\u0017-D\u0001k\u0015\tY'%\u0001\u0006d_2dWm\u0019;j_:L!!\u001c6\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003aN\u0004\"!I9\n\u0005I\u0014#a\u0002\"p_2,\u0017M\u001c\u0005\bK*\t\t\u00111\u0001b\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005M3\bbB3\f\u0003\u0003\u0005\r\u0001X\u0001\tQ\u0006\u001c\bnQ8eKR\tA,\u0001\u0004fcV\fGn\u001d\u000b\u0003anDq!Z\u0007\u0002\u0002\u0003\u0007\u0011\r\u000b\u0004\u0001{\u0006\u0005\u00111\u0001\t\u0003CyL!a \u0012\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002/%sg/\u00197jI6+7o]1hK\u0016C8-\u001a9uS>t\u0007C\u0001!\u0010'\u0015y\u00111BA\f!\u0019\ti!a\u00055\u007f5\u0011\u0011q\u0002\u0006\u0004\u0003#\u0011\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003+\tyAA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!!\u0007\u0002 5\u0011\u00111\u0004\u0006\u0004\u0003;9\u0016AA5p\u0013\r\u0001\u00141\u0004\u000b\u0003\u0003\u000f\t\u0001\u0002^8TiJLgn\u001a\u000b\u0002'\u0006)\u0011\r\u001d9msR\u0019q(a\u000b\t\u000bI\u0012\u0002\u0019\u0001\u001b\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011GA\u001c!\u0011\t\u00131\u0007\u001b\n\u0007\u0005U\"E\u0001\u0004PaRLwN\u001c\u0005\t\u0003s\u0019\u0012\u0011!a\u0001\u007f\u0005\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005}\u0002c\u0001+\u0002B%\u0019\u00111I+\u0003\r=\u0013'.Z2u\u0001")
public final class InvalidMessageException
extends AkkaException
implements Product {
    private static final long serialVersionUID = 1L;
    private final String message;

    public static Option<String> unapply(InvalidMessageException x$0) {
        return InvalidMessageException$.MODULE$.unapply(x$0);
    }

    public static InvalidMessageException apply(String message) {
        return InvalidMessageException$.MODULE$.apply(message);
    }

    public static <A> Function1<String, A> andThen(Function1<InvalidMessageException, A> g2) {
        return InvalidMessageException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, InvalidMessageException> compose(Function1<A, String> g2) {
        return InvalidMessageException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String message() {
        return this.message;
    }

    public InvalidMessageException copy(String message) {
        return new InvalidMessageException(message);
    }

    public String copy$default$1() {
        return this.message();
    }

    @Override
    public String productPrefix() {
        return "InvalidMessageException";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.message();
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
        return x$1 instanceof InvalidMessageException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "message";
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
        if (!(object instanceof InvalidMessageException)) return false;
        boolean bl = true;
        if (!bl) return false;
        InvalidMessageException invalidMessageException = (InvalidMessageException)x$1;
        String string = this.message();
        String string2 = invalidMessageException.message();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public InvalidMessageException(String message) {
        this.message = message;
        super(message);
        Product.$init$(this);
    }
}

