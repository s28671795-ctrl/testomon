/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.props.SimplePropertyInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyValueParser {
    protected static final Pattern CONFIG_PATTERN = Pattern.compile("([^\\{]+)\\s*\\{(.*)\\}\\s*");
    protected static final Pattern PARAMS_ONLY_CONFIG_PATTERN = Pattern.compile("\\s*\\{\\s*(.*)\\s*\\}\\s*");
    protected static final Pattern PROPERTY_PATTERN = Pattern.compile("([^\\}\\{])+");
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String className;
    private final Map<String, String> properties = new HashMap<String, String>();

    protected PropertyValueParser() {
    }

    public PropertyValueParser(String config) {
        Matcher matcher = CONFIG_PATTERN.matcher(config);
        if (matcher.matches()) {
            this.initialize(matcher.group(1).trim(), matcher.group(2).trim());
        }
    }

    public PropertyValueParser(String config, String clazz) {
        Matcher matcher = PARAMS_ONLY_CONFIG_PATTERN.matcher(config);
        if (matcher.matches()) {
            this.initialize(clazz, matcher.group(1).trim());
        }
    }

    protected void initialize(String clazz, String props) {
        this.setClassName(clazz);
        if (!"".equals(props)) {
            this.initializeProperties(PROPERTY_PATTERN.matcher(props));
        }
    }

    protected void initializeProperties(Matcher matcher) {
        while (matcher.find()) {
            String input = matcher.group().trim();
            if ("".equals(input)) continue;
            String[] s2 = input.split("=", 2);
            if (s2.length < 2) {
                throw new IllegalArgumentException("Invalid property syntax: " + input);
            }
            this.properties.put(s2[0].trim(), s2[1].trim());
        }
    }

    public String getClassName() {
        return this.className;
    }

    protected void setClassName(String name) {
        this.className = name;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public static boolean isConfig(String config) {
        return CONFIG_PATTERN.matcher(config).matches();
    }

    public static boolean isParamsOnlyConfig(String config) {
        return PARAMS_ONLY_CONFIG_PATTERN.matcher(config).matches();
    }

    public Object initializeType() {
        Class<?> c = SimplePropertyInvoker.createClass(this.getClassName());
        Class<?> o = SimplePropertyInvoker.instantiateType(c, this.getClassName());
        this.setProperties(c, o);
        return o;
    }

    protected void setProperties(Class<?> c, Object o) {
        SimplePropertyInvoker invoker = new SimplePropertyInvoker(c);
        for (Map.Entry<String, String> entry : this.getProperties().entrySet()) {
            invoker.setProperty(o, entry.getKey(), entry.getValue());
        }
        if (invoker.getProperties().contains("initialize")) {
            try {
                invoker.setProperty(o, "initialize", null);
            }
            catch (Throwable t2) {
                this.logger.debug("Error invoking initialize method", t2);
            }
        }
    }
}

