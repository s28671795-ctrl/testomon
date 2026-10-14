/*
 * Decompiled with CFR 0.152.
 */
package org.reflections.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.reflections.ReflectionsException;

public class FilterBuilder
implements Predicate<String> {
    private final List<Predicate<String>> chain = new ArrayList<Predicate<String>>();

    public FilterBuilder() {
    }

    private FilterBuilder(Collection<Predicate<String>> filters) {
        this.chain.addAll(filters);
    }

    public FilterBuilder includePackage(String value) {
        return this.includePattern(FilterBuilder.prefixPattern(value));
    }

    public FilterBuilder excludePackage(String value) {
        return this.excludePattern(FilterBuilder.prefixPattern(value));
    }

    public FilterBuilder includePattern(String regex2) {
        return this.add(new Include(regex2));
    }

    public FilterBuilder excludePattern(String regex2) {
        return this.add(new Exclude(regex2));
    }

    @Deprecated
    public FilterBuilder include(String regex2) {
        return this.add(new Include(regex2));
    }

    @Deprecated
    public FilterBuilder exclude(String regex2) {
        this.add(new Exclude(regex2));
        return this;
    }

    public static FilterBuilder parsePackages(String includeExcludeString) {
        ArrayList<Predicate<String>> filters = new ArrayList<Predicate<String>>();
        block4: for (String string : includeExcludeString.split(",")) {
            String trimmed = string.trim();
            char prefix = trimmed.charAt(0);
            String pattern = FilterBuilder.prefixPattern(trimmed.substring(1));
            switch (prefix) {
                case '+': {
                    filters.add(new Include(pattern));
                    continue block4;
                }
                case '-': {
                    filters.add(new Exclude(pattern));
                    continue block4;
                }
                default: {
                    throw new ReflectionsException("includeExclude should start with either + or -");
                }
            }
        }
        return new FilterBuilder(filters);
    }

    public FilterBuilder add(Predicate<String> filter2) {
        this.chain.add(filter2);
        return this;
    }

    @Override
    public boolean test(String regex2) {
        boolean accept = this.chain.isEmpty() || this.chain.get(0) instanceof Exclude;
        for (Predicate<String> filter2 : this.chain) {
            if (accept && filter2 instanceof Include || !accept && filter2 instanceof Exclude || (accept = filter2.test(regex2)) || !(filter2 instanceof Exclude)) continue;
            break;
        }
        return accept;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.chain, ((FilterBuilder)o).chain);
    }

    public int hashCode() {
        return Objects.hash(this.chain);
    }

    public String toString() {
        return this.chain.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    private static String prefixPattern(String fqn) {
        if (!fqn.endsWith(".")) {
            fqn = fqn + ".";
        }
        return fqn.replace(".", "\\.").replace("$", "\\$") + ".*";
    }

    static class Exclude
    extends Matcher {
        Exclude(String regex2) {
            super(regex2);
        }

        @Override
        public boolean test(String regex2) {
            return !this.pattern.matcher(regex2).matches();
        }

        @Override
        public String toString() {
            return "-" + this.pattern;
        }
    }

    static class Include
    extends Matcher {
        Include(String regex2) {
            super(regex2);
        }

        @Override
        public boolean test(String regex2) {
            return this.pattern.matcher(regex2).matches();
        }

        @Override
        public String toString() {
            return "+" + this.pattern;
        }
    }

    static abstract class Matcher
    implements Predicate<String> {
        final Pattern pattern;

        Matcher(String regex2) {
            this.pattern = Pattern.compile(regex2);
        }

        public int hashCode() {
            return Objects.hash(this.pattern);
        }

        public boolean equals(Object o) {
            return this == o || o != null && this.getClass() == o.getClass() && Objects.equals(this.pattern.pattern(), ((Matcher)o).pattern.pattern());
        }

        public String toString() {
            return this.pattern.pattern();
        }
    }
}

