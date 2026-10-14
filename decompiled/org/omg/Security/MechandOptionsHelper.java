/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AssociationOptionsHelper;
import org.omg.Security.MechandOptions;
import org.omg.Security.MechanismTypeHelper;

public abstract class MechandOptionsHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MechandOptionsHelper> clazz = MechandOptionsHelper.class;
        synchronized (MechandOptionsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(MechandOptionsHelper.id(), "MechandOptions", new StructMember[]{new StructMember("mechanism_type", ORB.init().create_alias_tc(MechanismTypeHelper.id(), "MechanismType", ORB.init().create_string_tc(0)), null), new StructMember("options_supported", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, MechandOptions s2) {
        any.type(MechandOptionsHelper.type());
        MechandOptionsHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MechandOptions extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            MechandOptions mechandOptions = MechandOptionsHelper.read(in);
            return mechandOptions;
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
        return "IDL:omg.org/Security/MechandOptions:1.0";
    }

    public static MechandOptions read(InputStream in) {
        MechandOptions result = new MechandOptions();
        result.mechanism_type = in.read_string();
        result.options_supported = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, MechandOptions s2) {
        String tmpResult7 = s2.mechanism_type;
        out.write_string(tmpResult7);
        out.write_ushort(s2.options_supported);
    }
}

