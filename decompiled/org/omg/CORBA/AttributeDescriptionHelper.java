/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttributeDescription;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class AttributeDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AttributeDescriptionHelper> clazz = AttributeDescriptionHelper.class;
        synchronized (AttributeDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(AttributeDescriptionHelper.id(), "AttributeDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(AttributeModeHelper.id(), "AttributeMode", new String[]{"ATTR_NORMAL", "ATTR_READONLY"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AttributeDescription s2) {
        any.type(AttributeDescriptionHelper.type());
        AttributeDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AttributeDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AttributeDescription attributeDescription = AttributeDescriptionHelper.read(in);
            return attributeDescription;
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
        return "IDL:omg.org/CORBA/AttributeDescription:1.0";
    }

    public static AttributeDescription read(InputStream in) {
        AttributeDescription result = new AttributeDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.type = in.read_TypeCode();
        result.mode = AttributeModeHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, AttributeDescription s2) {
        String tmpResult389 = s2.name;
        out.write_string(tmpResult389);
        String tmpResult390 = s2.id;
        out.write_string(tmpResult390);
        String tmpResult391 = s2.defined_in;
        out.write_string(tmpResult391);
        String tmpResult392 = s2.version;
        out.write_string(tmpResult392);
        out.write_TypeCode(s2.type);
        AttributeModeHelper.write(out, s2.mode);
    }
}

