/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager.node;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.vezha.dto.eventmanager.NodeDTO;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Node {
    private String id;
    private int x;
    private int y;
    private String type;
    private JSONObject params;
    private Map<Integer, String> outputs;

    public Node(NodeDTO dto) {
        this.id = Optional.ofNullable(dto.getId()).orElse(UUID.randomUUID().toString());
        this.x = dto.getX();
        this.y = dto.getY();
        this.type = dto.getType();
        this.params = dto.getParams();
        this.outputs = dto.getOutputs();
    }

    public String getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getType() {
        return this.type;
    }

    public JSONObject getParams() {
        return this.params;
    }

    public Map<Integer, String> getOutputs() {
        return this.outputs;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public void setOutputs(Map<Integer, String> outputs) {
        this.outputs = outputs;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        Node other = (Node)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getX() != other.getX()) {
            return false;
        }
        if (this.getY() != other.getY()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        JSONObject this$params = this.getParams();
        JSONObject other$params = other.getParams();
        if (this$params == null ? other$params != null : !((Object)this$params).equals(other$params)) {
            return false;
        }
        Map<Integer, String> this$outputs = this.getOutputs();
        Map<Integer, String> other$outputs = other.getOutputs();
        return !(this$outputs == null ? other$outputs != null : !((Object)this$outputs).equals(other$outputs));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Node;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getX();
        result = result * 59 + this.getY();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        JSONObject $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : ((Object)$params).hashCode());
        Map<Integer, String> $outputs = this.getOutputs();
        result = result * 59 + ($outputs == null ? 43 : ((Object)$outputs).hashCode());
        return result;
    }

    public String toString() {
        return "Node(id=" + this.getId() + ", x=" + this.getX() + ", y=" + this.getY() + ", type=" + this.getType() + ", params=" + String.valueOf(this.getParams()) + ", outputs=" + String.valueOf(this.getOutputs()) + ")";
    }

    public Node(String id, int x, int y, String type, JSONObject params, Map<Integer, String> outputs) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.params = params;
        this.outputs = outputs;
    }
}

