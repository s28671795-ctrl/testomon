/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.models;

import com.incoresoft.middleware.analytics.AnalyticsType;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.models.AnalyticsParamsAction;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public final class AnalyticsStartParams {
    private final int id;
    private final AnalyticsParamsAction action;
    private final String analyticsType;
    private final int streamId;
    private final String sig;
    private final String topic;
    private final int gpuId;
    private final String hash;

    public AnalyticsStartParams(AnalyticsParamsAction action, int id) {
        this.id = id;
        this.action = action;
        this.analyticsType = "";
        this.streamId = 0;
        this.sig = "";
        this.topic = "";
        this.gpuId = 0;
        this.hash = UUID.randomUUID().toString();
    }

    public AnalyticsStartParams(AnalyticsParamsAction action, BaseAnalytics analytics) {
        this.id = analytics.getId();
        this.action = action;
        this.analyticsType = analytics.getModule().getAnalyticsTypes().stream().map(AnalyticsType::getName).collect(Collectors.joining(","));
        this.streamId = analytics.getStreamId();
        this.sig = Optional.ofNullable(analytics.getAnalyticsStatus()).map(AnalyticsStatusImpl::getStartSignatureValue).orElse(null);
        this.topic = analytics.getUUID().toString();
        this.gpuId = Optional.ofNullable(analytics.getAnalyticsStatus()).map(AnalyticsStatusImpl::getLastGpuIdValue).orElse(-1);
        this.hash = UUID.randomUUID().toString();
    }

    public int getId() {
        return this.id;
    }

    public AnalyticsParamsAction getAction() {
        return this.action;
    }

    public String getAnalyticsType() {
        return this.analyticsType;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public String getSig() {
        return this.sig;
    }

    public String getTopic() {
        return this.topic;
    }

    public int getGpuId() {
        return this.gpuId;
    }

    public String getHash() {
        return this.hash;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsStartParams)) {
            return false;
        }
        AnalyticsStartParams other = (AnalyticsStartParams)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getStreamId() != other.getStreamId()) {
            return false;
        }
        if (this.getGpuId() != other.getGpuId()) {
            return false;
        }
        AnalyticsParamsAction this$action = this.getAction();
        AnalyticsParamsAction other$action = other.getAction();
        if (this$action == null ? other$action != null : !((Object)((Object)this$action)).equals((Object)other$action)) {
            return false;
        }
        String this$analyticsType = this.getAnalyticsType();
        String other$analyticsType = other.getAnalyticsType();
        if (this$analyticsType == null ? other$analyticsType != null : !this$analyticsType.equals(other$analyticsType)) {
            return false;
        }
        String this$sig = this.getSig();
        String other$sig = other.getSig();
        if (this$sig == null ? other$sig != null : !this$sig.equals(other$sig)) {
            return false;
        }
        String this$topic = this.getTopic();
        String other$topic = other.getTopic();
        if (this$topic == null ? other$topic != null : !this$topic.equals(other$topic)) {
            return false;
        }
        String this$hash = this.getHash();
        String other$hash = other.getHash();
        return !(this$hash == null ? other$hash != null : !this$hash.equals(other$hash));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getStreamId();
        result = result * 59 + this.getGpuId();
        AnalyticsParamsAction $action = this.getAction();
        result = result * 59 + ($action == null ? 43 : ((Object)((Object)$action)).hashCode());
        String $analyticsType = this.getAnalyticsType();
        result = result * 59 + ($analyticsType == null ? 43 : $analyticsType.hashCode());
        String $sig = this.getSig();
        result = result * 59 + ($sig == null ? 43 : $sig.hashCode());
        String $topic = this.getTopic();
        result = result * 59 + ($topic == null ? 43 : $topic.hashCode());
        String $hash = this.getHash();
        result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsStartParams(id=" + this.getId() + ", action=" + String.valueOf((Object)this.getAction()) + ", analyticsType=" + this.getAnalyticsType() + ", streamId=" + this.getStreamId() + ", sig=" + this.getSig() + ", topic=" + this.getTopic() + ", gpuId=" + this.getGpuId() + ", hash=" + this.getHash() + ")";
    }

    public AnalyticsStartParams(int id, AnalyticsParamsAction action, String analyticsType, int streamId, String sig, String topic, int gpuId, String hash) {
        this.id = id;
        this.action = action;
        this.analyticsType = analyticsType;
        this.streamId = streamId;
        this.sig = sig;
        this.topic = topic;
        this.gpuId = gpuId;
        this.hash = hash;
    }
}

