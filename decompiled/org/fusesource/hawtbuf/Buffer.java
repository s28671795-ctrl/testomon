/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.hawtbuf.AsciiBuffer;
import org.fusesource.hawtbuf.BufferEditor;
import org.fusesource.hawtbuf.BufferInputStream;
import org.fusesource.hawtbuf.BufferOutputStream;
import org.fusesource.hawtbuf.HexSupport;
import org.fusesource.hawtbuf.UTF8Buffer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Buffer
implements Comparable<Buffer> {
    public byte[] data;
    public int offset;
    public int length;

    public Buffer(ByteBuffer other) {
        this(other.array(), other.arrayOffset() + other.position(), other.remaining());
    }

    public Buffer(Buffer other) {
        this(other.data, other.offset, other.length);
    }

    public Buffer(int size) {
        this(new byte[size]);
    }

    public Buffer(byte[] data) {
        this(data, 0, data.length);
    }

    public Buffer(byte[] data, int offset, int length) {
        assert (data != null) : "data cannot be null";
        assert (offset + length <= data.length) : String.format("offset %d + length %d must be <= the data.length %d", offset, length, data.length);
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    public String hex() {
        return HexSupport.toHexFromBuffer(this);
    }

    public final Buffer flip() {
        this.length = this.offset;
        this.offset = 0;
        return this;
    }

    public final Buffer moveHead(int value) {
        assert (value <= this.length) : "Head position cannot be advanced past the tail";
        int newOffset = this.offset + value;
        assert (newOffset >= 0) : "Head position cannot be moved back past the start of the buffer";
        this.offset = newOffset;
        this.length -= value;
        return this;
    }

    public final Buffer moveTail(int value) {
        int newLength = this.length + value;
        assert (this.offset + newLength <= this.data.length) : "Tail position cannot be advanced past the end of the buffer";
        assert (newLength >= 0) : "Tail position cannot be moved back past head of the buffer";
        this.length = newLength;
        return this;
    }

    public final Buffer clear() {
        this.length = this.data.length;
        this.offset = 0;
        return this;
    }

    public final Buffer slice(int low, int high) {
        int sz = high < 0 ? this.length + high : high - low;
        if (sz < 0) {
            sz = 0;
        }
        return new Buffer(this.data, this.offset + low, sz);
    }

    public final byte[] getData() {
        return this.data;
    }

    public final Buffer data(byte[] data) {
        this.data = data;
        return this;
    }

    public final int getLength() {
        return this.length;
    }

    public final int length() {
        return this.length;
    }

    public final Buffer length(int length) {
        this.length = length;
        return this;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final Buffer offset(int offset) {
        this.offset = offset;
        return this;
    }

    public final Buffer deepCopy() {
        byte[] t2 = new byte[this.length];
        System.arraycopy(this.data, this.offset, t2, 0, this.length);
        return new Buffer(t2);
    }

    public final Buffer compact() {
        if (this.length != this.data.length) {
            return new Buffer(this.toByteArray());
        }
        return this;
    }

    public final byte[] toByteArray() {
        int length = this.length;
        byte[] data = this.data;
        if (length != data.length) {
            byte[] t2 = new byte[length];
            System.arraycopy(data, this.offset, t2, 0, length);
            data = t2;
        }
        return data;
    }

    public final byte get(int i) {
        return this.data[this.offset + i];
    }

    public final boolean equals(Buffer obj) {
        byte[] data = this.data;
        int offset = this.offset;
        int length = this.length;
        if (length != obj.length) {
            return false;
        }
        byte[] objData = obj.data;
        int objOffset = obj.offset;
        for (int i = 0; i < length; ++i) {
            if (objData[objOffset + i] == data[offset + i]) continue;
            return false;
        }
        return true;
    }

    public final BufferInputStream in() {
        return new BufferInputStream(this);
    }

    public final BufferOutputStream out() {
        return new BufferOutputStream(this);
    }

    public final BufferEditor bigEndianEditor() {
        return new BufferEditor.BigEndianBufferEditor(this);
    }

    public final BufferEditor littleEndianEditor() {
        return new BufferEditor.LittleEndianBufferEditor(this);
    }

    public final boolean isEmpty() {
        return this.length == 0;
    }

    public final boolean contains(byte value) {
        return this.indexOf(value, 0) >= 0;
    }

    public final int indexOf(byte value) {
        return this.indexOf(value, 0);
    }

    public final int indexOf(byte value, int pos) {
        byte[] data = this.data;
        int offset = this.offset;
        int length = this.length;
        for (int i = pos; i < length; ++i) {
            if (data[offset + i] != value) continue;
            return i;
        }
        return -1;
    }

    public final boolean startsWith(Buffer other) {
        return this.indexOf(other, 0) == 0;
    }

    public final int indexOf(Buffer needle) {
        return this.indexOf(needle, 0);
    }

    public final int indexOf(Buffer needle, int pos) {
        int max = this.length - needle.length;
        for (int i = pos; i <= max; ++i) {
            if (!this.matches(needle, i)) continue;
            return i;
        }
        return -1;
    }

    public final boolean containsAt(Buffer needle, int pos) {
        if (this.length - pos < needle.length) {
            return false;
        }
        return this.matches(needle, pos);
    }

    private final boolean matches(Buffer needle, int pos) {
        byte[] data = this.data;
        int offset = this.offset;
        int needleLength = needle.length;
        byte[] needleData = needle.data;
        int needleOffset = needle.offset;
        for (int i = 0; i < needleLength; ++i) {
            if (data[offset + pos + i] == needleData[needleOffset + i]) continue;
            return false;
        }
        return true;
    }

    public final Buffer trim() {
        return this.trimFront().trimEnd();
    }

    public final Buffer trimEnd() {
        int end;
        int pos;
        byte[] data = this.data;
        int offset = this.offset;
        int length = this.length;
        for (pos = end = offset + this.length - 1; offset <= pos && data[pos] <= 32; --pos) {
        }
        return pos == end ? this : new Buffer(data, offset, length - (end - pos));
    }

    public final Buffer trimFront() {
        int pos;
        byte[] data = this.data;
        int offset = this.offset;
        int end = offset + this.length;
        for (pos = offset; pos < end && data[pos] <= 32; ++pos) {
        }
        return pos == offset ? this : new Buffer(data, pos, this.length - (pos - offset));
    }

    public final Buffer buffer() {
        return new Buffer(this);
    }

    public final AsciiBuffer ascii() {
        return new AsciiBuffer(this);
    }

    public final UTF8Buffer utf8() {
        return new UTF8Buffer(this);
    }

    public final Buffer[] split(byte separator) {
        int pos;
        ArrayList<Buffer> rc = new ArrayList<Buffer>();
        byte[] data = this.data;
        int nextStart = pos = this.offset;
        int end = pos + this.length;
        while (pos < end) {
            if (data[pos] == separator) {
                if (nextStart < pos) {
                    rc.add(new Buffer(data, nextStart, pos - nextStart));
                }
                nextStart = pos + 1;
            }
            ++pos;
        }
        if (nextStart < pos) {
            rc.add(new Buffer(data, nextStart, pos - nextStart));
        }
        return rc.toArray(new Buffer[rc.size()]);
    }

    public void reset() {
        this.offset = 0;
        this.length = this.data.length;
    }

    public int hashCode() {
        byte[] data = this.data;
        int offset = this.offset;
        int length = this.length;
        byte[] target = new byte[4];
        for (int i = 0; i < length; ++i) {
            int n = i % 4;
            target[n] = (byte)(target[n] ^ data[offset + i]);
        }
        return target[0] << 24 | target[1] << 16 | target[2] << 8 | target[3];
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != Buffer.class) {
            return false;
        }
        return this.equals((Buffer)obj);
    }

    @Override
    public int compareTo(Buffer o) {
        if (this == o) {
            return 0;
        }
        byte[] data = this.data;
        int offset = this.offset;
        int length = this.length;
        int oLength = o.length;
        int oOffset = o.offset;
        byte[] oData = o.data;
        int minLength = Math.min(length, oLength);
        if (offset == oOffset) {
            int limit = minLength + offset;
            for (int pos = offset; pos < limit; ++pos) {
                int b1 = 0xFF & data[pos];
                int b2 = 0xFF & oData[pos];
                if (b1 == b2) continue;
                return b1 - b2;
            }
        } else {
            int offset1 = offset;
            int offset2 = oOffset;
            while (minLength-- != 0) {
                int b2;
                int b1;
                if ((b1 = 0xFF & data[offset1++]) == (b2 = 0xFF & oData[offset2++])) continue;
                return b1 - b2;
            }
        }
        return length - oLength;
    }

    public void writeTo(DataOutput out) throws IOException {
        out.write(this.data, this.offset, this.length);
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.data, this.offset, this.length);
    }

    public void readFrom(DataInput in) throws IOException {
        in.readFully(this.data, this.offset, this.length);
    }

    public int readFrom(InputStream in) throws IOException {
        return in.read(this.data, this.offset, this.length);
    }

    public static String string(Buffer value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static final Buffer join(List<Buffer> items, Buffer seperator) {
        if (items.isEmpty()) {
            return new Buffer(seperator.data, 0, 0);
        }
        int size = 0;
        for (Buffer item : items) {
            size += item.length;
        }
        int pos = 0;
        byte[] data = new byte[size += seperator.length * (items.size() - 1)];
        for (Buffer item : items) {
            if (pos != 0) {
                System.arraycopy(seperator.data, seperator.offset, data, pos, seperator.length);
                pos += seperator.length;
            }
            System.arraycopy(item.data, item.offset, data, pos, item.length);
            pos += item.length;
        }
        return new Buffer(data, 0, size);
    }

    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(this.data, this.offset, this.length);
    }

    public static AsciiBuffer ascii(String value) {
        return AsciiBuffer.ascii(value);
    }

    public static AsciiBuffer ascii(Buffer buffer) {
        return AsciiBuffer.ascii(buffer);
    }

    public static UTF8Buffer utf8(String value) {
        return UTF8Buffer.utf8(value);
    }

    public static UTF8Buffer utf8(Buffer buffer) {
        return UTF8Buffer.utf8(buffer);
    }

    public String toString() {
        int size = this.length;
        boolean asciiPrintable = true;
        for (int i = 0; i < size; ++i) {
            int c = this.data[this.offset + i] & 0xFF;
            if (c <= 126 && c >= 32 || c == 10 || c == 13 | c == 10 | c == 27) continue;
            asciiPrintable = false;
            break;
        }
        if (asciiPrintable) {
            return "ascii: " + this.ascii();
        }
        return "hex: " + HexSupport.toHexFromBuffer(this);
    }
}

