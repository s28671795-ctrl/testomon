/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import java.util.ArrayList;
import liquibase.Scope;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.precondition.ErrorPrecondition;
import liquibase.precondition.FailedPrecondition;
import liquibase.precondition.core.AndPrecondition;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public class PreconditionContainer
extends AndPrecondition
implements ChangeLogChild {
    private FailOption onFail = FailOption.HALT;
    private ErrorOption onError = ErrorOption.HALT;
    private OnSqlOutputOption onSqlOutput = OnSqlOutputOption.IGNORE;
    private String onFailMessage;
    private String onErrorMessage;

    public FailOption getOnFail() {
        return this.onFail;
    }

    public void setOnFail(String onFail) {
        if (onFail != null) {
            for (FailOption option : FailOption.values()) {
                if (!option.key.equalsIgnoreCase(onFail)) continue;
                this.onFail = option;
                return;
            }
            ArrayList<String> possibleOptions = new ArrayList<String>();
            for (FailOption option : FailOption.values()) {
                possibleOptions.add(option.key);
            }
            throw new RuntimeException("Unknown onFail attribute value '" + onFail + "'.  Possible values: " + StringUtil.join(possibleOptions, ", "));
        }
        this.onFail = FailOption.HALT;
    }

    public void setOnFail(FailOption onFail) {
        this.onFail = onFail;
    }

    public ErrorOption getOnError() {
        return this.onError;
    }

    public void setOnError(String onError2) {
        if (onError2 != null) {
            for (ErrorOption option : ErrorOption.values()) {
                if (!option.key.equalsIgnoreCase(onError2)) continue;
                this.onError = option;
                return;
            }
            ArrayList<String> possibleOptions = new ArrayList<String>();
            for (ErrorOption option : ErrorOption.values()) {
                possibleOptions.add(option.key);
            }
            throw new RuntimeException("Unknown onError attribute value '" + onError2 + "'.  Possible values: " + StringUtil.join(possibleOptions, ", "));
        }
        this.onError = ErrorOption.HALT;
    }

    public void setOnError(ErrorOption onError2) {
        this.onError = onError2;
    }

    public OnSqlOutputOption getOnSqlOutput() {
        return this.onSqlOutput;
    }

    public void setOnSqlOutput(String onSqlOutput) {
        if (onSqlOutput == null) {
            this.setOnSqlOutput((OnSqlOutputOption)null);
            return;
        }
        for (OnSqlOutputOption option : OnSqlOutputOption.values()) {
            if (!option.key.equalsIgnoreCase(onSqlOutput)) continue;
            this.setOnSqlOutput(option);
            return;
        }
        ArrayList<String> possibleOptions = new ArrayList<String>();
        for (OnSqlOutputOption option : OnSqlOutputOption.values()) {
            possibleOptions.add(option.key);
        }
        throw new RuntimeException("Unknown onSqlOutput attribute value '" + onSqlOutput + "'.  Possible values: " + StringUtil.join(possibleOptions, ", "));
    }

    public void setOnSqlOutput(OnSqlOutputOption onSqlOutput) {
        this.onSqlOutput = onSqlOutput == null ? OnSqlOutputOption.IGNORE : onSqlOutput;
    }

    public String getOnFailMessage() {
        return this.onFailMessage;
    }

    public void setOnFailMessage(String onFailMessage) {
        this.onFailMessage = onFailMessage;
    }

    public String getOnErrorMessage() {
        return this.onErrorMessage;
    }

    public void setOnErrorMessage(String onErrorMessage) {
        this.onErrorMessage = onErrorMessage;
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        String ranOn = String.valueOf(changeLog);
        if (changeSet != null) {
            ranOn = String.valueOf(changeSet);
        }
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        try {
            boolean testPrecondition = false;
            if (executor2.updatesDatabase()) {
                testPrecondition = true;
            } else if (this.getOnSqlOutput().equals((Object)OnSqlOutputOption.TEST)) {
                testPrecondition = true;
            } else {
                if (this.getOnSqlOutput().equals((Object)OnSqlOutputOption.FAIL)) {
                    throw new PreconditionFailedException("Unexpected precondition in updateSQL mode with onUpdateSQL value: " + (Object)((Object)this.getOnSqlOutput()), changeLog, this);
                }
                if (this.getOnSqlOutput().equals((Object)OnSqlOutputOption.IGNORE)) {
                    testPrecondition = false;
                }
            }
            if (testPrecondition) {
                super.check(database, changeLog, changeSet, changeExecListener);
            }
        }
        catch (PreconditionFailedException e) {
            StringBuilder message = new StringBuilder();
            message.append("     ").append(e.getFailedPreconditions().size()).append(" preconditions failed").append(StreamUtil.getLineSeparator());
            for (FailedPrecondition invalid : e.getFailedPreconditions()) {
                message.append("     ").append(invalid.toString());
                message.append(StreamUtil.getLineSeparator());
            }
            if (this.getOnFailMessage() != null) {
                message = new StringBuilder(this.getOnFailMessage());
            }
            if (this.getOnFail().equals((Object)FailOption.WARN)) {
                String exceptionMessage = "Executing " + ranOn + " despite precondition failure due to onFail='WARN':\n " + message;
                Scope.getCurrentScope().getUI().sendMessage("WARNING: " + exceptionMessage);
                Scope.getCurrentScope().getLog(this.getClass()).warning(exceptionMessage);
                if (changeExecListener != null) {
                    changeExecListener.preconditionFailed(e, FailOption.WARN);
                }
            }
            if (this.getOnFailMessage() == null) {
                throw e;
            }
            throw new PreconditionFailedException(this.getOnFailMessage(), changeLog, this);
        }
        catch (PreconditionErrorException e) {
            StringBuilder message = new StringBuilder();
            message.append("     ").append(e.getErrorPreconditions().size()).append(" preconditions failed").append(StreamUtil.getLineSeparator());
            for (ErrorPrecondition invalid : e.getErrorPreconditions()) {
                message.append("     ").append(invalid.toString());
                message.append(StreamUtil.getLineSeparator());
            }
            if (this.getOnError().equals((Object)ErrorOption.CONTINUE)) {
                Scope.getCurrentScope().getLog(this.getClass()).info("Continuing past: " + this + " despite precondition error:\n " + message);
                throw e;
            }
            if (this.getOnError().equals((Object)ErrorOption.WARN)) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Continuing past: " + this + " despite precondition error:\n " + message);
                if (changeExecListener != null) {
                    changeExecListener.preconditionErrored(e, ErrorOption.WARN);
                }
            }
            if (this.getOnErrorMessage() == null) {
                throw e;
            }
            throw new PreconditionErrorException(this.getOnErrorMessage(), e.getErrorPreconditions());
        }
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        this.setOnError(parsedNode.getChildValue((String)null, "onError", String.class));
        this.setOnErrorMessage(parsedNode.getChildValue((String)null, "onErrorMessage", String.class));
        this.setOnFail(parsedNode.getChildValue((String)null, "onFail", String.class));
        this.setOnFailMessage(parsedNode.getChildValue((String)null, "onFailMessage", String.class));
        this.setOnSqlOutput(parsedNode.getChildValue((String)null, "onSqlOutput", String.class));
        super.load(parsedNode, resourceAccessor);
    }

    @Override
    public String getName() {
        return "preConditions";
    }

    public static enum FailOption {
        HALT("HALT"),
        CONTINUE("CONTINUE"),
        MARK_RAN("MARK_RAN"),
        WARN("WARN");

        final String key;

        private FailOption(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public static enum ErrorOption {
        HALT("HALT"),
        CONTINUE("CONTINUE"),
        MARK_RAN("MARK_RAN"),
        WARN("WARN");

        final String key;

        private ErrorOption(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public static enum OnSqlOutputOption {
        IGNORE("IGNORE"),
        TEST("TEST"),
        FAIL("FAIL");

        final String key;

        private OnSqlOutputOption(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }
}

