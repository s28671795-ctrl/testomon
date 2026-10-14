/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.attributes;

import com.incoresoft.middleware.analytics.dto.attributes.PersonAttributesDTO;
import com.incoresoft.middleware.analytics.models.attributes.AgeAttributes;
import com.incoresoft.middleware.analytics.models.attributes.BagAttributes;
import com.incoresoft.middleware.analytics.models.attributes.ClothesAttributes;
import com.incoresoft.middleware.analytics.models.attributes.ClothesLengthAttributes;
import com.incoresoft.middleware.analytics.models.attributes.DownAttributes;
import com.incoresoft.middleware.analytics.models.attributes.GenderAttributes;
import com.incoresoft.middleware.analytics.models.attributes.HairAttributes;
import com.incoresoft.middleware.analytics.models.attributes.UpAttributes;

public class PersonAttributes {
    private AgeAttributes age;
    private BagAttributes bag;
    private DownAttributes down;
    private UpAttributes up;
    private ClothesAttributes clothes;
    private ClothesLengthAttributes downLength;
    private ClothesLengthAttributes upLength;
    private GenderAttributes gender;
    private HairAttributes hair;
    private Boolean hat;

    public PersonAttributes(PersonAttributesDTO dto) {
        this.age = dto.getAge();
        this.bag = dto.getBag();
        this.down = dto.getDown();
        this.up = dto.getUp();
        this.clothes = dto.getClothes();
        this.downLength = dto.getDownLength();
        this.upLength = dto.getUpLength();
        this.gender = dto.getGender();
        this.hair = dto.getHair();
        this.hat = dto.isHat();
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

    public Boolean getHat() {
        return this.hat;
    }

    public void setAge(AgeAttributes age) {
        this.age = age;
    }

    public void setBag(BagAttributes bag) {
        this.bag = bag;
    }

    public void setDown(DownAttributes down) {
        this.down = down;
    }

    public void setUp(UpAttributes up) {
        this.up = up;
    }

    public void setClothes(ClothesAttributes clothes) {
        this.clothes = clothes;
    }

    public void setDownLength(ClothesLengthAttributes downLength) {
        this.downLength = downLength;
    }

    public void setUpLength(ClothesLengthAttributes upLength) {
        this.upLength = upLength;
    }

    public void setGender(GenderAttributes gender) {
        this.gender = gender;
    }

    public void setHair(HairAttributes hair) {
        this.hair = hair;
    }

    public void setHat(Boolean hat) {
        this.hat = hat;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PersonAttributes)) {
            return false;
        }
        PersonAttributes other = (PersonAttributes)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Boolean this$hat = this.getHat();
        Boolean other$hat = other.getHat();
        if (this$hat == null ? other$hat != null : !((Object)this$hat).equals(other$hat)) {
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

    protected boolean canEqual(Object other) {
        return other instanceof PersonAttributes;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Boolean $hat = this.getHat();
        result = result * 59 + ($hat == null ? 43 : ((Object)$hat).hashCode());
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
        return "PersonAttributes(age=" + String.valueOf((Object)this.getAge()) + ", bag=" + String.valueOf((Object)this.getBag()) + ", down=" + String.valueOf((Object)this.getDown()) + ", up=" + String.valueOf((Object)this.getUp()) + ", clothes=" + String.valueOf((Object)this.getClothes()) + ", downLength=" + String.valueOf((Object)this.getDownLength()) + ", upLength=" + String.valueOf((Object)this.getUpLength()) + ", gender=" + String.valueOf((Object)this.getGender()) + ", hair=" + String.valueOf((Object)this.getHair()) + ", hat=" + this.getHat() + ")";
    }

    public PersonAttributes() {
    }
}

