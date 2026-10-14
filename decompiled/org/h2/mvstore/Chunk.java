/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Map;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.WriteBuffer;
import org.h2.util.StringUtils;

public abstract class Chunk<C extends Chunk<C>> {
    public static final int MAX_ID = 0x3FFFFFF;
    static final int MAX_HEADER_LENGTH = 1024;
    static final int FOOTER_LENGTH = 128;
    private static final String ATTR_CHUNK = "chunk";
    private static final String ATTR_BLOCK = "block";
    private static final String ATTR_LEN = "len";
    private static final String ATTR_MAP = "map";
    private static final String ATTR_MAX = "max";
    private static final String ATTR_NEXT = "next";
    private static final String ATTR_PAGES = "pages";
    private static final String ATTR_ROOT = "root";
    private static final String ATTR_TIME = "time";
    private static final String ATTR_VERSION = "version";
    private static final String ATTR_LIVE_MAX = "liveMax";
    private static final String ATTR_LIVE_PAGES = "livePages";
    private static final String ATTR_UNUSED = "unused";
    private static final String ATTR_UNUSED_AT_VERSION = "unusedAtVersion";
    private static final String ATTR_PIN_COUNT = "pinCount";
    private static final String ATTR_TOC = "toc";
    private static final String ATTR_OCCUPANCY = "occupancy";
    private static final String ATTR_FLETCHER = "fletcher";
    public final int id;
    public volatile long block;
    public int len;
    int pageCount;
    int pageCountLive;
    int tocPos;
    BitSet occupancy;
    public long maxLen;
    public long maxLenLive;
    int collectPriority;
    long layoutRootPos;
    public long version;
    public long time;
    public long unused;
    long unusedAtVersion;
    public int mapId;
    public long next;
    private int pinCount;
    public volatile ByteBuffer buffer;

    Chunk(String string) {
        this(DataUtils.parseMap(string), true);
    }

    Chunk(Map<String, String> map, boolean bl) {
        this(DataUtils.readHexInt(map, ATTR_CHUNK, 0));
        this.block = DataUtils.readHexLong(map, ATTR_BLOCK, 0L);
        this.len = DataUtils.readHexInt(map, ATTR_LEN, 0);
        this.version = DataUtils.readHexLong(map, ATTR_VERSION, this.id);
        if (bl) {
            this.pageCount = DataUtils.readHexInt(map, ATTR_PAGES, 0);
            this.pageCountLive = DataUtils.readHexInt(map, ATTR_LIVE_PAGES, this.pageCount);
            this.mapId = DataUtils.readHexInt(map, ATTR_MAP, 0);
            this.maxLen = DataUtils.readHexLong(map, ATTR_MAX, 0L);
            this.maxLenLive = DataUtils.readHexLong(map, ATTR_LIVE_MAX, this.maxLen);
            this.layoutRootPos = DataUtils.readHexLong(map, ATTR_ROOT, 0L);
            this.time = DataUtils.readHexLong(map, ATTR_TIME, 0L);
            this.unused = DataUtils.readHexLong(map, ATTR_UNUSED, 0L);
            this.unusedAtVersion = DataUtils.readHexLong(map, ATTR_UNUSED_AT_VERSION, 0L);
            this.next = DataUtils.readHexLong(map, ATTR_NEXT, 0L);
            this.pinCount = DataUtils.readHexInt(map, ATTR_PIN_COUNT, 0);
            this.tocPos = DataUtils.readHexInt(map, ATTR_TOC, 0);
            byte[] byArray = DataUtils.parseHexBytes(map, ATTR_OCCUPANCY);
            if (byArray == null) {
                this.occupancy = new BitSet();
                assert (this.pageCountLive == this.pageCount);
            } else {
                this.occupancy = BitSet.valueOf(byArray);
                if (this.pageCount - this.pageCountLive != this.occupancy.cardinality()) {
                    throw DataUtils.newMVStoreException(6, "Inconsistent occupancy info {0} - {1} != {2} {3}", this.pageCount, this.pageCountLive, this.occupancy.cardinality(), this);
                }
            }
        }
    }

    Chunk(int n) {
        this.id = n;
        if (n <= 0) {
            throw DataUtils.newMVStoreException(6, "Invalid chunk id {0}", n);
        }
    }

    protected abstract ByteBuffer readFully(FileStore<C> var1, long var2, int var4);

    static String readChunkHeader(ByteBuffer byteBuffer) {
        int n = byteBuffer.position();
        byte[] byArray = new byte[Math.min(byteBuffer.remaining(), 1024)];
        byteBuffer.get(byArray);
        for (int i = 0; i < byArray.length; ++i) {
            if (byArray[i] != 10) continue;
            byteBuffer.position(n + i + 1);
            String string = new String(byArray, 0, i, StandardCharsets.ISO_8859_1).trim();
            return string;
        }
        return null;
    }

    int estimateHeaderSize() {
        byte[] byArray = this.getHeaderBytes();
        int n = byArray.length;
        assert (57 <= n && n <= 94) : n + " " + this.getHeader();
        return n + 104 + 1;
    }

    void writeChunkHeader(WriteBuffer writeBuffer, int n) {
        int n2 = writeBuffer.position() + n - 1;
        byte[] byArray = this.getHeaderBytes();
        writeBuffer.put(byArray);
        while (writeBuffer.position() < n2) {
            writeBuffer.put((byte)32);
        }
        if (n != 0 && writeBuffer.position() > n2) {
            throw DataUtils.newMVStoreException(3, "Chunk metadata too long {0} {1} {2}", n2, writeBuffer.position(), this.getHeader());
        }
        writeBuffer.put((byte)10);
    }

    static String getMetaKey(int n) {
        return "chunk." + Integer.toHexString(n);
    }

    int getFillRate() {
        assert (this.maxLenLive <= this.maxLen) : this.maxLenLive + " > " + this.maxLen;
        if (this.maxLenLive <= 0L) {
            return 0;
        }
        if (this.maxLenLive == this.maxLen) {
            return 100;
        }
        return 1 + (int)(98L * this.maxLenLive / this.maxLen);
    }

    public int hashCode() {
        return this.id;
    }

    public boolean equals(Object object) {
        return object instanceof Chunk && ((Chunk)object).id == this.id;
    }

    public final String asString() {
        StringBuilder stringBuilder = new StringBuilder(240);
        this.dump(stringBuilder);
        return stringBuilder.toString();
    }

    protected void dump(StringBuilder stringBuilder) {
        DataUtils.appendMap(stringBuilder, ATTR_CHUNK, this.id);
        DataUtils.appendMap(stringBuilder, ATTR_BLOCK, this.block);
        DataUtils.appendMap(stringBuilder, ATTR_LEN, this.len);
        DataUtils.appendMap(stringBuilder, ATTR_PAGES, this.pageCount);
        if (this.pageCount != this.pageCountLive) {
            DataUtils.appendMap(stringBuilder, ATTR_LIVE_PAGES, this.pageCountLive);
        }
        DataUtils.appendMap(stringBuilder, ATTR_MAX, this.maxLen);
        if (this.maxLen != this.maxLenLive) {
            DataUtils.appendMap(stringBuilder, ATTR_LIVE_MAX, this.maxLenLive);
        }
        DataUtils.appendMap(stringBuilder, ATTR_MAP, this.mapId);
        if (this.next != 0L) {
            DataUtils.appendMap(stringBuilder, ATTR_NEXT, this.next);
        }
        DataUtils.appendMap(stringBuilder, ATTR_ROOT, this.layoutRootPos);
        DataUtils.appendMap(stringBuilder, ATTR_TIME, this.time);
        if (this.unused != 0L) {
            DataUtils.appendMap(stringBuilder, ATTR_UNUSED, this.unused);
        }
        if (this.unusedAtVersion != 0L) {
            DataUtils.appendMap(stringBuilder, ATTR_UNUSED_AT_VERSION, this.unusedAtVersion);
        }
        DataUtils.appendMap(stringBuilder, ATTR_VERSION, this.version);
        if (this.pinCount > 0) {
            DataUtils.appendMap(stringBuilder, ATTR_PIN_COUNT, this.pinCount);
        }
        if (this.tocPos > 0) {
            DataUtils.appendMap(stringBuilder, ATTR_TOC, this.tocPos);
        }
        if (this.occupancy != null && !this.occupancy.isEmpty()) {
            DataUtils.appendMap(stringBuilder, ATTR_OCCUPANCY, StringUtils.convertBytesToHex(this.occupancy.toByteArray()));
        }
    }

    public String getHeader() {
        return new String(this.getHeaderBytes(), StandardCharsets.ISO_8859_1);
    }

    private byte[] getHeaderBytes() {
        StringBuilder stringBuilder = new StringBuilder(240);
        DataUtils.appendMap(stringBuilder, ATTR_CHUNK, this.id);
        DataUtils.appendMap(stringBuilder, ATTR_LEN, this.len);
        DataUtils.appendMap(stringBuilder, ATTR_PAGES, this.pageCount);
        if (this.pinCount > 0) {
            DataUtils.appendMap(stringBuilder, ATTR_PIN_COUNT, this.pinCount);
        }
        DataUtils.appendMap(stringBuilder, ATTR_MAX, this.maxLen);
        DataUtils.appendMap(stringBuilder, ATTR_MAP, this.mapId);
        DataUtils.appendMap(stringBuilder, ATTR_ROOT, this.layoutRootPos);
        DataUtils.appendMap(stringBuilder, ATTR_TIME, this.time);
        DataUtils.appendMap(stringBuilder, ATTR_VERSION, this.version);
        if (this.next != 0L) {
            DataUtils.appendMap(stringBuilder, ATTR_NEXT, this.next);
        }
        if (this.tocPos > 0) {
            DataUtils.appendMap(stringBuilder, ATTR_TOC, this.tocPos);
        }
        return stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    byte[] getFooterBytes() {
        StringBuilder stringBuilder = new StringBuilder(128);
        DataUtils.appendMap(stringBuilder, ATTR_CHUNK, this.id);
        DataUtils.appendMap(stringBuilder, ATTR_LEN, this.len);
        DataUtils.appendMap(stringBuilder, ATTR_VERSION, this.version);
        byte[] byArray = stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1);
        int n = DataUtils.getFletcher32(byArray, 0, byArray.length);
        DataUtils.appendMap(stringBuilder, ATTR_FLETCHER, n);
        while (stringBuilder.length() < 127) {
            stringBuilder.append(' ');
        }
        stringBuilder.append('\n');
        return stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    boolean isAllocated() {
        return this.block != 0L;
    }

    boolean isSaved() {
        return this.isAllocated() && this.buffer == null;
    }

    boolean isLive() {
        return this.pageCountLive > 0;
    }

    boolean isRewritable() {
        return this.isSaved() && this.isLive() && this.pageCountLive < this.pageCount && this.isEvacuatable();
    }

    private boolean isEvacuatable() {
        return this.pinCount == 0;
    }

    /*
     * Unable to fully structure code
     */
    ByteBuffer readBufferForPage(FileStore<C> var1_1, int var2_2, long var3_3) {
        if (!Chunk.$assertionsDisabled && !this.isSaved()) {
            throw new AssertionError(this);
        }
        while (true) lbl-1000:
        // 3 sources

        {
            var5_4 = this.block;
            try {
                var7_5 = var5_4 * 4096L;
                var9_7 = var7_5 + (long)this.len * 4096L;
                if ((var7_5 += (long)var2_2) < 0L) {
                    throw DataUtils.newMVStoreException(6, "Negative position {0}; p={1}, c={2}", new Object[]{var7_5, var3_3, this.toString()});
                }
                var11_8 = DataUtils.getPageMaxLength(var3_3);
                if (var11_8 == 0x200000) {
                    var11_8 = this.readFully(var1_1, var7_5, 128).getInt();
                    var11_8 += 4;
                }
                if ((var11_8 = (int)Math.min(var9_7 - var7_5, (long)var11_8)) < 0) {
                    throw DataUtils.newMVStoreException(6, "Illegal page length {0} reading at {1}; max pos {2} ", new Object[]{var11_8, var7_5, var9_7});
                }
                var12_9 = this.buffer;
                if (var12_9 == null) {
                    var12_9 = this.readFully(var1_1, var7_5, var11_8);
                } else {
                    var12_9 = var12_9.duplicate();
                    var12_9.position(var2_2);
                    var12_9 = var12_9.slice();
                    var12_9.limit(var11_8);
                }
                if (var5_4 != this.block) ** GOTO lbl-1000
                return var12_9;
            }
            catch (MVStoreException var7_6) {
                if (var5_4 != this.block) continue;
                throw var7_6;
            }
            break;
        }
    }

    /*
     * Unable to fully structure code
     */
    long[] readToC(FileStore<C> var1_1) {
        if (!Chunk.$assertionsDisabled && this.buffer == null && !this.isAllocated()) {
            throw new AssertionError(this);
        }
        if (!Chunk.$assertionsDisabled && this.tocPos <= 0) {
            throw new AssertionError();
        }
        var2_2 = new long[this.pageCount];
        while (true) lbl-1000:
        // 3 sources

        {
            var3_3 = this.block;
            try {
                var5_4 = this.buffer;
                if (var5_4 == null) {
                    var6_6 = this.pageCount * 8;
                    var7_7 = var3_3 * 4096L + (long)this.tocPos;
                    var5_4 = this.readFully(var1_1, var7_7, var6_6);
                } else {
                    var5_4 = var5_4.duplicate();
                    var5_4.position(this.tocPos);
                    var5_4 = var5_4.slice();
                }
                var5_4.asLongBuffer().get(var2_2);
                if (var3_3 != this.block) ** GOTO lbl-1000
                return var2_2;
            }
            catch (MVStoreException var5_5) {
                if (var3_3 != this.block) continue;
                throw var5_5;
            }
            break;
        }
    }

    void accountForWrittenPage(int n, boolean bl) {
        this.maxLen += (long)n;
        ++this.pageCount;
        this.maxLenLive += (long)n;
        ++this.pageCountLive;
        if (bl) {
            ++this.pinCount;
        }
        assert (this.pageCount - this.pageCountLive == this.occupancy.cardinality()) : this.pageCount + " - " + this.pageCountLive + " <> " + this.occupancy.cardinality() + " : " + this.occupancy;
    }

    boolean accountForRemovedPage(int n, int n2, boolean bl, long l, long l2) {
        assert (this.buffer != null || this.isAllocated()) : this;
        if (this.tocPos > 0) {
            assert (n >= 0 && n < this.pageCount) : n + " // " + this.pageCount;
            assert (!this.occupancy.get(n)) : n + " " + this + " " + this.occupancy;
            assert (this.pageCount - this.pageCountLive == this.occupancy.cardinality()) : this.pageCount + " - " + this.pageCountLive + " <> " + this.occupancy.cardinality() + " : " + this.occupancy;
            this.occupancy.set(n);
        }
        this.maxLenLive -= (long)n2;
        --this.pageCountLive;
        if (bl) {
            --this.pinCount;
        }
        if (this.unusedAtVersion < l2) {
            this.unusedAtVersion = l2;
        }
        assert (this.pinCount >= 0) : this;
        assert (this.pageCountLive >= 0) : this;
        assert (this.pinCount <= this.pageCountLive) : this;
        assert (this.maxLenLive >= 0L) : this;
        assert (this.pageCountLive == 0 == (this.maxLenLive == 0L)) : this;
        if (!this.isLive()) {
            this.unused = l;
            return true;
        }
        return false;
    }

    public String toString() {
        return this.asString() + (this.buffer == null ? "" : ", buf");
    }

    public static final class PositionComparator<C extends Chunk<C>>
    implements Comparator<C> {
        public static final Comparator<? extends Chunk<?>> INSTANCE = new PositionComparator();

        public static <C extends Chunk<C>> Comparator<C> instance() {
            return INSTANCE;
        }

        private PositionComparator() {
        }

        @Override
        public int compare(C c, C c2) {
            return Long.compare(((Chunk)c).block, ((Chunk)c2).block);
        }
    }
}

