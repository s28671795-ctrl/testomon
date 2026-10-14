/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.ws.addressing.MessageIdCache;

public class DefaultMessageIdCache
implements MessageIdCache {
    private final Map<String, Boolean> messageIdSet = new ConcurrentHashMap<String, Boolean>();

    @Override
    public boolean checkUniquenessAndCacheId(String messageId) {
        return this.messageIdSet.put(messageId, Boolean.TRUE) == null;
    }

    protected Set<String> getMessageIdSet() {
        return this.messageIdSet.keySet();
    }
}

