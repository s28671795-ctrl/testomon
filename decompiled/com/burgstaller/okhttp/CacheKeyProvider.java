/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp;

import okhttp3.Request;

public interface CacheKeyProvider {
    public String getCachingKey(Request var1);
}

