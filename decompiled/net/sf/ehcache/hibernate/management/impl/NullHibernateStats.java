/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.impl;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.TabularData;
import net.sf.ehcache.hibernate.management.api.HibernateStats;

public final class NullHibernateStats
implements HibernateStats {
    public static final HibernateStats INSTANCE = new NullHibernateStats();

    private NullHibernateStats() {
    }

    @Override
    public void clearStats() {
    }

    @Override
    public void disableStats() {
    }

    @Override
    public void enableStats() {
    }

    @Override
    public long getCloseStatementCount() {
        return 0L;
    }

    @Override
    public TabularData getCollectionStats() {
        return null;
    }

    @Override
    public long getConnectCount() {
        return 0L;
    }

    public long getDBSQLExecutionSample() {
        return 0L;
    }

    @Override
    public TabularData getEntityStats() {
        return null;
    }

    @Override
    public long getFlushCount() {
        return 0L;
    }

    @Override
    public long getOptimisticFailureCount() {
        return 0L;
    }

    @Override
    public long getPrepareStatementCount() {
        return 0L;
    }

    @Override
    public long getQueryExecutionCount() {
        return 0L;
    }

    @Override
    public double getQueryExecutionRate() {
        return 0.0;
    }

    @Override
    public long getQueryExecutionSample() {
        return 0L;
    }

    @Override
    public TabularData getQueryStats() {
        return null;
    }

    @Override
    public long getSessionCloseCount() {
        return 0L;
    }

    @Override
    public long getSessionOpenCount() {
        return 0L;
    }

    @Override
    public long getSuccessfulTransactionCount() {
        return 0L;
    }

    @Override
    public long getTransactionCount() {
        return 0L;
    }

    @Override
    public boolean isStatisticsEnabled() {
        return false;
    }

    @Override
    public void setStatisticsEnabled(boolean flag) {
    }

    @Override
    public TabularData getCacheRegionStats() {
        return null;
    }

    @Override
    public void removeNotificationListener(NotificationListener listener, NotificationFilter filter2, Object handback) throws ListenerNotFoundException {
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter2, Object handback) throws IllegalArgumentException {
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return null;
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
    }
}

