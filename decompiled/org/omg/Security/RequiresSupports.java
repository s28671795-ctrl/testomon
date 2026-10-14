/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class RequiresSupports
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecRequires = 0;
    public static final RequiresSupports SecRequires = new RequiresSupports(0);
    public static final int _SecSupports = 1;
    public static final RequiresSupports SecSupports = new RequiresSupports(1);

    public int value() {
        return this.value;
    }

    public static RequiresSupports from_int(int value) {
        switch (value) {
            case 0: {
                return SecRequires;
            }
            case 1: {
                return SecSupports;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecRequires";
            }
            case 1: {
                return "SecSupports";
            }
        }
        throw new BAD_PARAM();
    }

    protected RequiresSupports(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return RequiresSupports.from_int(this.value());
    }
}

