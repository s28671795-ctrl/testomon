/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.IOP.Codec;
import org.omg.IOP.CodecFactoryOperations;
import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;
import org.omg.IOP.Encoding;
import org.omg.IOP._CodecFactoryLocalBase;

public class CodecFactoryLocalTie
extends _CodecFactoryLocalBase {
    private static final long serialVersionUID = 1L;
    private CodecFactoryOperations _delegate;

    public CodecFactoryLocalTie(CodecFactoryOperations delegate) {
        this._delegate = delegate;
    }

    public CodecFactoryOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(CodecFactoryOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public Codec create_codec(Encoding enc) throws UnknownEncoding {
        return this._delegate.create_codec(enc);
    }
}

