/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.ValueHandler;
import javax.rmi.CORBA.ValueHandlerMultiFormat;
import org.jboss.com.sun.corba.se.impl.corba.CORBAObjectImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.JavaSerializationComponent;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBClassLoader;
import org.jboss.com.sun.corba.se.impl.orbutil.ValueHandlerImpl_1_3;
import org.jboss.com.sun.corba.se.impl.orbutil.ValueHandlerImpl_1_3_1;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaClientDelegate;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoList;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class ORBUtility {
    private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("util");
    private static OMGSystemException omgWrapper = OMGSystemException.get("util");
    private static StructMember[] members = null;
    private static final Hashtable<String, String> exceptionClassNames = new Hashtable();
    private static final Hashtable<String, String> exceptionRepositoryIds = new Hashtable();

    private ORBUtility() {
    }

    private static StructMember[] systemExceptionMembers(ORB orb) {
        if (members == null) {
            members = new StructMember[3];
            ORBUtility.members[0] = new StructMember("id", orb.create_string_tc(0), null);
            ORBUtility.members[1] = new StructMember("minor", orb.get_primitive_tc(TCKind.tk_long), null);
            ORBUtility.members[2] = new StructMember("completed", orb.get_primitive_tc(TCKind.tk_long), null);
        }
        return members;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static TypeCode getSystemExceptionTypeCode(ORB orb, String repID, String name) {
        Class<TypeCode> clazz = TypeCode.class;
        synchronized (TypeCode.class) {
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return orb.create_exception_tc(repID, name, ORBUtility.systemExceptionMembers(orb));
        }
    }

    private static boolean isSystemExceptionTypeCode(TypeCode type, ORB orb) {
        StructMember[] systemExceptionMembers = ORBUtility.systemExceptionMembers(orb);
        try {
            return type.kind().value() == 22 && type.member_count() == 3 && type.member_type(0).equal(systemExceptionMembers[0].type) && type.member_type(1).equal(systemExceptionMembers[1].type) && type.member_type(2).equal(systemExceptionMembers[2].type);
        }
        catch (BadKind ex) {
            return false;
        }
        catch (Bounds ex) {
            return false;
        }
    }

    public static void insertSystemException(SystemException ex, Any any) {
        OutputStream out = any.create_output_stream();
        ORB orb = (ORB)out.orb();
        String name = ex.getClass().getName();
        String repID = ORBUtility.repositoryIdOf(name);
        out.write_string(repID);
        out.write_long(ex.minor);
        out.write_long(ex.completed.value());
        any.read_value(out.create_input_stream(), ORBUtility.getSystemExceptionTypeCode(orb, repID, name));
    }

    public static SystemException extractSystemException(Any any) {
        InputStream in = any.create_input_stream();
        ORB orb = (ORB)in.orb();
        if (!ORBUtility.isSystemExceptionTypeCode(any.type(), orb)) {
            throw wrapper.unknownDsiSysex(CompletionStatus.COMPLETED_MAYBE);
        }
        return ORBUtility.readSystemException(in);
    }

    public static ValueHandler createValueHandler(ORB orb) {
        if (orb == null) {
            return Util.createValueHandler();
        }
        ORBVersion version = orb.getORBVersion();
        if (version == null) {
            return Util.createValueHandler();
        }
        if (version.equals(ORBVersionFactory.getOLD())) {
            return new ValueHandlerImpl_1_3();
        }
        if (version.equals(ORBVersionFactory.getNEW())) {
            return new ValueHandlerImpl_1_3_1();
        }
        return Util.createValueHandler();
    }

    public static boolean isLegacyORB(ORB orb) {
        try {
            ORBVersion currentORB = orb.getORBVersion();
            return currentORB.equals(ORBVersionFactory.getOLD());
        }
        catch (SecurityException se) {
            return false;
        }
    }

    public static boolean isForeignORB(ORB orb) {
        if (orb == null) {
            return false;
        }
        try {
            return orb.getORBVersion().equals(ORBVersionFactory.getFOREIGN());
        }
        catch (SecurityException se) {
            return false;
        }
    }

    public static int bytesToInt(byte[] array, int offset) {
        int b1 = array[offset++] << 24 & 0xFF000000;
        int b2 = array[offset++] << 16 & 0xFF0000;
        int b3 = array[offset++] << 8 & 0xFF00;
        int b4 = array[offset++] << 0 & 0xFF;
        return b1 | b2 | b3 | b4;
    }

    public static void intToBytes(int value, byte[] array, int offset) {
        array[offset++] = (byte)(value >>> 24 & 0xFF);
        array[offset++] = (byte)(value >>> 16 & 0xFF);
        array[offset++] = (byte)(value >>> 8 & 0xFF);
        array[offset++] = (byte)(value >>> 0 & 0xFF);
    }

    public static int hexOf(char x) {
        int val = x - 48;
        if (val >= 0 && val <= 9) {
            return val;
        }
        val = x - 97 + 10;
        if (val >= 10 && val <= 15) {
            return val;
        }
        val = x - 65 + 10;
        if (val >= 10 && val <= 15) {
            return val;
        }
        throw wrapper.badHexDigit();
    }

    public static void writeSystemException(SystemException ex, OutputStream strm) {
        String s2 = ORBUtility.repositoryIdOf(ex.getClass().getName());
        strm.write_string(s2);
        strm.write_long(ex.minor);
        strm.write_long(ex.completed.value());
    }

    public static SystemException readSystemException(InputStream strm) {
        try {
            String name = ORBUtility.classNameOf(strm.read_string());
            SystemException ex = (SystemException)ORBClassLoader.loadClass(name).newInstance();
            ex.minor = strm.read_long();
            ex.completed = CompletionStatus.from_int(strm.read_long());
            return ex;
        }
        catch (Exception ex) {
            throw wrapper.unknownSysex(CompletionStatus.COMPLETED_MAYBE, ex);
        }
    }

    public static String classNameOf(String repositoryId) {
        String className = null;
        className = exceptionClassNames.get(repositoryId);
        if (className == null) {
            className = "org.omg.CORBA.UNKNOWN";
        }
        return className;
    }

    public static boolean isSystemException(String repositoryId) {
        String className = null;
        className = exceptionClassNames.get(repositoryId);
        return className != null;
    }

    public static byte getEncodingVersion(ORB orb, IOR ior) {
        IIOPProfile prof;
        IIOPProfileTemplate profTemp;
        Iterator<java.lang.Object> iter;
        if (orb.getORBData().isJavaSerializationEnabled() && (iter = (profTemp = (IIOPProfileTemplate)(prof = ior.getProfile()).getTaggedProfileTemplate()).iteratorById(1398099458)).hasNext()) {
            JavaSerializationComponent jc = (JavaSerializationComponent)iter.next();
            byte jcVersion = jc.javaSerializationVersion();
            if (jcVersion >= 1) {
                return 1;
            }
            if (jcVersion > 0) {
                return jc.javaSerializationVersion();
            }
        }
        return 0;
    }

    public static String repositoryIdOf(String name) {
        String id = exceptionRepositoryIds.get(name);
        if (id == null) {
            id = "IDL:omg.org/CORBA/UNKNOWN:1.0";
        }
        return id;
    }

    public static int[] parseVersion(String version) {
        int end;
        int start2;
        if (version == null) {
            return new int[0];
        }
        char[] s2 = version.toCharArray();
        for (start2 = 0; start2 < s2.length && (s2[start2] < '0' || s2[start2] > '9'); ++start2) {
            if (start2 != s2.length) continue;
            return new int[0];
        }
        int size = 1;
        for (end = start2 + 1; end < s2.length; ++end) {
            if (s2[end] == '.') {
                ++size;
                continue;
            }
            if (s2[end] < '0' || s2[end] > '9') break;
        }
        int[] val = new int[size];
        for (int i = 0; i < size; ++i) {
            int dot = version.indexOf(46, start2);
            if (dot == -1 || dot > end) {
                dot = end;
            }
            val[i] = start2 >= dot ? 0 : Integer.parseInt(version.substring(start2, dot));
            start2 = dot + 1;
        }
        return val;
    }

    public static int compareVersion(int[] v1, int[] v2) {
        if (v1 == null) {
            v1 = new int[]{};
        }
        if (v2 == null) {
            v2 = new int[]{};
        }
        for (int i = 0; i < v1.length; ++i) {
            if (i >= v2.length || v1[i] > v2[i]) {
                return 1;
            }
            if (v1[i] >= v2[i]) continue;
            return -1;
        }
        return v1.length == v2.length ? 0 : -1;
    }

    public static synchronized int compareVersion(String v1, String v2) {
        return ORBUtility.compareVersion(ORBUtility.parseVersion(v1), ORBUtility.parseVersion(v2));
    }

    private static String compressClassName(String name) {
        String prefix = "org.jboss.com.sun.corba.se.";
        if (name.startsWith(prefix)) {
            return "(ORB)." + name.substring(prefix.length());
        }
        return name;
    }

    public static String getThreadName(Thread thr) {
        if (thr == null) {
            return "null";
        }
        String name = thr.getName();
        StringTokenizer st = new StringTokenizer(name);
        int numTokens = st.countTokens();
        if (numTokens != 5) {
            return name;
        }
        String[] tokens = new String[numTokens];
        for (int ctr = 0; ctr < numTokens; ++ctr) {
            tokens[ctr] = st.nextToken();
        }
        if (!tokens[0].equals("SelectReaderThread")) {
            return name;
        }
        return "SelectReaderThread[" + tokens[2] + ":" + tokens[3] + "]";
    }

    private static String formatStackTraceElement(StackTraceElement ste) {
        return ORBUtility.compressClassName(ste.getClassName()) + "." + ste.getMethodName() + (ste.isNativeMethod() ? "(Native Method)" : (ste.getFileName() != null && ste.getLineNumber() >= 0 ? "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")" : (ste.getFileName() != null ? "(" + ste.getFileName() + ")" : "(Unknown Source)")));
    }

    private static void printStackTrace(StackTraceElement[] trace) {
        System.out.println("    Stack Trace:");
        for (int ctr = 1; ctr < trace.length; ++ctr) {
            System.out.print("        >");
            System.out.println(ORBUtility.formatStackTraceElement(trace[ctr]));
        }
    }

    public static synchronized void dprint(java.lang.Object obj, String msg) {
        System.out.println(ORBUtility.compressClassName(obj.getClass().getName()) + "(" + ORBUtility.getThreadName(Thread.currentThread()) + "): " + msg);
    }

    public static synchronized void dprint(String className, String msg) {
        System.out.println(ORBUtility.compressClassName(className) + "(" + ORBUtility.getThreadName(Thread.currentThread()) + "): " + msg);
    }

    public synchronized void dprint(String msg) {
        ORBUtility.dprint(this, msg);
    }

    public static synchronized void dprintTrace(java.lang.Object obj, String msg) {
        ORBUtility.dprint(obj, msg);
        Throwable thr = new Throwable();
        ORBUtility.printStackTrace(thr.getStackTrace());
    }

    public static synchronized void dprint(java.lang.Object caller, String msg, Throwable t2) {
        System.out.println(ORBUtility.compressClassName(caller.getClass().getName()) + '(' + Thread.currentThread() + "): " + msg);
        if (t2 != null) {
            ORBUtility.printStackTrace(t2.getStackTrace());
        }
    }

    public static String[] concatenateStringArrays(String[] arr1, String[] arr2) {
        int ctr;
        String[] result = new String[arr1.length + arr2.length];
        for (ctr = 0; ctr < arr1.length; ++ctr) {
            result[ctr] = arr1[ctr];
        }
        for (ctr = 0; ctr < arr2.length; ++ctr) {
            result[ctr + arr1.length] = arr2[ctr];
        }
        return result;
    }

    public static void throwNotSerializableForCorba(String className) {
        throw omgWrapper.notSerializable(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)className);
    }

    public static byte getMaxStreamFormatVersion() {
        ValueHandler vh = Util.createValueHandler();
        if (!(vh instanceof ValueHandlerMultiFormat)) {
            return 1;
        }
        return ((ValueHandlerMultiFormat)vh).getMaximumStreamFormatVersion();
    }

    public static CorbaClientDelegate makeClientDelegate(IOR ior) {
        ORB orb = ior.getORB();
        CorbaContactInfoList ccil = orb.getCorbaContactInfoListFactory().create(ior);
        CorbaClientDelegate del = orb.getClientDelegateFactory().create(ccil);
        return del;
    }

    public static Object makeObjectReference(IOR ior) {
        CorbaClientDelegate del = ORBUtility.makeClientDelegate(ior);
        CORBAObjectImpl objectImpl = new CORBAObjectImpl();
        StubAdapter.setDelegate(objectImpl, del);
        return objectImpl;
    }

    public static IOR getIOR(Object obj) {
        if (obj == null) {
            throw wrapper.nullObjectReference();
        }
        IOR ior = null;
        if (StubAdapter.isStub(obj)) {
            Delegate del = StubAdapter.getDelegate(obj);
            if (del instanceof CorbaClientDelegate) {
                CorbaClientDelegate cdel = (CorbaClientDelegate)del;
                ContactInfoList cil = cdel.getContactInfoList();
                if (cil instanceof CorbaContactInfoList) {
                    CorbaContactInfoList ccil = (CorbaContactInfoList)cil;
                    ior = ccil.getTargetIOR();
                    if (ior == null) {
                        throw wrapper.nullIor();
                    }
                    return ior;
                }
                throw new INTERNAL();
            }
            throw wrapper.objrefFromForeignOrb();
        }
        throw wrapper.localObjectNotAllowed();
    }

    public static IOR connectAndGetIOR(ORB orb, Object obj) {
        IOR result;
        try {
            result = ORBUtility.getIOR(obj);
        }
        catch (BAD_OPERATION bop) {
            if (StubAdapter.isStub(obj)) {
                try {
                    StubAdapter.connect(obj, orb);
                }
                catch (RemoteException exc) {
                    throw wrapper.connectingServant(exc);
                }
            } else {
                orb.connect(obj);
            }
            result = ORBUtility.getIOR(obj);
        }
        return result;
    }

    public static String operationNameAndRequestId(CorbaMessageMediator m4) {
        return "op/" + m4.getOperationName() + " id/" + m4.getRequestId();
    }

    public static boolean isPrintable(char c) {
        if (Character.isJavaIdentifierStart(c)) {
            return true;
        }
        if (Character.isDigit(c)) {
            return true;
        }
        switch (Character.getType(c)) {
            case 27: {
                return true;
            }
            case 20: {
                return true;
            }
            case 25: {
                return true;
            }
            case 24: {
                return true;
            }
            case 21: {
                return true;
            }
            case 22: {
                return true;
            }
        }
        return false;
    }

    public static String getClassSecurityInfo(final Class<?> cl) {
        String result = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                StringBuffer sb = new StringBuffer(500);
                ProtectionDomain pd = cl.getProtectionDomain();
                Policy policy = Policy.getPolicy();
                PermissionCollection pc = policy.getPermissions(pd);
                sb.append("\nPermissionCollection ");
                sb.append(pc.toString());
                sb.append(pd.toString());
                return sb.toString();
            }
        });
        return result;
    }

    static {
        exceptionClassNames.put("IDL:omg.org/CORBA/BAD_CONTEXT:1.0", "org.omg.CORBA.BAD_CONTEXT");
        exceptionClassNames.put("IDL:omg.org/CORBA/BAD_INV_ORDER:1.0", "org.omg.CORBA.BAD_INV_ORDER");
        exceptionClassNames.put("IDL:omg.org/CORBA/BAD_OPERATION:1.0", "org.omg.CORBA.BAD_OPERATION");
        exceptionClassNames.put("IDL:omg.org/CORBA/BAD_PARAM:1.0", "org.omg.CORBA.BAD_PARAM");
        exceptionClassNames.put("IDL:omg.org/CORBA/BAD_TYPECODE:1.0", "org.omg.CORBA.BAD_TYPECODE");
        exceptionClassNames.put("IDL:omg.org/CORBA/COMM_FAILURE:1.0", "org.omg.CORBA.COMM_FAILURE");
        exceptionClassNames.put("IDL:omg.org/CORBA/DATA_CONVERSION:1.0", "org.omg.CORBA.DATA_CONVERSION");
        exceptionClassNames.put("IDL:omg.org/CORBA/IMP_LIMIT:1.0", "org.omg.CORBA.IMP_LIMIT");
        exceptionClassNames.put("IDL:omg.org/CORBA/INTF_REPOS:1.0", "org.omg.CORBA.INTF_REPOS");
        exceptionClassNames.put("IDL:omg.org/CORBA/INTERNAL:1.0", "org.omg.CORBA.INTERNAL");
        exceptionClassNames.put("IDL:omg.org/CORBA/INV_FLAG:1.0", "org.omg.CORBA.INV_FLAG");
        exceptionClassNames.put("IDL:omg.org/CORBA/INV_IDENT:1.0", "org.omg.CORBA.INV_IDENT");
        exceptionClassNames.put("IDL:omg.org/CORBA/INV_OBJREF:1.0", "org.omg.CORBA.INV_OBJREF");
        exceptionClassNames.put("IDL:omg.org/CORBA/MARSHAL:1.0", "org.omg.CORBA.MARSHAL");
        exceptionClassNames.put("IDL:omg.org/CORBA/NO_MEMORY:1.0", "org.omg.CORBA.NO_MEMORY");
        exceptionClassNames.put("IDL:omg.org/CORBA/FREE_MEM:1.0", "org.omg.CORBA.FREE_MEM");
        exceptionClassNames.put("IDL:omg.org/CORBA/NO_IMPLEMENT:1.0", "org.omg.CORBA.NO_IMPLEMENT");
        exceptionClassNames.put("IDL:omg.org/CORBA/NO_PERMISSION:1.0", "org.omg.CORBA.NO_PERMISSION");
        exceptionClassNames.put("IDL:omg.org/CORBA/NO_RESOURCES:1.0", "org.omg.CORBA.NO_RESOURCES");
        exceptionClassNames.put("IDL:omg.org/CORBA/NO_RESPONSE:1.0", "org.omg.CORBA.NO_RESPONSE");
        exceptionClassNames.put("IDL:omg.org/CORBA/OBJ_ADAPTER:1.0", "org.omg.CORBA.OBJ_ADAPTER");
        exceptionClassNames.put("IDL:omg.org/CORBA/INITIALIZE:1.0", "org.omg.CORBA.INITIALIZE");
        exceptionClassNames.put("IDL:omg.org/CORBA/PERSIST_STORE:1.0", "org.omg.CORBA.PERSIST_STORE");
        exceptionClassNames.put("IDL:omg.org/CORBA/TRANSIENT:1.0", "org.omg.CORBA.TRANSIENT");
        exceptionClassNames.put("IDL:omg.org/CORBA/UNKNOWN:1.0", "org.omg.CORBA.UNKNOWN");
        exceptionClassNames.put("IDL:omg.org/CORBA/OBJECT_NOT_EXIST:1.0", "org.omg.CORBA.OBJECT_NOT_EXIST");
        exceptionClassNames.put("IDL:omg.org/CORBA/INVALID_TRANSACTION:1.0", "org.omg.CORBA.INVALID_TRANSACTION");
        exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_REQUIRED:1.0", "org.omg.CORBA.TRANSACTION_REQUIRED");
        exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_ROLLEDBACK:1.0", "org.omg.CORBA.TRANSACTION_ROLLEDBACK");
        exceptionClassNames.put("IDL:omg.org/CORBA/INV_POLICY:1.0", "org.omg.CORBA.INV_POLICY");
        exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_UNAVAILABLE:1.0", "org.omg.CORBA.TRANSACTION_UNAVAILABLE");
        exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_MODE:1.0", "org.omg.CORBA.TRANSACTION_MODE");
        exceptionClassNames.put("IDL:omg.org/CORBA/CODESET_INCOMPATIBLE:1.0", "org.omg.CORBA.CODESET_INCOMPATIBLE");
        exceptionClassNames.put("IDL:omg.org/CORBA/REBIND:1.0", "org.omg.CORBA.REBIND");
        exceptionClassNames.put("IDL:omg.org/CORBA/TIMEOUT:1.0", "org.omg.CORBA.TIMEOUT");
        exceptionClassNames.put("IDL:omg.org/CORBA/BAD_QOS:1.0", "org.omg.CORBA.BAD_QOS");
        exceptionClassNames.put("IDL:omg.org/CORBA/INVALID_ACTIVITY:1.0", "org.omg.CORBA.INVALID_ACTIVITY");
        exceptionClassNames.put("IDL:omg.org/CORBA/ACTIVITY_COMPLETED:1.0", "org.omg.CORBA.ACTIVITY_COMPLETED");
        exceptionClassNames.put("IDL:omg.org/CORBA/ACTIVITY_REQUIRED:1.0", "org.omg.CORBA.ACTIVITY_REQUIRED");
        Enumeration<String> keys2 = exceptionClassNames.keys();
        try {
            while (keys2.hasMoreElements()) {
                String rId = keys2.nextElement();
                String cName = exceptionClassNames.get(rId);
                exceptionRepositoryIds.put(cName, rId);
            }
        }
        catch (NoSuchElementException noSuchElementException) {
            // empty catch block
        }
    }
}

