/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.models;

import com.incoresoft.vezha.analytics.servers.dto.RunningServiceDTO;

public final class RunningService {
    private final String type;
    private final boolean isReady;

    public RunningService(RunningServiceDTO dto) {
        this.type = dto.getType();
        this.isReady = dto.isReady();
    }

    public String getType() {
        return this.type;
    }

    public boolean isReady() {
        return this.isReady;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RunningService)) {
            return false;
        }
        RunningService other = (RunningService)o;
        if (this.isReady() != other.isReady()) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        return !(this$type == null ? other$type != null : !this$type.equals(other$type));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isReady() ? 79 : 97);
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "RunningService(type=" + this.getType() + ", isReady=" + this.isReady() + ")";
    }
}

