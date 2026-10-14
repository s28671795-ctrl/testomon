/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.filter;

import java.util.Map;
import org.simpleframework.xml.filter.Filter;

public class MapFilter
implements Filter {
    private Filter filter;
    private Map map;

    public MapFilter(Map map) {
        this(map, null);
    }

    public MapFilter(Map map, Filter filter2) {
        this.filter = filter2;
        this.map = map;
    }

    @Override
    public String replace(String text) {
        Object value = null;
        if (this.map != null) {
            value = this.map.get(text);
        }
        if (value != null) {
            return value.toString();
        }
        if (this.filter != null) {
            return this.filter.replace(text);
        }
        return null;
    }
}

