/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.users;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;
import com.incoresoft.vezha.dto.users.UserDTO;
import com.incoresoft.vezha.models.users.UserImpl;
import java.util.Collection;

public final class UserUpdatedAuditMessage
extends ObjectUpdateAuditMessage<UserDTO> {
    private final int id;

    public UserUpdatedAuditMessage(UserImpl old, UserImpl updated, Collection<? extends Permission> permissions) {
        super(new UserDTO(old, permissions), new UserDTO(updated, permissions));
        this.id = updated.getId();
    }

    public UserUpdatedAuditMessage(UserDTO old, UserDTO updated) {
        super(old, updated);
        this.id = updated.getId();
    }

    @Override
    protected String getTemplateId() {
        return "USER_UPDATED";
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "UserUpdatedAuditMessage(id=" + this.getId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserUpdatedAuditMessage)) {
            return false;
        }
        UserUpdatedAuditMessage other = (UserUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getId() == other.getId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof UserUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        return result;
    }
}

