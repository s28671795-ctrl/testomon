/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;

public class SequenceExistsPrecondition
extends AbstractPrecondition {
    private String catalogName;
    private String schemaName;
    private String sequenceName;
    private static final String SQL_CHECK_POSTGRES_SEQUENCE_EXISTS = "SELECT c.relname FROM pg_class c JOIN pg_namespace ns on c.relnamespace = ns.oid WHERE c.relkind = 'S' AND ns.nspname = ? and c.relname = ?";

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            if (database instanceof PostgresDatabase) {
                this.checkPostgresSequence(database, changeLog);
            } else {
                Schema schema = new Schema(this.getCatalogName(), this.getSchemaName());
                if (!SnapshotGeneratorFactory.getInstance().has(new Sequence().setName(this.getSequenceName()).setSchema(schema), database)) {
                    throw new PreconditionFailedException("Sequence " + database.escapeSequenceName(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()) + " does not exist", changeLog, this);
                }
            }
        }
        catch (SQLException | LiquibaseException e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    private void checkPostgresSequence(Database database, DatabaseChangeLog changeLog) throws DatabaseException, SQLException, PreconditionFailedException, PreconditionErrorException {
        try (PreparedStatement statement = ((JdbcConnection)database.getConnection()).prepareStatement(SQL_CHECK_POSTGRES_SEQUENCE_EXISTS);){
            statement.setString(1, this.getSchemaName() != null ? this.getSchemaName() : database.getDefaultSchemaName());
            statement.setString(2, this.getSequenceName());
            try (ResultSet rs = statement.executeQuery();){
                if (!rs.next()) {
                    throw new PreconditionFailedException("Sequence " + database.escapeSequenceName(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()) + " does not exist", changeLog, this);
                }
            }
            catch (SQLException e) {
                throw new PreconditionErrorException(e, changeLog, this);
            }
        }
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "sequenceExists";
    }
}

