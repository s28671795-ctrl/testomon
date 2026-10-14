/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.math.BigInteger;
import liquibase.database.Database;
import liquibase.database.core.CockroachDatabase;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MariaDBDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.CreateSequenceStatement;
import liquibase.structure.core.Sequence;

public class CreateSequenceGenerator
extends AbstractSqlGenerator<CreateSequenceStatement> {
    @Override
    public boolean supports(CreateSequenceStatement statement, Database database) {
        return database.supports(Sequence.class);
    }

    @Override
    public ValidationErrors validate(CreateSequenceStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("sequenceName", statement.getSequenceName());
        validationErrors.checkDisallowedField("startValue", statement.getStartValue(), database, FirebirdDatabase.class);
        validationErrors.checkDisallowedField("incrementBy", statement.getIncrementBy(), database, FirebirdDatabase.class);
        if (this.isH2WithMinMaxSupport(database)) {
            validationErrors.checkDisallowedField("minValue", statement.getMinValue(), database, FirebirdDatabase.class, HsqlDatabase.class);
            validationErrors.checkDisallowedField("maxValue", statement.getMaxValue(), database, FirebirdDatabase.class, HsqlDatabase.class);
        } else {
            validationErrors.checkDisallowedField("minValue", statement.getMinValue(), database, FirebirdDatabase.class, H2Database.class, HsqlDatabase.class);
            validationErrors.checkDisallowedField("maxValue", statement.getMaxValue(), database, FirebirdDatabase.class, H2Database.class, HsqlDatabase.class);
        }
        validationErrors.checkDisallowedField("ordered", statement.getOrdered(), database, HsqlDatabase.class, PostgresDatabase.class, MSSQLDatabase.class);
        if (database instanceof PostgresDatabase) {
            if (this.isPostgreWithoutAsDatatypeSupport(database)) {
                validationErrors.checkDisallowedField("dataType", statement.getDataType(), database, PostgresDatabase.class);
            }
        } else if (database instanceof H2Database) {
            if (this.isH2WithoutAsDatatypeSupport(database) && statement.getDataType() != null && !statement.getDataType().equalsIgnoreCase("bigint")) {
                validationErrors.checkDisallowedField("dataType", statement.getDataType(), database, H2Database.class);
            }
        } else {
            validationErrors.checkDisallowedField("dataType", statement.getDataType(), database, HsqlDatabase.class, OracleDatabase.class, MySQLDatabase.class, MSSQLDatabase.class, CockroachDatabase.class, FirebirdDatabase.class);
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(CreateSequenceStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("CREATE SEQUENCE ");
        if (database instanceof PostgresDatabase) {
            try {
                if (database.getDatabaseMajorVersion() > 9 || database.getDatabaseMajorVersion() == 9 && database.getDatabaseMinorVersion() >= 5) {
                    queryStringBuilder.append(" IF NOT EXISTS ");
                }
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
        }
        queryStringBuilder.append(database.escapeSequenceName(statement.getCatalogName(), statement.getSchemaName(), statement.getSequenceName()));
        if (database instanceof HsqlDatabase || database instanceof Db2zDatabase) {
            queryStringBuilder.append(" AS BIGINT ");
        } else if (!(statement.getDataType() == null || this.isH2WithoutAsDatatypeSupport(database) || database instanceof CockroachDatabase || database instanceof SybaseASADatabase)) {
            queryStringBuilder.append(" AS ").append(statement.getDataType());
        }
        if (!(database instanceof MariaDBDatabase) && statement.getStartValue() != null) {
            queryStringBuilder.append(" START WITH ").append(statement.getStartValue());
        }
        if (statement.getIncrementBy() != null) {
            queryStringBuilder.append(" INCREMENT BY ").append(statement.getIncrementBy());
        }
        if (statement.getMinValue() != null) {
            queryStringBuilder.append(" MINVALUE ").append(statement.getMinValue());
        }
        if (statement.getMaxValue() != null) {
            queryStringBuilder.append(" MAXVALUE ").append(statement.getMaxValue());
        }
        if (database instanceof MariaDBDatabase && statement.getStartValue() != null) {
            queryStringBuilder.append(" START WITH ").append(statement.getStartValue());
        }
        if (statement.getCacheSize() != null && (database instanceof OracleDatabase || database instanceof Db2zDatabase || database instanceof PostgresDatabase || database instanceof MariaDBDatabase || database instanceof SybaseASADatabase)) {
            if (BigInteger.ZERO.equals(statement.getCacheSize())) {
                if (database instanceof OracleDatabase) {
                    queryStringBuilder.append(" NOCACHE ");
                } else if (database instanceof SybaseASADatabase) {
                    queryStringBuilder.append(" NO CACHE ");
                } else if (database instanceof MariaDBDatabase) {
                    queryStringBuilder.append(" CACHE 0");
                }
            } else {
                queryStringBuilder.append(" CACHE ").append(statement.getCacheSize());
            }
        }
        if (!(database instanceof MariaDBDatabase) && statement.getOrdered() != null && !(database instanceof SybaseASADatabase)) {
            if (statement.getOrdered().booleanValue()) {
                queryStringBuilder.append(" ORDER");
            } else if (database instanceof OracleDatabase) {
                queryStringBuilder.append(" NOORDER");
            }
        }
        if (statement.getCycle() != null && statement.getCycle().booleanValue()) {
            queryStringBuilder.append(" CYCLE");
        }
        return new Sql[]{new UnparsedSql(queryStringBuilder.toString(), this.getAffectedSequence(statement))};
    }

    protected Sequence getAffectedSequence(CreateSequenceStatement statement) {
        return new Sequence().setName(statement.getSequenceName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }

    private boolean isH2WithMinMaxSupport(Database database) {
        return H2Database.class.isAssignableFrom(database.getClass()) && ((H2Database)database).supportsMinMaxForSequences();
    }

    private boolean isPostgreWithoutAsDatatypeSupport(Database database) {
        try {
            return database instanceof PostgresDatabase && database.getDatabaseMajorVersion() < 10;
        }
        catch (DatabaseException e) {
            return false;
        }
    }

    private boolean isH2WithoutAsDatatypeSupport(Database database) {
        try {
            return database instanceof H2Database && database.getDatabaseMajorVersion() < 2;
        }
        catch (DatabaseException e) {
            return false;
        }
    }
}

