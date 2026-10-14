/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.Function0;
import scala.Function1;
import scala.concurrent.BlockContext;
import scala.concurrent.BlockContext$DefaultBlockContext$;

public final class BlockContext$ {
    public static final BlockContext$ MODULE$ = new BlockContext$();
    private static final ThreadLocal<BlockContext> contextLocal = new ThreadLocal();

    public final BlockContext defaultBlockContext() {
        return BlockContext$DefaultBlockContext$.MODULE$;
    }

    private final BlockContext prefer(BlockContext candidate) {
        if (candidate != null) {
            return candidate;
        }
        Thread t2 = Thread.currentThread();
        if (t2 instanceof BlockContext) {
            return (BlockContext)((Object)t2);
        }
        return BlockContext$DefaultBlockContext$.MODULE$;
    }

    public final BlockContext current() {
        return this.prefer(contextLocal.get());
    }

    public final <T> T withBlockContext(BlockContext blockContext, Function0<T> body2) {
        T t2;
        BlockContext old = contextLocal.get();
        if (old == blockContext) {
            return body2.apply();
        }
        contextLocal.set(blockContext);
        try {
            t2 = body2.apply();
        }
        finally {
            contextLocal.set(old);
        }
        return t2;
    }

    public final <I, T> T usingBlockContext(BlockContext blockContext, Function1<BlockContext, T> f) {
        T t2;
        BlockContext old = contextLocal.get();
        if (old == blockContext) {
            return f.apply(this.prefer(old));
        }
        contextLocal.set(blockContext);
        try {
            t2 = f.apply(this.prefer(old));
        }
        finally {
            contextLocal.set(old);
        }
        return t2;
    }

    private BlockContext$() {
    }
}

