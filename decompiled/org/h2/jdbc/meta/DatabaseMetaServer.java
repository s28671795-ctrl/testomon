/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc.meta;

import org.h2.engine.SessionLocal;
import org.h2.jdbc.meta.DatabaseMeta;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class DatabaseMetaServer {
    public static ResultInterface process(SessionLocal sessionLocal, int n, Value[] valueArray) {
        DatabaseMeta databaseMeta = sessionLocal.getDatabaseMeta();
        switch (n) {
            case 0: {
                return DatabaseMetaServer.result(databaseMeta.defaultNullOrdering().ordinal());
            }
            case 1: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getDatabaseProductVersion());
            }
            case 2: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getSQLKeywords());
            }
            case 3: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getNumericFunctions());
            }
            case 4: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getStringFunctions());
            }
            case 5: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getSystemFunctions());
            }
            case 6: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getTimeDateFunctions());
            }
            case 7: {
                return DatabaseMetaServer.result(sessionLocal, databaseMeta.getSearchStringEscape());
            }
            case 8: {
                return databaseMeta.getProcedures(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 9: {
                return databaseMeta.getProcedureColumns(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString());
            }
            case 10: {
                return databaseMeta.getTables(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), DatabaseMetaServer.toStringArray(valueArray[3]));
            }
            case 11: {
                return databaseMeta.getSchemas();
            }
            case 12: {
                return databaseMeta.getCatalogs();
            }
            case 13: {
                return databaseMeta.getTableTypes();
            }
            case 14: {
                return databaseMeta.getColumns(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString());
            }
            case 15: {
                return databaseMeta.getColumnPrivileges(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString());
            }
            case 16: {
                return databaseMeta.getTablePrivileges(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 17: {
                return databaseMeta.getBestRowIdentifier(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getInt(), valueArray[4].getBoolean());
            }
            case 18: {
                return databaseMeta.getVersionColumns(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 19: {
                return databaseMeta.getPrimaryKeys(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 20: {
                return databaseMeta.getImportedKeys(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 21: {
                return databaseMeta.getExportedKeys(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 22: {
                return databaseMeta.getCrossReference(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString(), valueArray[4].getString(), valueArray[5].getString());
            }
            case 23: {
                return databaseMeta.getTypeInfo();
            }
            case 24: {
                return databaseMeta.getIndexInfo(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getBoolean(), valueArray[4].getBoolean());
            }
            case 25: {
                return databaseMeta.getUDTs(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), DatabaseMetaServer.toIntArray(valueArray[3]));
            }
            case 26: {
                return databaseMeta.getSuperTypes(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 27: {
                return databaseMeta.getSuperTables(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 28: {
                return databaseMeta.getAttributes(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString());
            }
            case 29: {
                return DatabaseMetaServer.result(databaseMeta.getDatabaseMajorVersion());
            }
            case 30: {
                return DatabaseMetaServer.result(databaseMeta.getDatabaseMinorVersion());
            }
            case 31: {
                return databaseMeta.getSchemas(valueArray[0].getString(), valueArray[1].getString());
            }
            case 32: {
                return databaseMeta.getFunctions(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString());
            }
            case 33: {
                return databaseMeta.getFunctionColumns(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString());
            }
            case 34: {
                return databaseMeta.getPseudoColumns(valueArray[0].getString(), valueArray[1].getString(), valueArray[2].getString(), valueArray[3].getString());
            }
        }
        throw DbException.getUnsupportedException("META " + n);
    }

    private static String[] toStringArray(Value value) {
        if (value == ValueNull.INSTANCE) {
            return null;
        }
        Value[] valueArray = ((ValueArray)value).getList();
        int n = valueArray.length;
        String[] stringArray = new String[n];
        for (int i = 0; i < n; ++i) {
            stringArray[i] = valueArray[i].getString();
        }
        return stringArray;
    }

    private static int[] toIntArray(Value value) {
        if (value == ValueNull.INSTANCE) {
            return null;
        }
        Value[] valueArray = ((ValueArray)value).getList();
        int n = valueArray.length;
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            nArray[i] = valueArray[i].getInt();
        }
        return nArray;
    }

    private static ResultInterface result(int n) {
        return DatabaseMetaServer.result(ValueInteger.get(n));
    }

    private static ResultInterface result(SessionLocal sessionLocal, String string) {
        return DatabaseMetaServer.result(ValueVarchar.get(string, sessionLocal));
    }

    private static ResultInterface result(Value value) {
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("RESULT", value.getType());
        simpleResult.addRow(value);
        return simpleResult;
    }

    private DatabaseMetaServer() {
    }
}

