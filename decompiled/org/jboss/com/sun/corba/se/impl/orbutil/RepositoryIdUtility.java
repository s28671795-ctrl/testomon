/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

public interface RepositoryIdUtility {
    public static final int NO_TYPE_INFO = 0;
    public static final int SINGLE_REP_TYPE_INFO = 2;
    public static final int PARTIAL_LIST_TYPE_INFO = 6;

    public boolean isChunkedEncoding(int var1);

    public boolean isCodeBasePresent(int var1);

    public int getTypeInfo(int var1);

    public int getStandardRMIChunkedNoRepStrId();

    public int getCodeBaseRMIChunkedNoRepStrId();

    public int getStandardRMIChunkedId();

    public int getCodeBaseRMIChunkedId();

    public int getStandardRMIUnchunkedId();

    public int getCodeBaseRMIUnchunkedId();

    public int getStandardRMIUnchunkedNoRepStrId();

    public int getCodeBaseRMIUnchunkedNoRepStrId();
}

