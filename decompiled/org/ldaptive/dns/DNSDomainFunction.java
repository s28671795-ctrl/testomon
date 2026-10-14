/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import java.util.function.Function;
import org.ldaptive.dn.Dn;
import org.ldaptive.dn.RDn;

public class DNSDomainFunction
implements Function<Dn, String> {
    @Override
    public String apply(Dn dn) {
        StringBuilder domain = new StringBuilder();
        for (RDn rdn : dn.getRDns()) {
            if (rdn.size() == 1 && rdn.getNameValue().hasName("DC")) {
                String attrValue = rdn.getNameValue().getStringValue();
                if (attrValue == null || attrValue.isEmpty() || ".".equals(attrValue)) continue;
                if (domain.length() > 0) {
                    domain.append('.');
                }
                domain.append(attrValue);
                continue;
            }
            if (domain.length() <= 0) continue;
            domain.setLength(0);
        }
        return domain.toString();
    }
}

