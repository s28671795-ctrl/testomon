/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.Scope;
import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BigIntType;
import liquibase.datatype.core.CharType;
import liquibase.datatype.core.IntType;
import liquibase.datatype.core.UnknownType;
import liquibase.exception.ServiceNotFoundException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.core.DataType;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class DataTypeFactory {
    private static DataTypeFactory instance;
    private final Map<String, List<Class<? extends LiquibaseDataType>>> registry = new ConcurrentHashMap<String, List<Class<? extends LiquibaseDataType>>>();

    protected DataTypeFactory() {
        try {
            for (LiquibaseDataType type : Scope.getCurrentScope().getServiceLocator().findInstances(LiquibaseDataType.class)) {
                this.register(type);
            }
        }
        catch (ServiceNotFoundException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static synchronized DataTypeFactory getInstance() {
        if (instance == null) {
            instance = new DataTypeFactory();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = new DataTypeFactory();
    }

    public void register(LiquibaseDataType type) {
        try {
            ArrayList<String> names = new ArrayList<String>();
            names.add(type.getName());
            names.addAll(Arrays.asList(type.getAliases()));
            Comparator comparator = (o1, o2) -> {
                try {
                    return -1 * Integer.compare(((LiquibaseDataType)o1.getConstructor(new Class[0]).newInstance(new Object[0])).getPriority(), ((LiquibaseDataType)o2.getConstructor(new Class[0]).newInstance(new Object[0])).getPriority());
                }
                catch (Exception e) {
                    throw new UnexpectedLiquibaseException(e);
                }
            };
            for (String name : names) {
                name = name.toLowerCase(Locale.US);
                this.registry.computeIfAbsent(name, k -> new ArrayList());
                List<Class<? extends LiquibaseDataType>> classes = this.registry.get(name);
                classes.add(type.getClass());
                classes.sort(comparator);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public void unregister(String name) {
        this.registry.remove(name.toLowerCase(Locale.US));
    }

    public LiquibaseDataType fromDescription(String dataTypeDefinition, Database database) {
        String[] params;
        String paramStrings;
        String[][] quotePairs;
        if (dataTypeDefinition == null) {
            return null;
        }
        if (dataTypeDefinition.matches("^\\$\\{.*}$")) {
            return new UnknownType(dataTypeDefinition);
        }
        String dataTypeName = dataTypeDefinition;
        if (dataTypeName.matches(".+\\(.*\\).*")) {
            dataTypeName = dataTypeName.replaceFirst("\\s*\\(.*\\)", "");
        }
        if (dataTypeName.matches(".+\\{.*")) {
            dataTypeName = dataTypeName.replaceFirst("\\s*\\{.*", "");
        }
        boolean autoIncrement = false;
        if (dataTypeName.toLowerCase(Locale.US).endsWith(" identity")) {
            dataTypeName = dataTypeName.toLowerCase(Locale.US).replaceFirst(" identity$", "");
            autoIncrement = true;
        }
        for (String[] quotePair : quotePairs = new String[][]{{"\"", "\""}, {"[", "]"}, {"`", "`"}, {"'", "'"}}) {
            int indexOfCloseQuote;
            String openQuote = quotePair[0];
            String closeQuote = quotePair[1];
            if (!dataTypeName.startsWith(openQuote) || (indexOfCloseQuote = dataTypeName.indexOf(closeQuote, openQuote.length())) == -1 || dataTypeName.indexOf(closeQuote, indexOfCloseQuote + closeQuote.length()) != -1) continue;
            dataTypeName = dataTypeName.substring(openQuote.length(), indexOfCloseQuote) + dataTypeName.substring(indexOfCloseQuote + closeQuote.length(), dataTypeName.length());
            break;
        }
        String additionalInfo = null;
        if (!(dataTypeName.toLowerCase(Locale.US).startsWith("bit varying") || dataTypeName.toLowerCase(Locale.US).startsWith("character varying") || dataTypeName.toLowerCase(Locale.US).startsWith("long varchar") || dataTypeName.toLowerCase(Locale.US).startsWith("long nvarchar") || dataTypeName.toLowerCase(Locale.US).startsWith("long binary") || dataTypeName.toLowerCase(Locale.US).startsWith("long varbinary"))) {
            String[] splitTypeName = dataTypeName.trim().split("\\s+", 2);
            dataTypeName = splitTypeName[0];
            if (splitTypeName.length > 1) {
                additionalInfo = splitTypeName[1];
            }
        }
        Collection classes = this.registry.get(dataTypeName.toLowerCase(Locale.US));
        LiquibaseDataType liquibaseDataType = null;
        if (classes == null) {
            liquibaseDataType = dataTypeName.toUpperCase(Locale.US).startsWith("INTERVAL") ? new UnknownType(dataTypeDefinition) : new UnknownType(dataTypeName);
        } else {
            Iterator iterator2 = classes.iterator();
            do {
                try {
                    liquibaseDataType = (LiquibaseDataType)((Class)iterator2.next()).getConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Exception e) {
                    throw new UnexpectedLiquibaseException(e);
                }
            } while (database != null && !liquibaseDataType.supports(database) && iterator2.hasNext());
        }
        if (database != null && !liquibaseDataType.supports(database)) {
            throw new UnexpectedLiquibaseException("Could not find type for " + liquibaseDataType + " for DBMS " + database.getShortName());
        }
        if (liquibaseDataType == null) {
            liquibaseDataType = new UnknownType(dataTypeName);
        }
        liquibaseDataType.setAdditionalInformation(additionalInfo);
        if (dataTypeDefinition.matches(".+\\s*\\(.*")) {
            paramStrings = dataTypeDefinition.replaceFirst(".*?\\(", "").replaceFirst("\\).*", "");
            for (String param2 : params = paramStrings.split(",")) {
                if ((param2 = StringUtil.trimToNull(param2)) == null) continue;
                if (liquibaseDataType instanceof CharType && !(database instanceof OracleDatabase)) {
                    param2 = param2.replaceFirst(" BYTE", "");
                }
                liquibaseDataType.addParameter(param2);
            }
        }
        if (dataTypeDefinition.matches(".*\\{.*")) {
            paramStrings = dataTypeDefinition.replaceFirst(".*?\\{", "").replaceFirst("\\}.*", "");
            for (String param2 : params = paramStrings.split(",")) {
                if ((param2 = StringUtil.trimToNull(param2)) == null) continue;
                String[] paramAndValue = param2.split(":", 2);
                if (paramAndValue.length < 2) {
                    throw new UnexpectedLiquibaseException("Data type definition contains unparseable embedded information: `" + dataTypeDefinition + "`");
                }
                ObjectUtil.setProperty((Object)liquibaseDataType, paramAndValue[0], paramAndValue[1]);
            }
        }
        if (autoIncrement && liquibaseDataType instanceof IntType) {
            ((IntType)liquibaseDataType).setAutoIncrement(true);
        }
        if (autoIncrement && liquibaseDataType instanceof BigIntType) {
            ((BigIntType)liquibaseDataType).setAutoIncrement(true);
        }
        liquibaseDataType.finishInitialization(dataTypeDefinition);
        return liquibaseDataType;
    }

    public LiquibaseDataType fromObject(Object object, Database database) {
        if (object instanceof ColumnConfig.ValueNumeric) {
            object = ((ColumnConfig.ValueNumeric)object).getDelegate();
        }
        return this.fromDescription(object.getClass().getName(), database);
    }

    public LiquibaseDataType from(DataType type, Database database) {
        if (type == null) {
            return null;
        }
        return this.fromDescription(type.toString(), database);
    }

    public LiquibaseDataType from(DatabaseDataType type, Database database) {
        if (type == null) {
            return null;
        }
        return this.fromDescription(type.toString(), database);
    }

    public String getTrueBooleanValue(Database database) {
        return this.fromDescription("boolean", database).objectToSql(true, database);
    }

    public String getFalseBooleanValue(Database database) {
        return this.fromDescription("boolean", database).objectToSql(false, database);
    }
}

