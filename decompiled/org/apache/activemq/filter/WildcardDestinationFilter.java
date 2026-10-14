/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.filter.DestinationPath;

public class WildcardDestinationFilter
extends DestinationFilter {
    private String[] prefixes;
    private byte destinationType;

    public WildcardDestinationFilter(String[] prefixes, byte destinationType) {
        this.prefixes = new String[prefixes.length];
        for (int i = 0; i < prefixes.length; ++i) {
            String prefix = prefixes[i];
            if (prefix.equals("*")) continue;
            this.prefixes[i] = prefix;
        }
        this.destinationType = destinationType;
    }

    @Override
    public boolean matches(ActiveMQDestination destination) {
        int length;
        if (destination.getDestinationType() != this.destinationType) {
            return false;
        }
        String[] path = DestinationPath.getDestinationPaths(destination);
        if (path.length == (length = this.prefixes.length)) {
            for (int i = 0; i < length; ++i) {
                String prefix = this.prefixes[i];
                if (prefix == null || prefix.equals(path[i])) continue;
                return false;
            }
            return true;
        }
        return false;
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

