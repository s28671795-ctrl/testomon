/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.http.exceptions.ServerNotFoundException;

public class ServerBusyException
extends ServerNotFoundException {
    public ServerBusyException(Analytics analytics) {
        super(analytics);
    }
}

