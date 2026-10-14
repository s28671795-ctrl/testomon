/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class PriorityModel
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _CLIENT_PROPAGATED = 0;
    public static final PriorityModel CLIENT_PROPAGATED = new PriorityModel(0);
    public static final int _SERVER_DECLARED = 1;
    public static final PriorityModel SERVER_DECLARED = new PriorityModel(1);

    public int value() {
        return this.value;
    }

    public static PriorityModel from_int(int value) {
        switch (value) {
            case 0: {
                return CLIENT_PROPAGATED;
            }
            case 1: {
                return SERVER_DECLARED;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "CLIENT_PROPAGATED";
            }
            case 1: {
                return "SERVER_DECLARED";
            }
        }
        throw new BAD_PARAM();
    }

    protected PriorityModel(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return PriorityModel.from_int(this.value());
    }
}

