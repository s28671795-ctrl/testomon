/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.WindowDefinition;
import org.jooq.impl.QueryPartList;

final class WindowList
extends QueryPartList<WindowDefinition> {
    WindowList() {
    }

    @Override
    public final boolean declaresWindows() {
        return true;
    }
}

