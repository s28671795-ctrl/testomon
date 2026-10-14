/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.ValueUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.ObjectStreamField;
import org.omg.CORBA.ValueMember;

public class ObjectStreamClass_1_3_1
implements Serializable {
    public static final long kDefaultUID = -1L;
    private static Object[] noArgsList = new Object[0];
    private static Class<?>[] noTypesList = new Class[0];
    private static Hashtable<Object, Object> translatedFields;
    private static ObjectStreamClassEntry[] descriptorFor;
    private String name;
    private ObjectStreamClass_1_3_1 superclass;
    private boolean serializable;
    private boolean externalizable;
    private ObjectStreamField[] fields;
    private Class<?> ofClass;
    boolean forProxyClass;
    private long suid = -1L;
    private String suidStr = null;
    private long actualSuid = -1L;
    private String actualSuidStr = null;
    int primBytes;
    int objFields;
    private Object lock = new Object();
    private boolean hasWriteObjectMethod;
    private boolean hasExternalizableBlockData;
    Method writeObjectMethod;
    Method readObjectMethod;
    private Method writeReplaceObjectMethod;
    private Method readResolveObjectMethod;
    private ObjectStreamClass_1_3_1 localClassDesc;
    private static Class<?> classSerializable;
    private static Class<?> classExternalizable;
    private static final long serialVersionUID = -6120832682080437368L;
    public static final ObjectStreamField[] NO_FIELDS;
    private static Comparator<Member> compareMemberByName;

    static final ObjectStreamClass_1_3_1 lookup(Class<?> cl) {
        ObjectStreamClass_1_3_1 desc = ObjectStreamClass_1_3_1.lookupInternal(cl);
        if (desc.isSerializable() || desc.isExternalizable()) {
            return desc;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static ObjectStreamClass_1_3_1 lookupInternal(Class<?> cl) {
        ObjectStreamClass_1_3_1 desc = null;
        ObjectStreamClassEntry[] objectStreamClassEntryArray = descriptorFor;
        synchronized (descriptorFor) {
            Class<?> superclass;
            desc = ObjectStreamClass_1_3_1.findDescriptorFor(cl);
            if (desc != null) {
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return desc;
            }
            boolean serializable = classSerializable.isAssignableFrom(cl);
            ObjectStreamClass_1_3_1 superdesc = null;
            if (serializable && (superclass = cl.getSuperclass()) != null) {
                superdesc = ObjectStreamClass_1_3_1.lookup(superclass);
            }
            boolean externalizable = false;
            if (serializable) {
                boolean bl = externalizable = superdesc != null && superdesc.isExternalizable() || classExternalizable.isAssignableFrom(cl);
                if (externalizable) {
                    serializable = false;
                }
            }
            desc = new ObjectStreamClass_1_3_1(cl, superdesc, serializable, externalizable);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            desc.init();
            return desc;
        }
    }

    public final String getName() {
        return this.name;
    }

    public static final long getSerialVersionUID(Class<?> clazz) {
        ObjectStreamClass_1_3_1 theosc = ObjectStreamClass_1_3_1.lookup(clazz);
        if (theosc != null) {
            return theosc.getSerialVersionUID();
        }
        return 0L;
    }

    public final long getSerialVersionUID() {
        return this.suid;
    }

    public final String getSerialVersionUIDStr() {
        if (this.suidStr == null) {
            this.suidStr = Long.toHexString(this.suid).toUpperCase();
        }
        return this.suidStr;
    }

    public static final long getActualSerialVersionUID(Class<?> clazz) {
        ObjectStreamClass_1_3_1 theosc = ObjectStreamClass_1_3_1.lookup(clazz);
        if (theosc != null) {
            return theosc.getActualSerialVersionUID();
        }
        return 0L;
    }

    public final long getActualSerialVersionUID() {
        return this.actualSuid;
    }

    public final String getActualSerialVersionUIDStr() {
        if (this.actualSuidStr == null) {
            this.actualSuidStr = Long.toHexString(this.actualSuid).toUpperCase();
        }
        return this.actualSuidStr;
    }

    public final Class<?> forClass() {
        return this.ofClass;
    }

    public ObjectStreamField[] getFields() {
        if (this.fields.length > 0) {
            ObjectStreamField[] dup = new ObjectStreamField[this.fields.length];
            System.arraycopy(this.fields, 0, dup, 0, this.fields.length);
            return dup;
        }
        return this.fields;
    }

    public boolean hasField(ValueMember field2) {
        for (int i = 0; i < this.fields.length; ++i) {
            try {
                if (!this.fields[i].getName().equals(field2.name) || !this.fields[i].getSignature().equals(ValueUtility.getSignature(field2))) continue;
                return true;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return false;
    }

    final ObjectStreamField[] getFieldsNoCopy() {
        return this.fields;
    }

    public final ObjectStreamField getField(String name) {
        for (int i = this.fields.length - 1; i >= 0; --i) {
            if (!name.equals(this.fields[i].getName())) continue;
            return this.fields[i];
        }
        return null;
    }

    public Serializable writeReplace(Serializable value) {
        if (this.writeReplaceObjectMethod != null) {
            try {
                return (Serializable)this.writeReplaceObjectMethod.invoke((Object)value, noArgsList);
            }
            catch (Throwable t2) {
                throw new RuntimeException(t2.getMessage());
            }
        }
        return value;
    }

    public Object readResolve(Object value) {
        if (this.readResolveObjectMethod != null) {
            try {
                return this.readResolveObjectMethod.invoke(value, noArgsList);
            }
            catch (Throwable t2) {
                throw new RuntimeException(t2.getMessage());
            }
        }
        return value;
    }

    public final String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name);
        sb.append(": static final long serialVersionUID = ");
        sb.append(Long.toString(this.suid));
        sb.append("L;");
        return sb.toString();
    }

    private ObjectStreamClass_1_3_1(Class<?> cl, ObjectStreamClass_1_3_1 superdesc, boolean serial, boolean extern) {
        this.ofClass = cl;
        if (Proxy.isProxyClass(cl)) {
            this.forProxyClass = true;
        }
        this.name = cl.getName();
        this.superclass = superdesc;
        this.serializable = serial;
        if (!this.forProxyClass) {
            this.externalizable = extern;
        }
        ObjectStreamClass_1_3_1.insertDescriptorFor(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        Object object = this.lock;
        synchronized (object) {
            final Class<?> cl = this.ofClass;
            if (this.fields != null) {
                return;
            }
            if (!this.serializable || this.externalizable || this.forProxyClass || this.name.equals("java.lang.String")) {
                this.fields = NO_FIELDS;
            } else if (this.serializable) {
                AccessController.doPrivileged(new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        try {
                            Field pf = cl.getDeclaredField("serialPersistentFields");
                            pf.setAccessible(true);
                            pf.get(cl);
                            int mods = pf.getModifiers();
                            if (Modifier.isPrivate(mods) && Modifier.isStatic(mods) && Modifier.isFinal(mods)) {
                                ObjectStreamClass_1_3_1.access$002(ObjectStreamClass_1_3_1.this, (ObjectStreamField[])ObjectStreamClass_1_3_1.translateFields((Object[])pf.get(cl)));
                            }
                        }
                        catch (NoSuchFieldException e) {
                            ObjectStreamClass_1_3_1.access$002(ObjectStreamClass_1_3_1.this, null);
                        }
                        catch (IllegalAccessException e) {
                            ObjectStreamClass_1_3_1.access$002(ObjectStreamClass_1_3_1.this, null);
                        }
                        catch (IllegalArgumentException e) {
                            ObjectStreamClass_1_3_1.access$002(ObjectStreamClass_1_3_1.this, null);
                        }
                        catch (ClassCastException e) {
                            ObjectStreamClass_1_3_1.access$002(ObjectStreamClass_1_3_1.this, null);
                        }
                        if (ObjectStreamClass_1_3_1.this.fields == null) {
                            Field[] actualfields = cl.getDeclaredFields();
                            int numFields = 0;
                            ObjectStreamField[] tempFields = new ObjectStreamField[actualfields.length];
                            for (int i = 0; i < actualfields.length; ++i) {
                                int modifiers = actualfields[i].getModifiers();
                                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
                                tempFields[numFields++] = new ObjectStreamField(actualfields[i]);
                            }
                            ObjectStreamClass_1_3_1.access$002(ObjectStreamClass_1_3_1.this, new ObjectStreamField[numFields]);
                            System.arraycopy(tempFields, 0, ObjectStreamClass_1_3_1.this.fields, 0, numFields);
                        } else {
                            for (int j = ObjectStreamClass_1_3_1.this.fields.length - 1; j >= 0; --j) {
                                try {
                                    Field reflField = cl.getDeclaredField(ObjectStreamClass_1_3_1.this.fields[j].getName());
                                    if (ObjectStreamClass_1_3_1.this.fields[j].getType() != reflField.getType()) continue;
                                    ObjectStreamClass_1_3_1.this.fields[j].setField(reflField);
                                    continue;
                                }
                                catch (NoSuchFieldException noSuchFieldException) {
                                    // empty catch block
                                }
                            }
                        }
                        return null;
                    }
                });
                if (this.fields.length > 1) {
                    Arrays.sort(this.fields);
                }
                this.computeFieldInfo();
            }
            if (this.isNonSerializable()) {
                this.suid = 0L;
            } else {
                AccessController.doPrivileged(new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        int mods;
                        if (ObjectStreamClass_1_3_1.this.forProxyClass) {
                            ObjectStreamClass_1_3_1.this.suid = 0L;
                        } else {
                            try {
                                Field f = cl.getDeclaredField("serialVersionUID");
                                mods = f.getModifiers();
                                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)) {
                                    f.setAccessible(true);
                                    ObjectStreamClass_1_3_1.this.suid = f.getLong(cl);
                                } else {
                                    ObjectStreamClass_1_3_1.this.suid = ObjectStreamClass.getSerialVersionUID(cl);
                                }
                            }
                            catch (NoSuchFieldException ex) {
                                ObjectStreamClass_1_3_1.this.suid = ObjectStreamClass.getSerialVersionUID(cl);
                            }
                            catch (IllegalAccessException ex) {
                                ObjectStreamClass_1_3_1.this.suid = ObjectStreamClass.getSerialVersionUID(cl);
                            }
                        }
                        try {
                            ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod = cl.getDeclaredMethod("writeReplace", noTypesList);
                            if (Modifier.isStatic(ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod.getModifiers())) {
                                ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod = null;
                            } else {
                                ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod.setAccessible(true);
                            }
                        }
                        catch (NoSuchMethodException ex) {
                            // empty catch block
                        }
                        try {
                            ObjectStreamClass_1_3_1.this.readResolveObjectMethod = cl.getDeclaredMethod("readResolve", noTypesList);
                            if (Modifier.isStatic(ObjectStreamClass_1_3_1.this.readResolveObjectMethod.getModifiers())) {
                                ObjectStreamClass_1_3_1.this.readResolveObjectMethod = null;
                            } else {
                                ObjectStreamClass_1_3_1.this.readResolveObjectMethod.setAccessible(true);
                            }
                        }
                        catch (NoSuchMethodException ex) {
                            // empty catch block
                        }
                        if (ObjectStreamClass_1_3_1.this.serializable && !ObjectStreamClass_1_3_1.this.forProxyClass) {
                            Class[] args22;
                            try {
                                args22 = new Class[]{ObjectOutputStream.class};
                                ObjectStreamClass_1_3_1.this.writeObjectMethod = cl.getDeclaredMethod("writeObject", args22);
                                ObjectStreamClass_1_3_1.this.hasWriteObjectMethod = true;
                                mods = ObjectStreamClass_1_3_1.this.writeObjectMethod.getModifiers();
                                if (!Modifier.isPrivate(mods) || Modifier.isStatic(mods)) {
                                    ObjectStreamClass_1_3_1.this.writeObjectMethod = null;
                                    ObjectStreamClass_1_3_1.this.hasWriteObjectMethod = false;
                                }
                            }
                            catch (NoSuchMethodException args22) {
                                // empty catch block
                            }
                            try {
                                args22 = new Class[]{ObjectInputStream.class};
                                ObjectStreamClass_1_3_1.this.readObjectMethod = cl.getDeclaredMethod("readObject", args22);
                                mods = ObjectStreamClass_1_3_1.this.readObjectMethod.getModifiers();
                                if (!Modifier.isPrivate(mods) || Modifier.isStatic(mods)) {
                                    ObjectStreamClass_1_3_1.this.readObjectMethod = null;
                                }
                            }
                            catch (NoSuchMethodException noSuchMethodException) {
                                // empty catch block
                            }
                        }
                        return null;
                    }
                });
            }
            this.actualSuid = ObjectStreamClass_1_3_1.computeStructuralUID(this, cl);
        }
    }

    ObjectStreamClass_1_3_1(String n, long s2) {
        this.name = n;
        this.suid = s2;
        this.superclass = null;
    }

    private static Object[] translateFields(Object[] objs) throws NoSuchFieldException {
        try {
            java.io.ObjectStreamField[] fields = (java.io.ObjectStreamField[])objs;
            Object[] translation = null;
            if (translatedFields == null) {
                translatedFields = new Hashtable();
            }
            if ((translation = (Object[])translatedFields.get(fields)) != null) {
                return translation;
            }
            Class<ObjectStreamField> osfClass = ObjectStreamField.class;
            translation = (Object[])Array.newInstance(osfClass, objs.length);
            Object[] arg = new Object[2];
            Class[] types = new Class[]{String.class, Class.class};
            Constructor constructor = osfClass.getDeclaredConstructor(types);
            for (int i = fields.length - 1; i >= 0; --i) {
                arg[0] = fields[i].getName();
                arg[1] = fields[i].getType();
                translation[i] = constructor.newInstance(arg);
            }
            translatedFields.put(fields, translation);
            return translation;
        }
        catch (Throwable t2) {
            throw new NoSuchFieldException();
        }
    }

    static boolean compareClassNames(String streamName, String localName, char pkgSeparator) {
        int localNameIndex;
        int streamNameIndex = streamName.lastIndexOf(pkgSeparator);
        if (streamNameIndex < 0) {
            streamNameIndex = 0;
        }
        if ((localNameIndex = localName.lastIndexOf(pkgSeparator)) < 0) {
            localNameIndex = 0;
        }
        return streamName.regionMatches(false, streamNameIndex, localName, localNameIndex, streamName.length() - streamNameIndex);
    }

    final boolean typeEquals(ObjectStreamClass_1_3_1 other) {
        return this.suid == other.suid && ObjectStreamClass_1_3_1.compareClassNames(this.name, other.name, '.');
    }

    final void setSuperclass(ObjectStreamClass_1_3_1 s2) {
        this.superclass = s2;
    }

    final ObjectStreamClass_1_3_1 getSuperclass() {
        return this.superclass;
    }

    final boolean hasWriteObject() {
        return this.hasWriteObjectMethod;
    }

    final boolean isCustomMarshaled() {
        return this.hasWriteObject() || this.isExternalizable();
    }

    boolean hasExternalizableBlockDataMode() {
        return this.hasExternalizableBlockData;
    }

    final ObjectStreamClass_1_3_1 localClassDescriptor() {
        return this.localClassDesc;
    }

    boolean isSerializable() {
        return this.serializable;
    }

    boolean isExternalizable() {
        return this.externalizable;
    }

    boolean isNonSerializable() {
        return !this.externalizable && !this.serializable;
    }

    private void computeFieldInfo() {
        this.primBytes = 0;
        this.objFields = 0;
        block7: for (int i = 0; i < this.fields.length; ++i) {
            switch (this.fields[i].getTypeCode()) {
                case 'B': 
                case 'Z': {
                    ++this.primBytes;
                    continue block7;
                }
                case 'C': 
                case 'S': {
                    this.primBytes += 2;
                    continue block7;
                }
                case 'F': 
                case 'I': {
                    this.primBytes += 4;
                    continue block7;
                }
                case 'D': 
                case 'J': {
                    this.primBytes += 8;
                    continue block7;
                }
                case 'L': 
                case '[': {
                    ++this.objFields;
                }
            }
        }
    }

    private static long computeStructuralUID(ObjectStreamClass_1_3_1 osc, Class<?> cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);
        long h2 = 0L;
        try {
            int i;
            if (!Serializable.class.isAssignableFrom(cl) || cl.isInterface()) {
                return 0L;
            }
            if (Externalizable.class.isAssignableFrom(cl)) {
                return 1L;
            }
            MessageDigest md = MessageDigest.getInstance("SHA");
            DigestOutputStream mdo = new DigestOutputStream(devnull, md);
            DataOutputStream data = new DataOutputStream(mdo);
            Class<?> parent = cl.getSuperclass();
            if (parent != null) {
                data.writeLong(ObjectStreamClass_1_3_1.computeStructuralUID(ObjectStreamClass_1_3_1.lookup(parent), parent));
            }
            if (osc.hasWriteObject()) {
                data.writeInt(2);
            } else {
                data.writeInt(1);
            }
            ObjectStreamField[] fields = osc.getFields();
            int numNonNullFields = 0;
            for (int i2 = 0; i2 < fields.length; ++i2) {
                if (fields[i2].getField() == null) continue;
                ++numNonNullFields;
            }
            Field[] field2 = new Field[numNonNullFields];
            int fieldNum = 0;
            for (i = 0; i < fields.length; ++i) {
                if (fields[i].getField() == null) continue;
                field2[fieldNum++] = fields[i].getField();
            }
            if (field2.length > 1) {
                Arrays.sort(field2, compareMemberByName);
            }
            for (i = 0; i < field2.length; ++i) {
                Field f = field2[i];
                data.writeUTF(f.getName());
                data.writeUTF(ObjectStreamClass_1_3_1.getSignature(f.getType()));
            }
            data.flush();
            byte[] hasharray = md.digest();
            for (int i3 = 0; i3 < Math.min(8, hasharray.length); ++i3) {
                h2 += (long)(hasharray[i3] & 0xFF) << i3 * 8;
            }
        }
        catch (IOException ignore) {
            h2 = -1L;
        }
        catch (NoSuchAlgorithmException complain) {
            throw new SecurityException(complain.getMessage());
        }
        return h2;
    }

    static String getSignature(Class<?> clazz) {
        String type = null;
        if (clazz.isArray()) {
            Class<?> cl = clazz;
            int dimensions = 0;
            while (cl.isArray()) {
                ++dimensions;
                cl = cl.getComponentType();
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < dimensions; ++i) {
                sb.append("[");
            }
            sb.append(ObjectStreamClass_1_3_1.getSignature(cl));
            type = sb.toString();
        } else if (clazz.isPrimitive()) {
            if (clazz == Integer.TYPE) {
                type = "I";
            } else if (clazz == Byte.TYPE) {
                type = "B";
            } else if (clazz == Long.TYPE) {
                type = "J";
            } else if (clazz == Float.TYPE) {
                type = "F";
            } else if (clazz == Double.TYPE) {
                type = "D";
            } else if (clazz == Short.TYPE) {
                type = "S";
            } else if (clazz == Character.TYPE) {
                type = "C";
            } else if (clazz == Boolean.TYPE) {
                type = "Z";
            } else if (clazz == Void.TYPE) {
                type = "V";
            }
        } else {
            type = "L" + clazz.getName().replace('.', '/') + ";";
        }
        return type;
    }

    static String getSignature(Method meth) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Class<?>[] params = meth.getParameterTypes();
        for (int j = 0; j < params.length; ++j) {
            sb.append(ObjectStreamClass_1_3_1.getSignature(params[j]));
        }
        sb.append(")");
        sb.append(ObjectStreamClass_1_3_1.getSignature(meth.getReturnType()));
        return sb.toString();
    }

    static String getSignature(Constructor<?> cons) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Class<?>[] params = cons.getParameterTypes();
        for (int j = 0; j < params.length; ++j) {
            sb.append(ObjectStreamClass_1_3_1.getSignature(params[j]));
        }
        sb.append(")V");
        return sb.toString();
    }

    private static ObjectStreamClass_1_3_1 findDescriptorFor(Class<?> cl) {
        ObjectStreamClassEntry e;
        int hash = cl.hashCode();
        int index = (hash & Integer.MAX_VALUE) % descriptorFor.length;
        while ((e = descriptorFor[index]) != null && e.get() == null) {
            ObjectStreamClass_1_3_1.descriptorFor[index] = e.next;
        }
        ObjectStreamClassEntry prev = e;
        while (e != null) {
            ObjectStreamClass_1_3_1 desc = (ObjectStreamClass_1_3_1)e.get();
            if (desc == null) {
                prev.next = e.next;
            } else {
                if (desc.ofClass == cl) {
                    return desc;
                }
                prev = e;
            }
            e = e.next;
        }
        return null;
    }

    private static void insertDescriptorFor(ObjectStreamClass_1_3_1 desc) {
        if (ObjectStreamClass_1_3_1.findDescriptorFor(desc.ofClass) != null) {
            return;
        }
        int hash = desc.ofClass.hashCode();
        int index = (hash & Integer.MAX_VALUE) % descriptorFor.length;
        ObjectStreamClassEntry e = new ObjectStreamClassEntry(desc);
        e.next = descriptorFor[index];
        ObjectStreamClass_1_3_1.descriptorFor[index] = e;
    }

    static /* synthetic */ ObjectStreamField[] access$002(ObjectStreamClass_1_3_1 x0, ObjectStreamField[] x1) {
        x0.fields = x1;
        return x1;
    }

    static {
        descriptorFor = new ObjectStreamClassEntry[61];
        classSerializable = null;
        classExternalizable = null;
        try {
            classSerializable = Class.forName("java.io.Serializable");
            classExternalizable = Class.forName("java.io.Externalizable");
        }
        catch (Throwable e) {
            System.err.println("Could not load java.io.Serializable or java.io.Externalizable.");
        }
        NO_FIELDS = new ObjectStreamField[0];
        compareMemberByName = new CompareMemberByName();
    }

    private static class CompareMemberByName
    implements Comparator<Member> {
        private CompareMemberByName() {
        }

        @Override
        public int compare(Member o1, Member o2) {
            String s1 = o1.getName();
            String s2 = o2.getName();
            if (o1 instanceof Method) {
                s1 = s1 + ObjectStreamClass_1_3_1.getSignature((Method)o1);
                s2 = s2 + ObjectStreamClass_1_3_1.getSignature((Method)o2);
            } else if (o1 instanceof Constructor) {
                s1 = s1 + ObjectStreamClass_1_3_1.getSignature((Constructor)o1);
                s2 = s2 + ObjectStreamClass_1_3_1.getSignature((Constructor)o2);
            }
            return s1.compareTo(s2);
        }
    }

    private static class ObjectStreamClassEntry {
        ObjectStreamClassEntry next;
        private ObjectStreamClass_1_3_1 c;

        ObjectStreamClassEntry(ObjectStreamClass_1_3_1 c) {
            this.c = c;
        }

        public Object get() {
            return this.c;
        }
    }
}

