/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.Constants;
import org.apache.commons.csv.DuplicateHeaderMode;
import org.apache.commons.csv.ExtendedBufferedReader;
import org.apache.commons.csv.IOUtils;
import org.apache.commons.csv.QuoteMode;

public final class CSVFormat
implements Serializable {
    public static final CSVFormat DEFAULT = new CSVFormat(",", Constants.DOUBLE_QUOTE_CHAR, null, null, null, false, true, "\r\n", null, null, null, false, false, false, false, false, false, DuplicateHeaderMode.ALLOW_ALL);
    public static final CSVFormat EXCEL = DEFAULT.builder().setIgnoreEmptyLines(false).setAllowMissingColumnNames(true).build();
    public static final CSVFormat INFORMIX_UNLOAD = DEFAULT.builder().setDelimiter('|').setEscape('\\').setQuote(Constants.DOUBLE_QUOTE_CHAR).setRecordSeparator('\n').build();
    public static final CSVFormat INFORMIX_UNLOAD_CSV = DEFAULT.builder().setDelimiter(",").setQuote(Constants.DOUBLE_QUOTE_CHAR).setRecordSeparator('\n').build();
    public static final CSVFormat MONGODB_CSV = DEFAULT.builder().setDelimiter(",").setEscape(Constants.DOUBLE_QUOTE_CHAR).setQuote(Constants.DOUBLE_QUOTE_CHAR).setQuoteMode(QuoteMode.MINIMAL).setSkipHeaderRecord(false).build();
    public static final CSVFormat MONGODB_TSV = DEFAULT.builder().setDelimiter('\t').setEscape(Constants.DOUBLE_QUOTE_CHAR).setQuote(Constants.DOUBLE_QUOTE_CHAR).setQuoteMode(QuoteMode.MINIMAL).setSkipHeaderRecord(false).build();
    public static final CSVFormat MYSQL = DEFAULT.builder().setDelimiter('\t').setEscape('\\').setIgnoreEmptyLines(false).setQuote(null).setRecordSeparator('\n').setNullString("\\N").setQuoteMode(QuoteMode.ALL_NON_NULL).build();
    public static final CSVFormat ORACLE = DEFAULT.builder().setDelimiter(",").setEscape('\\').setIgnoreEmptyLines(false).setQuote(Constants.DOUBLE_QUOTE_CHAR).setNullString("\\N").setTrim(true).setRecordSeparator(System.lineSeparator()).setQuoteMode(QuoteMode.MINIMAL).build();
    public static final CSVFormat POSTGRESQL_CSV = DEFAULT.builder().setDelimiter(",").setEscape(null).setIgnoreEmptyLines(false).setQuote(Constants.DOUBLE_QUOTE_CHAR).setRecordSeparator('\n').setNullString("").setQuoteMode(QuoteMode.ALL_NON_NULL).build();
    public static final CSVFormat POSTGRESQL_TEXT = DEFAULT.builder().setDelimiter('\t').setEscape('\\').setIgnoreEmptyLines(false).setQuote(null).setRecordSeparator('\n').setNullString("\\N").setQuoteMode(QuoteMode.ALL_NON_NULL).build();
    public static final CSVFormat RFC4180 = DEFAULT.builder().setIgnoreEmptyLines(false).build();
    private static final long serialVersionUID = 2L;
    public static final CSVFormat TDF = DEFAULT.builder().setDelimiter('\t').setIgnoreSurroundingSpaces(true).build();
    private final DuplicateHeaderMode duplicateHeaderMode;
    private final boolean allowMissingColumnNames;
    private final boolean autoFlush;
    private final Character commentMarker;
    private final String delimiter;
    private final Character escapeCharacter;
    private final String[] headers;
    private final String[] headerComments;
    private final boolean ignoreEmptyLines;
    private final boolean ignoreHeaderCase;
    private final boolean ignoreSurroundingSpaces;
    private final String nullString;
    private final Character quoteCharacter;
    private final String quotedNullString;
    private final QuoteMode quoteMode;
    private final String recordSeparator;
    private final boolean skipHeaderRecord;
    private final boolean trailingDelimiter;
    private final boolean trim;

    @SafeVarargs
    static <T> T[] clone(T ... values2) {
        return values2 == null ? null : (Object[])values2.clone();
    }

    private static boolean contains(String source2, char searchCh) {
        return Objects.requireNonNull(source2, "source").indexOf(searchCh) >= 0;
    }

    private static boolean containsLineBreak(String source2) {
        return CSVFormat.contains(source2, '\r') || CSVFormat.contains(source2, '\n');
    }

    static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static boolean isLineBreak(char c) {
        return c == '\n' || c == '\r';
    }

    private static boolean isLineBreak(Character c) {
        return c != null && CSVFormat.isLineBreak(c.charValue());
    }

    private static boolean isTrimChar(char ch) {
        return ch <= ' ';
    }

    private static boolean isTrimChar(CharSequence charSequence, int pos) {
        return CSVFormat.isTrimChar(charSequence.charAt(pos));
    }

    public static CSVFormat newFormat(char delimiter) {
        return new CSVFormat(String.valueOf(delimiter), null, null, null, null, false, false, null, null, null, null, false, false, false, false, false, false, DuplicateHeaderMode.ALLOW_ALL);
    }

    static String[] toStringArray(Object[] values2) {
        if (values2 == null) {
            return null;
        }
        String[] strings = new String[values2.length];
        Arrays.setAll(strings, i -> Objects.toString(values2[i], null));
        return strings;
    }

    static CharSequence trim(CharSequence charSequence) {
        int pos;
        int count;
        if (charSequence instanceof String) {
            return ((String)charSequence).trim();
        }
        int len = count = charSequence.length();
        for (pos = 0; pos < len && CSVFormat.isTrimChar(charSequence, pos); ++pos) {
        }
        while (pos < len && CSVFormat.isTrimChar(charSequence, len - 1)) {
            --len;
        }
        return pos > 0 || len < count ? charSequence.subSequence(pos, len) : charSequence;
    }

    public static CSVFormat valueOf(String format) {
        return Predefined.valueOf(format).getFormat();
    }

    private CSVFormat(Builder builder) {
        this.delimiter = builder.delimiter;
        this.quoteCharacter = builder.quoteCharacter;
        this.quoteMode = builder.quoteMode;
        this.commentMarker = builder.commentMarker;
        this.escapeCharacter = builder.escapeCharacter;
        this.ignoreSurroundingSpaces = builder.ignoreSurroundingSpaces;
        this.allowMissingColumnNames = builder.allowMissingColumnNames;
        this.ignoreEmptyLines = builder.ignoreEmptyLines;
        this.recordSeparator = builder.recordSeparator;
        this.nullString = builder.nullString;
        this.headerComments = builder.headerComments;
        this.headers = builder.headers;
        this.skipHeaderRecord = builder.skipHeaderRecord;
        this.ignoreHeaderCase = builder.ignoreHeaderCase;
        this.trailingDelimiter = builder.trailingDelimiter;
        this.trim = builder.trim;
        this.autoFlush = builder.autoFlush;
        this.quotedNullString = builder.quotedNullString;
        this.duplicateHeaderMode = builder.duplicateHeaderMode;
        this.validate();
    }

    private CSVFormat(String delimiter, Character quoteChar, QuoteMode quoteMode, Character commentStart, Character escape, boolean ignoreSurroundingSpaces, boolean ignoreEmptyLines, String recordSeparator, String nullString, Object[] headerComments, String[] header, boolean skipHeaderRecord, boolean allowMissingColumnNames, boolean ignoreHeaderCase, boolean trim, boolean trailingDelimiter, boolean autoFlush, DuplicateHeaderMode duplicateHeaderMode) {
        this.delimiter = delimiter;
        this.quoteCharacter = quoteChar;
        this.quoteMode = quoteMode;
        this.commentMarker = commentStart;
        this.escapeCharacter = escape;
        this.ignoreSurroundingSpaces = ignoreSurroundingSpaces;
        this.allowMissingColumnNames = allowMissingColumnNames;
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.recordSeparator = recordSeparator;
        this.nullString = nullString;
        this.headerComments = CSVFormat.toStringArray(headerComments);
        this.headers = CSVFormat.clone(header);
        this.skipHeaderRecord = skipHeaderRecord;
        this.ignoreHeaderCase = ignoreHeaderCase;
        this.trailingDelimiter = trailingDelimiter;
        this.trim = trim;
        this.autoFlush = autoFlush;
        this.quotedNullString = this.quoteCharacter + nullString + this.quoteCharacter;
        this.duplicateHeaderMode = duplicateHeaderMode;
        this.validate();
    }

    private void append(char c, Appendable appendable) throws IOException {
        appendable.append(c);
    }

    private void append(CharSequence csq, Appendable appendable) throws IOException {
        appendable.append(csq);
    }

    public Builder builder() {
        return Builder.create(this);
    }

    CSVFormat copy() {
        return this.builder().build();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        CSVFormat other = (CSVFormat)obj;
        return this.duplicateHeaderMode == other.duplicateHeaderMode && this.allowMissingColumnNames == other.allowMissingColumnNames && this.autoFlush == other.autoFlush && Objects.equals(this.commentMarker, other.commentMarker) && Objects.equals(this.delimiter, other.delimiter) && Objects.equals(this.escapeCharacter, other.escapeCharacter) && Arrays.equals(this.headers, other.headers) && Arrays.equals(this.headerComments, other.headerComments) && this.ignoreEmptyLines == other.ignoreEmptyLines && this.ignoreHeaderCase == other.ignoreHeaderCase && this.ignoreSurroundingSpaces == other.ignoreSurroundingSpaces && Objects.equals(this.nullString, other.nullString) && Objects.equals(this.quoteCharacter, other.quoteCharacter) && this.quoteMode == other.quoteMode && Objects.equals(this.quotedNullString, other.quotedNullString) && Objects.equals(this.recordSeparator, other.recordSeparator) && this.skipHeaderRecord == other.skipHeaderRecord && this.trailingDelimiter == other.trailingDelimiter && this.trim == other.trim;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String format(Object ... values2) {
        StringWriter out = new StringWriter();
        try (CSVPrinter csvPrinter = new CSVPrinter(out, this);){
            csvPrinter.printRecord(values2);
            String res = out.toString();
            int len = this.recordSeparator != null ? res.length() - this.recordSeparator.length() : res.length();
            String string = res.substring(0, len);
            return string;
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Deprecated
    public boolean getAllowDuplicateHeaderNames() {
        return this.duplicateHeaderMode == DuplicateHeaderMode.ALLOW_ALL;
    }

    public boolean getAllowMissingColumnNames() {
        return this.allowMissingColumnNames;
    }

    public boolean getAutoFlush() {
        return this.autoFlush;
    }

    public Character getCommentMarker() {
        return this.commentMarker;
    }

    @Deprecated
    public char getDelimiter() {
        return this.delimiter.charAt(0);
    }

    public String getDelimiterString() {
        return this.delimiter;
    }

    public DuplicateHeaderMode getDuplicateHeaderMode() {
        return this.duplicateHeaderMode;
    }

    public Character getEscapeCharacter() {
        return this.escapeCharacter;
    }

    public String[] getHeader() {
        return this.headers != null ? (String[])this.headers.clone() : null;
    }

    public String[] getHeaderComments() {
        return this.headerComments != null ? (String[])this.headerComments.clone() : null;
    }

    public boolean getIgnoreEmptyLines() {
        return this.ignoreEmptyLines;
    }

    public boolean getIgnoreHeaderCase() {
        return this.ignoreHeaderCase;
    }

    public boolean getIgnoreSurroundingSpaces() {
        return this.ignoreSurroundingSpaces;
    }

    public String getNullString() {
        return this.nullString;
    }

    public Character getQuoteCharacter() {
        return this.quoteCharacter;
    }

    public QuoteMode getQuoteMode() {
        return this.quoteMode;
    }

    public String getRecordSeparator() {
        return this.recordSeparator;
    }

    public boolean getSkipHeaderRecord() {
        return this.skipHeaderRecord;
    }

    public boolean getTrailingDelimiter() {
        return this.trailingDelimiter;
    }

    public boolean getTrim() {
        return this.trim;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + Arrays.hashCode(this.headers);
        result = 31 * result + Arrays.hashCode(this.headerComments);
        return 31 * result + Objects.hash(new Object[]{this.duplicateHeaderMode, this.allowMissingColumnNames, this.autoFlush, this.commentMarker, this.delimiter, this.escapeCharacter, this.ignoreEmptyLines, this.ignoreHeaderCase, this.ignoreSurroundingSpaces, this.nullString, this.quoteCharacter, this.quoteMode, this.quotedNullString, this.recordSeparator, this.skipHeaderRecord, this.trailingDelimiter, this.trim});
    }

    public boolean isCommentMarkerSet() {
        return this.commentMarker != null;
    }

    private boolean isDelimiter(char ch, CharSequence charSeq, int startIndex, char[] delimiter, int delimiterLength) {
        if (ch != delimiter[0]) {
            return false;
        }
        int len = charSeq.length();
        if (startIndex + delimiterLength > len) {
            return false;
        }
        for (int i = 1; i < delimiterLength; ++i) {
            if (charSeq.charAt(startIndex + i) == delimiter[i]) continue;
            return false;
        }
        return true;
    }

    public boolean isEscapeCharacterSet() {
        return this.escapeCharacter != null;
    }

    public boolean isNullStringSet() {
        return this.nullString != null;
    }

    public boolean isQuoteCharacterSet() {
        return this.quoteCharacter != null;
    }

    public CSVParser parse(Reader reader) throws IOException {
        return new CSVParser(reader, this);
    }

    public CSVPrinter print(Appendable out) throws IOException {
        return new CSVPrinter(out, this);
    }

    public CSVPrinter print(File out, Charset charset) throws IOException {
        return new CSVPrinter(new OutputStreamWriter((OutputStream)new FileOutputStream(out), charset), this);
    }

    public synchronized void print(Object value, Appendable out, boolean newRecord) throws IOException {
        CharSequence charSequence;
        if (value == null) {
            charSequence = null == this.nullString ? "" : (QuoteMode.ALL == this.quoteMode ? this.quotedNullString : this.nullString);
        } else if (value instanceof CharSequence) {
            charSequence = (CharSequence)value;
        } else {
            if (value instanceof Reader) {
                this.print((Reader)value, out, newRecord);
                return;
            }
            charSequence = value.toString();
        }
        charSequence = this.getTrim() ? CSVFormat.trim(charSequence) : charSequence;
        this.print(value, charSequence, out, newRecord);
    }

    private synchronized void print(Object object, CharSequence value, Appendable out, boolean newRecord) throws IOException {
        boolean offset = false;
        int len = value.length();
        if (!newRecord) {
            out.append(this.getDelimiterString());
        }
        if (object == null) {
            out.append(value);
        } else if (this.isQuoteCharacterSet()) {
            this.printWithQuotes(object, value, out, newRecord);
        } else if (this.isEscapeCharacterSet()) {
            this.printWithEscapes(value, out);
        } else {
            out.append(value, 0, len);
        }
    }

    public CSVPrinter print(Path out, Charset charset) throws IOException {
        return this.print(Files.newBufferedWriter(out, charset, new OpenOption[0]));
    }

    private void print(Reader reader, Appendable out, boolean newRecord) throws IOException {
        if (!newRecord) {
            this.append(this.getDelimiterString(), out);
        }
        if (this.isQuoteCharacterSet()) {
            this.printWithQuotes(reader, out);
        } else if (this.isEscapeCharacterSet()) {
            this.printWithEscapes(reader, out);
        } else if (out instanceof Writer) {
            IOUtils.copyLarge(reader, (Writer)out);
        } else {
            IOUtils.copy(reader, out);
        }
    }

    public CSVPrinter printer() throws IOException {
        return new CSVPrinter(System.out, this);
    }

    public synchronized void println(Appendable appendable) throws IOException {
        if (this.getTrailingDelimiter()) {
            this.append(this.getDelimiterString(), appendable);
        }
        if (this.recordSeparator != null) {
            this.append(this.recordSeparator, appendable);
        }
    }

    public synchronized void printRecord(Appendable appendable, Object ... values2) throws IOException {
        for (int i = 0; i < values2.length; ++i) {
            this.print(values2[i], appendable, i == 0);
        }
        this.println(appendable);
    }

    private void printWithEscapes(CharSequence charSeq, Appendable appendable) throws IOException {
        int pos;
        int start2 = 0;
        int end = charSeq.length();
        char[] delim = this.getDelimiterString().toCharArray();
        int delimLength = delim.length;
        char escape = this.getEscapeCharacter().charValue();
        for (pos = 0; pos < end; ++pos) {
            char c = charSeq.charAt(pos);
            boolean isDelimiterStart = this.isDelimiter(c, charSeq, pos, delim, delimLength);
            if (c != '\r' && c != '\n' && c != escape && !isDelimiterStart) continue;
            if (pos > start2) {
                appendable.append(charSeq, start2, pos);
            }
            if (c == '\n') {
                c = 'n';
            } else if (c == '\r') {
                c = 'r';
            }
            appendable.append(escape);
            appendable.append(c);
            if (isDelimiterStart) {
                for (int i = 1; i < delimLength; ++i) {
                    c = charSeq.charAt(++pos);
                    appendable.append(escape);
                    appendable.append(c);
                }
            }
            start2 = pos + 1;
        }
        if (pos > start2) {
            appendable.append(charSeq, start2, pos);
        }
    }

    private void printWithEscapes(Reader reader, Appendable appendable) throws IOException {
        int c;
        int start2 = 0;
        int pos = 0;
        ExtendedBufferedReader bufferedReader = new ExtendedBufferedReader(reader);
        char[] delim = this.getDelimiterString().toCharArray();
        int delimLength = delim.length;
        char escape = this.getEscapeCharacter().charValue();
        StringBuilder builder = new StringBuilder(4096);
        while (-1 != (c = bufferedReader.read())) {
            builder.append((char)c);
            boolean isDelimiterStart = this.isDelimiter((char)c, builder.toString() + new String(bufferedReader.lookAhead(delimLength - 1)), pos, delim, delimLength);
            if (c == 13 || c == 10 || c == escape || isDelimiterStart) {
                if (pos > start2) {
                    this.append(builder.substring(start2, pos), appendable);
                    builder.setLength(0);
                    pos = -1;
                }
                if (c == 10) {
                    c = 110;
                } else if (c == 13) {
                    c = 114;
                }
                this.append(escape, appendable);
                this.append((char)c, appendable);
                if (isDelimiterStart) {
                    for (int i = 1; i < delimLength; ++i) {
                        c = bufferedReader.read();
                        this.append(escape, appendable);
                        this.append((char)c, appendable);
                    }
                }
                start2 = pos + 1;
            }
            ++pos;
        }
        if (pos > start2) {
            this.append(builder.substring(start2, pos), appendable);
        }
    }

    private void printWithQuotes(Object object, CharSequence charSeq, Appendable out, boolean newRecord) throws IOException {
        char c;
        boolean quote = false;
        int start2 = 0;
        int pos = 0;
        int len = charSeq.length();
        char[] delim = this.getDelimiterString().toCharArray();
        int delimLength = delim.length;
        char quoteChar = this.getQuoteCharacter().charValue();
        char escapeChar = this.isEscapeCharacterSet() ? this.getEscapeCharacter().charValue() : quoteChar;
        QuoteMode quoteModePolicy = this.getQuoteMode();
        if (quoteModePolicy == null) {
            quoteModePolicy = QuoteMode.MINIMAL;
        }
        switch (quoteModePolicy) {
            case ALL: 
            case ALL_NON_NULL: {
                quote = true;
                break;
            }
            case NON_NUMERIC: {
                quote = !(object instanceof Number);
                break;
            }
            case NONE: {
                this.printWithEscapes(charSeq, out);
                return;
            }
            case MINIMAL: {
                if (len <= 0) {
                    if (newRecord) {
                        quote = true;
                    }
                } else {
                    c = charSeq.charAt(pos);
                    if (c <= '#') {
                        quote = true;
                    } else {
                        while (pos < len) {
                            c = charSeq.charAt(pos);
                            if (c == '\n' || c == '\r' || c == quoteChar || c == escapeChar || this.isDelimiter(c, charSeq, pos, delim, delimLength)) {
                                quote = true;
                                break;
                            }
                            ++pos;
                        }
                        if (!quote && CSVFormat.isTrimChar(c = charSeq.charAt(pos = len - 1))) {
                            quote = true;
                        }
                    }
                }
                if (quote) break;
                out.append(charSeq, start2, len);
                return;
            }
            default: {
                throw new IllegalStateException("Unexpected Quote value: " + (Object)((Object)quoteModePolicy));
            }
        }
        if (!quote) {
            out.append(charSeq, start2, len);
            return;
        }
        out.append(quoteChar);
        while (pos < len) {
            c = charSeq.charAt(pos);
            if (c == quoteChar || c == escapeChar) {
                out.append(charSeq, start2, pos);
                out.append(escapeChar);
                start2 = pos;
            }
            ++pos;
        }
        out.append(charSeq, start2, pos);
        out.append(quoteChar);
    }

    private void printWithQuotes(Reader reader, Appendable appendable) throws IOException {
        int c;
        if (this.getQuoteMode() == QuoteMode.NONE) {
            this.printWithEscapes(reader, appendable);
            return;
        }
        int pos = 0;
        char quote = this.getQuoteCharacter().charValue();
        StringBuilder builder = new StringBuilder(4096);
        this.append(quote, appendable);
        while (-1 != (c = reader.read())) {
            builder.append((char)c);
            if (c == quote) {
                if (pos > 0) {
                    this.append(builder.substring(0, pos), appendable);
                    this.append(quote, appendable);
                    builder.setLength(0);
                    pos = -1;
                }
                this.append((char)c, appendable);
            }
            ++pos;
        }
        if (pos > 0) {
            this.append(builder.substring(0, pos), appendable);
        }
        this.append(quote, appendable);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Delimiter=<").append(this.delimiter).append('>');
        if (this.isEscapeCharacterSet()) {
            sb.append(' ');
            sb.append("Escape=<").append(this.escapeCharacter).append('>');
        }
        if (this.isQuoteCharacterSet()) {
            sb.append(' ');
            sb.append("QuoteChar=<").append(this.quoteCharacter).append('>');
        }
        if (this.quoteMode != null) {
            sb.append(' ');
            sb.append("QuoteMode=<").append((Object)this.quoteMode).append('>');
        }
        if (this.isCommentMarkerSet()) {
            sb.append(' ');
            sb.append("CommentStart=<").append(this.commentMarker).append('>');
        }
        if (this.isNullStringSet()) {
            sb.append(' ');
            sb.append("NullString=<").append(this.nullString).append('>');
        }
        if (this.recordSeparator != null) {
            sb.append(' ');
            sb.append("RecordSeparator=<").append(this.recordSeparator).append('>');
        }
        if (this.getIgnoreEmptyLines()) {
            sb.append(" EmptyLines:ignored");
        }
        if (this.getIgnoreSurroundingSpaces()) {
            sb.append(" SurroundingSpaces:ignored");
        }
        if (this.getIgnoreHeaderCase()) {
            sb.append(" IgnoreHeaderCase:ignored");
        }
        sb.append(" SkipHeaderRecord:").append(this.skipHeaderRecord);
        if (this.headerComments != null) {
            sb.append(' ');
            sb.append("HeaderComments:").append(Arrays.toString(this.headerComments));
        }
        if (this.headers != null) {
            sb.append(' ');
            sb.append("Header:").append(Arrays.toString(this.headers));
        }
        return sb.toString();
    }

    String trim(String value) {
        return this.getTrim() ? value.trim() : value;
    }

    private void validate() throws IllegalArgumentException {
        if (CSVFormat.containsLineBreak(this.delimiter)) {
            throw new IllegalArgumentException("The delimiter cannot be a line break");
        }
        if (this.quoteCharacter != null && CSVFormat.contains(this.delimiter, this.quoteCharacter.charValue())) {
            throw new IllegalArgumentException("The quoteChar character and the delimiter cannot be the same ('" + this.quoteCharacter + "')");
        }
        if (this.escapeCharacter != null && CSVFormat.contains(this.delimiter, this.escapeCharacter.charValue())) {
            throw new IllegalArgumentException("The escape character and the delimiter cannot be the same ('" + this.escapeCharacter + "')");
        }
        if (this.commentMarker != null && CSVFormat.contains(this.delimiter, this.commentMarker.charValue())) {
            throw new IllegalArgumentException("The comment start character and the delimiter cannot be the same ('" + this.commentMarker + "')");
        }
        if (this.quoteCharacter != null && this.quoteCharacter.equals(this.commentMarker)) {
            throw new IllegalArgumentException("The comment start character and the quoteChar cannot be the same ('" + this.commentMarker + "')");
        }
        if (this.escapeCharacter != null && this.escapeCharacter.equals(this.commentMarker)) {
            throw new IllegalArgumentException("The comment start and the escape character cannot be the same ('" + this.commentMarker + "')");
        }
        if (this.escapeCharacter == null && this.quoteMode == QuoteMode.NONE) {
            throw new IllegalArgumentException("No quotes mode set but no escape character is set");
        }
        if (this.headers != null && this.duplicateHeaderMode != DuplicateHeaderMode.ALLOW_ALL) {
            HashSet<String> dupCheckSet = new HashSet<String>(this.headers.length);
            boolean emptyDuplicatesAllowed = this.duplicateHeaderMode == DuplicateHeaderMode.ALLOW_EMPTY;
            for (String header : this.headers) {
                boolean containsHeader;
                boolean blank = CSVFormat.isBlank(header);
                boolean bl = containsHeader = !dupCheckSet.add(blank ? "" : header);
                if (!containsHeader || blank && emptyDuplicatesAllowed) continue;
                throw new IllegalArgumentException(String.format("The header contains a duplicate name: \"%s\" in %s. If this is valid then use CSVFormat.Builder.setDuplicateHeaderMode().", header, Arrays.toString(this.headers)));
            }
        }
    }

    @Deprecated
    public CSVFormat withAllowDuplicateHeaderNames() {
        return this.builder().setDuplicateHeaderMode(DuplicateHeaderMode.ALLOW_ALL).build();
    }

    @Deprecated
    public CSVFormat withAllowDuplicateHeaderNames(boolean allowDuplicateHeaderNames) {
        DuplicateHeaderMode mode = allowDuplicateHeaderNames ? DuplicateHeaderMode.ALLOW_ALL : DuplicateHeaderMode.ALLOW_EMPTY;
        return this.builder().setDuplicateHeaderMode(mode).build();
    }

    @Deprecated
    public CSVFormat withAllowMissingColumnNames() {
        return this.builder().setAllowMissingColumnNames(true).build();
    }

    @Deprecated
    public CSVFormat withAllowMissingColumnNames(boolean allowMissingColumnNames) {
        return this.builder().setAllowMissingColumnNames(allowMissingColumnNames).build();
    }

    @Deprecated
    public CSVFormat withAutoFlush(boolean autoFlush) {
        return this.builder().setAutoFlush(autoFlush).build();
    }

    @Deprecated
    public CSVFormat withCommentMarker(char commentMarker) {
        return this.builder().setCommentMarker(commentMarker).build();
    }

    @Deprecated
    public CSVFormat withCommentMarker(Character commentMarker) {
        return this.builder().setCommentMarker(commentMarker).build();
    }

    @Deprecated
    public CSVFormat withDelimiter(char delimiter) {
        return this.builder().setDelimiter(delimiter).build();
    }

    @Deprecated
    public CSVFormat withEscape(char escape) {
        return this.builder().setEscape(escape).build();
    }

    @Deprecated
    public CSVFormat withEscape(Character escape) {
        return this.builder().setEscape(escape).build();
    }

    @Deprecated
    public CSVFormat withFirstRecordAsHeader() {
        return this.builder().setHeader(new String[0]).setSkipHeaderRecord(true).build();
    }

    @Deprecated
    public CSVFormat withHeader(Class<? extends Enum<?>> headerEnum) {
        return this.builder().setHeader(headerEnum).build();
    }

    @Deprecated
    public CSVFormat withHeader(ResultSet resultSet) throws SQLException {
        return this.builder().setHeader(resultSet).build();
    }

    @Deprecated
    public CSVFormat withHeader(ResultSetMetaData resultSetMetaData) throws SQLException {
        return this.builder().setHeader(resultSetMetaData).build();
    }

    @Deprecated
    public CSVFormat withHeader(String ... header) {
        return this.builder().setHeader(header).build();
    }

    @Deprecated
    public CSVFormat withHeaderComments(Object ... headerComments) {
        return this.builder().setHeaderComments(headerComments).build();
    }

    @Deprecated
    public CSVFormat withIgnoreEmptyLines() {
        return this.builder().setIgnoreEmptyLines(true).build();
    }

    @Deprecated
    public CSVFormat withIgnoreEmptyLines(boolean ignoreEmptyLines) {
        return this.builder().setIgnoreEmptyLines(ignoreEmptyLines).build();
    }

    @Deprecated
    public CSVFormat withIgnoreHeaderCase() {
        return this.builder().setIgnoreHeaderCase(true).build();
    }

    @Deprecated
    public CSVFormat withIgnoreHeaderCase(boolean ignoreHeaderCase) {
        return this.builder().setIgnoreHeaderCase(ignoreHeaderCase).build();
    }

    @Deprecated
    public CSVFormat withIgnoreSurroundingSpaces() {
        return this.builder().setIgnoreSurroundingSpaces(true).build();
    }

    @Deprecated
    public CSVFormat withIgnoreSurroundingSpaces(boolean ignoreSurroundingSpaces) {
        return this.builder().setIgnoreSurroundingSpaces(ignoreSurroundingSpaces).build();
    }

    @Deprecated
    public CSVFormat withNullString(String nullString) {
        return this.builder().setNullString(nullString).build();
    }

    @Deprecated
    public CSVFormat withQuote(char quoteChar) {
        return this.builder().setQuote(quoteChar).build();
    }

    @Deprecated
    public CSVFormat withQuote(Character quoteChar) {
        return this.builder().setQuote(quoteChar).build();
    }

    @Deprecated
    public CSVFormat withQuoteMode(QuoteMode quoteMode) {
        return this.builder().setQuoteMode(quoteMode).build();
    }

    @Deprecated
    public CSVFormat withRecordSeparator(char recordSeparator) {
        return this.builder().setRecordSeparator(recordSeparator).build();
    }

    @Deprecated
    public CSVFormat withRecordSeparator(String recordSeparator) {
        return this.builder().setRecordSeparator(recordSeparator).build();
    }

    @Deprecated
    public CSVFormat withSkipHeaderRecord() {
        return this.builder().setSkipHeaderRecord(true).build();
    }

    @Deprecated
    public CSVFormat withSkipHeaderRecord(boolean skipHeaderRecord) {
        return this.builder().setSkipHeaderRecord(skipHeaderRecord).build();
    }

    @Deprecated
    public CSVFormat withSystemRecordSeparator() {
        return this.builder().setRecordSeparator(System.lineSeparator()).build();
    }

    @Deprecated
    public CSVFormat withTrailingDelimiter() {
        return this.builder().setTrailingDelimiter(true).build();
    }

    @Deprecated
    public CSVFormat withTrailingDelimiter(boolean trailingDelimiter) {
        return this.builder().setTrailingDelimiter(trailingDelimiter).build();
    }

    @Deprecated
    public CSVFormat withTrim() {
        return this.builder().setTrim(true).build();
    }

    @Deprecated
    public CSVFormat withTrim(boolean trim) {
        return this.builder().setTrim(trim).build();
    }

    public static enum Predefined {
        Default(DEFAULT),
        Excel(EXCEL),
        InformixUnload(INFORMIX_UNLOAD),
        InformixUnloadCsv(INFORMIX_UNLOAD_CSV),
        MongoDBCsv(MONGODB_CSV),
        MongoDBTsv(MONGODB_TSV),
        MySQL(MYSQL),
        Oracle(ORACLE),
        PostgreSQLCsv(POSTGRESQL_CSV),
        PostgreSQLText(POSTGRESQL_TEXT),
        RFC4180(RFC4180),
        TDF(TDF);

        private final CSVFormat format;

        private Predefined(CSVFormat format) {
            this.format = format;
        }

        public CSVFormat getFormat() {
            return this.format;
        }
    }

    public static class Builder {
        private boolean allowMissingColumnNames;
        private boolean autoFlush;
        private Character commentMarker;
        private String delimiter;
        private DuplicateHeaderMode duplicateHeaderMode;
        private Character escapeCharacter;
        private String[] headerComments;
        private String[] headers;
        private boolean ignoreEmptyLines;
        private boolean ignoreHeaderCase;
        private boolean ignoreSurroundingSpaces;
        private String nullString;
        private Character quoteCharacter;
        private String quotedNullString;
        private QuoteMode quoteMode;
        private String recordSeparator;
        private boolean skipHeaderRecord;
        private boolean trailingDelimiter;
        private boolean trim;

        public static Builder create() {
            return new Builder(DEFAULT);
        }

        public static Builder create(CSVFormat csvFormat) {
            return new Builder(csvFormat);
        }

        private Builder(CSVFormat csvFormat) {
            this.delimiter = csvFormat.delimiter;
            this.quoteCharacter = csvFormat.quoteCharacter;
            this.quoteMode = csvFormat.quoteMode;
            this.commentMarker = csvFormat.commentMarker;
            this.escapeCharacter = csvFormat.escapeCharacter;
            this.ignoreSurroundingSpaces = csvFormat.ignoreSurroundingSpaces;
            this.allowMissingColumnNames = csvFormat.allowMissingColumnNames;
            this.ignoreEmptyLines = csvFormat.ignoreEmptyLines;
            this.recordSeparator = csvFormat.recordSeparator;
            this.nullString = csvFormat.nullString;
            this.headerComments = csvFormat.headerComments;
            this.headers = csvFormat.headers;
            this.skipHeaderRecord = csvFormat.skipHeaderRecord;
            this.ignoreHeaderCase = csvFormat.ignoreHeaderCase;
            this.trailingDelimiter = csvFormat.trailingDelimiter;
            this.trim = csvFormat.trim;
            this.autoFlush = csvFormat.autoFlush;
            this.quotedNullString = csvFormat.quotedNullString;
            this.duplicateHeaderMode = csvFormat.duplicateHeaderMode;
        }

        public CSVFormat build() {
            return new CSVFormat(this);
        }

        @Deprecated
        public Builder setAllowDuplicateHeaderNames(boolean allowDuplicateHeaderNames) {
            this.setDuplicateHeaderMode(allowDuplicateHeaderNames ? DuplicateHeaderMode.ALLOW_ALL : DuplicateHeaderMode.ALLOW_EMPTY);
            return this;
        }

        public Builder setAllowMissingColumnNames(boolean allowMissingColumnNames) {
            this.allowMissingColumnNames = allowMissingColumnNames;
            return this;
        }

        public Builder setAutoFlush(boolean autoFlush) {
            this.autoFlush = autoFlush;
            return this;
        }

        public Builder setCommentMarker(char commentMarker) {
            this.setCommentMarker(Character.valueOf(commentMarker));
            return this;
        }

        public Builder setCommentMarker(Character commentMarker) {
            if (CSVFormat.isLineBreak(commentMarker)) {
                throw new IllegalArgumentException("The comment start marker character cannot be a line break");
            }
            this.commentMarker = commentMarker;
            return this;
        }

        public Builder setDelimiter(char delimiter) {
            return this.setDelimiter(String.valueOf(delimiter));
        }

        public Builder setDelimiter(String delimiter) {
            if (CSVFormat.containsLineBreak(delimiter)) {
                throw new IllegalArgumentException("The delimiter cannot be a line break");
            }
            if (delimiter.isEmpty()) {
                throw new IllegalArgumentException("The delimiter cannot be empty");
            }
            this.delimiter = delimiter;
            return this;
        }

        public Builder setDuplicateHeaderMode(DuplicateHeaderMode duplicateHeaderMode) {
            this.duplicateHeaderMode = Objects.requireNonNull(duplicateHeaderMode, "duplicateHeaderMode");
            return this;
        }

        public Builder setEscape(char escapeCharacter) {
            this.setEscape(Character.valueOf(escapeCharacter));
            return this;
        }

        public Builder setEscape(Character escapeCharacter) {
            if (CSVFormat.isLineBreak(escapeCharacter)) {
                throw new IllegalArgumentException("The escape character cannot be a line break");
            }
            this.escapeCharacter = escapeCharacter;
            return this;
        }

        public Builder setHeader(Class<? extends Enum<?>> headerEnum) {
            String[] header = null;
            if (headerEnum != null) {
                Enum[] enumValues = headerEnum.getEnumConstants();
                header = new String[enumValues.length];
                Arrays.setAll(header, i -> enumValues[i].name());
            }
            return this.setHeader(header);
        }

        public Builder setHeader(ResultSet resultSet) throws SQLException {
            return this.setHeader(resultSet != null ? resultSet.getMetaData() : null);
        }

        public Builder setHeader(ResultSetMetaData resultSetMetaData) throws SQLException {
            String[] labels = null;
            if (resultSetMetaData != null) {
                int columnCount = resultSetMetaData.getColumnCount();
                labels = new String[columnCount];
                for (int i = 0; i < columnCount; ++i) {
                    labels[i] = resultSetMetaData.getColumnLabel(i + 1);
                }
            }
            return this.setHeader(labels);
        }

        public Builder setHeader(String ... header) {
            this.headers = CSVFormat.clone(header);
            return this;
        }

        public Builder setHeaderComments(Object ... headerComments) {
            this.headerComments = CSVFormat.clone(CSVFormat.toStringArray(headerComments));
            return this;
        }

        public Builder setHeaderComments(String ... headerComments) {
            this.headerComments = CSVFormat.clone(headerComments);
            return this;
        }

        public Builder setIgnoreEmptyLines(boolean ignoreEmptyLines) {
            this.ignoreEmptyLines = ignoreEmptyLines;
            return this;
        }

        public Builder setIgnoreHeaderCase(boolean ignoreHeaderCase) {
            this.ignoreHeaderCase = ignoreHeaderCase;
            return this;
        }

        public Builder setIgnoreSurroundingSpaces(boolean ignoreSurroundingSpaces) {
            this.ignoreSurroundingSpaces = ignoreSurroundingSpaces;
            return this;
        }

        public Builder setNullString(String nullString) {
            this.nullString = nullString;
            this.quotedNullString = this.quoteCharacter + nullString + this.quoteCharacter;
            return this;
        }

        public Builder setQuote(char quoteCharacter) {
            this.setQuote(Character.valueOf(quoteCharacter));
            return this;
        }

        public Builder setQuote(Character quoteCharacter) {
            if (CSVFormat.isLineBreak(quoteCharacter)) {
                throw new IllegalArgumentException("The quoteChar cannot be a line break");
            }
            this.quoteCharacter = quoteCharacter;
            return this;
        }

        public Builder setQuoteMode(QuoteMode quoteMode) {
            this.quoteMode = quoteMode;
            return this;
        }

        public Builder setRecordSeparator(char recordSeparator) {
            this.recordSeparator = String.valueOf(recordSeparator);
            return this;
        }

        public Builder setRecordSeparator(String recordSeparator) {
            this.recordSeparator = recordSeparator;
            return this;
        }

        public Builder setSkipHeaderRecord(boolean skipHeaderRecord) {
            this.skipHeaderRecord = skipHeaderRecord;
            return this;
        }

        public Builder setTrailingDelimiter(boolean trailingDelimiter) {
            this.trailingDelimiter = trailingDelimiter;
            return this;
        }

        public Builder setTrim(boolean trim) {
            this.trim = trim;
            return this;
        }
    }
}

