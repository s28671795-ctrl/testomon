/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDrivenMetricFilter
implements MetricFilter {
    @Nonnull
    @NotEmpty
    private final String loggerPrefix;
    @Nonnull
    @NonnullElements
    private final Map<String, Level> levelMap;

    public LoggerDrivenMetricFilter(@Nonnull @ParameterName(name="prefix") @NotEmpty String prefix) {
        this(prefix, Collections.emptyMap());
    }

    public LoggerDrivenMetricFilter(@Nonnull @ParameterName(name="prefix") @NotEmpty String prefix, @Nullable @ParameterName(name="map") @NonnullElements Map<String, Level> map) {
        this.loggerPrefix = Constraint.isNotNull(StringSupport.trimOrNull(prefix), "Prefix cannot be null or empty.");
        if (map == null || map.isEmpty()) {
            this.levelMap = Collections.emptyMap();
        } else {
            this.levelMap = new HashMap<String, Level>(map.size());
            for (Map.Entry<String, Level> entry : map.entrySet()) {
                String trimmed = StringSupport.trimOrNull(entry.getKey());
                if (trimmed == null || entry.getValue() == null) continue;
                this.levelMap.put(trimmed, entry.getValue());
            }
        }
    }

    @Override
    public boolean matches(String name, Metric metric) {
        Logger logger2 = LoggerFactory.getLogger(this.loggerPrefix + name);
        Level level = this.levelMap.get(logger2.getName());
        if (level == null) {
            return logger2.isInfoEnabled();
        }
        switch (level) {
            case TRACE: {
                return logger2.isTraceEnabled();
            }
            case DEBUG: {
                return logger2.isDebugEnabled();
            }
            case INFO: {
                return logger2.isInfoEnabled();
            }
            case WARN: {
                return logger2.isWarnEnabled();
            }
            case ERROR: {
                return logger2.isErrorEnabled();
            }
        }
        return logger2.isInfoEnabled();
    }

    public static enum Level {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR;

    }
}

