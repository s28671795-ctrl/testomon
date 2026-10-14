/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dn;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.LdapUtils;
import org.ldaptive.dn.DefaultDnParser;
import org.ldaptive.dn.DnParser;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDnNormalizer;

public class RDn {
    private static final int HASH_CODE_SEED = 5009;
    private final Set<NameValue> nameValues;

    public RDn(String rdn) {
        this(rdn, new DefaultDnParser());
    }

    public RDn(String rdn, DnParser parser) {
        List<RDn> rdns = parser.parse(rdn);
        if (rdns.isEmpty()) {
            throw new IllegalArgumentException("Invalid RDN: no RDNs found in " + rdn);
        }
        if (rdns.size() > 1) {
            throw new IllegalArgumentException("Invalid RDN: multiple RDNs found in " + rdn);
        }
        this.nameValues = Collections.unmodifiableSet(rdns.get(0).getNameValues());
    }

    public RDn(NameValue ... value) {
        this.nameValues = Stream.of(value).filter(Objects::nonNull).collect(Collectors.collectingAndThen(Collectors.toCollection(LinkedHashSet::new), Collections::unmodifiableSet));
    }

    public RDn(Collection<NameValue> values2) {
        this.nameValues = values2.stream().filter(Objects::nonNull).collect(Collectors.collectingAndThen(Collectors.toCollection(LinkedHashSet::new), Collections::unmodifiableSet));
    }

    public RDn(String attributeName, String attributeValue) {
        this.nameValues = Set.of(new NameValue(attributeName, attributeValue));
    }

    public NameValue getNameValue() {
        if (this.nameValues.isEmpty()) {
            return null;
        }
        return this.nameValues.iterator().next();
    }

    public Set<NameValue> getNameValues() {
        return this.nameValues;
    }

    public Set<NameValue> getNameValues(String name) {
        return this.nameValues.stream().filter(nv -> nv.hasName(name)).collect(Collectors.collectingAndThen(Collectors.toCollection(LinkedHashSet::new), Collections::unmodifiableSet));
    }

    public NameValue getNameValue(String name) {
        return this.getNameValues(name).stream().findFirst().orElse(null);
    }

    public int size() {
        return this.nameValues.size();
    }

    public String format() {
        return this.format(null);
    }

    public String format(RDnNormalizer normalizer) {
        String formatted;
        switch (this.nameValues.size()) {
            case 0: {
                return "";
            }
            case 1: {
                if (normalizer != null) {
                    formatted = normalizer.normalize(this).getNameValues().iterator().next().format();
                    break;
                }
                formatted = this.nameValues.iterator().next().format();
                break;
            }
            default: {
                formatted = normalizer != null ? normalizer.normalize(this).getNameValues().stream().map(NameValue::format).collect(Collectors.joining("+")) : this.nameValues.stream().map(NameValue::format).collect(Collectors.joining("+"));
            }
        }
        return formatted;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof RDn) {
            RDn v = (RDn)o;
            return LdapUtils.areEqual(this.nameValues, v.nameValues);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(5009, this.nameValues);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "nameValues=" + this.nameValues;
    }
}

