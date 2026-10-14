/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.personreid.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.personreid.mq.PersonDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

@AnalyticsToken(value="detection")
public final class SmartTrackingDetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    private final int vaId;
    @JSONField(name="frame_image")
    @Schema(name="frame_image", description="Frame image", implementation=String.class, example="plates/35/2022/7/17/14/1658058444008.jpg")
    private final String frameImage;
    @JSONField(name="timestamp")
    private final Instant timestamp;
    @JSONField(name="persons")
    private final List<PersonDTO> persons;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public SmartTrackingDetectionDTO(int vaId, String frameImage, Instant timestamp, List<PersonDTO> persons) {
        this.vaId = vaId;
        this.frameImage = frameImage;
        this.timestamp = timestamp;
        this.persons = persons;
    }

    public int getVaId() {
        return this.vaId;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public List<PersonDTO> getPersons() {
        return this.persons;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SmartTrackingDetectionDTO)) {
            return false;
        }
        SmartTrackingDetectionDTO other = (SmartTrackingDetectionDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        List<PersonDTO> this$persons = this.getPersons();
        List<PersonDTO> other$persons = other.getPersons();
        return !(this$persons == null ? other$persons != null : !((Object)this$persons).equals(other$persons));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        List<PersonDTO> $persons = this.getPersons();
        result = result * 59 + ($persons == null ? 43 : ((Object)$persons).hashCode());
        return result;
    }

    public String toString() {
        return "SmartTrackingDetectionDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", persons=" + String.valueOf(this.getPersons()) + ")";
    }
}

