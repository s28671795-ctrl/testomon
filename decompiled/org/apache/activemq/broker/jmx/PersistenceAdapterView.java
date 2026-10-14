/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.activemq.broker.jmx.PersistenceAdapterViewMBean;
import org.apache.activemq.management.TimeStatisticImpl;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.PersistenceAdapterStatistics;

public class PersistenceAdapterView
implements PersistenceAdapterViewMBean {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String name;
    private final PersistenceAdapter persistenceAdapter;
    private Callable<String> inflightTransactionViewCallable;
    private Callable<String> dataViewCallable;
    private PersistenceAdapterStatistics persistenceAdapterStatistics;

    public PersistenceAdapterView(PersistenceAdapter adapter) {
        this.name = adapter.toString();
        this.persistenceAdapter = adapter;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTransactions() {
        return this.invoke(this.inflightTransactionViewCallable);
    }

    @Override
    public String getData() {
        return this.invoke(this.dataViewCallable);
    }

    @Override
    public long getSize() {
        return this.persistenceAdapter.size();
    }

    @Override
    public String getStatistics() {
        return this.serializePersistenceAdapterStatistics();
    }

    @Override
    public String resetStatistics() {
        String result = this.serializePersistenceAdapterStatistics();
        if (this.persistenceAdapterStatistics != null) {
            this.persistenceAdapterStatistics.reset();
        }
        return result;
    }

    private String invoke(Callable<String> callable) {
        String result = null;
        if (callable != null) {
            try {
                result = callable.call();
            }
            catch (Exception e) {
                result = e.toString();
            }
        }
        return result;
    }

    private String serializePersistenceAdapterStatistics() {
        if (this.persistenceAdapterStatistics != null) {
            try {
                HashMap<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
                result.put("writeTime", this.getTimeStatisticAsMap(this.persistenceAdapterStatistics.getWriteTime()));
                result.put("readTime", this.getTimeStatisticAsMap(this.persistenceAdapterStatistics.getReadTime()));
                return mapper.writeValueAsString(result);
            }
            catch (IOException e) {
                return e.toString();
            }
        }
        return null;
    }

    private Map<String, Object> getTimeStatisticAsMap(TimeStatisticImpl timeStatistic) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("count", timeStatistic.getCount());
        result.put("maxTime", timeStatistic.getMaxTime());
        result.put("minTime", timeStatistic.getMinTime());
        result.put("totalTime", timeStatistic.getTotalTime());
        result.put("averageTime", timeStatistic.getAverageTime());
        result.put("averageTimeExMinMax", timeStatistic.getAverageTimeExcludingMinMax());
        result.put("averagePerSecond", timeStatistic.getAveragePerSecond());
        result.put("averagePerSecondExMinMax", timeStatistic.getAveragePerSecondExcludingMinMax());
        return result;
    }

    public void setDataViewCallable(Callable<String> dataViewCallable) {
        this.dataViewCallable = dataViewCallable;
    }

    public void setInflightTransactionViewCallable(Callable<String> inflightTransactionViewCallable) {
        this.inflightTransactionViewCallable = inflightTransactionViewCallable;
    }

    public void setPersistenceAdapterStatistics(PersistenceAdapterStatistics persistenceAdapterStatistics) {
        this.persistenceAdapterStatistics = persistenceAdapterStatistics;
    }
}

