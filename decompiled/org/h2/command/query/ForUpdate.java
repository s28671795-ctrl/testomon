/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import org.h2.message.DbException;
import org.h2.util.HasSQL;
import org.h2.util.StringUtils;

public final class ForUpdate
implements HasSQL {
    public static final ForUpdate DEFAULT = new ForUpdate(Type.DEFAULT, -1);
    public static final ForUpdate NOWAIT = new ForUpdate(Type.NOWAIT, 0);
    public static final ForUpdate SKIP_LOCKED = new ForUpdate(Type.SKIP_LOCKED, -2);
    private final Type type;
    private final int timeoutMillis;

    public static final ForUpdate wait(int n) {
        if (n < 0) {
            throw DbException.getInvalidValueException("timeout", n);
        }
        if (n == 0) {
            return NOWAIT;
        }
        return new ForUpdate(Type.WAIT, n);
    }

    private ForUpdate(Type type, int n) {
        this.type = type;
        this.timeoutMillis = n;
    }

    public Type getType() {
        return this.type;
    }

    public int getTimeoutMillis() {
        return this.timeoutMillis;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(" FOR UPDATE");
        switch (this.type) {
            case WAIT: {
                stringBuilder.append(" WAIT ").append(this.timeoutMillis / 1000);
                int n2 = this.timeoutMillis % 1000;
                if (n2 <= 0) break;
                StringUtils.appendZeroPadded(stringBuilder.append('.'), 3, n2);
                break;
            }
            case NOWAIT: {
                stringBuilder.append(" NOWAIT");
                break;
            }
            case SKIP_LOCKED: {
                stringBuilder.append(" SKIP LOCKED");
                break;
            }
        }
        return stringBuilder;
    }

    public static enum Type {
        DEFAULT,
        WAIT,
        NOWAIT,
        SKIP_LOCKED;

    }
}

