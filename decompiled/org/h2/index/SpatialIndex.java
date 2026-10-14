/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.result.SearchRow;

public interface SpatialIndex {
    public Cursor findByGeometry(SessionLocal var1, SearchRow var2, SearchRow var3, SearchRow var4);
}

