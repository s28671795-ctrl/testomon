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
import org.omg.Messaging.RoutingTypeHelper;
import org.omg.Messaging.RoutingTypeRange;

public abstract class RoutingTypeRangeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RoutingTypeRangeHelper> clazz = RoutingTypeRangeHelper.class;
        synchronized (RoutingTypeRangeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(RoutingTypeRangeHelper.id(), "RoutingTypeRange", new StructMember[]{new StructMember("min", ORB.init().create_alias_tc(RoutingTypeHelper.id(), "RoutingType", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("max", ORB.init().create_alias_tc(RoutingTypeHelper.id(), "RoutingType", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RoutingTypeRange s2) {
        any.type(RoutingTypeRangeHelper.type());
        RoutingTypeRangeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RoutingTypeRange extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            RoutingTypeRange routingTypeRange = RoutingTypeRangeHelper.read(in);
            return routingTypeRange;
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
        return "IDL:omg.org/Messaging/RoutingTypeRange:1.0";
    }

    public static RoutingTypeRange read(InputStream in) {
        RoutingTypeRange result = new RoutingTypeRange();
        result.min = in.read_short();
        result.max = in.read_short();
        return result;
    }

    public static void write(OutputStream out, RoutingTypeRange s2) {
        out.write_short(s2.min);
        out.write_short(s2.max);
    }
}

