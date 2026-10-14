/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.SendingContext;

import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.jboss.com.sun.org.omg.SendingContext._CodeBaseStub;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public final class CodeBaseHelper {
    private static String _id = "IDL:omg.org/SendingContext/CodeBase:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, CodeBase that) {
        OutputStream out = a.create_output_stream();
        a.type(CodeBaseHelper.type());
        CodeBaseHelper.write(out, that);
        a.read_value(out.create_input_stream(), CodeBaseHelper.type());
    }

    public static CodeBase extract(Any a) {
        return CodeBaseHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_interface_tc(CodeBaseHelper.id(), "CodeBase");
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static CodeBase read(InputStream istream) {
        return CodeBaseHelper.narrow(istream.read_Object(_CodeBaseStub.class));
    }

    public static void write(OutputStream ostream, CodeBase value) {
        ostream.write_Object(value);
    }

    public static CodeBase narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CodeBase) {
            return (CodeBase)obj;
        }
        if (!obj._is_a(CodeBaseHelper.id())) {
            throw new BAD_PARAM();
        }
        Delegate delegate = ((ObjectImpl)obj)._get_delegate();
        return new _CodeBaseStub(delegate);
    }
}

