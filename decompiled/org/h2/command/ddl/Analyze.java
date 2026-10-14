/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.Arrays;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.value.DataType;
import org.h2.value.Value;

public class Analyze
extends DefineCommand {
    private int sampleRows;
    private Table table;

    public Analyze(SessionLocal sessionLocal) {
        super(sessionLocal);
        this.sampleRows = this.getDatabase().getSettings().analyzeSample;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        if (this.table != null) {
            Analyze.analyzeTable(this.session, this.table, this.sampleRows, true);
        } else {
            for (Schema schema : database.getAllSchemasNoMeta()) {
                for (Table table : schema.getAllTablesAndViews(null)) {
                    Analyze.analyzeTable(this.session, table, this.sampleRows, true);
                }
            }
        }
        return 0L;
    }

    public static void analyzeTable(SessionLocal sessionLocal, Table table, int n, boolean bl) {
        if (!table.isValid() || table.getTableType() != TableType.TABLE || table.isHidden() || sessionLocal == null || !bl && (sessionLocal.getDatabase().isSysTableLocked() || table.hasSelectTrigger()) || table.isTemporary() && !table.isGlobalTemporary() && sessionLocal.findLocalTempTable(table.getName()) == null || table.isLockedExclusively() && !table.isLockedExclusivelyBy(sessionLocal) || !sessionLocal.getUser().hasTableRight(table, 1) || sessionLocal.getCancel() != 0L) {
            return;
        }
        table.lock(sessionLocal, 0);
        Column[] columnArray = table.getColumns();
        int n2 = columnArray.length;
        if (n2 == 0) {
            return;
        }
        Cursor cursor = table.getScanIndex(sessionLocal).find(sessionLocal, null, null);
        if (cursor.next()) {
            SelectivityData[] selectivityDataArray = new SelectivityData[n2];
            for (int i = 0; i < n2; ++i) {
                Column column = columnArray[i];
                if (DataType.isLargeObject(column.getType().getValueType())) continue;
                selectivityDataArray[i] = new SelectivityData();
            }
            long l = 0L;
            do {
                Row row = cursor.get();
                for (int i = 0; i < n2; ++i) {
                    SelectivityData selectivityData = selectivityDataArray[i];
                    if (selectivityData == null) continue;
                    selectivityData.add(row.getValue(i));
                }
            } while ((n <= 0 || ++l < (long)n) && cursor.next());
            for (int i = 0; i < n2; ++i) {
                SelectivityData selectivityData = selectivityDataArray[i];
                if (selectivityData == null) continue;
                columnArray[i].setSelectivity(selectivityData.getSelectivity(l));
            }
        } else {
            for (int i = 0; i < n2; ++i) {
                columnArray[i].setSelectivity(0);
            }
        }
        sessionLocal.getDatabase().updateMeta(sessionLocal, table);
    }

    public void setTop(int n) {
        this.sampleRows = n;
    }

    @Override
    public int getType() {
        return 21;
    }

    private static final class SelectivityData {
        private long distinctCount;
        private int size;
        private int[] elements = new int[8];
        private boolean zeroElement;
        private int maxSize = 7;

        SelectivityData() {
        }

        void add(Value value) {
            int n;
            int n2 = this.currentSize();
            if (n2 >= 10000) {
                this.size = 0;
                Arrays.fill(this.elements, 0);
                this.zeroElement = false;
                this.distinctCount += (long)n2;
            }
            if ((n = value.hashCode()) == 0) {
                this.zeroElement = true;
            } else {
                if (this.size >= this.maxSize) {
                    this.rehash();
                }
                this.add(n);
            }
        }

        int getSelectivity(long l) {
            int n;
            if (l == 0L) {
                n = 0;
            } else {
                n = (int)(100L * (this.distinctCount + (long)this.currentSize()) / l);
                if (n <= 0) {
                    n = 1;
                }
            }
            return n;
        }

        private int currentSize() {
            int n = this.size;
            if (this.zeroElement) {
                ++n;
            }
            return n;
        }

        private void add(int n) {
            int n2 = this.elements.length;
            int n3 = n2 - 1;
            int n4 = n & n3;
            int n5 = 1;
            do {
                int n6;
                if ((n6 = this.elements[n4]) == 0) {
                    ++this.size;
                    this.elements[n4] = n;
                    return;
                }
                if (n6 == n) {
                    return;
                }
                n4 = n4 + n5++ & n3;
            } while (n5 <= n2);
        }

        private void rehash() {
            this.size = 0;
            int[] nArray = this.elements;
            int n = nArray.length << 1;
            this.elements = new int[n];
            this.maxSize = (int)((long)n * 90L / 100L);
            for (int n2 : nArray) {
                if (n2 == 0) continue;
                this.add(n2);
            }
        }
    }
}

