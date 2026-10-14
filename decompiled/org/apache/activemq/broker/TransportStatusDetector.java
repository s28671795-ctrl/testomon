/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.Service;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportStatusDetector
implements Service,
Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(TransportStatusDetector.class);
    private TransportConnector connector;
    private Set<TransportConnection> collectionCandidates = new CopyOnWriteArraySet<TransportConnection>();
    private AtomicBoolean started = new AtomicBoolean(false);
    private Thread runner;
    private int sweepInterval = 5000;

    TransportStatusDetector(TransportConnector connector) {
        this.connector = connector;
    }

    public int getSweepInterval() {
        return this.sweepInterval;
    }

    public void setSweepInterval(int sweepInterval) {
        this.sweepInterval = sweepInterval;
    }

    protected void doCollection() {
        for (TransportConnection tc : this.collectionCandidates) {
            if (tc.isMarkedCandidate()) {
                if (tc.isBlockedCandidate()) {
                    this.collectionCandidates.remove(tc);
                    this.doCollection(tc);
                    continue;
                }
                tc.doMark();
                continue;
            }
            this.collectionCandidates.remove(tc);
        }
    }

    protected void doSweep() {
        for (TransportConnection connection : this.connector.getConnections()) {
            if (!connection.isMarkedCandidate()) continue;
            connection.doMark();
            this.collectionCandidates.add(connection);
        }
    }

    protected void doCollection(TransportConnection tc) {
        LOG.warn("found a blocked client - stopping: {}", (Object)tc);
        try {
            tc.stop();
        }
        catch (Exception e) {
            LOG.error("Error stopping {}", (Object)tc, (Object)e);
        }
    }

    @Override
    public void run() {
        while (this.started.get()) {
            try {
                this.doCollection();
                this.doSweep();
                Thread.sleep(this.sweepInterval);
            }
            catch (Throwable e) {
                LOG.error("failed to complete a sweep for blocked clients", e);
            }
        }
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.runner = new Thread(this, "ActiveMQ Transport Status Monitor: " + this.connector);
            this.runner.setDaemon(true);
            this.runner.setPriority(9);
            this.runner.start();
        }
    }

    @Override
    public void stop() throws Exception {
        this.started.set(false);
        if (this.runner != null) {
            this.runner.join((long)this.getSweepInterval() * 5L);
        }
    }
}

