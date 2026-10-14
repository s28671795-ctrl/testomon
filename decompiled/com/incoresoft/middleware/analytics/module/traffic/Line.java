/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.traffic;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Schema(name="Line", description="Line class structure")
public final class Line {
    @JSONField(name="id")
    @Schema(name="id", description="Line ID", implementation=Integer.class, example="1")
    private int id;
    @JSONField(name="name")
    @Schema(name="name", description="Line name", implementation=String.class, example="Line 1")
    @NotBlank
    private final String name;
    @JSONField(name="color")
    @Schema(name="color", description="Line color", implementation=String.class, example="#ffffff")
    private final String color;
    @JSONField(name="direction")
    @Schema(name="direction", description="Line direction: 0 - both directions, 1 - entry, 2 - exit", implementation=Integer.class, example="1")
    private final int direction;
    @JSONField(name="count_type", defaultValue="1")
    @Schema(name="count_type", description="Enter/exit flag: 1, 2", implementation=Integer.class, example="1")
    private final int countType;
    @JSONField(name="x1")
    @Schema(name="x1", description="X1 coordinates", implementation=Double.class, example="1")
    private final double x1;
    @JSONField(name="y1")
    @Schema(name="y1", description="Y1 coordinates", implementation=Double.class, example="1")
    private final double y1;
    @JSONField(name="x2")
    @Schema(name="x2", description="X2 coordinates", implementation=Double.class, example="1")
    private final double x2;
    @JSONField(name="y2")
    @Schema(name="y2", description="Y2 coordinates", implementation=Double.class, example="1")
    private final double y2;
    @JSONField(name="types")
    @ArraySchema(schema=@Schema(name="types", description="<div><p>Objects types allowed in analytics.</p><p>Valid values are:</p><ul><li>0 - person<li>1 - bicycle<li>2 - car<li>3 - motorbike<li>4 - bus<li>5 - truck</ul></div>", implementation=Integer.class, example="1"))
    @NotEmpty
    private final int[] types;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getDirection() {
        return this.direction;
    }

    public int getCountType() {
        return this.countType;
    }

    public double getX1() {
        return this.x1;
    }

    public double getY1() {
        return this.y1;
    }

    public double getX2() {
        return this.x2;
    }

    public double getY2() {
        return this.y2;
    }

    public int[] getTypes() {
        return this.types;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Line)) {
            return false;
        }
        Line other = (Line)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getDirection() != other.getDirection()) {
            return false;
        }
        if (this.getCountType() != other.getCountType()) {
            return false;
        }
        if (Double.compare(this.getX1(), other.getX1()) != 0) {
            return false;
        }
        if (Double.compare(this.getY1(), other.getY1()) != 0) {
            return false;
        }
        if (Double.compare(this.getX2(), other.getX2()) != 0) {
            return false;
        }
        if (Double.compare(this.getY2(), other.getY2()) != 0) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$color = this.getColor();
        String other$color = other.getColor();
        if (this$color == null ? other$color != null : !this$color.equals(other$color)) {
            return false;
        }
        return Arrays.equals(this.getTypes(), other.getTypes());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getDirection();
        result = result * 59 + this.getCountType();
        long $x1 = Double.doubleToLongBits(this.getX1());
        result = result * 59 + (int)($x1 >>> 32 ^ $x1);
        long $y1 = Double.doubleToLongBits(this.getY1());
        result = result * 59 + (int)($y1 >>> 32 ^ $y1);
        long $x2 = Double.doubleToLongBits(this.getX2());
        result = result * 59 + (int)($x2 >>> 32 ^ $x2);
        long $y2 = Double.doubleToLongBits(this.getY2());
        result = result * 59 + (int)($y2 >>> 32 ^ $y2);
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $color = this.getColor();
        result = result * 59 + ($color == null ? 43 : $color.hashCode());
        result = result * 59 + Arrays.hashCode(this.getTypes());
        return result;
    }

    public String toString() {
        return "Line(id=" + this.getId() + ", name=" + this.getName() + ", color=" + this.getColor() + ", direction=" + this.getDirection() + ", countType=" + this.getCountType() + ", x1=" + this.getX1() + ", y1=" + this.getY1() + ", x2=" + this.getX2() + ", y2=" + this.getY2() + ", types=" + Arrays.toString(this.getTypes()) + ")";
    }

    public Line(int id, String name, String color, int direction, int countType, double x1, double y1, double x2, double y2, int[] types) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.direction = direction;
        this.countType = countType;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.types = types;
    }

    public void setId(int id) {
        this.id = id;
    }
}

