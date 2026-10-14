/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.sql.Sql;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RuntimeStatement;
import liquibase.util.StringUtil;

@DatabaseChange(name="output", description="Logs a message and continues execution.", priority=1, since="3.3")
public class OutputChange
extends AbstractChange {
    private String message;
    private String target;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = super.validate(database);
        validate.checkRequiredField("message", this.getMessage());
        return validate;
    }

    @DatabaseChangeProperty(description="Message to send to output", exampleValue="Make sure you feed the cat", serializationType=LiquibaseSerializable.SerializationType.DIRECT_VALUE)
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = StringUtil.trimToNull(message);
    }

    @DatabaseChangeProperty(description="Target for message. Possible values: STDOUT, STDERR, FATAL, WARN, INFO, DEBUG. Default: STDERR", exampleValue="STDERR")
    public String getTarget() {
        if (this.target == null) {
            return "STDERR";
        }
        return this.target;
    }

    public void setTarget(String target) {
        this.target = StringUtil.trimToNull(target);
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new RuntimeStatement(){

            @Override
            public Sql[] generate(Database database) {
                String target = OutputChange.this.getTarget();
                if ("STDOUT".equalsIgnoreCase(target)) {
                    System.out.println(OutputChange.this.getMessage());
                } else if ("STDERR".equalsIgnoreCase(target)) {
                    System.err.println(OutputChange.this.getMessage());
                } else if ("DEBUG".equalsIgnoreCase(target)) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine(OutputChange.this.getMessage());
                } else if ("INFO".equalsIgnoreCase(target)) {
                    Scope.getCurrentScope().getLog(this.getClass()).info(OutputChange.this.getMessage());
                } else if ("WARN".equalsIgnoreCase(target) || "WARNING".equalsIgnoreCase(target)) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning(OutputChange.this.getMessage());
                } else if ("SEVERE".equalsIgnoreCase(target) || "FATAL".equalsIgnoreCase(target) || "ERROR".equalsIgnoreCase(target)) {
                    Scope.getCurrentScope().getLog(this.getClass()).severe(OutputChange.this.getMessage());
                } else {
                    throw new UnexpectedLiquibaseException("Unknown target: " + target);
                }
                return null;
            }
        }};
    }

    @Override
    public String getConfirmationMessage() {
        return "Output: " + this.getMessage();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        Object value = super.getSerializableFieldValue(field2);
        if ("target".equals(field2) && "".equals(value)) {
            return null;
        }
        return value;
    }

    @Override
    protected Change[] createInverses() {
        return EMPTY_CHANGE;
    }
}

