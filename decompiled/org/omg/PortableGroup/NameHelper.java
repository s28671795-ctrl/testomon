/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameComponentHelper;

public abstract class NameHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, NameComponent[] s2) {
        any.type(NameHelper.type());
        NameHelper.write(any.create_output_stream(), s2);
    }

    public static NameComponent[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return NameHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NameHelper> clazz = NameHelper.class;
        synchronized (NameHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/Name:1.0";
    }

    public static NameComponent[] read(InputStream _in) {
        NameComponent[] _result = org.omg.CosNaming.NameHelper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, NameComponent[] _s) {
        org.omg.CosNaming.NameHelper.write(_out, _s);
    }
}

