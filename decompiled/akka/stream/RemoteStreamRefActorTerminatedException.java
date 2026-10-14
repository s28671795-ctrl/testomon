/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.RemoteStreamRefActorTerminatedException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]b\u0001B\u000b\u0017\u0005nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\tw\u0001\u0011\t\u0012)A\u0005g!)A\b\u0001C\u0001{!9\u0011\tAA\u0001\n\u0003\u0011\u0005b\u0002#\u0001#\u0003%\t!\u0012\u0005\b!\u0002\t\t\u0011\"\u0011R\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u000f\u001dYh#!A\t\u0002q4q!\u0006\f\u0002\u0002#\u0005Q\u0010\u0003\u0004=\u001f\u0011\u0005\u00111\u0003\u0005\n\u0003+y\u0011\u0011!C#\u0003/A\u0011\"!\u0007\u0010\u0003\u0003%\t)a\u0007\t\u0013\u0005}q\"!A\u0005\u0002\u0006\u0005\u0002\"CA\u0017\u001f\u0005\u0005I\u0011BA\u0018\u0005\u001d\u0012V-\\8uKN#(/Z1n%\u00164\u0017i\u0019;peR+'/\\5oCR,G-\u0012=dKB$\u0018n\u001c8\u000b\u0005]A\u0012AB:ue\u0016\fWNC\u0001\u001a\u0003\u0011\t7n[1\u0004\u0001M!\u0001\u0001\b\u0016/!\tirE\u0004\u0002\u001fI9\u0011qDI\u0007\u0002A)\u0011\u0011EG\u0001\u0007yI|w\u000e\u001e \n\u0003\r\nQa]2bY\u0006L!!\n\u0014\u0002\u000fA\f7m[1hK*\t1%\u0003\u0002)S\t\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\u0006\u0003K\u0019\u0002\"a\u000b\u0017\u000e\u0003\u0019J!!\f\u0014\u0003\u000fA\u0013x\u000eZ;diB\u0011QdL\u0005\u0003a%\u0012AbU3sS\u0006d\u0017N_1cY\u0016\f1!\\:h+\u0005\u0019\u0004C\u0001\u001b9\u001d\t)d\u0007\u0005\u0002 M%\u0011qGJ\u0001\u0007!J,G-\u001a4\n\u0005eR$AB*ue&twM\u0003\u00028M\u0005!Qn]4!\u0003\u0019a\u0014N\\5u}Q\u0011a\b\u0011\t\u0003\u007f\u0001i\u0011A\u0006\u0005\u0006c\r\u0001\raM\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002?\u0007\"9\u0011\u0007\u0002I\u0001\u0002\u0004\u0019\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\r*\u00121gR\u0016\u0002\u0011B\u0011\u0011JT\u0007\u0002\u0015*\u00111\nT\u0001\nk:\u001c\u0007.Z2lK\u0012T!!\u0014\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002P\u0015\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\u0011\u0006CA*Y\u001b\u0005!&BA+W\u0003\u0011a\u0017M\\4\u000b\u0003]\u000bAA[1wC&\u0011\u0011\bV\u0001\raJ|G-^2u\u0003JLG/_\u000b\u00027B\u00111\u0006X\u0005\u0003;\u001a\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"\u0001Y2\u0011\u0005-\n\u0017B\u00012'\u0005\r\te.\u001f\u0005\bI\"\t\t\u00111\u0001\\\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tq\rE\u0002iW\u0002l\u0011!\u001b\u0006\u0003U\u001a\n!bY8mY\u0016\u001cG/[8o\u0013\ta\u0017N\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA8s!\tY\u0003/\u0003\u0002rM\t9!i\\8mK\u0006t\u0007b\u00023\u000b\u0003\u0003\u0005\r\u0001Y\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0002Sk\"9AmCA\u0001\u0002\u0004Y\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003m\u000ba!Z9vC2\u001cHCA8{\u0011\u001d!W\"!AA\u0002\u0001\fqEU3n_R,7\u000b\u001e:fC6\u0014VMZ!di>\u0014H+\u001a:nS:\fG/\u001a3Fq\u000e,\u0007\u000f^5p]B\u0011qhD\n\u0005\u001fy\fI\u0001E\u0003\u0000\u0003\u000b\u0019d(\u0004\u0002\u0002\u0002)\u0019\u00111\u0001\u0014\u0002\u000fI,h\u000e^5nK&!\u0011qAA\u0001\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003\u0017\t\t\"\u0004\u0002\u0002\u000e)\u0019\u0011q\u0002,\u0002\u0005%|\u0017b\u0001\u0019\u0002\u000eQ\tA0\u0001\u0005u_N#(/\u001b8h)\u0005\u0011\u0016!B1qa2LHc\u0001 \u0002\u001e!)\u0011G\u0005a\u0001g\u00059QO\\1qa2LH\u0003BA\u0012\u0003S\u0001BaKA\u0013g%\u0019\u0011q\u0005\u0014\u0003\r=\u0003H/[8o\u0011!\tYcEA\u0001\u0002\u0004q\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0007\t\u0004'\u0006M\u0012bAA\u001b)\n1qJ\u00196fGR\u0004")
public final class RemoteStreamRefActorTerminatedException
extends RuntimeException
implements Product {
    private final String msg;

    public static Option<String> unapply(RemoteStreamRefActorTerminatedException x$0) {
        return RemoteStreamRefActorTerminatedException$.MODULE$.unapply(x$0);
    }

    public static RemoteStreamRefActorTerminatedException apply(String msg) {
        return RemoteStreamRefActorTerminatedException$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(Function1<RemoteStreamRefActorTerminatedException, A> g2) {
        return RemoteStreamRefActorTerminatedException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, RemoteStreamRefActorTerminatedException> compose(Function1<A, String> g2) {
        return RemoteStreamRefActorTerminatedException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public RemoteStreamRefActorTerminatedException copy(String msg) {
        return new RemoteStreamRefActorTerminatedException(msg);
    }

    public String copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "RemoteStreamRefActorTerminatedException";
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
                return this.msg();
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
        return x$1 instanceof RemoteStreamRefActorTerminatedException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "msg";
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
        if (!(object instanceof RemoteStreamRefActorTerminatedException)) return false;
        boolean bl = true;
        if (!bl) return false;
        RemoteStreamRefActorTerminatedException remoteStreamRefActorTerminatedException = (RemoteStreamRefActorTerminatedException)x$1;
        String string = this.msg();
        String string2 = remoteStreamRefActorTerminatedException.msg();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public RemoteStreamRefActorTerminatedException(String msg) {
        this.msg = msg;
        super(msg);
        Product.$init$(this);
    }
}

