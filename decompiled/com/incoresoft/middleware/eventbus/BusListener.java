/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventbus;

import com.incoresoft.middleware.eventbus.BusMessage;

public interface BusListener {
    public String subject();

    public void messageReceived(BusMessage var1);
}

