/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.wss4j.dom.handler.WSHandlerResult;

public interface WSS4JSecurityContextCreator {
    public void createSecurityContext(SoapMessage var1, WSHandlerResult var2);
}

