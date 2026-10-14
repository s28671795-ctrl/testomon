/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class WrongTransactionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<WrongTransactionHelper> clazz = WrongTransactionHelper.class;
        synchronized (WrongTransactionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(WrongTransactionHelper.id(), "WrongTransaction", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, WrongTransaction s2) {
        any.type(WrongTransactionHelper.type());
        WrongTransactionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static WrongTransaction extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            WrongTransaction wrongTransaction = WrongTransactionHelper.read(in);
            return wrongTransaction;
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
        return "IDL:omg.org/CORBA/WrongTransaction:1.0";
    }

    public static WrongTransaction read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(WrongTransactionHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        WrongTransaction result = new WrongTransaction(id);
        return result;
    }

    public static void write(OutputStream out, WrongTransaction s2) {
        out.write_string(WrongTransactionHelper.id());
    }
}

