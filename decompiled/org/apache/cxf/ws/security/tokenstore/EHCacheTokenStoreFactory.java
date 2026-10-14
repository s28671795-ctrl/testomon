/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import java.net.URL;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.tokenstore.EHCacheTokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreFactory;
import org.apache.wss4j.common.util.Loader;

public class EHCacheTokenStoreFactory
extends TokenStoreFactory {
    private static final String DEFAULT_CONFIG_FILE = "cxf-ehcache.xml";

    @Override
    public TokenStore newTokenStore(String key, Message message) {
        URL configFileURL = SecurityUtils.getConfigFileURL(message, "ws-security.cache.config.file", DEFAULT_CONFIG_FILE);
        if (configFileURL == null) {
            configFileURL = Loader.getResource(this.getClass().getClassLoader(), DEFAULT_CONFIG_FILE);
        }
        return new EHCacheTokenStore(key, message.getExchange().getBus(), configFileURL);
    }
}

