/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.eventbus.messages.Status;
import com.incoresoft.plugin.commons.serialization.InstantEpochSerializer;
import java.time.Instant;

public class AlprAlert {
    @JSONField(name="id")
    int id;
    @JSONField(name="name")
    String name;
    @JSONField(name="comment")
    String comment;
    @JSONField(name="status")
    Status status;
    @JSONField(name="created_by")
    int createdBy;
    @JSONField(name="created_at", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    Instant createdAt;
    @JSONField(name="closed_at", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    Instant closedAt;
    @JSONField(name="client_id")
    long clientId;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getComment() {
        return this.comment;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getClosedAt() {
        return this.closedAt;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public AlprAlert() {
    }

    public AlprAlert(int id, String name, String comment, Status status, int createdBy, Instant createdAt, Instant closedAt, long clientId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.clientId = clientId;
    }
}

