/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.net.MalformedURLException;
import java.util.Enumeration;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageEOFException;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.blob.BlobDownloader;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQStreamMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

public final class ActiveMQMessageTransformation {
    private ActiveMQMessageTransformation() {
    }

    public static ActiveMQDestination transformDestination(Destination destination) throws JMSException {
        return ActiveMQDestination.transform(destination);
    }

    public static ActiveMQMessage transformMessage(Message message, ActiveMQConnection connection) throws JMSException {
        if (message instanceof ActiveMQMessage) {
            return (ActiveMQMessage)message;
        }
        ActiveMQMessage activeMessage = null;
        if (message instanceof BytesMessage) {
            BytesMessage bytesMsg = (BytesMessage)message;
            bytesMsg.reset();
            ActiveMQBytesMessage msg = new ActiveMQBytesMessage();
            msg.setConnection(connection);
            try {
                while (true) {
                    msg.writeByte(bytesMsg.readByte());
                }
            }
            catch (MessageEOFException messageEOFException) {
            }
            catch (JMSException jMSException) {
                // empty catch block
            }
            activeMessage = msg;
        } else if (message instanceof MapMessage) {
            MapMessage mapMsg = (MapMessage)message;
            ActiveMQMapMessage msg = new ActiveMQMapMessage();
            msg.setConnection(connection);
            Enumeration iter = mapMsg.getMapNames();
            while (iter.hasMoreElements()) {
                String name = iter.nextElement().toString();
                msg.setObject(name, mapMsg.getObject(name));
            }
            activeMessage = msg;
        } else if (message instanceof ObjectMessage) {
            ObjectMessage objMsg = (ObjectMessage)message;
            ActiveMQObjectMessage msg = new ActiveMQObjectMessage();
            msg.setConnection(connection);
            msg.setObject(objMsg.getObject());
            msg.storeContent();
            activeMessage = msg;
        } else if (message instanceof StreamMessage) {
            StreamMessage streamMessage = (StreamMessage)message;
            streamMessage.reset();
            ActiveMQStreamMessage msg = new ActiveMQStreamMessage();
            msg.setConnection(connection);
            Object obj = null;
            try {
                while ((obj = streamMessage.readObject()) != null) {
                    msg.writeObject(obj);
                }
            }
            catch (MessageEOFException messageEOFException) {
            }
            catch (JMSException jMSException) {
                // empty catch block
            }
            activeMessage = msg;
        } else if (message instanceof TextMessage) {
            TextMessage textMsg = (TextMessage)message;
            ActiveMQTextMessage msg = new ActiveMQTextMessage();
            msg.setConnection(connection);
            msg.setText(textMsg.getText());
            activeMessage = msg;
        } else if (message instanceof BlobMessage) {
            BlobMessage blobMessage = (BlobMessage)message;
            ActiveMQBlobMessage msg = new ActiveMQBlobMessage();
            msg.setConnection(connection);
            if (connection != null) {
                msg.setBlobDownloader(new BlobDownloader(connection.getBlobTransferPolicy()));
            }
            try {
                msg.setURL(blobMessage.getURL());
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
            activeMessage = msg;
        } else {
            activeMessage = new ActiveMQMessage();
            activeMessage.setConnection(connection);
        }
        ActiveMQMessageTransformation.copyProperties(message, activeMessage);
        return activeMessage;
    }

    public static void copyProperties(Message fromMessage, Message toMessage) throws JMSException {
        toMessage.setJMSMessageID(fromMessage.getJMSMessageID());
        toMessage.setJMSCorrelationID(fromMessage.getJMSCorrelationID());
        toMessage.setJMSReplyTo(ActiveMQMessageTransformation.transformDestination(fromMessage.getJMSReplyTo()));
        toMessage.setJMSDestination(ActiveMQMessageTransformation.transformDestination(fromMessage.getJMSDestination()));
        toMessage.setJMSDeliveryMode(fromMessage.getJMSDeliveryMode());
        toMessage.setJMSRedelivered(fromMessage.getJMSRedelivered());
        toMessage.setJMSType(fromMessage.getJMSType());
        toMessage.setJMSExpiration(fromMessage.getJMSExpiration());
        toMessage.setJMSPriority(fromMessage.getJMSPriority());
        toMessage.setJMSTimestamp(fromMessage.getJMSTimestamp());
        Enumeration propertyNames = fromMessage.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = propertyNames.nextElement().toString();
            Object obj = fromMessage.getObjectProperty(name);
            toMessage.setObjectProperty(name, obj);
        }
    }
}

