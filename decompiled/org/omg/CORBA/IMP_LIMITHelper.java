/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.IMP_LIMIT;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class IMP_LIMITHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IMP_LIMITHelper> clazz = IMP_LIMITHelper.class;
        synchronized (IMP_LIMITHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(IMP_LIMITHelper.id(), "IMP_LIMIT", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IMP_LIMIT s2) {
        any.type(IMP_LIMITHelper.type());
        IMP_LIMITHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IMP_LIMIT extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IMP_LIMIT iMP_LIMIT = IMP_LIMITHelper.read(in);
            return iMP_LIMIT;
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
        return "IDL:omg.org/CORBA/IMP_LIMIT:1.0";
    }

    public static IMP_LIMIT read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(IMP_LIMITHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        IMP_LIMIT result = new IMP_LIMIT(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, IMP_LIMIT s2) {
        out.write_string(IMP_LIMITHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

