/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA._IDLTypeStub;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public final class IDLTypeHelper {
    private static String _id = "IDL:omg.org/CORBA/IDLType:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, IDLType that) {
        OutputStream out = a.create_output_stream();
        a.type(IDLTypeHelper.type());
        IDLTypeHelper.write(out, that);
        a.read_value(out.create_input_stream(), IDLTypeHelper.type());
    }

    public static IDLType extract(Any a) {
        return IDLTypeHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_interface_tc(IDLTypeHelper.id(), "IDLType");
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static IDLType read(InputStream istream) {
        return IDLTypeHelper.narrow(istream.read_Object(_IDLTypeStub.class));
    }

    public static void write(OutputStream ostream, IDLType value) {
        ostream.write_Object(value);
    }

    public static IDLType narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IDLType) {
            return (IDLType)obj;
        }
        if (!obj._is_a(IDLTypeHelper.id())) {
            throw new BAD_PARAM();
        }
        Delegate delegate = ((ObjectImpl)obj)._get_delegate();
        return new _IDLTypeStub(delegate);
    }
}

