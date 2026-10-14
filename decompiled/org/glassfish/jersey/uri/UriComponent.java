/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.uri;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

public class UriComponent {
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String[] SCHEME = new String[]{"0-9", "A-Z", "a-z", "+", "-", "."};
    private static final String[] UNRESERVED = new String[]{"0-9", "A-Z", "a-z", "-", ".", "_", "~"};
    private static final String[] SUB_DELIMS = new String[]{"!", "$", "&", "'", "(", ")", "*", "+", ",", ";", "="};
    private static final boolean[][] ENCODING_TABLES = UriComponent.initEncodingTables();
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
    private static final int[] HEX_TABLE = UriComponent.initHexTable();

    private UriComponent() {
    }

    public static void validate(String s2, Type t2) {
        UriComponent.validate(s2, t2, false);
    }

    public static void validate(String s2, Type t2, boolean template) {
        int i = UriComponent._valid(s2, t2, template);
        if (i > -1) {
            throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_INVALID_CHARACTER(s2, (Object)t2, Character.valueOf(s2.charAt(i)), i));
        }
    }

    public static boolean valid(String s2, Type t2) {
        return UriComponent.valid(s2, t2, false);
    }

    public static boolean valid(String s2, Type t2, boolean template) {
        return UriComponent._valid(s2, t2, template) == -1;
    }

    private static int _valid(String s2, Type t2, boolean template) {
        boolean[] table = ENCODING_TABLES[t2.ordinal()];
        for (int i = 0; i < s2.length(); ++i) {
            char c = s2.charAt(i);
            if ((c >= '\u0080' || c == '%' || table[c]) && c < '\u0080' || template && (c == '{' || c == '}')) continue;
            return i;
        }
        return -1;
    }

    public static String contextualEncode(String s2, Type t2) {
        return UriComponent._encode(s2, t2, false, true);
    }

    public static String contextualEncode(String s2, Type t2, boolean template) {
        return UriComponent._encode(s2, t2, template, true);
    }

    public static String encode(String s2, Type t2) {
        return UriComponent._encode(s2, t2, false, false);
    }

    public static String encode(String s2, Type t2, boolean template) {
        return UriComponent._encode(s2, t2, template, false);
    }

    public static String encodeTemplateNames(String s2) {
        int i = s2.indexOf(123);
        if (i != -1) {
            s2 = s2.replace("{", "%7B");
        }
        if ((i = s2.indexOf(125)) != -1) {
            s2 = s2.replace("}", "%7D");
        }
        return s2;
    }

    private static String _encode(String s2, Type t2, boolean template, boolean contextualEncode) {
        int codePoint;
        boolean[] table = ENCODING_TABLES[t2.ordinal()];
        boolean insideTemplateParam = false;
        StringBuilder sb = null;
        for (int offset = 0; offset < s2.length(); offset += Character.charCount(codePoint)) {
            codePoint = s2.codePointAt(offset);
            if (codePoint < 128 && table[codePoint]) {
                if (sb == null) continue;
                sb.append((char)codePoint);
                continue;
            }
            if (template) {
                boolean leavingTemplateParam = false;
                if (codePoint == 123) {
                    insideTemplateParam = true;
                } else if (codePoint == 125) {
                    insideTemplateParam = false;
                    leavingTemplateParam = true;
                }
                if (insideTemplateParam || leavingTemplateParam) {
                    if (sb == null) continue;
                    sb.append(Character.toChars(codePoint));
                    continue;
                }
            }
            if (contextualEncode && codePoint == 37 && offset + 2 < s2.length() && UriComponent.isHexCharacter(s2.charAt(offset + 1)) && UriComponent.isHexCharacter(s2.charAt(offset + 2))) {
                if (sb != null) {
                    sb.append('%').append(s2.charAt(offset + 1)).append(s2.charAt(offset + 2));
                }
                offset += 2;
                continue;
            }
            if (sb == null) {
                sb = new StringBuilder();
                sb.append(s2.substring(0, offset));
            }
            if (codePoint < 128) {
                if (codePoint == 32 && t2 == Type.QUERY_PARAM) {
                    sb.append('+');
                    continue;
                }
                UriComponent.appendPercentEncodedOctet(sb, (char)codePoint);
                continue;
            }
            UriComponent.appendUTF8EncodedCharacter(sb, codePoint);
        }
        return sb == null ? s2 : sb.toString();
    }

    private static void appendPercentEncodedOctet(StringBuilder sb, int b) {
        sb.append('%');
        sb.append(HEX_DIGITS[b >> 4]);
        sb.append(HEX_DIGITS[b & 0xF]);
    }

    private static void appendUTF8EncodedCharacter(StringBuilder sb, int codePoint) {
        CharBuffer chars = CharBuffer.wrap(Character.toChars(codePoint));
        ByteBuffer bytes = UTF_8_CHARSET.encode(chars);
        while (bytes.hasRemaining()) {
            UriComponent.appendPercentEncodedOctet(sb, bytes.get() & 0xFF);
        }
    }

    private static boolean[][] initEncodingTables() {
        boolean[][] tables = new boolean[Type.values().length][];
        ArrayList<String> l = new ArrayList<String>();
        l.addAll(Arrays.asList(SCHEME));
        tables[Type.SCHEME.ordinal()] = UriComponent.initEncodingTable(l);
        l.clear();
        l.addAll(Arrays.asList(UNRESERVED));
        tables[Type.UNRESERVED.ordinal()] = UriComponent.initEncodingTable(l);
        l.addAll(Arrays.asList(SUB_DELIMS));
        tables[Type.HOST.ordinal()] = UriComponent.initEncodingTable(l);
        tables[Type.PORT.ordinal()] = UriComponent.initEncodingTable(Arrays.asList("0-9"));
        l.add(":");
        tables[Type.USER_INFO.ordinal()] = UriComponent.initEncodingTable(l);
        l.add("@");
        tables[Type.AUTHORITY.ordinal()] = UriComponent.initEncodingTable(l);
        tables[Type.PATH_SEGMENT.ordinal()] = UriComponent.initEncodingTable(l);
        tables[Type.PATH_SEGMENT.ordinal()][59] = false;
        tables[Type.MATRIX_PARAM.ordinal()] = (boolean[])tables[Type.PATH_SEGMENT.ordinal()].clone();
        tables[Type.MATRIX_PARAM.ordinal()][61] = false;
        l.add("/");
        tables[Type.PATH.ordinal()] = UriComponent.initEncodingTable(l);
        tables[Type.QUERY.ordinal()] = UriComponent.initEncodingTable(l);
        tables[Type.QUERY.ordinal()][33] = false;
        tables[Type.QUERY.ordinal()][42] = false;
        tables[Type.QUERY.ordinal()][39] = false;
        tables[Type.QUERY.ordinal()][40] = false;
        tables[Type.QUERY.ordinal()][41] = false;
        tables[Type.QUERY.ordinal()][59] = false;
        tables[Type.QUERY.ordinal()][58] = false;
        tables[Type.QUERY.ordinal()][64] = false;
        tables[Type.QUERY.ordinal()][36] = false;
        tables[Type.QUERY.ordinal()][44] = false;
        tables[Type.QUERY.ordinal()][47] = false;
        tables[Type.QUERY.ordinal()][63] = false;
        tables[Type.QUERY_PARAM.ordinal()] = Arrays.copyOf(tables[Type.QUERY.ordinal()], tables[Type.QUERY.ordinal()].length);
        tables[Type.QUERY_PARAM.ordinal()][61] = false;
        tables[Type.QUERY_PARAM.ordinal()][43] = false;
        tables[Type.QUERY_PARAM.ordinal()][38] = false;
        tables[Type.QUERY_PARAM_SPACE_ENCODED.ordinal()] = tables[Type.QUERY_PARAM.ordinal()];
        tables[Type.FRAGMENT.ordinal()] = tables[Type.QUERY.ordinal()];
        return tables;
    }

    private static boolean[] initEncodingTable(List<String> allowed) {
        boolean[] table = new boolean[128];
        for (String range : allowed) {
            if (range.length() == 1) {
                table[range.charAt((int)0)] = true;
                continue;
            }
            if (range.length() != 3 || range.charAt(1) != '-') continue;
            for (int i = range.charAt(0); i <= range.charAt(2); ++i) {
                table[i] = true;
            }
        }
        return table;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String decode(String s2, Type t2) {
        if (s2 == null) {
            throw new IllegalArgumentException();
        }
        int n = s2.length();
        if (n == 0) {
            return s2;
        }
        if (s2.indexOf(37) < 0) {
            if (t2 != Type.QUERY_PARAM) return s2;
            if (s2.indexOf(43) < 0) {
                return s2;
            }
        } else {
            if (n < 2) {
                throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(1));
            }
            if (s2.charAt(n - 2) == '%') {
                throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(n - 2));
            }
        }
        if (t2 == null) {
            return UriComponent.decode(s2, n);
        }
        switch (t2) {
            case HOST: {
                return UriComponent.decodeHost(s2, n);
            }
            case QUERY_PARAM: {
                return UriComponent.decodeQueryParam(s2, n);
            }
        }
        return UriComponent.decode(s2, n);
    }

    public static MultivaluedMap<String, String> decodeQuery(URI u, boolean decode) {
        return UriComponent.decodeQuery(u.getRawQuery(), decode);
    }

    public static MultivaluedMap<String, String> decodeQuery(String q, boolean decode) {
        return UriComponent.decodeQuery(q, true, decode);
    }

    public static MultivaluedMap<String, String> decodeQuery(String q, boolean decodeNames, boolean decodeValues) {
        int e;
        MultivaluedStringMap queryParameters = new MultivaluedStringMap();
        if (q == null || q.length() == 0) {
            return queryParameters;
        }
        int s2 = 0;
        do {
            if ((e = q.indexOf(38, s2)) == -1) {
                UriComponent.decodeQueryParam(queryParameters, q.substring(s2), decodeNames, decodeValues);
                continue;
            }
            if (e <= s2) continue;
            UriComponent.decodeQueryParam(queryParameters, q.substring(s2, e), decodeNames, decodeValues);
        } while ((s2 = e + 1) > 0 && s2 < q.length());
        return queryParameters;
    }

    private static void decodeQueryParam(MultivaluedMap<String, String> params, String param2, boolean decodeNames, boolean decodeValues) {
        try {
            int equals = param2.indexOf(61);
            if (equals > 0) {
                params.add(decodeNames ? URLDecoder.decode(param2.substring(0, equals), "UTF-8") : param2.substring(0, equals), decodeValues ? URLDecoder.decode(param2.substring(equals + 1), "UTF-8") : param2.substring(equals + 1));
            } else if (equals != 0 && param2.length() > 0) {
                params.add(decodeNames ? URLDecoder.decode(param2, "UTF-8") : param2, "");
            }
        }
        catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static List<PathSegment> decodePath(URI u, boolean decode) {
        String rawPath = u.getRawPath();
        if (rawPath != null && rawPath.length() > 0 && rawPath.charAt(0) == '/') {
            rawPath = rawPath.substring(1);
        }
        return UriComponent.decodePath(rawPath, decode);
    }

    public static List<PathSegment> decodePath(String path, boolean decode) {
        int s2;
        LinkedList<PathSegment> segments = new LinkedList<PathSegment>();
        if (path == null) {
            return segments;
        }
        int e = -1;
        do {
            if ((e = path.indexOf(47, s2 = e + 1)) > s2) {
                UriComponent.decodePathSegment(segments, path.substring(s2, e), decode);
                continue;
            }
            if (e != s2) continue;
            segments.add(PathSegmentImpl.EMPTY_PATH_SEGMENT);
        } while (e != -1);
        if (s2 < path.length()) {
            UriComponent.decodePathSegment(segments, path.substring(s2), decode);
        } else {
            segments.add(PathSegmentImpl.EMPTY_PATH_SEGMENT);
        }
        return segments;
    }

    public static void decodePathSegment(List<PathSegment> segments, String segment, boolean decode) {
        int colon2 = segment.indexOf(59);
        if (colon2 != -1) {
            segments.add(new PathSegmentImpl(colon2 == 0 ? "" : segment.substring(0, colon2), decode, UriComponent.decodeMatrix(segment, decode)));
        } else {
            segments.add(new PathSegmentImpl(segment, decode));
        }
    }

    public static MultivaluedMap<String, String> decodeMatrix(String pathSegment, boolean decode) {
        int e;
        MultivaluedStringMap matrixMap = new MultivaluedStringMap();
        int s2 = pathSegment.indexOf(59) + 1;
        if (s2 == 0 || s2 == pathSegment.length()) {
            return matrixMap;
        }
        do {
            if ((e = pathSegment.indexOf(59, s2)) == -1) {
                UriComponent.decodeMatrixParam(matrixMap, pathSegment.substring(s2), decode);
                continue;
            }
            if (e <= s2) continue;
            UriComponent.decodeMatrixParam(matrixMap, pathSegment.substring(s2, e), decode);
        } while ((s2 = e + 1) > 0 && s2 < pathSegment.length());
        return matrixMap;
    }

    private static void decodeMatrixParam(MultivaluedMap<String, String> params, String param2, boolean decode) {
        int equals = param2.indexOf(61);
        if (equals > 0) {
            params.add(UriComponent.decode(param2.substring(0, equals), Type.MATRIX_PARAM), decode ? UriComponent.decode(param2.substring(equals + 1), Type.MATRIX_PARAM) : param2.substring(equals + 1));
        } else if (equals != 0 && param2.length() > 0) {
            params.add(UriComponent.decode(param2, Type.MATRIX_PARAM), "");
        }
    }

    private static String decode(String s2, int n) {
        StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;
        int i = 0;
        while (i < n) {
            char c;
            if ((c = s2.charAt(i++)) != '%') {
                sb.append(c);
                continue;
            }
            bb = UriComponent.decodePercentEncodedOctets(s2, i, bb);
            i = UriComponent.decodeOctets(i, bb, sb);
        }
        return sb.toString();
    }

    private static String decodeQueryParam(String s2, int n) {
        StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;
        int i = 0;
        while (i < n) {
            char c;
            if ((c = s2.charAt(i++)) != '%') {
                if (c != '+') {
                    sb.append(c);
                    continue;
                }
                sb.append(' ');
                continue;
            }
            bb = UriComponent.decodePercentEncodedOctets(s2, i, bb);
            i = UriComponent.decodeOctets(i, bb, sb);
        }
        return sb.toString();
    }

    private static String decodeHost(String s2, int n) {
        StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;
        boolean betweenBrackets = false;
        int i = 0;
        while (i < n) {
            char c;
            if ((c = s2.charAt(i++)) == '[') {
                betweenBrackets = true;
            } else if (betweenBrackets && c == ']') {
                betweenBrackets = false;
            }
            if (c != '%' || betweenBrackets) {
                sb.append(c);
                continue;
            }
            bb = UriComponent.decodePercentEncodedOctets(s2, i, bb);
            i = UriComponent.decodeOctets(i, bb, sb);
        }
        return sb.toString();
    }

    private static ByteBuffer decodePercentEncodedOctets(String s2, int i, ByteBuffer bb) {
        if (bb == null) {
            bb = ByteBuffer.allocate(1);
        } else {
            ((Buffer)bb).clear();
        }
        while (true) {
            bb.put((byte)(UriComponent.decodeHex(s2, i++) << 4 | UriComponent.decodeHex(s2, i++)));
            if (i == s2.length() || s2.charAt(i++) != '%') break;
            if (bb.position() != bb.capacity()) continue;
            ((Buffer)bb).flip();
            ByteBuffer bb_new = ByteBuffer.allocate(s2.length() / 3);
            bb_new.put(bb);
            bb = bb_new;
        }
        ((Buffer)bb).flip();
        return bb;
    }

    private static int decodeOctets(int i, ByteBuffer bb, StringBuilder sb) {
        if (bb.limit() == 1 && (bb.get(0) & 0xFF) < 128) {
            sb.append((char)bb.get(0));
            return i + 2;
        }
        CharBuffer cb = UTF_8_CHARSET.decode(bb);
        sb.append(cb.toString());
        return i + bb.limit() * 3 - 1;
    }

    private static int decodeHex(String s2, int i) {
        int v = UriComponent.decodeHex(s2.charAt(i));
        if (v == -1) {
            throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(i, Character.valueOf(s2.charAt(i))));
        }
        return v;
    }

    private static int[] initHexTable() {
        int c;
        int[] table = new int[128];
        Arrays.fill(table, -1);
        for (c = 48; c <= 57; c = (int)((char)(c + 1))) {
            table[c] = c - 48;
        }
        for (c = 65; c <= 70; c = (int)((char)(c + 1))) {
            table[c] = c - 65 + 10;
        }
        for (c = 97; c <= 102; c = (int)((char)(c + 1))) {
            table[c] = c - 97 + 10;
        }
        return table;
    }

    private static int decodeHex(char c) {
        return c < '\u0080' ? HEX_TABLE[c] : -1;
    }

    public static boolean isHexCharacter(char c) {
        return c < '\u0080' && HEX_TABLE[c] != -1;
    }

    public static String fullRelativeUri(URI uri) {
        if (uri == null) {
            return null;
        }
        String query = uri.getRawQuery();
        return uri.getRawPath() + (query != null && query.length() > 0 ? "?" + query : "");
    }

    private static final class PathSegmentImpl
    implements PathSegment {
        private static final PathSegment EMPTY_PATH_SEGMENT = new PathSegmentImpl("", false);
        private final String path;
        private final MultivaluedMap<String, String> matrixParameters;

        PathSegmentImpl(String path, boolean decode) {
            this(path, decode, new MultivaluedStringMap());
        }

        PathSegmentImpl(String path, boolean decode, MultivaluedMap<String, String> matrixParameters) {
            this.path = decode ? UriComponent.decode(path, Type.PATH_SEGMENT) : path;
            this.matrixParameters = matrixParameters;
        }

        @Override
        public String getPath() {
            return this.path;
        }

        @Override
        public MultivaluedMap<String, String> getMatrixParameters() {
            return this.matrixParameters;
        }

        public String toString() {
            return this.path;
        }
    }

    public static enum Type {
        UNRESERVED,
        SCHEME,
        AUTHORITY,
        USER_INFO,
        HOST,
        PORT,
        PATH,
        PATH_SEGMENT,
        MATRIX_PARAM,
        QUERY,
        QUERY_PARAM,
        QUERY_PARAM_SPACE_ENCODED,
        FRAGMENT;

    }
}

