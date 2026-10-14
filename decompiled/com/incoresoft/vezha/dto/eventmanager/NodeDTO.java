/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.eventmanager;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.eventmanager.node.Node;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Schema(name="EventSchemeDTO", description="Event scheme class structure")
public final class NodeDTO {
    @JSONField(name="id")
    @Schema(name="id", description="ID", example="1", implementation=Integer.class)
    @NotEmpty
    private final String id;
    @JSONField(name="x")
    @Schema(name="x", description="X", example="1", implementation=Integer.class)
    @Positive
    private final int x;
    @JSONField(name="y")
    @Schema(name="y", description="Y", example="1", implementation=Integer.class)
    @Positive
    private final int y;
    @JSONField(name="type")
    @Schema(name="type", description="Type", example="API", implementation=Integer.class)
    @NotEmpty
    private final String type;
    @JSONField(name="params")
    @Schema(name="params", description="Node params", type="object")
    private final JSONObject params;
    @JSONField(name="outputs")
    @Schema(name="outputs", description="Node outputs", type="object")
    private final Map<Integer, String> outputs;

    public NodeDTO(Node entity) {
        this.id = entity.getId();
        this.x = entity.getX();
        this.y = entity.getY();
        this.type = entity.getType();
        this.params = entity.getParams();
        this.outputs = entity.getOutputs();
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NodeDTO)) {
            return false;
        }
        NodeDTO other = (NodeDTO)o;
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
        return "NodeDTO(id=" + this.getId() + ", x=" + this.getX() + ", y=" + this.getY() + ", type=" + this.getType() + ", params=" + String.valueOf(this.getParams()) + ", outputs=" + String.valueOf(this.getOutputs()) + ")";
    }

    public NodeDTO(String id, int x, int y, String type, JSONObject params, Map<Integer, String> outputs) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.params = params;
        this.outputs = outputs;
    }
}

