/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.store.DataHandler;
import org.h2.store.FileStore;
import org.h2.store.FileStoreOutputStream;
import org.h2.store.LobStorageInterface;
import org.h2.util.Bits;
import org.h2.util.IOUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueLob;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;
import org.h2.value.lob.LobDataFetchOnDemand;
import org.h2.value.lob.LobDataFile;
import org.h2.value.lob.LobDataInMemory;

public final class ValueBlob
extends ValueLob {
    public static ValueBlob createSmall(byte[] byArray) {
        return new ValueBlob(new LobDataInMemory(byArray), byArray.length);
    }

    public static ValueBlob createTempBlob(InputStream inputStream2, long l, DataHandler dataHandler) {
        try {
            byte[] byArray;
            int n;
            long l2 = Long.MAX_VALUE;
            if (l >= 0L && l < l2) {
                l2 = l;
            }
            if ((n = ValueLob.getBufferSize(dataHandler, l2)) >= Integer.MAX_VALUE) {
                byArray = IOUtils.readBytesAndClose(inputStream2, -1);
                n = byArray.length;
            } else {
                byArray = Utils.newBytes(n);
                n = IOUtils.readFully(inputStream2, byArray, n);
            }
            if (n <= dataHandler.getMaxLengthInplaceLob()) {
                return ValueBlob.createSmall(Utils.copyBytes(byArray, n));
            }
            return ValueBlob.createTemporary(dataHandler, byArray, n, inputStream2, l2);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    private static ValueBlob createTemporary(DataHandler dataHandler, byte[] byArray, int n, InputStream inputStream2, long l) throws IOException {
        String string = ValueLob.createTempLobFileName(dataHandler);
        FileStore fileStore = dataHandler.openFile(string, "rw", false);
        fileStore.autoDelete();
        long l2 = 0L;
        try (FileStoreOutputStream fileStoreOutputStream = new FileStoreOutputStream(fileStore, null);){
            do {
                l2 += (long)n;
                fileStoreOutputStream.write(byArray, 0, n);
                if ((l -= (long)n) <= 0L) {
                    break;
                }
                n = ValueLob.getBufferSize(dataHandler, l);
            } while ((n = IOUtils.readFully(inputStream2, byArray, n)) > 0);
        }
        return new ValueBlob(new LobDataFile(dataHandler, string, fileStore), l2);
    }

    public ValueBlob(LobData lobData, long l) {
        super(lobData, l, -1L);
    }

    @Override
    public int getValueType() {
        return 7;
    }

    @Override
    public String getString() {
        long l = this.charLength;
        if (l >= 0L) {
            if (l > 1000000000L) {
                throw this.getStringTooLong(l);
            }
            return this.readString((int)l);
        }
        if (this.octetLength > 3000000000L) {
            throw this.getStringTooLong(this.charLength());
        }
        String string = this.lobData instanceof LobDataInMemory ? new String(((LobDataInMemory)this.lobData).getSmall(), StandardCharsets.UTF_8) : this.readString(Integer.MAX_VALUE);
        this.charLength = l = (long)string.length();
        if (l > 1000000000L) {
            throw this.getStringTooLong(l);
        }
        return string;
    }

    @Override
    byte[] getBytesInternal() {
        if (this.octetLength > 1000000000L) {
            throw this.getBinaryTooLong(this.octetLength);
        }
        return this.readBytes((int)this.octetLength);
    }

    @Override
    public InputStream getInputStream() {
        return this.lobData.getInputStream(this.octetLength);
    }

    @Override
    public InputStream getInputStream(long l, long l2) {
        long l3 = this.octetLength;
        return ValueBlob.rangeInputStream(this.lobData.getInputStream(l3), l, l2, l3);
    }

    @Override
    public Reader getReader(long l, long l2) {
        return ValueBlob.rangeReader(this.getReader(), l, l2, -1L);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        if (value == this) {
            return 0;
        }
        ValueBlob valueBlob = (ValueBlob)value;
        LobData lobData = this.lobData;
        LobData lobData2 = valueBlob.lobData;
        if (lobData.getClass() == lobData2.getClass()) {
            if (lobData instanceof LobDataInMemory) {
                return Bits.compareNotNullUnsigned(((LobDataInMemory)lobData).getSmall(), ((LobDataInMemory)lobData2).getSmall());
            }
            if (lobData instanceof LobDataDatabase ? ((LobDataDatabase)lobData).getLobId() == ((LobDataDatabase)lobData2).getLobId() : lobData instanceof LobDataFetchOnDemand && ((LobDataFetchOnDemand)lobData).getLobId() == ((LobDataFetchOnDemand)lobData2).getLobId()) {
                return 0;
            }
        }
        return ValueBlob.compare(this, valueBlob);
    }

    /*
     * Exception decompiling
     */
    private static int compare(ValueBlob var0, ValueBlob var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 42[FORLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if (!((n & 2) == 0 || this.lobData instanceof LobDataInMemory && this.octetLength <= SysProperties.MAX_TRACE_DATA_LENGTH)) {
            stringBuilder.append("CAST(REPEAT(CHAR(0), ").append(this.octetLength).append(") AS BINARY VARYING");
            this.formatLobDataComment(stringBuilder);
        } else if ((n & 6) == 0) {
            stringBuilder.append("CAST(X'");
            StringUtils.convertBytesToHex(stringBuilder, this.getBytesNoCopy()).append("' AS BINARY LARGE OBJECT(").append(this.octetLength).append("))");
        } else {
            stringBuilder.append("X'");
            StringUtils.convertBytesToHex(stringBuilder, this.getBytesNoCopy()).append('\'');
        }
        return stringBuilder;
    }

    ValueBlob convertPrecision(long l) {
        ValueBlob valueBlob;
        if (this.octetLength <= l) {
            return this;
        }
        DataHandler dataHandler = this.lobData.getDataHandler();
        if (dataHandler != null) {
            valueBlob = ValueBlob.createTempBlob(this.getInputStream(), l, dataHandler);
        } else {
            try {
                valueBlob = ValueBlob.createSmall(IOUtils.readBytesAndClose(this.getInputStream(), MathUtils.convertLongToInt(l)));
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, null);
            }
        }
        return valueBlob;
    }

    @Override
    public ValueLob copy(DataHandler dataHandler, int n) {
        if (this.lobData instanceof LobDataInMemory) {
            byte[] byArray = ((LobDataInMemory)this.lobData).getSmall();
            if (byArray.length > dataHandler.getMaxLengthInplaceLob()) {
                LobStorageInterface lobStorageInterface = dataHandler.getLobStorage();
                ValueBlob valueBlob = lobStorageInterface.createBlob(this.getInputStream(), this.octetLength);
                ValueLob valueLob = valueBlob.copy(dataHandler, n);
                valueBlob.remove();
                return valueLob;
            }
            return this;
        }
        if (this.lobData instanceof LobDataDatabase) {
            return dataHandler.getLobStorage().copyLob(this, n);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public long charLength() {
        long l = this.charLength;
        if (l < 0L) {
            if (this.lobData instanceof LobDataInMemory) {
                l = new String(((LobDataInMemory)this.lobData).getSmall(), StandardCharsets.UTF_8).length();
            } else {
                try (Reader reader = this.getReader();){
                    l = 0L;
                    while (true) {
                        l += reader.skip(Long.MAX_VALUE);
                        if (reader.read() < 0) {
                            break;
                        }
                        ++l;
                    }
                }
                catch (IOException iOException) {
                    throw DbException.convertIOException(iOException, null);
                }
            }
            this.charLength = l;
        }
        return l;
    }

    @Override
    public long octetLength() {
        return this.octetLength;
    }
}

