/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ModuleDescription;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ModuleDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ModuleDescriptionHelper> clazz = ModuleDescriptionHelper.class;
        synchronized (ModuleDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ModuleDescriptionHelper.id(), "ModuleDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ModuleDescription s2) {
        any.type(ModuleDescriptionHelper.type());
        ModuleDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ModuleDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ModuleDescription moduleDescription = ModuleDescriptionHelper.read(in);
            return moduleDescription;
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
        return "IDL:omg.org/CORBA/ModuleDescription:1.0";
    }

    public static ModuleDescription read(InputStream in) {
        ModuleDescription result = new ModuleDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        return result;
    }

    public static void write(OutputStream out, ModuleDescription s2) {
        String tmpResult164 = s2.name;
        out.write_string(tmpResult164);
        String tmpResult165 = s2.id;
        out.write_string(tmpResult165);
        String tmpResult166 = s2.defined_in;
        out.write_string(tmpResult166);
        String tmpResult167 = s2.version;
        out.write_string(tmpResult167);
    }
}

