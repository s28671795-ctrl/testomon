/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.polygon;

import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsMustBeNotNull;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsValidatable;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name="BoundingBox", description="Bounding box class structure")
@PointsMustBeNotNull
public class BoundingBox
implements PointsValidatable {
    @Valid
    @ArraySchema(arraySchema=@Schema(name="points", description="Points array of zone polygon"), schema=@Schema(implementation=Point.class))
    @Size(min=3, max=256)
    @NotNull
    private @Valid @Size(min=3, max=256) @NotNull Point[] points;

    public BoundingBox(List<Double> box) {
        this.points = new Point[box.size() / 2];
        int j = 0;
        for (int i = 0; i < box.size(); i += 2) {
            Point point = new Point(box.get(i), box.get(i + 1));
            this.points[j++] = point;
        }
    }

    public BoundingBox() {
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
}

