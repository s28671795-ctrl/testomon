/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class AttributeMode
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _ATTR_NORMAL = 0;
    public static final AttributeMode ATTR_NORMAL = new AttributeMode(0);
    public static final int _ATTR_READONLY = 1;
    public static final AttributeMode ATTR_READONLY = new AttributeMode(1);

    public int value() {
        return this.value;
    }

    public static AttributeMode from_int(int value) {
        switch (value) {
            case 0: {
                return ATTR_NORMAL;
            }
            case 1: {
                return ATTR_READONLY;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "ATTR_NORMAL";
            }
            case 1: {
                return "ATTR_READONLY";
            }
        }
        throw new BAD_PARAM();
    }

    protected AttributeMode(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return AttributeMode.from_int(this.value());
    }
}

