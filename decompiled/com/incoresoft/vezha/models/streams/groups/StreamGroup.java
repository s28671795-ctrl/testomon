/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.groups;

import com.incoresoft.middleware.model.clients.ClientId;

public class StreamGroup
implements ClientId {
    public static final int ID_NOT_DEFINED = -2;
    public static final int PARENT_NULL = -1;
    public static final int PARENT_DEFAULT = 0;
    public static final StreamGroup ROOT = new StreamGroup(0, -1, "root", 0);
    private int id;
    private int parentId;
    private String name;
    private int clientId;

    public int getId() {
        return this.id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamGroup)) {
            return false;
        }
        StreamGroup other = (StreamGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getParentId() != other.getParentId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getParentId();
        result = result * 59 + this.getClientId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "StreamGroup(id=" + this.getId() + ", parentId=" + this.getParentId() + ", name=" + this.getName() + ", clientId=" + this.getClientId() + ")";
    }

    public StreamGroup() {
    }

    public StreamGroup(int id, int parentId, String name, int clientId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.clientId = clientId;
    }
}

