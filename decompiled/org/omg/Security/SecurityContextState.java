/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class SecurityContextState
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecContextInitialized = 0;
    public static final SecurityContextState SecContextInitialized = new SecurityContextState(0);
    public static final int _SecContextContinued = 1;
    public static final SecurityContextState SecContextContinued = new SecurityContextState(1);
    public static final int _SecContextClientEstablished = 2;
    public static final SecurityContextState SecContextClientEstablished = new SecurityContextState(2);
    public static final int _SecContextEstablished = 3;
    public static final SecurityContextState SecContextEstablished = new SecurityContextState(3);
    public static final int _SecContextEstablishExpired = 4;
    public static final SecurityContextState SecContextEstablishExpired = new SecurityContextState(4);
    public static final int _SecContextExpired = 5;
    public static final SecurityContextState SecContextExpired = new SecurityContextState(5);
    public static final int _SecContextInvalid = 6;
    public static final SecurityContextState SecContextInvalid = new SecurityContextState(6);

    public int value() {
        return this.value;
    }

    public static SecurityContextState from_int(int value) {
        switch (value) {
            case 0: {
                return SecContextInitialized;
            }
            case 1: {
                return SecContextContinued;
            }
            case 2: {
                return SecContextClientEstablished;
            }
            case 3: {
                return SecContextEstablished;
            }
            case 4: {
                return SecContextEstablishExpired;
            }
            case 5: {
                return SecContextExpired;
            }
            case 6: {
                return SecContextInvalid;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecContextInitialized";
            }
            case 1: {
                return "SecContextContinued";
            }
            case 2: {
                return "SecContextClientEstablished";
            }
            case 3: {
                return "SecContextEstablished";
            }
            case 4: {
                return "SecContextEstablishExpired";
            }
            case 5: {
                return "SecContextExpired";
            }
            case 6: {
                return "SecContextInvalid";
            }
        }
        throw new BAD_PARAM();
    }

    protected SecurityContextState(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return SecurityContextState.from_int(this.value());
    }
}

