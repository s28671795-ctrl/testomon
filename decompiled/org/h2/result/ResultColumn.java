/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.io.IOException;
import org.h2.result.ResultInterface;
import org.h2.value.Transfer;
import org.h2.value.TypeInfo;

public class ResultColumn {
    final String alias;
    final String schemaName;
    final String tableName;
    final String columnName;
    final TypeInfo columnType;
    final boolean identity;
    final int nullable;

    ResultColumn(Transfer transfer) throws IOException {
        this.alias = transfer.readString();
        this.schemaName = transfer.readString();
        this.tableName = transfer.readString();
        this.columnName = transfer.readString();
        this.columnType = transfer.readTypeInfo();
        if (transfer.getVersion() < 20) {
            transfer.readInt();
        }
        this.identity = transfer.readBoolean();
        this.nullable = transfer.readInt();
    }

    public static void writeColumn(Transfer transfer, ResultInterface resultInterface, int n) throws IOException {
        transfer.writeString(resultInterface.getAlias(n));
        transfer.writeString(resultInterface.getSchemaName(n));
        transfer.writeString(resultInterface.getTableName(n));
        transfer.writeString(resultInterface.getColumnName(n));
        TypeInfo typeInfo = resultInterface.getColumnType(n);
        transfer.writeTypeInfo(typeInfo);
        if (transfer.getVersion() < 20) {
            transfer.writeInt(typeInfo.getDisplaySize());
        }
        transfer.writeBoolean(resultInterface.isIdentity(n));
        transfer.writeInt(resultInterface.getNullable(n));
    }
}

