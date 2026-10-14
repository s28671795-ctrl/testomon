/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.plexus.interpolation.InterpolationCycleException;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.Interpolator;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.SimpleRecursionInterceptor;
import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.interpolation.util.StringUtils;

public class RegexBasedInterpolator
implements Interpolator {
    private String startRegex;
    private String endRegex;
    private Map existingAnswers = new HashMap();
    private List<ValueSource> valueSources = new ArrayList<ValueSource>();
    private List<InterpolationPostProcessor> postProcessors = new ArrayList<InterpolationPostProcessor>();
    private boolean reusePatterns = false;
    private boolean cacheAnswers = false;
    public static final String DEFAULT_REGEXP = "\\$\\{(.+?)\\}";
    private Map<String, Pattern> compiledPatterns = new WeakHashMap<String, Pattern>();

    public RegexBasedInterpolator() {
        this.compiledPatterns.put(DEFAULT_REGEXP, Pattern.compile(DEFAULT_REGEXP));
    }

    public RegexBasedInterpolator(boolean reusePatterns) {
        this();
        this.reusePatterns = reusePatterns;
    }

    public RegexBasedInterpolator(String startRegex, String endRegex) {
        this();
        this.startRegex = startRegex;
        this.endRegex = endRegex;
    }

    public RegexBasedInterpolator(List valueSources) {
        this();
        this.valueSources.addAll(valueSources);
    }

    public RegexBasedInterpolator(String startRegex, String endRegex, List valueSources) {
        this();
        this.startRegex = startRegex;
        this.endRegex = endRegex;
        this.valueSources.addAll(valueSources);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String interpolate(String input, String thisPrefixPattern, RecursionInterceptor recursionInterceptor) throws InterpolationException {
        Pattern expressionPattern;
        if (input == null) {
            return "";
        }
        if (recursionInterceptor == null) {
            recursionInterceptor = new SimpleRecursionInterceptor();
        }
        if (thisPrefixPattern != null && thisPrefixPattern.length() == 0) {
            thisPrefixPattern = null;
        }
        int realExprGroup = 2;
        if (this.startRegex != null || this.endRegex != null) {
            if (thisPrefixPattern == null) {
                expressionPattern = this.getPattern(this.startRegex + this.endRegex);
                realExprGroup = 1;
            } else {
                expressionPattern = this.getPattern(this.startRegex + thisPrefixPattern + this.endRegex);
            }
        } else if (thisPrefixPattern != null) {
            expressionPattern = this.getPattern("\\$\\{(" + thisPrefixPattern + ")?(.+?)\\}");
        } else {
            expressionPattern = this.getPattern(DEFAULT_REGEXP);
            realExprGroup = 1;
        }
        try {
            String string = this.interpolate(input, recursionInterceptor, expressionPattern, realExprGroup);
            return string;
        }
        finally {
            if (!this.cacheAnswers) {
                this.clearAnswers();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Pattern getPattern(String regExp) {
        Pattern pattern;
        if (!this.reusePatterns) {
            return Pattern.compile(regExp);
        }
        RegexBasedInterpolator regexBasedInterpolator = this;
        synchronized (regexBasedInterpolator) {
            pattern = this.compiledPatterns.get(regExp);
            if (pattern != null) {
                return pattern;
            }
            pattern = Pattern.compile(regExp);
            this.compiledPatterns.put(regExp, pattern);
        }
        return pattern;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String interpolate(String input, RecursionInterceptor recursionInterceptor, Pattern expressionPattern, int realExprGroup) throws InterpolationException {
        if (input == null) {
            return "";
        }
        String result = input;
        Matcher matcher = expressionPattern.matcher(result);
        while (matcher.find()) {
            String wholeExpr = matcher.group(0);
            String realExpr = matcher.group(realExprGroup);
            if (realExpr.startsWith(".")) {
                realExpr = realExpr.substring(1);
            }
            if (recursionInterceptor.hasRecursiveExpression(realExpr)) {
                throw new InterpolationCycleException(recursionInterceptor, realExpr, wholeExpr);
            }
            recursionInterceptor.expressionResolutionStarted(realExpr);
            try {
                Object value = this.existingAnswers.get(realExpr);
                for (ValueSource vs : this.valueSources) {
                    if (value != null) break;
                    value = vs.getValue(realExpr);
                }
                if (value == null) continue;
                value = this.interpolate(String.valueOf(value), recursionInterceptor, expressionPattern, realExprGroup);
                if (this.postProcessors != null && !this.postProcessors.isEmpty()) {
                    for (InterpolationPostProcessor postProcessor : this.postProcessors) {
                        Object newVal = postProcessor.execute(realExpr, value);
                        if (newVal == null) continue;
                        value = newVal;
                        break;
                    }
                }
                result = StringUtils.replace(result, wholeExpr, String.valueOf(value));
                matcher.reset(result);
            }
            finally {
                recursionInterceptor.expressionResolutionFinished(realExpr);
            }
        }
        return result;
    }

    @Override
    public List getFeedback() {
        ArrayList messages = new ArrayList();
        for (ValueSource valueSource : this.valueSources) {
            ValueSource vs = valueSource;
            List feedback = vs.getFeedback();
            if (feedback == null || feedback.isEmpty()) continue;
            messages.addAll(feedback);
        }
        return messages;
    }

    @Override
    public void clearFeedback() {
        Iterator<ValueSource> iterator2 = this.valueSources.iterator();
        while (iterator2.hasNext()) {
            ValueSource valueSource;
            ValueSource vs = valueSource = iterator2.next();
            vs.clearFeedback();
        }
    }

    @Override
    public String interpolate(String input, String thisPrefixPattern) throws InterpolationException {
        return this.interpolate(input, thisPrefixPattern, null);
    }

    @Override
    public String interpolate(String input) throws InterpolationException {
        return this.interpolate(input, null, null);
    }

    @Override
    public String interpolate(String input, RecursionInterceptor recursionInterceptor) throws InterpolationException {
        return this.interpolate(input, null, recursionInterceptor);
    }

    public boolean isReusePatterns() {
        return this.reusePatterns;
    }

    public void setReusePatterns(boolean reusePatterns) {
        this.reusePatterns = reusePatterns;
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
}

