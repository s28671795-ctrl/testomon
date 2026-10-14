/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class DelegationMode
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecDelModeNoDelegation = 0;
    public static final DelegationMode SecDelModeNoDelegation = new DelegationMode(0);
    public static final int _SecDelModeSimpleDelegation = 1;
    public static final DelegationMode SecDelModeSimpleDelegation = new DelegationMode(1);
    public static final int _SecDelModeCompositeDelegation = 2;
    public static final DelegationMode SecDelModeCompositeDelegation = new DelegationMode(2);

    public int value() {
        return this.value;
    }

    public static DelegationMode from_int(int value) {
        switch (value) {
            case 0: {
                return SecDelModeNoDelegation;
            }
            case 1: {
                return SecDelModeSimpleDelegation;
            }
            case 2: {
                return SecDelModeCompositeDelegation;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecDelModeNoDelegation";
            }
            case 1: {
                return "SecDelModeSimpleDelegation";
            }
            case 2: {
                return "SecDelModeCompositeDelegation";
            }
        }
        throw new BAD_PARAM();
    }

    protected DelegationMode(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return DelegationMode.from_int(this.value());
    }
}

