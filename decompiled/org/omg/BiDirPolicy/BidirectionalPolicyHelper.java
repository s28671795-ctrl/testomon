/*
 * Decompiled with CFR 0.152.
 */
package org.omg.BiDirPolicy;

import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy._BidirectionalPolicyStub;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class BidirectionalPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BidirectionalPolicyHelper> clazz = BidirectionalPolicyHelper.class;
        synchronized (BidirectionalPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/BiDirPolicy/BidirectionalPolicy:1.0", "BidirectionalPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BidirectionalPolicy s2) {
        any.insert_Object(s2);
    }

    public static BidirectionalPolicy extract(Any any) {
        return BidirectionalPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/BiDirPolicy/BidirectionalPolicy:1.0";
    }

    public static BidirectionalPolicy read(InputStream in) {
        return BidirectionalPolicyHelper.narrow(in.read_Object(_BidirectionalPolicyStub.class));
    }

    public static void write(OutputStream _out, BidirectionalPolicy s2) {
        _out.write_Object(s2);
    }

    public static BidirectionalPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BidirectionalPolicy) {
            return (BidirectionalPolicy)obj;
        }
        if (obj._is_a("IDL:omg.org/BiDirPolicy/BidirectionalPolicy:1.0")) {
            _BidirectionalPolicyStub stub = new _BidirectionalPolicyStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static BidirectionalPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BidirectionalPolicy) {
            return (BidirectionalPolicy)obj;
        }
        _BidirectionalPolicyStub stub = new _BidirectionalPolicyStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

