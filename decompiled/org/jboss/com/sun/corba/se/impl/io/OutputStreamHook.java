/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamField;
import org.omg.CORBA.portable.ValueOutputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public abstract class OutputStreamHook
extends ObjectOutputStream {
    private HookPutFields putFields = null;
    protected byte streamFormatVersion = 1;
    protected WriteObjectState writeObjectState = NOT_IN_WRITE_OBJECT;
    protected static final WriteObjectState NOT_IN_WRITE_OBJECT = new DefaultState();
    protected static final WriteObjectState IN_WRITE_OBJECT = new InWriteObjectState();
    protected static final WriteObjectState WROTE_DEFAULT_DATA = new WroteDefaultDataState();
    protected static final WriteObjectState WROTE_CUSTOM_DATA = new WroteCustomDataState();

    abstract void writeField(ObjectStreamField var1, Object var2) throws IOException;

    @Override
    public void defaultWriteObject() throws IOException {
        this.writeObjectState.defaultWriteObject(this);
        this.defaultWriteObjectDelegate();
    }

    public abstract void defaultWriteObjectDelegate();

    @Override
    public ObjectOutputStream.PutField putFields() throws IOException {
        this.putFields = new HookPutFields();
        return this.putFields;
    }

    public byte getStreamFormatVersion() {
        return this.streamFormatVersion;
    }

    abstract ObjectStreamField[] getFieldsNoCopy();

    @Override
    public void writeFields() throws IOException {
        this.writeObjectState.defaultWriteObject(this);
        this.putFields.write(this);
    }

    public abstract OutputStream getOrbStream();

    protected abstract void beginOptionalCustomData();

    protected void setState(WriteObjectState newState) {
        this.writeObjectState = newState;
    }

    protected static class WroteCustomDataState
    extends InWriteObjectState {
        protected WroteCustomDataState() {
        }

        @Override
        public void exitWriteObject(OutputStreamHook stream) throws IOException {
            if (stream.getStreamFormatVersion() == 2) {
                ((ValueOutputStream)((Object)stream.getOrbStream())).end_value();
            }
            stream.setState(NOT_IN_WRITE_OBJECT);
        }

        @Override
        public void defaultWriteObject(OutputStreamHook stream) throws IOException {
            throw new IOException("Cannot call defaultWriteObject/writeFields after writing custom data in RMI-IIOP");
        }

        @Override
        public void writeData(OutputStreamHook stream) throws IOException {
        }
    }

    protected static class WroteDefaultDataState
    extends InWriteObjectState {
        protected WroteDefaultDataState() {
        }

        @Override
        public void exitWriteObject(OutputStreamHook stream) throws IOException {
            if (stream.getStreamFormatVersion() == 2) {
                stream.getOrbStream().write_long(0);
            }
            stream.setState(NOT_IN_WRITE_OBJECT);
        }

        @Override
        public void defaultWriteObject(OutputStreamHook stream) throws IOException {
            throw new IOException("Called defaultWriteObject/writeFields twice");
        }

        @Override
        public void writeData(OutputStreamHook stream) throws IOException {
            stream.beginOptionalCustomData();
            stream.setState(WROTE_CUSTOM_DATA);
        }
    }

    protected static class InWriteObjectState
    extends WriteObjectState {
        protected InWriteObjectState() {
        }

        @Override
        public void enterWriteObject(OutputStreamHook stream) throws IOException {
            throw new IOException("Internal state failure: Entered writeObject twice");
        }

        @Override
        public void exitWriteObject(OutputStreamHook stream) throws IOException {
            stream.getOrbStream().write_boolean(false);
            if (stream.getStreamFormatVersion() == 2) {
                stream.getOrbStream().write_long(0);
            }
            stream.setState(NOT_IN_WRITE_OBJECT);
        }

        @Override
        public void defaultWriteObject(OutputStreamHook stream) throws IOException {
            stream.getOrbStream().write_boolean(true);
            stream.setState(WROTE_DEFAULT_DATA);
        }

        @Override
        public void writeData(OutputStreamHook stream) throws IOException {
            stream.getOrbStream().write_boolean(false);
            stream.beginOptionalCustomData();
            stream.setState(WROTE_CUSTOM_DATA);
        }
    }

    protected static class DefaultState
    extends WriteObjectState {
        protected DefaultState() {
        }

        @Override
        public void enterWriteObject(OutputStreamHook stream) throws IOException {
            stream.setState(IN_WRITE_OBJECT);
        }
    }

    protected static class WriteObjectState {
        protected WriteObjectState() {
        }

        public void enterWriteObject(OutputStreamHook stream) throws IOException {
        }

        public void exitWriteObject(OutputStreamHook stream) throws IOException {
        }

        public void defaultWriteObject(OutputStreamHook stream) throws IOException {
        }

        public void writeData(OutputStreamHook stream) throws IOException {
        }
    }

    private class HookPutFields
    extends ObjectOutputStream.PutField {
        private Hashtable<String, Object> fields = new Hashtable();

        private HookPutFields() {
        }

        @Override
        public void put(String name, boolean value) {
            this.fields.put(name, new Boolean(value));
        }

        @Override
        public void put(String name, char value) {
            this.fields.put(name, new Character(value));
        }

        @Override
        public void put(String name, byte value) {
            this.fields.put(name, new Byte(value));
        }

        @Override
        public void put(String name, short value) {
            this.fields.put(name, new Short(value));
        }

        @Override
        public void put(String name, int value) {
            this.fields.put(name, new Integer(value));
        }

        @Override
        public void put(String name, long value) {
            this.fields.put(name, new Long(value));
        }

        @Override
        public void put(String name, float value) {
            this.fields.put(name, new Float(value));
        }

        @Override
        public void put(String name, double value) {
            this.fields.put(name, new Double(value));
        }

        @Override
        public void put(String name, Object value) {
            this.fields.put(name, value);
        }

        @Override
        public void write(ObjectOutput out) throws IOException {
            OutputStreamHook hook = (OutputStreamHook)out;
            ObjectStreamField[] osfields = hook.getFieldsNoCopy();
            for (int i = 0; i < osfields.length; ++i) {
                Object value = this.fields.get(osfields[i].getName());
                hook.writeField(osfields[i], value);
            }
        }
    }
}

