/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.FragmentHeader_1_2;
import org.omg.GIOP.FragmentHeader_1_2Helper;

public abstract class FragmentHeader_1_3Helper {
    private static volatile TypeCode _type;

    public static void insert(Any any, FragmentHeader_1_2 s2) {
        any.type(FragmentHeader_1_3Helper.type());
        FragmentHeader_1_3Helper.write(any.create_output_stream(), s2);
    }

    public static FragmentHeader_1_2 extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return FragmentHeader_1_3Helper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FragmentHeader_1_3Helper> clazz = FragmentHeader_1_3Helper.class;
        synchronized (FragmentHeader_1_3Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(FragmentHeader_1_3Helper.id(), "FragmentHeader_1_3", ORB.init().create_struct_tc(FragmentHeader_1_2Helper.id(), "FragmentHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/FragmentHeader_1_3:1.0";
    }

    public static FragmentHeader_1_2 read(InputStream _in) {
        FragmentHeader_1_2 _result = FragmentHeader_1_2Helper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, FragmentHeader_1_2 _s) {
        FragmentHeader_1_2Helper.write(_out, _s);
    }
}

