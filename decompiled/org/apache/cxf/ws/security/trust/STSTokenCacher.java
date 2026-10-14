/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.w3c.dom.Element;

public interface STSTokenCacher {
    public SecurityToken retrieveToken(Message var1, boolean var2);

    public SecurityToken retrieveToken(Message var1, Element var2, String var3);

    public void storeToken(Message var1, SecurityToken var2, boolean var3);

    public void storeToken(Message var1, Element var2, String var3, String var4);

    public void removeToken(Message var1, SecurityToken var2);
}

