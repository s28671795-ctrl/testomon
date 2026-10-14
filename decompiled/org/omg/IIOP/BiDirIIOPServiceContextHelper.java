/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IIOP.BiDirIIOPServiceContext;
import org.omg.IIOP.ListenPointHelper;
import org.omg.IIOP.ListenPointListHelper;

public abstract class BiDirIIOPServiceContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BiDirIIOPServiceContextHelper> clazz = BiDirIIOPServiceContextHelper.class;
        synchronized (BiDirIIOPServiceContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(BiDirIIOPServiceContextHelper.id(), "BiDirIIOPServiceContext", new StructMember[]{new StructMember("listen_points", ORB.init().create_alias_tc(ListenPointListHelper.id(), "ListenPointList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ListenPointHelper.id(), "ListenPoint", new StructMember[]{new StructMember("host", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BiDirIIOPServiceContext s2) {
        any.type(BiDirIIOPServiceContextHelper.type());
        BiDirIIOPServiceContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BiDirIIOPServiceContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            BiDirIIOPServiceContext biDirIIOPServiceContext = BiDirIIOPServiceContextHelper.read(in);
            return biDirIIOPServiceContext;
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
        return "IDL:omg.org/IIOP/BiDirIIOPServiceContext:1.0";
    }

    public static BiDirIIOPServiceContext read(InputStream in) {
        BiDirIIOPServiceContext result = new BiDirIIOPServiceContext();
        result.listen_points = ListenPointListHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, BiDirIIOPServiceContext s2) {
        ListenPointListHelper.write(out, s2.listen_points);
    }
}

