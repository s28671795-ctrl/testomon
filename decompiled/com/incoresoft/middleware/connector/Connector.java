/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.connector;

import com.incoresoft.middleware.connector.ConnectorStatus;
import com.incoresoft.middleware.connector.OnStatusChangeListener;
import java.util.Map;

public interface Connector {
    public void init();

    public void init(Map<String, String> var1);

    public void init(OnStatusChangeListener var1);

    public void init(OnStatusChangeListener var1, Map<String, String> var2);

    public ConnectorStatus getStatus();
}

