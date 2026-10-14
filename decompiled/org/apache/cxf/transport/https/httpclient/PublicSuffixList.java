/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https.httpclient;

import java.util.Collections;
import java.util.List;
import org.apache.cxf.transport.https.httpclient.DomainType;

public final class PublicSuffixList {
    private final DomainType type;
    private final List<String> rules;
    private final List<String> exceptions;

    public PublicSuffixList(DomainType type, List<String> rules, List<String> exceptions) {
        if (type == null) {
            throw new IllegalArgumentException("Domain type is null");
        }
        if (rules == null) {
            throw new IllegalArgumentException("Domain suffix rules are null");
        }
        this.type = type;
        this.rules = Collections.unmodifiableList(rules);
        this.exceptions = Collections.unmodifiableList(exceptions != null ? exceptions : Collections.emptyList());
    }

    public PublicSuffixList(List<String> rules, List<String> exceptions) {
        this(DomainType.UNKNOWN, rules, exceptions);
    }

    public DomainType getType() {
        return this.type;
    }

    public List<String> getRules() {
        return this.rules;
    }

    public List<String> getExceptions() {
        return this.exceptions;
    }
}

