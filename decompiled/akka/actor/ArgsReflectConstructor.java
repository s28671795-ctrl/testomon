/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import akka.util.Reflect$;
import java.lang.reflect.Constructor;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u3Qa\u0002\u0005\u0001\u00151A\u0001b\u0006\u0001\u0003\u0002\u0003\u0006I!\u0007\u0005\ta\u0001\u0011\t\u0011)A\u0005c!)A\b\u0001C\u0001{!1Q\t\u0001Q\u0001\n\u0019CQa\u0015\u0001\u0005BQCQa\u0017\u0001\u0005Bq\u0013a#\u0011:hgJ+g\r\\3di\u000e{gn\u001d;sk\u000e$xN\u001d\u0006\u0003\u0013)\tQ!Y2u_JT\u0011aC\u0001\u0005C.\\\u0017mE\u0002\u0001\u001bM\u0001\"AD\t\u000e\u0003=Q\u0011\u0001E\u0001\u0006g\u000e\fG.Y\u0005\u0003%=\u0011a!\u00118z%\u00164\u0007C\u0001\u000b\u0016\u001b\u0005A\u0011B\u0001\f\t\u0005UIe\u000eZ5sK\u000e$\u0018i\u0019;peB\u0013x\u000eZ;dKJ\f1a\u00197{\u0007\u0001\u0001$AG\u0014\u0011\u0007m\u0011SE\u0004\u0002\u001dAA\u0011QdD\u0007\u0002=)\u0011q\u0004G\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005z\u0011A\u0002)sK\u0012,g-\u0003\u0002$I\t)1\t\\1tg*\u0011\u0011e\u0004\t\u0003M\u001db\u0001\u0001B\u0005)\u0003\u0005\u0005\t\u0011!B\u0001S\t\u0019q\fJ\u001c\u0012\u0005)j\u0003C\u0001\b,\u0013\tasBA\u0004O_RD\u0017N\\4\u0011\u0005Qq\u0013BA\u0018\t\u0005\u0015\t5\r^8s\u0003\u0011\t'oZ:\u0011\u0007I:\u0014(D\u00014\u0015\t!T'A\u0005j[6,H/\u00192mK*\u0011agD\u0001\u000bG>dG.Z2uS>t\u0017B\u0001\u001d4\u0005\r\u0019V-\u001d\t\u0003\u001diJ!aO\b\u0003\u0007\u0005s\u00170\u0001\u0004=S:LGO\u0010\u000b\u0004}}\"\u0005C\u0001\u000b\u0001\u0011\u001592\u00011\u0001Aa\t\t5\tE\u0002\u001cE\t\u0003\"AJ\"\u0005\u0013!z\u0014\u0011!A\u0001\u0006\u0003I\u0003\"\u0002\u0019\u0004\u0001\u0004\t\u0014aC2p]N$(/^2u_J\u0004$a\u0012*\u0011\u0007!{\u0015+D\u0001J\u0015\tQ5*A\u0004sK\u001adWm\u0019;\u000b\u00051k\u0015\u0001\u00027b]\u001eT\u0011AT\u0001\u0005U\u00064\u0018-\u0003\u0002Q\u0013\nY1i\u001c8tiJ,8\r^8s!\t1#\u000bB\u0005)\t\u0005\u0005\t\u0011!B\u0001S\u0005Q\u0011m\u0019;pe\u000ec\u0017m]:\u0016\u0003U\u0003$A\u0016.\u0011\u0007]C\u0016,D\u0001L\u0013\t\u00193\n\u0005\u0002'5\u0012I\u0001&BA\u0001\u0002\u0003\u0015\t!K\u0001\baJ|G-^2f)\u0005i\u0003")
public class ArgsReflectConstructor
implements IndirectActorProducer {
    private final Class<? extends Actor> clz;
    private final Seq<Object> args;
    private final Constructor<? extends Actor> constructor;

    @Override
    public Class<? extends Actor> actorClass() {
        return this.clz;
    }

    @Override
    public Actor produce() {
        return Reflect$.MODULE$.instantiate(this.constructor, this.args);
    }

    public ArgsReflectConstructor(Class<? extends Actor> clz, Seq<Object> args2) {
        this.clz = clz;
        this.args = args2;
        this.constructor = Reflect$.MODULE$.findConstructor(clz, args2);
    }
}

