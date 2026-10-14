/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyFactoryPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;

public abstract class InconsistentTypeCodeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InconsistentTypeCodeHelper> clazz = InconsistentTypeCodeHelper.class;
        synchronized (InconsistentTypeCodeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InconsistentTypeCodeHelper.id(), "InconsistentTypeCode", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InconsistentTypeCode s2) {
        any.type(InconsistentTypeCodeHelper.type());
        InconsistentTypeCodeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InconsistentTypeCode extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InconsistentTypeCode inconsistentTypeCode = InconsistentTypeCodeHelper.read(in);
            return inconsistentTypeCode;
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
        return "IDL:omg.org/DynamicAny/DynAnyFactory/InconsistentTypeCode:1.0";
    }

    public static InconsistentTypeCode read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InconsistentTypeCodeHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InconsistentTypeCode result = new InconsistentTypeCode(id);
        return result;
    }

    public static void write(OutputStream out, InconsistentTypeCode s2) {
        out.write_string(InconsistentTypeCodeHelper.id());
    }
}

