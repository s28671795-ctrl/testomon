/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageFormatException;
import javax.jms.MessageNotWriteableException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.MarshallingSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.fusesource.hawtbuf.UTF8Buffer;

public class ActiveMQMapMessage
extends ActiveMQMessage
implements MapMessage {
    public static final byte DATA_STRUCTURE_TYPE = 25;
    protected transient Map<String, Object> map = new HashMap<String, Object>();

    private Object readResolve() throws ObjectStreamException {
        if (this.map == null) {
            this.map = new HashMap<String, Object>();
        }
        return this;
    }

    @Override
    public Message copy() {
        ActiveMQMapMessage copy = new ActiveMQMapMessage();
        this.copy(copy);
        return copy;
    }

    private void copy(ActiveMQMapMessage copy) {
        this.storeContent();
        super.copy(copy);
    }

    @Override
    public void beforeMarshall(WireFormat wireFormat) throws IOException {
        super.beforeMarshall(wireFormat);
        this.storeContent();
    }

    @Override
    public void clearUnMarshalledState() throws JMSException {
        super.clearUnMarshalledState();
        this.map.clear();
    }

    @Override
    public void storeContentAndClear() {
        this.storeContent();
        this.map.clear();
    }

    @Override
    public void storeContent() {
        try {
            if (this.getContent() == null && !this.map.isEmpty()) {
                ByteArrayOutputStream bytesOut;
                OutputStream os = bytesOut = new ByteArrayOutputStream();
                ActiveMQConnection connection = this.getConnection();
                if (connection != null && connection.isUseCompression()) {
                    this.compressed = true;
                    os = new DeflaterOutputStream(os);
                }
                DataOutputStream dataOut = new DataOutputStream(os);
                MarshallingSupport.marshalPrimitiveMap(this.map, dataOut);
                dataOut.close();
                this.setContent(bytesOut.toByteSequence());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isContentMarshalled() {
        return this.content != null || this.map == null || this.map.isEmpty();
    }

    private void loadContent() throws JMSException {
        try {
            if (this.getContent() != null && this.map.isEmpty()) {
                ByteSequence content = this.getContent();
                InputStream is = new ByteArrayInputStream(content);
                if (this.isCompressed()) {
                    is = new InflaterInputStream(is);
                }
                DataInputStream dataIn = new DataInputStream(is);
                this.map = MarshallingSupport.unmarshalPrimitiveMap(dataIn);
                dataIn.close();
            }
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    @Override
    public byte getDataStructureType() {
        return 25;
    }

    @Override
    public String getJMSXMimeType() {
        return "jms/map-message";
    }

    @Override
    public void clearBody() throws JMSException {
        super.clearBody();
        this.map.clear();
    }

    @Override
    public boolean getBoolean(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        if (value instanceof UTF8Buffer) {
            return Boolean.valueOf(value.toString());
        }
        if (value instanceof String) {
            return Boolean.valueOf(value.toString());
        }
        throw new MessageFormatException(" cannot read a boolean from " + value.getClass().getName());
    }

    @Override
    public byte getByte(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return 0;
        }
        if (value instanceof Byte) {
            return (Byte)value;
        }
        if (value instanceof UTF8Buffer) {
            return Byte.valueOf(value.toString());
        }
        if (value instanceof String) {
            return Byte.valueOf(value.toString());
        }
        throw new MessageFormatException(" cannot read a byte from " + value.getClass().getName());
    }

    @Override
    public short getShort(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return 0;
        }
        if (value instanceof Short) {
            return (Short)value;
        }
        if (value instanceof Byte) {
            return ((Byte)value).shortValue();
        }
        if (value instanceof UTF8Buffer) {
            return Short.valueOf(value.toString());
        }
        if (value instanceof String) {
            return Short.valueOf(value.toString());
        }
        throw new MessageFormatException(" cannot read a short from " + value.getClass().getName());
    }

    @Override
    public char getChar(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            throw new NullPointerException();
        }
        if (value instanceof Character) {
            return ((Character)value).charValue();
        }
        throw new MessageFormatException(" cannot read a char from " + value.getClass().getName());
    }

    @Override
    public int getInt(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        if (value instanceof Short) {
            return ((Short)value).intValue();
        }
        if (value instanceof Byte) {
            return ((Byte)value).intValue();
        }
        if (value instanceof UTF8Buffer) {
            return Integer.valueOf(value.toString());
        }
        if (value instanceof String) {
            return Integer.valueOf(value.toString());
        }
        throw new MessageFormatException(" cannot read an int from " + value.getClass().getName());
    }

    @Override
    public long getLong(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return 0L;
        }
        if (value instanceof Long) {
            return (Long)value;
        }
        if (value instanceof Integer) {
            return ((Integer)value).longValue();
        }
        if (value instanceof Short) {
            return ((Short)value).longValue();
        }
        if (value instanceof Byte) {
            return ((Byte)value).longValue();
        }
        if (value instanceof UTF8Buffer) {
            return Long.valueOf(value.toString());
        }
        if (value instanceof String) {
            return Long.valueOf(value.toString());
        }
        throw new MessageFormatException(" cannot read a long from " + value.getClass().getName());
    }

    @Override
    public float getFloat(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return 0.0f;
        }
        if (value instanceof Float) {
            return ((Float)value).floatValue();
        }
        if (value instanceof UTF8Buffer) {
            return Float.valueOf(value.toString()).floatValue();
        }
        if (value instanceof String) {
            return Float.valueOf(value.toString()).floatValue();
        }
        throw new MessageFormatException(" cannot read a float from " + value.getClass().getName());
    }

    @Override
    public double getDouble(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Double) {
            return (Double)value;
        }
        if (value instanceof Float) {
            return ((Float)value).floatValue();
        }
        if (value instanceof UTF8Buffer) {
            return Double.valueOf(value.toString());
        }
        if (value instanceof String) {
            return Double.valueOf(value.toString());
        }
        throw new MessageFormatException("Cannot read a double from " + value.getClass().getName());
    }

    @Override
    public String getString(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return null;
        }
        if (value instanceof byte[]) {
            throw new MessageFormatException("Use getBytes to read a byte array");
        }
        return value.toString();
    }

    @Override
    public byte[] getBytes(String name) throws JMSException {
        this.initializeReading();
        Object value = this.map.get(name);
        if (value == null) {
            return null;
        }
        if (value instanceof byte[]) {
            return (byte[])value;
        }
        throw new MessageFormatException(" cannot read a byte[] from " + value.getClass().getName());
    }

    @Override
    public Object getObject(String name) throws JMSException {
        this.initializeReading();
        Object result = this.map.get(name);
        if (result instanceof UTF8Buffer) {
            result = result.toString();
        }
        return result;
    }

    @Override
    public Enumeration<String> getMapNames() throws JMSException {
        this.initializeReading();
        return Collections.enumeration(this.map.keySet());
    }

    protected void put(String name, Object value) throws JMSException {
        if (name == null) {
            throw new IllegalArgumentException("The name of the property cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("The name of the property cannot be an emprty string.");
        }
        this.map.put(name, value);
    }

    @Override
    public void setBoolean(String name, boolean value) throws JMSException {
        this.initializeWriting();
        this.put(name, value ? Boolean.TRUE : Boolean.FALSE);
    }

    @Override
    public void setByte(String name, byte value) throws JMSException {
        this.initializeWriting();
        this.put(name, value);
    }

    @Override
    public void setShort(String name, short value) throws JMSException {
        this.initializeWriting();
        this.put(name, value);
    }

    @Override
    public void setChar(String name, char value) throws JMSException {
        this.initializeWriting();
        this.put(name, Character.valueOf(value));
    }

    @Override
    public void setInt(String name, int value) throws JMSException {
        this.initializeWriting();
        this.put(name, value);
    }

    @Override
    public void setLong(String name, long value) throws JMSException {
        this.initializeWriting();
        this.put(name, value);
    }

    @Override
    public void setFloat(String name, float value) throws JMSException {
        this.initializeWriting();
        this.put(name, new Float(value));
    }

    @Override
    public void setDouble(String name, double value) throws JMSException {
        this.initializeWriting();
        this.put(name, new Double(value));
    }

    @Override
    public void setString(String name, String value) throws JMSException {
        this.initializeWriting();
        this.put(name, value);
    }

    @Override
    public void setBytes(String name, byte[] value) throws JMSException {
        this.initializeWriting();
        if (value != null) {
            this.put(name, value);
        } else {
            this.map.remove(name);
        }
    }

    @Override
    public void setBytes(String name, byte[] value, int offset, int length) throws JMSException {
        this.initializeWriting();
        byte[] data = new byte[length];
        System.arraycopy(value, offset, data, 0, length);
        this.put(name, data);
    }

    @Override
    public void setObject(String name, Object value) throws JMSException {
        this.initializeWriting();
        if (value != null) {
            if (!(value instanceof byte[])) {
                this.checkValidObject(value);
            }
            this.put(name, value);
        } else {
            this.put(name, null);
        }
    }

    @Override
    public boolean itemExists(String name) throws JMSException {
        this.initializeReading();
        return this.map.containsKey(name);
    }

    private void initializeReading() throws JMSException {
        this.loadContent();
    }

    private void initializeWriting() throws MessageNotWriteableException {
        this.checkReadOnlyBody();
        this.setContent(null);
    }

    @Override
    public void compress() throws IOException {
        this.storeContent();
        super.compress();
    }

    @Override
    public String toString() {
        return super.toString() + " ActiveMQMapMessage{ theTable = " + this.map + " }";
    }

    public Map<String, Object> getContentMap() throws JMSException {
        this.initializeReading();
        return this.map;
    }
}

