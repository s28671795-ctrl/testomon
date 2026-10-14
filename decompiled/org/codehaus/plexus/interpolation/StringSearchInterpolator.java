/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.plexus.interpolation.InterpolationCycleException;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.Interpolator;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.SimpleRecursionInterceptor;
import org.codehaus.plexus.interpolation.ValueSource;

public class StringSearchInterpolator
implements Interpolator {
    private Map<String, Object> existingAnswers = new HashMap<String, Object>();
    private List<ValueSource> valueSources = new ArrayList<ValueSource>();
    private List<InterpolationPostProcessor> postProcessors = new ArrayList<InterpolationPostProcessor>();
    private boolean cacheAnswers = false;
    public static final String DEFAULT_START_EXPR = "${";
    public static final String DEFAULT_END_EXPR = "}";
    private String startExpr;
    private String endExpr;
    private String escapeString;

    public StringSearchInterpolator() {
        this.startExpr = DEFAULT_START_EXPR;
        this.endExpr = DEFAULT_END_EXPR;
    }

    public StringSearchInterpolator(String startExpr, String endExpr) {
        this.startExpr = startExpr;
        this.endExpr = endExpr;
    }

    @Override
    public void addValueSource(ValueSource valueSource) {
        this.valueSources.add(valueSource);
    }

    @Override
    public void removeValuesSource(ValueSource valueSource) {
        this.valueSources.remove(valueSource);
    }

    @Override
    public void addPostProcessor(InterpolationPostProcessor postProcessor) {
        this.postProcessors.add(postProcessor);
    }

    @Override
    public void removePostProcessor(InterpolationPostProcessor postProcessor) {
        this.postProcessors.remove(postProcessor);
    }

    @Override
    public String interpolate(String input, String thisPrefixPattern) throws InterpolationException {
        return this.interpolate(input, new SimpleRecursionInterceptor());
    }

    @Override
    public String interpolate(String input, String thisPrefixPattern, RecursionInterceptor recursionInterceptor) throws InterpolationException {
        return this.interpolate(input, recursionInterceptor);
    }

    @Override
    public String interpolate(String input) throws InterpolationException {
        return this.interpolate(input, new SimpleRecursionInterceptor());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String interpolate(String input, RecursionInterceptor recursionInterceptor) throws InterpolationException {
        try {
            String string = this.interpolate(input, recursionInterceptor, new HashSet<String>());
            return string;
        }
        finally {
            if (!this.cacheAnswers) {
                this.existingAnswers.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String interpolate(String input, RecursionInterceptor recursionInterceptor, Set<String> unresolvable) throws InterpolationException {
        int startIdx;
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
            if (!unresolvable.contains(wholeExpr)) {
                if (realExpr.startsWith(".")) {
                    realExpr = realExpr.substring(1);
                }
                if (recursionInterceptor.hasRecursiveExpression(realExpr)) {
                    throw new InterpolationCycleException(recursionInterceptor, realExpr, wholeExpr);
                }
                recursionInterceptor.expressionResolutionStarted(realExpr);
                try {
                    Object value = this.existingAnswers.get(realExpr);
                    Object bestAnswer = null;
                    for (ValueSource valueSource : this.valueSources) {
                        if (value != null) break;
                        value = valueSource.getValue(realExpr);
                        if (value == null || !value.toString().contains(wholeExpr)) continue;
                        bestAnswer = value;
                        value = null;
                    }
                    if (value == null && bestAnswer != null) {
                        throw new InterpolationCycleException(recursionInterceptor, realExpr, wholeExpr);
                    }
                    if (value != null) {
                        value = this.interpolate(String.valueOf(value), recursionInterceptor, unresolvable);
                        if (this.postProcessors != null && !this.postProcessors.isEmpty()) {
                            for (InterpolationPostProcessor postProcessor : this.postProcessors) {
                                Object newVal = postProcessor.execute(realExpr, value);
                                if (newVal == null) continue;
                                value = newVal;
                                break;
                            }
                        }
                        result.append(String.valueOf(value));
                        resolved = true;
                    } else {
                        unresolvable.add(wholeExpr);
                    }
                }
                finally {
                    recursionInterceptor.expressionResolutionFinished(realExpr);
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

    @Override
    public List getFeedback() {
        ArrayList messages = new ArrayList();
        for (ValueSource vs : this.valueSources) {
            List feedback = vs.getFeedback();
            if (feedback == null || feedback.isEmpty()) continue;
            messages.addAll(feedback);
        }
        return messages;
    }

    @Override
    public void clearFeedback() {
        for (ValueSource vs : this.valueSources) {
            vs.clearFeedback();
        }
    }

    @Override
    public boolean isCacheAnswers() {
        return this.cacheAnswers;
    }

    @Override
    public void setCacheAnswers(boolean cacheAnswers) {
        this.cacheAnswers = cacheAnswers;
    }

    @Override
    public void clearAnswers() {
        this.existingAnswers.clear();
    }

    public String getEscapeString() {
        return this.escapeString;
    }

    public void setEscapeString(String escapeString) {
        this.escapeString = escapeString;
    }
}

