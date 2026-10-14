/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityToken;

import java.util.Deque;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public interface SecurityTokenReference
extends SecurityToken {
    public Deque<XMLSecEvent> getXmlSecEvents();
}

