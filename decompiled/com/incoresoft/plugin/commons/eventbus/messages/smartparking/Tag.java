/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.smartparking;

import com.alibaba.fastjson.annotation.JSONField;

public class Tag {
    @JSONField(name="id")
    private int id;
    @JSONField(name="name")
    private String name;
    @JSONField(name="client_id")
    private long clientId;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getClientId() {
        return this.clientId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Tag() {
    }

    public Tag(int id, String name, long clientId) {
        this.id = id;
        this.name = name;
        this.clientId = clientId;
    }

    public String toString() {
        return "Tag(id=" + this.getId() + ", name=" + this.getName() + ", clientId=" + this.getClientId() + ")";
    }
}

