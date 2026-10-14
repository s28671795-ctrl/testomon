/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class OperationMode
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _OP_NORMAL = 0;
    public static final OperationMode OP_NORMAL = new OperationMode(0);
    public static final int _OP_ONEWAY = 1;
    public static final OperationMode OP_ONEWAY = new OperationMode(1);

    public int value() {
        return this.value;
    }

    public static OperationMode from_int(int value) {
        switch (value) {
            case 0: {
                return OP_NORMAL;
            }
            case 1: {
                return OP_ONEWAY;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "OP_NORMAL";
            }
            case 1: {
                return "OP_ONEWAY";
            }
        }
        throw new BAD_PARAM();
    }

    protected OperationMode(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return OperationMode.from_int(this.value());
    }
}

