/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server;

import java.sql.SQLException;

public interface Service {
    public void init(String ... var1) throws Exception;

    public String getURL();

    public void start() throws SQLException;

    public void listen();

    public void stop();

    public boolean isRunning(boolean var1);

    public boolean getAllowOthers();

    public String getName();

    public String getType();

    public int getPort();

    public boolean isDaemon();
}

