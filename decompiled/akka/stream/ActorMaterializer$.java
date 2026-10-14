/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorContext;
import akka.actor.ActorRefFactory;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerSettings;
import akka.stream.ActorMaterializerSettings$;
import akka.stream.SystemMaterializer;
import akka.stream.SystemMaterializer$;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import java.io.Serializable;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Some;

public final class ActorMaterializer$ {
    public static final ActorMaterializer$ MODULE$ = new ActorMaterializer$();

    public ActorMaterializer apply(Option<ActorMaterializerSettings> materializerSettings, Option<String> namePrefix, ActorRefFactory context) {
        ActorSystem system = this.actorSystemOf(context);
        ActorMaterializerSettings settings = (ActorMaterializerSettings)materializerSettings.getOrElse((Function0<ActorMaterializerSettings> & Serializable)() -> ((SystemMaterializer)SystemMaterializer$.MODULE$.apply(system)).materializerSettings());
        return this.apply(settings, (String)namePrefix.getOrElse((Function0<String> & Serializable)() -> "flow"), context);
    }

    public ActorMaterializer apply(ActorMaterializerSettings materializerSettings, String namePrefix, ActorRefFactory context) {
        ActorRefFactory actorRefFactory = context;
        if (actorRefFactory instanceof ActorSystem) {
            ActorSystem actorSystem = (ActorSystem)actorRefFactory;
            return (ActorMaterializer)((SystemMaterializer)SystemMaterializer$.MODULE$.apply(actorSystem)).createAdditionalLegacySystemMaterializer(namePrefix, materializerSettings);
        }
        if (actorRefFactory instanceof ActorContext) {
            ActorContext actorContext = (ActorContext)actorRefFactory;
            return PhasedFusingActorMaterializer$.MODULE$.apply(actorContext, namePrefix, materializerSettings, materializerSettings.toAttributes());
        }
        throw new IllegalArgumentException(new StringBuilder(28).append("Unexpected type of context: ").append(actorRefFactory).toString());
    }

    public ActorMaterializer apply(ActorMaterializerSettings materializerSettings, ActorRefFactory context) {
        return this.apply(new Some<ActorMaterializerSettings>(materializerSettings), None$.MODULE$, context);
    }

    public Option<ActorMaterializerSettings> apply$default$1() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$2() {
        return None$.MODULE$;
    }

    public ActorMaterializer create(ActorRefFactory context) {
        return this.apply(this.apply$default$1(), this.apply$default$2(), context);
    }

    public ActorMaterializer create(ActorRefFactory context, String namePrefix) {
        ActorSystem system = this.actorSystemOf(context);
        ActorMaterializerSettings settings = ActorMaterializerSettings$.MODULE$.apply(system);
        return this.apply(settings, namePrefix, context);
    }

    public ActorMaterializer create(ActorMaterializerSettings settings, ActorRefFactory context) {
        return this.apply(Option$.MODULE$.apply(settings), None$.MODULE$, context);
    }

    public ActorMaterializer create(ActorMaterializerSettings settings, ActorRefFactory context, String namePrefix) {
        return this.apply(Option$.MODULE$.apply(settings), Option$.MODULE$.apply(namePrefix), context);
    }

    private ActorSystem actorSystemOf(ActorRefFactory context) {
        ActorSystem actorSystem;
        ActorRefFactory actorRefFactory = context;
        if (actorRefFactory instanceof ExtendedActorSystem) {
            ExtendedActorSystem extendedActorSystem;
            actorSystem = extendedActorSystem = (ExtendedActorSystem)actorRefFactory;
        } else if (actorRefFactory instanceof ActorContext) {
            ActorContext actorContext = (ActorContext)actorRefFactory;
            actorSystem = actorContext.system();
        } else {
            if (actorRefFactory == null) {
                throw new IllegalArgumentException("ActorRefFactory context must be defined");
            }
            throw new IllegalArgumentException(new StringBuilder(70).append("ActorRefFactory context must be an ActorSystem or ActorContext, got [").append(context.getClass().getName()).append("]").toString());
        }
        ExtendedActorSystem system = actorSystem;
        return system;
    }

    private ActorMaterializer$() {
    }
}

