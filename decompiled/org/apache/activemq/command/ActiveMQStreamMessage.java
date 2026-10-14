/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.jms.JMSException;
import javax.jms.MessageEOFException;
import javax.jms.MessageFormatException;
import javax.jms.MessageNotReadableException;
import javax.jms.StreamMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.MarshallingSupport;

public class ActiveMQStreamMessage
extends ActiveMQMessage
implements StreamMessage {
    public static final byte DATA_STRUCTURE_TYPE = 27;
    protected transient DataOutputStream dataOut;
    protected transient ByteArrayOutputStream bytesOut;
    protected transient DataInputStream dataIn;
    protected transient int remainingBytes = -1;

    @Override
    public Message copy() {
        ActiveMQStreamMessage copy = new ActiveMQStreamMessage();
        this.copy(copy);
        return copy;
    }

    private void copy(ActiveMQStreamMessage copy) {
        this.storeContent();
        super.copy(copy);
        copy.dataOut = null;
        copy.bytesOut = null;
        copy.dataIn = null;
    }

    @Override
    public void onSend() throws JMSException {
        super.onSend();
        this.storeContent();
    }

    @Override
    public void storeContent() {
        if (this.dataOut != null) {
            try {
                this.dataOut.close();
                this.setContent(this.bytesOut.toByteSequence());
                this.bytesOut = null;
                this.dataOut = null;
            }
            catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }

    @Override
    public boolean isContentMarshalled() {
        return this.content != null || this.dataOut == null;
    }

    @Override
    public byte getDataStructureType() {
        return 27;
    }

    @Override
    public String getJMSXMimeType() {
        return "jms/stream-message";
    }

    @Override
    public void clearBody() throws JMSException {
        super.clearBody();
        this.dataOut = null;
        this.dataIn = null;
        this.bytesOut = null;
        this.remainingBytes = -1;
    }

    @Override
    public boolean readBoolean() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(10);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 1) {
                return this.dataIn.readBoolean();
            }
            if (type == 9) {
                return Boolean.valueOf(this.dataIn.readUTF());
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to boolean.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a boolean type");
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public byte readByte() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(10);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 2) {
                return this.dataIn.readByte();
            }
            if (type == 9) {
                return Byte.valueOf(this.dataIn.readUTF());
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to byte.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a byte type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public short readShort() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(17);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 4) {
                return this.dataIn.readShort();
            }
            if (type == 2) {
                return this.dataIn.readByte();
            }
            if (type == 9) {
                return Short.valueOf(this.dataIn.readUTF());
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to short.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a short type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public char readChar() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(17);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 3) {
                return this.dataIn.readChar();
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to char.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a char type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public int readInt() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(33);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 5) {
                return this.dataIn.readInt();
            }
            if (type == 4) {
                return this.dataIn.readShort();
            }
            if (type == 2) {
                return this.dataIn.readByte();
            }
            if (type == 9) {
                return Integer.valueOf(this.dataIn.readUTF());
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to int.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not an int type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public long readLong() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(65);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 6) {
                return this.dataIn.readLong();
            }
            if (type == 5) {
                return this.dataIn.readInt();
            }
            if (type == 4) {
                return this.dataIn.readShort();
            }
            if (type == 2) {
                return this.dataIn.readByte();
            }
            if (type == 9) {
                return Long.valueOf(this.dataIn.readUTF());
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to long.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a long type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public float readFloat() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(33);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 8) {
                return this.dataIn.readFloat();
            }
            if (type == 9) {
                return Float.valueOf(this.dataIn.readUTF()).floatValue();
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to float.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a float type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public double readDouble() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(65);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 7) {
                return this.dataIn.readDouble();
            }
            if (type == 8) {
                return this.dataIn.readFloat();
            }
            if (type == 9) {
                return Double.valueOf(this.dataIn.readUTF());
            }
            if (type == 0) {
                this.dataIn.reset();
                throw new NullPointerException("Cannot convert NULL value to double.");
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a double type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public String readString() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(65);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 0) {
                return null;
            }
            if (type == 13) {
                return MarshallingSupport.readUTF8(this.dataIn);
            }
            if (type == 9) {
                return this.dataIn.readUTF();
            }
            if (type == 6) {
                return new Long(this.dataIn.readLong()).toString();
            }
            if (type == 5) {
                return new Integer(this.dataIn.readInt()).toString();
            }
            if (type == 4) {
                return new Short(this.dataIn.readShort()).toString();
            }
            if (type == 2) {
                return new Byte(this.dataIn.readByte()).toString();
            }
            if (type == 8) {
                return new Float(this.dataIn.readFloat()).toString();
            }
            if (type == 7) {
                return new Double(this.dataIn.readDouble()).toString();
            }
            if (type == 1) {
                return (this.dataIn.readBoolean() ? Boolean.TRUE : Boolean.FALSE).toString();
            }
            if (type == 3) {
                return new Character(this.dataIn.readChar()).toString();
            }
            this.dataIn.reset();
            throw new MessageFormatException(" not a String type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            throw JMSExceptionSupport.createMessageEOFException(e);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.createMessageFormatException(e);
        }
    }

    @Override
    public int readBytes(byte[] value) throws JMSException {
        this.initializeReading();
        try {
            if (value == null) {
                throw new NullPointerException();
            }
            if (this.remainingBytes == -1) {
                this.dataIn.mark(value.length + 1);
                int type = this.dataIn.read();
                if (type == -1) {
                    throw new MessageEOFException("reached end of data");
                }
                if (type != 10) {
                    throw new MessageFormatException("Not a byte array");
                }
                this.remainingBytes = this.dataIn.readInt();
            } else if (this.remainingBytes == 0) {
                this.remainingBytes = -1;
                return -1;
            }
            if (value.length <= this.remainingBytes) {
                this.remainingBytes -= value.length;
                this.dataIn.readFully(value);
                return value.length;
            }
            int rc = this.dataIn.read(value, 0, this.remainingBytes);
            this.remainingBytes = 0;
            return rc != -1 ? rc : 0;
        }
        catch (EOFException e) {
            MessageEOFException jmsEx = new MessageEOFException(e.getMessage());
            jmsEx.setLinkedException(e);
            throw jmsEx;
        }
        catch (IOException e) {
            MessageFormatException jmsEx = new MessageFormatException(e.getMessage());
            jmsEx.setLinkedException(e);
            throw jmsEx;
        }
    }

    @Override
    public Object readObject() throws JMSException {
        this.initializeReading();
        try {
            this.dataIn.mark(65);
            int type = this.dataIn.read();
            if (type == -1) {
                throw new MessageEOFException("reached end of data");
            }
            if (type == 0) {
                return null;
            }
            if (type == 13) {
                return MarshallingSupport.readUTF8(this.dataIn);
            }
            if (type == 9) {
                return this.dataIn.readUTF();
            }
            if (type == 6) {
                return this.dataIn.readLong();
            }
            if (type == 5) {
                return this.dataIn.readInt();
            }
            if (type == 4) {
                return this.dataIn.readShort();
            }
            if (type == 2) {
                return this.dataIn.readByte();
            }
            if (type == 8) {
                return new Float(this.dataIn.readFloat());
            }
            if (type == 7) {
                return new Double(this.dataIn.readDouble());
            }
            if (type == 1) {
                return this.dataIn.readBoolean() ? Boolean.TRUE : Boolean.FALSE;
            }
            if (type == 3) {
                return Character.valueOf(this.dataIn.readChar());
            }
            if (type == 10) {
                int len = this.dataIn.readInt();
                byte[] value = new byte[len];
                this.dataIn.readFully(value);
                return value;
            }
            this.dataIn.reset();
            throw new MessageFormatException("unknown type");
        }
        catch (NumberFormatException mfe) {
            try {
                this.dataIn.reset();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            throw mfe;
        }
        catch (EOFException e) {
            MessageEOFException jmsEx = new MessageEOFException(e.getMessage());
            jmsEx.setLinkedException(e);
            throw jmsEx;
        }
        catch (IOException e) {
            MessageFormatException jmsEx = new MessageFormatException(e.getMessage());
            jmsEx.setLinkedException(e);
            throw jmsEx;
        }
    }

    @Override
    public void writeBoolean(boolean value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalBoolean(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeByte(byte value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalByte(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeShort(short value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalShort(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeChar(char value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalChar(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeInt(int value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalInt(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeLong(long value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalLong(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeFloat(float value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalFloat(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeDouble(double value) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalDouble(this.dataOut, value);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeString(String value) throws JMSException {
        this.initializeWriting();
        try {
            if (value == null) {
                MarshallingSupport.marshalNull(this.dataOut);
            } else {
                MarshallingSupport.marshalString(this.dataOut, value);
            }
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeBytes(byte[] value) throws JMSException {
        this.writeBytes(value, 0, value.length);
    }

    @Override
    public void writeBytes(byte[] value, int offset, int length) throws JMSException {
        this.initializeWriting();
        try {
            MarshallingSupport.marshalByteArray(this.dataOut, value, offset, length);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    @Override
    public void writeObject(Object value) throws JMSException {
        this.initializeWriting();
        if (value == null) {
            try {
                MarshallingSupport.marshalNull(this.dataOut);
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
        } else if (value instanceof String) {
            this.writeString(value.toString());
        } else if (value instanceof Character) {
            this.writeChar(((Character)value).charValue());
        } else if (value instanceof Boolean) {
            this.writeBoolean((Boolean)value);
        } else if (value instanceof Byte) {
            this.writeByte((Byte)value);
        } else if (value instanceof Short) {
            this.writeShort((Short)value);
        } else if (value instanceof Integer) {
            this.writeInt((Integer)value);
        } else if (value instanceof Float) {
            this.writeFloat(((Float)value).floatValue());
        } else if (value instanceof Double) {
            this.writeDouble((Double)value);
        } else if (value instanceof byte[]) {
            this.writeBytes((byte[])value);
        } else if (value instanceof Long) {
            this.writeLong((Long)value);
        } else {
            throw new MessageFormatException("Unsupported Object type: " + value.getClass());
        }
    }

    @Override
    public void reset() throws JMSException {
        this.storeContent();
        this.bytesOut = null;
        this.dataIn = null;
        this.dataOut = null;
        this.remainingBytes = -1;
        this.setReadOnlyBody(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initializeWriting() throws JMSException {
        this.checkReadOnlyBody();
        if (this.dataOut == null) {
            OutputStream os = this.bytesOut = new ByteArrayOutputStream();
            ActiveMQConnection connection = this.getConnection();
            if (connection != null && connection.isUseCompression()) {
                this.compressed = true;
                os = new DeflaterOutputStream(os);
            }
            this.dataOut = new DataOutputStream(os);
        }
        if (this.content != null && this.content.length > 0) {
            try {
                if (this.compressed) {
                    ByteArrayInputStream input = new ByteArrayInputStream(this.content.getData(), this.content.getOffset(), this.content.getLength());
                    try (InflaterInputStream inflater = new InflaterInputStream(input);){
                        byte[] buffer = new byte[8192];
                        int read = 0;
                        while ((read = inflater.read(buffer)) != -1) {
                            this.dataOut.write(buffer, 0, read);
                        }
                    }
                } else {
                    this.dataOut.write(this.content.getData(), this.content.getOffset(), this.content.getLength());
                }
                this.content = null;
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
        }
    }

    protected void checkWriteOnlyBody() throws MessageNotReadableException {
        if (!this.readOnlyBody) {
            throw new MessageNotReadableException("Message body is write-only");
        }
    }

    private void initializeReading() throws MessageNotReadableException {
        this.checkWriteOnlyBody();
        if (this.dataIn == null) {
            ByteSequence data = this.getContent();
            if (data == null) {
                data = new ByteSequence(new byte[0], 0, 0);
            }
            InputStream is = new ByteArrayInputStream(data);
            if (this.isCompressed()) {
                is = new InflaterInputStream(is);
                is = new BufferedInputStream(is);
            }
            this.dataIn = new DataInputStream(is);
        }
    }

    @Override
    public void compress() throws IOException {
        this.storeContent();
        super.compress();
    }

    @Override
    public String toString() {
        return super.toString() + " ActiveMQStreamMessage{ bytesOut = " + this.bytesOut + ", dataOut = " + this.dataOut + ", dataIn = " + this.dataIn + " }";
    }
}

