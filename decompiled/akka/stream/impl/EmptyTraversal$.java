/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Traversal;

@InternalApi
public final class EmptyTraversal$
implements Traversal {
    public static final EmptyTraversal$ MODULE$ = new EmptyTraversal$();

    static {
        Traversal.$init$(MODULE$);
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        return Traversal.rewireFirstTo$(this, relativeOffset);
    }

    @Override
    public Traversal concat(Traversal that) {
        return that;
    }

    private EmptyTraversal$() {
    }
}

