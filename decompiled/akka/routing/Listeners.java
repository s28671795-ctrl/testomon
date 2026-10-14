/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.routing.Deafen;
import akka.routing.Listen;
import akka.routing.WithListeners;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005A3\u0001BB\u0004\u0011\u0002\u0007\u0005A\u0002\u0013\u0005\u0006'\u0001!\t\u0001\u0006\u0005\b1\u0001\u0011\r\u0011\"\u0005\u001a\u0011\u0015A\u0003\u0001\"\u0005*\u0011\u0015\t\u0004\u0001\"\u00053\u0011\u001dY\u0004!%A\u0005\u0012q\u0012\u0011\u0002T5ti\u0016tWM]:\u000b\u0005!I\u0011a\u0002:pkRLgn\u001a\u0006\u0002\u0015\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0007\u0011\u00059\tR\"A\b\u000b\u0003A\tQa]2bY\u0006L!AE\b\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\tQ\u0003\u0005\u0002\u000f-%\u0011qc\u0004\u0002\u0005+:LG/A\u0005mSN$XM\\3sgV\t!\u0004E\u0002\u001cA\tj\u0011\u0001\b\u0006\u0003;y\tA!\u001e;jY*\tq$\u0001\u0003kCZ\f\u0017BA\u0011\u001d\u0005\r\u0019V\r\u001e\t\u0003G\u0019j\u0011\u0001\n\u0006\u0003K%\tQ!Y2u_JL!a\n\u0013\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f!\u0003\\5ti\u0016tWM]'b]\u0006<W-\\3oiV\t!\u0006\u0005\u0002,]9\u00111\u0005L\u0005\u0003[\u0011\nQ!Q2u_JL!a\f\u0019\u0003\u000fI+7-Z5wK*\u0011Q\u0006J\u0001\u0007O>\u001c8/\u001b9\u0015\u0005M2DCA\u000b5\u0011\u001d)D\u0001%AA\u0004\t\naa]3oI\u0016\u0014\b\"B\u001c\u0005\u0001\u0004A\u0014aA7tOB\u0011a\"O\u0005\u0003u=\u00111!\u00118z\u0003A9wn]:ja\u0012\"WMZ1vYR$#\u0007\u0006\u0002>\u000f*\u0012!EP\u0016\u0002\u007fA\u0011\u0001)R\u0007\u0002\u0003*\u0011!iQ\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001R\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002G\u0003\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\t\u000b]*\u0001\u0019\u0001\u001d\u0013\u0007%[UJ\u0002\u0003K\u0001\u0001A%\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004C\u0001'\u0001\u001b\u00059\u0001CA\u0012O\u0013\tyEEA\u0003BGR|'\u000f")
public interface Listeners {
    public void akka$routing$Listeners$_setter_$listeners_$eq(Set<ActorRef> var1);

    public Set<ActorRef> listeners();

    public static /* synthetic */ PartialFunction listenerManagement$(Listeners $this) {
        return $this.listenerManagement();
    }

    default public PartialFunction<Object, BoxedUnit> listenerManagement() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ Listeners $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Listen) {
                    Listen listen = (Listen)A1;
                    ActorRef l = listen.listener();
                    this.$outer.listeners().add(l);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Deafen) {
                    Deafen deafen = (Deafen)A1;
                    ActorRef l = deafen.listener();
                    this.$outer.listeners().remove(l);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof WithListeners) {
                    WithListeners withListeners = (WithListeners)A1;
                    Function1<ActorRef, BoxedUnit> f = withListeners.f();
                    Iterator<ActorRef> i = this.$outer.listeners().iterator();
                    while (i.hasNext()) {
                        f.apply(i.next());
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Listen) {
                    return true;
                }
                if (object instanceof Deafen) {
                    return true;
                }
                return object instanceof WithListeners;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static /* synthetic */ void gossip$(Listeners $this, Object msg, ActorRef sender) {
        $this.gossip(msg, sender);
    }

    default public void gossip(Object msg, ActorRef sender) {
        Iterator<ActorRef> i = this.listeners().iterator();
        while (i.hasNext()) {
            i.next().$bang(msg, sender);
        }
    }

    public static /* synthetic */ ActorRef gossip$default$2$(Listeners $this, Object msg) {
        return $this.gossip$default$2(msg);
    }

    default public ActorRef gossip$default$2(Object msg) {
        return Actor$.MODULE$.noSender();
    }

    public static void $init$(Listeners $this) {
        $this.akka$routing$Listeners$_setter_$listeners_$eq(new TreeSet<ActorRef>());
    }
}

