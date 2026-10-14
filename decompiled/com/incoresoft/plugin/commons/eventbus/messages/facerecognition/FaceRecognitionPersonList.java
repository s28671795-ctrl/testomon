/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.facerecognition;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.plugin.commons.eventbus.messages.Status;
import com.incoresoft.plugin.commons.eventbus.messages.facerecognition.FaceRecognitionPerson;
import com.incoresoft.plugin.commons.serialization.InstantEpochSerializer;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FaceRecognitionPersonList {
    @JSONField(name="id")
    int id;
    @JSONField(name="name")
    String name;
    @JSONField(name="comment")
    String comment;
    @JSONField(name="status")
    Status status;
    @JSONField(name="created_at", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    Instant createdAt;
    @JSONField(name="color")
    String color;
    @JSONField(name="streams")
    Set<Integer> streams;
    @JSONField(name="events_enabled")
    boolean eventsEnabled;
    @JSONField(name="events")
    List<Event> events;
    @JSONField(name="alerts")
    List<FaceRecognitionPerson> alerts;
    @JSONField(name="client_id")
    long clientId;
    @JSONField(name="additional_params")
    Map<String, String> additionalParams;
    @JSONField(name="send_internal_notifications")
    Boolean sendInternalNotifications;

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

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public String getColor() {
        return this.color;
    }

    public Set<Integer> getStreams() {
        return this.streams;
    }

    public boolean isEventsEnabled() {
        return this.eventsEnabled;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public List<FaceRecognitionPerson> getAlerts() {
        return this.alerts;
    }

    public long getClientId() {
        return this.clientId;
    }

    public Map<String, String> getAdditionalParams() {
        return this.additionalParams;
    }

    public Boolean getSendInternalNotifications() {
        return this.sendInternalNotifications;
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStreams(Set<Integer> streams) {
        this.streams = streams;
    }

    public void setEventsEnabled(boolean eventsEnabled) {
        this.eventsEnabled = eventsEnabled;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setAlerts(List<FaceRecognitionPerson> alerts) {
        this.alerts = alerts;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public void setSendInternalNotifications(Boolean sendInternalNotifications) {
        this.sendInternalNotifications = sendInternalNotifications;
    }

    public FaceRecognitionPersonList() {
    }

    public FaceRecognitionPersonList(int id, String name, String comment, Status status, Instant createdAt, String color, Set<Integer> streams, boolean eventsEnabled, List<Event> events, List<FaceRecognitionPerson> alerts, long clientId, Map<String, String> additionalParams, Boolean sendInternalNotifications) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.status = status;
        this.createdAt = createdAt;
        this.color = color;
        this.streams = streams;
        this.eventsEnabled = eventsEnabled;
        this.events = events;
        this.alerts = alerts;
        this.clientId = clientId;
        this.additionalParams = additionalParams;
        this.sendInternalNotifications = sendInternalNotifications;
    }
}

