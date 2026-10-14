/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.classic.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ConfiguratorRank;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.Loader;
import ch.qos.logback.core.util.OptionHelper;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@ConfiguratorRank(value=0)
public class DefaultJoranConfigurator
extends ContextAwareBase
implements Configurator {
    @Override
    public Configurator.ExecutionStatus configure(LoggerContext context) {
        URL url2 = this.performMultiStepConfigurationFileSearch(true);
        if (url2 != null) {
            try {
                this.configureByResource(url2);
            }
            catch (JoranException e) {
                e.printStackTrace();
            }
            return Configurator.ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
        }
        return Configurator.ExecutionStatus.INVOKE_NEXT_IF_ANY;
    }

    private URL performMultiStepConfigurationFileSearch(boolean updateStatus) {
        ClassLoader myClassLoader = Loader.getClassLoaderOfObject(this);
        URL url2 = this.findConfigFileURLFromSystemProperties(myClassLoader, updateStatus);
        if (url2 != null) {
            return url2;
        }
        url2 = this.getResource("logback-test.xml", myClassLoader, updateStatus);
        if (url2 != null) {
            return url2;
        }
        return this.getResource("logback.xml", myClassLoader, updateStatus);
    }

    public void configureByResource(URL url2) throws JoranException {
        if (url2 == null) {
            throw new IllegalArgumentException("URL argument cannot be null");
        }
        String urlString = url2.toString();
        if (!urlString.endsWith("xml")) {
            throw new LogbackException("Unexpected filename extension of file [" + url2.toString() + "]. Should be .xml");
        }
        JoranConfigurator configurator2 = new JoranConfigurator();
        configurator2.setContext(this.context);
        configurator2.doConfigure(url2);
    }

    public URL findURLOfDefaultConfigurationFile(boolean updateStatus) {
        return this.performMultiStepConfigurationFileSearch(updateStatus);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private URL findConfigFileURLFromSystemProperties(ClassLoader classLoader, boolean updateStatus) {
        String logbackConfigFile = OptionHelper.getSystemProperty("logback.configurationFile");
        if (logbackConfigFile != null) {
            URL result = null;
            try {
                URL uRL = result = new URL(logbackConfigFile);
                return uRL;
            }
            catch (MalformedURLException e) {
                result = Loader.getResource(logbackConfigFile, classLoader);
                if (result != null) {
                    URL uRL = result;
                    return uRL;
                }
                File f = new File(logbackConfigFile);
                if (f.exists() && f.isFile()) {
                    try {
                        URL uRL = result = f.toURI().toURL();
                        return uRL;
                    }
                    catch (MalformedURLException malformedURLException) {}
                }
            }
            finally {
                if (updateStatus) {
                    this.statusOnResourceSearch(logbackConfigFile, classLoader, result);
                }
            }
        }
        return null;
    }

    private URL getResource(String filename, ClassLoader myClassLoader, boolean updateStatus) {
        URL url2 = Loader.getResource(filename, myClassLoader);
        if (updateStatus) {
            this.statusOnResourceSearch(filename, myClassLoader, url2);
        }
        return url2;
    }

    private void statusOnResourceSearch(String resourceName, ClassLoader classLoader, URL url2) {
        StatusManager sm = this.context.getStatusManager();
        if (url2 == null) {
            sm.add(new InfoStatus("Could NOT find resource [" + resourceName + "]", this.context));
        } else {
            sm.add(new InfoStatus("Found resource [" + resourceName + "] at [" + url2.toString() + "]", this.context));
            this.multiplicityWarning(resourceName, classLoader);
        }
    }

    private void multiplicityWarning(String resourceName, ClassLoader classLoader) {
        Set<URL> urlSet = null;
        try {
            urlSet = Loader.getResources(resourceName, classLoader);
        }
        catch (IOException e) {
            this.addError("Failed to get url list for resource [" + resourceName + "]", e);
        }
        if (urlSet != null && urlSet.size() > 1) {
            this.addWarn("Resource [" + resourceName + "] occurs multiple times on the classpath.");
            for (URL url2 : urlSet) {
                this.addWarn("Resource [" + resourceName + "] occurs at [" + url2.toString() + "]");
            }
        }
    }
}

