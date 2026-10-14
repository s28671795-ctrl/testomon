/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class ServantRetentionPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _RETAIN = 0;
    public static final ServantRetentionPolicyValue RETAIN = new ServantRetentionPolicyValue(0);
    public static final int _NON_RETAIN = 1;
    public static final ServantRetentionPolicyValue NON_RETAIN = new ServantRetentionPolicyValue(1);

    public int value() {
        return this.value;
    }

    public static ServantRetentionPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return RETAIN;
            }
            case 1: {
                return NON_RETAIN;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "RETAIN";
            }
            case 1: {
                return "NON_RETAIN";
            }
        }
        throw new BAD_PARAM();
    }

    protected ServantRetentionPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return ServantRetentionPolicyValue.from_int(this.value());
    }
}

