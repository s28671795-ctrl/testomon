/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Stack;
import javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClassCorbaExt;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamField;
import org.jboss.com.sun.corba.se.impl.io.OutputStreamHook;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.sun.corba.Bridge;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ValueOutputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class IIOPOutputStream
extends OutputStreamHook {
    private UtilSystemException wrapper = UtilSystemException.get("rpc.encoding");
    private static Bridge bridge = AccessController.doPrivileged(new PrivilegedAction<Bridge>(){

        @Override
        public Bridge run() {
            return Bridge.get();
        }
    });
    private OutputStream orbStream;
    private java.lang.Object currentObject = null;
    private ObjectStreamClass currentClassDesc = null;
    private int recursionDepth = 0;
    private int simpleWriteDepth = 0;
    private IOException abortIOException = null;
    private Stack<ObjectStreamClass> classDescStack = new Stack();
    private java.lang.Object[] writeObjectArgList = new java.lang.Object[]{this};

    @Override
    protected void beginOptionalCustomData() {
        if (this.streamFormatVersion == 2) {
            ValueOutputStream vout = (ValueOutputStream)((java.lang.Object)this.orbStream);
            vout.start_value(this.currentClassDesc.getRMIIIOPOptionalDataRepId());
        }
    }

    public final void setOrbStream(OutputStream os) {
        this.orbStream = os;
    }

    @Override
    public final OutputStream getOrbStream() {
        return this.orbStream;
    }

    public final void increaseRecursionDepth() {
        ++this.recursionDepth;
    }

    public final int decreaseRecursionDepth() {
        return --this.recursionDepth;
    }

    @Override
    public final void writeObjectOverride(java.lang.Object obj) throws IOException {
        this.writeObjectState.writeData(this);
        Util.writeAbstractObject(this.orbStream, obj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void simpleWriteObject(java.lang.Object obj, byte formatVersion) {
        byte oldStreamFormatVersion = this.streamFormatVersion;
        this.streamFormatVersion = formatVersion;
        java.lang.Object prevObject = this.currentObject;
        ObjectStreamClass prevClassDesc = this.currentClassDesc;
        ++this.simpleWriteDepth;
        try {
            this.outputObject(obj);
        }
        catch (IOException ee) {
            if (this.abortIOException == null) {
                this.abortIOException = ee;
            }
        }
        finally {
            this.streamFormatVersion = oldStreamFormatVersion;
            --this.simpleWriteDepth;
            this.currentObject = prevObject;
            this.currentClassDesc = prevClassDesc;
        }
        IOException pending = this.abortIOException;
        if (this.simpleWriteDepth == 0) {
            this.abortIOException = null;
        }
        if (pending != null) {
            bridge.throwException(pending);
        }
    }

    @Override
    ObjectStreamField[] getFieldsNoCopy() {
        return this.currentClassDesc.getFieldsNoCopy();
    }

    @Override
    public final void defaultWriteObjectDelegate() {
        try {
            if (this.currentObject == null || this.currentClassDesc == null) {
                throw new NotActiveException("defaultWriteObjectDelegate");
            }
            ObjectStreamField[] fields = this.currentClassDesc.getFieldsNoCopy();
            if (fields.length > 0) {
                this.outputClassFields(this.currentObject, this.currentClassDesc.forClass(), fields);
            }
        }
        catch (IOException ioe) {
            bridge.throwException(ioe);
        }
    }

    public final boolean enableReplaceObjectDelegate(boolean enable) {
        return false;
    }

    @Override
    protected final void annotateClass(Class<?> cl) throws IOException {
        throw new IOException("Method annotateClass not supported");
    }

    @Override
    public final void close() throws IOException {
    }

    @Override
    protected final void drain() throws IOException {
    }

    @Override
    public final void flush() throws IOException {
        try {
            this.orbStream.flush();
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    protected final java.lang.Object replaceObject(java.lang.Object obj) throws IOException {
        throw new IOException("Method replaceObject not supported");
    }

    @Override
    public final void reset() throws IOException {
        try {
            if (this.currentObject != null || this.currentClassDesc != null) {
                throw new IOException("Illegal call to reset");
            }
            this.abortIOException = null;
            if (this.classDescStack == null) {
                this.classDescStack = new Stack();
            } else {
                this.classDescStack.setSize(0);
            }
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void write(byte[] b) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_octet_array(b, 0, b.length);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void write(byte[] b, int off, int len) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_octet_array(b, off, len);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void write(int data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_octet((byte)(data & 0xFF));
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeBoolean(boolean data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_boolean(data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeByte(int data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_octet((byte)data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeBytes(String data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            byte[] buf = data.getBytes();
            this.orbStream.write_octet_array(buf, 0, buf.length);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeChar(int data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_wchar((char)data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeChars(String data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            char[] buf = data.toCharArray();
            this.orbStream.write_wchar_array(buf, 0, buf.length);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeDouble(double data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_double(data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeFloat(float data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_float(data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeInt(int data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_long(data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeLong(long data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_longlong(data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    public final void writeShort(int data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.orbStream.write_short((short)data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    @Override
    protected final void writeStreamHeader() throws IOException {
    }

    protected void internalWriteUTF(org.omg.CORBA.portable.OutputStream stream, String data) {
        stream.write_wstring(data);
    }

    @Override
    public final void writeUTF(String data) throws IOException {
        try {
            this.writeObjectState.writeData(this);
            this.internalWriteUTF(this.orbStream, data);
        }
        catch (Error e) {
            IOException ioexc = new IOException(e.getMessage());
            ioexc.initCause(e);
            throw ioexc;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void outputObject(java.lang.Object obj) throws IOException {
        this.currentObject = obj;
        Class<?> currclass = obj.getClass();
        this.currentClassDesc = ObjectStreamClass.lookup(currclass);
        if (this.currentClassDesc == null) {
            throw new NotSerializableException(currclass.getName());
        }
        if (this.currentClassDesc.isExternalizable()) {
            this.orbStream.write_octet(this.streamFormatVersion);
            Externalizable ext = (Externalizable)obj;
            ext.writeExternal(this);
        } else {
            int stackMark = this.classDescStack.size();
            try {
                ObjectStreamClass next;
                while ((next = this.currentClassDesc.getSuperclass()) != null) {
                    this.classDescStack.push(this.currentClassDesc);
                    this.currentClassDesc = next;
                }
                do {
                    OutputStreamHook.WriteObjectState oldState = this.writeObjectState;
                    try {
                        this.setState(NOT_IN_WRITE_OBJECT);
                        if (this.currentClassDesc.hasWriteObject()) {
                            this.invokeObjectWriter(this.currentClassDesc, obj);
                            continue;
                        }
                        this.defaultWriteObjectDelegate();
                    }
                    finally {
                        this.setState(oldState);
                    }
                } while (this.classDescStack.size() > stackMark && (this.currentClassDesc = this.classDescStack.pop()) != null);
            }
            finally {
                this.classDescStack.setSize(stackMark);
            }
        }
    }

    private void invokeObjectWriter(ObjectStreamClass osc, java.lang.Object obj) throws IOException {
        try {
            this.orbStream.write_octet(this.streamFormatVersion);
            this.writeObjectState.enterWriteObject(this);
            osc.writeObjectMethod.invoke(obj, this.writeObjectArgList);
            this.writeObjectState.exitWriteObject(this);
        }
        catch (InvocationTargetException e) {
            Throwable t2 = e.getTargetException();
            if (t2 instanceof IOException) {
                throw (IOException)t2;
            }
            if (t2 instanceof RuntimeException) {
                throw (RuntimeException)t2;
            }
            if (t2 instanceof Error) {
                throw (Error)t2;
            }
            throw new Error("invokeObjectWriter internal error", e);
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
    }

    @Override
    void writeField(ObjectStreamField field2, java.lang.Object value) throws IOException {
        switch (field2.getTypeCode()) {
            case 'B': {
                if (value == null) {
                    this.orbStream.write_octet((byte)0);
                    break;
                }
                this.orbStream.write_octet((Byte)value);
                break;
            }
            case 'C': {
                if (value == null) {
                    this.orbStream.write_wchar('\u0000');
                    break;
                }
                this.orbStream.write_wchar(((Character)value).charValue());
                break;
            }
            case 'F': {
                if (value == null) {
                    this.orbStream.write_float(0.0f);
                    break;
                }
                this.orbStream.write_float(((Float)value).floatValue());
                break;
            }
            case 'D': {
                if (value == null) {
                    this.orbStream.write_double(0.0);
                    break;
                }
                this.orbStream.write_double((Double)value);
                break;
            }
            case 'I': {
                if (value == null) {
                    this.orbStream.write_long(0);
                    break;
                }
                this.orbStream.write_long((Integer)value);
                break;
            }
            case 'J': {
                if (value == null) {
                    this.orbStream.write_longlong(0L);
                    break;
                }
                this.orbStream.write_longlong((Long)value);
                break;
            }
            case 'S': {
                if (value == null) {
                    this.orbStream.write_short((short)0);
                    break;
                }
                this.orbStream.write_short((Short)value);
                break;
            }
            case 'Z': {
                if (value == null) {
                    this.orbStream.write_boolean(false);
                    break;
                }
                this.orbStream.write_boolean((Boolean)value);
                break;
            }
            case 'L': 
            case '[': {
                this.writeObjectField(field2, value);
                break;
            }
            default: {
                throw new InvalidClassException(this.currentClassDesc.getName());
            }
        }
    }

    private void writeObjectField(ObjectStreamField field2, java.lang.Object objectValue) throws IOException {
        if (ObjectStreamClassCorbaExt.isAny(field2.getTypeString())) {
            Util.writeAny(this.orbStream, objectValue);
        } else {
            Class<?> type = field2.getType();
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
            switch (callType) {
                case 0: {
                    Util.writeRemoteObject(this.orbStream, objectValue);
                    break;
                }
                case 1: {
                    Util.writeAbstractObject(this.orbStream, objectValue);
                    break;
                }
                case 2: {
                    try {
                        this.orbStream.write_value((Serializable)objectValue, type);
                        break;
                    }
                    catch (ClassCastException cce) {
                        if (objectValue instanceof Serializable) {
                            throw cce;
                        }
                        Utility.throwNotSerializableForCorba(objectValue.getClass().getName());
                    }
                }
            }
        }
    }

    private void outputClassFields(java.lang.Object o, Class<?> cl, ObjectStreamField[] fields) throws IOException, InvalidClassException {
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].getField() == null) {
                throw new InvalidClassException(cl.getName(), "Nonexistent field " + fields[i].getName());
            }
            try {
                switch (fields[i].getTypeCode()) {
                    case 'B': {
                        byte byteValue = fields[i].getField().getByte(o);
                        this.orbStream.write_octet(byteValue);
                        break;
                    }
                    case 'C': {
                        char charValue = fields[i].getField().getChar(o);
                        this.orbStream.write_wchar(charValue);
                        break;
                    }
                    case 'F': {
                        float floatValue = fields[i].getField().getFloat(o);
                        this.orbStream.write_float(floatValue);
                        break;
                    }
                    case 'D': {
                        double doubleValue = fields[i].getField().getDouble(o);
                        this.orbStream.write_double(doubleValue);
                        break;
                    }
                    case 'I': {
                        int intValue = fields[i].getField().getInt(o);
                        this.orbStream.write_long(intValue);
                        break;
                    }
                    case 'J': {
                        long longValue = fields[i].getField().getLong(o);
                        this.orbStream.write_longlong(longValue);
                        break;
                    }
                    case 'S': {
                        short shortValue = fields[i].getField().getShort(o);
                        this.orbStream.write_short(shortValue);
                        break;
                    }
                    case 'Z': {
                        boolean booleanValue = fields[i].getField().getBoolean(o);
                        this.orbStream.write_boolean(booleanValue);
                        break;
                    }
                    case 'L': 
                    case '[': {
                        java.lang.Object objectValue = fields[i].getField().get(o);
                        this.writeObjectField(fields[i], objectValue);
                        break;
                    }
                    default: {
                        throw new InvalidClassException(cl.getName());
                    }
                }
                continue;
            }
            catch (IllegalAccessException exc) {
                throw this.wrapper.illegalFieldAccess(exc, (java.lang.Object)fields[i].getName());
            }
        }
    }
}

