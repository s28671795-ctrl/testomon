/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3QAB\u0004\u0001\u0013-A\u0001B\u0006\u0001\u0003\u0002\u0003\u0006I\u0001\u0007\u0005\t_\u0001\u0011\t\u0011)A\u0005a!)1\u0007\u0001C\u0001i!)A\b\u0001C!{!)\u0001\n\u0001C!\u0013\naB+\u001f9fI\u000e\u0013X-\u0019;pe\u001a+hn\u0019;j_:\u001cuN\\:v[\u0016\u0014(B\u0001\u0005\n\u0003\u0015\t7\r^8s\u0015\u0005Q\u0011\u0001B1lW\u0006\u001c2\u0001\u0001\u0007\u0013!\ti\u0001#D\u0001\u000f\u0015\u0005y\u0011!B:dC2\f\u0017BA\t\u000f\u0005\u0019\te.\u001f*fMB\u00111\u0003F\u0007\u0002\u000f%\u0011Qc\u0002\u0002\u0016\u0013:$\u0017N]3di\u0006\u001bGo\u001c:Qe>$WoY3s\u0003\r\u0019GN_\u0002\u0001a\tIb\u0005E\u0002\u001bC\u0011r!aG\u0010\u0011\u0005qqQ\"A\u000f\u000b\u0005y9\u0012A\u0002\u001fs_>$h(\u0003\u0002!\u001d\u00051\u0001K]3eK\u001aL!AI\u0012\u0003\u000b\rc\u0017m]:\u000b\u0005\u0001r\u0001CA\u0013'\u0019\u0001!\u0011bJ\u0001\u0002\u0002\u0003\u0005)\u0011\u0001\u0015\u0003\u0007}#c'\u0005\u0002*YA\u0011QBK\u0005\u0003W9\u0011qAT8uQ&tw\r\u0005\u0002\u0014[%\u0011af\u0002\u0002\u0006\u0003\u000e$xN]\u0001\bGJ,\u0017\r^8s!\ri\u0011\u0007L\u0005\u0003e9\u0011\u0011BR;oGRLwN\u001c\u0019\u0002\rqJg.\u001b;?)\r)dg\u000f\t\u0003'\u0001AQAF\u0002A\u0002]\u0002$\u0001\u000f\u001e\u0011\u0007i\t\u0013\b\u0005\u0002&u\u0011IqENA\u0001\u0002\u0003\u0015\t\u0001\u000b\u0005\u0006_\r\u0001\r\u0001M\u0001\u000bC\u000e$xN]\"mCN\u001cX#\u0001 1\u0005}:\u0005c\u0001!F\r6\t\u0011I\u0003\u0002C\u0007\u0006!A.\u00198h\u0015\u0005!\u0015\u0001\u00026bm\u0006L!AI!\u0011\u0005\u0015:E!C\u0014\u0005\u0003\u0003\u0005\tQ!\u0001)\u0003\u001d\u0001(o\u001c3vG\u0016$\u0012\u0001\f")
public class TypedCreatorFunctionConsumer
implements IndirectActorProducer {
    private final Class<? extends Actor> clz;
    private final Function0<Actor> creator;

    @Override
    public Class<? extends Actor> actorClass() {
        return this.clz;
    }

    @Override
    public Actor produce() {
        return this.creator.apply();
    }

    public TypedCreatorFunctionConsumer(Class<? extends Actor> clz, Function0<Actor> creator) {
        this.clz = clz;
        this.creator = creator;
    }
}

