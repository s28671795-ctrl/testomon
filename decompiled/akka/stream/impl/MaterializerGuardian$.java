/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Materializer;
import akka.stream.impl.MaterializerGuardian;
import java.io.Serializable;
import scala.Function0;
import scala.concurrent.Promise;
import scala.reflect.ClassTag$;

@InternalApi
public final class MaterializerGuardian$ {
    public static final MaterializerGuardian$ MODULE$ = new MaterializerGuardian$();

    public Props props(Promise<Materializer> systemMaterializer, ActorMaterializerSettings materializerSettings) {
        return Props$.MODULE$.apply((Function0<MaterializerGuardian> & Serializable)() -> new MaterializerGuardian(systemMaterializer, materializerSettings), ClassTag$.MODULE$.apply(MaterializerGuardian.class));
    }

    private MaterializerGuardian$() {
    }
}

