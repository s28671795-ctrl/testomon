/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.attributes;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.incoresoft.middleware.analytics.models.attributes.AgeAttributes;
import com.incoresoft.middleware.analytics.models.attributes.BagAttributes;
import com.incoresoft.middleware.analytics.models.attributes.ClothesAttributes;
import com.incoresoft.middleware.analytics.models.attributes.ClothesLengthAttributes;
import com.incoresoft.middleware.analytics.models.attributes.DownAttributes;
import com.incoresoft.middleware.analytics.models.attributes.GenderAttributes;
import com.incoresoft.middleware.analytics.models.attributes.HairAttributes;
import com.incoresoft.middleware.analytics.models.attributes.UpAttributes;
import java.lang.reflect.Type;

public class JSONAttributesSerializerDeserializerInit {
    public static void init() {
        JSONAttributesSerializerDeserializerInit.initSerializer();
        JSONAttributesSerializerDeserializerInit.initDeserializer();
    }

    private static void initDeserializer() {
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)AgeAttributes.class), new AgeAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)BagAttributes.class), new BagAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)ClothesAttributes.class), new ClothesAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)ClothesLengthAttributes.class), new ClothesLengthAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)DownAttributes.class), new DownAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)GenderAttributes.class), new GenderAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)HairAttributes.class), new HairAttributes.Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)UpAttributes.class), new UpAttributes.Serializer());
    }

    private static void initSerializer() {
        SerializeConfig.getGlobalInstance().put((Type)((Object)AgeAttributes.class), new AgeAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)BagAttributes.class), new BagAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)ClothesAttributes.class), new ClothesAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)ClothesLengthAttributes.class), new ClothesLengthAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)DownAttributes.class), new DownAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)GenderAttributes.class), new GenderAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)HairAttributes.class), new HairAttributes.Serializer());
        SerializeConfig.getGlobalInstance().put((Type)((Object)UpAttributes.class), new UpAttributes.Serializer());
    }
}

