/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public class ParameterMode
implements IDLEntity {
    private int __value;
    private static int __size = 3;
    private static ParameterMode[] __array = new ParameterMode[__size];
    public static final int _PARAM_IN = 0;
    public static final ParameterMode PARAM_IN = new ParameterMode(0);
    public static final int _PARAM_OUT = 1;
    public static final ParameterMode PARAM_OUT = new ParameterMode(1);
    public static final int _PARAM_INOUT = 2;
    public static final ParameterMode PARAM_INOUT = new ParameterMode(2);

    public int value() {
        return this.__value;
    }

    public static ParameterMode from_int(int value) {
        if (value >= 0 && value < __size) {
            return __array[value];
        }
        throw new BAD_PARAM();
    }

    protected ParameterMode(int value) {
        this.__value = value;
        ParameterMode.__array[this.__value] = this;
    }
}

