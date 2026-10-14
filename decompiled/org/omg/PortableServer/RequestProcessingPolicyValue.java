/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class RequestProcessingPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _USE_ACTIVE_OBJECT_MAP_ONLY = 0;
    public static final RequestProcessingPolicyValue USE_ACTIVE_OBJECT_MAP_ONLY = new RequestProcessingPolicyValue(0);
    public static final int _USE_DEFAULT_SERVANT = 1;
    public static final RequestProcessingPolicyValue USE_DEFAULT_SERVANT = new RequestProcessingPolicyValue(1);
    public static final int _USE_SERVANT_MANAGER = 2;
    public static final RequestProcessingPolicyValue USE_SERVANT_MANAGER = new RequestProcessingPolicyValue(2);

    public int value() {
        return this.value;
    }

    public static RequestProcessingPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return USE_ACTIVE_OBJECT_MAP_ONLY;
            }
            case 1: {
                return USE_DEFAULT_SERVANT;
            }
            case 2: {
                return USE_SERVANT_MANAGER;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "USE_ACTIVE_OBJECT_MAP_ONLY";
            }
            case 1: {
                return "USE_DEFAULT_SERVANT";
            }
            case 2: {
                return "USE_SERVANT_MANAGER";
            }
        }
        throw new BAD_PARAM();
    }

    protected RequestProcessingPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return RequestProcessingPolicyValue.from_int(this.value());
    }
}

