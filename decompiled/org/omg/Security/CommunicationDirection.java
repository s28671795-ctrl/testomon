/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class CommunicationDirection
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecDirectionBoth = 0;
    public static final CommunicationDirection SecDirectionBoth = new CommunicationDirection(0);
    public static final int _SecDirectionRequest = 1;
    public static final CommunicationDirection SecDirectionRequest = new CommunicationDirection(1);
    public static final int _SecDirectionReply = 2;
    public static final CommunicationDirection SecDirectionReply = new CommunicationDirection(2);

    public int value() {
        return this.value;
    }

    public static CommunicationDirection from_int(int value) {
        switch (value) {
            case 0: {
                return SecDirectionBoth;
            }
            case 1: {
                return SecDirectionRequest;
            }
            case 2: {
                return SecDirectionReply;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecDirectionBoth";
            }
            case 1: {
                return "SecDirectionRequest";
            }
            case 2: {
                return "SecDirectionReply";
            }
        }
        throw new BAD_PARAM();
    }

    protected CommunicationDirection(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return CommunicationDirection.from_int(this.value());
    }
}

