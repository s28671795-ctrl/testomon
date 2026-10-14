/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.websocket.core.AbstractExtension;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.WebSocketComponents;

@ManagedObject(value="Identity Extension")
public class IdentityExtension
extends AbstractExtension {
    private String id;

    public String getParam(String key) {
        return this.getConfig().getParameter(key, "?");
    }

    @Override
    public String getName() {
        return "identity";
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        this.nextIncomingFrame(frame, callback);
    }

    @Override
    public void sendFrame(Frame frame, Callback callback, boolean batch) {
        this.nextOutgoingFrame(frame, callback, batch);
    }

    @Override
    public void init(ExtensionConfig config, WebSocketComponents components) {
        super.init(config, components);
        StringBuilder s2 = new StringBuilder();
        s2.append(config.getName());
        s2.append("@").append(Integer.toHexString(this.hashCode()));
        s2.append("[");
        boolean delim = false;
        for (String param2 : config.getParameterKeys()) {
            if (delim) {
                s2.append(';');
            }
            s2.append(param2).append('=').append(QuotedStringTokenizer.quoteIfNeeded(config.getParameter(param2, ""), ";="));
            delim = true;
        }
        s2.append("]");
        this.id = s2.toString();
    }

    @Override
    public String toString() {
        return this.id;
    }
}

