/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.ldaptive.io.ResourceUtils;
import org.ldaptive.props.PropertyInvoker;
import org.ldaptive.props.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPropertySource<T>
implements PropertySource<T> {
    public static final String PROPERTIES_FILE = "classpath:/org/ldaptive/ldap.properties";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final T object;
    protected final PropertySource.PropertyDomain propertiesDomain;
    protected final Properties properties;
    protected final Map<String, Object> extraProps = new HashMap<String, Object>();

    public AbstractPropertySource(T t2, PropertySource.PropertyDomain pd, Properties p) {
        this.object = t2;
        this.propertiesDomain = pd;
        this.properties = p;
    }

    protected static Properties loadProperties(String ... paths) {
        try {
            Reader[] readers = new Reader[paths.length];
            for (int i = 0; i < paths.length; ++i) {
                readers[i] = new InputStreamReader(ResourceUtils.getResource(paths[i]));
            }
            return AbstractPropertySource.loadProperties(readers);
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected static Properties loadProperties(Reader ... readers) {
        try {
            Properties properties = new Properties();
            Reader[] readerArray = readers;
            int n = readerArray.length;
            for (int i = 0; i < n; ++i) {
                Reader r;
                try (Reader reader = r = readerArray[i];){
                    properties.load(r);
                    continue;
                }
            }
            return properties;
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void initializeObject(PropertyInvoker invoker) {
        HashMap<String, String> props = new HashMap<String, String>();
        Enumeration<Object> en = this.properties.keys();
        if (en != null) {
            while (en.hasMoreElements()) {
                String name = (String)en.nextElement();
                String value = (String)this.properties.get(name);
                if (!name.startsWith(PropertySource.PropertyDomain.LDAP.value())) {
                    this.extraProps.put(name, value);
                    continue;
                }
                int split = name.lastIndexOf(46) + 1;
                String propName = name.substring(split);
                String propDomain = name.substring(0, split);
                if (this.propertiesDomain.value().equals(propDomain)) {
                    if (!invoker.hasProperty(propName)) continue;
                    props.put(propName, value);
                    continue;
                }
                if (!this.propertiesDomain.value().startsWith(propDomain) || !invoker.hasProperty(propName)) continue;
                invoker.setProperty(this.object, propName, value);
            }
            for (Map.Entry entry : props.entrySet()) {
                invoker.setProperty(this.object, (String)entry.getKey(), (String)entry.getValue());
            }
        }
    }
}

