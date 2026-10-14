/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.Provider;
import java.security.Security;
import org.apache.wss4j.common.crypto.SantuarioUtil;
import org.apache.wss4j.common.crypto.ThreadLocalSecurityProvider;
import org.apache.wss4j.common.crypto.WSS4JResourceBundle;
import org.apache.wss4j.common.util.Loader;
import org.apache.xml.security.Init;
import org.apache.xml.security.utils.I18n;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WSProviderConfig {
    private static final Logger LOG = LoggerFactory.getLogger(WSProviderConfig.class);
    private static boolean addJceProviders = true;
    private static boolean staticallyInitialized;
    private static boolean santuarioProviderAdded;
    private static boolean bcProviderAdded;
    private static boolean tlProviderAdded;

    private WSProviderConfig() {
    }

    public static synchronized void init() {
        if (!staticallyInitialized) {
            if (addJceProviders) {
                WSProviderConfig.initializeResourceBundles();
                WSProviderConfig.setXmlSecIgnoreLineBreak();
                AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                    @Override
                    public Boolean run() {
                        WSProviderConfig.addXMLDSigRIInternal();
                        return true;
                    }
                });
                santuarioProviderAdded = true;
                bcProviderAdded = false;
                tlProviderAdded = false;
            }
            staticallyInitialized = true;
        }
    }

    public static synchronized void init(boolean addXMLDSigRIInternalProv, boolean addBCProv, boolean addTLProv) {
        if (!staticallyInitialized) {
            WSProviderConfig.initializeResourceBundles();
            WSProviderConfig.setXmlSecIgnoreLineBreak();
            santuarioProviderAdded = addXMLDSigRIInternalProv;
            if (addXMLDSigRIInternalProv) {
                AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                    @Override
                    public Boolean run() {
                        WSProviderConfig.addXMLDSigRIInternal();
                        return true;
                    }
                });
            }
            bcProviderAdded = addBCProv;
            if (addBCProv) {
                AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                    @Override
                    public Boolean run() {
                        Provider bcProvider;
                        String bcProviderStr = WSProviderConfig.addJceProvider("BC", "org.bouncycastle.jce.provider.BouncyCastleProvider");
                        if (bcProviderStr != null && (bcProvider = Security.getProvider(bcProviderStr)).getVersion() < 1.5) {
                            WSProviderConfig.useIvParameterSpec();
                        }
                        return true;
                    }
                });
            }
            tlProviderAdded = addTLProv;
            if (addTLProv) {
                AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                    @Override
                    public Boolean run() {
                        ThreadLocalSecurityProvider.install();
                        return true;
                    }
                });
            }
            staticallyInitialized = true;
        }
    }

    public static synchronized void cleanUp() {
        if (staticallyInitialized) {
            if (santuarioProviderAdded) {
                Security.removeProvider("ApacheXMLDSig");
                santuarioProviderAdded = false;
            }
            if (bcProviderAdded) {
                Security.removeProvider("BC");
                bcProviderAdded = false;
            }
            if (tlProviderAdded) {
                Security.removeProvider("TLSP");
                tlProviderAdded = false;
            }
            staticallyInitialized = false;
        }
    }

    public static void setAddJceProviders(boolean value) {
        addJceProviders = value;
    }

    public static void setXmlSecIgnoreLineBreak() {
        boolean wasSet = false;
        try {
            wasSet = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                @Override
                public Boolean run() {
                    String lineBreakPropName = "org.apache.xml.security.ignoreLineBreaks";
                    if (System.getProperty(lineBreakPropName) == null) {
                        System.setProperty(lineBreakPropName, "true");
                        return false;
                    }
                    return true;
                }
            });
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        Init.init();
        if (!wasSet) {
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>(){

                    @Override
                    public Boolean run() throws Exception {
                        Field f = XMLUtils.class.getDeclaredField("ignoreLineBreaks");
                        f.setAccessible(true);
                        f.set(null, Boolean.TRUE);
                        return false;
                    }
                });
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    private static void useIvParameterSpec() {
        try {
            AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                @Override
                public Boolean run() {
                    String ivParameterSpec = "org.apache.xml.security.cipher.gcm.useIvParameterSpec";
                    if (System.getProperty(ivParameterSpec) == null) {
                        System.setProperty(ivParameterSpec, "true");
                        return false;
                    }
                    return true;
                }
            });
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static void addXMLDSigRIInternal() {
        Security.removeProvider("ApacheXMLDSig");
        WSProviderConfig.addJceProvider("ApacheXMLDSig", SantuarioUtil.getSantuarioProvider());
    }

    private static void initializeResourceBundles() {
        I18n.init(new WSS4JResourceBundle());
    }

    public static String addJceProvider(String name, String className) {
        Provider currentProvider = Security.getProvider(name);
        if (currentProvider == null) {
            try {
                Class<Provider> clazz = Loader.loadClass(className, false, Provider.class);
                Provider provider = clazz.newInstance();
                return WSProviderConfig.addJceProvider(name, provider);
            }
            catch (Throwable t2) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The provider " + name + " could not be added: " + t2.getMessage(), t2);
                }
                return null;
            }
        }
        return currentProvider.getName();
    }

    public static String addJceProvider(String name, Provider provider) {
        Provider currentProvider = Security.getProvider(name);
        if (currentProvider == null) {
            try {
                int ret = 0;
                Provider[] provs = Security.getProviders();
                for (int i = 0; i < provs.length; ++i) {
                    if (!"SUN".equals(provs[i].getName()) && !"IBMJCE".equals(provs[i].getName())) continue;
                    ret = Security.insertProviderAt(provider, i + 2);
                    break;
                }
                if (ret == 0) {
                    ret = Security.insertProviderAt(provider, 2);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The provider " + provider.getName() + " - " + provider.getVersion() + " was added at position: " + ret);
                }
                return provider.getName();
            }
            catch (Throwable t2) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The provider " + name + " could not be added: " + t2.getMessage(), t2);
                }
                return null;
            }
        }
        return currentProvider.getName();
    }

    public static String appendJceProvider(String name, String className) {
        Provider currentProvider = Security.getProvider(name);
        if (currentProvider == null) {
            try {
                Class<Provider> clazz = Loader.loadClass(className, false, Provider.class);
                Provider provider = clazz.newInstance();
                int ret = Security.addProvider(provider);
                LOG.debug("The provider {} was added at position: {}", (Object)provider.getName(), (Object)ret);
                return provider.getName();
            }
            catch (Throwable t2) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The provider " + name + " could not be added: " + t2.getMessage(), t2);
                }
                return null;
            }
        }
        return currentProvider.getName();
    }

    public static String appendJceProvider(String name, Provider provider) {
        Provider currentProvider = Security.getProvider(name);
        if (currentProvider == null) {
            try {
                int ret = Security.addProvider(provider);
                LOG.debug("The provider {} was added at position: {}", (Object)provider.getName(), (Object)ret);
                return provider.getName();
            }
            catch (Throwable t2) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The provider " + name + " could not be added: " + t2.getMessage(), t2);
                }
                return null;
            }
        }
        return currentProvider.getName();
    }
}

