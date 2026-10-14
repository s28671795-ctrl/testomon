/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.MarshallingSupport;
import org.apache.activemq.wireformat.WireFormat;

public class ActiveMQTextMessage
extends ActiveMQMessage
implements TextMessage {
    public static final byte DATA_STRUCTURE_TYPE = 28;
    protected String text;

    @Override
    public Message copy() {
        ActiveMQTextMessage copy = new ActiveMQTextMessage();
        this.copy(copy);
        return copy;
    }

    private void copy(ActiveMQTextMessage copy) {
        super.copy(copy);
        copy.text = this.text;
    }

    @Override
    public byte getDataStructureType() {
        return 28;
    }

    @Override
    public String getJMSXMimeType() {
        return "jms/text-message";
    }

    @Override
    public void setText(String text) throws MessageNotWriteableException {
        this.checkReadOnlyBody();
        this.text = text;
        this.setContent(null);
    }

    @Override
    public String getText() throws JMSException {
        ByteSequence content = this.getContent();
        if (this.text == null && content != null) {
            this.text = this.decodeContent(content);
            this.setContent(null);
            this.setCompressed(false);
        }
        return this.text;
    }

    private String decodeContent(ByteSequence bodyAsBytes) throws JMSException {
        String text = null;
        if (bodyAsBytes != null) {
            InputStream is = null;
            try {
                is = new ByteArrayInputStream(bodyAsBytes);
                if (this.isCompressed()) {
                    is = new InflaterInputStream(is);
                }
                DataInputStream dataIn = new DataInputStream(is);
                text = MarshallingSupport.readUTF8(dataIn);
                dataIn.close();
            }
            catch (IOException ioe) {
                throw JMSExceptionSupport.create(ioe);
            }
            finally {
                if (is != null) {
                    try {
                        is.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
        return text;
    }

    @Override
    public void beforeMarshall(WireFormat wireFormat) throws IOException {
        super.beforeMarshall(wireFormat);
        this.storeContentAndClear();
    }

    @Override
    public void storeContentAndClear() {
        this.storeContent();
        this.text = null;
    }

    @Override
    public void storeContent() {
        try {
            ByteSequence content = this.getContent();
            String text = this.text;
            if (content == null && text != null) {
                ByteArrayOutputStream bytesOut;
                OutputStream os = bytesOut = new ByteArrayOutputStream();
                ActiveMQConnection connection = this.getConnection();
                if (connection != null && connection.isUseCompression()) {
                    this.compressed = true;
                    os = new DeflaterOutputStream(os);
                }
                DataOutputStream dataOut = new DataOutputStream(os);
                MarshallingSupport.writeUTF8(dataOut, text);
                dataOut.close();
                this.setContent(bytesOut.toByteSequence());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearUnMarshalledState() throws JMSException {
        super.clearUnMarshalledState();
        this.text = null;
    }

    @Override
    public boolean isContentMarshalled() {
        return this.content != null || this.text == null;
    }

    @Override
    public void clearBody() throws JMSException {
        super.clearBody();
        this.text = null;
    }

    @Override
    public int getSize() {
        String text = this.text;
        if (this.size == 0 && this.content == null && text != null) {
            this.size = this.getMinimumMessageSize();
            if (this.marshalledProperties != null) {
                this.size += this.marshalledProperties.getLength();
            }
            this.size += text.length() * 2;
        }
        return super.getSize();
    }

    @Override
    public String toString() {
        try {
            String text = this.text;
            if (text == null) {
                text = this.decodeContent(this.getContent());
            }
            if (text != null) {
                text = MarshallingSupport.truncate64(text);
                HashMap<String, Object> overrideFields = new HashMap<String, Object>();
                overrideFields.put("text", text);
                return super.toString(overrideFields);
            }
        }
        catch (JMSException jMSException) {
            // empty catch block
        }
        return super.toString();
    }
}

