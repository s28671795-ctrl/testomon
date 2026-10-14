/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq.configs;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.middleware.mq.configs.MQPlacementDTO;
import java.util.List;
import java.util.Map;

@JsonDeserialize(builder=MQStreamConfigDTOBuilder.class)
public final class MQStreamConfigDTO {
    @JsonProperty(value="name")
    @JSONField(name="name")
    private final String name;
    @JsonProperty(value="storage")
    @JSONField(name="storage")
    private final String storageType;
    @JsonProperty(value="subjects")
    @JSONField(name="subjects")
    private final List<String> subjects;
    @JsonProperty(value="num_replicas")
    @JSONField(name="num_replicas")
    private final Integer replicasNumber;
    @JsonProperty(value="max_age")
    @JSONField(name="max_age")
    private final Long maxAge;
    @JsonProperty(value="max_bytes")
    @JSONField(name="max_bytes")
    private final Long maxBytes;
    @JsonProperty(value="max_msgs")
    @JSONField(name="max_msgs")
    private final Long maxMessages;
    @JsonProperty(value="max_msg_size")
    @JSONField(name="max_msg_size")
    private final Long maxMessageSize;
    @JsonProperty(value="max_consumers")
    @JSONField(name="max_consumers")
    private final Long maxConsumers;
    @JsonProperty(value="no_ack")
    @JSONField(name="no_ack")
    private final Boolean noAck;
    @JsonProperty(value="retention")
    @JSONField(name="retention")
    private final String retentionPolicy;
    @JsonProperty(value="discard")
    @JSONField(name="discard")
    private final String discardPolicy;
    @JsonProperty(value="duplicate_window")
    @JSONField(name="duplicate_window")
    private final Long duplicateWindow;
    @JsonProperty(value="max_msgs_per_subject")
    @JSONField(name="max_msgs_per_subject")
    private final Long maxMessagesPerSubject;
    @JsonProperty(value="description")
    @JSONField(name="description")
    private final String description;
    @JsonProperty(value="sealed")
    @JSONField(name="sealed")
    private final Boolean sealed;
    @JsonProperty(value="deny_delete")
    @JSONField(name="deny_delete")
    private final Boolean denyDelete;
    @JsonProperty(value="deny_purge")
    @JSONField(name="deny_purge")
    private final Boolean denyPurge;
    @JsonProperty(value="allow_rollup_hdrs")
    @JSONField(name="allow_rollup_hdrs")
    private final Boolean allowRollup;
    @JsonProperty(value="allow_direct")
    @JSONField(name="allow_direct")
    private final Boolean allowDirect;
    @JsonProperty(value="mirror_direct")
    @JSONField(name="mirror_direct")
    private final Boolean mirrorDirect;
    @JsonProperty(value="discard_new_per_subject")
    @JSONField(name="discard_new_per_subject")
    private final Boolean discardNewPerSubject;
    @JsonProperty(value="metadata")
    @JSONField(name="metadata")
    private final Map<String, String> metadata;
    @JsonProperty(value="compression")
    @JSONField(name="compression")
    private final String compressionOption;
    @JsonProperty(value="first_seq")
    @JSONField(name="first_seq")
    private final Long firstSequence;
    @JsonProperty(value="placement")
    @JSONField(name="placement")
    private final MQPlacementDTO placement;

    MQStreamConfigDTO(String name, String storageType, List<String> subjects, Integer replicasNumber, Long maxAge, Long maxBytes, Long maxMessages, Long maxMessageSize, Long maxConsumers, Boolean noAck, String retentionPolicy, String discardPolicy, Long duplicateWindow, Long maxMessagesPerSubject, String description, Boolean sealed, Boolean denyDelete, Boolean denyPurge, Boolean allowRollup, Boolean allowDirect, Boolean mirrorDirect, Boolean discardNewPerSubject, Map<String, String> metadata, String compressionOption, Long firstSequence, MQPlacementDTO placement) {
        this.name = name;
        this.storageType = storageType;
        this.subjects = subjects;
        this.replicasNumber = replicasNumber;
        this.maxAge = maxAge;
        this.maxBytes = maxBytes;
        this.maxMessages = maxMessages;
        this.maxMessageSize = maxMessageSize;
        this.maxConsumers = maxConsumers;
        this.noAck = noAck;
        this.retentionPolicy = retentionPolicy;
        this.discardPolicy = discardPolicy;
        this.duplicateWindow = duplicateWindow;
        this.maxMessagesPerSubject = maxMessagesPerSubject;
        this.description = description;
        this.sealed = sealed;
        this.denyDelete = denyDelete;
        this.denyPurge = denyPurge;
        this.allowRollup = allowRollup;
        this.allowDirect = allowDirect;
        this.mirrorDirect = mirrorDirect;
        this.discardNewPerSubject = discardNewPerSubject;
        this.metadata = metadata;
        this.compressionOption = compressionOption;
        this.firstSequence = firstSequence;
        this.placement = placement;
    }

    public static MQStreamConfigDTOBuilder builder() {
        return new MQStreamConfigDTOBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getStorageType() {
        return this.storageType;
    }

    public List<String> getSubjects() {
        return this.subjects;
    }

    public Integer getReplicasNumber() {
        return this.replicasNumber;
    }

    public Long getMaxAge() {
        return this.maxAge;
    }

    public Long getMaxBytes() {
        return this.maxBytes;
    }

    public Long getMaxMessages() {
        return this.maxMessages;
    }

    public Long getMaxMessageSize() {
        return this.maxMessageSize;
    }

    public Long getMaxConsumers() {
        return this.maxConsumers;
    }

    public Boolean getNoAck() {
        return this.noAck;
    }

    public String getRetentionPolicy() {
        return this.retentionPolicy;
    }

    public String getDiscardPolicy() {
        return this.discardPolicy;
    }

    public Long getDuplicateWindow() {
        return this.duplicateWindow;
    }

    public Long getMaxMessagesPerSubject() {
        return this.maxMessagesPerSubject;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getSealed() {
        return this.sealed;
    }

    public Boolean getDenyDelete() {
        return this.denyDelete;
    }

    public Boolean getDenyPurge() {
        return this.denyPurge;
    }

    public Boolean getAllowRollup() {
        return this.allowRollup;
    }

    public Boolean getAllowDirect() {
        return this.allowDirect;
    }

    public Boolean getMirrorDirect() {
        return this.mirrorDirect;
    }

    public Boolean getDiscardNewPerSubject() {
        return this.discardNewPerSubject;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public String getCompressionOption() {
        return this.compressionOption;
    }

    public Long getFirstSequence() {
        return this.firstSequence;
    }

    public MQPlacementDTO getPlacement() {
        return this.placement;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MQStreamConfigDTO)) {
            return false;
        }
        MQStreamConfigDTO other = (MQStreamConfigDTO)o;
        Integer this$replicasNumber = this.getReplicasNumber();
        Integer other$replicasNumber = other.getReplicasNumber();
        if (this$replicasNumber == null ? other$replicasNumber != null : !((Object)this$replicasNumber).equals(other$replicasNumber)) {
            return false;
        }
        Long this$maxAge = this.getMaxAge();
        Long other$maxAge = other.getMaxAge();
        if (this$maxAge == null ? other$maxAge != null : !((Object)this$maxAge).equals(other$maxAge)) {
            return false;
        }
        Long this$maxBytes = this.getMaxBytes();
        Long other$maxBytes = other.getMaxBytes();
        if (this$maxBytes == null ? other$maxBytes != null : !((Object)this$maxBytes).equals(other$maxBytes)) {
            return false;
        }
        Long this$maxMessages = this.getMaxMessages();
        Long other$maxMessages = other.getMaxMessages();
        if (this$maxMessages == null ? other$maxMessages != null : !((Object)this$maxMessages).equals(other$maxMessages)) {
            return false;
        }
        Long this$maxMessageSize = this.getMaxMessageSize();
        Long other$maxMessageSize = other.getMaxMessageSize();
        if (this$maxMessageSize == null ? other$maxMessageSize != null : !((Object)this$maxMessageSize).equals(other$maxMessageSize)) {
            return false;
        }
        Long this$maxConsumers = this.getMaxConsumers();
        Long other$maxConsumers = other.getMaxConsumers();
        if (this$maxConsumers == null ? other$maxConsumers != null : !((Object)this$maxConsumers).equals(other$maxConsumers)) {
            return false;
        }
        Boolean this$noAck = this.getNoAck();
        Boolean other$noAck = other.getNoAck();
        if (this$noAck == null ? other$noAck != null : !((Object)this$noAck).equals(other$noAck)) {
            return false;
        }
        Long this$duplicateWindow = this.getDuplicateWindow();
        Long other$duplicateWindow = other.getDuplicateWindow();
        if (this$duplicateWindow == null ? other$duplicateWindow != null : !((Object)this$duplicateWindow).equals(other$duplicateWindow)) {
            return false;
        }
        Long this$maxMessagesPerSubject = this.getMaxMessagesPerSubject();
        Long other$maxMessagesPerSubject = other.getMaxMessagesPerSubject();
        if (this$maxMessagesPerSubject == null ? other$maxMessagesPerSubject != null : !((Object)this$maxMessagesPerSubject).equals(other$maxMessagesPerSubject)) {
            return false;
        }
        Boolean this$sealed = this.getSealed();
        Boolean other$sealed = other.getSealed();
        if (this$sealed == null ? other$sealed != null : !((Object)this$sealed).equals(other$sealed)) {
            return false;
        }
        Boolean this$denyDelete = this.getDenyDelete();
        Boolean other$denyDelete = other.getDenyDelete();
        if (this$denyDelete == null ? other$denyDelete != null : !((Object)this$denyDelete).equals(other$denyDelete)) {
            return false;
        }
        Boolean this$denyPurge = this.getDenyPurge();
        Boolean other$denyPurge = other.getDenyPurge();
        if (this$denyPurge == null ? other$denyPurge != null : !((Object)this$denyPurge).equals(other$denyPurge)) {
            return false;
        }
        Boolean this$allowRollup = this.getAllowRollup();
        Boolean other$allowRollup = other.getAllowRollup();
        if (this$allowRollup == null ? other$allowRollup != null : !((Object)this$allowRollup).equals(other$allowRollup)) {
            return false;
        }
        Boolean this$allowDirect = this.getAllowDirect();
        Boolean other$allowDirect = other.getAllowDirect();
        if (this$allowDirect == null ? other$allowDirect != null : !((Object)this$allowDirect).equals(other$allowDirect)) {
            return false;
        }
        Boolean this$mirrorDirect = this.getMirrorDirect();
        Boolean other$mirrorDirect = other.getMirrorDirect();
        if (this$mirrorDirect == null ? other$mirrorDirect != null : !((Object)this$mirrorDirect).equals(other$mirrorDirect)) {
            return false;
        }
        Boolean this$discardNewPerSubject = this.getDiscardNewPerSubject();
        Boolean other$discardNewPerSubject = other.getDiscardNewPerSubject();
        if (this$discardNewPerSubject == null ? other$discardNewPerSubject != null : !((Object)this$discardNewPerSubject).equals(other$discardNewPerSubject)) {
            return false;
        }
        Long this$firstSequence = this.getFirstSequence();
        Long other$firstSequence = other.getFirstSequence();
        if (this$firstSequence == null ? other$firstSequence != null : !((Object)this$firstSequence).equals(other$firstSequence)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$storageType = this.getStorageType();
        String other$storageType = other.getStorageType();
        if (this$storageType == null ? other$storageType != null : !this$storageType.equals(other$storageType)) {
            return false;
        }
        List<String> this$subjects = this.getSubjects();
        List<String> other$subjects = other.getSubjects();
        if (this$subjects == null ? other$subjects != null : !((Object)this$subjects).equals(other$subjects)) {
            return false;
        }
        String this$retentionPolicy = this.getRetentionPolicy();
        String other$retentionPolicy = other.getRetentionPolicy();
        if (this$retentionPolicy == null ? other$retentionPolicy != null : !this$retentionPolicy.equals(other$retentionPolicy)) {
            return false;
        }
        String this$discardPolicy = this.getDiscardPolicy();
        String other$discardPolicy = other.getDiscardPolicy();
        if (this$discardPolicy == null ? other$discardPolicy != null : !this$discardPolicy.equals(other$discardPolicy)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        Map<String, String> this$metadata = this.getMetadata();
        Map<String, String> other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !((Object)this$metadata).equals(other$metadata)) {
            return false;
        }
        String this$compressionOption = this.getCompressionOption();
        String other$compressionOption = other.getCompressionOption();
        if (this$compressionOption == null ? other$compressionOption != null : !this$compressionOption.equals(other$compressionOption)) {
            return false;
        }
        MQPlacementDTO this$placement = this.getPlacement();
        MQPlacementDTO other$placement = other.getPlacement();
        return !(this$placement == null ? other$placement != null : !((Object)this$placement).equals(other$placement));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $replicasNumber = this.getReplicasNumber();
        result = result * 59 + ($replicasNumber == null ? 43 : ((Object)$replicasNumber).hashCode());
        Long $maxAge = this.getMaxAge();
        result = result * 59 + ($maxAge == null ? 43 : ((Object)$maxAge).hashCode());
        Long $maxBytes = this.getMaxBytes();
        result = result * 59 + ($maxBytes == null ? 43 : ((Object)$maxBytes).hashCode());
        Long $maxMessages = this.getMaxMessages();
        result = result * 59 + ($maxMessages == null ? 43 : ((Object)$maxMessages).hashCode());
        Long $maxMessageSize = this.getMaxMessageSize();
        result = result * 59 + ($maxMessageSize == null ? 43 : ((Object)$maxMessageSize).hashCode());
        Long $maxConsumers = this.getMaxConsumers();
        result = result * 59 + ($maxConsumers == null ? 43 : ((Object)$maxConsumers).hashCode());
        Boolean $noAck = this.getNoAck();
        result = result * 59 + ($noAck == null ? 43 : ((Object)$noAck).hashCode());
        Long $duplicateWindow = this.getDuplicateWindow();
        result = result * 59 + ($duplicateWindow == null ? 43 : ((Object)$duplicateWindow).hashCode());
        Long $maxMessagesPerSubject = this.getMaxMessagesPerSubject();
        result = result * 59 + ($maxMessagesPerSubject == null ? 43 : ((Object)$maxMessagesPerSubject).hashCode());
        Boolean $sealed = this.getSealed();
        result = result * 59 + ($sealed == null ? 43 : ((Object)$sealed).hashCode());
        Boolean $denyDelete = this.getDenyDelete();
        result = result * 59 + ($denyDelete == null ? 43 : ((Object)$denyDelete).hashCode());
        Boolean $denyPurge = this.getDenyPurge();
        result = result * 59 + ($denyPurge == null ? 43 : ((Object)$denyPurge).hashCode());
        Boolean $allowRollup = this.getAllowRollup();
        result = result * 59 + ($allowRollup == null ? 43 : ((Object)$allowRollup).hashCode());
        Boolean $allowDirect = this.getAllowDirect();
        result = result * 59 + ($allowDirect == null ? 43 : ((Object)$allowDirect).hashCode());
        Boolean $mirrorDirect = this.getMirrorDirect();
        result = result * 59 + ($mirrorDirect == null ? 43 : ((Object)$mirrorDirect).hashCode());
        Boolean $discardNewPerSubject = this.getDiscardNewPerSubject();
        result = result * 59 + ($discardNewPerSubject == null ? 43 : ((Object)$discardNewPerSubject).hashCode());
        Long $firstSequence = this.getFirstSequence();
        result = result * 59 + ($firstSequence == null ? 43 : ((Object)$firstSequence).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $storageType = this.getStorageType();
        result = result * 59 + ($storageType == null ? 43 : $storageType.hashCode());
        List<String> $subjects = this.getSubjects();
        result = result * 59 + ($subjects == null ? 43 : ((Object)$subjects).hashCode());
        String $retentionPolicy = this.getRetentionPolicy();
        result = result * 59 + ($retentionPolicy == null ? 43 : $retentionPolicy.hashCode());
        String $discardPolicy = this.getDiscardPolicy();
        result = result * 59 + ($discardPolicy == null ? 43 : $discardPolicy.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Map<String, String> $metadata = this.getMetadata();
        result = result * 59 + ($metadata == null ? 43 : ((Object)$metadata).hashCode());
        String $compressionOption = this.getCompressionOption();
        result = result * 59 + ($compressionOption == null ? 43 : $compressionOption.hashCode());
        MQPlacementDTO $placement = this.getPlacement();
        result = result * 59 + ($placement == null ? 43 : ((Object)$placement).hashCode());
        return result;
    }

    public String toString() {
        return "MQStreamConfigDTO(name=" + this.getName() + ", storageType=" + this.getStorageType() + ", subjects=" + String.valueOf(this.getSubjects()) + ", replicasNumber=" + this.getReplicasNumber() + ", maxAge=" + this.getMaxAge() + ", maxBytes=" + this.getMaxBytes() + ", maxMessages=" + this.getMaxMessages() + ", maxMessageSize=" + this.getMaxMessageSize() + ", maxConsumers=" + this.getMaxConsumers() + ", noAck=" + this.getNoAck() + ", retentionPolicy=" + this.getRetentionPolicy() + ", discardPolicy=" + this.getDiscardPolicy() + ", duplicateWindow=" + this.getDuplicateWindow() + ", maxMessagesPerSubject=" + this.getMaxMessagesPerSubject() + ", description=" + this.getDescription() + ", sealed=" + this.getSealed() + ", denyDelete=" + this.getDenyDelete() + ", denyPurge=" + this.getDenyPurge() + ", allowRollup=" + this.getAllowRollup() + ", allowDirect=" + this.getAllowDirect() + ", mirrorDirect=" + this.getMirrorDirect() + ", discardNewPerSubject=" + this.getDiscardNewPerSubject() + ", metadata=" + String.valueOf(this.getMetadata()) + ", compressionOption=" + this.getCompressionOption() + ", firstSequence=" + this.getFirstSequence() + ", placement=" + String.valueOf(this.getPlacement()) + ")";
    }

    public MQStreamConfigDTO withName(String name) {
        return this.name == name ? this : new MQStreamConfigDTO(name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withStorageType(String storageType) {
        return this.storageType == storageType ? this : new MQStreamConfigDTO(this.name, storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withSubjects(List<String> subjects) {
        return this.subjects == subjects ? this : new MQStreamConfigDTO(this.name, this.storageType, subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withReplicasNumber(Integer replicasNumber) {
        return this.replicasNumber == replicasNumber ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMaxAge(Long maxAge) {
        return this.maxAge == maxAge ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMaxBytes(Long maxBytes) {
        return this.maxBytes == maxBytes ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMaxMessages(Long maxMessages) {
        return this.maxMessages == maxMessages ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMaxMessageSize(Long maxMessageSize) {
        return this.maxMessageSize == maxMessageSize ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMaxConsumers(Long maxConsumers) {
        return this.maxConsumers == maxConsumers ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withNoAck(Boolean noAck) {
        return this.noAck == noAck ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withRetentionPolicy(String retentionPolicy) {
        return this.retentionPolicy == retentionPolicy ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withDiscardPolicy(String discardPolicy) {
        return this.discardPolicy == discardPolicy ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withDuplicateWindow(Long duplicateWindow) {
        return this.duplicateWindow == duplicateWindow ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMaxMessagesPerSubject(Long maxMessagesPerSubject) {
        return this.maxMessagesPerSubject == maxMessagesPerSubject ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withDescription(String description) {
        return this.description == description ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withSealed(Boolean sealed) {
        return this.sealed == sealed ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withDenyDelete(Boolean denyDelete) {
        return this.denyDelete == denyDelete ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withDenyPurge(Boolean denyPurge) {
        return this.denyPurge == denyPurge ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withAllowRollup(Boolean allowRollup) {
        return this.allowRollup == allowRollup ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withAllowDirect(Boolean allowDirect) {
        return this.allowDirect == allowDirect ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMirrorDirect(Boolean mirrorDirect) {
        return this.mirrorDirect == mirrorDirect ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withDiscardNewPerSubject(Boolean discardNewPerSubject) {
        return this.discardNewPerSubject == discardNewPerSubject ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withMetadata(Map<String, String> metadata) {
        return this.metadata == metadata ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, metadata, this.compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withCompressionOption(String compressionOption) {
        return this.compressionOption == compressionOption ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, compressionOption, this.firstSequence, this.placement);
    }

    public MQStreamConfigDTO withFirstSequence(Long firstSequence) {
        return this.firstSequence == firstSequence ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, firstSequence, this.placement);
    }

    public MQStreamConfigDTO withPlacement(MQPlacementDTO placement) {
        return this.placement == placement ? this : new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, placement);
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class MQStreamConfigDTOBuilder {
        private String name;
        private String storageType;
        private List<String> subjects;
        private Integer replicasNumber;
        private Long maxAge;
        private Long maxBytes;
        private Long maxMessages;
        private Long maxMessageSize;
        private Long maxConsumers;
        private Boolean noAck;
        private String retentionPolicy;
        private String discardPolicy;
        private Long duplicateWindow;
        private Long maxMessagesPerSubject;
        private String description;
        private Boolean sealed;
        private Boolean denyDelete;
        private Boolean denyPurge;
        private Boolean allowRollup;
        private Boolean allowDirect;
        private Boolean mirrorDirect;
        private Boolean discardNewPerSubject;
        private Map<String, String> metadata;
        private String compressionOption;
        private Long firstSequence;
        private MQPlacementDTO placement;

        MQStreamConfigDTOBuilder() {
        }

        @JsonProperty(value="name")
        public MQStreamConfigDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty(value="storage")
        public MQStreamConfigDTOBuilder storageType(String storageType) {
            this.storageType = storageType;
            return this;
        }

        @JsonProperty(value="subjects")
        public MQStreamConfigDTOBuilder subjects(List<String> subjects) {
            this.subjects = subjects;
            return this;
        }

        @JsonProperty(value="num_replicas")
        public MQStreamConfigDTOBuilder replicasNumber(Integer replicasNumber) {
            this.replicasNumber = replicasNumber;
            return this;
        }

        @JsonProperty(value="max_age")
        public MQStreamConfigDTOBuilder maxAge(Long maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        @JsonProperty(value="max_bytes")
        public MQStreamConfigDTOBuilder maxBytes(Long maxBytes) {
            this.maxBytes = maxBytes;
            return this;
        }

        @JsonProperty(value="max_msgs")
        public MQStreamConfigDTOBuilder maxMessages(Long maxMessages) {
            this.maxMessages = maxMessages;
            return this;
        }

        @JsonProperty(value="max_msg_size")
        public MQStreamConfigDTOBuilder maxMessageSize(Long maxMessageSize) {
            this.maxMessageSize = maxMessageSize;
            return this;
        }

        @JsonProperty(value="max_consumers")
        public MQStreamConfigDTOBuilder maxConsumers(Long maxConsumers) {
            this.maxConsumers = maxConsumers;
            return this;
        }

        @JsonProperty(value="no_ack")
        public MQStreamConfigDTOBuilder noAck(Boolean noAck) {
            this.noAck = noAck;
            return this;
        }

        @JsonProperty(value="retention")
        public MQStreamConfigDTOBuilder retentionPolicy(String retentionPolicy) {
            this.retentionPolicy = retentionPolicy;
            return this;
        }

        @JsonProperty(value="discard")
        public MQStreamConfigDTOBuilder discardPolicy(String discardPolicy) {
            this.discardPolicy = discardPolicy;
            return this;
        }

        @JsonProperty(value="duplicate_window")
        public MQStreamConfigDTOBuilder duplicateWindow(Long duplicateWindow) {
            this.duplicateWindow = duplicateWindow;
            return this;
        }

        @JsonProperty(value="max_msgs_per_subject")
        public MQStreamConfigDTOBuilder maxMessagesPerSubject(Long maxMessagesPerSubject) {
            this.maxMessagesPerSubject = maxMessagesPerSubject;
            return this;
        }

        @JsonProperty(value="description")
        public MQStreamConfigDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty(value="sealed")
        public MQStreamConfigDTOBuilder sealed(Boolean sealed) {
            this.sealed = sealed;
            return this;
        }

        @JsonProperty(value="deny_delete")
        public MQStreamConfigDTOBuilder denyDelete(Boolean denyDelete) {
            this.denyDelete = denyDelete;
            return this;
        }

        @JsonProperty(value="deny_purge")
        public MQStreamConfigDTOBuilder denyPurge(Boolean denyPurge) {
            this.denyPurge = denyPurge;
            return this;
        }

        @JsonProperty(value="allow_rollup_hdrs")
        public MQStreamConfigDTOBuilder allowRollup(Boolean allowRollup) {
            this.allowRollup = allowRollup;
            return this;
        }

        @JsonProperty(value="allow_direct")
        public MQStreamConfigDTOBuilder allowDirect(Boolean allowDirect) {
            this.allowDirect = allowDirect;
            return this;
        }

        @JsonProperty(value="mirror_direct")
        public MQStreamConfigDTOBuilder mirrorDirect(Boolean mirrorDirect) {
            this.mirrorDirect = mirrorDirect;
            return this;
        }

        @JsonProperty(value="discard_new_per_subject")
        public MQStreamConfigDTOBuilder discardNewPerSubject(Boolean discardNewPerSubject) {
            this.discardNewPerSubject = discardNewPerSubject;
            return this;
        }

        @JsonProperty(value="metadata")
        public MQStreamConfigDTOBuilder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        @JsonProperty(value="compression")
        public MQStreamConfigDTOBuilder compressionOption(String compressionOption) {
            this.compressionOption = compressionOption;
            return this;
        }

        @JsonProperty(value="first_seq")
        public MQStreamConfigDTOBuilder firstSequence(Long firstSequence) {
            this.firstSequence = firstSequence;
            return this;
        }

        @JsonProperty(value="placement")
        public MQStreamConfigDTOBuilder placement(MQPlacementDTO placement) {
            this.placement = placement;
            return this;
        }

        public MQStreamConfigDTO build() {
            return new MQStreamConfigDTO(this.name, this.storageType, this.subjects, this.replicasNumber, this.maxAge, this.maxBytes, this.maxMessages, this.maxMessageSize, this.maxConsumers, this.noAck, this.retentionPolicy, this.discardPolicy, this.duplicateWindow, this.maxMessagesPerSubject, this.description, this.sealed, this.denyDelete, this.denyPurge, this.allowRollup, this.allowDirect, this.mirrorDirect, this.discardNewPerSubject, this.metadata, this.compressionOption, this.firstSequence, this.placement);
        }

        public String toString() {
            return "MQStreamConfigDTO.MQStreamConfigDTOBuilder(name=" + this.name + ", storageType=" + this.storageType + ", subjects=" + String.valueOf(this.subjects) + ", replicasNumber=" + this.replicasNumber + ", maxAge=" + this.maxAge + ", maxBytes=" + this.maxBytes + ", maxMessages=" + this.maxMessages + ", maxMessageSize=" + this.maxMessageSize + ", maxConsumers=" + this.maxConsumers + ", noAck=" + this.noAck + ", retentionPolicy=" + this.retentionPolicy + ", discardPolicy=" + this.discardPolicy + ", duplicateWindow=" + this.duplicateWindow + ", maxMessagesPerSubject=" + this.maxMessagesPerSubject + ", description=" + this.description + ", sealed=" + this.sealed + ", denyDelete=" + this.denyDelete + ", denyPurge=" + this.denyPurge + ", allowRollup=" + this.allowRollup + ", allowDirect=" + this.allowDirect + ", mirrorDirect=" + this.mirrorDirect + ", discardNewPerSubject=" + this.discardNewPerSubject + ", metadata=" + String.valueOf(this.metadata) + ", compressionOption=" + this.compressionOption + ", firstSequence=" + this.firstSequence + ", placement=" + String.valueOf(this.placement) + ")";
        }
    }
}

