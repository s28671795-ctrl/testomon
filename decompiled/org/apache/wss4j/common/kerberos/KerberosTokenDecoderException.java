/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

public class KerberosTokenDecoderException
extends Exception {
    private static final long serialVersionUID = 1L;

    public KerberosTokenDecoderException(String message) {
        super(message);
    }

    public KerberosTokenDecoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public KerberosTokenDecoderException(Throwable cause) {
        super(cause);
    }
}

