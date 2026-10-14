/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.BufferedReader;
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
import org.h2.store.RangeReader;
import org.h2.util.IOUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueLob;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;
import org.h2.value.lob.LobDataFetchOnDemand;
import org.h2.value.lob.LobDataFile;
import org.h2.value.lob.LobDataInMemory;

public final class ValueClob
extends ValueLob {
    public static ValueClob createSmall(byte[] byArray) {
        return new ValueClob(new LobDataInMemory(byArray), byArray.length, new String(byArray, StandardCharsets.UTF_8).length());
    }

    public static ValueClob createSmall(byte[] byArray, long l) {
        return new ValueClob(new LobDataInMemory(byArray), byArray.length, l);
    }

    public static ValueClob createSmall(String string) {
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        return new ValueClob(new LobDataInMemory(byArray), byArray.length, string.length());
    }

    public static ValueClob createTempClob(Reader reader, long l, DataHandler dataHandler) {
        if (l >= 0L) {
            try {
                reader = new RangeReader(reader, 0L, l);
            }
            catch (IOException iOException) {
                throw DbException.convert(iOException);
            }
        }
        BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, 4096);
        try {
            char[] cArray;
            int n;
            long l2 = Long.MAX_VALUE;
            if (l >= 0L && l < l2) {
                l2 = l;
            }
            if ((n = ValueLob.getBufferSize(dataHandler, l2)) >= Integer.MAX_VALUE) {
                String string = IOUtils.readStringAndClose(bufferedReader, -1);
                cArray = string.toCharArray();
                n = cArray.length;
            } else {
                cArray = new char[n];
                bufferedReader.mark(n);
                n = IOUtils.readFully(bufferedReader, cArray, n);
            }
            if (n <= dataHandler.getMaxLengthInplaceLob()) {
                return ValueClob.createSmall(new String(cArray, 0, n));
            }
            bufferedReader.reset();
            return ValueClob.createTemporary(dataHandler, bufferedReader, l2);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    private static ValueClob createTemporary(DataHandler dataHandler, Reader reader, long l) throws IOException {
        String string = ValueLob.createTempLobFileName(dataHandler);
        FileStore fileStore = dataHandler.openFile(string, "rw", false);
        fileStore.autoDelete();
        long l2 = 0L;
        long l3 = 0L;
        try (FileStoreOutputStream fileStoreOutputStream = new FileStoreOutputStream(fileStore, null);){
            char[] cArray = new char[4096];
            while (true) {
                int n = ValueLob.getBufferSize(dataHandler, l);
                if ((n = IOUtils.readFully(reader, cArray, n)) == 0) {
                    break;
                }
                byte[] byArray = new String(cArray, 0, n).getBytes(StandardCharsets.UTF_8);
                fileStoreOutputStream.write(byArray);
                l2 += (long)byArray.length;
                l3 += (long)n;
            }
        }
        return new ValueClob(new LobDataFile(dataHandler, string, fileStore), l2, l3);
    }

    public ValueClob(LobData lobData, long l, long l2) {
        super(lobData, l, l2);
    }

    @Override
    public int getValueType() {
        return 3;
    }

    @Override
    public String getString() {
        if (this.charLength > 1000000000L) {
            throw this.getStringTooLong(this.charLength);
        }
        if (this.lobData instanceof LobDataInMemory) {
            return new String(((LobDataInMemory)this.lobData).getSmall(), StandardCharsets.UTF_8);
        }
        return this.readString((int)this.charLength);
    }

    @Override
    byte[] getBytesInternal() {
        long l = this.octetLength;
        if (l >= 0L) {
            if (l > 1000000000L) {
                throw this.getBinaryTooLong(l);
            }
            return this.readBytes((int)l);
        }
        if (this.octetLength > 1000000000L) {
            throw this.getBinaryTooLong(this.octetLength());
        }
        byte[] byArray = this.readBytes(Integer.MAX_VALUE);
        this.octetLength = l = (long)byArray.length;
        if (l > 1000000000L) {
            throw this.getBinaryTooLong(l);
        }
        return byArray;
    }

    @Override
    public InputStream getInputStream() {
        return this.lobData.getInputStream(-1L);
    }

    @Override
    public InputStream getInputStream(long l, long l2) {
        return ValueClob.rangeInputStream(this.lobData.getInputStream(-1L), l, l2, -1L);
    }

    @Override
    public Reader getReader(long l, long l2) {
        return ValueClob.rangeReader(this.getReader(), l, l2, this.charLength);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        if (value == this) {
            return 0;
        }
        ValueClob valueClob = (ValueClob)value;
        LobData lobData = this.lobData;
        LobData lobData2 = valueClob.lobData;
        if (lobData.getClass() == lobData2.getClass()) {
            if (lobData instanceof LobDataInMemory) {
                return Integer.signum(this.getString().compareTo(valueClob.getString()));
            }
            if (lobData instanceof LobDataDatabase ? ((LobDataDatabase)lobData).getLobId() == ((LobDataDatabase)lobData2).getLobId() : lobData instanceof LobDataFetchOnDemand && ((LobDataFetchOnDemand)lobData).getLobId() == ((LobDataFetchOnDemand)lobData2).getLobId()) {
                return 0;
            }
        }
        return ValueClob.compare(this, valueClob);
    }

    /*
     * Exception decompiling
     */
    private static int compare(ValueClob var0, ValueClob var1_1) {
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
        if (!((n & 2) == 0 || this.lobData instanceof LobDataInMemory && this.charLength <= SysProperties.MAX_TRACE_DATA_LENGTH)) {
            stringBuilder.append("SPACE(").append(this.charLength);
            this.formatLobDataComment(stringBuilder);
        } else if ((n & 6) == 0) {
            StringUtils.quoteStringSQL(stringBuilder.append("CAST("), this.getString()).append(" AS CHARACTER LARGE OBJECT(").append(this.charLength).append("))");
        } else {
            StringUtils.quoteStringSQL(stringBuilder, this.getString());
        }
        return stringBuilder;
    }

    ValueClob convertPrecision(long l) {
        ValueClob valueClob;
        if (this.charLength <= l) {
            return this;
        }
        DataHandler dataHandler = this.lobData.getDataHandler();
        if (dataHandler != null) {
            valueClob = ValueClob.createTempClob(this.getReader(), l, dataHandler);
        } else {
            try {
                valueClob = ValueClob.createSmall(IOUtils.readStringAndClose(this.getReader(), MathUtils.convertLongToInt(l)));
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, null);
            }
        }
        return valueClob;
    }

    @Override
    public ValueLob copy(DataHandler dataHandler, int n) {
        if (this.lobData instanceof LobDataInMemory) {
            byte[] byArray = ((LobDataInMemory)this.lobData).getSmall();
            if (byArray.length > dataHandler.getMaxLengthInplaceLob()) {
                LobStorageInterface lobStorageInterface = dataHandler.getLobStorage();
                ValueClob valueClob = lobStorageInterface.createClob(this.getReader(), this.charLength);
                ValueLob valueLob = valueClob.copy(dataHandler, n);
                valueClob.remove();
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
        return this.charLength;
    }

    @Override
    public long octetLength() {
        long l = this.octetLength;
        if (l < 0L) {
            if (this.lobData instanceof LobDataInMemory) {
                l = ((LobDataInMemory)this.lobData).getSmall().length;
            } else {
                try (InputStream inputStream2 = this.getInputStream();){
                    l = 0L;
                    while (true) {
                        l += inputStream2.skip(Long.MAX_VALUE);
                        if (inputStream2.read() < 0) {
                            break;
                        }
                        ++l;
                    }
                }
                catch (IOException iOException) {
                    throw DbException.convertIOException(iOException, null);
                }
            }
            this.octetLength = l;
        }
        return l;
    }
}

