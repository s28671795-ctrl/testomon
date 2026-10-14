/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.attributes;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.attributes.AgeAttributes;
import com.incoresoft.middleware.analytics.models.attributes.BagAttributes;
import com.incoresoft.middleware.analytics.models.attributes.ClothesAttributes;
import com.incoresoft.middleware.analytics.models.attributes.ClothesLengthAttributes;
import com.incoresoft.middleware.analytics.models.attributes.DownAttributes;
import com.incoresoft.middleware.analytics.models.attributes.GenderAttributes;
import com.incoresoft.middleware.analytics.models.attributes.HairAttributes;
import com.incoresoft.middleware.analytics.models.attributes.PersonAttributes;
import com.incoresoft.middleware.analytics.models.attributes.UpAttributes;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="PersonAttributesDTO", description="Person attributes class structure")
public final class PersonAttributesDTO {
    @JSONField(name="age")
    @Schema(name="age", description="Age", implementation=AgeAttributes.class)
    private final AgeAttributes age;
    @JSONField(name="bag")
    @Schema(name="bag", description="Bag type", implementation=BagAttributes.class)
    private final BagAttributes bag;
    @JSONField(name="down")
    @Schema(name="down", description="Down clothes color", implementation=DownAttributes.class)
    private final DownAttributes down;
    @JSONField(name="up")
    @Schema(name="up", description="Up clothes color", implementation=UpAttributes.class)
    private final UpAttributes up;
    @JSONField(name="down_clothes")
    @Schema(name="down_clothes", description="Down clothes type", implementation=ClothesAttributes.class)
    private final ClothesAttributes clothes;
    @JSONField(name="down_length")
    @Schema(name="down_length", description="Down clothes length", implementation=ClothesLengthAttributes.class)
    private final ClothesLengthAttributes downLength;
    @JSONField(name="up_length")
    @Schema(name="up_length", description="Up clothes length", implementation=ClothesLengthAttributes.class)
    private final ClothesLengthAttributes upLength;
    @JSONField(name="gender")
    @Schema(name="gender", description="Gender", implementation=GenderAttributes.class)
    private final GenderAttributes gender;
    @JSONField(name="hair")
    @Schema(name="hair", description="Hair length", implementation=HairAttributes.class)
    private final HairAttributes hair;
    @JSONField(name="hat")
    @Schema(name="hat", description="Hat", implementation=Boolean.class, example="false")
    private final boolean hat;

    public PersonAttributesDTO(PersonAttributes entity) {
        this.age = entity.getAge();
        this.bag = entity.getBag();
        this.down = entity.getDown();
        this.up = entity.getUp();
        this.clothes = entity.getClothes();
        this.downLength = entity.getDownLength();
        this.upLength = entity.getUpLength();
        this.gender = entity.getGender();
        this.hair = entity.getHair();
        this.hat = entity.getHat();
    }

    public AgeAttributes getAge() {
        return this.age;
    }

    public BagAttributes getBag() {
        return this.bag;
    }

    public DownAttributes getDown() {
        return this.down;
    }

    public UpAttributes getUp() {
        return this.up;
    }

    public ClothesAttributes getClothes() {
        return this.clothes;
    }

    public ClothesLengthAttributes getDownLength() {
        return this.downLength;
    }

    public ClothesLengthAttributes getUpLength() {
        return this.upLength;
    }

    public GenderAttributes getGender() {
        return this.gender;
    }

    public HairAttributes getHair() {
        return this.hair;
    }

    public boolean isHat() {
        return this.hat;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PersonAttributesDTO)) {
            return false;
        }
        PersonAttributesDTO other = (PersonAttributesDTO)o;
        if (this.isHat() != other.isHat()) {
            return false;
        }
        AgeAttributes this$age = this.getAge();
        AgeAttributes other$age = other.getAge();
        if (this$age == null ? other$age != null : !((Object)((Object)this$age)).equals((Object)other$age)) {
            return false;
        }
        BagAttributes this$bag = this.getBag();
        BagAttributes other$bag = other.getBag();
        if (this$bag == null ? other$bag != null : !((Object)((Object)this$bag)).equals((Object)other$bag)) {
            return false;
        }
        DownAttributes this$down = this.getDown();
        DownAttributes other$down = other.getDown();
        if (this$down == null ? other$down != null : !((Object)((Object)this$down)).equals((Object)other$down)) {
            return false;
        }
        UpAttributes this$up = this.getUp();
        UpAttributes other$up = other.getUp();
        if (this$up == null ? other$up != null : !((Object)((Object)this$up)).equals((Object)other$up)) {
            return false;
        }
        ClothesAttributes this$clothes = this.getClothes();
        ClothesAttributes other$clothes = other.getClothes();
        if (this$clothes == null ? other$clothes != null : !((Object)((Object)this$clothes)).equals((Object)other$clothes)) {
            return false;
        }
        ClothesLengthAttributes this$downLength = this.getDownLength();
        ClothesLengthAttributes other$downLength = other.getDownLength();
        if (this$downLength == null ? other$downLength != null : !((Object)((Object)this$downLength)).equals((Object)other$downLength)) {
            return false;
        }
        ClothesLengthAttributes this$upLength = this.getUpLength();
        ClothesLengthAttributes other$upLength = other.getUpLength();
        if (this$upLength == null ? other$upLength != null : !((Object)((Object)this$upLength)).equals((Object)other$upLength)) {
            return false;
        }
        GenderAttributes this$gender = this.getGender();
        GenderAttributes other$gender = other.getGender();
        if (this$gender == null ? other$gender != null : !((Object)((Object)this$gender)).equals((Object)other$gender)) {
            return false;
        }
        HairAttributes this$hair = this.getHair();
        HairAttributes other$hair = other.getHair();
        return !(this$hair == null ? other$hair != null : !((Object)((Object)this$hair)).equals((Object)other$hair));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isHat() ? 79 : 97);
        AgeAttributes $age = this.getAge();
        result = result * 59 + ($age == null ? 43 : ((Object)((Object)$age)).hashCode());
        BagAttributes $bag = this.getBag();
        result = result * 59 + ($bag == null ? 43 : ((Object)((Object)$bag)).hashCode());
        DownAttributes $down = this.getDown();
        result = result * 59 + ($down == null ? 43 : ((Object)((Object)$down)).hashCode());
        UpAttributes $up = this.getUp();
        result = result * 59 + ($up == null ? 43 : ((Object)((Object)$up)).hashCode());
        ClothesAttributes $clothes = this.getClothes();
        result = result * 59 + ($clothes == null ? 43 : ((Object)((Object)$clothes)).hashCode());
        ClothesLengthAttributes $downLength = this.getDownLength();
        result = result * 59 + ($downLength == null ? 43 : ((Object)((Object)$downLength)).hashCode());
        ClothesLengthAttributes $upLength = this.getUpLength();
        result = result * 59 + ($upLength == null ? 43 : ((Object)((Object)$upLength)).hashCode());
        GenderAttributes $gender = this.getGender();
        result = result * 59 + ($gender == null ? 43 : ((Object)((Object)$gender)).hashCode());
        HairAttributes $hair = this.getHair();
        result = result * 59 + ($hair == null ? 43 : ((Object)((Object)$hair)).hashCode());
        return result;
    }

    public String toString() {
        return "PersonAttributesDTO(age=" + String.valueOf((Object)this.getAge()) + ", bag=" + String.valueOf((Object)this.getBag()) + ", down=" + String.valueOf((Object)this.getDown()) + ", up=" + String.valueOf((Object)this.getUp()) + ", clothes=" + String.valueOf((Object)this.getClothes()) + ", downLength=" + String.valueOf((Object)this.getDownLength()) + ", upLength=" + String.valueOf((Object)this.getUpLength()) + ", gender=" + String.valueOf((Object)this.getGender()) + ", hair=" + String.valueOf((Object)this.getHair()) + ", hat=" + this.isHat() + ")";
    }

    public PersonAttributesDTO(AgeAttributes age, BagAttributes bag, DownAttributes down, UpAttributes up, ClothesAttributes clothes, ClothesLengthAttributes downLength, ClothesLengthAttributes upLength, GenderAttributes gender, HairAttributes hair, boolean hat) {
        this.age = age;
        this.bag = bag;
        this.down = down;
        this.up = up;
        this.clothes = clothes;
        this.downLength = downLength;
        this.upLength = upLength;
        this.gender = gender;
        this.hair = hair;
        this.hat = hat;
    }
}

