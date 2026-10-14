/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.concurrent.BeanExecutor;
import com.opencsv.bean.concurrent.ProcessCsvBean;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.exceptionhandler.ExceptionHandlerThrow;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvRuntimeException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.iterators.PeekingIterator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class StatefulBeanToCsv<T> {
    private static final char NO_CHARACTER = '\u0000';
    private int lineNumber = 0;
    private final char separator;
    private final char quotechar;
    private final char escapechar;
    private final String lineEnd;
    private boolean headerWritten = false;
    private MappingStrategy<T> mappingStrategy;
    private final Writer writer;
    private ICSVWriter csvwriter;
    private final CsvExceptionHandler exceptionHandler;
    private List<CsvException> capturedExceptions = new ArrayList<CsvException>();
    private boolean orderedResults = true;
    private BeanExecutor<T> executor = null;
    private Locale errorLocale = Locale.getDefault();
    private final boolean applyQuotesToAll;
    private final MultiValuedMap<Class<?>, Field> ignoredFields;
    private final String profile;

    StatefulBeanToCsv(char escapechar, String lineEnd, MappingStrategy<T> mappingStrategy, char quotechar, char separator, CsvExceptionHandler exceptionHandler, Writer writer, boolean applyQuotesToAll, MultiValuedMap<Class<?>, Field> ignoredFields, String profile) {
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;
        this.mappingStrategy = mappingStrategy;
        this.quotechar = quotechar;
        this.separator = separator;
        this.exceptionHandler = exceptionHandler;
        this.writer = writer;
        this.applyQuotesToAll = applyQuotesToAll;
        this.ignoredFields = ignoredFields;
        this.profile = StringUtils.defaultString(profile);
    }

    public StatefulBeanToCsv(MappingStrategy<T> mappingStrategy, CsvExceptionHandler exceptionHandler, boolean applyQuotesToAll, ICSVWriter csvWriter, MultiValuedMap<Class<?>, Field> ignoredFields, String profile) {
        this.mappingStrategy = mappingStrategy;
        this.exceptionHandler = exceptionHandler;
        this.applyQuotesToAll = applyQuotesToAll;
        this.csvwriter = csvWriter;
        this.escapechar = '\u0000';
        this.lineEnd = "";
        this.quotechar = '\u0000';
        this.separator = '\u0000';
        this.writer = null;
        this.ignoredFields = ignoredFields;
        this.profile = StringUtils.defaultString(profile);
    }

    private void beforeFirstWrite(T bean) throws CsvRequiredFieldEmptyException {
        String[] header;
        if (this.mappingStrategy == null) {
            this.mappingStrategy = OpencsvUtils.determineMappingStrategy(bean.getClass(), this.errorLocale, this.profile);
        }
        if (!this.ignoredFields.isEmpty()) {
            this.mappingStrategy.ignoreFields(this.ignoredFields);
        }
        if (this.csvwriter == null) {
            this.csvwriter = new CSVWriter(this.writer, this.separator, this.quotechar, this.escapechar, this.lineEnd);
        }
        if ((header = this.mappingStrategy.generateHeader(bean)).length > 0) {
            this.csvwriter.writeNext(header, this.applyQuotesToAll);
        }
        this.headerWritten = true;
    }

    public void write(T bean) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if (bean != null) {
            if (!this.headerWritten) {
                this.beforeFirstWrite(bean);
            }
            ArrayBlockingQueue<OrderedObject<String[]>> resultantLineQueue = new ArrayBlockingQueue<OrderedObject<String[]>>(1);
            LinkedBlockingQueue<OrderedObject<CsvException>> thrownExceptionsQueue = new LinkedBlockingQueue<OrderedObject<CsvException>>();
            ProcessCsvBean<T> proc = new ProcessCsvBean<T>(++this.lineNumber, this.mappingStrategy, bean, resultantLineQueue, thrownExceptionsQueue, new TreeSet<Long>(), this.exceptionHandler);
            try {
                proc.run();
            }
            catch (RuntimeException re) {
                if (re.getCause() != null) {
                    if (re.getCause() instanceof CsvRuntimeException) {
                        throw (CsvRuntimeException)re.getCause();
                    }
                    if (re.getCause() instanceof CsvDataTypeMismatchException) {
                        throw (CsvDataTypeMismatchException)re.getCause();
                    }
                    if (re.getCause() instanceof CsvRequiredFieldEmptyException) {
                        throw (CsvRequiredFieldEmptyException)re.getCause();
                    }
                }
                throw re;
            }
            if (!thrownExceptionsQueue.isEmpty()) {
                OrderedObject o = (OrderedObject)thrownExceptionsQueue.poll();
                while (o != null && o.getElement() != null) {
                    this.capturedExceptions.add((CsvException)o.getElement());
                    o = (OrderedObject)thrownExceptionsQueue.poll();
                }
            } else {
                OrderedObject result = (OrderedObject)resultantLineQueue.poll();
                if (result != null && result.getElement() != null) {
                    this.csvwriter.writeNext((String[])result.getElement(), this.applyQuotesToAll);
                }
            }
        }
    }

    private void submitAllLines(Iterator<T> beans) throws InterruptedException {
        while (beans.hasNext()) {
            T bean = beans.next();
            if (bean == null) continue;
            this.executor.submitBean(++this.lineNumber, this.mappingStrategy, bean, this.exceptionHandler);
        }
        this.executor.complete();
    }

    public void write(List<T> beans) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if (CollectionUtils.isNotEmpty(beans)) {
            this.write(beans.iterator());
        }
    }

    public void write(Iterator<T> iBeans) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        PeekingIterator<T> beans = new PeekingIterator<T>(iBeans);
        T firstBean = beans.peek();
        if (!beans.hasNext()) {
            return;
        }
        if (!this.headerWritten) {
            this.beforeFirstWrite(firstBean);
        }
        this.executor = new BeanExecutor(this.orderedResults, this.errorLocale);
        this.executor.prepare();
        try {
            this.submitAllLines(beans);
        }
        catch (RejectedExecutionException e) {
            if (this.executor.getTerminalException() instanceof RuntimeException) {
                throw (RuntimeException)this.executor.getTerminalException();
            }
            if (this.executor.getTerminalException() instanceof CsvDataTypeMismatchException) {
                throw (CsvDataTypeMismatchException)this.executor.getTerminalException();
            }
            if (this.executor.getTerminalException() instanceof CsvRequiredFieldEmptyException) {
                throw (CsvRequiredFieldEmptyException)this.executor.getTerminalException();
            }
            throw new RuntimeException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("error.writing.beans"), this.executor.getTerminalException());
        }
        catch (Exception e) {
            this.executor.shutdownNow();
            if (this.executor.getTerminalException() instanceof RuntimeException) {
                throw (RuntimeException)this.executor.getTerminalException();
            }
            throw new RuntimeException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("error.writing.beans"), e);
        }
        finally {
            this.capturedExceptions.addAll(this.executor.getCapturedExceptions());
        }
        StreamSupport.stream(this.executor, false).forEach(l -> this.csvwriter.writeNext((String[])l, this.applyQuotesToAll));
    }

    public void write(Stream<T> beans) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.write(beans.iterator());
    }

    public void setOrderedResults(boolean orderedResults) {
        this.orderedResults = orderedResults;
    }

    @Deprecated
    public boolean isThrowExceptions() {
        return this.exceptionHandler instanceof ExceptionHandlerThrow;
    }

    public List<CsvException> getCapturedExceptions() {
        List<CsvException> intermediate = this.capturedExceptions;
        this.capturedExceptions = new ArrayList<CsvException>();
        intermediate.sort(Comparator.comparingLong(CsvException::getLineNumber));
        return intermediate;
    }

    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }
}

