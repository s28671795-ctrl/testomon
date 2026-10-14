/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.Objects;
import org.h2.command.ParserBase;
import org.h2.command.ddl.SequenceOptions;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;
import org.h2.table.ColumnTemplate;
import org.h2.table.GeneratedColumnResolver;
import org.h2.table.Table;
import org.h2.util.HasSQL;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueUuid;

public final class Column
implements HasSQL,
Typed,
ColumnTemplate {
    public static final String ROWID = "_ROWID_";
    public static final int NOT_NULLABLE = 0;
    public static final int NULLABLE = 1;
    public static final int NULLABLE_UNKNOWN = 2;
    private TypeInfo type;
    private Table table;
    private String name;
    private int columnId;
    private boolean nullable = true;
    private Expression defaultExpression;
    private Expression onUpdateExpression;
    private SequenceOptions identityOptions;
    private boolean defaultOnNull;
    private Sequence sequence;
    private boolean isGeneratedAlways;
    private GeneratedColumnResolver generatedTableFilter;
    private int selectivity;
    private String comment;
    private boolean primaryKey;
    private boolean visible = true;
    private boolean rowId;
    private Domain domain;

    public static StringBuilder writeColumns(StringBuilder stringBuilder, Column[] columnArray, int n) {
        int n2 = columnArray.length;
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            columnArray[i].getSQL(stringBuilder, n);
        }
        return stringBuilder;
    }

    public static StringBuilder writeColumns(StringBuilder stringBuilder, Column[] columnArray, String string, String string2, int n) {
        int n2 = columnArray.length;
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(string);
            }
            columnArray[i].getSQL(stringBuilder, n).append(string2);
        }
        return stringBuilder;
    }

    public Column(String string, TypeInfo typeInfo) {
        this.name = string;
        this.type = typeInfo;
    }

    public Column(String string, TypeInfo typeInfo, Table table, int n) {
        this.name = string;
        this.type = typeInfo;
        this.table = table;
        this.columnId = n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Column)) {
            return false;
        }
        Column column = (Column)object;
        if (this.table == null || column.table == null || this.name == null || column.name == null) {
            return false;
        }
        if (this.table != column.table) {
            return false;
        }
        return this.name.equals(column.name);
    }

    public int hashCode() {
        if (this.table == null || this.name == null) {
            return 0;
        }
        return this.table.getId() ^ this.name.hashCode();
    }

    public Column getClone() {
        Column column = new Column(this.name, this.type);
        column.copy(this);
        return column;
    }

    public Value convert(CastDataProvider castDataProvider, Value value) {
        try {
            return value.convertTo(this.type, castDataProvider, this);
        }
        catch (DbException dbException) {
            DbException dbException2;
            if (dbException.getErrorCode() == 22018) {
                dbException2 = this.getDataConversionError(value, dbException);
            }
            throw dbException2;
        }
    }

    public boolean isIdentity() {
        return this.sequence != null || this.identityOptions != null;
    }

    public boolean isGenerated() {
        return this.isGeneratedAlways && this.defaultExpression != null;
    }

    public boolean isGeneratedAlways() {
        return this.isGeneratedAlways;
    }

    public void setGeneratedExpression(Expression expression) {
        this.isGeneratedAlways = true;
        this.defaultExpression = expression;
    }

    public void setTable(Table table, int n) {
        this.table = table;
        this.columnId = n;
    }

    public Table getTable() {
        return this.table;
    }

    @Override
    public void setDefaultExpression(SessionLocal sessionLocal, Expression expression) {
        if (expression != null && (expression = expression.optimize(sessionLocal)).isConstant()) {
            expression = ValueExpression.get(expression.getValue(sessionLocal));
        }
        this.defaultExpression = expression;
        this.isGeneratedAlways = false;
    }

    @Override
    public void setOnUpdateExpression(SessionLocal sessionLocal, Expression expression) {
        if (expression != null && (expression = expression.optimize(sessionLocal)).isConstant()) {
            expression = ValueExpression.get(expression.getValue(sessionLocal));
        }
        this.onUpdateExpression = expression;
    }

    public int getColumnId() {
        return this.columnId;
    }

    @Override
    public String getSQL(int n) {
        return this.rowId ? this.name : ParserBase.quoteIdentifier(this.name, n);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return this.rowId ? stringBuilder.append(this.name) : ParserUtil.quoteIdentifier(stringBuilder, this.name, n);
    }

    public StringBuilder getSQLWithTable(StringBuilder stringBuilder, int n) {
        return this.getSQL(this.table.getSQL(stringBuilder, n).append('.'), n);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    public void setNullable(boolean bl) {
        this.nullable = bl;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    @Override
    public Domain getDomain() {
        return this.domain;
    }

    @Override
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public boolean isRowId() {
        return this.rowId;
    }

    public void setRowId(boolean bl) {
        this.rowId = bl;
    }

    /*
     * Unable to fully structure code
     */
    Value validateConvertUpdateSequence(SessionLocal var1_1, Value var2_2, Row var3_3) {
        if (var2_2 != null) ** GOTO lbl6
        if (this.sequence != null) {
            var2_2 = var1_1.getNextValueFor(this.sequence, null);
        } else {
            var2_2 = this.getDefaultOrGenerated(var1_1, var3_3);
lbl6:
            // 2 sources

            if (var2_2 == ValueNull.INSTANCE && !this.nullable) {
                throw DbException.get(23502, this.name);
            }
        }
        try {
            var2_2 = var2_2.convertForAssignTo(this.type, var1_1, this.name);
        }
        catch (DbException var4_4) {
            if (var4_4.getErrorCode() == 22018) {
                var4_5 = this.getDataConversionError(var2_2, var4_4);
            }
            throw var4_5;
        }
        if (this.domain != null) {
            this.domain.checkConstraints(var1_1, var2_2);
        }
        if (this.sequence != null && var1_1.getMode().updateSequenceOnManualIdentityInsertion) {
            this.updateSequenceIfRequired(var1_1, var2_2.getLong());
        }
        return var2_2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Value getDefaultOrGenerated(SessionLocal sessionLocal, Row row) {
        Value value;
        Expression expression = this.getEffectiveDefaultExpression();
        if (expression == null) {
            value = ValueNull.INSTANCE;
        } else {
            if (this.isGeneratedAlways) {
                Column column = this;
                synchronized (column) {
                    this.generatedTableFilter.set(row);
                    try {
                        value = expression.getValue(sessionLocal);
                    }
                    finally {
                        this.generatedTableFilter.set(null);
                    }
                }
            }
            value = expression.getValue(sessionLocal);
        }
        return value;
    }

    private DbException getDataConversionError(Value value, DbException dbException) {
        StringBuilder stringBuilder = new StringBuilder().append(value.getTraceSQL()).append(" (");
        if (this.table != null) {
            stringBuilder.append(this.table.getName()).append(": ");
        }
        stringBuilder.append(this.getCreateSQL()).append(')');
        return DbException.get(22018, dbException, stringBuilder.toString());
    }

    private void updateSequenceIfRequired(SessionLocal sessionLocal, long l) {
        if (this.sequence.getCycle() == Sequence.Cycle.EXHAUSTED) {
            return;
        }
        long l2 = this.sequence.getCurrentValue();
        long l3 = this.sequence.getIncrement();
        if (l3 > 0L ? l < l2 : l > l2) {
            return;
        }
        try {
            this.sequence.modify(l + l3, null, null, null, null, null, null);
        }
        catch (DbException dbException) {
            if (dbException.getErrorCode() == 90009) {
                return;
            }
            throw dbException;
        }
        this.sequence.flush(sessionLocal);
    }

    public void initializeSequence(SessionLocal sessionLocal, Schema schema, int n, boolean bl) {
        String string;
        if (this.identityOptions == null) {
            throw DbException.getInternalError();
        }
        while (schema.findSequence(string = "SYSTEM_SEQUENCE_" + StringUtils.toUpperEnglish(ValueUuid.getNewRandom().getString().replace('-', '_'))) != null) {
        }
        this.identityOptions.setDataType(this.type);
        Sequence sequence2 = new Sequence(sessionLocal, schema, n, string, this.identityOptions, true);
        sequence2.setTemporary(bl);
        sessionLocal.getDatabase().addSchemaObject(sessionLocal, sequence2);
        this.setSequence(sequence2, this.isGeneratedAlways);
    }

    @Override
    public void prepareExpressions(SessionLocal sessionLocal) {
        if (this.defaultExpression != null) {
            if (this.isGeneratedAlways) {
                this.generatedTableFilter = new GeneratedColumnResolver(this.table);
                this.defaultExpression.mapColumns(this.generatedTableFilter, 0, 0);
            }
            this.defaultExpression = this.defaultExpression.optimize(sessionLocal);
        }
        if (this.onUpdateExpression != null) {
            this.onUpdateExpression = this.onUpdateExpression.optimize(sessionLocal);
        }
        if (this.domain != null) {
            this.domain.prepareExpressions(sessionLocal);
        }
    }

    public String getCreateSQLWithoutName() {
        return this.getCreateSQL(new StringBuilder(), false);
    }

    public String getCreateSQL() {
        return this.getCreateSQL(false);
    }

    public String getCreateSQL(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.name != null) {
            ParserUtil.quoteIdentifier(stringBuilder, this.name, 0).append(' ');
        }
        return this.getCreateSQL(stringBuilder, bl);
    }

    private String getCreateSQL(StringBuilder stringBuilder, boolean bl) {
        if (this.domain != null) {
            this.domain.getSQL(stringBuilder, 0);
        } else {
            this.type.getSQL(stringBuilder, 0);
        }
        if (!this.visible) {
            stringBuilder.append(" INVISIBLE ");
        }
        if (this.sequence != null) {
            stringBuilder.append(" GENERATED ").append(this.isGeneratedAlways ? "ALWAYS" : "BY DEFAULT").append(" AS IDENTITY");
            if (!bl) {
                this.sequence.getSequenceOptionsSQL(stringBuilder.append('(')).append(')');
            }
        } else if (this.defaultExpression != null) {
            if (this.isGeneratedAlways) {
                this.defaultExpression.getEnclosedSQL(stringBuilder.append(" GENERATED ALWAYS AS "), 0);
            } else {
                this.defaultExpression.getUnenclosedSQL(stringBuilder.append(" DEFAULT "), 0);
            }
        }
        if (this.onUpdateExpression != null) {
            this.onUpdateExpression.getUnenclosedSQL(stringBuilder.append(" ON UPDATE "), 0);
        }
        if (this.defaultOnNull) {
            stringBuilder.append(" DEFAULT ON NULL");
        }
        if (bl && this.sequence != null) {
            this.sequence.getSQL(stringBuilder.append(" SEQUENCE "), 0);
        }
        if (this.selectivity != 0) {
            stringBuilder.append(" SELECTIVITY ").append(this.selectivity);
        }
        if (this.comment != null) {
            StringUtils.quoteStringSQL(stringBuilder.append(" COMMENT "), this.comment);
        }
        if (!this.nullable) {
            stringBuilder.append(" NOT NULL");
        }
        return stringBuilder.toString();
    }

    public boolean isNullable() {
        return this.nullable;
    }

    @Override
    public Expression getDefaultExpression() {
        return this.defaultExpression;
    }

    @Override
    public Expression getEffectiveDefaultExpression() {
        if (this.sequence != null) {
            return null;
        }
        return this.defaultExpression != null ? this.defaultExpression : (this.domain != null ? this.domain.getEffectiveDefaultExpression() : null);
    }

    @Override
    public Expression getOnUpdateExpression() {
        return this.onUpdateExpression;
    }

    @Override
    public Expression getEffectiveOnUpdateExpression() {
        if (this.sequence != null || this.isGeneratedAlways) {
            return null;
        }
        return this.onUpdateExpression != null ? this.onUpdateExpression : (this.domain != null ? this.domain.getEffectiveOnUpdateExpression() : null);
    }

    public boolean hasIdentityOptions() {
        return this.identityOptions != null;
    }

    public void setIdentityOptions(SequenceOptions sequenceOptions, boolean bl) {
        this.identityOptions = sequenceOptions;
        this.isGeneratedAlways = bl;
        this.removeNonIdentityProperties();
    }

    private void removeNonIdentityProperties() {
        this.nullable = false;
        this.defaultExpression = null;
        this.onUpdateExpression = null;
    }

    public SequenceOptions getIdentityOptions() {
        return this.identityOptions;
    }

    public void setDefaultOnNull(boolean bl) {
        this.defaultOnNull = bl;
    }

    public boolean isDefaultOnNull() {
        return this.defaultOnNull;
    }

    public void rename(String string) {
        this.name = string;
    }

    public void setSequence(Sequence sequence2, boolean bl) {
        this.sequence = sequence2;
        this.isGeneratedAlways = bl;
        this.identityOptions = null;
        if (sequence2 != null) {
            this.removeNonIdentityProperties();
            if (sequence2.getDatabase().getMode().identityColumnsHaveDefaultOnNull) {
                this.defaultOnNull = true;
            }
        }
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public int getSelectivity() {
        return this.selectivity == 0 ? 50 : this.selectivity;
    }

    public void setSelectivity(int n) {
        this.selectivity = n = n < 0 ? 0 : (n > 100 ? 100 : n);
    }

    @Override
    public String getDefaultSQL() {
        return this.defaultExpression == null ? null : this.defaultExpression.getUnenclosedSQL(new StringBuilder(), 0).toString();
    }

    @Override
    public String getOnUpdateSQL() {
        return this.onUpdateExpression == null ? null : this.onUpdateExpression.getUnenclosedSQL(new StringBuilder(), 0).toString();
    }

    public void setComment(String string) {
        this.comment = string != null && !string.isEmpty() ? string : null;
    }

    public String getComment() {
        return this.comment;
    }

    public void setPrimaryKey(boolean bl) {
        this.primaryKey = bl;
    }

    boolean isEverything(ExpressionVisitor expressionVisitor) {
        Expression expression;
        if (expressionVisitor.getType() == 7 && this.sequence != null) {
            expressionVisitor.getDependencies().add(this.sequence);
        }
        if ((expression = this.getEffectiveDefaultExpression()) != null && !expression.isEverything(expressionVisitor)) {
            return false;
        }
        expression = this.getEffectiveOnUpdateExpression();
        return expression == null || expression.isEverything(expressionVisitor);
    }

    public boolean isPrimaryKey() {
        return this.primaryKey;
    }

    public String toString() {
        return this.name;
    }

    public boolean isWideningConversion(Column column) {
        long l;
        TypeInfo typeInfo = column.type;
        int n = this.type.getValueType();
        if (n != typeInfo.getValueType()) {
            return false;
        }
        long l2 = this.type.getPrecision();
        if (l2 > (l = typeInfo.getPrecision()) || l2 < l && (n == 1 || n == 5)) {
            return false;
        }
        if (this.type.getScale() != typeInfo.getScale()) {
            return false;
        }
        if (!Objects.equals(this.type.getExtTypeInfo(), typeInfo.getExtTypeInfo())) {
            return false;
        }
        if (this.nullable && !column.nullable) {
            return false;
        }
        if (this.primaryKey != column.primaryKey) {
            return false;
        }
        if (this.identityOptions != null || column.identityOptions != null) {
            return false;
        }
        if (this.domain != column.domain) {
            return false;
        }
        if (this.defaultExpression != null || column.defaultExpression != null) {
            return false;
        }
        if (this.isGeneratedAlways || column.isGeneratedAlways) {
            return false;
        }
        return this.onUpdateExpression == null && column.onUpdateExpression == null;
    }

    public void copy(Column column) {
        this.name = column.name;
        this.type = column.type;
        this.domain = column.domain;
        this.nullable = column.nullable;
        this.defaultExpression = column.defaultExpression;
        this.onUpdateExpression = column.onUpdateExpression;
        this.defaultOnNull = column.defaultOnNull;
        this.sequence = column.sequence;
        this.comment = column.comment;
        this.generatedTableFilter = column.generatedTableFilter;
        this.isGeneratedAlways = column.isGeneratedAlways;
        this.selectivity = column.selectivity;
        this.primaryKey = column.primaryKey;
        this.visible = column.visible;
    }
}

