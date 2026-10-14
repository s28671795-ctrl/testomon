/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import org.apache.activemq.broker.BrokerFactoryHandler;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.util.IntrospectionSupport;

public class PropertiesBrokerFactory
implements BrokerFactoryHandler {
    @Override
    public BrokerService createBroker(URI brokerURI) throws Exception {
        Map<Object, Object> properties = this.loadProperties(brokerURI);
        BrokerService brokerService = this.createBrokerService(brokerURI, properties);
        IntrospectionSupport.setProperties(brokerService, properties);
        return brokerService;
    }

    protected Map<Object, Object> loadProperties(URI brokerURI) throws IOException {
        String remaining = brokerURI.getSchemeSpecificPart();
        Properties properties = new Properties();
        File file = new File(remaining);
        try (InputStream inputStream2 = this.loadStream(file, remaining);){
            if (inputStream2 != null) {
                properties.load(inputStream2);
            }
        }
        try {
            Properties systemProperties = System.getProperties();
            properties.putAll((Map<?, ?>)systemProperties);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return properties;
    }

    protected InputStream loadStream(File file, String remaining) throws IOException {
        InputStream inputStream2 = null;
        if (file != null && file.exists()) {
            inputStream2 = new FileInputStream(file);
        } else {
            URL url2;
            block5: {
                url2 = null;
                try {
                    url2 = new URL(remaining);
                }
                catch (MalformedURLException e) {
                    inputStream2 = this.findResourceOnClassPath(remaining);
                    if (inputStream2 != null) break block5;
                    throw new IOException("File does not exist: " + remaining + ", could not be found on the classpath and is not a valid URL: " + e);
                }
            }
            if (inputStream2 == null && url2 != null) {
                inputStream2 = url2.openStream();
            }
        }
        return inputStream2;
    }

    protected InputStream findResourceOnClassPath(String remaining) {
        InputStream answer = Thread.currentThread().getContextClassLoader().getResourceAsStream(remaining);
        if (answer == null) {
            answer = this.getClass().getClassLoader().getResourceAsStream(remaining);
        }
        return answer;
    }

    protected BrokerService createBrokerService(URI brokerURI, Map<Object, Object> properties) {
        return new BrokerService();
    }
}

