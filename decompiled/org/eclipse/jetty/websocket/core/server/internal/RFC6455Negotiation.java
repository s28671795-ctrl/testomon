/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.QuotedCSV;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;

public class RFC6455Negotiation
extends WebSocketNegotiation {
    private boolean successful;
    private String key;

    public RFC6455Negotiation(Request baseRequest, HttpServletRequest request, HttpServletResponse response, WebSocketComponents components) throws BadMessageException {
        super(baseRequest, request, response, components);
    }

    @Override
    protected void negotiateHeaders(Request baseRequest) {
        super.negotiateHeaders(baseRequest);
        boolean upgrade = false;
        QuotedCSV connectionCSVs = null;
        for (HttpField field2 : baseRequest.getHttpFields()) {
            HttpHeader header = field2.getHeader();
            if (header == null) continue;
            switch (header) {
                case UPGRADE: {
                    upgrade = "websocket".equalsIgnoreCase(field2.getValue());
                    break;
                }
                case CONNECTION: {
                    if (connectionCSVs == null) {
                        connectionCSVs = new QuotedCSV(new String[0]);
                    }
                    connectionCSVs.addValue(field2.getValue());
                    break;
                }
                case SEC_WEBSOCKET_KEY: {
                    this.key = field2.getValue();
                    break;
                }
            }
        }
        this.successful = upgrade && connectionCSVs != null && connectionCSVs.getValues().stream().anyMatch(s2 -> s2.equalsIgnoreCase("upgrade"));
    }

    @Override
    public boolean validateHeaders() {
        return this.successful;
    }

    public String getKey() {
        return this.key;
    }
}

