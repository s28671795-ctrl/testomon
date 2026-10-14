/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.List;
import org.codehaus.plexus.interpolation.AbstractDelegatingValueSource;
import org.codehaus.plexus.interpolation.ObjectBasedValueSource;
import org.codehaus.plexus.interpolation.PrefixedValueSourceWrapper;
import org.codehaus.plexus.interpolation.QueryEnabledValueSource;
import org.codehaus.plexus.interpolation.ValueSource;

public class PrefixedObjectValueSource
extends AbstractDelegatingValueSource
implements QueryEnabledValueSource {
    public PrefixedObjectValueSource(String prefix, Object root) {
        super(new PrefixedValueSourceWrapper((ValueSource)new ObjectBasedValueSource(root), prefix));
    }

    public PrefixedObjectValueSource(List<String> possiblePrefixes, Object root, boolean allowUnprefixedExpressions) {
        super(new PrefixedValueSourceWrapper((ValueSource)new ObjectBasedValueSource(root), possiblePrefixes, allowUnprefixedExpressions));
    }

    @Override
    public String getLastExpression() {
        return ((QueryEnabledValueSource)this.getDelegate()).getLastExpression();
    }
}

