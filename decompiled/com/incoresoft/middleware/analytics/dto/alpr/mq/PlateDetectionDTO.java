/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.alpr.Color;
import com.incoresoft.middleware.analytics.models.alpr.VehicleType;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
public final class PlateDetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    @JsonProperty(value="va_id")
    private final int vaId;
    @JSONField(name="vehicle_type")
    @JsonProperty(value="vehicle_type")
    @Schema(name="vehicle_type", description="Vehicle type", example="CAR", implementation=VehicleType.class)
    private final VehicleType vehicleType;
    @JSONField(name="color")
    @JsonProperty(value="color")
    @Schema(name="color", description="Vehicle color", example="WHITE", implementation=Color.class)
    private final Color color;
    @JSONField(name="pattern")
    @JsonProperty(value="pattern")
    @Schema(name="pattern", description="", implementation=String.class, example="UA-1")
    private final String pattern;
    @JSONField(name="country")
    @JsonProperty(value="country")
    @Schema(name="country", description="", implementation=String.class, example="UA")
    private final String country;
    @JSONField(name="state")
    @JsonProperty(value="state")
    @Schema(name="state", description="", implementation=String.class, example="VN")
    private final String state;
    @JSONField(name="direction")
    @JsonProperty(value="direction")
    @Schema(name="direction", description="Direction angle", implementation=Integer.class, example="270")
    private final int direction;
    @JSONField(name="frame_image")
    @JsonProperty(value="frame_image")
    @Schema(name="frame_image", description="Frame image", implementation=String.class, example="frame_image.jpg")
    private final String frameImage;
    @JSONField(name="make_model_id")
    @JsonProperty(value="make_model_id")
    @Schema(name="make_model_id", description="Make and model ID", implementation=Integer.class, example="100234")
    private final int makeModelId;
    @JSONField(name="number")
    @JsonProperty(value="number")
    @Schema(name="number", description="Latin license plate number", implementation=String.class, example="AA1234AA")
    private final String number;
    @JSONField(name="arabic_number")
    @JsonProperty(value="arabic_number")
    @Schema(name="arabic_number", description="Arabic number in unicode", implementation=String.class, example="%u0668%u0660%u0669%u0665%u0639%u0627%u0627")
    private final String arabicNumber;
    @JSONField(name="adr")
    @JsonProperty(value="adr")
    @Schema(name="adr", description="ADR plate number", implementation=String.class, example="331203")
    private final String adr;
    @JSONField(name="timestamp")
    @JsonProperty(value="timestamp")
    @Schema(name="timestamp", description="Timestamp", implementation=Long.class, example="1691687997000")
    @JsonFormat(shape=JsonFormat.Shape.NUMBER)
    private final Instant timestamp;
    @JSONField(name="box")
    @JsonProperty(value="box")
    @Schema(name="box", description="License plate bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="plate_image")
    @JsonProperty(value="plate_image")
    @Schema(name="plate_image", description="License plate image", implementation=String.class, example="plates/35/2022/7/17/14/AB3709CK_1658058444008_cropped.jpg")
    private final String plateImage;
    @JSONField(name="uuid")
    @JsonProperty(value="uuid")
    @Schema(name="uuid", description="License plate UUID", implementation=String.class, example="1dee2937-f160-4d00-bc82-61454dd99997")
    private final String uuid;
    @JSONField(name="zone")
    @JsonProperty(value="zone")
    private final Zone zone;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public PlateDetectionDTO(int vaId, VehicleType vehicleType, Color color, String pattern, String country, String state, int direction, String frameImage, int makeModelId, String number, String arabicNumber, String adr, Instant timestamp, double[] box, String plateImage, String uuid, Zone zone) {
        this.vaId = vaId;
        this.vehicleType = vehicleType;
        this.color = color;
        this.pattern = pattern;
        this.country = country;
        this.state = state;
        this.direction = direction;
        this.frameImage = frameImage;
        this.makeModelId = makeModelId;
        this.number = number;
        this.arabicNumber = arabicNumber;
        this.adr = adr;
        this.timestamp = timestamp;
        this.box = box;
        this.plateImage = plateImage;
        this.uuid = uuid;
        this.zone = zone;
    }

    public int getVaId() {
        return this.vaId;
    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public Color getColor() {
        return this.color;
    }

    public String getPattern() {
        return this.pattern;
    }

    public String getCountry() {
        return this.country;
    }

    public String getState() {
        return this.state;
    }

    public int getDirection() {
        return this.direction;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public int getMakeModelId() {
        return this.makeModelId;
    }

    public String getNumber() {
        return this.number;
    }

    public String getArabicNumber() {
        return this.arabicNumber;
    }

    public String getAdr() {
        return this.adr;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public double[] getBox() {
        return this.box;
    }

    public String getPlateImage() {
        return this.plateImage;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Zone getZone() {
        return this.zone;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlateDetectionDTO)) {
            return false;
        }
        PlateDetectionDTO other = (PlateDetectionDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (this.getDirection() != other.getDirection()) {
            return false;
        }
        if (this.getMakeModelId() != other.getMakeModelId()) {
            return false;
        }
        VehicleType this$vehicleType = this.getVehicleType();
        VehicleType other$vehicleType = other.getVehicleType();
        if (this$vehicleType == null ? other$vehicleType != null : !((Object)((Object)this$vehicleType)).equals((Object)other$vehicleType)) {
            return false;
        }
        Color this$color = this.getColor();
        Color other$color = other.getColor();
        if (this$color == null ? other$color != null : !((Object)((Object)this$color)).equals((Object)other$color)) {
            return false;
        }
        String this$pattern = this.getPattern();
        String other$pattern = other.getPattern();
        if (this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern)) {
            return false;
        }
        String this$country = this.getCountry();
        String other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) {
            return false;
        }
        String this$state = this.getState();
        String other$state = other.getState();
        if (this$state == null ? other$state != null : !this$state.equals(other$state)) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        String this$number = this.getNumber();
        String other$number = other.getNumber();
        if (this$number == null ? other$number != null : !this$number.equals(other$number)) {
            return false;
        }
        String this$arabicNumber = this.getArabicNumber();
        String other$arabicNumber = other.getArabicNumber();
        if (this$arabicNumber == null ? other$arabicNumber != null : !this$arabicNumber.equals(other$arabicNumber)) {
            return false;
        }
        String this$adr = this.getAdr();
        String other$adr = other.getAdr();
        if (this$adr == null ? other$adr != null : !this$adr.equals(other$adr)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        String this$plateImage = this.getPlateImage();
        String other$plateImage = other.getPlateImage();
        if (this$plateImage == null ? other$plateImage != null : !this$plateImage.equals(other$plateImage)) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        return !(this$zone == null ? other$zone != null : !this$zone.equals(other$zone));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        result = result * 59 + this.getDirection();
        result = result * 59 + this.getMakeModelId();
        VehicleType $vehicleType = this.getVehicleType();
        result = result * 59 + ($vehicleType == null ? 43 : ((Object)((Object)$vehicleType)).hashCode());
        Color $color = this.getColor();
        result = result * 59 + ($color == null ? 43 : ((Object)((Object)$color)).hashCode());
        String $pattern = this.getPattern();
        result = result * 59 + ($pattern == null ? 43 : $pattern.hashCode());
        String $country = this.getCountry();
        result = result * 59 + ($country == null ? 43 : $country.hashCode());
        String $state = this.getState();
        result = result * 59 + ($state == null ? 43 : $state.hashCode());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $number = this.getNumber();
        result = result * 59 + ($number == null ? 43 : $number.hashCode());
        String $arabicNumber = this.getArabicNumber();
        result = result * 59 + ($arabicNumber == null ? 43 : $arabicNumber.hashCode());
        String $adr = this.getAdr();
        result = result * 59 + ($adr == null ? 43 : $adr.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        String $plateImage = this.getPlateImage();
        result = result * 59 + ($plateImage == null ? 43 : $plateImage.hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        return result;
    }

    public String toString() {
        return "PlateDetectionDTO(vaId=" + this.getVaId() + ", vehicleType=" + String.valueOf((Object)this.getVehicleType()) + ", color=" + String.valueOf((Object)this.getColor()) + ", pattern=" + this.getPattern() + ", country=" + this.getCountry() + ", state=" + this.getState() + ", direction=" + this.getDirection() + ", frameImage=" + this.getFrameImage() + ", makeModelId=" + this.getMakeModelId() + ", number=" + this.getNumber() + ", arabicNumber=" + this.getArabicNumber() + ", adr=" + this.getAdr() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", box=" + Arrays.toString(this.getBox()) + ", plateImage=" + this.getPlateImage() + ", uuid=" + this.getUuid() + ", zone=" + String.valueOf(this.getZone()) + ")";
    }
}

