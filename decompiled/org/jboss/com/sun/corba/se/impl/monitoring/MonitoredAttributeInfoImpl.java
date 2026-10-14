/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.monitoring;

import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttributeInfo;

public class MonitoredAttributeInfoImpl
implements MonitoredAttributeInfo {
    private final String description;
    private final Class<?> type;
    private final boolean writableFlag;
    private final boolean statisticFlag;

    MonitoredAttributeInfoImpl(String description, Class<?> type, boolean isWritable, boolean isStatistic) {
        this.description = description;
        this.type = type;
        this.writableFlag = isWritable;
        this.statisticFlag = isStatistic;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Class<?> type() {
        return this.type;
    }

    @Override
    public boolean isWritable() {
        return this.writableFlag;
    }

    @Override
    public boolean isStatistic() {
        return this.statisticFlag;
    }
}

