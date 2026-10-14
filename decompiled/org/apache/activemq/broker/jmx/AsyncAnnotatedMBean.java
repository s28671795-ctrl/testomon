/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.management.MBeanException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.ManagementContext;

public class AsyncAnnotatedMBean
extends AnnotatedMBean {
    private ExecutorService executor;
    private long timeout = 0L;

    public <T> AsyncAnnotatedMBean(ExecutorService executor2, long timeout2, T impl, Class<T> mbeanInterface, ObjectName objectName) throws NotCompliantMBeanException {
        super(impl, mbeanInterface, objectName);
        this.executor = executor2;
        this.timeout = timeout2;
    }

    protected AsyncAnnotatedMBean(Class<?> mbeanInterface, ObjectName objectName) throws NotCompliantMBeanException {
        super(mbeanInterface, objectName);
    }

    protected Object asyncInvole(String s2, Object[] objects, String[] strings) throws MBeanException, ReflectionException {
        return super.invoke(s2, objects, strings);
    }

    public static ObjectInstance registerMBean(ExecutorService executor2, long timeout2, ManagementContext context, Object object, ObjectName objectName) throws Exception {
        if (timeout2 < 0L && executor2 != null) {
            throw new IllegalArgumentException("async timeout cannot be negative.");
        }
        if (timeout2 > 0L && executor2 == null) {
            throw new NullPointerException("timeout given but no ExecutorService instance given.");
        }
        String mbeanName = object.getClass().getName() + "MBean";
        for (Class<?> c : object.getClass().getInterfaces()) {
            if (!mbeanName.equals(c.getName())) continue;
            if (timeout2 == 0L) {
                return context.registerMBean(new AnnotatedMBean(object, c, objectName), objectName);
            }
            return context.registerMBean(new AsyncAnnotatedMBean(executor2, timeout2, object, c, objectName), objectName);
        }
        return context.registerMBean(object, objectName);
    }

    @Override
    public Object invoke(String s2, Object[] objects, String[] strings) throws MBeanException, ReflectionException {
        final String action = s2;
        final Object[] params = objects;
        final String[] signature = strings;
        Future<Object> task = this.executor.submit(new Callable<Object>(){

            @Override
            public Object call() throws Exception {
                return AsyncAnnotatedMBean.this.asyncInvole(action, params, signature);
            }
        });
        try {
            Object object = task.get(this.timeout, TimeUnit.MILLISECONDS);
            return object;
        }
        catch (ExecutionException e) {
            if (e.getCause() instanceof MBeanException) {
                throw (MBeanException)e.getCause();
            }
            throw new MBeanException(e);
        }
        catch (Exception e) {
            throw new MBeanException(e);
        }
        finally {
            if (!task.isDone()) {
                task.cancel(true);
            }
        }
    }
}

