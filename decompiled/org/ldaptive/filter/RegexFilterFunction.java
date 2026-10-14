/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.ResultCode;
import org.ldaptive.filter.AbstractFilterFunction;
import org.ldaptive.filter.ApproximateFilter;
import org.ldaptive.filter.EqualityFilter;
import org.ldaptive.filter.ExtensibleFilter;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterParseException;
import org.ldaptive.filter.FilterUtils;
import org.ldaptive.filter.GreaterOrEqualFilter;
import org.ldaptive.filter.LessOrEqualFilter;
import org.ldaptive.filter.PresenceFilter;
import org.ldaptive.filter.SubstringFilter;

public class RegexFilterFunction
extends AbstractFilterFunction {
    private static final String ATTRIBUTE_DESC = "[\\p{Alnum};\\-\\.]+";
    private static final String ASSERTION_VALUE = "([^\\)]*+)";
    private static final Pattern ESCAPE_CHARS_PATTERN = Pattern.compile("[\u0000\\(\\)]+");
    private static final Pattern PRESENCE_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)=\\*\\)");
    private static final Pattern EQUALITY_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)=([^\\*]*)\\)");
    private static final Pattern SUBSTRING_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)=((?:[^\\*]*\\*[^\\*]*)+)\\)");
    private static final Pattern EXTENSIBLE_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)?(:[Dd][Nn])?(?::(.+))?:=(([^\\)]*+))\\)");
    private static final Pattern GREATER_OR_EQUAL_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)>=(([^\\)]*+))\\)");
    private static final Pattern LESS_OR_EQUAL_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)<=(([^\\)]*+))\\)");
    private static final Pattern APPROXIMATE_FILTER_PATTERN = Pattern.compile("\\(([\\p{Alnum};\\-\\.]+)~=(([^\\)]*+))\\)");

    @Override
    protected Filter parseFilterComp(String filter2) throws FilterParseException {
        Filter searchFilter = RegexFilterFunction.parsePresenceFilter(filter2);
        if (searchFilter == null) {
            searchFilter = RegexFilterFunction.parseEqualityFilter(filter2);
        }
        if (searchFilter == null) {
            searchFilter = RegexFilterFunction.parseSubstringFilter(filter2);
        }
        if (searchFilter == null) {
            searchFilter = RegexFilterFunction.parseExtensibleFilter(filter2);
        }
        if (searchFilter == null) {
            searchFilter = RegexFilterFunction.parseGreaterOrEqualFilter(filter2);
        }
        if (searchFilter == null) {
            searchFilter = RegexFilterFunction.parseLessOrEqualFilter(filter2);
        }
        if (searchFilter == null) {
            searchFilter = RegexFilterFunction.parseApproximateFilter(filter2);
        }
        return searchFilter;
    }

    static PresenceFilter parsePresenceFilter(String component) {
        Matcher m4 = PRESENCE_FILTER_PATTERN.matcher(component);
        if (m4.matches()) {
            return new PresenceFilter(m4.group(1));
        }
        return null;
    }

    static EqualityFilter parseEqualityFilter(String component) throws FilterParseException {
        Matcher m4 = EQUALITY_FILTER_PATTERN.matcher(component);
        if (m4.matches()) {
            String attr = m4.group(1);
            String value = m4.group(2);
            RegexFilterFunction.throwOnEscapeChars(value);
            return new EqualityFilter(attr, FilterUtils.parseAssertionValue(value));
        }
        return null;
    }

    static SubstringFilter parseSubstringFilter(String component) throws FilterParseException {
        Matcher m4 = SUBSTRING_FILTER_PATTERN.matcher(component);
        if (m4.matches() && !m4.group(2).equals("*") && !m4.group(2).contains("**")) {
            String attr = m4.group(1);
            String assertions = m4.group(2);
            String startsWith = null;
            int firstAsterisk = assertions.indexOf(42);
            if (firstAsterisk > 0) {
                startsWith = assertions.substring(0, firstAsterisk);
                RegexFilterFunction.throwOnEscapeChars(startsWith);
            }
            String endsWith = null;
            int lastAsterisk = assertions.lastIndexOf(42);
            if (lastAsterisk < assertions.length() - 1) {
                endsWith = assertions.substring(lastAsterisk + 1);
                RegexFilterFunction.throwOnEscapeChars(endsWith);
            }
            String[] contains = null;
            if (lastAsterisk > firstAsterisk) {
                contains = assertions.substring(firstAsterisk + 1, lastAsterisk).split("\\*");
                RegexFilterFunction.throwOnEscapeChars(contains);
            }
            try {
                return new SubstringFilter(attr, startsWith != null ? FilterUtils.parseAssertionValue(startsWith) : null, endsWith != null ? FilterUtils.parseAssertionValue(endsWith) : null, contains != null ? FilterUtils.parseAssertionValue(contains) : null);
            }
            catch (IllegalArgumentException e) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, (Throwable)e);
            }
        }
        return null;
    }

    static ExtensibleFilter parseExtensibleFilter(String component) throws FilterParseException {
        Matcher m4 = EXTENSIBLE_FILTER_PATTERN.matcher(component);
        if (m4.matches()) {
            String rule = m4.group(3);
            String attr = m4.group(1);
            String value = m4.group(4);
            boolean dn = m4.group(2) != null;
            try {
                return new ExtensibleFilter(rule, attr, FilterUtils.parseAssertionValue(value), dn);
            }
            catch (IllegalArgumentException e) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, (Throwable)e);
            }
        }
        return null;
    }

    static GreaterOrEqualFilter parseGreaterOrEqualFilter(String component) throws FilterParseException {
        Matcher m4 = GREATER_OR_EQUAL_FILTER_PATTERN.matcher(component);
        if (m4.matches()) {
            String attr = m4.group(1);
            String value = m4.group(2);
            return new GreaterOrEqualFilter(attr, FilterUtils.parseAssertionValue(value));
        }
        return null;
    }

    static LessOrEqualFilter parseLessOrEqualFilter(String component) throws FilterParseException {
        Matcher m4 = LESS_OR_EQUAL_FILTER_PATTERN.matcher(component);
        if (m4.matches()) {
            String attr = m4.group(1);
            String value = m4.group(2);
            return new LessOrEqualFilter(attr, FilterUtils.parseAssertionValue(value));
        }
        return null;
    }

    static ApproximateFilter parseApproximateFilter(String component) throws FilterParseException {
        Matcher m4 = APPROXIMATE_FILTER_PATTERN.matcher(component);
        if (m4.matches()) {
            String attr = m4.group(1);
            String value = m4.group(2);
            return new ApproximateFilter(attr, FilterUtils.parseAssertionValue(value));
        }
        return null;
    }

    private static void throwOnEscapeChars(String ... values2) throws FilterParseException {
        for (String s2 : values2) {
            Matcher m4 = ESCAPE_CHARS_PATTERN.matcher(s2);
            if (!m4.find()) continue;
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid filter syntax, contains unescaped characters");
        }
    }
}

