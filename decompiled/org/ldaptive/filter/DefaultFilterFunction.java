/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class DefaultFilterFunction
extends AbstractFilterFunction {
    protected static final String DEFAULT_ATTRIBUTE_DESCRIPTION_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789;.-";
    private final String attributeDescriptionChars;

    public DefaultFilterFunction() {
        this(DEFAULT_ATTRIBUTE_DESCRIPTION_CHARS);
    }

    public DefaultFilterFunction(String validChars) {
        this.attributeDescriptionChars = validChars;
    }

    @Override
    protected Filter parseFilterComp(String filter2) throws FilterParseException {
        Filter searchFilter;
        if (filter2 == null || filter2.isEmpty()) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter cannot be null or empty");
        }
        CharBuffer filterBuffer = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(filter2.getBytes()));
        if (filterBuffer.get() != '(') {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter '" + filter2 + "' must start with '('");
        }
        if (filterBuffer.get(filterBuffer.limit() - 1) != ')') {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter '" + filter2 + "' must end with ')'");
        }
        if (!filterBuffer.hasRemaining()) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter '" + filter2 + "' does not contain an expression");
        }
        if (!(filterBuffer = filterBuffer.limit(filterBuffer.limit() - 1).slice()).hasRemaining()) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Filter '" + filter2 + "' does not contain an expression");
        }
        if (filterBuffer.get() == ':') {
            searchFilter = this.parseExtensible(null, filterBuffer);
        } else {
            filterBuffer.position(filterBuffer.position() - 1);
            CharBuffer attribute = this.readAttribute(filterBuffer);
            if (attribute.length() == 0) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid attribute description for filter '" + filter2 + "'");
            }
            switch (filterBuffer.get()) {
                case '=': {
                    if (!filterBuffer.hasRemaining()) {
                        searchFilter = new EqualityFilter(attribute.toString(), new byte[0]);
                        break;
                    }
                    if (filterBuffer.get() == '*' && !filterBuffer.hasRemaining()) {
                        searchFilter = new PresenceFilter(attribute.toString());
                        break;
                    }
                    searchFilter = this.parseSubstringOrEquality(attribute.toString(), filterBuffer.position(filterBuffer.position() - 1).slice());
                    break;
                }
                case ':': {
                    if (filterBuffer.get() != '=') {
                        searchFilter = this.parseExtensible(attribute.toString(), filterBuffer.position(filterBuffer.position() - 1).slice());
                        break;
                    }
                    try {
                        searchFilter = new ExtensibleFilter(null, attribute.toString(), FilterUtils.parseAssertionValue(filterBuffer.slice().toString()));
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        throw new FilterParseException(ResultCode.FILTER_ERROR, (Throwable)e);
                    }
                }
                case '>': {
                    if (filterBuffer.get() != '=') {
                        throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid greaterOrEqual expression for filter '" + filter2 + "'");
                    }
                    searchFilter = new GreaterOrEqualFilter(attribute.toString(), FilterUtils.parseAssertionValue(filterBuffer.slice().toString()));
                    break;
                }
                case '<': {
                    if (filterBuffer.get() != '=') {
                        throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid lessOrEqual expression for filter '" + filter2 + "'");
                    }
                    searchFilter = new LessOrEqualFilter(attribute.toString(), FilterUtils.parseAssertionValue(filterBuffer.slice().toString()));
                    break;
                }
                case '~': {
                    if (filterBuffer.get() != '=') {
                        throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid approximate expression for filter '" + filter2 + "'");
                    }
                    searchFilter = new ApproximateFilter(attribute.toString(), FilterUtils.parseAssertionValue(filterBuffer.slice().toString()));
                    break;
                }
                default: {
                    throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid filter expression for filter '" + filter2 + "'");
                }
            }
        }
        return searchFilter;
    }

    private CharBuffer readAttribute(CharBuffer cb) throws FilterParseException {
        char c;
        if (cb.length() == 0) {
            throw new FilterParseException(ResultCode.LOCAL_ERROR, "Attribute buffer size must be greater than zero");
        }
        int limit = cb.limit();
        while (cb.hasRemaining() && this.attributeDescriptionChars.indexOf(c = cb.get()) != -1) {
        }
        int pos = cb.position() - 1;
        cb.position(pos);
        CharBuffer slice = cb.flip().slice();
        cb.limit(limit).position(pos);
        return slice;
    }

    private Filter parseSubstringOrEquality(String attribute, CharBuffer cb) throws FilterParseException {
        Filter filter2;
        Map<String, List<CharBuffer>> substrings = this.readSubstrings(cb);
        if (substrings.size() == 0) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Could not parse equality or substring assertion");
        }
        if (substrings.containsKey("EQUALITY")) {
            filter2 = new EqualityFilter(attribute, FilterUtils.parseAssertionValue(substrings.get("EQUALITY").get(0).toString()));
        } else {
            try {
                filter2 = new SubstringFilter(attribute, substrings.get("INITIAL") == null ? null : FilterUtils.parseAssertionValue(substrings.get("INITIAL").get(0).toString()), substrings.get("FINAL") == null ? null : FilterUtils.parseAssertionValue(substrings.get("FINAL").get(0).toString()), substrings.get("ANY") == null ? null : FilterUtils.parseAssertionValue((String[])substrings.get("ANY").stream().map(CharBuffer::toString).toArray(String[]::new)));
            }
            catch (IllegalArgumentException e) {
                throw new FilterParseException(ResultCode.FILTER_ERROR, (Throwable)e);
            }
        }
        return filter2;
    }

    private Map<String, List<CharBuffer>> readSubstrings(CharBuffer cb) {
        HashMap<String, List<CharBuffer>> buffers = new HashMap<String, List<CharBuffer>>();
        int limit = cb.limit();
        cb.mark();
        while (cb.hasRemaining()) {
            char c = cb.get();
            if (c != '*') continue;
            if (cb.position() == 1) {
                buffers.put("INITIAL", null);
                cb.mark();
                continue;
            }
            if (cb.position() == cb.limit()) {
                buffers.put("FINAL", null);
            }
            int pos = cb.position();
            if (buffers.containsKey("INITIAL")) {
                if (!buffers.containsKey("ANY")) {
                    buffers.put("ANY", new ArrayList());
                }
                ((List)buffers.get("ANY")).add(cb.limit(pos - 1).reset().slice());
            } else {
                buffers.put("INITIAL", Collections.singletonList(cb.limit(pos - 1).reset().slice()));
            }
            cb.limit(limit).position(pos);
            cb.mark();
        }
        cb.reset();
        if (cb.hasRemaining()) {
            if (buffers.size() > 0) {
                buffers.put("FINAL", Collections.singletonList(cb.slice()));
            } else {
                buffers.put("EQUALITY", Collections.singletonList(cb.slice()));
            }
        }
        if (!buffers.containsKey("INITIAL")) {
            buffers.put("INITIAL", null);
        }
        if (!buffers.containsKey("ANY")) {
            buffers.put("ANY", null);
        }
        if (!buffers.containsKey("FINAL")) {
            buffers.put("FINAL", null);
        }
        return buffers;
    }

    private ExtensibleFilter parseExtensible(String attribute, CharBuffer cb) throws FilterParseException {
        boolean dnAttrs = false;
        CharBuffer remainingFilter = cb.slice();
        CharBuffer matchingRule = this.sliceAtMatch(remainingFilter, ':');
        if (matchingRule == null) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid extensible expression, no data after ':'");
        }
        if ("dn".equalsIgnoreCase(matchingRule.toString())) {
            dnAttrs = true;
            matchingRule = null;
            if (remainingFilter.hasRemaining()) {
                if (remainingFilter.get() != '=') {
                    remainingFilter = remainingFilter.position(remainingFilter.position() - 1).slice();
                    matchingRule = this.sliceAtMatch(remainingFilter, ':');
                } else {
                    remainingFilter.position(remainingFilter.position() - 1);
                }
            }
        }
        if (remainingFilter.hasRemaining() && remainingFilter.get() != '=') {
            throw new FilterParseException(ResultCode.FILTER_ERROR, "Invalid extensible expression");
        }
        try {
            return new ExtensibleFilter(matchingRule == null ? null : matchingRule.toString(), attribute, FilterUtils.parseAssertionValue(remainingFilter.slice().toString()), dnAttrs);
        }
        catch (IllegalArgumentException e) {
            throw new FilterParseException(ResultCode.FILTER_ERROR, (Throwable)e);
        }
    }

    private CharBuffer sliceAtMatch(CharBuffer cb, char match) {
        int limit = cb.limit();
        while (cb.hasRemaining()) {
            char c = cb.get();
            if (c != match) continue;
            int pos = cb.position();
            cb.position(pos - 1);
            CharBuffer slice = cb.flip().slice();
            cb.limit(limit).position(pos);
            return slice;
        }
        return null;
    }
}

