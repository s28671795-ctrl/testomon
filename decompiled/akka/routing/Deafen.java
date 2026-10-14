/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.Deafen$;
import akka.routing.ListenerMessage;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ub\u0001\u0002\f\u0018\u0005rA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t}\u0001\u0011\t\u0012)A\u0005q!)q\b\u0001C\u0001\u0001\"91\tAA\u0001\n\u0003!\u0005b\u0002$\u0001#\u0003%\ta\u0012\u0005\b%\u0002\t\t\u0011\"\u0011T\u0011\u001da\u0006!!A\u0005\u0002uCq!\u0019\u0001\u0002\u0002\u0013\u0005!\rC\u0004i\u0001\u0005\u0005I\u0011I5\t\u000fA\u0004\u0011\u0011!C\u0001c\"9a\u000fAA\u0001\n\u0003:\bbB=\u0001\u0003\u0003%\tE\u001f\u0005\bw\u0002\t\t\u0011\"\u0011}\u0011\u001di\b!!A\u0005By<\u0011\"!\u0001\u0018\u0003\u0003E\t!a\u0001\u0007\u0011Y9\u0012\u0011!E\u0001\u0003\u000bAaa\u0010\t\u0005\u0002\u0005u\u0001bB>\u0011\u0003\u0003%)\u0005 \u0005\n\u0003?\u0001\u0012\u0011!CA\u0003CA\u0011\"!\n\u0011\u0003\u0003%\t)a\n\t\u0013\u0005M\u0002#!A\u0005\n\u0005U\"A\u0002#fC\u001a,gN\u0003\u0002\u00193\u00059!o\\;uS:<'\"\u0001\u000e\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u0001QdI\u0014+!\tq\u0012%D\u0001 \u0015\u0005\u0001\u0013!B:dC2\f\u0017B\u0001\u0012 \u0005\u0019\te.\u001f*fMB\u0011A%J\u0007\u0002/%\u0011ae\u0006\u0002\u0010\u0019&\u001cH/\u001a8fe6+7o]1hKB\u0011a\u0004K\u0005\u0003S}\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002,g9\u0011A&\r\b\u0003[Aj\u0011A\f\u0006\u0003_m\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0011\n\u0005Iz\u0012a\u00029bG.\fw-Z\u0005\u0003iU\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!AM\u0010\u0002\u00111L7\u000f^3oKJ,\u0012\u0001\u000f\t\u0003sqj\u0011A\u000f\u0006\u0003we\tQ!Y2u_JL!!\u0010\u001e\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0011\u0002\\5ti\u0016tWM\u001d\u0011\u0002\rqJg.\u001b;?)\t\t%\t\u0005\u0002%\u0001!)ag\u0001a\u0001q\u0005!1m\u001c9z)\t\tU\tC\u00047\tA\u0005\t\u0019\u0001\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\t\u0001J\u000b\u00029\u0013.\n!\n\u0005\u0002L!6\tAJ\u0003\u0002N\u001d\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u001f~\t!\"\u00198o_R\fG/[8o\u0013\t\tFJA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001+\u0011\u0005USV\"\u0001,\u000b\u0005]C\u0016\u0001\u00027b]\u001eT\u0011!W\u0001\u0005U\u00064\u0018-\u0003\u0002\\-\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012A\u0018\t\u0003=}K!\u0001Y\u0010\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005\r4\u0007C\u0001\u0010e\u0013\t)wDA\u0002B]fDqa\u001a\u0005\u0002\u0002\u0003\u0007a,A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002UB\u00191N\\2\u000e\u00031T!!\\\u0010\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002pY\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\t\u0011X\u000f\u0005\u0002\u001fg&\u0011Ao\b\u0002\b\u0005>|G.Z1o\u0011\u001d9'\"!AA\u0002\r\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0011A\u000b\u001f\u0005\bO.\t\t\u00111\u0001_\u0003!A\u0017m\u001d5D_\u0012,G#\u00010\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001V\u0001\u0007KF,\u0018\r\\:\u0015\u0005I|\bbB4\u000f\u0003\u0003\u0005\raY\u0001\u0007\t\u0016\fg-\u001a8\u0011\u0005\u0011\u00022#\u0002\t\u0002\b\u0005M\u0001CBA\u0005\u0003\u001fA\u0014)\u0004\u0002\u0002\f)\u0019\u0011QB\u0010\u0002\u000fI,h\u000e^5nK&!\u0011\u0011CA\u0006\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003+\tY\"\u0004\u0002\u0002\u0018)\u0019\u0011\u0011\u0004-\u0002\u0005%|\u0017b\u0001\u001b\u0002\u0018Q\u0011\u00111A\u0001\u0006CB\u0004H.\u001f\u000b\u0004\u0003\u0006\r\u0002\"\u0002\u001c\u0014\u0001\u0004A\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003S\ty\u0003\u0005\u0003\u001f\u0003WA\u0014bAA\u0017?\t1q\n\u001d;j_:D\u0001\"!\r\u0015\u0003\u0003\u0005\r!Q\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA\u001c!\r)\u0016\u0011H\u0005\u0004\u0003w1&AB(cU\u0016\u001cG\u000f")
public final class Deafen
implements ListenerMessage,
Product,
Serializable {
    private final ActorRef listener;

    public static Option<ActorRef> unapply(Deafen x$0) {
        return Deafen$.MODULE$.unapply(x$0);
    }

    public static Deafen apply(ActorRef listener) {
        return Deafen$.MODULE$.apply(listener);
    }

    public static <A> Function1<ActorRef, A> andThen(Function1<Deafen, A> g2) {
        return Deafen$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Deafen> compose(Function1<A, ActorRef> g2) {
        return Deafen$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef listener() {
        return this.listener;
    }

    public Deafen copy(ActorRef listener) {
        return new Deafen(listener);
    }

    public ActorRef copy$default$1() {
        return this.listener();
    }

    @Override
    public String productPrefix() {
        return "Deafen";
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
                return this.listener();
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
        return x$1 instanceof Deafen;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "listener";
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
        if (!(object instanceof Deafen)) return false;
        boolean bl = true;
        if (!bl) return false;
        Deafen deafen = (Deafen)x$1;
        ActorRef actorRef2 = this.listener();
        ActorRef actorRef3 = deafen.listener();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public Deafen(ActorRef listener) {
        this.listener = listener;
        Product.$init$(this);
    }
}

