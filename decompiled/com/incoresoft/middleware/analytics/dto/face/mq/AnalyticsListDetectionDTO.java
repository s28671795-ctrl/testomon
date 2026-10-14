/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.face.mq;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;

public final class AnalyticsListDetectionDTO {
    @JSONField(name="uuid")
    @Schema(name="uuid", description="UUID", implementation=String.class, example="62bcc115-59f6-40e8-bed2-2612bc01dcd5")
    private final String uuid;
    @JSONField(name="alert_list_name")
    @Schema(name="alert_list_name", description="Alert list name", implementation=String.class, example="Some list name")
    private final String listName;
    @JSONField(name="alert_name")
    @Schema(name="alert_name", description="Alert name", implementation=String.class, example="Some alert name")
    private final String alertName;
    @JSONField(name="alert_list_color")
    @Schema(name="alert_list_color", description="Alert list color", implementation=String.class, example="#ffffff")
    private final String listColor;
    @JSONField(name="confidence_threshold")
    @Schema(name="confidence_threshold", description="Alert confidence", implementation=Integer.class, example="80")
    private final Integer confidenceThreshold;

    public AnalyticsListDetectionDTO(String uuid, String listName, String alertName, String listColor, Integer confidenceThreshold) {
        this.uuid = uuid;
        this.listName = listName;
        this.alertName = alertName;
        this.listColor = listColor;
        this.confidenceThreshold = confidenceThreshold;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getListName() {
        return this.listName;
    }

    public String getAlertName() {
        return this.alertName;
    }

    public String getListColor() {
        return this.listColor;
    }

    public Integer getConfidenceThreshold() {
        return this.confidenceThreshold;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsListDetectionDTO)) {
            return false;
        }
        AnalyticsListDetectionDTO other = (AnalyticsListDetectionDTO)o;
        Integer this$confidenceThreshold = this.getConfidenceThreshold();
        Integer other$confidenceThreshold = other.getConfidenceThreshold();
        if (this$confidenceThreshold == null ? other$confidenceThreshold != null : !((Object)this$confidenceThreshold).equals(other$confidenceThreshold)) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        String this$listName = this.getListName();
        String other$listName = other.getListName();
        if (this$listName == null ? other$listName != null : !this$listName.equals(other$listName)) {
            return false;
        }
        String this$alertName = this.getAlertName();
        String other$alertName = other.getAlertName();
        if (this$alertName == null ? other$alertName != null : !this$alertName.equals(other$alertName)) {
            return false;
        }
        String this$listColor = this.getListColor();
        String other$listColor = other.getListColor();
        return !(this$listColor == null ? other$listColor != null : !this$listColor.equals(other$listColor));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $confidenceThreshold = this.getConfidenceThreshold();
        result = result * 59 + ($confidenceThreshold == null ? 43 : ((Object)$confidenceThreshold).hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        String $listName = this.getListName();
        result = result * 59 + ($listName == null ? 43 : $listName.hashCode());
        String $alertName = this.getAlertName();
        result = result * 59 + ($alertName == null ? 43 : $alertName.hashCode());
        String $listColor = this.getListColor();
        result = result * 59 + ($listColor == null ? 43 : $listColor.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsListDetectionDTO(uuid=" + this.getUuid() + ", listName=" + this.getListName() + ", alertName=" + this.getAlertName() + ", listColor=" + this.getListColor() + ", confidenceThreshold=" + this.getConfidenceThreshold() + ")";
    }
}

