/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.MergePreferred;

public final class MergePreferred$ {
    public static final MergePreferred$ MODULE$ = new MergePreferred$();

    public <T> MergePreferred<T> apply(int secondaryPorts, boolean eagerComplete) {
        return new MergePreferred(secondaryPorts, eagerComplete);
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    private MergePreferred$() {
    }
}

