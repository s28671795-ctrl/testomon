/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.QuotedCSV;
import org.eclipse.jetty.util.Index;
import org.eclipse.jetty.util.StringUtil;

public class ExtensionConfig {
    private static final Index<ExtensionConfig> CACHE = new Index.Builder().caseSensitive(false).with("identity", new ExtensionConfig("identity")).with("permessage-deflate", new ExtensionConfig("permessage-deflate")).with("permessage-deflate; client_max_window_bits", new ExtensionConfig("permessage-deflate; client_max_window_bits")).build();
    private final String name;
    private final Map<String, String> parameters;

    public static ExtensionConfig parse(String parameterizedName) {
        ExtensionConfig config = CACHE.get(parameterizedName);
        if (config != null) {
            return config;
        }
        return new ExtensionConfig(parameterizedName);
    }

    public static List<ExtensionConfig> parseEnum(Enumeration<String> valuesEnum) {
        ArrayList<ExtensionConfig> configs = new ArrayList<ExtensionConfig>();
        if (valuesEnum != null) {
            while (valuesEnum.hasMoreElements()) {
                QuotedCSV csv = new QuotedCSV(valuesEnum.nextElement());
                for (String extToken : csv) {
                    configs.add(ExtensionConfig.parse(extToken));
                }
            }
        }
        return configs;
    }

    public static List<ExtensionConfig> parseList(String ... rawSecWebSocketExtensions) {
        ArrayList<ExtensionConfig> configs = new ArrayList<ExtensionConfig>();
        for (String rawValue : rawSecWebSocketExtensions) {
            QuotedCSV csv = new QuotedCSV(rawValue);
            for (String extToken : csv) {
                configs.add(ExtensionConfig.parse(extToken));
            }
        }
        return configs;
    }

    public static String toHeaderValue(List<ExtensionConfig> configs) {
        if (configs == null || configs.isEmpty()) {
            return null;
        }
        StringBuilder parameters = new StringBuilder();
        boolean needsDelim = false;
        for (ExtensionConfig ext : configs) {
            if (needsDelim) {
                parameters.append(", ");
            }
            parameters.append(ext.getParameterizedName());
            needsDelim = true;
        }
        return parameters.toString();
    }

    public ExtensionConfig(ExtensionConfig copy) {
        this.name = copy.name;
        this.parameters = new HashMap<String, String>();
        this.parameters.putAll(copy.parameters);
    }

    public ExtensionConfig(String name, Map<String, String> parameters) {
        this.name = name;
        this.parameters = new HashMap<String, String>();
        this.parameters.putAll(parameters);
    }

    public ExtensionConfig(String parameterizedName) {
        ParamParser paramParser = new ParamParser(parameterizedName);
        paramParser.parse();
        this.name = paramParser.getName();
        this.parameters = paramParser.getParams();
    }

    public boolean isInternalExtension() {
        return this.name.startsWith("@");
    }

    public List<Map.Entry<String, String>> getInternalParameters() {
        return this.parameters.entrySet().stream().filter(entry -> ((String)entry.getKey()).startsWith("@")).collect(Collectors.toList());
    }

    public void removeInternalParameters() {
        this.parameters.entrySet().removeIf(entry -> ((String)entry.getKey()).startsWith("@"));
    }

    public String getName() {
        return this.name;
    }

    public final int getParameter(String key, int defValue) {
        String val = this.parameters.get(key);
        if (val == null) {
            return defValue;
        }
        return Integer.parseInt(val);
    }

    public final String getParameter(String key, String defValue) {
        String val = this.parameters.get(key);
        if (val == null) {
            return defValue;
        }
        return val;
    }

    public final String getParameterizedName() {
        StringBuilder str = new StringBuilder();
        str.append(this.name);
        for (String param2 : this.parameters.keySet()) {
            str.append(';');
            str.append(param2);
            String value = this.parameters.get(param2);
            if (value == null) continue;
            str.append('=');
            ExtensionConfig.quoteIfNeeded(str, value);
        }
        return str.toString();
    }

    public final String getParameterizedNameWithoutInternalParams() {
        StringBuilder str = new StringBuilder();
        str.append(this.name);
        for (String param2 : this.parameters.keySet()) {
            if (param2.startsWith("@")) continue;
            str.append(';');
            str.append(param2);
            String value = this.parameters.get(param2);
            if (value == null) continue;
            str.append('=');
            ExtensionConfig.quoteIfNeeded(str, value);
        }
        return str.toString();
    }

    public static void quoteIfNeeded(StringBuilder buf, String str) {
        if (str == null) {
            return;
        }
        int len = str.length();
        if (len == 0) {
            return;
        }
        for (int i = 0; i < len; ++i) {
            int ch = str.codePointAt(i);
            if (ch != 59 && ch != 61) continue;
            buf.append('\"').append(str).append('\"');
            return;
        }
        buf.append(str);
    }

    public final Set<String> getParameterKeys() {
        return Collections.unmodifiableSet(this.parameters.keySet());
    }

    public final Map<String, String> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }

    public final void setParameter(String key) {
        this.parameters.put(key, null);
    }

    public final void setParameter(String key, int value) {
        this.parameters.put(key, Integer.toString(value));
    }

    public final void setParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public String toString() {
        return this.getParameterizedName();
    }

    private static class ParamParser
    extends QuotedCSV {
        private final String parameterizedName;
        private String name;
        private final Map<String, String> params = new HashMap<String, String>();

        public ParamParser(String parameterizedName) {
            super(false, new String[0]);
            this.parameterizedName = parameterizedName;
        }

        public String getName() {
            return this.name;
        }

        public Map<String, String> getParams() {
            return this.params;
        }

        @Override
        protected void parsedParam(StringBuffer buffer, int valueLength, int paramNameIdx, int paramValueIdx) {
            String paramName = "";
            String paramValue = null;
            if (paramValueIdx > 0) {
                paramName = buffer.substring(paramNameIdx, paramValueIdx - 1);
                paramValue = buffer.substring(paramValueIdx);
            } else if (paramNameIdx > 0) {
                paramName = buffer.substring(paramNameIdx);
            }
            this.params.put(paramName, paramValue);
            super.parsedParam(buffer, valueLength, paramNameIdx, paramValueIdx);
        }

        @Override
        protected void parsedValue(StringBuffer buffer) {
            String extName = buffer.toString();
            if (this.name != null) {
                throw new IllegalArgumentException("parameterizedName contains multiple ExtensionConfigs: " + this.parameterizedName);
            }
            this.name = extName;
            super.parsedValue(buffer);
        }

        public void parse() {
            this.addValue(this.parameterizedName);
            if (StringUtil.isEmpty(this.name)) {
                throw new IllegalArgumentException("parameterizedName contains no ExtensionConfigs: " + this.parameterizedName);
            }
        }
    }
}

