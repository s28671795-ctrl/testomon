/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecPackage;

import org.omg.CORBA.UserException;
import org.omg.IOP.CodecPackage.InvalidTypeForEncodingHelper;

public final class InvalidTypeForEncoding
extends UserException {
    private static final long serialVersionUID = 1L;

    public InvalidTypeForEncoding() {
        super(InvalidTypeForEncodingHelper.id());
    }

    public InvalidTypeForEncoding(String value) {
        super(value);
    }
}

