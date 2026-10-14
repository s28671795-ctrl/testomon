/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.redis;

import com.incoresoft.plugin.commons.redis.Redis;
import java.io.IOException;
import java.net.Socket;

public class RedisConnector {
    private final Redis redis;

    public RedisConnector(String host2, int port) throws IOException {
        this.redis = new Redis(new Socket(host2, port));
    }

    public synchronized Object call(String ... args2) throws IOException {
        return this.redis.call(args2);
    }
}

