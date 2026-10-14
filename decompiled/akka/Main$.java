/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.Main;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystem$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props$;
import scala.Option;
import scala.Predef$;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NonFatal$;

public final class Main$ {
    public static final Main$ MODULE$ = new Main$();

    public void main(String[] args2) {
        if (args2.length != 1) {
            Predef$.MODULE$.println("you need to provide exactly one argument: the class of the application supervisor actor");
            return;
        }
        ActorSystem system = ActorSystem$.MODULE$.apply("Main");
        try {
            Class appClass = ((ExtendedActorSystem)system).dynamicAccess().getClassFor(args2[0], ClassTag$.MODULE$.apply(Actor.class)).get();
            ActorRef app = system.actorOf(Props$.MODULE$.apply(appClass, Nil$.MODULE$), "app");
            system.actorOf(Props$.MODULE$.apply(Main.Terminator.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{app})), "app-terminator");
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                system.terminate();
                throw e;
            }
            throw throwable;
        }
    }

    private Main$() {
    }
}

