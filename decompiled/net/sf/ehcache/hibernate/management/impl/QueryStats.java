/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.stat.QueryStatistics
 */
package net.sf.ehcache.hibernate.management.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import net.sf.ehcache.hibernate.management.impl.BeanUtils;
import org.hibernate.stat.QueryStatistics;

public class QueryStats
implements Serializable {
    private static final String COMPOSITE_TYPE_NAME = "QueryStats";
    private static final String COMPOSITE_TYPE_DESCRIPTION = "Statistics per Query";
    private static final String[] ITEM_NAMES = new String[]{"query", "cacheHitCount", "cacheMissCount", "cachePutCount", "executionCount", "executionRowCount", "executionAvgTime", "executionMaxTime", "executionMinTime"};
    private static final String[] ITEM_DESCRIPTIONS = new String[]{"query", "cacheHitCount", "cacheMissCount", "cachePutCount", "executionCount", "executionRowCount", "executionAvgTime", "executionMaxTime", "executionMinTime"};
    private static final OpenType[] ITEM_TYPES = new OpenType[]{SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
    private static final CompositeType COMPOSITE_TYPE;
    private static final String TABULAR_TYPE_NAME = "Statistics by Query";
    private static final String TABULAR_TYPE_DESCRIPTION = "All Query Statistics";
    private static final String[] INDEX_NAMES;
    private static final TabularType TABULAR_TYPE;
    protected final String query;
    protected long cacheHitCount;
    protected long cacheMissCount;
    protected long cachePutCount;
    protected long executionCount;
    protected long executionRowCount;
    protected long executionAvgTime;
    protected long executionMaxTime;
    protected long executionMinTime;

    public QueryStats(String name) {
        this.query = name;
    }

    public QueryStats(String name, QueryStatistics src) {
        this(name);
        try {
            this.cacheHitCount = BeanUtils.getLongBeanProperty(src, "cacheHitCount");
            this.cacheMissCount = BeanUtils.getLongBeanProperty(src, "cacheMissCount");
            this.cachePutCount = BeanUtils.getLongBeanProperty(src, "cachePutCount");
            this.executionCount = BeanUtils.getLongBeanProperty(src, "executionCount");
            this.executionRowCount = BeanUtils.getLongBeanProperty(src, "executionRowCount");
            this.executionAvgTime = BeanUtils.getLongBeanProperty(src, "executionAvgTime");
            this.executionMaxTime = BeanUtils.getLongBeanProperty(src, "executionMaxTime");
            this.executionMinTime = BeanUtils.getLongBeanProperty(src, "executionMinTime");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception retrieving statistics", e);
        }
    }

    public QueryStats(CompositeData cData) {
        int i = 0;
        this.query = (String)cData.get(ITEM_NAMES[i++]);
        this.cacheHitCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.cacheMissCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.cachePutCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.executionCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.executionRowCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.executionAvgTime = (Long)cData.get(ITEM_NAMES[i++]);
        this.executionMaxTime = (Long)cData.get(ITEM_NAMES[i++]);
        this.executionMinTime = (Long)cData.get(ITEM_NAMES[i++]);
    }

    private static int safeParseInt(String s2) {
        try {
            return Integer.parseInt(s2);
        }
        catch (Exception e) {
            return -1;
        }
    }

    public void add(QueryStats stats) {
        this.cacheHitCount += stats.getCacheHitCount();
        this.cacheMissCount += stats.getCacheMissCount();
        this.cachePutCount += stats.getCachePutCount();
        this.executionCount += stats.getExecutionCount();
        this.executionRowCount += stats.getExecutionRowCount();
        this.executionAvgTime += stats.getExecutionAvgTime();
        this.executionMaxTime += stats.getExecutionMaxTime();
        this.executionMinTime += stats.getExecutionMinTime();
    }

    public String toString() {
        return "query=" + this.query + ", cacheHitCount=" + this.cacheHitCount + ", cacheMissCount=" + this.cacheMissCount + ", cachePutCount=" + this.cachePutCount + ", executionCount=" + this.executionCount + ", executionRowCount=" + this.executionRowCount + ", executionAvgTime=" + this.executionAvgTime + ", executionMaxTime=" + this.executionMaxTime + ", executionMinTime=" + this.executionMinTime;
    }

    public String getQuery() {
        return this.query;
    }

    public long getCacheHitCount() {
        return this.cacheHitCount;
    }

    public long getCacheMissCount() {
        return this.cacheMissCount;
    }

    public long getCachePutCount() {
        return this.cachePutCount;
    }

    public long getExecutionCount() {
        return this.executionCount;
    }

    public long getExecutionRowCount() {
        return this.executionRowCount;
    }

    public long getExecutionAvgTime() {
        return this.executionAvgTime;
    }

    public long getExecutionMaxTime() {
        return this.executionMaxTime;
    }

    public long getExecutionMinTime() {
        return this.executionMinTime;
    }

    public CompositeData toCompositeData() {
        try {
            return new CompositeDataSupport(COMPOSITE_TYPE, ITEM_NAMES, new Object[]{this.query, this.cacheHitCount, this.cacheMissCount, this.cachePutCount, this.executionCount, this.executionRowCount, this.executionAvgTime, this.executionMaxTime, this.executionMinTime});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData newTabularDataInstance() {
        return new TabularDataSupport(TABULAR_TYPE);
    }

    public static QueryStats[] fromTabularData(TabularData tabularData) {
        ArrayList<QueryStats> countList = new ArrayList<QueryStats>(tabularData.size());
        Iterator<?> pos = tabularData.values().iterator();
        while (pos.hasNext()) {
            countList.add(new QueryStats((CompositeData)pos.next()));
        }
        return countList.toArray(new QueryStats[countList.size()]);
    }

    static {
        INDEX_NAMES = new String[]{"query"};
        try {
            COMPOSITE_TYPE = new CompositeType(COMPOSITE_TYPE_NAME, COMPOSITE_TYPE_DESCRIPTION, ITEM_NAMES, ITEM_DESCRIPTIONS, ITEM_TYPES);
            TABULAR_TYPE = new TabularType(TABULAR_TYPE_NAME, TABULAR_TYPE_DESCRIPTION, COMPOSITE_TYPE, INDEX_NAMES);
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

