/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ErrorInformation;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;

public class ErrorInformationImpl
implements ErrorInformation {
    private final ErrorType errorType;
    private final Descriptor descriptor;
    private final Injectee injectee;
    private final MultiException exception;

    ErrorInformationImpl(ErrorType errorType, Descriptor descriptor, Injectee injectee, MultiException exception) {
        this.errorType = errorType;
        this.descriptor = descriptor;
        this.injectee = injectee;
        this.exception = exception;
    }

    @Override
    public ErrorType getErrorType() {
        return this.errorType;
    }

    @Override
    public Descriptor getDescriptor() {
        return this.descriptor;
    }

    @Override
    public Injectee getInjectee() {
        return this.injectee;
    }

    @Override
    public MultiException getAssociatedException() {
        return this.exception;
    }

    public String toString() {
        return "ErrorInformation(" + (Object)((Object)this.errorType) + "," + this.descriptor + "," + this.injectee + "," + this.exception + "," + System.identityHashCode(this) + ")";
    }
}

