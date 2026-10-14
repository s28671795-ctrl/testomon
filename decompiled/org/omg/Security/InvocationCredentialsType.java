/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class InvocationCredentialsType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecOwnCredentials = 0;
    public static final InvocationCredentialsType SecOwnCredentials = new InvocationCredentialsType(0);
    public static final int _SecReceivedCredentials = 1;
    public static final InvocationCredentialsType SecReceivedCredentials = new InvocationCredentialsType(1);

    public int value() {
        return this.value;
    }

    public static InvocationCredentialsType from_int(int value) {
        switch (value) {
            case 0: {
                return SecOwnCredentials;
            }
            case 1: {
                return SecReceivedCredentials;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecOwnCredentials";
            }
            case 1: {
                return "SecReceivedCredentials";
            }
        }
        throw new BAD_PARAM();
    }

    protected InvocationCredentialsType(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return InvocationCredentialsType.from_int(this.value());
    }
}

