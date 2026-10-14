/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.workqueue.WorkQueueManager;

public class OneWayProcessorInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String USE_ORIGINAL_THREAD = OneWayProcessorInterceptor.class.getName() + ".USE_ORIGINAL_THREAD";
    private static final Logger LOG = LogUtils.getL7dLogger(OneWayProcessorInterceptor.class);

    public OneWayProcessorInterceptor() {
        super("pre-logical");
    }

    public OneWayProcessorInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleFault(Message message) {
        InputStream in;
        if (message.getExchange().isOneWay() && !this.isRequestor(message) && (in = message.getContent(InputStream.class)) != null) {
            try {
                in.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleMessage(Message message) {
        if (message.getExchange().isOneWay() && !this.isRequestor(message) && message.get(OneWayProcessorInterceptor.class) == null && message.getExchange().get(Executor.class) == null) {
            DelegatingInputStream in;
            message.put(OneWayProcessorInterceptor.class, this);
            final InterceptorChain chain = message.getInterceptorChain();
            boolean robust = MessageUtils.getContextualBoolean(message, "org.apache.cxf.oneway.robust", false);
            boolean useOriginalThread = MessageUtils.getContextualBoolean(message, USE_ORIGINAL_THREAD, false);
            if (!useOriginalThread && !robust && (in = message.getContent(DelegatingInputStream.class)) != null) {
                in.cacheInput();
            }
            if (robust) {
                chain.pause();
                chain.resume();
                if (message.getContent(Exception.class) != null) {
                    return;
                }
            }
            try {
                Message partial = OneWayProcessorInterceptor.createMessage(message.getExchange());
                partial.remove("Content-Type");
                partial.setExchange(message.getExchange());
                Conduit conduit = message.getExchange().getDestination().getBackChannel(message);
                if (conduit != null) {
                    message.getExchange().setInMessage(null);
                    conduit.prepare(partial);
                    conduit.close(partial);
                    message.getExchange().setInMessage(message);
                }
            }
            catch (IOException partial) {
                // empty catch block
            }
            if (!useOriginalThread && !robust) {
                chain.pause();
                try {
                    Object lock;
                    Object object = lock = new Object();
                    synchronized (object) {
                        message.getExchange().getBus().getExtension(WorkQueueManager.class).getAutomaticWorkQueue().execute(new Runnable(){

                            /*
                             * WARNING - Removed try catching itself - possible behaviour change.
                             */
                            @Override
                            public void run() {
                                Object object = lock;
                                synchronized (object) {
                                    lock.notifyAll();
                                }
                                chain.resume();
                            }
                        });
                        lock.wait(20L);
                    }
                }
                catch (RejectedExecutionException e) {
                    LOG.warning("Executor queue is full, run the oneway invocation task in caller thread.  Users can specify a larger executor queue to avoid this.");
                    if (!MessageUtils.getContextualBoolean(message, "org.apache.cxf.oneway.rejected_execution_exception", false)) {
                        chain.unpause();
                    }
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        }
    }

    private static Message createMessage(Exchange exchange) {
        Endpoint ep = exchange.getEndpoint();
        Message msg = null;
        if (ep != null) {
            msg = new MessageImpl();
            msg.setExchange(exchange);
            msg = ep.getBinding().createMessage(msg);
        }
        return msg;
    }
}

