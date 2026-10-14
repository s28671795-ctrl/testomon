/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.InterfaceDescription;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class InterfaceDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InterfaceDescriptionHelper> clazz = InterfaceDescriptionHelper.class;
        synchronized (InterfaceDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(InterfaceDescriptionHelper.id(), "InterfaceDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("base_interfaces", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InterfaceDescription s2) {
        any.type(InterfaceDescriptionHelper.type());
        InterfaceDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InterfaceDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InterfaceDescription interfaceDescription = InterfaceDescriptionHelper.read(in);
            return interfaceDescription;
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
        return "IDL:omg.org/CORBA/InterfaceDescription:1.0";
    }

    public static InterfaceDescription read(InputStream in) {
        InterfaceDescription result = new InterfaceDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.base_interfaces = RepositoryIdSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, InterfaceDescription s2) {
        String tmpResult487 = s2.name;
        out.write_string(tmpResult487);
        String tmpResult488 = s2.id;
        out.write_string(tmpResult488);
        String tmpResult489 = s2.defined_in;
        out.write_string(tmpResult489);
        String tmpResult490 = s2.version;
        out.write_string(tmpResult490);
        RepositoryIdSeqHelper.write(out, s2.base_interfaces);
    }
}

