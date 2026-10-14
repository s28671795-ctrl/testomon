/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dn;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.ldaptive.dn.AttributeValueEscaper;
import org.ldaptive.dn.DefaultAttributeValueEscaper;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDn;
import org.ldaptive.dn.RDnNormalizer;

public class DefaultRDnNormalizer
implements RDnNormalizer {
    private final AttributeValueEscaper valueEscaper;

    public DefaultRDnNormalizer() {
        this(new DefaultAttributeValueEscaper());
    }

    public DefaultRDnNormalizer(AttributeValueEscaper escaper) {
        this.valueEscaper = escaper;
    }

    public AttributeValueEscaper getValueEscaper() {
        return this.valueEscaper;
    }

    @Override
    public RDn normalize(RDn rdn) {
        Set nameValues = rdn.getNameValues().stream().map(nv -> new NameValue(this.normalizeName(nv.getName()), this.normalizeValue(nv.getStringValue()))).sorted(Comparator.comparing(nv -> nv.getName())).collect(Collectors.toCollection(LinkedHashSet::new));
        return new RDn(nameValues);
    }

    private String normalizeName(String name) {
        return name.toLowerCase();
    }

    private String normalizeValue(String value) {
        return this.valueEscaper.escape(value);
    }
}

