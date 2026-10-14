/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.logwriters;

import java.io.IOException;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.transport.LogWriter;
import org.slf4j.Logger;

public class CustomLogWriter
implements LogWriter {
    @Override
    public void setPrefix(String prefix) {
    }

    @Override
    public void initialMessage(Logger log) {
    }

    @Override
    public void logRequest(Logger log, Object command) {
        log.debug("$$ SENDREQ: " + CustomLogWriter.commandToString(command));
    }

    @Override
    public void logResponse(Logger log, Object response) {
        log.debug("$$ GOT_RESPONSE: " + response);
    }

    @Override
    public void logAsyncRequest(Logger log, Object command) {
        log.debug("$$ SENDING_ASNYC_REQUEST: " + command);
    }

    @Override
    public void logOneWay(Logger log, Object command) {
        log.debug("$$ SENDING: " + CustomLogWriter.commandToString(command));
    }

    @Override
    public void logReceivedCommand(Logger log, Object command) {
        log.debug("$$ RECEIVED: " + CustomLogWriter.commandToString(command));
    }

    @Override
    public void logReceivedException(Logger log, IOException error) {
        log.debug("$$ RECEIVED_EXCEPTION: " + error, error);
    }

    private static String commandToString(Object command) {
        StringBuilder sb = new StringBuilder();
        if (command instanceof BaseCommand) {
            BaseCommand bc = (BaseCommand)command;
            sb.append(command.getClass().getSimpleName());
            sb.append(' ');
            sb.append(bc.isResponseRequired() ? (char)'T' : 'F');
            Message m4 = null;
            if (bc instanceof Message) {
                m4 = (Message)bc;
            }
            if (bc instanceof MessageDispatch) {
                m4 = ((MessageDispatch)bc).getMessage();
            }
            if (m4 != null) {
                sb.append(' ');
                sb.append(m4.getMessageId());
                sb.append(',');
                sb.append(m4.getCommandId());
                ProducerId pid = m4.getProducerId();
                long sid = pid.getSessionId();
                sb.append(',');
                sb.append(pid.getConnectionId());
                sb.append(',');
                sb.append(sid);
                sb.append(',');
                sb.append(pid.getValue());
                sb.append(',');
                sb.append(m4.getCorrelationId());
                sb.append(',');
                sb.append(m4.getType());
            }
            if (bc instanceof MessageDispatch) {
                sb.append(" toConsumer:");
                sb.append(((MessageDispatch)bc).getConsumerId());
            }
            if (bc instanceof ProducerAck) {
                sb.append(" ProducerId:");
                sb.append(((ProducerAck)bc).getProducerId());
            }
            if (bc instanceof MessageAck) {
                MessageAck ma = (MessageAck)bc;
                sb.append(" ConsumerID:");
                sb.append(ma.getConsumerId());
                sb.append(" ack:");
                sb.append(ma.getFirstMessageId());
                sb.append('-');
                sb.append(ma.getLastMessageId());
            }
            if (bc instanceof ConnectionInfo) {
                ConnectionInfo ci = (ConnectionInfo)bc;
                sb.append(' ');
                sb.append(ci.getConnectionId());
            }
        } else if (command instanceof WireFormatInfo) {
            sb.append("WireFormatInfo");
        } else {
            sb.append("Unrecognized_object ");
            sb.append(command.toString());
        }
        return sb.toString();
    }
}

