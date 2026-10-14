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
import org.omg.Security.AttributeType;
import org.omg.Security.ExtensibleFamilyHelper;
import org.omg.Security.SecurityAttributeTypeHelper;

public abstract class AttributeTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AttributeTypeHelper> clazz = AttributeTypeHelper.class;
        synchronized (AttributeTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(AttributeTypeHelper.id(), "AttributeType", new StructMember[]{new StructMember("attribute_family", ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}), null), new StructMember("attribute_type", ORB.init().create_alias_tc(SecurityAttributeTypeHelper.id(), "SecurityAttributeType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AttributeType s2) {
        any.type(AttributeTypeHelper.type());
        AttributeTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AttributeType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AttributeType attributeType = AttributeTypeHelper.read(in);
            return attributeType;
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
        return "IDL:omg.org/Security/AttributeType:1.0";
    }

    public static AttributeType read(InputStream in) {
        AttributeType result = new AttributeType();
        result.attribute_family = ExtensibleFamilyHelper.read(in);
        result.attribute_type = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, AttributeType s2) {
        ExtensibleFamilyHelper.write(out, s2.attribute_family);
        out.write_ulong(s2.attribute_type);
    }
}

