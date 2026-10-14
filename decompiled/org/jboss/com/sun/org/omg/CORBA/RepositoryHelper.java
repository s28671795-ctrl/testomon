/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.Repository;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class RepositoryHelper {
    private static String _id = "IDL:org.jboss.com.sun.omg.org/CORBA/Repository:3.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, Repository that) {
        OutputStream out = a.create_output_stream();
        a.type(RepositoryHelper.type());
        RepositoryHelper.write(out, that);
        a.read_value(out.create_input_stream(), RepositoryHelper.type());
    }

    public static Repository extract(Any a) {
        return RepositoryHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_string_tc(0);
            __typeCode = ORB.init().create_alias_tc(RepositoryHelper.id(), "Repository", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static Repository read(InputStream istream) {
        return null;
    }

    public static void write(OutputStream ostream, Repository value) {
        ostream.write_string(null);
    }
}

