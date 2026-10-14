/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl;

import javax.xml.namespace.QName;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;

public class SecurityHeaderOrder {
    private QName securityHeaderElementName;
    private XMLSecurityConstants.Action action;
    private boolean encrypted;

    public SecurityHeaderOrder(QName securityHeaderElementName, XMLSecurityConstants.Action action, boolean encrypted) {
        this.securityHeaderElementName = securityHeaderElementName;
        this.action = action;
        this.encrypted = encrypted;
    }

    public QName getSecurityHeaderElementName() {
        return this.securityHeaderElementName;
    }

    public XMLSecurityConstants.Action getAction() {
        return this.action;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }
}

