/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import java.io.Serializable;
import org.jboss.com.sun.corba.se.impl.io.ValueHandlerImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryId_1_3;
import org.omg.CORBA.TCKind;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.SendingContext.RunTime;

public class ValueHandlerImpl_1_3
extends ValueHandlerImpl {
    public ValueHandlerImpl_1_3() {
    }

    public ValueHandlerImpl_1_3(boolean isInputStream) {
        super(isInputStream);
    }

    @Override
    public void writeValue(org.omg.CORBA.portable.OutputStream _out, Serializable value) {
        super.writeValue(_out, value);
    }

    @Override
    public Serializable readValue(org.omg.CORBA.portable.InputStream _in, int offset, Class<?> clazz, String repositoryID, RunTime _sender) {
        return super.readValue(_in, offset, clazz, repositoryID, _sender);
    }

    @Override
    public String getRMIRepositoryID(Class<?> clz) {
        return RepositoryId_1_3.createForJavaType(clz);
    }

    @Override
    public boolean isCustomMarshaled(Class<?> clz) {
        return super.isCustomMarshaled(clz);
    }

    @Override
    public RunTime getRunTimeCodeBase() {
        return super.getRunTimeCodeBase();
    }

    @Override
    public Serializable writeReplace(Serializable value) {
        return super.writeReplace(value);
    }

    @Override
    public boolean useFullValueDescription(Class<?> clazz, String repositoryID) throws IOException {
        return RepositoryId_1_3.useFullValueDescription(clazz, repositoryID);
    }

    @Override
    public String getClassName(String id) {
        RepositoryId_1_3 repID = RepositoryId_1_3.cache.getId(id);
        return repID.getClassName();
    }

    @Override
    public Class<?> getClassFromType(String id) throws ClassNotFoundException {
        RepositoryId_1_3 repId = RepositoryId_1_3.cache.getId(id);
        return repId.getClassFromType();
    }

    @Override
    public Class<?> getAnyClassFromType(String id) throws ClassNotFoundException {
        RepositoryId_1_3 repId = RepositoryId_1_3.cache.getId(id);
        return repId.getAnyClassFromType();
    }

    @Override
    public String createForAnyType(Class<?> cl) {
        return RepositoryId_1_3.createForAnyType(cl);
    }

    @Override
    public String getDefinedInId(String id) {
        RepositoryId_1_3 repId = RepositoryId_1_3.cache.getId(id);
        return repId.getDefinedInId();
    }

    @Override
    public String getUnqualifiedName(String id) {
        RepositoryId_1_3 repId = RepositoryId_1_3.cache.getId(id);
        return repId.getUnqualifiedName();
    }

    @Override
    public String getSerialVersionUID(String id) {
        RepositoryId_1_3 repId = RepositoryId_1_3.cache.getId(id);
        return repId.getSerialVersionUID();
    }

    @Override
    public boolean isAbstractBase(Class<?> clazz) {
        return RepositoryId_1_3.isAbstractBase(clazz);
    }

    @Override
    public boolean isSequence(String id) {
        RepositoryId_1_3 repId = RepositoryId_1_3.cache.getId(id);
        return repId.isSequence();
    }

    @Override
    protected void writeCharArray(OutputStream out, char[] array, int offset, int length) {
        out.write_char_array(array, offset, length);
    }

    @Override
    protected void readCharArray(InputStream in, char[] array, int offset, int length) {
        in.read_char_array(array, offset, length);
    }

    @Override
    protected final String getOutputStreamClassName() {
        return "org.jboss.com.sun.corba.se.impl.orbutil.IIOPOutputStream_1_3";
    }

    @Override
    protected final String getInputStreamClassName() {
        return "org.jboss.com.sun.corba.se.impl.orbutil.IIOPInputStream_1_3";
    }

    @Override
    protected TCKind getJavaCharTCKind() {
        return TCKind.tk_char;
    }
}

