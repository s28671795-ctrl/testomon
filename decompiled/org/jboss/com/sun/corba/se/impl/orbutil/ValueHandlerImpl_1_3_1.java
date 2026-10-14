/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.io.ValueHandlerImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryId_1_3_1;
import org.omg.CORBA.TCKind;

public class ValueHandlerImpl_1_3_1
extends ValueHandlerImpl {
    public ValueHandlerImpl_1_3_1() {
    }

    public ValueHandlerImpl_1_3_1(boolean isInputStream) {
        super(isInputStream);
    }

    @Override
    protected TCKind getJavaCharTCKind() {
        return TCKind.tk_char;
    }

    @Override
    public boolean useFullValueDescription(Class<?> clazz, String repositoryID) throws IOException {
        return RepositoryId_1_3_1.useFullValueDescription(clazz, repositoryID);
    }

    @Override
    protected final String getOutputStreamClassName() {
        return "org.jboss.com.sun.corba.se.impl.orbutil.IIOPOutputStream_1_3_1";
    }

    @Override
    protected final String getInputStreamClassName() {
        return "org.jboss.com.sun.corba.se.impl.orbutil.IIOPInputStream_1_3_1";
    }
}

