/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.presentation.rmi;

import java.lang.reflect.Method;

public interface IDLNameTranslator {
    public Class<?>[] getInterfaces();

    public Method[] getMethods();

    public Method getMethod(String var1);

    public String getIDLName(Method var1);
}

