/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class ParameterMode
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _PARAM_IN = 0;
    public static final ParameterMode PARAM_IN = new ParameterMode(0);
    public static final int _PARAM_OUT = 1;
    public static final ParameterMode PARAM_OUT = new ParameterMode(1);
    public static final int _PARAM_INOUT = 2;
    public static final ParameterMode PARAM_INOUT = new ParameterMode(2);

    public int value() {
        return this.value;
    }

    public static ParameterMode from_int(int value) {
        switch (value) {
            case 0: {
                return PARAM_IN;
            }
            case 1: {
                return PARAM_OUT;
            }
            case 2: {
                return PARAM_INOUT;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "PARAM_IN";
            }
            case 1: {
                return "PARAM_OUT";
            }
            case 2: {
                return "PARAM_INOUT";
            }
        }
        throw new BAD_PARAM();
    }

    protected ParameterMode(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return ParameterMode.from_int(this.value());
    }
}

