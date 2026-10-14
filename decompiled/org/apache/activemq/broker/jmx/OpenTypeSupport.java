/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.apache.activemq.broker.jmx.HealthStatus;
import org.apache.activemq.broker.region.policy.SlowConsumerEntry;
import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQStreamMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.fusesource.hawtbuf.UTF8Buffer;

public final class OpenTypeSupport {
    private static final Map<Class, AbstractOpenTypeFactory> OPEN_TYPE_FACTORIES = new HashMap<Class, AbstractOpenTypeFactory>();

    private OpenTypeSupport() {
    }

    public static OpenTypeFactory getFactory(Class<?> clazz) throws OpenDataException {
        return OPEN_TYPE_FACTORIES.get(clazz);
    }

    public static CompositeData convert(Object message) throws OpenDataException {
        OpenTypeFactory f = OpenTypeSupport.getFactory(message.getClass());
        if (f == null) {
            throw new OpenDataException("Cannot create a CompositeData for type: " + message.getClass().getName());
        }
        CompositeType ct = f.getCompositeType();
        Map<String, Object> fields = f.getFields(message);
        return new CompositeDataSupport(ct, fields);
    }

    static {
        OPEN_TYPE_FACTORIES.put(ActiveMQMessage.class, new MessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(ActiveMQBytesMessage.class, new ByteMessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(ActiveMQMapMessage.class, new MapMessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(ActiveMQObjectMessage.class, new ObjectMessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(ActiveMQStreamMessage.class, new StreamMessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(ActiveMQTextMessage.class, new TextMessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(Job.class, new JobOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(SlowConsumerEntry.class, new SlowConsumerEntryOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(ActiveMQBlobMessage.class, new ActiveMQBlobMessageOpenTypeFactory());
        OPEN_TYPE_FACTORIES.put(HealthStatus.class, new HealthStatusOpenTypeFactory());
    }

    static class HealthStatusOpenTypeFactory
    extends AbstractOpenTypeFactory {
        HealthStatusOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return HealthStatus.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("healthId", "health check id", SimpleType.STRING);
            this.addItem("level", "severity", SimpleType.STRING);
            this.addItem("message", "severity", SimpleType.STRING);
            this.addItem("resource", "event resource", SimpleType.STRING);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            HealthStatus event = (HealthStatus)o;
            Map<String, Object> rc = super.getFields(o);
            rc.put("healthId", event.getHealthId());
            rc.put("level", event.getLevel());
            rc.put("message", event.getMessage());
            rc.put("resource", event.getResource());
            return rc;
        }
    }

    static class SlowConsumerEntryOpenTypeFactory
    extends AbstractOpenTypeFactory {
        SlowConsumerEntryOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return SlowConsumerEntry.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("subscription", "the subscription view", SimpleType.OBJECTNAME);
            this.addItem("slowCount", "number of times deemed slow", SimpleType.INTEGER);
            this.addItem("markCount", "number of periods remaining slow", SimpleType.INTEGER);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            SlowConsumerEntry entry = (SlowConsumerEntry)o;
            Map<String, Object> rc = super.getFields(o);
            rc.put("subscription", entry.getSubscription());
            rc.put("slowCount", entry.getSlowCount());
            rc.put("markCount", entry.getMarkCount());
            return rc;
        }
    }

    static class ActiveMQBlobMessageOpenTypeFactory
    extends MessageOpenTypeFactory {
        ActiveMQBlobMessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQBlobMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("Url", "Body Url", SimpleType.STRING);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            ActiveMQBlobMessage m4 = (ActiveMQBlobMessage)o;
            Map<String, Object> rc = super.getFields(o);
            try {
                rc.put("Url", "" + m4.getURL().toString());
            }
            catch (JMSException e) {
                rc.put("Url", "");
            }
            return rc;
        }
    }

    static class JobOpenTypeFactory
    extends AbstractOpenTypeFactory {
        JobOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return Job.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("jobId", "jobId", SimpleType.STRING);
            this.addItem("cronEntry", "Cron entry", SimpleType.STRING);
            this.addItem("start", "start time", SimpleType.STRING);
            this.addItem("delay", "initial delay", SimpleType.LONG);
            this.addItem("next", "next time", SimpleType.STRING);
            this.addItem("period", "period between jobs", SimpleType.LONG);
            this.addItem("repeat", "number of times to repeat", SimpleType.INTEGER);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            Job job = (Job)o;
            Map<String, Object> rc = super.getFields(o);
            rc.put("jobId", job.getJobId());
            rc.put("cronEntry", "" + job.getCronEntry());
            rc.put("start", job.getStartTime());
            rc.put("delay", job.getDelay());
            rc.put("next", job.getNextExecutionTime());
            rc.put("period", job.getPeriod());
            rc.put("repeat", job.getRepeat());
            return rc;
        }
    }

    static class TextMessageOpenTypeFactory
    extends MessageOpenTypeFactory {
        TextMessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQTextMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("Text", "Text", SimpleType.STRING);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            ActiveMQTextMessage m4 = (ActiveMQTextMessage)o;
            Map<String, Object> rc = super.getFields(o);
            try {
                rc.put("Text", "" + m4.getText());
            }
            catch (JMSException e) {
                rc.put("Text", "");
            }
            return rc;
        }
    }

    static class StreamMessageOpenTypeFactory
    extends MessageOpenTypeFactory {
        StreamMessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQStreamMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            Map<String, Object> rc = super.getFields(o);
            return rc;
        }
    }

    static class ObjectMessageOpenTypeFactory
    extends MessageOpenTypeFactory {
        ObjectMessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQObjectMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            Map<String, Object> rc = super.getFields(o);
            return rc;
        }
    }

    static class MapMessageOpenTypeFactory
    extends MessageOpenTypeFactory {
        MapMessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQMapMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("ContentMap", "Content map", SimpleType.STRING);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            ActiveMQMapMessage m4 = (ActiveMQMapMessage)o;
            Map<String, Object> rc = super.getFields(o);
            try {
                rc.put("ContentMap", "" + m4.getContentMap());
            }
            catch (JMSException e) {
                rc.put("ContentMap", "");
            }
            return rc;
        }
    }

    static class ByteMessageOpenTypeFactory
    extends MessageOpenTypeFactory {
        ByteMessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQBytesMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("BodyLength", "Body length", SimpleType.LONG);
            this.addItem("BodyPreview", "Body preview", new ArrayType(1, SimpleType.BYTE));
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            ActiveMQBytesMessage m4 = (ActiveMQBytesMessage)o;
            m4.setReadOnlyBody(true);
            Map<String, Object> rc = super.getFields(o);
            long length = 0L;
            try {
                length = m4.getBodyLength();
                rc.put("BodyLength", length);
            }
            catch (JMSException e) {
                rc.put("BodyLength", 0L);
            }
            try {
                byte[] preview = new byte[(int)Math.min(length, 255L)];
                m4.readBytes(preview);
                m4.reset();
                Byte[] data = new Byte[preview.length];
                for (int i = 0; i < data.length; ++i) {
                    data[i] = new Byte(preview[i]);
                }
                rc.put("BodyPreview", data);
            }
            catch (JMSException e) {
                rc.put("BodyPreview", new Byte[0]);
            }
            return rc;
        }
    }

    static class MessageOpenTypeFactory
    extends AbstractOpenTypeFactory {
        protected TabularType stringPropertyTabularType;
        protected TabularType booleanPropertyTabularType;
        protected TabularType bytePropertyTabularType;
        protected TabularType shortPropertyTabularType;
        protected TabularType intPropertyTabularType;
        protected TabularType longPropertyTabularType;
        protected TabularType floatPropertyTabularType;
        protected TabularType doublePropertyTabularType;

        MessageOpenTypeFactory() {
        }

        @Override
        protected String getTypeName() {
            return ActiveMQMessage.class.getName();
        }

        @Override
        protected void init() throws OpenDataException {
            super.init();
            this.addItem("JMSCorrelationID", "JMSCorrelationID", SimpleType.STRING);
            this.addItem("JMSDestination", "JMSDestination", SimpleType.STRING);
            this.addItem("JMSMessageID", "JMSMessageID", SimpleType.STRING);
            this.addItem("JMSReplyTo", "JMSReplyTo", SimpleType.STRING);
            this.addItem("JMSType", "JMSType", SimpleType.STRING);
            this.addItem("JMSDeliveryMode", "JMSDeliveryMode", SimpleType.STRING);
            this.addItem("JMSExpiration", "JMSExpiration", SimpleType.LONG);
            this.addItem("JMSPriority", "JMSPriority", SimpleType.INTEGER);
            this.addItem("JMSRedelivered", "JMSRedelivered", SimpleType.BOOLEAN);
            this.addItem("JMSTimestamp", "JMSTimestamp", SimpleType.DATE);
            this.addItem("JMSXGroupID", "Message Group ID", SimpleType.STRING);
            this.addItem("JMSXGroupSeq", "Message Group Sequence Number", SimpleType.INTEGER);
            this.addItem("JMSXUserID", "The user that sent the message", SimpleType.STRING);
            this.addItem("BrokerPath", "Brokers traversed", SimpleType.STRING);
            this.addItem("OriginalDestination", "Original Destination Before Senting To DLQ", SimpleType.STRING);
            this.addItem("PropertiesText", "User Properties Text", SimpleType.STRING);
            this.stringPropertyTabularType = this.createTabularType(String.class, SimpleType.STRING);
            this.booleanPropertyTabularType = this.createTabularType(Boolean.class, SimpleType.BOOLEAN);
            this.bytePropertyTabularType = this.createTabularType(Byte.class, SimpleType.BYTE);
            this.shortPropertyTabularType = this.createTabularType(Short.class, SimpleType.SHORT);
            this.intPropertyTabularType = this.createTabularType(Integer.class, SimpleType.INTEGER);
            this.longPropertyTabularType = this.createTabularType(Long.class, SimpleType.LONG);
            this.floatPropertyTabularType = this.createTabularType(Float.class, SimpleType.FLOAT);
            this.doublePropertyTabularType = this.createTabularType(Double.class, SimpleType.DOUBLE);
            this.addItem("StringProperties", "User String Properties", this.stringPropertyTabularType);
            this.addItem("BooleanProperties", "User Boolean Properties", this.booleanPropertyTabularType);
            this.addItem("ByteProperties", "User Byte Properties", this.bytePropertyTabularType);
            this.addItem("ShortProperties", "User Short Properties", this.shortPropertyTabularType);
            this.addItem("IntProperties", "User Integer Properties", this.intPropertyTabularType);
            this.addItem("LongProperties", "User Long Properties", this.longPropertyTabularType);
            this.addItem("FloatProperties", "User Float Properties", this.floatPropertyTabularType);
            this.addItem("DoubleProperties", "User Double Properties", this.doublePropertyTabularType);
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            ActiveMQMessage m4 = (ActiveMQMessage)o;
            Map<String, Object> rc = super.getFields(o);
            rc.put("JMSCorrelationID", m4.getJMSCorrelationID());
            rc.put("JMSDestination", "" + m4.getJMSDestination());
            rc.put("JMSMessageID", m4.getJMSMessageID());
            rc.put("JMSReplyTo", this.toString(m4.getJMSReplyTo()));
            rc.put("JMSType", m4.getJMSType());
            rc.put("JMSDeliveryMode", m4.getJMSDeliveryMode() == 2 ? "PERSISTENT" : "NON-PERSISTENT");
            rc.put("JMSExpiration", m4.getJMSExpiration());
            rc.put("JMSPriority", m4.getJMSPriority());
            rc.put("JMSRedelivered", m4.getJMSRedelivered());
            rc.put("JMSTimestamp", new Date(m4.getJMSTimestamp()));
            rc.put("JMSXGroupID", m4.getGroupID());
            rc.put("JMSXGroupSeq", m4.getGroupSequence());
            rc.put("JMSXUserID", m4.getUserID());
            rc.put("BrokerPath", Arrays.toString(m4.getBrokerPath()));
            rc.put("OriginalDestination", this.toString(m4.getOriginalDestination()));
            try {
                rc.put("PropertiesText", "" + m4.getProperties());
            }
            catch (IOException e) {
                rc.put("PropertiesText", "");
            }
            try {
                rc.put("StringProperties", this.createTabularData(m4, this.stringPropertyTabularType, String.class));
            }
            catch (IOException e) {
                rc.put("StringProperties", new TabularDataSupport(this.stringPropertyTabularType));
            }
            try {
                rc.put("BooleanProperties", this.createTabularData(m4, this.booleanPropertyTabularType, Boolean.class));
            }
            catch (IOException e) {
                rc.put("BooleanProperties", new TabularDataSupport(this.booleanPropertyTabularType));
            }
            try {
                rc.put("ByteProperties", this.createTabularData(m4, this.bytePropertyTabularType, Byte.class));
            }
            catch (IOException e) {
                rc.put("ByteProperties", new TabularDataSupport(this.bytePropertyTabularType));
            }
            try {
                rc.put("ShortProperties", this.createTabularData(m4, this.shortPropertyTabularType, Short.class));
            }
            catch (IOException e) {
                rc.put("ShortProperties", new TabularDataSupport(this.shortPropertyTabularType));
            }
            try {
                rc.put("IntProperties", this.createTabularData(m4, this.intPropertyTabularType, Integer.class));
            }
            catch (IOException e) {
                rc.put("IntProperties", new TabularDataSupport(this.intPropertyTabularType));
            }
            try {
                rc.put("LongProperties", this.createTabularData(m4, this.longPropertyTabularType, Long.class));
            }
            catch (IOException e) {
                rc.put("LongProperties", new TabularDataSupport(this.longPropertyTabularType));
            }
            try {
                rc.put("FloatProperties", this.createTabularData(m4, this.floatPropertyTabularType, Float.class));
            }
            catch (IOException e) {
                rc.put("FloatProperties", new TabularDataSupport(this.floatPropertyTabularType));
            }
            try {
                rc.put("DoubleProperties", this.createTabularData(m4, this.doublePropertyTabularType, Double.class));
            }
            catch (IOException e) {
                rc.put("DoubleProperties", new TabularDataSupport(this.doublePropertyTabularType));
            }
            return rc;
        }

        protected String toString(Object value) {
            if (value == null) {
                return null;
            }
            return value.toString();
        }

        protected <T> TabularType createTabularType(Class<T> type, OpenType openType) throws OpenDataException {
            String typeName = "java.util.Map<java.lang.String, " + type.getName() + ">";
            String[] keyValue = new String[]{"key", "value"};
            OpenType[] openTypes = new OpenType[]{SimpleType.STRING, openType};
            CompositeType rowType = new CompositeType(typeName, typeName, keyValue, keyValue, openTypes);
            return new TabularType(typeName, typeName, rowType, new String[]{"key"});
        }

        protected TabularDataSupport createTabularData(ActiveMQMessage m4, TabularType type, Class valueType) throws IOException, OpenDataException {
            TabularDataSupport answer = new TabularDataSupport(type);
            Set<Map.Entry<String, Object>> entries = m4.getProperties().entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object value = entry.getValue();
                if (value instanceof UTF8Buffer && valueType.equals(String.class)) {
                    String actual = value.toString();
                    CompositeDataSupport compositeData = this.createTabularRowValue(type, entry.getKey(), actual);
                    answer.put(compositeData);
                }
                if (!valueType.isInstance(value)) continue;
                CompositeDataSupport compositeData = this.createTabularRowValue(type, entry.getKey(), value);
                answer.put(compositeData);
            }
            return answer;
        }

        protected CompositeDataSupport createTabularRowValue(TabularType type, String key, Object value) throws OpenDataException {
            HashMap<String, Object> fields = new HashMap<String, Object>();
            fields.put("key", key);
            fields.put("value", value);
            return new CompositeDataSupport(type.getRowType(), fields);
        }
    }

    public static abstract class AbstractOpenTypeFactory
    implements OpenTypeFactory {
        private CompositeType compositeType;
        private final List<String> itemNamesList = new ArrayList<String>();
        private final List<String> itemDescriptionsList = new ArrayList<String>();
        private final List<OpenType> itemTypesList = new ArrayList<OpenType>();

        @Override
        public synchronized CompositeType getCompositeType() throws OpenDataException {
            if (this.compositeType == null) {
                this.init();
                this.compositeType = this.createCompositeType();
            }
            return this.compositeType;
        }

        protected void init() throws OpenDataException {
        }

        protected CompositeType createCompositeType() throws OpenDataException {
            String[] itemNames = this.itemNamesList.toArray(new String[this.itemNamesList.size()]);
            String[] itemDescriptions = this.itemDescriptionsList.toArray(new String[this.itemDescriptionsList.size()]);
            OpenType[] itemTypes = this.itemTypesList.toArray(new OpenType[this.itemTypesList.size()]);
            return new CompositeType(this.getTypeName(), this.getDescription(), itemNames, itemDescriptions, itemTypes);
        }

        protected abstract String getTypeName();

        protected void addItem(String name, String description, OpenType type) {
            this.itemNamesList.add(name);
            this.itemDescriptionsList.add(description);
            this.itemTypesList.add(type);
        }

        protected String getDescription() {
            return this.getTypeName();
        }

        @Override
        public Map<String, Object> getFields(Object o) throws OpenDataException {
            HashMap<String, Object> rc = new HashMap<String, Object>();
            return rc;
        }
    }

    public static interface OpenTypeFactory {
        public CompositeType getCompositeType() throws OpenDataException;

        public Map<String, Object> getFields(Object var1) throws OpenDataException;
    }
}

