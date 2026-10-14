/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class LifespanPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _TRANSIENT = 0;
    public static final LifespanPolicyValue TRANSIENT = new LifespanPolicyValue(0);
    public static final int _PERSISTENT = 1;
    public static final LifespanPolicyValue PERSISTENT = new LifespanPolicyValue(1);

    public int value() {
        return this.value;
    }

    public static LifespanPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return TRANSIENT;
            }
            case 1: {
                return PERSISTENT;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "TRANSIENT";
            }
            case 1: {
                return "PERSISTENT";
            }
        }
        throw new BAD_PARAM();
    }

    protected LifespanPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return LifespanPolicyValue.from_int(this.value());
    }
}

