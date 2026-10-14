/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

import java.util.Set;

public final class StreamHasAnalyticsException
extends RuntimeException {
    private final Set<Integer> analyticsIds;

    public Set<Integer> getAnalyticsIds() {
        return this.analyticsIds;
    }

    @Override
    public String toString() {
        return "StreamHasAnalyticsException(analyticsIds=" + String.valueOf(this.getAnalyticsIds()) + ")";
    }

    public StreamHasAnalyticsException(Set<Integer> analyticsIds) {
        this.analyticsIds = analyticsIds;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamHasAnalyticsException)) {
            return false;
        }
        StreamHasAnalyticsException other = (StreamHasAnalyticsException)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Set<Integer> this$analyticsIds = this.getAnalyticsIds();
        Set<Integer> other$analyticsIds = other.getAnalyticsIds();
        return !(this$analyticsIds == null ? other$analyticsIds != null : !((Object)this$analyticsIds).equals(other$analyticsIds));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamHasAnalyticsException;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Set<Integer> $analyticsIds = this.getAnalyticsIds();
        result = result * 59 + ($analyticsIds == null ? 43 : ((Object)$analyticsIds).hashCode());
        return result;
    }
}

