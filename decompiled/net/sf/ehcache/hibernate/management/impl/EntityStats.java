/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.stat.EntityStatistics
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
import net.sf.ehcache.hibernate.management.impl.CacheRegionUtils;
import org.hibernate.stat.EntityStatistics;

public class EntityStats
implements Serializable {
    private static final String COMPOSITE_TYPE_NAME = "EntityStats";
    private static final String COMPOSITE_TYPE_DESCRIPTION = "Statistics per Entity";
    private static final String[] ITEM_NAMES = new String[]{"name", "shortName", "loadCount", "updateCount", "insertCount", "deleteCount", "fetchCount", "optimisticFailureCount"};
    private static final String[] ITEM_DESCRIPTIONS = new String[]{"name", "shortName", "loadCount", "updateCount", "insertCount", "deleteCount", "fetchCount", "optimisticFailureCount"};
    private static final OpenType[] ITEM_TYPES = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
    private static final CompositeType COMPOSITE_TYPE;
    private static final String TABULAR_TYPE_NAME = "Statistics by Entity";
    private static final String TABULAR_TYPE_DESCRIPTION = "All Entity Statistics";
    private static final String[] INDEX_NAMES;
    private static final TabularType TABULAR_TYPE;
    protected final String name;
    protected final String shortName;
    protected long loadCount;
    protected long updateCount;
    protected long insertCount;
    protected long deleteCount;
    protected long fetchCount;
    protected long optimisticFailureCount;

    public EntityStats(String name) {
        this.name = name;
        this.shortName = CacheRegionUtils.determineShortName(name);
    }

    public EntityStats(String name, EntityStatistics src) {
        this(name);
        try {
            this.loadCount = BeanUtils.getLongBeanProperty(src, "loadCount");
            this.updateCount = BeanUtils.getLongBeanProperty(src, "updateCount");
            this.insertCount = BeanUtils.getLongBeanProperty(src, "insertCount");
            this.deleteCount = BeanUtils.getLongBeanProperty(src, "deleteCount");
            this.fetchCount = BeanUtils.getLongBeanProperty(src, "fetchCount");
            this.optimisticFailureCount = BeanUtils.getLongBeanProperty(src, "optimisticFailureCount");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception retrieving statistics", e);
        }
    }

    public EntityStats(CompositeData cData) {
        int i = 0;
        this.name = (String)cData.get(ITEM_NAMES[i++]);
        this.shortName = (String)cData.get(ITEM_NAMES[i++]);
        this.loadCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.updateCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.insertCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.deleteCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.fetchCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.optimisticFailureCount = (Long)cData.get(ITEM_NAMES[i++]);
    }

    private static int safeParseInt(String s2) {
        try {
            return Integer.parseInt(s2);
        }
        catch (Exception e) {
            return -1;
        }
    }

    public void add(EntityStats stats) {
        this.loadCount += stats.getLoadCount();
        this.updateCount += stats.getUpdateCount();
        this.insertCount += stats.getInsertCount();
        this.deleteCount += stats.getDeleteCount();
        this.fetchCount += stats.getFetchCount();
        this.optimisticFailureCount += stats.getOptimisticFailureCount();
    }

    public String toString() {
        return "name=" + this.name + ", shortName=" + this.shortName + ",loadCount=" + this.loadCount + ", updateCount=" + this.updateCount + ", insertCount=" + this.insertCount + ", deleteCount=" + this.deleteCount + ", fetchCount=" + this.fetchCount + ", optimisticFailureCount" + this.optimisticFailureCount;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public long getLoadCount() {
        return this.loadCount;
    }

    public long getUpdateCount() {
        return this.updateCount;
    }

    public long getInsertCount() {
        return this.insertCount;
    }

    public long getDeleteCount() {
        return this.deleteCount;
    }

    public long getFetchCount() {
        return this.fetchCount;
    }

    public long getOptimisticFailureCount() {
        return this.optimisticFailureCount;
    }

    public CompositeData toCompositeData() {
        try {
            return new CompositeDataSupport(COMPOSITE_TYPE, ITEM_NAMES, new Object[]{this.name, this.shortName, this.loadCount, this.updateCount, this.insertCount, this.deleteCount, this.fetchCount, this.optimisticFailureCount});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData newTabularDataInstance() {
        return new TabularDataSupport(TABULAR_TYPE);
    }

    public static EntityStats[] fromTabularData(TabularData tabularData) {
        ArrayList<EntityStats> countList = new ArrayList<EntityStats>(tabularData.size());
        Iterator<?> pos = tabularData.values().iterator();
        while (pos.hasNext()) {
            countList.add(new EntityStats((CompositeData)pos.next()));
        }
        return countList.toArray(new EntityStats[countList.size()]);
    }

    static {
        INDEX_NAMES = new String[]{"name"};
        try {
            COMPOSITE_TYPE = new CompositeType(COMPOSITE_TYPE_NAME, COMPOSITE_TYPE_DESCRIPTION, ITEM_NAMES, ITEM_DESCRIPTIONS, ITEM_TYPES);
            TABULAR_TYPE = new TabularType(TABULAR_TYPE_NAME, TABULAR_TYPE_DESCRIPTION, COMPOSITE_TYPE, INDEX_NAMES);
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

