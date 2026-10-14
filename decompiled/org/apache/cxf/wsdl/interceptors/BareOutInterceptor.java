/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.interceptors;

import java.util.List;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;

public class BareOutInterceptor
extends AbstractOutDatabindingInterceptor {
    public BareOutInterceptor() {
        super("marshal");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void handleMessage(Message message) {
        Exchange exchange = message.getExchange();
        BindingOperationInfo operation = exchange.getBindingOperationInfo();
        if (operation == null) {
            return;
        }
        MessageContentsList objs = MessageContentsList.getContentsList(message);
        if (objs == null || objs.isEmpty()) {
            return;
        }
        List<MessagePartInfo> parts = null;
        BindingMessageInfo bmsg = null;
        boolean client = this.isRequestor(message);
        if (!client) {
            if (operation.getOutput() == null) return;
            bmsg = operation.getOutput();
            parts = bmsg.getMessageParts();
        } else {
            bmsg = operation.getInput();
            parts = bmsg.getMessageParts();
        }
        this.writeParts(message, exchange, operation, objs, parts);
    }
}

