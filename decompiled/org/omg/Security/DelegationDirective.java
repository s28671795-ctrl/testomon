/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class DelegationDirective
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _Delegate = 0;
    public static final DelegationDirective Delegate = new DelegationDirective(0);
    public static final int _NoDelegate = 1;
    public static final DelegationDirective NoDelegate = new DelegationDirective(1);

    public int value() {
        return this.value;
    }

    public static DelegationDirective from_int(int value) {
        switch (value) {
            case 0: {
                return Delegate;
            }
            case 1: {
                return NoDelegate;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "Delegate";
            }
            case 1: {
                return "NoDelegate";
            }
        }
        throw new BAD_PARAM();
    }

    protected DelegationDirective(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return DelegationDirective.from_int(this.value());
    }
}

