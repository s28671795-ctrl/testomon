/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.jndi.LazyCreateContext;
import org.apache.activemq.jndi.ReadOnlyContext;

public class ActiveMQInitialContextFactory
implements InitialContextFactory {
    private static final String[] DEFAULT_CONNECTION_FACTORY_NAMES = new String[]{"ConnectionFactory", "XAConnectionFactory", "QueueConnectionFactory", "TopicConnectionFactory"};
    private String connectionPrefix = "connection.";
    private String queuePrefix = "queue.";
    private String topicPrefix = "topic.";

    public Context getInitialContext(Hashtable environment) throws NamingException {
        ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<String, Object>();
        String[] names = this.getConnectionFactoryNames(environment);
        for (int i = 0; i < names.length; ++i) {
            ActiveMQConnectionFactory factory2 = null;
            String name = names[i];
            try {
                factory2 = this.createConnectionFactory(name, environment);
            }
            catch (Exception e) {
                throw new NamingException("Invalid broker URL");
            }
            data.put(name, factory2);
        }
        this.createQueues(data, environment);
        this.createTopics(data, environment);
        data.put("dynamicQueues", new LazyCreateContext(){
            private static final long serialVersionUID = 6503881346214855588L;

            @Override
            protected Object createEntry(String name) {
                return new ActiveMQQueue(name);
            }
        });
        data.put("dynamicTopics", new LazyCreateContext(){
            private static final long serialVersionUID = 2019166796234979615L;

            @Override
            protected Object createEntry(String name) {
                return new ActiveMQTopic(name);
            }
        });
        return this.createContext(environment, data);
    }

    public String getTopicPrefix() {
        return this.topicPrefix;
    }

    public void setTopicPrefix(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public String getQueuePrefix() {
        return this.queuePrefix;
    }

    public void setQueuePrefix(String queuePrefix) {
        this.queuePrefix = queuePrefix;
    }

    protected ReadOnlyContext createContext(Hashtable environment, Map<String, Object> data) {
        return new ReadOnlyContext(environment, data);
    }

    protected ActiveMQConnectionFactory createConnectionFactory(String name, Hashtable environment) throws URISyntaxException {
        Hashtable<String, String> temp = new Hashtable<String, String>(environment);
        if (DEFAULT_CONNECTION_FACTORY_NAMES[1].equals(name)) {
            temp.put("xa", String.valueOf(true));
        }
        String prefix = this.connectionPrefix + name + ".";
        for (Map.Entry entry : environment.entrySet()) {
            String key = (String)entry.getKey();
            if (!key.startsWith(prefix)) continue;
            temp.remove(key);
            key = key.substring(prefix.length());
            temp.put(key, (String)entry.getValue());
        }
        return this.createConnectionFactory(temp);
    }

    protected String[] getConnectionFactoryNames(Map environment) {
        String factoryNames = (String)environment.get("connectionFactoryNames");
        if (factoryNames != null) {
            ArrayList<String> list = new ArrayList<String>();
            StringTokenizer enumeration = new StringTokenizer(factoryNames, ",");
            while (enumeration.hasMoreTokens()) {
                list.add(enumeration.nextToken().trim());
            }
            int size = list.size();
            if (size > 0) {
                String[] answer = new String[size];
                list.toArray(answer);
                return answer;
            }
        }
        return DEFAULT_CONNECTION_FACTORY_NAMES;
    }

    protected void createQueues(Map<String, Object> data, Hashtable environment) {
        for (Map.Entry entry : environment.entrySet()) {
            String key = entry.getKey().toString();
            if (!key.startsWith(this.queuePrefix)) continue;
            String jndiName = key.substring(this.queuePrefix.length());
            data.put(jndiName, this.createQueue(entry.getValue().toString()));
        }
    }

    protected void createTopics(Map<String, Object> data, Hashtable environment) {
        for (Map.Entry entry : environment.entrySet()) {
            String key = entry.getKey().toString();
            if (!key.startsWith(this.topicPrefix)) continue;
            String jndiName = key.substring(this.topicPrefix.length());
            data.put(jndiName, this.createTopic(entry.getValue().toString()));
        }
    }

    protected Queue createQueue(String name) {
        return new ActiveMQQueue(name);
    }

    protected Topic createTopic(String name) {
        return new ActiveMQTopic(name);
    }

    protected ActiveMQConnectionFactory createConnectionFactory(Hashtable environment) throws URISyntaxException {
        ActiveMQConnectionFactory answer = this.needsXA(environment) ? new ActiveMQXAConnectionFactory() : new ActiveMQConnectionFactory();
        Properties properties = new Properties();
        properties.putAll((Map<?, ?>)environment);
        answer.setProperties(properties);
        return answer;
    }

    protected boolean needsXA(Hashtable environment) {
        boolean isXA = Boolean.parseBoolean((String)environment.get("xa"));
        environment.remove("xa");
        return isXA;
    }

    public String getConnectionPrefix() {
        return this.connectionPrefix;
    }

    public void setConnectionPrefix(String connectionPrefix) {
        this.connectionPrefix = connectionPrefix;
    }
}

