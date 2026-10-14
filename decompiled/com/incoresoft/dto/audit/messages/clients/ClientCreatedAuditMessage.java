/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.clients;

import com.incoresoft.clients.Client;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class ClientCreatedAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    public ClientCreatedAuditMessage(Client client) {
        this.id = client.getId();
        this.name = client.getName();
    }

    @Override
    protected String getTemplateId() {
        return "CLIENT_CREATED";
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "ClientCreatedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ClientCreatedAuditMessage)) {
            return false;
        }
        ClientCreatedAuditMessage other = (ClientCreatedAuditMessage)o;
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
        return other instanceof ClientCreatedAuditMessage;
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

