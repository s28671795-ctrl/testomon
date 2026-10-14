/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.alpr.makemodel;

public final class MakeModel {
    public static final MakeModel UNKNOWN = new MakeModel(-1, "", "");
    private final int id;
    private final String make;
    private final String model;

    public boolean equals(Object obj) {
        if (!(obj instanceof MakeModel)) {
            return false;
        }
        MakeModel makeModel = (MakeModel)obj;
        return this.make.equals(makeModel.make) && this.model.equals(makeModel.model);
    }

    public int getId() {
        return this.id;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public String toString() {
        return "MakeModel(id=" + this.getId() + ", make=" + this.getMake() + ", model=" + this.getModel() + ")";
    }

    public MakeModel(int id, String make, String model) {
        this.id = id;
        this.make = make;
        this.model = model;
    }
}

