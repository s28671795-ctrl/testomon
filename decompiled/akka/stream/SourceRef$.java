/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.NotUsed;
import akka.stream.SourceRef;
import akka.stream.scaladsl.Source;

public final class SourceRef$ {
    public static final SourceRef$ MODULE$ = new SourceRef$();

    public <T> Source<T, NotUsed> convertRefToSource(SourceRef<T> ref) {
        return ref.source();
    }

    private SourceRef$() {
    }
}

