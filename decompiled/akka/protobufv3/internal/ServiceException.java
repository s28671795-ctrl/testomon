/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

public class ServiceException
extends Exception {
    private static final long serialVersionUID = -1219262335729891920L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

