/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.util.List;
import liquibase.change.ColumnConfig;

public interface ChangeWithColumns<T extends ColumnConfig> {
    public void addColumn(T var1);

    public List<T> getColumns();

    public void setColumns(List<T> var1);
}

