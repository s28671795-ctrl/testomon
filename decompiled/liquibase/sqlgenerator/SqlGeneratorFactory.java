/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorComparator;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;

public class SqlGeneratorFactory {
    private static SqlGeneratorFactory instance;
    private final Map<Class<?>, Type[]> genericInterfacesCache = new HashMap();
    private final Map<Class<?>, Type> genericSuperClassCache = new HashMap();
    private final List<SqlGenerator> generators = new ArrayList<SqlGenerator>();
    private final Map<String, SortedSet<SqlGenerator>> generatorsByKey = new HashMap<String, SortedSet<SqlGenerator>>();
    public static final String GENERATED_SQL_ARRAY_SCOPE_KEY = "generatedSqlArray";

    private SqlGeneratorFactory() {
        try {
            for (SqlGenerator generator : Scope.getCurrentScope().getServiceLocator().findInstances(SqlGenerator.class)) {
                this.register(generator);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SqlGeneratorFactory getInstance() {
        if (instance == null) {
            instance = new SqlGeneratorFactory();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = new SqlGeneratorFactory();
    }

    public void register(SqlGenerator generator) {
        if (this.generators.size() == 0) {
            this.generatorsByKey.clear();
        }
        this.generators.add(generator);
    }

    public void unregister(SqlGenerator generator) {
        this.generators.remove(generator);
    }

    public void unregister(Class generatorClass) {
        SqlGenerator toRemove = null;
        for (SqlGenerator existingGenerator : this.generators) {
            if (!existingGenerator.getClass().equals(generatorClass)) continue;
            toRemove = existingGenerator;
        }
        this.unregister(toRemove);
    }

    protected Collection<SqlGenerator> getGenerators() {
        return this.generators;
    }

    public synchronized SortedSet<SqlGenerator> getGenerators(SqlStatement statement, Database database) {
        int version;
        String databaseName = null;
        databaseName = database == null ? "NULL" : database.getShortName();
        if (database == null) {
            version = 0;
        } else {
            try {
                version = database.getDatabaseMajorVersion();
            }
            catch (Exception e) {
                version = 0;
            }
        }
        String key = statement.getClass().getName() + ":" + databaseName + ":" + version;
        if (this.generatorsByKey.containsKey(key) && !this.generatorsByKey.get(key).isEmpty()) {
            TreeSet<SqlGenerator> result = new TreeSet<SqlGenerator>(new SqlGeneratorComparator());
            result.addAll((Collection<SqlGenerator>)this.generatorsByKey.get(key));
            result.retainAll(this.getGenerators());
            return result;
        }
        TreeSet<SqlGenerator> validGenerators = new TreeSet<SqlGenerator>(new SqlGeneratorComparator());
        for (SqlGenerator generator : this.getGenerators()) {
            Type classType = null;
            for (Class<?> clazz = generator.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                if (classType instanceof ParameterizedType) {
                    this.checkType(classType, statement, generator, database, validGenerators);
                }
                for (Type type : this.getGenericInterfaces(clazz)) {
                    if (type instanceof ParameterizedType) {
                        this.checkType(type, statement, generator, database, validGenerators);
                        continue;
                    }
                    if (!this.isTypeEqual(type, SqlGenerator.class) || !generator.supports(statement, database)) continue;
                    validGenerators.add(generator);
                }
                classType = this.getGenericSuperclass(clazz);
            }
        }
        this.generatorsByKey.put(key, validGenerators);
        return validGenerators;
    }

    private Type[] getGenericInterfaces(Class<?> clazz) {
        if (this.genericInterfacesCache.containsKey(clazz)) {
            return this.genericInterfacesCache.get(clazz);
        }
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        this.genericInterfacesCache.put(clazz, genericInterfaces);
        return genericInterfaces;
    }

    private Type getGenericSuperclass(Class<?> clazz) {
        if (this.genericSuperClassCache.containsKey(clazz)) {
            return this.genericSuperClassCache.get(clazz);
        }
        Type genericSuperclass = clazz.getGenericSuperclass();
        this.genericSuperClassCache.put(clazz, genericSuperclass);
        return genericSuperclass;
    }

    private boolean isTypeEqual(Type aType, Class aClass) {
        if (aType instanceof Class) {
            return ((Class)aType).isAssignableFrom(aClass);
        }
        return aType.equals(aClass);
    }

    private void checkType(Type type, SqlStatement statement, SqlGenerator generator, Database database, SortedSet<SqlGenerator> validGenerators) {
        for (Type typeClass : ((ParameterizedType)type).getActualTypeArguments()) {
            if (typeClass instanceof TypeVariable) {
                typeClass = ((TypeVariable)typeClass).getBounds()[0];
            }
            if (this.isTypeEqual(typeClass, SqlStatement.class)) {
                return;
            }
            if (!((Class)typeClass).isAssignableFrom(statement.getClass()) || !generator.supports(statement, database)) continue;
            validGenerators.add(generator);
        }
    }

    private SqlGeneratorChain createGeneratorChain(SqlStatement statement, Database database) {
        SortedSet sqlGenerators = this.getGenerators(statement, database);
        if (sqlGenerators == null || sqlGenerators.isEmpty()) {
            return null;
        }
        return new SqlGeneratorChain(sqlGenerators);
    }

    public Sql[] generateSql(Change change, Database database) {
        SqlStatement[] sqlStatements = change.generateStatements(database);
        if (sqlStatements == null) {
            return SqlGenerator.EMPTY_SQL;
        }
        return this.generateSql(sqlStatements, database);
    }

    public Sql[] generateSql(SqlStatement[] statements, Database database) {
        ArrayList<Sql> returnList = new ArrayList<Sql>();
        SqlGeneratorFactory factory2 = SqlGeneratorFactory.getInstance();
        for (SqlStatement statement : statements) {
            Sql[] sqlArray = factory2.generateSql(statement, database);
            if (sqlArray == null || sqlArray.length <= 0) continue;
            List<Sql> sqlList = Arrays.asList(sqlArray);
            returnList.addAll(sqlList);
        }
        return this.putSqlArrayInScope(returnList.toArray(SqlGenerator.EMPTY_SQL));
    }

    public Sql[] generateSql(SqlStatement statement, Database database) {
        SqlGeneratorChain generatorChain = this.createGeneratorChain(statement, database);
        if (generatorChain == null) {
            throw new IllegalStateException("Cannot find generators for database " + database.getClass() + ", statement: " + statement + ". Either Liquibase or the database platform does not support the type of statement being generated. Please check your database documentation for more information.");
        }
        return this.putSqlArrayInScope(generatorChain.generateSql(statement, database));
    }

    private Sql[] putSqlArrayInScope(Sql[] sqls) {
        AtomicReference sqlsReference = (AtomicReference)((Object)Scope.getCurrentScope().get(GENERATED_SQL_ARRAY_SCOPE_KEY, AtomicReference.class));
        if (sqlsReference != null) {
            sqlsReference.set(sqls);
        }
        return sqls;
    }

    public boolean generateStatementsVolatile(SqlStatement statement, Database database) {
        for (SqlGenerator generator : this.getGenerators(statement, database)) {
            if (!generator.generateStatementsIsVolatile(database)) continue;
            return true;
        }
        return false;
    }

    public boolean generateRollbackStatementsVolatile(SqlStatement statement, Database database) {
        for (SqlGenerator generator : this.getGenerators(statement, database)) {
            if (!generator.generateRollbackStatementsIsVolatile(database)) continue;
            return true;
        }
        return false;
    }

    public boolean supports(SqlStatement statement, Database database) {
        return !this.getGenerators(statement, database).isEmpty();
    }

    public ValidationErrors validate(SqlStatement statement, Database database) {
        SqlGeneratorChain generatorChain = this.createGeneratorChain(statement, database);
        if (generatorChain == null) {
            throw new UnexpectedLiquibaseException("Unable to create generator chain for " + statement.getClass().getName() + " on " + database.getShortName());
        }
        return generatorChain.validate(statement, database);
    }

    public Warnings warn(SqlStatement statement, Database database) {
        SqlGeneratorChain generatorChain = this.createGeneratorChain(statement, database);
        if (generatorChain != null) {
            return generatorChain.warn(statement, database);
        }
        return new Warnings().addWarning("No generator chain created for SQL Statement associated with database " + database.getShortName());
    }

    public Set<DatabaseObject> getAffectedDatabaseObjects(SqlStatement statement, Database database) {
        Sql[] sqls;
        HashSet<DatabaseObject> affectedObjects = new HashSet<DatabaseObject>();
        SqlGeneratorChain sqlGeneratorChain = this.createGeneratorChain(statement, database);
        if (sqlGeneratorChain != null && (sqls = sqlGeneratorChain.generateSql(statement, database)) != null) {
            for (Sql sql : sqls) {
                affectedObjects.addAll(sql.getAffectedDatabaseObjects());
            }
        }
        return affectedObjects;
    }
}

