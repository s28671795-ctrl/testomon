/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.internal.ServerTraceEvent;

public final class TracingUtils {
    private static final List<String> SUMMARY_HEADERS = new ArrayList<String>();
    private static final TracingConfig DEFAULT_CONFIGURATION_TYPE;

    private TracingUtils() {
    }

    public static void initTracingSupport(TracingConfig type, TracingLogger.Level appThreshold, ContainerRequest containerRequest) {
        TracingLogger tracingLogger = TracingUtils.isTracingSupportEnabled(type, containerRequest) ? TracingLogger.create(TracingUtils.getTracingThreshold(appThreshold, containerRequest), TracingUtils.getTracingLoggerNameSuffix(containerRequest)) : TracingLogger.empty();
        containerRequest.setProperty(TracingLogger.PROPERTY_NAME, tracingLogger);
    }

    public static void logStart(ContainerRequest request) {
        StringBuilder textSB;
        TracingLogger tracingLogger = TracingLogger.getInstance(request);
        if (tracingLogger.isLogEnabled(ServerTraceEvent.START)) {
            textSB = new StringBuilder();
            textSB.append(String.format("baseUri=[%s] requestUri=[%s] method=[%s] authScheme=[%s]", request.getBaseUri(), request.getRequestUri(), request.getMethod(), TracingUtils.toStringOrNA(request.getSecurityContext().getAuthenticationScheme())));
            for (String header : SUMMARY_HEADERS) {
                textSB.append(String.format(" %s=%s", header, TracingUtils.toStringOrNA(request.getRequestHeaders().get(header))));
            }
            tracingLogger.log(ServerTraceEvent.START, textSB.toString());
        }
        if (tracingLogger.isLogEnabled(ServerTraceEvent.START_HEADERS)) {
            textSB = new StringBuilder();
            for (String header : request.getRequestHeaders().keySet()) {
                if (SUMMARY_HEADERS.contains(header)) continue;
                textSB.append(String.format(" %s=%s", header, TracingUtils.toStringOrNA(request.getRequestHeaders().get(header))));
            }
            if (textSB.length() > 0) {
                textSB.insert(0, "Other request headers:");
            }
            tracingLogger.log(ServerTraceEvent.START_HEADERS, textSB.toString());
        }
    }

    private static boolean isTracingSupportEnabled(TracingConfig type, ContainerRequest containerRequest) {
        return type == TracingConfig.ALL || type == TracingConfig.ON_DEMAND && containerRequest.getHeaderString("X-Jersey-Tracing-Accept") != null;
    }

    static TracingConfig getTracingConfig(Configuration configuration) {
        String tracingText = (String)((Object)ServerProperties.getValue(configuration.getProperties(), "jersey.config.server.tracing.type", String.class));
        TracingConfig result = tracingText != null ? TracingConfig.valueOf(tracingText) : DEFAULT_CONFIGURATION_TYPE;
        return result;
    }

    private static String getTracingLoggerNameSuffix(ContainerRequest request) {
        return request.getHeaderString("X-Jersey-Tracing-Logger");
    }

    static TracingLogger.Level getTracingThreshold(Configuration configuration) {
        String thresholdText = (String)((Object)ServerProperties.getValue(configuration.getProperties(), "jersey.config.server.tracing.threshold", String.class));
        return thresholdText == null ? TracingLogger.DEFAULT_LEVEL : TracingLogger.Level.valueOf(thresholdText);
    }

    private static TracingLogger.Level getTracingThreshold(TracingLogger.Level appThreshold, ContainerRequest containerRequest) {
        String thresholdText = containerRequest.getHeaderString("X-Jersey-Tracing-Threshold");
        return thresholdText == null ? appThreshold : TracingLogger.Level.valueOf(thresholdText);
    }

    private static String toStringOrNA(Object object) {
        if (object == null) {
            return "n/a";
        }
        return String.valueOf(object);
    }

    static {
        SUMMARY_HEADERS.add("Accept".toLowerCase(Locale.ROOT));
        SUMMARY_HEADERS.add("Accept-Encoding".toLowerCase(Locale.ROOT));
        SUMMARY_HEADERS.add("Accept-Charset".toLowerCase(Locale.ROOT));
        SUMMARY_HEADERS.add("Accept-Language".toLowerCase(Locale.ROOT));
        SUMMARY_HEADERS.add("Content-Type".toLowerCase(Locale.ROOT));
        SUMMARY_HEADERS.add("Content-Length".toLowerCase(Locale.ROOT));
        DEFAULT_CONFIGURATION_TYPE = TracingConfig.OFF;
    }
}

