/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.net.MalformedURLException;

public interface RepositoryIdInterface {
    public Class<?> getClassFromType() throws ClassNotFoundException;

    public Class<?> getClassFromType(String var1) throws ClassNotFoundException, MalformedURLException;

    public Class<?> getClassFromType(Class<?> var1, String var2) throws ClassNotFoundException, MalformedURLException;

    public String getClassName();
}

