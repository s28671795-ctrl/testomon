/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.ArrayList;
import org.codehaus.plexus.interpolation.BasicInterpolator;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.fixed.FixedValueSource;
import org.codehaus.plexus.interpolation.fixed.InterpolationCycleException;
import org.codehaus.plexus.interpolation.fixed.InterpolationState;

public class FixedStringSearchInterpolator
implements FixedValueSource {
    private final FixedValueSource[] valueSources;
    private final InterpolationPostProcessor postProcessor;
    public static final String DEFAULT_START_EXPR = "${";
    public static final String DEFAULT_END_EXPR = "}";
    private final String startExpr;
    private final String endExpr;
    private final String escapeString;

    private FixedStringSearchInterpolator(String startExpr, String endExpr, String escapeString, InterpolationPostProcessor postProcessor, FixedValueSource ... valueSources) {
        this.startExpr = startExpr;
        this.endExpr = endExpr;
        this.escapeString = escapeString;
        if (valueSources == null) {
            throw new IllegalArgumentException("valueSources cannot be null");
        }
        for (int i = 0; i < valueSources.length; ++i) {
            if (valueSources[i] != null) continue;
            throw new IllegalArgumentException("valueSources[" + i + "] is null");
        }
        this.valueSources = valueSources;
        this.postProcessor = postProcessor;
    }

    public static FixedStringSearchInterpolator create(String startExpr, String endExpr, FixedValueSource ... valueSources) {
        return new FixedStringSearchInterpolator(startExpr, endExpr, null, null, valueSources);
    }

    public static FixedStringSearchInterpolator create(FixedValueSource ... valueSources) {
        return new FixedStringSearchInterpolator(DEFAULT_START_EXPR, DEFAULT_END_EXPR, null, null, valueSources);
    }

    public static FixedStringSearchInterpolator createWithPermittedNulls(FixedValueSource ... valueSources) {
        ArrayList<FixedValueSource> nonnulls = new ArrayList<FixedValueSource>();
        for (FixedValueSource item : valueSources) {
            if (item == null) continue;
            nonnulls.add(item);
        }
        return new FixedStringSearchInterpolator(DEFAULT_START_EXPR, DEFAULT_END_EXPR, null, null, nonnulls.toArray(new FixedValueSource[nonnulls.size()]));
    }

    public FixedStringSearchInterpolator withExpressionMarkers(String startExpr, String endExpr) {
        return new FixedStringSearchInterpolator(startExpr, endExpr, this.escapeString, this.postProcessor, this.valueSources);
    }

    public FixedStringSearchInterpolator withPostProcessor(InterpolationPostProcessor postProcessor) {
        return new FixedStringSearchInterpolator(this.startExpr, this.endExpr, this.escapeString, postProcessor, this.valueSources);
    }

    public FixedStringSearchInterpolator withEscapeString(String escapeString) {
        return new FixedStringSearchInterpolator(this.startExpr, this.endExpr, escapeString, this.postProcessor, this.valueSources);
    }

    public String interpolate(String input) throws InterpolationCycleException {
        return this.interpolate(input, new InterpolationState());
    }

    public static FixedStringSearchInterpolator empty() {
        return FixedStringSearchInterpolator.create(new FixedValueSource[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object getValue(String realExpr, InterpolationState interpolationState) {
        interpolationState.recursionInterceptor.expressionResolutionStarted(realExpr);
        try {
            FixedValueSource valueSource;
            Object value = null;
            Object object = this.valueSources;
            int n = ((FixedValueSource[])object).length;
            for (int i = 0; i < n && (value = (valueSource = object[i]).getValue(realExpr, interpolationState)) == null; ++i) {
            }
            if (value != null) {
                if (interpolationState.root != null) {
                    value = interpolationState.root.interpolate(String.valueOf(value), interpolationState);
                }
                object = String.valueOf(value);
                return object;
            }
            object = null;
            return object;
        }
        finally {
            interpolationState.recursionInterceptor.expressionResolutionFinished(realExpr);
        }
    }

    public BasicInterpolator asBasicInterpolator() {
        final InterpolationState is = new InterpolationState();
        return new BasicInterpolator(){

            @Override
            public String interpolate(String input) throws InterpolationException {
                return FixedStringSearchInterpolator.this.interpolate(input, is);
            }

            @Override
            public String interpolate(String input, RecursionInterceptor recursionInterceptor) throws InterpolationException {
                is.setRecursionInterceptor(recursionInterceptor);
                return FixedStringSearchInterpolator.this.interpolate(input, is);
            }
        };
    }

    public String interpolate(String input, InterpolationState interpolationState) throws InterpolationCycleException {
        int startIdx;
        if (interpolationState.root == null) {
            interpolationState.root = this;
        }
        if (input == null) {
            return "";
        }
        StringBuilder result = new StringBuilder(input.length() * 2);
        int endIdx = -1;
        while ((startIdx = input.indexOf(this.startExpr, endIdx + 1)) > -1) {
            result.append(input, endIdx + 1, startIdx);
            endIdx = input.indexOf(this.endExpr, startIdx + 1);
            if (endIdx < 0) break;
            String wholeExpr = input.substring(startIdx, endIdx + this.endExpr.length());
            String realExpr = wholeExpr.substring(this.startExpr.length(), wholeExpr.length() - this.endExpr.length());
            if (startIdx >= 0 && this.escapeString != null && this.escapeString.length() > 0) {
                String escape;
                int startEscapeIdx;
                int n = startEscapeIdx = startIdx == 0 ? 0 : startIdx - this.escapeString.length();
                if (startEscapeIdx >= 0 && this.escapeString.equals(escape = input.substring(startEscapeIdx, startIdx))) {
                    result.append(wholeExpr);
                    result.replace(startEscapeIdx, startEscapeIdx + this.escapeString.length(), "");
                    continue;
                }
            }
            boolean resolved = false;
            if (!interpolationState.unresolvable.contains(wholeExpr)) {
                if (realExpr.startsWith(".")) {
                    realExpr = realExpr.substring(1);
                }
                if (interpolationState.recursionInterceptor.hasRecursiveExpression(realExpr)) {
                    throw new InterpolationCycleException(interpolationState.recursionInterceptor, realExpr, wholeExpr);
                }
                Object value = this.getValue(realExpr, interpolationState);
                if (value != null) {
                    Object newVal;
                    value = this.interpolate(String.valueOf(value), interpolationState);
                    if (this.postProcessor != null && (newVal = this.postProcessor.execute(realExpr, value)) != null) {
                        value = newVal;
                    }
                    result.append(String.valueOf(value));
                    resolved = true;
                } else {
                    interpolationState.unresolvable.add(wholeExpr);
                }
            }
            if (!resolved) {
                result.append(wholeExpr);
            }
            if (endIdx <= -1) continue;
            endIdx += this.endExpr.length() - 1;
        }
        if (endIdx == -1 && startIdx > -1) {
            result.append(input, startIdx, input.length());
        } else if (endIdx < input.length()) {
            result.append(input, endIdx + 1, input.length());
        }
        return result.toString();
    }
}

