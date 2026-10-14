/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;

public interface TypeCodeFactory {
    public void setTypeCode(String var1, TypeCodeImpl var2);

    public TypeCodeImpl getTypeCode(String var1);

    public void setTypeCodeForClass(Class<?> var1, TypeCodeImpl var2);

    public TypeCodeImpl getTypeCodeForClass(Class<?> var1);
}

