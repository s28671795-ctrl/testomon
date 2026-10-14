/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.IllegalTokenRequestHelper;
import org.omg.CORBA.UserException;

public final class IllegalTokenRequest
extends UserException {
    private static final long serialVersionUID = 1L;
    public int the_errnum;
    public String the_reason = "";

    public IllegalTokenRequest() {
        super(IllegalTokenRequestHelper.id());
    }

    public IllegalTokenRequest(String _reason, int the_errnum, String the_reason) {
        super(_reason);
        this.the_errnum = the_errnum;
        this.the_reason = the_reason;
    }

    public IllegalTokenRequest(int the_errnum, String the_reason) {
        super(IllegalTokenRequestHelper.id());
        this.the_errnum = the_errnum;
        this.the_reason = the_reason;
    }
}

