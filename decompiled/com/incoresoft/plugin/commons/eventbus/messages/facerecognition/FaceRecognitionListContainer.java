/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.facerecognition;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.plugin.commons.eventbus.messages.facerecognition.FaceRecognitionPersonList;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public final class FaceRecognitionListContainer {
    @JSONField(name="lists")
    private final List<FaceRecognitionPersonList> lists;

    public FaceRecognitionListContainer(List<FaceRecognitionPersonList> lists) {
        this.lists = lists;
    }

    public List<FaceRecognitionPersonList> getLists() {
        return this.lists;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaceRecognitionListContainer)) {
            return false;
        }
        FaceRecognitionListContainer other = (FaceRecognitionListContainer)o;
        List<FaceRecognitionPersonList> this$lists = this.getLists();
        List<FaceRecognitionPersonList> other$lists = other.getLists();
        return !(this$lists == null ? other$lists != null : !((Object)this$lists).equals(other$lists));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<FaceRecognitionPersonList> $lists = this.getLists();
        result = result * 59 + ($lists == null ? 43 : ((Object)$lists).hashCode());
        return result;
    }

    public String toString() {
        return "FaceRecognitionListContainer(lists=" + String.valueOf(this.getLists()) + ")";
    }

    public static class Serializer
    implements ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)new FaceRecognitionListContainer(Collections.unmodifiableList(parser.parseArray(FaceRecognitionPersonList.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 14;
        }

        static {
            ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)FaceRecognitionListContainer.class), new Serializer());
        }
    }
}

