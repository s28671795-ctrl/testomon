/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ResourceBundle;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.OutgoingChainInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.transport.Conduit;

public class MessageSenderInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(MessageSenderInterceptor.class);
    private MessageSenderEndingInterceptor ending = new MessageSenderEndingInterceptor();

    public MessageSenderInterceptor() {
        super("prepare-send");
    }

    @Override
    public void handleMessage(Message message) {
        try {
            MessageSenderInterceptor.getConduit(message).prepare(message);
        }
        catch (IOException ex) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_SEND", BUNDLE, new Object[0]), (Throwable)ex);
        }
        message.getInterceptorChain().add(this.ending);
    }

    public static Conduit getConduit(Message message) throws IOException {
        Exchange exchange = message.getExchange();
        Conduit conduit = exchange.getConduit(message);
        if (conduit == null && (exchange.getOutMessage() != null || exchange.getOutFaultMessage() != null)) {
            conduit = OutgoingChainInterceptor.getBackChannelConduit(message);
        }
        return conduit;
    }

    public static class MessageSenderEndingInterceptor
    extends AbstractPhaseInterceptor<Message> {
        public MessageSenderEndingInterceptor() {
            super("prepare-send-ending");
        }

        @Override
        public void handleMessage(Message message) {
            try {
                MessageSenderInterceptor.getConduit(message).close(message);
            }
            catch (SocketTimeoutException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_RECEIVE", BUNDLE, new Object[0]), (Throwable)e);
            }
            catch (IOException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_SEND", BUNDLE, new Object[0]), (Throwable)e);
            }
        }
    }
}

