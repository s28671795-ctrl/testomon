/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.handler;

import java.util.List;
import java.util.Map;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;

public class WSHandlerResult {
    private final String actor;
    private final List<WSSecurityEngineResult> wsSecurityResults;
    private final Map<Integer, List<WSSecurityEngineResult>> actionResults;

    public WSHandlerResult(String actor, List<WSSecurityEngineResult> results, Map<Integer, List<WSSecurityEngineResult>> actionResults) {
        this.actor = actor;
        this.wsSecurityResults = results;
        this.actionResults = actionResults;
    }

    public String getActor() {
        return this.actor;
    }

    public List<WSSecurityEngineResult> getResults() {
        return this.wsSecurityResults;
    }

    public Map<Integer, List<WSSecurityEngineResult>> getActionResults() {
        return this.actionResults;
    }
}

