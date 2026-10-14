/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.Collection;
import org.h2.value.Value;

public interface ResultExternal {
    public void reset();

    public Value[] next();

    public int addRow(Value[] var1);

    public int addRows(Collection<Value[]> var1);

    public void close();

    public int removeRow(Value[] var1);

    public boolean contains(Value[] var1);

    public ResultExternal createShallowCopy();
}

