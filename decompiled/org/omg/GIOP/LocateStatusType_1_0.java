/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class LocateStatusType_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _UNKNOWN_OBJECT = 0;
    public static final LocateStatusType_1_0 UNKNOWN_OBJECT = new LocateStatusType_1_0(0);
    public static final int _OBJECT_HERE = 1;
    public static final LocateStatusType_1_0 OBJECT_HERE = new LocateStatusType_1_0(1);
    public static final int _OBJECT_FORWARD = 2;
    public static final LocateStatusType_1_0 OBJECT_FORWARD = new LocateStatusType_1_0(2);

    public int value() {
        return this.value;
    }

    public static LocateStatusType_1_0 from_int(int value) {
        switch (value) {
            case 0: {
                return UNKNOWN_OBJECT;
            }
            case 1: {
                return OBJECT_HERE;
            }
            case 2: {
                return OBJECT_FORWARD;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "UNKNOWN_OBJECT";
            }
            case 1: {
                return "OBJECT_HERE";
            }
            case 2: {
                return "OBJECT_FORWARD";
            }
        }
        throw new BAD_PARAM();
    }

    protected LocateStatusType_1_0(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return LocateStatusType_1_0.from_int(this.value());
    }
}

