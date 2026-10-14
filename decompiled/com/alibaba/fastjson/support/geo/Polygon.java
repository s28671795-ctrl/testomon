/*
 * Decompiled with CFR 0.152.
 */
package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.support.geo.Geometry;

@JSONType(typeName="Polygon", orders={"type", "bbox", "coordinates"})
public class Polygon
extends Geometry {
    private double[][][] coordinates;

    public Polygon() {
        super("Polygon");
    }

    public double[][][] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(double[][][] coordinates) {
        this.coordinates = coordinates;
    }
}

