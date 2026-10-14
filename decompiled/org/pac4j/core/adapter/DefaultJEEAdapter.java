/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.adapter;

import org.pac4j.core.adapter.JEEAdapter;

public class DefaultJEEAdapter
extends JEEAdapter {
    @Override
    public int compareManagers(Object obj1, Object obj2) {
        if (obj1 != null && obj2 != null) {
            return obj2.getClass().getSimpleName().compareTo(obj1.getClass().getSimpleName());
        }
        return 0;
    }

    public String toString() {
        return "default";
    }
}

