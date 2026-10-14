/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.clients;

import com.incoresoft.dto.clients.ClientDTO;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;

public final class ClientUpdatedAuditMessage
extends ObjectUpdateAuditMessage<ClientDTO> {
    private final int id;
    private final String name;

    @Override
    protected String getTemplateId() {
        return "CLIENT_UPDATED";
    }

    public ClientUpdatedAuditMessage(ClientDTO old, ClientDTO updated) {
        super(old, updated);
        this.id = updated.getId();
        this.name = updated.getName();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "ClientUpdatedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ClientUpdatedAuditMessage)) {
            return false;
        }
        ClientUpdatedAuditMessage other = (ClientUpdatedAuditMessage)o;
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
        return other instanceof ClientUpdatedAuditMessage;
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

