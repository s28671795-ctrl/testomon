/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp;

import com.burgstaller.okhttp.CacheKeyProvider;
import okhttp3.HttpUrl;
import okhttp3.Request;

public final class DefaultCacheKeyProvider
implements CacheKeyProvider {
    @Override
    public String getCachingKey(Request request) {
        HttpUrl url2 = request.url();
        if (url2 == null) {
            return null;
        }
        return url2.scheme() + ":" + url2.host() + ":" + url2.port();
    }
}

