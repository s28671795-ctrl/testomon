/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.io.IOException;
import org.h2.expression.ParameterInterface;
import org.h2.message.DbException;
import org.h2.value.Transfer;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueLob;

public class ParameterRemote
implements ParameterInterface {
    private Value value;
    private final int index;
    private TypeInfo type = TypeInfo.TYPE_UNKNOWN;
    private int nullable = 2;

    public ParameterRemote(int n) {
        this.index = n;
    }

    @Override
    public void setValue(Value value, boolean bl) {
        if (bl && this.value instanceof ValueLob) {
            ((ValueLob)this.value).remove();
        }
        this.value = value;
    }

    @Override
    public Value getParamValue() {
        return this.value;
    }

    @Override
    public void checkSet() {
        if (this.value == null) {
            throw DbException.get(90012, "#" + (this.index + 1));
        }
    }

    @Override
    public boolean isValueSet() {
        return this.value != null;
    }

    @Override
    public TypeInfo getType() {
        return this.value == null ? this.type : this.value.getType();
    }

    @Override
    public int getNullable() {
        return this.nullable;
    }

    public void readMetaData(Transfer transfer) throws IOException {
        this.type = transfer.readTypeInfo();
        this.nullable = transfer.readInt();
    }

    public static void writeMetaData(Transfer transfer, ParameterInterface parameterInterface) throws IOException {
        transfer.writeTypeInfo(parameterInterface.getType()).writeInt(parameterInterface.getNullable());
    }
}

