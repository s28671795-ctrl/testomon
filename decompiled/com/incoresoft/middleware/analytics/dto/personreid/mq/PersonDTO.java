/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.personreid.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.attributes.PersonAttributesDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class PersonDTO {
    @JSONField(name="encoding")
    @Schema(name="encoding", description="Object encoding", type="object", example="[0.6200000047683716, ... {512 doubles} ..., 0.8899999856948853]")
    private final double[] encoding;
    @JSONField(name="object_image")
    @Schema(name="person_image", description="Person image file name", implementation=String.class, example="person_image.jpg")
    private final String objectImage;
    @JSONField(name="box")
    @Schema(name="box", description="Object bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="attributes")
    @Schema(name="attributes", description="Person attributes", implementation=PersonAttributesDTO.class)
    private final PersonAttributesDTO attributes;

    public List<Float> getEncodingList() {
        return Arrays.stream(this.encoding).boxed().map(Double::floatValue).collect(Collectors.toList());
    }

    public PersonDTO(double[] encoding, String objectImage, double[] box, PersonAttributesDTO attributes) {
        this.encoding = encoding;
        this.objectImage = objectImage;
        this.box = box;
        this.attributes = attributes;
    }

    public double[] getEncoding() {
        return this.encoding;
    }

    public String getObjectImage() {
        return this.objectImage;
    }

    public double[] getBox() {
        return this.box;
    }

    public PersonAttributesDTO getAttributes() {
        return this.attributes;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }
        PersonDTO other = (PersonDTO)o;
        if (!Arrays.equals(this.getEncoding(), other.getEncoding())) {
            return false;
        }
        String this$objectImage = this.getObjectImage();
        String other$objectImage = other.getObjectImage();
        if (this$objectImage == null ? other$objectImage != null : !this$objectImage.equals(other$objectImage)) {
            return false;
        }
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        PersonAttributesDTO this$attributes = this.getAttributes();
        PersonAttributesDTO other$attributes = other.getAttributes();
        return !(this$attributes == null ? other$attributes != null : !((Object)this$attributes).equals(other$attributes));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Arrays.hashCode(this.getEncoding());
        String $objectImage = this.getObjectImage();
        result = result * 59 + ($objectImage == null ? 43 : $objectImage.hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        PersonAttributesDTO $attributes = this.getAttributes();
        result = result * 59 + ($attributes == null ? 43 : ((Object)$attributes).hashCode());
        return result;
    }

    public String toString() {
        return "PersonDTO(encoding=" + Arrays.toString(this.getEncoding()) + ", objectImage=" + this.getObjectImage() + ", box=" + Arrays.toString(this.getBox()) + ", attributes=" + String.valueOf(this.getAttributes()) + ")";
    }
}

