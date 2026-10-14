/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVIterator;
import com.opencsv.CSVParser;
import com.opencsv.ICSVParser;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;
import com.opencsv.exceptions.CsvMultilineLimitBrokenException;
import com.opencsv.exceptions.CsvRuntimeException;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.processor.RowProcessor;
import com.opencsv.stream.reader.LineReader;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidatorAggregator;
import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.zip.ZipException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class CSVReader
implements Closeable,
Iterable<String[]> {
    public static final boolean DEFAULT_KEEP_CR = false;
    public static final boolean DEFAULT_VERIFY_READER = true;
    static final int CONTEXT_MULTILINE_EXCEPTION_MESSAGE_SIZE = 100;
    public static final int DEFAULT_SKIP_LINES = 0;
    public static final int DEFAULT_MULTILINE_LIMIT = 0;
    protected static final List<Class<? extends IOException>> PASSTHROUGH_EXCEPTIONS = Collections.unmodifiableList(Arrays.asList(CharacterCodingException.class, CharConversionException.class, UnsupportedEncodingException.class, UTFDataFormatException.class, ZipException.class, FileNotFoundException.class, MalformedInputException.class));
    public static final int READ_AHEAD_LIMIT = 2;
    private static final int MAX_WIDTH = 100;
    protected ICSVParser parser;
    protected int skipLines;
    protected BufferedReader br;
    protected LineReader lineReader;
    protected boolean hasNext = true;
    protected boolean linesSkipped;
    protected boolean keepCR;
    protected boolean verifyReader;
    protected int multilineLimit = 0;
    protected Locale errorLocale;
    protected long linesRead = 0L;
    protected long recordsRead = 0L;
    protected String[] peekedLine = null;
    protected final Queue<OrderedObject<String>> peekedLines = new LinkedList<OrderedObject<String>>();
    private final LineValidatorAggregator lineValidatorAggregator;
    private final RowValidatorAggregator rowValidatorAggregator;
    private final RowProcessor rowProcessor;

    public CSVReader(Reader reader) {
        this(reader, 0, new CSVParser(',', '\"', '\\', false, true, false, ICSVParser.DEFAULT_NULL_FIELD_INDICATOR, Locale.getDefault()), false, true, 0, Locale.getDefault(), new LineValidatorAggregator(), new RowValidatorAggregator(), null);
    }

    CSVReader(Reader reader, int line, ICSVParser icsvParser, boolean keepCR, boolean verifyReader, int multilineLimit, Locale errorLocale, LineValidatorAggregator lineValidatorAggregator, RowValidatorAggregator rowValidatorAggregator, RowProcessor rowProcessor) {
        this.br = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
        this.lineReader = new LineReader(this.br, keepCR);
        this.skipLines = line;
        this.parser = icsvParser;
        this.keepCR = keepCR;
        this.verifyReader = verifyReader;
        this.multilineLimit = multilineLimit;
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        this.lineValidatorAggregator = lineValidatorAggregator;
        this.rowValidatorAggregator = rowValidatorAggregator;
        this.rowProcessor = rowProcessor;
    }

    public ICSVParser getParser() {
        return this.parser;
    }

    public int getSkipLines() {
        return this.skipLines;
    }

    public boolean keepCarriageReturns() {
        return this.keepCR;
    }

    public List<String[]> readAll() throws IOException, CsvException {
        LinkedList<String[]> allElements = new LinkedList<String[]>();
        while (this.hasNext) {
            String[] nextLineAsTokens = this.readNext();
            if (nextLineAsTokens == null) continue;
            allElements.add(nextLineAsTokens);
        }
        return allElements;
    }

    public String[] readNext() throws IOException, CsvValidationException {
        return this.flexibleRead(true, true);
    }

    public String[] readNextSilently() throws IOException {
        try {
            return this.flexibleRead(true, false);
        }
        catch (CsvValidationException e) {
            throw new CsvRuntimeException("A CSValidationException was thrown from the runNextSilently method which should not happen", e);
        }
    }

    private void primeNextRecord() throws IOException {
        int lastItemIndex;
        int linesInThisRecord = 0;
        long lastSuccessfulLineRead = this.linesRead + 1L;
        do {
            String nextLine = this.getNextLine();
            this.peekedLines.add(new OrderedObject<String>(lastSuccessfulLineRead, nextLine));
            ++linesInThisRecord;
            if (!this.hasNext) {
                if (this.parser.isPending()) {
                    throw new CsvMalformedLineException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("unterminated.quote"), StringUtils.abbreviate(this.parser.getPendingText(), 100)), lastSuccessfulLineRead, this.parser.getPendingText());
                }
                return;
            }
            if (this.multilineLimit > 0 && linesInThisRecord > this.multilineLimit) {
                long row = this.recordsRead + 1L;
                String context = this.parser.getPendingText();
                if (context.length() > 100) {
                    context = context.substring(0, 100);
                }
                String messageFormat = ResourceBundle.getBundle("opencsv", this.errorLocale).getString("multiline.limit.broken");
                String message = String.format(this.errorLocale, messageFormat, this.multilineLimit, row, context);
                throw new CsvMultilineLimitBrokenException(message, row, this.parser.getPendingText(), this.multilineLimit);
            }
            String[] r = this.parser.parseLineMulti(nextLine);
            if (r.length <= 0) continue;
            this.peekedLine = this.peekedLine == null ? r : this.combineResultsFromMultipleReads(this.peekedLine, r);
        } while (this.parser.isPending());
        if (this.keepCR && this.peekedLine[lastItemIndex = this.peekedLine.length - 1] != null && this.peekedLine[lastItemIndex].endsWith("\r")) {
            this.peekedLine[lastItemIndex] = this.peekedLine[lastItemIndex].substring(0, this.peekedLine[lastItemIndex].length() - 1);
        }
    }

    private void validateLine(long lastSuccessfulLineRead, String nextLine) throws CsvValidationException {
        try {
            this.lineValidatorAggregator.validate(nextLine);
        }
        catch (CsvValidationException cve) {
            cve.setLineNumber(lastSuccessfulLineRead);
            throw cve;
        }
    }

    protected void validateResult(String[] result, long lineStartOfRow) throws CsvValidationException {
        if (result != null) {
            if (this.rowProcessor != null) {
                this.rowProcessor.processRow(result);
            }
            try {
                this.rowValidatorAggregator.validate(result);
            }
            catch (CsvValidationException cve) {
                cve.setLineNumber(lineStartOfRow);
                throw cve;
            }
        }
    }

    protected String[] combineResultsFromMultipleReads(String[] buffer, String[] lastRead) {
        String[] t2 = new String[buffer.length + lastRead.length];
        System.arraycopy(buffer, 0, t2, 0, buffer.length);
        System.arraycopy(lastRead, 0, t2, buffer.length, lastRead.length);
        return t2;
    }

    protected String getNextLine() throws IOException {
        String nextLine;
        if (this.isClosed()) {
            this.hasNext = false;
            return null;
        }
        if (!this.linesSkipped) {
            for (int i = 0; i < this.skipLines; ++i) {
                this.lineReader.readLine();
                ++this.linesRead;
            }
            this.linesSkipped = true;
        }
        if ((nextLine = this.lineReader.readLine()) == null) {
            this.hasNext = false;
        } else {
            ++this.linesRead;
        }
        return this.hasNext ? nextLine : null;
    }

    public int getMultilineLimit() {
        return this.multilineLimit;
    }

    protected boolean isClosed() throws IOException {
        if (!this.verifyReader) {
            return false;
        }
        try {
            this.br.mark(2);
            int nextByte = this.br.read();
            this.br.reset();
            return nextByte == -1;
        }
        catch (IOException e) {
            if (PASSTHROUGH_EXCEPTIONS.contains(e.getClass())) {
                throw e;
            }
            return true;
        }
    }

    @Override
    public void close() throws IOException {
        this.br.close();
    }

    @Override
    public Iterator<String[]> iterator() {
        try {
            CSVIterator it = new CSVIterator(this);
            it.setErrorLocale(this.errorLocale);
            return it;
        }
        catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyReader() {
        return this.verifyReader;
    }

    public long getLinesRead() {
        return this.linesRead;
    }

    public long getRecordsRead() {
        return this.recordsRead;
    }

    public void skip(int numberOfLinesToSkip) throws IOException {
        for (int j = 0; j < numberOfLinesToSkip; ++j) {
            this.readNextSilently();
        }
    }

    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        if (this.parser != null) {
            this.parser.setErrorLocale(this.errorLocale);
        }
    }

    public String[] peek() throws IOException {
        String[] result = null;
        try {
            result = this.flexibleRead(false, false);
        }
        catch (CsvValidationException csvValidationException) {
            // empty catch block
        }
        return result;
    }

    private String[] flexibleRead(boolean popLine, boolean validate) throws IOException, CsvValidationException {
        if (this.peekedLines.isEmpty()) {
            this.primeNextRecord();
        }
        if (validate) {
            for (OrderedObject orderedObject : this.peekedLines) {
                this.validateLine(orderedObject.getOrdinal(), (String)orderedObject.getElement());
            }
            this.validateResult(this.peekedLine, this.linesRead);
        }
        String[] result = this.peekedLine;
        if (popLine) {
            this.peekedLines.clear();
            this.peekedLine = null;
            if (result != null) {
                ++this.recordsRead;
            }
        }
        return result;
    }
}

