/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.util.Utils;
import org.h2.value.Value;

public final class MergedResult {
    private final ArrayList<Map<SimpleResult.Column, Value>> data = Utils.newSmallArrayList();
    private final ArrayList<SimpleResult.Column> columns = Utils.newSmallArrayList();

    public void add(ResultInterface resultInterface) {
        int n = resultInterface.getVisibleColumnCount();
        if (n == 0) {
            return;
        }
        SimpleResult.Column[] columnArray = new SimpleResult.Column[n];
        for (int i = 0; i < n; ++i) {
            SimpleResult.Column column;
            columnArray[i] = column = new SimpleResult.Column(resultInterface.getAlias(i), resultInterface.getColumnName(i), resultInterface.getColumnType(i));
            if (this.columns.contains(column)) continue;
            this.columns.add(column);
        }
        while (resultInterface.next()) {
            if (n == 1) {
                this.data.add(Collections.singletonMap(columnArray[0], resultInterface.currentRow()[0]));
                continue;
            }
            HashMap<SimpleResult.Column, Value> hashMap = new HashMap<SimpleResult.Column, Value>();
            for (int i = 0; i < n; ++i) {
                SimpleResult.Column column = columnArray[i];
                hashMap.put(column, resultInterface.currentRow()[i]);
            }
            this.data.add(hashMap);
        }
    }

    public SimpleResult getResult() {
        SimpleResult simpleResult = new SimpleResult();
        for (SimpleResult.Column object : this.columns) {
            simpleResult.addColumn(object);
        }
        for (Map map : this.data) {
            Value[] valueArray = new Value[this.columns.size()];
            for (Map.Entry entry : map.entrySet()) {
                valueArray[this.columns.indexOf(entry.getKey())] = (Value)entry.getValue();
            }
            simpleResult.addRow(valueArray);
        }
        return simpleResult;
    }

    public String toString() {
        return this.columns + ": " + this.data.size();
    }
}

