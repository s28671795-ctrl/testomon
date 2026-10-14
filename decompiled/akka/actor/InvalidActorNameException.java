/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.InvalidActorNameException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015c\u0001B\u000b\u0017\u0005nA\u0001B\r\u0001\u0003\u0016\u0004%\ta\r\u0005\ty\u0001\u0011\t\u0012)A\u0005i!)Q\b\u0001C\u0001}!9!\tAA\u0001\n\u0003\u0019\u0005bB#\u0001#\u0003%\tA\u0012\u0005\b#\u0002\t\t\u0011\"\u0011S\u0011\u001dQ\u0006!!A\u0005\u0002mCqa\u0018\u0001\u0002\u0002\u0013\u0005\u0001\rC\u0004g\u0001\u0005\u0005I\u0011I4\t\u000f9\u0004\u0011\u0011!C\u0001_\"9A\u000fAA\u0001\n\u0003*\bbB<\u0001\u0003\u0003%\t\u0005\u001f\u0005\bs\u0002\t\t\u0011\"\u0011{\u000f%\t)AFA\u0001\u0012\u0003\t9A\u0002\u0005\u0016-\u0005\u0005\t\u0012AA\u0005\u0011\u0019it\u0002\"\u0001\u0002\"!I\u00111E\b\u0002\u0002\u0013\u0015\u0013Q\u0005\u0005\n\u0003Oy\u0011\u0011!CA\u0003SA\u0011\"!\f\u0010\u0003\u0003%\t)a\f\t\u0013\u0005mr\"!A\u0005\n\u0005u\"!G%om\u0006d\u0017\u000eZ!di>\u0014h*Y7f\u000bb\u001cW\r\u001d;j_:T!a\u0006\r\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003e\tA!Y6lC\u000e\u00011\u0003\u0002\u0001\u001dA\u0019\u0002\"!\b\u0010\u000e\u0003aI!a\b\r\u0003\u001b\u0005[7.Y#yG\u0016\u0004H/[8o!\t\tC%D\u0001#\u0015\u0005\u0019\u0013!B:dC2\f\u0017BA\u0013#\u0005\u001d\u0001&o\u001c3vGR\u0004\"aJ\u0018\u000f\u0005!jcBA\u0015-\u001b\u0005Q#BA\u0016\u001b\u0003\u0019a$o\\8u}%\t1%\u0003\u0002/E\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u00192\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tq#%A\u0004nKN\u001c\u0018mZ3\u0016\u0003Q\u0002\"!N\u001d\u000f\u0005Y:\u0004CA\u0015#\u0013\tA$%\u0001\u0004Qe\u0016$WMZ\u0005\u0003um\u0012aa\u0015;sS:<'B\u0001\u001d#\u0003!iWm]:bO\u0016\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002@\u0003B\u0011\u0001\tA\u0007\u0002-!)!g\u0001a\u0001i\u0005!1m\u001c9z)\tyD\tC\u00043\tA\u0005\t\u0019\u0001\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tqI\u000b\u00025\u0011.\n\u0011\n\u0005\u0002K\u001f6\t1J\u0003\u0002M\u001b\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u001d\n\n!\"\u00198o_R\fG/[8o\u0013\t\u00016JA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A*\u0011\u0005QKV\"A+\u000b\u0005Y;\u0016\u0001\u00027b]\u001eT\u0011\u0001W\u0001\u0005U\u00064\u0018-\u0003\u0002;+\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tA\f\u0005\u0002\";&\u0011aL\t\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003C\u0012\u0004\"!\t2\n\u0005\r\u0014#aA!os\"9Q\rCA\u0001\u0002\u0004a\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001i!\rIG.Y\u0007\u0002U*\u00111NI\u0001\u000bG>dG.Z2uS>t\u0017BA7k\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005A\u001c\bCA\u0011r\u0013\t\u0011(EA\u0004C_>dW-\u00198\t\u000f\u0015T\u0011\u0011!a\u0001C\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\t\u0019f\u000fC\u0004f\u0017\u0005\u0005\t\u0019\u0001/\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001X\u0001\u0007KF,\u0018\r\\:\u0015\u0005A\\\bbB3\u000e\u0003\u0003\u0005\r!\u0019\u0015\u0007\u0001u\f\t!a\u0001\u0011\u0005\u0005r\u0018BA@#\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0003eIeN^1mS\u0012\f5\r^8s\u001d\u0006lW-\u0012=dKB$\u0018n\u001c8\u0011\u0005\u0001{1#B\b\u0002\f\u0005]\u0001CBA\u0007\u0003'!t(\u0004\u0002\u0002\u0010)\u0019\u0011\u0011\u0003\u0012\u0002\u000fI,h\u000e^5nK&!\u0011QCA\b\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u00033\ty\"\u0004\u0002\u0002\u001c)\u0019\u0011QD,\u0002\u0005%|\u0017b\u0001\u0019\u0002\u001cQ\u0011\u0011qA\u0001\ti>\u001cFO]5oOR\t1+A\u0003baBd\u0017\u0010F\u0002@\u0003WAQA\r\nA\u0002Q\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u00022\u0005]\u0002\u0003B\u0011\u00024QJ1!!\u000e#\u0005\u0019y\u0005\u000f^5p]\"A\u0011\u0011H\n\u0002\u0002\u0003\u0007q(A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u0010\u0011\u0007Q\u000b\t%C\u0002\u0002DU\u0013aa\u00142kK\u000e$\b")
public final class InvalidActorNameException
extends AkkaException
implements Product {
    private static final long serialVersionUID = 1L;
    private final String message;

    public static Option<String> unapply(InvalidActorNameException x$0) {
        return InvalidActorNameException$.MODULE$.unapply(x$0);
    }

    public static InvalidActorNameException apply(String message) {
        return InvalidActorNameException$.MODULE$.apply(message);
    }

    public static <A> Function1<String, A> andThen(Function1<InvalidActorNameException, A> g2) {
        return InvalidActorNameException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, InvalidActorNameException> compose(Function1<A, String> g2) {
        return InvalidActorNameException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String message() {
        return this.message;
    }

    public InvalidActorNameException copy(String message) {
        return new InvalidActorNameException(message);
    }

    public String copy$default$1() {
        return this.message();
    }

    @Override
    public String productPrefix() {
        return "InvalidActorNameException";
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
        return x$1 instanceof InvalidActorNameException;
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
        if (!(object instanceof InvalidActorNameException)) return false;
        boolean bl = true;
        if (!bl) return false;
        InvalidActorNameException invalidActorNameException = (InvalidActorNameException)x$1;
        String string = this.message();
        String string2 = invalidActorNameException.message();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public InvalidActorNameException(String message) {
        this.message = message;
        super(message);
        Product.$init$(this);
    }
}

