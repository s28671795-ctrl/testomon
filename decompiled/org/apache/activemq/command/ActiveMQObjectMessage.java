/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.TransientInitializer;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.ClassLoadingAwareObjectInputStream;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.wireformat.WireFormat;

public class ActiveMQObjectMessage
extends ActiveMQMessage
implements ObjectMessage,
TransientInitializer {
    public static final byte DATA_STRUCTURE_TYPE = 26;
    private transient List<String> trustedPackages = Arrays.asList(ClassLoadingAwareObjectInputStream.serializablePackages);
    private transient boolean trustAllPackages = false;
    protected transient Serializable object;

    @Override
    public Message copy() {
        ActiveMQObjectMessage copy = new ActiveMQObjectMessage();
        this.copy(copy);
        copy.setTrustAllPackages(this.trustAllPackages);
        copy.setTrustedPackages(this.trustedPackages);
        return copy;
    }

    private void copy(ActiveMQObjectMessage copy) {
        ActiveMQConnection connection = this.getConnection();
        if (connection == null || !connection.isObjectMessageSerializationDefered()) {
            this.storeContent();
            copy.object = null;
        } else {
            copy.object = this.object;
        }
        super.copy(copy);
    }

    @Override
    public void storeContentAndClear() {
        this.storeContent();
        this.object = null;
    }

    @Override
    public void storeContent() {
        ByteSequence bodyAsBytes = this.getContent();
        if (bodyAsBytes == null && this.object != null) {
            try {
                ByteArrayOutputStream bytesOut;
                OutputStream os = bytesOut = new ByteArrayOutputStream();
                ActiveMQConnection connection = this.getConnection();
                if (connection != null && connection.isUseCompression()) {
                    this.compressed = true;
                    os = new DeflaterOutputStream(os);
                }
                DataOutputStream dataOut = new DataOutputStream(os);
                ObjectOutputStream objOut = new ObjectOutputStream(dataOut);
                objOut.writeObject(this.object);
                objOut.flush();
                objOut.reset();
                objOut.close();
                this.setContent(bytesOut.toByteSequence());
            }
            catch (IOException ioe) {
                throw new RuntimeException(ioe.getMessage(), ioe);
            }
        }
    }

    @Override
    public boolean isContentMarshalled() {
        return this.content != null || this.object == null;
    }

    @Override
    public byte getDataStructureType() {
        return 26;
    }

    @Override
    public String getJMSXMimeType() {
        return "jms/object-message";
    }

    @Override
    public void clearBody() throws JMSException {
        super.clearBody();
        this.object = null;
    }

    @Override
    public void setObject(Serializable newObject) throws JMSException {
        this.checkReadOnlyBody();
        this.object = newObject;
        this.setContent(null);
        ActiveMQConnection connection = this.getConnection();
        if (connection == null || !connection.isObjectMessageSerializationDefered()) {
            this.storeContent();
        }
    }

    @Override
    public Serializable getObject() throws JMSException {
        if (this.object == null && this.getContent() != null) {
            try {
                ByteSequence content = this.getContent();
                InputStream is = new ByteArrayInputStream(content);
                if (this.isCompressed()) {
                    is = new InflaterInputStream(is);
                }
                DataInputStream dataIn = new DataInputStream(is);
                ClassLoadingAwareObjectInputStream objIn = new ClassLoadingAwareObjectInputStream(dataIn);
                objIn.setTrustedPackages(this.trustedPackages);
                objIn.setTrustAllPackages(this.trustAllPackages);
                try {
                    this.object = (Serializable)objIn.readObject();
                }
                catch (ClassNotFoundException ce) {
                    throw JMSExceptionSupport.create("Failed to build body from content. Serializable class not available to broker. Reason: " + ce, ce);
                }
                finally {
                    dataIn.close();
                }
            }
            catch (IOException e) {
                throw JMSExceptionSupport.create("Failed to build body from bytes. Reason: " + e, e);
            }
        }
        return this.object;
    }

    @Override
    public void beforeMarshall(WireFormat wireFormat) throws IOException {
        super.beforeMarshall(wireFormat);
        this.storeContent();
    }

    @Override
    public void clearUnMarshalledState() throws JMSException {
        super.clearUnMarshalledState();
        this.object = null;
    }

    @Override
    public void onMessageRolledBack() {
        super.onMessageRolledBack();
        this.object = null;
    }

    @Override
    public void compress() throws IOException {
        this.storeContent();
        super.compress();
    }

    @Override
    public String toString() {
        try {
            this.getObject();
        }
        catch (JMSException jMSException) {
            // empty catch block
        }
        return super.toString();
    }

    public List<String> getTrustedPackages() {
        return this.trustedPackages;
    }

    public void setTrustedPackages(List<String> trustedPackages) {
        this.trustedPackages = trustedPackages;
    }

    public boolean isTrustAllPackages() {
        return this.trustAllPackages;
    }

    public void setTrustAllPackages(boolean trustAllPackages) {
        this.trustAllPackages = trustAllPackages;
    }

    @Override
    public void initTransients() {
        this.trustedPackages = Arrays.asList(ClassLoadingAwareObjectInputStream.serializablePackages);
        this.trustAllPackages = false;
    }
}

