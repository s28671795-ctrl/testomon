/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;

public class FilterParseException
extends LdapException {
    private static final long serialVersionUID = 2314015446271971772L;

    public FilterParseException(ResultCode code, String msg) {
        super(code, msg);
    }

    public FilterParseException(ResultCode code, Throwable e) {
        super(code, e);
    }

    public FilterParseException(ResultCode code, String msg, Throwable e) {
        super(code, msg, e);
    }
}

