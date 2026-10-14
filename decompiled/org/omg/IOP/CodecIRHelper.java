/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.util.Hashtable;

public class CodecIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("decode_value", "(in:data org.omg.CORBA.OctetSeq,in:tc )");
        irInfo.put("encode_value", "org.omg.CORBA.OctetSeq(in:data )");
        irInfo.put("encode", "org.omg.CORBA.OctetSeq(in:data )");
        irInfo.put("decode", "(in:data org.omg.CORBA.OctetSeq)");
    }
}

