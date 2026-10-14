/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;

public class WSSecurityTokenHolder
extends WSSecBase {
    SecurityToken token;

    public WSSecurityTokenHolder(SecurityToken t2, WSSecHeader securityHeader) {
        super(securityHeader);
        this.token = t2;
    }

    public SecurityToken getToken() {
        return this.token;
    }
}

