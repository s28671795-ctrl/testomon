/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.tokenstore.MemoryTokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreFactory;

public class MemoryTokenStoreFactory
extends TokenStoreFactory {
    @Override
    public TokenStore newTokenStore(String key, Message message) {
        return new MemoryTokenStore();
    }
}

