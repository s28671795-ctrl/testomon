/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA.portable.ValueInputStream;
import org.omg.CORBA_2_3.portable.InputStream;

public abstract class InputStreamHook
extends ObjectInputStream {
    static final OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
    static final UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
    protected ReadObjectState readObjectState = DEFAULT_STATE;
    protected static final ReadObjectState DEFAULT_STATE = new DefaultState();
    protected static final ReadObjectState IN_READ_OBJECT_OPT_DATA = new InReadObjectOptionalDataState();
    protected static final ReadObjectState IN_READ_OBJECT_NO_MORE_OPT_DATA = new InReadObjectNoMoreOptionalDataState();
    protected static final ReadObjectState IN_READ_OBJECT_DEFAULTS_SENT = new InReadObjectDefaultsSentState();
    protected static final ReadObjectState NO_READ_OBJECT_DEFAULTS_SENT = new NoReadObjectDefaultsSentState();
    protected static final ReadObjectState IN_READ_OBJECT_REMOTE_NOT_CUSTOM_MARSHALED = new InReadObjectRemoteDidNotUseWriteObjectState();
    protected static final ReadObjectState IN_READ_OBJECT_PAST_DEFAULTS_REMOTE_NOT_CUSTOM = new InReadObjectPastDefaultsRemoteDidNotUseWOState();

    @Override
    public void defaultReadObject() throws IOException, ClassNotFoundException, NotActiveException {
        this.readObjectState.beginDefaultReadObject(this);
        this.defaultReadObjectDelegate();
        this.readObjectState.endDefaultReadObject(this);
    }

    public abstract void defaultReadObjectDelegate();

    abstract void readFields(Map<String, Object> var1) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException;

    @Override
    public ObjectInputStream.GetField readFields() throws IOException, ClassNotFoundException, NotActiveException {
        HashMap<String, Object> fieldValueMap = new HashMap<String, Object>();
        this.readFields(fieldValueMap);
        this.readObjectState.endDefaultReadObject(this);
        return new HookGetFields(fieldValueMap);
    }

    protected void setState(ReadObjectState newState) {
        this.readObjectState = newState;
    }

    protected abstract byte getStreamFormatVersion();

    protected abstract InputStream getOrbStream();

    protected void throwOptionalDataIncompatibleException() {
        throw omgWrapper.rmiiiopOptionalDataIncompatible2();
    }

    protected static class NoReadObjectDefaultsSentState
    extends ReadObjectState {
        protected NoReadObjectDefaultsSentState() {
        }

        @Override
        public void endUnmarshalCustomValue(InputStreamHook stream) throws IOException {
            if (stream.getStreamFormatVersion() == 2) {
                ((ValueInputStream)((Object)stream.getOrbStream())).start_value();
                ((ValueInputStream)((Object)stream.getOrbStream())).end_value();
            }
            stream.setState(DEFAULT_STATE);
        }
    }

    protected static class InReadObjectNoMoreOptionalDataState
    extends InReadObjectOptionalDataState {
        protected InReadObjectNoMoreOptionalDataState() {
        }

        @Override
        public void readData(InputStreamHook stream) throws IOException {
            stream.throwOptionalDataIncompatibleException();
        }
    }

    protected static class InReadObjectOptionalDataState
    extends ReadObjectState {
        protected InReadObjectOptionalDataState() {
        }

        @Override
        public void beginUnmarshalCustomValue(InputStreamHook stream, boolean calledDefaultWriteObject, boolean hasReadObject) {
            throw utilWrapper.badBeginUnmarshalCustomValue();
        }

        @Override
        public void endUnmarshalCustomValue(InputStreamHook stream) throws IOException {
            if (stream.getStreamFormatVersion() == 2) {
                ((ValueInputStream)((Object)stream.getOrbStream())).end_value();
            }
            stream.setState(DEFAULT_STATE);
        }

        @Override
        public void beginDefaultReadObject(InputStreamHook stream) throws IOException {
            throw new StreamCorruptedException("Default data not sent or already read/passed");
        }
    }

    protected static class InReadObjectDefaultsSentState
    extends ReadObjectState {
        protected InReadObjectDefaultsSentState() {
        }

        @Override
        public void beginUnmarshalCustomValue(InputStreamHook stream, boolean calledDefaultWriteObject, boolean hasReadObject) {
            throw utilWrapper.badBeginUnmarshalCustomValue();
        }

        @Override
        public void endUnmarshalCustomValue(InputStreamHook stream) {
            if (stream.getStreamFormatVersion() == 2) {
                ((ValueInputStream)((Object)stream.getOrbStream())).start_value();
                ((ValueInputStream)((Object)stream.getOrbStream())).end_value();
            }
            stream.setState(DEFAULT_STATE);
        }

        @Override
        public void endDefaultReadObject(InputStreamHook stream) throws IOException {
            if (stream.getStreamFormatVersion() == 2) {
                ((ValueInputStream)((Object)stream.getOrbStream())).start_value();
            }
            stream.setState(IN_READ_OBJECT_OPT_DATA);
        }

        @Override
        public void readData(InputStreamHook stream) throws IOException {
            org.omg.CORBA.ORB orb = stream.getOrbStream().orb();
            if (orb == null || !(orb instanceof ORB)) {
                throw new StreamCorruptedException("Default data must be read first");
            }
            ORBVersion clientOrbVersion = ((ORB)orb).getORBVersion();
            if (ORBVersionFactory.getPEORB().compareTo(clientOrbVersion) <= 0 || clientOrbVersion.equals(ORBVersionFactory.getFOREIGN())) {
                throw new StreamCorruptedException("Default data must be read first");
            }
        }
    }

    protected static class InReadObjectPastDefaultsRemoteDidNotUseWOState
    extends ReadObjectState {
        protected InReadObjectPastDefaultsRemoteDidNotUseWOState() {
        }

        @Override
        public void beginUnmarshalCustomValue(InputStreamHook stream, boolean calledDefaultWriteObject, boolean hasReadObject) {
            throw utilWrapper.badBeginUnmarshalCustomValue();
        }

        @Override
        public void beginDefaultReadObject(InputStreamHook stream) throws IOException {
            throw new StreamCorruptedException("Default data already read");
        }

        @Override
        public void readData(InputStreamHook stream) {
            stream.throwOptionalDataIncompatibleException();
        }
    }

    protected static class InReadObjectRemoteDidNotUseWriteObjectState
    extends ReadObjectState {
        protected InReadObjectRemoteDidNotUseWriteObjectState() {
        }

        @Override
        public void beginUnmarshalCustomValue(InputStreamHook stream, boolean calledDefaultWriteObject, boolean hasReadObject) {
            throw utilWrapper.badBeginUnmarshalCustomValue();
        }

        @Override
        public void endDefaultReadObject(InputStreamHook stream) {
            stream.setState(IN_READ_OBJECT_PAST_DEFAULTS_REMOTE_NOT_CUSTOM);
        }

        @Override
        public void readData(InputStreamHook stream) {
            stream.throwOptionalDataIncompatibleException();
        }
    }

    protected static class DefaultState
    extends ReadObjectState {
        protected DefaultState() {
        }

        @Override
        public void beginUnmarshalCustomValue(InputStreamHook stream, boolean calledDefaultWriteObject, boolean hasReadObject) throws IOException {
            if (hasReadObject) {
                if (calledDefaultWriteObject) {
                    stream.setState(IN_READ_OBJECT_DEFAULTS_SENT);
                } else {
                    try {
                        if (stream.getStreamFormatVersion() == 2) {
                            ((ValueInputStream)((Object)stream.getOrbStream())).start_value();
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    stream.setState(IN_READ_OBJECT_OPT_DATA);
                }
            } else if (calledDefaultWriteObject) {
                stream.setState(NO_READ_OBJECT_DEFAULTS_SENT);
            } else {
                throw new StreamCorruptedException("No default data sent");
            }
        }
    }

    protected static class ReadObjectState {
        protected ReadObjectState() {
        }

        public void beginUnmarshalCustomValue(InputStreamHook stream, boolean calledDefaultWriteObject, boolean hasReadObject) throws IOException {
        }

        public void endUnmarshalCustomValue(InputStreamHook stream) throws IOException {
        }

        public void beginDefaultReadObject(InputStreamHook stream) throws IOException {
        }

        public void endDefaultReadObject(InputStreamHook stream) throws IOException {
        }

        public void readData(InputStreamHook stream) throws IOException {
        }
    }

    private class HookGetFields
    extends ObjectInputStream.GetField {
        private Map<String, Object> fields = null;

        HookGetFields(Map<String, Object> fields) {
            this.fields = fields;
        }

        @Override
        public ObjectStreamClass getObjectStreamClass() {
            return null;
        }

        @Override
        public boolean defaulted(String name) throws IOException, IllegalArgumentException {
            return !this.fields.containsKey(name);
        }

        @Override
        public boolean get(String name, boolean defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return (Boolean)this.fields.get(name);
        }

        @Override
        public char get(String name, char defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return ((Character)this.fields.get(name)).charValue();
        }

        @Override
        public byte get(String name, byte defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return (Byte)this.fields.get(name);
        }

        @Override
        public short get(String name, short defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return (Short)this.fields.get(name);
        }

        @Override
        public int get(String name, int defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return (Integer)this.fields.get(name);
        }

        @Override
        public long get(String name, long defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return (Long)this.fields.get(name);
        }

        @Override
        public float get(String name, float defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return ((Float)this.fields.get(name)).floatValue();
        }

        @Override
        public double get(String name, double defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return (Double)this.fields.get(name);
        }

        @Override
        public Object get(String name, Object defvalue) throws IOException, IllegalArgumentException {
            if (this.defaulted(name)) {
                return defvalue;
            }
            return this.fields.get(name);
        }

        public String toString() {
            return this.fields.toString();
        }
    }
}

