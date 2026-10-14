/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

abstract class ValueBigDecimalBase
extends Value {
    final BigDecimal value;
    TypeInfo type;

    ValueBigDecimalBase(BigDecimal bigDecimal) {
        if (bigDecimal != null) {
            if (bigDecimal.getClass() != BigDecimal.class) {
                throw DbException.get(90125, BigDecimal.class.getName(), bigDecimal.getClass().getName());
            }
            int n = bigDecimal.precision();
            if (n > 100000) {
                throw DbException.getValueTooLongException(ValueBigDecimalBase.getTypeName(this.getValueType()), bigDecimal.toString(), n);
            }
        }
        this.value = bigDecimal;
    }
}

