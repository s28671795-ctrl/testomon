/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AssociationOptionsHelper;
import org.omg.Security.CommunicationDirectionHelper;
import org.omg.Security.OptionsDirectionPair;
import org.omg.Security.OptionsDirectionPairHelper;

public abstract class OptionsDirectionPairListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, OptionsDirectionPair[] s2) {
        any.type(OptionsDirectionPairListHelper.type());
        OptionsDirectionPairListHelper.write(any.create_output_stream(), s2);
    }

    public static OptionsDirectionPair[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return OptionsDirectionPairListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OptionsDirectionPairListHelper> clazz = OptionsDirectionPairListHelper.class;
        synchronized (OptionsDirectionPairListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(OptionsDirectionPairListHelper.id(), "OptionsDirectionPairList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(OptionsDirectionPairHelper.id(), "OptionsDirectionPair", new StructMember[]{new StructMember("options", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("direction", ORB.init().create_enum_tc(CommunicationDirectionHelper.id(), "CommunicationDirection", new String[]{"SecDirectionBoth", "SecDirectionRequest", "SecDirectionReply"}), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/OptionsDirectionPairList:1.0";
    }

    public static OptionsDirectionPair[] read(InputStream _in) {
        int _l_result7 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result7 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result7);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        OptionsDirectionPair[] _result = new OptionsDirectionPair[_l_result7];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = OptionsDirectionPairHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, OptionsDirectionPair[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            OptionsDirectionPairHelper.write(_out, _s[i]);
        }
    }
}

