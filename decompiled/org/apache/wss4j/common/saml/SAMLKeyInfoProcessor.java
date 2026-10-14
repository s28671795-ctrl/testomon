/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.w3c.dom.Element;

public interface SAMLKeyInfoProcessor {
    public SAMLKeyInfo processSAMLKeyInfo(Element var1) throws WSSecurityException;
}

