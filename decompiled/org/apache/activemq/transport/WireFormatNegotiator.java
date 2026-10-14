/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.util.IOExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WireFormatNegotiator
extends TransportFilter {
    private static final Logger LOG = LoggerFactory.getLogger(WireFormatNegotiator.class);
    private OpenWireFormat wireFormat;
    private final int minimumVersion;
    private long negotiateTimeout = 15000L;
    private final AtomicBoolean firstStart = new AtomicBoolean(true);
    private final CountDownLatch readyCountDownLatch = new CountDownLatch(1);
    private final CountDownLatch wireInfoSentDownLatch = new CountDownLatch(1);

    public WireFormatNegotiator(Transport next, OpenWireFormat wireFormat, int minimumVersion) {
        super(next);
        this.wireFormat = wireFormat;
        if (minimumVersion <= 0) {
            minimumVersion = 1;
        }
        this.minimumVersion = minimumVersion;
        try {
            if (wireFormat.getPreferedWireFormatInfo() != null) {
                this.setNegotiateTimeout(wireFormat.getPreferedWireFormatInfo().getMaxInactivityDurationInitalDelay());
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void start() throws Exception {
        super.start();
        if (this.firstStart.compareAndSet(true, false)) {
            this.sendWireFormat();
        }
    }

    public void sendWireFormat() throws IOException {
        try {
            WireFormatInfo info = this.wireFormat.getPreferedWireFormatInfo();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending: " + info);
            }
            this.sendWireFormat(info);
        }
        finally {
            this.wireInfoSentDownLatch.countDown();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        this.readyCountDownLatch.countDown();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void oneway(Object command) throws IOException {
        boolean wasInterrupted = Thread.interrupted();
        try {
            if (this.readyCountDownLatch.getCount() > 0L && !this.readyCountDownLatch.await(this.negotiateTimeout, TimeUnit.MILLISECONDS)) {
                throw new IOException("Wire format negotiation timeout: peer did not send his wire format.");
            }
        }
        catch (InterruptedException e) {
            InterruptedIOException interruptedIOException = new InterruptedIOException("Interrupted waiting for wire format negotiation");
            interruptedIOException.initCause(e);
            try {
                this.onException(interruptedIOException);
            }
            finally {
                Thread.currentThread().interrupt();
                wasInterrupted = false;
            }
            throw interruptedIOException;
        }
        finally {
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
        super.oneway(command);
    }

    @Override
    public void onCommand(Object o) {
        Command command = (Command)o;
        if (command.isWireFormatInfo()) {
            WireFormatInfo info = (WireFormatInfo)command;
            this.negociate(info);
        }
        this.getTransportListener().onCommand(command);
    }

    public void negociate(WireFormatInfo info) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received WireFormat: " + info);
        }
        try {
            this.wireInfoSentDownLatch.await();
            if (LOG.isDebugEnabled()) {
                LOG.debug(this + " before negotiation: " + this.wireFormat);
            }
            if (!info.isValid()) {
                this.onException(new IOException("Remote wire format magic is invalid"));
            } else if (info.getVersion() < this.minimumVersion) {
                this.onException(new IOException("Remote wire format (" + info.getVersion() + ") is lower the minimum version required (" + this.minimumVersion + ")"));
            }
            this.wireFormat.renegotiateWireFormat(info);
            Socket socket = this.next.narrow(Socket.class);
            if (socket != null) {
                socket.setTcpNoDelay(this.wireFormat.isTcpNoDelayEnabled());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(this + " after negotiation: " + this.wireFormat);
            }
        }
        catch (IOException e) {
            this.onException(e);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.onException((IOException)new InterruptedIOException().initCause(e));
        }
        catch (Exception e) {
            this.onException(IOExceptionSupport.create(e));
        }
        this.readyCountDownLatch.countDown();
        this.onWireFormatNegotiated(info);
    }

    @Override
    public void onException(IOException error) {
        this.readyCountDownLatch.countDown();
        super.onException(error);
    }

    @Override
    public String toString() {
        return this.next.toString();
    }

    protected void sendWireFormat(WireFormatInfo info) throws IOException {
        this.next.oneway(info);
    }

    protected void onWireFormatNegotiated(WireFormatInfo info) {
    }

    public long getNegotiateTimeout() {
        return this.negotiateTimeout;
    }

    public void setNegotiateTimeout(long negotiateTimeout) {
        this.negotiateTimeout = negotiateTimeout;
    }
}

