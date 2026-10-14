/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.facerecognition;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;

public final class AgeRange {
    @JSONField(name="lower_bound")
    private final int lowerBound;
    @JSONField(name="upper_bound")
    private final int upperBound;

    public AgeRange(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AgeRange)) {
            return false;
        }
        AgeRange other = (AgeRange)o;
        if (this.getLowerBound() != other.getLowerBound()) {
            return false;
        }
        return this.getUpperBound() == other.getUpperBound();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getLowerBound();
        result = result * 59 + this.getUpperBound();
        return result;
    }

    public String toString() {
        return "AgeRange(lowerBound=" + this.getLowerBound() + ", upperBound=" + this.getUpperBound() + ")";
    }

    public static class Deserializer
    implements ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            JSONObject object = parser.parseObject();
            return (T)new AgeRange(object.getInteger("lower_bound"), object.getInteger("upper_bound"));
        }

        @Override
        public int getFastMatchToken() {
            return 12;
        }

        static {
            ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)AgeRange.class), new Deserializer());
        }
    }
}

