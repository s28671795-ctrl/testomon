/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class MsgType_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _Request = 0;
    public static final MsgType_1_0 Request = new MsgType_1_0(0);
    public static final int _Reply = 1;
    public static final MsgType_1_0 Reply = new MsgType_1_0(1);
    public static final int _CancelRequest = 2;
    public static final MsgType_1_0 CancelRequest = new MsgType_1_0(2);
    public static final int _LocateRequest = 3;
    public static final MsgType_1_0 LocateRequest = new MsgType_1_0(3);
    public static final int _LocateReply = 4;
    public static final MsgType_1_0 LocateReply = new MsgType_1_0(4);
    public static final int _CloseConnection = 5;
    public static final MsgType_1_0 CloseConnection = new MsgType_1_0(5);
    public static final int _MessageError = 6;
    public static final MsgType_1_0 MessageError = new MsgType_1_0(6);

    public int value() {
        return this.value;
    }

    public static MsgType_1_0 from_int(int value) {
        switch (value) {
            case 0: {
                return Request;
            }
            case 1: {
                return Reply;
            }
            case 2: {
                return CancelRequest;
            }
            case 3: {
                return LocateRequest;
            }
            case 4: {
                return LocateReply;
            }
            case 5: {
                return CloseConnection;
            }
            case 6: {
                return MessageError;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "Request";
            }
            case 1: {
                return "Reply";
            }
            case 2: {
                return "CancelRequest";
            }
            case 3: {
                return "LocateRequest";
            }
            case 4: {
                return "LocateReply";
            }
            case 5: {
                return "CloseConnection";
            }
            case 6: {
                return "MessageError";
            }
        }
        throw new BAD_PARAM();
    }

    protected MsgType_1_0(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return MsgType_1_0.from_int(this.value());
    }
}

