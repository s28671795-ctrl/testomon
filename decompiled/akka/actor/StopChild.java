/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.StopChild$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Eb!\u0002\f\u0018\u0005fY\u0002\u0002\u0003\u001a\u0001\u0005+\u0007I\u0011A\u001a\t\u0011a\u0002!\u0011#Q\u0001\nQBQ!\u000f\u0001\u0005\u0002iBq!\u0010\u0001\u0002\u0002\u0013\u0005a\bC\u0004A\u0001E\u0005I\u0011A!\t\u000f1\u0003\u0011\u0011!C!\u001b\"9a\u000bAA\u0001\n\u00039\u0006bB.\u0001\u0003\u0003%\t\u0001\u0018\u0005\bE\u0002\t\t\u0011\"\u0011d\u0011\u001dQ\u0007!!A\u0005\u0002-Dq\u0001\u001d\u0001\u0002\u0002\u0013\u0005\u0013\u000fC\u0004t\u0001\u0005\u0005I\u0011\t;\t\u000fU\u0004\u0011\u0011!C!m\"9q\u000fAA\u0001\n\u0003Bx\u0001\u0003>\u0018\u0003\u0003E\t!G>\u0007\u0011Y9\u0012\u0011!E\u00013qDa!\u000f\t\u0005\u0002\u0005E\u0001bB;\u0011\u0003\u0003%)E\u001e\u0005\n\u0003'\u0001\u0012\u0011!CA\u0003+A\u0011\"!\u0007\u0011\u0003\u0003%\t)a\u0007\t\u0013\u0005\u001d\u0002#!A\u0005\n\u0005%\"!C*u_B\u001c\u0005.\u001b7e\u0015\tA\u0012$A\u0003bGR|'OC\u0001\u001b\u0003\u0011\t7n[1\u0014\t\u0001a\"%\n\t\u0003;\u0001j\u0011A\b\u0006\u0002?\u0005)1oY1mC&\u0011\u0011E\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005u\u0019\u0013B\u0001\u0013\u001f\u0005\u001d\u0001&o\u001c3vGR\u0004\"AJ\u0018\u000f\u0005\u001djcB\u0001\u0015-\u001b\u0005I#B\u0001\u0016,\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0010\n\u00059r\u0012a\u00029bG.\fw-Z\u0005\u0003aE\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!A\f\u0010\u0002\u000b\rD\u0017\u000e\u001c3\u0016\u0003Q\u0002\"!\u000e\u001c\u000e\u0003]I!aN\f\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\faa\u00195jY\u0012\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002<yA\u0011Q\u0007\u0001\u0005\u0006e\r\u0001\r\u0001N\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002<\u007f!9!\u0007\u0002I\u0001\u0002\u0004!\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u0005*\u0012AgQ\u0016\u0002\tB\u0011QIS\u0007\u0002\r*\u0011q\tS\u0001\nk:\u001c\u0007.Z2lK\u0012T!!\u0013\u0010\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002L\r\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005q\u0005CA(U\u001b\u0005\u0001&BA)S\u0003\u0011a\u0017M\\4\u000b\u0003M\u000bAA[1wC&\u0011Q\u000b\u0015\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003a\u0003\"!H-\n\u0005is\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA/a!\tib,\u0003\u0002`=\t\u0019\u0011I\\=\t\u000f\u0005D\u0011\u0011!a\u00011\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012\u0001\u001a\t\u0004K\"lV\"\u00014\u000b\u0005\u001dt\u0012AC2pY2,7\r^5p]&\u0011\u0011N\u001a\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002m_B\u0011Q$\\\u0005\u0003]z\u0011qAQ8pY\u0016\fg\u000eC\u0004b\u0015\u0005\u0005\t\u0019A/\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0003\u001dJDq!Y\u0006\u0002\u0002\u0003\u0007\u0001,\u0001\u0005iCND7i\u001c3f)\u0005A\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00039\u000ba!Z9vC2\u001cHC\u00017z\u0011\u001d\tg\"!AA\u0002u\u000b\u0011b\u0015;pa\u000eC\u0017\u000e\u001c3\u0011\u0005U\u00022\u0003\u0002\t~\u0003\u000f\u0001RA`A\u0002imj\u0011a \u0006\u0004\u0003\u0003q\u0012a\u0002:v]RLW.Z\u0005\u0004\u0003\u000by(!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u0011\u0011BA\b\u001b\t\tYAC\u0002\u0002\u000eI\u000b!![8\n\u0007A\nY\u0001F\u0001|\u0003\u0015\t\u0007\u000f\u001d7z)\rY\u0014q\u0003\u0005\u0006eM\u0001\r\u0001N\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ti\"a\t\u0011\tu\ty\u0002N\u0005\u0004\u0003Cq\"AB(qi&|g\u000e\u0003\u0005\u0002&Q\t\t\u00111\u0001<\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003W\u00012aTA\u0017\u0013\r\ty\u0003\u0015\u0002\u0007\u001f\nTWm\u0019;")
public final class StopChild
implements Product,
Serializable {
    private final ActorRef child;

    public static Option<ActorRef> unapply(StopChild x$0) {
        return StopChild$.MODULE$.unapply(x$0);
    }

    public static StopChild apply(ActorRef child) {
        return StopChild$.MODULE$.apply(child);
    }

    public static <A> Function1<ActorRef, A> andThen(Function1<StopChild, A> g2) {
        return StopChild$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, StopChild> compose(Function1<A, ActorRef> g2) {
        return StopChild$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef child() {
        return this.child;
    }

    public StopChild copy(ActorRef child) {
        return new StopChild(child);
    }

    public ActorRef copy$default$1() {
        return this.child();
    }

    @Override
    public String productPrefix() {
        return "StopChild";
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
                return this.child();
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
        return x$1 instanceof StopChild;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "child";
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
        if (!(object instanceof StopChild)) return false;
        boolean bl = true;
        if (!bl) return false;
        StopChild stopChild = (StopChild)x$1;
        ActorRef actorRef2 = this.child();
        ActorRef actorRef3 = stopChild.child();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public StopChild(ActorRef child) {
        this.child = child;
        Product.$init$(this);
    }
}

