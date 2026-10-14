/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.IllegalActorStateException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015c\u0001B\u000b\u0017\u0005nA\u0001B\r\u0001\u0003\u0016\u0004%\ta\r\u0005\ty\u0001\u0011\t\u0012)A\u0005i!1Q\b\u0001C\u00011yBqA\u0011\u0001\u0002\u0002\u0013\u00051\tC\u0004F\u0001E\u0005I\u0011\u0001$\t\u000fE\u0003\u0011\u0011!C!%\"9!\fAA\u0001\n\u0003Y\u0006bB0\u0001\u0003\u0003%\t\u0001\u0019\u0005\bM\u0002\t\t\u0011\"\u0011h\u0011\u001dq\u0007!!A\u0005\u0002=Dq\u0001\u001e\u0001\u0002\u0002\u0013\u0005S\u000fC\u0004x\u0001\u0005\u0005I\u0011\t=\t\u000fe\u0004\u0011\u0011!C!u\u001eI\u0011Q\u0001\f\u0002\u0002#\u0005\u0011q\u0001\u0004\t+Y\t\t\u0011#\u0001\u0002\n!1Qh\u0004C\u0001\u0003CA\u0011\"a\t\u0010\u0003\u0003%)%!\n\t\u0013\u0005\u001dr\"!A\u0005\u0002\u0006%\u0002\"CA\u0017\u001f\u0005\u0005I\u0011QA\u0018\u0011%\tYdDA\u0001\n\u0013\tiD\u0001\u000eJY2,w-\u00197BGR|'o\u0015;bi\u0016,\u0005pY3qi&|gN\u0003\u0002\u00181\u0005)\u0011m\u0019;pe*\t\u0011$\u0001\u0003bW.\f7\u0001A\n\u0005\u0001q\u0001c\u0005\u0005\u0002\u001e=5\t\u0001$\u0003\u0002 1\ti\u0011i[6b\u000bb\u001cW\r\u001d;j_:\u0004\"!\t\u0013\u000e\u0003\tR\u0011aI\u0001\u0006g\u000e\fG.Y\u0005\u0003K\t\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002(_9\u0011\u0001&\f\b\u0003S1j\u0011A\u000b\u0006\u0003Wi\ta\u0001\u0010:p_Rt\u0014\"A\u0012\n\u00059\u0012\u0013a\u00029bG.\fw-Z\u0005\u0003aE\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!A\f\u0012\u0002\u000f5,7o]1hKV\tA\u0007\u0005\u00026s9\u0011ag\u000e\t\u0003S\tJ!\u0001\u000f\u0012\u0002\rA\u0013X\rZ3g\u0013\tQ4H\u0001\u0004TiJLgn\u001a\u0006\u0003q\t\n\u0001\"\\3tg\u0006<W\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005}\n\u0005C\u0001!\u0001\u001b\u00051\u0002\"\u0002\u001a\u0004\u0001\u0004!\u0014\u0001B2paf$\"a\u0010#\t\u000fI\"\u0001\u0013!a\u0001i\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A$+\u0005QB5&A%\u0011\u0005){U\"A&\u000b\u00051k\u0015!C;oG\",7m[3e\u0015\tq%%\u0001\u0006b]:|G/\u0019;j_:L!\u0001U&\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002'B\u0011A+W\u0007\u0002+*\u0011akV\u0001\u0005Y\u0006twMC\u0001Y\u0003\u0011Q\u0017M^1\n\u0005i*\u0016\u0001\u00049s_\u0012,8\r^!sSRLX#\u0001/\u0011\u0005\u0005j\u0016B\u00010#\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t\tG\r\u0005\u0002\"E&\u00111M\t\u0002\u0004\u0003:L\bbB3\t\u0003\u0003\u0005\r\u0001X\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003!\u00042!\u001b7b\u001b\u0005Q'BA6#\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003[*\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011\u0001o\u001d\t\u0003CEL!A\u001d\u0012\u0003\u000f\t{w\u000e\\3b]\"9QMCA\u0001\u0002\u0004\t\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"a\u0015<\t\u000f\u0015\\\u0011\u0011!a\u00019\u0006A\u0001.Y:i\u0007>$W\rF\u0001]\u0003\u0019)\u0017/^1mgR\u0011\u0001o\u001f\u0005\bK6\t\t\u00111\u0001bQ\u0019\u0001Q0!\u0001\u0002\u0004A\u0011\u0011E`\u0005\u0003\u007f\n\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\t!$\u00137mK\u001e\fG.Q2u_J\u001cF/\u0019;f\u000bb\u001cW\r\u001d;j_:\u0004\"\u0001Q\b\u0014\u000b=\tY!a\u0006\u0011\r\u00055\u00111\u0003\u001b@\u001b\t\tyAC\u0002\u0002\u0012\t\nqA];oi&lW-\u0003\u0003\u0002\u0016\u0005=!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u0011\u0011DA\u0010\u001b\t\tYBC\u0002\u0002\u001e]\u000b!![8\n\u0007A\nY\u0002\u0006\u0002\u0002\b\u0005AAo\\*ue&tw\rF\u0001T\u0003\u0015\t\u0007\u000f\u001d7z)\ry\u00141\u0006\u0005\u0006eI\u0001\r\u0001N\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t$a\u000e\u0011\t\u0005\n\u0019\u0004N\u0005\u0004\u0003k\u0011#AB(qi&|g\u000e\u0003\u0005\u0002:M\t\t\u00111\u0001@\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u007f\u00012\u0001VA!\u0013\r\t\u0019%\u0016\u0002\u0007\u001f\nTWm\u0019;")
public final class IllegalActorStateException
extends AkkaException
implements Product {
    private static final long serialVersionUID = 1L;
    private final String message;

    public static Option<String> unapply(IllegalActorStateException x$0) {
        return IllegalActorStateException$.MODULE$.unapply(x$0);
    }

    public static IllegalActorStateException apply(String message) {
        return IllegalActorStateException$.MODULE$.apply(message);
    }

    public static <A> Function1<String, A> andThen(Function1<IllegalActorStateException, A> g2) {
        return IllegalActorStateException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, IllegalActorStateException> compose(Function1<A, String> g2) {
        return IllegalActorStateException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String message() {
        return this.message;
    }

    public IllegalActorStateException copy(String message) {
        return new IllegalActorStateException(message);
    }

    public String copy$default$1() {
        return this.message();
    }

    @Override
    public String productPrefix() {
        return "IllegalActorStateException";
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
        return x$1 instanceof IllegalActorStateException;
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
        if (!(object instanceof IllegalActorStateException)) return false;
        boolean bl = true;
        if (!bl) return false;
        IllegalActorStateException illegalActorStateException = (IllegalActorStateException)x$1;
        String string = this.message();
        String string2 = illegalActorStateException.message();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public IllegalActorStateException(String message) {
        this.message = message;
        super(message);
        Product.$init$(this);
    }
}

