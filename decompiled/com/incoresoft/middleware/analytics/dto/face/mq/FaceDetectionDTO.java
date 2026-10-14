/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.face.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.face.gender.Gender;
import com.incoresoft.middleware.analytics.models.face.race.Race;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
public final class FaceDetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="uuid")
    @JsonProperty(value="uuid")
    @Schema(name="uuid", description="UUID", implementation=String.class, example="62bcc115-59f6-40e8-bed2-2612bc01dcd5")
    private final String uuid;
    @JSONField(name="va_id")
    @JsonProperty(value="va_id")
    @Schema(name="va_id", description="Video analytics ID", implementation=Integer.class, example="1")
    private final int vaId;
    @JSONField(name="box")
    @JsonProperty(value="box")
    @Schema(name="box", description="Face bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="encoding")
    @JsonProperty(value="encoding")
    @Schema(name="encoding", description="Face image encoding 512 doubles", type="object", example="[0.6200000047683716, ...{512 items}... ,0.8899999856948853]")
    private final float[] encoding;
    @JSONField(name="timestamp")
    @JsonProperty(value="timestamp")
    @Schema(name="timestamp", description="Timestamp", implementation=Long.class, example="1234567890")
    private final Instant timestamp;
    @JSONField(name="millis_from_start")
    @JsonProperty(value="millis_from_start")
    @Schema(name="millis_from_start", description="Only for files: millis from file's start", implementation=Integer.class, example="0")
    private final Integer millisFromStart;
    @JSONField(name="face_image")
    @JsonProperty(value="face_image")
    @Schema(name="image", description="Face image file name", implementation=String.class, example="faces/35/2022/7/17/14/AB3709CK_1658058444008_cropped.jpg")
    private final String faceImage;
    @JSONField(name="frame_image")
    @JsonProperty(value="frame_image")
    @Schema(name="frame", description="Event frame image file name", implementation=String.class, example="frame_image.jpg")
    private final String frameImage;
    @JSONField(name="age")
    @JsonProperty(value="age")
    @Schema(name="age", description="Person age", implementation=String.class, example="35-40")
    private final String age;
    @JSONField(name="gender")
    @JsonProperty(value="gender")
    @Schema(name="gender", description="Person gender", implementation=Gender.class)
    private final Gender gender;
    @JSONField(name="race")
    @JsonProperty(value="race")
    @Schema(name="race", description="Enable or disable race detection", implementation=Race.class, example="false")
    private final Race race;
    @JSONField(name="spoofed")
    @JsonProperty(value="spoofed")
    @Schema(name="spoofed", description="Is face spoofed(liveness)", implementation=Boolean.class, example="false")
    private final Boolean spoofed;
    @JSONField(name="mask")
    @JsonProperty(value="mask")
    @Schema(name="mask", description="Is face in mask", implementation=Boolean.class, example="true")
    private final Boolean mask;

    @JsonCreator
    public FaceDetectionDTO(@JsonProperty(value="uuid") String uuid, @JsonProperty(value="va_id") int vaId, @JsonProperty(value="box") double[] box, @JsonProperty(value="encoding") float[] encoding, @JsonProperty(value="timestamp") Instant timestamp, @JsonProperty(value="millis_from_start") Integer millisFromStart, @JsonProperty(value="face_image") String faceImage, @JsonProperty(value="frame_image") String frameImage, @JsonProperty(value="age") String age, @JsonProperty(value="gender") Gender gender, @JsonProperty(value="race") Race race, @JsonProperty(value="spoofed") Boolean spoofed, @JsonProperty(value="mask") Boolean mask) {
        this.uuid = uuid;
        this.vaId = vaId;
        this.box = box;
        this.encoding = encoding;
        this.timestamp = timestamp;
        this.millisFromStart = millisFromStart;
        this.faceImage = faceImage;
        this.frameImage = frameImage;
        this.age = age;
        this.gender = gender;
        this.race = race;
        this.spoofed = spoofed;
        this.mask = mask;
    }

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public String getUuid() {
        return this.uuid;
    }

    public int getVaId() {
        return this.vaId;
    }

    public double[] getBox() {
        return this.box;
    }

    public float[] getEncoding() {
        return this.encoding;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public Integer getMillisFromStart() {
        return this.millisFromStart;
    }

    public String getFaceImage() {
        return this.faceImage;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public String getAge() {
        return this.age;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Race getRace() {
        return this.race;
    }

    public Boolean getSpoofed() {
        return this.spoofed;
    }

    public Boolean getMask() {
        return this.mask;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaceDetectionDTO)) {
            return false;
        }
        FaceDetectionDTO other = (FaceDetectionDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        Integer this$millisFromStart = this.getMillisFromStart();
        Integer other$millisFromStart = other.getMillisFromStart();
        if (this$millisFromStart == null ? other$millisFromStart != null : !((Object)this$millisFromStart).equals(other$millisFromStart)) {
            return false;
        }
        Boolean this$spoofed = this.getSpoofed();
        Boolean other$spoofed = other.getSpoofed();
        if (this$spoofed == null ? other$spoofed != null : !((Object)this$spoofed).equals(other$spoofed)) {
            return false;
        }
        Boolean this$mask = this.getMask();
        Boolean other$mask = other.getMask();
        if (this$mask == null ? other$mask != null : !((Object)this$mask).equals(other$mask)) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        if (!Arrays.equals(this.getEncoding(), other.getEncoding())) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        String this$faceImage = this.getFaceImage();
        String other$faceImage = other.getFaceImage();
        if (this$faceImage == null ? other$faceImage != null : !this$faceImage.equals(other$faceImage)) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        String this$age = this.getAge();
        String other$age = other.getAge();
        if (this$age == null ? other$age != null : !this$age.equals(other$age)) {
            return false;
        }
        Gender this$gender = this.getGender();
        Gender other$gender = other.getGender();
        if (this$gender == null ? other$gender != null : !((Object)((Object)this$gender)).equals((Object)other$gender)) {
            return false;
        }
        Race this$race = this.getRace();
        Race other$race = other.getRace();
        return !(this$race == null ? other$race != null : !((Object)((Object)this$race)).equals((Object)other$race));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        Integer $millisFromStart = this.getMillisFromStart();
        result = result * 59 + ($millisFromStart == null ? 43 : ((Object)$millisFromStart).hashCode());
        Boolean $spoofed = this.getSpoofed();
        result = result * 59 + ($spoofed == null ? 43 : ((Object)$spoofed).hashCode());
        Boolean $mask = this.getMask();
        result = result * 59 + ($mask == null ? 43 : ((Object)$mask).hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        result = result * 59 + Arrays.hashCode(this.getEncoding());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        String $faceImage = this.getFaceImage();
        result = result * 59 + ($faceImage == null ? 43 : $faceImage.hashCode());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $age = this.getAge();
        result = result * 59 + ($age == null ? 43 : $age.hashCode());
        Gender $gender = this.getGender();
        result = result * 59 + ($gender == null ? 43 : ((Object)((Object)$gender)).hashCode());
        Race $race = this.getRace();
        result = result * 59 + ($race == null ? 43 : ((Object)((Object)$race)).hashCode());
        return result;
    }

    public String toString() {
        return "FaceDetectionDTO(uuid=" + this.getUuid() + ", vaId=" + this.getVaId() + ", box=" + Arrays.toString(this.getBox()) + ", encoding=" + Arrays.toString(this.getEncoding()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", millisFromStart=" + this.getMillisFromStart() + ", faceImage=" + this.getFaceImage() + ", frameImage=" + this.getFrameImage() + ", age=" + this.getAge() + ", gender=" + String.valueOf((Object)this.getGender()) + ", race=" + String.valueOf((Object)this.getRace()) + ", spoofed=" + this.getSpoofed() + ", mask=" + this.getMask() + ")";
    }
}

