/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import org.codehaus.plexus.interpolation.multi.DelimiterSpecification;

public class MultiDelimiterStringSearchInterpolator
implements Interpolator {
    private static final int MAX_TRIES = 10;
    private Map existingAnswers = new HashMap();
    private List<ValueSource> valueSources = new ArrayList<ValueSource>();
    private List postProcessors = new ArrayList();
    private boolean cacheAnswers = false;
    private LinkedHashSet<DelimiterSpecification> delimiters = new LinkedHashSet();
    private String escapeString;

    public MultiDelimiterStringSearchInterpolator() {
        this.delimiters.add(DelimiterSpecification.DEFAULT_SPEC);
    }

    public MultiDelimiterStringSearchInterpolator addDelimiterSpec(String delimiterSpec) {
        if (delimiterSpec == null) {
            return this;
        }
        this.delimiters.add(DelimiterSpecification.parse(delimiterSpec));
        return this;
    }

    public boolean removeDelimiterSpec(String delimiterSpec) {
        if (delimiterSpec == null) {
            return false;
        }
        return this.delimiters.remove(DelimiterSpecification.parse(delimiterSpec));
    }

    public MultiDelimiterStringSearchInterpolator withValueSource(ValueSource vs) {
        this.addValueSource(vs);
        return this;
    }

    public MultiDelimiterStringSearchInterpolator withPostProcessor(InterpolationPostProcessor postProcessor) {
        this.addPostProcessor(postProcessor);
        return this;
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

    private String interpolate(String input, RecursionInterceptor recursionInterceptor, Set<String> unresolvable) throws InterpolationException {
        if (input == null) {
            return "";
        }
        StringBuilder result = new StringBuilder(input.length() * 2);
        String lastResult = input;
        int tries = 0;
        do {
            ++tries;
            if (result.length() > 0) {
                lastResult = result.toString();
                result.setLength(0);
            }
            int startIdx = -1;
            int endIdx = -1;
            DelimiterSpecification selectedSpec = null;
            while ((selectedSpec = this.select(input, endIdx)) != null) {
                String startExpr = selectedSpec.getBegin();
                String endExpr = selectedSpec.getEnd();
                startIdx = selectedSpec.getNextStartIndex();
                result.append(input, endIdx + 1, startIdx);
                endIdx = input.indexOf(endExpr, startIdx + 1);
                if (endIdx < 0) break;
                String wholeExpr = input.substring(startIdx, endIdx + endExpr.length());
                String realExpr = wholeExpr.substring(startExpr.length(), wholeExpr.length() - endExpr.length());
                if (startIdx >= 0 && this.escapeString != null && this.escapeString.length() > 0) {
                    String escape;
                    int startEscapeIdx;
                    int n = startEscapeIdx = startIdx == 0 ? 0 : startIdx - this.escapeString.length();
                    if (startEscapeIdx >= 0 && (escape = input.substring(startEscapeIdx, startIdx)) != null && this.escapeString.equals(escape)) {
                        result.append(wholeExpr);
                        if (startEscapeIdx > 0) {
                            --startEscapeIdx;
                        }
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
                    Object value = this.existingAnswers.get(realExpr);
                    Object bestAnswer = null;
                    for (ValueSource vs : this.valueSources) {
                        if (value != null) break;
                        value = vs.getValue(realExpr);
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
                            for (ValueSource postProcessor1 : this.postProcessors) {
                                InterpolationPostProcessor postProcessor = (InterpolationPostProcessor)((Object)postProcessor1);
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
                    recursionInterceptor.expressionResolutionFinished(realExpr);
                }
                if (!resolved) {
                    result.append(wholeExpr);
                }
                if (endIdx <= -1) continue;
                endIdx += endExpr.length() - 1;
            }
            if (endIdx == -1 && startIdx > -1) {
                result.append(input, startIdx, input.length());
                continue;
            }
            if (endIdx >= input.length()) continue;
            result.append(input, endIdx + 1, input.length());
        } while (!lastResult.equals(result.toString()) && tries < 10);
        return result.toString();
    }

    private DelimiterSpecification select(String input, int lastEndIdx) {
        DelimiterSpecification selected = null;
        for (DelimiterSpecification spec : this.delimiters) {
            int idx;
            spec.clearNextStart();
            if (selected != null || (idx = input.indexOf(spec.getBegin(), lastEndIdx + 1)) <= -1) continue;
            spec.setNextStartIndex(idx);
            selected = spec;
        }
        return selected;
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

    public MultiDelimiterStringSearchInterpolator escapeString(String escapeString) {
        this.escapeString = escapeString;
        return this;
    }

    public MultiDelimiterStringSearchInterpolator setDelimiterSpecs(LinkedHashSet<String> specs) {
        this.delimiters.clear();
        for (String spec : specs) {
            if (spec == null) continue;
            this.delimiters.add(DelimiterSpecification.parse(spec));
        }
        return this;
    }
}

