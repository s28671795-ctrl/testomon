/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.Arrays;
import org.h2.engine.Session;
import org.h2.result.ResultInterface;
import org.h2.util.MathUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueVarchar;

public class ResultWithPaddedStrings
implements ResultInterface {
    private final ResultInterface source;

    public static ResultInterface get(ResultInterface resultInterface) {
        int n = resultInterface.getVisibleColumnCount();
        for (int i = 0; i < n; ++i) {
            if (resultInterface.getColumnType(i).getValueType() != 1) continue;
            return new ResultWithPaddedStrings(resultInterface);
        }
        return resultInterface;
    }

    private ResultWithPaddedStrings(ResultInterface resultInterface) {
        this.source = resultInterface;
    }

    @Override
    public void reset() {
        this.source.reset();
    }

    @Override
    public Value[] currentRow() {
        int n = this.source.getVisibleColumnCount();
        Value[] valueArray = Arrays.copyOf(this.source.currentRow(), n);
        for (int i = 0; i < n; ++i) {
            String string;
            TypeInfo typeInfo = this.source.getColumnType(i);
            if (typeInfo.getValueType() != 1) continue;
            long l = typeInfo.getPrecision();
            if (l == Integer.MAX_VALUE) {
                l = 1L;
            }
            if ((string = valueArray[i].getString()) == null || (long)string.length() >= l) continue;
            valueArray[i] = ValueVarchar.get(ResultWithPaddedStrings.rightPadWithSpaces(string, MathUtils.convertLongToInt(l)));
        }
        return valueArray;
    }

    private static String rightPadWithSpaces(String string, int n) {
        int n2 = string.length();
        if (n <= n2) {
            return string;
        }
        char[] cArray = new char[n];
        string.getChars(0, n2, cArray, 0);
        Arrays.fill(cArray, n2, n, ' ');
        return new String(cArray);
    }

    @Override
    public boolean next() {
        return this.source.next();
    }

    @Override
    public long getRowId() {
        return this.source.getRowId();
    }

    @Override
    public boolean isAfterLast() {
        return this.source.isAfterLast();
    }

    @Override
    public int getVisibleColumnCount() {
        return this.source.getVisibleColumnCount();
    }

    @Override
    public long getRowCount() {
        return this.source.getRowCount();
    }

    @Override
    public boolean hasNext() {
        return this.source.hasNext();
    }

    @Override
    public boolean needToClose() {
        return this.source.needToClose();
    }

    @Override
    public void close() {
        this.source.close();
    }

    @Override
    public String getAlias(int n) {
        return this.source.getAlias(n);
    }

    @Override
    public String getSchemaName(int n) {
        return this.source.getSchemaName(n);
    }

    @Override
    public String getTableName(int n) {
        return this.source.getTableName(n);
    }

    @Override
    public String getColumnName(int n) {
        return this.source.getColumnName(n);
    }

    @Override
    public TypeInfo getColumnType(int n) {
        return this.source.getColumnType(n);
    }

    @Override
    public boolean isIdentity(int n) {
        return this.source.isIdentity(n);
    }

    @Override
    public int getNullable(int n) {
        return this.source.getNullable(n);
    }

    @Override
    public void setFetchSize(int n) {
        this.source.setFetchSize(n);
    }

    @Override
    public int getFetchSize() {
        return this.source.getFetchSize();
    }

    @Override
    public boolean isLazy() {
        return this.source.isLazy();
    }

    @Override
    public boolean isClosed() {
        return this.source.isClosed();
    }

    @Override
    public ResultInterface createShallowCopy(Session session) {
        ResultInterface resultInterface = this.source.createShallowCopy(session);
        return resultInterface != null ? new ResultWithPaddedStrings(resultInterface) : null;
    }
}

