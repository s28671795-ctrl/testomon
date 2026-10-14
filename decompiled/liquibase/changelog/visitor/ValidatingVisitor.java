/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import liquibase.ChecksumVersion;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.ErrorPrecondition;
import liquibase.precondition.FailedPrecondition;
import liquibase.precondition.core.PreconditionContainer;
import liquibase.util.StringUtil;
import liquibase.util.ValidatingVisitorUtil;

public class ValidatingVisitor
implements ChangeSetVisitor {
    private final List<String> invalidMD5Sums = new ArrayList<String>();
    private String failedPreconditionsMessage = null;
    private String errorPreconditionsMessage = null;
    private final List<FailedPrecondition> failedPreconditions = new ArrayList<FailedPrecondition>();
    private final List<ErrorPrecondition> errorPreconditions = new ArrayList<ErrorPrecondition>();
    private final Set<ChangeSet> duplicateChangeSets = new LinkedHashSet<ChangeSet>();
    private final List<SetupException> setupExceptions = new ArrayList<SetupException>();
    private final List<Throwable> changeValidationExceptions = new ArrayList<Throwable>();
    private final ValidationErrors validationErrors = new ValidationErrors();
    private final Warnings warnings = new Warnings();
    private final Set<String> seenChangeSets = new HashSet<String>();
    private Map<String, RanChangeSet> ranIndex;
    private Database database;

    public ValidatingVisitor() {
    }

    public ValidatingVisitor(List<RanChangeSet> ranChangeSets) {
        this.ranIndex = new HashMap<String, RanChangeSet>();
        for (RanChangeSet changeSet : ranChangeSets) {
            this.ranIndex.put(changeSet.toString(), changeSet);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void validate(Database database, DatabaseChangeLog changeLog) {
        this.database = database;
        PreconditionContainer preconditions = changeLog.getPreconditions();
        try {
            if (preconditions == null) {
                return;
            }
            ValidationErrors foundErrors = preconditions.validate(database);
            if (foundErrors.hasErrors()) {
                this.validationErrors.addAll(foundErrors);
            } else {
                preconditions.check(database, changeLog, null, null);
            }
        }
        catch (PreconditionFailedException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Precondition Failed: " + e.getMessage(), e);
            this.failedPreconditionsMessage = e.getMessage();
            this.failedPreconditions.addAll(e.getFailedPreconditions());
        }
        catch (PreconditionErrorException e) {
            Scope.getCurrentScope().getLog(this.getClass()).severe("Precondition Error: " + e.getMessage(), e);
            this.errorPreconditionsMessage = e.getMessage();
            this.errorPreconditions.addAll(e.getErrorPreconditions());
        }
        finally {
            try {
                if (database.getConnection() != null) {
                    database.rollback();
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Error rolling back after precondition check", e);
            }
        }
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    private RanChangeSet findChangeSet(ChangeSet changeSet) {
        String key = changeSet.toNormalizedString();
        return this.ranIndex.get(key);
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        String changeSetString;
        boolean shouldValidate;
        if (changeSet.isIgnore()) {
            Scope.getCurrentScope().getLog(ValidatingVisitor.class).info("Not validating ignored change set '" + changeSet.toString() + "'");
            return;
        }
        RanChangeSet ranChangeSet = this.findChangeSet(changeSet);
        boolean ran = ranChangeSet != null;
        Set<String> dbmsSet = changeSet.getDbmsSet();
        if (dbmsSet != null) {
            DatabaseList.validateDefinitions(changeSet.getDbmsSet(), this.validationErrors);
        }
        changeSet.setStoredCheckSum(ran ? ranChangeSet.getLastCheckSum() : null);
        changeSet.setStoredFilePath(ran ? ranChangeSet.getStoredChangeLog() : null);
        boolean bl = shouldValidate = !ran || changeSet.shouldRunOnChange() || changeSet.shouldAlwaysRun();
        if (!this.areChangeSetAttributesValid(changeSet)) {
            changeSet.setValidationFailed(true);
            shouldValidate = false;
        }
        for (Change change : changeSet.getChanges()) {
            this.validateChange(changeSet, database, change, shouldValidate);
        }
        this.additionalValidations(changeSet, database, shouldValidate, ran);
        if (!(ranChangeSet == null || changeSet.isCheckSumValid(ranChangeSet.getLastCheckSum()) || ValidatingVisitorUtil.isChecksumIssue(changeSet, ranChangeSet, databaseChangeLog, database) || changeSet.shouldRunOnChange() || changeSet.shouldAlwaysRun())) {
            this.invalidMD5Sums.add(changeSet.toString(false) + " was: " + ranChangeSet.getLastCheckSum().toString() + " but is now: " + changeSet.generateCheckSum(ChecksumVersion.enumFromChecksumVersion(ranChangeSet.getLastCheckSum().getVersion())).toString());
        }
        if (this.seenChangeSets.contains(changeSetString = changeSet.toString(false))) {
            this.duplicateChangeSets.add(changeSet);
        } else {
            this.seenChangeSets.add(changeSetString);
        }
    }

    protected void additionalValidations(ChangeSet changeSet, Database database, boolean shouldValidate, boolean ran) {
    }

    protected void validateChange(ChangeSet changeSet, Database database, Change change, boolean shouldValidate) {
        try {
            change.finishInitialization();
        }
        catch (SetupException se) {
            this.setupExceptions.add(se);
        }
        if (shouldValidate) {
            this.warnings.addAll(change.warn(database));
            try {
                ValidationErrors foundErrors = change.validate(database);
                if (foundErrors != null) {
                    if (foundErrors.hasErrors() && changeSet.getOnValidationFail().equals((Object)ChangeSet.ValidationFailOption.MARK_RAN)) {
                        Scope.getCurrentScope().getLog(this.getClass()).info("Skipping changeset " + changeSet + " due to validation error(s): " + StringUtil.join(foundErrors.getErrorMessages(), ", "));
                        changeSet.setValidationFailed(true);
                    } else {
                        if (!foundErrors.getWarningMessages().isEmpty()) {
                            Scope.getCurrentScope().getLog(this.getClass()).warning("Changeset " + changeSet + ": " + StringUtil.join(foundErrors.getWarningMessages(), ", "));
                        }
                        this.validationErrors.addAll(foundErrors, changeSet);
                    }
                }
            }
            catch (Exception e) {
                this.changeValidationExceptions.add(e);
            }
        }
    }

    private boolean areChangeSetAttributesValid(ChangeSet changeSet) {
        boolean authorEmpty = StringUtil.isEmpty(changeSet.getAuthor());
        boolean idEmpty = StringUtil.isEmpty(changeSet.getId());
        boolean strictCurrentValue = GlobalConfiguration.STRICT.getCurrentValue();
        boolean valid = false;
        if (authorEmpty && idEmpty) {
            this.validationErrors.addError("ChangeSet Id and Author are empty", changeSet);
        } else if (authorEmpty && strictCurrentValue) {
            this.validationErrors.addError("ChangeSet Author is empty", changeSet);
        } else if (idEmpty) {
            this.validationErrors.addError("ChangeSet Id is empty", changeSet);
        } else {
            valid = true;
        }
        return valid;
    }

    public boolean validationPassed() {
        return this.invalidMD5Sums.isEmpty() && this.failedPreconditions.isEmpty() && this.errorPreconditions.isEmpty() && this.duplicateChangeSets.isEmpty() && this.changeValidationExceptions.isEmpty() && this.setupExceptions.isEmpty() && !this.validationErrors.hasErrors();
    }

    public List<String> getInvalidMD5Sums() {
        return this.invalidMD5Sums;
    }

    public String getFailedPreconditionsMessage() {
        return this.failedPreconditionsMessage;
    }

    public String getErrorPreconditionsMessage() {
        return this.errorPreconditionsMessage;
    }

    public List<FailedPrecondition> getFailedPreconditions() {
        return this.failedPreconditions;
    }

    public List<ErrorPrecondition> getErrorPreconditions() {
        return this.errorPreconditions;
    }

    public Set<ChangeSet> getDuplicateChangeSets() {
        return this.duplicateChangeSets;
    }

    public List<SetupException> getSetupExceptions() {
        return this.setupExceptions;
    }

    public List<Throwable> getChangeValidationExceptions() {
        return this.changeValidationExceptions;
    }

    public ValidationErrors getValidationErrors() {
        return this.validationErrors;
    }

    public Warnings getWarnings() {
        return this.warnings;
    }

    public Set<String> getSeenChangeSets() {
        return this.seenChangeSets;
    }

    public Map<String, RanChangeSet> getRanIndex() {
        return this.ranIndex;
    }

    public Database getDatabase() {
        return this.database;
    }
}

