/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.bean.concurrent.AccumulateCsvResults;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

class IntolerantThreadPoolExecutor<T>
extends ThreadPoolExecutor
implements Spliterator<T> {
    protected final BlockingQueue<OrderedObject<T>> resultQueue = new LinkedBlockingQueue<OrderedObject<T>>();
    protected final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue = new LinkedBlockingQueue<OrderedObject<CsvException>>();
    private ConcurrentNavigableMap<Long, T> resultantBeansMap = null;
    private ListValuedMap<Long, CsvException> thrownExceptionsMap = null;
    protected AccumulateCsvResults<T> accumulateThread = null;
    protected final SortedSet<Long> expectedRecords = new ConcurrentSkipListSet<Long>();
    private final boolean orderedResults;
    protected final Locale errorLocale;
    private Throwable terminalException;

    IntolerantThreadPoolExecutor(boolean orderedResults, Locale errorLocale) {
        super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
        this.orderedResults = orderedResults;
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    public void prepare() {
        this.prestartAllCoreThreads();
        if (this.orderedResults) {
            this.resultantBeansMap = new ConcurrentSkipListMap<Long, T>();
            this.thrownExceptionsMap = new ArrayListValuedHashMap<Long, CsvException>();
            this.accumulateThread = new AccumulateCsvResults<T>(this.resultQueue, this.thrownExceptionsQueue, this.expectedRecords, this.resultantBeansMap, this.thrownExceptionsMap);
            this.accumulateThread.start();
        }
    }

    public void complete() throws InterruptedException {
        this.shutdown();
        this.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        if (this.accumulateThread != null) {
            this.accumulateThread.setMustStop(true);
            this.accumulateThread.join();
        }
        if (this.terminalException != null) {
            throw new RejectedExecutionException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<CsvException> getCapturedExceptions() {
        LinkedList<CsvException> returnList = null;
        if (this.thrownExceptionsMap == null) {
            returnList = this.thrownExceptionsQueue.stream().filter(Objects::nonNull).map(OrderedObject::getElement).collect(Collectors.toList());
        } else {
            returnList = new LinkedList();
            ListValuedMap<Long, CsvException> listValuedMap = this.thrownExceptionsMap;
            synchronized (listValuedMap) {
                LinkedList<CsvException> finalReturnList = returnList;
                this.thrownExceptionsMap.keySet().stream().sorted().forEach(l -> finalReturnList.addAll(this.thrownExceptionsMap.get(l)));
            }
        }
        return returnList;
    }

    @Override
    public List<Runnable> shutdownNow() {
        if (this.accumulateThread != null) {
            this.accumulateThread.setMustStop(true);
            try {
                this.accumulateThread.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        return super.shutdownNow();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t2) {
        super.afterExecute(r, t2);
        if (t2 != null) {
            this.terminalException = t2.getCause() != null ? t2.getCause() : t2;
            this.shutdownNow();
        }
    }

    public Throwable getTerminalException() {
        return this.terminalException;
    }

    protected void checkExceptions() {
        if (this.terminalException != null) {
            if (this.terminalException instanceof CsvException) {
                CsvException csve = (CsvException)this.terminalException;
                throw new RuntimeException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("parsing.error.linenumber"), csve.getLineNumber(), String.join((CharSequence)",", ArrayUtils.nullToEmpty(csve.getLine()))), csve);
            }
            throw new RuntimeException(this.terminalException);
        }
    }

    private boolean isConversionComplete() {
        return this.isTerminated() && (this.accumulateThread == null || !this.accumulateThread.isAlive());
    }

    private boolean areMoreResultsAvailable() {
        this.checkExceptions();
        boolean elementFound = false;
        while (!elementFound && !this.isConversionComplete()) {
            if (this.accumulateThread == null) {
                if (this.resultQueue.isEmpty()) {
                    Thread.yield();
                } else {
                    elementFound = true;
                }
            } else if (this.resultantBeansMap.isEmpty()) {
                Thread.yield();
            } else {
                elementFound = true;
            }
            this.checkExceptions();
        }
        return this.accumulateThread == null ? !this.resultQueue.isEmpty() : !this.resultantBeansMap.isEmpty();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        Object bean = null;
        if (this.areMoreResultsAvailable()) {
            if (this.accumulateThread == null) {
                OrderedObject orderedObject = (OrderedObject)this.resultQueue.poll();
                if (orderedObject != null) {
                    bean = orderedObject.getElement();
                }
            } else {
                Map.Entry mapEntry = this.resultantBeansMap.pollFirstEntry();
                if (mapEntry != null) {
                    bean = mapEntry.getValue();
                }
            }
            if (bean != null) {
                action.accept(bean);
            }
        }
        return bean != null;
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<Object> s2 = null;
        if (this.areMoreResultsAvailable()) {
            if (this.isConversionComplete()) {
                s2 = this.accumulateThread == null ? this.resultQueue.stream().map(OrderedObject::getElement).spliterator() : this.resultantBeansMap.values().spliterator();
            } else {
                ArrayList<Object> c;
                if (this.accumulateThread == null) {
                    int size = this.resultQueue.size();
                    c = new ArrayList<Object>(size);
                    for (int i = 0; i < size; ++i) {
                        OrderedObject orderedObject = (OrderedObject)this.resultQueue.poll();
                        if (orderedObject == null) continue;
                        c.add(orderedObject.getElement());
                    }
                } else {
                    int size = this.resultantBeansMap.size();
                    c = new ArrayList(size);
                    for (int i = 0; i < size; ++i) {
                        Map.Entry mapEntry = this.resultantBeansMap.pollFirstEntry();
                        if (mapEntry == null) continue;
                        c.add(mapEntry.getValue());
                    }
                }
                s2 = c.spliterator();
            }
        }
        return s2;
    }

    @Override
    public long estimateSize() {
        return this.accumulateThread == null ? (long)this.resultQueue.size() : (long)this.resultantBeansMap.size();
    }

    @Override
    public int characteristics() {
        int characteristics = 4352;
        if (this.accumulateThread != null) {
            characteristics |= 0x10;
        }
        return characteristics;
    }
}

