/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.ValidationErrors;
import liquibase.util.StringUtil;

public class DatabaseList {
    public static boolean definitionMatches(String definition, String databaseShortName, boolean returnValueIfEmpty) {
        return DatabaseList.definitionMatches(StringUtil.splitAndTrim(StringUtil.trimToNull(definition), ","), databaseShortName, returnValueIfEmpty);
    }

    public static boolean definitionMatches(String definition, Database database, boolean returnValueIfEmpty) {
        return DatabaseList.definitionMatches(StringUtil.splitAndTrim(StringUtil.trimToNull(definition), ","), database, returnValueIfEmpty);
    }

    public static boolean definitionMatches(Collection<String> definition, String databaseShortName, boolean returnValueIfEmptyList) {
        if (definition == null || definition.isEmpty()) {
            return returnValueIfEmptyList;
        }
        if (definition.contains("all")) {
            return true;
        }
        if (definition.contains("none")) {
            return false;
        }
        if (definition.contains("!" + databaseShortName)) {
            return false;
        }
        HashSet<String> dbmsSupported = new HashSet<String>();
        for (String dbms : definition) {
            if (dbms.startsWith("!")) continue;
            dbmsSupported.add(dbms);
        }
        return dbmsSupported.isEmpty() || dbmsSupported.contains(databaseShortName);
    }

    public static boolean definitionMatches(Collection<String> definition, Database database, boolean returnValueIfEmptyList) {
        String shortName = database == null ? "null" : database.getShortName();
        return DatabaseList.definitionMatches(definition, shortName, returnValueIfEmptyList);
    }

    public static Set<String> toDbmsSet(String dbmsList) {
        HashSet<String> dbmsSet = null;
        if (StringUtil.trimToNull(dbmsList) != null) {
            dbmsSet = new HashSet<String>();
            for (String string : dbmsList.toLowerCase().split(",")) {
                dbmsSet.add(string.trim());
            }
        }
        return dbmsSet;
    }

    public static void validateDefinitions(String definition, ValidationErrors vErrors) {
        Set<String> dbmsSet = DatabaseList.toDbmsSet(definition);
        if (dbmsSet != null) {
            DatabaseList.validateDefinitions(dbmsSet, vErrors);
        }
    }

    public static void validateDefinitions(Collection<String> definitions, ValidationErrors vErrors) {
        definitions.stream().forEach(definition -> DatabaseList.validateDefinition(definition, vErrors));
    }

    private static void validateDefinition(String definition, ValidationErrors vErrors) {
        Database database;
        if (!(definition.contentEquals("none") || definition.contentEquals("all") || definition.startsWith("!") || (database = DatabaseFactory.getInstance().getDatabase(definition.toLowerCase())) != null)) {
            vErrors.addError(String.format("%s is not a supported DB", definition));
        }
    }
}

