/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.bsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BSPEnforcer {
    private static final Logger LOG = LoggerFactory.getLogger(BSPEnforcer.class);
    private List<BSPRule> ignoredBSPRules = Collections.emptyList();
    private boolean disableBSPRules;

    public BSPEnforcer() {
    }

    public BSPEnforcer(List<BSPRule> bspRules) {
        this.ignoredBSPRules = new ArrayList<BSPRule>(bspRules);
    }

    public BSPEnforcer(boolean disableBSPRules) {
        this.disableBSPRules = disableBSPRules;
    }

    public void handleBSPRule(BSPRule bspRule) throws WSSecurityException {
        if (this.disableBSPRules) {
            return;
        }
        if (!this.ignoredBSPRules.contains((Object)bspRule)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"BSP:" + bspRule.name() + ": " + bspRule.getMsg()});
        }
        LOG.debug("BSP:{}: {}", (Object)bspRule.name(), (Object)bspRule.getMsg());
    }

    public void setIgnoredBSPRules(List<BSPRule> bspRules) {
        this.ignoredBSPRules = new ArrayList<BSPRule>(bspRules);
    }

    public void setDisableBSPRules(boolean disableBSPRules) {
        this.disableBSPRules = disableBSPRules;
    }
}

