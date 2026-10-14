/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.handler;

import org.apache.wss4j.common.SecurityActionToken;

public class HandlerAction {
    private Integer action;
    private SecurityActionToken actionToken;

    public HandlerAction() {
    }

    public HandlerAction(Integer action) {
        this(action, null);
    }

    public HandlerAction(Integer action, SecurityActionToken actionToken) {
        this.action = action;
        this.actionToken = actionToken;
    }

    public Integer getAction() {
        return this.action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public SecurityActionToken getActionToken() {
        return this.actionToken;
    }

    public void setActionToken(SecurityActionToken actionToken) {
        this.actionToken = actionToken;
    }
}

