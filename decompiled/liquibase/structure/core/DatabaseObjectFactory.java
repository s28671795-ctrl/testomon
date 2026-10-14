/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import liquibase.Scope;
import liquibase.changelog.column.LiquibaseColumn;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.DatabaseObject;
import liquibase.util.StringUtil;

public class DatabaseObjectFactory {
    private static DatabaseObjectFactory instance;
    private Set<Class<? extends DatabaseObject>> standardTypes;

    public static synchronized DatabaseObjectFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseObjectFactory();
        }
        return instance;
    }

    private DatabaseObjectFactory() {
    }

    public Set<Class<? extends DatabaseObject>> parseTypes(String typesString) {
        if (StringUtil.trimToNull(typesString) == null) {
            return this.getStandardTypes();
        }
        HashSet<Class<? extends DatabaseObject>> returnSet = new HashSet<Class<? extends DatabaseObject>>();
        HashSet<String> typesToInclude = new HashSet<String>(Arrays.asList(typesString.toLowerCase().split("\\s*,\\s*")));
        HashSet<String> typesNotFound = new HashSet<String>(typesToInclude);
        for (DatabaseObject object : Scope.getCurrentScope().getServiceLocator().findInstances(DatabaseObject.class)) {
            Class<?> clazz = object.getClass();
            if (!typesToInclude.contains(clazz.getSimpleName().toLowerCase()) && !typesToInclude.contains(clazz.getSimpleName().toLowerCase() + "s") && !typesToInclude.contains(clazz.getSimpleName().toLowerCase() + "es")) continue;
            returnSet.add(clazz);
            typesNotFound.remove(clazz.getSimpleName().toLowerCase());
            typesNotFound.remove(clazz.getSimpleName().toLowerCase() + "s");
            typesNotFound.remove(clazz.getSimpleName().toLowerCase() + "es");
        }
        if (!typesNotFound.isEmpty()) {
            throw new UnexpectedLiquibaseException("Unknown snapshot type(s) " + StringUtil.join(typesNotFound, ", "));
        }
        return returnSet;
    }

    public Set<Class<? extends DatabaseObject>> getStandardTypes() {
        if (this.standardTypes == null) {
            HashSet<Class<? extends DatabaseObject>> set = new HashSet<Class<? extends DatabaseObject>>();
            for (DatabaseObject databaseObject : Scope.getCurrentScope().getServiceLocator().findInstances(DatabaseObject.class)) {
                if (databaseObject.getClass().equals(LiquibaseColumn.class) || !databaseObject.snapshotByDefault()) continue;
                set.add(databaseObject.getClass());
            }
            this.standardTypes = set;
        }
        return this.standardTypes;
    }

    public void reset() {
        this.standardTypes = null;
    }
}

