/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.JavaUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.ReferencingAuthenticator;

public class CXFAuthenticator
extends Authenticator {
    static CXFAuthenticator instance;

    public static synchronized void addAuthenticator() {
        if (instance == null) {
            Method m4;
            instance = new CXFAuthenticator();
            Authenticator wrapped = null;
            if (JavaUtils.isJava9Compatible()) {
                try {
                    Method m5 = ReflectionUtil.getMethod(Authenticator.class, "getDefault", new Class[0]);
                    wrapped = (Authenticator)m5.invoke(null, new Object[0]);
                }
                catch (Exception m5) {}
            } else {
                for (Field f : ReflectionUtil.getDeclaredFields(Authenticator.class)) {
                    if (!f.getType().equals(Authenticator.class)) continue;
                    ReflectionUtil.setAccessible(f);
                    try {
                        wrapped = (Authenticator)f.get(null);
                        if (wrapped != null && wrapped.getClass().getName().equals(ReferencingAuthenticator.class.getName())) {
                            m4 = wrapped.getClass().getMethod("check", new Class[0]);
                            m4.setAccessible(true);
                            m4.invoke((Object)wrapped, new Object[0]);
                        }
                        wrapped = (Authenticator)f.get(null);
                    }
                    catch (Exception m6) {
                        // empty catch block
                    }
                }
            }
            try {
                Class<?> cls = null;
                InputStream ins = ReferencingAuthenticator.class.getResourceAsStream("ReferencingAuthenticator.class");
                byte[] b = IOUtils.readBytesFromStream(ins);
                if (JavaUtils.isJava9Compatible()) {
                    Class<?> methodHandles = Class.forName("java.lang.invoke.MethodHandles");
                    m4 = ReflectionUtil.getMethod(methodHandles, "lookup", new Class[0]);
                    Object lookup = m4.invoke(null, new Object[0]);
                    m4 = ReflectionUtil.getMethod(lookup.getClass(), "findClass", String.class);
                    try {
                        cls = (Class<?>)m4.invoke(lookup, "org.apache.cxf.transport.http.ReferencingAuthenticator");
                    }
                    catch (InvocationTargetException e) {
                        m4 = ReflectionUtil.getMethod(lookup.getClass(), "defineClass", byte[].class);
                        cls = (Class)m4.invoke(lookup, new Object[]{b});
                    }
                } else {
                    ClassLoader loader = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                        @Override
                        public ClassLoader run() {
                            return new URLClassLoader(new URL[0], ClassLoader.getSystemClassLoader());
                        }
                    }, null);
                    m4 = ReflectionUtil.getDeclaredMethod(ClassLoader.class, "defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                    ReflectionUtil.setAccessible(m4).invoke((Object)loader, ReferencingAuthenticator.class.getName(), b, 0, b.length);
                    cls = loader.loadClass(ReferencingAuthenticator.class.getName());
                    try {
                        Field f = ReflectionUtil.getDeclaredField(loader.getClass(), "acc");
                        ReflectionUtil.setAccessible(f).set(loader, null);
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                final Authenticator auth = (Authenticator)cls.getConstructor(Authenticator.class, Authenticator.class).newInstance(instance, wrapped);
                if (System.getSecurityManager() == null) {
                    Authenticator.setDefault(auth);
                } else {
                    AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                        @Override
                        public Boolean run() {
                            Authenticator.setDefault(auth);
                            return true;
                        }
                    });
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        Exchange exchange;
        Conduit conduit;
        PasswordAuthentication auth = null;
        Message m4 = PhaseInterceptorChain.getCurrentMessage();
        if (m4 != null && (conduit = (exchange = m4.getExchange()).getConduit(m4)) instanceof HTTPConduit) {
            HTTPConduit httpConduit = (HTTPConduit)conduit;
            if (this.getRequestorType() == Authenticator.RequestorType.PROXY && httpConduit.getProxyAuthorization() != null) {
                String un = httpConduit.getProxyAuthorization().getUserName();
                String pwd = httpConduit.getProxyAuthorization().getPassword();
                if (un != null && pwd != null) {
                    auth = new PasswordAuthentication(un, pwd.toCharArray());
                }
            } else if (this.getRequestorType() == Authenticator.RequestorType.SERVER && httpConduit.getAuthorization() != null) {
                if ("basic".equals(this.getRequestingScheme()) || "digest".equals(this.getRequestingScheme())) {
                    return null;
                }
                String un = httpConduit.getAuthorization().getUserName();
                String pwd = httpConduit.getAuthorization().getPassword();
                if (un != null && pwd != null) {
                    auth = new PasswordAuthentication(un, pwd.toCharArray());
                }
            }
        }
        return auth;
    }
}

