/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.system.disk.DiskSpaceInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record DiskSpaceInfoDTO(@JsonProperty(value="disk_name") @JSONField(name="disk_name") @Schema(name="disk_name", description="Disk name", example="C:") String diskName, @JsonProperty(value="total_space") @JSONField(name="total_space") @Schema(name="total_space", description="Total space on disk", example="1073741824") long totalSpace, @JsonProperty(value="free_space") @JSONField(name="free_space") @Schema(name="free_space", description="Free space on disk", example="1073741824") long freeSpace, @JsonProperty(value="used_space") @JSONField(name="used_space") @Schema(name="used_space", description="Used space on disk", example="1073741824") long usedSpace) {
    public DiskSpaceInfoDTO(DiskSpaceInfo diskSpaceInfo) {
        this(diskSpaceInfo.diskName(), diskSpaceInfo.totalSpace(), diskSpaceInfo.freeSpace(), diskSpaceInfo.usedSpace());
    }
}

