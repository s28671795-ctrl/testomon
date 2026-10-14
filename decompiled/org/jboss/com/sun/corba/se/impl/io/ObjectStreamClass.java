/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamField;
import org.jboss.com.sun.corba.se.impl.io.ValueUtility;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.sun.corba.Bridge;
import org.omg.CORBA.ValueMember;

public class ObjectStreamClass
implements Serializable {
    private static final boolean DEBUG_SVUID = false;
    public static final long kDefaultUID = -1L;
    private static Object[] noArgsList = new Object[0];
    private static Class<?>[] noTypesList = new Class[0];
    private static Hashtable<Object, Object> translatedFields;
    private static final Bridge bridge;
    public static final int CLASS_MASK = 1553;
    public static final int FIELD_MASK = 223;
    public static final int METHOD_MASK = 3391;
    private static ObjectStreamClassEntry[] descriptorFor;
    private String name;
    private ObjectStreamClass superclass;
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
    private boolean initialized = false;
    private Object lock = new Object();
    private boolean hasExternalizableBlockData;
    Method writeObjectMethod;
    Method readObjectMethod;
    private Method writeReplaceObjectMethod;
    private Method readResolveObjectMethod;
    private Constructor<?> cons;
    private String rmiiiopOptionalDataRepId = null;
    private ObjectStreamClass localClassDesc;
    private static Method hasStaticInitializerMethod;
    private static Class<?> classSerializable;
    private static Class<?> classExternalizable;
    private static final long serialVersionUID = -6120832682080437368L;
    public static final ObjectStreamField[] NO_FIELDS;
    private static Comparator<Class<?>> compareClassByName;
    private static final Comparator<ObjectStreamField> compareObjStrFieldsByName;
    private static Comparator<Member> compareMemberByName;

    static final ObjectStreamClass lookup(Class<?> cl) {
        ObjectStreamClass desc = ObjectStreamClass.lookupInternal(cl);
        if (desc.isSerializable() || desc.isExternalizable()) {
            return desc;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static ObjectStreamClass lookupInternal(Class<?> cl) {
        ObjectStreamClass desc = null;
        ObjectStreamClassEntry[] objectStreamClassEntryArray = descriptorFor;
        synchronized (descriptorFor) {
            desc = ObjectStreamClass.findDescriptorFor(cl);
            if (desc == null) {
                Class<?> superclass;
                boolean serializable = classSerializable.isAssignableFrom(cl);
                ObjectStreamClass superdesc = null;
                if (serializable && (superclass = cl.getSuperclass()) != null) {
                    superdesc = ObjectStreamClass.lookup(superclass);
                }
                boolean externalizable = false;
                if (serializable) {
                    boolean bl = externalizable = superdesc != null && superdesc.isExternalizable() || classExternalizable.isAssignableFrom(cl);
                    if (externalizable) {
                        serializable = false;
                    }
                }
                desc = new ObjectStreamClass(cl, superdesc, serializable, externalizable);
            }
            desc.init();
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return desc;
        }
    }

    public final String getName() {
        return this.name;
    }

    public static final long getSerialVersionUID(Class<?> clazz) {
        ObjectStreamClass theosc = ObjectStreamClass.lookup(clazz);
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
        ObjectStreamClass theosc = ObjectStreamClass.lookup(clazz);
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
        try {
            for (int i = 0; i < this.fields.length; ++i) {
                if (!this.fields[i].getName().equals(field2.name) || !this.fields[i].getSignature().equals(ValueUtility.getSignature(field2))) continue;
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
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
                throw new RuntimeException(t2);
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
                throw new RuntimeException(t2);
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

    private ObjectStreamClass(Class<?> cl, ObjectStreamClass superdesc, boolean serial, boolean extern) {
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
        ObjectStreamClass.insertDescriptorFor(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        Object object = this.lock;
        synchronized (object) {
            if (this.initialized) {
                return;
            }
            final Class<?> cl = this.ofClass;
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
                                ObjectStreamClass.access$002(ObjectStreamClass.this, (ObjectStreamField[])ObjectStreamClass.translateFields((Object[])pf.get(cl)));
                            }
                        }
                        catch (NoSuchFieldException e) {
                            ObjectStreamClass.access$002(ObjectStreamClass.this, null);
                        }
                        catch (IllegalAccessException e) {
                            ObjectStreamClass.access$002(ObjectStreamClass.this, null);
                        }
                        catch (IllegalArgumentException e) {
                            ObjectStreamClass.access$002(ObjectStreamClass.this, null);
                        }
                        catch (ClassCastException e) {
                            ObjectStreamClass.access$002(ObjectStreamClass.this, null);
                        }
                        if (ObjectStreamClass.this.fields == null) {
                            Field[] actualfields = cl.getDeclaredFields();
                            int numFields = 0;
                            ObjectStreamField[] tempFields = new ObjectStreamField[actualfields.length];
                            for (int i = 0; i < actualfields.length; ++i) {
                                Field fld = actualfields[i];
                                int modifiers = fld.getModifiers();
                                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
                                fld.setAccessible(true);
                                tempFields[numFields++] = new ObjectStreamField(fld);
                            }
                            ObjectStreamClass.access$002(ObjectStreamClass.this, new ObjectStreamField[numFields]);
                            System.arraycopy(tempFields, 0, ObjectStreamClass.this.fields, 0, numFields);
                        } else {
                            for (int j = ObjectStreamClass.this.fields.length - 1; j >= 0; --j) {
                                try {
                                    Field reflField = cl.getDeclaredField(ObjectStreamClass.this.fields[j].getName());
                                    if (ObjectStreamClass.this.fields[j].getType() != reflField.getType()) continue;
                                    reflField.setAccessible(true);
                                    ObjectStreamClass.this.fields[j].setField(reflField);
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
                        if (ObjectStreamClass.this.forProxyClass) {
                            ObjectStreamClass.this.suid = 0L;
                        } else {
                            try {
                                Field f = cl.getDeclaredField("serialVersionUID");
                                int mods = f.getModifiers();
                                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)) {
                                    f.setAccessible(true);
                                    ObjectStreamClass.this.suid = f.getLong(cl);
                                } else {
                                    ObjectStreamClass.this.suid = ObjectStreamClass._computeSerialVersionUID(cl);
                                }
                            }
                            catch (NoSuchFieldException ex) {
                                ObjectStreamClass.this.suid = ObjectStreamClass._computeSerialVersionUID(cl);
                            }
                            catch (IllegalAccessException ex) {
                                ObjectStreamClass.this.suid = ObjectStreamClass._computeSerialVersionUID(cl);
                            }
                        }
                        ObjectStreamClass.this.writeReplaceObjectMethod = ObjectStreamClass.getInheritableMethod(cl, "writeReplace", noTypesList, Object.class);
                        ObjectStreamClass.this.readResolveObjectMethod = ObjectStreamClass.getInheritableMethod(cl, "readResolve", noTypesList, Object.class);
                        if (ObjectStreamClass.this.externalizable) {
                            ObjectStreamClass.this.cons = ObjectStreamClass.getExternalizableConstructor(cl);
                        } else {
                            ObjectStreamClass.this.cons = ObjectStreamClass.getSerializableConstructor(cl);
                        }
                        if (ObjectStreamClass.this.serializable && !ObjectStreamClass.this.forProxyClass) {
                            ObjectStreamClass.this.writeObjectMethod = ObjectStreamClass.getPrivateMethod(cl, "writeObject", new Class[]{ObjectOutputStream.class}, Void.TYPE);
                            ObjectStreamClass.this.readObjectMethod = ObjectStreamClass.getPrivateMethod(cl, "readObject", new Class[]{ObjectInputStream.class}, Void.TYPE);
                        }
                        return null;
                    }
                });
            }
            this.actualSuid = ObjectStreamClass.computeStructuralUID(this, cl);
            if (this.hasWriteObject()) {
                this.rmiiiopOptionalDataRepId = this.computeRMIIIOPOptionalDataRepId();
            }
            this.initialized = true;
        }
    }

    private static Method getPrivateMethod(Class<?> cl, String name, Class<?>[] argTypes, Class<?> returnType) {
        try {
            Method meth = cl.getDeclaredMethod(name, argTypes);
            meth.setAccessible(true);
            int mods = meth.getModifiers();
            return meth.getReturnType() == returnType && (mods & 8) == 0 && (mods & 2) != 0 ? meth : null;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }

    private String computeRMIIIOPOptionalDataRepId() {
        StringBuffer sbuf = new StringBuffer("RMI:org.omg.custom.");
        sbuf.append(RepositoryId.convertToISOLatin1(this.getName()));
        sbuf.append(':');
        sbuf.append(this.getActualSerialVersionUIDStr());
        sbuf.append(':');
        sbuf.append(this.getSerialVersionUIDStr());
        return sbuf.toString();
    }

    public final String getRMIIIOPOptionalDataRepId() {
        return this.rmiiiopOptionalDataRepId;
    }

    ObjectStreamClass(String n, long s2) {
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
            Class<?> osfClass = Class.forName("org.jboss.com.sun.corba.se.impl.io.ObjectStreamField");
            translation = (Object[])Array.newInstance(osfClass, objs.length);
            Object[] arg = new Object[2];
            Class[] types = new Class[]{String.class, Class.class};
            Constructor<?> constructor = osfClass.getDeclaredConstructor(types);
            for (int i = fields.length - 1; i >= 0; --i) {
                arg[0] = fields[i].getName();
                arg[1] = fields[i].getType();
                translation[i] = constructor.newInstance(arg);
            }
            translatedFields.put(fields, translation);
            return translation;
        }
        catch (Throwable t2) {
            NoSuchFieldException nsfe = new NoSuchFieldException();
            nsfe.initCause(t2);
            throw nsfe;
        }
    }

    final void setClass(Class<?> cl) throws InvalidClassException {
        if (cl == null) {
            this.localClassDesc = null;
            this.ofClass = null;
            this.computeFieldInfo();
            return;
        }
        this.localClassDesc = ObjectStreamClass.lookupInternal(cl);
        if (this.localClassDesc == null) {
            throw new InvalidClassException(cl.getName(), "Local class not compatible");
        }
        if (this.suid != this.localClassDesc.suid) {
            boolean arraySUID;
            boolean addedSerialOrExtern = this.isNonSerializable() || this.localClassDesc.isNonSerializable();
            boolean bl = arraySUID = cl.isArray() && !cl.getName().equals(this.name);
            if (!arraySUID && !addedSerialOrExtern) {
                throw new InvalidClassException(cl.getName(), "Local class not compatible: stream classdesc serialVersionUID=" + this.suid + " local class serialVersionUID=" + this.localClassDesc.suid);
            }
        }
        if (!ObjectStreamClass.compareClassNames(this.name, cl.getName(), '.')) {
            throw new InvalidClassException(cl.getName(), "Incompatible local class name. Expected class name compatible with " + this.name);
        }
        if (this.serializable != this.localClassDesc.serializable || this.externalizable != this.localClassDesc.externalizable || !this.serializable && !this.externalizable) {
            throw new InvalidClassException(cl.getName(), "Serialization incompatible with Externalization");
        }
        ObjectStreamField[] destfield = this.localClassDesc.fields;
        ObjectStreamField[] srcfield = this.fields;
        int j = 0;
        block0: for (int i = 0; i < srcfield.length; ++i) {
            for (int k = j; k < destfield.length; ++k) {
                if (!srcfield[i].getName().equals(destfield[k].getName())) continue;
                if (srcfield[i].isPrimitive() && !srcfield[i].typeEquals(destfield[k])) {
                    throw new InvalidClassException(cl.getName(), "The type of field " + srcfield[i].getName() + " of class " + this.name + " is incompatible.");
                }
                j = k;
                srcfield[i].setField(destfield[j].getField());
                continue block0;
            }
        }
        this.computeFieldInfo();
        this.ofClass = cl;
        this.readObjectMethod = this.localClassDesc.readObjectMethod;
        this.readResolveObjectMethod = this.localClassDesc.readResolveObjectMethod;
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

    final boolean typeEquals(ObjectStreamClass other) {
        return this.suid == other.suid && ObjectStreamClass.compareClassNames(this.name, other.name, '.');
    }

    final void setSuperclass(ObjectStreamClass s2) {
        this.superclass = s2;
    }

    final ObjectStreamClass getSuperclass() {
        return this.superclass;
    }

    final boolean hasReadObject() {
        return this.readObjectMethod != null;
    }

    final boolean hasWriteObject() {
        return this.writeObjectMethod != null;
    }

    final boolean isCustomMarshaled() {
        return this.hasWriteObject() || this.isExternalizable() || this.superclass != null && this.superclass.isCustomMarshaled();
    }

    boolean hasExternalizableBlockDataMode() {
        return this.hasExternalizableBlockData;
    }

    Object newInstance() throws InstantiationException, InvocationTargetException, UnsupportedOperationException {
        if (this.cons != null) {
            try {
                return this.cons.newInstance(new Object[0]);
            }
            catch (IllegalAccessException ex) {
                InternalError ie = new InternalError();
                ie.initCause(ex);
                throw ie;
            }
        }
        throw new UnsupportedOperationException();
    }

    private static Constructor<?> getExternalizableConstructor(Class<?> cl) {
        try {
            Constructor<?> cons = cl.getDeclaredConstructor(new Class[0]);
            cons.setAccessible(true);
            return (cons.getModifiers() & 1) != 0 ? cons : null;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }

    private static Constructor<?> getSerializableConstructor(Class<?> cl) {
        Class<?> initCl = cl;
        while (Serializable.class.isAssignableFrom(initCl)) {
            if ((initCl = initCl.getSuperclass()) != null) continue;
            return null;
        }
        try {
            Constructor<?> cons = initCl.getDeclaredConstructor(new Class[0]);
            int mods = cons.getModifiers();
            if ((mods & 2) != 0 || (mods & 5) == 0 && !ObjectStreamClass.packageEquals(cl, initCl)) {
                return null;
            }
            cons = bridge.newConstructorForSerialization(cl, cons);
            cons.setAccessible(true);
            return cons;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }

    final ObjectStreamClass localClassDescriptor() {
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

    private static void msg(String str) {
        System.out.println(str);
    }

    private static long _computeSerialVersionUID(Class<?> cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);
        long h2 = 0L;
        try {
            int modifier;
            String desc;
            int i;
            MessageDigest md = MessageDigest.getInstance("SHA");
            DigestOutputStream mdo = new DigestOutputStream(devnull, md);
            DataOutputStream data = new DataOutputStream(mdo);
            data.writeUTF(cl.getName());
            int classaccess = cl.getModifiers();
            Member[] method2 = cl.getDeclaredMethods();
            if (((classaccess &= 0x611) & 0x200) != 0) {
                classaccess &= 0xFFFFFBFF;
                if (method2.length > 0) {
                    classaccess |= 0x400;
                }
            }
            data.writeInt(classaccess &= 0x611);
            if (!cl.isArray()) {
                Class<?>[] interfaces = cl.getInterfaces();
                Arrays.sort(interfaces, compareClassByName);
                for (i = 0; i < interfaces.length; ++i) {
                    data.writeUTF(interfaces[i].getName());
                }
            }
            Field[] field2 = cl.getDeclaredFields();
            Arrays.sort(field2, compareMemberByName);
            for (i = 0; i < field2.length; ++i) {
                Field f = field2[i];
                int m4 = f.getModifiers();
                if (Modifier.isPrivate(m4) && (Modifier.isTransient(m4) || Modifier.isStatic(m4))) continue;
                data.writeUTF(f.getName());
                data.writeInt(m4 &= 0xDF);
                data.writeUTF(ObjectStreamClass.getSignature(f.getType()));
            }
            if (ObjectStreamClass.hasStaticInitializer(cl)) {
                data.writeUTF("<clinit>");
                data.writeInt(8);
                data.writeUTF("()V");
            }
            MethodSignature[] constructors = MethodSignature.removePrivateAndSort(cl.getDeclaredConstructors());
            for (int i2 = 0; i2 < constructors.length; ++i2) {
                MethodSignature c = constructors[i2];
                String mname = "<init>";
                desc = c.signature;
                desc = desc.replace('/', '.');
                data.writeUTF(mname);
                modifier = c.member.getModifiers() & 0xD3F;
                data.writeInt(modifier);
                data.writeUTF(desc);
            }
            MethodSignature[] methods = MethodSignature.removePrivateAndSort(method2);
            for (int i3 = 0; i3 < methods.length; ++i3) {
                MethodSignature m5 = methods[i3];
                desc = m5.signature;
                desc = desc.replace('/', '.');
                data.writeUTF(m5.member.getName());
                modifier = m5.member.getModifiers() & 0xD3F;
                data.writeInt(modifier);
                data.writeUTF(desc);
            }
            data.flush();
            byte[] hasharray = md.digest();
            for (int i4 = 0; i4 < Math.min(8, hasharray.length); ++i4) {
                h2 += (long)(hasharray[i4] & 0xFF) << i4 * 8;
            }
        }
        catch (IOException ignore) {
            h2 = -1L;
        }
        catch (NoSuchAlgorithmException complain) {
            SecurityException se = new SecurityException();
            se.initCause(complain);
            throw se;
        }
        return h2;
    }

    private static long computeStructuralUID(ObjectStreamClass osc, Class<?> cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);
        long h2 = 0L;
        try {
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
                data.writeLong(ObjectStreamClass.computeStructuralUID(ObjectStreamClass.lookup(parent), parent));
            }
            if (osc.hasWriteObject()) {
                data.writeInt(2);
            } else {
                data.writeInt(1);
            }
            ObjectStreamField[] field2 = osc.getFields();
            if (field2.length > 1) {
                Arrays.sort(field2, compareObjStrFieldsByName);
            }
            for (int i = 0; i < field2.length; ++i) {
                data.writeUTF(field2[i].getName());
                data.writeUTF(field2[i].getSignature());
            }
            data.flush();
            byte[] hasharray = md.digest();
            for (int i = 0; i < Math.min(8, hasharray.length); ++i) {
                h2 += (long)(hasharray[i] & 0xFF) << i * 8;
            }
        }
        catch (IOException ignore) {
            h2 = -1L;
        }
        catch (NoSuchAlgorithmException complain) {
            SecurityException se = new SecurityException();
            se.initCause(complain);
            throw se;
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
            sb.append(ObjectStreamClass.getSignature(cl));
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
            sb.append(ObjectStreamClass.getSignature(params[j]));
        }
        sb.append(")");
        sb.append(ObjectStreamClass.getSignature(meth.getReturnType()));
        return sb.toString();
    }

    static String getSignature(Constructor<?> cons) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Class<?>[] params = cons.getParameterTypes();
        for (int j = 0; j < params.length; ++j) {
            sb.append(ObjectStreamClass.getSignature(params[j]));
        }
        sb.append(")V");
        return sb.toString();
    }

    private static ObjectStreamClass findDescriptorFor(Class<?> cl) {
        ObjectStreamClassEntry e;
        int hash = cl.hashCode();
        int index = (hash & Integer.MAX_VALUE) % descriptorFor.length;
        while ((e = descriptorFor[index]) != null && e.get() == null) {
            ObjectStreamClass.descriptorFor[index] = e.next;
        }
        ObjectStreamClassEntry prev = e;
        while (e != null) {
            ObjectStreamClass desc = (ObjectStreamClass)e.get();
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

    private static void insertDescriptorFor(ObjectStreamClass desc) {
        if (ObjectStreamClass.findDescriptorFor(desc.ofClass) != null) {
            return;
        }
        int hash = desc.ofClass.hashCode();
        int index = (hash & Integer.MAX_VALUE) % descriptorFor.length;
        ObjectStreamClassEntry e = new ObjectStreamClassEntry(desc);
        e.next = descriptorFor[index];
        ObjectStreamClass.descriptorFor[index] = e;
    }

    private static boolean hasStaticInitializer(Class<?> cl) {
        if (hasStaticInitializerMethod == null) {
            Class<Object> classWithThisMethod = null;
            try {
                try {
                    classWithThisMethod = Class.forName("sun.misc.ClassReflector");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
                if (classWithThisMethod == null) {
                    classWithThisMethod = java.io.ObjectStreamClass.class;
                }
                hasStaticInitializerMethod = classWithThisMethod.getDeclaredMethod("hasStaticInitializer", Class.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            if (hasStaticInitializerMethod == null) {
                throw new InternalError("Can't find hasStaticInitializer method on " + classWithThisMethod.getName());
            }
            hasStaticInitializerMethod.setAccessible(true);
        }
        try {
            Boolean retval = (Boolean)hasStaticInitializerMethod.invoke(null, cl);
            return retval;
        }
        catch (Exception ex) {
            InternalError ie = new InternalError("Error invoking hasStaticInitializer");
            ie.initCause(ex);
            throw ie;
        }
    }

    private static Method getInheritableMethod(Class<?> cl, String name, Class<?>[] argTypes, Class<?> returnType) {
        Class<?> defCl;
        Method meth = null;
        for (defCl = cl; defCl != null; defCl = defCl.getSuperclass()) {
            try {
                meth = defCl.getDeclaredMethod(name, argTypes);
                break;
            }
            catch (NoSuchMethodException ex) {
                continue;
            }
        }
        if (meth == null || meth.getReturnType() != returnType) {
            return null;
        }
        meth.setAccessible(true);
        int mods = meth.getModifiers();
        if ((mods & 0x408) != 0) {
            return null;
        }
        if ((mods & 5) != 0) {
            return meth;
        }
        if ((mods & 2) != 0) {
            return cl == defCl ? meth : null;
        }
        return ObjectStreamClass.packageEquals(cl, defCl) ? meth : null;
    }

    private static boolean packageEquals(Class<?> cl1, Class<?> cl2) {
        Package pkg2;
        Package pkg1 = cl1.getPackage();
        return pkg1 == (pkg2 = cl2.getPackage()) || pkg1 != null && pkg1.equals(pkg2);
    }

    static /* synthetic */ ObjectStreamField[] access$002(ObjectStreamClass x0, ObjectStreamField[] x1) {
        x0.fields = x1;
        return x1;
    }

    static {
        bridge = AccessController.doPrivileged(new PrivilegedAction<Bridge>(){

            @Override
            public Bridge run() {
                return Bridge.get();
            }
        });
        descriptorFor = new ObjectStreamClassEntry[61];
        hasStaticInitializerMethod = null;
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
        compareClassByName = new CompareClassByName();
        compareObjStrFieldsByName = new CompareObjStrFieldsByName();
        compareMemberByName = new CompareMemberByName();
    }

    private static class MethodSignature
    implements Comparator<MethodSignature> {
        Member member;
        String signature;

        static MethodSignature[] removePrivateAndSort(Member[] m4) {
            int numNonPrivate = 0;
            for (int i = 0; i < m4.length; ++i) {
                if (Modifier.isPrivate(m4[i].getModifiers())) continue;
                ++numNonPrivate;
            }
            MethodSignature[] cm = new MethodSignature[numNonPrivate];
            int cmi = 0;
            for (int i = 0; i < m4.length; ++i) {
                if (Modifier.isPrivate(m4[i].getModifiers())) continue;
                cm[cmi] = new MethodSignature(m4[i]);
                ++cmi;
            }
            if (cmi > 0) {
                Arrays.sort(cm, cm[0]);
            }
            return cm;
        }

        @Override
        public int compare(MethodSignature c1, MethodSignature c2) {
            int result;
            if (c1 == c2) {
                return 0;
            }
            if (this.isConstructor()) {
                result = c1.signature.compareTo(c2.signature);
            } else {
                result = c1.member.getName().compareTo(c2.member.getName());
                if (result == 0) {
                    result = c1.signature.compareTo(c2.signature);
                }
            }
            return result;
        }

        private final boolean isConstructor() {
            return this.member instanceof Constructor;
        }

        private MethodSignature(Member m4) {
            this.member = m4;
            this.signature = this.isConstructor() ? ObjectStreamClass.getSignature((Constructor)m4) : ObjectStreamClass.getSignature((Method)m4);
        }
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
                s1 = s1 + ObjectStreamClass.getSignature((Method)o1);
                s2 = s2 + ObjectStreamClass.getSignature((Method)o2);
            } else if (o1 instanceof Constructor) {
                s1 = s1 + ObjectStreamClass.getSignature((Constructor)o1);
                s2 = s2 + ObjectStreamClass.getSignature((Constructor)o2);
            }
            return s1.compareTo(s2);
        }
    }

    private static class CompareObjStrFieldsByName
    implements Comparator<ObjectStreamField> {
        private CompareObjStrFieldsByName() {
        }

        @Override
        public int compare(ObjectStreamField o1, ObjectStreamField o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    private static class CompareClassByName
    implements Comparator<Class<?>> {
        private CompareClassByName() {
        }

        @Override
        public int compare(Class<?> c1, Class<?> c2) {
            return c1.getName().compareTo(c2.getName());
        }
    }

    private static class ObjectStreamClassEntry {
        ObjectStreamClassEntry next;
        private ObjectStreamClass c;

        ObjectStreamClassEntry(ObjectStreamClass c) {
            this.c = c;
        }

        public Object get() {
            return this.c;
        }
    }
}

