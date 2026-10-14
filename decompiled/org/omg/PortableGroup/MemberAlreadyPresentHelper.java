/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableGroup.MemberAlreadyPresent;

public abstract class MemberAlreadyPresentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MemberAlreadyPresentHelper> clazz = MemberAlreadyPresentHelper.class;
        synchronized (MemberAlreadyPresentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(MemberAlreadyPresentHelper.id(), "MemberAlreadyPresent", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, MemberAlreadyPresent s2) {
        any.type(MemberAlreadyPresentHelper.type());
        MemberAlreadyPresentHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MemberAlreadyPresent extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            MemberAlreadyPresent memberAlreadyPresent = MemberAlreadyPresentHelper.read(in);
            return memberAlreadyPresent;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/MemberAlreadyPresent:1.0";
    }

    public static MemberAlreadyPresent read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(MemberAlreadyPresentHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        MemberAlreadyPresent result = new MemberAlreadyPresent(id);
        return result;
    }

    public static void write(OutputStream out, MemberAlreadyPresent s2) {
        out.write_string(MemberAlreadyPresentHelper.id());
    }
}

