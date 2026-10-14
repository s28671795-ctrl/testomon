/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class ReplyStatusType_1_2
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _NO_EXCEPTION = 0;
    public static final ReplyStatusType_1_2 NO_EXCEPTION = new ReplyStatusType_1_2(0);
    public static final int _USER_EXCEPTION = 1;
    public static final ReplyStatusType_1_2 USER_EXCEPTION = new ReplyStatusType_1_2(1);
    public static final int _SYSTEM_EXCEPTION = 2;
    public static final ReplyStatusType_1_2 SYSTEM_EXCEPTION = new ReplyStatusType_1_2(2);
    public static final int _LOCATION_FORWARD = 3;
    public static final ReplyStatusType_1_2 LOCATION_FORWARD = new ReplyStatusType_1_2(3);
    public static final int _LOCATION_FORWARD_PERM = 4;
    public static final ReplyStatusType_1_2 LOCATION_FORWARD_PERM = new ReplyStatusType_1_2(4);
    public static final int _NEEDS_ADDRESSING_MODE = 5;
    public static final ReplyStatusType_1_2 NEEDS_ADDRESSING_MODE = new ReplyStatusType_1_2(5);

    public int value() {
        return this.value;
    }

    public static ReplyStatusType_1_2 from_int(int value) {
        switch (value) {
            case 0: {
                return NO_EXCEPTION;
            }
            case 1: {
                return USER_EXCEPTION;
            }
            case 2: {
                return SYSTEM_EXCEPTION;
            }
            case 3: {
                return LOCATION_FORWARD;
            }
            case 4: {
                return LOCATION_FORWARD_PERM;
            }
            case 5: {
                return NEEDS_ADDRESSING_MODE;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "NO_EXCEPTION";
            }
            case 1: {
                return "USER_EXCEPTION";
            }
            case 2: {
                return "SYSTEM_EXCEPTION";
            }
            case 3: {
                return "LOCATION_FORWARD";
            }
            case 4: {
                return "LOCATION_FORWARD_PERM";
            }
            case 5: {
                return "NEEDS_ADDRESSING_MODE";
            }
        }
        throw new BAD_PARAM();
    }

    protected ReplyStatusType_1_2(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return ReplyStatusType_1_2.from_int(this.value());
    }
}

