/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Security;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.StringUtils;

final class JDKBugHacks {
    private JDKBugHacks() {
    }

    private static boolean skipHack(String key) {
        return JDKBugHacks.skipHack(key, "false");
    }

    private static boolean skipHack(final String key, String def) {
        String cname;
        block17: {
            cname = null;
            try {
                cname = AccessController.doPrivileged(new PrivilegedAction<String>(){

                    @Override
                    public String run() {
                        return System.getProperty(key);
                    }
                });
                if (!StringUtils.isEmpty(cname)) break block17;
                InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/cxf/" + key);
                if (ins == null) {
                    ins = ClassLoader.getSystemResourceAsStream("META-INF/cxf/" + key);
                }
                if (ins == null) break block17;
                try (BufferedReader din = new BufferedReader(new InputStreamReader(ins));){
                    cname = din.readLine();
                    if (cname != null) {
                        cname = cname.trim();
                    }
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (StringUtils.isEmpty(cname)) {
            cname = def;
        }
        return Boolean.parseBoolean(cname);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void doHacks() {
        if (JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.all")) {
            return;
        }
        try {
            ClassLoaderUtils.ClassLoaderHolder orig = ClassLoaderUtils.setThreadContextClassloader(ClassLoader.getSystemClassLoader());
            try {
                Method method2;
                try {
                    if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.imageIO", "true")) {
                        ImageIO.getCacheDirectory();
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                try {
                    if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.documentBuilderFactory")) {
                        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
                        factory2.newDocumentBuilder();
                    }
                }
                catch (Throwable factory2) {
                    // empty catch block
                }
                try {
                    Class<?> clazz;
                    Long l;
                    if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.gcRequestLatency") && (l = (Long)(method2 = (clazz = Class.forName("sun.misc.GC")).getDeclaredMethod("currentLatencyTarget", new Class[0])).invoke(null, new Object[0])) != null && l == 0L) {
                        method2 = clazz.getDeclaredMethod("requestLatency", Long.TYPE);
                        method2.invoke(null, 36000000L);
                    }
                }
                catch (Throwable clazz) {
                    // empty catch block
                }
                try {
                    if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.policy")) {
                        Class<?> policyClass = Class.forName("javax.security.auth.Policy");
                        method2 = policyClass.getMethod("getPolicy", new Class[0]);
                        method2.invoke(null, new Object[0]);
                    }
                }
                catch (Throwable policyClass) {
                    // empty catch block
                }
                try {
                    if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.authConfiguration")) {
                        Class.forName("javax.security.auth.login.Configuration", true, ClassLoader.getSystemClassLoader());
                    }
                }
                catch (Throwable policyClass) {
                    // empty catch block
                }
                if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.securityProviders")) {
                    Security.getProviders();
                }
                try {
                    if (!JDKBugHacks.skipHack("org.apache.cxf.JDKBugHacks.defaultUsesCaches")) {
                        URL url2 = new URL("jar:file://dummy.jar!/");
                        URLConnection uConn = new URLConnection(url2){

                            @Override
                            public void connect() throws IOException {
                            }
                        };
                        uConn.setDefaultUseCaches(false);
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            finally {
                if (orig != null) {
                    orig.reset();
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }
}

