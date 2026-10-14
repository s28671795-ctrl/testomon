/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.config;

import java.util.Collection;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.config.ConfigError;

public class InvalidConfigurationException
extends CacheException {
    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(Collection<ConfigError> errors2) {
        this(null, errors2);
    }

    public InvalidConfigurationException(String rootCause, Collection<ConfigError> errors2) {
        super(InvalidConfigurationException.createErrorMessage(rootCause, errors2));
    }

    private static String createErrorMessage(String rootCause, Collection<ConfigError> errors2) {
        StringBuilder sb = new StringBuilder();
        if (rootCause == null) {
            sb.append("There ");
            if (errors2.size() == 1) {
                sb.append("is one error ");
            } else {
                sb.append("are ").append(errors2.size()).append(" errors ");
            }
            sb.append("in your configuration: \n");
        } else {
            sb.append(rootCause).append('\n');
        }
        for (ConfigError error : errors2) {
            sb.append("\t* ").append(error.toString()).append('\n');
        }
        return sb.append("\n").toString();
    }
}

