/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.routing.ActorSelectionRoutee$;
import akka.routing.Routee;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]c\u0001B\f\u0019\u0005vA\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u007f\u0001\u0011\t\u0012)A\u0005s!)\u0001\t\u0001C\u0001\u0003\")A\t\u0001C!\u000b\"91\u000bAA\u0001\n\u0003!\u0006b\u0002,\u0001#\u0003%\ta\u0016\u0005\bE\u0002\t\t\u0011\"\u0011d\u0011\u001da\u0007!!A\u0005\u00025Dq!\u001d\u0001\u0002\u0002\u0013\u0005!\u000fC\u0004v\u0001\u0005\u0005I\u0011\t<\t\u000fu\u0004\u0011\u0011!C\u0001}\"I\u0011q\u0001\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0002\u0005\n\u0003\u001b\u0001\u0011\u0011!C!\u0003\u001fA\u0011\"!\u0005\u0001\u0003\u0003%\t%a\u0005\t\u0013\u0005U\u0001!!A\u0005B\u0005]q!CA\u000e1\u0005\u0005\t\u0012AA\u000f\r!9\u0002$!A\t\u0002\u0005}\u0001B\u0002!\u0012\t\u0003\t9\u0004C\u0005\u0002\u0012E\t\t\u0011\"\u0012\u0002\u0014!I\u0011\u0011H\t\u0002\u0002\u0013\u0005\u00151\b\u0005\n\u0003\u007f\t\u0012\u0011!CA\u0003\u0003B\u0011\"!\u0014\u0012\u0003\u0003%I!a\u0014\u0003)\u0005\u001bGo\u001c:TK2,7\r^5p]J{W\u000f^3f\u0015\tI\"$A\u0004s_V$\u0018N\\4\u000b\u0003m\tA!Y6lC\u000e\u00011#\u0002\u0001\u001fI!Z\u0003CA\u0010#\u001b\u0005\u0001#\"A\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0002#AB!osJ+g\r\u0005\u0002&M5\t\u0001$\u0003\u0002(1\t1!k\\;uK\u0016\u0004\"aH\u0015\n\u0005)\u0002#a\u0002)s_\u0012,8\r\u001e\t\u0003YQr!!\f\u001a\u000f\u00059\nT\"A\u0018\u000b\u0005Ab\u0012A\u0002\u001fs_>$h(C\u0001\"\u0013\t\u0019\u0004%A\u0004qC\u000e\\\u0017mZ3\n\u0005U2$\u0001D*fe&\fG.\u001b>bE2,'BA\u001a!\u0003%\u0019X\r\\3di&|g.F\u0001:!\tQT(D\u0001<\u0015\ta$$A\u0003bGR|'/\u0003\u0002?w\tq\u0011i\u0019;peN+G.Z2uS>t\u0017AC:fY\u0016\u001cG/[8oA\u00051A(\u001b8jiz\"\"AQ\"\u0011\u0005\u0015\u0002\u0001\"B\u001c\u0004\u0001\u0004I\u0014\u0001B:f]\u0012$2AR%O!\tyr)\u0003\u0002IA\t!QK\\5u\u0011\u0015QE\u00011\u0001L\u0003\u001diWm]:bO\u0016\u0004\"a\b'\n\u00055\u0003#aA!os\")q\n\u0002a\u0001!\u000611/\u001a8eKJ\u0004\"AO)\n\u0005I[$\u0001C!di>\u0014(+\u001a4\u0002\t\r|\u0007/\u001f\u000b\u0003\u0005VCqaN\u0003\u0011\u0002\u0003\u0007\u0011(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003aS#!O-,\u0003i\u0003\"a\u00171\u000e\u0003qS!!\u00180\u0002\u0013Ut7\r[3dW\u0016$'BA0!\u0003)\tgN\\8uCRLwN\\\u0005\u0003Cr\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tA\r\u0005\u0002fU6\taM\u0003\u0002hQ\u0006!A.\u00198h\u0015\u0005I\u0017\u0001\u00026bm\u0006L!a\u001b4\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005q\u0007CA\u0010p\u0013\t\u0001\bEA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002Lg\"9A/CA\u0001\u0002\u0004q\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001x!\rA8pS\u0007\u0002s*\u0011!\u0010I\u0001\u000bG>dG.Z2uS>t\u0017B\u0001?z\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007}\f)\u0001E\u0002 \u0003\u0003I1!a\u0001!\u0005\u001d\u0011un\u001c7fC:Dq\u0001^\u0006\u0002\u0002\u0003\u00071*\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,Gc\u00013\u0002\f!9A\u000fDA\u0001\u0002\u0004q\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u00039\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002I\u00061Q-];bYN$2a`A\r\u0011\u001d!x\"!AA\u0002-\u000bA#Q2u_J\u001cV\r\\3di&|gNU8vi\u0016,\u0007CA\u0013\u0012'\u0015\t\u0012\u0011EA\u0017!\u0019\t\u0019#!\u000b:\u00056\u0011\u0011Q\u0005\u0006\u0004\u0003O\u0001\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003W\t)CA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!a\f\u000265\u0011\u0011\u0011\u0007\u0006\u0004\u0003gA\u0017AA5p\u0013\r)\u0014\u0011\u0007\u000b\u0003\u0003;\tQ!\u00199qYf$2AQA\u001f\u0011\u00159D\u00031\u0001:\u0003\u001d)h.\u00199qYf$B!a\u0011\u0002JA!q$!\u0012:\u0013\r\t9\u0005\t\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005-S#!AA\u0002\t\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\t\u0006E\u0002f\u0003'J1!!\u0016g\u0005\u0019y%M[3di\u0002")
public final class ActorSelectionRoutee
implements Routee,
Product,
Serializable {
    private final ActorSelection selection;

    public static Option<ActorSelection> unapply(ActorSelectionRoutee x$0) {
        return ActorSelectionRoutee$.MODULE$.unapply(x$0);
    }

    public static ActorSelectionRoutee apply(ActorSelection selection) {
        return ActorSelectionRoutee$.MODULE$.apply(selection);
    }

    public static <A> Function1<ActorSelection, A> andThen(Function1<ActorSelectionRoutee, A> g2) {
        return ActorSelectionRoutee$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ActorSelectionRoutee> compose(Function1<A, ActorSelection> g2) {
        return ActorSelectionRoutee$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorSelection selection() {
        return this.selection;
    }

    @Override
    public void send(Object message, ActorRef sender) {
        this.selection().tell(message, sender);
    }

    public ActorSelectionRoutee copy(ActorSelection selection) {
        return new ActorSelectionRoutee(selection);
    }

    public ActorSelection copy$default$1() {
        return this.selection();
    }

    @Override
    public String productPrefix() {
        return "ActorSelectionRoutee";
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
                return this.selection();
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
        return x$1 instanceof ActorSelectionRoutee;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "selection";
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
        if (!(object instanceof ActorSelectionRoutee)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorSelectionRoutee actorSelectionRoutee = (ActorSelectionRoutee)x$1;
        ActorSelection actorSelection = this.selection();
        ActorSelection actorSelection2 = actorSelectionRoutee.selection();
        if (actorSelection != null) {
            if (!((Object)actorSelection).equals(actorSelection2)) return false;
            return true;
        }
        if (actorSelection2 == null) return true;
        return false;
    }

    public ActorSelectionRoutee(ActorSelection selection) {
        this.selection = selection;
        Product.$init$(this);
    }
}

