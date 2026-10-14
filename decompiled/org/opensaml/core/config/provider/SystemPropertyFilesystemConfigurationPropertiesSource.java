/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config.provider;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.config.provider.AbstractFilesystemConfigurationPropertiesSource;

public class SystemPropertyFilesystemConfigurationPropertiesSource
extends AbstractFilesystemConfigurationPropertiesSource {
    @Nonnull
    @NotEmpty
    public static final String PROPERTY_FILE_NAME = "opensaml.config.fileName";

    @Override
    protected String getFilename() {
        return System.getProperty(PROPERTY_FILE_NAME);
    }
}

