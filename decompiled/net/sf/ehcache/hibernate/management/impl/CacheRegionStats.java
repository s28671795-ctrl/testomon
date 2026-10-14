/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.stat.SecondLevelCacheStatistics
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
import org.hibernate.stat.SecondLevelCacheStatistics;

public class CacheRegionStats
implements Serializable {
    private static final String COMPOSITE_TYPE_NAME = "CacheRegionStats";
    private static final String COMPOSITE_TYPE_DESCRIPTION = "Statistics per Cache-region";
    private static final String[] ITEM_NAMES = new String[]{"region", "shortName", "hitCount", "missCount", "putCount", "hitRatio", "elementCountInMemory", "elementCountOnDisk", "elementCountTotal"};
    private static final String[] ITEM_DESCRIPTIONS = new String[]{"region", "shortName", "hitCount", "missCount", "putCount", "hitRatio", "elementCountInMemory", "elementCountOnDisk", "elementCountTotal"};
    private static final OpenType[] ITEM_TYPES = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.DOUBLE, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
    private static final CompositeType COMPOSITE_TYPE;
    private static final String TABULAR_TYPE_NAME = "Statistics by Cache-region";
    private static final String TABULAR_TYPE_DESCRIPTION = "All Cache Region Statistics";
    private static final String[] INDEX_NAMES;
    private static final TabularType TABULAR_TYPE;
    protected final String region;
    protected final String shortName;
    protected long hitCount;
    protected long missCount;
    protected long putCount;
    protected double hitRatio;
    protected long elementCountInMemory;
    protected long elementCountOnDisk;
    protected long elementCountTotal;

    public CacheRegionStats(String region) {
        this.region = region;
        this.shortName = CacheRegionUtils.determineShortName(region);
    }

    public CacheRegionStats(String region, SecondLevelCacheStatistics src) {
        this(region);
        try {
            this.hitCount = BeanUtils.getLongBeanProperty(src, "hitCount");
            this.missCount = BeanUtils.getLongBeanProperty(src, "missCount");
            this.putCount = BeanUtils.getLongBeanProperty(src, "putCount");
            this.hitRatio = this.determineHitRatio();
            this.elementCountInMemory = BeanUtils.getLongBeanProperty(src, "elementCountInMemory");
            this.elementCountOnDisk = BeanUtils.getLongBeanProperty(src, "elementCountOnDisk");
            this.elementCountTotal = BeanUtils.getLongBeanProperty(src, "elementCountOnDisk");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception retrieving statistics", e);
        }
    }

    public CacheRegionStats(CompositeData cData) {
        int i = 0;
        this.region = (String)cData.get(ITEM_NAMES[i++]);
        this.shortName = (String)cData.get(ITEM_NAMES[i++]);
        this.hitCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.missCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.putCount = (Long)cData.get(ITEM_NAMES[i++]);
        this.hitRatio = (Double)cData.get(ITEM_NAMES[i++]);
        this.elementCountInMemory = (Long)cData.get(ITEM_NAMES[i++]);
        this.elementCountOnDisk = (Long)cData.get(ITEM_NAMES[i++]);
        this.elementCountTotal = (Long)cData.get(ITEM_NAMES[i++]);
    }

    private static int safeParseInt(String s2) {
        try {
            return Integer.parseInt(s2);
        }
        catch (Exception e) {
            return -1;
        }
    }

    protected double determineHitRatio() {
        double result = 0.0;
        long readCount = this.getHitCount() + this.getMissCount();
        if (readCount > 0L) {
            result = (double)this.getHitCount() / (double)readCount;
        }
        return result;
    }

    public String toString() {
        return "region=" + this.getRegion() + "shortName=" + this.getShortName() + ", hitCount=" + this.getHitCount() + ", missCount=" + this.getMissCount() + ", putCount" + this.getPutCount() + ", hitRatio" + this.getHitRatio() + ", elementCountInMemory=" + this.getElementCountInMemory() + ", elementCountOnDisk=" + this.getElementCountOnDisk() + ", elementCountTotal=" + this.getElementCountTotal();
    }

    public String getRegion() {
        return this.region;
    }

    public String getShortName() {
        return this.shortName;
    }

    public long getHitCount() {
        return this.hitCount;
    }

    public long getMissCount() {
        return this.missCount;
    }

    public long getPutCount() {
        return this.putCount;
    }

    public double getHitRatio() {
        return this.hitRatio;
    }

    public long getElementCountInMemory() {
        return this.elementCountInMemory;
    }

    public long getElementCountOnDisk() {
        return this.elementCountOnDisk;
    }

    public long getElementCountTotal() {
        return this.elementCountTotal;
    }

    public CompositeData toCompositeData() {
        try {
            return new CompositeDataSupport(COMPOSITE_TYPE, ITEM_NAMES, new Object[]{this.getRegion(), this.getShortName(), this.getHitCount(), this.getMissCount(), this.getPutCount(), this.getHitRatio(), this.getElementCountInMemory(), this.getElementCountOnDisk(), this.getElementCountTotal()});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData newTabularDataInstance() {
        return new TabularDataSupport(TABULAR_TYPE);
    }

    public static CacheRegionStats[] fromTabularData(TabularData tabularData) {
        ArrayList<CacheRegionStats> countList = new ArrayList<CacheRegionStats>(tabularData.size());
        Iterator<?> pos = tabularData.values().iterator();
        while (pos.hasNext()) {
            countList.add(new CacheRegionStats((CompositeData)pos.next()));
        }
        return countList.toArray(new CacheRegionStats[countList.size()]);
    }

    static {
        INDEX_NAMES = new String[]{"region"};
        try {
            COMPOSITE_TYPE = new CompositeType(COMPOSITE_TYPE_NAME, COMPOSITE_TYPE_DESCRIPTION, ITEM_NAMES, ITEM_DESCRIPTIONS, ITEM_TYPES);
            TABULAR_TYPE = new TabularType(TABULAR_TYPE_NAME, TABULAR_TYPE_DESCRIPTION, COMPOSITE_TYPE, INDEX_NAMES);
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

