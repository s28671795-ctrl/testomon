/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.traffic.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.traffic.TrafficType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="TrafficCountDTO", description="Traffic count class structure")
public final class TrafficCountDTO {
    @JSONField(name="line_id")
    @Schema(name="line_id", description="Line ID", implementation=Integer.class, example="1")
    private final int lineId;
    @JSONField(name="type")
    @Schema(name="type", description="<div><p>Object type allowed in analytics.</p><p>Valid values are:</p><ul><li>0 - person<li>1 - bicycle<li>2 - car<li>3 - motorbike<li>4 - bus<li>5 - truck</ul></div>", implementation=TrafficType.class, example="5")
    private final TrafficType type;
    @JSONField(name="direction")
    @Schema(name="direction", description="Line direction: 0,1,2", implementation=Integer.class, example="1")
    private final int direction;
    @JSONField(name="box")
    @Schema(name="box", description="Bounding box of event", type="object", example="[0.592708, 0.0083333, 0.6510416, 0.0388888]")
    private final double[] box;
    @JSONField(name="frame_image")
    @Schema(name="frame_image", description="Frame image", implementation=String.class, example="frameImage.jpg")
    private final String frameImage;
    @JSONField(name="thumbnail_image")
    @Schema(name="thumbnail_image", description="Thumbnail image", implementation=String.class, example="thumbnailImage.jpg")
    private final String objectImage;
    @JSONField(name="count")
    @Schema(name="count", description="Total number of lines", implementation=Long.class, example="100")
    private final int count;
    @JSONField(name="confidence")
    @Schema(name="confidence", description="Confidence level of the detection", example="0.95")
    private final Float confidence;

    public TrafficCountDTO(int lineId, TrafficType type, int direction, double[] box, String frameImage, String objectImage, int count, Float confidence) {
        this.lineId = lineId;
        this.type = type;
        this.direction = direction;
        this.box = box;
        this.frameImage = frameImage;
        this.objectImage = objectImage;
        this.count = count;
        this.confidence = confidence;
    }

    public int getLineId() {
        return this.lineId;
    }

    public TrafficType getType() {
        return this.type;
    }

    public int getDirection() {
        return this.direction;
    }

    public double[] getBox() {
        return this.box;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public String getObjectImage() {
        return this.objectImage;
    }

    public int getCount() {
        return this.count;
    }

    public Float getConfidence() {
        return this.confidence;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrafficCountDTO)) {
            return false;
        }
        TrafficCountDTO other = (TrafficCountDTO)o;
        if (this.getLineId() != other.getLineId()) {
            return false;
        }
        if (this.getDirection() != other.getDirection()) {
            return false;
        }
        if (this.getCount() != other.getCount()) {
            return false;
        }
        Float this$confidence = this.getConfidence();
        Float other$confidence = other.getConfidence();
        if (this$confidence == null ? other$confidence != null : !((Object)this$confidence).equals(other$confidence)) {
            return false;
        }
        TrafficType this$type = this.getType();
        TrafficType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        String this$objectImage = this.getObjectImage();
        String other$objectImage = other.getObjectImage();
        return !(this$objectImage == null ? other$objectImage != null : !this$objectImage.equals(other$objectImage));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getLineId();
        result = result * 59 + this.getDirection();
        result = result * 59 + this.getCount();
        Float $confidence = this.getConfidence();
        result = result * 59 + ($confidence == null ? 43 : ((Object)$confidence).hashCode());
        TrafficType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $objectImage = this.getObjectImage();
        result = result * 59 + ($objectImage == null ? 43 : $objectImage.hashCode());
        return result;
    }

    public String toString() {
        return "TrafficCountDTO(lineId=" + this.getLineId() + ", type=" + String.valueOf((Object)this.getType()) + ", direction=" + this.getDirection() + ", box=" + Arrays.toString(this.getBox()) + ", frameImage=" + this.getFrameImage() + ", objectImage=" + this.getObjectImage() + ", count=" + this.getCount() + ", confidence=" + this.getConfidence() + ")";
    }
}

