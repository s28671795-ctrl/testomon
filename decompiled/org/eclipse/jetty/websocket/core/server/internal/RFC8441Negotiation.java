/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;

public class RFC8441Negotiation
extends WebSocketNegotiation {
    public RFC8441Negotiation(Request baseRequest, HttpServletRequest request, HttpServletResponse response, WebSocketComponents components) throws BadMessageException {
        super(baseRequest, request, response, components);
    }

    @Override
    public boolean validateHeaders() {
        MetaData.Request metaData = this.getBaseRequest().getMetaData();
        if (metaData == null) {
            return false;
        }
        return "websocket".equals(metaData.getProtocol());
    }
}

