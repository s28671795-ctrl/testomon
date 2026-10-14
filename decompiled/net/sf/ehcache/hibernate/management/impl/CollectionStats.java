/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.stat.CollectionStatistics
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
import org.hibernate.stat.CollectionStatistics;

public class CollectionStats
implements Serializable {
    private static final String COMPOSITE_TYPE_NAME = "CollectionsStats";
    private static final String COMPOSITE_TYPE_DESCRIPTION = "Statistics per Collections";
    private static final String[] ITEM_NAMES = new String[]{"roleName", "shortName", "loadCount", "fetchCount", "updateCount", "removeCount", "recreateCount"};
    private static final String[] ITEM_DESCRIPTIONS = new String[]{"roleName", "shortName", "loadCount", "fetchCount", "updateCount", "removeCount", "recreateCount"};
    private static final OpenType[] ITEM_TYPES = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
    private static final CompositeType COMPOSITE_TYPE;
    private static final String TABULAR_TYPE_NAME = "Statistics by Collection";
    private static final String TABULAR_TYPE_DESCRIPTION = "All Collection Statistics";
    private static final String[] INDEX_NAMES;
    private static final TabularType TABULAR_TYPE;
    protected final String roleName;
    protected final String shortName;
    protected long loadCount;
    protected long fetchCount;
    protected long updateCount;
    protected long removeCount;
    protected long recreateCount;

    public CollectionStats(String roleName) {
        this.roleName = roleName;
        this.shortName = CacheRegionUtils.determineShortName(roleName);
    }

    public CollectionStats(String name, CollectionStatistics src) {
        this(name);
        try {
            this.loadCount = BeanUtils.getLongBeanProperty(src, "loadCount");
            this.fetchCount = BeanUtils.getLongBeanProperty(src, "fetchCount");
            this.updateCount = BeanUtils.getLongBeanProperty(src, "updateCount");
            this.removeCount = BeanUtils.getLongBeanProperty(src, "removeCount");
            this.recreateCount = BeanUtils.getLongBeanProperty(src, "recreateCount");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception retrieving statistics", e);
        }
    }

    public CollectionStats(CompositeData cData) {
        int i = 0;
        this.roleName = (String)cData.get(ITEM_NAMES[i++]);
        this.shortName = (String)cData.get(ITEM_NAMES[i++]);
        this.loadCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.fetchCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.updateCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.removeCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.recreateCount = (Long)cData.get(ITEM_NAMES[i++]);
    }

    private static int safeParseInt(String s2) {
        try {
            return Integer.parseInt(s2);
        }
        catch (Exception e) {
            return -1;
        }
    }

    public void add(CollectionStats stats) {
        this.loadCount += stats.getLoadCount();
        this.fetchCount += stats.getFetchCount();
        this.updateCount += stats.getUpdateCount();
        this.removeCount += stats.getRemoveCount();
        this.recreateCount += stats.getRecreateCount();
    }

    public String toString() {
        return "roleName=" + this.roleName + "shortName=" + this.shortName + ", loadCount=" + this.loadCount + ", fetchCount=" + this.fetchCount + ", updateCount=" + this.updateCount + ", removeCount=" + this.removeCount + ", recreateCount" + this.recreateCount;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public long getLoadCount() {
        return this.loadCount;
    }

    public long getFetchCount() {
        return this.fetchCount;
    }

    public long getUpdateCount() {
        return this.updateCount;
    }

    public long getRemoveCount() {
        return this.removeCount;
    }

    public long getRecreateCount() {
        return this.recreateCount;
    }

    public CompositeData toCompositeData() {
        try {
            return new CompositeDataSupport(COMPOSITE_TYPE, ITEM_NAMES, new Object[]{this.roleName, this.shortName, this.loadCount, this.fetchCount, this.updateCount, this.removeCount, this.recreateCount});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData newTabularDataInstance() {
        return new TabularDataSupport(TABULAR_TYPE);
    }

    public static CollectionStats[] fromTabularData(TabularData tabularData) {
        ArrayList<CollectionStats> countList = new ArrayList<CollectionStats>(tabularData.size());
        Iterator<?> pos = tabularData.values().iterator();
        while (pos.hasNext()) {
            countList.add(new CollectionStats((CompositeData)pos.next()));
        }
        return countList.toArray(new CollectionStats[countList.size()]);
    }

    static {
        INDEX_NAMES = new String[]{"roleName"};
        try {
            COMPOSITE_TYPE = new CompositeType(COMPOSITE_TYPE_NAME, COMPOSITE_TYPE_DESCRIPTION, ITEM_NAMES, ITEM_DESCRIPTIONS, ITEM_TYPES);
            TABULAR_TYPE = new TabularType(TABULAR_TYPE_NAME, TABULAR_TYPE_DESCRIPTION, COMPOSITE_TYPE, INDEX_NAMES);
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

