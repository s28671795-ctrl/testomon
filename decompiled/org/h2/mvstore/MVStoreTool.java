/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;
import org.h2.compress.CompressDeflate;
import org.h2.compress.CompressLZF;
import org.h2.compress.Compressor;
import org.h2.mvstore.Chunk;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.SFChunk;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.StringDataType;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FileUtils;
import org.h2.util.Utils;

public class MVStoreTool {
    public static void main(String ... stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string;
            if ("-dump".equals(stringArray[i])) {
                string = stringArray[++i];
                MVStoreTool.dump(string, new PrintWriter(System.out), true);
                continue;
            }
            if ("-info".equals(stringArray[i])) {
                string = stringArray[++i];
                MVStoreTool.info(string, new PrintWriter(System.out));
                continue;
            }
            if ("-compact".equals(stringArray[i])) {
                string = stringArray[++i];
                MVStoreTool.compact(string, false);
                continue;
            }
            if ("-compress".equals(stringArray[i])) {
                string = stringArray[++i];
                MVStoreTool.compact(string, true);
                continue;
            }
            if ("-rollback".equals(stringArray[i])) {
                string = stringArray[++i];
                long l = Long.decode(stringArray[++i]);
                MVStoreTool.rollback(string, l, new PrintWriter(System.out));
                continue;
            }
            if (!"-repair".equals(stringArray[i])) continue;
            string = stringArray[++i];
            MVStoreTool.repair(string);
        }
    }

    public static void dump(String string, boolean bl) {
        MVStoreTool.dump(string, new PrintWriter(System.out), bl);
    }

    public static void info(String string) {
        MVStoreTool.info(string, new PrintWriter(System.out));
    }

    public static void dump(String string, Writer writer, boolean bl) {
        PrintWriter printWriter = new PrintWriter(writer, true);
        if (!FilePath.get(string).exists()) {
            printWriter.println("File not found: " + string);
            return;
        }
        long l = FileUtils.size(string);
        printWriter.printf("File %s, %d bytes, %d MB\n", string, l, l / 1024L / 1024L);
        int n = 4096;
        TreeMap<Integer, Long> treeMap = new TreeMap<Integer, Long>();
        long l2 = 0L;
        try (FileChannel fileChannel = FilePath.get(string).open("r");){
            long l3 = fileChannel.size();
            int n2 = Long.toHexString(l3).length();
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            long l4 = 0L;
            long l5 = 0L;
            while (l5 < l3) {
                int n3;
                int n4;
                Object object;
                byteBuffer.rewind();
                try {
                    DataUtils.readFully(fileChannel, l5, byteBuffer);
                }
                catch (MVStoreException mVStoreException) {
                    printWriter.printf("ERROR illegal position %d%n", l5 += (long)n);
                    continue;
                }
                byteBuffer.rewind();
                byte by = byteBuffer.get();
                if (by == 72) {
                    object = new String(byteBuffer.array(), StandardCharsets.ISO_8859_1).trim();
                    printWriter.printf("%0" + n2 + "x fileHeader %s%n", l5, object);
                    l5 += (long)n;
                    continue;
                }
                if (by != 99) {
                    l5 += (long)n;
                    continue;
                }
                byteBuffer.position(0);
                try {
                    object = new SFChunk(Chunk.readChunkHeader(byteBuffer));
                }
                catch (MVStoreException mVStoreException) {
                    l5 += (long)n;
                    continue;
                }
                if (((Chunk)object).len <= 0) {
                    l5 += (long)n;
                    continue;
                }
                int n5 = ((Chunk)object).len * 4096;
                printWriter.printf("%n%0" + n2 + "x chunkHeader %s%n", l5, object);
                ByteBuffer byteBuffer2 = ByteBuffer.allocate(n5);
                DataUtils.readFully(fileChannel, l5, byteBuffer2);
                int n6 = byteBuffer.position();
                l5 += (long)n5;
                int n7 = ((Chunk)object).pageCount;
                l4 += (long)((Chunk)object).pageCount;
                TreeMap<Integer, Integer> treeMap2 = new TreeMap<Integer, Integer>();
                int n8 = 0;
                while (n7 > 0) {
                    boolean bl2;
                    n4 = n6;
                    try {
                        byteBuffer2.position(n6);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        printWriter.printf("ERROR illegal position %d%n", n6);
                        break;
                    }
                    int n9 = byteBuffer2.getInt();
                    byteBuffer2.getShort();
                    DataUtils.readVarInt(byteBuffer2);
                    n3 = DataUtils.readVarInt(byteBuffer2);
                    int n10 = DataUtils.readVarInt(byteBuffer2);
                    byte by2 = byteBuffer2.get();
                    boolean bl3 = (by2 & 2) != 0;
                    boolean bl4 = bl2 = (by2 & 1) != 0;
                    if (bl) {
                        printWriter.printf("+%0" + n2 + "x %s, map %x, %d entries, %d bytes, maxLen %x%n", n6, (bl2 ? "node" : "leaf") + (bl3 ? " compressed" : ""), n3, bl2 ? n10 + 1 : n10, n9, DataUtils.getPageMaxLength(DataUtils.composePagePos(0, 0, n9, 0)));
                    }
                    n6 += n9;
                    Integer n11 = (Integer)treeMap2.get(n3);
                    if (n11 == null) {
                        n11 = 0;
                    }
                    treeMap2.put(n3, n11 + n9);
                    Long l6 = (Long)treeMap.get(n3);
                    if (l6 == null) {
                        l6 = 0L;
                    }
                    treeMap.put(n3, l6 + (long)n9);
                    n8 += n9;
                    l2 += (long)n9;
                    --n7;
                    long[] lArray = null;
                    long[] lArray2 = null;
                    if (bl2) {
                        int n12;
                        lArray = new long[n10 + 1];
                        for (n12 = 0; n12 <= n10; ++n12) {
                            lArray[n12] = byteBuffer2.getLong();
                        }
                        lArray2 = new long[n10 + 1];
                        for (n12 = 0; n12 <= n10; ++n12) {
                            long l7;
                            lArray2[n12] = l7 = DataUtils.readVarLong(byteBuffer2);
                        }
                    }
                    String[] stringArray = new String[n10];
                    if (n3 == 0 && bl) {
                        int n13;
                        ByteBuffer byteBuffer3;
                        Object object2;
                        int n14;
                        if (bl3) {
                            n14 = (by2 & 6) != 6 ? 1 : 0;
                            object2 = MVStoreTool.getCompressor(n14 != 0);
                            int n15 = DataUtils.readVarInt(byteBuffer2);
                            int n16 = n9 + n4 - byteBuffer2.position();
                            byte[] byArray = Utils.newBytes(n16);
                            byteBuffer2.get(byArray);
                            int n17 = n16 + n15;
                            byteBuffer3 = ByteBuffer.allocate(n17);
                            object2.expand(byArray, 0, n16, byteBuffer3.array(), 0, n17);
                        } else {
                            byteBuffer3 = byteBuffer2;
                        }
                        for (n14 = 0; n14 < n10; ++n14) {
                            stringArray[n14] = object2 = StringDataType.INSTANCE.read(byteBuffer3);
                        }
                        if (bl2) {
                            for (n14 = 0; n14 < n10; ++n14) {
                                long l8 = lArray[n14];
                                printWriter.printf("    %d children < %s @ chunk %x +%0" + n2 + "x%n", lArray2[n14], stringArray[n14], DataUtils.getPageChunkId(l8), DataUtils.getPageOffset(l8));
                            }
                            long l9 = lArray[n10];
                            printWriter.printf("    %d children >= %s @ chunk %x +%0" + n2 + "x%n", lArray2[n10], n10 <= stringArray.length ? null : stringArray[n10], DataUtils.getPageChunkId(l9), DataUtils.getPageOffset(l9));
                            continue;
                        }
                        String[] stringArray2 = new String[n10];
                        for (n13 = 0; n13 < n10; ++n13) {
                            String string2;
                            stringArray2[n13] = string2 = StringDataType.INSTANCE.read(byteBuffer3);
                        }
                        for (n13 = 0; n13 < n10; ++n13) {
                            printWriter.println("    " + stringArray[n13] + " = " + stringArray2[n13]);
                        }
                        continue;
                    }
                    if (!bl2 || !bl) continue;
                    for (int i = 0; i <= n10; ++i) {
                        long l10 = lArray[i];
                        printWriter.printf("    %d children @ chunk %x +%0" + n2 + "x%n", lArray2[i], DataUtils.getPageChunkId(l10), DataUtils.getPageOffset(l10));
                    }
                }
                n8 = Math.max(1, n8);
                for (Integer n18 : treeMap2.keySet()) {
                    n3 = 100 * (Integer)treeMap2.get(n18) / n8;
                    printWriter.printf("map %x: %d bytes, %d%%%n", n18, treeMap2.get(n18), n3);
                }
                n4 = byteBuffer2.limit() - 128;
                try {
                    byteBuffer2.position(n4);
                    printWriter.printf("+%0" + n2 + "x chunkFooter %s%n", n4, new String(byteBuffer2.array(), byteBuffer2.position(), 128, StandardCharsets.ISO_8859_1).trim());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    printWriter.printf("ERROR illegal footer position %d%n", n4);
                }
            }
            printWriter.printf("%n%0" + n2 + "x eof%n", l3);
            printWriter.printf("\n", new Object[0]);
            l4 = Math.max(1L, l4);
            printWriter.printf("page size total: %d bytes, page count: %d, average page size: %d bytes\n", l2, l4, l2 / l4);
            l2 = Math.max(1L, l2);
            for (Integer n19 : treeMap.keySet()) {
                int n20 = (int)(100L * (Long)treeMap.get(n19) / l2);
                printWriter.printf("map %x: %d bytes, %d%%%n", n19, treeMap.get(n19), n20);
            }
        }
        catch (IOException iOException) {
            printWriter.println("ERROR: " + iOException);
            iOException.printStackTrace(printWriter);
        }
        printWriter.flush();
    }

    private static Compressor getCompressor(boolean bl) {
        return bl ? new CompressLZF() : new CompressDeflate();
    }

    public static String info(String string, Writer writer) {
        PrintWriter printWriter = new PrintWriter(writer, true);
        if (!FilePath.get(string).exists()) {
            printWriter.println("File not found: " + string);
            return "File not found: " + string;
        }
        long l = FileUtils.size(string);
        try (MVStore mVStore = new MVStore.Builder().fileName(string).recoveryMode().readOnly().open();){
            Object object;
            Map<String, String> map = mVStore.getLayoutMap();
            Map<String, Object> map2 = mVStore.getStoreHeader();
            long l2 = DataUtils.readHexLong(map2, "created", 0L);
            TreeMap treeMap = new TreeMap();
            long l3 = 0L;
            long l4 = 0L;
            long l5 = 0L;
            long l6 = 0L;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                object = entry.getKey();
                if (!((String)object).startsWith("chunk.")) continue;
                Object obj = mVStore.getFileStore().createChunk(entry.getValue());
                treeMap.put(((Chunk)obj).id, obj);
                l3 += (long)((Chunk)obj).len * 4096L;
                l4 += ((Chunk)obj).maxLen;
                l5 += ((Chunk)obj).maxLenLive;
                if (((Chunk)obj).maxLenLive <= 0L) continue;
                l6 += ((Chunk)obj).maxLen;
            }
            printWriter.printf("Created: %s\n", MVStoreTool.formatTimestamp(l2, l2));
            printWriter.printf("Last modified: %s\n", MVStoreTool.formatTimestamp(FileUtils.lastModified(string), l2));
            printWriter.printf("File length: %d\n", l);
            printWriter.printf("The last chunk is not listed\n", new Object[0]);
            printWriter.printf("Chunk length: %d\n", l3);
            printWriter.printf("Chunk count: %d\n", treeMap.size());
            printWriter.printf("Used space: %d%%\n", MVStoreTool.getPercent(l3, l));
            printWriter.printf("Chunk fill rate: %d%%\n", l4 == 0L ? 100 : MVStoreTool.getPercent(l5, l4));
            printWriter.printf("Chunk fill rate excluding empty chunks: %d%%\n", l6 == 0L ? 100 : MVStoreTool.getPercent(l5, l6));
            for (Map.Entry<String, String> entry : treeMap.entrySet()) {
                object = (Chunk)((Object)entry.getValue());
                long l7 = l2 + ((Chunk)object).time;
                printWriter.printf("  Chunk %d: %s, %d%% used, %d blocks", ((Chunk)object).id, MVStoreTool.formatTimestamp(l7, l2), MVStoreTool.getPercent(((Chunk)object).maxLenLive, ((Chunk)object).maxLen), ((Chunk)object).len);
                if (((Chunk)object).maxLenLive == 0L) {
                    printWriter.printf(", unused: %s", MVStoreTool.formatTimestamp(l2 + ((Chunk)object).unused, l2));
                }
                printWriter.printf("\n", new Object[0]);
            }
            printWriter.printf("\n", new Object[0]);
        }
        catch (Exception exception) {
            printWriter.println("ERROR: " + exception);
            exception.printStackTrace(printWriter);
            return exception.getMessage();
        }
        printWriter.flush();
        return null;
    }

    private static String formatTimestamp(long l, long l2) {
        String string = new Timestamp(l).toString();
        String string2 = string.substring(0, 19);
        string2 = string2 + " (+" + (l - l2) / 1000L + " s)";
        return string2;
    }

    private static int getPercent(long l, long l2) {
        if (l == 0L) {
            return 0;
        }
        if (l == l2) {
            return 100;
        }
        return (int)(1L + 98L * l / Math.max(1L, l2));
    }

    public static void compact(String string, boolean bl) {
        String string2 = string + ".tempFile";
        FileUtils.delete(string2);
        MVStoreTool.compact(string, string2, bl);
        MVStoreTool.moveAtomicReplace(string2, string);
    }

    public static void moveAtomicReplace(String string, String string2) {
        try {
            FileUtils.moveAtomicReplace(string, string2);
        }
        catch (MVStoreException mVStoreException) {
            String string3 = string2 + ".newFile";
            FileUtils.delete(string3);
            FileUtils.move(string, string3);
            FileUtils.delete(string2);
            FileUtils.move(string3, string2);
        }
    }

    public static void compactCleanUp(String string) {
        String string2;
        String string3 = string + ".tempFile";
        if (FileUtils.exists(string3)) {
            FileUtils.delete(string3);
        }
        if (FileUtils.exists(string2 = string + ".newFile")) {
            if (FileUtils.exists(string)) {
                FileUtils.delete(string2);
            } else {
                FileUtils.move(string2, string);
            }
        }
    }

    public static void compact(String string, String string2, boolean bl) {
        try (MVStore mVStore = new MVStore.Builder().fileName(string).readOnly().open();){
            FileUtils.delete(string2);
            MVStore.Builder builder = new MVStore.Builder().fileName(string2);
            if (bl) {
                builder.compress();
            }
            try (MVStore mVStore2 = builder.open();){
                MVStoreTool.compact(mVStore, mVStore2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void compact(MVStore mVStore, MVStore mVStore2) {
        mVStore2.setCurrentVersion(mVStore.getCurrentVersion());
        mVStore2.adjustLastMapId(mVStore.getLastMapId());
        int n = mVStore2.getAutoCommitDelay();
        boolean bl = mVStore2.isSpaceReused();
        try {
            Object object;
            mVStore2.setReuseSpace(false);
            mVStore2.setAutoCommitDelay(0);
            MVMap<String, String> mVMap = mVStore.getMetaMap();
            MVMap<String, String> mVMap2 = mVStore2.getMetaMap();
            for (Map.Entry<String, String> entry : mVMap.entrySet()) {
                object = entry.getKey();
                if (((String)object).startsWith("map.") || ((String)object).startsWith("name.")) continue;
                mVMap2.put((String)object, entry.getValue());
            }
            for (String string : mVStore.getMapNames()) {
                object = MVStoreTool.getGenericMapBuilder();
                if (string.startsWith("undoLog")) {
                    ((MVMap.Builder)object).singleWriter();
                }
                Object m4 = mVStore.openMap(string, object);
                Object m5 = mVStore2.openMap(string, object);
                ((MVMap)m5).copyFrom(m4);
                mVMap2.put(MVMap.getMapKey(((MVMap)m5).getId()), mVMap.get(MVMap.getMapKey(((MVMap)m4).getId())));
            }
            mVStore2.commit();
        }
        finally {
            mVStore2.setAutoCommitDelay(n);
            mVStore2.setReuseSpace(bl);
        }
    }

    public static void repair(String string) {
        PrintWriter printWriter = new PrintWriter(System.out);
        OutputStream outputStream2 = new OutputStream(){

            @Override
            public void write(int n) {
            }
        };
        for (long i = Long.MAX_VALUE; i >= 0L; --i) {
            printWriter.println(i == Long.MAX_VALUE ? "Trying latest version" : "Trying version " + i);
            printWriter.flush();
            i = MVStoreTool.rollback(string, i, new PrintWriter(outputStream2));
            try {
                String string2 = MVStoreTool.info(string + ".temp", new PrintWriter(outputStream2));
                if (string2 == null) {
                    FilePath.get(string).moveTo(FilePath.get(string + ".back"), true);
                    FilePath.get(string + ".temp").moveTo(FilePath.get(string), true);
                    printWriter.println("Success");
                    break;
                }
                printWriter.println("    ... failed: " + string2);
                continue;
            }
            catch (Exception exception) {
                printWriter.println("Fail: " + exception.getMessage());
                printWriter.flush();
            }
        }
        printWriter.flush();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long rollback(String string, long l, Writer writer) {
        long l2 = -1L;
        PrintWriter printWriter = new PrintWriter(writer, true);
        if (!FilePath.get(string).exists()) {
            printWriter.println("File not found: " + string);
            return l2;
        }
        FileChannel fileChannel = null;
        AbstractInterruptibleChannel abstractInterruptibleChannel = null;
        int n = 4096;
        try {
            fileChannel = FilePath.get(string).open("r");
            FilePath.get(string + ".temp").delete();
            abstractInterruptibleChannel = FilePath.get(string + ".temp").open("rw");
            long l3 = fileChannel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            SFChunk sFChunk = null;
            long l4 = 0L;
            while (l4 < l3) {
                SFChunk sFChunk2;
                byteBuffer.rewind();
                DataUtils.readFully(fileChannel, l4, byteBuffer);
                byteBuffer.rewind();
                byte by = byteBuffer.get();
                if (by == 72) {
                    byteBuffer.rewind();
                    ((FileChannel)abstractInterruptibleChannel).write(byteBuffer, l4);
                    l4 += (long)n;
                    continue;
                }
                if (by != 99) {
                    l4 += (long)n;
                    continue;
                }
                try {
                    sFChunk2 = new SFChunk(Chunk.readChunkHeader(byteBuffer));
                }
                catch (MVStoreException mVStoreException) {
                    l4 += (long)n;
                    continue;
                }
                if (sFChunk2.len <= 0) {
                    l4 += (long)n;
                    continue;
                }
                int n2 = sFChunk2.len * 4096;
                ByteBuffer byteBuffer2 = ByteBuffer.allocate(n2);
                DataUtils.readFully(fileChannel, l4, byteBuffer2);
                if (sFChunk2.version > l) {
                    l4 += (long)n2;
                    continue;
                }
                byteBuffer2.rewind();
                ((FileChannel)abstractInterruptibleChannel).write(byteBuffer2, l4);
                if (sFChunk == null || sFChunk2.version > sFChunk.version) {
                    sFChunk = sFChunk2;
                    l2 = sFChunk2.version;
                }
                l4 += (long)n2;
            }
            int n3 = sFChunk.len * 4096;
            ByteBuffer byteBuffer3 = ByteBuffer.allocate(n3);
            DataUtils.readFully(fileChannel, sFChunk.block * 4096L, byteBuffer3);
            byteBuffer3.rewind();
            ((FileChannel)abstractInterruptibleChannel).write(byteBuffer3, l3);
        }
        catch (IOException iOException) {
            printWriter.println("ERROR: " + iOException);
            iOException.printStackTrace(printWriter);
        }
        finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                }
                catch (IOException iOException) {}
            }
            if (abstractInterruptibleChannel != null) {
                try {
                    abstractInterruptibleChannel.close();
                }
                catch (IOException iOException) {}
            }
        }
        printWriter.flush();
        return l2;
    }

    static MVMap.Builder<Object, Object> getGenericMapBuilder() {
        return ((MVMap.Builder)new MVMap.Builder().keyType((DataType)GenericDataType.INSTANCE)).valueType((DataType)GenericDataType.INSTANCE);
    }

    private static class GenericDataType
    extends BasicDataType<byte[]> {
        static GenericDataType INSTANCE = new GenericDataType();

        private GenericDataType() {
        }

        @Override
        public boolean isMemoryEstimationAllowed() {
            return false;
        }

        @Override
        public int getMemory(byte[] byArray) {
            return byArray == null ? 0 : byArray.length * 8;
        }

        public byte[][] createStorage(int n) {
            return new byte[n][];
        }

        @Override
        public void write(WriteBuffer writeBuffer, byte[] byArray) {
            if (byArray != null) {
                writeBuffer.put(byArray);
            }
        }

        @Override
        public byte[] read(ByteBuffer byteBuffer) {
            int n = byteBuffer.remaining();
            if (n == 0) {
                return null;
            }
            byte[] byArray = new byte[n];
            byteBuffer.get(byArray);
            return byArray;
        }
    }
}

