/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class BindingType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _nobject = 0;
    public static final BindingType nobject = new BindingType(0);
    public static final int _ncontext = 1;
    public static final BindingType ncontext = new BindingType(1);

    public int value() {
        return this.value;
    }

    public static BindingType from_int(int value) {
        switch (value) {
            case 0: {
                return nobject;
            }
            case 1: {
                return ncontext;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "nobject";
            }
            case 1: {
                return "ncontext";
            }
        }
        throw new BAD_PARAM();
    }

    protected BindingType(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return BindingType.from_int(this.value());
    }
}

