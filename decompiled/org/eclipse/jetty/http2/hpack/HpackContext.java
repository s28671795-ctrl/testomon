/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http2.hpack.Huffman;
import org.eclipse.jetty.http2.hpack.NBitInteger;
import org.eclipse.jetty.http2.hpack.StaticTableHttpField;
import org.eclipse.jetty.util.Index;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HpackContext {
    public static final Logger LOG = LoggerFactory.getLogger(HpackContext.class);
    private static final String EMPTY = "";
    public static final String[][] STATIC_TABLE = new String[][]{{null, null}, {":authority", ""}, {":method", "GET"}, {":method", "POST"}, {":path", "/"}, {":path", "/index.html"}, {":scheme", "http"}, {":scheme", "https"}, {":status", "200"}, {":status", "204"}, {":status", "206"}, {":status", "304"}, {":status", "400"}, {":status", "404"}, {":status", "500"}, {"accept-charset", ""}, {"accept-encoding", "gzip, deflate"}, {"accept-language", ""}, {"accept-ranges", ""}, {"accept", ""}, {"access-control-allow-origin", ""}, {"age", ""}, {"allow", ""}, {"authorization", ""}, {"cache-control", ""}, {"content-disposition", ""}, {"content-encoding", ""}, {"content-language", ""}, {"content-length", ""}, {"content-location", ""}, {"content-range", ""}, {"content-type", ""}, {"cookie", ""}, {"date", ""}, {"etag", ""}, {"expect", ""}, {"expires", ""}, {"from", ""}, {"host", ""}, {"if-match", ""}, {"if-modified-since", ""}, {"if-none-match", ""}, {"if-range", ""}, {"if-unmodified-since", ""}, {"last-modified", ""}, {"link", ""}, {"location", ""}, {"max-forwards", ""}, {"proxy-authenticate", ""}, {"proxy-authorization", ""}, {"range", ""}, {"referer", ""}, {"refresh", ""}, {"retry-after", ""}, {"server", ""}, {"set-cookie", ""}, {"strict-transport-security", ""}, {"transfer-encoding", ""}, {"user-agent", ""}, {"vary", ""}, {"via", ""}, {"www-authenticate", ""}};
    private static final Map<HttpField, Entry> __staticFieldMap = new HashMap<HttpField, Entry>();
    private static final Index<StaticEntry> __staticNameMap;
    private static final StaticEntry[] __staticTableByHeader;
    private static final StaticEntry[] __staticTable;
    public static final int STATIC_SIZE;
    private int _maxDynamicTableSizeInBytes;
    private int _dynamicTableSizeInBytes;
    private final DynamicTable _dynamicTable;
    private final Map<HttpField, Entry> _fieldMap = new HashMap<HttpField, Entry>();
    private final Map<String, Entry> _nameMap = new HashMap<String, Entry>();

    HpackContext(int maxDynamicTableSize) {
        this._maxDynamicTableSizeInBytes = maxDynamicTableSize;
        int guesstimateEntries = 10 + maxDynamicTableSize / 52;
        this._dynamicTable = new DynamicTable(guesstimateEntries);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("HdrTbl[%x] created max=%d", this.hashCode(), maxDynamicTableSize));
        }
    }

    public void resize(int newMaxDynamicTableSize) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("HdrTbl[%x] resized max=%d->%d", this.hashCode(), this._maxDynamicTableSizeInBytes, newMaxDynamicTableSize));
        }
        this._maxDynamicTableSizeInBytes = newMaxDynamicTableSize;
        this._dynamicTable.evict();
    }

    public Entry get(HttpField field2) {
        Entry entry = this._fieldMap.get(field2);
        if (entry == null) {
            entry = __staticFieldMap.get(field2);
        }
        return entry;
    }

    public Entry get(String name) {
        Entry entry = __staticNameMap.get(name);
        if (entry != null) {
            return entry;
        }
        return this._nameMap.get(StringUtil.asciiToLowerCase(name));
    }

    public Entry get(int index) {
        if (index <= STATIC_SIZE) {
            return __staticTable[index];
        }
        return this._dynamicTable.get(index);
    }

    public Entry get(HttpHeader header) {
        StaticEntry e = __staticTableByHeader[header.ordinal()];
        if (e == null) {
            return this.get(header.asString());
        }
        return e;
    }

    public static Entry getStatic(HttpHeader header) {
        return __staticTableByHeader[header.ordinal()];
    }

    public Entry add(HttpField field2) {
        Entry entry = new Entry(field2);
        int size = entry.getSize();
        if (size > this._maxDynamicTableSizeInBytes) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("HdrTbl[%x] !added size %d>%d", this.hashCode(), size, this._maxDynamicTableSizeInBytes));
            }
            this._dynamicTable.evictAll();
            return null;
        }
        this._dynamicTableSizeInBytes += size;
        this._dynamicTable.add(entry);
        this._fieldMap.put(field2, entry);
        this._nameMap.put(field2.getLowerCaseName(), entry);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("HdrTbl[%x] added %s", this.hashCode(), entry));
        }
        this._dynamicTable.evict();
        return entry;
    }

    public int size() {
        return this._dynamicTable.size();
    }

    public int getDynamicTableSize() {
        return this._dynamicTableSizeInBytes;
    }

    public int getMaxDynamicTableSize() {
        return this._maxDynamicTableSizeInBytes;
    }

    public int index(Entry entry) {
        if (entry._slot < 0) {
            return 0;
        }
        if (entry.isStatic()) {
            return entry._slot;
        }
        return this._dynamicTable.index(entry);
    }

    public static int staticIndex(HttpHeader header) {
        if (header == null) {
            return 0;
        }
        Entry entry = __staticNameMap.get(header.asString());
        if (entry == null) {
            return 0;
        }
        return entry._slot;
    }

    public String toString() {
        return String.format("HpackContext@%x{entries=%d,size=%d,max=%d}", this.hashCode(), this._dynamicTable.size(), this._dynamicTableSizeInBytes, this._maxDynamicTableSizeInBytes);
    }

    static {
        __staticTableByHeader = new StaticEntry[HttpHeader.values().length];
        __staticTable = new StaticEntry[STATIC_TABLE.length];
        STATIC_SIZE = STATIC_TABLE.length - 1;
        Index.Builder<StaticEntry> staticNameMapBuilder = new Index.Builder().caseSensitive(false);
        HashSet<String> added = new HashSet<String>();
        for (int i = 1; i < STATIC_TABLE.length; ++i) {
            StaticEntry entry = null;
            String name = STATIC_TABLE[i][0];
            String value = STATIC_TABLE[i][1];
            HttpHeader header = HttpHeader.CACHE.get(name);
            if (header != null && value != null) {
                switch (header) {
                    case C_METHOD: {
                        HttpMethod method2 = HttpMethod.CACHE.get(value);
                        if (method2 == null) break;
                        entry = new StaticEntry(i, new StaticTableHttpField(header, name, value, (Object)method2));
                        break;
                    }
                    case C_SCHEME: {
                        HttpScheme scheme2 = HttpScheme.CACHE.get(value);
                        if (scheme2 == null) break;
                        entry = new StaticEntry(i, new StaticTableHttpField(header, name, value, (Object)scheme2));
                        break;
                    }
                    case C_STATUS: {
                        entry = new StaticEntry(i, new StaticTableHttpField(header, name, value, value));
                        break;
                    }
                }
            }
            if (entry == null) {
                entry = new StaticEntry(i, header == null ? new HttpField(STATIC_TABLE[i][0], value) : new HttpField(header, name, value));
            }
            HpackContext.__staticTable[i] = entry;
            if (entry._field.getValue() != null) {
                __staticFieldMap.put(entry._field, entry);
            }
            if (added.contains(entry._field.getName())) continue;
            added.add(entry._field.getName());
            staticNameMapBuilder.with(entry._field.getName(), entry);
        }
        __staticNameMap = staticNameMapBuilder.build();
        for (HttpHeader h2 : HttpHeader.values()) {
            StaticEntry entry = __staticNameMap.get(h2.asString());
            if (entry == null) continue;
            HpackContext.__staticTableByHeader[h2.ordinal()] = entry;
        }
    }

    private class DynamicTable {
        Entry[] _entries;
        int _size;
        int _offset;
        int _growby;

        private DynamicTable(int initCapacity) {
            this._entries = new Entry[initCapacity];
            this._growby = initCapacity;
        }

        public void add(Entry entry) {
            if (this._size == this._entries.length) {
                Entry[] entries = new Entry[this._entries.length + this._growby];
                for (int i = 0; i < this._size; ++i) {
                    int slot = (this._offset + i) % this._entries.length;
                    entries[i] = this._entries[slot];
                    entries[i]._slot = i;
                }
                this._entries = entries;
                this._offset = 0;
            }
            int slot = (this._size++ + this._offset) % this._entries.length;
            this._entries[slot] = entry;
            entry._slot = slot;
        }

        public int index(Entry entry) {
            return STATIC_SIZE + this._size - (entry._slot - this._offset + this._entries.length) % this._entries.length;
        }

        public Entry get(int index) {
            int d = index - STATIC_SIZE - 1;
            if (d < 0 || d >= this._size) {
                return null;
            }
            int slot = (this._offset + this._size - d - 1) % this._entries.length;
            return this._entries[slot];
        }

        public int size() {
            return this._size;
        }

        private void evict() {
            while (HpackContext.this._dynamicTableSizeInBytes > HpackContext.this._maxDynamicTableSizeInBytes) {
                Entry entry = this._entries[this._offset];
                this._entries[this._offset] = null;
                this._offset = (this._offset + 1) % this._entries.length;
                --this._size;
                if (LOG.isDebugEnabled()) {
                    LOG.debug(String.format("HdrTbl[%x] evict %s", HpackContext.this.hashCode(), entry));
                }
                HpackContext.this._dynamicTableSizeInBytes -= entry.getSize();
                entry._slot = -1;
                HpackContext.this._fieldMap.remove(entry.getHttpField());
                String lc = entry.getHttpField().getLowerCaseName();
                if (entry != HpackContext.this._nameMap.get(lc)) continue;
                HpackContext.this._nameMap.remove(lc);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("HdrTbl[%x] entries=%d, size=%d, max=%d", HpackContext.this.hashCode(), HpackContext.this._dynamicTable.size(), HpackContext.this._dynamicTableSizeInBytes, HpackContext.this._maxDynamicTableSizeInBytes));
            }
        }

        private void evictAll() {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("HdrTbl[%x] evictAll", HpackContext.this.hashCode()));
            }
            if (this.size() > 0) {
                HpackContext.this._fieldMap.clear();
                HpackContext.this._nameMap.clear();
                this._offset = 0;
                this._size = 0;
                HpackContext.this._dynamicTableSizeInBytes = 0;
                Arrays.fill(this._entries, null);
            }
        }
    }

    public static class Entry {
        final HttpField _field;
        int _slot;

        Entry() {
            this._slot = -1;
            this._field = null;
        }

        Entry(HttpField field2) {
            this._field = field2;
        }

        public int getSize() {
            String value = this._field.getValue();
            return 32 + this._field.getName().length() + (value == null ? 0 : value.length());
        }

        public HttpField getHttpField() {
            return this._field;
        }

        public boolean isStatic() {
            return false;
        }

        public byte[] getStaticHuffmanValue() {
            return null;
        }

        public String toString() {
            return String.format("{%s,%d,%s,%x}", this.isStatic() ? "S" : "D", this._slot, this._field, this.hashCode());
        }
    }

    public static class StaticEntry
    extends Entry {
        private final byte[] _huffmanValue;
        private final byte _encodedField;

        StaticEntry(int index, HttpField field2) {
            super(field2);
            this._slot = index;
            String value = field2.getValue();
            if (value != null && value.length() > 0) {
                int huffmanLen = Huffman.octetsNeeded(value);
                if (huffmanLen < 0) {
                    throw new IllegalStateException("bad value");
                }
                int lenLen = NBitInteger.octectsNeeded(7, huffmanLen);
                this._huffmanValue = new byte[1 + lenLen + huffmanLen];
                ByteBuffer buffer = ByteBuffer.wrap(this._huffmanValue);
                buffer.put((byte)-128);
                NBitInteger.encode(buffer, 7, huffmanLen);
                Huffman.encode(buffer, value);
            } else {
                this._huffmanValue = null;
            }
            this._encodedField = (byte)(0x80 | index);
        }

        @Override
        public boolean isStatic() {
            return true;
        }

        @Override
        public byte[] getStaticHuffmanValue() {
            return this._huffmanValue;
        }

        public byte getEncodedField() {
            return this._encodedField;
        }
    }
}

