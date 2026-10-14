/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.util.List;
import javax.xml.ws.Holder;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;

public class HolderInInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String CLIENT_HOLDERS = "client.holders";

    public HolderInInterceptor() {
        super("pre-invoke");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        MessageContentsList inObjects = MessageContentsList.getContentsList(message);
        Exchange exchange = message.getExchange();
        BindingOperationInfo bop = exchange.getBindingOperationInfo();
        if (bop == null) {
            return;
        }
        OperationInfo op = bop.getOperationInfo();
        if (op == null || !op.hasOutput() || op.getOutput().size() == 0) {
            return;
        }
        List<MessagePartInfo> parts = op.getOutput().getMessageParts();
        boolean client = Boolean.TRUE.equals(message.get("org.apache.cxf.client"));
        if (client) {
            List outHolders = CastUtils.cast((List)message.getExchange().getOutMessage().get(CLIENT_HOLDERS));
            for (MessagePartInfo part : parts) {
                Holder holder;
                if (part.getIndex() == 0 || part.getTypeClass() == null || (holder = (Holder)outHolders.get(part.getIndex() - 1)) == null) continue;
                holder.value = inObjects.get(part);
                inObjects.put(part, holder);
            }
        } else {
            for (MessagePartInfo part : parts) {
                int idx = part.getIndex() - 1;
                if (idx < 0 || part.getTypeClass() == null) continue;
                if (inObjects == null) {
                    inObjects = new MessageContentsList();
                    message.setContent(List.class, inObjects);
                }
                if (idx >= inObjects.size()) {
                    inObjects.set(idx, new Holder());
                    continue;
                }
                Object o = inObjects.get(idx);
                inObjects.set(idx, new Holder(o));
            }
        }
    }
}

