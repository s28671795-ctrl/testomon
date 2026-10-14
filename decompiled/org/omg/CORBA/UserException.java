/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.portable.IDLEntity;

public abstract class UserException
extends Exception
implements IDLEntity {
    public UserException() {
    }

    public UserException(String value) {
        super(value);
    }
}

