/*
 * Decompiled with CFR 0.152.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface AutowiredObjectSerializer
extends ObjectSerializer {
    public Set<Type> getAutowiredFor();
}

