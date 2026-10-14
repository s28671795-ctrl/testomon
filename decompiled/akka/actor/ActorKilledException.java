/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.ActorKilledException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005Uc\u0001B\u000b\u0017\u0005nA\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005y!1Q\t\u0001C\u00011\u0019CqA\u0013\u0001\u0002\u0002\u0013\u00051\nC\u0004N\u0001E\u0005I\u0011\u0001(\t\u000fe\u0003\u0011\u0011!C!5\"9!\rAA\u0001\n\u0003\u0019\u0007bB4\u0001\u0003\u0003%\t\u0001\u001b\u0005\b]\u0002\t\t\u0011\"\u0011p\u0011\u001d1\b!!A\u0005\u0002]Dq\u0001 \u0001\u0002\u0002\u0013\u0005S\u0010\u0003\u0005\u0000\u0001\u0005\u0005I\u0011IA\u0001\u0011%\t\u0019\u0001AA\u0001\n\u0003\n)aB\u0005\u0002\u0016Y\t\t\u0011#\u0001\u0002\u0018\u0019AQCFA\u0001\u0012\u0003\tI\u0002\u0003\u0004F\u001f\u0011\u0005\u0011\u0011\u0007\u0005\n\u0003gy\u0011\u0011!C#\u0003kA\u0011\"a\u000e\u0010\u0003\u0003%\t)!\u000f\t\u0013\u0005ur\"!A\u0005\u0002\u0006}\u0002\"CA&\u001f\u0005\u0005I\u0011BA'\u0005Q\t5\r^8s\u0017&dG.\u001a3Fq\u000e,\u0007\u000f^5p]*\u0011q\u0003G\u0001\u0006C\u000e$xN\u001d\u0006\u00023\u0005!\u0011m[6b\u0007\u0001\u0019R\u0001\u0001\u000f!U9\u0002\"!\b\u0010\u000e\u0003aI!a\b\r\u0003\u001b\u0005[7.Y#yG\u0016\u0004H/[8o!\t\t\u0003&D\u0001#\u0015\t\u0019C%A\u0004d_:$(o\u001c7\u000b\u0005\u00152\u0013\u0001B;uS2T\u0011aJ\u0001\u0006g\u000e\fG.Y\u0005\u0003S\t\u0012ABT8Ti\u0006\u001c7\u000e\u0016:bG\u0016\u0004\"a\u000b\u0017\u000e\u0003\u0019J!!\f\u0014\u0003\u000fA\u0013x\u000eZ;diB\u0011qf\u000e\b\u0003aUr!!\r\u001b\u000e\u0003IR!a\r\u000e\u0002\rq\u0012xn\u001c;?\u0013\u00059\u0013B\u0001\u001c'\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001O\u001d\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Y2\u0013aB7fgN\fw-Z\u000b\u0002yA\u0011Q(\u0011\b\u0003}}\u0002\"!\r\u0014\n\u0005\u00013\u0013A\u0002)sK\u0012,g-\u0003\u0002C\u0007\n11\u000b\u001e:j]\u001eT!\u0001\u0011\u0014\u0002\u00115,7o]1hK\u0002\na\u0001P5oSRtDCA$J!\tA\u0005!D\u0001\u0017\u0011\u0015Q4\u00011\u0001=\u0003\u0011\u0019w\u000e]=\u0015\u0005\u001dc\u0005b\u0002\u001e\u0005!\u0003\u0005\r\u0001P\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005y%F\u0001\u001fQW\u0005\t\u0006C\u0001*X\u001b\u0005\u0019&B\u0001+V\u0003%)hn\u00195fG.,GM\u0003\u0002WM\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005a\u001b&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u0017\t\u00039\u0006l\u0011!\u0018\u0006\u0003=~\u000bA\u0001\\1oO*\t\u0001-\u0001\u0003kCZ\f\u0017B\u0001\"^\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005!\u0007CA\u0016f\u0013\t1gEA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002jYB\u00111F[\u0005\u0003W\u001a\u00121!\u00118z\u0011\u001di\u0007\"!AA\u0002\u0011\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u00019\u0011\u0007E$\u0018.D\u0001s\u0015\t\u0019h%\u0001\u0006d_2dWm\u0019;j_:L!!\u001e:\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003qn\u0004\"aK=\n\u0005i4#a\u0002\"p_2,\u0017M\u001c\u0005\b[*\t\t\u00111\u0001j\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005ms\bbB7\f\u0003\u0003\u0005\r\u0001Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA-\u0001\u0004fcV\fGn\u001d\u000b\u0004q\u0006\u001d\u0001bB7\u000e\u0003\u0003\u0005\r!\u001b\u0015\b\u0001\u0005-\u0011\u0011CA\n!\rY\u0013QB\u0005\u0004\u0003\u001f1#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011\u0001F!di>\u00148*\u001b7mK\u0012,\u0005pY3qi&|g\u000e\u0005\u0002I\u001fM)q\"a\u0007\u0002(A1\u0011QDA\u0012y\u001dk!!a\b\u000b\u0007\u0005\u0005b%A\u0004sk:$\u0018.\\3\n\t\u0005\u0015\u0012q\u0004\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u0015\u0003_i!!a\u000b\u000b\u0007\u00055r,\u0001\u0002j_&\u0019\u0001(a\u000b\u0015\u0005\u0005]\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003m\u000bQ!\u00199qYf$2aRA\u001e\u0011\u0015Q$\u00031\u0001=\u0003\u001d)h.\u00199qYf$B!!\u0011\u0002HA!1&a\u0011=\u0013\r\t)E\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005%3#!AA\u0002\u001d\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ty\u0005E\u0002]\u0003#J1!a\u0015^\u0005\u0019y%M[3di\u0002")
public final class ActorKilledException
extends AkkaException
implements NoStackTrace,
Product {
    private static final long serialVersionUID = 1L;
    private final String message;

    public static Option<String> unapply(ActorKilledException x$0) {
        return ActorKilledException$.MODULE$.unapply(x$0);
    }

    public static ActorKilledException apply(String message) {
        return ActorKilledException$.MODULE$.apply(message);
    }

    public static <A> Function1<String, A> andThen(Function1<ActorKilledException, A> g2) {
        return ActorKilledException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ActorKilledException> compose(Function1<A, String> g2) {
        return ActorKilledException$.MODULE$.compose(g2);
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

    public String message() {
        return this.message;
    }

    public ActorKilledException copy(String message) {
        return new ActorKilledException(message);
    }

    public String copy$default$1() {
        return this.message();
    }

    @Override
    public String productPrefix() {
        return "ActorKilledException";
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
        return x$1 instanceof ActorKilledException;
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
        if (!(object instanceof ActorKilledException)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorKilledException actorKilledException = (ActorKilledException)x$1;
        String string = this.message();
        String string2 = actorKilledException.message();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public ActorKilledException(String message) {
        this.message = message;
        super(message);
        NoStackTrace.$init$(this);
        Product.$init$(this);
    }
}

