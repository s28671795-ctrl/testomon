/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.ext;

import java.util.List;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;

public interface WSInboundSecurityContext
extends InboundSecurityContext {
    public void handleBSPRule(BSPRule var1) throws WSSecurityException;

    public void ignoredBSPRules(List<BSPRule> var1);
}

