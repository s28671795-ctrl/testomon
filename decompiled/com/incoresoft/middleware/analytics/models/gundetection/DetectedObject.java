/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.gundetection;

import com.incoresoft.middleware.analytics.dto.gundetection.objects.DetectedObjectDTO;
import com.incoresoft.middleware.analytics.models.gundetection.WeaponType;
import java.util.Arrays;

public class DetectedObject {
    private WeaponType type;
    private double confidence;
    private double[] box;

    public DetectedObject(DetectedObjectDTO dto) {
        this.type = dto.getType();
        this.confidence = dto.getConfidence();
        this.box = dto.getBox();
    }

    public WeaponType getType() {
        return this.type;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public double[] getBox() {
        return this.box;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void setBox(double[] box) {
        this.box = box;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectedObject)) {
            return false;
        }
        DetectedObject other = (DetectedObject)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getConfidence(), other.getConfidence()) != 0) {
            return false;
        }
        WeaponType this$type = this.getType();
        WeaponType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        return Arrays.equals(this.getBox(), other.getBox());
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetectedObject;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $confidence = Double.doubleToLongBits(this.getConfidence());
        result = result * 59 + (int)($confidence >>> 32 ^ $confidence);
        WeaponType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        return result;
    }

    public String toString() {
        return "DetectedObject(type=" + String.valueOf((Object)this.getType()) + ", confidence=" + this.getConfidence() + ", box=" + Arrays.toString(this.getBox()) + ")";
    }

    public DetectedObject(WeaponType type, double confidence, double[] box) {
        this.type = type;
        this.confidence = confidence;
        this.box = box;
    }
}

