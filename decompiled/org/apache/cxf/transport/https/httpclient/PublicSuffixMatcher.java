/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https.httpclient;

import java.net.IDN;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.transport.https.httpclient.DomainType;
import org.apache.cxf.transport.https.httpclient.PublicSuffixList;

public final class PublicSuffixMatcher {
    private final Map<String, DomainType> rules;
    private final Map<String, DomainType> exceptions;

    public PublicSuffixMatcher(Collection<String> rules, Collection<String> exceptions) {
        this(DomainType.UNKNOWN, rules, exceptions);
    }

    public PublicSuffixMatcher(DomainType domainType, Collection<String> rules, Collection<String> exceptions) {
        if (domainType == null) {
            throw new IllegalArgumentException("Domain type is null");
        }
        if (rules == null) {
            throw new IllegalArgumentException("Domain suffix rules are null");
        }
        this.rules = new ConcurrentHashMap<String, DomainType>(rules.size());
        for (String rule : rules) {
            this.rules.put(rule, domainType);
        }
        this.exceptions = new ConcurrentHashMap<String, DomainType>();
        if (exceptions != null) {
            for (String exception : exceptions) {
                this.exceptions.put(exception, domainType);
            }
        }
    }

    public PublicSuffixMatcher(Collection<PublicSuffixList> lists) {
        if (lists == null) {
            throw new IllegalArgumentException("Domain suffix lists are null");
        }
        this.rules = new ConcurrentHashMap<String, DomainType>();
        this.exceptions = new ConcurrentHashMap<String, DomainType>();
        for (PublicSuffixList list : lists) {
            DomainType domainType = list.getType();
            for (String rule : list.getRules()) {
                this.rules.put(rule, domainType);
            }
            if (list.getExceptions() == null) continue;
            for (String exception : list.getExceptions()) {
                this.exceptions.put(exception, domainType);
            }
        }
    }

    private static boolean hasEntry(Map<String, DomainType> map, String rule, DomainType expectedType) {
        if (map == null) {
            return false;
        }
        DomainType domainType = map.get(rule);
        if (domainType == null) {
            return false;
        }
        return expectedType == null || domainType.equals((Object)expectedType);
    }

    private boolean hasRule(String rule, DomainType expectedType) {
        return PublicSuffixMatcher.hasEntry(this.rules, rule, expectedType);
    }

    private boolean hasException(String exception, DomainType expectedType) {
        return PublicSuffixMatcher.hasEntry(this.exceptions, exception, expectedType);
    }

    public String getDomainRoot(String domain) {
        return this.getDomainRoot(domain, null);
    }

    public String getDomainRoot(String domain, DomainType expectedType) {
        if (domain == null) {
            return null;
        }
        if (domain.startsWith(".")) {
            return null;
        }
        String domainName = null;
        String segment = domain.toLowerCase(Locale.ROOT);
        while (segment != null) {
            String nextSegment;
            if (this.hasException(IDN.toUnicode(segment), expectedType)) {
                return segment;
            }
            if (this.hasRule(IDN.toUnicode(segment), expectedType)) break;
            int nextdot = segment.indexOf(46);
            String string = nextSegment = nextdot != -1 ? segment.substring(nextdot + 1) : null;
            if (nextSegment != null && this.hasRule("*." + IDN.toUnicode(nextSegment), expectedType)) break;
            if (nextdot != -1) {
                domainName = segment;
            }
            segment = nextSegment;
        }
        return domainName;
    }

    public boolean matches(String domain) {
        return this.matches(domain, null);
    }

    public boolean matches(String domain, DomainType expectedType) {
        if (domain == null) {
            return false;
        }
        String domainRoot = this.getDomainRoot(domain.startsWith(".") ? domain.substring(1) : domain, expectedType);
        return domainRoot == null;
    }
}

