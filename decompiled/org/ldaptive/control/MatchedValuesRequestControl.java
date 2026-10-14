/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import java.util.Arrays;
import java.util.stream.Stream;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.filter.ExtensibleFilter;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterParseException;
import org.ldaptive.filter.FilterParser;
import org.ldaptive.filter.FilterSet;

public class MatchedValuesRequestControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.2.826.0.1.3344810.2.3";
    private static final int HASH_CODE_SEED = 7057;
    private Filter[] matchedValuesFilters;

    public MatchedValuesRequestControl() {
        super(OID);
    }

    public MatchedValuesRequestControl(String ... filters) {
        this(filters, false);
    }

    public MatchedValuesRequestControl(String[] filters, boolean critical) {
        super(OID, critical);
        this.setMatchedValuesFilters(filters);
    }

    public MatchedValuesRequestControl(Filter ... filters) {
        this(filters, false);
    }

    public MatchedValuesRequestControl(Filter[] filters, boolean critical) {
        super(OID, critical);
        this.setMatchedValuesFilters(filters);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public Filter[] getMatchedValuesFilters() {
        return this.matchedValuesFilters;
    }

    public void setMatchedValuesFilters(String ... filters) {
        Filter[] parsedFilters = new Filter[filters.length];
        for (int i = 0; i < filters.length; ++i) {
            try {
                parsedFilters[i] = FilterParser.parse(filters[i]);
                continue;
            }
            catch (FilterParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
        this.setMatchedValuesFilters(parsedFilters);
    }

    public void setMatchedValuesFilters(Filter ... filters) {
        for (Filter filter2 : filters) {
            this.validateFilter(filter2);
        }
        this.matchedValuesFilters = filters;
    }

    private void validateFilter(Filter filter2) {
        if (filter2 == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }
        if (filter2 instanceof FilterSet) {
            throw new IllegalArgumentException("MatchedValuesRequestControl does not support AND, OR and NOT filter types");
        }
        if (filter2 instanceof ExtensibleFilter && ((ExtensibleFilter)filter2).getDnAttributes()) {
            throw new IllegalArgumentException("MatchedValuesRequestControl does not support an extensible filter with dnAttributes");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MatchedValuesRequestControl && super.equals(o)) {
            MatchedValuesRequestControl v = (MatchedValuesRequestControl)o;
            return LdapUtils.areEqual(this.matchedValuesFilters, v.matchedValuesFilters);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(7057, this.getOID(), this.getCriticality(), this.matchedValuesFilters);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "matchedValuesFilters=" + Arrays.toString(this.matchedValuesFilters) + "]";
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, (DEREncoder[])Stream.of(this.matchedValuesFilters).map(Filter::getEncoder).toArray(DEREncoder[]::new));
        return se.encode();
    }
}

