/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.ldaptive.LdapUtils;
import org.ldaptive.dn.DefaultDnParser;
import org.ldaptive.dn.DnParser;
import org.ldaptive.dn.RDn;
import org.ldaptive.dn.RDnNormalizer;

public class Dn {
    private static final int HASH_CODE_SEED = 5003;
    private final List<RDn> rdnComponents = new ArrayList<RDn>();

    public Dn() {
    }

    public Dn(String dn) {
        this(dn, new DefaultDnParser());
    }

    public Dn(String dn, DnParser parser) {
        this.rdnComponents.addAll(parser.parse(dn));
    }

    public Dn(RDn ... rdn) {
        this(Arrays.asList(rdn));
    }

    public Dn(List<RDn> rdns) {
        this.rdnComponents.addAll(rdns);
    }

    public RDn getRDn() {
        if (this.rdnComponents.size() == 0) {
            return null;
        }
        return this.rdnComponents.get(0);
    }

    public List<RDn> getRDns() {
        return this.rdnComponents;
    }

    public void add(Dn dn) {
        dn.getRDns().stream().forEach(rdn -> this.rdnComponents.add((RDn)rdn));
    }

    public void add(RDn rdn) {
        this.rdnComponents.add(rdn);
    }

    public void add(int index, RDn rdn) {
        this.rdnComponents.add(index, rdn);
    }

    public Dn subDn(int index) {
        return this.subDn(index, this.rdnComponents.size());
    }

    public Dn subDn(int beginIndex, int endIndex) {
        return new Dn(IntStream.range(0, this.rdnComponents.size()).filter(i -> i >= beginIndex && i < endIndex).mapToObj(i -> this.rdnComponents.get(i)).collect(Collectors.toList()));
    }

    public Collection<String> getValues(String name) {
        return this.rdnComponents.stream().filter(rdn -> rdn.getNameValue().hasName(name)).map(rdn -> rdn.getNameValue().getStringValue()).collect(Collectors.toList());
    }

    public String getValue(String name) {
        return this.getValues(name).stream().findFirst().orElse(null);
    }

    public int size() {
        return this.rdnComponents.size();
    }

    public String format() {
        return this.format(null);
    }

    public String format(RDnNormalizer normalizer) {
        if (this.rdnComponents == null || this.rdnComponents.size() == 0) {
            return "";
        }
        if (normalizer == null) {
            return this.rdnComponents.stream().map(RDn::format).collect(Collectors.joining(","));
        }
        return this.rdnComponents.stream().map(rdn -> rdn.format(normalizer)).collect(Collectors.joining(","));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Dn) {
            Dn v = (Dn)o;
            return LdapUtils.areEqual(this.rdnComponents, v.rdnComponents);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(5003, this.rdnComponents);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "rdnComponents=" + this.rdnComponents;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Dn object = new Dn();

        protected Builder() {
        }

        public Builder add(String dn) {
            this.object.add(new Dn(dn));
            return this;
        }

        public Builder add(Dn dn) {
            this.object.add(dn);
            return this;
        }

        public Builder add(RDn rdn) {
            this.object.add(rdn);
            return this;
        }

        public Dn build() {
            return this.object;
        }
    }
}

