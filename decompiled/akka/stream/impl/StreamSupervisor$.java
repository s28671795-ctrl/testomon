/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Deploy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import akka.stream.impl.SeqActorName$;
import akka.stream.impl.SeqActorNameImpl;
import akka.stream.impl.StreamSupervisor;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Function0;
import scala.reflect.ClassTag$;

@InternalApi
public final class StreamSupervisor$ {
    public static final StreamSupervisor$ MODULE$ = new StreamSupervisor$();
    private static final String baseName = "StreamSupervisor";
    private static final SeqActorNameImpl actorName = SeqActorName$.MODULE$.apply(MODULE$.baseName());

    public Props props(Attributes attributes, AtomicBoolean haveShutDown) {
        return Props$.MODULE$.apply((Function0<StreamSupervisor> & Serializable)() -> new StreamSupervisor(haveShutDown), ClassTag$.MODULE$.apply(StreamSupervisor.class)).withDeploy(Deploy$.MODULE$.local()).withDispatcher(((ActorAttributes.Dispatcher)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher()).withMailbox(PhasedFusingActorMaterializer$.MODULE$.Mailbox());
    }

    public String baseName() {
        return baseName;
    }

    private SeqActorNameImpl actorName() {
        return actorName;
    }

    public String nextName() {
        return this.actorName().next();
    }

    private StreamSupervisor$() {
    }
}

