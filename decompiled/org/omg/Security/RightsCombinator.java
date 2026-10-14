/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class RightsCombinator
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecAllRights = 0;
    public static final RightsCombinator SecAllRights = new RightsCombinator(0);
    public static final int _SecAnyRight = 1;
    public static final RightsCombinator SecAnyRight = new RightsCombinator(1);

    public int value() {
        return this.value;
    }

    public static RightsCombinator from_int(int value) {
        switch (value) {
            case 0: {
                return SecAllRights;
            }
            case 1: {
                return SecAnyRight;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecAllRights";
            }
            case 1: {
                return "SecAnyRight";
            }
        }
        throw new BAD_PARAM();
    }

    protected RightsCombinator(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return RightsCombinator.from_int(this.value());
    }
}

