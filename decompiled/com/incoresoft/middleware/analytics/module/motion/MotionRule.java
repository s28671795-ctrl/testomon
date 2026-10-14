/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.motion;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.Utils.StringUtils;
import com.incoresoft.middleware.analytics.models.motion.MotionRuleType;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsMustBeNotNull;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsValidatable;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.module.motion.validators.LineCrossingObjectHasOnlyTwoPoints;
import com.incoresoft.middleware.analytics.module.motion.validators.LineCrossingObjectMustContainDirection;
import com.incoresoft.middleware.analytics.module.motion.validators.NonLineCrossingObjectHasAtLeastThreePoints;
import com.incoresoft.middleware.analytics.module.motion.validators.NotNull;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NotNull
@LineCrossingObjectHasOnlyTwoPoints
@LineCrossingObjectMustContainDirection
@NonLineCrossingObjectHasAtLeastThreePoints
@Schema(name="MotionRule", description="Rule for motion detection")
@PointsMustBeNotNull
public final class MotionRule
implements PointsValidatable {
    @NotBlank
    @Schema(name="id", description="Zone ID", implementation=String.class, example="537f49859cd2")
    private final String id;
    @Valid
    @ArraySchema(schema=@Schema(name="points", description="Points array of zone polygon", implementation=Point.class))
    @Size(min=2, max=256)
    @javax.validation.constraints.NotNull
    private final @Valid @Size(min=2, max=256) @javax.validation.constraints.NotNull Point[] points;
    @JSONField(name="name")
    @Schema(name="name", description="Name of zone", implementation=String.class, example="Zone 1")
    @NotBlank
    private final String name;
    @JSONField(name="color")
    @Schema(name="color", description="Color of zone", implementation=String.class, example="#ffffff")
    private final String color;
    @JSONField(name="time_periods")
    @ArraySchema(schema=@Schema(name="time_periods", description="Time periods for zone", implementation=TimePeriod.class))
    @NotEmpty
    private final TimePeriod[] timePeriods;
    @JSONField(name="rule")
    @Schema(name="rule", description="Rule for zone", implementation=MotionRuleType.class)
    @javax.validation.constraints.NotNull
    private final MotionRuleType rule;
    @JSONField(name="direction")
    @Schema(name="direction", description="Direction", implementation=Integer.class, example="0")
    private final Integer direction;
    @JSONField(name="dwell_time")
    @Schema(name="dwell_time", description="Dwell time", implementation=Integer.class, example="5")
    private final Integer dwellTime;

    public MotionRule(String id, @Valid @Size(min=2, max=256) @Valid @Size(min=2, max=256) Point[] points, String name, String color, @NotEmpty TimePeriod[] timePeriods, MotionRuleType rule, Integer direction, Integer dwellTime) {
        this.id = this.getZoneId(id);
        this.points = points;
        this.name = name;
        this.color = color;
        this.timePeriods = timePeriods;
        this.rule = rule;
        this.direction = direction;
        this.dwellTime = dwellTime;
    }

    private String getZoneId(String id) {
        if (id == null || StringUtils.isBlank((CharSequence)id)) {
            String[] split = UUID.randomUUID().toString().split("-");
            id = split[0] + split[1];
        }
        return id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public TimePeriod[] getTimePeriods() {
        return this.timePeriods;
    }

    public MotionRuleType getRule() {
        return this.rule;
    }

    public Integer getDirection() {
        return this.direction;
    }

    public Integer getDwellTime() {
        return this.dwellTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MotionRule)) {
            return false;
        }
        MotionRule other = (MotionRule)o;
        Integer this$direction = this.getDirection();
        Integer other$direction = other.getDirection();
        if (this$direction == null ? other$direction != null : !((Object)this$direction).equals(other$direction)) {
            return false;
        }
        Integer this$dwellTime = this.getDwellTime();
        Integer other$dwellTime = other.getDwellTime();
        if (this$dwellTime == null ? other$dwellTime != null : !((Object)this$dwellTime).equals(other$dwellTime)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getPoints(), other.getPoints())) {
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
        if (!Arrays.deepEquals(this.getTimePeriods(), other.getTimePeriods())) {
            return false;
        }
        MotionRuleType this$rule = this.getRule();
        MotionRuleType other$rule = other.getRule();
        return !(this$rule == null ? other$rule != null : !((Object)((Object)this$rule)).equals((Object)other$rule));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $direction = this.getDirection();
        result = result * 59 + ($direction == null ? 43 : ((Object)$direction).hashCode());
        Integer $dwellTime = this.getDwellTime();
        result = result * 59 + ($dwellTime == null ? 43 : ((Object)$dwellTime).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getPoints());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $color = this.getColor();
        result = result * 59 + ($color == null ? 43 : $color.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getTimePeriods());
        MotionRuleType $rule = this.getRule();
        result = result * 59 + ($rule == null ? 43 : ((Object)((Object)$rule)).hashCode());
        return result;
    }

    public String toString() {
        return "MotionRule(id=" + this.getId() + ", points=" + Arrays.deepToString(this.getPoints()) + ", name=" + this.getName() + ", color=" + this.getColor() + ", timePeriods=" + Arrays.deepToString(this.getTimePeriods()) + ", rule=" + String.valueOf((Object)this.getRule()) + ", direction=" + this.getDirection() + ", dwellTime=" + this.getDwellTime() + ")";
    }
}

