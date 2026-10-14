/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.ValueHandlerMultiFormat;
import org.jboss.com.sun.corba.se.impl.io.FVDCodeBaseImpl;
import org.jboss.com.sun.corba.se.impl.io.IIOPInputStream;
import org.jboss.com.sun.corba.se.impl.io.IIOPOutputStream;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClassCorbaExt;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.IIOPInputStream_1_3;
import org.jboss.com.sun.corba.se.impl.orbutil.IIOPInputStream_1_3_1;
import org.jboss.com.sun.corba.se.impl.orbutil.IIOPOutputStream_1_3;
import org.jboss.com.sun.corba.se.impl.orbutil.IIOPOutputStream_1_3_1;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.jboss.com.sun.org.omg.SendingContext.CodeBaseHelper;
import org.omg.CORBA.Object;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.portable.IndirectionException;
import org.omg.CORBA.portable.ValueOutputStream;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.SendingContext.RunTime;

public class ValueHandlerImpl
implements ValueHandlerMultiFormat {
    public static final String FORMAT_VERSION_PROPERTY = "org.jboss.com.sun.CORBA.MaxStreamFormatVersion";
    private static final byte MAX_SUPPORTED_FORMAT_VERSION = 2;
    private static final byte STREAM_FORMAT_VERSION_1 = 1;
    private static final byte MAX_STREAM_FORMAT_VERSION = ValueHandlerImpl.getMaxStreamFormatVersion();
    public static final short kRemoteType = 0;
    public static final short kAbstractType = 1;
    public static final short kValueType = 2;
    private Hashtable<org.omg.CORBA.portable.InputStream, IIOPInputStream> inputStreamPairs = null;
    private Hashtable<org.omg.CORBA.portable.OutputStream, IIOPOutputStream> outputStreamPairs = null;
    private CodeBase codeBase = null;
    private boolean useHashtables = true;
    private IIOPOutputStream outputStreamBridge = null;
    private IIOPInputStream inputStreamBridge = null;
    private OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
    private UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");

    private static byte getMaxStreamFormatVersion() {
        try {
            String propValue = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty(ValueHandlerImpl.FORMAT_VERSION_PROPERTY);
                }
            });
            if (propValue == null) {
                return 2;
            }
            byte result = Byte.parseByte(propValue);
            if (result < 1 || result > 2) {
                throw new ExceptionInInitializerError("Invalid stream format version: " + result + ".  Valid range is 1 through " + 2);
            }
            return result;
        }
        catch (Exception ex) {
            ExceptionInInitializerError err = new ExceptionInInitializerError(ex);
            err.initCause(ex);
            throw err;
        }
    }

    @Override
    public byte getMaximumStreamFormatVersion() {
        return MAX_STREAM_FORMAT_VERSION;
    }

    @Override
    public void writeValue(org.omg.CORBA.portable.OutputStream out, Serializable value, byte streamFormatVersion) {
        if (streamFormatVersion == 2) {
            if (!(out instanceof ValueOutputStream)) {
                throw this.omgWrapper.notAValueoutputstream();
            }
        } else if (streamFormatVersion != 1) {
            throw this.omgWrapper.invalidStreamFormatVersion(new Integer(streamFormatVersion));
        }
        this.writeValueWithVersion(out, value, streamFormatVersion);
    }

    public ValueHandlerImpl() {
    }

    public ValueHandlerImpl(boolean isInputStream) {
        this();
        this.useHashtables = false;
    }

    @Override
    public void writeValue(org.omg.CORBA.portable.OutputStream _out, Serializable value) {
        this.writeValueWithVersion(_out, value, (byte)1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeValueWithVersion(org.omg.CORBA.portable.OutputStream _out, Serializable value, byte streamFormatVersion) {
        OutputStream out = (OutputStream)_out;
        if (!this.useHashtables) {
            if (this.outputStreamBridge == null) {
                this.outputStreamBridge = this.createOutputStream();
                this.outputStreamBridge.setOrbStream(out);
            }
            try {
                this.outputStreamBridge.increaseRecursionDepth();
                this.writeValueInternal(this.outputStreamBridge, out, value, streamFormatVersion);
            }
            finally {
                this.outputStreamBridge.decreaseRecursionDepth();
            }
            return;
        }
        IIOPOutputStream jdkToOrbOutputStreamBridge = null;
        if (this.outputStreamPairs == null) {
            this.outputStreamPairs = new Hashtable();
        }
        if ((jdkToOrbOutputStreamBridge = this.outputStreamPairs.get(_out)) == null) {
            jdkToOrbOutputStreamBridge = this.createOutputStream();
            jdkToOrbOutputStreamBridge.setOrbStream(out);
            this.outputStreamPairs.put(_out, jdkToOrbOutputStreamBridge);
        }
        try {
            jdkToOrbOutputStreamBridge.increaseRecursionDepth();
            this.writeValueInternal(jdkToOrbOutputStreamBridge, out, value, streamFormatVersion);
        }
        finally {
            if (jdkToOrbOutputStreamBridge.decreaseRecursionDepth() == 0) {
                this.outputStreamPairs.remove(_out);
            }
        }
    }

    private void writeValueInternal(IIOPOutputStream bridge, OutputStream out, Serializable value, byte streamFormatVersion) {
        Class<?> clazz = value.getClass();
        if (clazz.isArray()) {
            this.write_Array(out, value, clazz.getComponentType());
        } else {
            bridge.simpleWriteObject(value, streamFormatVersion);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Serializable readValue(org.omg.CORBA.portable.InputStream _in, int offset, Class<?> clazz, String repositoryID, RunTime _sender) {
        CodeBase sender = CodeBaseHelper.narrow(_sender);
        InputStream in = (InputStream)_in;
        if (!this.useHashtables) {
            if (this.inputStreamBridge == null) {
                this.inputStreamBridge = this.createInputStream();
                this.inputStreamBridge.setOrbStream(in);
                this.inputStreamBridge.setSender(sender);
                this.inputStreamBridge.setValueHandler(this);
            }
            Serializable result = null;
            try {
                this.inputStreamBridge.increaseRecursionDepth();
                result = this.readValueInternal(this.inputStreamBridge, in, offset, clazz, repositoryID, sender);
            }
            finally {
                if (this.inputStreamBridge.decreaseRecursionDepth() == 0) {
                    // empty if block
                }
            }
            return result;
        }
        IIOPInputStream jdkToOrbInputStreamBridge = null;
        if (this.inputStreamPairs == null) {
            this.inputStreamPairs = new Hashtable();
        }
        if ((jdkToOrbInputStreamBridge = this.inputStreamPairs.get(_in)) == null) {
            jdkToOrbInputStreamBridge = this.createInputStream();
            jdkToOrbInputStreamBridge.setOrbStream(in);
            jdkToOrbInputStreamBridge.setSender(sender);
            jdkToOrbInputStreamBridge.setValueHandler(this);
            this.inputStreamPairs.put(_in, jdkToOrbInputStreamBridge);
        }
        Serializable result = null;
        try {
            jdkToOrbInputStreamBridge.increaseRecursionDepth();
            result = this.readValueInternal(jdkToOrbInputStreamBridge, in, offset, clazz, repositoryID, sender);
        }
        finally {
            if (jdkToOrbInputStreamBridge.decreaseRecursionDepth() == 0) {
                this.inputStreamPairs.remove(_in);
            }
        }
        return result;
    }

    private Serializable readValueInternal(IIOPInputStream bridge, InputStream in, int offset, Class<?> clazz, String repositoryID, CodeBase sender) {
        Serializable result = null;
        if (clazz == null) {
            if (this.isArray(repositoryID)) {
                this.read_Array(bridge, in, null, sender, offset);
            } else {
                bridge.simpleSkipObject(repositoryID, sender);
            }
            return result;
        }
        result = clazz.isArray() ? (Serializable)this.read_Array(bridge, in, clazz, sender, offset) : (Serializable)bridge.simpleReadObject(clazz, repositoryID, sender, offset);
        return result;
    }

    @Override
    public String getRMIRepositoryID(Class<?> clz) {
        return RepositoryId.createForJavaType(clz);
    }

    @Override
    public boolean isCustomMarshaled(Class<?> clz) {
        return ObjectStreamClass.lookup(clz).isCustomMarshaled();
    }

    @Override
    public RunTime getRunTimeCodeBase() {
        if (this.codeBase != null) {
            return this.codeBase;
        }
        this.codeBase = new FVDCodeBaseImpl();
        FVDCodeBaseImpl fvdImpl = (FVDCodeBaseImpl)this.codeBase;
        fvdImpl.setValueHandler(this);
        return this.codeBase;
    }

    public boolean useFullValueDescription(Class<?> clazz, String repositoryID) throws IOException {
        return RepositoryId.useFullValueDescription(clazz, repositoryID);
    }

    public String getClassName(String id) {
        RepositoryId repID = RepositoryId.cache.getId(id);
        return repID.getClassName();
    }

    public Class<?> getClassFromType(String id) throws ClassNotFoundException {
        RepositoryId repId = RepositoryId.cache.getId(id);
        return repId.getClassFromType();
    }

    public Class<?> getAnyClassFromType(String id) throws ClassNotFoundException {
        RepositoryId repId = RepositoryId.cache.getId(id);
        return repId.getAnyClassFromType();
    }

    public String createForAnyType(Class<?> cl) {
        return RepositoryId.createForAnyType(cl);
    }

    public String getDefinedInId(String id) {
        RepositoryId repId = RepositoryId.cache.getId(id);
        return repId.getDefinedInId();
    }

    public String getUnqualifiedName(String id) {
        RepositoryId repId = RepositoryId.cache.getId(id);
        return repId.getUnqualifiedName();
    }

    public String getSerialVersionUID(String id) {
        RepositoryId repId = RepositoryId.cache.getId(id);
        return repId.getSerialVersionUID();
    }

    public boolean isAbstractBase(Class<?> clazz) {
        return RepositoryId.isAbstractBase(clazz);
    }

    public boolean isSequence(String id) {
        RepositoryId repId = RepositoryId.cache.getId(id);
        return repId.isSequence();
    }

    @Override
    public Serializable writeReplace(Serializable value) {
        return ObjectStreamClass.lookup(value.getClass()).writeReplace(value);
    }

    protected void writeCharArray(OutputStream out, char[] array, int offset, int length) {
        out.write_wchar_array(array, offset, length);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void write_Array(OutputStream out, Serializable obj, Class<?> type) {
        if (type.isPrimitive()) {
            if (type == Integer.TYPE) {
                int[] array = (int[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_long_array(array, 0, length);
                return;
            } else if (type == Byte.TYPE) {
                byte[] array = (byte[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_octet_array(array, 0, length);
                return;
            } else if (type == Long.TYPE) {
                long[] array = (long[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_longlong_array(array, 0, length);
                return;
            } else if (type == Float.TYPE) {
                float[] array = (float[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_float_array(array, 0, length);
                return;
            } else if (type == Double.TYPE) {
                double[] array = (double[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_double_array(array, 0, length);
                return;
            } else if (type == Short.TYPE) {
                short[] array = (short[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_short_array(array, 0, length);
                return;
            } else if (type == Character.TYPE) {
                char[] array = (char[])obj;
                int length = array.length;
                out.write_ulong(length);
                this.writeCharArray(out, array, 0, length);
                return;
            } else {
                if (type != Boolean.TYPE) throw new Error("Invalid primitive type : " + obj.getClass().getName());
                boolean[] array = (boolean[])obj;
                int length = array.length;
                out.write_ulong(length);
                out.write_boolean_array(array, 0, length);
            }
            return;
        } else if (type == java.lang.Object.class) {
            java.lang.Object[] array = (java.lang.Object[])obj;
            int length = array.length;
            out.write_ulong(length);
            for (int i = 0; i < length; ++i) {
                Util.writeAny(out, array[i]);
            }
            return;
        } else {
            java.lang.Object[] array = (java.lang.Object[])obj;
            int length = array.length;
            out.write_ulong(length);
            int callType = 2;
            if (type.isInterface()) {
                if (Remote.class.isAssignableFrom(type)) {
                    callType = 0;
                } else if (Object.class.isAssignableFrom(type)) {
                    callType = 0;
                } else if (RepositoryId.isAbstractBase(type)) {
                    callType = 1;
                } else if (ObjectStreamClassCorbaExt.isAbstractInterface(type)) {
                    callType = 1;
                }
            }
            block8: for (int i = 0; i < length; ++i) {
                switch (callType) {
                    case 0: {
                        Util.writeRemoteObject(out, array[i]);
                        continue block8;
                    }
                    case 1: {
                        Util.writeAbstractObject(out, array[i]);
                        continue block8;
                    }
                    case 2: {
                        try {
                            out.write_value((Serializable)array[i]);
                            continue block8;
                        }
                        catch (ClassCastException cce) {
                            if (array[i] instanceof Serializable) {
                                throw cce;
                            }
                            Utility.throwNotSerializableForCorba(array[i].getClass().getName());
                        }
                    }
                }
            }
        }
    }

    protected void readCharArray(InputStream in, char[] array, int offset, int length) {
        in.read_wchar_array(array, offset, length);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private java.lang.Object read_Array(IIOPInputStream bridge, InputStream in, Class<?> sequence2, CodeBase sender, int offset) {
        try {
            Class<?> componentType;
            int length = in.read_ulong();
            if (sequence2 == null) {
                for (int i = 0; i < length; ++i) {
                    in.read_value();
                }
                java.lang.Object var8_10 = null;
                return var8_10;
            }
            Class<?> actualType = componentType = sequence2.getComponentType();
            if (componentType.isPrimitive()) {
                if (componentType == Integer.TYPE) {
                    int[] array = new int[length];
                    in.read_long_array(array, 0, length);
                    int[] nArray = array;
                    return nArray;
                }
                if (componentType == Byte.TYPE) {
                    byte[] array = new byte[length];
                    in.read_octet_array(array, 0, length);
                    byte[] byArray = array;
                    return byArray;
                }
                if (componentType == Long.TYPE) {
                    long[] array = new long[length];
                    in.read_longlong_array(array, 0, length);
                    long[] lArray = array;
                    return lArray;
                }
                if (componentType == Float.TYPE) {
                    float[] array = new float[length];
                    in.read_float_array(array, 0, length);
                    float[] fArray = array;
                    return fArray;
                }
                if (componentType == Double.TYPE) {
                    double[] array = new double[length];
                    in.read_double_array(array, 0, length);
                    double[] dArray = array;
                    return dArray;
                }
                if (componentType == Short.TYPE) {
                    short[] array = new short[length];
                    in.read_short_array(array, 0, length);
                    short[] sArray = array;
                    return sArray;
                }
                if (componentType == Character.TYPE) {
                    char[] array = new char[length];
                    this.readCharArray(in, array, 0, length);
                    char[] cArray = array;
                    return cArray;
                }
                if (componentType == Boolean.TYPE) {
                    boolean[] array = new boolean[length];
                    in.read_boolean_array(array, 0, length);
                    boolean[] blArray = array;
                    return blArray;
                }
                throw new Error("Invalid primitive componentType : " + sequence2.getName());
            }
            if (componentType == java.lang.Object.class) {
                java.lang.Object objectValue;
                java.lang.Object[] array = (java.lang.Object[])Array.newInstance(componentType, length);
                bridge.activeRecursionMgr.addObject(offset, array);
                for (int i = 0; i < length; ++i) {
                    objectValue = null;
                    try {
                        objectValue = Util.readAny(in);
                    }
                    catch (IndirectionException cdrie) {
                        try {
                            objectValue = bridge.activeRecursionMgr.getObject(cdrie.offset);
                        }
                        catch (IOException ie) {
                            throw this.utilWrapper.invalidIndirection(ie, (java.lang.Object)new Integer(cdrie.offset));
                        }
                    }
                    array[i] = objectValue;
                }
                objectValue = array;
                return objectValue;
            }
            java.lang.Object[] array = (java.lang.Object[])Array.newInstance(componentType, length);
            bridge.activeRecursionMgr.addObject(offset, array);
            int callType = 2;
            boolean narrow = false;
            if (componentType.isInterface()) {
                boolean loadStubClass = false;
                if (Remote.class.isAssignableFrom(componentType)) {
                    callType = 0;
                    loadStubClass = true;
                } else if (Object.class.isAssignableFrom(componentType)) {
                    callType = 0;
                    loadStubClass = true;
                } else if (RepositoryId.isAbstractBase(componentType)) {
                    callType = 1;
                    loadStubClass = true;
                } else if (ObjectStreamClassCorbaExt.isAbstractInterface(componentType)) {
                    callType = 1;
                }
                if (loadStubClass) {
                    try {
                        String codebase = Util.getCodebase(componentType);
                        String repID = RepositoryId.createForAnyType(componentType);
                        Class<?> stubType = Utility.loadStubClass(repID, codebase, componentType);
                        actualType = stubType;
                    }
                    catch (ClassNotFoundException e) {
                        narrow = true;
                    }
                } else {
                    narrow = true;
                }
            }
            for (int i = 0; i < length; ++i) {
                try {
                    switch (callType) {
                        case 0: {
                            if (!narrow) {
                                array[i] = in.read_Object(actualType);
                                break;
                            }
                            array[i] = Utility.readObjectAndNarrow(in, actualType);
                            break;
                        }
                        case 1: {
                            if (!narrow) {
                                array[i] = in.read_abstract_interface(actualType);
                                break;
                            }
                            array[i] = Utility.readAbstractAndNarrow(in, actualType);
                            break;
                        }
                        case 2: {
                            array[i] = in.read_value(actualType);
                        }
                    }
                    continue;
                }
                catch (IndirectionException cdrie) {
                    try {
                        array[i] = bridge.activeRecursionMgr.getObject(cdrie.offset);
                        continue;
                    }
                    catch (IOException ioe) {
                        throw this.utilWrapper.invalidIndirection(ioe, (java.lang.Object)new Integer(cdrie.offset));
                    }
                }
            }
            java.lang.Object[] objectArray = array;
            return objectArray;
        }
        finally {
            bridge.activeRecursionMgr.removeObject(offset);
        }
    }

    private boolean isArray(String repId) {
        return RepositoryId.cache.getId(repId).isSequence();
    }

    protected String getOutputStreamClassName() {
        return "org.jboss.com.sun.corba.se.impl.io.IIOPOutputStream";
    }

    private IIOPOutputStream createOutputStream() {
        String name = this.getOutputStreamClassName();
        try {
            IIOPOutputStream stream = this.createOutputStreamBuiltIn(name);
            if (stream != null) {
                return stream;
            }
            return this.createCustom(IIOPOutputStream.class, name);
        }
        catch (Throwable t2) {
            InternalError ie = new InternalError("Error loading " + name);
            ie.initCause(t2);
            throw ie;
        }
    }

    private IIOPOutputStream createOutputStreamBuiltIn(final String name) throws Throwable {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<IIOPOutputStream>(){

                @Override
                public IIOPOutputStream run() throws IOException {
                    return ValueHandlerImpl.this.createOutputStreamBuiltInNoPriv(name);
                }
            });
        }
        catch (PrivilegedActionException exc) {
            throw exc.getCause();
        }
    }

    private IIOPOutputStream createOutputStreamBuiltInNoPriv(String name) throws IOException {
        return name.equals(IIOPOutputStream.class.getName()) ? new IIOPOutputStream() : (name.equals(IIOPOutputStream_1_3.class.getName()) ? new IIOPOutputStream_1_3() : (name.equals(IIOPOutputStream_1_3_1.class.getName()) ? new IIOPOutputStream_1_3_1() : null));
    }

    protected String getInputStreamClassName() {
        return "org.jboss.com.sun.corba.se.impl.io.IIOPInputStream";
    }

    private IIOPInputStream createInputStream() {
        String name = this.getInputStreamClassName();
        try {
            IIOPInputStream stream = this.createInputStreamBuiltIn(name);
            if (stream != null) {
                return stream;
            }
            return this.createCustom(IIOPInputStream.class, name);
        }
        catch (Throwable t2) {
            InternalError ie = new InternalError("Error loading " + name);
            ie.initCause(t2);
            throw ie;
        }
    }

    private IIOPInputStream createInputStreamBuiltIn(final String name) throws Throwable {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<IIOPInputStream>(){

                @Override
                public IIOPInputStream run() throws IOException {
                    return ValueHandlerImpl.this.createInputStreamBuiltInNoPriv(name);
                }
            });
        }
        catch (PrivilegedActionException exc) {
            throw exc.getCause();
        }
    }

    private IIOPInputStream createInputStreamBuiltInNoPriv(String name) throws IOException {
        return name.equals(IIOPInputStream.class.getName()) ? new IIOPInputStream() : (name.equals(IIOPInputStream_1_3.class.getName()) ? new IIOPInputStream_1_3() : (name.equals(IIOPInputStream_1_3_1.class.getName()) ? new IIOPInputStream_1_3_1() : null));
    }

    private <T> T createCustom(Class<T> type, String className) throws Throwable {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        Class<?> clazz = cl.loadClass(className);
        Class<T> streamClass = clazz.asSubclass(type);
        return streamClass.newInstance();
    }

    protected TCKind getJavaCharTCKind() {
        return TCKind.tk_wchar;
    }
}

