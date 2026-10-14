/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.h2.message.DbException;
import org.h2.mvstore.DataUtils;
import org.h2.store.fs.FileUtils;
import org.h2.tools.SimpleResultSet;
import org.h2.tools.SimpleRowSource;
import org.h2.util.IOUtils;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class Csv
implements SimpleRowSource {
    private String[] columnNames;
    private String characterSet;
    private char escapeCharacter = (char)34;
    private char fieldDelimiter = (char)34;
    private char fieldSeparatorRead = (char)44;
    private String fieldSeparatorWrite = ",";
    private boolean caseSensitiveColumnNames;
    private boolean preserveWhitespace;
    private boolean writeColumnHeader = true;
    private char lineComment;
    private String lineSeparator = System.lineSeparator();
    private String nullString = "";
    private boolean quotedNulls = false;
    private String fileName;
    private BufferedReader input;
    private char[] inputBuffer;
    private int inputBufferPos;
    private int inputBufferStart = -1;
    private int inputBufferEnd;
    private Writer output;
    private boolean endOfLine;
    private boolean endOfFile;

    private int writeResultSet(ResultSet resultSet) throws SQLException {
        try {
            int n;
            int n2 = 0;
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int n3 = resultSetMetaData.getColumnCount();
            String[] stringArray = new String[n3];
            for (n = 0; n < n3; ++n) {
                stringArray[n] = resultSetMetaData.getColumnLabel(n + 1);
            }
            if (this.writeColumnHeader) {
                this.writeRow(stringArray);
            }
            while (resultSet.next()) {
                for (n = 0; n < n3; ++n) {
                    stringArray[n] = resultSet.getString(n + 1);
                }
                this.writeRow(stringArray);
                ++n2;
            }
            this.output.close();
            n = n2;
            return n;
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
        finally {
            this.close();
            JdbcUtils.closeSilently(resultSet);
        }
    }

    public int write(Writer writer, ResultSet resultSet) throws SQLException {
        this.output = writer;
        return this.writeResultSet(resultSet);
    }

    public int write(String string, ResultSet resultSet, String string2) throws SQLException {
        this.init(string, string2);
        try {
            this.initWrite();
            return this.writeResultSet(resultSet);
        }
        catch (IOException iOException) {
            throw Csv.convertException("IOException writing " + string, iOException);
        }
    }

    public int write(Connection connection, String string, String string2, String string3) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(string2);
        int n = this.write(string, resultSet, string3);
        statement.close();
        return n;
    }

    public ResultSet read(String string, String[] stringArray, String string2) throws SQLException {
        this.init(string, string2);
        try {
            return this.readResultSet(stringArray);
        }
        catch (IOException iOException) {
            throw Csv.convertException("IOException reading " + string, iOException);
        }
    }

    public ResultSet read(Reader reader, String[] stringArray) throws IOException {
        this.init(null, null);
        this.input = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, 4096);
        return this.readResultSet(stringArray);
    }

    private ResultSet readResultSet(String[] stringArray) throws IOException {
        this.columnNames = stringArray;
        this.initRead();
        SimpleResultSet simpleResultSet = new SimpleResultSet(this);
        this.makeColumnNamesUnique();
        for (String string : this.columnNames) {
            simpleResultSet.addColumn(string, 12, Integer.MAX_VALUE, 0);
        }
        return simpleResultSet;
    }

    private void makeColumnNamesUnique() {
        for (int i = 0; i < this.columnNames.length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            String string = this.columnNames[i];
            if (string == null || string.isEmpty()) {
                stringBuilder.append('C').append(i + 1);
            } else {
                stringBuilder.append(string);
            }
            for (int j = 0; j < i; ++j) {
                String string2 = this.columnNames[j];
                if (!stringBuilder.toString().equals(string2)) continue;
                stringBuilder.append('1');
                j = -1;
            }
            this.columnNames[i] = stringBuilder.toString();
        }
    }

    private void init(String string, String string2) {
        this.fileName = string;
        this.characterSet = string2;
    }

    private void initWrite() throws IOException {
        if (this.output == null) {
            try {
                OutputStream outputStream2 = FileUtils.newOutputStream(this.fileName, false);
                outputStream2 = new BufferedOutputStream(outputStream2, 4096);
                this.output = new BufferedWriter(this.characterSet != null ? new OutputStreamWriter(outputStream2, this.characterSet) : new OutputStreamWriter(outputStream2));
            }
            catch (Exception exception) {
                this.close();
                throw DataUtils.convertToIOException(exception);
            }
        }
    }

    private void writeRow(String[] stringArray) throws IOException {
        for (int i = 0; i < stringArray.length; ++i) {
            String string;
            if (i > 0 && this.fieldSeparatorWrite != null) {
                this.output.write(this.fieldSeparatorWrite);
            }
            if ((string = stringArray[i]) != null) {
                if (this.escapeCharacter != '\u0000') {
                    if (this.fieldDelimiter != '\u0000') {
                        this.output.write(this.fieldDelimiter);
                    }
                    this.output.write(this.escape(string));
                    if (this.fieldDelimiter == '\u0000') continue;
                    this.output.write(this.fieldDelimiter);
                    continue;
                }
                this.output.write(string);
                continue;
            }
            if (this.nullString == null) continue;
            if (this.quotedNulls && this.fieldDelimiter != '\u0000') {
                this.output.write(this.fieldDelimiter);
                this.output.write(this.nullString);
                this.output.write(this.fieldDelimiter);
                continue;
            }
            this.output.write(this.nullString);
        }
        this.output.write(this.lineSeparator);
    }

    private String escape(String string) {
        if (string.indexOf(this.fieldDelimiter) < 0 && (this.escapeCharacter == this.fieldDelimiter || string.indexOf(this.escapeCharacter) < 0)) {
            return string;
        }
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == this.fieldDelimiter || c == this.escapeCharacter) {
                stringBuilder.append(this.escapeCharacter);
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void initRead() throws IOException {
        if (this.input == null) {
            try {
                this.input = FileUtils.newBufferedReader(this.fileName, this.characterSet != null ? Charset.forName(this.characterSet) : StandardCharsets.UTF_8);
            }
            catch (IOException iOException) {
                this.close();
                throw iOException;
            }
        }
        this.input.mark(1);
        int n = this.input.read();
        if (n != 65279) {
            this.input.reset();
        }
        this.inputBuffer = new char[8192];
        if (this.columnNames == null) {
            this.readHeader();
        }
    }

    private void readHeader() throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        while (true) {
            String string;
            if ((string = this.readValue()) == null) {
                if (this.endOfLine) {
                    if (!this.endOfFile && arrayList.isEmpty()) continue;
                    break;
                }
                string = "COLUMN" + arrayList.size();
                arrayList.add(string);
                continue;
            }
            if (string.isEmpty()) {
                string = "COLUMN" + arrayList.size();
            } else if (!this.caseSensitiveColumnNames && Csv.isSimpleColumnName(string)) {
                string = StringUtils.toUpperEnglish(string);
            }
            arrayList.add(string);
            if (this.endOfLine) break;
        }
        this.columnNames = arrayList.toArray(new String[0]);
    }

    private static boolean isSimpleColumnName(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (!(i == 0 ? c != '_' && !Character.isLetter(c) : c != '_' && !Character.isLetterOrDigit(c))) continue;
            return false;
        }
        return string.length() != 0;
    }

    private void pushBack() {
        --this.inputBufferPos;
    }

    private int readChar() throws IOException {
        if (this.inputBufferPos >= this.inputBufferEnd) {
            return this.readBuffer();
        }
        return this.inputBuffer[this.inputBufferPos++];
    }

    private int readBuffer() throws IOException {
        int n;
        if (this.endOfFile) {
            return -1;
        }
        if (this.inputBufferStart >= 0) {
            n = this.inputBufferPos - this.inputBufferStart;
            if (n > 0) {
                char[] cArray = this.inputBuffer;
                if (n + 4096 > cArray.length) {
                    this.inputBuffer = new char[cArray.length * 2];
                }
                System.arraycopy(cArray, this.inputBufferStart, this.inputBuffer, 0, n);
            }
            this.inputBufferStart = 0;
        } else {
            n = 0;
        }
        this.inputBufferPos = n;
        int n2 = this.input.read(this.inputBuffer, n, 4096);
        if (n2 == -1) {
            this.inputBufferEnd = -1024;
            this.endOfFile = true;
            ++this.inputBufferPos;
            return -1;
        }
        this.inputBufferEnd = n + n2;
        return this.inputBuffer[this.inputBufferPos++];
    }

    private String readValue() throws IOException {
        int n;
        this.endOfLine = false;
        this.inputBufferStart = this.inputBufferPos;
        do {
            if ((n = this.readChar()) == this.fieldDelimiter) {
                int n2;
                boolean bl;
                block16: {
                    bl = false;
                    this.inputBufferStart = this.inputBufferPos;
                    while (true) {
                        if ((n = this.readChar()) == this.fieldDelimiter) {
                            n = this.readChar();
                            if (n != this.fieldDelimiter) {
                                n2 = 2;
                                break block16;
                            }
                            bl = true;
                            continue;
                        }
                        if (n == this.escapeCharacter) {
                            n = this.readChar();
                            if (n < 0) {
                                n2 = 1;
                                break block16;
                            }
                            bl = true;
                            continue;
                        }
                        if (n < 0) break;
                    }
                    n2 = 1;
                }
                String string = new String(this.inputBuffer, this.inputBufferStart, this.inputBufferPos - this.inputBufferStart - n2);
                if (bl) {
                    string = this.unEscape(string);
                }
                this.inputBufferStart = -1;
                while (n != this.fieldSeparatorRead) {
                    if (n == 10 || n < 0 || n == 13) {
                        this.endOfLine = true;
                        break;
                    }
                    if (n != 32 && n != 9) {
                        this.pushBack();
                        break;
                    }
                    n = this.readChar();
                }
                return string;
            }
            if (n == 10 || n < 0 || n == 13) {
                this.endOfLine = true;
                return null;
            }
            if (n != this.fieldSeparatorRead) continue;
            return null;
        } while (n <= 32);
        if (this.lineComment != '\u0000' && n == this.lineComment) {
            this.inputBufferStart = -1;
            while ((n = this.readChar()) != 10 && n >= 0 && n != 13) {
            }
            this.endOfLine = true;
            return null;
        }
        while ((n = this.readChar()) != this.fieldSeparatorRead) {
            if (n != 10 && n >= 0 && n != 13) continue;
            this.endOfLine = true;
            break;
        }
        String string = new String(this.inputBuffer, this.inputBufferStart, this.inputBufferPos - this.inputBufferStart - 1);
        if (!this.preserveWhitespace) {
            string = string.trim();
        }
        this.inputBufferStart = -1;
        return this.readNull(string);
    }

    private String readNull(String string) {
        return string.equals(this.nullString) ? null : string;
    }

    private String unEscape(String string) {
        int n;
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n2 = 0;
        char[] cArray = null;
        while ((n = string.indexOf(this.escapeCharacter, n2)) >= 0 || (n = string.indexOf(this.fieldDelimiter, n2)) >= 0) {
            if (cArray == null) {
                cArray = string.toCharArray();
            }
            stringBuilder.append(cArray, n2, n - n2);
            if (n == string.length() - 1) {
                n2 = string.length();
                break;
            }
            stringBuilder.append(cArray[n + 1]);
            n2 = n + 2;
        }
        stringBuilder.append(string, n2, string.length());
        return stringBuilder.toString();
    }

    @Override
    public Object[] readRow() throws SQLException {
        if (this.input == null) {
            return null;
        }
        Object[] objectArray = new String[this.columnNames.length];
        try {
            int n = 0;
            while (true) {
                String string;
                if ((string = this.readValue()) == null && this.endOfLine) {
                    if (n == 0) {
                        if (!this.endOfFile) continue;
                        return null;
                    }
                    break;
                }
                if (n < objectArray.length) {
                    if (this.quotedNulls) {
                        objectArray[n++] = string != null && !string.equals(this.nullString) ? string : null;
                    } else {
                        objectArray[n++] = string;
                    }
                }
                if (this.endOfLine) break;
            }
        }
        catch (IOException iOException) {
            throw Csv.convertException("IOException reading from " + this.fileName, iOException);
        }
        return objectArray;
    }

    private static SQLException convertException(String string, Exception exception) {
        return DbException.getJdbcSQLException(90028, exception, string);
    }

    @Override
    public void close() {
        IOUtils.closeSilently(this.input);
        this.input = null;
        IOUtils.closeSilently(this.output);
        this.output = null;
    }

    @Override
    public void reset() throws SQLException {
        throw new SQLException("Method is not supported", "CSV");
    }

    public void setFieldSeparatorWrite(String string) {
        this.fieldSeparatorWrite = string;
    }

    public String getFieldSeparatorWrite() {
        return this.fieldSeparatorWrite;
    }

    public void setCaseSensitiveColumnNames(boolean bl) {
        this.caseSensitiveColumnNames = bl;
    }

    public boolean getCaseSensitiveColumnNames() {
        return this.caseSensitiveColumnNames;
    }

    public void setFieldSeparatorRead(char c) {
        this.fieldSeparatorRead = c;
    }

    public char getFieldSeparatorRead() {
        return this.fieldSeparatorRead;
    }

    public void setLineCommentCharacter(char c) {
        this.lineComment = c;
    }

    public char getLineCommentCharacter() {
        return this.lineComment;
    }

    public void setFieldDelimiter(char c) {
        this.fieldDelimiter = c;
    }

    public char getFieldDelimiter() {
        return this.fieldDelimiter;
    }

    public void setEscapeCharacter(char c) {
        this.escapeCharacter = c;
    }

    public char getEscapeCharacter() {
        return this.escapeCharacter;
    }

    public void setLineSeparator(String string) {
        this.lineSeparator = string;
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public void setQuotedNulls(boolean bl) {
        this.quotedNulls = bl;
    }

    public boolean isQuotedNulls() {
        return this.quotedNulls;
    }

    public void setNullString(String string) {
        this.nullString = string;
    }

    public String getNullString() {
        return this.nullString;
    }

    public void setPreserveWhitespace(boolean bl) {
        this.preserveWhitespace = bl;
    }

    public boolean getPreserveWhitespace() {
        return this.preserveWhitespace;
    }

    public void setWriteColumnHeader(boolean bl) {
        this.writeColumnHeader = bl;
    }

    public boolean getWriteColumnHeader() {
        return this.writeColumnHeader;
    }

    public String setOptions(String string) {
        String[] stringArray;
        String string2 = null;
        for (String string3 : stringArray = StringUtils.arraySplit(string, ' ', false)) {
            char c;
            if (string3.isEmpty()) continue;
            int n = string3.indexOf(61);
            String string4 = StringUtils.trimSubstring(string3, 0, n);
            String string5 = string3.substring(n + 1);
            char c2 = c = string5.isEmpty() ? (char)'\u0000' : string5.charAt(0);
            if (Csv.isParam(string4, "escape", "esc", "escapeCharacter")) {
                this.setEscapeCharacter(c);
                continue;
            }
            if (Csv.isParam(string4, "fieldDelimiter", "fieldDelim")) {
                this.setFieldDelimiter(c);
                continue;
            }
            if (Csv.isParam(string4, "fieldSeparator", "fieldSep")) {
                this.setFieldSeparatorRead(c);
                this.setFieldSeparatorWrite(string5);
                continue;
            }
            if (Csv.isParam(string4, "lineComment", "lineCommentCharacter")) {
                this.setLineCommentCharacter(c);
                continue;
            }
            if (Csv.isParam(string4, "lineSeparator", "lineSep")) {
                this.setLineSeparator(string5);
                continue;
            }
            if (Csv.isParam(string4, "null", "nullString")) {
                this.setNullString(string5);
                continue;
            }
            if (Csv.isParam(string4, "quotedNulls")) {
                this.setQuotedNulls(Utils.parseBoolean(string5, false, false));
                continue;
            }
            if (Csv.isParam(string4, "charset", "characterSet")) {
                string2 = string5;
                continue;
            }
            if (Csv.isParam(string4, "preserveWhitespace")) {
                this.setPreserveWhitespace(Utils.parseBoolean(string5, false, false));
                continue;
            }
            if (Csv.isParam(string4, "writeColumnHeader")) {
                this.setWriteColumnHeader(Utils.parseBoolean(string5, true, false));
                continue;
            }
            if (Csv.isParam(string4, "caseSensitiveColumnNames")) {
                this.setCaseSensitiveColumnNames(Utils.parseBoolean(string5, false, false));
                continue;
            }
            throw DbException.getUnsupportedException(string4);
        }
        return string2;
    }

    private static boolean isParam(String string, String ... stringArray) {
        for (String string2 : stringArray) {
            if (!string.equalsIgnoreCase(string2)) continue;
            return true;
        }
        return false;
    }
}

