/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.List;
import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.InterpolationState;
import org.codehaus.plexus.interpolation.util.ValueSourceUtils;

public class PrefixedValueSourceWrapper
implements FixedValueSource {
    private final FixedValueSource valueSource;
    private final String[] possiblePrefixes;
    private boolean allowUnprefixedExpressions;
    private String lastExpression;

    public PrefixedValueSourceWrapper(FixedValueSource valueSource, String prefix) {
        this.valueSource = valueSource;
        this.possiblePrefixes = new String[]{prefix};
    }

    public PrefixedValueSourceWrapper(FixedValueSource valueSource, String prefix, boolean allowUnprefixedExpressions) {
        this.valueSource = valueSource;
        this.possiblePrefixes = new String[]{prefix};
        this.allowUnprefixedExpressions = allowUnprefixedExpressions;
    }

    public PrefixedValueSourceWrapper(FixedValueSource valueSource, List<String> possiblePrefixes) {
        this.valueSource = valueSource;
        this.possiblePrefixes = possiblePrefixes.toArray(new String[possiblePrefixes.size()]);
    }

    public PrefixedValueSourceWrapper(FixedValueSource valueSource, List<String> possiblePrefixes, boolean allowUnprefixedExpressions) {
        this.valueSource = valueSource;
        this.possiblePrefixes = possiblePrefixes.toArray(new String[possiblePrefixes.size()]);
        this.allowUnprefixedExpressions = allowUnprefixedExpressions;
    }

    @Override
    public Object getValue(String expression, InterpolationState interpolationState) {
        if ((expression = ValueSourceUtils.trimPrefix(expression, this.possiblePrefixes, this.allowUnprefixedExpressions)) == null) {
            return null;
        }
        return this.valueSource.getValue(expression, interpolationState);
    }
}

