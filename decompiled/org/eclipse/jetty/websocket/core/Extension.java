/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import java.io.Closeable;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.IncomingFrames;
import org.eclipse.jetty.websocket.core.OutgoingFrames;
import org.eclipse.jetty.websocket.core.WebSocketComponents;

public interface Extension
extends IncomingFrames,
OutgoingFrames,
Closeable {
    public void init(ExtensionConfig var1, WebSocketComponents var2);

    @Override
    default public void close() {
    }

    public ExtensionConfig getConfig();

    public String getName();

    public boolean isRsv1User();

    public boolean isRsv2User();

    public boolean isRsv3User();

    public void setNextIncomingFrames(IncomingFrames var1);

    public void setNextOutgoingFrames(OutgoingFrames var1);

    public void setCoreSession(CoreSession var1);
}

