/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorCell;
import akka.dispatch.Dispatcher;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.ThreadPoolConfig;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import scala.Function0;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r4A\u0001D\u0007\u0001%!Iq\u0003\u0001B\u0001B\u0003%\u0001d\u0007\u0005\t?\u0001\u0011\t\u0011)A\u0005A!Ia\u0005\u0001B\u0001B\u0003%q\u0005\u000e\u0005\nm\u0001\u0011\t\u0011)A\u0005o}B\u0001\"\u0011\u0001\u0003\u0002\u0003\u0006IA\u0011\u0005\u0006\u000b\u0002!\tA\u0012\u0005\b\u001b\u0002\u0001\r\u0011\"\u0003O\u0011\u001dy\u0005\u00011A\u0005\nACaa\u0016\u0001!B\u0013\u0001\u0003B\u0002/\u0001\t#zQ\f\u0003\u0004a\u0001\u0011Es\"\u0019\u0002\u0011!&tg.\u001a3ESN\u0004\u0018\r^2iKJT!AD\b\u0002\u0011\u0011L7\u000f]1uG\"T\u0011\u0001E\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001\u0019\u0002C\u0001\u000b\u0016\u001b\u0005i\u0011B\u0001\f\u000e\u0005)!\u0015n\u001d9bi\u000eDWM]\u0001\u000e?\u000e|gNZ5hkJ\fGo\u001c:\u0011\u0005QI\u0012B\u0001\u000e\u000e\u0005uiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\"p]\u001aLw-\u001e:bi>\u0014\u0018B\u0001\u000f\u001e\u00031\u0019wN\u001c4jOV\u0014\u0018\r^8s\u0013\tqRBA\tNKN\u001c\u0018mZ3ESN\u0004\u0018\r^2iKJ\faaX1di>\u0014\bCA\u0011%\u001b\u0005\u0011#BA\u0012\u0010\u0003\u0015\t7\r^8s\u0013\t)#EA\u0005BGR|'oQ3mY\u0006\u0019q,\u001b3\u0011\u0005!\ndBA\u00150!\tQS&D\u0001,\u0015\ta\u0013#\u0001\u0004=e>|GO\u0010\u0006\u0002]\u0005)1oY1mC&\u0011\u0001'L\u0001\u0007!J,G-\u001a4\n\u0005I\u001a$AB*ue&twM\u0003\u00021[%\u0011Q'F\u0001\u0003S\u0012\f\u0001cX:ikR$wn\u001e8US6,w.\u001e;\u0011\u0005ajT\"A\u001d\u000b\u0005iZ\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005qj\u0013AC2p]\u000e,(O]3oi&\u0011a(\u000f\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0013\t\u0001U#A\btQV$Hm\\<o)&lWm\\;u\u0003EyF\u000f\u001b:fC\u0012\u0004vn\u001c7D_:4\u0017n\u001a\t\u0003)\rK!\u0001R\u0007\u0003!QC'/Z1e!>|GnQ8oM&<\u0017A\u0002\u001fj]&$h\b\u0006\u0004H\u0011&S5\n\u0014\t\u0003)\u0001AQa\u0006\u0004A\u0002aAQa\b\u0004A\u0002\u0001BQA\n\u0004A\u0002\u001dBQA\u000e\u0004A\u0002]BQ!\u0011\u0004A\u0002\t\u000bQa\\<oKJ,\u0012\u0001I\u0001\n_^tWM]0%KF$\"!U+\u0011\u0005I\u001bV\"A\u0017\n\u0005Qk#\u0001B+oSRDqA\u0016\u0005\u0002\u0002\u0003\u0007\u0001%A\u0002yIE\naa\\<oKJ\u0004\u0003FA\u0005Z!\t\u0011&,\u0003\u0002\\[\tAao\u001c7bi&dW-\u0001\u0005sK\u001eL7\u000f^3s)\t\tf\fC\u0003`\u0015\u0001\u0007\u0001%A\u0005bGR|'oQ3mY\u0006QQO\u001c:fO&\u001cH/\u001a:\u0015\u0005E\u0013\u0007\"B\u0012\f\u0001\u0004\u0001\u0003")
public class PinnedDispatcher
extends Dispatcher {
    private volatile ActorCell owner;

    private ActorCell owner() {
        return this.owner;
    }

    private void owner_$eq(ActorCell x$1) {
        this.owner = x$1;
    }

    @Override
    public void register(ActorCell actorCell) {
        ActorCell actor = this.owner();
        if (actor != null) {
            ActorCell actorCell2 = actorCell;
            ActorCell actorCell3 = actor;
            if (actorCell2 == null ? actorCell3 != null : !actorCell2.equals(actorCell3)) {
                throw new IllegalArgumentException(new StringBuilder(30).append("Cannot register to anyone but ").append(actor).toString());
            }
        }
        this.owner_$eq(actorCell);
        super.register(actorCell);
    }

    @Override
    public void unregister(ActorCell actor) {
        super.unregister(actor);
        this.owner_$eq(null);
    }

    public PinnedDispatcher(MessageDispatcherConfigurator _configurator, ActorCell _actor, String _id, FiniteDuration _shutdownTimeout, ThreadPoolConfig _threadPoolConfig) {
        boolean x$1 = true;
        boolean x$2 = true;
        boolean x$3 = _threadPoolConfig.copy$default$1();
        Duration x$4 = _threadPoolConfig.copy$default$4();
        Function0<BlockingQueue<Runnable>> x$5 = _threadPoolConfig.copy$default$5();
        RejectedExecutionHandler x$6 = _threadPoolConfig.copy$default$6();
        super(_configurator, _id, Integer.MAX_VALUE, Duration$.MODULE$.Zero(), _threadPoolConfig.copy(x$3, 1, 1, x$4, x$5, x$6), _shutdownTimeout);
        this.owner = _actor;
    }
}

