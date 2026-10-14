/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.List;
import java.util.Properties;
import org.codehaus.plexus.interpolation.AbstractDelegatingValueSource;
import org.codehaus.plexus.interpolation.PrefixedValueSourceWrapper;
import org.codehaus.plexus.interpolation.PropertiesBasedValueSource;
import org.codehaus.plexus.interpolation.QueryEnabledValueSource;
import org.codehaus.plexus.interpolation.ValueSource;

public class PrefixedPropertiesValueSource
extends AbstractDelegatingValueSource
implements QueryEnabledValueSource {
    public PrefixedPropertiesValueSource(String prefix, Properties properties) {
        super(new PrefixedValueSourceWrapper((ValueSource)new PropertiesBasedValueSource(properties), prefix));
    }

    public PrefixedPropertiesValueSource(List<String> possiblePrefixes, Properties properties, boolean allowUnprefixedExpressions) {
        super(new PrefixedValueSourceWrapper((ValueSource)new PropertiesBasedValueSource(properties), possiblePrefixes, allowUnprefixedExpressions));
    }

    @Override
    public String getLastExpression() {
        return ((QueryEnabledValueSource)this.getDelegate()).getLastExpression();
    }
}

