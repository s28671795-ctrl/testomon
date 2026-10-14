/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ssl;

import io.grpc.netty.shaded.io.netty.handler.ssl.OpenSslKeyMaterialProvider;
import io.grpc.netty.shaded.io.netty.handler.ssl.OpenSslSessionCache;
import io.grpc.netty.shaded.io.netty.handler.ssl.OpenSslSessionContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSL;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLContext;
import java.util.concurrent.locks.Lock;

public final class OpenSslServerSessionContext
extends OpenSslSessionContext {
    OpenSslServerSessionContext(ReferenceCountedOpenSslContext context, OpenSslKeyMaterialProvider provider) {
        super(context, provider, SSL.SSL_SESS_CACHE_SERVER, new OpenSslSessionCache(context.engineMap));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean setSessionIdContext(byte[] sidCtx) {
        Lock writerLock = this.context.ctxLock.writeLock();
        writerLock.lock();
        try {
            boolean bl = SSLContext.setSessionIdContext(this.context.ctx, sidCtx);
            return bl;
        }
        finally {
            writerLock.unlock();
        }
    }
}

