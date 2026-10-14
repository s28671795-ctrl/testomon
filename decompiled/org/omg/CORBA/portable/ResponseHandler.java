/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.portable.OutputStream;

public interface ResponseHandler {
    public OutputStream createReply();

    public OutputStream createExceptionReply();
}

