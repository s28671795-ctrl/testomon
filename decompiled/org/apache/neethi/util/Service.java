/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Service {
    static Map<String, List<?>> instanceMap = new HashMap();

    private Service() {
    }

    private static <T> List<T> cast(List<?> p) {
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static synchronized <T> List<? extends T> providers(Class<T> cls) {
        Enumeration<URL> e;
        String serviceFile = "META-INF/services/" + cls.getName();
        List<T> l = Service.cast(instanceMap.get(serviceFile));
        if (l != null) {
            return l;
        }
        l = new ArrayList<T>();
        instanceMap.put(serviceFile, l);
        ClassLoader cl = null;
        try {
            cl = cls.getClassLoader();
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        if (cl == null) {
            cl = Service.class.getClassLoader();
        }
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        if (cl == null) {
            return l;
        }
        try {
            e = cl.getResources(serviceFile);
        }
        catch (IOException ioe) {
            return l;
        }
        block21: while (e.hasMoreElements()) {
            InputStream is = null;
            try {
                URL u = e.nextElement();
                is = u.openStream();
                InputStreamReader r = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(r);
                String line = br.readLine();
                while (true) {
                    if (line == null) continue block21;
                    try {
                        int idx = line.indexOf(35);
                        if (idx != -1) {
                            line = line.substring(0, idx);
                        }
                        if ((line = line.trim()).length() == 0) {
                            line = br.readLine();
                            continue;
                        }
                        Object obj = cl.loadClass(line).newInstance();
                        l.add(cls.cast(obj));
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    line = br.readLine();
                }
            }
            catch (Exception exception) {
                continue;
            }
            catch (LinkageError linkageError) {
                continue;
            }
            finally {
                try {
                    if (is == null) continue;
                    is.close();
                }
                catch (IOException iOException) {}
                continue;
            }
            break;
        }
        return l;
    }
}

