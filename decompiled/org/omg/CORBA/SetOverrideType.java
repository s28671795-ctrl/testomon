/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class SetOverrideType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SET_OVERRIDE = 0;
    public static final SetOverrideType SET_OVERRIDE = new SetOverrideType(0);
    public static final int _ADD_OVERRIDE = 1;
    public static final SetOverrideType ADD_OVERRIDE = new SetOverrideType(1);

    public int value() {
        return this.value;
    }

    public static SetOverrideType from_int(int value) {
        switch (value) {
            case 0: {
                return SET_OVERRIDE;
            }
            case 1: {
                return ADD_OVERRIDE;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SET_OVERRIDE";
            }
            case 1: {
                return "ADD_OVERRIDE";
            }
        }
        throw new BAD_PARAM();
    }

    protected SetOverrideType(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return SetOverrideType.from_int(this.value());
    }
}

