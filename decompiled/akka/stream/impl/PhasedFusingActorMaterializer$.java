/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Deploy$;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.dispatch.Dispatchers;
import akka.stream.ActorAttributes;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.impl.FlowNames;
import akka.stream.impl.FlowNames$;
import akka.stream.impl.GraphStageIsland;
import akka.stream.impl.GraphStageTag$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Phase;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.ProcessorModuleIslandTag$;
import akka.stream.impl.ProcessorModulePhase;
import akka.stream.impl.SeqActorName;
import akka.stream.impl.SinkModuleIslandTag$;
import akka.stream.impl.SinkModulePhase;
import akka.stream.impl.SourceModuleIslandTag$;
import akka.stream.impl.SourceModulePhase;
import akka.stream.impl.StreamSupervisor$;
import akka.stream.impl.TlsModuleIslandTag$;
import akka.stream.impl.TlsModulePhase;
import akka.util.OptionVal$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.Tuple7;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.reflect.ClassTag$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;

@InternalApi
public final class PhasedFusingActorMaterializer$
implements Serializable {
    public static final PhasedFusingActorMaterializer$ MODULE$ = new PhasedFusingActorMaterializer$();
    private static final boolean Debug = false;
    private static final String Mailbox = "akka.stream.materializer.mailbox";
    private static final Phase<Object> DefaultPhase = new Phase<Object>(){

        public PhaseIsland<Object> apply(ActorMaterializerSettings settings, Attributes effectiveAttributes, PhasedFusingActorMaterializer materializer, String islandName) {
            OptionVal$.MODULE$.None();
            return new GraphStageIsland(effectiveAttributes, materializer, islandName, null);
        }
    };
    private static final Map<IslandTag, Phase<Object>> DefaultPhases = (Map)Map$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(SinkModuleIslandTag$.MODULE$), new Phase<Object>(){

        public PhaseIsland<Object> apply(ActorMaterializerSettings settings, Attributes effectiveAttributes, PhasedFusingActorMaterializer materializer, String islandName) {
            return new SinkModulePhase(materializer, islandName);
        }
    }), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(SourceModuleIslandTag$.MODULE$), new Phase<Object>(){

        public PhaseIsland<Object> apply(ActorMaterializerSettings settings, Attributes effectiveAttributes, PhasedFusingActorMaterializer materializer, String islandName) {
            return new SourceModulePhase(materializer, islandName);
        }
    }), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ProcessorModuleIslandTag$.MODULE$), new Phase<Object>(){

        public PhaseIsland<Object> apply(ActorMaterializerSettings settings, Attributes effectiveAttributes, PhasedFusingActorMaterializer materializer, String islandName) {
            return new ProcessorModulePhase();
        }
    }), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(TlsModuleIslandTag$.MODULE$), new Phase<Object>(){

        public PhaseIsland<Object> apply(ActorMaterializerSettings settings, Attributes effectiveAttributes, PhasedFusingActorMaterializer materializer, String islandName) {
            return new TlsModulePhase(materializer, islandName);
        }
    }), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(GraphStageTag$.MODULE$), MODULE$.DefaultPhase())}));

    public boolean Debug() {
        return Debug;
    }

    public String Mailbox() {
        return Mailbox;
    }

    public Phase<Object> DefaultPhase() {
        return DefaultPhase;
    }

    public Map<IslandTag, Phase<Object>> DefaultPhases() {
        return DefaultPhases;
    }

    public PhasedFusingActorMaterializer apply(ActorContext context, String namePrefix, ActorMaterializerSettings settings, Attributes attributes) {
        AtomicBoolean haveShutDown = new AtomicBoolean(false);
        String dispatcher = ((ActorAttributes.Dispatcher)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher();
        Props supervisorProps = StreamSupervisor$.MODULE$.props(attributes, haveShutDown).withDispatcher(dispatcher).withMailbox(this.Mailbox()).withDeploy(Deploy$.MODULE$.local());
        ActorRef streamSupervisor = context.actorOf(supervisorProps, StreamSupervisor$.MODULE$.nextName());
        return new PhasedFusingActorMaterializer(context.system(), settings, attributes, context.system().dispatchers(), streamSupervisor, haveShutDown, ((FlowNames)FlowNames$.MODULE$.apply(context.system())).name().copy(namePrefix));
    }

    public PhasedFusingActorMaterializer apply(ActorSystem system, ActorMaterializerSettings settings, Attributes defaultAttributes, Dispatchers dispatchers, ActorRef supervisor, AtomicBoolean haveShutDown, SeqActorName flowNames) {
        return new PhasedFusingActorMaterializer(system, settings, defaultAttributes, dispatchers, supervisor, haveShutDown, flowNames);
    }

    public Option<Tuple7<ActorSystem, ActorMaterializerSettings, Attributes, Dispatchers, ActorRef, AtomicBoolean, SeqActorName>> unapply(PhasedFusingActorMaterializer x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple7<ActorSystem, ActorMaterializerSettings, Attributes, Dispatchers, ActorRef, AtomicBoolean, SeqActorName>>(new Tuple7<ActorSystem, ActorMaterializerSettings, Attributes, Dispatchers, ActorRef, AtomicBoolean, SeqActorName>(x$0.system(), x$0.settings(), x$0.defaultAttributes(), x$0.dispatchers(), x$0.supervisor(), x$0.haveShutDown(), x$0.flowNames()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PhasedFusingActorMaterializer$.class);
    }

    private PhasedFusingActorMaterializer$() {
    }
}

