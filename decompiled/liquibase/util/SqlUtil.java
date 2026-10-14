/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BigIntType;
import liquibase.datatype.core.BlobType;
import liquibase.datatype.core.BooleanType;
import liquibase.datatype.core.CharType;
import liquibase.datatype.core.ClobType;
import liquibase.datatype.core.DateTimeType;
import liquibase.datatype.core.DateType;
import liquibase.datatype.core.DecimalType;
import liquibase.datatype.core.DoubleType;
import liquibase.datatype.core.FloatType;
import liquibase.datatype.core.IntType;
import liquibase.datatype.core.NCharType;
import liquibase.datatype.core.NVarcharType;
import liquibase.datatype.core.NumberType;
import liquibase.datatype.core.SmallIntType;
import liquibase.datatype.core.TimeType;
import liquibase.datatype.core.TimestampType;
import liquibase.datatype.core.TinyIntType;
import liquibase.datatype.core.VarcharType;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SqlStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;

public abstract class SqlUtil {
    private static final String NAME_REGEX = ":name|\\?|:value";
    public static final Pattern NAME_PATTERN = Pattern.compile(":name|\\?|:value");

    public static boolean isNumeric(int dataType) {
        switch (dataType) {
            case -7: 
            case -6: 
            case -5: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                return true;
            }
        }
        return false;
    }

    public static boolean isBoolean(int dataType) {
        return dataType == 16;
    }

    public static boolean isDate(int dataType) {
        switch (dataType) {
            case 91: 
            case 92: 
            case 93: 
            case 2013: 
            case 2014: {
                return true;
            }
        }
        return false;
    }

    public static Object parseValue(Database database, Object val, DataType type) {
        if (!(val instanceof String)) {
            return val;
        }
        int typeId = Integer.MIN_VALUE;
        if (type.getDataTypeId() != null) {
            typeId = type.getDataTypeId();
        }
        LiquibaseDataType liquibaseDataType = DataTypeFactory.getInstance().from(type, database);
        String stringVal = (String)val;
        if (stringVal.isEmpty()) {
            if (liquibaseDataType instanceof CharType) {
                return "";
            }
            return null;
        }
        if (database instanceof OracleDatabase && !stringVal.startsWith("'") && !stringVal.endsWith("'")) {
            Object maybeDate = null;
            if (liquibaseDataType instanceof DateType || typeId == 91) {
                maybeDate = stringVal.endsWith("'HH24:MI:SS')") ? DataTypeFactory.getInstance().fromDescription("time", database).sqlToObject(stringVal, database) : DataTypeFactory.getInstance().fromDescription("date", database).sqlToObject(stringVal, database);
            } else if (liquibaseDataType instanceof DateTimeType || typeId == 93) {
                maybeDate = DataTypeFactory.getInstance().fromDescription("datetime", database).sqlToObject(stringVal, database);
            } else if (!stringVal.matches("\\d+\\.?\\d*")) {
                return new DatabaseFunction(stringVal);
            }
            if (maybeDate != null) {
                if (maybeDate instanceof Date) {
                    return maybeDate;
                }
                return new DatabaseFunction(stringVal);
            }
        }
        if ((database instanceof PostgresDatabase || database instanceof OracleDatabase) && (liquibaseDataType instanceof CharType || liquibaseDataType instanceof ClobType) && stringVal.toUpperCase(Locale.ENGLISH).startsWith("GENERATED ALWAYS AS ")) {
            return new DatabaseFunction(stringVal);
        }
        boolean strippedSingleQuotes = false;
        if (stringVal.startsWith("'") && stringVal.endsWith("'")) {
            stringVal = stringVal.substring(1, stringVal.length() - 1);
            strippedSingleQuotes = true;
        } else if (stringVal.startsWith("((") && stringVal.endsWith("))")) {
            stringVal = stringVal.substring(2, stringVal.length() - 2);
        } else if (stringVal.startsWith("('") && stringVal.endsWith("')")) {
            stringVal = stringVal.substring(2, stringVal.length() - 2);
        } else if (stringVal.startsWith("(") && stringVal.endsWith(")")) {
            return new DatabaseFunction(stringVal.substring(1, stringVal.length() - 1));
        }
        String typeName = type.getTypeName();
        try (Scanner scanner = new Scanner(stringVal.trim()).useLocale(Locale.US);){
            if (typeId == 2003) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof BigIntType || typeId == -5) {
                if (scanner.hasNextBigInteger()) {
                    BigInteger bigInteger = scanner.nextBigInteger();
                    return bigInteger;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == -2) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal.trim());
                return databaseFunction;
            }
            if (typeId == -7) {
                if (stringVal.startsWith("b'") || stringVal.startsWith("B'")) {
                    stringVal = stringVal.replaceFirst("b'", "").replaceFirst("B'", "").replaceFirst("'$", "");
                }
                if (stringVal.endsWith("'::\"bit\"")) {
                    stringVal = stringVal.replaceFirst("'::\"bit\"", "");
                }
                stringVal = stringVal.trim();
                Object value = stringVal;
                if (scanner.hasNextBoolean()) {
                    value = scanner.nextBoolean();
                } else if (scanner.hasNextInt()) {
                    if (stringVal.length() > 1) {
                        stringVal = stringVal.substring(0, 1);
                    }
                    value = Integer.valueOf(stringVal);
                }
                if (database instanceof MSSQLDatabase) {
                    if (value instanceof Boolean) {
                        if (((Boolean)value).booleanValue()) {
                            DatabaseFunction databaseFunction = new DatabaseFunction("'true'");
                            return databaseFunction;
                        }
                        DatabaseFunction databaseFunction = new DatabaseFunction("'false'");
                        return databaseFunction;
                    }
                    if (value instanceof Integer) {
                        if ((Integer)value != 0) {
                            DatabaseFunction databaseFunction = new DatabaseFunction("'true'");
                            return databaseFunction;
                        }
                        DatabaseFunction databaseFunction = new DatabaseFunction("'false'");
                        return databaseFunction;
                    }
                    DatabaseFunction databaseFunction = new DatabaseFunction(String.format("'%s'", value));
                    return databaseFunction;
                }
                Object object = value;
                return object;
            }
            if (liquibaseDataType instanceof BlobType || typeId == 2004) {
                if (strippedSingleQuotes) {
                    String string = stringVal;
                    return string;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof BooleanType || typeId == 16) {
                if (scanner.hasNextBoolean()) {
                    Boolean bl = scanner.nextBoolean();
                    return bl;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof CharType || typeId == 1) {
                String string = stringVal;
                return string;
            }
            if (liquibaseDataType instanceof ClobType || typeId == 2005) {
                String string = stringVal;
                return string;
            }
            if (typeId == 70) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof DateType || typeId == 91) {
                if (typeName.equalsIgnoreCase("year")) {
                    String string = stringVal.trim();
                    return string;
                }
                Object object = DataTypeFactory.getInstance().fromDescription("date", database).sqlToObject(stringVal, database);
                return object;
            }
            if (liquibaseDataType instanceof DecimalType || typeId == 3) {
                if (scanner.hasNextBigDecimal()) {
                    BigDecimal bigDecimal = scanner.nextBigDecimal();
                    return bigDecimal;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == 2001) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof DoubleType || typeId == 8) {
                if (scanner.hasNextDouble()) {
                    Double d = scanner.nextDouble();
                    return d;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof FloatType || typeId == 6) {
                if (scanner.hasNextFloat()) {
                    Float f = Float.valueOf(scanner.nextFloat());
                    return f;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof IntType || typeId == 4) {
                if (scanner.hasNextInt()) {
                    Integer n = scanner.nextInt();
                    return n;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == 2000) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == -16) {
                Object object = strippedSingleQuotes ? stringVal : new DatabaseFunction(stringVal);
                return object;
            }
            if (typeId == -4) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == -1) {
                Object object = strippedSingleQuotes ? stringVal : new DatabaseFunction(stringVal);
                return object;
            }
            if (liquibaseDataType instanceof NCharType || typeId == -15 || liquibaseDataType.getName().equalsIgnoreCase("NCLOB")) {
                String string = stringVal;
                return string;
            }
            if (typeId == 2011) {
                String string = stringVal;
                return string;
            }
            if (typeId == 0) {
                Object var9_39 = null;
                return var9_39;
            }
            if (liquibaseDataType instanceof NumberType || typeId == 2) {
                if (scanner.hasNextBigDecimal()) {
                    if (database instanceof MSSQLDatabase && stringVal.endsWith(".0") || stringVal.endsWith(".00") || stringVal.endsWith(".000")) {
                        DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                        return databaseFunction;
                    }
                    BigDecimal bigDecimal = scanner.nextBigDecimal();
                    return bigDecimal;
                }
                if (stringVal.equals("")) {
                    DatabaseFunction databaseFunction = new DatabaseFunction("''");
                    return databaseFunction;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof NVarcharType || typeId == -9) {
                String string = stringVal;
                return string;
            }
            if (typeId == 1111) {
                if (database instanceof AbstractDb2Database && typeName.equalsIgnoreCase("DECFLOAT")) {
                    BigDecimal bigDecimal = new BigDecimal(stringVal);
                    return bigDecimal;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == 7) {
                BigDecimal bigDecimal = new BigDecimal(stringVal.trim());
                return bigDecimal;
            }
            if (typeId == 2006) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == -8) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof SmallIntType || typeId == 5) {
                if (scanner.hasNextInt()) {
                    Integer n = scanner.nextInt();
                    return n;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == 2009) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == 2002) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof TimeType || typeId == 92) {
                Object object = DataTypeFactory.getInstance().fromDescription("time", database).sqlToObject(stringVal, database);
                return object;
            }
            if (liquibaseDataType instanceof DateTimeType || liquibaseDataType instanceof TimestampType || typeId == 93) {
                Object object = DataTypeFactory.getInstance().fromDescription("datetime", database).sqlToObject(stringVal, database);
                return object;
            }
            if (liquibaseDataType instanceof TinyIntType || typeId == -6) {
                if (scanner.hasNextInt()) {
                    Integer n = scanner.nextInt();
                    return n;
                }
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (typeId == -3) {
                DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
                return databaseFunction;
            }
            if (liquibaseDataType instanceof VarcharType || typeId == 12) {
                String string = stringVal;
                return string;
            }
            if (database instanceof MySQLDatabase && typeName.toLowerCase().startsWith("enum")) {
                String string = stringVal;
                return string;
            }
            if (database instanceof MSSQLDatabase && typeName.toLowerCase().startsWith("datetimeoffset")) {
                String string = stringVal;
                return string;
            }
            if (stringVal.equals("")) {
                String string = stringVal;
                return string;
            }
            Scope.getCurrentScope().getLog(SqlUtil.class).info("Unknown default value: value '" + stringVal + "' type " + typeName + " (" + type + "). Calling it a function so it's not additionally quoted");
            if (strippedSingleQuotes) {
                DatabaseFunction databaseFunction = new DatabaseFunction("'" + stringVal + "'");
                return databaseFunction;
            }
            DatabaseFunction databaseFunction = new DatabaseFunction(stringVal);
            return databaseFunction;
        }
    }

    public static String replacePredicatePlaceholders(Database database, String predicate, List<String> columnNames, List<Object> parameters) {
        Matcher matcher = NAME_PATTERN.matcher(predicate.trim());
        StringBuffer sb = new StringBuffer();
        Iterator<String> columnNameIter = columnNames.iterator();
        Iterator<Object> paramIter = parameters.iterator();
        block0: while (matcher.find()) {
            if (":name".equals(matcher.group())) {
                while (columnNameIter.hasNext()) {
                    String columnName = columnNameIter.next();
                    if (columnName == null) continue;
                    matcher.appendReplacement(sb, Matcher.quoteReplacement(database.escapeObjectName(columnName, Column.class)));
                    continue block0;
                }
                continue;
            }
            if (!paramIter.hasNext()) continue;
            Object param2 = paramIter.next();
            matcher.appendReplacement(sb, Matcher.quoteReplacement(DataTypeFactory.getInstance().fromObject(param2, database).objectToSql(param2, database)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String getSqlString(SqlStatement statement, SqlGeneratorFactory sqlGeneratorFactory, Database database) {
        Sql[] sqlStatements = sqlGeneratorFactory.generateSql(statement, database);
        return SqlUtil.convertSqlArrayToString(sqlStatements);
    }

    public static String convertSqlArrayToString(Sql[] sqlStatements) {
        if (sqlStatements != null) {
            return Arrays.stream(sqlStatements).map(sql -> sql.toSql().endsWith(sql.getEndDelimiter()) ? sql.toSql() : sql.toSql() + sql.getEndDelimiter()).collect(Collectors.joining("\n"));
        }
        return "";
    }
}

