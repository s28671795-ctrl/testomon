/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class PrimitiveKind
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _pk_null = 0;
    public static final PrimitiveKind pk_null = new PrimitiveKind(0);
    public static final int _pk_void = 1;
    public static final PrimitiveKind pk_void = new PrimitiveKind(1);
    public static final int _pk_short = 2;
    public static final PrimitiveKind pk_short = new PrimitiveKind(2);
    public static final int _pk_long = 3;
    public static final PrimitiveKind pk_long = new PrimitiveKind(3);
    public static final int _pk_ushort = 4;
    public static final PrimitiveKind pk_ushort = new PrimitiveKind(4);
    public static final int _pk_ulong = 5;
    public static final PrimitiveKind pk_ulong = new PrimitiveKind(5);
    public static final int _pk_float = 6;
    public static final PrimitiveKind pk_float = new PrimitiveKind(6);
    public static final int _pk_double = 7;
    public static final PrimitiveKind pk_double = new PrimitiveKind(7);
    public static final int _pk_boolean = 8;
    public static final PrimitiveKind pk_boolean = new PrimitiveKind(8);
    public static final int _pk_char = 9;
    public static final PrimitiveKind pk_char = new PrimitiveKind(9);
    public static final int _pk_octet = 10;
    public static final PrimitiveKind pk_octet = new PrimitiveKind(10);
    public static final int _pk_any = 11;
    public static final PrimitiveKind pk_any = new PrimitiveKind(11);
    public static final int _pk_TypeCode = 12;
    public static final PrimitiveKind pk_TypeCode = new PrimitiveKind(12);
    public static final int _pk_Principal = 13;
    public static final PrimitiveKind pk_Principal = new PrimitiveKind(13);
    public static final int _pk_string = 14;
    public static final PrimitiveKind pk_string = new PrimitiveKind(14);
    public static final int _pk_objref = 15;
    public static final PrimitiveKind pk_objref = new PrimitiveKind(15);
    public static final int _pk_longlong = 16;
    public static final PrimitiveKind pk_longlong = new PrimitiveKind(16);
    public static final int _pk_ulonglong = 17;
    public static final PrimitiveKind pk_ulonglong = new PrimitiveKind(17);
    public static final int _pk_longdouble = 18;
    public static final PrimitiveKind pk_longdouble = new PrimitiveKind(18);
    public static final int _pk_wchar = 19;
    public static final PrimitiveKind pk_wchar = new PrimitiveKind(19);
    public static final int _pk_wstring = 20;
    public static final PrimitiveKind pk_wstring = new PrimitiveKind(20);
    public static final int _pk_value_base = 21;
    public static final PrimitiveKind pk_value_base = new PrimitiveKind(21);

    public int value() {
        return this.value;
    }

    public static PrimitiveKind from_int(int value) {
        switch (value) {
            case 0: {
                return pk_null;
            }
            case 1: {
                return pk_void;
            }
            case 2: {
                return pk_short;
            }
            case 3: {
                return pk_long;
            }
            case 4: {
                return pk_ushort;
            }
            case 5: {
                return pk_ulong;
            }
            case 6: {
                return pk_float;
            }
            case 7: {
                return pk_double;
            }
            case 8: {
                return pk_boolean;
            }
            case 9: {
                return pk_char;
            }
            case 10: {
                return pk_octet;
            }
            case 11: {
                return pk_any;
            }
            case 12: {
                return pk_TypeCode;
            }
            case 13: {
                return pk_Principal;
            }
            case 14: {
                return pk_string;
            }
            case 15: {
                return pk_objref;
            }
            case 16: {
                return pk_longlong;
            }
            case 17: {
                return pk_ulonglong;
            }
            case 18: {
                return pk_longdouble;
            }
            case 19: {
                return pk_wchar;
            }
            case 20: {
                return pk_wstring;
            }
            case 21: {
                return pk_value_base;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "pk_null";
            }
            case 1: {
                return "pk_void";
            }
            case 2: {
                return "pk_short";
            }
            case 3: {
                return "pk_long";
            }
            case 4: {
                return "pk_ushort";
            }
            case 5: {
                return "pk_ulong";
            }
            case 6: {
                return "pk_float";
            }
            case 7: {
                return "pk_double";
            }
            case 8: {
                return "pk_boolean";
            }
            case 9: {
                return "pk_char";
            }
            case 10: {
                return "pk_octet";
            }
            case 11: {
                return "pk_any";
            }
            case 12: {
                return "pk_TypeCode";
            }
            case 13: {
                return "pk_Principal";
            }
            case 14: {
                return "pk_string";
            }
            case 15: {
                return "pk_objref";
            }
            case 16: {
                return "pk_longlong";
            }
            case 17: {
                return "pk_ulonglong";
            }
            case 18: {
                return "pk_longdouble";
            }
            case 19: {
                return "pk_wchar";
            }
            case 20: {
                return "pk_wstring";
            }
            case 21: {
                return "pk_value_base";
            }
        }
        throw new BAD_PARAM();
    }

    protected PrimitiveKind(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return PrimitiveKind.from_int(this.value());
    }
}

