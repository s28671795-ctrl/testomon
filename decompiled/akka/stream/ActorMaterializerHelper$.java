/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

public final class ActorMaterializerHelper$ {
    public static final ActorMaterializerHelper$ MODULE$ = new ActorMaterializerHelper$();

    public ActorMaterializer downcast(Materializer materializer) {
        Materializer materializer2 = materializer;
        if (materializer2 instanceof ActorMaterializer) {
            ActorMaterializer actorMaterializer = (ActorMaterializer)materializer2;
            return actorMaterializer;
        }
        throw new IllegalArgumentException(new StringBuilder(22).append("required [").append(ActorMaterializer.class.getName()).append("] ").append("but got [").append(materializer.getClass().getName()).append("]").toString());
    }

    private ActorMaterializerHelper$() {
    }
}

