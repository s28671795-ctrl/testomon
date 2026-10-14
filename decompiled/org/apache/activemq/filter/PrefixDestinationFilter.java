/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.filter.DestinationPath;

public class PrefixDestinationFilter
extends DestinationFilter {
    private String[] prefixes;
    private byte destinationType;

    public PrefixDestinationFilter(String[] prefixes, byte destinationType) {
        int lastIndex;
        for (lastIndex = prefixes.length - 1; lastIndex >= 0 && ">".equals(prefixes[lastIndex]); --lastIndex) {
        }
        this.prefixes = new String[lastIndex + 2];
        System.arraycopy(prefixes, 0, this.prefixes, 0, this.prefixes.length);
        this.destinationType = destinationType;
    }

    @Override
    public boolean matches(ActiveMQDestination destination) {
        int length;
        if (destination.getDestinationType() != this.destinationType) {
            return false;
        }
        String[] path = DestinationPath.getDestinationPaths(destination.getPhysicalName());
        if (path.length >= (length = this.prefixes.length)) {
            int size = length - 1;
            for (int i = 0; i < size; ++i) {
                if (this.matches(this.prefixes[i], path[i])) continue;
                return false;
            }
            return true;
        }
        boolean match = true;
        for (int i = 0; i < path.length && match; ++i) {
            match = this.matches(this.prefixes[i], path[i]);
        }
        return match && this.prefixes.length == path.length + 1;
    }

    private boolean matches(String prefix, String path) {
        return path.equals("*") || prefix.equals("*") || prefix.equals(path);
    }

    public String getText() {
        return DestinationPath.toString(this.prefixes);
    }

    public String toString() {
        return super.toString() + "[destination: " + this.getText() + "]";
    }

    @Override
    public boolean isWildcard() {
        return true;
    }
}

