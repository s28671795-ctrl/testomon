/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.statement.AbstractSqlStatement;

public class SelectFromDatabaseChangeLogStatement
extends AbstractSqlStatement {
    private ColumnConfig[] columnsToSelect;
    private WhereClause whereClause;
    private String[] orderByColumns;
    private Integer limit;

    public SelectFromDatabaseChangeLogStatement(String ... columnsToSelect) {
        if (columnsToSelect != null) {
            this.columnsToSelect = new ColumnConfig[columnsToSelect.length];
            for (int i = 0; i < columnsToSelect.length; ++i) {
                this.columnsToSelect[i] = new ColumnConfig().setName(columnsToSelect[i]);
            }
        }
    }

    public SelectFromDatabaseChangeLogStatement(ColumnConfig ... columnsToSelect) {
        this((WhereClause)null, columnsToSelect);
    }

    public SelectFromDatabaseChangeLogStatement(WhereClause whereClause, ColumnConfig ... columnsToSelect) {
        this.columnsToSelect = columnsToSelect;
        this.whereClause = whereClause;
    }

    public ColumnConfig[] getColumnsToSelect() {
        return this.columnsToSelect;
    }

    public WhereClause getWhereClause() {
        return this.whereClause;
    }

    public String[] getOrderByColumns() {
        return this.orderByColumns;
    }

    public SelectFromDatabaseChangeLogStatement setOrderBy(String ... columns) {
        this.orderByColumns = columns;
        return this;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public SelectFromDatabaseChangeLogStatement setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public static interface WhereClause {
        public String generateSql(Database var1);
    }

    public static class ByCheckSumNotNullAndNotLike
    implements WhereClause {
        private final int notLikeCheckSumVersion;

        @Override
        public String generateSql(Database database) {
            String md5SUMColumnName = database.escapeColumnName(null, null, null, "MD5SUM");
            return String.format(" WHERE %s IS NOT NULL AND %s NOT LIKE '%d:%%'", md5SUMColumnName, md5SUMColumnName, this.notLikeCheckSumVersion);
        }

        public ByCheckSumNotNullAndNotLike(int notLikeCheckSumVersion) {
            this.notLikeCheckSumVersion = notLikeCheckSumVersion;
        }

        public int getNotLikeCheckSumVersion() {
            return this.notLikeCheckSumVersion;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ByCheckSumNotNullAndNotLike)) {
                return false;
            }
            ByCheckSumNotNullAndNotLike other = (ByCheckSumNotNullAndNotLike)o;
            if (!other.canEqual(this)) {
                return false;
            }
            return this.getNotLikeCheckSumVersion() == other.getNotLikeCheckSumVersion();
        }

        protected boolean canEqual(Object other) {
            return other instanceof ByCheckSumNotNullAndNotLike;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            result = result * 59 + this.getNotLikeCheckSumVersion();
            return result;
        }

        public String toString() {
            return "SelectFromDatabaseChangeLogStatement.ByCheckSumNotNullAndNotLike(notLikeCheckSumVersion=" + this.getNotLikeCheckSumVersion() + ")";
        }
    }

    public static class ByTag
    implements WhereClause {
        private final String tagName;

        public ByTag(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public String generateSql(Database database) {
            return String.format(" WHERE %s='%s'", database.escapeColumnName(null, null, null, "TAG"), this.getTagName());
        }

        public String getTagName() {
            return this.tagName;
        }
    }

    public static class ByNotNullCheckSum
    implements WhereClause {
        @Override
        public String generateSql(Database database) {
            return String.format(" WHERE %s IS NOT NULL", database.escapeColumnName(null, null, null, "MD5SUM"));
        }
    }
}

