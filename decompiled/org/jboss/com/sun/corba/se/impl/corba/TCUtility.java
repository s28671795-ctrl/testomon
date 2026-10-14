/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import java.io.Serializable;
import java.math.BigDecimal;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class TCUtility {
    static void marshalIn(org.omg.CORBA.portable.OutputStream s2, TypeCode typeCode, long l, java.lang.Object o) {
        switch (typeCode.kind().value()) {
            case 0: 
            case 1: 
            case 31: {
                break;
            }
            case 2: {
                s2.write_short((short)(l & 0xFFFFL));
                break;
            }
            case 4: {
                s2.write_ushort((short)(l & 0xFFFFL));
                break;
            }
            case 3: 
            case 17: {
                s2.write_long((int)(l & 0xFFFFFFFFL));
                break;
            }
            case 5: {
                s2.write_ulong((int)(l & 0xFFFFFFFFL));
                break;
            }
            case 6: {
                s2.write_float(Float.intBitsToFloat((int)(l & 0xFFFFFFFFL)));
                break;
            }
            case 7: {
                s2.write_double(Double.longBitsToDouble(l));
                break;
            }
            case 8: {
                if (l == 0L) {
                    s2.write_boolean(false);
                    break;
                }
                s2.write_boolean(true);
                break;
            }
            case 9: {
                s2.write_char((char)(l & 0xFFFFL));
                break;
            }
            case 10: {
                s2.write_octet((byte)(l & 0xFFL));
                break;
            }
            case 11: {
                s2.write_any((Any)o);
                break;
            }
            case 12: {
                s2.write_TypeCode((TypeCode)o);
                break;
            }
            case 13: {
                s2.write_Principal((Principal)o);
                break;
            }
            case 14: {
                s2.write_Object((Object)o);
                break;
            }
            case 23: {
                s2.write_longlong(l);
                break;
            }
            case 24: {
                s2.write_ulonglong(l);
                break;
            }
            case 26: {
                s2.write_wchar((char)(l & 0xFFFFL));
                break;
            }
            case 18: {
                s2.write_string((String)o);
                break;
            }
            case 27: {
                s2.write_wstring((String)o);
                break;
            }
            case 29: 
            case 30: {
                ((OutputStream)s2).write_value((Serializable)o);
                break;
            }
            case 28: {
                if (s2 instanceof CDROutputStream) {
                    try {
                        ((CDROutputStream)s2).write_fixed((BigDecimal)o, typeCode.fixed_digits(), typeCode.fixed_scale());
                    }
                    catch (BadKind badKind) {}
                    break;
                }
                s2.write_fixed((BigDecimal)o);
                break;
            }
            case 15: 
            case 16: 
            case 19: 
            case 20: 
            case 21: 
            case 22: {
                ((Streamable)o)._write(s2);
                break;
            }
            case 32: {
                ((OutputStream)s2).write_abstract_interface(o);
                break;
            }
            default: {
                ORBUtilSystemException wrapper = ORBUtilSystemException.get((ORB)s2.orb(), "rpc.presentation");
                throw wrapper.typecodeNotSupported();
            }
        }
    }

    static void unmarshalIn(org.omg.CORBA.portable.InputStream s2, TypeCode typeCode, long[] la, java.lang.Object[] oa) {
        int type = typeCode.kind().value();
        long l = 0L;
        java.lang.Object o = oa[0];
        switch (type) {
            case 0: 
            case 1: 
            case 31: {
                break;
            }
            case 2: {
                l = (long)s2.read_short() & 0xFFFFL;
                break;
            }
            case 4: {
                l = (long)s2.read_ushort() & 0xFFFFL;
                break;
            }
            case 3: 
            case 17: {
                l = (long)s2.read_long() & 0xFFFFFFFFL;
                break;
            }
            case 5: {
                l = (long)s2.read_ulong() & 0xFFFFFFFFL;
                break;
            }
            case 6: {
                l = (long)Float.floatToIntBits(s2.read_float()) & 0xFFFFFFFFL;
                break;
            }
            case 7: {
                l = Double.doubleToLongBits(s2.read_double());
                break;
            }
            case 9: {
                l = (long)s2.read_char() & 0xFFFFL;
                break;
            }
            case 10: {
                l = (long)s2.read_octet() & 0xFFL;
                break;
            }
            case 8: {
                if (s2.read_boolean()) {
                    l = 1L;
                    break;
                }
                l = 0L;
                break;
            }
            case 11: {
                o = s2.read_any();
                break;
            }
            case 12: {
                o = s2.read_TypeCode();
                break;
            }
            case 13: {
                o = s2.read_Principal();
                break;
            }
            case 14: {
                if (o instanceof Streamable) {
                    ((Streamable)o)._read(s2);
                    break;
                }
                o = s2.read_Object();
                break;
            }
            case 23: {
                l = s2.read_longlong();
                break;
            }
            case 24: {
                l = s2.read_ulonglong();
                break;
            }
            case 26: {
                l = (long)s2.read_wchar() & 0xFFFFL;
                break;
            }
            case 18: {
                o = s2.read_string();
                break;
            }
            case 27: {
                o = s2.read_wstring();
                break;
            }
            case 29: 
            case 30: {
                o = ((InputStream)s2).read_value();
                break;
            }
            case 28: {
                try {
                    if (s2 instanceof CDRInputStream) {
                        o = ((CDRInputStream)s2).read_fixed(typeCode.fixed_digits(), typeCode.fixed_scale());
                        break;
                    }
                    BigDecimal bigDecimal = s2.read_fixed();
                    o = bigDecimal.movePointLeft(typeCode.fixed_scale());
                }
                catch (BadKind bigDecimal) {}
                break;
            }
            case 15: 
            case 16: 
            case 19: 
            case 20: 
            case 21: 
            case 22: {
                ((Streamable)o)._read(s2);
                break;
            }
            case 32: {
                o = ((InputStream)s2).read_abstract_interface();
                break;
            }
            default: {
                ORBUtilSystemException wrapper = ORBUtilSystemException.get((ORB)s2.orb(), "rpc.presentation");
                throw wrapper.typecodeNotSupported();
            }
        }
        oa[0] = o;
        la[0] = l;
    }
}

