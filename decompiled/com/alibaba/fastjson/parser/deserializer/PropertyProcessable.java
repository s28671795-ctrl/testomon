/*
 * Decompiled with CFR 0.152.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import java.lang.reflect.Type;

public interface PropertyProcessable
extends ParseProcess {
    public Type getType(String var1);

    public void apply(String var1, Object var2);
}

