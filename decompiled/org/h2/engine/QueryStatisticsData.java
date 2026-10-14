/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryStatisticsData {
    private static final Comparator<QueryEntry> QUERY_ENTRY_COMPARATOR = Comparator.comparingLong(queryEntry -> queryEntry.lastUpdateTime);
    private final HashMap<String, QueryEntry> map = new HashMap();
    private int maxQueryEntries;

    public QueryStatisticsData(int n) {
        this.maxQueryEntries = n;
    }

    public synchronized void setMaxQueryEntries(int n) {
        this.maxQueryEntries = n;
    }

    public synchronized List<QueryEntry> getQueries() {
        ArrayList<QueryEntry> arrayList = new ArrayList<QueryEntry>(this.map.values());
        arrayList.sort(QUERY_ENTRY_COMPARATOR);
        return arrayList.subList(0, Math.min(arrayList.size(), this.maxQueryEntries));
    }

    public synchronized void update(String string, long l, long l2) {
        QueryEntry queryEntry = this.map.get(string);
        if (queryEntry == null) {
            queryEntry = new QueryEntry(string);
            this.map.put(string, queryEntry);
        }
        queryEntry.update(l, l2);
        if ((float)this.map.size() > (float)this.maxQueryEntries * 1.5f) {
            ArrayList<QueryEntry> arrayList = new ArrayList<QueryEntry>(this.map.values());
            arrayList.sort(QUERY_ENTRY_COMPARATOR);
            HashSet<QueryEntry> hashSet = new HashSet<QueryEntry>(arrayList.subList(0, arrayList.size() / 3));
            Iterator<Map.Entry<String, QueryEntry>> iterator2 = this.map.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<String, QueryEntry> entry = iterator2.next();
                if (!hashSet.contains(entry.getValue())) continue;
                iterator2.remove();
            }
        }
    }

    public static final class QueryEntry {
        public final String sqlStatement;
        public int count;
        public long lastUpdateTime;
        public long executionTimeMinNanos;
        public long executionTimeMaxNanos;
        public long executionTimeCumulativeNanos;
        public long rowCountMin;
        public long rowCountMax;
        public long rowCountCumulative;
        public double executionTimeMeanNanos;
        public double rowCountMean;
        private double executionTimeM2Nanos;
        private double rowCountM2;

        public QueryEntry(String string) {
            this.sqlStatement = string;
        }

        void update(long l, long l2) {
            ++this.count;
            this.executionTimeMinNanos = Math.min(l, this.executionTimeMinNanos);
            this.executionTimeMaxNanos = Math.max(l, this.executionTimeMaxNanos);
            this.rowCountMin = Math.min(l2, this.rowCountMin);
            this.rowCountMax = Math.max(l2, this.rowCountMax);
            double d = (double)l2 - this.rowCountMean;
            this.rowCountMean += d / (double)this.count;
            this.rowCountM2 += d * ((double)l2 - this.rowCountMean);
            double d2 = (double)l - this.executionTimeMeanNanos;
            this.executionTimeMeanNanos += d2 / (double)this.count;
            this.executionTimeM2Nanos += d2 * ((double)l - this.executionTimeMeanNanos);
            this.executionTimeCumulativeNanos += l;
            this.rowCountCumulative += l2;
            this.lastUpdateTime = System.currentTimeMillis();
        }

        public double getExecutionTimeStandardDeviation() {
            return Math.sqrt(this.executionTimeM2Nanos / (double)this.count);
        }

        public double getRowCountStandardDeviation() {
            return Math.sqrt(this.rowCountM2 / (double)this.count);
        }
    }
}

