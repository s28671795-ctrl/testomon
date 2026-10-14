/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.roles;

import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;
import com.incoresoft.vezha.dto.roles.RoleDTO;

public final class RoleUpdatedAuditMessage
extends ObjectUpdateAuditMessage<RoleDTO> {
    private final int id;

    @Override
    protected String getTemplateId() {
        return "ROLE_UPDATED";
    }

    public RoleUpdatedAuditMessage(Role old, Role updated) {
        super(new RoleDTO(old), new RoleDTO(updated));
        this.id = updated.getId();
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "RoleUpdatedAuditMessage(id=" + this.getId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RoleUpdatedAuditMessage)) {
            return false;
        }
        RoleUpdatedAuditMessage other = (RoleUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getId() == other.getId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof RoleUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        return result;
    }
}

