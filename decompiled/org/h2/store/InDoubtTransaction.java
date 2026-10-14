/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import org.h2.message.DbException;

public interface InDoubtTransaction {
    public static final int IN_DOUBT = 0;
    public static final int COMMIT = 1;
    public static final int ROLLBACK = 2;

    public void setState(int var1);

    public int getState();

    default public String getStateDescription() {
        int n = this.getState();
        switch (n) {
            case 0: {
                return "IN_DOUBT";
            }
            case 1: {
                return "COMMIT";
            }
            case 2: {
                return "ROLLBACK";
            }
        }
        throw DbException.getInternalError("state=" + n);
    }

    public String getTransactionName();
}

