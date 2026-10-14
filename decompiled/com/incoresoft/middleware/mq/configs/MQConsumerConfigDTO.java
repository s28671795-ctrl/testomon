/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq.configs;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@JsonDeserialize(builder=MQConsumerConfigDTOBuilder.class)
public final class MQConsumerConfigDTO {
    @JsonProperty(value="durable")
    @JSONField(name="durable")
    private final String durableName;
    @JsonProperty(value="filter_subject")
    @JSONField(name="filter_subject")
    private final String filterSubject;
    @JsonProperty(value="ack_policy")
    @JSONField(name="ack_policy")
    private final String ackPolicy;
    @JsonProperty(value="ack_wait")
    @JSONField(name="ack_wait")
    private final Long ackWait;
    @JsonProperty(value="deliver_policy")
    @JSONField(name="deliver_policy")
    private final String deliverPolicy;
    @JsonProperty(value="opt_start_seq")
    @JSONField(name="opt_start_seq")
    private final Long optionStartSequence;
    @JsonProperty(value="opt_start_time")
    @JSONField(name="opt_start_time")
    private final ZonedDateTime optionStartTime;
    @JsonProperty(value="description")
    @JSONField(name="description")
    private final String description;
    @JsonProperty(value="inactive_threshold")
    @JSONField(name="inactive_threshold")
    private final Long inactiveThreshold;
    @JsonProperty(value="max_ack_pending")
    @JSONField(name="max_ack_pending")
    private final Long maxAckPending;
    @JsonProperty(value="max_deliver")
    @JSONField(name="max_deliver")
    private final Long maxDeliverAttemptsPerMessage;
    @JsonProperty(value="backoff")
    @JSONField(name="backoff")
    private final List<Long> backoff;
    @JsonProperty(value="replay_policy")
    @JSONField(name="replay_policy")
    private final String replayPolicy;
    @JsonProperty(value="num_replicas")
    @JSONField(name="num_replicas")
    private final Integer replicasNumber;
    @JsonProperty(value="mem_storage")
    @JSONField(name="mem_storage")
    private final Boolean memoryStorage;
    @JsonProperty(value="sample_freq")
    @JSONField(name="sample_freq")
    private final String sampleFrequency;
    @JsonProperty(value="metadata")
    @JSONField(name="metadata")
    private final Map<String, String> metadata;
    @JsonProperty(value="max_waiting")
    @JSONField(name="max_waiting")
    private final Long maxPullWaiting;
    @JsonProperty(value="max_expires")
    @JSONField(name="max_expires")
    private final Long maxExpires;
    @JsonProperty(value="max_batch")
    @JSONField(name="max_batch")
    private final Integer maxBatch;
    @JsonProperty(value="max_bytes")
    @JSONField(name="max_bytes")
    private final Long maxBytes;

    MQConsumerConfigDTO(String durableName, String filterSubject, String ackPolicy, Long ackWait, String deliverPolicy, Long optionStartSequence, ZonedDateTime optionStartTime, String description, Long inactiveThreshold, Long maxAckPending, Long maxDeliverAttemptsPerMessage, List<Long> backoff, String replayPolicy, Integer replicasNumber, Boolean memoryStorage, String sampleFrequency, Map<String, String> metadata, Long maxPullWaiting, Long maxExpires, Integer maxBatch, Long maxBytes) {
        this.durableName = durableName;
        this.filterSubject = filterSubject;
        this.ackPolicy = ackPolicy;
        this.ackWait = ackWait;
        this.deliverPolicy = deliverPolicy;
        this.optionStartSequence = optionStartSequence;
        this.optionStartTime = optionStartTime;
        this.description = description;
        this.inactiveThreshold = inactiveThreshold;
        this.maxAckPending = maxAckPending;
        this.maxDeliverAttemptsPerMessage = maxDeliverAttemptsPerMessage;
        this.backoff = backoff;
        this.replayPolicy = replayPolicy;
        this.replicasNumber = replicasNumber;
        this.memoryStorage = memoryStorage;
        this.sampleFrequency = sampleFrequency;
        this.metadata = metadata;
        this.maxPullWaiting = maxPullWaiting;
        this.maxExpires = maxExpires;
        this.maxBatch = maxBatch;
        this.maxBytes = maxBytes;
    }

    public static MQConsumerConfigDTOBuilder builder() {
        return new MQConsumerConfigDTOBuilder();
    }

    public String getDurableName() {
        return this.durableName;
    }

    public String getFilterSubject() {
        return this.filterSubject;
    }

    public String getAckPolicy() {
        return this.ackPolicy;
    }

    public Long getAckWait() {
        return this.ackWait;
    }

    public String getDeliverPolicy() {
        return this.deliverPolicy;
    }

    public Long getOptionStartSequence() {
        return this.optionStartSequence;
    }

    public ZonedDateTime getOptionStartTime() {
        return this.optionStartTime;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getInactiveThreshold() {
        return this.inactiveThreshold;
    }

    public Long getMaxAckPending() {
        return this.maxAckPending;
    }

    public Long getMaxDeliverAttemptsPerMessage() {
        return this.maxDeliverAttemptsPerMessage;
    }

    public List<Long> getBackoff() {
        return this.backoff;
    }

    public String getReplayPolicy() {
        return this.replayPolicy;
    }

    public Integer getReplicasNumber() {
        return this.replicasNumber;
    }

    public Boolean getMemoryStorage() {
        return this.memoryStorage;
    }

    public String getSampleFrequency() {
        return this.sampleFrequency;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public Long getMaxPullWaiting() {
        return this.maxPullWaiting;
    }

    public Long getMaxExpires() {
        return this.maxExpires;
    }

    public Integer getMaxBatch() {
        return this.maxBatch;
    }

    public Long getMaxBytes() {
        return this.maxBytes;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MQConsumerConfigDTO)) {
            return false;
        }
        MQConsumerConfigDTO other = (MQConsumerConfigDTO)o;
        Long this$ackWait = this.getAckWait();
        Long other$ackWait = other.getAckWait();
        if (this$ackWait == null ? other$ackWait != null : !((Object)this$ackWait).equals(other$ackWait)) {
            return false;
        }
        Long this$optionStartSequence = this.getOptionStartSequence();
        Long other$optionStartSequence = other.getOptionStartSequence();
        if (this$optionStartSequence == null ? other$optionStartSequence != null : !((Object)this$optionStartSequence).equals(other$optionStartSequence)) {
            return false;
        }
        Long this$inactiveThreshold = this.getInactiveThreshold();
        Long other$inactiveThreshold = other.getInactiveThreshold();
        if (this$inactiveThreshold == null ? other$inactiveThreshold != null : !((Object)this$inactiveThreshold).equals(other$inactiveThreshold)) {
            return false;
        }
        Long this$maxAckPending = this.getMaxAckPending();
        Long other$maxAckPending = other.getMaxAckPending();
        if (this$maxAckPending == null ? other$maxAckPending != null : !((Object)this$maxAckPending).equals(other$maxAckPending)) {
            return false;
        }
        Long this$maxDeliverAttemptsPerMessage = this.getMaxDeliverAttemptsPerMessage();
        Long other$maxDeliverAttemptsPerMessage = other.getMaxDeliverAttemptsPerMessage();
        if (this$maxDeliverAttemptsPerMessage == null ? other$maxDeliverAttemptsPerMessage != null : !((Object)this$maxDeliverAttemptsPerMessage).equals(other$maxDeliverAttemptsPerMessage)) {
            return false;
        }
        Integer this$replicasNumber = this.getReplicasNumber();
        Integer other$replicasNumber = other.getReplicasNumber();
        if (this$replicasNumber == null ? other$replicasNumber != null : !((Object)this$replicasNumber).equals(other$replicasNumber)) {
            return false;
        }
        Boolean this$memoryStorage = this.getMemoryStorage();
        Boolean other$memoryStorage = other.getMemoryStorage();
        if (this$memoryStorage == null ? other$memoryStorage != null : !((Object)this$memoryStorage).equals(other$memoryStorage)) {
            return false;
        }
        Long this$maxPullWaiting = this.getMaxPullWaiting();
        Long other$maxPullWaiting = other.getMaxPullWaiting();
        if (this$maxPullWaiting == null ? other$maxPullWaiting != null : !((Object)this$maxPullWaiting).equals(other$maxPullWaiting)) {
            return false;
        }
        Long this$maxExpires = this.getMaxExpires();
        Long other$maxExpires = other.getMaxExpires();
        if (this$maxExpires == null ? other$maxExpires != null : !((Object)this$maxExpires).equals(other$maxExpires)) {
            return false;
        }
        Integer this$maxBatch = this.getMaxBatch();
        Integer other$maxBatch = other.getMaxBatch();
        if (this$maxBatch == null ? other$maxBatch != null : !((Object)this$maxBatch).equals(other$maxBatch)) {
            return false;
        }
        Long this$maxBytes = this.getMaxBytes();
        Long other$maxBytes = other.getMaxBytes();
        if (this$maxBytes == null ? other$maxBytes != null : !((Object)this$maxBytes).equals(other$maxBytes)) {
            return false;
        }
        String this$durableName = this.getDurableName();
        String other$durableName = other.getDurableName();
        if (this$durableName == null ? other$durableName != null : !this$durableName.equals(other$durableName)) {
            return false;
        }
        String this$filterSubject = this.getFilterSubject();
        String other$filterSubject = other.getFilterSubject();
        if (this$filterSubject == null ? other$filterSubject != null : !this$filterSubject.equals(other$filterSubject)) {
            return false;
        }
        String this$ackPolicy = this.getAckPolicy();
        String other$ackPolicy = other.getAckPolicy();
        if (this$ackPolicy == null ? other$ackPolicy != null : !this$ackPolicy.equals(other$ackPolicy)) {
            return false;
        }
        String this$deliverPolicy = this.getDeliverPolicy();
        String other$deliverPolicy = other.getDeliverPolicy();
        if (this$deliverPolicy == null ? other$deliverPolicy != null : !this$deliverPolicy.equals(other$deliverPolicy)) {
            return false;
        }
        ZonedDateTime this$optionStartTime = this.getOptionStartTime();
        ZonedDateTime other$optionStartTime = other.getOptionStartTime();
        if (this$optionStartTime == null ? other$optionStartTime != null : !((Object)this$optionStartTime).equals(other$optionStartTime)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        List<Long> this$backoff = this.getBackoff();
        List<Long> other$backoff = other.getBackoff();
        if (this$backoff == null ? other$backoff != null : !((Object)this$backoff).equals(other$backoff)) {
            return false;
        }
        String this$replayPolicy = this.getReplayPolicy();
        String other$replayPolicy = other.getReplayPolicy();
        if (this$replayPolicy == null ? other$replayPolicy != null : !this$replayPolicy.equals(other$replayPolicy)) {
            return false;
        }
        String this$sampleFrequency = this.getSampleFrequency();
        String other$sampleFrequency = other.getSampleFrequency();
        if (this$sampleFrequency == null ? other$sampleFrequency != null : !this$sampleFrequency.equals(other$sampleFrequency)) {
            return false;
        }
        Map<String, String> this$metadata = this.getMetadata();
        Map<String, String> other$metadata = other.getMetadata();
        return !(this$metadata == null ? other$metadata != null : !((Object)this$metadata).equals(other$metadata));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $ackWait = this.getAckWait();
        result = result * 59 + ($ackWait == null ? 43 : ((Object)$ackWait).hashCode());
        Long $optionStartSequence = this.getOptionStartSequence();
        result = result * 59 + ($optionStartSequence == null ? 43 : ((Object)$optionStartSequence).hashCode());
        Long $inactiveThreshold = this.getInactiveThreshold();
        result = result * 59 + ($inactiveThreshold == null ? 43 : ((Object)$inactiveThreshold).hashCode());
        Long $maxAckPending = this.getMaxAckPending();
        result = result * 59 + ($maxAckPending == null ? 43 : ((Object)$maxAckPending).hashCode());
        Long $maxDeliverAttemptsPerMessage = this.getMaxDeliverAttemptsPerMessage();
        result = result * 59 + ($maxDeliverAttemptsPerMessage == null ? 43 : ((Object)$maxDeliverAttemptsPerMessage).hashCode());
        Integer $replicasNumber = this.getReplicasNumber();
        result = result * 59 + ($replicasNumber == null ? 43 : ((Object)$replicasNumber).hashCode());
        Boolean $memoryStorage = this.getMemoryStorage();
        result = result * 59 + ($memoryStorage == null ? 43 : ((Object)$memoryStorage).hashCode());
        Long $maxPullWaiting = this.getMaxPullWaiting();
        result = result * 59 + ($maxPullWaiting == null ? 43 : ((Object)$maxPullWaiting).hashCode());
        Long $maxExpires = this.getMaxExpires();
        result = result * 59 + ($maxExpires == null ? 43 : ((Object)$maxExpires).hashCode());
        Integer $maxBatch = this.getMaxBatch();
        result = result * 59 + ($maxBatch == null ? 43 : ((Object)$maxBatch).hashCode());
        Long $maxBytes = this.getMaxBytes();
        result = result * 59 + ($maxBytes == null ? 43 : ((Object)$maxBytes).hashCode());
        String $durableName = this.getDurableName();
        result = result * 59 + ($durableName == null ? 43 : $durableName.hashCode());
        String $filterSubject = this.getFilterSubject();
        result = result * 59 + ($filterSubject == null ? 43 : $filterSubject.hashCode());
        String $ackPolicy = this.getAckPolicy();
        result = result * 59 + ($ackPolicy == null ? 43 : $ackPolicy.hashCode());
        String $deliverPolicy = this.getDeliverPolicy();
        result = result * 59 + ($deliverPolicy == null ? 43 : $deliverPolicy.hashCode());
        ZonedDateTime $optionStartTime = this.getOptionStartTime();
        result = result * 59 + ($optionStartTime == null ? 43 : ((Object)$optionStartTime).hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        List<Long> $backoff = this.getBackoff();
        result = result * 59 + ($backoff == null ? 43 : ((Object)$backoff).hashCode());
        String $replayPolicy = this.getReplayPolicy();
        result = result * 59 + ($replayPolicy == null ? 43 : $replayPolicy.hashCode());
        String $sampleFrequency = this.getSampleFrequency();
        result = result * 59 + ($sampleFrequency == null ? 43 : $sampleFrequency.hashCode());
        Map<String, String> $metadata = this.getMetadata();
        result = result * 59 + ($metadata == null ? 43 : ((Object)$metadata).hashCode());
        return result;
    }

    public String toString() {
        return "MQConsumerConfigDTO(durableName=" + this.getDurableName() + ", filterSubject=" + this.getFilterSubject() + ", ackPolicy=" + this.getAckPolicy() + ", ackWait=" + this.getAckWait() + ", deliverPolicy=" + this.getDeliverPolicy() + ", optionStartSequence=" + this.getOptionStartSequence() + ", optionStartTime=" + String.valueOf(this.getOptionStartTime()) + ", description=" + this.getDescription() + ", inactiveThreshold=" + this.getInactiveThreshold() + ", maxAckPending=" + this.getMaxAckPending() + ", maxDeliverAttemptsPerMessage=" + this.getMaxDeliverAttemptsPerMessage() + ", backoff=" + String.valueOf(this.getBackoff()) + ", replayPolicy=" + this.getReplayPolicy() + ", replicasNumber=" + this.getReplicasNumber() + ", memoryStorage=" + this.getMemoryStorage() + ", sampleFrequency=" + this.getSampleFrequency() + ", metadata=" + String.valueOf(this.getMetadata()) + ", maxPullWaiting=" + this.getMaxPullWaiting() + ", maxExpires=" + this.getMaxExpires() + ", maxBatch=" + this.getMaxBatch() + ", maxBytes=" + this.getMaxBytes() + ")";
    }

    public MQConsumerConfigDTO withDurableName(String durableName) {
        return this.durableName == durableName ? this : new MQConsumerConfigDTO(durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withFilterSubject(String filterSubject) {
        return this.filterSubject == filterSubject ? this : new MQConsumerConfigDTO(this.durableName, filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withAckPolicy(String ackPolicy) {
        return this.ackPolicy == ackPolicy ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withAckWait(Long ackWait) {
        return this.ackWait == ackWait ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withDeliverPolicy(String deliverPolicy) {
        return this.deliverPolicy == deliverPolicy ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withOptionStartSequence(Long optionStartSequence) {
        return this.optionStartSequence == optionStartSequence ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withOptionStartTime(ZonedDateTime optionStartTime) {
        return this.optionStartTime == optionStartTime ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withDescription(String description) {
        return this.description == description ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withInactiveThreshold(Long inactiveThreshold) {
        return this.inactiveThreshold == inactiveThreshold ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMaxAckPending(Long maxAckPending) {
        return this.maxAckPending == maxAckPending ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMaxDeliverAttemptsPerMessage(Long maxDeliverAttemptsPerMessage) {
        return this.maxDeliverAttemptsPerMessage == maxDeliverAttemptsPerMessage ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withBackoff(List<Long> backoff) {
        return this.backoff == backoff ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withReplayPolicy(String replayPolicy) {
        return this.replayPolicy == replayPolicy ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withReplicasNumber(Integer replicasNumber) {
        return this.replicasNumber == replicasNumber ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMemoryStorage(Boolean memoryStorage) {
        return this.memoryStorage == memoryStorage ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withSampleFrequency(String sampleFrequency) {
        return this.sampleFrequency == sampleFrequency ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMetadata(Map<String, String> metadata) {
        return this.metadata == metadata ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMaxPullWaiting(Long maxPullWaiting) {
        return this.maxPullWaiting == maxPullWaiting ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMaxExpires(Long maxExpires) {
        return this.maxExpires == maxExpires ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, maxExpires, this.maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMaxBatch(Integer maxBatch) {
        return this.maxBatch == maxBatch ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, maxBatch, this.maxBytes);
    }

    public MQConsumerConfigDTO withMaxBytes(Long maxBytes) {
        return this.maxBytes == maxBytes ? this : new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, maxBytes);
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class MQConsumerConfigDTOBuilder {
        private String durableName;
        private String filterSubject;
        private String ackPolicy;
        private Long ackWait;
        private String deliverPolicy;
        private Long optionStartSequence;
        private ZonedDateTime optionStartTime;
        private String description;
        private Long inactiveThreshold;
        private Long maxAckPending;
        private Long maxDeliverAttemptsPerMessage;
        private List<Long> backoff;
        private String replayPolicy;
        private Integer replicasNumber;
        private Boolean memoryStorage;
        private String sampleFrequency;
        private Map<String, String> metadata;
        private Long maxPullWaiting;
        private Long maxExpires;
        private Integer maxBatch;
        private Long maxBytes;

        MQConsumerConfigDTOBuilder() {
        }

        @JsonProperty(value="durable")
        public MQConsumerConfigDTOBuilder durableName(String durableName) {
            this.durableName = durableName;
            return this;
        }

        @JsonProperty(value="filter_subject")
        public MQConsumerConfigDTOBuilder filterSubject(String filterSubject) {
            this.filterSubject = filterSubject;
            return this;
        }

        @JsonProperty(value="ack_policy")
        public MQConsumerConfigDTOBuilder ackPolicy(String ackPolicy) {
            this.ackPolicy = ackPolicy;
            return this;
        }

        @JsonProperty(value="ack_wait")
        public MQConsumerConfigDTOBuilder ackWait(Long ackWait) {
            this.ackWait = ackWait;
            return this;
        }

        @JsonProperty(value="deliver_policy")
        public MQConsumerConfigDTOBuilder deliverPolicy(String deliverPolicy) {
            this.deliverPolicy = deliverPolicy;
            return this;
        }

        @JsonProperty(value="opt_start_seq")
        public MQConsumerConfigDTOBuilder optionStartSequence(Long optionStartSequence) {
            this.optionStartSequence = optionStartSequence;
            return this;
        }

        @JsonProperty(value="opt_start_time")
        public MQConsumerConfigDTOBuilder optionStartTime(ZonedDateTime optionStartTime) {
            this.optionStartTime = optionStartTime;
            return this;
        }

        @JsonProperty(value="description")
        public MQConsumerConfigDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty(value="inactive_threshold")
        public MQConsumerConfigDTOBuilder inactiveThreshold(Long inactiveThreshold) {
            this.inactiveThreshold = inactiveThreshold;
            return this;
        }

        @JsonProperty(value="max_ack_pending")
        public MQConsumerConfigDTOBuilder maxAckPending(Long maxAckPending) {
            this.maxAckPending = maxAckPending;
            return this;
        }

        @JsonProperty(value="max_deliver")
        public MQConsumerConfigDTOBuilder maxDeliverAttemptsPerMessage(Long maxDeliverAttemptsPerMessage) {
            this.maxDeliverAttemptsPerMessage = maxDeliverAttemptsPerMessage;
            return this;
        }

        @JsonProperty(value="backoff")
        public MQConsumerConfigDTOBuilder backoff(List<Long> backoff) {
            this.backoff = backoff;
            return this;
        }

        @JsonProperty(value="replay_policy")
        public MQConsumerConfigDTOBuilder replayPolicy(String replayPolicy) {
            this.replayPolicy = replayPolicy;
            return this;
        }

        @JsonProperty(value="num_replicas")
        public MQConsumerConfigDTOBuilder replicasNumber(Integer replicasNumber) {
            this.replicasNumber = replicasNumber;
            return this;
        }

        @JsonProperty(value="mem_storage")
        public MQConsumerConfigDTOBuilder memoryStorage(Boolean memoryStorage) {
            this.memoryStorage = memoryStorage;
            return this;
        }

        @JsonProperty(value="sample_freq")
        public MQConsumerConfigDTOBuilder sampleFrequency(String sampleFrequency) {
            this.sampleFrequency = sampleFrequency;
            return this;
        }

        @JsonProperty(value="metadata")
        public MQConsumerConfigDTOBuilder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        @JsonProperty(value="max_waiting")
        public MQConsumerConfigDTOBuilder maxPullWaiting(Long maxPullWaiting) {
            this.maxPullWaiting = maxPullWaiting;
            return this;
        }

        @JsonProperty(value="max_expires")
        public MQConsumerConfigDTOBuilder maxExpires(Long maxExpires) {
            this.maxExpires = maxExpires;
            return this;
        }

        @JsonProperty(value="max_batch")
        public MQConsumerConfigDTOBuilder maxBatch(Integer maxBatch) {
            this.maxBatch = maxBatch;
            return this;
        }

        @JsonProperty(value="max_bytes")
        public MQConsumerConfigDTOBuilder maxBytes(Long maxBytes) {
            this.maxBytes = maxBytes;
            return this;
        }

        public MQConsumerConfigDTO build() {
            return new MQConsumerConfigDTO(this.durableName, this.filterSubject, this.ackPolicy, this.ackWait, this.deliverPolicy, this.optionStartSequence, this.optionStartTime, this.description, this.inactiveThreshold, this.maxAckPending, this.maxDeliverAttemptsPerMessage, this.backoff, this.replayPolicy, this.replicasNumber, this.memoryStorage, this.sampleFrequency, this.metadata, this.maxPullWaiting, this.maxExpires, this.maxBatch, this.maxBytes);
        }

        public String toString() {
            return "MQConsumerConfigDTO.MQConsumerConfigDTOBuilder(durableName=" + this.durableName + ", filterSubject=" + this.filterSubject + ", ackPolicy=" + this.ackPolicy + ", ackWait=" + this.ackWait + ", deliverPolicy=" + this.deliverPolicy + ", optionStartSequence=" + this.optionStartSequence + ", optionStartTime=" + String.valueOf(this.optionStartTime) + ", description=" + this.description + ", inactiveThreshold=" + this.inactiveThreshold + ", maxAckPending=" + this.maxAckPending + ", maxDeliverAttemptsPerMessage=" + this.maxDeliverAttemptsPerMessage + ", backoff=" + String.valueOf(this.backoff) + ", replayPolicy=" + this.replayPolicy + ", replicasNumber=" + this.replicasNumber + ", memoryStorage=" + this.memoryStorage + ", sampleFrequency=" + this.sampleFrequency + ", metadata=" + String.valueOf(this.metadata) + ", maxPullWaiting=" + this.maxPullWaiting + ", maxExpires=" + this.maxExpires + ", maxBatch=" + this.maxBatch + ", maxBytes=" + this.maxBytes + ")";
        }
    }
}

