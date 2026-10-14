/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.conf;

import java.io.Serializable;

abstract class SettingsBase
implements Serializable,
Cloneable {
    SettingsBase() {
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

