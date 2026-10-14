/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.xml.security.utils.XMLUtils;

public class TokenStoreCallbackHandler
implements CallbackHandler {
    private CallbackHandler internal;
    private TokenStore store;

    public TokenStoreCallbackHandler(CallbackHandler in, TokenStore st) {
        this.internal = in;
        this.store = st;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            WSPasswordCallback pc;
            String id;
            SecurityToken tok;
            if (!(callback instanceof WSPasswordCallback) || (tok = this.store.getToken(id = (pc = (WSPasswordCallback)callback).getIdentifier())) == null || tok.isExpired()) continue;
            if (tok.getSHA1() == null && pc.getKey() != null) {
                tok.setSHA1(TokenStoreCallbackHandler.getSHA1(pc.getKey()));
                this.store.add(tok.getSHA1(), tok);
            }
            pc.setKey(tok.getSecret());
            pc.setKey(tok.getKey());
            pc.setCustomToken(tok.getToken());
            return;
        }
        if (this.internal != null) {
            this.internal.handle(callbacks);
        }
    }

    private static String getSHA1(byte[] input) {
        try {
            byte[] digestBytes = KeyUtils.generateDigest(input);
            return XMLUtils.encodeToString(digestBytes);
        }
        catch (WSSecurityException wSSecurityException) {
            return null;
        }
    }
}

