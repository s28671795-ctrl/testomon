/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA_2_3.portable.OutputStream;

public interface ExceptionHandler {
    public boolean isDeclaredException(Class<?> var1);

    public void writeException(OutputStream var1, Exception var2);

    public Exception readException(ApplicationException var1);
}

