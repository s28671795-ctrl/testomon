/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.profile.logic;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.net.InetAddresses;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.IPRange;
import org.opensaml.messaging.context.BaseContext;

public class IPRangePredicate
implements Predicate<BaseContext> {
    @Nullable
    private HttpServletRequest httpRequest;
    @Nonnull
    @NonnullElements
    private Collection<IPRange> addressRanges = Collections.emptyList();

    IPRangePredicate() {
    }

    public void setAddressRanges(@Nonnull @NonnullElements Iterable<IPRange> ranges) {
        Constraint.isNotNull(ranges, "Address range collection cannot be null");
        this.addressRanges = new ArrayList<IPRange>();
        for (IPRange range : Iterables.filter(ranges, Predicates.notNull())) {
            this.addressRanges.add(range);
        }
    }

    public void setRanges(@Nonnull @NonnullElements Collection<IPRange> ranges) {
        Constraint.isNotNull(ranges, "Address range collection cannot be null");
        this.addressRanges = new ArrayList<IPRange>(Collections2.filter(ranges, Predicates.notNull()));
    }

    public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
        this.httpRequest = Constraint.isNotNull(request, "HttpServletRequest cannot be null");
    }

    @Override
    public boolean apply(@Nullable BaseContext input) {
        String address;
        String string = address = this.httpRequest != null ? this.httpRequest.getRemoteAddr() : null;
        if (address == null || !InetAddresses.isInetAddress(address)) {
            return false;
        }
        for (IPRange range : this.addressRanges) {
            if (!range.contains(InetAddresses.forString(address))) continue;
            return true;
        }
        return false;
    }
}

