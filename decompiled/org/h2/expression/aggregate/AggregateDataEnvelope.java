/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.aggregate.AggregateData;
import org.h2.index.Index;
import org.h2.mvstore.db.MVSpatialIndex;
import org.h2.table.Column;
import org.h2.table.TableFilter;
import org.h2.util.geometry.GeometryUtils;
import org.h2.value.Value;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueNull;

final class AggregateDataEnvelope
extends AggregateData {
    private double[] envelope;

    AggregateDataEnvelope() {
    }

    static Index getGeometryColumnIndex(Expression expression) {
        ArrayList<Index> arrayList;
        TableFilter tableFilter;
        ExpressionColumn expressionColumn;
        Column column;
        if (expression instanceof ExpressionColumn && (column = (expressionColumn = (ExpressionColumn)expression).getColumn()).getType().getValueType() == 37 && (tableFilter = expressionColumn.getTableFilter()) != null && (arrayList = tableFilter.getTable().getIndexes()) != null) {
            int n = arrayList.size();
            for (int i = 1; i < n; ++i) {
                Index index = arrayList.get(i);
                if (!(index instanceof MVSpatialIndex) || !index.isFirstColumn(column)) continue;
                return index;
            }
        }
        return null;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        if (value == ValueNull.INSTANCE) {
            return;
        }
        this.envelope = GeometryUtils.union(this.envelope, value.convertToGeometry(null).getEnvelopeNoCopy());
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        return ValueGeometry.fromEnvelope(this.envelope);
    }
}

