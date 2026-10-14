/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.control.ResponseControl;

public interface Message {
    public int getMessageID();

    public ResponseControl[] getControls();

    default public ResponseControl getControl(String oid) {
        if (this.getControls() != null) {
            for (ResponseControl c : this.getControls()) {
                if (c == null || !c.getOID().equals(oid)) continue;
                return c;
            }
        }
        return null;
    }
}

