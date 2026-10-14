/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.CompleteEstablishContext;
import org.omg.CSI.ContextIdHelper;
import org.omg.CSI.GSSTokenHelper;

public abstract class CompleteEstablishContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CompleteEstablishContextHelper> clazz = CompleteEstablishContextHelper.class;
        synchronized (CompleteEstablishContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CompleteEstablishContextHelper.id(), "CompleteEstablishContext", new StructMember[]{new StructMember("client_context_id", ORB.init().create_alias_tc(ContextIdHelper.id(), "ContextId", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("context_stateful", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("final_context_token", ORB.init().create_alias_tc(GSSTokenHelper.id(), "GSSToken", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CompleteEstablishContext s2) {
        any.type(CompleteEstablishContextHelper.type());
        CompleteEstablishContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CompleteEstablishContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CompleteEstablishContext completeEstablishContext = CompleteEstablishContextHelper.read(in);
            return completeEstablishContext;
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
        return "IDL:omg.org/CSI/CompleteEstablishContext:1.0";
    }

    public static CompleteEstablishContext read(InputStream in) {
        CompleteEstablishContext result = new CompleteEstablishContext();
        result.client_context_id = in.read_ulonglong();
        result.context_stateful = in.read_boolean();
        result.final_context_token = GSSTokenHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, CompleteEstablishContext s2) {
        out.write_ulonglong(s2.client_context_id);
        out.write_boolean(s2.context_stateful);
        GSSTokenHelper.write(out, s2.final_context_token);
    }
}

