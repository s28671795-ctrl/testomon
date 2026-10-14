/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class AuthenticationStatus
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecAuthSuccess = 0;
    public static final AuthenticationStatus SecAuthSuccess = new AuthenticationStatus(0);
    public static final int _SecAuthFailure = 1;
    public static final AuthenticationStatus SecAuthFailure = new AuthenticationStatus(1);
    public static final int _SecAuthContinue = 2;
    public static final AuthenticationStatus SecAuthContinue = new AuthenticationStatus(2);
    public static final int _SecAuthExpired = 3;
    public static final AuthenticationStatus SecAuthExpired = new AuthenticationStatus(3);

    public int value() {
        return this.value;
    }

    public static AuthenticationStatus from_int(int value) {
        switch (value) {
            case 0: {
                return SecAuthSuccess;
            }
            case 1: {
                return SecAuthFailure;
            }
            case 2: {
                return SecAuthContinue;
            }
            case 3: {
                return SecAuthExpired;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecAuthSuccess";
            }
            case 1: {
                return "SecAuthFailure";
            }
            case 2: {
                return "SecAuthContinue";
            }
            case 3: {
                return "SecAuthExpired";
            }
        }
        throw new BAD_PARAM();
    }

    protected AuthenticationStatus(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return AuthenticationStatus.from_int(this.value());
    }
}

