/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Messaging.PriorityHelper;
import org.omg.Messaging.PriorityRange;

public abstract class PriorityRangeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PriorityRangeHelper> clazz = PriorityRangeHelper.class;
        synchronized (PriorityRangeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(PriorityRangeHelper.id(), "PriorityRange", new StructMember[]{new StructMember("min", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("max", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PriorityRange s2) {
        any.type(PriorityRangeHelper.type());
        PriorityRangeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PriorityRange extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PriorityRange priorityRange = PriorityRangeHelper.read(in);
            return priorityRange;
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
        return "IDL:omg.org/Messaging/PriorityRange:1.0";
    }

    public static PriorityRange read(InputStream in) {
        PriorityRange result = new PriorityRange();
        result.min = in.read_short();
        result.max = in.read_short();
        return result;
    }

    public static void write(OutputStream out, PriorityRange s2) {
        out.write_short(s2.min);
        out.write_short(s2.max);
    }
}

