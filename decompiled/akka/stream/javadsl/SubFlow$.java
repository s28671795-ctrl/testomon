/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.javadsl.SubFlow;

public final class SubFlow$ {
    public static final SubFlow$ MODULE$ = new SubFlow$();

    public <In, SuperOut, Out extends SuperOut, M> SubFlow<In, SuperOut, M> upcast(SubFlow<In, Out, M> flow) {
        return flow;
    }

    private SubFlow$() {
    }
}

