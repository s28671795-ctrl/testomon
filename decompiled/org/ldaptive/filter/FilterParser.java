/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.lang.reflect.Constructor;
import org.ldaptive.LdapUtils;
import org.ldaptive.filter.DefaultFilterFunction;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterFunction;
import org.ldaptive.filter.FilterParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FilterParser {
    private static final String FILTER_FUNCTION_PROPERTY = "org.ldaptive.filter.function";
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterParser.class);
    private static final FilterFunction FILTER_FUNCTION = FilterParser.getFilterFunction();
    private static final Constructor<?> FILTER_FUNCTION_CONSTRUCTOR = LdapUtils.createConstructorFromProperty("org.ldaptive.filter.function");

    private FilterParser() {
    }

    public static FilterFunction getFilterFunction() {
        if (FILTER_FUNCTION_CONSTRUCTOR != null) {
            try {
                return (FilterFunction)FILTER_FUNCTION_CONSTRUCTOR.newInstance(new Object[0]);
            }
            catch (Exception e) {
                LOGGER.error("Error creating new filter function instance with {}", (Object)FILTER_FUNCTION_CONSTRUCTOR, (Object)e);
                throw new IllegalStateException(e);
            }
        }
        return new DefaultFilterFunction();
    }

    public static Filter parse(String filter2) throws FilterParseException {
        return FILTER_FUNCTION.parse(filter2);
    }

    static {
        if (FILTER_FUNCTION_CONSTRUCTOR != null) {
            LOGGER.info("Setting ldap filter function to {}", (Object)FILTER_FUNCTION_CONSTRUCTOR);
        }
    }
}

