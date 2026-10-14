/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.transport.AbstractInactivityMonitor;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InactivityMonitor
extends AbstractInactivityMonitor {
    private static final Logger LOG = LoggerFactory.getLogger(InactivityMonitor.class);
    private WireFormatInfo localWireFormatInfo;
    private WireFormatInfo remoteWireFormatInfo;
    private boolean ignoreRemoteWireFormat = false;
    private boolean ignoreAllWireFormatInfo = false;

    public InactivityMonitor(Transport next, WireFormat wireFormat) {
        super(next, wireFormat);
        if (this.wireFormat == null) {
            this.ignoreAllWireFormatInfo = true;
        }
    }

    @Override
    public void start() throws Exception {
        if (!this.isMonitorStarted()) {
            this.startConnectCheckTask();
        }
        super.start();
    }

    @Override
    protected void processInboundWireFormatInfo(WireFormatInfo info) throws IOException {
        this.stopConnectCheckTask();
        IOException error = null;
        this.remoteWireFormatInfo = info;
        try {
            this.startMonitorThreads();
        }
        catch (IOException e) {
            error = e;
        }
        if (error != null) {
            this.onException(error);
        }
    }

    @Override
    protected void processOutboundWireFormatInfo(WireFormatInfo info) throws IOException {
        this.localWireFormatInfo = info;
        this.startMonitorThreads();
    }

    @Override
    protected synchronized void startMonitorThreads() throws IOException {
        if (this.isMonitorStarted()) {
            return;
        }
        long readCheckTime = this.getReadCheckTime();
        if (readCheckTime > 0L) {
            this.setWriteCheckTime(this.writeCheckValueFromReadCheck(readCheckTime));
        }
        super.startMonitorThreads();
    }

    private long writeCheckValueFromReadCheck(long readCheckTime) {
        return readCheckTime > 3L ? readCheckTime / 3L : readCheckTime;
    }

    @Override
    protected boolean configuredOk() throws IOException {
        boolean configured = false;
        if (this.ignoreAllWireFormatInfo) {
            configured = true;
        } else if (this.localWireFormatInfo != null && this.remoteWireFormatInfo != null) {
            if (!this.ignoreRemoteWireFormat) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Using min of local: " + this.localWireFormatInfo + " and remote: " + this.remoteWireFormatInfo);
                }
                long readCheckTime = Math.min(this.localWireFormatInfo.getMaxInactivityDuration(), this.remoteWireFormatInfo.getMaxInactivityDuration());
                long writeCheckTime = this.writeCheckValueFromReadCheck(readCheckTime);
                this.setReadCheckTime(readCheckTime);
                this.setInitialDelayTime(Math.min(this.localWireFormatInfo.getMaxInactivityDurationInitalDelay(), this.remoteWireFormatInfo.getMaxInactivityDurationInitalDelay()));
                this.setWriteCheckTime(writeCheckTime);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Using local: " + this.localWireFormatInfo);
                }
                long readCheckTime = this.localWireFormatInfo.getMaxInactivityDuration();
                long writeCheckTime = this.writeCheckValueFromReadCheck(readCheckTime);
                this.setReadCheckTime(readCheckTime);
                this.setInitialDelayTime(this.localWireFormatInfo.getMaxInactivityDurationInitalDelay());
                this.setWriteCheckTime(writeCheckTime);
            }
            configured = true;
        }
        return configured;
    }

    public boolean isIgnoreAllWireFormatInfo() {
        return this.ignoreAllWireFormatInfo;
    }

    public void setIgnoreAllWireFormatInfo(boolean ignoreAllWireFormatInfo) {
        this.ignoreAllWireFormatInfo = ignoreAllWireFormatInfo;
    }

    public boolean isIgnoreRemoteWireFormat() {
        return this.ignoreRemoteWireFormat;
    }

    public void setIgnoreRemoteWireFormat(boolean ignoreRemoteWireFormat) {
        this.ignoreRemoteWireFormat = ignoreRemoteWireFormat;
    }
}

