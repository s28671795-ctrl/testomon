/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.callback;

import java.util.List;
import javax.xml.crypto.dom.DOMCryptoContext;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.w3c.dom.Element;

public interface CallbackLookup {
    public Element getElement(String var1, String var2, boolean var3) throws WSSecurityException;

    public Element getAndRegisterElement(String var1, String var2, boolean var3, DOMCryptoContext var4) throws WSSecurityException;

    public List<Element> getElements(String var1, String var2) throws WSSecurityException;

    public Element getSOAPBody();
}

