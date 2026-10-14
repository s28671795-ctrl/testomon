/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;
import akka.pattern.AskableActorRef$;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;
import akka.stream.SystemMaterializer$;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.StreamSupervisor;
import akka.stream.impl.fusing.ActorGraphInterpreter$Snapshot$;
import akka.stream.snapshot.StreamSnapshot;
import akka.util.Timeout;
import akka.util.Timeout$;
import java.io.Serializable;
import scala.Function1;
import scala.collection.immutable.Seq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ClassTag$;

public final class MaterializerState$ {
    public static final MaterializerState$ MODULE$ = new MaterializerState$();

    @ApiMayChange
    public Future<Seq<StreamSnapshot>> streamSnapshots(ActorSystem system) {
        Materializer materializer = ((SystemMaterializer)SystemMaterializer$.MODULE$.apply(system)).materializer();
        if (materializer instanceof PhasedFusingActorMaterializer) {
            PhasedFusingActorMaterializer phasedFusingActorMaterializer = (PhasedFusingActorMaterializer)materializer;
            return this.requestFromSupervisor(phasedFusingActorMaterializer.supervisor(), phasedFusingActorMaterializer.system().dispatchers().internalDispatcher());
        }
        throw new IllegalArgumentException(new StringBuilder(30).append("Unsupported Materializer type ").append(materializer.getClass()).toString());
    }

    @ApiMayChange
    public Future<Seq<StreamSnapshot>> streamSnapshots(Materializer mat) {
        Materializer materializer = mat;
        if (materializer instanceof PhasedFusingActorMaterializer) {
            PhasedFusingActorMaterializer phasedFusingActorMaterializer = (PhasedFusingActorMaterializer)materializer;
            return this.requestFromSupervisor(phasedFusingActorMaterializer.supervisor(), phasedFusingActorMaterializer.system().dispatchers().internalDispatcher());
        }
        throw new IllegalArgumentException(new StringBuilder(30).append("Unsupported Materializer type ").append(materializer.getClass()).toString());
    }

    @InternalApi
    public Future<Seq<StreamSnapshot>> requestFromSupervisor(ActorRef supervisor, ExecutionContext ec) {
        Timeout timeout2 = Timeout$.MODULE$.durationToTimeout(new package.DurationInt(package$.MODULE$.DurationInt(10)).seconds());
        ActorRef qual$1 = akka.pattern.package$.MODULE$.ask(supervisor);
        StreamSupervisor.GetChildrenSnapshots x$12 = new StreamSupervisor.GetChildrenSnapshots(timeout2.duration());
        Timeout x$2 = timeout2;
        ActorRef x$3 = AskableActorRef$.MODULE$.askWithStatus$default$3$extension(qual$1, x$12);
        return AskableActorRef$.MODULE$.askWithStatus$extension(qual$1, x$12, x$2, x$3).mapTo(ClassTag$.MODULE$.apply(StreamSupervisor.ChildrenSnapshots.class)).map((Function1<StreamSupervisor.ChildrenSnapshots, Seq> & Serializable)x$1 -> x$1.seq(), ec);
    }

    @InternalApi
    public Future<StreamSnapshot> requestFromChild(ActorRef child) {
        Timeout timeout2 = Timeout$.MODULE$.durationToTimeout(new package.DurationInt(package$.MODULE$.DurationInt(10)).seconds());
        ActorRef qual$1 = akka.pattern.package$.MODULE$.ask(child);
        ActorGraphInterpreter$Snapshot$ x$1 = ActorGraphInterpreter$Snapshot$.MODULE$;
        Timeout x$2 = timeout2;
        ActorRef x$3 = AskableActorRef$.MODULE$.$qmark$default$3$extension(qual$1, x$1);
        return AskableActorRef$.MODULE$.$qmark$extension(qual$1, x$1, x$2, x$3).mapTo(ClassTag$.MODULE$.apply(StreamSnapshot.class));
    }

    private MaterializerState$() {
    }
}

