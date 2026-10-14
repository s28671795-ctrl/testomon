/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.filter;

import org.simpleframework.xml.filter.Filter;

public class SystemFilter
implements Filter {
    private Filter filter;

    public SystemFilter() {
        this(null);
    }

    public SystemFilter(Filter filter2) {
        this.filter = filter2;
    }

    @Override
    public String replace(String text) {
        String value = System.getProperty(text);
        if (value != null) {
            return value;
        }
        if (this.filter != null) {
            return this.filter.replace(text);
        }
        return null;
    }
}

