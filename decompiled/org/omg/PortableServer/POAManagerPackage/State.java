/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerPackage;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class State
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _HOLDING = 0;
    public static final State HOLDING = new State(0);
    public static final int _ACTIVE = 1;
    public static final State ACTIVE = new State(1);
    public static final int _DISCARDING = 2;
    public static final State DISCARDING = new State(2);
    public static final int _INACTIVE = 3;
    public static final State INACTIVE = new State(3);

    public int value() {
        return this.value;
    }

    public static State from_int(int value) {
        switch (value) {
            case 0: {
                return HOLDING;
            }
            case 1: {
                return ACTIVE;
            }
            case 2: {
                return DISCARDING;
            }
            case 3: {
                return INACTIVE;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "HOLDING";
            }
            case 1: {
                return "ACTIVE";
            }
            case 2: {
                return "DISCARDING";
            }
            case 3: {
                return "INACTIVE";
            }
        }
        throw new BAD_PARAM();
    }

    protected State(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return State.from_int(this.value());
    }
}

