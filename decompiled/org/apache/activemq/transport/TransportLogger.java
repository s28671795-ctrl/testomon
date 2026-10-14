/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.LogWriter;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.TransportLoggerView;
import org.slf4j.Logger;

public class TransportLogger
extends TransportFilter {
    private final Logger log;
    private boolean logging;
    private final LogWriter logWriter;
    private TransportLoggerView view;

    public TransportLogger(Transport next, Logger log, boolean startLogging, LogWriter logWriter) {
        super(next);
        this.log = log;
        this.logging = startLogging;
        this.logWriter = logWriter;
    }

    public boolean isLogging() {
        return this.logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    @Override
    public Object request(Object command) throws IOException {
        if (this.logging) {
            this.logWriter.logRequest(this.log, command);
        }
        Object rc = super.request(command);
        if (this.logging) {
            this.logWriter.logResponse(this.log, command);
        }
        return rc;
    }

    @Override
    public Object request(Object command, int timeout2) throws IOException {
        if (this.logging) {
            this.logWriter.logRequest(this.log, command);
        }
        Object rc = super.request(command, timeout2);
        if (this.logging) {
            this.logWriter.logResponse(this.log, command);
        }
        return rc;
    }

    @Override
    public FutureResponse asyncRequest(Object command, ResponseCallback responseCallback) throws IOException {
        if (this.logging) {
            this.logWriter.logAsyncRequest(this.log, command);
        }
        FutureResponse rc = this.next.asyncRequest(command, responseCallback);
        return rc;
    }

    @Override
    public void oneway(Object command) throws IOException {
        if (this.logging && this.log.isDebugEnabled()) {
            this.logWriter.logOneWay(this.log, command);
        }
        this.next.oneway(command);
    }

    @Override
    public void onCommand(Object command) {
        if (this.logging && this.log.isDebugEnabled()) {
            this.logWriter.logReceivedCommand(this.log, command);
        }
        this.getTransportListener().onCommand(command);
    }

    @Override
    public void onException(IOException error) {
        if (this.logging && this.log.isDebugEnabled()) {
            this.logWriter.logReceivedException(this.log, error);
        }
        this.getTransportListener().onException(error);
    }

    public TransportLoggerView getView() {
        return this.view;
    }

    public void setView(TransportLoggerView view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return this.next.toString();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (this.view != null) {
            this.view.unregister();
        }
    }
}

