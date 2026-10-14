/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.railroad.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
public final class RailroadNumberDetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    @Schema(name="va_id", description="Video analytics ID in DB", implementation=Integer.class, example="1")
    private final int vaId;
    @JSONField(name="frame_image")
    @Schema(name="frame_image", description="Frame image", implementation=String.class, example="plates/35/2022/7/17/14/AB3709CK_1658058444008.jpg")
    private final String frameImage;
    @JSONField(name="plate_image")
    @Schema(name="plate_image", description="License plate image", implementation=String.class, example="plates/35/2022/7/17/14/AB3709CK_1658058444008_cropped.jpg")
    private final String plateImage;
    @JSONField(name="number")
    @Schema(name="number", description="License plate number", implementation=String.class, example="AB1234AA")
    private final String number;
    @JSONField(name="average_character_height")
    @Schema(name="average_character_height", description="Average character height", implementation=Integer.class, example="70")
    private final int averageCharacterHeight;
    @JSONField(name="iso_code")
    @Schema(name="iso_code", description="Container ISO code", implementation=String.class, example="45G1")
    private final String isoCode;
    @JSONField(name="box")
    @Schema(name="box", description="License plate bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="zone")
    private final Zone zone;
    @JSONField(name="timestamp")
    private final Instant timestamp;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public RailroadNumberDetectionDTO(int vaId, String frameImage, String plateImage, String number, int averageCharacterHeight, String isoCode, double[] box, Zone zone, Instant timestamp) {
        this.vaId = vaId;
        this.frameImage = frameImage;
        this.plateImage = plateImage;
        this.number = number;
        this.averageCharacterHeight = averageCharacterHeight;
        this.isoCode = isoCode;
        this.box = box;
        this.zone = zone;
        this.timestamp = timestamp;
    }

    public int getVaId() {
        return this.vaId;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public String getPlateImage() {
        return this.plateImage;
    }

    public String getNumber() {
        return this.number;
    }

    public int getAverageCharacterHeight() {
        return this.averageCharacterHeight;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public double[] getBox() {
        return this.box;
    }

    public Zone getZone() {
        return this.zone;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RailroadNumberDetectionDTO)) {
            return false;
        }
        RailroadNumberDetectionDTO other = (RailroadNumberDetectionDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (this.getAverageCharacterHeight() != other.getAverageCharacterHeight()) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        String this$plateImage = this.getPlateImage();
        String other$plateImage = other.getPlateImage();
        if (this$plateImage == null ? other$plateImage != null : !this$plateImage.equals(other$plateImage)) {
            return false;
        }
        String this$number = this.getNumber();
        String other$number = other.getNumber();
        if (this$number == null ? other$number != null : !this$number.equals(other$number)) {
            return false;
        }
        String this$isoCode = this.getIsoCode();
        String other$isoCode = other.getIsoCode();
        if (this$isoCode == null ? other$isoCode != null : !this$isoCode.equals(other$isoCode)) {
            return false;
        }
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        if (this$zone == null ? other$zone != null : !this$zone.equals(other$zone)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        result = result * 59 + this.getAverageCharacterHeight();
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $plateImage = this.getPlateImage();
        result = result * 59 + ($plateImage == null ? 43 : $plateImage.hashCode());
        String $number = this.getNumber();
        result = result * 59 + ($number == null ? 43 : $number.hashCode());
        String $isoCode = this.getIsoCode();
        result = result * 59 + ($isoCode == null ? 43 : $isoCode.hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "RailroadNumberDetectionDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", plateImage=" + this.getPlateImage() + ", number=" + this.getNumber() + ", averageCharacterHeight=" + this.getAverageCharacterHeight() + ", isoCode=" + this.getIsoCode() + ", box=" + Arrays.toString(this.getBox()) + ", zone=" + String.valueOf(this.getZone()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }
}

