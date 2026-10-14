/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.ATLASCacheIdHelper;
import org.omg.ATLAS.ATLASLocatorHelper;
import org.omg.ATLAS.ATLASProfile;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ATLASProfileHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ATLASProfileHelper> clazz = ATLASProfileHelper.class;
        synchronized (ATLASProfileHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ATLASProfileHelper.id(), "ATLASProfile", new StructMember[]{new StructMember("the_locator", ATLASLocatorHelper.type(), null), new StructMember("the_cache_id", ORB.init().create_alias_tc(ATLASCacheIdHelper.id(), "ATLASCacheId", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ATLASProfile s2) {
        any.type(ATLASProfileHelper.type());
        ATLASProfileHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ATLASProfile extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ATLASProfile aTLASProfile = ATLASProfileHelper.read(in);
            return aTLASProfile;
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
        return "IDL:omg.org/ATLAS/ATLASProfile:1.0";
    }

    public static ATLASProfile read(InputStream in) {
        ATLASProfile result = new ATLASProfile();
        result.the_locator = ATLASLocatorHelper.read(in);
        result.the_cache_id = ATLASCacheIdHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ATLASProfile s2) {
        ATLASLocatorHelper.write(out, s2.the_locator);
        ATLASCacheIdHelper.write(out, s2.the_cache_id);
    }
}

