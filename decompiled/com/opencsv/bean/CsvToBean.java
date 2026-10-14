/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.concurrent.CompleteFileReader;
import com.opencsv.bean.concurrent.LineExecutor;
import com.opencsv.bean.concurrent.ProcessCsvLine;
import com.opencsv.bean.concurrent.SingleLineReader;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.exceptionhandler.ExceptionHandlerQueue;
import com.opencsv.bean.exceptionhandler.ExceptionHandlerThrow;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.ObjectUtils;

public class CsvToBean<T>
implements Iterable<T> {
    private final List<CsvException> capturedExceptions = new LinkedList<CsvException>();
    private MappingStrategy<? extends T> mappingStrategy;
    private CSVReader csvReader;
    private CsvToBeanFilter filter = null;
    private CsvExceptionHandler exceptionHandler = new ExceptionHandlerThrow();
    private boolean orderedResults = true;
    private LineExecutor<T> executor;
    private Locale errorLocale = Locale.getDefault();
    private List<BeanVerifier<T>> verifiers = Collections.emptyList();
    private boolean ignoreEmptyLines = false;

    public List<T> parse() throws IllegalStateException {
        return this.stream().collect(Collectors.toList());
    }

    public Stream<T> stream() throws IllegalStateException {
        this.prepareToReadInput();
        CompleteFileReader<? extends T> completeFileReader = new CompleteFileReader<T>(this.csvReader, this.filter, this.ignoreEmptyLines, this.mappingStrategy, this.exceptionHandler, this.verifiers);
        this.executor = new LineExecutor<T>(this.orderedResults, this.errorLocale, completeFileReader);
        this.executor.prepare();
        return StreamSupport.stream(this.executor, false);
    }

    public List<CsvException> getCapturedExceptions() {
        return this.executor != null ? this.executor.getCapturedExceptions() : this.capturedExceptions;
    }

    public void setMappingStrategy(MappingStrategy<? extends T> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
    }

    public void setCsvReader(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    public void setFilter(CsvToBeanFilter filter2) {
        this.filter = filter2;
    }

    public void setThrowExceptions(boolean throwExceptions) {
        this.exceptionHandler = throwExceptions ? new ExceptionHandlerThrow() : new ExceptionHandlerQueue();
    }

    public void setExceptionHandler(CsvExceptionHandler handler) {
        if (handler != null) {
            this.exceptionHandler = handler;
        }
    }

    CsvExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public void setOrderedResults(boolean orderedResults) {
        this.orderedResults = orderedResults;
    }

    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        if (this.csvReader != null) {
            this.csvReader.setErrorLocale(this.errorLocale);
        }
        if (this.mappingStrategy != null) {
            this.mappingStrategy.setErrorLocale(this.errorLocale);
        }
    }

    public void setVerifiers(List<BeanVerifier<T>> verifiers) {
        this.verifiers = ObjectUtils.defaultIfNull(verifiers, Collections.emptyList());
    }

    private void prepareToReadInput() throws IllegalStateException {
        if (this.mappingStrategy == null || this.csvReader == null) {
            throw new IllegalStateException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("specify.strategy.reader"));
        }
        try {
            this.mappingStrategy.captureHeader(this.csvReader);
        }
        catch (Exception e) {
            throw new RuntimeException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.error"), e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        this.prepareToReadInput();
        return new CsvToBeanIterator();
    }

    public void setIgnoreEmptyLines(boolean ignoreEmptyLines) {
        this.ignoreEmptyLines = ignoreEmptyLines;
    }

    private class CsvToBeanIterator
    implements Iterator<T> {
        private final BlockingQueue<OrderedObject<T>> resultantBeansQueue;
        private final BlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue;
        private final SingleLineReader lineReader;
        private String[] line;
        private long lineProcessed;
        private T bean;

        CsvToBeanIterator() {
            this.lineReader = new SingleLineReader(CsvToBean.this.csvReader, CsvToBean.this.ignoreEmptyLines);
            this.line = null;
            this.lineProcessed = 0L;
            this.resultantBeansQueue = new ArrayBlockingQueue(1);
            this.thrownExceptionsQueue = new LinkedBlockingQueue<OrderedObject<CsvException>>();
            this.readSingleLine();
        }

        private void processException() {
            OrderedObject o = (OrderedObject)this.thrownExceptionsQueue.poll();
            while (o != null && o.getElement() != null) {
                CsvToBean.this.capturedExceptions.add(o.getElement());
                o = (OrderedObject)this.thrownExceptionsQueue.poll();
            }
        }

        private void readLineWithPossibleError() throws IOException, CsvValidationException {
            this.bean = null;
            while (this.bean == null && null != (this.line = this.lineReader.readNextLine())) {
                this.lineProcessed = this.lineReader.getLinesRead();
                ProcessCsvLine proc = new ProcessCsvLine(this.lineProcessed, CsvToBean.this.mappingStrategy, CsvToBean.this.filter, CsvToBean.this.verifiers, this.line, this.resultantBeansQueue, this.thrownExceptionsQueue, new TreeSet<Long>(), CsvToBean.this.exceptionHandler);
                proc.run();
                if (!this.thrownExceptionsQueue.isEmpty()) {
                    this.processException();
                    continue;
                }
                OrderedObject o = (OrderedObject)this.resultantBeansQueue.poll();
                this.bean = o == null ? null : o.getElement();
            }
            if (this.line == null) {
                this.bean = null;
            }
        }

        private void readSingleLine() {
            try {
                this.readLineWithPossibleError();
            }
            catch (CsvValidationException | IOException e) {
                this.line = null;
                throw new RuntimeException(String.format(ResourceBundle.getBundle("opencsv", CsvToBean.this.errorLocale).getString("parsing.error"), this.lineProcessed, Arrays.toString(this.line)), e);
            }
        }

        @Override
        public boolean hasNext() {
            return this.bean != null;
        }

        @Override
        public T next() {
            if (this.bean == null) {
                throw new NoSuchElementException();
            }
            Object intermediateBean = this.bean;
            this.readSingleLine();
            return intermediateBean;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", CsvToBean.this.errorLocale).getString("read.only.iterator"));
        }
    }
}

