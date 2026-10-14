/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public class TokenContext {
    private WSSSecurityProperties wssSecurityProperties;
    private WSInboundSecurityContext wsInboundSecurityContext;
    private List<XMLSecEvent> xmlSecEvents;
    private List<QName> elementPath;

    public TokenContext(WSSSecurityProperties wssSecurityProperties, WSInboundSecurityContext wsInboundSecurityContext, List<XMLSecEvent> xmlSecEvents, List<QName> elementPath) {
        this.wssSecurityProperties = wssSecurityProperties;
        this.wsInboundSecurityContext = wsInboundSecurityContext;
        this.xmlSecEvents = xmlSecEvents;
        this.elementPath = elementPath;
    }

    public WSSSecurityProperties getWssSecurityProperties() {
        return this.wssSecurityProperties;
    }

    public WSInboundSecurityContext getWsSecurityContext() {
        return this.wsInboundSecurityContext;
    }

    public List<QName> getElementPath() {
        return this.elementPath;
    }

    public XMLSecEvent getFirstXMLSecEvent() {
        return this.xmlSecEvents.get(0);
    }
}

