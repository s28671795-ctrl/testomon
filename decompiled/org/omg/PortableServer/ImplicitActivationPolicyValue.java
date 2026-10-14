/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class ImplicitActivationPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _IMPLICIT_ACTIVATION = 0;
    public static final ImplicitActivationPolicyValue IMPLICIT_ACTIVATION = new ImplicitActivationPolicyValue(0);
    public static final int _NO_IMPLICIT_ACTIVATION = 1;
    public static final ImplicitActivationPolicyValue NO_IMPLICIT_ACTIVATION = new ImplicitActivationPolicyValue(1);

    public int value() {
        return this.value;
    }

    public static ImplicitActivationPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return IMPLICIT_ACTIVATION;
            }
            case 1: {
                return NO_IMPLICIT_ACTIVATION;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "IMPLICIT_ACTIVATION";
            }
            case 1: {
                return "NO_IMPLICIT_ACTIVATION";
            }
        }
        throw new BAD_PARAM();
    }

    protected ImplicitActivationPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return ImplicitActivationPolicyValue.from_int(this.value());
    }
}

