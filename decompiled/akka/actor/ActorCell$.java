/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.IllegalActorStateException$;
import akka.actor.Props;
import akka.actor.Props$;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import scala.Function0;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.HashSet$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

public final class ActorCell$ {
    public static final ActorCell$ MODULE$ = new ActorCell$();
    private static final ThreadLocal<List<ActorContext>> contextStack = new ThreadLocal<List<ActorContext>>(){

        public List<ActorContext> initialValue() {
            return Nil$.MODULE$;
        }
    };
    private static final Cancellable emptyCancellable = new Cancellable(){

        public boolean isCancelled() {
            return false;
        }

        public boolean cancel() {
            return false;
        }
    };
    private static final List<PartialFunction<Object, BoxedUnit>> emptyBehaviorStack = Nil$.MODULE$;
    private static final Set<ActorRef> emptyActorRefSet = HashSet$.MODULE$.empty();
    private static final Props terminatedProps = Props$.MODULE$.apply((Function0<Actor> & Serializable)() -> {
        throw IllegalActorStateException$.MODULE$.apply("This Actor has been terminated");
    }, ClassTag$.MODULE$.apply(Actor.class));

    public ThreadLocal<List<ActorContext>> contextStack() {
        return contextStack;
    }

    public final Cancellable emptyCancellable() {
        return emptyCancellable;
    }

    public final List<PartialFunction<Object, BoxedUnit>> emptyBehaviorStack() {
        return emptyBehaviorStack;
    }

    public final Set<ActorRef> emptyActorRefSet() {
        return emptyActorRefSet;
    }

    public final Props terminatedProps() {
        return terminatedProps;
    }

    public final int undefinedUid() {
        return 0;
    }

    public final int newUid() {
        int uid;
        while ((uid = ThreadLocalRandom.current().nextInt()) == 0) {
        }
        return uid;
    }

    public final Tuple2<String, Object> splitNameAndUid(String name) {
        int i = name.indexOf(35);
        if (i < 0) {
            return new Tuple2<String, Object>(name, BoxesRunTime.boxToInteger(0));
        }
        return new Tuple2<String, Object>(name.substring(0, i), BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int(Integer.valueOf(name.substring(i + 1)))));
    }

    public final int DefaultState() {
        return 0;
    }

    public final int SuspendedState() {
        return 1;
    }

    public final int SuspendedWaitForChildrenState() {
        return 2;
    }

    private ActorCell$() {
    }
}

