/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.InternalActorRef;
import akka.japi.Util$;
import akka.routing.ActorRefRoutee;
import akka.routing.ActorSelectionRoutee;
import akka.routing.Broadcast;
import akka.routing.NoRoutee$;
import akka.routing.Routee;
import akka.routing.Router$;
import akka.routing.RouterEnvelope;
import akka.routing.RoutingLogic;
import akka.routing.SeveralRoutees;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Eh\u0001B\u0014)\u00056B\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u0013\u0002\u0011\t\u0012)A\u0005\u000b\"A!\n\u0001BK\u0002\u0013\u00051\n\u0003\u0005X\u0001\tE\t\u0015!\u0003M\u0011\u0015A\u0006\u0001\"\u0001Z\u0011\u0015A\u0006\u0001\"\u0001^\u0011\u0015A\u0006\u0001\"\u0001`\u0011\u0015Q\u0007\u0001\"\u0001l\u0011\u0015a\b\u0001\"\u0003~\u0011\u001d\t9\u0001\u0001C\u0005\u0003\u0013Aq!!\u0004\u0001\t\u0003\ty\u0001C\u0004\u0002\u0016\u0001!\t!a\u0006\t\u000f\u0005U\u0001\u0001\"\u0001\u0002\u001c!9\u0011Q\u0003\u0001\u0005\u0002\u0005\u0005\u0002bBA\u0017\u0001\u0011\u0005\u0011q\u0006\u0005\b\u0003[\u0001A\u0011AA\u001a\u0011\u001d\ti\u0003\u0001C\u0001\u0003oA\u0011\"a\u000f\u0001\u0003\u0003%\t!!\u0010\t\u0013\u0005\r\u0003!%A\u0005\u0002\u0005\u0015\u0003\"CA.\u0001E\u0005I\u0011AA/\u0011%\t\t\u0007AA\u0001\n\u0003\n\u0019\u0007C\u0005\u0002l\u0001\t\t\u0011\"\u0001\u0002n!I\u0011Q\u000f\u0001\u0002\u0002\u0013\u0005\u0011q\u000f\u0005\n\u0003{\u0002\u0011\u0011!C!\u0003\u007fB\u0011\"!#\u0001\u0003\u0003%\t!a#\t\u0013\u0005U\u0005!!A\u0005B\u0005]\u0005\"CAN\u0001\u0005\u0005I\u0011IAO\u0011%\ty\nAA\u0001\n\u0003\n\t\u000bC\u0005\u0002$\u0002\t\t\u0011\"\u0011\u0002&\u001eI\u0011\u0011\u0016\u0015\u0002\u0002#\u0005\u00111\u0016\u0004\tO!\n\t\u0011#\u0001\u0002.\"1\u0001l\bC\u0001\u0003\u000bD\u0011\"a( \u0003\u0003%)%!)\t\u0013\u0005\u001dw$!A\u0005\u0002\u0006%\u0007\"CAh?E\u0005I\u0011AA/\u0011%\t\tnHA\u0001\n\u0003\u000b\u0019\u000eC\u0005\u0002f~\t\n\u0011\"\u0001\u0002^!I\u0011q]\u0010\u0002\u0002\u0013%\u0011\u0011\u001e\u0002\u0007%>,H/\u001a:\u000b\u0005%R\u0013a\u0002:pkRLgn\u001a\u0006\u0002W\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001\u0001\u00185oA\u0011qFM\u0007\u0002a)\t\u0011'A\u0003tG\u0006d\u0017-\u0003\u00024a\t1\u0011I\\=SK\u001a\u0004\"aL\u001b\n\u0005Y\u0002$a\u0002)s_\u0012,8\r\u001e\t\u0003q\u0001s!!\u000f \u000f\u0005ijT\"A\u001e\u000b\u0005qb\u0013A\u0002\u001fs_>$h(C\u00012\u0013\ty\u0004'A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0005\u0013%\u0001D*fe&\fG.\u001b>bE2,'BA 1\u0003\u0015awnZ5d+\u0005)\u0005C\u0001$H\u001b\u0005A\u0013B\u0001%)\u00051\u0011v.\u001e;j]\u001edunZ5d\u0003\u0019awnZ5dA\u00059!o\\;uK\u0016\u001cX#\u0001'\u0011\u00075\u0013F+D\u0001O\u0015\ty\u0005+A\u0005j[6,H/\u00192mK*\u0011\u0011\u000bM\u0001\u000bG>dG.Z2uS>t\u0017BA*O\u0005)Ie\u000eZ3yK\u0012\u001cV-\u001d\t\u0003\rVK!A\u0016\u0015\u0003\rI{W\u000f^3f\u0003!\u0011x.\u001e;fKN\u0004\u0013A\u0002\u001fj]&$h\bF\u0002[7r\u0003\"A\u0012\u0001\t\u000b\r+\u0001\u0019A#\t\u000f)+\u0001\u0013!a\u0001\u0019R\u0011!L\u0018\u0005\u0006\u0007\u001a\u0001\r!\u0012\u000b\u00045\u0002\f\u0007\"B\"\b\u0001\u0004)\u0005\"\u0002&\b\u0001\u0004\u0011\u0007cA2i)6\tAM\u0003\u0002fM\u0006!A.\u00198h\u0015\u00059\u0017\u0001\u00026bm\u0006L!!\u001b3\u0003\u0011%#XM]1cY\u0016\fQA]8vi\u0016$2\u0001\\8u!\tyS.\u0003\u0002oa\t!QK\\5u\u0011\u0015\u0001\b\u00021\u0001r\u0003\u001diWm]:bO\u0016\u0004\"a\f:\n\u0005M\u0004$aA!os\")Q\u000f\u0003a\u0001m\u000611/\u001a8eKJ\u0004\"a\u001e>\u000e\u0003aT!!\u001f\u0016\u0002\u000b\u0005\u001cGo\u001c:\n\u0005mD(\u0001C!di>\u0014(+\u001a4\u0002\tM,g\u000e\u001a\u000b\u0007Yz\f\t!!\u0002\t\u000b}L\u0001\u0019\u0001+\u0002\rI|W\u000f^3f\u0011\u0019\t\u0019!\u0003a\u0001c\u0006\u0019Qn]4\t\u000bUL\u0001\u0019\u0001<\u0002\rUtwO]1q)\r\t\u00181\u0002\u0005\u0007\u0003\u0007Q\u0001\u0019A9\u0002\u0017]LG\u000f\u001b*pkR,Wm\u001d\u000b\u00045\u0006E\u0001BBA\n\u0017\u0001\u0007A*\u0001\u0002sg\u0006I\u0011\r\u001a3S_V$X-\u001a\u000b\u00045\u0006e\u0001\"B@\r\u0001\u0004!Fc\u0001.\u0002\u001e!1\u0011qD\u0007A\u0002Y\f1A]3g)\rQ\u00161\u0005\u0005\b\u0003Kq\u0001\u0019AA\u0014\u0003\r\u0019X\r\u001c\t\u0004o\u0006%\u0012bAA\u0016q\nq\u0011i\u0019;peN+G.Z2uS>t\u0017\u0001\u0004:f[>4XMU8vi\u0016,Gc\u0001.\u00022!)qp\u0004a\u0001)R\u0019!,!\u000e\t\r\u0005}\u0001\u00031\u0001w)\rQ\u0016\u0011\b\u0005\b\u0003K\t\u0002\u0019AA\u0014\u0003\u0011\u0019w\u000e]=\u0015\u000bi\u000by$!\u0011\t\u000f\r\u0013\u0002\u0013!a\u0001\u000b\"9!J\u0005I\u0001\u0002\u0004a\u0015AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003\u000fR3!RA%W\t\tY\u0005\u0005\u0003\u0002N\u0005]SBAA(\u0015\u0011\t\t&a\u0015\u0002\u0013Ut7\r[3dW\u0016$'bAA+a\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u0013q\n\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003?R3\u0001TA%\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011Q\r\t\u0004G\u0006\u001d\u0014bAA5I\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a\u001c\u0011\u0007=\n\t(C\u0002\u0002tA\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2!]A=\u0011%\tYhFA\u0001\u0002\u0004\ty'A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u0003\u0003R!a!\u0002\u0006Fl\u0011\u0001U\u0005\u0004\u0003\u000f\u0003&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!$\u0002\u0014B\u0019q&a$\n\u0007\u0005E\u0005GA\u0004C_>dW-\u00198\t\u0011\u0005m\u0014$!AA\u0002E\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QMAM\u0011%\tYHGA\u0001\u0002\u0004\ty'\u0001\u0005iCND7i\u001c3f)\t\ty'\u0001\u0005u_N#(/\u001b8h)\t\t)'\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u001b\u000b9\u000b\u0003\u0005\u0002|u\t\t\u00111\u0001r\u0003\u0019\u0011v.\u001e;feB\u0011aiH\n\u0006?\u0005=\u00161\u0018\t\b\u0003c\u000b9,\u0012'[\u001b\t\t\u0019LC\u0002\u00026B\nqA];oi&lW-\u0003\u0003\u0002:\u0006M&!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!\u0011QXAb\u001b\t\tyLC\u0002\u0002B\u001a\f!![8\n\u0007\u0005\u000by\f\u0006\u0002\u0002,\u0006)\u0011\r\u001d9msR)!,a3\u0002N\")1I\ta\u0001\u000b\"9!J\tI\u0001\u0002\u0004a\u0015aD1qa2LH\u0005Z3gCVdG\u000f\n\u001a\u0002\u000fUt\u0017\r\u001d9msR!\u0011Q[Aq!\u0015y\u0013q[An\u0013\r\tI\u000e\r\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000b=\ni.\u0012'\n\u0007\u0005}\u0007G\u0001\u0004UkBdWM\r\u0005\t\u0003G$\u0013\u0011!a\u00015\u0006\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\tY\u000fE\u0002d\u0003[L1!a<e\u0005\u0019y%M[3di\u0002")
public final class Router
implements Product,
Serializable {
    private final RoutingLogic logic;
    private final IndexedSeq<Routee> routees;

    public static IndexedSeq<Routee> $lessinit$greater$default$2() {
        return Router$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple2<RoutingLogic, IndexedSeq<Routee>>> unapply(Router x$0) {
        return Router$.MODULE$.unapply(x$0);
    }

    public static IndexedSeq<Routee> apply$default$2() {
        return Router$.MODULE$.apply$default$2();
    }

    public static Router apply(RoutingLogic logic, IndexedSeq<Routee> routees) {
        return Router$.MODULE$.apply(logic, routees);
    }

    public static Function1<Tuple2<RoutingLogic, IndexedSeq<Routee>>, Router> tupled() {
        return Router$.MODULE$.tupled();
    }

    public static Function1<RoutingLogic, Function1<IndexedSeq<Routee>, Router>> curried() {
        return Router$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public RoutingLogic logic() {
        return this.logic;
    }

    public IndexedSeq<Routee> routees() {
        return this.routees;
    }

    public void route(Object message, ActorRef sender) {
        Object object = message;
        if (object instanceof Broadcast) {
            Broadcast broadcast = (Broadcast)object;
            Object msg = broadcast.message();
            new SeveralRoutees(this.routees()).send(msg, sender);
            return;
        }
        this.send(this.logic().select(object, this.routees()), message, sender);
    }

    private void send(Routee routee, Object msg, ActorRef sender) {
        Routee routee2 = routee;
        NoRoutee$ noRoutee$ = NoRoutee$.MODULE$;
        if (!(routee2 != null ? !routee2.equals(noRoutee$) : noRoutee$ != null) && sender instanceof InternalActorRef) {
            ((InternalActorRef)sender).provider().deadLetters().tell(this.unwrap(msg), sender);
            return;
        }
        routee.send(this.unwrap(msg), sender);
    }

    private Object unwrap(Object msg) {
        Object object = msg;
        if (object instanceof RouterEnvelope) {
            RouterEnvelope routerEnvelope = (RouterEnvelope)object;
            return routerEnvelope.message();
        }
        return msg;
    }

    public Router withRoutees(IndexedSeq<Routee> rs) {
        IndexedSeq<Routee> x$1 = rs;
        RoutingLogic x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public Router addRoutee(Routee routee) {
        IndexedSeq x$1 = (IndexedSeq)this.routees().$colon$plus(routee);
        RoutingLogic x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public Router addRoutee(ActorRef ref) {
        return this.addRoutee(new ActorRefRoutee(ref));
    }

    public Router addRoutee(ActorSelection sel) {
        return this.addRoutee(new ActorSelectionRoutee(sel));
    }

    public Router removeRoutee(Routee routee) {
        IndexedSeq x$1 = (IndexedSeq)this.routees().filterNot((Function1<Routee, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(Router.$anonfun$removeRoutee$1(routee, x$2)));
        RoutingLogic x$22 = this.copy$default$1();
        return this.copy(x$22, x$1);
    }

    public Router removeRoutee(ActorRef ref) {
        return this.removeRoutee(new ActorRefRoutee(ref));
    }

    public Router removeRoutee(ActorSelection sel) {
        return this.removeRoutee(new ActorSelectionRoutee(sel));
    }

    public Router copy(RoutingLogic logic, IndexedSeq<Routee> routees) {
        return new Router(logic, routees);
    }

    public RoutingLogic copy$default$1() {
        return this.logic();
    }

    public IndexedSeq<Routee> copy$default$2() {
        return this.routees();
    }

    @Override
    public String productPrefix() {
        return "Router";
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
                return this.logic();
            }
            case 1: {
                return this.routees();
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
        return x$1 instanceof Router;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "logic";
            }
            case 1: {
                return "routees";
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
        if (!(object instanceof Router)) return false;
        boolean bl = true;
        if (!bl) return false;
        Router router = (Router)x$1;
        RoutingLogic routingLogic = this.logic();
        RoutingLogic routingLogic2 = router.logic();
        if (routingLogic == null) {
            if (routingLogic2 != null) {
                return false;
            }
        } else if (!routingLogic.equals(routingLogic2)) return false;
        IndexedSeq<Routee> indexedSeq = this.routees();
        IndexedSeq<Routee> indexedSeq2 = router.routees();
        if (indexedSeq == null) {
            if (indexedSeq2 == null) return true;
            return false;
        } else {
            if (!indexedSeq.equals(indexedSeq2)) return false;
            return true;
        }
    }

    public static final /* synthetic */ boolean $anonfun$removeRoutee$1(Routee routee$1, Routee x$2) {
        Routee routee = x$2;
        Routee routee2 = routee$1;
        return !(routee != null ? !routee.equals(routee2) : routee2 != null);
    }

    public Router(RoutingLogic logic, IndexedSeq<Routee> routees) {
        this.logic = logic;
        this.routees = routees;
        Product.$init$(this);
    }

    public Router(RoutingLogic logic) {
        this(logic, (IndexedSeq<Routee>)package$.MODULE$.Vector().empty());
    }

    public Router(RoutingLogic logic, Iterable<Routee> routees) {
        this(logic, Util$.MODULE$.immutableSeq(routees).toVector());
    }
}

