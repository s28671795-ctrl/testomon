/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.CompleteEstablishContext;
import org.omg.CSI.CompleteEstablishContextHelper;
import org.omg.CSI.ContextError;
import org.omg.CSI.ContextErrorHelper;
import org.omg.CSI.EstablishContext;
import org.omg.CSI.EstablishContextHelper;
import org.omg.CSI.MessageInContext;
import org.omg.CSI.MessageInContextHelper;
import org.omg.CSI.MsgTypeHelper;
import org.omg.CSI.SASContextBody;

public abstract class SASContextBodyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SASContextBodyHelper> clazz = SASContextBodyHelper.class;
        synchronized (SASContextBodyHelper.class) {
            if (_type != null) return _type;
            UnionMember[] members = new UnionMember[4];
            Any label_any = ORB.init().create_any();
            label_any.insert_short((short)0);
            members[0] = new UnionMember("establish_msg", label_any, EstablishContextHelper.type(), null);
            label_any = ORB.init().create_any();
            label_any.insert_short((short)1);
            members[1] = new UnionMember("complete_msg", label_any, CompleteEstablishContextHelper.type(), null);
            label_any = ORB.init().create_any();
            label_any.insert_short((short)4);
            members[2] = new UnionMember("error_msg", label_any, ContextErrorHelper.type(), null);
            label_any = ORB.init().create_any();
            label_any.insert_short((short)5);
            members[3] = new UnionMember("in_context_msg", label_any, MessageInContextHelper.type(), null);
            _type = ORB.init().create_union_tc(SASContextBodyHelper.id(), "SASContextBody", ORB.init().create_alias_tc(MsgTypeHelper.id(), "MsgType", ORB.init().get_primitive_tc(TCKind.from_int(2))), members);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SASContextBody s2) {
        any.type(SASContextBodyHelper.type());
        SASContextBodyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SASContextBody extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SASContextBody sASContextBody = SASContextBodyHelper.read(in);
            return sASContextBody;
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
        return "IDL:omg.org/CSI/SASContextBody:1.0";
    }

    public static SASContextBody read(InputStream in) {
        SASContextBody result = new SASContextBody();
        short disc = in.read_short();
        switch (disc) {
            case 0: {
                EstablishContext _var = EstablishContextHelper.read(in);
                result.establish_msg(_var);
                break;
            }
            case 1: {
                CompleteEstablishContext _var = CompleteEstablishContextHelper.read(in);
                result.complete_msg(_var);
                break;
            }
            case 4: {
                ContextError _var = ContextErrorHelper.read(in);
                result.error_msg(_var);
                break;
            }
            case 5: {
                MessageInContext _var = MessageInContextHelper.read(in);
                result.in_context_msg(_var);
                break;
            }
        }
        return result;
    }

    public static void write(OutputStream out, SASContextBody s2) {
        out.write_short(s2.discriminator());
        switch (s2.discriminator()) {
            case 0: {
                EstablishContextHelper.write(out, s2.establish_msg());
                break;
            }
            case 1: {
                CompleteEstablishContextHelper.write(out, s2.complete_msg());
                break;
            }
            case 4: {
                ContextErrorHelper.write(out, s2.error_msg());
                break;
            }
            case 5: {
                MessageInContextHelper.write(out, s2.in_context_msg());
            }
        }
    }
}

