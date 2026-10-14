/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GSSUP.ErrorCodeHelper;
import org.omg.GSSUP.ErrorToken;

public abstract class ErrorTokenHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ErrorTokenHelper> clazz = ErrorTokenHelper.class;
        synchronized (ErrorTokenHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ErrorTokenHelper.id(), "ErrorToken", new StructMember[]{new StructMember("error_code", ORB.init().create_alias_tc(ErrorCodeHelper.id(), "ErrorCode", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ErrorToken s2) {
        any.type(ErrorTokenHelper.type());
        ErrorTokenHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ErrorToken extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ErrorToken errorToken = ErrorTokenHelper.read(in);
            return errorToken;
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
        return "IDL:omg.org/GSSUP/ErrorToken:1.0";
    }

    public static ErrorToken read(InputStream in) {
        ErrorToken result = new ErrorToken();
        result.error_code = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, ErrorToken s2) {
        out.write_ulong(s2.error_code);
    }
}

