/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

public class PtzStatus {
    private double pan;
    private double tilt;
    private double zoom;

    public PtzStatus(double pan, double tilt, double zoom) {
        this.pan = pan;
        this.tilt = tilt;
        this.zoom = zoom;
    }

    public double getPan() {
        return this.pan;
    }

    public void setPan(double pan) {
        this.pan = pan;
    }

    public double getTilt() {
        return this.tilt;
    }

    public void setTilt(double tilt) {
        this.tilt = tilt;
    }

    public double getZoom() {
        return this.zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}

