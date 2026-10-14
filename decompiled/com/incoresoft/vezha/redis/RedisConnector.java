/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.redis;

import com.incoresoft.vezha.redis.Redis;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisConnector {
    private Redis redis = null;
    private boolean connected;
    private Exception lastError;

    public RedisConnector(String host2, int port) {
        Logger logger2 = LoggerFactory.getLogger(this.getClass());
        try {
            this.redis = new Redis(new Socket(host2, port));
            logger2.info(String.format("Connected to redis on %s:%d", host2, port));
            this.connected = true;
        }
        catch (IOException e) {
            this.lastError = e;
            logger2.error(String.format("Could not connect to redis on %s:%d", host2, port));
            this.connected = false;
        }
    }

    public synchronized Object call(String ... args2) {
        try {
            if (this.redis != null) {
                return this.redis.call(args2);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public Exception getLastError() {
        return this.lastError;
    }
}

