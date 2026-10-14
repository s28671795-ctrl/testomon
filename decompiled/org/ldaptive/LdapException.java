/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.Result;
import org.ldaptive.ResultCode;

public class LdapException
extends Exception {
    private static final long serialVersionUID = 6812614366508784841L;
    private final ResultCode resultCode;

    public LdapException(Result result) {
        this(result.getResultCode(), LdapException.formatResult(result));
    }

    public LdapException(String msg) {
        this(null, msg);
    }

    public LdapException(ResultCode code, String msg) {
        super(msg);
        this.resultCode = code;
    }

    public LdapException(Throwable e) {
        this((ResultCode)null, e);
    }

    public LdapException(ResultCode code, Throwable e) {
        super(e);
        this.resultCode = code;
    }

    public LdapException(String msg, Throwable e) {
        this(null, msg, e);
    }

    public LdapException(ResultCode code, String msg, Throwable e) {
        super(msg, e);
        this.resultCode = code;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    protected static String formatResult(Result result) {
        return "resultCode=" + (Object)((Object)result.getResultCode()) + ", " + "diagnosticMessage=" + result.getEncodedDiagnosticMessage();
    }
}

