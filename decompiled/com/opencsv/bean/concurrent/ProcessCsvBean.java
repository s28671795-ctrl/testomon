/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.opencsv.exceptions.CsvRuntimeException;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;

public class ProcessCsvBean<T>
implements Runnable {
    private final long lineNumber;
    private final MappingStrategy<T> mappingStrategy;
    private final T bean;
    private final BlockingQueue<OrderedObject<String[]>> resultantLineQueue;
    private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;
    private final SortedSet<Long> expectedRecords;
    private final CsvExceptionHandler exceptionHandler;

    public ProcessCsvBean(long lineNumber, MappingStrategy<T> mappingStrategy, T bean, BlockingQueue<OrderedObject<String[]>> resultantLineQueue, BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue, SortedSet<Long> expectedRecords, CsvExceptionHandler exceptionHandler) {
        this.lineNumber = lineNumber;
        this.mappingStrategy = mappingStrategy;
        this.bean = bean;
        this.resultantLineQueue = resultantLineQueue;
        this.thrownExceptionsQueue = thrownExceptionsQueue;
        this.expectedRecords = expectedRecords;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        try {
            OpencsvUtils.queueRefuseToAcceptDefeat(this.resultantLineQueue, new OrderedObject<String[]>(this.lineNumber, this.mappingStrategy.transmuteBean(this.bean)));
        }
        catch (CsvChainedException | CsvFieldAssignmentException e) {
            this.expectedRecords.remove(this.lineNumber);
            OpencsvUtils.handleException(e, this.lineNumber, this.exceptionHandler, this.thrownExceptionsQueue);
        }
        catch (CsvRuntimeException csvre) {
            this.expectedRecords.remove(this.lineNumber);
            throw csvre;
        }
        catch (Exception t2) {
            this.expectedRecords.remove(this.lineNumber);
            throw new RuntimeException(t2);
        }
    }
}

