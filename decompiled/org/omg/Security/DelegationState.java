/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class DelegationState
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecInitiator = 0;
    public static final DelegationState SecInitiator = new DelegationState(0);
    public static final int _SecDelegate = 1;
    public static final DelegationState SecDelegate = new DelegationState(1);

    public int value() {
        return this.value;
    }

    public static DelegationState from_int(int value) {
        switch (value) {
            case 0: {
                return SecInitiator;
            }
            case 1: {
                return SecDelegate;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecInitiator";
            }
            case 1: {
                return "SecDelegate";
            }
        }
        throw new BAD_PARAM();
    }

    protected DelegationState(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return DelegationState.from_int(this.value());
    }
}

