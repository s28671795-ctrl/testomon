/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class TCKind
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _tk_null = 0;
    public static final TCKind tk_null = new TCKind(0);
    public static final int _tk_void = 1;
    public static final TCKind tk_void = new TCKind(1);
    public static final int _tk_short = 2;
    public static final TCKind tk_short = new TCKind(2);
    public static final int _tk_long = 3;
    public static final TCKind tk_long = new TCKind(3);
    public static final int _tk_ushort = 4;
    public static final TCKind tk_ushort = new TCKind(4);
    public static final int _tk_ulong = 5;
    public static final TCKind tk_ulong = new TCKind(5);
    public static final int _tk_float = 6;
    public static final TCKind tk_float = new TCKind(6);
    public static final int _tk_double = 7;
    public static final TCKind tk_double = new TCKind(7);
    public static final int _tk_boolean = 8;
    public static final TCKind tk_boolean = new TCKind(8);
    public static final int _tk_char = 9;
    public static final TCKind tk_char = new TCKind(9);
    public static final int _tk_octet = 10;
    public static final TCKind tk_octet = new TCKind(10);
    public static final int _tk_any = 11;
    public static final TCKind tk_any = new TCKind(11);
    public static final int _tk_TypeCode = 12;
    public static final TCKind tk_TypeCode = new TCKind(12);
    public static final int _tk_Principal = 13;
    public static final TCKind tk_Principal = new TCKind(13);
    public static final int _tk_objref = 14;
    public static final TCKind tk_objref = new TCKind(14);
    public static final int _tk_struct = 15;
    public static final TCKind tk_struct = new TCKind(15);
    public static final int _tk_union = 16;
    public static final TCKind tk_union = new TCKind(16);
    public static final int _tk_enum = 17;
    public static final TCKind tk_enum = new TCKind(17);
    public static final int _tk_string = 18;
    public static final TCKind tk_string = new TCKind(18);
    public static final int _tk_sequence = 19;
    public static final TCKind tk_sequence = new TCKind(19);
    public static final int _tk_array = 20;
    public static final TCKind tk_array = new TCKind(20);
    public static final int _tk_alias = 21;
    public static final TCKind tk_alias = new TCKind(21);
    public static final int _tk_except = 22;
    public static final TCKind tk_except = new TCKind(22);
    public static final int _tk_longlong = 23;
    public static final TCKind tk_longlong = new TCKind(23);
    public static final int _tk_ulonglong = 24;
    public static final TCKind tk_ulonglong = new TCKind(24);
    public static final int _tk_longdouble = 25;
    public static final TCKind tk_longdouble = new TCKind(25);
    public static final int _tk_wchar = 26;
    public static final TCKind tk_wchar = new TCKind(26);
    public static final int _tk_wstring = 27;
    public static final TCKind tk_wstring = new TCKind(27);
    public static final int _tk_fixed = 28;
    public static final TCKind tk_fixed = new TCKind(28);
    public static final int _tk_value = 29;
    public static final TCKind tk_value = new TCKind(29);
    public static final int _tk_value_box = 30;
    public static final TCKind tk_value_box = new TCKind(30);
    public static final int _tk_native = 31;
    public static final TCKind tk_native = new TCKind(31);
    public static final int _tk_abstract_interface = 32;
    public static final TCKind tk_abstract_interface = new TCKind(32);
    public static final int _tk_local_interface = 33;
    public static final TCKind tk_local_interface = new TCKind(33);
    public static final int _tk_component = 34;
    public static final TCKind tk_component = new TCKind(34);
    public static final int _tk_home = 35;
    public static final TCKind tk_home = new TCKind(35);
    public static final int _tk_event = 36;
    public static final TCKind tk_event = new TCKind(36);

    public int value() {
        return this.value;
    }

    public static TCKind from_int(int value) {
        switch (value) {
            case 0: {
                return tk_null;
            }
            case 1: {
                return tk_void;
            }
            case 2: {
                return tk_short;
            }
            case 3: {
                return tk_long;
            }
            case 4: {
                return tk_ushort;
            }
            case 5: {
                return tk_ulong;
            }
            case 6: {
                return tk_float;
            }
            case 7: {
                return tk_double;
            }
            case 8: {
                return tk_boolean;
            }
            case 9: {
                return tk_char;
            }
            case 10: {
                return tk_octet;
            }
            case 11: {
                return tk_any;
            }
            case 12: {
                return tk_TypeCode;
            }
            case 13: {
                return tk_Principal;
            }
            case 14: {
                return tk_objref;
            }
            case 15: {
                return tk_struct;
            }
            case 16: {
                return tk_union;
            }
            case 17: {
                return tk_enum;
            }
            case 18: {
                return tk_string;
            }
            case 19: {
                return tk_sequence;
            }
            case 20: {
                return tk_array;
            }
            case 21: {
                return tk_alias;
            }
            case 22: {
                return tk_except;
            }
            case 23: {
                return tk_longlong;
            }
            case 24: {
                return tk_ulonglong;
            }
            case 25: {
                return tk_longdouble;
            }
            case 26: {
                return tk_wchar;
            }
            case 27: {
                return tk_wstring;
            }
            case 28: {
                return tk_fixed;
            }
            case 29: {
                return tk_value;
            }
            case 30: {
                return tk_value_box;
            }
            case 31: {
                return tk_native;
            }
            case 32: {
                return tk_abstract_interface;
            }
            case 33: {
                return tk_local_interface;
            }
            case 34: {
                return tk_component;
            }
            case 35: {
                return tk_home;
            }
            case 36: {
                return tk_event;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "tk_null";
            }
            case 1: {
                return "tk_void";
            }
            case 2: {
                return "tk_short";
            }
            case 3: {
                return "tk_long";
            }
            case 4: {
                return "tk_ushort";
            }
            case 5: {
                return "tk_ulong";
            }
            case 6: {
                return "tk_float";
            }
            case 7: {
                return "tk_double";
            }
            case 8: {
                return "tk_boolean";
            }
            case 9: {
                return "tk_char";
            }
            case 10: {
                return "tk_octet";
            }
            case 11: {
                return "tk_any";
            }
            case 12: {
                return "tk_TypeCode";
            }
            case 13: {
                return "tk_Principal";
            }
            case 14: {
                return "tk_objref";
            }
            case 15: {
                return "tk_struct";
            }
            case 16: {
                return "tk_union";
            }
            case 17: {
                return "tk_enum";
            }
            case 18: {
                return "tk_string";
            }
            case 19: {
                return "tk_sequence";
            }
            case 20: {
                return "tk_array";
            }
            case 21: {
                return "tk_alias";
            }
            case 22: {
                return "tk_except";
            }
            case 23: {
                return "tk_longlong";
            }
            case 24: {
                return "tk_ulonglong";
            }
            case 25: {
                return "tk_longdouble";
            }
            case 26: {
                return "tk_wchar";
            }
            case 27: {
                return "tk_wstring";
            }
            case 28: {
                return "tk_fixed";
            }
            case 29: {
                return "tk_value";
            }
            case 30: {
                return "tk_value_box";
            }
            case 31: {
                return "tk_native";
            }
            case 32: {
                return "tk_abstract_interface";
            }
            case 33: {
                return "tk_local_interface";
            }
            case 34: {
                return "tk_component";
            }
            case 35: {
                return "tk_home";
            }
            case 36: {
                return "tk_event";
            }
        }
        throw new BAD_PARAM();
    }

    protected TCKind(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return TCKind.from_int(this.value());
    }
}

