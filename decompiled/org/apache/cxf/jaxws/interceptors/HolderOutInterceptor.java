/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.Holder;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.interceptors.WrapperClassOutInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;

public class HolderOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(HolderOutInterceptor.class);

    public HolderOutInterceptor() {
        super("pre-logical");
        this.addBefore(WrapperClassOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        OperationInfo op;
        MessageContentsList outObjects = MessageContentsList.getContentsList(message);
        Exchange exchange = message.getExchange();
        OperationInfo operationInfo = op = exchange.getBindingOperationInfo() == null ? null : exchange.getBindingOperationInfo().getOperationInfo();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("op: " + op);
            if (null != op) {
                LOG.fine("op.hasOutput(): " + op.hasOutput());
                if (op.hasOutput()) {
                    LOG.fine("op.getOutput().size(): " + op.getOutput().size());
                }
            }
        }
        if (op == null || !op.hasOutput() || op.getOutput().size() == 0) {
            LOG.fine("Returning.");
            return;
        }
        if (!Boolean.TRUE.equals(message.get("org.apache.cxf.client"))) {
            List<MessagePartInfo> parts = op.getOutput().getMessageParts();
            MessageContentsList inObjects = MessageContentsList.getContentsList(exchange.getInMessage());
            if (inObjects != null) {
                if (inObjects != outObjects) {
                    for (int x = 0; x < inObjects.size(); ++x) {
                        Object o = inObjects.get(x);
                        if (!(o instanceof Holder)) continue;
                        outObjects.set(x + 1, o);
                    }
                } else {
                    LOG.severe("CANNOT_SET_HOLDER_OBJECTS");
                    throw new Fault(new org.apache.cxf.common.i18n.Message("CANNOT_SET_HOLDER_OBJECTS", LOG, new Object[0]));
                }
            }
            for (MessagePartInfo part : parts) {
                if (part.getIndex() <= 0 || part.getTypeClass() == null) continue;
                Holder holder = (Holder)outObjects.get(part);
                outObjects.put(part, holder.value);
            }
        } else {
            ArrayList<Object> holders = new ArrayList<Object>(outObjects);
            for (int x = 0; x < outObjects.size(); ++x) {
                Object o = outObjects.get(x);
                if (o instanceof Holder) {
                    outObjects.set(x, ((Holder)o).value);
                    continue;
                }
                holders.set(x, null);
            }
            message.put("client.holders", holders);
        }
    }
}

