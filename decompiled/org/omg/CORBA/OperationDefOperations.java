/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainedOperations;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.TypeCode;

public interface OperationDefOperations
extends ContainedOperations {
    public TypeCode result();

    public IDLType result_def();

    public void result_def(IDLType var1);

    public ParameterDescription[] params();

    public void params(ParameterDescription[] var1);

    public OperationMode mode();

    public void mode(OperationMode var1);

    public String[] contexts();

    public void contexts(String[] var1);

    public ExceptionDef[] exceptions();

    public void exceptions(ExceptionDef[] var1);
}

