/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005)2\u0011b\u0001\u0003\u0011\u0002\u0007\u0005\u0001B\u0003\r\t\u000bE\u0001A\u0011A\n\t\u000b]\u0001A\u0011A\n\u0003\u0019M{g\r^*ikR$wn\u001e8\u000b\u0005\u00151\u0011\u0001B5na2T!a\u0002\u0005\u0002\rM$(/Z1n\u0015\u0005I\u0011\u0001B1lW\u0006\u001c\"\u0001A\u0006\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uI\r\u0001A#\u0001\u000b\u0011\u00051)\u0012B\u0001\f\u000e\u0005\u0011)f.\u001b;\u0002\u0019M|g\r^*ikR$wn\u001e8\u0013\u0007eYRD\u0002\u0003\u001b\u0001\u0001A\"\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004C\u0001\u000f\u0001\u001b\u0005!\u0001C\u0001\u0010\"\u001b\u0005y\"B\u0001\u0011\t\u0003\u0015\t7\r^8s\u0013\t\u0011sDA\u0003BGR|'\u000f\u000b\u0002\u0001IA\u0011Q\u0005K\u0007\u0002M)\u0011q\u0005C\u0001\u000bC:tw\u000e^1uS>t\u0017BA\u0015'\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public interface SoftShutdown {
    public static /* synthetic */ void softShutdown$(SoftShutdown $this) {
        $this.softShutdown();
    }

    default public void softShutdown() {
        Iterable<ActorRef> children = ((Actor)((Object)this)).context().children();
        if (children.isEmpty()) {
            ((Actor)((Object)this)).context().stop(((Actor)((Object)this)).self());
            return;
        }
        ((Actor)((Object)this)).context().children().foreach((Function1<ActorRef, ActorRef> & Serializable)subject -> ((Actor)((Object)this)).context().watch((ActorRef)subject));
        ((Actor)((Object)this)).context().become((PartialFunction<Object, BoxedUnit>)((Object)new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SoftShutdown $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Terminated) {
                    if (((Actor)((Object)this.$outer)).context().children().isEmpty()) {
                        ((Actor)((Object)this.$outer)).context().stop(((Actor)((Object)this.$outer)).self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return (B1)BoxedUnit.UNIT;
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Terminated) {
                    return true;
                }
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }));
    }

    public static void $init$(SoftShutdown $this) {
    }
}

