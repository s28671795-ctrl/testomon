/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.IORAddressingInfo;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.IORHelper;

public abstract class IORAddressingInfoHelper {
    private static String _id = "IDL:messages/IORAddressingInfo:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, IORAddressingInfo that) {
        OutputStream out = a.create_output_stream();
        a.type(IORAddressingInfoHelper.type());
        IORAddressingInfoHelper.write(out, that);
        a.read_value(out.create_input_stream(), IORAddressingInfoHelper.type());
    }

    public static IORAddressingInfo extract(Any a) {
        return IORAddressingInfoHelper.read(a.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static synchronized TypeCode type() {
        if (__typeCode != null) return __typeCode;
        Class<TypeCode> clazz = TypeCode.class;
        synchronized (TypeCode.class) {
            if (__typeCode != null) return __typeCode;
            if (__active) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return ORB.init().create_recursive_tc(_id);
            }
            __active = true;
            StructMember[] _members0 = new StructMember[2];
            TypeCode _tcOf_members0 = null;
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_ulong);
            _members0[0] = new StructMember("selected_profile_index", _tcOf_members0, null);
            _tcOf_members0 = IORHelper.type();
            _members0[1] = new StructMember("ior", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(IORAddressingInfoHelper.id(), "IORAddressingInfo", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static IORAddressingInfo read(InputStream istream) {
        IORAddressingInfo value = new IORAddressingInfo();
        value.selected_profile_index = istream.read_ulong();
        value.ior = IORHelper.read(istream);
        return value;
    }

    public static void write(OutputStream ostream, IORAddressingInfo value) {
        ostream.write_ulong(value.selected_profile_index);
        IORHelper.write(ostream, value.ior);
    }
}

