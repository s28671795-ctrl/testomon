/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class NotFoundReason
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _missing_node = 0;
    public static final NotFoundReason missing_node = new NotFoundReason(0);
    public static final int _not_context = 1;
    public static final NotFoundReason not_context = new NotFoundReason(1);
    public static final int _not_object = 2;
    public static final NotFoundReason not_object = new NotFoundReason(2);

    public int value() {
        return this.value;
    }

    public static NotFoundReason from_int(int value) {
        switch (value) {
            case 0: {
                return missing_node;
            }
            case 1: {
                return not_context;
            }
            case 2: {
                return not_object;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "missing_node";
            }
            case 1: {
                return "not_context";
            }
            case 2: {
                return "not_object";
            }
        }
        throw new BAD_PARAM();
    }

    protected NotFoundReason(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return NotFoundReason.from_int(this.value());
    }
}

