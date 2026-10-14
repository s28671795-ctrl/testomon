/*
 * Decompiled with CFR 0.152.
 */
package org.h2.message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.h2.api.ErrorCode;
import org.h2.jdbc.JdbcException;
import org.h2.jdbc.JdbcSQLDataException;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbc.JdbcSQLFeatureNotSupportedException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.h2.jdbc.JdbcSQLInvalidAuthorizationSpecException;
import org.h2.jdbc.JdbcSQLNonTransientConnectionException;
import org.h2.jdbc.JdbcSQLNonTransientException;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import org.h2.jdbc.JdbcSQLTimeoutException;
import org.h2.jdbc.JdbcSQLTransactionRollbackException;
import org.h2.jdbc.JdbcSQLTransientException;
import org.h2.util.SortedProperties;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class DbException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public static final String HIDE_SQL = "--hide--";
    private static final Properties MESSAGES = new Properties();
    public static final SQLException SQL_OOME = new SQLException("OutOfMemoryError", "HY000", 90108, new OutOfMemoryError());
    private static final DbException OOME = new DbException(SQL_OOME);
    private Object source;

    private DbException(SQLException sQLException) {
        super(sQLException.getMessage(), sQLException);
    }

    private static String translate(String string, String ... stringArray) {
        String string2 = MESSAGES.getProperty(string);
        if (string2 == null) {
            string2 = "(Message " + string + " not found)";
        }
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                String string3 = stringArray[i];
                if (string3 == null || string3.length() <= 0) continue;
                stringArray[i] = DbException.quote(string3);
            }
            string2 = MessageFormat.format(string2, stringArray);
        }
        return string2;
    }

    private static String quote(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n + 2).append('\"');
        int n2 = 0;
        while (n2 < n) {
            int n3 = string.codePointAt(n2);
            n2 += Character.charCount(n3);
            int n4 = Character.getType(n3);
            if (n4 == 0 || n4 >= 12 && n4 <= 19 && n3 != 32) {
                if (n3 <= 65535) {
                    StringUtils.appendHex(stringBuilder.append('\\'), n3, 2);
                    continue;
                }
                StringUtils.appendHex(stringBuilder.append("\\+"), n3, 3);
                continue;
            }
            if (n3 == 34 || n3 == 92) {
                stringBuilder.append((char)n3);
            }
            stringBuilder.appendCodePoint(n3);
        }
        return stringBuilder.append('\"').toString();
    }

    public SQLException getSQLException() {
        return (SQLException)this.getCause();
    }

    public int getErrorCode() {
        return this.getSQLException().getErrorCode();
    }

    public DbException addSQL(String string) {
        SQLException sQLException = this.getSQLException();
        if (sQLException instanceof JdbcException) {
            JdbcException jdbcException = (JdbcException)((Object)sQLException);
            if (jdbcException.getSQL() == null) {
                jdbcException.setSQL(DbException.filterSQL(string));
            }
            return this;
        }
        sQLException = DbException.getJdbcSQLException(sQLException.getMessage(), string, sQLException.getSQLState(), sQLException.getErrorCode(), sQLException, null);
        return new DbException(sQLException);
    }

    public static DbException get(int n) {
        return DbException.get(n, (String)null);
    }

    public static DbException get(int n, String string) {
        return DbException.get(n, new String[]{string});
    }

    public static DbException get(int n, Throwable throwable, String ... stringArray) {
        return new DbException(DbException.getJdbcSQLException(n, throwable, stringArray));
    }

    public static DbException get(int n, String ... stringArray) {
        return new DbException(DbException.getJdbcSQLException(n, null, stringArray));
    }

    public static DbException fromUser(String string, String string2) {
        return new DbException(DbException.getJdbcSQLException(string2, null, string, 0, null, null));
    }

    public static DbException getSyntaxError(String string, int n) {
        string = StringUtils.addAsterisk(string, n);
        return DbException.get(42000, string);
    }

    public static DbException getSyntaxError(String string, int n, String string2) {
        string = StringUtils.addAsterisk(string, n);
        return new DbException(DbException.getJdbcSQLException(42001, null, string, string2));
    }

    public static DbException getSyntaxError(int n, String string, int n2, String ... stringArray) {
        string = StringUtils.addAsterisk(string, n2);
        String string2 = ErrorCode.getState(n);
        String string3 = DbException.translate(string2, stringArray);
        return new DbException(DbException.getJdbcSQLException(string3, string, string2, n, null, null));
    }

    public static DbException getUnsupportedException(String string) {
        return DbException.get(50100, string);
    }

    public static DbException getInvalidValueException(String string, Object object) {
        return DbException.get(90008, object == null ? "null" : object.toString(), string);
    }

    public static DbException getInvalidValueException(Throwable throwable, String string, Object object) {
        return DbException.get(90008, throwable, object == null ? "null" : object.toString(), string);
    }

    public static DbException getValueTooLongException(String string, String string2, long l) {
        StringBuilder stringBuilder;
        int n = string2.length();
        int n2 = l >= 0L ? 22 : 0;
        StringBuilder stringBuilder2 = stringBuilder = n > 80 ? new StringBuilder(83 + n2).append(string2, 0, 80).append("...") : new StringBuilder(n + n2).append(string2);
        if (l >= 0L) {
            stringBuilder.append(" (").append(l).append(')');
        }
        return DbException.get(22001, string, stringBuilder.toString());
    }

    public static DbException getFileVersionError(String string) {
        return DbException.get(90048, "Old database: " + string + " - please convert the database to a SQL script and re-create it.");
    }

    public static RuntimeException getInternalError(String string) {
        RuntimeException runtimeException = new RuntimeException(string);
        DbException.traceThrowable(runtimeException);
        return runtimeException;
    }

    public static RuntimeException getInternalError() {
        return DbException.getInternalError("Unexpected code path");
    }

    public static SQLException toSQLException(Throwable throwable) {
        if (throwable instanceof SQLException) {
            return (SQLException)throwable;
        }
        return DbException.convert(throwable).getSQLException();
    }

    public static DbException convert(Throwable throwable) {
        try {
            if (throwable instanceof DbException) {
                return (DbException)throwable;
            }
            if (throwable instanceof SQLException) {
                return new DbException((SQLException)throwable);
            }
            if (throwable instanceof InvocationTargetException) {
                return DbException.convertInvocation((InvocationTargetException)throwable, null);
            }
            if (throwable instanceof IOException) {
                return DbException.get(90028, throwable, throwable.toString());
            }
            if (throwable instanceof OutOfMemoryError) {
                return DbException.get(90108, throwable, new String[0]);
            }
            if (throwable instanceof StackOverflowError || throwable instanceof LinkageError) {
                return DbException.get(50000, throwable, throwable.toString());
            }
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            return DbException.get(50000, throwable, throwable.toString());
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return OOME;
        }
        catch (Throwable throwable2) {
            try {
                DbException dbException = new DbException(new SQLException("GeneralError", "HY000", 50000, throwable));
                dbException.addSuppressed(throwable2);
                return dbException;
            }
            catch (OutOfMemoryError outOfMemoryError) {
                return OOME;
            }
        }
    }

    public static DbException convertInvocation(InvocationTargetException invocationTargetException, String string) {
        Throwable throwable = invocationTargetException.getTargetException();
        if (throwable instanceof SQLException || throwable instanceof DbException) {
            return DbException.convert(throwable);
        }
        string = string == null ? throwable.getMessage() : string + ": " + throwable.getMessage();
        return DbException.get(90105, throwable, string);
    }

    public static DbException convertIOException(IOException iOException, String string) {
        if (string == null) {
            Throwable throwable = iOException.getCause();
            if (throwable instanceof DbException) {
                return (DbException)throwable;
            }
            return DbException.get(90028, iOException, iOException.toString());
        }
        return DbException.get(90031, iOException, iOException.toString(), string);
    }

    public static SQLException getJdbcSQLException(int n) {
        return DbException.getJdbcSQLException(n, (Throwable)null, new String[0]);
    }

    public static SQLException getJdbcSQLException(int n, String string) {
        return DbException.getJdbcSQLException(n, null, string);
    }

    public static SQLException getJdbcSQLException(int n, Throwable throwable, String ... stringArray) {
        String string = ErrorCode.getState(n);
        String string2 = DbException.translate(string, stringArray);
        return DbException.getJdbcSQLException(string2, null, string, n, throwable, null);
    }

    public static SQLException getJdbcSQLException(String string, String string2, String string3, int n, Throwable throwable, String string4) {
        string2 = DbException.filterSQL(string2);
        switch (n / 1000) {
            case 2: {
                return new JdbcSQLNonTransientException(string, string2, string3, n, throwable, string4);
            }
            case 7: 
            case 21: 
            case 42: 
            case 54: {
                return new JdbcSQLSyntaxErrorException(string, string2, string3, n, throwable, string4);
            }
            case 8: {
                return new JdbcSQLNonTransientConnectionException(string, string2, string3, n, throwable, string4);
            }
            case 22: {
                return new JdbcSQLDataException(string, string2, string3, n, throwable, string4);
            }
            case 23: {
                return new JdbcSQLIntegrityConstraintViolationException(string, string2, string3, n, throwable, string4);
            }
            case 28: {
                return new JdbcSQLInvalidAuthorizationSpecException(string, string2, string3, n, throwable, string4);
            }
            case 40: {
                return new JdbcSQLTransactionRollbackException(string, string2, string3, n, throwable, string4);
            }
        }
        switch (n) {
            case 50000: 
            case 50004: 
            case 90001: 
            case 90002: 
            case 90006: 
            case 90007: 
            case 90019: 
            case 90021: 
            case 90024: 
            case 90025: 
            case 90028: 
            case 90029: 
            case 90031: 
            case 90034: 
            case 90040: 
            case 90044: 
            case 90058: 
            case 90062: 
            case 90063: 
            case 90064: 
            case 90065: 
            case 90096: 
            case 90097: 
            case 90101: 
            case 90102: 
            case 90103: 
            case 90104: 
            case 90105: 
            case 90111: 
            case 90124: 
            case 90125: 
            case 90126: 
            case 90127: 
            case 90128: 
            case 90130: 
            case 90134: 
            case 90140: 
            case 90148: {
                return new JdbcSQLNonTransientException(string, string2, string3, n, throwable, string4);
            }
            case 50100: {
                return new JdbcSQLFeatureNotSupportedException(string, string2, string3, n, throwable, string4);
            }
            case 50200: 
            case 57014: 
            case 90039: {
                return new JdbcSQLTimeoutException(string, string2, string3, n, throwable, string4);
            }
            case 90000: 
            case 90005: 
            case 90015: 
            case 90016: 
            case 90017: 
            case 90022: 
            case 90023: 
            case 90032: 
            case 90033: 
            case 90035: 
            case 90036: 
            case 90037: 
            case 90038: 
            case 90041: 
            case 90042: 
            case 90043: 
            case 90045: 
            case 90052: 
            case 90054: 
            case 90057: 
            case 90059: 
            case 90068: 
            case 90069: 
            case 90070: 
            case 90071: 
            case 90072: 
            case 90073: 
            case 90074: 
            case 90075: 
            case 90076: 
            case 90077: 
            case 90078: 
            case 90079: 
            case 90080: 
            case 90081: 
            case 90082: 
            case 90083: 
            case 90084: 
            case 90085: 
            case 90086: 
            case 90087: 
            case 90089: 
            case 90090: 
            case 90091: 
            case 90106: 
            case 90107: 
            case 90109: 
            case 90110: 
            case 90114: 
            case 90115: 
            case 90116: 
            case 90118: 
            case 90119: 
            case 90120: 
            case 90122: 
            case 90123: 
            case 90129: 
            case 90132: 
            case 90136: 
            case 90137: 
            case 90139: 
            case 90141: 
            case 90145: 
            case 90150: 
            case 90151: 
            case 90152: 
            case 90153: 
            case 90154: 
            case 90155: 
            case 90156: 
            case 90157: {
                return new JdbcSQLSyntaxErrorException(string, string2, string3, n, throwable, string4);
            }
            case 90003: 
            case 90004: 
            case 90008: 
            case 90009: 
            case 90010: 
            case 90012: 
            case 90014: 
            case 90026: 
            case 90027: 
            case 90053: 
            case 90056: 
            case 90095: 
            case 90142: {
                return new JdbcSQLDataException(string, string2, string3, n, throwable, string4);
            }
            case 90011: 
            case 90013: 
            case 90018: 
            case 90020: 
            case 90030: 
            case 90046: 
            case 90047: 
            case 90048: 
            case 90049: 
            case 90050: 
            case 90055: 
            case 90060: 
            case 90061: 
            case 90066: 
            case 90067: 
            case 90088: 
            case 90093: 
            case 90094: 
            case 90098: 
            case 90099: 
            case 90108: 
            case 90113: 
            case 90117: 
            case 90121: 
            case 90133: 
            case 90135: 
            case 90138: 
            case 90144: 
            case 90146: 
            case 90147: 
            case 90149: {
                return new JdbcSQLNonTransientConnectionException(string, string2, string3, n, throwable, string4);
            }
            case 90112: 
            case 90131: 
            case 90143: {
                return new JdbcSQLTransientException(string, string2, string3, n, throwable, string4);
            }
        }
        return new JdbcSQLException(string, string2, string3, n, throwable, string4);
    }

    private static String filterSQL(String string) {
        return string == null || !string.contains(HIDE_SQL) ? string : "-";
    }

    public static String buildMessageForException(JdbcException jdbcException) {
        String string = jdbcException.getOriginalMessage();
        StringBuilder stringBuilder = new StringBuilder(string != null ? string : "- ");
        string = jdbcException.getSQL();
        if (string != null) {
            stringBuilder.append("; SQL statement:\n").append(string);
        }
        stringBuilder.append(" [").append(jdbcException.getErrorCode()).append('-').append(224).append(']');
        return stringBuilder.toString();
    }

    public static void printNextExceptions(SQLException sQLException, PrintWriter printWriter) {
        int n = 0;
        while ((sQLException = sQLException.getNextException()) != null) {
            if (n++ == 100) {
                printWriter.println("(truncated)");
                return;
            }
            printWriter.println(sQLException.toString());
        }
    }

    public static void printNextExceptions(SQLException sQLException, PrintStream printStream) {
        int n = 0;
        while ((sQLException = sQLException.getNextException()) != null) {
            if (n++ == 100) {
                printStream.println("(truncated)");
                return;
            }
            printStream.println(sQLException.toString());
        }
    }

    public Object getSource() {
        return this.source;
    }

    public void setSource(Object object) {
        this.source = object;
    }

    public static void traceThrowable(Throwable throwable) {
        PrintWriter printWriter = DriverManager.getLogWriter();
        if (printWriter != null) {
            throwable.printStackTrace(printWriter);
        }
    }

    static {
        try {
            byte[] byArray;
            String string;
            byte[] byArray2 = Utils.getResource("/org/h2/res/_messages_en.prop");
            if (byArray2 != null) {
                MESSAGES.load(new ByteArrayInputStream(byArray2));
            }
            if (!"en".equals(string = Locale.getDefault().getLanguage()) && (byArray = Utils.getResource("/org/h2/res/_messages_" + string + ".prop")) != null) {
                SortedProperties sortedProperties = SortedProperties.fromLines(new String(byArray, StandardCharsets.UTF_8));
                for (Map.Entry<Object, Object> entry : sortedProperties.entrySet()) {
                    String string2 = (String)entry.getKey();
                    String string3 = (String)entry.getValue();
                    if (string3 == null || string3.startsWith("#")) continue;
                    String string4 = MESSAGES.getProperty(string2);
                    String string5 = string3 + "\n" + string4;
                    MESSAGES.put(string2, string5);
                }
            }
        }
        catch (IOException | OutOfMemoryError throwable) {
            DbException.traceThrowable(throwable);
        }
    }
}

