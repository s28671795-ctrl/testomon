/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class SecurityContextType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecClientSecurityContext = 0;
    public static final SecurityContextType SecClientSecurityContext = new SecurityContextType(0);
    public static final int _SecServerSecurityContext = 1;
    public static final SecurityContextType SecServerSecurityContext = new SecurityContextType(1);

    public int value() {
        return this.value;
    }

    public static SecurityContextType from_int(int value) {
        switch (value) {
            case 0: {
                return SecClientSecurityContext;
            }
            case 1: {
                return SecServerSecurityContext;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecClientSecurityContext";
            }
            case 1: {
                return "SecServerSecurityContext";
            }
        }
        throw new BAD_PARAM();
    }

    protected SecurityContextType(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return SecurityContextType.from_int(this.value());
    }
}

