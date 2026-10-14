/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.DayOfTheWeek;

public abstract class DayOfTheWeekHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DayOfTheWeekHelper> clazz = DayOfTheWeekHelper.class;
        synchronized (DayOfTheWeekHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(DayOfTheWeekHelper.id(), "DayOfTheWeek", new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DayOfTheWeek s2) {
        any.type(DayOfTheWeekHelper.type());
        DayOfTheWeekHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DayOfTheWeek extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            DayOfTheWeek dayOfTheWeek = DayOfTheWeekHelper.read(in);
            return dayOfTheWeek;
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
        return "IDL:omg.org/Security/DayOfTheWeek:1.0";
    }

    public static DayOfTheWeek read(InputStream in) {
        return DayOfTheWeek.from_int(in.read_long());
    }

    public static void write(OutputStream out, DayOfTheWeek s2) {
        out.write_long(s2.value());
    }
}

