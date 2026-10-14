/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.time.Instant;

@Schema(name="ListItemDTO", description="List item class structure")
@JsonDeserialize(builder=IntegrationListItemDTOBuilder.class)
public final class IntegrationListItemDTO {
    @JSONField(name="id")
    @JsonProperty(value="id")
    @Schema(name="id", description="List item ID", implementation=Integer.class, example="1")
    private final Integer id;
    @JSONField(name="number", deserializeUsing=LicensePlateNumberDeserializer.class)
    @JsonProperty(value="number")
    @Schema(name="number", description="license plate number", implementation=String.class, example="AA1234AA")
    private final String number;
    @JSONField(name="created_by")
    @JsonProperty(value="created_by")
    @Schema(name="created_by", description="Name of user which create this record", implementation=String.class, example="admin")
    private final String createdBy;
    @JSONField(name="created_at")
    @JsonProperty(value="created_at")
    @Schema(name="created_at", description="Created timestamp", implementation=Long.class, example="123456789")
    private final Instant createdAt;
    @JSONField(name="list_id")
    @JsonProperty(value="list_id")
    @Schema(name="list_id", description="List ID", implementation=Integer.class, example="1")
    private final Integer listId;
    @JSONField(name="comment")
    @JsonProperty(value="comment")
    @Schema(name="comment", description="Comment", implementation=String.class, example="Some comment")
    private final String comment;

    public Instant getCreatedAt() {
        return this.createdAt != null ? this.createdAt : Instant.now();
    }

    public static IntegrationListItemDTOBuilder builder() {
        return new IntegrationListItemDTOBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getNumber() {
        return this.number;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Integer getListId() {
        return this.listId;
    }

    public String getComment() {
        return this.comment;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntegrationListItemDTO)) {
            return false;
        }
        IntegrationListItemDTO other = (IntegrationListItemDTO)o;
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Integer this$listId = this.getListId();
        Integer other$listId = other.getListId();
        if (this$listId == null ? other$listId != null : !((Object)this$listId).equals(other$listId)) {
            return false;
        }
        String this$number = this.getNumber();
        String other$number = other.getNumber();
        if (this$number == null ? other$number != null : !this$number.equals(other$number)) {
            return false;
        }
        String this$createdBy = this.getCreatedBy();
        String other$createdBy = other.getCreatedBy();
        if (this$createdBy == null ? other$createdBy != null : !this$createdBy.equals(other$createdBy)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
            return false;
        }
        String this$comment = this.getComment();
        String other$comment = other.getComment();
        return !(this$comment == null ? other$comment != null : !this$comment.equals(other$comment));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $listId = this.getListId();
        result = result * 59 + ($listId == null ? 43 : ((Object)$listId).hashCode());
        String $number = this.getNumber();
        result = result * 59 + ($number == null ? 43 : $number.hashCode());
        String $createdBy = this.getCreatedBy();
        result = result * 59 + ($createdBy == null ? 43 : $createdBy.hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        return result;
    }

    public String toString() {
        return "IntegrationListItemDTO(id=" + this.getId() + ", number=" + this.getNumber() + ", createdBy=" + this.getCreatedBy() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", listId=" + this.getListId() + ", comment=" + this.getComment() + ")";
    }

    public IntegrationListItemDTO(Integer id, String number, String createdBy, Instant createdAt, Integer listId, String comment) {
        this.id = id;
        this.number = number;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.listId = listId;
        this.comment = comment;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class IntegrationListItemDTOBuilder {
        private Integer id;
        private String number;
        private String createdBy;
        private Instant createdAt;
        private Integer listId;
        private String comment;

        IntegrationListItemDTOBuilder() {
        }

        @JsonProperty(value="id")
        public IntegrationListItemDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value="number")
        public IntegrationListItemDTOBuilder number(String number) {
            this.number = number;
            return this;
        }

        @JsonProperty(value="created_by")
        public IntegrationListItemDTOBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        @JsonProperty(value="created_at")
        public IntegrationListItemDTOBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @JsonProperty(value="list_id")
        public IntegrationListItemDTOBuilder listId(Integer listId) {
            this.listId = listId;
            return this;
        }

        @JsonProperty(value="comment")
        public IntegrationListItemDTOBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public IntegrationListItemDTO build() {
            return new IntegrationListItemDTO(this.id, this.number, this.createdBy, this.createdAt, this.listId, this.comment);
        }

        public String toString() {
            return "IntegrationListItemDTO.IntegrationListItemDTOBuilder(id=" + this.id + ", number=" + this.number + ", createdBy=" + this.createdBy + ", createdAt=" + String.valueOf(this.createdAt) + ", listId=" + this.listId + ", comment=" + this.comment + ")";
        }
    }

    public static class LicensePlateNumberDeserializer
    implements ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            String licensePlateNumber = parser.parseObject(String.class);
            return (T)(licensePlateNumber != null ? licensePlateNumber.replace(" ", "") : null);
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

