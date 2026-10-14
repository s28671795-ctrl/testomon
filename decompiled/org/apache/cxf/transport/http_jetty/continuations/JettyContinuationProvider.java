/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jetty.continuations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http_jetty.continuations.JettyContinuationWrapper;

public class JettyContinuationProvider
implements ContinuationProvider {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Message inMessage;
    private JettyContinuationWrapper wrapper;

    public JettyContinuationProvider(HttpServletRequest req, HttpServletResponse resp, Message m4) {
        this.request = req;
        this.response = resp;
        this.inMessage = m4;
    }

    @Override
    public void complete() {
        JettyContinuationWrapper r = this.getContinuation(false);
        if (r != null) {
            r.reset();
        }
        this.wrapper = null;
    }

    @Override
    public Continuation getContinuation() {
        return this.getContinuation(true);
    }

    public JettyContinuationWrapper getContinuation(boolean create) {
        Message m4 = this.inMessage;
        if (m4 != null && m4.getExchange() != null && m4.getExchange().getInMessage() != null) {
            m4 = m4.getExchange().getInMessage();
        }
        if (m4 == null || m4.getExchange() == null || m4.getExchange().isOneWay()) {
            return null;
        }
        if (this.wrapper == null && create) {
            this.wrapper = new JettyContinuationWrapper(this.request, this.response, m4);
        }
        return this.wrapper;
    }
}

