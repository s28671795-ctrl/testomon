/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.IOP.CodecOperations;
import org.omg.IOP.CodecPackage.FormatMismatch;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;
import org.omg.IOP.CodecPackage.TypeMismatch;
import org.omg.IOP._CodecLocalBase;

public class CodecLocalTie
extends _CodecLocalBase {
    private static final long serialVersionUID = 1L;
    private CodecOperations _delegate;

    public CodecLocalTie(CodecOperations delegate) {
        this._delegate = delegate;
    }

    public CodecOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(CodecOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public Any decode(byte[] data) throws FormatMismatch {
        return this._delegate.decode(data);
    }

    @Override
    public Any decode_value(byte[] data, TypeCode tc) throws TypeMismatch, FormatMismatch {
        return this._delegate.decode_value(data, tc);
    }

    @Override
    public byte[] encode_value(Any data) throws InvalidTypeForEncoding {
        return this._delegate.encode_value(data);
    }

    @Override
    public byte[] encode(Any data) throws InvalidTypeForEncoding {
        return this._delegate.encode(data);
    }
}

