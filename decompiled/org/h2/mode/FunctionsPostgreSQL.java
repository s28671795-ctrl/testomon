/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.HashMap;
import java.util.StringJoiner;
import org.h2.command.Parser;
import org.h2.engine.Constants;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.CurrentGeneralValueSpecification;
import org.h2.expression.function.RandFunction;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.mode.FunctionInfo;
import org.h2.mode.ModeFunction;
import org.h2.mode.ToDateParser;
import org.h2.schema.Schema;
import org.h2.server.pg.PgServer;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class FunctionsPostgreSQL
extends ModeFunction {
    private static final int CURRENT_DATABASE = 3001;
    private static final int CURRTID2 = 3002;
    private static final int FORMAT_TYPE = 3003;
    private static final int HAS_DATABASE_PRIVILEGE = 3004;
    private static final int HAS_SCHEMA_PRIVILEGE = 3005;
    private static final int HAS_TABLE_PRIVILEGE = 3006;
    private static final int LASTVAL = 3007;
    private static final int VERSION = 3008;
    private static final int OBJ_DESCRIPTION = 3009;
    private static final int PG_ENCODING_TO_CHAR = 3010;
    private static final int PG_GET_EXPR = 3011;
    private static final int PG_GET_INDEXDEF = 3012;
    private static final int PG_GET_USERBYID = 3013;
    private static final int PG_POSTMASTER_START_TIME = 3014;
    private static final int PG_RELATION_SIZE = 3015;
    private static final int PG_TABLE_IS_VISIBLE = 3016;
    private static final int SET_CONFIG = 3017;
    private static final int ARRAY_TO_STRING = 3018;
    private static final int PG_STAT_GET_NUMSCANS = 3019;
    private static final int TO_DATE = 3020;
    private static final int TO_TIMESTAMP = 3021;
    private static final int GEN_RANDOM_UUID = 3022;
    private static final HashMap<String, FunctionInfo> FUNCTIONS = new HashMap(32);

    public static FunctionsPostgreSQL getFunction(String string) {
        FunctionInfo functionInfo = FUNCTIONS.get(string);
        if (functionInfo != null) {
            return new FunctionsPostgreSQL(functionInfo);
        }
        return null;
    }

    private FunctionsPostgreSQL(FunctionInfo functionInfo) {
        super(functionInfo);
    }

    @Override
    protected void checkParameterCount(int n) {
        int n2;
        int n3;
        switch (this.info.type) {
            case 3004: 
            case 3005: 
            case 3006: {
                n3 = 2;
                n2 = 3;
                break;
            }
            case 3009: 
            case 3015: {
                n3 = 1;
                n2 = 2;
                break;
            }
            case 3012: {
                if (n != 1 && n != 3) {
                    throw DbException.get(7001, this.info.name, "1, 3");
                }
                return;
            }
            case 3011: 
            case 3018: {
                n3 = 2;
                n2 = 3;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        if (n < n3 || n > n2) {
            throw DbException.get(7001, this.info.name, n3 + ".." + n2);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        switch (this.info.type) {
            case 3001: {
                return new CurrentGeneralValueSpecification(0).optimize(sessionLocal);
            }
            case 3022: {
                return new RandFunction(null, 2).optimize(sessionLocal);
            }
        }
        boolean bl = this.optimizeArguments(sessionLocal);
        this.type = TypeInfo.getTypeInfo(this.info.returnDataType);
        if (bl) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        Value[] valueArray = this.getArgumentsValues(sessionLocal, this.args);
        if (valueArray == null) {
            return ValueNull.INSTANCE;
        }
        Value value2 = FunctionsPostgreSQL.getNullOrValue(sessionLocal, this.args, valueArray, 0);
        Value value3 = FunctionsPostgreSQL.getNullOrValue(sessionLocal, this.args, valueArray, 1);
        Value value4 = FunctionsPostgreSQL.getNullOrValue(sessionLocal, this.args, valueArray, 2);
        switch (this.info.type) {
            case 3002: {
                value = ValueInteger.get(1);
                break;
            }
            case 3003: {
                value = value2 != ValueNull.INSTANCE ? ValueVarchar.get(PgServer.formatType(value2.getInt())) : ValueNull.INSTANCE;
                break;
            }
            case 3004: 
            case 3005: 
            case 3006: 
            case 3016: {
                value = ValueBoolean.TRUE;
                break;
            }
            case 3007: {
                value = sessionLocal.getLastIdentity();
                if (value == ValueNull.INSTANCE) {
                    throw DbException.get(90148, "lastval()");
                }
                value = value.convertToBigint(null);
                break;
            }
            case 3008: {
                value = ValueVarchar.get("PostgreSQL 8.2.23 server protocol using H2 " + Constants.FULL_VERSION);
                break;
            }
            case 3009: {
                value = ValueNull.INSTANCE;
                break;
            }
            case 3010: {
                value = ValueVarchar.get(FunctionsPostgreSQL.encodingToChar(value2.getInt()));
                break;
            }
            case 3011: {
                value = ValueNull.INSTANCE;
                break;
            }
            case 3012: {
                value = FunctionsPostgreSQL.getIndexdef(sessionLocal, value2.getInt(), value3, value4);
                break;
            }
            case 3013: {
                value = ValueVarchar.get(FunctionsPostgreSQL.getUserbyid(sessionLocal, value2.getInt()));
                break;
            }
            case 3014: {
                value = sessionLocal.getDatabase().getSystemSession().getSessionStart();
                break;
            }
            case 3015: {
                value = FunctionsPostgreSQL.relationSize(sessionLocal, value2);
                break;
            }
            case 3017: {
                value = value3.convertTo(2);
                break;
            }
            case 3018: {
                if (value2 == ValueNull.INSTANCE || value3 == ValueNull.INSTANCE) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                StringJoiner stringJoiner = new StringJoiner(value3.getString());
                if (value2.getValueType() != 40) {
                    throw DbException.getInvalidValueException("ARRAY_TO_STRING array", value2);
                }
                String string = null;
                if (value4 != null) {
                    string = value4.getString();
                }
                for (Value value5 : ((ValueArray)value2).getList()) {
                    if (value5 != ValueNull.INSTANCE) {
                        stringJoiner.add(value5.getString());
                        continue;
                    }
                    if (string == null) continue;
                    stringJoiner.add(string);
                }
                value = ValueVarchar.get(stringJoiner.toString());
                break;
            }
            case 3019: {
                value = ValueInteger.get(0);
                break;
            }
            case 3020: {
                value = ToDateParser.toDate(sessionLocal, value2.getString(), value3.getString()).convertToDate(sessionLocal);
                break;
            }
            case 3021: {
                value = ToDateParser.toTimestampTz(sessionLocal, value2.getString(), value3.getString());
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        return value;
    }

    private static String encodingToChar(int n) {
        switch (n) {
            case 0: {
                return "SQL_ASCII";
            }
            case 6: {
                return "UTF8";
            }
            case 8: {
                return "LATIN1";
            }
        }
        return n < 40 ? "UTF8" : "";
    }

    private static Value getIndexdef(SessionLocal sessionLocal, int n, Value value, Value value2) {
        block0: for (Schema schema : sessionLocal.getDatabase().getAllSchemasNoMeta()) {
            for (Index index : schema.getAllIndexes()) {
                Column[] columnArray;
                int n2;
                if (index.getId() != n) continue;
                if (index.getTable().isHidden()) continue block0;
                if (value == null || (n2 = value.getInt()) == 0) {
                    return ValueVarchar.get(index.getCreateSQL());
                }
                if (n2 < 1 || n2 > (columnArray = index.getColumns()).length) continue block0;
                return ValueVarchar.get(columnArray[n2 - 1].getName());
            }
        }
        return ValueNull.INSTANCE;
    }

    private static String getUserbyid(SessionLocal sessionLocal, int n) {
        String string;
        User user = sessionLocal.getUser();
        if (user.getId() == n) {
            string = user.getName();
        } else if (user.isAdmin()) {
            for (RightOwner rightOwner : sessionLocal.getDatabase().getAllUsersAndRoles()) {
                if (rightOwner.getId() != n) continue;
                string = rightOwner.getName();
                break;
            }
        } else {
            return "unknown (OID=" + n + ')';
        }
        if (sessionLocal.getDatabase().getSettings().databaseToLower) {
            string = StringUtils.toLowerEnglish(string);
        }
        return string;
    }

    private static Value relationSize(SessionLocal sessionLocal, Value value) {
        if (value.getValueType() == 11) {
            int n = value.getInt();
            block0: for (Schema schema : sessionLocal.getDatabase().getAllSchemasNoMeta()) {
                for (Table table : schema.getAllTablesAndViews(sessionLocal)) {
                    if (n != table.getId()) continue;
                    Table table2 = table;
                    continue block0;
                }
            }
            return ValueNull.INSTANCE;
        }
        Table table = new Parser(sessionLocal).parseTableName(value.getString());
        return ValueBigint.get(table.getDiskSpaceUsed());
    }

    static {
        FUNCTIONS.put("CURRENT_DATABASE", new FunctionInfo("CURRENT_DATABASE", 3001, 0, 2, true, false));
        FUNCTIONS.put("CURRTID2", new FunctionInfo("CURRTID2", 3002, 2, 11, true, false));
        FUNCTIONS.put("FORMAT_TYPE", new FunctionInfo("FORMAT_TYPE", 3003, 2, 2, false, true));
        FUNCTIONS.put("HAS_DATABASE_PRIVILEGE", new FunctionInfo("HAS_DATABASE_PRIVILEGE", 3004, -1, 8, true, false));
        FUNCTIONS.put("HAS_SCHEMA_PRIVILEGE", new FunctionInfo("HAS_SCHEMA_PRIVILEGE", 3005, -1, 8, true, false));
        FUNCTIONS.put("HAS_TABLE_PRIVILEGE", new FunctionInfo("HAS_TABLE_PRIVILEGE", 3006, -1, 8, true, false));
        FUNCTIONS.put("LASTVAL", new FunctionInfo("LASTVAL", 3007, 0, 12, true, false));
        FUNCTIONS.put("VERSION", new FunctionInfo("VERSION", 3008, 0, 2, true, false));
        FUNCTIONS.put("OBJ_DESCRIPTION", new FunctionInfo("OBJ_DESCRIPTION", 3009, -1, 2, true, false));
        FUNCTIONS.put("PG_ENCODING_TO_CHAR", new FunctionInfo("PG_ENCODING_TO_CHAR", 3010, 1, 2, true, true));
        FUNCTIONS.put("PG_GET_EXPR", new FunctionInfo("PG_GET_EXPR", 3011, -1, 2, true, true));
        FUNCTIONS.put("PG_GET_INDEXDEF", new FunctionInfo("PG_GET_INDEXDEF", 3012, -1, 2, true, false));
        FUNCTIONS.put("PG_GET_USERBYID", new FunctionInfo("PG_GET_USERBYID", 3013, 1, 2, true, false));
        FUNCTIONS.put("PG_POSTMASTER_START_TIME", new FunctionInfo("PG_POSTMASTER_START_TIME", 3014, 0, 21, true, false));
        FUNCTIONS.put("PG_RELATION_SIZE", new FunctionInfo("PG_RELATION_SIZE", 3015, -1, 12, true, false));
        FUNCTIONS.put("PG_TABLE_IS_VISIBLE", new FunctionInfo("PG_TABLE_IS_VISIBLE", 3016, 1, 8, true, false));
        FUNCTIONS.put("SET_CONFIG", new FunctionInfo("SET_CONFIG", 3017, 3, 2, true, false));
        FUNCTIONS.put("ARRAY_TO_STRING", new FunctionInfo("ARRAY_TO_STRING", 3018, -1, 2, false, true));
        FUNCTIONS.put("PG_STAT_GET_NUMSCANS", new FunctionInfo("PG_STAT_GET_NUMSCANS", 3019, 1, 11, true, true));
        FUNCTIONS.put("TO_DATE", new FunctionInfo("TO_DATE", 3020, 2, 17, true, true));
        FUNCTIONS.put("TO_TIMESTAMP", new FunctionInfo("TO_TIMESTAMP", 3021, 2, 21, true, true));
        FUNCTIONS.put("GEN_RANDOM_UUID", new FunctionInfo("GEN_RANDOM_UUID", 3022, 0, 39, true, false));
    }
}

