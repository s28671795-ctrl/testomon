/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class ThreadPolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _ORB_CTRL_MODEL = 0;
    public static final ThreadPolicyValue ORB_CTRL_MODEL = new ThreadPolicyValue(0);
    public static final int _SINGLE_THREAD_MODEL = 1;
    public static final ThreadPolicyValue SINGLE_THREAD_MODEL = new ThreadPolicyValue(1);
    public static final int _MAIN_THREAD_MODEL = 2;
    public static final ThreadPolicyValue MAIN_THREAD_MODEL = new ThreadPolicyValue(2);

    public int value() {
        return this.value;
    }

    public static ThreadPolicyValue from_int(int value) {
        switch (value) {
            case 0: {
                return ORB_CTRL_MODEL;
            }
            case 1: {
                return SINGLE_THREAD_MODEL;
            }
            case 2: {
                return MAIN_THREAD_MODEL;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "ORB_CTRL_MODEL";
            }
            case 1: {
                return "SINGLE_THREAD_MODEL";
            }
            case 2: {
                return "MAIN_THREAD_MODEL";
            }
        }
        throw new BAD_PARAM();
    }

    protected ThreadPolicyValue(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return ThreadPolicyValue.from_int(this.value());
    }
}

