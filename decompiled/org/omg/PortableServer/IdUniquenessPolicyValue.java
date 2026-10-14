/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class IdUniquenessPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _UNIQUE_ID = 0;
    public static final IdUniquenessPolicyValue UNIQUE_ID = new IdUniquenessPolicyValue(0);
    public static final int _MULTIPLE_ID = 1;
    public static final IdUniquenessPolicyValue MULTIPLE_ID = new IdUniquenessPolicyValue(1);

    public int value() {
        return this.value;
    }

    public static IdUniquenessPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return UNIQUE_ID;
            }
            case 1: {
                return MULTIPLE_ID;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "UNIQUE_ID";
            }
            case 1: {
                return "MULTIPLE_ID";
            }
        }
        throw new BAD_PARAM();
    }

    protected IdUniquenessPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return IdUniquenessPolicyValue.from_int(this.value());
    }
}

