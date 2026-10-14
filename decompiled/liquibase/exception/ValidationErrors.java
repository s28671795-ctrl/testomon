/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.precondition.Precondition;
import liquibase.util.StringUtil;

public class ValidationErrors {
    protected List<String> errorMessages = new ArrayList<String>();
    protected List<String> warningMessages = new ArrayList<String>();
    protected String change = null;

    public boolean hasErrors() {
        return !this.errorMessages.isEmpty();
    }

    public ValidationErrors() {
    }

    public ValidationErrors(String change) {
        this.change = change;
    }

    public ValidationErrors(Change change) {
        this.change = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getName();
    }

    public ValidationErrors(Precondition precondition) {
        this.change = precondition.getName();
    }

    public String getChangeName() {
        return this.change;
    }

    public ValidationErrors checkRequiredField(String requiredFieldName, Object value) {
        return this.checkRequiredField(requiredFieldName, value, false);
    }

    public ValidationErrors checkRequiredField(String requiredFieldName, Object value, boolean allowEmptyValue) {
        return this.checkRequiredField(requiredFieldName, value, null, allowEmptyValue);
    }

    public ValidationErrors checkRequiredField(String requiredFieldName, Object value, String postfix) {
        return this.checkRequiredField(requiredFieldName, value, postfix, false);
    }

    public ValidationErrors checkRequiredField(String requiredFieldName, Object value, String postfix, boolean allowEmptyValue) {
        String err = null;
        if (value == null) {
            err = requiredFieldName + " is required";
        }
        if (!allowEmptyValue) {
            if (value instanceof Collection && ((Collection)value).isEmpty() || value instanceof Object[] && ((Object[])value).length == 0) {
                err = "No " + requiredFieldName + " defined";
            } else if (value instanceof String && StringUtil.trimToNull((String)value) == null) {
                err = requiredFieldName + " is empty";
            }
        }
        if (err != null) {
            this.addError(err + (this.change == null ? "" : " for " + this.change) + (postfix == null ? "" : postfix));
        }
        return this;
    }

    @SafeVarargs
    public final void checkDisallowedField(String disallowedFieldName, Object value, Database database, Class<? extends Database> ... disallowedDatabases) {
        boolean isDisallowed = false;
        if (disallowedDatabases == null || disallowedDatabases.length == 0) {
            isDisallowed = true;
        } else {
            for (Class<? extends Database> databaseClass : disallowedDatabases) {
                if (!databaseClass.isAssignableFrom(database.getClass())) continue;
                isDisallowed = true;
            }
        }
        if (isDisallowed && value != null) {
            this.addError(disallowedFieldName + " is not allowed on " + (database == null ? "unknown" : database.getShortName()));
        }
    }

    public ValidationErrors addError(String message) {
        this.errorMessages.add(message);
        return this;
    }

    public ValidationErrors addError(String message, ChangeSet changeSet) {
        this.errorMessages.add(message + ", " + changeSet);
        return this;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }

    public ValidationErrors addWarning(String message) {
        this.warningMessages.add(message);
        return this;
    }

    public List<String> getWarningMessages() {
        return this.warningMessages;
    }

    public ValidationErrors addAll(ValidationErrors validationErrors) {
        if (validationErrors == null) {
            return this;
        }
        this.errorMessages.addAll(validationErrors.getErrorMessages());
        return this;
    }

    public void addAll(ValidationErrors validationErrors, ChangeSet changeSet) {
        for (String message : validationErrors.getErrorMessages()) {
            this.addError(message, changeSet);
        }
        for (String message : validationErrors.getWarningMessages()) {
            this.warningMessages.add(message + ", " + changeSet);
        }
    }

    public String toString() {
        if (this.getErrorMessages().isEmpty()) {
            return "No errors";
        }
        return StringUtil.join(this.getErrorMessages(), "; ");
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ValidationErrors)) {
            return false;
        }
        return this.toString().equals(obj.toString());
    }

    public List<String> getRequiredErrorMessages() {
        ArrayList<String> requiredErrorMessages = new ArrayList<String>();
        for (String message : this.errorMessages) {
            if (!message.contains("is required")) continue;
            requiredErrorMessages.add(message);
        }
        return Collections.unmodifiableList(requiredErrorMessages);
    }

    public List<String> getUnsupportedErrorMessages() {
        ArrayList<String> unsupportedErrorMessages = new ArrayList<String>();
        for (String message : this.errorMessages) {
            if (!message.contains(" is not allowed on ")) continue;
            unsupportedErrorMessages.add(message);
        }
        return Collections.unmodifiableList(unsupportedErrorMessages);
    }
}

