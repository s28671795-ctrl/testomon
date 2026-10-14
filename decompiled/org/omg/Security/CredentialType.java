/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class CredentialType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecInvocationCredentials = 0;
    public static final CredentialType SecInvocationCredentials = new CredentialType(0);
    public static final int _SecNRCredentials = 1;
    public static final CredentialType SecNRCredentials = new CredentialType(1);

    public int value() {
        return this.value;
    }

    public static CredentialType from_int(int value) {
        switch (value) {
            case 0: {
                return SecInvocationCredentials;
            }
            case 1: {
                return SecNRCredentials;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecInvocationCredentials";
            }
            case 1: {
                return "SecNRCredentials";
            }
        }
        throw new BAD_PARAM();
    }

    protected CredentialType(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return CredentialType.from_int(this.value());
    }
}

