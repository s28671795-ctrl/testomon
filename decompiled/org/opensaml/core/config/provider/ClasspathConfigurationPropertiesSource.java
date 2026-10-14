/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config.provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.Nonnull;
import org.opensaml.core.config.ConfigurationPropertiesSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathConfigurationPropertiesSource
implements ConfigurationPropertiesSource {
    @Nonnull
    private static final String RESOURCE_NAME = "opensaml-config.properties";
    @Nonnull
    private Logger log = LoggerFactory.getLogger(ClasspathConfigurationPropertiesSource.class);
    private Properties cachedProperties;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Properties getProperties() {
        ClasspathConfigurationPropertiesSource classpathConfigurationPropertiesSource = this;
        synchronized (classpathConfigurationPropertiesSource) {
            if (this.cachedProperties == null) {
                try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_NAME);){
                    if (is != null) {
                        Properties props = new Properties();
                        props.load(is);
                        this.cachedProperties = props;
                    }
                }
                catch (IOException e) {
                    this.log.warn("Problem attempting to load configuration properties 'opensaml-config.properties' from classpath", e);
                }
            }
            return this.cachedProperties;
        }
    }
}

