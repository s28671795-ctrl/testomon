/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.Map;
import java.util.SortedSet;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.ClientCallback;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractFaultChainInitiatorObserver;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseManager;

public class ClientOutFaultObserver
extends AbstractFaultChainInitiatorObserver {
    public ClientOutFaultObserver(Bus bus) {
        super(bus);
    }

    @Override
    protected SortedSet<Phase> getPhases() {
        return this.getBus().getExtension(PhaseManager.class).getOutPhases();
    }

    @Override
    public void onMessage(Message m4) {
        if (m4.get("org.apache.cxf.message.inbound").equals(Boolean.TRUE)) {
            return;
        }
        Exception ex = m4.getContent(Exception.class);
        ClientCallback callback = m4.getExchange().remove(ClientCallback.class);
        if (callback != null) {
            Map<String, Object> resCtx = CastUtils.cast((Map)m4.getExchange().getOutMessage().get("org.apache.cxf.invocation.context"));
            resCtx = CastUtils.cast((Map)resCtx.get("ResponseContext"));
            callback.handleException(resCtx, ex);
        }
    }

    @Override
    protected boolean isOutboundObserver() {
        return true;
    }
}

