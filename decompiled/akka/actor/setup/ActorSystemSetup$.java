/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.setup;

import akka.actor.setup.ActorSystemSetup;
import akka.actor.setup.Setup;
import java.io.Serializable;
import scala.$less$colon$less$;
import scala.Function1;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.runtime.ScalaRunTime$;

public final class ActorSystemSetup$ {
    public static final ActorSystemSetup$ MODULE$ = new ActorSystemSetup$();
    private static final ActorSystemSetup empty = new ActorSystemSetup((Map<Class<?>, Object>)Predef$.MODULE$.Map().empty());

    public ActorSystemSetup create(Setup ... settings) {
        return this.create(ScalaRunTime$.MODULE$.wrapRefArray((Object[])settings));
    }

    public ActorSystemSetup empty() {
        return empty;
    }

    public ActorSystemSetup apply(Seq<Setup> settings) {
        return new ActorSystemSetup(((IterableOnceOps)settings.map((Function1<Setup, Tuple2> & Serializable)s2 -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(s2.getClass()), s2))).toMap($less$colon$less$.MODULE$.refl()));
    }

    public ActorSystemSetup create(Seq<Setup> settings) {
        return this.apply(settings);
    }

    private ActorSystemSetup$() {
    }
}

