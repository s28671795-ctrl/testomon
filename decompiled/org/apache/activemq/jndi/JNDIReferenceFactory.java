/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import org.apache.activemq.jndi.JNDIStorableInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JNDIReferenceFactory
implements ObjectFactory {
    static Logger log = LoggerFactory.getLogger(JNDIReferenceFactory.class);

    public Object getObjectInstance(Object object, Name name, Context nameCtx, Hashtable environment) throws Exception {
        JNDIStorableInterface result = null;
        if (object instanceof Reference) {
            Class theClass;
            Reference reference = (Reference)object;
            if (log.isTraceEnabled()) {
                log.trace("Getting instance of " + reference.getClassName());
            }
            if (JNDIStorableInterface.class.isAssignableFrom(theClass = JNDIReferenceFactory.loadClass(this, reference.getClassName()))) {
                JNDIStorableInterface store = (JNDIStorableInterface)theClass.newInstance();
                Properties properties = new Properties();
                Enumeration<RefAddr> iter = reference.getAll();
                while (iter.hasMoreElements()) {
                    StringRefAddr addr = (StringRefAddr)iter.nextElement();
                    properties.put(addr.getType(), addr.getContent() == null ? "" : addr.getContent());
                }
                store.setProperties(properties);
                result = store;
            }
        } else {
            log.error("Object " + object + " is not a reference - cannot load");
            throw new RuntimeException("Object " + object + " is not a reference");
        }
        return result;
    }

    public static Reference createReference(String instanceClassName, JNDIStorableInterface po) throws NamingException {
        if (log.isTraceEnabled()) {
            log.trace("Creating reference: " + instanceClassName + "," + po);
        }
        Reference result = new Reference(instanceClassName, JNDIReferenceFactory.class.getName(), null);
        try {
            Properties props = po.getProperties();
            Enumeration<?> iter = props.propertyNames();
            while (iter.hasMoreElements()) {
                String key = (String)iter.nextElement();
                String value = props.getProperty(key);
                StringRefAddr addr = new StringRefAddr(key, value);
                result.add(addr);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new NamingException(e.getMessage());
        }
        return result;
    }

    public static Class loadClass(Object thisObj, String className) throws ClassNotFoundException {
        ClassLoader loader = thisObj.getClass().getClassLoader();
        Class<?> theClass = loader != null ? loader.loadClass(className) : Class.forName(className);
        return theClass;
    }
}

