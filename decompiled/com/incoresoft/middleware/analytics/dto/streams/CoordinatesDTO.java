/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.model.Coordinates;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="CoordinatesDTO", description="Coordinates class structure")
public class CoordinatesDTO {
    @JsonProperty(value="latitude")
    @JSONField(name="latitude")
    @Schema(description="latitude", implementation=Double.class, example="48.926563")
    private double latitude;
    @JsonProperty(value="longitude")
    @JSONField(name="longitude")
    @Schema(description="longitude", implementation=Double.class, example="31.475782")
    private double longitude;

    public CoordinatesDTO(Coordinates coordinates) {
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    @JsonProperty(value="latitude")
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty(value="longitude")
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CoordinatesDTO)) {
            return false;
        }
        CoordinatesDTO other = (CoordinatesDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getLatitude(), other.getLatitude()) != 0) {
            return false;
        }
        return Double.compare(this.getLongitude(), other.getLongitude()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CoordinatesDTO;
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
        return "CoordinatesDTO(latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ")";
    }

    public CoordinatesDTO() {
    }

    public CoordinatesDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

