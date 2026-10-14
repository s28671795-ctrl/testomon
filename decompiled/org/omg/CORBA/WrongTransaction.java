/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.UserException;
import org.omg.CORBA.WrongTransactionHelper;

public final class WrongTransaction
extends UserException {
    private static final long serialVersionUID = 1L;

    public WrongTransaction() {
        super(WrongTransactionHelper.id());
    }

    public WrongTransaction(String value) {
        super(value);
    }
}

