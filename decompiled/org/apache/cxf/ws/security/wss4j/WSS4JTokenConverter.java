/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Principal;
import org.apache.cxf.common.security.SecurityToken;
import org.apache.cxf.common.security.UsernameToken;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.message.Message;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;

public final class WSS4JTokenConverter {
    private WSS4JTokenConverter() {
    }

    public static void convertToken(Message msg, Principal p) {
        if (p instanceof UsernameTokenPrincipal) {
            UsernameTokenPrincipal utp = (UsernameTokenPrincipal)p;
            String nonce = null;
            if (utp.getNonce() != null) {
                nonce = Base64Utility.encode(utp.getNonce());
            }
            msg.put(SecurityToken.class, new UsernameToken(utp.getName(), utp.getPassword(), utp.getPasswordType(), utp.isPasswordDigest(), nonce, utp.getCreatedTime()));
        }
    }
}

