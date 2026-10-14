/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.AggregateFilterStep;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.OrderedAggregateFunctionOfDeferredType;
import org.jooq.SortField;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DefaultAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.SQLDataType;

final class ModeDeferred
implements OrderedAggregateFunctionOfDeferredType {
    ModeDeferred() {
    }

    @Override
    public final <T> AggregateFilterStep<T> withinGroupOrderBy(OrderField<T> field2) {
        DataType<BigDecimal> dataType;
        if (field2 instanceof SortField) {
            SortField s2 = (SortField)field2;
            dataType = s2.$field().getDataType();
        } else if (field2 instanceof AbstractField) {
            AbstractField f = (AbstractField)field2;
            dataType = f.getDataType();
        } else {
            dataType = SQLDataType.NUMERIC;
        }
        DataType<BigDecimal> type = dataType;
        return new DefaultAggregateFunction<BigDecimal>(Names.N_MODE, type, new Field[0]).withinGroupOrderBy(new OrderField[]{field2});
    }
}

