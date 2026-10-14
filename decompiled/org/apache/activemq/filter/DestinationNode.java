/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.Collection;
import java.util.Set;

public interface DestinationNode {
    public void appendMatchingValues(Set<DestinationNode> var1, String[] var2, int var3);

    public void appendMatchingWildcards(Set<DestinationNode> var1, String[] var2, int var3);

    public void appendDescendantValues(Set<DestinationNode> var1);

    public Collection<DestinationNode> getDesendentValues();

    public DestinationNode getChild(String var1);

    public Collection<DestinationNode> getValues();

    public Collection<DestinationNode> getChildren();

    public Collection<DestinationNode> removeDesendentValues();

    public Collection<DestinationNode> removeValues();
}

