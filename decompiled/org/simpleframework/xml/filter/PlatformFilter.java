/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.filter;

import java.util.Map;
import org.simpleframework.xml.filter.EnvironmentFilter;
import org.simpleframework.xml.filter.MapFilter;
import org.simpleframework.xml.filter.StackFilter;
import org.simpleframework.xml.filter.SystemFilter;

public class PlatformFilter
extends StackFilter {
    public PlatformFilter() {
        this(null);
    }

    public PlatformFilter(Map map) {
        this.push(new EnvironmentFilter());
        this.push(new SystemFilter());
        this.push(new MapFilter(map));
    }
}

