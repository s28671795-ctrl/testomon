/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.SessionFactory
 *  org.hibernate.stat.Statistics
 */
package net.sf.ehcache.hibernate.management.impl;

import java.util.ArrayList;
import javax.management.MBeanNotificationInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import net.sf.ehcache.hibernate.management.api.HibernateStats;
import net.sf.ehcache.hibernate.management.impl.BaseEmitterBean;
import net.sf.ehcache.hibernate.management.impl.CacheRegionStats;
import net.sf.ehcache.hibernate.management.impl.CollectionStats;
import net.sf.ehcache.hibernate.management.impl.EntityStats;
import net.sf.ehcache.hibernate.management.impl.QueryStats;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

public class HibernateStatsImpl
extends BaseEmitterBean
implements HibernateStats {
    private static final double MILLIS_PER_SECOND = 1000.0;
    private static final MBeanNotificationInfo NOTIFICATION_INFO;
    private final SessionFactory sessionFactory;

    public HibernateStatsImpl(SessionFactory sessionFactory) throws NotCompliantMBeanException {
        super(HibernateStats.class);
        this.sessionFactory = sessionFactory;
    }

    private Statistics getStatistics() {
        return this.sessionFactory.getStatistics();
    }

    @Override
    public void clearStats() {
        this.getStatistics().clear();
        this.sendNotification("CacheStatisticsReset");
    }

    @Override
    public void disableStats() {
        this.setStatisticsEnabled(false);
    }

    @Override
    public void enableStats() {
        this.setStatisticsEnabled(true);
    }

    @Override
    public long getCloseStatementCount() {
        return this.getStatistics().getCloseStatementCount();
    }

    @Override
    public long getConnectCount() {
        return this.getStatistics().getConnectCount();
    }

    public long getDBSQLExecutionSample() {
        throw new UnsupportedOperationException("Use getQueryExecutionCount() instead");
    }

    @Override
    public long getFlushCount() {
        return this.getStatistics().getFlushCount();
    }

    @Override
    public long getOptimisticFailureCount() {
        return this.getStatistics().getOptimisticFailureCount();
    }

    @Override
    public long getPrepareStatementCount() {
        return this.getStatistics().getPrepareStatementCount();
    }

    @Override
    public long getQueryExecutionCount() {
        return this.getStatistics().getQueryExecutionCount();
    }

    @Override
    public double getQueryExecutionRate() {
        long startTime = this.getStatistics().getStartTime();
        long now = System.currentTimeMillis();
        double deltaSecs = (double)(now - startTime) / 1000.0;
        return (double)this.getQueryExecutionCount() / deltaSecs;
    }

    @Override
    public long getQueryExecutionSample() {
        throw new UnsupportedOperationException("TODO: need to impl. rates for query execution");
    }

    @Override
    public long getSessionCloseCount() {
        return this.getStatistics().getSessionCloseCount();
    }

    @Override
    public long getSessionOpenCount() {
        return this.getStatistics().getSessionOpenCount();
    }

    @Override
    public long getSuccessfulTransactionCount() {
        return this.getStatistics().getSuccessfulTransactionCount();
    }

    @Override
    public long getTransactionCount() {
        return this.getStatistics().getTransactionCount();
    }

    @Override
    public boolean isStatisticsEnabled() {
        return this.getStatistics().isStatisticsEnabled();
    }

    @Override
    public void setStatisticsEnabled(boolean flag) {
        this.getStatistics().setStatisticsEnabled(flag);
        this.sendNotification("CacheStatisticsEnabled", flag);
    }

    @Override
    public TabularData getEntityStats() {
        ArrayList<CompositeData> result = new ArrayList<CompositeData>();
        Statistics statistics = this.getStatistics();
        for (String entity : statistics.getEntityNames()) {
            EntityStats entityStats = new EntityStats(entity, statistics.getEntityStatistics(entity));
            result.add(entityStats.toCompositeData());
        }
        TabularData td = EntityStats.newTabularDataInstance();
        td.putAll(result.toArray(new CompositeData[result.size()]));
        return td;
    }

    @Override
    public TabularData getCollectionStats() {
        ArrayList<CompositeData> result = new ArrayList<CompositeData>();
        Statistics statistics = this.getStatistics();
        for (String roleName : statistics.getCollectionRoleNames()) {
            CollectionStats collectionStats = new CollectionStats(roleName, statistics.getCollectionStatistics(roleName));
            result.add(collectionStats.toCompositeData());
        }
        TabularData td = CollectionStats.newTabularDataInstance();
        td.putAll(result.toArray(new CompositeData[result.size()]));
        return td;
    }

    @Override
    public TabularData getQueryStats() {
        ArrayList<CompositeData> result = new ArrayList<CompositeData>();
        Statistics statistics = this.getStatistics();
        for (String query : statistics.getQueries()) {
            QueryStats queryStats = new QueryStats(query, statistics.getQueryStatistics(query));
            result.add(queryStats.toCompositeData());
        }
        TabularData td = QueryStats.newTabularDataInstance();
        td.putAll(result.toArray(new CompositeData[result.size()]));
        return td;
    }

    @Override
    public TabularData getCacheRegionStats() {
        ArrayList<CompositeData> list = new ArrayList<CompositeData>();
        Statistics statistics = this.getStatistics();
        for (String region : statistics.getSecondLevelCacheRegionNames()) {
            CacheRegionStats l2CacheStats = new CacheRegionStats(region, statistics.getSecondLevelCacheStatistics(region));
            list.add(l2CacheStats.toCompositeData());
        }
        TabularData td = CacheRegionStats.newTabularDataInstance();
        td.putAll(list.toArray(new CompositeData[list.size()]));
        return td;
    }

    @Override
    protected void doDispose() {
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{NOTIFICATION_INFO};
    }

    static {
        String[] notifTypes = new String[]{};
        String name = Notification.class.getName();
        String description = "Hibernate Statistics Event";
        NOTIFICATION_INFO = new MBeanNotificationInfo(notifTypes, name, "Hibernate Statistics Event");
    }
}

