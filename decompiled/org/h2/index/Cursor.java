/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.result.Row;
import org.h2.result.SearchRow;

public interface Cursor {
    public Row get();

    public SearchRow getSearchRow();

    public boolean next();

    public boolean previous();
}

