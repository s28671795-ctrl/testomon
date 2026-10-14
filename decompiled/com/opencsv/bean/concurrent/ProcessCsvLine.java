/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

public class ProcessCsvLine<T>
implements Runnable {
    private final long lineNumber;
    private final MappingStrategy<? extends T> mapper;
    private final CsvToBeanFilter filter;
    private final List<BeanVerifier<T>> verifiers;
    private final String[] line;
    private final BlockingQueue<OrderedObject<T>> resultantBeanQueue;
    private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;
    private final SortedSet<Long> expectedRecords;
    private final CsvExceptionHandler exceptionHandler;

    public ProcessCsvLine(long lineNumber, MappingStrategy<? extends T> mapper2, CsvToBeanFilter filter2, List<BeanVerifier<T>> verifiers, String[] line, BlockingQueue<OrderedObject<T>> resultantBeanQueue, BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue, SortedSet<Long> expectedRecords, CsvExceptionHandler exceptionHandler) {
        this.lineNumber = lineNumber;
        this.mapper = mapper2;
        this.filter = filter2;
        this.verifiers = ObjectUtils.defaultIfNull(new ArrayList<BeanVerifier<T>>(verifiers), Collections.emptyList());
        this.line = ArrayUtils.clone(line);
        this.resultantBeanQueue = resultantBeanQueue;
        this.thrownExceptionsQueue = thrownExceptionsQueue;
        this.expectedRecords = expectedRecords;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        try {
            if (this.filter == null || this.filter.allowLine(this.line)) {
                T obj = this.processLine();
                ListIterator<BeanVerifier<T>> verifierList = this.verifiers.listIterator();
                boolean keep = true;
                while (keep && verifierList.hasNext()) {
                    keep = verifierList.next().verifyBean(obj);
                }
                if (keep) {
                    OpencsvUtils.queueRefuseToAcceptDefeat(this.resultantBeanQueue, new OrderedObject<T>(this.lineNumber, obj));
                } else {
                    this.expectedRecords.remove(this.lineNumber);
                }
            } else {
                this.expectedRecords.remove(this.lineNumber);
            }
        }
        catch (CsvException e) {
            this.expectedRecords.remove(this.lineNumber);
            e.setLine(this.line);
            OpencsvUtils.handleException(e, this.lineNumber, this.exceptionHandler, this.thrownExceptionsQueue);
        }
        catch (Exception e) {
            this.expectedRecords.remove(this.lineNumber);
            throw new RuntimeException(e);
        }
    }

    private T processLine() throws CsvBeanIntrospectionException, CsvBadConverterException, CsvFieldAssignmentException, CsvChainedException {
        return this.mapper.populateNewBean(this.line);
    }
}

