/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.ConnectionFactory;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.LdapEntryHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.SearchResultHandler;
import org.ldaptive.props.AbstractPropertyInvoker;

public class SearchOperationFactoryPropertyInvoker
extends AbstractPropertyInvoker {
    public SearchOperationFactoryPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = ConnectionFactory.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(ConnectionFactory.class, value) : (ExceptionHandler.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(ExceptionHandler.class, value) : (LdapEntryHandler[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(LdapEntryHandler.class, value) : (ResultHandler[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(ResultHandler.class, value) : (SearchResultHandler[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(SearchResultHandler.class, value) : this.convertSimpleType(type, value)))));
        }
        return newValue;
    }
}

