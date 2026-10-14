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
import org.omg.Security.AttributeType;
import org.omg.Security.AttributeTypeHelper;
import org.omg.Security.ExtensibleFamilyHelper;
import org.omg.Security.SecurityAttributeTypeHelper;

public abstract class AttributeTypeListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, AttributeType[] s2) {
        any.type(AttributeTypeListHelper.type());
        AttributeTypeListHelper.write(any.create_output_stream(), s2);
    }

    public static AttributeType[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return AttributeTypeListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AttributeTypeListHelper> clazz = AttributeTypeListHelper.class;
        synchronized (AttributeTypeListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(AttributeTypeListHelper.id(), "AttributeTypeList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(AttributeTypeHelper.id(), "AttributeType", new StructMember[]{new StructMember("attribute_family", ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}), null), new StructMember("attribute_type", ORB.init().create_alias_tc(SecurityAttributeTypeHelper.id(), "SecurityAttributeType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/AttributeTypeList:1.0";
    }

    public static AttributeType[] read(InputStream _in) {
        int _l_result3 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result3 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result3);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        AttributeType[] _result = new AttributeType[_l_result3];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = AttributeTypeHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, AttributeType[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            AttributeTypeHelper.write(_out, _s[i]);
        }
    }
}

