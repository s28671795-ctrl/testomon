/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.h2.command.Prepared;
import org.h2.engine.NullsDistinct;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.LinkedIndex;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableLinkConnection;
import org.h2.table.TableType;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class TableLink
extends Table {
    private static final int MAX_RETRY = 2;
    private static final long ROW_COUNT_APPROXIMATION = 100000L;
    private final String originalSchema;
    private String driver;
    private String url;
    private String user;
    private String password;
    private String originalTable;
    private String qualifiedTableName;
    private TableLinkConnection conn;
    private HashMap<String, PreparedStatement> preparedMap = new HashMap();
    private final ArrayList<Index> indexes = Utils.newSmallArrayList();
    private final boolean emitUpdates;
    private LinkedIndex linkedIndex;
    private DbException connectException;
    private boolean storesLowerCase;
    private boolean storesMixedCase;
    private boolean storesMixedCaseQuoted;
    private boolean supportsMixedCaseIdentifiers;
    private String identifierQuoteString;
    private boolean globalTemporary;
    private boolean readOnly;
    private final boolean targetsMySql;
    private int fetchSize = 0;
    private boolean autocommit = true;

    public TableLink(Schema schema, int n, String string, String string2, String string3, String string4, String string5, String string6, String string7, boolean bl, boolean bl2) {
        super(schema, n, string, false, true);
        this.driver = string2;
        this.url = string3;
        this.user = string4;
        this.password = string5;
        this.originalSchema = string6;
        this.originalTable = string7;
        this.emitUpdates = bl;
        this.targetsMySql = TableLink.isMySqlUrl(this.url);
        try {
            this.connect();
        }
        catch (DbException dbException) {
            if (!bl2) {
                throw dbException;
            }
            Column[] columnArray = new Column[]{};
            this.setColumns(columnArray);
            this.linkedIndex = new LinkedIndex(this, n, IndexColumn.wrap(columnArray), 0, IndexType.createNonUnique(false));
            this.indexes.add(this.linkedIndex);
        }
    }

    private void connect() {
        this.connectException = null;
        int n = 0;
        while (true) {
            try {
                this.conn = this.database.getLinkConnection(this.driver, this.url, this.user, this.password);
                this.conn.setAutoCommit(this.autocommit);
                TableLinkConnection tableLinkConnection = this.conn;
                synchronized (tableLinkConnection) {
                    try {
                        this.readMetaData();
                        return;
                    }
                    catch (Exception exception) {
                        this.conn.close(true);
                        this.conn = null;
                        throw DbException.convert(exception);
                    }
                }
            }
            catch (DbException dbException) {
                if (n >= 2) {
                    this.connectException = dbException;
                    throw dbException;
                }
                ++n;
                continue;
            }
            break;
        }
    }

    private void readMetaData() throws SQLException {
        Column column;
        int n;
        int n2;
        long l;
        Object object;
        Object object2;
        Object object3;
        Throwable throwable;
        AutoCloseable autoCloseable;
        DatabaseMetaData databaseMetaData = this.conn.getConnection().getMetaData();
        this.storesLowerCase = databaseMetaData.storesLowerCaseIdentifiers();
        this.storesMixedCase = databaseMetaData.storesMixedCaseIdentifiers();
        this.storesMixedCaseQuoted = databaseMetaData.storesMixedCaseQuotedIdentifiers();
        this.supportsMixedCaseIdentifiers = databaseMetaData.supportsMixedCaseIdentifiers();
        this.identifierQuoteString = databaseMetaData.getIdentifierQuoteString();
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        HashMap<String, Column> hashMap = new HashMap<String, Column>();
        String string = null;
        boolean bl = this.originalTable.startsWith("(");
        if (!bl) {
            autoCloseable = databaseMetaData.getTables(null, this.originalSchema, this.originalTable, null);
            throwable = null;
            try {
                if (autoCloseable.next() && autoCloseable.next()) {
                    throw DbException.get(90080, this.originalTable);
                }
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (autoCloseable != null) {
                    if (throwable != null) {
                        try {
                            autoCloseable.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        autoCloseable.close();
                    }
                }
            }
            autoCloseable = databaseMetaData.getColumns(null, this.originalSchema, this.originalTable, null);
            throwable = null;
            try {
                int n3 = 0;
                object3 = null;
                while (autoCloseable.next()) {
                    object2 = autoCloseable.getString("TABLE_CAT");
                    if (object3 == null) {
                        object3 = object2;
                    }
                    String string2 = autoCloseable.getString("TABLE_SCHEM");
                    if (string == null) {
                        string = string2;
                    }
                    if (!Objects.equals(object3, object2) || !Objects.equals(string, string2)) {
                        hashMap.clear();
                        arrayList.clear();
                        break;
                    }
                    String string3 = autoCloseable.getString("COLUMN_NAME");
                    string3 = this.convertColumnName(string3);
                    int n4 = autoCloseable.getInt("DATA_TYPE");
                    object = autoCloseable.getString("TYPE_NAME");
                    l = autoCloseable.getInt("COLUMN_SIZE");
                    l = TableLink.convertPrecision(n4, l);
                    n2 = autoCloseable.getInt("DECIMAL_DIGITS");
                    n2 = TableLink.convertScale(n4, n2);
                    n = DataType.convertSQLTypeToValueType(n4, (String)object);
                    column = new Column(string3, TypeInfo.getTypeInfo(n, l, n2, null), this, n3++);
                    arrayList.add(column);
                    hashMap.put(string3, column);
                }
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (autoCloseable != null) {
                    if (throwable != null) {
                        try {
                            autoCloseable.close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        autoCloseable.close();
                    }
                }
            }
        }
        this.qualifiedTableName = this.originalTable.indexOf(46) < 0 && !StringUtils.isNullOrEmpty(string) ? string + '.' + this.originalTable : this.originalTable;
        try {
            autoCloseable = this.conn.getConnection().createStatement();
            throwable = null;
            try {
                ResultSet resultSet = autoCloseable.executeQuery("SELECT * FROM " + this.qualifiedTableName + " T WHERE 1=0");
                object3 = null;
                try {
                    if (resultSet instanceof JdbcResultSet) {
                        object2 = ((JdbcResultSet)resultSet).getResult();
                        arrayList.clear();
                        hashMap.clear();
                        int n5 = 0;
                        int n6 = object2.getVisibleColumnCount();
                        while (n5 < n6) {
                            String string4 = object2.getColumnName(n5);
                            object = new Column(string4, object2.getColumnType(n5), this, ++n5);
                            arrayList.add((Column)object);
                            hashMap.put(string4, (Column)object);
                        }
                    } else if (arrayList.isEmpty()) {
                        object2 = resultSet.getMetaData();
                        int n7 = 0;
                        int n8 = object2.getColumnCount();
                        while (n7 < n8) {
                            String string5 = object2.getColumnName(n7 + 1);
                            string5 = this.convertColumnName(string5);
                            int n9 = object2.getColumnType(n7 + 1);
                            l = object2.getPrecision(n7 + 1);
                            l = TableLink.convertPrecision(n9, l);
                            n2 = object2.getScale(n7 + 1);
                            n2 = TableLink.convertScale(n9, n2);
                            n = DataType.getValueTypeFromResultSet((ResultSetMetaData)object2, n7 + 1);
                            column = new Column(string5, TypeInfo.getTypeInfo(n, l, n2, null), this, n7++);
                            arrayList.add(column);
                            hashMap.put(string5, column);
                        }
                    }
                }
                catch (Throwable throwable6) {
                    object3 = throwable6;
                    throw throwable6;
                }
                finally {
                    if (resultSet != null) {
                        if (object3 != null) {
                            try {
                                resultSet.close();
                            }
                            catch (Throwable throwable7) {
                                ((Throwable)object3).addSuppressed(throwable7);
                            }
                        } else {
                            resultSet.close();
                        }
                    }
                }
            }
            catch (Throwable throwable8) {
                throwable = throwable8;
                throw throwable8;
            }
            finally {
                if (autoCloseable != null) {
                    if (throwable != null) {
                        try {
                            autoCloseable.close();
                        }
                        catch (Throwable throwable9) {
                            throwable.addSuppressed(throwable9);
                        }
                    } else {
                        autoCloseable.close();
                    }
                }
            }
        }
        catch (Exception exception) {
            throw DbException.get(42102, exception, this.originalTable + '(' + exception + ')');
        }
        autoCloseable = arrayList.toArray(new Column[0]);
        this.setColumns((Column[])autoCloseable);
        int n10 = this.getId();
        this.linkedIndex = new LinkedIndex(this, n10, IndexColumn.wrap((Column[])autoCloseable), 0, IndexType.createNonUnique(false));
        this.indexes.add(this.linkedIndex);
        if (!bl) {
            this.readIndexes(databaseMetaData, hashMap);
        }
    }

    private void readIndexes(DatabaseMetaData databaseMetaData, HashMap<String, Column> hashMap) {
        Throwable throwable;
        ResultSet resultSet;
        String string = null;
        try {
            resultSet = databaseMetaData.getPrimaryKeys(null, this.originalSchema, this.originalTable);
            throwable = null;
            try {
                if (resultSet.next()) {
                    string = this.readPrimaryKey(resultSet, hashMap);
                }
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (resultSet != null) {
                    if (throwable != null) {
                        try {
                            resultSet.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        resultSet.close();
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            resultSet = databaseMetaData.getIndexInfo(null, this.originalSchema, this.originalTable, false, true);
            throwable = null;
            try {
                this.readIndexes(resultSet, hashMap, string);
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (resultSet != null) {
                    if (throwable != null) {
                        try {
                            resultSet.close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        resultSet.close();
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private String readPrimaryKey(ResultSet resultSet, HashMap<String, Column> hashMap) throws SQLException {
        String string = null;
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        do {
            int n = resultSet.getInt("KEY_SEQ");
            if (StringUtils.isNullOrEmpty(string)) {
                string = resultSet.getString("PK_NAME");
            }
            while (arrayList.size() < n) {
                arrayList.add(null);
            }
            String string2 = resultSet.getString("COLUMN_NAME");
            string2 = this.convertColumnName(string2);
            Column column = hashMap.get(string2);
            if (n == 0) {
                arrayList.add(column);
                continue;
            }
            arrayList.set(n - 1, column);
        } while (resultSet.next());
        this.addIndex(arrayList, arrayList.size(), IndexType.createPrimaryKey(false, false));
        return string;
    }

    private void readIndexes(ResultSet resultSet, HashMap<String, Column> hashMap, String string) throws SQLException {
        String string2 = null;
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        int n = 0;
        IndexType indexType = null;
        while (resultSet.next()) {
            if (resultSet.getShort("TYPE") == 0) continue;
            String string3 = resultSet.getString("INDEX_NAME");
            if (string != null && string.equals(string3)) continue;
            if (string2 != null && !string2.equals(string3)) {
                this.addIndex(arrayList, n, indexType);
                n = 0;
                string2 = null;
            }
            if (string2 == null) {
                string2 = string3;
                arrayList.clear();
            }
            if (!resultSet.getBoolean("NON_UNIQUE")) {
                ++n;
            }
            indexType = n > 0 ? IndexType.createUnique(false, false, n, NullsDistinct.NOT_DISTINCT) : IndexType.createNonUnique(false);
            String string4 = resultSet.getString("COLUMN_NAME");
            string4 = this.convertColumnName(string4);
            Column column = hashMap.get(string4);
            arrayList.add(column);
        }
        if (string2 != null) {
            this.addIndex(arrayList, n, indexType);
        }
    }

    private static long convertPrecision(int n, long l) {
        switch (n) {
            case 2: 
            case 3: {
                if (l != 0L) break;
                l = 65535L;
                break;
            }
            case 91: {
                l = Math.max(10L, l);
                break;
            }
            case 93: {
                l = Math.max(29L, l);
                break;
            }
            case 92: {
                l = Math.max(18L, l);
            }
        }
        return l;
    }

    private static int convertScale(int n, int n2) {
        switch (n) {
            case 2: 
            case 3: {
                if (n2 >= 0) break;
                n2 = Short.MAX_VALUE;
            }
        }
        return n2;
    }

    private String convertColumnName(String string) {
        if (this.targetsMySql) {
            string = StringUtils.toUpperEnglish(string);
        } else if ((this.storesMixedCase || this.storesLowerCase) && string.equals(StringUtils.toLowerEnglish(string))) {
            string = StringUtils.toUpperEnglish(string);
        } else if (this.storesMixedCase && !this.supportsMixedCaseIdentifiers) {
            string = StringUtils.toUpperEnglish(string);
        } else if (this.storesMixedCase && this.storesMixedCaseQuoted) {
            string = StringUtils.toUpperEnglish(string);
        }
        return string;
    }

    private void addIndex(List<Column> list, int n, IndexType indexType) {
        int n2 = list.indexOf(null);
        if (n2 == 0) {
            this.trace.info("Omitting linked index - no recognized columns.");
            return;
        }
        if (n2 > 0) {
            this.trace.info("Unrecognized columns in linked index. Registering the index against the leading {0} recognized columns of {1} total columns.", n2, list.size());
            list = list.subList(0, n2);
        }
        Column[] columnArray = list.toArray(new Column[0]);
        LinkedIndex linkedIndex = new LinkedIndex(this, 0, IndexColumn.wrap(columnArray), n, indexType);
        this.indexes.add(linkedIndex);
    }

    @Override
    public String getDropSQL() {
        StringBuilder stringBuilder = new StringBuilder("DROP TABLE IF EXISTS ");
        return this.getSQL(stringBuilder, 0).toString();
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("CREATE FORCE ");
        if (this.isTemporary()) {
            if (this.globalTemporary) {
                stringBuilder.append("GLOBAL ");
            } else {
                stringBuilder.append("LOCAL ");
            }
            stringBuilder.append("TEMPORARY ");
        }
        stringBuilder.append("LINKED TABLE ");
        this.getSQL(stringBuilder, 0);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        stringBuilder.append('(');
        StringUtils.quoteStringSQL(stringBuilder, this.driver).append(", ");
        StringUtils.quoteStringSQL(stringBuilder, this.url).append(", ");
        StringUtils.quoteStringSQL(stringBuilder, this.user).append(", ");
        StringUtils.quoteStringSQL(stringBuilder, this.password).append(", ");
        StringUtils.quoteStringSQL(stringBuilder, this.originalTable).append(')');
        if (this.emitUpdates) {
            stringBuilder.append(" EMIT UPDATES");
        }
        if (this.readOnly) {
            stringBuilder.append(" READONLY");
        }
        if (this.fetchSize != 0) {
            stringBuilder.append(" FETCH_SIZE ").append(this.fetchSize);
        }
        if (!this.autocommit) {
            stringBuilder.append(" AUTOCOMMIT OFF");
        }
        stringBuilder.append(" /*").append("--hide--").append("*/");
        return stringBuilder.toString();
    }

    @Override
    public Index addIndex(SessionLocal sessionLocal, String string, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType, boolean bl, String string2) {
        throw DbException.getUnsupportedException("LINK");
    }

    @Override
    public Index getScanIndex(SessionLocal sessionLocal) {
        return this.linkedIndex;
    }

    @Override
    public boolean isInsertable() {
        return !this.readOnly;
    }

    private void checkReadOnly() {
        if (this.readOnly) {
            throw DbException.get(90097);
        }
    }

    @Override
    public void removeRow(SessionLocal sessionLocal, Row row) {
        this.checkReadOnly();
        this.getScanIndex(sessionLocal).remove(sessionLocal, row);
    }

    @Override
    public void addRow(SessionLocal sessionLocal, Row row) {
        this.checkReadOnly();
        this.getScanIndex(sessionLocal).add(sessionLocal, row);
    }

    @Override
    public void close(SessionLocal sessionLocal) {
        if (this.conn != null) {
            try {
                this.conn.close(false);
            }
            finally {
                this.conn = null;
            }
        }
    }

    @Override
    public synchronized long getRowCount(SessionLocal sessionLocal) {
        String string = "SELECT COUNT(*) FROM " + this.qualifiedTableName + " T";
        try {
            PreparedStatement preparedStatement = this.execute(string, null, false, sessionLocal);
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            long l = resultSet.getLong(1);
            resultSet.close();
            this.reusePreparedStatement(preparedStatement, string);
            return l;
        }
        catch (Exception exception) {
            throw TableLink.wrapException(string, exception);
        }
    }

    public static DbException wrapException(String string, Exception exception) {
        SQLException sQLException = DbException.toSQLException(exception);
        return DbException.get(90111, sQLException, string, sQLException.toString());
    }

    public String getQualifiedTable() {
        return this.qualifiedTableName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PreparedStatement execute(String string, ArrayList<Value> arrayList, boolean bl, SessionLocal sessionLocal) {
        if (this.conn == null) {
            throw this.connectException;
        }
        int n = 0;
        while (true) {
            try {
                TableLinkConnection tableLinkConnection = this.conn;
                synchronized (tableLinkConnection) {
                    Value value;
                    int n2;
                    int n3;
                    Object object;
                    PreparedStatement preparedStatement = this.preparedMap.remove(string);
                    if (preparedStatement == null) {
                        preparedStatement = this.conn.getConnection().prepareStatement(string);
                        if (this.fetchSize != 0) {
                            preparedStatement.setFetchSize(this.fetchSize);
                        }
                    }
                    if (this.trace.isDebugEnabled()) {
                        object = new StringBuilder(this.getName()).append(":\n").append(string);
                        if (arrayList != null && !arrayList.isEmpty()) {
                            ((StringBuilder)object).append(" {");
                            n3 = 0;
                            n2 = arrayList.size();
                            while (n3 < n2) {
                                value = arrayList.get(n3);
                                if (n3 > 0) {
                                    ((StringBuilder)object).append(", ");
                                }
                                ((StringBuilder)object).append(++n3).append(": ");
                                value.getSQL((StringBuilder)object, 0);
                            }
                            ((StringBuilder)object).append('}');
                        }
                        ((StringBuilder)object).append(';');
                        this.trace.debug(((StringBuilder)object).toString());
                    }
                    if (arrayList != null) {
                        object = sessionLocal.createConnection(false);
                        n2 = arrayList.size();
                        for (n3 = 0; n3 < n2; ++n3) {
                            value = arrayList.get(n3);
                            JdbcUtils.set(preparedStatement, n3 + 1, value, (JdbcConnection)object);
                        }
                    }
                    preparedStatement.execute();
                    if (bl) {
                        this.reusePreparedStatement(preparedStatement, string);
                        return null;
                    }
                    return preparedStatement;
                }
            }
            catch (SQLException sQLException) {
                if (n >= 2) {
                    throw DbException.convert(sQLException);
                }
                this.conn.close(true);
                this.connect();
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public void checkSupportAlter() {
        throw DbException.getUnsupportedException("LINK");
    }

    @Override
    public long truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("LINK");
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return true;
    }

    @Override
    public boolean canDrop() {
        return true;
    }

    @Override
    public TableType getTableType() {
        return TableType.TABLE_LINK;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        super.removeChildrenAndResources(sessionLocal);
        this.close(sessionLocal);
        this.database.removeMeta(sessionLocal, this.getId());
        this.driver = null;
        this.originalTable = null;
        this.password = null;
        this.user = null;
        this.url = null;
        this.preparedMap = null;
        this.invalidate();
    }

    public boolean isOracle() {
        return this.url.startsWith("jdbc:oracle:");
    }

    private static boolean isMySqlUrl(String string) {
        return string.startsWith("jdbc:mysql:") || string.startsWith("jdbc:mariadb:");
    }

    @Override
    public ArrayList<Index> getIndexes() {
        return this.indexes;
    }

    @Override
    public long getMaxDataModificationId() {
        return Long.MAX_VALUE;
    }

    @Override
    public void updateRows(Prepared prepared, SessionLocal sessionLocal, LocalResult localResult) {
        this.checkReadOnly();
        if (this.emitUpdates) {
            while (localResult.next()) {
                prepared.checkCanceled();
                Row row = localResult.currentRowForTable();
                localResult.next();
                Row row2 = localResult.currentRowForTable();
                this.linkedIndex.update(row, row2, sessionLocal);
            }
        } else {
            super.updateRows(prepared, sessionLocal, localResult);
        }
    }

    public void setGlobalTemporary(boolean bl) {
        this.globalTemporary = bl;
    }

    public void setReadOnly(boolean bl) {
        this.readOnly = bl;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return 100000L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reusePreparedStatement(PreparedStatement preparedStatement, String string) {
        TableLinkConnection tableLinkConnection = this.conn;
        synchronized (tableLinkConnection) {
            this.preparedMap.put(string, preparedStatement);
        }
    }

    @Override
    public boolean isDeterministic() {
        return false;
    }

    @Override
    public void checkWritingAllowed() {
    }

    @Override
    public void convertInsertRow(SessionLocal sessionLocal, Row row, Boolean bl) {
        this.convertRow(sessionLocal, row);
    }

    @Override
    public void convertUpdateRow(SessionLocal sessionLocal, Row row, boolean bl) {
        this.convertRow(sessionLocal, row);
    }

    private void convertRow(SessionLocal sessionLocal, Row row) {
        for (int i = 0; i < this.columns.length; ++i) {
            Column column;
            Value value;
            Value value2 = row.getValue(i);
            if (value2 == null || (value = (column = this.columns[i]).validateConvertUpdateSequence(sessionLocal, value2, row)) == value2) continue;
            row.setValue(i, value);
        }
    }

    public void setFetchSize(int n) {
        this.fetchSize = n;
    }

    public void setAutoCommit(boolean bl) {
        this.autocommit = bl;
    }

    public boolean getAutocommit() {
        return this.autocommit;
    }

    public int getFetchSize() {
        return this.fetchSize;
    }

    public String getIdentifierQuoteString() {
        return this.identifierQuoteString;
    }
}

