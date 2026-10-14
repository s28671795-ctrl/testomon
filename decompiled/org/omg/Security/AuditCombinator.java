/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class AuditCombinator
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecAllSelectors = 0;
    public static final AuditCombinator SecAllSelectors = new AuditCombinator(0);
    public static final int _SecAnySelector = 1;
    public static final AuditCombinator SecAnySelector = new AuditCombinator(1);

    public int value() {
        return this.value;
    }

    public static AuditCombinator from_int(int value) {
        switch (value) {
            case 0: {
                return SecAllSelectors;
            }
            case 1: {
                return SecAnySelector;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecAllSelectors";
            }
            case 1: {
                return "SecAnySelector";
            }
        }
        throw new BAD_PARAM();
    }

    protected AuditCombinator(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return AuditCombinator.from_int(this.value());
    }
}

