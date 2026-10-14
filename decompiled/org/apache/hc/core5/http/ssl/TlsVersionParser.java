/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.http.ssl;

import java.util.BitSet;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.util.Tokenizer;

final class TlsVersionParser {
    public static final TlsVersionParser INSTANCE = new TlsVersionParser();
    private final Tokenizer tokenizer = Tokenizer.INSTANCE;

    TlsVersionParser() {
    }

    ProtocolVersion parse(CharSequence buffer, Tokenizer.Cursor cursor, BitSet delimiters) throws ParseException {
        int minor;
        int major;
        int lowerBound = cursor.getLowerBound();
        int upperBound = cursor.getUpperBound();
        int pos = cursor.getPos();
        if (pos + 4 > cursor.getUpperBound()) {
            throw new ParseException("Invalid TLS protocol version", buffer, lowerBound, upperBound, pos);
        }
        if (buffer.charAt(pos) != 'T' || buffer.charAt(pos + 1) != 'L' || buffer.charAt(pos + 2) != 'S' || buffer.charAt(pos + 3) != 'v') {
            throw new ParseException("Invalid TLS protocol version", buffer, lowerBound, upperBound, pos);
        }
        cursor.updatePos(pos += 4);
        if (cursor.atEnd()) {
            throw new ParseException("Invalid TLS version", buffer, lowerBound, upperBound, pos);
        }
        String s2 = this.tokenizer.parseToken(buffer, cursor, delimiters);
        int idx = s2.indexOf(46);
        if (idx == -1) {
            int major2;
            try {
                major2 = Integer.parseInt(s2);
            }
            catch (NumberFormatException e) {
                throw new ParseException("Invalid TLS major version", buffer, lowerBound, upperBound, pos);
            }
            return new ProtocolVersion("TLS", major2, 0);
        }
        String s1 = s2.substring(0, idx);
        try {
            major = Integer.parseInt(s1);
        }
        catch (NumberFormatException e) {
            throw new ParseException("Invalid TLS major version", buffer, lowerBound, upperBound, pos);
        }
        String s22 = s2.substring(idx + 1);
        try {
            minor = Integer.parseInt(s22);
        }
        catch (NumberFormatException e) {
            throw new ParseException("Invalid TLS minor version", buffer, lowerBound, upperBound, pos);
        }
        return new ProtocolVersion("TLS", major, minor);
    }

    ProtocolVersion parse(String s2) throws ParseException {
        if (s2 == null) {
            return null;
        }
        Tokenizer.Cursor cursor = new Tokenizer.Cursor(0, s2.length());
        return this.parse(s2, cursor, null);
    }
}

