/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc.meta;

import java.io.IOException;
import java.util.ArrayList;
import org.h2.engine.SessionRemote;
import org.h2.jdbc.meta.DatabaseMeta;
import org.h2.message.DbException;
import org.h2.mode.DefaultNullOrdering;
import org.h2.result.ResultInterface;
import org.h2.result.ResultRemote;
import org.h2.value.Transfer;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public class DatabaseMetaRemote
extends DatabaseMeta {
    static final int DEFAULT_NULL_ORDERING = 0;
    static final int GET_DATABASE_PRODUCT_VERSION = 1;
    static final int GET_SQL_KEYWORDS = 2;
    static final int GET_NUMERIC_FUNCTIONS = 3;
    static final int GET_STRING_FUNCTIONS = 4;
    static final int GET_SYSTEM_FUNCTIONS = 5;
    static final int GET_TIME_DATE_FUNCTIONS = 6;
    static final int GET_SEARCH_STRING_ESCAPE = 7;
    static final int GET_PROCEDURES_3 = 8;
    static final int GET_PROCEDURE_COLUMNS_4 = 9;
    static final int GET_TABLES_4 = 10;
    static final int GET_SCHEMAS = 11;
    static final int GET_CATALOGS = 12;
    static final int GET_TABLE_TYPES = 13;
    static final int GET_COLUMNS_4 = 14;
    static final int GET_COLUMN_PRIVILEGES_4 = 15;
    static final int GET_TABLE_PRIVILEGES_3 = 16;
    static final int GET_BEST_ROW_IDENTIFIER_5 = 17;
    static final int GET_VERSION_COLUMNS_3 = 18;
    static final int GET_PRIMARY_KEYS_3 = 19;
    static final int GET_IMPORTED_KEYS_3 = 20;
    static final int GET_EXPORTED_KEYS_3 = 21;
    static final int GET_CROSS_REFERENCE_6 = 22;
    static final int GET_TYPE_INFO = 23;
    static final int GET_INDEX_INFO_5 = 24;
    static final int GET_UDTS_4 = 25;
    static final int GET_SUPER_TYPES_3 = 26;
    static final int GET_SUPER_TABLES_3 = 27;
    static final int GET_ATTRIBUTES_4 = 28;
    static final int GET_DATABASE_MAJOR_VERSION = 29;
    static final int GET_DATABASE_MINOR_VERSION = 30;
    static final int GET_SCHEMAS_2 = 31;
    static final int GET_FUNCTIONS_3 = 32;
    static final int GET_FUNCTION_COLUMNS_4 = 33;
    static final int GET_PSEUDO_COLUMNS_4 = 34;
    private final SessionRemote session;
    private final ArrayList<Transfer> transferList;

    public DatabaseMetaRemote(SessionRemote sessionRemote, ArrayList<Transfer> arrayList) {
        this.session = sessionRemote;
        this.transferList = arrayList;
    }

    @Override
    public DefaultNullOrdering defaultNullOrdering() {
        ResultInterface resultInterface = this.executeQuery(0, new Value[0]);
        resultInterface.next();
        return DefaultNullOrdering.valueOf(resultInterface.currentRow()[0].getInt());
    }

    @Override
    public String getDatabaseProductVersion() {
        ResultInterface resultInterface = this.executeQuery(1, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public String getSQLKeywords() {
        ResultInterface resultInterface = this.executeQuery(2, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public String getNumericFunctions() {
        ResultInterface resultInterface = this.executeQuery(3, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public String getStringFunctions() {
        ResultInterface resultInterface = this.executeQuery(4, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public String getSystemFunctions() {
        ResultInterface resultInterface = this.executeQuery(5, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public String getTimeDateFunctions() {
        ResultInterface resultInterface = this.executeQuery(6, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public String getSearchStringEscape() {
        ResultInterface resultInterface = this.executeQuery(7, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getString();
    }

    @Override
    public ResultInterface getProcedures(String string, String string2, String string3) {
        return this.executeQuery(8, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getProcedureColumns(String string, String string2, String string3, String string4) {
        return this.executeQuery(9, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4));
    }

    @Override
    public ResultInterface getTables(String string, String string2, String string3, String[] stringArray) {
        return this.executeQuery(10, this.getString(string), this.getString(string2), this.getString(string3), this.getStringArray(stringArray));
    }

    @Override
    public ResultInterface getSchemas() {
        return this.executeQuery(11, new Value[0]);
    }

    @Override
    public ResultInterface getCatalogs() {
        return this.executeQuery(12, new Value[0]);
    }

    @Override
    public ResultInterface getTableTypes() {
        return this.executeQuery(13, new Value[0]);
    }

    @Override
    public ResultInterface getColumns(String string, String string2, String string3, String string4) {
        return this.executeQuery(14, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4));
    }

    @Override
    public ResultInterface getColumnPrivileges(String string, String string2, String string3, String string4) {
        return this.executeQuery(15, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4));
    }

    @Override
    public ResultInterface getTablePrivileges(String string, String string2, String string3) {
        return this.executeQuery(16, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getBestRowIdentifier(String string, String string2, String string3, int n, boolean bl) {
        return this.executeQuery(17, this.getString(string), this.getString(string2), this.getString(string3), ValueInteger.get(n), ValueBoolean.get(bl));
    }

    @Override
    public ResultInterface getVersionColumns(String string, String string2, String string3) {
        return this.executeQuery(18, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getPrimaryKeys(String string, String string2, String string3) {
        return this.executeQuery(19, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getImportedKeys(String string, String string2, String string3) {
        return this.executeQuery(20, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getExportedKeys(String string, String string2, String string3) {
        return this.executeQuery(21, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getCrossReference(String string, String string2, String string3, String string4, String string5, String string6) {
        return this.executeQuery(22, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4), this.getString(string5), this.getString(string6));
    }

    @Override
    public ResultInterface getTypeInfo() {
        return this.executeQuery(23, new Value[0]);
    }

    @Override
    public ResultInterface getIndexInfo(String string, String string2, String string3, boolean bl, boolean bl2) {
        return this.executeQuery(24, this.getString(string), this.getString(string2), this.getString(string3), ValueBoolean.get(bl), ValueBoolean.get(bl2));
    }

    @Override
    public ResultInterface getUDTs(String string, String string2, String string3, int[] nArray) {
        return this.executeQuery(25, this.getString(string), this.getString(string2), this.getString(string3), this.getIntArray(nArray));
    }

    @Override
    public ResultInterface getSuperTypes(String string, String string2, String string3) {
        return this.executeQuery(26, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getSuperTables(String string, String string2, String string3) {
        return this.executeQuery(27, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getAttributes(String string, String string2, String string3, String string4) {
        return this.executeQuery(28, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4));
    }

    @Override
    public int getDatabaseMajorVersion() {
        ResultInterface resultInterface = this.executeQuery(29, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getInt();
    }

    @Override
    public int getDatabaseMinorVersion() {
        ResultInterface resultInterface = this.executeQuery(30, new Value[0]);
        resultInterface.next();
        return resultInterface.currentRow()[0].getInt();
    }

    @Override
    public ResultInterface getSchemas(String string, String string2) {
        return this.executeQuery(31, this.getString(string), this.getString(string2));
    }

    @Override
    public ResultInterface getFunctions(String string, String string2, String string3) {
        return this.executeQuery(32, this.getString(string), this.getString(string2), this.getString(string3));
    }

    @Override
    public ResultInterface getFunctionColumns(String string, String string2, String string3, String string4) {
        return this.executeQuery(33, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4));
    }

    @Override
    public ResultInterface getPseudoColumns(String string, String string2, String string3, String string4) {
        return this.executeQuery(34, this.getString(string), this.getString(string2), this.getString(string3), this.getString(string4));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ResultInterface executeQuery(int n, Value ... valueArray) {
        if (this.session.isClosed()) {
            throw DbException.get(90121);
        }
        this.session.lock();
        try {
            int n2 = this.session.getNextId();
            int n3 = 0;
            for (int i = 0; i < this.transferList.size(); ++i) {
                Transfer transfer = this.transferList.get(i);
                try {
                    int n4;
                    this.session.traceOperation("GET_META", n2);
                    int n5 = valueArray.length;
                    transfer.writeInt(19).writeInt(n).writeInt(n5);
                    for (n4 = 0; n4 < n5; ++n4) {
                        transfer.writeValue(valueArray[n4]);
                    }
                    this.session.done(transfer);
                    n4 = transfer.readInt();
                    ResultRemote resultRemote = new ResultRemote(this.session, transfer, n2, n4, Integer.MAX_VALUE);
                    return resultRemote;
                }
                catch (IOException iOException) {
                    this.session.removeServer(iOException, i--, ++n3);
                    continue;
                }
            }
            ResultInterface resultInterface = null;
            return resultInterface;
        }
        finally {
            this.session.unlock();
        }
    }

    private Value getIntArray(int[] nArray) {
        if (nArray == null) {
            return ValueNull.INSTANCE;
        }
        int n = nArray.length;
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            valueArray[i] = ValueInteger.get(nArray[i]);
        }
        return ValueArray.get(TypeInfo.TYPE_INTEGER, valueArray, this.session);
    }

    private Value getStringArray(String[] stringArray) {
        if (stringArray == null) {
            return ValueNull.INSTANCE;
        }
        int n = stringArray.length;
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            valueArray[i] = this.getString(stringArray[i]);
        }
        return ValueArray.get(TypeInfo.TYPE_VARCHAR, valueArray, this.session);
    }

    private Value getString(String string) {
        return string != null ? ValueVarchar.get(string, this.session) : ValueNull.INSTANCE;
    }
}

