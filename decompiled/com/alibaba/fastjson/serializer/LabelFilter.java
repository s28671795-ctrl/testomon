/*
 * Decompiled with CFR 0.152.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface LabelFilter
extends SerializeFilter {
    public boolean apply(String var1);
}

