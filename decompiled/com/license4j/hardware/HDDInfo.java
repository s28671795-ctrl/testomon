/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.hardware;

public final class HDDInfo {
    private final String model;
    private final String serial;

    public HDDInfo(String model, String serial) {
        this.model = model;
        this.serial = serial;
    }

    public String getModel() {
        return this.model;
    }

    public String getSerial() {
        return this.serial;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HDDInfo)) {
            return false;
        }
        HDDInfo other = (HDDInfo)o;
        String this$model = this.getModel();
        String other$model = other.getModel();
        if (this$model == null ? other$model != null : !this$model.equals(other$model)) {
            return false;
        }
        String this$serial = this.getSerial();
        String other$serial = other.getSerial();
        return !(this$serial == null ? other$serial != null : !this$serial.equals(other$serial));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $model = this.getModel();
        result = result * 59 + ($model == null ? 43 : $model.hashCode());
        String $serial = this.getSerial();
        result = result * 59 + ($serial == null ? 43 : $serial.hashCode());
        return result;
    }

    public String toString() {
        return "HDDInfo(model=" + this.getModel() + ", serial=" + this.getSerial() + ")";
    }
}

