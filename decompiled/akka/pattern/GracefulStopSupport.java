/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.InternalActorRef;
import akka.actor.PoisonPill$;
import akka.actor.Terminated;
import akka.dispatch.ExecutionContexts$;
import akka.dispatch.sysmsg.Unwatch;
import akka.dispatch.sysmsg.Watch;
import akka.pattern.PromiseActorRef;
import akka.pattern.PromiseActorRef$;
import akka.util.Timeout;
import java.io.Serializable;
import scala.Function1;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t3q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003\u0012\u0001\u0011\u0005!\u0003C\u0003\u0017\u0001\u0011\u0005q\u0003C\u00047\u0001E\u0005I\u0011A\u001c\u0003'\u001d\u0013\u0018mY3gk2\u001cFo\u001c9TkB\u0004xN\u001d;\u000b\u0005\u00199\u0011a\u00029biR,'O\u001c\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0006\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t1\u0003\u0005\u0002\r)%\u0011Q#\u0004\u0002\u0005+:LG/\u0001\u0007he\u0006\u001cWMZ;m'R|\u0007\u000f\u0006\u0003\u0019C%\n\u0004cA\r\u001d=5\t!D\u0003\u0002\u001c\u001b\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005uQ\"A\u0002$viV\u0014X\r\u0005\u0002\r?%\u0011\u0001%\u0004\u0002\b\u0005>|G.Z1o\u0011\u0015\u0011#\u00011\u0001$\u0003\u0019!\u0018M]4fiB\u0011AeJ\u0007\u0002K)\u0011aeB\u0001\u0006C\u000e$xN]\u0005\u0003Q\u0015\u0012\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006U\t\u0001\raK\u0001\bi&lWm\\;u!\tas&D\u0001.\u0015\tq#$\u0001\u0005ekJ\fG/[8o\u0013\t\u0001TF\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\t\u000fI\u0012\u0001\u0013!a\u0001g\u0005Y1\u000f^8q\u001b\u0016\u001c8/Y4f!\taA'\u0003\u00026\u001b\t\u0019\u0011I\\=\u0002-\u001d\u0014\u0018mY3gk2\u001cFo\u001c9%I\u00164\u0017-\u001e7uIM*\u0012\u0001\u000f\u0016\u0003geZ\u0013A\u000f\t\u0003w\u0001k\u0011\u0001\u0010\u0006\u0003{y\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005}j\u0011AC1o]>$\u0018\r^5p]&\u0011\u0011\t\u0010\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007")
public interface GracefulStopSupport {
    public static /* synthetic */ Future gracefulStop$(GracefulStopSupport $this, ActorRef target, FiniteDuration timeout2, Object stopMessage) {
        return $this.gracefulStop(target, timeout2, stopMessage);
    }

    default public Future<Object> gracefulStop(ActorRef target, FiniteDuration timeout2, Object stopMessage) {
        InternalActorRef internalTarget = (InternalActorRef)target;
        PromiseActorRef ref = PromiseActorRef$.MODULE$.apply(internalTarget.provider(), new Timeout(timeout2), target, stopMessage.getClass().getName(), target.path().name(), PromiseActorRef$.MODULE$.apply$default$6(), PromiseActorRef$.MODULE$.apply$default$7());
        internalTarget.sendSystemMessage(new Watch(internalTarget, ref));
        target.tell(stopMessage, Actor$.MODULE$.noSender());
        return ref.result().future().transform((Function1<Object, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(GracefulStopSupport.$anonfun$gracefulStop$1(target, internalTarget, ref, x0$1)), (Function1<Throwable, Throwable> & Serializable)t2 -> {
            internalTarget.sendSystemMessage(new Unwatch(target, ref));
            return t2;
        }, ExecutionContexts$.MODULE$.parasitic());
    }

    public static /* synthetic */ Object gracefulStop$default$3$(GracefulStopSupport $this) {
        return $this.gracefulStop$default$3();
    }

    default public Object gracefulStop$default$3() {
        return PoisonPill$.MODULE$;
    }

    public static /* synthetic */ boolean $anonfun$gracefulStop$1(ActorRef target$1, InternalActorRef internalTarget$1, PromiseActorRef ref$1, Object x0$1) {
        Object object = x0$1;
        if (object instanceof Terminated) {
            Terminated terminated = (Terminated)object;
            ActorRef t2 = terminated.actor();
            ActorPath actorPath = t2.path();
            ActorPath actorPath2 = target$1.path();
            if (!(actorPath != null ? !actorPath.equals(actorPath2) : actorPath2 != null)) {
                return true;
            }
        }
        internalTarget$1.sendSystemMessage(new Unwatch(target$1, ref$1));
        return false;
    }

    public static void $init$(GracefulStopSupport $this) {
    }
}

