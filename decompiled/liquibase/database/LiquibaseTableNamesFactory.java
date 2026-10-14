/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.database.Database;
import liquibase.database.LiquibaseTableNames;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.ServiceLocator;

public class LiquibaseTableNamesFactory
implements SingletonObject {
    private final SortedSet<LiquibaseTableNames> generators;

    private LiquibaseTableNamesFactory() {
        ServiceLocator serviceLocator = Scope.getCurrentScope().getServiceLocator();
        this.generators = new TreeSet<LiquibaseTableNames>(Comparator.comparingInt(LiquibaseTableNames::getOrder));
        this.generators.addAll(serviceLocator.findInstances(LiquibaseTableNames.class));
    }

    public List<String> getLiquibaseTableNames(Database database) {
        return this.generators.stream().flatMap(f -> f.getLiquibaseGeneratedTableNames(database).stream()).collect(Collectors.toList());
    }

    public void destroy(Database abstractJdbcDatabase) throws DatabaseException {
        for (LiquibaseTableNames generator : this.generators) {
            generator.destroy(abstractJdbcDatabase);
            abstractJdbcDatabase.commit();
        }
    }
}

