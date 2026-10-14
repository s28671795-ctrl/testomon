/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.system.DiskSpaceInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;

public class CombinedDiskSpaceInfoDTO {
    @JsonProperty(value="system")
    @JSONField(name="system")
    @Schema(name="system", description="System disk space information")
    private DiskSpaceInfoDTO system;
    @JsonProperty(value="storage")
    @JSONField(name="storage")
    @Schema(name="storage", description="Storage disk space information")
    private DiskSpaceInfoDTO storage;

    public DiskSpaceInfoDTO getSystem() {
        return this.system;
    }

    public DiskSpaceInfoDTO getStorage() {
        return this.storage;
    }

    @JsonProperty(value="system")
    public void setSystem(DiskSpaceInfoDTO system) {
        this.system = system;
    }

    @JsonProperty(value="storage")
    public void setStorage(DiskSpaceInfoDTO storage) {
        this.storage = storage;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CombinedDiskSpaceInfoDTO)) {
            return false;
        }
        CombinedDiskSpaceInfoDTO other = (CombinedDiskSpaceInfoDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        DiskSpaceInfoDTO this$system = this.getSystem();
        DiskSpaceInfoDTO other$system = other.getSystem();
        if (this$system == null ? other$system != null : !((Object)this$system).equals(other$system)) {
            return false;
        }
        DiskSpaceInfoDTO this$storage = this.getStorage();
        DiskSpaceInfoDTO other$storage = other.getStorage();
        return !(this$storage == null ? other$storage != null : !((Object)this$storage).equals(other$storage));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CombinedDiskSpaceInfoDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        DiskSpaceInfoDTO $system = this.getSystem();
        result = result * 59 + ($system == null ? 43 : ((Object)$system).hashCode());
        DiskSpaceInfoDTO $storage = this.getStorage();
        result = result * 59 + ($storage == null ? 43 : ((Object)$storage).hashCode());
        return result;
    }

    public String toString() {
        return "CombinedDiskSpaceInfoDTO(system=" + String.valueOf(this.getSystem()) + ", storage=" + String.valueOf(this.getStorage()) + ")";
    }

    public CombinedDiskSpaceInfoDTO(DiskSpaceInfoDTO system, DiskSpaceInfoDTO storage) {
        this.system = system;
        this.storage = storage;
    }

    public CombinedDiskSpaceInfoDTO() {
    }
}

