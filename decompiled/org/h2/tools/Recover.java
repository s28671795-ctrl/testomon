/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.h2.engine.MetaRecord;
import org.h2.message.DbException;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreTool;
import org.h2.mvstore.StreamStore;
import org.h2.mvstore.db.LobStorageMap;
import org.h2.mvstore.db.ValueDataType;
import org.h2.mvstore.tx.TransactionMap;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.MetaType;
import org.h2.mvstore.type.StringDataType;
import org.h2.result.Row;
import org.h2.store.DataHandler;
import org.h2.store.FileLister;
import org.h2.store.FileStore;
import org.h2.store.LobStorageInterface;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.util.SmallLRUCache;
import org.h2.util.StringUtils;
import org.h2.util.TempFileDeleter;
import org.h2.util.Tool;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueCollectionBase;
import org.h2.value.ValueLob;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;

public class Recover
extends Tool
implements DataHandler {
    private String databaseName;
    private int storageId;
    private String storageName;
    private int recordLength;
    private int valueId;
    private boolean trace;
    private ArrayList<MetaRecord> schema;
    private HashSet<Integer> objectIdSet;
    private HashMap<Integer, String> tableMap;
    private HashMap<String, String> columnTypeMap;
    private boolean lobMaps;

    public static void main(String ... stringArray) throws SQLException {
        new Recover().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = ".";
        String string2 = null;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string3 = stringArray[i];
            if ("-dir".equals(string3)) {
                string = stringArray[++i];
                continue;
            }
            if ("-db".equals(string3)) {
                string2 = stringArray[++i];
                continue;
            }
            if ("-trace".equals(string3)) {
                this.trace = true;
                continue;
            }
            if (string3.equals("-help") || string3.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string3);
        }
        this.process(string, string2);
    }

    public static InputStream readBlobMap(Connection connection, long l, long l2) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT DATA FROM INFORMATION_SCHEMA.LOB_BLOCKS WHERE LOB_ID = ? AND SEQ = ? AND ? > 0");
        preparedStatement.setLong(1, l);
        preparedStatement.setLong(3, l2);
        return new SequenceInputStream((Enumeration<? extends InputStream>)new Enumeration<InputStream>(){
            private int seq;
            private byte[] data = this.fetch();

            private byte[] fetch() {
                try {
                    preparedStatement.setInt(2, this.seq++);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getBytes(1);
                    }
                    return null;
                }
                catch (SQLException sQLException) {
                    throw DbException.convert(sQLException);
                }
            }

            @Override
            public boolean hasMoreElements() {
                return this.data != null;
            }

            @Override
            public InputStream nextElement() {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.data);
                this.data = this.fetch();
                return byteArrayInputStream;
            }
        });
    }

    public static Reader readClobMap(Connection connection, long l, long l2) throws Exception {
        InputStream inputStream2 = Recover.readBlobMap(connection, l, l2);
        return new BufferedReader(new InputStreamReader(inputStream2, StandardCharsets.UTF_8));
    }

    private void trace(String string) {
        if (this.trace) {
            this.out.println(string);
        }
    }

    private void traceError(String string, Throwable throwable) {
        this.out.println(string + ": " + throwable.toString());
        if (this.trace) {
            throwable.printStackTrace(this.out);
        }
    }

    public static void execute(String string, String string2) throws SQLException {
        try {
            new Recover().process(string, string2);
        }
        catch (DbException dbException) {
            throw DbException.toSQLException(dbException);
        }
    }

    private void process(String string, String string2) {
        ArrayList<String> arrayList = FileLister.getDatabaseFiles(string, string2, true);
        if (arrayList.isEmpty()) {
            this.printNoDatabaseFilesFound(string, string2);
        }
        for (String string3 : arrayList) {
            if (!string3.endsWith(".mv.db")) continue;
            String string4 = string3.substring(0, string3.length() - ".mv.db".length());
            try (PrintWriter printWriter = this.getWriter(string3, ".txt");){
                MVStoreTool.dump(string3, printWriter, true);
                MVStoreTool.info(string3, printWriter);
            }
            printWriter = this.getWriter(string4 + ".h2.db", ".sql");
            var8_8 = null;
            try {
                this.dumpMVStoreFile(printWriter, string3);
            }
            catch (Throwable throwable) {
                var8_8 = throwable;
                throw throwable;
            }
            finally {
                if (printWriter == null) continue;
                if (var8_8 != null) {
                    try {
                        printWriter.close();
                    }
                    catch (Throwable throwable) {
                        var8_8.addSuppressed(throwable);
                    }
                    continue;
                }
                printWriter.close();
            }
        }
    }

    private PrintWriter getWriter(String string, String string2) {
        string = string.substring(0, string.length() - 3);
        String string3 = string + string2;
        this.trace("Created file: " + string3);
        try {
            return new PrintWriter(IOUtils.getBufferedWriter(FileUtils.newOutputStream(string3, false)));
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    private void getSQL(StringBuilder stringBuilder, String string, Value value) {
        ValueLob valueLob;
        LobData lobData;
        if (value instanceof ValueLob && (lobData = (valueLob = (ValueLob)value).getLobData()) instanceof LobDataDatabase) {
            String string2;
            long l;
            LobDataDatabase lobDataDatabase = (LobDataDatabase)lobData;
            int n = value.getValueType();
            long l2 = lobDataDatabase.getLobId();
            if (n == 7) {
                l = valueLob.octetLength();
                string2 = "BLOB";
                stringBuilder.append("READ_BLOB");
            } else {
                l = valueLob.charLength();
                string2 = "CLOB";
                stringBuilder.append("READ_CLOB");
            }
            if (this.lobMaps) {
                stringBuilder.append("_MAP");
            } else {
                stringBuilder.append("_DB");
            }
            this.columnTypeMap.put(string, string2);
            stringBuilder.append('(').append(l2).append(", ").append(l).append(')');
            return;
        }
        value.getSQL(stringBuilder, 4);
    }

    private void setDatabaseName(String string) {
        this.databaseName = string;
    }

    private void dumpMVStoreFile(PrintWriter printWriter, String string) {
        printWriter.println("-- MVStore");
        String string2 = this.getClass().getName();
        printWriter.println("CREATE ALIAS IF NOT EXISTS READ_BLOB_MAP FOR '" + string2 + ".readBlobMap';");
        printWriter.println("CREATE ALIAS IF NOT EXISTS READ_CLOB_MAP FOR '" + string2 + ".readClobMap';");
        this.resetSchema();
        this.setDatabaseName(string.substring(0, string.length() - ".mv.db".length()));
        try (MVStore mVStore = new MVStore.Builder().fileName(string).recoveryMode().readOnly().open();){
            Row row;
            Iterator<Object> iterator2;
            TransactionMap transactionMap;
            String string3;
            this.dumpLobMaps(printWriter, mVStore);
            printWriter.println("-- Layout");
            Recover.dumpLayout(printWriter, mVStore);
            printWriter.println("-- Meta");
            Recover.dumpMeta(printWriter, mVStore);
            printWriter.println("-- Types");
            Recover.dumpTypes(printWriter, mVStore);
            printWriter.println("-- Tables");
            TransactionStore transactionStore = new TransactionStore(mVStore, new ValueDataType());
            try {
                transactionStore.init();
            }
            catch (Throwable throwable) {
                this.writeError(printWriter, throwable);
            }
            for (String string4 : mVStore.getMapNames()) {
                if (!string4.startsWith("table.") || Integer.parseInt(string3 = string4.substring("table.".length())) != 0) continue;
                transactionMap = transactionStore.begin().openMap(string4);
                iterator2 = transactionMap.keyIterator(null);
                while (iterator2.hasNext()) {
                    Long l = iterator2.next();
                    row = (Row)transactionMap.get(l);
                    try {
                        this.writeMetaRow(row);
                    }
                    catch (Throwable throwable) {
                        this.writeError(printWriter, throwable);
                    }
                }
            }
            this.writeSchemaSET(printWriter);
            printWriter.println("---- Table Data ----");
            for (String string4 : mVStore.getMapNames()) {
                if (!string4.startsWith("table.") || Integer.parseInt(string3 = string4.substring("table.".length())) == 0) continue;
                transactionMap = transactionStore.begin().openMap(string4);
                iterator2 = transactionMap.keyIterator(null);
                boolean bl = false;
                while (iterator2.hasNext()) {
                    String string5;
                    StringBuilder stringBuilder;
                    Value[] valueArray;
                    row = iterator2.next();
                    Object v = transactionMap.get(row);
                    if (v instanceof Row) {
                        valueArray = ((Row)v).getValueList();
                        this.recordLength = valueArray.length;
                    } else {
                        valueArray = ((ValueCollectionBase)v).getList();
                        this.recordLength = valueArray.length - 1;
                    }
                    if (!bl) {
                        this.setStorage(Integer.parseInt(string3));
                        stringBuilder = new StringBuilder();
                        this.valueId = 0;
                        while (this.valueId < this.recordLength) {
                            string5 = this.storageName + "." + this.valueId;
                            stringBuilder.setLength(0);
                            this.getSQL(stringBuilder, string5, valueArray[this.valueId]);
                            ++this.valueId;
                        }
                        this.createTemporaryTable(printWriter);
                        bl = true;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("INSERT INTO O_").append(string3).append(" VALUES(");
                    this.valueId = 0;
                    while (this.valueId < this.recordLength) {
                        if (this.valueId > 0) {
                            stringBuilder.append(", ");
                        }
                        string5 = this.storageName + "." + this.valueId;
                        this.getSQL(stringBuilder, string5, valueArray[this.valueId]);
                        ++this.valueId;
                    }
                    stringBuilder.append(");");
                    printWriter.println(stringBuilder.toString());
                }
            }
            this.writeSchema(printWriter);
            printWriter.println("DROP ALIAS READ_BLOB_MAP;");
            printWriter.println("DROP ALIAS READ_CLOB_MAP;");
            printWriter.println("DROP TABLE IF EXISTS INFORMATION_SCHEMA.LOB_BLOCKS;");
        }
        catch (Throwable throwable) {
            this.writeError(printWriter, throwable);
        }
    }

    private static void dumpLayout(PrintWriter printWriter, MVStore mVStore) {
        Map<String, String> map = mVStore.getLayoutMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            printWriter.println("-- " + entry.getKey() + " = " + entry.getValue());
        }
    }

    private static void dumpMeta(PrintWriter printWriter, MVStore mVStore) {
        MVMap<String, String> mVMap = mVStore.getMetaMap();
        for (Map.Entry<String, String> entry : mVMap.entrySet()) {
            printWriter.println("-- " + entry.getKey() + " = " + entry.getValue());
        }
    }

    private static void dumpTypes(PrintWriter printWriter, MVStore mVStore) {
        MVMap.BasicBuilder basicBuilder = ((MVMap.Builder)new MVMap.Builder().keyType((DataType)StringDataType.INSTANCE)).valueType(new MetaType<Object>(null, null));
        Object m4 = mVStore.openMap("_", basicBuilder);
        for (Map.Entry entry : ((MVMap)m4).entrySet()) {
            printWriter.println("-- " + (String)entry.getKey() + " = " + entry.getValue());
        }
    }

    private void dumpLobMaps(PrintWriter printWriter, MVStore mVStore) {
        this.lobMaps = mVStore.hasMap("lobData");
        if (!this.lobMaps) {
            return;
        }
        TransactionStore transactionStore = new TransactionStore(mVStore);
        MVMap<Long, byte[]> mVMap = LobStorageMap.openLobDataMap(transactionStore);
        StreamStore streamStore = new StreamStore(mVMap);
        MVMap<Long, LobStorageMap.BlobMeta> mVMap2 = LobStorageMap.openLobMap(transactionStore);
        printWriter.println("-- LOB");
        printWriter.println("CREATE TABLE IF NOT EXISTS INFORMATION_SCHEMA.LOB_BLOCKS(LOB_ID BIGINT, SEQ INT, DATA VARBINARY, PRIMARY KEY(LOB_ID, SEQ));");
        boolean bl = false;
        block2: for (Map.Entry<Long, LobStorageMap.BlobMeta> object : mVMap2.entrySet()) {
            long l = object.getKey();
            LobStorageMap.BlobMeta blobMeta = object.getValue();
            byte[] byArray = blobMeta.streamStoreId;
            InputStream inputStream2 = streamStore.get(byArray);
            int n = 8192;
            byte[] byArray2 = new byte[n];
            try {
                int n2 = 0;
                while (true) {
                    int n3;
                    if ((n3 = IOUtils.readFully(inputStream2, byArray2, byArray2.length)) > 0) {
                        printWriter.print("INSERT INTO INFORMATION_SCHEMA.LOB_BLOCKS VALUES(" + l + ", " + n2 + ", X'");
                        printWriter.print(StringUtils.convertBytesToHex(byArray2, n3));
                        printWriter.println("');");
                    }
                    if (n3 != n) continue block2;
                    ++n2;
                }
            }
            catch (IOException iOException) {
                this.writeError(printWriter, iOException);
                bl = true;
            }
        }
        printWriter.println("-- lobMap.size: " + mVMap2.sizeAsLong());
        printWriter.println("-- lobData.size: " + mVMap.sizeAsLong());
        if (bl) {
            printWriter.println("-- lobMap");
            for (Long l : mVMap2.keyList()) {
                LobStorageMap.BlobMeta blobMeta = mVMap2.get(l);
                byte[] byArray = blobMeta.streamStoreId;
                printWriter.println("--     " + l + " " + StreamStore.toString(byArray));
            }
            printWriter.println("-- lobData");
            for (Long l : mVMap.keyList()) {
                printWriter.println("--     " + l + " len " + mVMap.get(l).length);
            }
        }
    }

    private String setStorage(int n) {
        this.storageId = n;
        this.storageName = "O_" + Integer.toString(n).replace('-', 'M');
        return this.storageName;
    }

    private void writeMetaRow(Row row) {
        MetaRecord metaRecord = new MetaRecord(row);
        int n = metaRecord.getObjectType();
        if (n == 1 && metaRecord.getSQL().startsWith("CREATE PRIMARY KEY ")) {
            return;
        }
        this.schema.add(metaRecord);
        if (n == 0) {
            this.tableMap.put(metaRecord.getId(), Recover.extractTableOrViewName(metaRecord.getSQL()));
        }
    }

    private void resetSchema() {
        this.schema = new ArrayList();
        this.objectIdSet = new HashSet();
        this.tableMap = new HashMap();
        this.columnTypeMap = new HashMap();
    }

    private void writeSchemaSET(PrintWriter printWriter) {
        printWriter.println("---- Schema SET ----");
        for (MetaRecord metaRecord : this.schema) {
            if (metaRecord.getObjectType() != 6) continue;
            String string = metaRecord.getSQL();
            printWriter.println(string + ";");
        }
    }

    private void writeSchema(PrintWriter printWriter) {
        String string;
        printWriter.println("---- Schema ----");
        Collections.sort(this.schema);
        for (MetaRecord object22 : this.schema) {
            if (object22.getObjectType() == 6 || Recover.isSchemaObjectTypeDelayed(object22)) continue;
            String string2 = object22.getSQL();
            printWriter.println(string2 + ";");
        }
        boolean bl = false;
        for (Map.Entry<Integer, String> entry : this.tableMap.entrySet()) {
            Integer n = entry.getKey();
            string = entry.getValue();
            if (!this.objectIdSet.contains(n) || !Recover.isLobTable(string)) continue;
            this.setStorage(n);
            printWriter.println("DELETE FROM " + string + ";");
            printWriter.println("INSERT INTO " + string + " SELECT * FROM " + this.storageName + ";");
            if (!string.equals("INFORMATION_SCHEMA.LOBS") && !string.equalsIgnoreCase("\"INFORMATION_SCHEMA\".\"LOBS\"")) continue;
            printWriter.println("UPDATE " + string + " SET `TABLE` = " + -2 + ";");
            bl = true;
        }
        for (Map.Entry<Integer, String> entry : this.tableMap.entrySet()) {
            Integer n = entry.getKey();
            string = entry.getValue();
            if (!this.objectIdSet.contains(n)) continue;
            this.setStorage(n);
            if (Recover.isLobTable(string)) continue;
            printWriter.println("INSERT INTO " + string + " SELECT * FROM " + this.storageName + ";");
        }
        for (Integer n : this.objectIdSet) {
            this.setStorage(n);
            printWriter.println("DROP TABLE " + this.storageName + ";");
        }
        if (bl) {
            printWriter.println("DELETE FROM INFORMATION_SCHEMA.LOBS WHERE `TABLE` = -2;");
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (MetaRecord metaRecord : this.schema) {
            if (!Recover.isSchemaObjectTypeDelayed(metaRecord)) continue;
            string = metaRecord.getSQL();
            if (metaRecord.getObjectType() == 5 && string.endsWith("NOCHECK") && string.contains(" FOREIGN KEY") && string.contains("REFERENCES ")) {
                arrayList.add(string);
                continue;
            }
            printWriter.println(string + ';');
        }
        for (String string3 : arrayList) {
            printWriter.println(string3 + ';');
        }
    }

    private static boolean isLobTable(String string) {
        return string.startsWith("INFORMATION_SCHEMA.LOB") || string.startsWith("\"INFORMATION_SCHEMA\".\"LOB") || string.startsWith("\"information_schema\".\"lob");
    }

    private static boolean isSchemaObjectTypeDelayed(MetaRecord metaRecord) {
        switch (metaRecord.getObjectType()) {
            case 1: 
            case 4: 
            case 5: {
                return true;
            }
        }
        return false;
    }

    private void createTemporaryTable(PrintWriter printWriter) {
        if (!this.objectIdSet.contains(this.storageId)) {
            this.objectIdSet.add(this.storageId);
            printWriter.write("CREATE TABLE ");
            printWriter.write(this.storageName);
            printWriter.write(40);
            for (int i = 0; i < this.recordLength; ++i) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                printWriter.write(67);
                printWriter.print(i);
                printWriter.write(32);
                String string = this.columnTypeMap.get(this.storageName + "." + i);
                printWriter.write(string == null ? "VARCHAR" : string);
            }
            printWriter.println(");");
            printWriter.flush();
        }
    }

    private static String extractTableOrViewName(String string) {
        int n = string.indexOf(" TABLE ");
        int n2 = string.indexOf(" VIEW ");
        if (n > 0 && n2 > 0) {
            if (n < n2) {
                n2 = -1;
            } else {
                n = -1;
            }
        }
        if (n2 > 0) {
            string = string.substring(n2 + " VIEW ".length());
        } else if (n > 0) {
            string = string.substring(n + " TABLE ".length());
        } else {
            return "UNKNOWN";
        }
        if (string.startsWith("IF NOT EXISTS ")) {
            string = string.substring("IF NOT EXISTS ".length());
        }
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\"') {
                bl = !bl;
                continue;
            }
            if (bl || c > ' ' && c != '(') continue;
            string = string.substring(0, i);
            return string;
        }
        return "UNKNOWN";
    }

    private void writeError(PrintWriter printWriter, Throwable throwable) {
        if (printWriter != null) {
            printWriter.println("// error: " + throwable);
        }
        this.traceError("Error", throwable);
    }

    @Override
    public String getDatabasePath() {
        return this.databaseName;
    }

    @Override
    public FileStore openFile(String string, String string2, boolean bl) {
        return FileStore.open(this, string, "rw");
    }

    @Override
    public void checkPowerOff() {
    }

    @Override
    public void checkWritingAllowed() {
    }

    @Override
    public int getMaxLengthInplaceLob() {
        throw DbException.getInternalError();
    }

    @Override
    public Object getLobSyncObject() {
        return this;
    }

    @Override
    public SmallLRUCache<String, String[]> getLobFileListCache() {
        return null;
    }

    @Override
    public TempFileDeleter getTempFileDeleter() {
        return TempFileDeleter.getInstance();
    }

    @Override
    public LobStorageInterface getLobStorage() {
        return null;
    }

    @Override
    public int readLob(long l, byte[] byArray, long l2, byte[] byArray2, int n, int n2) {
        throw DbException.getInternalError();
    }

    @Override
    public CompareMode getCompareMode() {
        return CompareMode.getInstance(null, 0);
    }
}

