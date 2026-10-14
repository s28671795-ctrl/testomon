/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.control.RequestControl;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterParseException;
import org.ldaptive.filter.FilterParser;
import org.ldaptive.props.AbstractPropertyInvoker;

public class SearchRequestPropertyInvoker
extends AbstractPropertyInvoker {
    public SearchRequestPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            if (Filter.class.isAssignableFrom(type)) {
                try {
                    newValue = FilterParser.parse(value);
                }
                catch (FilterParseException e) {
                    throw new IllegalArgumentException("Could not parse filter string '" + value + "'", e);
                }
            } else {
                newValue = RequestControl[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(RequestControl.class, value) : this.convertSimpleType(type, value);
            }
        }
        return newValue;
    }
}

