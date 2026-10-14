/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.Objects;
import org.h2.expression.ValueExpression;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class TypedValueExpression
extends ValueExpression {
    public static final TypedValueExpression UNKNOWN = new TypedValueExpression(ValueNull.INSTANCE, TypeInfo.TYPE_BOOLEAN);
    private final TypeInfo type;

    public static ValueExpression get(Value value, TypeInfo typeInfo) {
        return TypedValueExpression.getImpl(value, typeInfo, true);
    }

    public static ValueExpression getTypedIfNull(Value value, TypeInfo typeInfo) {
        return TypedValueExpression.getImpl(value, typeInfo, false);
    }

    private static ValueExpression getImpl(Value value, TypeInfo typeInfo, boolean bl) {
        if (value == ValueNull.INSTANCE) {
            switch (typeInfo.getValueType()) {
                case 0: {
                    return ValueExpression.NULL;
                }
                case 8: {
                    return UNKNOWN;
                }
            }
            return new TypedValueExpression(value, typeInfo);
        }
        if (bl) {
            DataType dataType = DataType.getDataType(typeInfo.getValueType());
            TypeInfo typeInfo2 = value.getType();
            if (dataType.supportsPrecision && typeInfo.getPrecision() != typeInfo2.getPrecision() || dataType.supportsScale && typeInfo.getScale() != typeInfo2.getScale() || !Objects.equals(typeInfo.getExtTypeInfo(), typeInfo2.getExtTypeInfo())) {
                return new TypedValueExpression(value, typeInfo);
            }
        }
        return ValueExpression.get(value);
    }

    private TypedValueExpression(Value value, TypeInfo typeInfo) {
        super(value);
        this.type = typeInfo;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        if (this == UNKNOWN) {
            stringBuilder.append("UNKNOWN");
        } else {
            this.value.getSQL(stringBuilder.append("CAST("), n | 4).append(" AS ");
            this.type.getSQL(stringBuilder, n).append(')');
        }
        return stringBuilder;
    }

    @Override
    public boolean isNullConstant() {
        return this.value == ValueNull.INSTANCE;
    }
}

