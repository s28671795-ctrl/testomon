/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.Serializable;
import java.net.MalformedURLException;
import org.jboss.com.sun.corba.se.impl.io.TypeMismatchException;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdInterface;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryId_1_3;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;

public final class RepIdDelegator_1_3
implements RepositoryIdStrings,
RepositoryIdUtility,
RepositoryIdInterface {
    private RepositoryId_1_3 delegate = null;

    @Override
    public String createForAnyType(Class<?> type) {
        return RepositoryId_1_3.createForAnyType(type);
    }

    @Override
    public String createForJavaType(Serializable ser) throws TypeMismatchException {
        return RepositoryId_1_3.createForJavaType(ser);
    }

    @Override
    public String createForJavaType(Class<?> clz) throws TypeMismatchException {
        return RepositoryId_1_3.createForJavaType(clz);
    }

    @Override
    public String createSequenceRepID(Object ser) {
        return RepositoryId_1_3.createSequenceRepID(ser);
    }

    @Override
    public String createSequenceRepID(Class<?> clazz) {
        return RepositoryId_1_3.createSequenceRepID(clazz);
    }

    @Override
    public RepositoryIdInterface getFromString(String repIdString) {
        return new RepIdDelegator_1_3(RepositoryId_1_3.cache.getId(repIdString));
    }

    @Override
    public boolean isChunkedEncoding(int valueTag) {
        return RepositoryId.isChunkedEncoding(valueTag);
    }

    @Override
    public boolean isCodeBasePresent(int valueTag) {
        return RepositoryId.isCodeBasePresent(valueTag);
    }

    @Override
    public String getClassDescValueRepId() {
        return RepositoryId_1_3.kClassDescValueRepID;
    }

    @Override
    public String getWStringValueRepId() {
        return "IDL:omg.org/CORBA/WStringValue:1.0";
    }

    @Override
    public int getTypeInfo(int valueTag) {
        return RepositoryId.getTypeInfo(valueTag);
    }

    @Override
    public int getStandardRMIChunkedNoRepStrId() {
        return RepositoryId.kPreComputed_StandardRMIChunked_NoRep;
    }

    @Override
    public int getCodeBaseRMIChunkedNoRepStrId() {
        return RepositoryId.kPreComputed_CodeBaseRMIChunked_NoRep;
    }

    @Override
    public int getStandardRMIChunkedId() {
        return RepositoryId.kPreComputed_StandardRMIChunked;
    }

    @Override
    public int getCodeBaseRMIChunkedId() {
        return RepositoryId.kPreComputed_CodeBaseRMIChunked;
    }

    @Override
    public int getStandardRMIUnchunkedId() {
        return RepositoryId.kPreComputed_StandardRMIUnchunked;
    }

    @Override
    public int getCodeBaseRMIUnchunkedId() {
        return RepositoryId.kPreComputed_CodeBaseRMIUnchunked;
    }

    @Override
    public int getStandardRMIUnchunkedNoRepStrId() {
        return RepositoryId.kPreComputed_StandardRMIUnchunked_NoRep;
    }

    @Override
    public int getCodeBaseRMIUnchunkedNoRepStrId() {
        return RepositoryId.kPreComputed_CodeBaseRMIUnchunked_NoRep;
    }

    @Override
    public Class<?> getClassFromType() throws ClassNotFoundException {
        return this.delegate.getClassFromType();
    }

    @Override
    public Class<?> getClassFromType(String codebaseURL) throws ClassNotFoundException, MalformedURLException {
        return this.delegate.getClassFromType(codebaseURL);
    }

    @Override
    public Class<?> getClassFromType(Class<?> expectedType, String codebaseURL) throws ClassNotFoundException, MalformedURLException {
        return this.delegate.getClassFromType(expectedType, codebaseURL);
    }

    @Override
    public String getClassName() {
        return this.delegate.getClassName();
    }

    public RepIdDelegator_1_3() {
    }

    private RepIdDelegator_1_3(RepositoryId_1_3 _delegate) {
        this.delegate = _delegate;
    }

    public String toString() {
        if (this.delegate != null) {
            return this.delegate.toString();
        }
        return this.getClass().getName();
    }

    public boolean equals(Object obj) {
        if (this.delegate != null) {
            return this.delegate.equals(obj);
        }
        return super.equals(obj);
    }
}

