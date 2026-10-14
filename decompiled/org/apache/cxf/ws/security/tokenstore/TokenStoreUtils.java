/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreFactory;

public final class TokenStoreUtils {
    private TokenStoreUtils() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TokenStore getTokenStore(Message message) {
        EndpointInfo info;
        EndpointInfo endpointInfo = info = message.getExchange().getEndpoint().getEndpointInfo();
        synchronized (endpointInfo) {
            TokenStore tokenStore = (TokenStore)message.getContextualProperty("org.apache.cxf.ws.security.tokenstore.TokenStore");
            if (tokenStore == null) {
                tokenStore = (TokenStore)info.getProperty("org.apache.cxf.ws.security.tokenstore.TokenStore");
            }
            if (tokenStore == null) {
                TokenStoreFactory tokenStoreFactory = TokenStoreFactory.newInstance();
                String cacheKey = "org.apache.cxf.ws.security.tokenstore.TokenStore";
                String cacheIdentifier = (String)message.getContextualProperty("ws-security.cache.identifier");
                if (cacheIdentifier != null) {
                    cacheKey = cacheKey + "-" + cacheIdentifier;
                } else if (info.getName() != null) {
                    int hashcode = info.getName().toString().hashCode();
                    cacheKey = hashcode < 0 ? cacheKey + hashcode : cacheKey + "-" + hashcode;
                }
                tokenStore = tokenStoreFactory.newTokenStore(cacheKey, message);
                info.setProperty("org.apache.cxf.ws.security.tokenstore.TokenStore", tokenStore);
            }
            return tokenStore;
        }
    }
}

