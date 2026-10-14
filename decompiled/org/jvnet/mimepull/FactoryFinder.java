/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

class FactoryFinder {
    private static ClassLoader cl = FactoryFinder.class.getClassLoader();

    FactoryFinder() {
    }

    static Object find(String factoryId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String systemProp = System.getProperty(factoryId);
        if (systemProp != null) {
            return FactoryFinder.newInstance(systemProp);
        }
        String providerName = FactoryFinder.findJarServiceProviderName(factoryId);
        if (providerName != null && providerName.trim().length() > 0) {
            return FactoryFinder.newInstance(providerName);
        }
        return null;
    }

    static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> providerClass = cl.loadClass(className);
        Object instance = providerClass.newInstance();
        return instance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String findJarServiceProviderName(String factoryId) {
        String factoryClassName;
        String serviceId = "META-INF/services/" + factoryId;
        InputStream is = cl.getResourceAsStream(serviceId);
        if (is == null) {
            return null;
        }
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            try {
                factoryClassName = rd.readLine();
            }
            catch (IOException x) {
                String string = null;
                if (rd != null) {
                    try {
                        rd.close();
                    }
                    catch (IOException ex) {
                        Logger.getLogger(FactoryFinder.class.getName()).log(Level.INFO, null, ex);
                    }
                }
                return string;
            }
        }
        finally {
            if (rd != null) {
                try {
                    rd.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(FactoryFinder.class.getName()).log(Level.INFO, null, ex);
                }
            }
        }
        return factoryClassName;
    }
}

