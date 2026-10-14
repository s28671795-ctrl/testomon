/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.ResultCode;
import org.ldaptive.filter.AndFilter;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterFunction;
import org.ldaptive.filter.FilterParseException;
import org.ldaptive.filter.FilterSet;
import org.ldaptive.filter.NotFilter;
import org.ldaptive.filter.OrFilter;

public abstract class AbstractFilterFunction
implements FilterFunction {
    @Override
    public Filter parse(String filter2) throws FilterParseException {
        String balancedFilter;
        if (filter2.startsWith("(")) {
            if (!filter2.endsWith(")")) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, "Unbalanced parentheses. Opening paren without closing paren.");
            }
            balancedFilter = filter2;
        } else {
            if (filter2.endsWith(")")) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, "Unbalanced parentheses. Closing paren without opening paren.");
            }
            balancedFilter = "(".concat(filter2).concat(")");
        }
        return this.readNextComponent(balancedFilter);
    }

    private Filter readNextComponent(String filter2) throws FilterParseException {
        Filter searchFilter;
        int end = filter2.length() - 1;
        if (filter2.charAt(0) != '(' || filter2.charAt(end) != ')') {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter must be surround by parentheses: '" + filter2 + "'");
        }
        int pos = 1;
        switch (filter2.charAt(pos)) {
            case '&': {
                searchFilter = this.readFilterSet(new AndFilter(), filter2, ++pos, end);
                break;
            }
            case '|': {
                searchFilter = this.readFilterSet(new OrFilter(), filter2, ++pos, end);
                break;
            }
            case '!': {
                searchFilter = this.readFilterSet(new NotFilter(), filter2, ++pos, end);
                break;
            }
            default: {
                searchFilter = this.parseFilterComp(filter2);
                if (searchFilter != null) break;
                throw new FilterParseException(ResultCode.FILTER_ERROR, "Could not determine filter type for '" + filter2 + "'");
            }
        }
        return searchFilter;
    }

    private FilterSet readFilterSet(FilterSet set, String filter2, int start2, int end) throws FilterParseException {
        int pos = start2;
        int closeIndex = this.findMatchingParenPosition(filter2, pos);
        if (filter2.charAt(pos) != '(' || closeIndex == -1 || closeIndex == end) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid filter syntax, missing parenthesis after " + set.getType());
        }
        while (pos < end) {
            try {
                set.add(this.readNextComponent(filter2.substring(pos, closeIndex + 1)));
            }
            catch (Exception e) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, (Throwable)e);
            }
            pos = closeIndex + 1;
            if (pos >= end) continue;
            closeIndex = this.findMatchingParenPosition(filter2, pos);
        }
        return set;
    }

    private int findMatchingParenPosition(String filter2, int start2) throws FilterParseException {
        if (filter2 == null || filter2.length() == 0) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter cannot be null or empty");
        }
        if (filter2.charAt(start2) != '(') {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter must begin with '('");
        }
        int parenCount = 1;
        for (int pos = start2 + 1; pos < filter2.length(); ++pos) {
            char c = filter2.charAt(pos);
            if (c == '(') {
                ++parenCount;
            } else if (c == ')') {
                --parenCount;
            }
            if (parenCount != 0) continue;
            return pos;
        }
        return -1;
    }

    protected abstract Filter parseFilterComp(String var1) throws FilterParseException;
}

