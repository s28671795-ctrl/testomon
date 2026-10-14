/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.change.AbstractSQLChange;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.DatabaseChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.DropIndexChange;
import liquibase.change.core.SQLFileChange;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

public class ValidatingVisitorUtil {
    private ValidatingVisitorUtil() {
    }

    public static boolean isChecksumIssue(ChangeSet changeSet, RanChangeSet ranChangeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        return ValidatingVisitorUtil.validateMongoDbExtensionIssue(changeSet, ranChangeSet, databaseChangeLog, database) || ValidatingVisitorUtil.validateAbstractSqlChangeV8ChecksumVariant(changeSet, ranChangeSet) || ValidatingVisitorUtil.validateCreateFunctionChangeV8ChecksumVariant(changeSet, ranChangeSet) || ValidatingVisitorUtil.validateSqlFileChangeAndExpandExpressions(changeSet, ranChangeSet, database);
    }

    private static boolean validateAbstractSqlChangeV8ChecksumVariant(ChangeSet changeSet, RanChangeSet ranChangeSet) {
        if (StringUtil.isNotEmpty(changeSet.getRunWith()) && ChecksumVersion.V8.lowerOrEqualThan(Scope.getCurrentScope().getChecksumVersion())) {
            List<AbstractSQLChange> changes = changeSet.getChanges().stream().filter(AbstractSQLChange.class::isInstance).map(c -> (AbstractSQLChange)c).collect(Collectors.toList());
            if (!changes.isEmpty()) {
                ValidatingVisitorUtil.revertIgnoreOriginalSplitStatementsFlag(changeSet, changes);
                boolean valid = changeSet.isCheckSumValid(ranChangeSet.getLastCheckSum());
                if (!valid) {
                    ValidatingVisitorUtil.revertIgnoreOriginalSplitStatementsFlag(changeSet, changes);
                }
                return valid;
            }
        }
        return false;
    }

    private static boolean validateCreateFunctionChangeV8ChecksumVariant(ChangeSet changeSet, RanChangeSet ranChangeSet) {
        List<Change> changes;
        if (ChecksumVersion.V8.lowerOrEqualThan(Scope.getCurrentScope().getChecksumVersion()) && !(changes = changeSet.getChanges().stream().filter(c -> c.getClass().getTypeName().equals("com.datical.liquibase.ext.storedlogic.function.change.CreateFunctionChange")).collect(Collectors.toList())).isEmpty() && ValidatingVisitorUtil.checkLiquibaseVersionIs(ranChangeSet.getLiquibaseVersion(), 4, 21)) {
            ValidatingVisitorUtil.setUp421xChecksumFlagForCreateFunctionChange(changes, true);
            changeSet.clearCheckSum();
            boolean valid = changeSet.isCheckSumValid(ranChangeSet.getLastCheckSum());
            if (!valid) {
                ValidatingVisitorUtil.setUp421xChecksumFlagForCreateFunctionChange(changes, false);
            }
            return valid;
        }
        return false;
    }

    private static boolean checkLiquibaseVersionIs(String version, int major, int minor) {
        String[] liquibaseVersion = version.split("\\.");
        try {
            return liquibaseVersion.length == 3 && Integer.parseInt(liquibaseVersion[0]) == major && Integer.parseInt(liquibaseVersion[1]) == minor;
        }
        catch (NumberFormatException ne) {
            return false;
        }
    }

    private static boolean checkLiquibaseVersionMinorThan(String version, int major, int minor) {
        if (StringUtil.isEmpty(version)) {
            return false;
        }
        String[] liquibaseVersion = version.split("\\.");
        try {
            return liquibaseVersion.length == 3 && Integer.parseInt(liquibaseVersion[0]) == major && Integer.parseInt(liquibaseVersion[1]) < minor;
        }
        catch (NumberFormatException ne) {
            return false;
        }
    }

    private static void setUp421xChecksumFlagForCreateFunctionChange(List<Change> changes, boolean set) {
        changes.forEach(change -> {
            try {
                change.getClass().getMethod("setUseChecksumV8ForLiquibase421x", Boolean.TYPE).invoke(change, set);
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Scope.getCurrentScope().getLog(ValidatingVisitorUtil.class).severe("Commercial jar version doesn't provide method setUseChecksumV8ForLiquibase421x method for CreateFunctionChange. Make sure that you are using a commercial jar version compatible with this core version.", e);
            }
        });
    }

    private static void revertIgnoreOriginalSplitStatementsFlag(ChangeSet changeSet, List<AbstractSQLChange> changes) {
        changes.forEach(change -> change.setIgnoreOriginalSplitStatements(!BooleanUtil.isTrue(change.isIgnoreOriginalSplitStatements())));
        changeSet.clearCheckSum();
    }

    private static boolean validateMongoDbExtensionIssue(ChangeSet changeSet, RanChangeSet ranChangeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        Optional<Change> change = changeSet.getChanges().stream().filter(c -> c instanceof CreateIndexChange || c instanceof DropIndexChange).findFirst();
        if (change.isPresent() && !database.getShortName().equals("mongodb")) {
            try {
                ChangeFactory changeFactory = Scope.getCurrentScope().getSingleton(ChangeFactory.class);
                changeFactory.setPerformSupportsDatabaseValidation(false);
                DatabaseChange databaseChange = change.get().getClass().getAnnotation(DatabaseChange.class);
                Change newChange = changeFactory.create(databaseChange.name());
                if (newChange.getClass().getTypeName().equalsIgnoreCase("liquibase.ext.mongodb.change." + databaseChange.name() + "Change")) {
                    ChangeSet newChangeset = ValidatingVisitorUtil.generateNewChangeSet(databaseChangeLog, change.get(), newChange, changeSet);
                    if (newChangeset.isCheckSumValid(ranChangeSet.getLastCheckSum())) {
                        ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
                        changeLogService.replaceChecksum(changeSet);
                        boolean bl = true;
                        return bl;
                    }
                    changeSet.clearCheckSum();
                }
            }
            catch (DatabaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
            finally {
                Scope.getCurrentScope().getSingleton(ChangeFactory.class).setPerformSupportsDatabaseValidation(true);
            }
        }
        return false;
    }

    private static ChangeSet generateNewChangeSet(DatabaseChangeLog databaseChangeLog, Change originalChange, Change newChange, ChangeSet changeSet) {
        ChangeSet newChangeset = new ChangeSet(changeSet.getId(), changeSet.getAuthor(), changeSet.shouldAlwaysRun(), changeSet.isRunOnChange(), changeSet.getFilePath(), null, null, databaseChangeLog);
        for (Change c : changeSet.getChanges()) {
            if (!originalChange.getClass().isInstance(c)) {
                newChangeset.addChange(c);
                continue;
            }
            newChangeset.addChange(newChange);
        }
        return newChangeset;
    }

    private static boolean validateSqlFileChangeAndExpandExpressions(ChangeSet changeSet, RanChangeSet ranChangeSet, Database database) {
        List changes = changeSet.getChanges().stream().filter(SQLFileChange.class::isInstance).map(c -> (SQLFileChange)c).collect(Collectors.toList());
        if (changes.isEmpty() || !ValidatingVisitorUtil.checkLiquibaseVersionMinorThan(ranChangeSet.getLiquibaseVersion(), 4, 26)) {
            return false;
        }
        try {
            for (Object change : changes) {
                ((SQLFileChange)change).setDoExpandExpressionsInGenerateChecksum(true);
            }
            changeSet.clearCheckSum();
            boolean valid = changeSet.isCheckSumValid(ranChangeSet.getLastCheckSum());
            for (SQLFileChange change : changes) {
                change.setDoExpandExpressionsInGenerateChecksum(false);
            }
            changeSet.clearCheckSum();
            if (valid) {
                ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
                changeLogService.replaceChecksum(changeSet);
            }
            return valid;
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

