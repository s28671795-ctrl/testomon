/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Properties;
import javax.naming.Context;
import javax.sql.DataSource;
import org.h2.api.JavaObjectSerializer;
import org.h2.engine.SysProperties;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.message.DbException;
import org.h2.tools.SimpleResultSet;
import org.h2.util.Bits;
import org.h2.util.JSR310Utils;
import org.h2.util.LegacyDateTimeUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.Value;
import org.h2.value.ValueLob;
import org.h2.value.ValueToObjectConverter;
import org.h2.value.ValueUuid;

public class JdbcUtils {
    public static JavaObjectSerializer serializer;
    private static final String[] DRIVERS;
    private static final byte[] UUID_PREFIX;
    private static boolean allowAllClasses;
    private static HashSet<String> allowedClassNames;
    private static final ArrayList<Utils.ClassFactory> userClassFactories;
    private static String[] allowedClassNamePrefixes;

    private JdbcUtils() {
    }

    public static void addClassFactory(Utils.ClassFactory classFactory) {
        userClassFactories.add(classFactory);
    }

    public static void removeClassFactory(Utils.ClassFactory classFactory) {
        userClassFactories.remove(classFactory);
    }

    public static <Z> Class<Z> loadUserClass(String string) {
        if (allowedClassNames == null) {
            String string2 = SysProperties.ALLOWED_CLASSES;
            ArrayList<String> object = new ArrayList<String>();
            int n = 0;
            HashSet<String> hashSet = new HashSet<String>();
            for (String string3 : StringUtils.arraySplit(string2, ',', true)) {
                if (string3.equals("*")) {
                    n = 1;
                    continue;
                }
                if (string3.endsWith("*")) {
                    object.add(string3.substring(0, string3.length() - 1));
                    continue;
                }
                hashSet.add(string3);
            }
            allowedClassNamePrefixes = object.toArray(new String[0]);
            allowAllClasses = n;
            allowedClassNames = hashSet;
        }
        if (!allowAllClasses && !allowedClassNames.contains(string)) {
            boolean bl = false;
            for (String string4 : allowedClassNamePrefixes) {
                if (!string.startsWith(string4)) continue;
                bl = true;
                break;
            }
            if (!bl) {
                throw DbException.get(90134, string);
            }
        }
        for (Utils.ClassFactory classFactory : userClassFactories) {
            if (!classFactory.match(string)) continue;
            try {
                Class<?> clazz = classFactory.loadClass(string);
                if (clazz == null) continue;
                return clazz;
            }
            catch (Exception exception) {
                throw DbException.get(90086, exception, string);
            }
        }
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                return Class.forName(string, true, Thread.currentThread().getContextClassLoader());
            }
            catch (Exception exception) {
                throw DbException.get(90086, classNotFoundException, string);
            }
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            throw DbException.get(90086, noClassDefFoundError, string);
        }
        catch (Error error) {
            throw DbException.get(50000, error, string);
        }
    }

    public static void closeSilently(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
    }

    public static void closeSilently(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
    }

    public static void closeSilently(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
    }

    public static Connection getConnection(String string, String string2, String string3, String string4) throws SQLException {
        return JdbcUtils.getConnection(string, string2, string3, string4, null, false);
    }

    public static Connection getConnection(String string, String string2, String string3, String string4, NetworkConnectionInfo networkConnectionInfo, boolean bl) throws SQLException {
        if (string2.startsWith("jdbc:h2:")) {
            JdbcConnection jdbcConnection = new JdbcConnection(string2, null, string3, string4, bl);
            if (networkConnectionInfo != null) {
                jdbcConnection.getSession().setNetworkConnectionInfo(networkConnectionInfo);
            }
            return jdbcConnection;
        }
        if (StringUtils.isNullOrEmpty(string)) {
            JdbcUtils.load(string2);
        } else {
            Class clazz = JdbcUtils.loadUserClass(string);
            try {
                if (Driver.class.isAssignableFrom(clazz)) {
                    Connection connection;
                    Driver driver = (Driver)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    Properties properties = new Properties();
                    if (string3 != null) {
                        properties.setProperty("user", string3);
                    }
                    if (string4 != null) {
                        properties.setProperty("password", string4);
                    }
                    if ((connection = driver.connect(string2, properties)) != null) {
                        return connection;
                    }
                    throw new SQLException("Driver " + string + " is not suitable for " + string2, "08001");
                }
                if (Context.class.isAssignableFrom(clazz)) {
                    if (!string2.startsWith("java:")) {
                        throw new SQLException("Only java scheme is supported for JNDI lookups", "08001");
                    }
                    Context context = (Context)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    DataSource dataSource = (DataSource)context.lookup(string2);
                    if (StringUtils.isNullOrEmpty(string3) && StringUtils.isNullOrEmpty(string4)) {
                        return dataSource.getConnection();
                    }
                    return dataSource.getConnection(string3, string4);
                }
            }
            catch (Exception exception) {
                throw DbException.toSQLException(exception);
            }
        }
        return DriverManager.getConnection(string2, string3, string4);
    }

    public static String getDriver(String string) {
        if (string.startsWith("jdbc:")) {
            string = string.substring("jdbc:".length());
            for (int i = 0; i < DRIVERS.length; i += 2) {
                String string2 = DRIVERS[i];
                if (!string.startsWith(string2)) continue;
                return DRIVERS[i + 1];
            }
        }
        return null;
    }

    public static void load(String string) {
        String string2 = JdbcUtils.getDriver(string);
        if (string2 != null) {
            JdbcUtils.loadUserClass(string2);
        }
    }

    public static byte[] serialize(Object object, JavaObjectSerializer javaObjectSerializer) {
        try {
            if (javaObjectSerializer != null) {
                return javaObjectSerializer.serialize(object);
            }
            if (serializer != null) {
                return serializer.serialize(object);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        }
        catch (Throwable throwable) {
            throw DbException.get(90026, throwable, throwable.toString());
        }
    }

    public static Object deserialize(byte[] byArray, JavaObjectSerializer javaObjectSerializer) {
        try {
            ObjectInputStream objectInputStream;
            if (javaObjectSerializer != null) {
                return javaObjectSerializer.deserialize(byArray);
            }
            if (serializer != null) {
                return serializer.deserialize(byArray);
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            if (SysProperties.USE_THREAD_CONTEXT_CLASS_LOADER) {
                final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                objectInputStream = new ObjectInputStream(byteArrayInputStream){

                    @Override
                    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                        try {
                            return Class.forName(objectStreamClass.getName(), true, classLoader);
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            return super.resolveClass(objectStreamClass);
                        }
                    }
                };
            } else {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
            }
            return objectInputStream.readObject();
        }
        catch (Throwable throwable) {
            throw DbException.get(90027, throwable, throwable.toString());
        }
    }

    public static ValueUuid deserializeUuid(byte[] byArray) {
        block3: {
            if (byArray.length == 80) {
                for (int i = 0; i < 64; ++i) {
                    if (byArray[i] == UUID_PREFIX[i]) {
                        continue;
                    }
                    break block3;
                }
                return ValueUuid.get(Bits.readLong(byArray, 72), Bits.readLong(byArray, 64));
            }
        }
        throw DbException.get(90027, "Is not a UUID");
    }

    public static void set(PreparedStatement preparedStatement, int n, Value value, JdbcConnection jdbcConnection) throws SQLException {
        if (preparedStatement instanceof JdbcPreparedStatement) {
            if (value instanceof ValueLob) {
                JdbcUtils.setLob(preparedStatement, n, (ValueLob)value);
            } else {
                preparedStatement.setObject(n, value);
            }
        } else {
            JdbcUtils.setOther(preparedStatement, n, value, jdbcConnection);
        }
    }

    private static void setOther(PreparedStatement preparedStatement, int n, Value value, JdbcConnection jdbcConnection) throws SQLException {
        int n2 = value.getValueType();
        switch (n2) {
            case 0: {
                preparedStatement.setNull(n, 0);
                break;
            }
            case 8: {
                preparedStatement.setBoolean(n, value.getBoolean());
                break;
            }
            case 9: {
                preparedStatement.setByte(n, value.getByte());
                break;
            }
            case 10: {
                preparedStatement.setShort(n, value.getShort());
                break;
            }
            case 11: {
                preparedStatement.setInt(n, value.getInt());
                break;
            }
            case 12: {
                preparedStatement.setLong(n, value.getLong());
                break;
            }
            case 13: 
            case 16: {
                preparedStatement.setBigDecimal(n, value.getBigDecimal());
                break;
            }
            case 15: {
                preparedStatement.setDouble(n, value.getDouble());
                break;
            }
            case 14: {
                preparedStatement.setFloat(n, value.getFloat());
                break;
            }
            case 18: {
                try {
                    preparedStatement.setObject(n, (Object)JSR310Utils.valueToLocalTime(value, null), 92);
                }
                catch (SQLException sQLException) {
                    preparedStatement.setTime(n, LegacyDateTimeUtils.toTime(null, null, value));
                }
                break;
            }
            case 17: {
                try {
                    preparedStatement.setObject(n, (Object)JSR310Utils.valueToLocalDate(value, null), 91);
                }
                catch (SQLException sQLException) {
                    preparedStatement.setDate(n, LegacyDateTimeUtils.toDate(null, null, value));
                }
                break;
            }
            case 20: {
                try {
                    preparedStatement.setObject(n, (Object)JSR310Utils.valueToLocalDateTime(value, null), 93);
                }
                catch (SQLException sQLException) {
                    preparedStatement.setTimestamp(n, LegacyDateTimeUtils.toTimestamp(null, null, value));
                }
                break;
            }
            case 5: 
            case 6: 
            case 37: 
            case 38: {
                preparedStatement.setBytes(n, value.getBytesNoCopy());
                break;
            }
            case 2: 
            case 4: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 36: {
                preparedStatement.setString(n, value.getString());
                break;
            }
            case 3: 
            case 7: {
                JdbcUtils.setLob(preparedStatement, n, (ValueLob)value);
                break;
            }
            case 40: {
                preparedStatement.setArray(n, preparedStatement.getConnection().createArrayOf("NULL", (Object[])ValueToObjectConverter.valueToDefaultObject(value, jdbcConnection, true)));
                break;
            }
            case 35: {
                preparedStatement.setObject(n, JdbcUtils.deserialize(value.getBytesNoCopy(), jdbcConnection.getJavaObjectSerializer()), 2000);
                break;
            }
            case 39: {
                preparedStatement.setBytes(n, value.getBytes());
                break;
            }
            case 1: {
                try {
                    preparedStatement.setObject(n, (Object)value.getString(), 1);
                }
                catch (SQLException sQLException) {
                    preparedStatement.setString(n, value.getString());
                }
                break;
            }
            case 21: {
                try {
                    preparedStatement.setObject(n, (Object)JSR310Utils.valueToOffsetDateTime(value, null), 2014);
                    return;
                }
                catch (SQLException sQLException) {
                    preparedStatement.setString(n, value.getString());
                    break;
                }
            }
            case 19: {
                try {
                    preparedStatement.setObject(n, (Object)JSR310Utils.valueToOffsetTime(value, null), 2013);
                    return;
                }
                catch (SQLException sQLException) {
                    preparedStatement.setString(n, value.getString());
                    break;
                }
            }
            default: {
                throw DbException.getUnsupportedException(Value.getTypeName(n2));
            }
        }
    }

    private static void setLob(PreparedStatement preparedStatement, int n, ValueLob valueLob) throws SQLException {
        if (valueLob.getValueType() == 7) {
            long l = valueLob.octetLength();
            preparedStatement.setBinaryStream(n, valueLob.getInputStream(), l > Integer.MAX_VALUE ? -1 : (int)l);
        } else {
            long l = valueLob.charLength();
            preparedStatement.setCharacterStream(n, valueLob.getReader(), l > Integer.MAX_VALUE ? -1 : (int)l);
        }
    }

    public static ResultSet getMetaResultSet(Connection connection, String string) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        if (JdbcUtils.isBuiltIn(string, "@best_row_identifier")) {
            String[] stringArray = JdbcUtils.split(string);
            int n = stringArray[4] == null ? 0 : Integer.parseInt(stringArray[4]);
            boolean bl = Boolean.parseBoolean(stringArray[5]);
            return databaseMetaData.getBestRowIdentifier(stringArray[1], stringArray[2], stringArray[3], n, bl);
        }
        if (JdbcUtils.isBuiltIn(string, "@catalogs")) {
            return databaseMetaData.getCatalogs();
        }
        if (JdbcUtils.isBuiltIn(string, "@columns")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getColumns(stringArray[1], stringArray[2], stringArray[3], stringArray[4]);
        }
        if (JdbcUtils.isBuiltIn(string, "@column_privileges")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getColumnPrivileges(stringArray[1], stringArray[2], stringArray[3], stringArray[4]);
        }
        if (JdbcUtils.isBuiltIn(string, "@cross_references")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getCrossReference(stringArray[1], stringArray[2], stringArray[3], stringArray[4], stringArray[5], stringArray[6]);
        }
        if (JdbcUtils.isBuiltIn(string, "@exported_keys")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getExportedKeys(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@imported_keys")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getImportedKeys(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@index_info")) {
            String[] stringArray = JdbcUtils.split(string);
            boolean bl = Boolean.parseBoolean(stringArray[4]);
            boolean bl2 = Boolean.parseBoolean(stringArray[5]);
            return databaseMetaData.getIndexInfo(stringArray[1], stringArray[2], stringArray[3], bl, bl2);
        }
        if (JdbcUtils.isBuiltIn(string, "@primary_keys")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getPrimaryKeys(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@procedures")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getProcedures(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@procedure_columns")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getProcedureColumns(stringArray[1], stringArray[2], stringArray[3], stringArray[4]);
        }
        if (JdbcUtils.isBuiltIn(string, "@schemas")) {
            return databaseMetaData.getSchemas();
        }
        if (JdbcUtils.isBuiltIn(string, "@tables")) {
            String[] stringArray = JdbcUtils.split(string);
            String[] stringArray2 = stringArray[4] == null ? null : StringUtils.arraySplit(stringArray[4], ',', false);
            return databaseMetaData.getTables(stringArray[1], stringArray[2], stringArray[3], stringArray2);
        }
        if (JdbcUtils.isBuiltIn(string, "@table_privileges")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getTablePrivileges(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@table_types")) {
            return databaseMetaData.getTableTypes();
        }
        if (JdbcUtils.isBuiltIn(string, "@type_info")) {
            return databaseMetaData.getTypeInfo();
        }
        if (JdbcUtils.isBuiltIn(string, "@udts")) {
            int[] nArray;
            String[] stringArray = JdbcUtils.split(string);
            if (stringArray[4] == null) {
                nArray = null;
            } else {
                String[] stringArray3 = StringUtils.arraySplit(stringArray[4], ',', false);
                nArray = new int[stringArray3.length];
                for (int i = 0; i < stringArray3.length; ++i) {
                    nArray[i] = Integer.parseInt(stringArray3[i]);
                }
            }
            return databaseMetaData.getUDTs(stringArray[1], stringArray[2], stringArray[3], nArray);
        }
        if (JdbcUtils.isBuiltIn(string, "@version_columns")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getVersionColumns(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@memory")) {
            SimpleResultSet simpleResultSet = new SimpleResultSet();
            simpleResultSet.addColumn("Type", 12, 0, 0);
            simpleResultSet.addColumn("KB", 12, 0, 0);
            simpleResultSet.addRow("Used Memory", Long.toString(Utils.getMemoryUsed()));
            simpleResultSet.addRow("Free Memory", Long.toString(Utils.getMemoryFree()));
            return simpleResultSet;
        }
        if (JdbcUtils.isBuiltIn(string, "@info")) {
            String string2;
            SimpleResultSet simpleResultSet = new SimpleResultSet();
            simpleResultSet.addColumn("KEY", 12, 0, 0);
            simpleResultSet.addColumn("VALUE", 12, 0, 0);
            simpleResultSet.addRow("conn.getCatalog", connection.getCatalog());
            simpleResultSet.addRow("conn.getAutoCommit", Boolean.toString(connection.getAutoCommit()));
            simpleResultSet.addRow("conn.getTransactionIsolation", Integer.toString(connection.getTransactionIsolation()));
            simpleResultSet.addRow("conn.getWarnings", String.valueOf(connection.getWarnings()));
            try {
                string2 = String.valueOf(connection.getTypeMap());
            }
            catch (SQLException sQLException) {
                string2 = sQLException.toString();
            }
            simpleResultSet.addRow("conn.getTypeMap", string2);
            simpleResultSet.addRow("conn.isReadOnly", Boolean.toString(connection.isReadOnly()));
            simpleResultSet.addRow("conn.getHoldability", Integer.toString(connection.getHoldability()));
            JdbcUtils.addDatabaseMetaData(simpleResultSet, databaseMetaData);
            return simpleResultSet;
        }
        if (JdbcUtils.isBuiltIn(string, "@attributes")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getAttributes(stringArray[1], stringArray[2], stringArray[3], stringArray[4]);
        }
        if (JdbcUtils.isBuiltIn(string, "@super_tables")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getSuperTables(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@super_types")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getSuperTypes(stringArray[1], stringArray[2], stringArray[3]);
        }
        if (JdbcUtils.isBuiltIn(string, "@pseudo_columns")) {
            String[] stringArray = JdbcUtils.split(string);
            return databaseMetaData.getPseudoColumns(stringArray[1], stringArray[2], stringArray[3], stringArray[4]);
        }
        return null;
    }

    private static void addDatabaseMetaData(SimpleResultSet simpleResultSet, DatabaseMetaData databaseMetaData) {
        Method[] methodArray = DatabaseMetaData.class.getDeclaredMethods();
        Arrays.sort(methodArray, Comparator.comparing(Method::toString));
        for (Method method2 : methodArray) {
            if (method2.getParameterTypes().length != 0) continue;
            try {
                Object object = method2.invoke((Object)databaseMetaData, new Object[0]);
                simpleResultSet.addRow("meta." + method2.getName(), String.valueOf(object));
            }
            catch (InvocationTargetException invocationTargetException) {
                simpleResultSet.addRow("meta." + method2.getName(), invocationTargetException.getTargetException().toString());
            }
            catch (Exception exception) {
                simpleResultSet.addRow("meta." + method2.getName(), exception.toString());
            }
        }
    }

    public static boolean isBuiltIn(String string, String string2) {
        return string.regionMatches(true, 0, string2, 0, string2.length());
    }

    public static String[] split(String string) {
        String[] stringArray = StringUtils.arraySplit(string, ' ', true);
        String[] stringArray2 = new String[Math.max(10, stringArray.length)];
        System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
        for (int i = 0; i < stringArray2.length; ++i) {
            if (!"null".equals(stringArray2[i])) continue;
            stringArray2[i] = null;
        }
        return stringArray2;
    }

    static {
        DRIVERS = new String[]{"h2:", "org.h2.Driver", "Cache:", "com.intersys.jdbc.CacheDriver", "daffodilDB://", "in.co.daffodil.db.rmi.RmiDaffodilDBDriver", "daffodil", "in.co.daffodil.db.jdbc.DaffodilDBDriver", "db2:", "com.ibm.db2.jcc.DB2Driver", "derby:net:", "org.apache.derby.client.ClientAutoloadedDriver", "derby://", "org.apache.derby.client.ClientAutoloadedDriver", "derby:", "org.apache.derby.iapi.jdbc.AutoloadedDriver", "FrontBase:", "com.frontbase.jdbc.FBJDriver", "firebirdsql:", "org.firebirdsql.jdbc.FBDriver", "hsqldb:", "org.hsqldb.jdbcDriver", "informix-sqli:", "com.informix.jdbc.IfxDriver", "jtds:", "net.sourceforge.jtds.jdbc.Driver", "microsoft:", "com.microsoft.jdbc.sqlserver.SQLServerDriver", "mimer:", "com.mimer.jdbc.Driver", "mysql:", "com.mysql.cj.jdbc.Driver", "mariadb:", "org.mariadb.jdbc.Driver", "odbc:", "sun.jdbc.odbc.JdbcOdbcDriver", "oracle:", "oracle.jdbc.driver.OracleDriver", "pervasive:", "com.pervasive.jdbc.v2.Driver", "pointbase:micro:", "com.pointbase.me.jdbc.jdbcDriver", "pointbase:", "com.pointbase.jdbc.jdbcUniversalDriver", "postgresql:", "org.postgresql.Driver", "sybase:", "com.sybase.jdbc3.jdbc.SybDriver", "sqlserver:", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "teradata:", "com.ncr.teradata.TeraDriver"};
        UUID_PREFIX = "\u00ac\u00ed\u0000\u0005sr\u0000\u000ejava.util.UUID\u00bc\u0099\u0003\u00f7\u0098m\u0085/\u0002\u0000\u0002J\u0000\fleastSigBitsJ\u0000\u000bmostSigBitsxp".getBytes(StandardCharsets.ISO_8859_1);
        userClassFactories = new ArrayList();
        String string = SysProperties.JAVA_OBJECT_SERIALIZER;
        if (string != null) {
            try {
                serializer = (JavaObjectSerializer)JdbcUtils.loadUserClass(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                throw DbException.convert(exception);
            }
        }
    }
}

