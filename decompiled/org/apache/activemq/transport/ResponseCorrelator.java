/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Response;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportDisposedIOException;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.util.IntSequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseCorrelator
extends TransportFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseCorrelator.class);
    private final Map<Integer, FutureResponse> requestMap = new HashMap<Integer, FutureResponse>();
    private IntSequenceGenerator sequenceGenerator;
    private final boolean debug = LOG.isDebugEnabled();
    private IOException error;

    public ResponseCorrelator(Transport next) {
        this(next, new IntSequenceGenerator());
    }

    public ResponseCorrelator(Transport next, IntSequenceGenerator sequenceGenerator) {
        super(next);
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void oneway(Object o) throws IOException {
        Command command = (Command)o;
        command.setCommandId(this.sequenceGenerator.getNextSequenceId());
        command.setResponseRequired(false);
        this.next.oneway(command);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FutureResponse asyncRequest(Object o, ResponseCallback responseCallback) throws IOException {
        Command command = (Command)o;
        command.setCommandId(this.sequenceGenerator.getNextSequenceId());
        command.setResponseRequired(true);
        FutureResponse future = new FutureResponse(responseCallback, this);
        IOException priorError = null;
        Map<Integer, FutureResponse> map = this.requestMap;
        synchronized (map) {
            priorError = this.error;
            if (priorError == null) {
                this.requestMap.put(new Integer(command.getCommandId()), future);
            }
        }
        if (priorError != null) {
            future.set(new ExceptionResponse(priorError));
            throw priorError;
        }
        this.next.oneway(command);
        return future;
    }

    @Override
    public Object request(Object command) throws IOException {
        FutureResponse response = this.asyncRequest(command, null);
        return response.getResult();
    }

    @Override
    public Object request(Object command, int timeout2) throws IOException {
        FutureResponse response = this.asyncRequest(command, null);
        return response.getResult(timeout2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onCommand(Object o) {
        Command command = null;
        if (!(o instanceof Command)) {
            throw new ClassCastException("Object cannot be converted to a Command,  Object: " + o);
        }
        command = (Command)o;
        if (command.isResponse()) {
            Response response = (Response)command;
            FutureResponse future = null;
            Map<Integer, FutureResponse> map = this.requestMap;
            synchronized (map) {
                future = this.requestMap.remove(response.getCorrelationId());
            }
            if (future != null) {
                future.set(response);
            } else if (this.debug) {
                LOG.debug("Received unexpected response: {" + command + "}for command id: " + response.getCorrelationId());
            }
        } else {
            this.getTransportListener().onCommand(command);
        }
    }

    @Override
    public void onException(IOException error) {
        this.dispose(new TransportDisposedIOException("Disposed due to prior exception", error));
        super.onException(error);
    }

    @Override
    public void stop() throws Exception {
        this.dispose(new IOException("Stopped."));
        super.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void dispose(IOException error) {
        ArrayList<FutureResponse> requests = null;
        Map<Integer, FutureResponse> map = this.requestMap;
        synchronized (map) {
            if (this.error == null) {
                this.error = error;
                requests = new ArrayList<FutureResponse>(this.requestMap.values());
                this.requestMap.clear();
            }
        }
        if (requests != null) {
            for (FutureResponse fr : requests) {
                fr.set(new ExceptionResponse(error));
            }
        }
    }

    public IntSequenceGenerator getSequenceGenerator() {
        return this.sequenceGenerator;
    }

    @Override
    public String toString() {
        return this.next.toString();
    }
}

