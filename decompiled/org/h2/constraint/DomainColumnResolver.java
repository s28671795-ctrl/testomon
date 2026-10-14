/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class DomainColumnResolver
implements ColumnResolver {
    private final Column column;
    private Value value;
    private String name;

    public DomainColumnResolver(TypeInfo typeInfo) {
        this.column = new Column("VALUE", typeInfo);
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public Value getValue(Column column) {
        return this.value;
    }

    @Override
    public Column[] getColumns() {
        return new Column[]{this.column};
    }

    @Override
    public Column findColumn(String string) {
        return null;
    }

    void setColumnName(String string) {
        this.name = string;
    }

    void resetColumnName() {
        this.name = null;
    }

    public String getColumnName() {
        return this.name;
    }

    public TypeInfo getValueType() {
        return this.column.getType();
    }
}

