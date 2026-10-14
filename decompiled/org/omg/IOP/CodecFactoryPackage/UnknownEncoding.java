/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecFactoryPackage;

import org.omg.CORBA.UserException;
import org.omg.IOP.CodecFactoryPackage.UnknownEncodingHelper;

public final class UnknownEncoding
extends UserException {
    private static final long serialVersionUID = 1L;

    public UnknownEncoding() {
        super(UnknownEncodingHelper.id());
    }

    public UnknownEncoding(String value) {
        super(value);
    }
}

