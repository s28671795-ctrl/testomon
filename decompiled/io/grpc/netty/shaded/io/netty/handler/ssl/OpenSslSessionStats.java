/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ssl;

import io.grpc.netty.shaded.io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLContext;
import java.util.concurrent.locks.Lock;

public final class OpenSslSessionStats {
    private final ReferenceCountedOpenSslContext context;

    OpenSslSessionStats(ReferenceCountedOpenSslContext context) {
        this.context = context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long number() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionNumber(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long connect() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionConnect(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long connectGood() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionConnectGood(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long connectRenegotiate() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionConnectRenegotiate(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long accept() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionAccept(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long acceptGood() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionAcceptGood(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long acceptRenegotiate() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionAcceptRenegotiate(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long hits() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionHits(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long cbHits() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionCbHits(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long misses() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionMisses(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long timeouts() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionTimeouts(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long cacheFull() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionCacheFull(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyFail() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionTicketKeyFail(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyNew() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionTicketKeyNew(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyRenew() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionTicketKeyRenew(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyResume() {
        Lock readerLock = this.context.ctxLock.readLock();
        readerLock.lock();
        try {
            long l = SSLContext.sessionTicketKeyResume(this.context.ctx);
            return l;
        }
        finally {
            readerLock.unlock();
        }
    }
}

