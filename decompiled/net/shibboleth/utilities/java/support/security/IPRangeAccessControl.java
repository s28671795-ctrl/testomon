/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 */
package net.shibboleth.utilities.java.support.security;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.net.InetAddresses;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.IPRange;
import net.shibboleth.utilities.java.support.security.AccessControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPRangeAccessControl
extends AbstractIdentifiableInitializableComponent
implements AccessControl {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(IPRangeAccessControl.class);
    @Nonnull
    @NonnullElements
    private Collection<IPRange> allowedRanges = Collections.emptyList();

    public void setAllowedRanges(@Nonnull @NonnullElements Collection<IPRange> ranges) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        Constraint.isNotNull(ranges, "IPRange collection cannot be null");
        this.allowedRanges = new ArrayList<IPRange>(Collections2.filter(ranges, Predicates.notNull()));
    }

    @Override
    public boolean checkAccess(@Nonnull ServletRequest request, @Nullable String operation, @Nullable String resource) {
        Constraint.isNotNull(request, "ServletRequest cannot be null");
        String addr = request.getRemoteAddr();
        if (addr != null) {
            this.log.debug("{} Evaluating client address '{}'", (Object)this.getLogPrefix(), (Object)addr);
            try {
                byte[] resolvedAddress = InetAddresses.forString(addr).getAddress();
                for (IPRange range : this.allowedRanges) {
                    if (!range.contains(resolvedAddress)) continue;
                    return true;
                }
            }
            catch (IllegalArgumentException e) {
                this.log.warn(this.getLogPrefix() + " Error translating client address", e);
            }
            this.log.warn("{} Denied request from client address '{}'", (Object)this.getLogPrefix(), (Object)addr);
        } else {
            this.log.warn("{} No client address, access denied", (Object)this.getLogPrefix());
        }
        return false;
    }

    @Nonnull
    private String getLogPrefix() {
        return "Policy " + this.getId() + ":";
    }
}

