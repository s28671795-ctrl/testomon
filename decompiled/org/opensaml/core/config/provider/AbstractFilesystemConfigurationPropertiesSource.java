/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.config.ConfigurationPropertiesSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFilesystemConfigurationPropertiesSource
implements ConfigurationPropertiesSource {
    private Properties cachedProperties;
    private Logger log = LoggerFactory.getLogger(AbstractFilesystemConfigurationPropertiesSource.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Properties getProperties() {
        String fileName = StringSupport.trimOrNull(this.getFilename());
        if (fileName == null) {
            this.log.warn("No filename was supplied, unable to load properties");
            return null;
        }
        AbstractFilesystemConfigurationPropertiesSource abstractFilesystemConfigurationPropertiesSource = this;
        synchronized (abstractFilesystemConfigurationPropertiesSource) {
            File file;
            if (this.cachedProperties == null && (file = new File(fileName)).exists()) {
                try (FileInputStream is = new FileInputStream(fileName);){
                    Properties props = new Properties();
                    props.load(is);
                    this.cachedProperties = props;
                }
                catch (FileNotFoundException e) {
                    this.log.warn("File not found attempting to load configuration properties '" + fileName + "' from filesystem");
                }
                catch (IOException e) {
                    this.log.warn("I/O problem attempting to load configuration properties '" + fileName + "' from filesystem", e);
                }
            }
            return this.cachedProperties;
        }
    }

    protected abstract String getFilename();
}

