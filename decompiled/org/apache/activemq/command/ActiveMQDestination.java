/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DefaultUnresolvedDestinationTransformer;
import org.apache.activemq.command.UnresolvedDestinationTransformer;
import org.apache.activemq.filter.AnyDestination;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.jndi.JNDIBaseStorable;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public abstract class ActiveMQDestination
extends JNDIBaseStorable
implements DataStructure,
Destination,
Externalizable,
Comparable<Object> {
    public static final String PATH_SEPERATOR = ".";
    public static final char COMPOSITE_SEPERATOR = ',';
    public static final byte QUEUE_TYPE = 1;
    public static final byte TOPIC_TYPE = 2;
    public static final byte TEMP_MASK = 4;
    public static final byte TEMP_TOPIC_TYPE = 6;
    public static final byte TEMP_QUEUE_TYPE = 5;
    public static final String QUEUE_QUALIFIED_PREFIX = "queue://";
    public static final String TOPIC_QUALIFIED_PREFIX = "topic://";
    public static final String TEMP_QUEUE_QUALIFED_PREFIX = "temp-queue://";
    public static final String TEMP_TOPIC_QUALIFED_PREFIX = "temp-topic://";
    public static final String IS_DLQ = "isDLQ";
    public static final String TEMP_DESTINATION_NAME_PREFIX = "ID:";
    private static final long serialVersionUID = -3885260014960795889L;
    protected String physicalName;
    protected transient ActiveMQDestination[] compositeDestinations;
    protected transient String[] destinationPaths;
    protected transient boolean isPattern;
    protected transient int hashValue;
    protected Map<String, String> options;
    protected static UnresolvedDestinationTransformer unresolvableDestinationTransformer = new DefaultUnresolvedDestinationTransformer();

    public ActiveMQDestination() {
    }

    protected ActiveMQDestination(String name) {
        this.setPhysicalName(name);
    }

    public ActiveMQDestination(ActiveMQDestination[] composites) {
        this.setCompositeDestinations(composites);
    }

    public static ActiveMQDestination createDestination(String name, byte defaultType) {
        if (name.startsWith(QUEUE_QUALIFIED_PREFIX)) {
            return new ActiveMQQueue(name.substring(QUEUE_QUALIFIED_PREFIX.length()));
        }
        if (name.startsWith(TOPIC_QUALIFIED_PREFIX)) {
            return new ActiveMQTopic(name.substring(TOPIC_QUALIFIED_PREFIX.length()));
        }
        if (name.startsWith(TEMP_QUEUE_QUALIFED_PREFIX)) {
            return new ActiveMQTempQueue(name.substring(TEMP_QUEUE_QUALIFED_PREFIX.length()));
        }
        if (name.startsWith(TEMP_TOPIC_QUALIFED_PREFIX)) {
            return new ActiveMQTempTopic(name.substring(TEMP_TOPIC_QUALIFED_PREFIX.length()));
        }
        switch (defaultType) {
            case 1: {
                return new ActiveMQQueue(name);
            }
            case 2: {
                return new ActiveMQTopic(name);
            }
            case 5: {
                return new ActiveMQTempQueue(name);
            }
            case 6: {
                return new ActiveMQTempTopic(name);
            }
        }
        throw new IllegalArgumentException("Invalid default destination type: " + defaultType);
    }

    public static ActiveMQDestination transform(Destination dest) throws JMSException {
        if (dest == null) {
            return null;
        }
        if (dest instanceof ActiveMQDestination) {
            return (ActiveMQDestination)dest;
        }
        if (dest instanceof Queue && dest instanceof Topic) {
            String queueName = ((Queue)dest).getQueueName();
            String topicName = ((Topic)dest).getTopicName();
            if (queueName != null && topicName == null) {
                return new ActiveMQQueue(queueName);
            }
            if (queueName == null && topicName != null) {
                return new ActiveMQTopic(topicName);
            }
            return unresolvableDestinationTransformer.transform(dest);
        }
        if (dest instanceof TemporaryQueue) {
            return new ActiveMQTempQueue(((TemporaryQueue)dest).getQueueName());
        }
        if (dest instanceof TemporaryTopic) {
            return new ActiveMQTempTopic(((TemporaryTopic)dest).getTopicName());
        }
        if (dest instanceof Queue) {
            return new ActiveMQQueue(((Queue)dest).getQueueName());
        }
        if (dest instanceof Topic) {
            return new ActiveMQTopic(((Topic)dest).getTopicName());
        }
        throw new JMSException("Could not transform the destination into a ActiveMQ destination: " + dest);
    }

    public static int compare(ActiveMQDestination destination, ActiveMQDestination destination2) {
        if (destination == destination2) {
            return 0;
        }
        if (destination == null || destination2 instanceof AnyDestination) {
            return -1;
        }
        if (destination2 == null || destination instanceof AnyDestination) {
            return 1;
        }
        if (destination.getDestinationType() == destination2.getDestinationType()) {
            DestinationFilter filter2;
            if (destination.isPattern() && destination2.isPattern() && destination.getPhysicalName().compareTo(destination2.getPhysicalName()) == 0) {
                return 0;
            }
            if (destination.isPattern() && (filter2 = DestinationFilter.parseFilter(destination)).matches(destination2)) {
                return 1;
            }
            if (destination2.isPattern() && (filter2 = DestinationFilter.parseFilter(destination2)).matches(destination)) {
                return -1;
            }
            return destination.getPhysicalName().compareTo(destination2.getPhysicalName());
        }
        return destination.isQueue() ? -1 : 1;
    }

    @Override
    public int compareTo(Object that) {
        if (that instanceof ActiveMQDestination) {
            return ActiveMQDestination.compare(this, (ActiveMQDestination)that);
        }
        if (that == null) {
            return 1;
        }
        return this.getClass().getName().compareTo(that.getClass().getName());
    }

    public boolean isComposite() {
        return this.compositeDestinations != null;
    }

    public ActiveMQDestination[] getCompositeDestinations() {
        return this.compositeDestinations;
    }

    public void setCompositeDestinations(ActiveMQDestination[] destinations) {
        this.compositeDestinations = destinations;
        this.destinationPaths = null;
        this.hashValue = 0;
        this.isPattern = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < destinations.length; ++i) {
            if (i != 0) {
                sb.append(',');
            }
            if (this.getDestinationType() == destinations[i].getDestinationType()) {
                sb.append(destinations[i].getPhysicalName());
                continue;
            }
            sb.append(destinations[i].getQualifiedName());
        }
        this.physicalName = sb.toString();
    }

    public String getQualifiedName() {
        if (this.isComposite()) {
            return this.physicalName;
        }
        return this.getQualifiedPrefix() + this.physicalName;
    }

    protected abstract String getQualifiedPrefix();

    public String getPhysicalName() {
        return this.physicalName;
    }

    public void setPhysicalName(String physicalName) {
        physicalName = physicalName.trim();
        int length = physicalName.length();
        if (physicalName.isEmpty()) {
            throw new IllegalArgumentException("Invalid destination name: a non-empty name is required");
        }
        int p = -1;
        boolean composite = false;
        for (int i = 0; i < length; ++i) {
            char c = physicalName.charAt(i);
            if (c == '?') {
                p = i;
                break;
            }
            if (c == ',') {
                this.isPattern = false;
                composite = true;
                continue;
            }
            if (composite || c != '*' && c != '>') continue;
            this.isPattern = true;
        }
        if (p >= 0) {
            String optstring = physicalName.substring(p + 1);
            physicalName = physicalName.substring(0, p);
            try {
                this.options = URISupport.parseQuery(optstring);
            }
            catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid destination name: " + physicalName + ", it's options are not encoded properly: " + e);
            }
        }
        this.physicalName = physicalName;
        this.destinationPaths = null;
        this.hashValue = 0;
        if (composite) {
            HashSet<String> l = new HashSet<String>();
            StringTokenizer iter = new StringTokenizer(physicalName, ",");
            while (iter.hasMoreTokens()) {
                String name = iter.nextToken().trim();
                if (name.length() == 0) continue;
                l.add(name);
            }
            this.compositeDestinations = new ActiveMQDestination[l.size()];
            int counter = 0;
            for (String dest : l) {
                this.compositeDestinations[counter++] = this.createDestination(dest);
            }
        }
    }

    public ActiveMQDestination createDestination(String name) {
        return ActiveMQDestination.createDestination(name, this.getDestinationType());
    }

    public String[] getDestinationPaths() {
        if (this.destinationPaths != null) {
            return this.destinationPaths;
        }
        ArrayList<String> l = new ArrayList<String>();
        StringBuilder level = new StringBuilder();
        char separator = PATH_SEPERATOR.charAt(0);
        for (char c : this.physicalName.toCharArray()) {
            if (c == separator) {
                l.add(level.toString());
                level.delete(0, level.length());
                continue;
            }
            level.append(c);
        }
        l.add(level.toString());
        this.destinationPaths = new String[l.size()];
        l.toArray(this.destinationPaths);
        return this.destinationPaths;
    }

    public abstract byte getDestinationType();

    public boolean isQueue() {
        return false;
    }

    public boolean isTopic() {
        return false;
    }

    public boolean isTemporary() {
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ActiveMQDestination d = (ActiveMQDestination)o;
        return this.physicalName.equals(d.physicalName);
    }

    public int hashCode() {
        if (this.hashValue == 0) {
            this.hashValue = this.physicalName.hashCode();
        }
        return this.hashValue;
    }

    public String toString() {
        return this.getQualifiedName();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.getPhysicalName());
        out.writeObject(this.options);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setPhysicalName(in.readUTF());
        this.options = (Map)in.readObject();
    }

    public String getDestinationTypeAsString() {
        switch (this.getDestinationType()) {
            case 1: {
                return "Queue";
            }
            case 2: {
                return "Topic";
            }
            case 5: {
                return "TempQueue";
            }
            case 6: {
                return "TempTopic";
            }
        }
        throw new IllegalArgumentException("Invalid destination type: " + this.getDestinationType());
    }

    public Map<String, String> getOptions() {
        return this.options;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    @Override
    public void buildFromProperties(Properties properties) {
        if (properties == null) {
            properties = new Properties();
        }
        IntrospectionSupport.setProperties(this, properties);
    }

    @Override
    public void populateProperties(Properties props) {
        props.setProperty("physicalName", this.getPhysicalName());
    }

    public boolean isPattern() {
        return this.isPattern;
    }

    public boolean isDLQ() {
        return this.options != null && this.options.containsKey(IS_DLQ);
    }

    public void setDLQ(boolean val) {
        if (this.options == null) {
            this.options = new HashMap<String, String>();
        }
        this.options.put(IS_DLQ, String.valueOf(val));
    }

    public static UnresolvedDestinationTransformer getUnresolvableDestinationTransformer() {
        return unresolvableDestinationTransformer;
    }

    public static void setUnresolvableDestinationTransformer(UnresolvedDestinationTransformer unresolvableDestinationTransformer) {
        ActiveMQDestination.unresolvableDestinationTransformer = unresolvableDestinationTransformer;
    }
}

