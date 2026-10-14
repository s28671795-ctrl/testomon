/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.UnsupportedEncodingException;
import org.fusesource.hawtbuf.Buffer;

public final class UTF8Buffer
extends Buffer {
    int hashCode;
    String value;

    public UTF8Buffer(Buffer other) {
        super(other);
    }

    public UTF8Buffer(byte[] data, int offset, int length) {
        super(data, offset, length);
    }

    public UTF8Buffer(byte[] data) {
        super(data);
    }

    public UTF8Buffer(String input) {
        super(UTF8Buffer.encode(input));
    }

    public String toString() {
        if (this.value == null) {
            this.value = UTF8Buffer.decode(this);
        }
        return this.value;
    }

    public int compareTo(Buffer other) {
        return this.toString().compareTo(other.toString());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != UTF8Buffer.class) {
            return false;
        }
        return this.equals((Buffer)obj);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = super.hashCode();
        }
        return this.hashCode;
    }

    public static UTF8Buffer utf8(String value) {
        if (value == null) {
            return null;
        }
        return new UTF8Buffer(value);
    }

    public static UTF8Buffer utf8(Buffer buffer) {
        if (buffer == null) {
            return null;
        }
        if (buffer.getClass() == UTF8Buffer.class) {
            return (UTF8Buffer)buffer;
        }
        return new UTF8Buffer(buffer);
    }

    public static byte[] encode(String value) {
        try {
            return value.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("A UnsupportedEncodingException was thrown for teh UTF-8 encoding. (This should never happen)");
        }
    }

    public static String decode(Buffer buffer) {
        try {
            return new String(buffer.getData(), buffer.getOffset(), buffer.getLength(), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("A UnsupportedEncodingException was thrown for teh UTF-8 encoding. (This should never happen)");
        }
    }
}

