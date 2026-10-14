/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.polygon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Schema(name="Point", description="Point class structure")
public final class Point {
    @JsonProperty(value="x")
    @Schema(name="x", description="X coordinate of the point in zone polygon", implementation=Double.class, example="0.9922")
    @DecimalMin(value="0.0")
    @DecimalMax(value="1.0")
    private final @DecimalMin(value="0.0") @DecimalMax(value="1.0") double x;
    @JsonProperty(value="y")
    @Schema(name="y", description="Y coordinate of the point in zone polygon", implementation=Double.class, example="0.822")
    @DecimalMin(value="0.0")
    @DecimalMax(value="1.0")
    private final @DecimalMin(value="0.0") @DecimalMax(value="1.0") double y;

    @JsonCreator
    public Point(@JsonProperty(value="x") double x, @JsonProperty(value="y") double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point other = (Point)o;
        if (Double.compare(this.getX(), other.getX()) != 0) {
            return false;
        }
        return Double.compare(this.getY(), other.getY()) == 0;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $x = Double.doubleToLongBits(this.getX());
        result = result * 59 + (int)($x >>> 32 ^ $x);
        long $y = Double.doubleToLongBits(this.getY());
        result = result * 59 + (int)($y >>> 32 ^ $y);
        return result;
    }

    public String toString() {
        return "Point(x=" + this.getX() + ", y=" + this.getY() + ")";
    }
}

