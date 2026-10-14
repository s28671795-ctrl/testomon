/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest.fromhttpclient;

import com.burgstaller.okhttp.digest.fromhttpclient.Args;
import com.burgstaller.okhttp.digest.fromhttpclient.BasicHeaderElement;
import com.burgstaller.okhttp.digest.fromhttpclient.BasicNameValuePair;
import com.burgstaller.okhttp.digest.fromhttpclient.CharArrayBuffer;
import com.burgstaller.okhttp.digest.fromhttpclient.HTTP;
import com.burgstaller.okhttp.digest.fromhttpclient.HeaderElement;
import com.burgstaller.okhttp.digest.fromhttpclient.HeaderValueParser;
import com.burgstaller.okhttp.digest.fromhttpclient.NameValuePair;
import com.burgstaller.okhttp.digest.fromhttpclient.ParseException;
import com.burgstaller.okhttp.digest.fromhttpclient.ParserCursor;
import java.util.ArrayList;

public class BasicHeaderValueParser {
    @Deprecated
    public static final BasicHeaderValueParser DEFAULT = new BasicHeaderValueParser();
    public static final BasicHeaderValueParser INSTANCE = new BasicHeaderValueParser();
    private static final char PARAM_DELIMITER = ';';
    private static final char ELEM_DELIMITER = ',';
    private static final char[] ALL_DELIMITERS = new char[]{';', ','};

    public static HeaderElement[] parseElements(String value, HeaderValueParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        return ((HeaderValueParser)(parser != null ? parser : INSTANCE)).parseElements(buffer, cursor);
    }

    public HeaderElement[] parseElements(CharArrayBuffer buffer, ParserCursor cursor) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        ArrayList<HeaderElement> elements = new ArrayList<HeaderElement>();
        while (!cursor.atEnd()) {
            HeaderElement element = this.parseHeaderElement(buffer, cursor);
            if (element.getName().length() == 0 && element.getValue() == null) continue;
            elements.add(element);
        }
        return elements.toArray(new HeaderElement[elements.size()]);
    }

    public static HeaderElement parseHeaderElement(String value, HeaderValueParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        return ((HeaderValueParser)(parser != null ? parser : INSTANCE)).parseHeaderElement(buffer, cursor);
    }

    public HeaderElement parseHeaderElement(CharArrayBuffer buffer, ParserCursor cursor) {
        char ch;
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        NameValuePair nvp = this.parseNameValuePair(buffer, cursor);
        NameValuePair[] params = null;
        if (!cursor.atEnd() && (ch = buffer.charAt(cursor.getPos() - 1)) != ',') {
            params = this.parseParameters(buffer, cursor);
        }
        return this.createHeaderElement(nvp.getName(), nvp.getValue(), params);
    }

    protected HeaderElement createHeaderElement(String name, String value, NameValuePair[] params) {
        return new BasicHeaderElement(name, value, params);
    }

    public static NameValuePair[] parseParameters(String value, HeaderValueParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        return ((HeaderValueParser)(parser != null ? parser : INSTANCE)).parseParameters(buffer, cursor);
    }

    public NameValuePair[] parseParameters(CharArrayBuffer buffer, ParserCursor cursor) {
        char params;
        int pos;
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        int indexTo = cursor.getUpperBound();
        for (pos = cursor.getPos(); pos < indexTo && HTTP.isWhitespace(params = buffer.charAt(pos)); ++pos) {
        }
        cursor.updatePos(pos);
        if (cursor.atEnd()) {
            return new NameValuePair[0];
        }
        ArrayList<NameValuePair> var8 = new ArrayList<NameValuePair>();
        while (!cursor.atEnd()) {
            NameValuePair param2 = this.parseNameValuePair(buffer, cursor);
            var8.add(param2);
            char ch = buffer.charAt(cursor.getPos() - 1);
            if (ch != ',') continue;
            break;
        }
        return var8.toArray(new NameValuePair[var8.size()]);
    }

    public static NameValuePair parseNameValuePair(String value, HeaderValueParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        return ((HeaderValueParser)(parser != null ? parser : INSTANCE)).parseNameValuePair(buffer, cursor);
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor) {
        return this.parseNameValuePair(buffer, cursor, ALL_DELIMITERS);
    }

    private static boolean isOneOf(char ch, char[] chs) {
        if (chs != null) {
            char[] arr$ = chs;
            int len$ = chs.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                char ch2 = arr$[i$];
                if (ch != ch2) continue;
                return true;
            }
        }
        return false;
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor, char[] delimiters) {
        String name;
        char value;
        int pos;
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        boolean terminated = false;
        int indexFrom = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        for (pos = cursor.getPos(); pos < indexTo && (value = buffer.charAt(pos)) != '='; ++pos) {
            if (!BasicHeaderValueParser.isOneOf(value, delimiters)) continue;
            terminated = true;
            break;
        }
        if (pos == indexTo) {
            terminated = true;
            name = buffer.substringTrimmed(indexFrom, indexTo);
        } else {
            name = buffer.substringTrimmed(indexFrom, pos);
            ++pos;
        }
        if (terminated) {
            cursor.updatePos(pos);
            return this.createNameValuePair(name, null);
        }
        int i1 = pos;
        boolean qouted = false;
        boolean escaped = false;
        while (pos < indexTo) {
            char i2 = buffer.charAt(pos);
            if (i2 == '\"' && !escaped) {
                boolean bl = qouted = !qouted;
            }
            if (!qouted && !escaped && BasicHeaderValueParser.isOneOf(i2, delimiters)) {
                terminated = true;
                break;
            }
            escaped = escaped ? false : qouted && i2 == '\\';
            ++pos;
        }
        int var15 = pos;
        while (i1 < var15 && HTTP.isWhitespace(buffer.charAt(i1))) {
            ++i1;
        }
        while (var15 > i1 && HTTP.isWhitespace(buffer.charAt(var15 - 1))) {
            --var15;
        }
        if (var15 - i1 >= 2 && buffer.charAt(i1) == '\"' && buffer.charAt(var15 - 1) == '\"') {
            ++i1;
            --var15;
        }
        String var14 = buffer.substring(i1, var15);
        if (terminated) {
            ++pos;
        }
        cursor.updatePos(pos);
        return this.createNameValuePair(name, var14);
    }

    protected NameValuePair createNameValuePair(String name, String value) {
        return new BasicNameValuePair(name, value);
    }

    public static boolean isWhitespace(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
    }
}

