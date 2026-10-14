/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.ObjectInputValidation;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.ValueHandler;
import org.jboss.com.sun.corba.se.impl.io.InputStreamHook;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClassCorbaExt;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamField;
import org.jboss.com.sun.corba.se.impl.io.ValueHandlerImpl;
import org.jboss.com.sun.corba.se.impl.io.ValueUtility;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.jboss.sun.corba.Bridge;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.portable.IndirectionException;
import org.omg.CORBA.portable.ValueInputStream;
import org.omg.CORBA_2_3.portable.InputStream;

public class IIOPInputStream
extends InputStreamHook {
    private static Bridge bridge = AccessController.doPrivileged(new PrivilegedAction<Bridge>(){

        @Override
        public Bridge run() {
            return Bridge.get();
        }
    });
    private static OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
    private static UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
    private ValueMember[] defaultReadObjectFVDMembers = null;
    private InputStream orbStream;
    private CodeBase cbSender;
    private ValueHandlerImpl vhandler;
    private java.lang.Object currentObject = null;
    private ObjectStreamClass currentClassDesc = null;
    private Class<?> currentClass = null;
    private int recursionDepth = 0;
    private int simpleReadDepth = 0;
    ActiveRecursionManager activeRecursionMgr = new ActiveRecursionManager();
    private IOException abortIOException = null;
    private ClassNotFoundException abortClassNotFoundException = null;
    private Vector<?> callbacks;
    ObjectStreamClass[] classdesc;
    Class<?>[] classes;
    int spClass;
    private static final String kEmptyStr = "";
    public static final TypeCode kRemoteTypeCode = ORB.init().get_primitive_tc(TCKind.tk_objref);
    public static final TypeCode kValueTypeCode = ORB.init().get_primitive_tc(TCKind.tk_value);
    private byte streamFormatVersion;
    private static final Constructor<?> OPT_DATA_EXCEPTION_CTOR = IIOPInputStream.getOptDataExceptionCtor();
    private java.lang.Object[] readObjectArgList = new java.lang.Object[]{this};

    private static Constructor<?> getOptDataExceptionCtor() {
        try {
            Constructor result = (Constructor)AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>>(){

                @Override
                public Constructor<?> run() throws NoSuchMethodException, SecurityException {
                    Constructor boolCtor = OptionalDataException.class.getDeclaredConstructor(Boolean.TYPE);
                    boolCtor.setAccessible(true);
                    return boolCtor;
                }
            });
            if (result == null) {
                throw new Error("Unable to find OptionalDataException constructor");
            }
            return result;
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private OptionalDataException createOptionalDataException() {
        try {
            OptionalDataException result = (OptionalDataException)OPT_DATA_EXCEPTION_CTOR.newInstance(Boolean.TRUE);
            if (result == null) {
                throw new Error("Created null OptionalDataException");
            }
            return result;
        }
        catch (Exception ex) {
            throw new Error("Couldn't create OptionalDataException", ex);
        }
    }

    @Override
    protected byte getStreamFormatVersion() {
        return this.streamFormatVersion;
    }

    private void readFormatVersion() throws IOException {
        this.streamFormatVersion = this.orbStream.read_octet();
        if (this.streamFormatVersion < 1 || this.streamFormatVersion > this.vhandler.getMaximumStreamFormatVersion()) {
            MARSHAL sysex = omgWrapper.unsupportedFormatVersion(CompletionStatus.COMPLETED_MAYBE);
            IOException result = new IOException("Unsupported format version: " + this.streamFormatVersion);
            result.initCause(sysex);
            throw result;
        }
        if (this.streamFormatVersion == 2 && !(this.orbStream instanceof ValueInputStream)) {
            BAD_PARAM sysex = omgWrapper.notAValueinputstream(CompletionStatus.COMPLETED_MAYBE);
            IOException result = new IOException("Not a ValueInputStream");
            result.initCause(sysex);
            throw result;
        }
    }

    public static void setTestFVDFlag(boolean val) {
    }

    public IIOPInputStream() throws IOException {
        this.resetStream();
    }

    public final void setOrbStream(InputStream os) {
        this.orbStream = os;
    }

    @Override
    public final InputStream getOrbStream() {
        return this.orbStream;
    }

    public final void setSender(CodeBase cb) {
        this.cbSender = cb;
    }

    public final CodeBase getSender() {
        return this.cbSender;
    }

    public final void setValueHandler(ValueHandler vh) {
        this.vhandler = (ValueHandlerImpl)vh;
    }

    public final ValueHandler getValueHandler() {
        return this.vhandler;
    }

    public final void increaseRecursionDepth() {
        ++this.recursionDepth;
    }

    public final int decreaseRecursionDepth() {
        return --this.recursionDepth;
    }

    public final java.lang.Object readObjectDelegate() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_abstract_interface();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, true);
            throw marshalException;
        }
        catch (IndirectionException cdrie) {
            return this.activeRecursionMgr.getObject(cdrie.offset);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final java.lang.Object simpleReadObject(Class<?> clz, String repositoryID, CodeBase sender, int offset) {
        java.lang.Object prevObject = this.currentObject;
        ObjectStreamClass prevClassDesc = this.currentClassDesc;
        Class<?> prevClass = this.currentClass;
        byte oldStreamFormatVersion = this.streamFormatVersion;
        ++this.simpleReadDepth;
        java.lang.Object obj = null;
        try {
            obj = this.vhandler.useFullValueDescription(clz, repositoryID) ? this.inputObjectUsingFVD(clz, repositoryID, sender, offset) : this.inputObject(clz, repositoryID, sender, offset);
            obj = this.currentClassDesc.readResolve(obj);
        }
        catch (ClassNotFoundException cnfe) {
            bridge.throwException(cnfe);
            java.lang.Object var11_13 = null;
            return var11_13;
        }
        catch (IOException ioe) {
            bridge.throwException(ioe);
            java.lang.Object var11_14 = null;
            return var11_14;
        }
        finally {
            --this.simpleReadDepth;
            this.currentObject = prevObject;
            this.currentClassDesc = prevClassDesc;
            this.currentClass = prevClass;
            this.streamFormatVersion = oldStreamFormatVersion;
        }
        IOException exIOE = this.abortIOException;
        if (this.simpleReadDepth == 0) {
            this.abortIOException = null;
        }
        if (exIOE != null) {
            bridge.throwException(exIOE);
            return null;
        }
        ClassNotFoundException exCNF = this.abortClassNotFoundException;
        if (this.simpleReadDepth == 0) {
            this.abortClassNotFoundException = null;
        }
        if (exCNF != null) {
            bridge.throwException(exCNF);
            return null;
        }
        return obj;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void simpleSkipObject(String repositoryID, CodeBase sender) {
        java.lang.Object prevObject = this.currentObject;
        ObjectStreamClass prevClassDesc = this.currentClassDesc;
        Class<?> prevClass = this.currentClass;
        byte oldStreamFormatVersion = this.streamFormatVersion;
        ++this.simpleReadDepth;
        try {
            this.skipObjectUsingFVD(repositoryID, sender);
        }
        catch (ClassNotFoundException cnfe) {
            bridge.throwException(cnfe);
            return;
        }
        catch (IOException ioe) {
            bridge.throwException(ioe);
            return;
        }
        finally {
            --this.simpleReadDepth;
            this.streamFormatVersion = oldStreamFormatVersion;
            this.currentObject = prevObject;
            this.currentClassDesc = prevClassDesc;
            this.currentClass = prevClass;
        }
        IOException exIOE = this.abortIOException;
        if (this.simpleReadDepth == 0) {
            this.abortIOException = null;
        }
        if (exIOE != null) {
            bridge.throwException(exIOE);
            return;
        }
        ClassNotFoundException exCNF = this.abortClassNotFoundException;
        if (this.simpleReadDepth == 0) {
            this.abortClassNotFoundException = null;
        }
        if (exCNF != null) {
            bridge.throwException(exCNF);
            return;
        }
    }

    @Override
    protected final java.lang.Object readObjectOverride() throws OptionalDataException, ClassNotFoundException, IOException {
        return this.readObjectDelegate();
    }

    @Override
    public final void defaultReadObjectDelegate() {
        try {
            if (this.currentObject == null || this.currentClassDesc == null) {
                throw new NotActiveException("defaultReadObjectDelegate");
            }
            if (this.defaultReadObjectFVDMembers != null && this.defaultReadObjectFVDMembers.length > 0) {
                this.inputClassFields(this.currentObject, this.currentClass, this.currentClassDesc, this.defaultReadObjectFVDMembers, this.cbSender);
            } else {
                ObjectStreamField[] fields = this.currentClassDesc.getFieldsNoCopy();
                if (fields.length > 0) {
                    this.inputClassFields(this.currentObject, this.currentClass, fields, this.cbSender);
                }
            }
        }
        catch (NotActiveException nae) {
            bridge.throwException(nae);
        }
        catch (IOException ioe) {
            bridge.throwException(ioe);
        }
        catch (ClassNotFoundException cnfe) {
            bridge.throwException(cnfe);
        }
    }

    public final boolean enableResolveObjectDelegate(boolean enable) {
        return false;
    }

    @Override
    public final void mark(int readAheadLimit) {
        this.orbStream.mark(readAheadLimit);
    }

    @Override
    public final boolean markSupported() {
        return this.orbStream.markSupported();
    }

    @Override
    public final void reset() throws IOException {
        try {
            this.orbStream.reset();
        }
        catch (Error e) {
            IOException err = new IOException(e.getMessage());
            err.initCause(e);
            throw err;
        }
    }

    @Override
    public final int available() throws IOException {
        return 0;
    }

    @Override
    public final void close() throws IOException {
    }

    @Override
    public final int read() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_octet() << 0 & 0xFF;
        }
        catch (MARSHAL marshalException) {
            if (marshalException.minor == 1330446344) {
                this.setState(IN_READ_OBJECT_NO_MORE_OPT_DATA);
                return -1;
            }
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final int read(byte[] data, int offset, int length) throws IOException {
        try {
            this.readObjectState.readData(this);
            this.orbStream.read_octet_array(data, offset, length);
            return length;
        }
        catch (MARSHAL marshalException) {
            if (marshalException.minor == 1330446344) {
                this.setState(IN_READ_OBJECT_NO_MORE_OPT_DATA);
                return -1;
            }
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final boolean readBoolean() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_boolean();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final byte readByte() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_octet();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final char readChar() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_wchar();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final double readDouble() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_double();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final float readFloat() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_float();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final void readFully(byte[] data) throws IOException {
        this.readFully(data, 0, data.length);
    }

    @Override
    public final void readFully(byte[] data, int offset, int size) throws IOException {
        try {
            this.readObjectState.readData(this);
            this.orbStream.read_octet_array(data, offset, size);
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final int readInt() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_long();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final String readLine() throws IOException {
        throw new IOException("Method readLine not supported");
    }

    @Override
    public final long readLong() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_longlong();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final short readShort() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_short();
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    protected final void readStreamHeader() throws IOException, StreamCorruptedException {
    }

    @Override
    public final int readUnsignedByte() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_octet() << 0 & 0xFF;
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    @Override
    public final int readUnsignedShort() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.orbStream.read_ushort() << 0 & 0xFFFF;
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    protected String internalReadUTF(org.omg.CORBA.portable.InputStream stream) {
        return stream.read_wstring();
    }

    @Override
    public final String readUTF() throws IOException {
        try {
            this.readObjectState.readData(this);
            return this.internalReadUTF(this.orbStream);
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    private void handleOptionalDataMarshalException(MARSHAL marshalException, boolean objectRead) throws IOException {
        if (marshalException.minor == 1330446344) {
            IOException result = !objectRead ? new EOFException("No more optional data") : this.createOptionalDataException();
            result.initCause(marshalException);
            this.setState(IN_READ_OBJECT_NO_MORE_OPT_DATA);
            throw result;
        }
    }

    @Override
    public final synchronized void registerValidation(ObjectInputValidation obj, int prio) throws NotActiveException, InvalidObjectException {
        throw new Error("Method registerValidation not supported");
    }

    protected final Class<?> resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
        throw new IOException("Method resolveClass not supported");
    }

    @Override
    protected final java.lang.Object resolveObject(java.lang.Object obj) throws IOException {
        throw new IOException("Method resolveObject not supported");
    }

    @Override
    public final int skipBytes(int len) throws IOException {
        try {
            this.readObjectState.readData(this);
            byte[] buf = new byte[len];
            this.orbStream.read_octet_array(buf, 0, len);
            return len;
        }
        catch (MARSHAL marshalException) {
            this.handleOptionalDataMarshalException(marshalException, false);
            throw marshalException;
        }
        catch (Error e) {
            IOException exc = new IOException(e.getMessage());
            exc.initCause(e);
            throw exc;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private java.lang.Object inputObject(Class<?> clz, String repositoryID, CodeBase sender, int offset) throws IOException, ClassNotFoundException {
        block32: {
            this.currentClassDesc = ObjectStreamClass.lookup(clz);
            this.currentClass = this.currentClassDesc.forClass();
            if (this.currentClass == null) {
                throw new ClassNotFoundException(this.currentClassDesc.getName());
            }
            try {
                if (this.currentClassDesc.isExternalizable()) {
                    try {
                        v0 = this.currentObject = this.currentClass == null ? null : this.currentClassDesc.newInstance();
                        if (this.currentObject != null) {
                            this.activeRecursionMgr.addObject(offset, this.currentObject);
                            this.readFormatVersion();
                            ext = (Externalizable)this.currentObject;
                            ext.readExternal(this);
                        }
                        break block32;
                    }
                    catch (InvocationTargetException e) {
                        exc = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
                        exc.initCause(e);
                        throw exc;
                    }
                    catch (UnsupportedOperationException e) {
                        exc = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
                        exc.initCause(e);
                        throw exc;
                    }
                    catch (InstantiationException e) {
                        exc = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
                        exc.initCause(e);
                        throw exc;
                    }
                }
                currdesc = this.currentClassDesc;
                currclass = this.currentClass;
                spBase = this.spClass;
                currclass = this.currentClass;
                for (currdesc = this.currentClassDesc; currdesc != null && currdesc.isSerializable(); currdesc = currdesc.getSuperclass()) {
                    cc = currdesc.forClass();
                    for (cl = currclass; cl != null && cc != cl; cl = cl.getSuperclass()) {
                    }
                    ++this.spClass;
                    if (this.spClass >= this.classes.length) {
                        newlen = this.classes.length * 2;
                        newclasses = new Class[newlen];
                        newclassdesc = new ObjectStreamClass[newlen];
                        System.arraycopy(this.classes, 0, newclasses, 0, this.classes.length);
                        System.arraycopy(this.classdesc, 0, newclassdesc, 0, this.classes.length);
                        this.classes = newclasses;
                        this.classdesc = newclassdesc;
                    }
                    if (cl == null) {
                        this.classdesc[this.spClass] = currdesc;
                        this.classes[this.spClass] = null;
                        continue;
                    }
                    this.classdesc[this.spClass] = currdesc;
                    this.classes[this.spClass] = cl;
                    currclass = cl.getSuperclass();
                }
                try {
                    this.currentObject = this.currentClass == null ? null : this.currentClassDesc.newInstance();
                    this.activeRecursionMgr.addObject(offset, this.currentObject);
                }
                catch (InvocationTargetException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                catch (UnsupportedOperationException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                catch (InstantiationException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                try {
                    while (this.spClass > spBase) {
                        this.currentClassDesc = this.classdesc[this.spClass];
                        this.currentClass = this.classes[this.spClass];
                        if (this.classes[this.spClass] != null) {
                            oldState = this.readObjectState;
                            this.setState(IIOPInputStream.DEFAULT_STATE);
                            try {
                                if (this.currentClassDesc.hasWriteObject()) {
                                    this.readFormatVersion();
                                    calledDefaultWriteObject = this.readBoolean();
                                    this.readObjectState.beginUnmarshalCustomValue(this, calledDefaultWriteObject, this.currentClassDesc.readObjectMethod != null);
                                } else if (this.currentClassDesc.hasReadObject()) {
                                    this.setState(IIOPInputStream.IN_READ_OBJECT_REMOTE_NOT_CUSTOM_MARSHALED);
                                }
                                if (!(this.invokeObjectReader(this.currentClassDesc, this.currentObject, this.currentClass) && this.readObjectState != IIOPInputStream.IN_READ_OBJECT_DEFAULTS_SENT || (fields = this.currentClassDesc.getFieldsNoCopy()).length <= 0)) {
                                    this.inputClassFields(this.currentObject, this.currentClass, fields, sender);
                                }
                                if (!this.currentClassDesc.hasWriteObject()) ** GOTO lbl100
                                this.readObjectState.endUnmarshalCustomValue(this);
                            }
                            finally {
                                this.setState(oldState);
                            }
                        } else {
                            fields = this.currentClassDesc.getFieldsNoCopy();
                            if (fields.length > 0) {
                                this.inputClassFields(null, this.currentClass, fields, sender);
                            }
                        }
lbl100:
                        // 5 sources

                        --this.spClass;
                    }
                }
                finally {
                    this.spClass = spBase;
                }
            }
            finally {
                this.activeRecursionMgr.removeObject(offset);
            }
        }
        return this.currentObject;
    }

    private Vector<FullValueDescription> getOrderedDescriptions(String repositoryID, CodeBase sender) {
        Vector<FullValueDescription> descs = new Vector<FullValueDescription>();
        if (sender == null) {
            return descs;
        }
        FullValueDescription aFVD = sender.meta(repositoryID);
        while (aFVD != null) {
            descs.insertElementAt(aFVD, 0);
            if (aFVD.base_value != null && !kEmptyStr.equals(aFVD.base_value)) {
                aFVD = sender.meta(aFVD.base_value);
                continue;
            }
            return descs;
        }
        return descs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private java.lang.Object inputObjectUsingFVD(Class<?> clz, String repositoryID, CodeBase sender, int offset) throws IOException, ClassNotFoundException {
        spBase = this.spClass;
        try {
            currdesc = this.currentClassDesc = ObjectStreamClass.lookup(clz);
            this.currentClass = clz;
            currclass = this.currentClass;
            if (this.currentClassDesc.isExternalizable()) {
                try {
                    v0 = this.currentObject = this.currentClass == null ? null : this.currentClassDesc.newInstance();
                    if (this.currentObject == null) ** GOTO lbl148
                    this.activeRecursionMgr.addObject(offset, this.currentObject);
                    this.readFormatVersion();
                    ext = (Externalizable)this.currentObject;
                    ext.readExternal(this);
                }
                catch (InvocationTargetException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                catch (UnsupportedOperationException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                catch (InstantiationException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
            } else {
                currclass = this.currentClass;
                for (currdesc = this.currentClassDesc; currdesc != null && currdesc.isSerializable(); currdesc = currdesc.getSuperclass()) {
                    cc = currdesc.forClass();
                    for (cl = currclass; cl != null && cc != cl; cl = cl.getSuperclass()) {
                    }
                    ++this.spClass;
                    if (this.spClass >= this.classes.length) {
                        newlen = this.classes.length * 2;
                        newclasses = new Class[newlen];
                        newclassdesc = new ObjectStreamClass[newlen];
                        System.arraycopy(this.classes, 0, newclasses, 0, this.classes.length);
                        System.arraycopy(this.classdesc, 0, newclassdesc, 0, this.classes.length);
                        this.classes = newclasses;
                        this.classdesc = newclassdesc;
                    }
                    if (cl == null) {
                        this.classdesc[this.spClass] = currdesc;
                        this.classes[this.spClass] = null;
                        continue;
                    }
                    this.classdesc[this.spClass] = currdesc;
                    this.classes[this.spClass] = cl;
                    currclass = cl.getSuperclass();
                }
                try {
                    this.currentObject = this.currentClass == null ? null : this.currentClassDesc.newInstance();
                    this.activeRecursionMgr.addObject(offset, this.currentObject);
                }
                catch (InvocationTargetException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                catch (UnsupportedOperationException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                catch (InstantiationException e) {
                    exc = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
                    exc.initCause(e);
                    throw exc;
                }
                fvdsList = this.getOrderedDescriptions(repositoryID, sender).elements();
                while (fvdsList.hasMoreElements() && this.spClass > spBase) {
                    fvd = fvdsList.nextElement();
                    repIDForFVD = this.vhandler.getClassName(fvd.id);
                    repIDForClass = this.vhandler.getClassName(this.vhandler.getRMIRepositoryID(this.currentClass));
                    while (this.spClass > spBase && !repIDForFVD.equals(repIDForClass)) {
                        pos = this.findNextClass(repIDForFVD, this.classes, this.spClass, spBase);
                        if (pos != -1) {
                            this.spClass = pos;
                            this.currentClass = this.classes[this.spClass];
                            currclass = this.currentClass;
                            repIDForClass = this.vhandler.getClassName(this.vhandler.getRMIRepositoryID(this.currentClass));
                            continue;
                        }
                        if (fvd.is_custom) {
                            this.readFormatVersion();
                            calledDefaultWriteObject = this.readBoolean();
                            if (calledDefaultWriteObject) {
                                this.inputClassFields(null, null, null, fvd.members, sender);
                            }
                            if (this.getStreamFormatVersion() == 2) {
                                ((ValueInputStream)this.getOrbStream()).start_value();
                                ((ValueInputStream)this.getOrbStream()).end_value();
                            }
                        } else {
                            this.inputClassFields(null, this.currentClass, null, fvd.members, sender);
                        }
                        if (fvdsList.hasMoreElements()) {
                            fvd = fvdsList.nextElement();
                            repIDForFVD = this.vhandler.getClassName(fvd.id);
                            continue;
                        }
                        calledDefaultWriteObject = this.currentObject;
                        return calledDefaultWriteObject;
                    }
                    currdesc = this.currentClassDesc = ObjectStreamClass.lookup(this.currentClass);
                    if (!repIDForClass.equals("java.lang.Object")) {
                        oldState = this.readObjectState;
                        this.setState(IIOPInputStream.DEFAULT_STATE);
                        try {
                            if (fvd.is_custom) {
                                this.readFormatVersion();
                                calledDefaultWriteObject = this.readBoolean();
                                this.readObjectState.beginUnmarshalCustomValue(this, calledDefaultWriteObject, this.currentClassDesc.readObjectMethod != null);
                            }
                            usedReadObject = false;
                            try {
                                if (!fvd.is_custom && this.currentClassDesc.hasReadObject()) {
                                    this.setState(IIOPInputStream.IN_READ_OBJECT_REMOTE_NOT_CUSTOM_MARSHALED);
                                }
                                this.defaultReadObjectFVDMembers = fvd.members;
                                usedReadObject = this.invokeObjectReader(this.currentClassDesc, this.currentObject, this.currentClass);
                            }
                            finally {
                                this.defaultReadObjectFVDMembers = null;
                            }
                            if (!usedReadObject || this.readObjectState == IIOPInputStream.IN_READ_OBJECT_DEFAULTS_SENT) {
                                this.inputClassFields(this.currentObject, this.currentClass, currdesc, fvd.members, sender);
                            }
                            if (fvd.is_custom) {
                                this.readObjectState.endUnmarshalCustomValue(this);
                            }
                        }
                        finally {
                            this.setState(oldState);
                        }
                        this.currentClass = this.classes[--this.spClass];
                        currclass = this.currentClass;
                        continue;
                    }
                    this.inputClassFields(null, this.currentClass, null, fvd.members, sender);
                    while (fvdsList.hasMoreElements()) {
                        fvd = fvdsList.nextElement();
                        if (fvd.is_custom) {
                            this.skipCustomUsingFVD(fvd.members, sender);
                            continue;
                        }
                        this.inputClassFields(null, this.currentClass, null, fvd.members, sender);
                    }
                }
                while (fvdsList.hasMoreElements()) {
                    fvd = fvdsList.nextElement();
                    if (fvd.is_custom) {
                        this.skipCustomUsingFVD(fvd.members, sender);
                        continue;
                    }
                    this.throwAwayData(fvd.members, sender);
                }
            }
lbl148:
            // 3 sources

            var8_8 = this.currentObject;
            return var8_8;
        }
        finally {
            this.spClass = spBase;
            this.activeRecursionMgr.removeObject(offset);
        }
    }

    private java.lang.Object skipObjectUsingFVD(String repositoryID, CodeBase sender) throws IOException, ClassNotFoundException {
        Enumeration<FullValueDescription> fvdsList = this.getOrderedDescriptions(repositoryID, sender).elements();
        while (fvdsList.hasMoreElements()) {
            FullValueDescription fvd = fvdsList.nextElement();
            String repIDForFVD = this.vhandler.getClassName(fvd.id);
            if (repIDForFVD.equals("java.lang.Object")) continue;
            if (fvd.is_custom) {
                this.readFormatVersion();
                boolean calledDefaultWriteObject = this.readBoolean();
                if (calledDefaultWriteObject) {
                    this.inputClassFields(null, null, null, fvd.members, sender);
                }
                if (this.getStreamFormatVersion() != 2) continue;
                ((ValueInputStream)((java.lang.Object)this.getOrbStream())).start_value();
                ((ValueInputStream)((java.lang.Object)this.getOrbStream())).end_value();
                continue;
            }
            this.inputClassFields(null, null, null, fvd.members, sender);
        }
        return null;
    }

    private int findNextClass(String classname, Class<?>[] classes, int _spClass, int _spBase) {
        for (int i = _spClass; i > _spBase; --i) {
            if (!classname.equals(classes[i].getName())) continue;
            return i;
        }
        return -1;
    }

    private boolean invokeObjectReader(ObjectStreamClass osc, java.lang.Object obj, Class<?> aclass) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        if (osc.readObjectMethod == null) {
            return false;
        }
        try {
            osc.readObjectMethod.invoke(obj, this.readObjectArgList);
            return true;
        }
        catch (InvocationTargetException e) {
            Throwable t2 = e.getTargetException();
            if (t2 instanceof ClassNotFoundException) {
                throw (ClassNotFoundException)t2;
            }
            if (t2 instanceof IOException) {
                throw (IOException)t2;
            }
            if (t2 instanceof RuntimeException) {
                throw (RuntimeException)t2;
            }
            if (t2 instanceof Error) {
                throw (Error)t2;
            }
            throw new Error("internal error");
        }
        catch (IllegalAccessException e) {
            return false;
        }
    }

    private void resetStream() throws IOException {
        int i;
        if (this.classes == null) {
            this.classes = new Class[20];
        } else {
            for (i = 0; i < this.classes.length; ++i) {
                this.classes[i] = null;
            }
        }
        if (this.classdesc == null) {
            this.classdesc = new ObjectStreamClass[20];
        } else {
            for (i = 0; i < this.classdesc.length; ++i) {
                this.classdesc[i] = null;
            }
        }
        this.spClass = 0;
        if (this.callbacks != null) {
            this.callbacks.setSize(0);
        }
    }

    private void inputPrimitiveField(java.lang.Object o, Class<?> cl, ObjectStreamField field2) throws InvalidClassException, IOException {
        try {
            switch (field2.getTypeCode()) {
                case 'B': {
                    byte byteValue = this.orbStream.read_octet();
                    bridge.putByte(o, field2.getFieldID(), byteValue);
                    break;
                }
                case 'Z': {
                    boolean booleanValue = this.orbStream.read_boolean();
                    bridge.putBoolean(o, field2.getFieldID(), booleanValue);
                    break;
                }
                case 'C': {
                    char charValue = this.orbStream.read_wchar();
                    bridge.putChar(o, field2.getFieldID(), charValue);
                    break;
                }
                case 'S': {
                    short shortValue = this.orbStream.read_short();
                    bridge.putShort(o, field2.getFieldID(), shortValue);
                    break;
                }
                case 'I': {
                    int intValue = this.orbStream.read_long();
                    bridge.putInt(o, field2.getFieldID(), intValue);
                    break;
                }
                case 'J': {
                    long longValue = this.orbStream.read_longlong();
                    bridge.putLong(o, field2.getFieldID(), longValue);
                    break;
                }
                case 'F': {
                    float floatValue = this.orbStream.read_float();
                    bridge.putFloat(o, field2.getFieldID(), floatValue);
                    break;
                }
                case 'D': {
                    double doubleValue = this.orbStream.read_double();
                    bridge.putDouble(o, field2.getFieldID(), doubleValue);
                    break;
                }
                default: {
                    throw new InvalidClassException(cl.getName());
                }
            }
        }
        catch (IllegalArgumentException e) {
            ClassCastException cce = new ClassCastException("Assigning instance of class " + field2.getType().getName() + " to field " + this.currentClassDesc.getName() + '#' + field2.getField().getName());
            cce.initCause(e);
            throw cce;
        }
    }

    private java.lang.Object inputObjectField(ValueMember field2, CodeBase sender) throws IndirectionException, ClassNotFoundException, IOException, StreamCorruptedException {
        java.lang.Object objectValue = null;
        Class<?> type = null;
        String id = field2.id;
        try {
            type = this.vhandler.getClassFromType(id);
        }
        catch (ClassNotFoundException cnfe) {
            type = null;
        }
        String signature = null;
        if (type != null) {
            signature = ValueUtility.getSignature(field2);
        }
        if (signature != null && (signature.equals("Ljava/lang/Object;") || signature.equals("Ljava/io/Serializable;") || signature.equals("Ljava/io/Externalizable;"))) {
            objectValue = Util.readAny(this.orbStream);
        } else {
            int callType = 2;
            if (!this.vhandler.isSequence(id)) {
                if (field2.type.kind().value() == kRemoteTypeCode.kind().value()) {
                    callType = 0;
                } else if (type != null && type.isInterface() && (this.vhandler.isAbstractBase(type) || ObjectStreamClassCorbaExt.isAbstractInterface(type))) {
                    callType = 1;
                }
            }
            switch (callType) {
                case 0: {
                    if (type != null) {
                        objectValue = Utility.readObjectAndNarrow(this.orbStream, type);
                        break;
                    }
                    objectValue = this.orbStream.read_Object();
                    break;
                }
                case 1: {
                    if (type != null) {
                        objectValue = Utility.readAbstractAndNarrow(this.orbStream, type);
                        break;
                    }
                    objectValue = this.orbStream.read_abstract_interface();
                    break;
                }
                case 2: {
                    if (type != null) {
                        objectValue = this.orbStream.read_value(type);
                        break;
                    }
                    objectValue = this.orbStream.read_value();
                    break;
                }
                default: {
                    throw new StreamCorruptedException("Unknown callType: " + callType);
                }
            }
        }
        return objectValue;
    }

    private java.lang.Object inputObjectField(ObjectStreamField field2) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IndirectionException, IOException {
        Class<?> fieldType;
        if (ObjectStreamClassCorbaExt.isAny(field2.getTypeString())) {
            return Util.readAny(this.orbStream);
        }
        java.lang.Object objectValue = null;
        Class<?> actualType = fieldType = field2.getType();
        int callType = 2;
        boolean narrow = false;
        if (fieldType.isInterface()) {
            boolean loadStubClass = false;
            if (Remote.class.isAssignableFrom(fieldType)) {
                callType = 0;
            } else if (Object.class.isAssignableFrom(fieldType)) {
                callType = 0;
                loadStubClass = true;
            } else if (this.vhandler.isAbstractBase(fieldType)) {
                callType = 1;
                loadStubClass = true;
            } else if (ObjectStreamClassCorbaExt.isAbstractInterface(fieldType)) {
                callType = 1;
            }
            if (loadStubClass) {
                try {
                    String codebase = Util.getCodebase(fieldType);
                    String repID = this.vhandler.createForAnyType(fieldType);
                    Class<?> stubType = Utility.loadStubClass(repID, codebase, fieldType);
                    actualType = stubType;
                }
                catch (ClassNotFoundException e) {
                    narrow = true;
                }
            } else {
                narrow = true;
            }
        }
        switch (callType) {
            case 0: {
                if (!narrow) {
                    objectValue = this.orbStream.read_Object(actualType);
                    break;
                }
                objectValue = Utility.readObjectAndNarrow(this.orbStream, actualType);
                break;
            }
            case 1: {
                if (!narrow) {
                    objectValue = this.orbStream.read_abstract_interface(actualType);
                    break;
                }
                objectValue = Utility.readAbstractAndNarrow(this.orbStream, actualType);
                break;
            }
            case 2: {
                objectValue = this.orbStream.read_value(actualType);
                break;
            }
            default: {
                throw new StreamCorruptedException("Unknown callType: " + callType);
            }
        }
        return objectValue;
    }

    private final boolean mustUseRemoteValueMembers() {
        return this.defaultReadObjectFVDMembers != null;
    }

    @Override
    void readFields(Map<String, java.lang.Object> fieldToValueMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        if (this.mustUseRemoteValueMembers()) {
            this.inputRemoteMembersForReadFields(fieldToValueMap);
        } else {
            this.inputCurrentClassFieldsForReadFields(fieldToValueMap);
        }
    }

    private final void inputRemoteMembersForReadFields(Map<String, java.lang.Object> fieldToValueMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        ValueMember[] fields = this.defaultReadObjectFVDMembers;
        try {
            block15: for (int i = 0; i < fields.length; ++i) {
                switch (fields[i].type.kind().value()) {
                    case 10: {
                        byte byteValue = this.orbStream.read_octet();
                        fieldToValueMap.put(fields[i].name, new Byte(byteValue));
                        continue block15;
                    }
                    case 8: {
                        boolean booleanValue = this.orbStream.read_boolean();
                        fieldToValueMap.put(fields[i].name, new Boolean(booleanValue));
                        continue block15;
                    }
                    case 9: 
                    case 26: {
                        char charValue = this.orbStream.read_wchar();
                        fieldToValueMap.put(fields[i].name, new Character(charValue));
                        continue block15;
                    }
                    case 2: {
                        short shortValue = this.orbStream.read_short();
                        fieldToValueMap.put(fields[i].name, new Short(shortValue));
                        continue block15;
                    }
                    case 3: {
                        int intValue = this.orbStream.read_long();
                        fieldToValueMap.put(fields[i].name, new Integer(intValue));
                        continue block15;
                    }
                    case 23: {
                        long longValue = this.orbStream.read_longlong();
                        fieldToValueMap.put(fields[i].name, new Long(longValue));
                        continue block15;
                    }
                    case 6: {
                        float floatValue = this.orbStream.read_float();
                        fieldToValueMap.put(fields[i].name, new Float(floatValue));
                        continue block15;
                    }
                    case 7: {
                        double doubleValue = this.orbStream.read_double();
                        fieldToValueMap.put(fields[i].name, new Double(doubleValue));
                        continue block15;
                    }
                    case 14: 
                    case 29: 
                    case 30: {
                        java.lang.Object objectValue = null;
                        try {
                            objectValue = this.inputObjectField(fields[i], this.cbSender);
                        }
                        catch (IndirectionException cdrie) {
                            objectValue = this.activeRecursionMgr.getObject(cdrie.offset);
                        }
                        fieldToValueMap.put(fields[i].name, objectValue);
                        continue block15;
                    }
                    default: {
                        throw new StreamCorruptedException("Unknown kind: " + fields[i].type.kind().value());
                    }
                }
            }
        }
        catch (Throwable t2) {
            StreamCorruptedException result = new StreamCorruptedException(t2.getMessage());
            result.initCause(t2);
            throw result;
        }
    }

    private final void inputCurrentClassFieldsForReadFields(Map<String, java.lang.Object> fieldToValueMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        int i;
        ObjectStreamField[] fields = this.currentClassDesc.getFieldsNoCopy();
        int primFields = fields.length - this.currentClassDesc.objFields;
        block12: for (i = 0; i < primFields; ++i) {
            switch (fields[i].getTypeCode()) {
                case 'B': {
                    byte byteValue = this.orbStream.read_octet();
                    fieldToValueMap.put(fields[i].getName(), new Byte(byteValue));
                    continue block12;
                }
                case 'Z': {
                    boolean booleanValue = this.orbStream.read_boolean();
                    fieldToValueMap.put(fields[i].getName(), new Boolean(booleanValue));
                    continue block12;
                }
                case 'C': {
                    char charValue = this.orbStream.read_wchar();
                    fieldToValueMap.put(fields[i].getName(), new Character(charValue));
                    continue block12;
                }
                case 'S': {
                    short shortValue = this.orbStream.read_short();
                    fieldToValueMap.put(fields[i].getName(), new Short(shortValue));
                    continue block12;
                }
                case 'I': {
                    int intValue = this.orbStream.read_long();
                    fieldToValueMap.put(fields[i].getName(), new Integer(intValue));
                    continue block12;
                }
                case 'J': {
                    long longValue = this.orbStream.read_longlong();
                    fieldToValueMap.put(fields[i].getName(), new Long(longValue));
                    continue block12;
                }
                case 'F': {
                    float floatValue = this.orbStream.read_float();
                    fieldToValueMap.put(fields[i].getName(), new Float(floatValue));
                    continue block12;
                }
                case 'D': {
                    double doubleValue = this.orbStream.read_double();
                    fieldToValueMap.put(fields[i].getName(), new Double(doubleValue));
                    continue block12;
                }
                default: {
                    throw new InvalidClassException(this.currentClassDesc.getName());
                }
            }
        }
        if (this.currentClassDesc.objFields > 0) {
            for (i = primFields; i < fields.length; ++i) {
                java.lang.Object objectValue = null;
                try {
                    objectValue = this.inputObjectField(fields[i]);
                }
                catch (IndirectionException cdrie) {
                    objectValue = this.activeRecursionMgr.getObject(cdrie.offset);
                }
                fieldToValueMap.put(fields[i].getName(), objectValue);
            }
        }
    }

    private void inputClassFields(java.lang.Object o, Class<?> cl, ObjectStreamField[] fields, CodeBase sender) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        int i;
        int primFields = fields.length - this.currentClassDesc.objFields;
        if (o != null) {
            for (i = 0; i < primFields; ++i) {
                if (fields[i].getField() == null) continue;
                this.inputPrimitiveField(o, cl, fields[i]);
            }
        }
        if (this.currentClassDesc.objFields > 0) {
            for (i = primFields; i < fields.length; ++i) {
                java.lang.Object objectValue = null;
                try {
                    objectValue = this.inputObjectField(fields[i]);
                }
                catch (IndirectionException cdrie) {
                    objectValue = this.activeRecursionMgr.getObject(cdrie.offset);
                }
                if (o == null || fields[i].getField() == null) continue;
                try {
                    bridge.putObject(o, fields[i].getFieldID(), objectValue);
                    continue;
                }
                catch (IllegalArgumentException e) {
                    ClassCastException exc = new ClassCastException("Assigning instance of class " + objectValue.getClass().getName() + " to field " + this.currentClassDesc.getName() + '#' + fields[i].getField().getName());
                    exc.initCause(e);
                    throw exc;
                }
            }
        }
    }

    private void inputClassFields(java.lang.Object o, Class<?> cl, ObjectStreamClass osc, ValueMember[] fields, CodeBase sender) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        try {
            block19: for (int i = 0; i < fields.length; ++i) {
                try {
                    switch (fields[i].type.kind().value()) {
                        case 10: {
                            byte byteValue = this.orbStream.read_octet();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setByteField(o, cl, fields[i].name, byteValue);
                            break;
                        }
                        case 8: {
                            boolean booleanValue = this.orbStream.read_boolean();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setBooleanField(o, cl, fields[i].name, booleanValue);
                            break;
                        }
                        case 9: 
                        case 26: {
                            char charValue = this.orbStream.read_wchar();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setCharField(o, cl, fields[i].name, charValue);
                            break;
                        }
                        case 2: {
                            short shortValue = this.orbStream.read_short();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setShortField(o, cl, fields[i].name, shortValue);
                            break;
                        }
                        case 3: {
                            int intValue = this.orbStream.read_long();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setIntField(o, cl, fields[i].name, intValue);
                            break;
                        }
                        case 23: {
                            long longValue = this.orbStream.read_longlong();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setLongField(o, cl, fields[i].name, longValue);
                            break;
                        }
                        case 6: {
                            float floatValue = this.orbStream.read_float();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setFloatField(o, cl, fields[i].name, floatValue);
                            break;
                        }
                        case 7: {
                            double doubleValue = this.orbStream.read_double();
                            if (o == null || !osc.hasField(fields[i])) continue block19;
                            IIOPInputStream.setDoubleField(o, cl, fields[i].name, doubleValue);
                            break;
                        }
                        case 14: 
                        case 29: 
                        case 30: {
                            java.lang.Object objectValue = null;
                            try {
                                objectValue = this.inputObjectField(fields[i], sender);
                            }
                            catch (IndirectionException cdrie) {
                                objectValue = this.activeRecursionMgr.getObject(cdrie.offset);
                            }
                            if (o == null) continue block19;
                            try {
                                if (!osc.hasField(fields[i])) continue block19;
                                IIOPInputStream.setObjectField(o, cl, fields[i].name, objectValue);
                                break;
                            }
                            catch (IllegalArgumentException e) {
                                ClassCastException cce = new ClassCastException("Assigning instance of class " + objectValue.getClass().getName() + " to field " + fields[i].name);
                                cce.initCause(e);
                                throw cce;
                            }
                        }
                        default: {
                            throw new StreamCorruptedException("Unknown kind: " + fields[i].type.kind().value());
                        }
                    }
                    continue;
                }
                catch (IllegalArgumentException e) {
                    ClassCastException cce = new ClassCastException("Assigning instance of class " + fields[i].id + " to field " + this.currentClassDesc.getName() + '#' + fields[i].name);
                    cce.initCause(e);
                    throw cce;
                }
            }
        }
        catch (Throwable t2) {
            StreamCorruptedException sce = new StreamCorruptedException(t2.getMessage());
            sce.initCause(t2);
            throw sce;
        }
    }

    private void skipCustomUsingFVD(ValueMember[] fields, CodeBase sender) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        this.readFormatVersion();
        boolean calledDefaultWriteObject = this.readBoolean();
        if (calledDefaultWriteObject) {
            this.throwAwayData(fields, sender);
        }
        if (this.getStreamFormatVersion() == 2) {
            ((ValueInputStream)((java.lang.Object)this.getOrbStream())).start_value();
            ((ValueInputStream)((java.lang.Object)this.getOrbStream())).end_value();
        }
    }

    /*
     * Unable to fully structure code
     */
    private void throwAwayData(ValueMember[] fields, CodeBase sender) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
        block22: for (i = 0; i < fields.length; ++i) {
            try {
                switch (fields[i].type.kind().value()) {
                    case 10: {
                        this.orbStream.read_octet();
                        break;
                    }
                    case 8: {
                        this.orbStream.read_boolean();
                        break;
                    }
                    case 9: 
                    case 26: {
                        this.orbStream.read_wchar();
                        break;
                    }
                    case 2: {
                        this.orbStream.read_short();
                        break;
                    }
                    case 3: {
                        this.orbStream.read_long();
                        break;
                    }
                    case 23: {
                        this.orbStream.read_longlong();
                        break;
                    }
                    case 6: {
                        this.orbStream.read_float();
                        break;
                    }
                    case 7: {
                        this.orbStream.read_double();
                        break;
                    }
                    case 14: 
                    case 29: 
                    case 30: {
                        type = null;
                        id = fields[i].id;
                        try {
                            type = this.vhandler.getClassFromType(id);
                        }
                        catch (ClassNotFoundException cnfe) {
                            type = null;
                        }
                        signature = null;
                        if (type != null) {
                            signature = ValueUtility.getSignature(fields[i]);
                        }
                        try {
                            if (signature != null && (signature.equals("Ljava/lang/Object;") || signature.equals("Ljava/io/Serializable;") || signature.equals("Ljava/io/Externalizable;"))) {
                                Util.readAny(this.orbStream);
                                ** break;
                            }
                            callType = 2;
                            if (!this.vhandler.isSequence(id)) {
                                fieldFVD = sender.meta(fields[i].id);
                                if (IIOPInputStream.kRemoteTypeCode == fields[i].type) {
                                    callType = 0;
                                } else if (fieldFVD.is_abstract) {
                                    callType = 1;
                                }
                            }
                            switch (callType) {
                                case 0: {
                                    this.orbStream.read_Object();
                                    ** break;
                                }
                                case 1: {
                                    this.orbStream.read_abstract_interface();
                                    ** break;
                                }
                                case 2: {
                                    if (type != null) {
                                        this.orbStream.read_value(type);
                                        ** break;
                                    }
                                    this.orbStream.read_value();
                                    ** break;
                                }
                            }
                            throw new StreamCorruptedException("Unknown callType: " + callType);
lbl78:
                            // 5 sources

                            break;
                        }
                        catch (IndirectionException cdrie) {
                            continue block22;
                        }
                    }
                    default: {
                        throw new StreamCorruptedException("Unknown kind: " + fields[i].type.kind().value());
                    }
                }
                continue;
            }
            catch (IllegalArgumentException e) {
                cce = new ClassCastException("Assigning instance of class " + fields[i].id + " to field " + this.currentClassDesc.getName() + '#' + fields[i].name);
                cce.initCause(e);
                throw cce;
            }
        }
    }

    private static void setObjectField(java.lang.Object o, Class<?> c, String fieldName, java.lang.Object v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putObject(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetObjectField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)v.toString());
        }
    }

    private static void setBooleanField(java.lang.Object o, Class<?> c, String fieldName, boolean v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putBoolean(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetBooleanField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Boolean(v));
        }
    }

    private static void setByteField(java.lang.Object o, Class<?> c, String fieldName, byte v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putByte(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetByteField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Byte(v));
        }
    }

    private static void setCharField(java.lang.Object o, Class<?> c, String fieldName, char v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putChar(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetCharField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Character(v));
        }
    }

    private static void setShortField(java.lang.Object o, Class<?> c, String fieldName, short v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putShort(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetShortField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Short(v));
        }
    }

    private static void setIntField(java.lang.Object o, Class<?> c, String fieldName, int v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putInt(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetIntField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Integer(v));
        }
    }

    private static void setLongField(java.lang.Object o, Class<?> c, String fieldName, long v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putLong(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetLongField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Long(v));
        }
    }

    private static void setFloatField(java.lang.Object o, Class<?> c, String fieldName, float v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putFloat(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetFloatField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Float(v));
        }
    }

    private static void setDoubleField(java.lang.Object o, Class<?> c, String fieldName, double v) {
        try {
            Field fld = c.getDeclaredField(fieldName);
            long key = bridge.objectFieldOffset(fld);
            bridge.putDouble(o, key, v);
        }
        catch (Exception e) {
            throw utilWrapper.errorSetDoubleField(e, (java.lang.Object)fieldName, (java.lang.Object)o.toString(), (java.lang.Object)new Double(v));
        }
    }

    static class ActiveRecursionManager {
        private Map<Integer, java.lang.Object> offsetToObjectMap = new HashMap<Integer, java.lang.Object>();

        public void addObject(int offset, java.lang.Object value) {
            this.offsetToObjectMap.put(new Integer(offset), value);
        }

        public java.lang.Object getObject(int offset) throws IOException {
            Integer position = new Integer(offset);
            if (!this.offsetToObjectMap.containsKey(position)) {
                throw new IOException("Invalid indirection to offset " + offset);
            }
            return this.offsetToObjectMap.get(position);
        }

        public void removeObject(int offset) {
            this.offsetToObjectMap.remove(new Integer(offset));
        }

        public boolean containsObject(int offset) {
            return this.offsetToObjectMap.containsKey(new Integer(offset));
        }
    }
}

