/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSI.CompleteEstablishContext;
import org.omg.CSI.ContextError;
import org.omg.CSI.EstablishContext;
import org.omg.CSI.MessageInContext;

public final class SASContextBody
implements IDLEntity {
    private short discriminator;
    private EstablishContext establish_msg;
    private CompleteEstablishContext complete_msg;
    private ContextError error_msg;
    private MessageInContext in_context_msg;

    public short discriminator() {
        return this.discriminator;
    }

    public EstablishContext establish_msg() {
        if (this.discriminator != 0) {
            throw new BAD_OPERATION();
        }
        return this.establish_msg;
    }

    public void establish_msg(EstablishContext _x) {
        this.discriminator = 0;
        this.establish_msg = _x;
    }

    public CompleteEstablishContext complete_msg() {
        if (this.discriminator != 1) {
            throw new BAD_OPERATION();
        }
        return this.complete_msg;
    }

    public void complete_msg(CompleteEstablishContext _x) {
        this.discriminator = 1;
        this.complete_msg = _x;
    }

    public ContextError error_msg() {
        if (this.discriminator != 4) {
            throw new BAD_OPERATION();
        }
        return this.error_msg;
    }

    public void error_msg(ContextError _x) {
        this.discriminator = (short)4;
        this.error_msg = _x;
    }

    public MessageInContext in_context_msg() {
        if (this.discriminator != 5) {
            throw new BAD_OPERATION();
        }
        return this.in_context_msg;
    }

    public void in_context_msg(MessageInContext _x) {
        this.discriminator = (short)5;
        this.in_context_msg = _x;
    }

    public void __default() {
        this.discriminator = (short)2;
    }

    public void __default(short _discriminator) {
        if (_discriminator == 0 || _discriminator == 1 || _discriminator == 4 || _discriminator == 5) {
            throw new BAD_PARAM("Illegal value is used in __default method", 34, CompletionStatus.COMPLETED_NO);
        }
        this.discriminator = _discriminator;
    }
}

