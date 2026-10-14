/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import liquibase.change.AddColumnConfig;
import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.util.StringUtil;

public class CreateIndexGeneratorFirebird
extends CreateIndexGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(CreateIndexStatement statement, Database database) {
        return database instanceof FirebirdDatabase;
    }

    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ColumnAnalysisResult result;
        List<String> associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint") || associatedWith.contains("foreignKey"))) {
            return EMPTY_SQL;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE ");
        if (statement.isUnique() != null && statement.isUnique().booleanValue()) {
            buffer.append("UNIQUE ");
        }
        if ((result = this.analyseColumns(statement, database)).isFoundDescColumns()) {
            buffer.append("DESCENDING ");
        }
        buffer.append("INDEX ");
        if (statement.getIndexName() != null) {
            String indexSchema = statement.getTableSchemaName();
            buffer.append(database.escapeIndexName(statement.getTableCatalogName(), indexSchema, statement.getIndexName())).append(" ");
        }
        buffer.append("ON ");
        buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName()));
        if (result.getNumComputedCols() > 0) {
            buffer.append(" COMPUTED BY ");
        }
        buffer.append(String.format("(%s)", result.getColumnExpression()));
        return new Sql[]{new UnparsedSql(buffer.toString(), this.getAffectedIndex(statement))};
    }

    private ColumnAnalysisResult analyseColumns(CreateIndexStatement statement, Database database) {
        ColumnAnalysisResult result = new ColumnAnalysisResult();
        StringBuilder idxColsBuf = new StringBuilder();
        result.foundAscColumns = false;
        result.foundDescColumns = false;
        result.numComputedCols = 0;
        result.numRegularCols = 0;
        Iterator<AddColumnConfig> iterator2 = Arrays.asList(statement.getColumns()).iterator();
        while (iterator2.hasNext()) {
            AddColumnConfig column = iterator2.next();
            boolean columnExpressionIsComputed = column.getComputed() == null ? this.applyIsComputedExpressionHeuristic(column, database) : column.getComputed().booleanValue();
            if (columnExpressionIsComputed) {
                idxColsBuf.append(column.getName());
                result.setNumComputedCols(result.getNumComputedCols() + 1);
            } else {
                result.setNumRegularCols(result.getNumRegularCols() + 1);
                idxColsBuf.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName()));
            }
            if (column.getDescending() != null && column.getDescending().booleanValue()) {
                result.setFoundDescColumns(true);
            } else {
                result.setFoundAscColumns(true);
            }
            if (!iterator2.hasNext()) continue;
            idxColsBuf.append(", ");
        }
        result.setColumnExpression(idxColsBuf.toString());
        return result;
    }

    @Override
    public ValidationErrors validate(CreateIndexStatement createIndexStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors2 = super.validate(createIndexStatement, database, sqlGeneratorChain);
        errors2.checkRequiredField("name", createIndexStatement.getIndexName());
        ColumnAnalysisResult result = this.analyseColumns(createIndexStatement, database);
        if (result.isFoundAscColumns() && result.isFoundDescColumns()) {
            errors2.addError("Firebird cannot create indexes with mixed ASCending / DESCending columns.");
        }
        if (result.getNumComputedCols() > 0 && result.getNumRegularCols() > 0) {
            errors2.addError("Firebird cannot create indexes with both computed expressions and regular columns.");
        }
        if (result.getNumComputedCols() > 1) {
            errors2.addError("Firebird cannot create indexes on more than 1 computed expression.");
        }
        return errors2;
    }

    private boolean applyIsComputedExpressionHeuristic(ColumnConfig column, Database database) {
        String regex2;
        String expr = column.getName();
        if (!expr.matches(regex2 = "^(?i)[ABCDEFGHIJKLMNOPQRSTUVWXYZ][ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_$]{0,30}$")) {
            return true;
        }
        return database.isFunction(expr);
    }

    private final class ColumnAnalysisResult {
        private String columnExpression;
        private boolean foundAscColumns;
        private boolean foundDescColumns;
        private int numComputedCols;
        private int numRegularCols;

        private ColumnAnalysisResult() {
        }

        public String getColumnExpression() {
            return this.columnExpression;
        }

        public void setColumnExpression(String columnExpression) {
            this.columnExpression = columnExpression;
        }

        public boolean isFoundAscColumns() {
            return this.foundAscColumns;
        }

        public void setFoundAscColumns(boolean foundAscColumns) {
            this.foundAscColumns = foundAscColumns;
        }

        public boolean isFoundDescColumns() {
            return this.foundDescColumns;
        }

        public void setFoundDescColumns(boolean foundDescColumns) {
            this.foundDescColumns = foundDescColumns;
        }

        public int getNumComputedCols() {
            return this.numComputedCols;
        }

        public void setNumComputedCols(int numComputedCols) {
            this.numComputedCols = numComputedCols;
        }

        public int getNumRegularCols() {
            return this.numRegularCols;
        }

        public void setNumRegularCols(int numRegularCols) {
            this.numRegularCols = numRegularCols;
        }
    }
}

