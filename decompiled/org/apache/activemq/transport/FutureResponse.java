/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.activemq.command.Response;
import org.apache.activemq.transport.RequestTimedOutIOException;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.TransportFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureResponse {
    private static final Logger LOG = LoggerFactory.getLogger(FutureResponse.class);
    private final ResponseCallback responseCallback;
    private final TransportFilter transportFilter;
    private final ArrayBlockingQueue<Response> responseSlot = new ArrayBlockingQueue(1);

    public FutureResponse(ResponseCallback responseCallback) {
        this(responseCallback, null);
    }

    public FutureResponse(ResponseCallback responseCallback, TransportFilter transportFilter) {
        this.responseCallback = responseCallback;
        this.transportFilter = transportFilter;
    }

    public Response getResult() throws IOException {
        boolean hasInterruptPending = Thread.interrupted();
        try {
            Response response = this.responseSlot.take();
            return response;
        }
        catch (InterruptedException e) {
            hasInterruptPending = false;
            throw this.dealWithInterrupt(e);
        }
        finally {
            if (hasInterruptPending) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private InterruptedIOException dealWithInterrupt(InterruptedException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Operation interrupted: " + e, e);
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException(e.getMessage());
        interruptedIOException.initCause(e);
        try {
            if (this.transportFilter != null) {
                this.transportFilter.onException(interruptedIOException);
            }
        }
        finally {
            Thread.currentThread().interrupt();
        }
        return interruptedIOException;
    }

    public Response getResult(int timeout2) throws IOException {
        boolean wasInterrupted = Thread.interrupted();
        try {
            Response result = this.responseSlot.poll(timeout2, TimeUnit.MILLISECONDS);
            if (result == null && timeout2 > 0) {
                throw new RequestTimedOutIOException();
            }
            Response response = result;
            return response;
        }
        catch (InterruptedException e) {
            throw this.dealWithInterrupt(e);
        }
        finally {
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void set(Response result) {
        if (this.responseSlot.offer(result) && this.responseCallback != null) {
            this.responseCallback.onCompletion(this);
        }
    }
}

