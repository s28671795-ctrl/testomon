/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class IdAssignmentPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _USER_ID = 0;
    public static final IdAssignmentPolicyValue USER_ID = new IdAssignmentPolicyValue(0);
    public static final int _SYSTEM_ID = 1;
    public static final IdAssignmentPolicyValue SYSTEM_ID = new IdAssignmentPolicyValue(1);

    public int value() {
        return this.value;
    }

    public static IdAssignmentPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return USER_ID;
            }
            case 1: {
                return SYSTEM_ID;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "USER_ID";
            }
            case 1: {
                return "SYSTEM_ID";
            }
        }
        throw new BAD_PARAM();
    }

    protected IdAssignmentPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return IdAssignmentPolicyValue.from_int(this.value());
    }
}

