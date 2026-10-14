/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.face.gender;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Gender", description="Person gender", example="male")
public enum Gender {
    female(0),
    male(1),
    unknown(2);

    private final int id;

    private static Gender getById(int id) {
        if (id == 0) {
            return female;
        }
        if (id == 1) {
            return male;
        }
        return unknown;
    }

    public static Gender getById(Number id) {
        if (id == null) {
            return unknown;
        }
        return Gender.getById(id.intValue());
    }

    private Gender(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

