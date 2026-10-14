/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.List;
import java.util.Properties;
import org.codehaus.plexus.interpolation.fixed.AbstractDelegatingValueSource;
import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.PrefixedValueSourceWrapper;
import org.codehaus.plexus.interpolation.fixed.PropertiesBasedValueSource;

public class PrefixedPropertiesValueSource
extends AbstractDelegatingValueSource {
    public PrefixedPropertiesValueSource(String prefix, Properties properties) {
        super(new PrefixedValueSourceWrapper((FixedValueSource)new PropertiesBasedValueSource(properties), prefix));
    }

    public PrefixedPropertiesValueSource(List<String> possiblePrefixes, Properties properties, boolean allowUnprefixedExpressions) {
        super(new PrefixedValueSourceWrapper((FixedValueSource)new PropertiesBasedValueSource(properties), possiblePrefixes, allowUnprefixedExpressions));
    }
}

