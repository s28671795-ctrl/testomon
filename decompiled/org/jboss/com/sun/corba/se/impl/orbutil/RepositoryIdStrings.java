/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.Serializable;
import org.jboss.com.sun.corba.se.impl.io.TypeMismatchException;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdInterface;

public interface RepositoryIdStrings {
    public String createForAnyType(Class<?> var1);

    public String createForJavaType(Serializable var1) throws TypeMismatchException;

    public String createForJavaType(Class<?> var1) throws TypeMismatchException;

    public String createSequenceRepID(Object var1);

    public String createSequenceRepID(Class<?> var1);

    public RepositoryIdInterface getFromString(String var1);

    public String getClassDescValueRepId();

    public String getWStringValueRepId();
}

