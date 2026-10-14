/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class DefinitionKind
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _dk_none = 0;
    public static final DefinitionKind dk_none = new DefinitionKind(0);
    public static final int _dk_all = 1;
    public static final DefinitionKind dk_all = new DefinitionKind(1);
    public static final int _dk_Attribute = 2;
    public static final DefinitionKind dk_Attribute = new DefinitionKind(2);
    public static final int _dk_Constant = 3;
    public static final DefinitionKind dk_Constant = new DefinitionKind(3);
    public static final int _dk_Exception = 4;
    public static final DefinitionKind dk_Exception = new DefinitionKind(4);
    public static final int _dk_Interface = 5;
    public static final DefinitionKind dk_Interface = new DefinitionKind(5);
    public static final int _dk_Module = 6;
    public static final DefinitionKind dk_Module = new DefinitionKind(6);
    public static final int _dk_Operation = 7;
    public static final DefinitionKind dk_Operation = new DefinitionKind(7);
    public static final int _dk_Typedef = 8;
    public static final DefinitionKind dk_Typedef = new DefinitionKind(8);
    public static final int _dk_Alias = 9;
    public static final DefinitionKind dk_Alias = new DefinitionKind(9);
    public static final int _dk_Struct = 10;
    public static final DefinitionKind dk_Struct = new DefinitionKind(10);
    public static final int _dk_Union = 11;
    public static final DefinitionKind dk_Union = new DefinitionKind(11);
    public static final int _dk_Enum = 12;
    public static final DefinitionKind dk_Enum = new DefinitionKind(12);
    public static final int _dk_Primitive = 13;
    public static final DefinitionKind dk_Primitive = new DefinitionKind(13);
    public static final int _dk_String = 14;
    public static final DefinitionKind dk_String = new DefinitionKind(14);
    public static final int _dk_Sequence = 15;
    public static final DefinitionKind dk_Sequence = new DefinitionKind(15);
    public static final int _dk_Array = 16;
    public static final DefinitionKind dk_Array = new DefinitionKind(16);
    public static final int _dk_Repository = 17;
    public static final DefinitionKind dk_Repository = new DefinitionKind(17);
    public static final int _dk_Wstring = 18;
    public static final DefinitionKind dk_Wstring = new DefinitionKind(18);
    public static final int _dk_Fixed = 19;
    public static final DefinitionKind dk_Fixed = new DefinitionKind(19);
    public static final int _dk_Value = 20;
    public static final DefinitionKind dk_Value = new DefinitionKind(20);
    public static final int _dk_ValueBox = 21;
    public static final DefinitionKind dk_ValueBox = new DefinitionKind(21);
    public static final int _dk_ValueMember = 22;
    public static final DefinitionKind dk_ValueMember = new DefinitionKind(22);
    public static final int _dk_Native = 23;
    public static final DefinitionKind dk_Native = new DefinitionKind(23);
    public static final int _dk_AbstractInterface = 24;
    public static final DefinitionKind dk_AbstractInterface = new DefinitionKind(24);
    public static final int _dk_LocalInterface = 25;
    public static final DefinitionKind dk_LocalInterface = new DefinitionKind(25);
    public static final int _dk_Component = 26;
    public static final DefinitionKind dk_Component = new DefinitionKind(26);
    public static final int _dk_Home = 27;
    public static final DefinitionKind dk_Home = new DefinitionKind(27);
    public static final int _dk_Factory = 28;
    public static final DefinitionKind dk_Factory = new DefinitionKind(28);
    public static final int _dk_Finder = 29;
    public static final DefinitionKind dk_Finder = new DefinitionKind(29);
    public static final int _dk_Emits = 30;
    public static final DefinitionKind dk_Emits = new DefinitionKind(30);
    public static final int _dk_Publishes = 31;
    public static final DefinitionKind dk_Publishes = new DefinitionKind(31);
    public static final int _dk_Consumes = 32;
    public static final DefinitionKind dk_Consumes = new DefinitionKind(32);
    public static final int _dk_Provides = 33;
    public static final DefinitionKind dk_Provides = new DefinitionKind(33);
    public static final int _dk_Uses = 34;
    public static final DefinitionKind dk_Uses = new DefinitionKind(34);
    public static final int _dk_Event = 35;
    public static final DefinitionKind dk_Event = new DefinitionKind(35);

    public int value() {
        return this.value;
    }

    public static DefinitionKind from_int(int value) {
        switch (value) {
            case 0: {
                return dk_none;
            }
            case 1: {
                return dk_all;
            }
            case 2: {
                return dk_Attribute;
            }
            case 3: {
                return dk_Constant;
            }
            case 4: {
                return dk_Exception;
            }
            case 5: {
                return dk_Interface;
            }
            case 6: {
                return dk_Module;
            }
            case 7: {
                return dk_Operation;
            }
            case 8: {
                return dk_Typedef;
            }
            case 9: {
                return dk_Alias;
            }
            case 10: {
                return dk_Struct;
            }
            case 11: {
                return dk_Union;
            }
            case 12: {
                return dk_Enum;
            }
            case 13: {
                return dk_Primitive;
            }
            case 14: {
                return dk_String;
            }
            case 15: {
                return dk_Sequence;
            }
            case 16: {
                return dk_Array;
            }
            case 17: {
                return dk_Repository;
            }
            case 18: {
                return dk_Wstring;
            }
            case 19: {
                return dk_Fixed;
            }
            case 20: {
                return dk_Value;
            }
            case 21: {
                return dk_ValueBox;
            }
            case 22: {
                return dk_ValueMember;
            }
            case 23: {
                return dk_Native;
            }
            case 24: {
                return dk_AbstractInterface;
            }
            case 25: {
                return dk_LocalInterface;
            }
            case 26: {
                return dk_Component;
            }
            case 27: {
                return dk_Home;
            }
            case 28: {
                return dk_Factory;
            }
            case 29: {
                return dk_Finder;
            }
            case 30: {
                return dk_Emits;
            }
            case 31: {
                return dk_Publishes;
            }
            case 32: {
                return dk_Consumes;
            }
            case 33: {
                return dk_Provides;
            }
            case 34: {
                return dk_Uses;
            }
            case 35: {
                return dk_Event;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "dk_none";
            }
            case 1: {
                return "dk_all";
            }
            case 2: {
                return "dk_Attribute";
            }
            case 3: {
                return "dk_Constant";
            }
            case 4: {
                return "dk_Exception";
            }
            case 5: {
                return "dk_Interface";
            }
            case 6: {
                return "dk_Module";
            }
            case 7: {
                return "dk_Operation";
            }
            case 8: {
                return "dk_Typedef";
            }
            case 9: {
                return "dk_Alias";
            }
            case 10: {
                return "dk_Struct";
            }
            case 11: {
                return "dk_Union";
            }
            case 12: {
                return "dk_Enum";
            }
            case 13: {
                return "dk_Primitive";
            }
            case 14: {
                return "dk_String";
            }
            case 15: {
                return "dk_Sequence";
            }
            case 16: {
                return "dk_Array";
            }
            case 17: {
                return "dk_Repository";
            }
            case 18: {
                return "dk_Wstring";
            }
            case 19: {
                return "dk_Fixed";
            }
            case 20: {
                return "dk_Value";
            }
            case 21: {
                return "dk_ValueBox";
            }
            case 22: {
                return "dk_ValueMember";
            }
            case 23: {
                return "dk_Native";
            }
            case 24: {
                return "dk_AbstractInterface";
            }
            case 25: {
                return "dk_LocalInterface";
            }
            case 26: {
                return "dk_Component";
            }
            case 27: {
                return "dk_Home";
            }
            case 28: {
                return "dk_Factory";
            }
            case 29: {
                return "dk_Finder";
            }
            case 30: {
                return "dk_Emits";
            }
            case 31: {
                return "dk_Publishes";
            }
            case 32: {
                return "dk_Consumes";
            }
            case 33: {
                return "dk_Provides";
            }
            case 34: {
                return "dk_Uses";
            }
            case 35: {
                return "dk_Event";
            }
        }
        throw new BAD_PARAM();
    }

    protected DefinitionKind(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return DefinitionKind.from_int(this.value());
    }
}

