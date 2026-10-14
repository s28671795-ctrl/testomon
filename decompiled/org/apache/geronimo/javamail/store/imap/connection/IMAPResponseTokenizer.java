/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;
import javax.mail.internet.ParameterList;
import org.apache.geronimo.javamail.store.imap.connection.IMAPCommand;
import org.apache.geronimo.javamail.util.ResponseFormatException;

public class IMAPResponseTokenizer {
    protected static final byte[] decodingTable = new byte[256];
    protected static MailDateFormat dateParser;
    public static final Token EOF;
    public static final Token NIL;
    private static final String WHITE = " \t\n\r";
    private static final String atomDelimiters = "(){}%*\"\\ \t\n\r";
    private static final String tokenDelimiters = "<>[].(){}%*\"\\ \t\n\r";
    private byte[] response;
    private int pos;

    protected static void initializeDecodingTable() {
        for (int i = 0; i < IMAPCommand.encodingTable.length; ++i) {
            IMAPResponseTokenizer.decodingTable[IMAPCommand.encodingTable[i]] = (byte)i;
        }
    }

    public IMAPResponseTokenizer(byte[] response) {
        this.response = response;
    }

    public String getRemainder() {
        if (this.pos >= this.response.length) {
            return "";
        }
        try {
            return new String(this.response, this.pos, this.response.length - this.pos, "ISO8859-1");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public Token next() throws MessagingException {
        return this.next(false);
    }

    public Token next(boolean nilAllowed) throws MessagingException {
        return this.readToken(nilAllowed, false);
    }

    public Token next(boolean nilAllowed, boolean expandedDelimiters) throws MessagingException {
        return this.readToken(nilAllowed, expandedDelimiters);
    }

    public Token peek() throws MessagingException {
        return this.peek(false, false);
    }

    public Token peek(boolean nilAllowed) throws MessagingException {
        return this.peek(nilAllowed, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Token peek(boolean nilAllowed, boolean expandedDelimiters) throws MessagingException {
        int start2 = this.pos;
        try {
            Token token = this.readToken(nilAllowed, expandedDelimiters);
            return token;
        }
        finally {
            this.pos = start2;
        }
    }

    private Token readAtomicToken(String delimiters) {
        int start2 = this.pos;
        while (++this.pos < this.response.length) {
            byte ch = this.response[this.pos];
            if (delimiters.indexOf(this.response[this.pos]) == -1 && ch >= 32 && ch < 127) continue;
            break;
        }
        try {
            String value = new String(this.response, start2, this.pos - start2, "ISO8859-1");
            try {
                int intValue = Integer.parseInt(value);
                return new Token(-4, value);
            }
            catch (NumberFormatException e) {
                return new Token(-1, value);
            }
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private Token readToken(boolean nilAllowed, boolean expandedDelimiters) throws MessagingException {
        String delimiters;
        String string = delimiters = expandedDelimiters ? tokenDelimiters : atomDelimiters;
        if (this.pos >= this.response.length) {
            return EOF;
        }
        byte ch = this.response[this.pos];
        if (ch == 34) {
            return this.readQuotedString();
        }
        if (ch == 123) {
            return this.readLiteral();
        }
        if (WHITE.indexOf(ch) != -1) {
            this.eatWhiteSpace();
            return this.readToken(nilAllowed, expandedDelimiters);
        }
        if (ch < 32 || ch >= 127 || delimiters.indexOf(ch) != -1) {
            ++this.pos;
            return new Token(ch, String.valueOf((char)ch));
        }
        Token token = this.readAtomicToken(delimiters);
        if (nilAllowed && token.getValue().equalsIgnoreCase("NIL")) {
            return NIL;
        }
        return token;
    }

    private byte[] readData(boolean nilAllowed) throws MessagingException {
        if (this.pos >= this.response.length) {
            return null;
        }
        byte ch = this.response[this.pos];
        if (ch == 34) {
            return this.readQuotedStringData();
        }
        if (ch == 123) {
            return this.readLiteralData();
        }
        if (WHITE.indexOf(ch) != -1) {
            this.eatWhiteSpace();
            return this.readData(nilAllowed);
        }
        if (ch < 32 || ch >= 127 || atomDelimiters.indexOf(ch) != -1) {
            throw new ResponseFormatException("Invalid string value: " + ch);
        }
        if (nilAllowed) {
            Token token = this.next(true);
            if (token.isType(-6)) {
                return null;
            }
            throw new ResponseFormatException("Invalid string value: " + token.getValue());
        }
        throw new ResponseFormatException("Invalid string value: " + ch);
    }

    private byte[] getEscapedValue(int start2, int end) throws MessagingException {
        ByteArrayOutputStream value = new ByteArrayOutputStream();
        for (int i = start2; i < end; ++i) {
            byte ch = this.response[i];
            if (ch == 92) {
                if (++i == end) {
                    throw new ResponseFormatException("Invalid escape character");
                }
                value.write(this.response[i]);
                continue;
            }
            if (ch == 13) {
                if (i >= end - 1 || this.response[i + 1] != 10) continue;
                ++i;
                continue;
            }
            value.write(ch);
        }
        return value.toByteArray();
    }

    private Token readQuotedString() throws MessagingException {
        try {
            String value = new String(this.readQuotedStringData(), "ISO8859-1");
            return new Token(-2, value);
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private byte[] readQuotedStringData() throws MessagingException {
        int start2 = this.pos + 1;
        boolean requiresEscaping = false;
        while (++this.pos < this.response.length) {
            byte ch = this.response[this.pos];
            if (ch == 34) {
                byte[] value = requiresEscaping ? this.getEscapedValue(start2, this.pos) : this.subarray(start2, this.pos);
                ++this.pos;
                return value;
            }
            if (ch == 92) {
                ++this.pos;
                requiresEscaping = true;
                continue;
            }
            if (ch != 13) continue;
            requiresEscaping = true;
        }
        throw new ResponseFormatException("Missing '\"'");
    }

    protected Token readLiteral() throws MessagingException {
        try {
            String value = new String(this.readLiteralData(), "ISO8859-1");
            return new Token(-3, value);
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected byte[] readLiteralData() throws MessagingException {
        int lengthStart = this.pos + 1;
        int lengthEnd = this.indexOf("}\r\n", lengthStart);
        if (lengthEnd == -1) {
            throw new ResponseFormatException("Missing terminator on literal length");
        }
        int count = 0;
        try {
            count = Integer.parseInt(this.substring(lengthStart, lengthEnd));
        }
        catch (NumberFormatException e) {
            throw new ResponseFormatException("Invalid literal length " + this.substring(lengthStart, lengthEnd));
        }
        this.pos = lengthEnd + 3;
        if (this.pos + count > this.response.length) {
            throw new ResponseFormatException("Invalid literal length: " + count);
        }
        byte[] value = this.subarray(this.pos, this.pos + count);
        this.pos += count;
        return value;
    }

    protected String substring(int start2, int end) {
        try {
            return new String(this.response, start2, end - start2, "ISO8859-1");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected byte[] subarray(int start2, int end) {
        byte[] result = new byte[end - start2];
        System.arraycopy(this.response, start2, result, 0, end - start2);
        return result;
    }

    public boolean match(int position, String needle) {
        int length = needle.length();
        if (this.response.length - position < length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (this.response[position + i] == needle.charAt(i)) continue;
            return false;
        }
        return true;
    }

    public int indexOf(String needle) {
        return this.indexOf(needle, this.pos);
    }

    public int indexOf(String needle, int position) {
        int last = this.response.length - needle.length();
        if (last < position) {
            return -1;
        }
        for (int i = position; i <= last; ++i) {
            if (!this.match(i, needle)) continue;
            return i;
        }
        return -1;
    }

    private void eatWhiteSpace() {
        while (++this.pos < this.response.length && WHITE.indexOf(this.response[this.pos]) != -1) {
        }
    }

    public void checkLeftParen() throws MessagingException {
        Token token = this.next();
        if (token.getType() != 40) {
            throw new ResponseFormatException("Missing '(' in response");
        }
    }

    public void checkRightParen() throws MessagingException {
        Token token = this.next();
        if (token.getType() != 41) {
            throw new ResponseFormatException("Missing ')' in response");
        }
    }

    public String readString() throws MessagingException {
        Token token = this.next(true);
        int type = token.getType();
        if (type == -6) {
            return null;
        }
        if (type != -1 && type != -2 && type != -3 && type != -4) {
            throw new ResponseFormatException("String token expected in response: " + token.getValue());
        }
        return token.getValue();
    }

    public String readEncodedString() throws MessagingException {
        String value = this.readString();
        return this.decode(value);
    }

    public String decode(String original) throws MessagingException {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < original.length(); ++i) {
            char ch = original.charAt(i);
            if (ch == '&') {
                i = IMAPResponseTokenizer.decode(original, i, result);
                continue;
            }
            result.append(ch);
        }
        return result.toString();
    }

    public static int decode(String original, int index, StringBuffer result) throws MessagingException {
        int terminator = original.indexOf(45, index);
        if (terminator == -1) {
            throw new MessagingException("Invalid UTF-7 encoded string");
        }
        if (terminator == index + 1) {
            result.append('&');
            return index + 2;
        }
        int chars = terminator - ++index;
        int quads = chars / 4;
        int residual = chars % 4;
        byte[] buffer = new byte[4];
        int bufferCount = 0;
        for (int i = 0; i < quads; ++i) {
            byte b1 = decodingTable[original.charAt(index++) & 0xFF];
            byte b2 = decodingTable[original.charAt(index++) & 0xFF];
            byte b3 = decodingTable[original.charAt(index++) & 0xFF];
            byte b4 = decodingTable[original.charAt(index++) & 0xFF];
            buffer[bufferCount++] = (byte)(b1 << 2 | b2 >> 4);
            buffer[bufferCount++] = (byte)(b2 << 4 | b3 >> 2);
            buffer[bufferCount++] = (byte)(b3 << 6 | b4);
            if (bufferCount == 4) {
                b1 = buffer[0];
                b2 = buffer[1];
                result.append((char)((b1 << 8) + (b2 & 0xFF)));
                b1 = buffer[2];
                b2 = buffer[3];
                result.append((char)((b1 << 8) + (b2 & 0xFF)));
                bufferCount = 0;
                continue;
            }
            b1 = buffer[0];
            b2 = buffer[1];
            result.append((char)((b1 << 8) + (b2 & 0xFF)));
            buffer[0] = buffer[2];
            bufferCount = 1;
        }
        switch (residual) {
            case 0: {
                if (bufferCount == 1) {
                    throw new MessagingException("Invalid UTF-7 encoded string");
                }
            }
            case 1: {
                throw new MessagingException("Invalid UTF-7 encoded string");
            }
            case 2: {
                if (bufferCount != 1) {
                    throw new MessagingException("Invalid UTF-7 encoded string");
                }
                byte b1 = decodingTable[original.charAt(index++) & 0xFF];
                byte b2 = decodingTable[original.charAt(index++) & 0xFF];
                buffer[bufferCount++] = (byte)(b1 << 2 | b2 >> 4);
                b1 = buffer[0];
                b2 = buffer[1];
                result.append((char)((b1 << 8) + (b2 & 0xFF)));
                break;
            }
            case 3: {
                if (bufferCount == 1) {
                    throw new MessagingException("Invalid UTF-7 encoded string");
                }
                byte b1 = decodingTable[original.charAt(index++) & 0xFF];
                byte b2 = decodingTable[original.charAt(index++) & 0xFF];
                byte b3 = decodingTable[original.charAt(index++) & 0xFF];
                buffer[bufferCount++] = (byte)(b1 << 2 | b2 >> 4);
                buffer[bufferCount++] = (byte)(b2 << 4 | b3 >> 2);
                b1 = buffer[0];
                b2 = buffer[1];
                result.append((char)((b1 << 8) + (b2 & 0xFF)));
                break;
            }
        }
        return terminator + 1;
    }

    public String readAtom() throws MessagingException {
        return this.readAtom(false);
    }

    public String readAtom(boolean expandedDelimiters) throws MessagingException {
        Token token = this.next(false, expandedDelimiters);
        int type = token.getType();
        if (type != -1) {
            throw new ResponseFormatException("ATOM token expected in response: " + token.getValue());
        }
        return token.getValue();
    }

    public int readInteger() throws MessagingException {
        Token token = this.next();
        return token.getInteger();
    }

    public int readLong() throws MessagingException {
        Token token = this.next();
        return token.getInteger();
    }

    public String readStringOrNil() throws MessagingException {
        Token token = this.next(true);
        int type = token.getType();
        if (type != -1 && type != -2 && type != -3 && type != -6) {
            throw new ResponseFormatException("String token or NIL expected in response: " + token.getValue());
        }
        return token.getValue();
    }

    protected String readQuotedStringOrNil() throws MessagingException {
        Token token = this.next(true);
        int type = token.getType();
        if (type != -2 && type != -6) {
            throw new ResponseFormatException("String token or NIL expected in response");
        }
        return token.getValue();
    }

    public Date readDate() throws MessagingException {
        String value = this.readString();
        try {
            return dateParser.parse(value);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Date readDateOrNil() throws MessagingException {
        String value = this.readStringOrNil();
        if (value == null) {
            return null;
        }
        try {
            return dateParser.parse(value);
        }
        catch (Exception e) {
            return null;
        }
    }

    public InternetAddress readAddress() throws MessagingException {
        if (this.peek().getType() != 40) {
            return null;
        }
        this.checkLeftParen();
        String personal = this.readStringOrNil();
        String routing = this.readStringOrNil();
        String mailbox = this.readStringOrNil();
        String host2 = this.readStringOrNil();
        this.checkRightParen();
        if (host2 != null) {
            StringBuffer address = new StringBuffer();
            if (routing != null) {
                address.append(routing);
                address.append(':');
            }
            address.append(mailbox);
            address.append('@');
            address.append(host2);
            try {
                return new InternetAddress(address.toString(), personal);
            }
            catch (UnsupportedEncodingException e) {
                throw new ResponseFormatException("Invalid Internet address format");
            }
        }
        if (mailbox == null) {
            return null;
        }
        StringBuffer groupAddress = new StringBuffer();
        groupAddress.append(mailbox);
        groupAddress.append(':');
        int count = 0;
        while (true) {
            InternetAddress member;
            if ((member = this.readAddress()) == null) {
                groupAddress.append(';');
                try {
                    return new InternetAddress(groupAddress.toString(), personal);
                }
                catch (UnsupportedEncodingException e) {
                    throw new ResponseFormatException("Invalid Internet address format");
                }
            }
            if (count != 0) {
                groupAddress.append(',');
            }
            groupAddress.append(member.toString());
            ++count;
        }
    }

    public InternetAddress[] readAddressList() throws MessagingException {
        Token token = this.next(true);
        int type = token.getType();
        if (type == -6) {
            return null;
        }
        if (type != 40) {
            throw new ResponseFormatException("Missing '(' in response");
        }
        ArrayList<InternetAddress> addresses = new ArrayList<InternetAddress>();
        while (this.notListEnd()) {
            InternetAddress address = this.readAddress();
            addresses.add(address);
        }
        this.checkRightParen();
        return addresses.toArray(new InternetAddress[addresses.size()]);
    }

    public boolean checkListEnd() throws MessagingException {
        Token token = this.peek(true);
        if (token.getType() == 41) {
            this.next();
            return true;
        }
        return false;
    }

    public List readStringList() throws MessagingException {
        Token token = this.peek(true);
        if (token.getType() == 40) {
            ArrayList<String> list = new ArrayList<String>();
            this.next();
            while (this.notListEnd()) {
                String value = this.readString();
                if (value == null) continue;
                list.add(value);
            }
            this.next();
            return list;
        }
        if (token != NIL) {
            ArrayList<String> list = new ArrayList<String>();
            String value = this.readString();
            if (value != null) {
                list.add(value);
            }
            return list;
        }
        this.next();
        return null;
    }

    public List readStrings() throws MessagingException {
        ArrayList<String> list = new ArrayList<String>();
        while (this.hasMore()) {
            String value = this.readString();
            list.add(value);
        }
        return list;
    }

    public void skipExtensionItem() throws MessagingException {
        Token token = this.next();
        int type = token.getType();
        if (type == 40) {
            this.skipNestedValue();
        } else if (type == -5) {
            throw new ResponseFormatException("Missing ')'");
        }
    }

    public void skipNestedValue() throws MessagingException {
        Token token = this.next();
        int type;
        while ((type = token.getType()) != 41) {
            if (type == -5) {
                throw new ResponseFormatException("Missing ')'");
            }
            if (type == 40) {
                this.skipNestedValue();
            }
            token = this.next();
        }
        return;
    }

    public void checkToken(int type) throws MessagingException {
        Token token = this.next();
        if (token.getType() != type) {
            throw new ResponseFormatException("Unexpected token: " + token.getValue());
        }
    }

    public byte[] readByteArray() throws MessagingException {
        return this.readData(true);
    }

    public static int getEncoding(byte[] value) {
        if (value.length == 0) {
            return -2;
        }
        for (int i = 0; i < value.length; ++i) {
            int ch = value[i];
            if ((ch &= 0xFF) == 0) {
                return -3;
            }
            if (ch > 127) {
                return -3;
            }
            if (ch == 13) {
                return -3;
            }
            if (ch == 10) {
                return -3;
            }
            if (atomDelimiters.indexOf(ch) != -1) {
                return -2;
            }
            if (ch >= 32) continue;
            return -2;
        }
        return -1;
    }

    public ParameterList readParameterList() throws MessagingException {
        ParameterList params = new ParameterList();
        Token token = this.next(true, false);
        if (token.isType(-6)) {
            return params;
        }
        while (this.notListEnd()) {
            String name = this.readString();
            String value = this.readString();
            params.set(name, value);
        }
        this.checkRightParen();
        return params;
    }

    public boolean hasMore() throws MessagingException {
        this.eatWhiteSpace();
        return this.pos < this.response.length;
    }

    public boolean notListEnd() throws MessagingException {
        return this.peek().getType() != 41;
    }

    public Flags readFlagList() throws MessagingException {
        Flags flags = new Flags();
        this.checkLeftParen();
        while (this.notListEnd()) {
            Token token = this.next();
            if (token.isType(-1)) {
                flags.add(token.getValue());
                continue;
            }
            if (token.isType(92)) {
                token = this.next();
                if (token.isType(42)) {
                    flags.add(Flags.Flag.USER);
                    continue;
                }
                if (token.isType(-1)) {
                    String name = token.getValue();
                    if (name.equalsIgnoreCase("Seen")) {
                        flags.add(Flags.Flag.SEEN);
                        continue;
                    }
                    if (name.equalsIgnoreCase("RECENT")) {
                        flags.add(Flags.Flag.RECENT);
                        continue;
                    }
                    if (name.equalsIgnoreCase("DELETED")) {
                        flags.add(Flags.Flag.DELETED);
                        continue;
                    }
                    if (name.equalsIgnoreCase("ANSWERED")) {
                        flags.add(Flags.Flag.ANSWERED);
                        continue;
                    }
                    if (name.equalsIgnoreCase("DRAFT")) {
                        flags.add(Flags.Flag.DRAFT);
                        continue;
                    }
                    if (name.equalsIgnoreCase("FLAGGED")) {
                        flags.add(Flags.Flag.FLAGGED);
                        continue;
                    }
                    flags.add("\\" + name);
                    continue;
                }
                throw new MessagingException("Invalid Flag: " + token.getValue());
            }
            throw new MessagingException("Invalid Flag: " + token.getValue());
        }
        this.checkRightParen();
        return flags;
    }

    public List readSystemNameList() throws MessagingException {
        ArrayList<String> flags = new ArrayList<String>();
        this.checkLeftParen();
        while (this.notListEnd()) {
            Token token = this.next();
            if (token.isType(92)) {
                token = this.next();
                if (token.isType(-1)) {
                    flags.add("\\" + token.getValue());
                    continue;
                }
                throw new MessagingException("Invalid Flag: " + token.getValue());
            }
            throw new MessagingException("Invalid Flag: " + token.getValue());
        }
        this.checkRightParen();
        return flags;
    }

    static {
        IMAPResponseTokenizer.initializeDecodingTable();
        dateParser = new MailDateFormat();
        EOF = new Token(-5, null);
        NIL = new Token(-6, null);
    }

    public static class Token {
        public static final int ATOM = -1;
        public static final int QUOTEDSTRING = -2;
        public static final int LITERAL = -3;
        public static final int NUMERIC = -4;
        public static final int EOF = -5;
        public static final int NIL = -6;
        public static final int CONTINUATION = 43;
        public static final int UNTAGGED = 42;
        private int type;
        private String value;

        public Token(int type, String value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return this.type;
        }

        public String getValue() {
            return this.value;
        }

        public boolean isType(int type) {
            return this.type == type;
        }

        public int getInteger() throws MessagingException {
            if (this.value != null) {
                try {
                    return Integer.parseInt(this.value);
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            throw new ResponseFormatException("Number value expected in response; fount: " + this.value);
        }

        public long getLong() throws MessagingException {
            if (this.value != null) {
                try {
                    return Long.parseLong(this.value);
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            throw new ResponseFormatException("Number value expected in response; fount: " + this.value);
        }

        public String toString() {
            if (this.type == -6) {
                return "NIL";
            }
            if (this.type == -5) {
                return "EOF";
            }
            if (this.value == null) {
                return "";
            }
            return this.value;
        }
    }
}

