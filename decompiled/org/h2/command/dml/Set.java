/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.ParserBase;
import org.h2.command.Prepared;
import org.h2.command.dml.SetTypes;
import org.h2.engine.Database;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.engine.Setting;
import org.h2.expression.Expression;
import org.h2.expression.TimeZoneOperation;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.mode.DefaultNullOrdering;
import org.h2.result.ResultInterface;
import org.h2.schema.Schema;
import org.h2.security.auth.AuthenticatorFactory;
import org.h2.table.Table;
import org.h2.util.DateTimeUtils;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;

public class Set
extends Prepared {
    private final int type;
    private Expression expression;
    private String stringValue;
    private String[] stringValueList;

    public Set(SessionLocal sessionLocal, int n) {
        super(sessionLocal);
        this.type = n;
    }

    public void setString(String string) {
        this.stringValue = string;
    }

    @Override
    public boolean isTransactional() {
        switch (this.type) {
            case 4: 
            case 8: 
            case 9: 
            case 12: 
            case 13: 
            case 18: 
            case 22: 
            case 24: 
            case 29: 
            case 30: 
            case 33: 
            case 36: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 45: {
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long update() {
        Database database = this.getDatabase();
        String string = SetTypes.getTypeName(this.type);
        block42 : switch (this.type) {
            case 21: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0 || n > 2) {
                    throw DbException.getInvalidValueException("ALLOW_LITERALS", n);
                }
                Database database2 = database;
                synchronized (database2) {
                    database.setAllowLiterals(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 7: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("CACHE_SIZE", n);
                }
                Database database3 = database;
                synchronized (database3) {
                    database.setCacheSize(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 12: {
                SessionLocal sessionLocal;
                String string2;
                if ("TRUE".equals(this.stringValue) || (string2 = StringUtils.quoteStringSQL(this.stringValue)).equals(database.getCluster())) break;
                if (!string2.equals("''")) {
                    this.session.getUser().checkAdmin();
                }
                database.setCluster(string2);
                SessionLocal sessionLocal2 = sessionLocal = database.getSystemSession();
                synchronized (sessionLocal2) {
                    Database database4 = database;
                    synchronized (database4) {
                        this.addOrUpdateSetting(sessionLocal, string, string2, 0);
                        sessionLocal.commit(true);
                        break;
                    }
                }
            }
            case 11: {
                CompareMode compareMode;
                this.session.getUser().checkAdmin();
                StringBuilder stringBuilder = new StringBuilder(this.stringValue);
                if (this.stringValue.equals("OFF")) {
                    compareMode = CompareMode.getInstance(null, 0);
                } else {
                    int n = this.getIntValue();
                    stringBuilder.append(" STRENGTH ");
                    if (n == 3) {
                        stringBuilder.append("IDENTICAL");
                    } else if (n == 0) {
                        stringBuilder.append("PRIMARY");
                    } else if (n == 1) {
                        stringBuilder.append("SECONDARY");
                    } else if (n == 2) {
                        stringBuilder.append("TERTIARY");
                    }
                    compareMode = CompareMode.getInstance(this.stringValue, n);
                }
                Database database5 = database;
                synchronized (database5) {
                    CompareMode compareMode2 = database.getCompareMode();
                    if (compareMode2.equals(compareMode)) {
                        break;
                    }
                    Table table = database.getFirstUserTable();
                    if (table != null) {
                        throw DbException.get(90089, table.getTraceSQL());
                    }
                    this.addOrUpdateSetting(string, stringBuilder.toString(), 0);
                    database.setCompareMode(compareMode);
                    break;
                }
            }
            case 28: {
                this.session.getUser().checkAdmin();
                if (!database.isStarting()) break;
                int n = this.getIntValue();
                Database database6 = database;
                synchronized (database6) {
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 14: {
                this.session.getUser().checkAdmin();
                database.setEventListenerClass(this.stringValue);
                break;
            }
            case 17: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n != -1 && n < 0) {
                    throw DbException.getInvalidValueException("DB_CLOSE_DELAY", n);
                }
                Database database7 = database;
                synchronized (database7) {
                    database.setCloseDelay(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 5: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("DEFAULT_LOCK_TIMEOUT", n);
                }
                Database database8 = database;
                synchronized (database8) {
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 6: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                Database database9 = database;
                synchronized (database9) {
                    database.setDefaultTableType(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 27: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                switch (n) {
                    case 0: {
                        if (database.unsetExclusiveSession(this.session)) break block42;
                        throw DbException.get(90135);
                    }
                    case 1: {
                        if (database.setExclusiveSession(this.session, false)) break block42;
                        throw DbException.get(90135);
                    }
                    case 2: {
                        if (database.setExclusiveSession(this.session, true)) break block42;
                        throw DbException.get(90135);
                    }
                    default: {
                        throw DbException.getInvalidValueException("EXCLUSIVE", n);
                    }
                }
            }
            case 32: {
                this.session.getUser().checkAdmin();
                Database database10 = database;
                synchronized (database10) {
                    Table table = database.getFirstUserTable();
                    if (table != null) {
                        throw DbException.get(90141, table.getTraceSQL());
                    }
                    database.setJavaObjectSerializerName(this.stringValue);
                    this.addOrUpdateSetting(string, this.stringValue, 0);
                    break;
                }
            }
            case 0: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                Database database11 = database;
                synchronized (database11) {
                    database.setIgnoreCase(n == 1);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 16: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                Database database12 = database;
                synchronized (database12) {
                    database.setLockMode(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 4: {
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("LOCK_TIMEOUT", n);
                }
                this.session.setLockTimeout(n);
                break;
            }
            case 20: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("MAX_LENGTH_INPLACE_LOB", n);
                }
                Database database13 = database;
                synchronized (database13) {
                    database.setMaxLengthInplaceLob(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 1: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n >= 0) break;
                throw DbException.getInvalidValueException("MAX_LOG_SIZE", n);
            }
            case 15: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("MAX_MEMORY_ROWS", n);
                }
                Database database14 = database;
                synchronized (database14) {
                    database.setMaxMemoryRows(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 19: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("MAX_MEMORY_UNDO", n);
                }
                Database database15 = database;
                synchronized (database15) {
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 26: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("MAX_OPERATION_MEMORY", n);
                }
                database.setMaxOperationMemory(n);
                break;
            }
            case 2: {
                Mode mode = Mode.getInstance(this.stringValue);
                if (mode == null) {
                    throw DbException.get(90088, this.stringValue);
                }
                if (database.getMode() == mode) break;
                this.session.getUser().checkAdmin();
                database.setMode(mode);
                break;
            }
            case 23: {
                this.session.getUser().checkAdmin();
                database.setOptimizeReuseResults(this.getIntValue() != 0);
                break;
            }
            case 30: {
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("QUERY_TIMEOUT", n);
                }
                this.session.setQueryTimeout(n);
                break;
            }
            case 31: {
                DbException.getUnsupportedException("MV_STORE + SET REDO_LOG_BINARY");
                break;
            }
            case 25: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0 || n > 1) {
                    throw DbException.getInvalidValueException("REFERENTIAL_INTEGRITY", n);
                }
                database.setReferentialIntegrity(n == 1);
                break;
            }
            case 34: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0 || n > 1) {
                    throw DbException.getInvalidValueException("QUERY_STATISTICS", n);
                }
                database.setQueryStatistics(n == 1);
                break;
            }
            case 35: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 1) {
                    throw DbException.getInvalidValueException("QUERY_STATISTICS_MAX_ENTRIES", n);
                }
                database.setQueryStatisticsMaxEntries(n);
                break;
            }
            case 22: {
                Schema schema = database.getSchema(this.expression.optimize(this.session).getValue(this.session).getString());
                this.session.setCurrentSchema(schema);
                break;
            }
            case 24: {
                this.session.setSchemaSearchPath(this.stringValueList);
                break;
            }
            case 40: {
                String string3 = database.getShortName();
                String string4 = this.expression.optimize(this.session).getValue(this.session).getString();
                if (string4 != null && (database.equalsIdentifiers(string3, string4) || database.equalsIdentifiers(string3, string4.trim()))) break;
                throw DbException.get(90013, this.stringValue);
            }
            case 9: {
                this.session.getUser().checkAdmin();
                if (this.getPersistedObjectId() != 0) break;
                database.getTraceSystem().setLevelFile(this.getIntValue());
                break;
            }
            case 8: {
                this.session.getUser().checkAdmin();
                if (this.getPersistedObjectId() != 0) break;
                database.getTraceSystem().setLevelSystemOut(this.getIntValue());
                break;
            }
            case 10: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("TRACE_MAX_FILE_SIZE", n);
                }
                int n2 = n * 0x100000;
                Database database16 = database;
                synchronized (database16) {
                    database.getTraceSystem().setMaxFileSize(n2);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 18: {
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("THROTTLE", n);
                }
                this.session.setThrottle(n);
                break;
            }
            case 29: {
                Expression expression = this.expression.optimize(this.session);
                this.session.setVariable(this.stringValue, expression.getValue(this.session));
                break;
            }
            case 13: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("WRITE_DELAY", n);
                }
                Database database17 = database;
                synchronized (database17) {
                    database.setWriteDelay(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 33: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n < 0) {
                    throw DbException.getInvalidValueException("RETENTION_TIME", n);
                }
                Database database18 = database;
                synchronized (database18) {
                    database.setRetentionTime(n);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 36: {
                int n = this.getIntValue();
                if (n != 0 && n != 1) {
                    throw DbException.getInvalidValueException("LAZY_QUERY_EXECUTION", n);
                }
                this.session.setLazyQueryExecution(n == 1);
                break;
            }
            case 37: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                if (n != 0 && n != 1) {
                    throw DbException.getInvalidValueException("BUILTIN_ALIAS_OVERRIDE", n);
                }
                database.setAllowBuiltinAliasOverride(n == 1);
                break;
            }
            case 38: {
                this.session.getUser().checkAdmin();
                boolean bl = this.expression.optimize(this.session).getBooleanValue(this.session);
                try {
                    Database database19 = database;
                    synchronized (database19) {
                        if (bl) {
                            database.setAuthenticator(AuthenticatorFactory.createAuthenticator());
                        } else {
                            database.setAuthenticator(null);
                        }
                        this.addOrUpdateSetting(string, bl ? "TRUE" : "FALSE", 0);
                    }
                }
                catch (Exception exception) {
                    if (!database.isStarting()) {
                        throw DbException.convert(exception);
                    }
                    database.getTrace(2).error(exception, "{0}: failed to set authenticator during database start ", this.expression.toString());
                }
                break;
            }
            case 39: {
                this.session.getUser().checkAdmin();
                int n = this.getIntValue();
                Database database20 = database;
                synchronized (database20) {
                    database.setIgnoreCatalogs(n == 1);
                    this.addOrUpdateSetting(string, null, n);
                    break;
                }
            }
            case 41: {
                this.session.setNonKeywords(ParserBase.parseNonKeywords(this.stringValueList));
                break;
            }
            case 42: {
                this.session.setTimeZone(this.expression == null ? DateTimeUtils.getTimeZone() : Set.parseTimeZone(this.expression.getValue(this.session)));
                break;
            }
            case 43: {
                this.session.setVariableBinary(this.expression.getBooleanValue(this.session));
                break;
            }
            case 44: {
                DefaultNullOrdering defaultNullOrdering;
                try {
                    defaultNullOrdering = DefaultNullOrdering.valueOf(StringUtils.toUpperEnglish(this.stringValue));
                }
                catch (RuntimeException runtimeException) {
                    throw DbException.getInvalidValueException("DEFAULT_NULL_ORDERING", this.stringValue);
                }
                if (database.getDefaultNullOrdering() == defaultNullOrdering) break;
                this.session.getUser().checkAdmin();
                database.setDefaultNullOrdering(defaultNullOrdering);
                break;
            }
            case 45: {
                this.session.setTruncateLargeLength(this.expression.getBooleanValue(this.session));
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        database.getNextModificationDataId();
        database.getNextModificationMetaId();
        return 0L;
    }

    private static TimeZoneProvider parseTimeZone(Value value) {
        if (DataType.isCharacterStringType(value.getValueType())) {
            TimeZoneProvider timeZoneProvider;
            try {
                timeZoneProvider = TimeZoneProvider.ofId(value.getString());
            }
            catch (RuntimeException runtimeException) {
                throw DbException.getInvalidValueException("TIME ZONE", value.getTraceSQL());
            }
            return timeZoneProvider;
        }
        if (value == ValueNull.INSTANCE) {
            throw DbException.getInvalidValueException("TIME ZONE", value);
        }
        return TimeZoneProvider.ofOffset(TimeZoneOperation.parseInterval(value));
    }

    private int getIntValue() {
        this.expression = this.expression.optimize(this.session);
        return this.expression.getValue(this.session).getInt();
    }

    public void setInt(int n) {
        this.expression = ValueExpression.get(ValueInteger.get(n));
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    private void addOrUpdateSetting(String string, String string2, int n) {
        this.addOrUpdateSetting(this.session, string, string2, n);
    }

    private void addOrUpdateSetting(SessionLocal sessionLocal, String string, String string2, int n) {
        Database database = sessionLocal.getDatabase();
        assert (Thread.holdsLock(database));
        if (database.isReadOnly()) {
            return;
        }
        Setting setting = database.findSetting(string);
        boolean bl = false;
        if (setting == null) {
            bl = true;
            int n2 = this.getObjectId();
            setting = new Setting(database, n2, string);
        }
        if (string2 != null) {
            if (!bl && setting.getStringValue().equals(string2)) {
                return;
            }
            setting.setStringValue(string2);
        } else {
            if (!bl && setting.getIntValue() == n) {
                return;
            }
            setting.setIntValue(n);
        }
        if (bl) {
            database.addDatabaseObject(sessionLocal, setting);
        } else {
            database.updateMeta(sessionLocal, setting);
        }
    }

    @Override
    public boolean needRecompile() {
        return false;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    public void setStringArray(String[] stringArray) {
        this.stringValueList = stringArray;
    }

    @Override
    public int getType() {
        return 67;
    }
}

