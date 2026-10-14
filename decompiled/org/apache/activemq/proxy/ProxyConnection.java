/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.proxy;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.Service;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ProxyConnection
implements Service {
    private static final Logger LOG = LoggerFactory.getLogger(ProxyConnection.class);
    protected final Transport localTransport;
    protected final Transport remoteTransport;
    private final AtomicBoolean shuttingDown = new AtomicBoolean(false);
    private final AtomicBoolean running = new AtomicBoolean(false);

    public ProxyConnection(Transport localTransport, Transport remoteTransport) {
        this.localTransport = localTransport;
        this.remoteTransport = remoteTransport;
    }

    public void onFailure(IOException e) {
        if (!this.shuttingDown.get()) {
            LOG.debug("Transport error: {}", (Object)e.getMessage(), (Object)e);
            try {
                this.stop();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public void start() throws Exception {
        if (!this.running.compareAndSet(false, true)) {
            return;
        }
        this.localTransport.setTransportListener(new DefaultTransportListener(){

            @Override
            public void onCommand(Object command) {
                boolean shutdown = false;
                if (command.getClass() == ShutdownInfo.class) {
                    ProxyConnection.this.shuttingDown.set(true);
                    shutdown = true;
                }
                if (command.getClass() == WireFormatInfo.class) {
                    return;
                }
                try {
                    ProxyConnection.this.remoteTransport.oneway(command);
                    if (shutdown) {
                        ProxyConnection.this.stop();
                    }
                }
                catch (IOException error) {
                    ProxyConnection.this.onFailure(error);
                }
                catch (Exception error) {
                    ProxyConnection.this.onFailure(IOExceptionSupport.create(error));
                }
            }

            @Override
            public void onException(IOException error) {
                ProxyConnection.this.onFailure(error);
            }
        });
        this.remoteTransport.setTransportListener(new DefaultTransportListener(){

            @Override
            public void onCommand(Object command) {
                try {
                    if (command.getClass() == WireFormatInfo.class) {
                        return;
                    }
                    ProxyConnection.this.localTransport.oneway(command);
                }
                catch (IOException error) {
                    ProxyConnection.this.onFailure(error);
                }
            }

            @Override
            public void onException(IOException error) {
                ProxyConnection.this.onFailure(error);
            }
        });
        this.localTransport.start();
        this.remoteTransport.start();
    }

    @Override
    public void stop() throws Exception {
        if (!this.running.compareAndSet(true, false)) {
            return;
        }
        this.shuttingDown.set(true);
        ServiceStopper ss = new ServiceStopper();
        ss.stop(this.remoteTransport);
        ss.stop(this.localTransport);
        ss.throwFirstException();
    }

    public boolean equals(Object arg) {
        if (arg == null || !(arg instanceof ProxyConnection)) {
            return false;
        }
        ProxyConnection other = (ProxyConnection)arg;
        String otherRemote = "";
        String otherLocal = "";
        String thisRemote = "";
        String thisLocal = "";
        if (other.localTransport != null && other.localTransport.getRemoteAddress() != null) {
            otherLocal = other.localTransport.getRemoteAddress();
        }
        if (other.remoteTransport != null && other.remoteTransport.getRemoteAddress() != null) {
            otherRemote = other.remoteTransport.getRemoteAddress();
        }
        if (this.remoteTransport != null && this.remoteTransport.getRemoteAddress() != null) {
            thisRemote = this.remoteTransport.getRemoteAddress();
        }
        if (this.localTransport != null && this.localTransport.getRemoteAddress() != null) {
            thisLocal = this.localTransport.getRemoteAddress();
        }
        return otherRemote.equals(thisRemote) && otherLocal.equals(thisLocal);
    }

    public int hashCode() {
        int hash = 17;
        if (this.localTransport != null && this.localTransport.getRemoteAddress() != null) {
            hash += 31 * hash + this.localTransport.getRemoteAddress().hashCode();
        }
        if (this.remoteTransport != null && this.remoteTransport.getRemoteAddress() != null) {
            hash = 31 * hash + this.remoteTransport.hashCode();
        }
        return hash;
    }

    public String toString() {
        return "ProxyConnection [localTransport=" + this.localTransport + ", remoteTransport=" + this.remoteTransport + ", shuttingDown=" + this.shuttingDown.get() + ", running=" + this.running.get() + "]";
    }
}

