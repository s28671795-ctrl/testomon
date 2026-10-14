/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventbus;

import com.incoresoft.middleware.eventbus.BusListener;
import com.incoresoft.middleware.eventbus.BusMessage;
import java.util.concurrent.CompletableFuture;

public interface Bus {
    public void publish(String var1, String var2);

    public void publish(String var1, byte[] var2);

    public void publish(String var1, BusMessage var2);

    public CompletableFuture<BusMessage> request(String var1, String var2);

    public CompletableFuture<BusMessage> request(String var1, byte[] var2);

    public CompletableFuture<BusMessage> request(String var1, BusMessage var2);

    public boolean addListener(BusListener var1);

    public boolean removeListener(BusListener var1);
}

