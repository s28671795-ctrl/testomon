/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.users;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class LDAPUsersFetchedAuditMessage
extends AuditMessage {
    private final int id;
    private final String login;

    public LDAPUsersFetchedAuditMessage(User user) {
        this.id = user.getId();
        this.login = user.getEmail();
    }

    @Override
    protected String getTemplateId() {
        return "LDAP_USERS_FETCHED";
    }

    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String toString() {
        return "LDAPUsersFetchedAuditMessage(id=" + this.getId() + ", login=" + this.getLogin() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LDAPUsersFetchedAuditMessage)) {
            return false;
        }
        LDAPUsersFetchedAuditMessage other = (LDAPUsersFetchedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$login = this.getLogin();
        String other$login = other.getLogin();
        return !(this$login == null ? other$login != null : !this$login.equals(other$login));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LDAPUsersFetchedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        String $login = this.getLogin();
        result = result * 59 + ($login == null ? 43 : $login.hashCode());
        return result;
    }
}

