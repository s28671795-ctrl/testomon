/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jmx;

public interface DatabaseInfoMBean {
    public boolean isExclusive();

    public boolean isReadOnly();

    public String getMode();

    public long getFileWriteCount();

    public long getFileReadCount();

    public long getFileSize();

    public int getCacheSizeMax();

    public void setCacheSizeMax(int var1);

    public int getCacheSize();

    public String getVersion();

    public int getTraceLevel();

    public void setTraceLevel(int var1);

    public String listSettings();

    public String listSessions();
}

