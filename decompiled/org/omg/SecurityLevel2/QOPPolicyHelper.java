/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SecurityLevel2.QOPPolicy;

public abstract class QOPPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<QOPPolicyHelper> clazz = QOPPolicyHelper.class;
        synchronized (QOPPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/QOPPolicy:1.0", "QOPPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, QOPPolicy s2) {
        any.insert_Object(s2);
    }

    public static QOPPolicy extract(Any any) {
        return QOPPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/QOPPolicy:1.0";
    }

    public static QOPPolicy read(InputStream in) {
        return QOPPolicyHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, QOPPolicy s2) {
        _out.write_Object(s2);
    }

    public static QOPPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof QOPPolicy) {
            return (QOPPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static QOPPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof QOPPolicy) {
            return (QOPPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

