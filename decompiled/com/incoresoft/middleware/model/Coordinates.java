/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model;

public final class Coordinates {
    public static final Coordinates ZERO = new Coordinates(0.0, 0.0);
    private final double latitude;
    private final double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Coordinates)) {
            return false;
        }
        Coordinates other = (Coordinates)o;
        if (Double.compare(this.getLatitude(), other.getLatitude()) != 0) {
            return false;
        }
        return Double.compare(this.getLongitude(), other.getLongitude()) == 0;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $latitude = Double.doubleToLongBits(this.getLatitude());
        result = result * 59 + (int)($latitude >>> 32 ^ $latitude);
        long $longitude = Double.doubleToLongBits(this.getLongitude());
        result = result * 59 + (int)($longitude >>> 32 ^ $longitude);
        return result;
    }

    public String toString() {
        return "Coordinates(latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ")";
    }
}

