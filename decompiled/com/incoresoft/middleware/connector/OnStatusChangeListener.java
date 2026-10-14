/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.connector;

import com.incoresoft.middleware.connector.Connector;
import com.incoresoft.middleware.connector.ConnectorStatus;

public interface OnStatusChangeListener {
    public void onStatusChanged(Connector var1, ConnectorStatus var2);
}

