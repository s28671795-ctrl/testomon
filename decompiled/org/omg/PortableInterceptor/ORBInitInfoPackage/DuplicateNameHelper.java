/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor.ORBInitInfoPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

public abstract class DuplicateNameHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DuplicateNameHelper> clazz = DuplicateNameHelper.class;
        synchronized (DuplicateNameHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(DuplicateNameHelper.id(), "DuplicateName", new StructMember[]{new StructMember("name", ORB.init().create_string_tc(0), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DuplicateName s2) {
        any.type(DuplicateNameHelper.type());
        DuplicateNameHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DuplicateName extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            DuplicateName duplicateName = DuplicateNameHelper.read(in);
            return duplicateName;
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
        return "IDL:omg.org/PortableInterceptor/ORBInitInfo/DuplicateName:1.0";
    }

    public static DuplicateName read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(DuplicateNameHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        String x0 = in.read_string();
        DuplicateName result = new DuplicateName(id, x0);
        return result;
    }

    public static void write(OutputStream out, DuplicateName s2) {
        out.write_string(DuplicateNameHelper.id());
        String tmpResult4 = s2.name;
        out.write_string(tmpResult4);
    }
}

