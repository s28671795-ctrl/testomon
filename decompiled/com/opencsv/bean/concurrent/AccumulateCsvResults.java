/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvException;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.collections4.ListValuedMap;

class AccumulateCsvResults<T>
extends Thread {
    private final BlockingQueue<OrderedObject<T>> resultantBeansQueue;
    private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;
    private final SortedSet<Long> expectedRecords;
    private final ConcurrentMap<Long, T> resultantBeanMap;
    private boolean mustStop = false;
    private final ListValuedMap<Long, CsvException> thrownExceptionsMap;

    AccumulateCsvResults(BlockingQueue<OrderedObject<T>> resultantBeansQueue, BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue, SortedSet<Long> expectedRecords, ConcurrentMap<Long, T> resultantBeanMap, ListValuedMap<Long, CsvException> thrownExceptionsMap) {
        this.resultantBeansQueue = resultantBeansQueue;
        this.thrownExceptionsQueue = thrownExceptionsQueue;
        this.expectedRecords = expectedRecords;
        this.resultantBeanMap = resultantBeanMap;
        this.thrownExceptionsMap = thrownExceptionsMap;
    }

    private synchronized boolean isMustStop() {
        return this.mustStop;
    }

    synchronized void setMustStop(boolean mustStop) {
        this.mustStop = mustStop;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (!(this.isMustStop() && this.resultantBeansQueue.isEmpty() && this.thrownExceptionsQueue.isEmpty())) {
            OrderedObject orderedObject = null;
            if (!this.expectedRecords.isEmpty()) {
                orderedObject = this.resultantBeansQueue.stream().filter(e -> this.expectedRecords.first().equals(e.getOrdinal())).findAny().orElse(null);
            }
            while (orderedObject != null) {
                this.resultantBeansQueue.remove(orderedObject);
                this.expectedRecords.remove(this.expectedRecords.first());
                this.resultantBeanMap.put(orderedObject.getOrdinal(), orderedObject.getElement());
                if (!this.expectedRecords.isEmpty()) {
                    orderedObject = this.resultantBeansQueue.stream().filter(e -> this.expectedRecords.first().equals(e.getOrdinal())).findAny().orElse(null);
                    continue;
                }
                orderedObject = null;
            }
            while (!this.thrownExceptionsQueue.isEmpty()) {
                OrderedObject capturedException = (OrderedObject)this.thrownExceptionsQueue.poll();
                if (capturedException == null) continue;
                ListValuedMap<Long, CsvException> listValuedMap = this.thrownExceptionsMap;
                synchronized (listValuedMap) {
                    this.thrownExceptionsMap.put(capturedException.getOrdinal(), (CsvException)capturedException.getElement());
                }
            }
            Thread.yield();
        }
    }
}

