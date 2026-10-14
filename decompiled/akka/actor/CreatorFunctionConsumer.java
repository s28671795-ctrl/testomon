/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000552Q!\u0002\u0004\u0001\u0011)A\u0001\"\u0006\u0001\u0003\u0002\u0003\u0006Ia\u0006\u0005\u0006;\u0001!\tA\b\u0005\u0006C\u0001!\tE\t\u0005\u0006W\u0001!\t\u0005\f\u0002\u0018\u0007J,\u0017\r^8s\rVt7\r^5p]\u000e{gn];nKJT!a\u0002\u0005\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003%\tA!Y6lCN\u0019\u0001aC\t\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g!\t\u00112#D\u0001\u0007\u0013\t!bAA\u000bJ]\u0012L'/Z2u\u0003\u000e$xN\u001d)s_\u0012,8-\u001a:\u0002\u000f\r\u0014X-\u0019;pe\u000e\u0001\u0001c\u0001\u0007\u00195%\u0011\u0011$\u0004\u0002\n\rVt7\r^5p]B\u0002\"AE\u000e\n\u0005q1!!B!di>\u0014\u0018A\u0002\u001fj]&$h\b\u0006\u0002 AA\u0011!\u0003\u0001\u0005\u0006+\t\u0001\raF\u0001\u000bC\u000e$xN]\"mCN\u001cX#A\u0012\u0011\u0007\u0011J#$D\u0001&\u0015\t1s%\u0001\u0003mC:<'\"\u0001\u0015\u0002\t)\fg/Y\u0005\u0003U\u0015\u0012Qa\u00117bgN\fq\u0001\u001d:pIV\u001cW\rF\u0001\u001b\u0001")
public class CreatorFunctionConsumer
implements IndirectActorProducer {
    private final Function0<Actor> creator;

    public Class<Actor> actorClass() {
        return Actor.class;
    }

    @Override
    public Actor produce() {
        return this.creator.apply();
    }

    public CreatorFunctionConsumer(Function0<Actor> creator) {
        this.creator = creator;
    }
}

