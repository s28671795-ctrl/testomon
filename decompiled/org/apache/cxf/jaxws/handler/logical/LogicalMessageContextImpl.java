/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.logical;

import java.util.Map;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.jaxws.handler.logical.LogicalMessageImpl;
import org.apache.cxf.message.Message;

public class LogicalMessageContextImpl
extends WrappedMessageContext
implements LogicalMessageContext {
    public LogicalMessageContextImpl(Message wrapped) {
        super(wrapped, MessageContext.Scope.HANDLER);
    }

    @Override
    public LogicalMessage getMessage() {
        return new LogicalMessageImpl(this);
    }

    @Override
    public Object get(Object key) {
        Object o = super.get(key);
        if (("javax.xml.ws.http.response.headers".equals(key) || "javax.xml.ws.http.request.headers".equals(key)) && o != null) {
            if (((Map)o).isEmpty()) {
                return null;
            }
            if (!this.isResponse() && this.isOutbound() && "javax.xml.ws.http.response.headers".equals(key)) {
                return null;
            }
            if (this.isRequestor() && !this.isOutbound() && "javax.xml.ws.http.request.headers".equals(key)) {
                return null;
            }
        }
        return o;
    }
}

