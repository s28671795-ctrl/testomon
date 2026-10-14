/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;

public abstract class HttpAction
extends TechnicalException {
    private static final long serialVersionUID = -3959659239684160075L;
    protected int code;

    protected HttpAction(int code) {
        super("Performing a " + code + " HTTP action");
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "code", this.code);
    }
}

