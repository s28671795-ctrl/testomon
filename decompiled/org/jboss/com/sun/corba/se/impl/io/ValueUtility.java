/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.rmi.Remote;
import java.util.Stack;
import javax.rmi.CORBA.ValueHandler;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClassCorbaExt;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamField;
import org.jboss.com.sun.corba.se.impl.io.ValueHandlerImpl;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.org.omg.CORBA.AttributeDescription;
import org.jboss.com.sun.org.omg.CORBA.Initializer;
import org.jboss.com.sun.org.omg.CORBA.OperationDescription;
import org.jboss.com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.jboss.com.sun.org.omg.CORBA._IDLTypeStub;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;

public class ValueUtility {
    public static final short PRIVATE_MEMBER = 0;
    public static final short PUBLIC_MEMBER = 1;
    private static final String[] primitiveConstants = new String[]{null, null, "S", "I", "S", "I", "F", "D", "Z", "C", "B", null, null, null, null, null, null, null, null, null, null, null, null, "J", "J", "D", "C", null, null, null, null, null, null};

    public static String getSignature(ValueMember member) throws ClassNotFoundException {
        if (member.type.kind().value() == 30 || member.type.kind().value() == 29 || member.type.kind().value() == 14) {
            Class<?> c = RepositoryId.cache.getId(member.id).getClassFromType();
            return ObjectStreamClass.getSignature(c);
        }
        return primitiveConstants[member.type.kind().value()];
    }

    public static FullValueDescription translate(ORB orb, ObjectStreamClass osc, ValueHandler vh) {
        int interfaceIndex;
        FullValueDescription result = new FullValueDescription();
        Class<?> className = osc.forClass();
        ValueHandlerImpl vhandler = (ValueHandlerImpl)vh;
        String repId = vhandler.createForAnyType(className);
        result.name = vhandler.getUnqualifiedName(repId);
        if (result.name == null) {
            result.name = "";
        }
        result.id = vhandler.getRMIRepositoryID(className);
        if (result.id == null) {
            result.id = "";
        }
        result.is_abstract = ObjectStreamClassCorbaExt.isAbstractInterface(className);
        result.is_custom = osc.hasWriteObject() || osc.isExternalizable();
        result.defined_in = vhandler.getDefinedInId(repId);
        if (result.defined_in == null) {
            result.defined_in = "";
        }
        result.version = vhandler.getSerialVersionUID(repId);
        if (result.version == null) {
            result.version = "";
        }
        result.operations = new OperationDescription[0];
        result.attributes = new AttributeDescription[0];
        IdentityKeyValueStack createdIDs = new IdentityKeyValueStack();
        result.members = ValueUtility.translateMembers(orb, osc, vh, createdIDs);
        result.initializers = new Initializer[0];
        Class<?>[] interfaces = osc.forClass().getInterfaces();
        int abstractCount = 0;
        result.supported_interfaces = new String[interfaces.length];
        for (interfaceIndex = 0; interfaceIndex < interfaces.length; ++interfaceIndex) {
            result.supported_interfaces[interfaceIndex] = vhandler.createForAnyType(interfaces[interfaceIndex]);
            if (Remote.class.isAssignableFrom(interfaces[interfaceIndex]) && Modifier.isPublic(interfaces[interfaceIndex].getModifiers())) continue;
            ++abstractCount;
        }
        result.abstract_base_values = new String[abstractCount];
        for (interfaceIndex = 0; interfaceIndex < interfaces.length; ++interfaceIndex) {
            if (Remote.class.isAssignableFrom(interfaces[interfaceIndex]) && Modifier.isPublic(interfaces[interfaceIndex].getModifiers())) continue;
            result.abstract_base_values[interfaceIndex] = vhandler.createForAnyType(interfaces[interfaceIndex]);
        }
        result.is_truncatable = false;
        Class<?> superClass = osc.forClass().getSuperclass();
        result.base_value = Serializable.class.isAssignableFrom(superClass) ? vhandler.getRMIRepositoryID(superClass) : "";
        result.type = orb.get_primitive_tc(TCKind.tk_value);
        return result;
    }

    private static ValueMember[] translateMembers(ORB orb, ObjectStreamClass osc, ValueHandler vh, IdentityKeyValueStack createdIDs) {
        ValueHandlerImpl vhandler = (ValueHandlerImpl)vh;
        ObjectStreamField[] fields = osc.getFields();
        int fieldsLength = fields.length;
        ValueMember[] members = new ValueMember[fieldsLength];
        block10: for (int i = 0; i < fieldsLength; ++i) {
            int m4;
            String valRepId = vhandler.getRMIRepositoryID(fields[i].getClazz());
            members[i] = new ValueMember();
            members[i].name = fields[i].getName();
            members[i].id = valRepId;
            members[i].defined_in = vhandler.getDefinedInId(valRepId);
            members[i].version = "1.0";
            members[i].type_def = new _IDLTypeStub();
            members[i].access = fields[i].getField() == null ? (short)0 : (Modifier.isPublic(m4 = fields[i].getField().getModifiers()) ? (short)1 : 0);
            switch (fields[i].getTypeCode()) {
                case 'B': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_octet);
                    continue block10;
                }
                case 'C': {
                    members[i].type = orb.get_primitive_tc(vhandler.getJavaCharTCKind());
                    continue block10;
                }
                case 'F': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_float);
                    continue block10;
                }
                case 'D': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_double);
                    continue block10;
                }
                case 'I': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_long);
                    continue block10;
                }
                case 'J': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_longlong);
                    continue block10;
                }
                case 'S': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_short);
                    continue block10;
                }
                case 'Z': {
                    members[i].type = orb.get_primitive_tc(TCKind.tk_boolean);
                    continue block10;
                }
                default: {
                    members[i].type = ValueUtility.createTypeCodeForClassInternal(orb, fields[i].getClazz(), vhandler, createdIDs);
                    members[i].id = vhandler.createForAnyType(fields[i].getType());
                }
            }
        }
        return members;
    }

    private static boolean exists(String str, String[] strs) {
        for (int i = 0; i < strs.length; ++i) {
            if (!str.equals(strs[i])) continue;
            return true;
        }
        return false;
    }

    public static boolean isAssignableFrom(String clzRepositoryId, FullValueDescription type, CodeBase sender) {
        if (ValueUtility.exists(clzRepositoryId, type.supported_interfaces)) {
            return true;
        }
        if (clzRepositoryId.equals(type.id)) {
            return true;
        }
        if (type.base_value != null && !type.base_value.equals("")) {
            FullValueDescription parent = sender.meta(type.base_value);
            return ValueUtility.isAssignableFrom(clzRepositoryId, parent, sender);
        }
        return false;
    }

    public static TypeCode createTypeCodeForClass(ORB orb, Class<?> c, ValueHandler vh) {
        IdentityKeyValueStack createdIDs = new IdentityKeyValueStack();
        TypeCode tc = ValueUtility.createTypeCodeForClassInternal(orb, c, vh, createdIDs);
        return tc;
    }

    private static TypeCode createTypeCodeForClassInternal(ORB orb, Class<?> c, ValueHandler vh, IdentityKeyValueStack createdIDs) {
        TypeCode tc = null;
        String id = (String)createdIDs.get(c);
        if (id != null) {
            return orb.create_recursive_tc(id);
        }
        id = vh.getRMIRepositoryID(c);
        if (id == null) {
            id = "";
        }
        createdIDs.push(c, id);
        tc = ValueUtility.createTypeCodeInternal(orb, c, vh, id, createdIDs);
        createdIDs.pop();
        return tc;
    }

    private static TypeCode createTypeCodeInternal(ORB orb, Class<?> c, ValueHandler vh, String id, IdentityKeyValueStack createdIDs) {
        if (c.isArray()) {
            Class<?> componentClass = c.getComponentType();
            TypeCode embeddedType = componentClass.isPrimitive() ? ValueUtility.getPrimitiveTypeCodeForClass(orb, componentClass, vh) : ValueUtility.createTypeCodeForClassInternal(orb, componentClass, vh, createdIDs);
            TypeCode t2 = orb.create_sequence_tc(0, embeddedType);
            return orb.create_value_box_tc(id, "Sequence", t2);
        }
        if (c == String.class) {
            TypeCode t3 = orb.create_string_tc(0);
            return orb.create_value_box_tc(id, "StringValue", t3);
        }
        if (Remote.class.isAssignableFrom(c)) {
            return orb.get_primitive_tc(TCKind.tk_objref);
        }
        if (Object.class.isAssignableFrom(c)) {
            return orb.get_primitive_tc(TCKind.tk_objref);
        }
        ObjectStreamClass osc = ObjectStreamClass.lookup(c);
        if (osc == null) {
            return orb.create_value_box_tc(id, "Value", orb.get_primitive_tc(TCKind.tk_value));
        }
        short modifier = osc.isCustomMarshaled() ? (short)1 : 0;
        TypeCode base = null;
        Class<?> superClass = c.getSuperclass();
        if (superClass != null && Serializable.class.isAssignableFrom(superClass)) {
            base = ValueUtility.createTypeCodeForClassInternal(orb, superClass, vh, createdIDs);
        }
        ValueMember[] members = ValueUtility.translateMembers(orb, osc, vh, createdIDs);
        return orb.create_value_tc(id, c.getName(), modifier, base, members);
    }

    public static TypeCode getPrimitiveTypeCodeForClass(ORB orb, Class<?> c, ValueHandler vh) {
        if (c == Integer.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_long);
        }
        if (c == Byte.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_octet);
        }
        if (c == Long.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_longlong);
        }
        if (c == Float.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_float);
        }
        if (c == Double.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_double);
        }
        if (c == Short.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_short);
        }
        if (c == Character.TYPE) {
            return orb.get_primitive_tc(((ValueHandlerImpl)vh).getJavaCharTCKind());
        }
        if (c == Boolean.TYPE) {
            return orb.get_primitive_tc(TCKind.tk_boolean);
        }
        return orb.get_primitive_tc(TCKind.tk_any);
    }

    private static class IdentityKeyValueStack {
        Stack<KeyValuePair> pairs = null;

        private IdentityKeyValueStack() {
        }

        java.lang.Object get(java.lang.Object key) {
            if (this.pairs == null) {
                return null;
            }
            for (KeyValuePair pair : this.pairs) {
                if (pair.key != key) continue;
                return pair.value;
            }
            return null;
        }

        void push(java.lang.Object key, java.lang.Object value) {
            if (this.pairs == null) {
                this.pairs = new Stack();
            }
            this.pairs.push(new KeyValuePair(key, value));
        }

        void pop() {
            this.pairs.pop();
        }

        private static class KeyValuePair {
            java.lang.Object key;
            java.lang.Object value;

            KeyValuePair(java.lang.Object key, java.lang.Object value) {
                this.key = key;
                this.value = value;
            }

            boolean equals(KeyValuePair pair) {
                return pair.key == this.key;
            }
        }
    }
}

