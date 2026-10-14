/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.List;
import org.codehaus.plexus.interpolation.fixed.AbstractDelegatingValueSource;
import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.ObjectBasedValueSource;
import org.codehaus.plexus.interpolation.fixed.PrefixedValueSourceWrapper;

public class PrefixedObjectValueSource
extends AbstractDelegatingValueSource {
    public PrefixedObjectValueSource(String prefix, Object root) {
        super(new PrefixedValueSourceWrapper((FixedValueSource)new ObjectBasedValueSource(root), prefix));
    }

    public PrefixedObjectValueSource(List<String> possiblePrefixes, Object root, boolean allowUnprefixedExpressions) {
        super(new PrefixedValueSourceWrapper((FixedValueSource)new ObjectBasedValueSource(root), possiblePrefixes, allowUnprefixedExpressions));
    }
}

