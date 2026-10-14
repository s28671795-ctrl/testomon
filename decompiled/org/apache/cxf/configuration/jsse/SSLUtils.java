/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.resource.ResourceManager;

public final class SSLUtils {
    static final String PKCS12_TYPE = "PKCS12";
    private static final String DEFAULT_KEYSTORE_TYPE = "JKS";
    private static final String DEFAULT_TRUST_STORE_TYPE = "JKS";
    private static final String HTTPS_CIPHER_SUITES = "https.cipherSuites";
    private static final List<String> DEFAULT_CIPHERSUITE_FILTERS_EXCLUDE = Arrays.asList(".*NULL.*", ".*anon.*", ".*EXPORT.*", ".*DES.*", ".*MD5", ".*CBC.*", ".*RC4.*");
    private static volatile KeyManager[] defaultManagers;

    private SSLUtils() {
    }

    public static KeyManager[] getDefaultKeyStoreManagers(Logger log) {
        if (defaultManagers == null) {
            SSLUtils.loadDefaultKeyManagers(log);
        }
        if (defaultManagers.length == 0) {
            return null;
        }
        return defaultManagers;
    }

    private static synchronized void loadDefaultKeyManagers(Logger log) {
        if (defaultManagers != null) {
            return;
        }
        String location = SSLUtils.getKeystore(null, log);
        String keyStorePassword = SSLUtils.getKeystorePassword(null, log);
        String keyPassword = SSLUtils.getKeyPassword(null, log);
        String keyStoreType = SSLUtils.getKeystoreType(null, log);
        if (location != null) {
            File file = new File(location);
            try (InputStream is = FileUtils.exists(file) ? Files.newInputStream(file.toPath(), new OpenOption[0]) : SSLUtils.getResourceAsStream(location);){
                if (is != null) {
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    KeyStore ks = KeyStore.getInstance(keyStoreType != null ? keyStoreType : KeyStore.getDefaultType());
                    ks.load(is, keyStorePassword != null ? keyStorePassword.toCharArray() : null);
                    kmf.init(ks, keyPassword != null ? keyPassword.toCharArray() : null);
                    defaultManagers = kmf.getKeyManagers();
                }
            }
            catch (Exception e) {
                log.log(Level.WARNING, "Default key managers cannot be initialized: " + e.getMessage(), e);
                defaultManagers = new KeyManager[0];
            }
        }
        if (null == defaultManagers) {
            log.log(Level.FINER, "No default keystore {0}", location);
            defaultManagers = new KeyManager[0];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TrustManager[] getDefaultTrustStoreManagers(Logger log) {
        String location = SSLUtils.getTruststore(null, log);
        String trustStorePassword = SSLUtils.getTruststorePassword(null, log);
        String trustStoreType = SSLUtils.getTrustStoreType(null, log, "JKS");
        if (location == null) return null;
        File file = new File(location);
        try (InputStream is = FileUtils.exists(file) ? Files.newInputStream(file.toPath(), new OpenOption[0]) : SSLUtils.getResourceAsStream(location);){
            if (is != null) {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                KeyStore ks = KeyStore.getInstance(trustStoreType);
                ks.load(is, trustStorePassword != null ? trustStorePassword.toCharArray() : null);
                tmf.init(ks);
                TrustManager[] trustManagerArray = tmf.getTrustManagers();
                return trustManagerArray;
            }
            log.log(Level.FINER, "No default trust keystore {0}", location);
            return null;
        }
        catch (Exception e) {
            log.log(Level.WARNING, "Default trust managers cannot be initialized: " + e.getMessage(), e);
        }
        return null;
    }

    private static InputStream getResourceAsStream(String resource) {
        Bus bus;
        ResourceManager rm;
        InputStream is = ClassLoaderUtils.getResourceAsStream(resource, SSLUtils.class);
        if (is == null && (rm = (bus = BusFactory.getThreadDefaultBus(true)).getExtension(ResourceManager.class)) != null) {
            is = rm.getResourceAsStream(resource);
        }
        return is;
    }

    public static KeyManager[] loadKeyStore(KeyManagerFactory kmf, KeyStore ks, ByteArrayInputStream bin, String keyStoreLocation, String keyStorePassword, Logger log) {
        KeyManager[] keystoreManagers = null;
        try {
            ks.load(bin, keyStorePassword.toCharArray());
            kmf.init(ks, keyStorePassword.toCharArray());
            keystoreManagers = kmf.getKeyManagers();
            LogUtils.log(log, Level.FINE, "LOADED_KEYSTORE", keyStoreLocation);
        }
        catch (Exception e) {
            LogUtils.log(log, Level.WARNING, "FAILED_TO_LOAD_KEYSTORE", new Object[]{keyStoreLocation, e.getMessage()});
        }
        return keystoreManagers;
    }

    protected static byte[] loadFile(String fileName) throws IOException {
        if (fileName == null) {
            return null;
        }
        Path path = FileSystems.getDefault().getPath(fileName, new String[0]);
        return Files.readAllBytes(path);
    }

    public static String getKeystore(String keyStoreLocation, Logger log) {
        String logMsg;
        if (keyStoreLocation != null) {
            logMsg = "KEY_STORE_SET";
        } else {
            keyStoreLocation = SystemPropertyAction.getProperty("javax.net.ssl.keyStore");
            if (keyStoreLocation != null) {
                logMsg = "KEY_STORE_SYSTEM_PROPERTY_SET";
            } else {
                keyStoreLocation = SystemPropertyAction.getProperty("user.home") + "/.keystore";
                logMsg = "KEY_STORE_NOT_SET";
            }
        }
        LogUtils.log(log, Level.FINE, logMsg, keyStoreLocation);
        return keyStoreLocation;
    }

    public static String getKeystoreType(String keyStoreType, Logger log) {
        return SSLUtils.getKeystoreType(keyStoreType, log, "JKS");
    }

    public static String getKeystoreType(String keyStoreType, Logger log, String def) {
        String logMsg;
        if (keyStoreType != null) {
            logMsg = "KEY_STORE_TYPE_SET";
        } else {
            keyStoreType = SystemPropertyAction.getProperty("javax.net.ssl.keyStoreType", null);
            if (keyStoreType == null) {
                keyStoreType = def;
                logMsg = "KEY_STORE_TYPE_NOT_SET";
            } else {
                logMsg = "KEY_STORE_TYPE_SYSTEM_SET";
            }
        }
        LogUtils.log(log, Level.FINE, logMsg, keyStoreType);
        return keyStoreType;
    }

    public static String getKeystoreProvider(String keyStoreProvider, Logger log) {
        String logMsg = keyStoreProvider != null ? "KEY_STORE_PROVIDER_SET" : ((keyStoreProvider = SystemPropertyAction.getProperty("javax.net.ssl.keyStoreProvider", null)) == null ? "KEY_STORE_PROVIDER_NOT_SET" : "KEY_STORE_PROVIDER_SYSTEM_SET");
        LogUtils.log(log, Level.FINE, logMsg, keyStoreProvider);
        return keyStoreProvider;
    }

    public static String getKeystorePassword(String keyStorePassword, Logger log) {
        String logMsg = keyStorePassword != null ? "KEY_STORE_PASSWORD_SET" : ((keyStorePassword = SystemPropertyAction.getProperty("javax.net.ssl.keyStorePassword")) != null ? "KEY_STORE_PASSWORD_SYSTEM_PROPERTY_SET" : "KEY_STORE_PASSWORD_NOT_SET");
        LogUtils.log(log, Level.FINE, logMsg);
        return keyStorePassword;
    }

    public static String getKeyPassword(String keyPassword, Logger log) {
        String logMsg;
        if (keyPassword != null) {
            logMsg = "KEY_PASSWORD_SET";
        } else {
            keyPassword = SystemPropertyAction.getProperty("javax.net.ssl.keyPassword");
            if (keyPassword == null) {
                keyPassword = SystemPropertyAction.getProperty("javax.net.ssl.keyStorePassword");
            }
            logMsg = keyPassword != null ? "KEY_PASSWORD_SYSTEM_PROPERTY_SET" : "KEY_PASSWORD_NOT_SET";
        }
        LogUtils.log(log, Level.FINE, logMsg);
        return keyPassword;
    }

    public static String getKeystoreAlgorithm(String keyStoreMgrFactoryAlgorithm, Logger log) {
        String logMsg;
        if (keyStoreMgrFactoryAlgorithm != null) {
            logMsg = "KEY_STORE_ALGORITHM_SET";
        } else {
            keyStoreMgrFactoryAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
            logMsg = "KEY_STORE_ALGORITHM_NOT_SET";
        }
        LogUtils.log(log, Level.FINE, logMsg, keyStoreMgrFactoryAlgorithm);
        return keyStoreMgrFactoryAlgorithm;
    }

    public static String getTrustStoreAlgorithm(String trustStoreMgrFactoryAlgorithm, Logger log) {
        String logMsg;
        if (trustStoreMgrFactoryAlgorithm != null) {
            logMsg = "TRUST_STORE_ALGORITHM_SET";
        } else {
            trustStoreMgrFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            logMsg = "TRUST_STORE_ALGORITHM_NOT_SET";
        }
        LogUtils.log(log, Level.FINE, logMsg, trustStoreMgrFactoryAlgorithm);
        return trustStoreMgrFactoryAlgorithm;
    }

    public static SSLContext getSSLContext(String protocol, KeyManager[] keyStoreManagers, TrustManager[] trustStoreManagers) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance(protocol);
        ctx.init(keyStoreManagers, trustStoreManagers, null);
        return ctx;
    }

    public static String[] getSupportedCipherSuites(SSLContext context) {
        return context.getSocketFactory().getSupportedCipherSuites();
    }

    public static String[] getServerSupportedCipherSuites(SSLContext context) {
        return context.getServerSocketFactory().getSupportedCipherSuites();
    }

    public static String[] getCiphersuitesToInclude(List<String> cipherSuitesList, FiltersType filters, String[] defaultCipherSuites, String[] supportedCipherSuites, Logger log) {
        if (cipherSuitesList != null && !cipherSuitesList.isEmpty()) {
            return SSLUtils.getCiphersFromList(cipherSuitesList, log, false);
        }
        String[] cipherSuites = SSLUtils.getSystemCiphersuites(log);
        if (cipherSuites != null) {
            return cipherSuites;
        }
        if (defaultCipherSuites != null && defaultCipherSuites.length != 0 && (filters == null || !filters.isSetInclude() && !filters.isSetExclude())) {
            LogUtils.log(log, Level.FINE, "CIPHERSUITES_SET", Arrays.toString(defaultCipherSuites));
            return defaultCipherSuites;
        }
        LogUtils.log(log, Level.FINE, "CIPHERSUITES_NOT_SET");
        return SSLUtils.getFilteredCiphersuites(filters, supportedCipherSuites, log, false);
    }

    public static String[] getFilteredCiphersuites(FiltersType filters, String[] supportedCipherSuites, Logger log, boolean exclude) {
        ArrayList<Pattern> includes = new ArrayList<Pattern>();
        ArrayList<Pattern> excludes = new ArrayList<Pattern>();
        if (filters != null) {
            SSLUtils.compileRegexPatterns(includes, filters.getInclude(), true, log);
            if (filters.isSetExclude()) {
                SSLUtils.compileRegexPatterns(excludes, filters.getExclude(), false, log);
            } else {
                List<String> filteredExcludes = SSLUtils.filterDefaultExcludes(filters.getInclude(), DEFAULT_CIPHERSUITE_FILTERS_EXCLUDE);
                SSLUtils.compileRegexPatterns(excludes, filteredExcludes, false, log);
            }
        }
        ArrayList<String> filteredCipherSuites = new ArrayList<String>();
        for (String supportedCipherSuite : supportedCipherSuites) {
            if (SSLUtils.matchesOneOf(supportedCipherSuite, includes) && !SSLUtils.matchesOneOf(supportedCipherSuite, excludes)) {
                LogUtils.log(log, Level.FINE, "CIPHERSUITE_INCLUDED", supportedCipherSuite);
                if (exclude) continue;
                filteredCipherSuites.add(supportedCipherSuite);
                continue;
            }
            LogUtils.log(log, Level.FINE, "CIPHERSUITE_EXCLUDED", supportedCipherSuite);
            if (!exclude) continue;
            filteredCipherSuites.add(supportedCipherSuite);
        }
        return SSLUtils.getCiphersFromList(filteredCipherSuites, log, exclude);
    }

    private static List<String> filterDefaultExcludes(List<String> includes, List<String> defaultExcludes) {
        if (includes != null && !includes.isEmpty()) {
            return defaultExcludes.stream().filter(ex -> !includes.stream().anyMatch(inc -> inc.matches((String)ex))).collect(Collectors.toList());
        }
        return defaultExcludes;
    }

    private static String[] getSystemCiphersuites(Logger log) {
        String jvmCipherSuites = System.getProperty(HTTPS_CIPHER_SUITES);
        if (jvmCipherSuites != null && !jvmCipherSuites.isEmpty()) {
            LogUtils.log(log, Level.FINE, "CIPHERSUITES_SYSTEM_PROPERTY_SET", jvmCipherSuites);
            return jvmCipherSuites.split(",");
        }
        return null;
    }

    private static void compileRegexPatterns(List<Pattern> patterns, List<String> regexes, boolean include, Logger log) {
        if (regexes != null) {
            String msg = include ? "CIPHERSUITE_INCLUDE_FILTER" : "CIPHERSUITE_EXCLUDE_FILTER";
            for (String s2 : regexes) {
                LogUtils.log(log, Level.FINE, msg, s2);
                patterns.add(Pattern.compile(s2));
            }
        }
    }

    private static boolean matchesOneOf(String s2, List<Pattern> patterns) {
        boolean matches = false;
        if (patterns != null) {
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(s2);
                if (!matcher.matches()) continue;
                matches = true;
                break;
            }
        }
        return matches;
    }

    private static String[] getCiphersFromList(List<String> cipherSuitesList, Logger log, boolean exclude) {
        CharSequence[] cipherSuites = cipherSuitesList.toArray(new String[0]);
        if (log.isLoggable(Level.FINE)) {
            LogUtils.log(log, Level.FINE, exclude ? "CIPHERSUITES_EXCLUDED" : "CIPHERSUITES_SET", String.join((CharSequence)", ", cipherSuites));
        }
        return cipherSuites;
    }

    public static String getTruststore(String trustStoreLocation, Logger log) {
        String logMsg = trustStoreLocation != null ? "TRUST_STORE_SET" : ((trustStoreLocation = SystemPropertyAction.getProperty("javax.net.ssl.trustStore")) != null ? "TRUST_STORE_SYSTEM_PROPERTY_SET" : "TRUST_STORE_NOT_SET");
        LogUtils.log(log, Level.FINE, logMsg, trustStoreLocation);
        return trustStoreLocation;
    }

    public static String getTrustStoreType(String trustStoreType, Logger log) {
        return SSLUtils.getTrustStoreType(trustStoreType, log, "JKS");
    }

    public static String getTrustStoreType(String trustStoreType, Logger log, String def) {
        String logMsg;
        if (trustStoreType != null) {
            logMsg = "TRUST_STORE_TYPE_SET";
        } else {
            trustStoreType = SystemPropertyAction.getProperty("javax.net.ssl.trustStoreType");
            if (trustStoreType == null) {
                trustStoreType = def;
                logMsg = "TRUST_STORE_TYPE_NOT_SET";
            } else {
                logMsg = "TRUST_STORE_TYPE_SYSTEM_SET";
            }
        }
        LogUtils.log(log, Level.FINE, logMsg, trustStoreType);
        return trustStoreType;
    }

    public static String getTruststorePassword(String trustStorePassword, Logger log) {
        String logMsg = trustStorePassword != null ? "TRUST_STORE_PASSWORD_SET" : ((trustStorePassword = SystemPropertyAction.getProperty("javax.net.ssl.trustStorePassword")) != null ? "TRUST_STORE_PASSWORD_SYSTEM_PROPERTY_SET" : "TRUST_STORE_PASSWORD_NOT_SET");
        LogUtils.log(log, Level.FINE, logMsg);
        return trustStorePassword;
    }

    public static String getTruststoreProvider(String trustStoreProvider, Logger log) {
        String logMsg = trustStoreProvider != null ? "TRUST_STORE_PROVIDER_SET" : ((trustStoreProvider = SystemPropertyAction.getProperty("javax.net.ssl.trustStoreProvider", null)) == null ? "TRUST_STORE_PROVIDER_NOT_SET" : "TRUST_STORE_PROVIDER_SYSTEM_SET");
        LogUtils.log(log, Level.FINE, logMsg, trustStoreProvider);
        return trustStoreProvider;
    }
}

