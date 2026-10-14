/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.activemq.util.IntrospectionSupport;

public class AutoTransportUtils {
    public static String ALL = "all";
    public static String OPENWIRE = "default";
    public static String STOMP = "stomp";
    public static String AMQP = "amqp";
    public static String MQTT = "mqtt";
    public static String AUTO = "auto";

    public static Map<String, Map<String, Object>> extractWireFormatOptions(Map<String, String> options) {
        HashMap<String, Map<String, Object>> wireFormatOptions = new HashMap<String, Map<String, Object>>();
        if (options != null) {
            wireFormatOptions.put(OPENWIRE, IntrospectionSupport.extractProperties(options, "wireFormat.default."));
            wireFormatOptions.put(STOMP, IntrospectionSupport.extractProperties(options, "wireFormat.stomp."));
            wireFormatOptions.put(AMQP, IntrospectionSupport.extractProperties(options, "wireFormat.amqp."));
            wireFormatOptions.put(MQTT, IntrospectionSupport.extractProperties(options, "wireFormat.mqtt."));
            wireFormatOptions.put(ALL, IntrospectionSupport.extractProperties(options, "wireFormat."));
        }
        return wireFormatOptions;
    }

    public static Set<String> parseProtocols(String protocolString) {
        HashSet<String> protocolSet = new HashSet<String>();
        if (protocolString != null && !protocolString.isEmpty()) {
            protocolSet.addAll(Arrays.asList(protocolString.split(",")));
        }
        return protocolSet;
    }
}

