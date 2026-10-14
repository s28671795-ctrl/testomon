/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.roles;

import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class RoleDeletedAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    public RoleDeletedAuditMessage(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    @Override
    protected String getTemplateId() {
        return "ROLE_DELETED";
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "RoleDeletedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RoleDeletedAuditMessage)) {
            return false;
        }
        RoleDeletedAuditMessage other = (RoleDeletedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RoleDeletedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}

