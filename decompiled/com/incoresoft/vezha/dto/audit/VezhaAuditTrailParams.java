/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.audit;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.http.params.AuditTrailParams;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.Set;

public class VezhaAuditTrailParams
implements AuditTrailParams {
    @JSONField(name="actions")
    private Set<AuditEventAction> actions;
    @JSONField(name="categories")
    private Set<AuditEventCategory> categories;
    @JSONField(name="user_email")
    private String login;

    public Set<AuditEventAction> getActions() {
        return this.actions;
    }

    public Set<AuditEventCategory> getCategories() {
        return this.categories;
    }

    @Override
    public String getLogin() {
        return this.login;
    }

    public void setActions(Set<AuditEventAction> actions) {
        this.actions = actions;
    }

    public void setCategories(Set<AuditEventCategory> categories) {
        this.categories = categories;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

