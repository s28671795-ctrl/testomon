/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;

public class ConnectException
extends LdapException {
    private static final long serialVersionUID = -5935483002226156942L;

    public ConnectException(ResultCode code, String msg) {
        super(code, msg);
    }

    public ConnectException(ResultCode code, Throwable e) {
        super(code, e);
    }

    public ConnectException(ResultCode code, String msg, Throwable e) {
        super(code, msg, e);
    }
}

