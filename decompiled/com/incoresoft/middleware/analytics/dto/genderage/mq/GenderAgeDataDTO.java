/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.genderage.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.attributes.GenderAttributes;
import com.incoresoft.middleware.analytics.models.genderage.Age;

public final class GenderAgeDataDTO {
    @JSONField(name="gender")
    private final GenderAttributes gender;
    @JSONField(name="age")
    private final Age age;
    @JSONField(name="count")
    private final int count;

    public GenderAttributes getGender() {
        return this.gender;
    }

    public Age getAge() {
        return this.age;
    }

    public int getCount() {
        return this.count;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GenderAgeDataDTO)) {
            return false;
        }
        GenderAgeDataDTO other = (GenderAgeDataDTO)o;
        if (this.getCount() != other.getCount()) {
            return false;
        }
        GenderAttributes this$gender = this.getGender();
        GenderAttributes other$gender = other.getGender();
        if (this$gender == null ? other$gender != null : !((Object)((Object)this$gender)).equals((Object)other$gender)) {
            return false;
        }
        Age this$age = this.getAge();
        Age other$age = other.getAge();
        return !(this$age == null ? other$age != null : !((Object)((Object)this$age)).equals((Object)other$age));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getCount();
        GenderAttributes $gender = this.getGender();
        result = result * 59 + ($gender == null ? 43 : ((Object)((Object)$gender)).hashCode());
        Age $age = this.getAge();
        result = result * 59 + ($age == null ? 43 : ((Object)((Object)$age)).hashCode());
        return result;
    }

    public String toString() {
        return "GenderAgeDataDTO(gender=" + String.valueOf((Object)this.getGender()) + ", age=" + String.valueOf((Object)this.getAge()) + ", count=" + this.getCount() + ")";
    }

    public GenderAgeDataDTO(GenderAttributes gender, Age age, int count) {
        this.gender = gender;
        this.age = age;
        this.count = count;
    }
}

