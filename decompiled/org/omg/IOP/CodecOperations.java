/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.IOP.CodecPackage.FormatMismatch;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;
import org.omg.IOP.CodecPackage.TypeMismatch;

public interface CodecOperations {
    public byte[] encode(Any var1) throws InvalidTypeForEncoding;

    public Any decode(byte[] var1) throws FormatMismatch;

    public byte[] encode_value(Any var1) throws InvalidTypeForEncoding;

    public Any decode_value(byte[] var1, TypeCode var2) throws TypeMismatch, FormatMismatch;
}

