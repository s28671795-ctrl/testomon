/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Unwatch$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015d!B\r\u001b\u0005z\u0001\u0003\u0002C\u001e\u0001\u0005+\u0007I\u0011\u0001\u001f\t\u0011\r\u0003!\u0011#Q\u0001\nuB\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0010\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005{!)a\t\u0001C\u0001\u000f\"91\nAA\u0001\n\u0003a\u0005bB(\u0001#\u0003%\t\u0001\u0015\u0005\b7\u0002\t\n\u0011\"\u0001Q\u0011\u001da\u0006!!A\u0005BuCqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004l\u0001\u0005\u0005I\u0011\u00017\t\u000fI\u0004\u0011\u0011!C!g\"9!\u0010AA\u0001\n\u0003Y\b\"CA\u0001\u0001\u0005\u0005I\u0011IA\u0002\u0011%\t9\u0001AA\u0001\n\u0003\nI\u0001C\u0005\u0002\f\u0001\t\t\u0011\"\u0011\u0002\u000e!I\u0011q\u0002\u0001\u0002\u0002\u0013\u0005\u0013\u0011C\u0004\u000b\u0003CQ\u0012\u0011!E\u0001=\u0005\rb!C\r\u001b\u0003\u0003E\tAHA\u0013\u0011\u001915\u0003\"\u0001\u0002>!I\u00111B\n\u0002\u0002\u0013\u0015\u0013Q\u0002\u0005\n\u0003\u007f\u0019\u0012\u0011!CA\u0003\u0003B\u0011\"a\u0012\u0014\u0003\u0003%\t)!\u0013\t\u0013\u0005m3#!A\u0005\n\u0005u#aB+oo\u0006$8\r\u001b\u0006\u00037q\taa]=t[N<'BA\u000f\u001f\u0003!!\u0017n\u001d9bi\u000eD'\"A\u0010\u0002\t\u0005\\7.Y\n\u0006\u0001\u0005:3F\f\t\u0003E\u0015j\u0011a\t\u0006\u0002I\u0005)1oY1mC&\u0011ae\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0005!JS\"\u0001\u000e\n\u0005)R\"!D*zgR,W.T3tg\u0006<W\r\u0005\u0002#Y%\u0011Qf\t\u0002\b!J|G-^2u!\ty\u0003H\u0004\u00021m9\u0011\u0011'N\u0007\u0002e)\u00111\u0007N\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\tA%\u0003\u00028G\u00059\u0001/Y2lC\u001e,\u0017BA\u001d;\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t94%A\u0004xCR\u001c\u0007.Z3\u0016\u0003u\u0002\"AP!\u000e\u0003}R!\u0001\u0011\u0010\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\t{$\u0001C!di>\u0014(+\u001a4\u0002\u0011]\fGo\u00195fK\u0002\nqa^1uG\",'/\u0001\u0005xCR\u001c\u0007.\u001a:!\u0003\u0019a\u0014N\\5u}Q\u0019\u0001*\u0013&\u0011\u0005!\u0002\u0001\"B\u001e\u0006\u0001\u0004i\u0004\"\u0002#\u0006\u0001\u0004i\u0014\u0001B2paf$2\u0001S'O\u0011\u001dYd\u0001%AA\u0002uBq\u0001\u0012\u0004\u0011\u0002\u0003\u0007Q(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003ES#!\u0010*,\u0003M\u0003\"\u0001V-\u000e\u0003US!AV,\u0002\u0013Ut7\r[3dW\u0016$'B\u0001-$\u0003)\tgN\\8uCRLwN\\\u0005\u00035V\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u00010\u0011\u0005}#W\"\u00011\u000b\u0005\u0005\u0014\u0017\u0001\u00027b]\u001eT\u0011aY\u0001\u0005U\u00064\u0018-\u0003\u0002fA\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\u001b\t\u0003E%L!A[\u0012\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u00055\u0004\bC\u0001\u0012o\u0013\ty7EA\u0002B]fDq!]\u0006\u0002\u0002\u0003\u0007\u0001.A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002iB\u0019Q\u000f_7\u000e\u0003YT!a^\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002zm\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\tax\u0010\u0005\u0002#{&\u0011ap\t\u0002\b\u0005>|G.Z1o\u0011\u001d\tX\"!AA\u00025\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019a,!\u0002\t\u000fEt\u0011\u0011!a\u0001Q\u0006A\u0001.Y:i\u0007>$W\rF\u0001i\u0003!!xn\u0015;sS:<G#\u00010\u0002\r\u0015\fX/\u00197t)\ra\u00181\u0003\u0005\bcF\t\t\u00111\u0001nQ\u001d\u0001\u0011qCA\u000f\u0003?\u00012AIA\r\u0013\r\tYb\t\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\b+:<\u0018\r^2i!\tA3cE\u0003\u0014\u0003O\t\u0019\u0004E\u0004\u0002*\u0005=R(\u0010%\u000e\u0005\u0005-\"bAA\u0017G\u00059!/\u001e8uS6,\u0017\u0002BA\u0019\u0003W\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83!\u0011\t)$a\u000f\u000e\u0005\u0005]\"bAA\u001dE\u0006\u0011\u0011n\\\u0005\u0004s\u0005]BCAA\u0012\u0003\u0015\t\u0007\u000f\u001d7z)\u0015A\u00151IA#\u0011\u0015Yd\u00031\u0001>\u0011\u0015!e\u00031\u0001>\u0003\u001d)h.\u00199qYf$B!a\u0013\u0002XA)!%!\u0014\u0002R%\u0019\u0011qJ\u0012\u0003\r=\u0003H/[8o!\u0015\u0011\u00131K\u001f>\u0013\r\t)f\t\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u0005es#!AA\u0002!\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ty\u0006E\u0002`\u0003CJ1!a\u0019a\u0005\u0019y%M[3di\u0002")
public final class Unwatch
implements SystemMessage,
Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef watchee;
    private final ActorRef watcher;
    private transient SystemMessage next;

    public static Option<Tuple2<ActorRef, ActorRef>> unapply(Unwatch x$0) {
        return Unwatch$.MODULE$.unapply(x$0);
    }

    public static Unwatch apply(ActorRef watchee, ActorRef watcher) {
        return Unwatch$.MODULE$.apply(watchee, watcher);
    }

    public static Function1<Tuple2<ActorRef, ActorRef>, Unwatch> tupled() {
        return Unwatch$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<ActorRef, Unwatch>> curried() {
        return Unwatch$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public void unlink() {
        SystemMessage.unlink$(this);
    }

    @Override
    public boolean unlinked() {
        return SystemMessage.unlinked$(this);
    }

    @Override
    public SystemMessage next() {
        return this.next;
    }

    @Override
    public void next_$eq(SystemMessage x$1) {
        this.next = x$1;
    }

    public ActorRef watchee() {
        return this.watchee;
    }

    public ActorRef watcher() {
        return this.watcher;
    }

    public Unwatch copy(ActorRef watchee, ActorRef watcher) {
        return new Unwatch(watchee, watcher);
    }

    public ActorRef copy$default$1() {
        return this.watchee();
    }

    public ActorRef copy$default$2() {
        return this.watcher();
    }

    @Override
    public String productPrefix() {
        return "Unwatch";
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
                return this.watchee();
            }
            case 1: {
                return this.watcher();
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
        return x$1 instanceof Unwatch;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "watchee";
            }
            case 1: {
                return "watcher";
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
        if (!(object instanceof Unwatch)) return false;
        boolean bl = true;
        if (!bl) return false;
        Unwatch unwatch = (Unwatch)x$1;
        ActorRef actorRef2 = this.watchee();
        ActorRef actorRef3 = unwatch.watchee();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        ActorRef actorRef4 = this.watcher();
        ActorRef actorRef5 = unwatch.watcher();
        if (actorRef4 == null) {
            if (actorRef5 == null) return true;
            return false;
        } else {
            if (!((Object)actorRef4).equals(actorRef5)) return false;
            return true;
        }
    }

    public Unwatch(ActorRef watchee, ActorRef watcher) {
        this.watchee = watchee;
        this.watcher = watcher;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

