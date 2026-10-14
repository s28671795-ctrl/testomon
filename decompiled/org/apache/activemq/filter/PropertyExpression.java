/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.util.JMSExceptionSupport;

public class PropertyExpression
implements Expression {
    private static final Map<String, SubExpression> JMS_PROPERTY_EXPRESSIONS = new HashMap<String, SubExpression>();
    private final String name;
    private final SubExpression jmsPropertyExpression;

    public PropertyExpression(String name) {
        this.name = name;
        this.jmsPropertyExpression = JMS_PROPERTY_EXPRESSIONS.get(name);
    }

    /*
     * Loose catch block
     */
    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        block6: {
            if (message.isDropped()) {
                return null;
            }
            if (this.jmsPropertyExpression == null) break block6;
            return this.jmsPropertyExpression.evaluate(message.getMessage());
            {
                catch (IOException e) {
                    throw JMSExceptionSupport.create(e);
                }
            }
        }
        try {
            return message.getMessage().getProperty(this.name);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create("Could not get property: " + this.name + " reason: " + ioe.getMessage(), ioe);
        }
    }

    public Object evaluate(Message message) throws JMSException {
        if (this.jmsPropertyExpression != null) {
            return this.jmsPropertyExpression.evaluate(message);
        }
        try {
            return message.getProperty(this.name);
        }
        catch (IOException ioe) {
            throw JMSExceptionSupport.create(ioe);
        }
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        return this.name.equals(((PropertyExpression)o).name);
    }

    static {
        JMS_PROPERTY_EXPRESSIONS.put("JMSDestination", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                ActiveMQDestination dest = message.getOriginalDestination();
                if (dest == null) {
                    dest = message.getDestination();
                }
                if (dest == null) {
                    return null;
                }
                return dest.toString();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSReplyTo", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                if (message.getReplyTo() == null) {
                    return null;
                }
                return message.getReplyTo().toString();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSType", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getType();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSDeliveryMode", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.isPersistent() ? "PERSISTENT" : "NON_PERSISTENT";
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSPriority", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return (int)message.getPriority();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSMessageID", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                if (message.getMessageId() == null) {
                    return null;
                }
                return message.getMessageId().toString();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSTimestamp", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getTimestamp();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSCorrelationID", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getCorrelationId();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSExpiration", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getExpiration();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSRedelivered", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.isRedelivered();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSXDeliveryCount", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getRedeliveryCounter() + 1;
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSXGroupID", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getGroupID();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSXUserID", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                Object userId = message.getUserID();
                if (userId == null) {
                    try {
                        userId = message.getProperty("JMSXUserID");
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                return userId;
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSXGroupSeq", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return new Integer(message.getGroupSequence());
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSXProducerTXID", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                TransactionId txId = message.getOriginalTransactionId();
                if (txId == null) {
                    txId = message.getTransactionId();
                }
                if (txId == null) {
                    return null;
                }
                return txId.toString();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSActiveMQBrokerInTime", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getBrokerInTime();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSActiveMQBrokerOutTime", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.getBrokerOutTime();
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSActiveMQBrokerPath", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return Arrays.toString(message.getBrokerPath());
            }
        });
        JMS_PROPERTY_EXPRESSIONS.put("JMSXGroupFirstForConsumer", new SubExpression(){

            @Override
            public Object evaluate(Message message) {
                return message.isJMSXGroupFirstForConsumer();
            }
        });
    }

    static interface SubExpression {
        public Object evaluate(Message var1);
    }
}

