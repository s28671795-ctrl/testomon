/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.store.DataHandler;
import org.h2.store.LobStorageInterface;
import org.h2.store.RangeInputStream;
import org.h2.store.RangeReader;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;
import org.h2.value.lob.LobDataFetchOnDemand;
import org.h2.value.lob.LobDataInMemory;

public abstract class ValueLob
extends Value {
    static final int BLOCK_COMPARISON_SIZE = 512;
    private TypeInfo type;
    final LobData lobData;
    long octetLength;
    long charLength;
    private int hash;

    private static void rangeCheckUnknown(long l, long l2) {
        if (l < 0L) {
            throw DbException.getInvalidValueException("offset", l + 1L);
        }
        if (l2 < 0L) {
            throw DbException.getInvalidValueException("length", l2);
        }
    }

    protected static InputStream rangeInputStream(InputStream inputStream2, long l, long l2, long l3) {
        if (l3 > 0L) {
            ValueLob.rangeCheck(l - 1L, l2, l3);
        } else {
            ValueLob.rangeCheckUnknown(l - 1L, l2);
        }
        try {
            return new RangeInputStream(inputStream2, l - 1L, l2);
        }
        catch (IOException iOException) {
            throw DbException.getInvalidValueException("offset", l);
        }
    }

    static Reader rangeReader(Reader reader, long l, long l2, long l3) {
        if (l3 > 0L) {
            ValueLob.rangeCheck(l - 1L, l2, l3);
        } else {
            ValueLob.rangeCheckUnknown(l - 1L, l2);
        }
        try {
            return new RangeReader(reader, l - 1L, l2);
        }
        catch (IOException iOException) {
            throw DbException.getInvalidValueException("offset", l);
        }
    }

    ValueLob(LobData lobData, long l, long l2) {
        this.lobData = lobData;
        this.octetLength = l;
        this.charLength = l2;
    }

    static String createTempLobFileName(DataHandler dataHandler) throws IOException {
        String string = dataHandler.getDatabasePath();
        if (string.isEmpty()) {
            string = SysProperties.PREFIX_TEMP_FILE;
        }
        return FileUtils.createTempFile(string, ".temp.db", true);
    }

    static int getBufferSize(DataHandler dataHandler, long l) {
        if (l < 0L || l > Integer.MAX_VALUE) {
            l = Integer.MAX_VALUE;
        }
        int n = dataHandler.getMaxLengthInplaceLob();
        long l2 = 4096L;
        if (l2 < l && l2 <= (long)n) {
            l2 = Math.min(l, (long)n + 1L);
            l2 = MathUtils.roundUpLong(l2, 4096L);
        }
        l2 = Math.min(l, l2);
        if ((l2 = (long)MathUtils.convertLongToInt(l2)) < 0L) {
            l2 = Integer.MAX_VALUE;
        }
        return (int)l2;
    }

    public boolean isLinkedToTable() {
        return this.lobData.isLinkedToTable();
    }

    public void remove() {
        this.lobData.remove(this);
    }

    public abstract ValueLob copy(DataHandler var1, int var2);

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            int n;
            this.type = typeInfo = new TypeInfo(n, (n = this.getValueType()) == 3 ? this.charLength : this.octetLength, 0, null);
        }
        return typeInfo;
    }

    DbException getStringTooLong(long l) {
        return DbException.getValueTooLongException("CHARACTER VARYING", this.readString(81), l);
    }

    String readString(int n) {
        try {
            return IOUtils.readStringAndClose(this.getReader(), n);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.toString());
        }
    }

    @Override
    public Reader getReader() {
        return IOUtils.getReader(this.getInputStream());
    }

    @Override
    public byte[] getBytes() {
        if (this.lobData instanceof LobDataInMemory) {
            return Utils.cloneByteArray(this.getSmall());
        }
        return this.getBytesInternal();
    }

    @Override
    public byte[] getBytesNoCopy() {
        if (this.lobData instanceof LobDataInMemory) {
            return this.getSmall();
        }
        return this.getBytesInternal();
    }

    private byte[] getSmall() {
        byte[] byArray = ((LobDataInMemory)this.lobData).getSmall();
        int n = byArray.length;
        if (n > 1000000000) {
            throw DbException.getValueTooLongException("BINARY VARYING", StringUtils.convertBytesToHex(byArray, 41), n);
        }
        return byArray;
    }

    abstract byte[] getBytesInternal();

    DbException getBinaryTooLong(long l) {
        return DbException.getValueTooLongException("BINARY VARYING", StringUtils.convertBytesToHex(this.readBytes(41)), l);
    }

    byte[] readBytes(int n) {
        try {
            return IOUtils.readBytesAndClose(this.getInputStream(), n);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.toString());
        }
    }

    @Override
    public int hashCode() {
        if (this.hash == 0) {
            long l;
            int n = this.getValueType();
            long l2 = l = n == 3 ? this.charLength : this.octetLength;
            if (l > 4096L) {
                return (int)(l ^ l >>> 32);
            }
            this.hash = Utils.getByteArrayHash(this.getBytesNoCopy());
        }
        return this.hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ValueLob)) {
            return false;
        }
        ValueLob valueLob = (ValueLob)object;
        if (this.hashCode() != valueLob.hashCode()) {
            return false;
        }
        return this.compareTypeSafe((Value)object, null, null) == 0;
    }

    @Override
    public int getMemory() {
        return this.lobData.getMemory();
    }

    public LobData getLobData() {
        return this.lobData;
    }

    public ValueLob copyToResult() {
        LobStorageInterface lobStorageInterface;
        if (this.lobData instanceof LobDataDatabase && !(lobStorageInterface = this.lobData.getDataHandler().getLobStorage()).isReadOnly()) {
            return lobStorageInterface.copyLob(this, -3);
        }
        return this;
    }

    final void formatLobDataComment(StringBuilder stringBuilder) {
        if (this.lobData instanceof LobDataDatabase) {
            LobDataDatabase lobDataDatabase = (LobDataDatabase)this.lobData;
            stringBuilder.append(" /* table: ").append(lobDataDatabase.getTableId()).append(" id: ").append(lobDataDatabase.getLobId()).append(" */)");
        } else if (this.lobData instanceof LobDataFetchOnDemand) {
            LobDataFetchOnDemand lobDataFetchOnDemand = (LobDataFetchOnDemand)this.lobData;
            stringBuilder.append(" /* table: ").append(lobDataFetchOnDemand.getTableId()).append(" id: ").append(lobDataFetchOnDemand.getLobId()).append(" */)");
        } else {
            stringBuilder.append(" /* ").append(this.lobData.toString().replaceAll("\\*/", "\\\\*\\\\/")).append(" */");
        }
    }
}

