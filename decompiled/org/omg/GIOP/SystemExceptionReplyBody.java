/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class SystemExceptionReplyBody
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String exception_id = "";
    public int minor_code_value;
    public int completion_status;

    public SystemExceptionReplyBody() {
    }

    public SystemExceptionReplyBody(String exception_id, int minor_code_value, int completion_status) {
        this.exception_id = exception_id;
        this.minor_code_value = minor_code_value;
        this.completion_status = completion_status;
    }
}

