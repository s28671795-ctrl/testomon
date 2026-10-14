/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterators;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.Constants;
import org.apache.commons.csv.DuplicateHeaderMode;
import org.apache.commons.csv.ExtendedBufferedReader;
import org.apache.commons.csv.Lexer;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.csv.Token;

public final class CSVParser
implements Iterable<CSVRecord>,
Closeable {
    private String headerComment;
    private String trailerComment;
    private final CSVFormat format;
    private final Headers headers;
    private final Lexer lexer;
    private final CSVRecordIterator csvRecordIterator;
    private final List<String> recordList = new ArrayList<String>();
    private long recordNumber;
    private final long characterOffset;
    private final Token reusableToken = new Token();

    public static CSVParser parse(File file, Charset charset, CSVFormat format) throws IOException {
        Objects.requireNonNull(file, "file");
        return CSVParser.parse(file.toPath(), charset, format);
    }

    public static CSVParser parse(InputStream inputStream2, Charset charset, CSVFormat format) throws IOException {
        Objects.requireNonNull(inputStream2, "inputStream");
        Objects.requireNonNull(format, "format");
        return CSVParser.parse(new InputStreamReader(inputStream2, charset), format);
    }

    public static CSVParser parse(Path path, Charset charset, CSVFormat format) throws IOException {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(format, "format");
        return CSVParser.parse(Files.newInputStream(path, new OpenOption[0]), charset, format);
    }

    public static CSVParser parse(Reader reader, CSVFormat format) throws IOException {
        return new CSVParser(reader, format);
    }

    public static CSVParser parse(String string, CSVFormat format) throws IOException {
        Objects.requireNonNull(string, "string");
        Objects.requireNonNull(format, "format");
        return new CSVParser(new StringReader(string), format);
    }

    public static CSVParser parse(URL url2, Charset charset, CSVFormat format) throws IOException {
        Objects.requireNonNull(url2, "url");
        Objects.requireNonNull(charset, "charset");
        Objects.requireNonNull(format, "format");
        return new CSVParser(new InputStreamReader(url2.openStream(), charset), format);
    }

    public CSVParser(Reader reader, CSVFormat format) throws IOException {
        this(reader, format, 0L, 1L);
    }

    public CSVParser(Reader reader, CSVFormat format, long characterOffset, long recordNumber) throws IOException {
        Objects.requireNonNull(reader, "reader");
        Objects.requireNonNull(format, "format");
        this.format = format.copy();
        this.lexer = new Lexer(format, new ExtendedBufferedReader(reader));
        this.csvRecordIterator = new CSVRecordIterator();
        this.headers = this.createHeaders();
        this.characterOffset = characterOffset;
        this.recordNumber = recordNumber - 1L;
    }

    private void addRecordValue(boolean lastRecord) {
        String input = this.format.trim(this.reusableToken.content.toString());
        if (lastRecord && input.isEmpty() && this.format.getTrailingDelimiter()) {
            return;
        }
        this.recordList.add(this.handleNull(input));
    }

    @Override
    public void close() throws IOException {
        if (this.lexer != null) {
            this.lexer.close();
        }
    }

    private Map<String, Integer> createEmptyHeaderMap() {
        return this.format.getIgnoreHeaderCase() ? new TreeMap(String.CASE_INSENSITIVE_ORDER) : new LinkedHashMap();
    }

    private Headers createHeaders() throws IOException {
        Map<String, Integer> hdrMap = null;
        List<String> headerNames = null;
        Object[] formatHeader = this.format.getHeader();
        if (formatHeader != null) {
            CSVRecord nextRecord;
            hdrMap = this.createEmptyHeaderMap();
            Object[] headerRecord = null;
            if (formatHeader.length == 0) {
                nextRecord = this.nextRecord();
                if (nextRecord != null) {
                    headerRecord = nextRecord.values();
                    this.headerComment = nextRecord.getComment();
                }
            } else {
                if (this.format.getSkipHeaderRecord() && (nextRecord = this.nextRecord()) != null) {
                    this.headerComment = nextRecord.getComment();
                }
                headerRecord = formatHeader;
            }
            if (headerRecord != null) {
                boolean observedMissing = false;
                for (int i = 0; i < headerRecord.length; ++i) {
                    boolean emptyDuplicatesAllowed;
                    String header = headerRecord[i];
                    boolean blankHeader = CSVFormat.isBlank(header);
                    if (blankHeader && !this.format.getAllowMissingColumnNames()) {
                        throw new IllegalArgumentException("A header name is missing in " + Arrays.toString(headerRecord));
                    }
                    boolean containsHeader = blankHeader ? observedMissing : hdrMap.containsKey(header);
                    DuplicateHeaderMode headerMode = this.format.getDuplicateHeaderMode();
                    boolean duplicatesAllowed = headerMode == DuplicateHeaderMode.ALLOW_ALL;
                    boolean bl = emptyDuplicatesAllowed = headerMode == DuplicateHeaderMode.ALLOW_EMPTY;
                    if (!(!containsHeader || duplicatesAllowed || blankHeader && emptyDuplicatesAllowed)) {
                        throw new IllegalArgumentException(String.format("The header contains a duplicate name: \"%s\" in %s. If this is valid then use CSVFormat.Builder.setDuplicateHeaderMode().", header, Arrays.toString(headerRecord)));
                    }
                    observedMissing |= blankHeader;
                    if (header == null) continue;
                    hdrMap.put(header, i);
                    if (headerNames == null) {
                        headerNames = new ArrayList<String>(headerRecord.length);
                    }
                    headerNames.add(header);
                }
            }
        }
        headerNames = headerNames == null ? Collections.emptyList() : Collections.unmodifiableList(headerNames);
        return new Headers(hdrMap, headerNames);
    }

    public long getCurrentLineNumber() {
        return this.lexer.getCurrentLineNumber();
    }

    public String getFirstEndOfLine() {
        return this.lexer.getFirstEol();
    }

    public String getHeaderComment() {
        return this.headerComment;
    }

    public Map<String, Integer> getHeaderMap() {
        if (this.headers.headerMap == null) {
            return null;
        }
        Map<String, Integer> map = this.createEmptyHeaderMap();
        map.putAll(this.headers.headerMap);
        return map;
    }

    Map<String, Integer> getHeaderMapRaw() {
        return this.headers.headerMap;
    }

    public List<String> getHeaderNames() {
        return Collections.unmodifiableList(this.headers.headerNames);
    }

    public long getRecordNumber() {
        return this.recordNumber;
    }

    public List<CSVRecord> getRecords() {
        return this.stream().collect(Collectors.toList());
    }

    public String getTrailerComment() {
        return this.trailerComment;
    }

    private String handleNull(String input) {
        boolean isQuoted = this.reusableToken.isQuoted;
        String nullString = this.format.getNullString();
        boolean strictQuoteMode = this.isStrictQuoteMode();
        if (input.equals(nullString)) {
            return strictQuoteMode && isQuoted ? input : null;
        }
        return strictQuoteMode && nullString == null && input.isEmpty() && !isQuoted ? null : input;
    }

    public boolean hasHeaderComment() {
        return this.headerComment != null;
    }

    public boolean hasTrailerComment() {
        return this.trailerComment != null;
    }

    public boolean isClosed() {
        return this.lexer.isClosed();
    }

    private boolean isStrictQuoteMode() {
        return this.format.getQuoteMode() == QuoteMode.ALL_NON_NULL || this.format.getQuoteMode() == QuoteMode.NON_NUMERIC;
    }

    @Override
    public Iterator<CSVRecord> iterator() {
        return this.csvRecordIterator;
    }

    CSVRecord nextRecord() throws IOException {
        CSVRecord result = null;
        this.recordList.clear();
        StringBuilder sb = null;
        long startCharPosition = this.lexer.getCharacterPosition() + this.characterOffset;
        block7: do {
            this.reusableToken.reset();
            this.lexer.nextToken(this.reusableToken);
            switch (this.reusableToken.type) {
                case TOKEN: {
                    this.addRecordValue(false);
                    break;
                }
                case EORECORD: {
                    this.addRecordValue(true);
                    break;
                }
                case EOF: {
                    if (this.reusableToken.isReady) {
                        this.addRecordValue(true);
                        break;
                    }
                    if (sb == null) continue block7;
                    this.trailerComment = sb.toString();
                    break;
                }
                case INVALID: {
                    throw new IOException("(line " + this.getCurrentLineNumber() + ") invalid parse sequence");
                }
                case COMMENT: {
                    if (sb == null) {
                        sb = new StringBuilder();
                    } else {
                        sb.append('\n');
                    }
                    sb.append((CharSequence)this.reusableToken.content);
                    this.reusableToken.type = Token.Type.TOKEN;
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected Token type: " + (Object)((Object)this.reusableToken.type));
                }
            }
        } while (this.reusableToken.type == Token.Type.TOKEN);
        if (!this.recordList.isEmpty()) {
            ++this.recordNumber;
            String comment = sb == null ? null : sb.toString();
            result = new CSVRecord(this, this.recordList.toArray(Constants.EMPTY_STRING_ARRAY), comment, this.recordNumber, startCharPosition);
        }
        return result;
    }

    public Stream<CSVRecord> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.iterator(), 16), false);
    }

    private static final class Headers {
        final Map<String, Integer> headerMap;
        final List<String> headerNames;

        Headers(Map<String, Integer> headerMap, List<String> headerNames) {
            this.headerMap = headerMap;
            this.headerNames = headerNames;
        }
    }

    class CSVRecordIterator
    implements Iterator<CSVRecord> {
        private CSVRecord current;

        CSVRecordIterator() {
        }

        private CSVRecord getNextRecord() {
            try {
                return CSVParser.this.nextRecord();
            }
            catch (IOException e) {
                throw new UncheckedIOException(e.getClass().getSimpleName() + " reading next record: " + e.toString(), e);
            }
        }

        @Override
        public boolean hasNext() {
            if (CSVParser.this.isClosed()) {
                return false;
            }
            if (this.current == null) {
                this.current = this.getNextRecord();
            }
            return this.current != null;
        }

        @Override
        public CSVRecord next() {
            if (CSVParser.this.isClosed()) {
                throw new NoSuchElementException("CSVParser has been closed");
            }
            CSVRecord next = this.current;
            this.current = null;
            if (next == null && (next = this.getNextRecord()) == null) {
                throw new NoSuchElementException("No more CSV records available");
            }
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

