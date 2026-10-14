/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.transport;

import com.hierynomus.protocol.PacketData;
import com.hierynomus.protocol.transport.PacketReceiver;
import com.hierynomus.protocol.transport.TransportException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PacketReader<D extends PacketData<?>>
implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected InputStream in;
    private PacketReceiver<D> handler;
    private AtomicBoolean stopped = new AtomicBoolean(false);
    private Thread thread;

    public PacketReader(String host2, InputStream in, PacketReceiver<D> handler) {
        this.in = in;
        this.handler = handler;
        this.thread = new Thread(this, "Packet Reader for " + host2);
        this.thread.setDaemon(true);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && !this.stopped.get()) {
            try {
                this.readPacket();
            }
            catch (TransportException e) {
                if (this.stopped.get()) break;
                this.logger.info("PacketReader error, got exception.", e);
                this.handler.handleError(e);
                return;
            }
        }
        if (this.stopped.get()) {
            this.logger.info("{} stopped.", (Object)this.thread);
        }
    }

    public void stop() {
        this.logger.debug("Stopping PacketReader...");
        this.stopped.set(true);
        this.thread.interrupt();
    }

    private void readPacket() throws TransportException {
        D packet = this.doRead();
        this.logger.debug("Received packet {}", (Object)packet);
        this.handler.handle(packet);
    }

    protected abstract D doRead() throws TransportException;

    public void start() {
        this.logger.debug("Starting PacketReader on thread: {}", (Object)this.thread.getName());
        this.thread.start();
    }
}

