/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.asn1;

public class ASN1ParseException
extends RuntimeException {
    public ASN1ParseException(String message) {
        super(message);
    }

    public ASN1ParseException(Throwable cause, String messageFormat, Object ... args2) {
        super(String.format(messageFormat, args2), cause);
    }

    public ASN1ParseException(String messageFormat, Object ... args2) {
        super(String.format(messageFormat, args2));
    }
}

