/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public class OperationMode
implements IDLEntity {
    private int __value;
    private static int __size = 2;
    private static OperationMode[] __array = new OperationMode[__size];
    public static final int _OP_NORMAL = 0;
    public static final OperationMode OP_NORMAL = new OperationMode(0);
    public static final int _OP_ONEWAY = 1;
    public static final OperationMode OP_ONEWAY = new OperationMode(1);

    public int value() {
        return this.__value;
    }

    public static OperationMode from_int(int value) {
        if (value >= 0 && value < __size) {
            return __array[value];
        }
        throw new BAD_PARAM();
    }

    protected OperationMode(int value) {
        this.__value = value;
        OperationMode.__array[this.__value] = this;
    }
}

