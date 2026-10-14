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
import org.omg.Security.AttributeTypeHelper;
import org.omg.Security.ExtensibleFamilyHelper;
import org.omg.Security.OpaqueHelper;
import org.omg.Security.SecAttribute;
import org.omg.Security.SecurityAttributeTypeHelper;

public abstract class SecAttributeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SecAttributeHelper> clazz = SecAttributeHelper.class;
        synchronized (SecAttributeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(SecAttributeHelper.id(), "SecAttribute", new StructMember[]{new StructMember("attribute_type", ORB.init().create_struct_tc(AttributeTypeHelper.id(), "AttributeType", new StructMember[]{new StructMember("attribute_family", ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}), null), new StructMember("attribute_type", ORB.init().create_alias_tc(SecurityAttributeTypeHelper.id(), "SecurityAttributeType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)}), null), new StructMember("defining_authority", ORB.init().create_alias_tc(OpaqueHelper.id(), "Opaque", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("value", ORB.init().create_alias_tc(OpaqueHelper.id(), "Opaque", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SecAttribute s2) {
        any.type(SecAttributeHelper.type());
        SecAttributeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SecAttribute extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SecAttribute secAttribute = SecAttributeHelper.read(in);
            return secAttribute;
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
        return "IDL:omg.org/Security/SecAttribute:1.0";
    }

    public static SecAttribute read(InputStream in) {
        SecAttribute result = new SecAttribute();
        result.attribute_type = AttributeTypeHelper.read(in);
        result.defining_authority = OpaqueHelper.read(in);
        result.value = OpaqueHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, SecAttribute s2) {
        AttributeTypeHelper.write(out, s2.attribute_type);
        OpaqueHelper.write(out, s2.defining_authority);
        OpaqueHelper.write(out, s2.value);
    }
}

