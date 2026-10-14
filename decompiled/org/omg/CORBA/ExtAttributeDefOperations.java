/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeDefOperations;
import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.ExtAttributeDescription;

public interface ExtAttributeDefOperations
extends AttributeDefOperations {
    public ExceptionDescription[] get_exceptions();

    public void get_exceptions(ExceptionDescription[] var1);

    public ExceptionDescription[] set_exceptions();

    public void set_exceptions(ExceptionDescription[] var1);

    public ExtAttributeDescription describe_attribute();
}

