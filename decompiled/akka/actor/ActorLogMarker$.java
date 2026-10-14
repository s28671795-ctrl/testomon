/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.annotation.ApiMayChange;
import akka.event.LogMarker;
import akka.event.LogMarker$;
import akka.event.LogMarker$Properties$;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.runtime.ScalaRunTime$;

@ApiMayChange
public final class ActorLogMarker$ {
    public static final ActorLogMarker$ MODULE$ = new ActorLogMarker$();

    public LogMarker deadLetter(String messageClass) {
        return LogMarker$.MODULE$.apply("akkaDeadLetter", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(LogMarker$Properties$.MODULE$.MessageClass()), messageClass)})));
    }

    private ActorLogMarker$() {
    }
}

