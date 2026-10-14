/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.StandardMBean;
import javax.security.auth.Subject;
import org.apache.activemq.broker.jmx.MBeanInfo;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.util.AuditLogEntry;
import org.apache.activemq.broker.util.AuditLogService;
import org.apache.activemq.broker.util.JMXAuditLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotatedMBean
extends StandardMBean {
    private static final Map<String, Class<?>> primitives;
    private static final Logger LOG;
    private static final byte OFF = 0;
    private static final byte ENTRY = 1;
    private static final byte EXIT = 2;
    private static final byte ALL = 3;
    private static byte audit;
    private static AuditLogService auditLog;
    private final ObjectName objectName;
    static final String[] targetPropertiesCandidates;

    private static byte byteFromProperty(String s2) {
        int val = 0;
        String config = System.getProperty(s2, "").toLowerCase(Locale.ENGLISH);
        if ("true".equals(config) || "entry".equals(config)) {
            val = 1;
        } else if ("exit".equals(config)) {
            val = 2;
        } else if ("all".equals(config)) {
            val = 3;
        }
        return (byte)val;
    }

    public static void registerMBean(ManagementContext context, Object object, ObjectName objectName) throws Exception {
        String mbeanName = object.getClass().getName() + "MBean";
        for (Class<?> c : object.getClass().getInterfaces()) {
            if (!mbeanName.equals(c.getName())) continue;
            context.registerMBean(new AnnotatedMBean(object, c, objectName), objectName);
            return;
        }
        context.registerMBean(object, objectName);
    }

    public <T> AnnotatedMBean(T impl, Class<T> mbeanInterface, ObjectName objectName) throws NotCompliantMBeanException {
        super(impl, mbeanInterface);
        this.objectName = objectName;
    }

    protected AnnotatedMBean(Class<?> mbeanInterface, ObjectName objectName) throws NotCompliantMBeanException {
        super(mbeanInterface);
        this.objectName = objectName;
    }

    @Override
    protected String getDescription(MBeanAttributeInfo info) {
        MBeanInfo d;
        String descr = info.getDescription();
        Method m4 = AnnotatedMBean.getMethod(this.getMBeanInterface(), "get" + info.getName().substring(0, 1).toUpperCase() + info.getName().substring(1), new String[0]);
        if (m4 == null) {
            m4 = AnnotatedMBean.getMethod(this.getMBeanInterface(), "is" + info.getName().substring(0, 1).toUpperCase() + info.getName().substring(1), new String[0]);
        }
        if (m4 == null) {
            m4 = AnnotatedMBean.getMethod(this.getMBeanInterface(), "does" + info.getName().substring(0, 1).toUpperCase() + info.getName().substring(1), new String[0]);
        }
        if (m4 != null && (d = m4.getAnnotation(MBeanInfo.class)) != null) {
            descr = d.value();
        }
        return descr;
    }

    @Override
    protected String getDescription(MBeanOperationInfo op) {
        MBeanInfo d;
        String descr = op.getDescription();
        Method m4 = this.getMethod(op);
        if (m4 != null && (d = m4.getAnnotation(MBeanInfo.class)) != null) {
            descr = d.value();
        }
        return descr;
    }

    @Override
    protected String getParameterName(MBeanOperationInfo op, MBeanParameterInfo param2, int paramNo) {
        String name = param2.getName();
        Method m4 = this.getMethod(op);
        if (m4 != null) {
            for (Annotation a : m4.getParameterAnnotations()[paramNo]) {
                if (!MBeanInfo.class.isInstance(a)) continue;
                name = ((MBeanInfo)MBeanInfo.class.cast(a)).value();
            }
        }
        return name;
    }

    private Method getMethod(MBeanOperationInfo op) {
        MBeanParameterInfo[] params = op.getSignature();
        String[] paramTypes = new String[params.length];
        for (int i = 0; i < params.length; ++i) {
            paramTypes[i] = params[i].getType();
        }
        return AnnotatedMBean.getMethod(this.getMBeanInterface(), op.getName(), paramTypes);
    }

    private static Method getMethod(Class<?> mbean, String method2, String ... params) {
        try {
            ClassLoader loader = mbean.getClassLoader();
            Class[] paramClasses = new Class[params.length];
            for (int i = 0; i < params.length; ++i) {
                paramClasses[i] = primitives.get(params[i]);
                if (paramClasses[i] != null) continue;
                paramClasses[i] = Class.forName(params[i], false, loader);
            }
            return mbean.getMethod(method2, paramClasses);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object invoke(String s2, Object[] objects, String[] strings) throws MBeanException, ReflectionException {
        JMXAuditLogEntry entry = null;
        if (audit != 0) {
            Subject subject = Subject.getSubject(AccessController.getContext());
            String caller = "anonymous";
            if (subject != null) {
                caller = "";
                for (Principal principal : subject.getPrincipals()) {
                    caller = caller + principal.getName() + " ";
                }
            }
            entry = new JMXAuditLogEntry();
            entry.setUser(caller);
            entry.setTimestamp(System.currentTimeMillis());
            entry.setTarget(this.extractTargetTypeProperty(this.objectName));
            entry.setOperation(this.getMBeanInfo().getClassName() + "." + s2);
            try {
                if (objects.length == strings.length) {
                    Method m4 = this.getMBeanMethod(this.getImplementationClass(), s2, strings);
                    entry.getParameters().put("arguments", AuditLogEntry.sanitizeArguments(objects, m4));
                } else {
                    entry.getParameters().put("arguments", objects);
                }
            }
            catch (ReflectiveOperationException e) {
                entry.getParameters().put("arguments", objects);
            }
            if ((audit & 1) == 1) {
                auditLog.log(entry);
            }
        }
        Object result = super.invoke(s2, objects, strings);
        if ((audit & 2) == 2) {
            entry.complete();
            auditLog.log(entry);
        }
        return result;
    }

    private String extractTargetTypeProperty(ObjectName objectName) {
        String result = null;
        for (String attr : targetPropertiesCandidates) {
            try {
                result = objectName.getKeyProperty(attr);
                if (result == null) continue;
                break;
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
        return result;
    }

    private Method getMBeanMethod(Class clazz, String methodName, String[] signature) throws ReflectiveOperationException {
        Class[] parameterTypes = new Class[signature.length];
        for (int i = 0; i < signature.length; ++i) {
            parameterTypes[i] = Class.forName(signature[i]);
        }
        return clazz.getMethod(methodName, parameterTypes);
    }

    static {
        Class[] p;
        primitives = new HashMap();
        LOG = LoggerFactory.getLogger("org.apache.activemq.audit");
        audit = 0;
        for (Class c : p = new Class[]{Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Character.TYPE, Boolean.TYPE}) {
            primitives.put(c.getName(), c);
        }
        audit = AnnotatedMBean.byteFromProperty("org.apache.activemq.audit");
        if (audit != 0) {
            auditLog = AuditLogService.getAuditLog();
        }
        targetPropertiesCandidates = new String[]{"destinationName", "networkConnectorName", "connectorName", "connectionName", "brokerName"};
    }
}

