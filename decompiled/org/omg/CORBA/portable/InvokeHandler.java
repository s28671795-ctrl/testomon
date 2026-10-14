/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

public interface InvokeHandler {
    public OutputStream _invoke(String var1, InputStream var2, ResponseHandler var3);
}

