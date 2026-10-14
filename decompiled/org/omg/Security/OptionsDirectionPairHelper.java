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
import org.omg.Security.CommunicationDirectionHelper;
import org.omg.Security.OptionsDirectionPair;

public abstract class OptionsDirectionPairHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OptionsDirectionPairHelper> clazz = OptionsDirectionPairHelper.class;
        synchronized (OptionsDirectionPairHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(OptionsDirectionPairHelper.id(), "OptionsDirectionPair", new StructMember[]{new StructMember("options", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("direction", ORB.init().create_enum_tc(CommunicationDirectionHelper.id(), "CommunicationDirection", new String[]{"SecDirectionBoth", "SecDirectionRequest", "SecDirectionReply"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, OptionsDirectionPair s2) {
        any.type(OptionsDirectionPairHelper.type());
        OptionsDirectionPairHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static OptionsDirectionPair extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            OptionsDirectionPair optionsDirectionPair = OptionsDirectionPairHelper.read(in);
            return optionsDirectionPair;
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
        return "IDL:omg.org/Security/OptionsDirectionPair:1.0";
    }

    public static OptionsDirectionPair read(InputStream in) {
        OptionsDirectionPair result = new OptionsDirectionPair();
        result.options = in.read_ushort();
        result.direction = CommunicationDirectionHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, OptionsDirectionPair s2) {
        out.write_ushort(s2.options);
        CommunicationDirectionHelper.write(out, s2.direction);
    }
}

