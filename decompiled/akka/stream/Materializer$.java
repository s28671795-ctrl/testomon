/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRefFactory;
import akka.actor.ClassicActorContextProvider;
import akka.actor.ClassicActorSystemProvider;
import akka.stream.ActorMaterializer$;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;
import akka.stream.SystemMaterializer$;
import scala.None$;

public final class Materializer$ {
    public static final Materializer$ MODULE$ = new Materializer$();

    public Materializer matFromSystem(ClassicActorSystemProvider provider) {
        return ((SystemMaterializer)SystemMaterializer$.MODULE$.apply(provider.classicSystem())).materializer();
    }

    public Materializer apply(ClassicActorContextProvider contextProvider) {
        return ActorMaterializer$.MODULE$.apply(None$.MODULE$, None$.MODULE$, (ActorRefFactory)contextProvider.classicActorContext());
    }

    public Materializer createMaterializer(ClassicActorContextProvider contextProvider) {
        return this.apply(contextProvider);
    }

    public Materializer apply(ClassicActorSystemProvider systemProvider) {
        return ((SystemMaterializer)SystemMaterializer$.MODULE$.apply(systemProvider.classicSystem())).createAdditionalSystemMaterializer();
    }

    public Materializer createMaterializer(ClassicActorSystemProvider systemProvider) {
        return this.apply(systemProvider);
    }

    private Materializer$() {
    }
}

