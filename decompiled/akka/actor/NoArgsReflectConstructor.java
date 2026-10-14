/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import akka.util.Reflect$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00113Q!\u0002\u0004\u0001\u0011)A\u0001\"\u0006\u0001\u0003\u0002\u0003\u0006Ia\u0006\u0005\u0006]\u0001!\ta\f\u0005\u0006m\u0001!\te\u000e\u0005\u0006\u0005\u0002!\te\u0011\u0002\u0019\u001d>\f%oZ:SK\u001adWm\u0019;D_:\u001cHO];di>\u0014(BA\u0004\t\u0003\u0015\t7\r^8s\u0015\u0005I\u0011\u0001B1lW\u0006\u001c2\u0001A\u0006\u0012!\taq\"D\u0001\u000e\u0015\u0005q\u0011!B:dC2\f\u0017B\u0001\t\u000e\u0005\u0019\te.\u001f*fMB\u0011!cE\u0007\u0002\r%\u0011AC\u0002\u0002\u0016\u0013:$\u0017N]3di\u0006\u001bGo\u001c:Qe>$WoY3s\u0003\r\u0019GN_\u0002\u0001a\tAR\u0005E\u0002\u001aA\rr!A\u0007\u0010\u0011\u0005miQ\"\u0001\u000f\u000b\u0005u1\u0012A\u0002\u001fs_>$h(\u0003\u0002 \u001b\u00051\u0001K]3eK\u001aL!!\t\u0012\u0003\u000b\rc\u0017m]:\u000b\u0005}i\u0001C\u0001\u0013&\u0019\u0001!\u0011BJ\u0001\u0002\u0002\u0003\u0005)\u0011A\u0014\u0003\u0007}#\u0003(\u0005\u0002)WA\u0011A\"K\u0005\u0003U5\u0011qAT8uQ&tw\r\u0005\u0002\u0013Y%\u0011QF\u0002\u0002\u0006\u0003\u000e$xN]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005A\n\u0004C\u0001\n\u0001\u0011\u0015)\"\u00011\u00013a\t\u0019T\u0007E\u0002\u001aAQ\u0002\"\u0001J\u001b\u0005\u0013\u0019\n\u0014\u0011!A\u0001\u0006\u00039\u0013AC1di>\u00148\t\\1tgV\t\u0001\b\r\u0002:\u0003B\u0019!h\u0010!\u000e\u0003mR!\u0001P\u001f\u0002\t1\fgn\u001a\u0006\u0002}\u0005!!.\u0019<b\u0013\t\t3\b\u0005\u0002%\u0003\u0012IaeAA\u0001\u0002\u0003\u0015\taJ\u0001\baJ|G-^2f)\u0005Y\u0003")
public class NoArgsReflectConstructor
implements IndirectActorProducer {
    private final Class<? extends Actor> clz;

    @Override
    public Class<? extends Actor> actorClass() {
        return this.clz;
    }

    @Override
    public Actor produce() {
        return Reflect$.MODULE$.instantiate(this.clz);
    }

    public NoArgsReflectConstructor(Class<? extends Actor> clz) {
        this.clz = clz;
        Reflect$.MODULE$.findConstructor(clz, (Seq<Object>)package$.MODULE$.List().empty());
    }
}

