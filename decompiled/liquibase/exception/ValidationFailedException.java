/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.io.PrintStream;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.visitor.ValidatingVisitor;
import liquibase.exception.MigrationFailedException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.precondition.ErrorPrecondition;
import liquibase.precondition.FailedPrecondition;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public class ValidationFailedException
extends MigrationFailedException {
    private static final long serialVersionUID = -6824856974397660436L;
    public static final String INDENT_SPACES = "     ";
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    private final List<String> invalidMD5Sums;
    private final String failedPreconditionsMessage;
    private final List<FailedPrecondition> failedPreconditions;
    private final String errorPreconditionMessage;
    private final List<ErrorPrecondition> errorPreconditions;
    private final Set<ChangeSet> duplicateChangeSets;
    private final List<SetupException> setupExceptions;
    private final List<Throwable> changeValidationExceptions;
    private final ValidationErrors validationErrors;

    public ValidationFailedException(ValidatingVisitor changeLogHandler) {
        this.invalidMD5Sums = changeLogHandler.getInvalidMD5Sums();
        this.failedPreconditionsMessage = changeLogHandler.getFailedPreconditionsMessage();
        this.failedPreconditions = changeLogHandler.getFailedPreconditions();
        this.errorPreconditionMessage = changeLogHandler.getErrorPreconditionsMessage();
        this.errorPreconditions = changeLogHandler.getErrorPreconditions();
        this.duplicateChangeSets = changeLogHandler.getDuplicateChangeSets();
        this.setupExceptions = changeLogHandler.getSetupExceptions();
        this.changeValidationExceptions = changeLogHandler.getChangeValidationExceptions();
        this.validationErrors = changeLogHandler.getValidationErrors();
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder();
        String separator = StreamUtil.getLineSeparator();
        message.append(coreBundle.getString("validation.failed")).append(separator);
        if (!this.invalidMD5Sums.isEmpty()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("check.sum.changed"), this.invalidMD5Sums.size())).append(separator);
            for (int i = 0; i < this.invalidMD5Sums.size() && i <= 25; ++i) {
                message.append("          ").append(this.invalidMD5Sums.get(i));
                message.append(separator);
            }
        }
        if (!this.failedPreconditions.isEmpty()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("preconditions.failed"), this.failedPreconditions.size())).append(separator);
            if (StringUtil.isNotEmpty(this.failedPreconditionsMessage)) {
                message.append(INDENT_SPACES).append(this.failedPreconditionsMessage).append(separator);
            }
            for (FailedPrecondition failedPrecondition : this.failedPreconditions) {
                message.append(INDENT_SPACES).append(failedPrecondition.toString());
                message.append(separator);
            }
        }
        if (!this.errorPreconditions.isEmpty()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("preconditions.generated.error"), this.errorPreconditions.size())).append(separator);
            if (StringUtil.isNotEmpty(this.errorPreconditionMessage)) {
                message.append(INDENT_SPACES).append(this.errorPreconditionMessage).append(separator);
            }
            for (ErrorPrecondition errorPrecondition : this.errorPreconditions) {
                message.append(INDENT_SPACES).append(errorPrecondition.toString());
                message.append(separator);
            }
        }
        if (!this.duplicateChangeSets.isEmpty()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("change.sets.duplicate.identifiers"), this.duplicateChangeSets.size())).append(separator);
            for (ChangeSet changeSet : this.duplicateChangeSets) {
                message.append("          ").append(changeSet.toString(false));
                message.append(separator);
            }
        }
        if (!this.setupExceptions.isEmpty()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("changes.have.failures"), this.setupExceptions.size())).append(separator);
            for (SetupException setupException : this.setupExceptions) {
                message.append("          ").append(setupException.toString());
                message.append(separator);
            }
        }
        if (!this.changeValidationExceptions.isEmpty()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("changes.have.validation.errors"), this.changeValidationExceptions.size())).append(separator);
            for (Throwable throwable : this.changeValidationExceptions) {
                Scope.getCurrentScope().getLog(this.getClass()).fine(coreBundle.getString("validation.exception"), throwable);
                message.append("          ").append(throwable.toString());
                message.append(separator);
            }
        }
        if (this.validationErrors.hasErrors()) {
            message.append(INDENT_SPACES).append(String.format(coreBundle.getString("changes.have.validation.failures"), this.validationErrors.getErrorMessages().size())).append(separator);
            for (String string : this.validationErrors.getErrorMessages()) {
                message.append("          ").append(string);
                message.append(separator);
            }
        }
        return message.toString();
    }

    public List<String> getInvalidMD5Sums() {
        return this.invalidMD5Sums;
    }

    public void printDescriptiveError(PrintStream out) {
        out.println("Validation Error: ");
        if (!this.invalidMD5Sums.isEmpty()) {
            out.println(INDENT_SPACES + this.invalidMD5Sums.size() + " changesets have changed since they were ran against the database");
            for (String message : this.invalidMD5Sums) {
                out.println("          " + message);
            }
        }
        if (!this.failedPreconditions.isEmpty()) {
            out.println(INDENT_SPACES + this.failedPreconditions.size() + " preconditions failed");
            for (FailedPrecondition failedPrecondition : this.failedPreconditions) {
                out.println("          " + failedPrecondition.toString());
            }
        }
        if (!this.errorPreconditions.isEmpty()) {
            out.println(INDENT_SPACES + this.errorPreconditions.size() + " preconditions generated an error");
            for (ErrorPrecondition errorPrecondition : this.errorPreconditions) {
                out.println("          " + errorPrecondition.toString());
            }
        }
        if (!this.duplicateChangeSets.isEmpty()) {
            out.println(INDENT_SPACES + this.duplicateChangeSets.size() + " changesets had duplicate identifiers");
            for (ChangeSet duplicate : this.duplicateChangeSets) {
                out.println("          " + duplicate.toString(false));
            }
        }
        if (!this.setupExceptions.isEmpty()) {
            out.println(INDENT_SPACES + this.setupExceptions.size() + " changes had errors");
            for (SetupException setupEx : this.setupExceptions) {
                out.println("          " + setupEx.getMessage());
            }
        }
    }
}

