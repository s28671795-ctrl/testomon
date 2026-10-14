/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.util.Hashtable;
import javax.rmi.CORBA.ClassDesc;
import javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.TypeMismatchException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBClassLoader;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.ObjectStreamClassUtil_1_3;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdCache_1_3;
import org.jboss.com.sun.corba.se.impl.util.IdentityHashtable;
import org.jboss.com.sun.corba.se.impl.util.JDKBridge;
import org.jboss.com.sun.corba.se.impl.util.PackagePrefixChecker;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.ValueBase;

public class RepositoryId_1_3 {
    public static final RepositoryIdCache_1_3 cache = new RepositoryIdCache_1_3();
    public static final byte[] IDL_IDENTIFIER_CHARS = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1};
    private static String defaultServerURL = null;
    private static IdentityHashtable classToRepStr;
    private static IdentityHashtable classIDLToRepStr;
    private static IdentityHashtable classSeqToRepStr;
    private static IdentityHashtable repStrToByteArray;
    private static Hashtable<String, Class<?>> repStrToClass;
    private String repId = null;
    private boolean isSupportedFormat = true;
    private String typeString = null;
    private String versionString = null;
    private boolean isSequence = false;
    private boolean isRMIValueType = false;
    private boolean isIDLType = false;
    private String completeClassName = null;
    private String unqualifiedName = null;
    private String definedInId = null;
    private Class<?> clazz = null;
    private String suid = null;
    private String actualSuid = null;
    private long suidLong = -1L;
    private long actualSuidLong = -1L;
    private static final String kValuePrefix = "RMI:";
    private static final String kIDLPrefix = "IDL:";
    private static final String kIDLNamePrefix = "omg.org/";
    private static final String kIDLClassnamePrefix = "org.omg.";
    private static final String kSequencePrefix = "[";
    private static final String kCORBAPrefix = "CORBA/";
    private static final int kValuePrefixLength;
    private static final int kIDLPrefixLength;
    private static final String kInterfaceHashCode = ":0000000000000000";
    private static final String kInterfaceOnlyHashStr = "0000000000000000";
    private static final String kExternalizableHashStr = "0000000000000001";
    public static final int kInitialValueTag = 0x7FFFFF00;
    public static final int kNoTypeInfo = 0;
    public static final int kSingleRepTypeInfo = 2;
    public static final int kPartialListTypeInfo = 6;
    public static final int kChunkedMask = 8;
    public static final String kWStringValueVersion = "1.0";
    public static final String kWStringValueHash = ":1.0";
    public static final String kWStringStubValue = "WStringValue";
    public static final String kWStringTypeStr = "omg.org/CORBA/WStringValue";
    public static final String kWStringValueRepID = "IDL:omg.org/CORBA/WStringValue:1.0";
    public static final String kAnyRepID = "IDL:omg.org/CORBA/Any";
    public static final String kClassDescValueHash;
    public static final String kClassDescStubValue = "ClassDesc";
    public static final String kClassDescTypeStr = "javax.rmi.CORBA.ClassDesc";
    public static final String kClassDescValueRepID;
    public static final String kObjectValueHash = ":1.0";
    public static final String kObjectStubValue = "Object";
    public static final String kSequenceValueHash = ":1.0";
    public static final String kPrimitiveSequenceValueHash = ":0000000000000000";
    public static final String kSerializableValueHash = ":1.0";
    public static final String kSerializableStubValue = "Serializable";
    public static final String kExternalizableValueHash = ":1.0";
    public static final String kExternalizableStubValue = "Externalizable";
    public static final String kRemoteValueHash = "";
    public static final String kRemoteStubValue = "";
    public static final String kRemoteTypeStr = "";
    public static final String kRemoteValueRepID = "";
    public static final Hashtable<String, StringBuffer> kSpecialArrayTypeStrings;
    public static final Hashtable<Class<?>, String> kSpecialCasesRepIDs;
    public static final Hashtable<Class<?>, String> kSpecialCasesStubValues;
    public static final Hashtable<Class<?>, String> kSpecialCasesVersions;
    public static final Hashtable<String, Class<?>> kSpecialCasesClasses;
    public static final Hashtable<Class<?>, String> kSpecialCasesArrayPrefix;
    public static final Hashtable<String, String> kSpecialPrimitives;
    private static final byte[] ASCII_HEX;
    public static final String kjava_rmi_Remote;
    public static final String korg_omg_CORBA_Object;
    public static final Class<?>[] kNoParamTypes;
    public static final java.lang.Object[] kNoArgs;

    RepositoryId_1_3() {
    }

    RepositoryId_1_3(String aRepId) {
        this.init(aRepId);
    }

    RepositoryId_1_3 init(String aRepId) {
        this.repId = aRepId;
        if (aRepId.length() == 0) {
            this.clazz = Remote.class;
            this.typeString = "";
            this.isRMIValueType = true;
            this.suid = kInterfaceOnlyHashStr;
            return this;
        }
        if (aRepId.equals(kWStringValueRepID)) {
            this.clazz = String.class;
            this.typeString = kWStringTypeStr;
            this.isIDLType = true;
            this.versionString = kWStringValueVersion;
            return this;
        }
        String repId = RepositoryId_1_3.convertFromISOLatin1(aRepId);
        this.versionString = repId.substring(repId.indexOf(58, repId.indexOf(58) + 1));
        if (repId.startsWith(kIDLPrefix)) {
            this.typeString = repId.substring(kIDLPrefixLength, repId.indexOf(58, kIDLPrefixLength));
            this.isIDLType = true;
            this.completeClassName = this.typeString.startsWith(kIDLNamePrefix) ? kIDLClassnamePrefix + this.typeString.substring(kIDLNamePrefix.length()).replace('/', '.') : this.typeString.replace('/', '.');
        } else if (repId.startsWith(kValuePrefix)) {
            this.typeString = repId.substring(kValuePrefixLength, repId.indexOf(58, kValuePrefixLength));
            this.isRMIValueType = true;
            if (this.versionString.indexOf(46) == -1) {
                this.suid = this.actualSuid = this.versionString.substring(1);
                if (this.actualSuid.indexOf(58) != -1) {
                    int pos = this.actualSuid.indexOf(58) + 1;
                    this.suid = this.actualSuid.substring(pos);
                    this.actualSuid = this.actualSuid.substring(0, pos - 1);
                }
            }
        } else {
            this.isSupportedFormat = false;
        }
        if (this.typeString.startsWith(kSequencePrefix)) {
            this.isSequence = true;
        }
        return this;
    }

    public final String getUnqualifiedName() {
        if (this.unqualifiedName == null) {
            int index;
            String className = this.getClassName();
            int n = index = className != null ? className.lastIndexOf(46) : -1;
            if (index == -1) {
                this.unqualifiedName = className;
                this.definedInId = "IDL::1.0";
            } else {
                this.unqualifiedName = className.substring(index);
                this.definedInId = kIDLPrefix + className.substring(0, index).replace('.', '/') + ":1.0";
            }
        }
        return this.unqualifiedName;
    }

    public final String getDefinedInId() {
        if (this.definedInId == null) {
            this.getUnqualifiedName();
        }
        return this.definedInId;
    }

    public final String getTypeString() {
        return this.typeString;
    }

    public final String getVersionString() {
        return this.versionString;
    }

    public final String getSerialVersionUID() {
        return this.suid;
    }

    public final String getActualSerialVersionUID() {
        return this.actualSuid;
    }

    public final long getSerialVersionUIDAsLong() {
        return this.suidLong;
    }

    public final long getActualSerialVersionUIDAsLong() {
        return this.actualSuidLong;
    }

    public final boolean isRMIValueType() {
        return this.isRMIValueType;
    }

    public final boolean isIDLType() {
        return this.isIDLType;
    }

    public final String getRepositoryId() {
        return this.repId;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getByteArray(String repStr) {
        IdentityHashtable identityHashtable = repStrToByteArray;
        synchronized (identityHashtable) {
            return (byte[])repStrToByteArray.get(repStr);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setByteArray(String repStr, byte[] repStrBytes) {
        IdentityHashtable identityHashtable = repStrToByteArray;
        synchronized (identityHashtable) {
            repStrToByteArray.put(repStr, repStrBytes);
        }
    }

    public final boolean isSequence() {
        return this.isSequence;
    }

    public final boolean isSupportedFormat() {
        return this.isSupportedFormat;
    }

    public final String getClassName() {
        if (this.isRMIValueType) {
            return this.typeString;
        }
        if (this.isIDLType) {
            return this.completeClassName;
        }
        return null;
    }

    public final Class<?> getAnyClassFromType() throws ClassNotFoundException {
        try {
            return this.getClassFromType();
        }
        catch (ClassNotFoundException cnfe) {
            Class<?> clz = repStrToClass.get(this.repId);
            if (clz != null) {
                return clz;
            }
            throw cnfe;
        }
    }

    public final Class<?> getClassFromType() throws ClassNotFoundException {
        if (this.clazz != null) {
            return this.clazz;
        }
        Class<?> specialCase = kSpecialCasesClasses.get(this.getClassName());
        if (specialCase != null) {
            this.clazz = specialCase;
            return specialCase;
        }
        try {
            return Util.loadClass(this.getClassName(), null, null);
        }
        catch (ClassNotFoundException cnfe) {
            if (defaultServerURL != null) {
                try {
                    return this.getClassFromType(defaultServerURL);
                }
                catch (MalformedURLException mue) {
                    throw cnfe;
                }
            }
            throw cnfe;
        }
    }

    public final Class<?> getClassFromType(Class<?> expectedType, String codebase) throws ClassNotFoundException {
        if (this.clazz != null) {
            return this.clazz;
        }
        Class<?> specialCase = kSpecialCasesClasses.get(this.getClassName());
        if (specialCase != null) {
            this.clazz = specialCase;
            return specialCase;
        }
        ClassLoader expectedTypeClassLoader = expectedType == null ? null : expectedType.getClassLoader();
        return this.loadClassOfType(this.getClassName(), codebase, expectedTypeClassLoader, expectedType, expectedTypeClassLoader);
    }

    public final Class<?> getClassFromType(String url2) throws ClassNotFoundException, MalformedURLException {
        return Util.loadClass(this.getClassName(), url2, null);
    }

    public final String toString() {
        return this.repId;
    }

    private static String createHashString(Class<?> clazz) {
        if (clazz.isInterface() || !Serializable.class.isAssignableFrom(clazz)) {
            return ":0000000000000000";
        }
        long actualLong = ObjectStreamClassUtil_1_3.computeStructuralUID(false, clazz);
        String hash = null;
        hash = actualLong == 0L ? kInterfaceOnlyHashStr : (actualLong == 1L ? kExternalizableHashStr : Long.toHexString(actualLong).toUpperCase());
        while (hash.length() < 16) {
            hash = "0" + hash;
        }
        long declaredLong = ObjectStreamClassUtil_1_3.computeSerialVersionUID(clazz);
        String declared = null;
        declared = declaredLong == 0L ? kInterfaceOnlyHashStr : (declaredLong == 1L ? kExternalizableHashStr : Long.toHexString(declaredLong).toUpperCase());
        while (declared.length() < 16) {
            declared = "0" + declared;
        }
        hash = hash + ":" + declared;
        return ":" + hash;
    }

    public static String createSequenceRepID(java.lang.Object ser) {
        return RepositoryId_1_3.createSequenceRepID(ser.getClass());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String createSequenceRepID(Class<?> clazz) {
        IdentityHashtable identityHashtable = classSeqToRepStr;
        synchronized (identityHashtable) {
            String repid = (String)classSeqToRepStr.get(clazz);
            if (repid != null) {
                return repid;
            }
            Class<?> originalClazz = clazz;
            Class<?> type = null;
            int numOfDims = 0;
            while ((type = clazz.getComponentType()) != null) {
                ++numOfDims;
                clazz = type;
            }
            if (clazz.isPrimitive()) {
                repid = kValuePrefix + originalClazz.getName() + ":0000000000000000";
            } else {
                StringBuffer buf = new StringBuffer();
                buf.append(kValuePrefix);
                while (numOfDims-- > 0) {
                    buf.append(kSequencePrefix);
                }
                buf.append("L");
                buf.append(RepositoryId_1_3.convertToISOLatin1(clazz.getName()));
                buf.append(";");
                buf.append(RepositoryId_1_3.createHashString(clazz));
                repid = buf.toString();
            }
            classSeqToRepStr.put(originalClazz, repid);
            return repid;
        }
    }

    public static String createForSpecialCase(Class<?> clazz) {
        if (clazz.isArray()) {
            return RepositoryId_1_3.createSequenceRepID(clazz);
        }
        return kSpecialCasesRepIDs.get(clazz);
    }

    public static String createForSpecialCase(Serializable ser) {
        Class<?> clazz = ser.getClass();
        if (clazz.isArray()) {
            return RepositoryId_1_3.createSequenceRepID(ser);
        }
        return RepositoryId_1_3.createForSpecialCase(clazz);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String createForJavaType(Serializable ser) throws TypeMismatchException {
        IdentityHashtable identityHashtable = classToRepStr;
        synchronized (identityHashtable) {
            String repid = RepositoryId_1_3.createForSpecialCase(ser);
            if (repid != null) {
                return repid;
            }
            Class<?> clazz = ser.getClass();
            repid = (String)classToRepStr.get(clazz);
            if (repid != null) {
                return repid;
            }
            repid = kValuePrefix + RepositoryId_1_3.convertToISOLatin1(clazz.getName()) + RepositoryId_1_3.createHashString(clazz);
            classToRepStr.put(clazz, repid);
            repStrToClass.put(repid, clazz);
            return repid;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String createForJavaType(Class<?> clz) throws TypeMismatchException {
        IdentityHashtable identityHashtable = classToRepStr;
        synchronized (identityHashtable) {
            String repid = RepositoryId_1_3.createForSpecialCase(clz);
            if (repid != null) {
                return repid;
            }
            repid = (String)classToRepStr.get(clz);
            if (repid != null) {
                return repid;
            }
            repid = kValuePrefix + RepositoryId_1_3.convertToISOLatin1(clz.getName()) + RepositoryId_1_3.createHashString(clz);
            classToRepStr.put(clz, repid);
            repStrToClass.put(repid, clz);
            return repid;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String createForIDLType(Class<?> ser, int major, int minor) throws TypeMismatchException {
        IdentityHashtable identityHashtable = classIDLToRepStr;
        synchronized (identityHashtable) {
            String repid = (String)classIDLToRepStr.get(ser);
            if (repid != null) {
                return repid;
            }
            repid = kIDLPrefix + RepositoryId_1_3.convertToISOLatin1(ser.getName()).replace('.', '/') + ":" + major + "." + minor;
            classIDLToRepStr.put(ser, repid);
            return repid;
        }
    }

    private static String getIdFromHelper(Class<?> clazz) {
        try {
            Class<?> helperClazz = Utility.loadClassForClass(clazz.getName() + "Helper", null, clazz.getClassLoader(), clazz, clazz.getClassLoader());
            Method idMethod = helperClazz.getDeclaredMethod("id", kNoParamTypes);
            return (String)idMethod.invoke(null, kNoArgs);
        }
        catch (ClassNotFoundException cnfe) {
            throw new MARSHAL(cnfe.toString());
        }
        catch (NoSuchMethodException nsme) {
            throw new MARSHAL(nsme.toString());
        }
        catch (InvocationTargetException ite) {
            throw new MARSHAL(ite.toString());
        }
        catch (IllegalAccessException iae) {
            throw new MARSHAL(iae.toString());
        }
    }

    public static String createForAnyType(Class<?> type) {
        try {
            if (type.isArray()) {
                return RepositoryId_1_3.createSequenceRepID(type);
            }
            if (IDLEntity.class.isAssignableFrom(type)) {
                try {
                    return RepositoryId_1_3.getIdFromHelper(type);
                }
                catch (Throwable t2) {
                    return RepositoryId_1_3.createForIDLType(type, 1, 0);
                }
            }
            return RepositoryId_1_3.createForJavaType(type);
        }
        catch (TypeMismatchException e) {
            return null;
        }
    }

    public static boolean isAbstractBase(Class<?> clazz) {
        return clazz.isInterface() && IDLEntity.class.isAssignableFrom(clazz) && !ValueBase.class.isAssignableFrom(clazz) && !Object.class.isAssignableFrom(clazz);
    }

    private static String convertToISOLatin1(String name) {
        int length = name.length();
        if (length == 0) {
            return name;
        }
        StringBuffer buffer = null;
        for (int i = 0; i < length; ++i) {
            char c = name.charAt(i);
            if (c > '\u00ff' || IDL_IDENTIFIER_CHARS[c] == 0) {
                if (buffer == null) {
                    buffer = new StringBuffer(name.substring(0, i));
                }
                buffer.append("\\U" + (char)ASCII_HEX[(c & 0xF000) >>> 12] + (char)ASCII_HEX[(c & 0xF00) >>> 8] + (char)ASCII_HEX[(c & 0xF0) >>> 4] + (char)ASCII_HEX[c & 0xF]);
                continue;
            }
            if (buffer == null) continue;
            buffer.append(c);
        }
        if (buffer != null) {
            name = buffer.toString();
        }
        return name;
    }

    private static String convertFromISOLatin1(String name) {
        int index = -1;
        StringBuffer buf = new StringBuffer(name);
        while ((index = buf.toString().indexOf("\\U")) != -1) {
            String str = "0000" + buf.toString().substring(index + 2, index + 6);
            byte[] buffer = new byte[(str.length() - 4) / 2];
            int j = 0;
            for (int i = 4; i < str.length(); i += 2) {
                buffer[j] = (byte)(ORBUtility.hexOf(str.charAt(i)) << 4 & 0xF0);
                int n = j++;
                buffer[n] = (byte)(buffer[n] | (byte)(ORBUtility.hexOf(str.charAt(i + 1)) << 0 & 0xF));
            }
            buf = new StringBuffer(RepositoryId_1_3.delete(buf.toString(), index, index + 6));
            buf.insert(index, (char)buffer[1]);
        }
        return buf.toString();
    }

    private static String delete(String str, int from, int to) {
        return str.substring(0, from) + str.substring(to, str.length());
    }

    private Class<?> loadClassOfType(String className, String remoteCodebase, ClassLoader loader, Class<?> expectedType, ClassLoader expectedTypeClassLoader) throws ClassNotFoundException {
        Class<?> loadedClass;
        block7: {
            loadedClass = null;
            try {
                try {
                    loadedClass = !PackagePrefixChecker.hasOffendingPrefix(PackagePrefixChecker.withoutPackagePrefix(className)) ? Util.loadClass(PackagePrefixChecker.withoutPackagePrefix(className), remoteCodebase, loader) : Util.loadClass(className, remoteCodebase, loader);
                }
                catch (ClassNotFoundException cnfe) {
                    loadedClass = Util.loadClass(className, remoteCodebase, loader);
                }
                if (expectedType == null) {
                    return loadedClass;
                }
            }
            catch (ClassNotFoundException cnfe) {
                if (expectedType != null) break block7;
                throw cnfe;
            }
        }
        if (loadedClass == null || !expectedType.isAssignableFrom(loadedClass)) {
            if (expectedType.getClassLoader() != expectedTypeClassLoader) {
                throw new IllegalArgumentException("expectedTypeClassLoader not class loader of expectedType.");
            }
            loadedClass = expectedTypeClassLoader != null ? expectedTypeClassLoader.loadClass(className) : ORBClassLoader.loadClass(className);
        }
        return loadedClass;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean useFullValueDescription(Class<?> clazz, String repositoryID) throws IOException {
        RepositoryId_1_3 clazzRepid;
        RepositoryId_1_3 targetRepid;
        String clazzRepIDStr = RepositoryId_1_3.createForAnyType(clazz);
        if (clazzRepIDStr.equals(repositoryID)) {
            return false;
        }
        RepositoryIdCache_1_3 repositoryIdCache_1_3 = cache;
        synchronized (repositoryIdCache_1_3) {
            targetRepid = cache.getId(repositoryID);
            clazzRepid = cache.getId(clazzRepIDStr);
        }
        if (targetRepid.isRMIValueType() && clazzRepid.isRMIValueType()) {
            if (!targetRepid.getSerialVersionUID().equals(clazzRepid.getSerialVersionUID())) {
                String mssg = "Mismatched serialization UIDs : Source (Rep. ID" + clazzRepid + ") = " + clazzRepid.getSerialVersionUID() + " whereas Target (Rep. ID " + repositoryID + ") = " + targetRepid.getSerialVersionUID();
                throw new IOException(mssg);
            }
            return true;
        }
        throw new IOException("The repository ID is not of an RMI value type (Expected ID = " + clazzRepIDStr + "; Received ID = " + repositoryID + ")");
    }

    static {
        if (defaultServerURL == null) {
            defaultServerURL = JDKBridge.getLocalCodebase();
        }
        classToRepStr = new IdentityHashtable();
        classIDLToRepStr = new IdentityHashtable();
        classSeqToRepStr = new IdentityHashtable();
        repStrToByteArray = new IdentityHashtable();
        repStrToClass = new Hashtable();
        kValuePrefixLength = kValuePrefix.length();
        kIDLPrefixLength = kIDLPrefix.length();
        kClassDescValueHash = ":" + Long.toHexString(ObjectStreamClass.getSerialVersionUID(ClassDesc.class));
        kClassDescValueRepID = "RMI:javax.rmi.CORBA.ClassDesc" + kClassDescValueHash;
        kSpecialArrayTypeStrings = new Hashtable();
        kSpecialArrayTypeStrings.put("CORBA.WStringValue", new StringBuffer(String.class.getName()));
        kSpecialArrayTypeStrings.put(kClassDescTypeStr, new StringBuffer(Class.class.getName()));
        kSpecialArrayTypeStrings.put("CORBA.Object", new StringBuffer(Remote.class.getName()));
        kSpecialCasesRepIDs = new Hashtable();
        kSpecialCasesRepIDs.put(String.class, kWStringValueRepID);
        kSpecialCasesRepIDs.put(Class.class, kClassDescValueRepID);
        kSpecialCasesRepIDs.put(Remote.class, "");
        kSpecialCasesStubValues = new Hashtable();
        kSpecialCasesStubValues.put(String.class, kWStringStubValue);
        kSpecialCasesStubValues.put(Class.class, kClassDescStubValue);
        kSpecialCasesStubValues.put(java.lang.Object.class, kObjectStubValue);
        kSpecialCasesStubValues.put(Serializable.class, kSerializableStubValue);
        kSpecialCasesStubValues.put(Externalizable.class, kExternalizableStubValue);
        kSpecialCasesStubValues.put(Remote.class, "");
        kSpecialCasesVersions = new Hashtable();
        kSpecialCasesVersions.put(String.class, ":1.0");
        kSpecialCasesVersions.put(Class.class, kClassDescValueHash);
        kSpecialCasesVersions.put(java.lang.Object.class, ":1.0");
        kSpecialCasesVersions.put(Serializable.class, ":1.0");
        kSpecialCasesVersions.put(Externalizable.class, ":1.0");
        kSpecialCasesVersions.put(Remote.class, "");
        kSpecialCasesClasses = new Hashtable();
        kSpecialCasesClasses.put(kWStringTypeStr, String.class);
        kSpecialCasesClasses.put(kClassDescTypeStr, Class.class);
        kSpecialCasesClasses.put("", Remote.class);
        kSpecialCasesClasses.put("org.omg.CORBA.WStringValue", String.class);
        kSpecialCasesClasses.put(kClassDescTypeStr, Class.class);
        kSpecialCasesArrayPrefix = new Hashtable();
        kSpecialCasesArrayPrefix.put(String.class, "RMI:[CORBA/");
        kSpecialCasesArrayPrefix.put(Class.class, "RMI:[javax/rmi/CORBA/");
        kSpecialCasesArrayPrefix.put(java.lang.Object.class, "RMI:[java/lang/");
        kSpecialCasesArrayPrefix.put(Serializable.class, "RMI:[java/io/");
        kSpecialCasesArrayPrefix.put(Externalizable.class, "RMI:[java/io/");
        kSpecialCasesArrayPrefix.put(Remote.class, "RMI:[CORBA/");
        kSpecialPrimitives = new Hashtable();
        kSpecialPrimitives.put("int", "long");
        kSpecialPrimitives.put("long", "longlong");
        kSpecialPrimitives.put("byte", "octet");
        ASCII_HEX = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
        kjava_rmi_Remote = RepositoryId_1_3.createForAnyType(Remote.class);
        korg_omg_CORBA_Object = RepositoryId_1_3.createForAnyType(Object.class);
        kNoParamTypes = new Class[0];
        kNoArgs = new java.lang.Object[0];
    }
}

