/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.facerecognition;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.eventbus.messages.Status;
import com.incoresoft.plugin.commons.serialization.InstantEpochSerializer;
import java.time.Instant;

public class FaceRecognitionPerson {
    @JSONField(name="id")
    private int id;
    @JSONField(name="name")
    private String name;
    @JSONField(name="comment")
    private String comment;
    @JSONField(name="status")
    private Status status;
    @JSONField(name="created_by")
    private int createdBy;
    @JSONField(name="created_at", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    private Instant createdAt;
    @JSONField(name="closed_at", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    private Instant closedAt;
    @JSONField(name="client_id")
    private long clientId;
    @JSONField(name="confidence")
    private double confidence;

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

    public double getConfidence() {
        return this.confidence;
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

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public FaceRecognitionPerson() {
    }

    public FaceRecognitionPerson(int id, String name, String comment, Status status, int createdBy, Instant createdAt, Instant closedAt, long clientId, double confidence) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.clientId = clientId;
        this.confidence = confidence;
    }
}

