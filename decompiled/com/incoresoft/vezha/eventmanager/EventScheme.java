/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.vezha.dto.eventmanager.EventSchemeDTO;
import com.incoresoft.vezha.eventmanager.node.Node;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EventScheme
implements ClientId {
    private Integer id;
    private String uuid;
    private String title;
    private String description;
    private List<Node> nodes;
    private Instant createdAt;
    private int clientId;

    public EventScheme(String uuid, String title, String description, List<Node> nodes, int clientId) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.nodes = nodes;
        this.createdAt = Instant.now();
        this.clientId = clientId;
    }

    public EventScheme(EventSchemeDTO dto, int clientId) {
        this.uuid = dto.getId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.nodes = new LinkedList<Node>();
        Node stopNode = new Node(UUID.randomUUID().toString(), 100, 400, "stop", new JSONObject(), Collections.emptyMap());
        this.nodes.add(stopNode);
        this.nodes.add(new Node(UUID.randomUUID().toString(), 100, 100, "start", new JSONObject(), Map.of(0, stopNode.getId())));
        this.createdAt = Instant.now();
        this.clientId = clientId;
    }

    public EventScheme(EventSchemeDTO dto, EventScheme eventToUpdate) {
        this.uuid = eventToUpdate.getUuid();
        this.id = eventToUpdate.getId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.nodes = Optional.ofNullable(dto.getNodes()).map(n -> n.stream().map(Node::new).toList()).orElse(eventToUpdate.getNodes());
        this.createdAt = eventToUpdate.getCreatedAt();
        this.clientId = eventToUpdate.getClientId();
    }

    public Integer getId() {
        return this.id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventScheme)) {
            return false;
        }
        EventScheme other = (EventScheme)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        List<Node> this$nodes = this.getNodes();
        List<Node> other$nodes = other.getNodes();
        if (this$nodes == null ? other$nodes != null : !((Object)this$nodes).equals(other$nodes)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        return !(this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventScheme;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getClientId();
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        List<Node> $nodes = this.getNodes();
        result = result * 59 + ($nodes == null ? 43 : ((Object)$nodes).hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        return result;
    }

    public String toString() {
        return "EventScheme(id=" + this.getId() + ", uuid=" + this.getUuid() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", nodes=" + String.valueOf(this.getNodes()) + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", clientId=" + this.getClientId() + ")";
    }

    public EventScheme() {
    }

    public EventScheme(Integer id, String uuid, String title, String description, List<Node> nodes, Instant createdAt, int clientId) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.nodes = nodes;
        this.createdAt = createdAt;
        this.clientId = clientId;
    }
}

