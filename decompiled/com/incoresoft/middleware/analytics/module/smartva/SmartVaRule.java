/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.incoresoft.Utils.StringUtils;
import com.incoresoft.middleware.analytics.models.objectinzone.ObjectType;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsMustBeNotNull;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsValidatable;
import com.incoresoft.middleware.analytics.models.smartva.SmartVaObjectType;
import com.incoresoft.middleware.analytics.models.smartva.TimePeriod;
import com.incoresoft.middleware.analytics.module.smartva.RuleType;
import com.incoresoft.middleware.analytics.module.smartva.rules.Line;
import com.incoresoft.middleware.analytics.module.smartva.rules.Path;
import com.incoresoft.middleware.analytics.module.smartva.rules.Zone;
import com.incoresoft.middleware.analytics.module.smartva.rules.ZoneWithDirection;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name="SmartVaRule", description="Smart-VA rule class structure ")
@PointsMustBeNotNull
public class SmartVaRule
implements PointsValidatable {
    @NotBlank
    @Schema(name="id", description="Zone ID", implementation=String.class, example="537f49859cd2")
    private final String id;
    @JSONField(name="name", defaultValue="Zone 1")
    @Schema(name="name", description="Name of zone", implementation=String.class, example="Zone 1")
    @NotBlank
    private final String name;
    @JSONField(name="rule_type", defaultValue="zone")
    @Schema(name="rule_type", description="Type of rule", implementation=RuleType.class, example="zone")
    private final RuleType type;
    @Valid
    @ArraySchema(arraySchema=@Schema(name="points", description="Points array of zone polygon"), schema=@Schema(implementation=Point.class))
    @Size(min=2, max=256)
    @NotNull
    private final @Valid @Size(min=2, max=256) @NotNull Point[] points;
    @JSONField(name="color", defaultValue="#A347FF")
    @Schema(name="color", description="Color of zone", implementation=String.class, example="#ffffff")
    private final String color;
    @JSONField(name="object_types")
    @NotEmpty
    @ArraySchema(arraySchema=@Schema(name="object_types", description="Selected object types"), schema=@Schema(name="object_types", description="Object types", implementation=ObjectType.class, example="person"))
    private final SmartVaObjectType[] objectTypes;
    @JSONField(name="time_periods")
    @ArraySchema(arraySchema=@Schema(name="time_periods", description="Time periods array"), schema=@Schema(name="time_periods", description="Time periods settings", implementation=TimePeriod.class))
    @NotEmpty
    @Valid
    private final TimePeriod[] timePeriods;

    @JSONCreator
    public SmartVaRule(String id, String name, String color, RuleType type, Point[] points, TimePeriod[] timePeriods, SmartVaObjectType[] objectTypes) {
        this.id = this.getZoneId(id);
        this.name = name;
        this.points = points;
        this.type = type;
        this.color = color;
        this.timePeriods = timePeriods;
        this.objectTypes = objectTypes;
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

    public String getName() {
        return this.name;
    }

    public RuleType getType() {
        return this.type;
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    public String getColor() {
        return this.color;
    }

    public SmartVaObjectType[] getObjectTypes() {
        return this.objectTypes;
    }

    public TimePeriod[] getTimePeriods() {
        return this.timePeriods;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(object);
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            JSONObject jsonObject = parser.parseObject();
            RuleType ruleType = RuleType.valueOf(jsonObject.getString("rule_type").toUpperCase());
            String ruleString = jsonObject.toJSONString();
            Class ruleClass = null;
            switch (ruleType) {
                case ZONE: {
                    ruleClass = Zone.class;
                    break;
                }
                case ZONE_WITH_DIRECTION: {
                    ruleClass = ZoneWithDirection.class;
                    break;
                }
                case PATH: {
                    ruleClass = Path.class;
                    break;
                }
                case LINE: {
                    ruleClass = Line.class;
                }
            }
            return (T)JSONObject.parseObject(ruleString, ruleClass);
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

