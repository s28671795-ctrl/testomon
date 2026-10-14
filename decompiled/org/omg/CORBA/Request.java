/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.Environment;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WrongTransaction;

public abstract class Request {
    public abstract Object target();

    public abstract String operation();

    public abstract NVList arguments();

    public abstract NamedValue result();

    public abstract Environment env();

    public abstract ExceptionList exceptions();

    public abstract ContextList contexts();

    public abstract void ctx(Context var1);

    public abstract Context ctx();

    public abstract Any add_in_arg();

    public abstract Any add_named_in_arg(String var1);

    public abstract Any add_inout_arg();

    public abstract Any add_named_inout_arg(String var1);

    public abstract Any add_out_arg();

    public abstract Any add_named_out_arg(String var1);

    public abstract void set_return_type(TypeCode var1);

    public abstract Any return_value();

    public abstract void invoke();

    public abstract void send_oneway();

    public abstract void send_deferred();

    public abstract void get_response() throws WrongTransaction;

    public abstract boolean poll_response();
}

