/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.scaladsl.MergePreferred;

public final class MergePreferred$ {
    public static final MergePreferred$ MODULE$ = new MergePreferred$();

    public <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(int secondaryPorts) {
        return akka.stream.scaladsl.MergePreferred$.MODULE$.apply(secondaryPorts, akka.stream.scaladsl.MergePreferred$.MODULE$.apply$default$2());
    }

    public <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(Class<T> clazz, int secondaryPorts) {
        return this.create(secondaryPorts);
    }

    public <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(int secondaryPorts, boolean eagerComplete) {
        return akka.stream.scaladsl.MergePreferred$.MODULE$.apply(secondaryPorts, eagerComplete);
    }

    public <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(Class<T> clazz, int secondaryPorts, boolean eagerComplete) {
        return this.create(secondaryPorts, eagerComplete);
    }

    private MergePreferred$() {
    }
}

