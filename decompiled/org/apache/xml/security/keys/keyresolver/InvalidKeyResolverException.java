/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.keys.keyresolver;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class InvalidKeyResolverException
extends XMLSecurityException {
    private static final long serialVersionUID = 1L;

    public InvalidKeyResolverException() {
    }

    public InvalidKeyResolverException(String msgID) {
        super(msgID);
    }

    public InvalidKeyResolverException(String msgID, Object[] exArgs) {
        super(msgID, exArgs);
    }

    public InvalidKeyResolverException(Exception originalException, String msgID) {
        super(originalException, msgID);
    }

    @Deprecated
    public InvalidKeyResolverException(String msgID, Exception originalException) {
        this(originalException, msgID);
    }

    public InvalidKeyResolverException(Exception originalException, String msgID, Object[] exArgs) {
        super(originalException, msgID, exArgs);
    }

    @Deprecated
    public InvalidKeyResolverException(String msgID, Object[] exArgs, Exception originalException) {
        this(originalException, msgID, exArgs);
    }
}

